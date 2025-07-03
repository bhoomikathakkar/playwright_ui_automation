package digifyE2E.restAssured.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataRoomSettings {
    @JsonProperty(value = "DataRoomName", required = true)
    private String dataRoomName;

    @JsonProperty(value = "DataRoomGuid", required = true)
    private String dataRoomGuid;

    @JsonProperty(value = "Link", required = true)
    private String link;

    @JsonProperty("EmbedLink")
    private String embedLink;

    @JsonProperty("Access")
    private String access;

    @JsonProperty("Domain")
    private List<String> Domain;

    @JsonProperty("AdditionalVerification")
    private boolean additionalVerification;

    @JsonProperty("DefaultPermission")
    private String defaultPermission;

    @JsonProperty("DefaultPrintCount")
    private int defaultPrintCount;

    @JsonProperty("Watermark")
    private boolean watermark;

    @JsonProperty("Watermark_text")
    private String watermark_text;

    @JsonProperty("Watermark_text_line2")
    private String watermark_text_line2;

    @JsonProperty("Watermark_opacity")
    private double watermark_opacity;

    @JsonProperty("Watermark_color")
    private String watermark_color;

    @JsonProperty("Watermark_size")
    private String watermark_size;

    @JsonProperty("DisableSpreadsheetWatermark")
    private boolean disableSpreadsheetWatermark;

    @JsonProperty("ScreenShield")
    private boolean screenShield;

    @JsonProperty("Screen_shield")
    private String screen_shield;

    @JsonProperty("DataRoomExpiry")
    private String dataRoomExpiry;

    @JsonProperty("FileIndex")
    private boolean fileIndex;

    @JsonProperty("Notification")
    private String notification;

    @JsonProperty("TermsOfAccess")
    private boolean termsOfAccess;

    @JsonProperty("GuestList")
    private String guestList;

    @JsonProperty("AboutPage")
    private boolean aboutPage;

    @JsonProperty("HideBanner")
    private boolean hideBanner;

    @JsonProperty("FileStorageBinding")
    private String fileStorageBinding;

    @JsonProperty("DefaultGroupGuid")
    private String defaultGroupGuid;

    @JsonProperty("HasLandingPage")
    private boolean hasLandingPage;
}
