package digifyE2E.restAssured.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataRoomCreateGroupSVCResponse {

    @JsonProperty(value = "DataRoomGuid", required = true)
    private String dataRoomGuid;

    @JsonProperty(value = "GroupName", required = true)
    private String groupName;

    @JsonProperty(value = "GroupGuid", required = true)
    private String groupGuid;

    @JsonProperty("Permission")
    private String permission;

    @JsonProperty("PrintCount")
    private int printCount;

    @JsonProperty("GroupExpiry")
    private String groupExpiry;

    @JsonProperty("Status")
    private Status status;
}