package profileTableRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Data;

@Data
public class Profile {

  private String name;
  private String url;
  private String type;
  private List<FhirElement> elements = Collections.emptyList();

  public String getMarkdown() {
    StringBuilder markdown = new StringBuilder();

    // Create the header row
    markdown.append("| Variable | Value |\n");
    markdown.append("|----------|-------|\n");

    // Create a row for each variable
    markdown.append("| Name     | ").append(name != null ? name : "N/A").append(" |\n");
    markdown.append("| URL      | ").append(url != null ? url : "N/A").append(" |\n");
    markdown.append("| Type     | ").append(type != null ? type : "N/A").append(" |\n");

    return markdown.toString();
  }
}
