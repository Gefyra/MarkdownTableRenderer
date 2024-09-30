package profileTableRenderer;

import lombok.Data;

@Data
public class FhirElement {

  private String id;
  private String type;
  private String min;
  private String max;
  private String pattern;
  private String fixed;
  private Boolean mustSupport;
  private String setBindingUrl;
  private String bindingStrength;
  private String vsConcepts;


  public String getMarkdown() {
    StringBuilder markdown = new StringBuilder();

    // Create a row for each variable
    markdown.append("| ").append(id != null ? id : "N/A").append(" | ")
        .append(type != null ? type : "N/A").append(" | ")
        .append(min != null ? min : "N/A").append(" | ")
        .append(max != null ? max : "N/A").append(" | ")
        .append(pattern != null ? pattern : "N/A").append(" | ")
        .append(fixed != null ? fixed : "N/A").append(" | ")
        .append(mustSupport != null ? mustSupport : "false").append(" | ")
        .append(setBindingUrl != null ? setBindingUrl : "N/A").append(" | ")
        .append(bindingStrength != null ? bindingStrength : "N/A").append(" | ")
        .append(vsConcepts != null ? vsConcepts : "N/A").append(" |\n");

    return markdown.toString();
  }
}
