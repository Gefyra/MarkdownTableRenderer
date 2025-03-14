package profileTableRenderer;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.context.support.IValidationSupport.ValueSetExpansionOutcome;
import ca.uhn.fhir.context.support.ValidationSupportContext;
import ca.uhn.fhir.parser.IParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.hl7.fhir.common.hapi.validation.support.InMemoryTerminologyServerValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.PrePopulatedValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.ValidationSupportChain;
import org.hl7.fhir.r4.model.CodeSystem;
import org.hl7.fhir.r4.model.ElementDefinition;
import org.hl7.fhir.r4.model.ElementDefinition.ElementDefinitionBindingComponent;
import org.hl7.fhir.r4.model.Resource;
import org.hl7.fhir.r4.model.StructureDefinition;
import org.hl7.fhir.r4.model.Type;
import org.hl7.fhir.r4.model.ValueSet;

public class ProfileProcessor {

  private final FhirContext fhirContext;
  private final ValidationSupportChain terminologySupportChain;
  private final PrePopulatedValidationSupport prePopulatedValidationSupport;
  private final List<Resource> resources;
  private final InMemoryTerminologyServerValidationSupport inMemoryTerminologyServerValidationSupport;
  private final IParser iParser;

  /**
   * Constructs a ProfileProcessor with the specified file path.
   * Initializes the FHIR context, validation support chain, and loads resources from the folder.
   *
   * @param filePath the path to the folder containing FHIR resources
   */
  public ProfileProcessor(String filePath) {
    this.fhirContext = FhirContext.forR4();
    prePopulatedValidationSupport = new PrePopulatedValidationSupport(fhirContext);
    inMemoryTerminologyServerValidationSupport = new InMemoryTerminologyServerValidationSupport(fhirContext);
    terminologySupportChain = new ValidationSupportChain(
        new DefaultProfileValidationSupport(fhirContext), prePopulatedValidationSupport,
        inMemoryTerminologyServerValidationSupport);

    resources = loadResourcesFromFolder(filePath);
    preloadTerminologyResources(resources);
    iParser = fhirContext.newJsonParser().setPrettyPrint(true);
  }

  /**
   * The main method to run the ProfileProcessor.
   * Expects a single argument for the path to the resources folder.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("missing: <path_to_resources_folder>");
      return;
    }
    String filePath = args[0];
    ProfileProcessor processor = new ProfileProcessor(filePath);
    processor.processResources();
  }

  /**
   * Preloads terminology resources (ValueSet and CodeSystem) into the validation support.
   *
   * @param resources the list of resources to preload
   */
  private void preloadTerminologyResources(List<Resource> resources) {
    if (resources == null || resources.isEmpty()) {
      System.out.println("No resources to preload.");
      return;
    }

    resources.stream()
        .filter(resource -> resource instanceof ValueSet || resource instanceof CodeSystem)
        .forEach(resource -> {
          if (resource instanceof ValueSet valueSet) {
            prePopulatedValidationSupport.addValueSet(valueSet);
            System.out.println("Loaded ValueSet: " + valueSet.getUrl());
          } else if (resource instanceof CodeSystem codeSystem) {
            prePopulatedValidationSupport.addCodeSystem(codeSystem);
            System.out.println("Loaded CodeSystem: " + codeSystem.getUrl());
          }
        });
  }

  /**
   * Processes the loaded resources and generates markdown files for each profile.
   */
  private void processResources() {
    List<Profile> profiles = resources.stream()
        .filter(resource -> resource instanceof StructureDefinition)
        .map(resource -> (StructureDefinition) resource)
        .map(this::processStructureDefinition)
        .collect(Collectors.toList());

    profiles.forEach(this::saveMarkdown);
  }

  /**
   * Saves the profile information as a markdown file.
   *
   * @param profile the profile to save
   */
  private void saveMarkdown(Profile profile) {
    StringBuilder markdown = new StringBuilder();
    markdown.append(profile.getMarkdown()).append("\n").append("\n");

    markdown.append(
        "| ID        | Type      | Min  | Max  | Pattern   | Fixed    | must-support| VS-Url      | Strength    | VS Concepts |\n");
    markdown.append(
        "|-----------|-----------|------|------|-----------|----------|-------------|-------------|-------------|-------------|\n");

    profile.getElements().forEach(e -> markdown.append(e.getMarkdown()));

    String folderName = "profileTables";
    Path folderPath = Paths.get(folderName);

    try {
      if (!Files.exists(folderPath)) {
        Files.createDirectories(folderPath);
      }

      String fileName = profile.getName() + ".md";
      Path filePath = folderPath.resolve(fileName);

      Files.write(filePath, markdown.toString().getBytes());

      System.out.println("Markdown saved to: " + filePath.toString());

    } catch (IOException e) {
      System.err.println("Error saving markdown for profile: " + profile.getName());
      e.printStackTrace();
    }
  }

