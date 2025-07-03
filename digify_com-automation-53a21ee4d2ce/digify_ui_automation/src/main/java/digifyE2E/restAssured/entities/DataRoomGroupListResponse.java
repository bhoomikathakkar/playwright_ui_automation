package digifyE2E.restAssured.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataRoomGroupListResponse {

    @JsonProperty("Groups")
    private List<DataRoomGroupList.Group> groups;

    @JsonProperty("Status")
    private Status status;
}
