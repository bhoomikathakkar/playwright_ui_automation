package digifyE2E.restAssured.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentSecurityFileSettingsResponse {

    @JsonProperty("FileSettings")
    private FileSettings fileSettings;

    @JsonProperty("Status")
    private Status status;

}


