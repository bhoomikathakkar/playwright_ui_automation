package digifyE2E.restAssured.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentSecurityFileReplaceResponse {
    @JsonProperty(value = "Guid", required = true)
    private String Guid;

    @JsonProperty(value = "Link", required = true)
    private String Link;

    @JsonProperty("EmbedLink")
    private String embedLink;

    @JsonProperty("FileName")
    private String fileName;

    @JsonProperty("Version")
    private Integer version;

    @JsonProperty(value = "Status", required = true)
    private Status status;
}
