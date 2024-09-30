package profileTableRenderer;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.context.support.IValidationSupport.ValueSetExpansionOutcome;
import ca.uhn.fhir.context.support.ValidationSupportContext;
import ca.uhn.fhir.parser.IParser;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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


  public ProfileProcessor(String filePath) throws IOException {
    // Initialize FHIR context (for R4 version in this case)
    this.fhirContext = FhirContext.forR4();
    // Create a validation support chain with DefaultProfileValidationSupport and in-memory support
    prePopulatedValidationSupport = new PrePopulatedValidationSupport(fhirContext);
    inMemoryTerminologyServerValidationSupport = new InMemoryTerminologyServerValidationSupport(
        fhirContext);
    terminologySupportChain = new ValidationSupportChain(
        new DefaultProfileValidationSupport(fhirContext),
        prePopulatedValidationSupport,
        inMemoryTerminologyServerValidationSupport);

    resources = loadResourcesFromFolder(filePath);
    // Preload all CodeSystems and ValueSets
    preloadTerminologyResources(resources);
    iParser = fhirContext.newJsonParser().setPrettyPrint(true);

  }

  private void preloadTerminologyResources(List<Resource> resources) {
    if (resources == null || resources.isEmpty()) {
      System.out.println("No resources to preload.");
      return;
    }

    // Filter resources for ValueSet and CodeSystem and add them to prePopulatedValidationSupport
    resources.stream()
        .filter(resource -> resource instanceof ValueSet || resource instanceof CodeSystem)
        .forEach(resource -> {
          if (resource instanceof ValueSet) {
            ValueSet valueSet = (ValueSet) resource;
            prePopulatedValidationSupport.addValueSet(valueSet);
            System.out.println("Loaded ValueSet: " + valueSet.getUrl());
          } else if (resource instanceof CodeSystem) {
            CodeSystem codeSystem = (CodeSystem) resource;
            prePopulatedValidationSupport.addCodeSystem(codeSystem);
            System.out.println("Loaded CodeSystem: " + codeSystem.getUrl());
          }
        });
  }

  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      System.out.println(
          "missing: <path_to_resources_folder>");
      return;
    }
    String filePath = args[0];
    ProfileProcessor processor = new ProfileProcessor(filePath);
    processor.processResources();

  }

  private void processResources() {
    // Filter out only StructureDefinition resources
    List<Profile> profiles = resources.stream()
        .filter(resource -> resource instanceof StructureDefinition)
        .map(resource -> (StructureDefinition) resource)
        .map(this::processStructureDefinition)  // Collect the outcome of the processing
        .collect(Collectors.toList());

    profiles.forEach(this::saveMarkdown);

  }

  private void saveMarkdown(Profile profile) {
    StringBuilder markdown = new StringBuilder();
    markdown.append(profile.getMarkdown()).append("\n").append("\n");

    // Create the header row
    markdown.append(
        "| ID        | Type      | Min  | Max  | Pattern   | Fixed    | must-support| VS-Url      | Strength    | VS Concepts |\n");
    markdown.append(
        "|-----------|-----------|------|------|-----------|----------|-------------|-------------|-------------|-------------|\n");

    profile.getElements().forEach(e -> markdown.append(e.getMarkdown()));

    // Define the folder and file path
    String folderName = "profileTables";
    Path folderPath = Paths.get(folderName);

    try {
      // Create the folder if it doesn't exist
      if (!Files.exists(folderPath)) {
        Files.createDirectories(folderPath);
      }

      // Use the profile's name as the filename
      String fileName = profile.getName() + ".md";
      Path filePath = folderPath.resolve(fileName);

      // Write the markdown content to the file
      Files.write(filePath, markdown.toString().getBytes());

      System.out.println("Markdown saved to: " + filePath.toString());

    } catch (IOException e) {
      System.err.println("Error saving markdown for profile: " + profile.getName());
      e.printStackTrace();
    }
  }

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

    // Extract the profiled resource type
    String resourceType = structureDefinition.getType();
    profile.setType(resourceType);
    System.out.println("Profiled Resource Type: " + resourceType);

    // Get all diffElements in the StructureDefinition
    List<ElementDefinition> snapshotElements = structureDefinition.getSnapshot().getElement();
    List<ElementDefinition> diffElements = structureDefinition.getDifferential().getElement();
    if (diffElements != null && diffElements.size() > 0) {
      List<FhirElement> elements = processElements(diffElements, snapshotElements);
      profile.setElements(elements);
    } else {
      System.err.println("No diffElements found in StructureDefinition.differentiel");
    }
    System.out.println("-------------------------");
    return profile;
  }

  private List<FhirElement> processElements(List<ElementDefinition> elements,
      List<ElementDefinition> snapshotElements) {
    if (elements == null || elements.isEmpty()) {
      System.out.println("No elements to process.");
      return null;
    }

    // Skip the first element and process the rest
    List<FhirElement> fhirElements = elements.stream()
        //.skip(1)
        .map(e -> processElement(e, snapshotElements))
        .collect(Collectors.toList());

    return fhirElements;
  }

  private FhirElement processElement(ElementDefinition element,
      List<ElementDefinition> snapshotElements) {
    if (element == null) {
      System.out.println("Null ElementDefinition encountered, skipping.");
      return null;
    }

    FhirElement fhirElement = new FhirElement();

    // Extract element details with null checks
    String id = element.getId();
    if (id != null) {
      System.out.println("Element Id: " + id);
    } else {
      System.out.println("Element has no id.");
    }
    fhirElement.setId(id);

    // Look for the matching element in the snapshotElements list by ID
    Optional<ElementDefinition> matchingSnapshotElement = snapshotElements.stream()
        .filter(snapshotElement -> snapshotElement.getId() != null && snapshotElement.getId()
            .equals(id))
        .findFirst();

    // Extract cardinality
    String min = null;
    String max = null;

    // Only search for a matching snapshot element if min or max is missing
    if (element.hasMin()) {
      min = String.valueOf(element.getMin());
    }

    if (element.hasMax()) {
      max = element.getMax();
    }

    // If either min or max is null, find the matching element in snapshotElements
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

    // Check and print pattern if available
    if (element.hasPattern()) {
      Type pattern = element.getPattern();
      String patternString = iParser.encodeToString(pattern);
      System.out.println("Pattern: " + pattern.fhirType() + " - " + patternString);
      fhirElement.setPattern(patternString);

    } else {
      System.out.println("No pattern defined for this element.");
    }

    // Check and print fixed value if available
    if (element.hasFixed()) {
      Type fixedValue = element.getFixed();
      String fixedValueString = iParser.encodeToString(fixedValue);
      System.out.println("Fixed Value: " + fixedValue.fhirType() + " - " + fixedValueString);
      fhirElement.setFixed(fixedValueString);
    } else {
      System.out.println("No fixed value defined for this element.");
    }

    AtomicReference<String> type = new AtomicReference<>("");
    // Check and print types if available, otherwise fallback to snapshot
    if (element.hasType()) {
      element.getType().forEach(typeRef -> {
        if (typeRef.hasCode()) {
          type.set(appendTypeWithSeparator(type.get(), typeRef.getCode()));
        }
      });
      System.out.println("Element Type: " + type.get());

    } else if (matchingSnapshotElement.isPresent() && matchingSnapshotElement.get().hasType()) {
      // Fallback to snapshot element type if the current element has no type
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
    // Handle binding to a ValueSet
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

  private String expandValueSet(String valueSetUrl) {
    AtomicReference<String> expandedValueSetConcepts = new AtomicReference<>("");
    try {
      // Expand ValueSet locally
      ValidationSupportContext context = new ValidationSupportContext(terminologySupportChain);
      ValueSetExpansionOutcome expandedValueSet = terminologySupportChain.expandValueSet(context,
          null,
          valueSetUrl);
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

  public List<Resource> loadResourcesFromFolder(String folderPath) {
    List<Resource> resources = new ArrayList<>();
    File folder = new File(folderPath);

    if (!folder.isDirectory()) {
      throw new IllegalArgumentException("Provided path is not a directory");
    }

    // Recursively process files and subfolders
    processFolder(folder, resources);

    return resources;
  }

  private void processFolder(File folder, List<Resource> resources) {
    File[] filesAndSubfolders = folder.listFiles();

    if (filesAndSubfolders != null) {
      IParser parser = fhirContext.newJsonParser();

      for (File fileOrSubfolder : filesAndSubfolders) {
        if (fileOrSubfolder.isDirectory()) {
          // Recursively process subfolder
          processFolder(fileOrSubfolder, resources);
        } else if (fileOrSubfolder.isFile() && fileOrSubfolder.getName().endsWith(".json")) {
          try (FileInputStream inputStream = new FileInputStream(fileOrSubfolder)) {
            // Parse each file as a FHIR resource
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

  private String appendTypeWithSeparator(String baseString, String appendString) {
    if (appendString == null || appendString.isEmpty()) {
      return baseString; // Return baseString unchanged if appendString is null or empty
    }

    // Count the number of commas in baseString to infer the number of added concepts
    int commaCount = baseString == null ? 0 : baseString.length() - baseString.replace(",", "").length();

    // If there are already 10 concepts, return the string with "..." appended if not already added
    if (commaCount >= 9) { // 9 commas mean 10 items
      if (!baseString.endsWith(", ...")) {
        return baseString + ", ...";
      }
      return baseString;
    }

    // Append the new concept with ", " separator if baseString is not empty
    if (baseString != null && !baseString.isEmpty()) {
      return baseString + ", " + appendString;
    } else {
      // If baseString is empty, just append the appendString
      return appendString;
    }
  }


  private String getUrlWithoutVersion(String input) {
    if (input == null || input.isEmpty()) {
      return "";  // Return an empty string if the input is null or empty
    }

    // Split the string at "|" and return the first part
    String[] parts = input.split("\\|",
        2); // The limit of 2 ensures it splits only at the first occurrence
    return parts[0]; // Return the first part
  }

}
