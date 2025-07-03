package digifyE2E.restAssured.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataRoomAddGuestSVCResponse {

    @JsonProperty(value = "DataRoomName", required = true)
    private String dataRoomName;

    @JsonProperty(value = "DataRoomGuid", required = true)
    private String dataRoomGuid;

    @JsonProperty(value = "Link", required = true)
    private String link;

    @JsonProperty("EmbedLink")
    private String embedLink;

    @JsonProperty("RecipientEmail")
    private String recipientEmail;

    @JsonProperty("Permission")
    private String permission;

    @JsonProperty("PrintCount")
    private int printCount;

    @JsonProperty("UserExpiry")
    private String userExpiry;

    @JsonProperty("GroupName")
    private String groupName;

    @JsonProperty("GroupGuid")
    private String groupGuid;

    @JsonProperty("Status")
    private Status status;
}
