package digifyE2E.restAssured.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataRoomGroupList {
    @JsonProperty("Groups")
    private List<Group> groups;

    @Getter
    @Setter
    public static class Group {

        @JsonProperty("GroupName")
        private String groupName;

        @JsonProperty("GroupGuid")
        private String groupGuid;

        @JsonProperty("Permission")
        private String permission;

        @JsonProperty("PrintCount")
        private int printCount;

        @JsonProperty("GroupExpiry")
        private String groupExpiry;

        @JsonProperty("GroupRecipientCount")
        private int groupRecipientCount;
    }
}
