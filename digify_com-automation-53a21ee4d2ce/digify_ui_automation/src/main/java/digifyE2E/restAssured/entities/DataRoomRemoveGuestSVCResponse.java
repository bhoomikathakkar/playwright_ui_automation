package digifyE2E.restAssured.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataRoomRemoveGuestSVCResponse {

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

    @JsonProperty("Status")
    private Status status;
}
