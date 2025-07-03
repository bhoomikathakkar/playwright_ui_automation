package digifyE2E.restAssured.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataRoomSettingsResponse {

    @JsonProperty("DataRoomSettings")
    private DataRoomSettings dataRoomSettings;

    @JsonProperty("Status")
    private Status status;

}


