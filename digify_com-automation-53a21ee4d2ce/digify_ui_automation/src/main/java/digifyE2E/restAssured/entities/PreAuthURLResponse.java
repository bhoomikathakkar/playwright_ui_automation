package digifyE2E.restAssured.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreAuthURLResponse {

    @JsonAlias({"FileName", "Name"})
    private String fileName;

    @JsonProperty("RecipientUserEmail")
    private String recipientUserEmail;

    @JsonProperty(value = "ExpiryTime", required = true)
    private String expiryTime;

    @JsonProperty(value = "PreAuthURL", required = true)
    private String preAuthUrl;

    @JsonProperty(value = "EmbedLink", required = true)
    private String embedLink;

    @JsonProperty(value = "Status", required = true)
    private Status status;
}