  /**
   * Processes a StructureDefinition resource and extracts profile information.
   *
   * @param structureDefinition the StructureDefinition to process
   * @return the processed Profile
   */
  private Profile processStructureDefinition(StructureDefinition structureDefinition) {
    if (structureDefinition == null) {
      System.out.println("Null StructureDefinition encountered, skipping.");
      return null;
    }

    String name = structureDefinition.getName();
    String url = structureDefinition.getUrl();

    Profile profile = new Profile();
    profile.setName(name);
    profile.setUrl(url);

    System.out.println("-----------------------####-----------------------");
    System.out.println("StructureDefinition Name: " + name);
    System.out.println("StructureDefinition URL: " + url);

    String resourceType = structureDefinition.getType();
    profile.setType(resourceType);
    System.out.println("Profiled Resource Type: " + resourceType);

    List<ElementDefinition> snapshotElements = structureDefinition.getSnapshot().getElement();
    List<ElementDefinition> diffElements = structureDefinition.getDifferential().getElement();
    if (diffElements != null && diffElements.size() > 0) {
      List<FhirElement> elements = processElements(diffElements, snapshotElements);
      profile.setElements(elements);
    } else {
      System.err.println("No diffElements found in StructureDefinition.differential");
    }
    System.out.println("-------------------------");
    return profile;
  }

  /**
   * Processes a list of ElementDefinition objects and extracts FhirElement information.
   *
   * @param elements the list of ElementDefinition objects to process
   * @param snapshotElements the list of snapshot elements for reference
   * @return the list of processed FhirElement objects
   */
  private List<FhirElement> processElements(List<ElementDefinition> elements,
      List<ElementDefinition> snapshotElements) {
    if (elements == null || elements.isEmpty()) {
      System.out.println("No elements to process.");
      return null;
    }

    List<FhirElement> fhirElements = elements.stream()
        .map(e -> processElement(e, snapshotElements)).collect(Collectors.toList());

    return fhirElements;
  }

  /**
   * Processes a single ElementDefinition and extracts FhirElement information.
   *
   * @param element the ElementDefinition to process
   * @param snapshotElements the list of snapshot elements for reference
   * @return the processed FhirElement
   */
  private FhirElement processElement(ElementDefinition element,
      List<ElementDefinition> snapshotElements) {
    if (element == null) {
      System.out.println("Null ElementDefinition encountered, skipping.");
      return null;
    }

    FhirElement fhirElement = new FhirElement();

    String id = element.getId();
    if (id != null) {
      System.out.println("Element Id: " + id);
    } else {
      System.out.println("Element has no id.");
    }
    fhirElement.setId(id);

    Optional<ElementDefinition> matchingSnapshotElement = snapshotElements.stream().filter(
            snapshotElement -> snapshotElement.getId() != null && snapshotElement.getId().equals(id))
        .findFirst();

    String min = null;
    String max = null;

    if (element.hasMin()) {
      min = String.valueOf(element.getMin());
    }

    if (element.hasMax()) {
      max = element.getMax();
    }

    if (min == null || max == null) {
      if (min == null && matchingSnapshotElement.isPresent()) {
        min = String.valueOf(matchingSnapshotElement.get().getMin());
      }

      if (max == null && matchingSnapshotElement.isPresent()) {
        max = matchingSnapshotElement.get().getMax();
      }
    }

    System.out.println("Cardinality: " + min + ".." + max);
    fhirElement.setMin(min);
    fhirElement.setMax(max);

    if (max.equals("0")) {
      System.out.println("Element max = 0, skipping further processing.");
      return fhirElement;
    }

    if (element.hasMustSupport()) {
      fhirElement.setMustSupport(element.getMustSupport());
    }

    if (element.hasPattern()) {
      Type pattern = element.getPattern();
      String patternString = iParser.encodeToString(pattern);
      System.out.println("Pattern: " + pattern.fhirType() + " - " + patternString);
      fhirElement.setPattern(patternString);
    } else {
      System.out.println("No pattern defined for this element.");
    }

    if (element.hasFixed()) {
      Type fixedValue = element.getFixed();
      String fixedValueString = iParser.encodeToString(fixedValue);
      System.out.println("Fixed Value: " + fixedValue.fhirType() + " - " + fixedValueString);
      fhirElement.setFixed(fixedValueString);
    } else {
      System.out.println("No fixed value defined for this element.");
    }

    AtomicReference<String> type = new AtomicReference<>("");
    if (element.hasType()) {
      element.getType().forEach(typeRef -> {
        if (typeRef.hasCode()) {
          type.set(appendTypeWithSeparator(type.get(), typeRef.getCode()));
        }
      });
      System.out.println("Element Type: " + type.get());

    } else if (matchingSnapshotElement.isPresent() && matchingSnapshotElement.get().hasType()) {
      System.out.println("Type found in snapshot element:");
      matchingSnapshotElement.get().getType().forEach(typeRef -> {
        if (typeRef.hasCode()) {
          type.set(appendTypeWithSeparator(type.get(), typeRef.getCode()));
        } else {
          System.err.println("Element has no type code in snapshot.");
        }
      });
      System.out.println("Element Type: " + type.get());
    } else {
      System.err.println("Element has no type.");
    }
    fhirElement.setType(type.get());

    if (element.hasBinding()) {
      ElementDefinitionBindingComponent binding = element.getBinding();
      String valueSetUrl = binding.getValueSet();
      if (valueSetUrl != null) {
        fhirElement.setSetBindingUrl(getUrlWithoutVersion(valueSetUrl));
        fhirElement.setBindingStrength(binding.getStrength().toCode());
        System.out.println("Element has a ValueSet binding: " + valueSetUrl);
        String expandedValueSet = expandValueSet(valueSetUrl);
        fhirElement.setVsConcepts(expandedValueSet);
      }
    } else if (matchingSnapshotElement.isPresent() && matchingSnapshotElement.get().hasBinding()) {
      ElementDefinition snapshotElement = matchingSnapshotElement.get();
      ElementDefinitionBindingComponent binding = snapshotElement.getBinding();
      String valueSetUrl = binding.getValueSet();
      if (valueSetUrl != null) {
        fhirElement.setSetBindingUrl(getUrlWithoutVersion(valueSetUrl));
        fhirElement.setBindingStrength(binding.getStrength().toCode());
        System.out.println("Element has a ValueSet binding: " + valueSetUrl);
        String expandedValueSet = expandValueSet(valueSetUrl);
        fhirElement.setVsConcepts(expandedValueSet);
      }
    }
    System.out.println("-------------------------");
    return fhirElement;
  }

