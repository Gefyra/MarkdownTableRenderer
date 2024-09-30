package questionnairerenderer;

import ca.uhn.fhir.context.FhirContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.lang.model.type.ReferenceType;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.DateType;
import org.hl7.fhir.r4.model.DecimalType;
import org.hl7.fhir.r4.model.Extension;
import org.hl7.fhir.r4.model.IntegerType;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Questionnaire;
import org.hl7.fhir.r4.model.Questionnaire.QuestionnaireItemComponent;
import org.hl7.fhir.r4.model.Questionnaire.QuestionnaireItemEnableWhenComponent;
import org.hl7.fhir.r4.model.Resource;
import org.hl7.fhir.r4.model.ResourceType;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.r4.model.TimeType;
import org.hl7.fhir.r4.model.Type;
import org.hl7.fhir.r4.model.ValueSet;
import org.hl7.fhir.r4.model.ValueSet.ValueSetExpansionContainsComponent;

public class QuestionnaireProcessor {

  private final String itemSourceExtensionUrl = "https://www.oegd.de/fhir/seu/StructureDefinition/ItemSource";
  private FhirContext fhirContext;

  public QuestionnaireProcessor() {
    this.fhirContext = FhirContext.forR4();
  }

  public Questionnaire loadQuestionnaireFromFile(String filePath) throws IOException {
    File file = new File(filePath);
    FileInputStream inputStream = new FileInputStream(file);
    return (Questionnaire) fhirContext.newJsonParser().parseResource(inputStream);
  }

  public List<Question> collectQuestions(Questionnaire questionnaire) {
    List<Question> questions = new ArrayList<>();
    for (QuestionnaireItemComponent item : questionnaire.getItem()) {
      questions.addAll(collectQuestionTextsFromItem(item, questionnaire.getContained()));
    }
    return questions;
  }

  private List<Question> collectQuestionTextsFromItem(QuestionnaireItemComponent item,
      List<Resource> contained) {
    List<Question> questions = new ArrayList<>();
    questions.add(handleItem(item, contained));
    if (item.hasItem()) {
      for (QuestionnaireItemComponent nestedItem : item.getItem()) {
        questions.addAll(collectQuestionTextsFromItem(nestedItem, contained));
      }
    }
    return questions;
  }

  private Question handleItem(QuestionnaireItemComponent item, List<Resource> contained) {
    Question question = new Question();
    question.setQuestionLinkId(item.getLinkId());
    question.setQuestionText(item.getText());
    question.setQuestionType(item.getType().toCode());
    question.setEnableWhen(handleEnableWhen(item));
    question.setVsList(handleVSBinding(item, contained));
    question.setHerkunft(getOriginExtensionValue(item));
    return question;
  }

  private String getOriginExtensionValue(QuestionnaireItemComponent item) {
    Extension originExtension = item.getExtensionByUrl(itemSourceExtensionUrl);
    if (originExtension != null) {
      Coding origin = (Coding) originExtension.getValue();
      return origin.getCode();
    }
    return "";
  }

  private String handleVSBinding(QuestionnaireItemComponent item, List<Resource> contained) {
    StringBuilder vsDisplaysString = new StringBuilder();
    String answerValueSetUri = item.getAnswerValueSet();
    if (Objects.nonNull(answerValueSetUri)) {
      Optional<ValueSet> valueSet = contained.stream()
          .filter(r -> r.getResourceType().equals(ResourceType.ValueSet))
          .map(r -> (ValueSet) r).filter(vs -> vs.getId().equals(answerValueSetUri)).findFirst();
      if (valueSet.isPresent()) {
        List<ValueSetExpansionContainsComponent> contains = valueSet.get().getExpansion()
            .getContains();
        for (int i = 0; i < 9 && i < contains.size(); i++) {
          vsDisplaysString.append(contains.get(i).getDisplay() + " :: ");
        }
        if (contains.size() > 10) {
          vsDisplaysString.append("...");
        } else {
          vsDisplaysString.setLength(vsDisplaysString.length() - 3);
        }
      }
    }
    return vsDisplaysString.toString();
  }

