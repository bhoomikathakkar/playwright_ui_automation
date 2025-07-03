package digifyE2E.restAssured.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Status {

    @JsonProperty("StatusCode")
    private int statusCode;

    @JsonProperty("StatusMessage")
    private String statusMessage;

}