  /**
   * Expands a ValueSet and returns the concepts as a string.
   *
   * @param valueSetUrl the URL of the ValueSet to expand
   * @return the expanded ValueSet concepts as a string
   */
  private String expandValueSet(String valueSetUrl) {
    AtomicReference<String> expandedValueSetConcepts = new AtomicReference<>("");
    try {
      ValidationSupportContext context = new ValidationSupportContext(terminologySupportChain);
      ValueSetExpansionOutcome expandedValueSet = terminologySupportChain.expandValueSet(context,
          null, valueSetUrl);
      ValueSet valueSet = (ValueSet) expandedValueSet.getValueSet();

      if (valueSet != null && valueSet.hasExpansion()) {
        valueSet.getExpansion().getContains().forEach(concept -> {
          expandedValueSetConcepts.set(
              appendTypeWithSeparator(expandedValueSetConcepts.get(), concept.getDisplay()));
          System.out.println("Concept: " + concept.getCode() + " - " + concept.getDisplay());
        });
      } else {
        System.err.println("Unable to expand ValueSet: " + valueSetUrl);
      }
    } catch (Exception e) {
      System.err.println("Error expanding ValueSet: " + valueSetUrl + " - " + e.getMessage());
    }
    return expandedValueSetConcepts.get();
  }

  /**
   * Loads FHIR resources from the specified folder.
   *
   * @param folderPath the path to the folder containing FHIR resources
   * @return the list of loaded resources
   */
  public List<Resource> loadResourcesFromFolder(String folderPath) {
    List<Resource> resources = new ArrayList<>();
    File folder = new File(folderPath);

    if (!folder.isDirectory()) {
      throw new IllegalArgumentException("Provided path is not a directory");
    }

    processFolder(folder, resources);

    return resources;
  }

  /**
   * Processes a folder and its subfolders to load FHIR resources.
   *
   * @param folder the folder to process
   * @param resources the list to which the loaded resources will be added
   */
  private void processFolder(File folder, List<Resource> resources) {
    File[] filesAndSubfolders = folder.listFiles();

    if (filesAndSubfolders != null) {
      IParser parser = fhirContext.newJsonParser();

      for (File fileOrSubfolder : filesAndSubfolders) {
        if (fileOrSubfolder.isDirectory()) {
          processFolder(fileOrSubfolder, resources);
        } else if (fileOrSubfolder.isFile() && fileOrSubfolder.getName().endsWith(".json")) {
          try (FileInputStream inputStream = new FileInputStream(fileOrSubfolder)) {
            Resource resource = (Resource) parser.parseResource(inputStream);
            resources.add(resource);
          } catch (Exception e) {
            System.err.println(
                "Error parsing file: " + fileOrSubfolder.getName() + " - " + e.getMessage());
          }
        }
      }
    }
  }

  /**
   * Appends a string to a base string with a separator, ensuring a maximum of 10 items.
   *
   * @param baseString the base string
   * @param appendString the string to append
   * @return the combined string with a separator
   */
  private String appendTypeWithSeparator(String baseString, String appendString) {
    if (appendString == null || appendString.isEmpty()) {
      return baseString;
    }

    int commaCount =
        baseString == null ? 0 : baseString.length() - baseString.replace(",", "").length();

    if (commaCount >= 9) {
      if (!baseString.endsWith(", ...")) {
        return baseString + ", ...";
      }
      return baseString;
    }

    if (baseString != null && !baseString.isEmpty()) {
      return baseString + ", " + appendString;
    } else {
      return appendString;
    }
  }

  /**
   * Removes the version from a URL, if present.
   *
   * @param input the URL with or without a version
   * @return the URL without the version
   */
  private String getUrlWithoutVersion(String input) {
    if (input == null || input.isEmpty()) {
      return "";
    }

    String[] parts = input.split("\\|", 2);
    return parts[0];
  }
}