  private List<String> handleEnableWhen(QuestionnaireItemComponent item) {
    List<String> enableWhenStrings = new ArrayList<>();
    List<QuestionnaireItemEnableWhenComponent> enableWhenComponents = item.getEnableWhen();
    enableWhenComponents.forEach(e -> {
      Type answer = e.getAnswer();
      String linkId = e.getQuestion();
      String operatorCode = e.getOperator().toCode();
      String answerString = "";
      if (answer instanceof BooleanType) {
        answerString = BooleanType.class.cast(answer).getValue().toString();
      } else if (answer instanceof StringType) {
        answerString = StringType.class.cast(answer).getValue().toString();
      } else if (answer instanceof DecimalType) {
        answerString = ((DecimalType) answer).getValue().toString();
      } else if (answer instanceof IntegerType) {
        answerString = IntegerType.class.cast(answer).getValue().toString();
      } else if (answer instanceof DateType) {
        answerString = DateType.class.cast(answer).getValue().toString();
      } else if (answer instanceof DateType) {
        answerString = DateType.class.cast(answer).getValue().toString();
      } else if (answer instanceof DateTimeType) {
        answerString = DateTimeType.class.cast(answer).getValue().toString();
      } else if (answer instanceof TimeType) {
        answerString = TimeType.class.cast(answer).getValue().toString();
      } else if (answer instanceof Coding) {
        Coding coding = Coding.class.cast(answer);
        answerString = coding.getCode();
      } else if (answer instanceof Quantity) {
        answerString = Quantity.class.cast(answer).toString();
      } else if (answer instanceof ReferenceType) {
        answerString = ReferenceType.class.cast(answer).toString();
      }
      enableWhenStrings.add(linkId + " " + operatorCode + " " + answerString);
    });
    return enableWhenStrings;
  }

  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println(
          "Usage: java questionnaireRenderer.QuestionnaireProcessor <path_to_questionnaire_file>");
      return;
    }

    String filePath = args[0];
    QuestionnaireProcessor processor = new QuestionnaireProcessor();

    try {
      Questionnaire questionnaire = processor.loadQuestionnaireFromFile(filePath);
      List<Question> questions = processor.collectQuestions(questionnaire);

// Create Markdown table
      StringBuilder sb = new StringBuilder();

      // Header row for Markdown
      sb.append("| Link-ID | Text |  Typ | Enable When | Wertemenge | Herkunft | Nutzung |\n");
      sb.append(
          "|------------------|---------------|---------------|-------------|-------------|-------------|-------------|\n");

      // Create CSV file
      String questionnaireName = questionnaire.getId();
      questionnaireName = questionnaireName.replace("/", "-");
      String csvFilePath = questionnaireName + ".csv";
      try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFilePath))) {
        // Header row for CSV
        csvWriter.println("\"Link-ID\",\"Text\",\"Typ\",\"Enable When\",\"Wertemenge\",\"Herkunft\",\"Nutzung\"");

        // Data rows
        for (Question question : questions) {
          // Add row to Markdown table
          sb.append("| ")
              .append(question.getQuestionLinkId()).append(" | ")
              .append(question.getQuestionText()).append(" | ")
              .append(question.getQuestionType()).append(" | ")
              .append(String.join(", ", question.getEnableWhen())).append(" | ")
              .append(question.getVsList()).append(" | ")
              .append(question.getHerkunft()).append(" | ")
              .append(String.join(", ", question.getNutzung())).append(" |\n");

          // Add row to CSV file
          csvWriter.println(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
              question.getQuestionLinkId(),
              question.getQuestionText(),
              question.getQuestionType(),
              String.join(";", question.getEnableWhen()),
              question.getVsList(),
              question.getHerkunft(),
              String.join(";", question.getNutzung())
          ));
        }
      } catch (IOException e) {
        System.err.println("Error writing CSV file: " + e.getMessage());
      }

      // Print Markdown table to console
      System.out.println(sb.toString());


    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Failed to load questionnaire from file: " + filePath);
    }
  }

  public static <T extends Type> Class<T> getTypeClass(Type type) {
    return (Class<T>) type.getClass();
  }

  @SuppressWarnings("unchecked")
  public static <T extends Type> T castType(Type type, Class<T> targetType) {
    if (targetType.isInstance(type)) {
      return targetType.cast(type);
    } else {
      throw new IllegalArgumentException("Type cannot be cast to " + targetType.getSimpleName());
    }
  }
}
