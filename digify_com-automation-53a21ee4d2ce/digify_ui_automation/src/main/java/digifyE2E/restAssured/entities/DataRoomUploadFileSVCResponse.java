package digifyE2E.restAssured.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataRoomUploadFileSVCResponse {

    @JsonProperty(value = "FileName", required = true)
    private String fileName;

    @JsonProperty(value = "Guid", required = true)
    private String guid;

    @JsonProperty(value = "Link", required = true)
    private String link;

    @JsonProperty("EmbedLink")
    private String embedLink;

    @JsonProperty("Notification")
    private String notification;

    @JsonProperty("ParentFolderGuid")
    private String parentFolderGuid;

    @JsonProperty("Description")
    private int description;

    @JsonProperty(value = "Status", required = true)
    private Status status;
}