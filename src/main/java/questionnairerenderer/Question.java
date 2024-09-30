package questionnairerenderer;

import java.util.Collections;
import java.util.List;
import lombok.Data;

@Data
public class Question {

  private String questionLinkId;
  private String questionText;
  private String questionType;
  private List<String> enableWhen;
  private String vsList;
  private String herkunft = "";
  private List<String> nutzung = Collections.emptyList();
}
