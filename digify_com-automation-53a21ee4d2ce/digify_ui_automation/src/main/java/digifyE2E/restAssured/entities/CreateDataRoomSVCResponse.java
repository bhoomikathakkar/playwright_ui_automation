package digifyE2E.restAssured.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class CreateDataRoomSVCResponse {

    @JsonProperty(value = "DataRoomName", required = true)
    private String dataRoomName;

    @JsonProperty(value = "DataRoomGuid", required = true)
    private String DataRoomGuid;

    @JsonProperty(value = "Link", required = true)
    private String Link;

    @JsonProperty("EmbedLink")
    private String EmbedLink;

    @JsonProperty("Access")
    private String Access;

    @JsonProperty("Domain")
    private List<String> Domain;

    @JsonProperty("AdditionalVerification")
    private boolean AdditionalVerification;

    @JsonProperty("DefaultPermission")
    private String DefaultPermission;

    @JsonProperty("DefaultPrintCount")
    private int DefaultPrintCount;

    @JsonProperty("Watermark")
    private boolean Watermark;

    @JsonProperty("Watermark_text")
    private String Watermark_text;

    @JsonProperty("Watermark_text_line2")
    private String Watermark_text_line2;

    @JsonProperty("Watermark_opacity")
    private double Watermark_opacity;

    @JsonProperty("Watermark_color")
    private String Watermark_color;

    @JsonProperty("Watermark_size")
    private String Watermark_size;

    @JsonProperty("DisableSpreadsheetWatermark")
    private boolean DisableSpreadsheetWatermark;

    @JsonProperty("ScreenShield")
    private boolean ScreenShield;

    @JsonProperty("Screen_shield")
    private String Screen_shield;

    @JsonProperty("DataRoomExpiry")
    private String DataRoomExpiry;

    @JsonProperty("FileIndex")
    private boolean FileIndex;

    @JsonProperty("Notification")
    private String Notification;

    @JsonProperty("TermsOfAccess")
    private boolean TermsOfAccess;

    @JsonProperty("GuestList")
    private String GuestList;

    @JsonProperty("AboutPage")
    private boolean AboutPage;

    @JsonProperty("HideBanner")
    private boolean HideBanner;

    @JsonProperty("FileStorageBinding")
    private String FileStorageBinding;

    @JsonProperty(value = "Status", required = true)
    private Status status;
}
