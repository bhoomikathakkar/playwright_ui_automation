package digifyE2E.restAssured.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileSettings {

    @JsonProperty("Guid")
    private String guid;

    @JsonProperty("Link")
    private String link;

    @JsonProperty("EmbedLink")
    private String embedLink;

    @JsonProperty("FileName")
    private String fileName;

    @JsonProperty("FileCreatedTime")
    private String fileCreatedTime;

    @JsonProperty("UploadStatus")
    private String uploadStatus;

    @JsonProperty("FileStatus")
    private String fileStatus;

    @JsonProperty("TotalViewedCount")
    private int totalViewedCount;

    @JsonProperty("Permission")
    private String permission;

    @JsonProperty("RequestEmail")
    private boolean requestEmail;

    @JsonProperty("Recipients")
    private String recipients;

    @JsonProperty("RestrictForwarding")
    private boolean restrictForwarding;

    @JsonProperty("Download")
    private int download;

    @JsonProperty("Print")
    private int print;

    @JsonProperty("Secret")
    private boolean secret;

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

    @JsonProperty("MovableWatermark")
    private boolean movableWatermark;

    @JsonProperty("Expiry")
    private String expiry;

    @JsonProperty("DestructSeconds")
    private int destructSeconds;

    @JsonProperty("ExpiryDate")
    private String expiryDate;

    @JsonProperty("AdditionalVerification")
    private boolean additionalVerification;

    @JsonProperty("ScreenShield")
    private boolean screenShield;

    @JsonProperty("TermsOfAccess")
    private boolean termsOfAccess;

    @JsonProperty("Password")
    private String password;

    @JsonProperty("PersistentProtection")
    private boolean persistentProtection;
}

