package digifyE2E.cucumber.stepDefinitions.apiStepDefinitions.stepHelpers;

import com.google.gson.JsonObject;
import digifyE2E.pages.Base;
import digifyE2E.restAssured.BaseRestHandler;
import digifyE2E.restAssured.entities.*;
import digifyE2E.testDataManager.SharedDM;
import digifyE2E.utils.FileUtils;
import io.cucumber.datatable.DataTable;
import io.restassured.specification.MultiPartSpecification;
import org.testng.Assert;

import java.io.File;
import java.util.*;

import static org.testng.Assert.assertEquals;

public class DataRoomAPIHelper extends BaseRestHandler {


    public static final String V_1_DATAROOM_CREATE = "v1/dataroom/create";
    public static final String V_1_DATAROOM_PREAUTHURL_CREATE = "v1/dataroom/preauthurl/create";
    public static final String V_1_DATAROOM_ADDRECIPIENT = "v1/dataroom/addrecipient";
    public static final String V_1_DATAROOM_UPLOADFILE = "v1/dataroom/uploadfile";
    public static final String V_1_DATAROOM_FILE_DELETE = "v1/dataroom/file/delete";

    public void generateDRFilePreAuthUrl(String drOwner, String recipientEmail, int linkExpiry, String isBrowserLock) {
        Map<String, String> requestForm = new HashMap<>();
        requestForm.put("Guid", SharedDM.dataRoomFileGUID.get());
        requestForm.put("RecipientUserEmail", FileUtils.getUserCredentialsFromJsonFile(recipientEmail).get("username"));
        requestForm.put("LinkExpiry", String.valueOf(linkExpiry));
        requestForm.put("browserLock", isBrowserLock);
        PreAuthURLResponse dataRoomPreAuthURLResponse = POST(200, V_1_DATAROOM_PREAUTHURL_CREATE, drOwner, requestForm, null, true, PreAuthURLResponse.class);
        assertEquals(dataRoomPreAuthURLResponse.getStatus().getStatusCode(),
                17000, "Invalid error code returned by API for a DR pre auth url");
        assertEquals(dataRoomPreAuthURLResponse.getStatus().getStatusMessage(),
                "Successfully generated data room pre-auth URL",
                "Invalid error message returned by API for a DR pre auth url");
        SharedDM.preAuthUrl.set(dataRoomPreAuthURLResponse.getPreAuthUrl());
    }

    @Deprecated
    public void addGuestInDRBySvcEndpoint(String drOwner, String guestEmail, String permissionType) {
        Map<String, String> requestForm = new HashMap<>();
        requestForm.put("DataRoomGuid", SharedDM.dataRoomGuid.get());
        requestForm.put("RecipientEmail", FileUtils.getUserCredentialsFromJsonFile(guestEmail).get("username"));
        requestForm.put("Permission", permissionType);
        DataRoomAddGuestSVCResponse dataRoomAddGuestSVCResponse = POST(201, V_1_DATAROOM_ADDRECIPIENT, drOwner, requestForm, null, true, DataRoomAddGuestSVCResponse.class);
        assertEquals(dataRoomAddGuestSVCResponse.getStatus().getStatusCode(),
                50000, "Invalid error code returned by API for data room add guest");
        assertEquals(dataRoomAddGuestSVCResponse.getStatus().getStatusMessage(),
                "Successfully added recipient",
                "Invalid error message returned by API for data room add guest");
    }

    @Deprecated
    public void uploadFileInDRBySvcEndpoint(String drOwner, String fileName) {
        JsonObject fileSettings = new JsonObject();
        fileSettings.addProperty("ParentFolderGuid", SharedDM.dataRoomGuid.get());
        fileSettings.addProperty("Notification", "Off");
        File file = new File("src/main/resources/testFiles/" + fileName);

        List<MultiPartSpecification> multipartSpecs = getMultipartSpecifications(file, "FileSettings", fileSettings.toString());
        DataRoomUploadFileSVCResponse dataRoomUploadFileSVCResponse = POST(201, V_1_DATAROOM_UPLOADFILE, drOwner, null, multipartSpecs, true, DataRoomUploadFileSVCResponse.class);
        SharedDM.dataRoomFileGUID.set(dataRoomUploadFileSVCResponse.getGuid());
        assertEquals(dataRoomUploadFileSVCResponse.getStatus().getStatusCode(), 40000,
                "Incorrect response code for DR upload file endpoint");
        assertEquals(dataRoomUploadFileSVCResponse.getStatus().getStatusMessage(), "Successfully uploaded file",
                "Incorrect response message for DR upload file endpoint");
    }

    @Deprecated
    public void invalidReqBodyForDRPreAuth(String userType, int linkExpiry, String guestUsername, int statusCode) {
        //Invalid email
        Map<String, String> reqWrongEmail = new HashMap<>();
        reqWrongEmail.put("Guid", SharedDM.dataRoomGuid.get());
        reqWrongEmail.put("RecipientUserEmail", "Invalid.email.com");
        reqWrongEmail.put("LinkExpiry", String.valueOf(linkExpiry));
        StatusResponse statusResponse = POST(statusCode, V_1_DATAROOM_PREAUTHURL_CREATE, userType, reqWrongEmail, null, true, StatusResponse.class);
        assertEquals(statusResponse.getStatus().getStatusMessage(),
                "RecipientUserEmail is not a valid email",
                "Incorrect error message returned by API for invalid email");
        assertEquals(statusResponse.getStatus().getStatusCode(),
                17003,
                "Incorrect error code returned by API for invalid email");

        //Invalid expiry
        Map<String, String> reqInvalidLnkExp = new HashMap<>();
        reqInvalidLnkExp.put("Guid", SharedDM.dataRoomGuid.get());
        reqInvalidLnkExp.put("RecipientUserEmail", FileUtils.getUserCredentialsFromJsonFile(guestUsername).get("username"));
        reqInvalidLnkExp.put("LinkExpiry", String.valueOf(-1));
        StatusResponse statusResponse2 = POST(statusCode, V_1_DATAROOM_PREAUTHURL_CREATE, userType, reqInvalidLnkExp, null, true, StatusResponse.class);
        Assert.assertEquals(statusResponse2.getStatus().getStatusMessage(),
                "Link expiry must be between 1 and 604800",
                "Invalid error message returned by API for invalid link expiry");
        Assert.assertEquals(statusResponse2.getStatus().getStatusCode(),
                17004, "Invalid error code returned by API for invalid link expiry");

        //null guid
        Map<String, String> reqNullGUID = new HashMap<>();
        reqNullGUID.put("Guid", "");
        reqNullGUID.put("RecipientUserEmail", FileUtils.getUserCredentialsFromJsonFile(guestUsername).get("username"));
        reqNullGUID.put("LinkExpiry", String.valueOf(100));
        StatusResponse statusResponse3 = POST(statusCode, V_1_DATAROOM_PREAUTHURL_CREATE, userType, reqNullGUID, null, true, StatusResponse.class);
        Assert.assertEquals(statusResponse3.getStatus().getStatusMessage(),
                "Guid is null",
                "Invalid error message returned by API for null GUID");
        Assert.assertEquals(statusResponse3.getStatus().getStatusCode(),
                17001, "Invalid error code returned by API for null GUID");

        //null email
        Map<String, String> reqNullRecipientEmail = new HashMap<>();
        reqNullRecipientEmail.put("Guid", SharedDM.dataRoomGuid.get());
        reqNullRecipientEmail.put("RecipientUserEmail", null);
        reqNullRecipientEmail.put("LinkExpiry", String.valueOf(100));
        StatusResponse statusResponse4 = POST(statusCode, V_1_DATAROOM_PREAUTHURL_CREATE, userType, reqNullRecipientEmail, null, true, StatusResponse.class);
        Assert.assertEquals(statusResponse4.getStatus().getStatusMessage(),
                "RecipientUserEmail is null",
                "Invalid error message returned by API for null RecipientEmail");
        Assert.assertEquals(statusResponse4.getStatus().getStatusCode(),
                17002, "Invalid error code returned by API for null RecipientEmail");

        //link expiry more than 7 days
        Map<String, String> exceededLinkExpValue = new HashMap<>();
        exceededLinkExpValue.put("Guid", SharedDM.dataRoomGuid.get());
        exceededLinkExpValue.put("RecipientUserEmail", FileUtils.getUserCredentialsFromJsonFile(guestUsername).get("username"));
        exceededLinkExpValue.put("LinkExpiry", String.valueOf(605000));
        StatusResponse statusResponse5 = POST(statusCode, V_1_DATAROOM_PREAUTHURL_CREATE, userType, exceededLinkExpValue, null, true, StatusResponse.class);
        Assert.assertEquals(statusResponse5.getStatus().getStatusMessage(),
                "Link expiry must be between 1 and 604800",
                "Invalid error message returned by API for link expiry cannot be more than 7 days");
        Assert.assertEquals(statusResponse5.getStatus().getStatusCode(),
                17004, "Invalid error code returned by API for link expiry cannot be more than 7 days");
    }

    @Deprecated
    public void validateDeleteResForDRPreAuth(String userType, int linkExpiry, String guestUsername, int statusCode) {
        //deleted data room
        Map<String, String> deletedDataRoom = new HashMap<>();
        deletedDataRoom.put("Guid", SharedDM.dataRoomGuid.get());
        deletedDataRoom.put("RecipientUserEmail", FileUtils.getUserCredentialsFromJsonFile(guestUsername).get("username"));
        deletedDataRoom.put("LinkExpiry", String.valueOf(linkExpiry));
        StatusResponse statusResponse = POST(statusCode, V_1_DATAROOM_PREAUTHURL_CREATE, userType, deletedDataRoom, null, true, StatusResponse.class);
        Assert.assertEquals(statusResponse.getStatus().getStatusMessage(),
                "Data room is not active",
                "Incorrect error message returned by API for deleted data room");
        Assert.assertEquals(statusResponse.getStatus().getStatusCode(),
                17009, "Incorrect error code returned by API for deleted data room");
    }

    @Deprecated
    public void validateInvalidGuidResponse(String userType, int linkExpiry, String guestUsername, int statusCode) {
        //invalid GUID
        Map<String, String> reqWrongGUID = new HashMap<>();
        reqWrongGUID.put("Guid", "2000000e000f48c49f4fb3f021500002");
        reqWrongGUID.put("RecipientUserEmail", FileUtils.getUserCredentialsFromJsonFile(guestUsername).get("username"));
        reqWrongGUID.put("LinkExpiry", String.valueOf(linkExpiry));
        StatusResponse statusResponse = POST(statusCode, V_1_DATAROOM_PREAUTHURL_CREATE, userType, reqWrongGUID, null, true, StatusResponse.class);
        Assert.assertEquals(statusResponse.getStatus().getStatusMessage(),
                "Invalid GUID",
                "Invalid error message returned by API for invalid GUID");
        Assert.assertEquals(statusResponse.getStatus().getStatusCode(), 17005, "Invalid error code returned by API for invalid GUID");
    }

    @Deprecated
    public void validateDeleteResForDRFilePreAuth(String userType, int linkExpiry, String guestUsername, int statusCode) {
        //deleted data room file
        Map<String, String> deletedDataRoomFile = new HashMap<>();
        deletedDataRoomFile.put("Guid", SharedDM.dataRoomFileGUID.get());
        deletedDataRoomFile.put("RecipientUserEmail", FileUtils.getUserCredentialsFromJsonFile(guestUsername).get("username"));
        deletedDataRoomFile.put("LinkExpiry", String.valueOf(linkExpiry));
        StatusResponse statusResponse = POST(statusCode, V_1_DATAROOM_PREAUTHURL_CREATE, userType, deletedDataRoomFile, null, true, StatusResponse.class);
        assertEquals(statusResponse.getStatus().getStatusMessage(),
                "Invalid GUID",
                "Invalid error message returned by API for deleted data room file");
        assertEquals(statusResponse.getStatus().getStatusCode(),
                17005, "Invalid error code returned by API for deleted data room file");
    }

    @Deprecated
    public void validateGuestNoAccessResp(String userType, int linkExpiry, String guestUsername, int statusCode) {
        //guest permission is no access
        Map<String, String> guestWithNoAccessPerm = new HashMap<>();
        guestWithNoAccessPerm.put("Guid", SharedDM.dataRoomFileGUID.get());
        guestWithNoAccessPerm.put("RecipientUserEmail", FileUtils.getUserCredentialsFromJsonFile(guestUsername).get("username"));
        guestWithNoAccessPerm.put("LinkExpiry", String.valueOf(linkExpiry));
        StatusResponse statusResponse = POST(statusCode, V_1_DATAROOM_PREAUTHURL_CREATE, userType, guestWithNoAccessPerm, null, true, StatusResponse.class);
        assertEquals(statusResponse.getStatus().getStatusMessage(),
                "RecipientUserEmail guest permission is no access",
                "Invalid error message returned by API when guest permission is no access");
        assertEquals(statusResponse.getStatus().getStatusCode(),
                17011, "Invalid error code returned by API when guest permission is no access");
    }

    @Deprecated
    public void validateUserNotGuestResp(String userType, int linkExpiry, String guestUsername, int statusCode) {
        //recipientUserEmail is not a DR guest
        Map<String, String> userIsNotDRGuest = new HashMap<>();
        userIsNotDRGuest.put("Guid", SharedDM.dataRoomFileGUID.get());
        userIsNotDRGuest.put("RecipientUserEmail", FileUtils.getUserCredentialsFromJsonFile(guestUsername).get("username"));
        userIsNotDRGuest.put("LinkExpiry", String.valueOf(linkExpiry));
        StatusResponse statusResponse = POST(statusCode, V_1_DATAROOM_PREAUTHURL_CREATE, userType, userIsNotDRGuest, null, true, StatusResponse.class);
        assertEquals(statusResponse.getStatus().getStatusMessage(),
                "RecipientUserEmail is not a guest",
                "Invalid error message returned by API when recipient user is not a data room guest");
        assertEquals(statusResponse.getStatus().getStatusCode(),
                17008, "Invalid error code returned by API when recipient user is not a data room guest");
    }

    @Deprecated
    public void validateInvalidDrOwner(String userType, int linkExpiry, String guestUsername, int statusCode) {
        //user is not a DR owner
        Map<String, String> notDROwner = new HashMap<>();
        notDROwner.put("Guid", SharedDM.dataRoomFileGUID.get());
        notDROwner.put("RecipientUserEmail", FileUtils.getUserCredentialsFromJsonFile(guestUsername).get("username"));
        notDROwner.put("LinkExpiry", String.valueOf(linkExpiry));
        StatusResponse statusResponse = POST(statusCode, V_1_DATAROOM_PREAUTHURL_CREATE, userType, notDROwner, null, true, StatusResponse.class);
        assertEquals(statusResponse.getStatus().getStatusMessage(),
                "User is not an owner or co-owner",
                "Invalid error message returned by API when user is not a DR owner");
        assertEquals(statusResponse.getStatus().getStatusCode(),
                17007,
                "Invalid error code returned by API when user is not a DR owner");
    }

    @Deprecated
    public void createDataRoomBySvcEndpoint(String userType, String permission, String access, String screenShield, String aboutPage) {
        Map<String, String> requestForm = new HashMap<>();
        requestForm.put("DataRoomName", SharedDM.getDataRoomName());
        requestForm.put("DefaultPermission", permission);
        requestForm.put("Access", access);
        requestForm.put("Screen_shield", screenShield);
        requestForm.put("AboutPage", aboutPage);
        CreateDataRoomSVCResponse createDataRoomSVCResponse = POST(201, V_1_DATAROOM_CREATE, userType, requestForm, null, true, CreateDataRoomSVCResponse.class);
        assertEquals(createDataRoomSVCResponse.getStatus().getStatusCode(),
                10000, "Invalid error code returned by API for a create data room");
        assertEquals(createDataRoomSVCResponse.getStatus().getStatusMessage(),
                "Successfully created data room",
                "Invalid error message returned by API for a create data room");
        SharedDM.dataRoomGuid.set(createDataRoomSVCResponse.getDataRoomGuid());
    }

    @Deprecated
    public void createDataRoomBySvcEndpoint(String userType, String permission, String access, String screenShield) {
        createDataRoomBySvcEndpoint(userType, permission, access, screenShield, null);
    }

    @Deprecated
    public void generateDRPreAuthUrl(String drOwner, String recipientEmail, int linkExpiry, String isBrowserLock) {
        Map<String, String> requestForm = new HashMap<>();
        requestForm.put("Guid", SharedDM.dataRoomGuid.get());
        requestForm.put("RecipientUserEmail", FileUtils.getUserCredentialsFromJsonFile(recipientEmail).get("username"));
        requestForm.put("LinkExpiry", String.valueOf(linkExpiry));
        requestForm.put("browserLock", isBrowserLock);

        PreAuthURLResponse dataRoomPreAuthURLResponse = POST(200, V_1_DATAROOM_PREAUTHURL_CREATE, drOwner, requestForm, null, true, PreAuthURLResponse.class);
        assertEquals(dataRoomPreAuthURLResponse.getStatus().getStatusCode(),
                17000, "Invalid error code returned by API for a DR pre auth url");
        assertEquals(dataRoomPreAuthURLResponse.getStatus().getStatusMessage(),
                "Successfully generated data room pre-auth URL",
                "Invalid error message returned by API for a DR pre auth url");
        SharedDM.preAuthUrl.set(dataRoomPreAuthURLResponse.getPreAuthUrl());
    }

    public void deleteDataRoomFile(String userType) {
        Map<String, String> requestForm = new HashMap<>();
        requestForm.put("Guid", SharedDM.dataRoomFileGUID.get());
        StatusResponse sharedDeleteResponse = POST(200, V_1_DATAROOM_FILE_DELETE, userType, requestForm, null, true, StatusResponse.class);
        Assert.assertEquals(sharedDeleteResponse.getStatus().getStatusMessage(),
                "Successfully deleted file",
                "Invalid error message returned by API for data room deletion");
        Assert.assertEquals(sharedDeleteResponse.getStatus().getStatusCode(),
                90000, "Invalid error message returned by API for data room file deletion");
    }

    public void postV1DataroomCreateEndpoint(String drOwnerUsername, String endpoint, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        Map<String, String> requestForm = new HashMap<>();
        if (dataTable != null && !dataTable.asMaps().isEmpty()) {
            Map<String, String> settings = convertDataTableToMap(dataTable);
            requestForm.putAll(settings);
        }
        CreateDataRoomSVCResponse response = POST(
                httpCode, endpoint, drOwnerUsername, requestForm, null, true, CreateDataRoomSVCResponse.class);
        assertEquals(response.getStatus().getStatusCode(), statusCode, "Unexpected API status code");
        assertEquals(response.getStatus().getStatusMessage(), statusMsg, "Unexpected API status message");
        if (statusCode == 10000) {
            SharedDM.dataRoomGuid.set(response.getDataRoomGuid());
        }
    }

    private void validateDataRoomSettings(Map<String, String> expectedDRSettings, DataRoomSettings actualDRSettings) {
        List<String> allowedKeys = Arrays.asList(
                "dataroomname", "dataroomguid", "link", "embedlink", "access", "domain", "additionalverification",
                "defaultpermission", "defaultprintcount", "watermark", "watermark_text", "watermark_text_line2",
                "watermark_opacity", "watermark_color", "watermark_size", "disablespreadsheetwatermark",
                "screenshield", "screen_shield", "dataroomexpiry", "fileindex", "notification", "termsofaccess",
                "guestlist", "aboutpage", "hidebanner", "filestoragebinding"
        );

        List<String> unexpectedKeys = expectedDRSettings.keySet().stream()
                .filter(key -> !allowedKeys.contains(key.toLowerCase()))
                .toList();

        if (!unexpectedKeys.isEmpty()) {
            throw new IllegalArgumentException("Unexpected keys found in expectedFileSettings: " + unexpectedKeys);
        }
        if (expectedDRSettings.containsKey("DataRoomName"))
            assertEquals(expectedDRSettings.get("DataRoomName"), actualDRSettings.getDataRoomName());
        if (expectedDRSettings.containsKey("DataRoomGuid"))
            assertEquals(expectedDRSettings.get("DataRoomGuid"), actualDRSettings.getDataRoomGuid());
        if (expectedDRSettings.containsKey("Link"))
            assertEquals(expectedDRSettings.get("Link"), actualDRSettings.getLink());
        if (expectedDRSettings.containsKey("EmbedLink"))
            assertEquals(expectedDRSettings.get("EmbedLink"), actualDRSettings.getEmbedLink());
        if (expectedDRSettings.containsKey("Access"))
            assertEquals(expectedDRSettings.get("Access"), actualDRSettings.getAccess());
        if (expectedDRSettings.containsKey("Domain")) {
            List<String> expectedDomains = new ArrayList<>();
            for (String domain : expectedDRSettings.get("Domain").split(",")) {
                expectedDomains.add(domain.trim());
            }
            List<String> actualDomains = new ArrayList<>();
            for (String raw : actualDRSettings.getDomain()) {
                for (String domain : raw.split(",")) {
                    actualDomains.add(domain.trim());
                }
            }
            assertEquals(expectedDomains, actualDomains);
        }
        if (expectedDRSettings.containsKey("AdditionalVerification"))
            assertEquals(Boolean.parseBoolean(expectedDRSettings.get("AdditionalVerification")), actualDRSettings.isAdditionalVerification());
        if (expectedDRSettings.containsKey("DefaultPermission"))
            assertEquals(expectedDRSettings.get("DefaultPermission"), actualDRSettings.getDefaultPermission());
        if (expectedDRSettings.containsKey("DefaultPrintCount"))
            assertEquals(Integer.parseInt(expectedDRSettings.get("DefaultPrintCount")), actualDRSettings.getDefaultPrintCount());
        if (expectedDRSettings.containsKey("Watermark"))
            assertEquals(Boolean.parseBoolean(expectedDRSettings.get("Watermark")), actualDRSettings.isWatermark());
        if (expectedDRSettings.containsKey("Watermark_text"))
            assertEquals(expectedDRSettings.get("Watermark_text"), actualDRSettings.getWatermark_text());
        if (expectedDRSettings.containsKey("Watermark_text_line2"))
            assertEquals(expectedDRSettings.get("Watermark_text_line2"), actualDRSettings.getWatermark_text_line2());
        if (expectedDRSettings.containsKey("Watermark_opacity"))
            assertEquals(Double.parseDouble(expectedDRSettings.get("Watermark_opacity")), actualDRSettings.getWatermark_opacity());
        if (expectedDRSettings.containsKey("Watermark_color"))
            assertEquals(expectedDRSettings.get("Watermark_color"), actualDRSettings.getWatermark_color());
        if (expectedDRSettings.containsKey("Watermark_size"))
            assertEquals(expectedDRSettings.get("Watermark_size"), actualDRSettings.getWatermark_size());
        if (expectedDRSettings.containsKey("DisableSpreadsheetWatermark"))
            assertEquals(Boolean.parseBoolean(expectedDRSettings.get("DisableSpreadsheetWatermark")), actualDRSettings.isDisableSpreadsheetWatermark());
        if (expectedDRSettings.containsKey("ScreenShield"))
            assertEquals(Boolean.parseBoolean(expectedDRSettings.get("ScreenShield")), actualDRSettings.isScreenShield());
        if (expectedDRSettings.containsKey("Screen_shield"))
            assertEquals(expectedDRSettings.get("Screen_shield"), actualDRSettings.getScreen_shield());
        if (expectedDRSettings.containsKey("DataRoomExpiry"))
            assertEquals(expectedDRSettings.get("DataRoomExpiry"), actualDRSettings.getDataRoomExpiry());
        if (expectedDRSettings.containsKey("FileIndex"))
            assertEquals(Boolean.parseBoolean(expectedDRSettings.get("FileIndex")), actualDRSettings.isFileIndex());
        if (expectedDRSettings.containsKey("Notification"))
            assertEquals(expectedDRSettings.get("Notification"), actualDRSettings.getNotification());
        if (expectedDRSettings.containsKey("TermsOfAccess"))
            assertEquals(Boolean.parseBoolean(expectedDRSettings.get("TermsOfAccess")), actualDRSettings.isTermsOfAccess());
        if (expectedDRSettings.containsKey("GuestList"))
            assertEquals(expectedDRSettings.get("GuestList"), actualDRSettings.getGuestList());
        if (expectedDRSettings.containsKey("AboutPage"))
            assertEquals(Boolean.parseBoolean(expectedDRSettings.get("AboutPage")), actualDRSettings.isAboutPage());
        if (expectedDRSettings.containsKey("HideBanner"))
            assertEquals(Boolean.parseBoolean(expectedDRSettings.get("HideBanner")), actualDRSettings.isHideBanner());
        if (expectedDRSettings.containsKey("FileStorageBinding"))
            assertEquals(expectedDRSettings.get("FileStorageBinding"), actualDRSettings.getFileStorageBinding());
        if (expectedDRSettings.containsKey("DefaultGroupGuid"))
            assertEquals(expectedDRSettings.get("DefaultGroupGuid"), actualDRSettings.getDefaultGroupGuid());
        if (expectedDRSettings.containsKey("HasLandingPage"))
            assertEquals(Boolean.parseBoolean(expectedDRSettings.get("HasLandingPage")), actualDRSettings.isHasLandingPage());
    }

    private void validateDataRoomGroupListResponse(Map<String, String> expectedGroup, DataRoomGroupList.Group actualResponse) {
        List<String> allowedKeys = Arrays.asList(
                "groups", "groupname", "groupguid", "permission", "printcount", "groupexpiry", "grouprecipientcount");

        List<String> unexpectedGroupKeys = expectedGroup.keySet().stream()
                .filter(key -> !allowedKeys.contains(key.toLowerCase()))
                .toList();

        if (!unexpectedGroupKeys.isEmpty()) {
            throw new IllegalArgumentException("Unexpected keys found in group list: " + unexpectedGroupKeys);
        }
        if (actualResponse.getGroupGuid() == null || actualResponse.getGroupGuid().isEmpty()) {
            throw new AssertionError("Group list is empty");
        }

        if (expectedGroup.containsKey("GroupName")) {
            assertEquals(expectedGroup.get("GroupName"), actualResponse.getGroupName());
        }
        if (expectedGroup.containsKey("GroupGuid")) {
            assertEquals(expectedGroup.get("GroupGuid"), actualResponse.getGroupGuid());
        }
        if (expectedGroup.containsKey("Permission")) {
            assertEquals(expectedGroup.get("Permission"), actualResponse.getPermission());
        }
        if (expectedGroup.containsKey("PrintCount")) {
            assertEquals(Integer.parseInt(expectedGroup.get("PrintCount")), actualResponse.getPrintCount());
        }
        if (expectedGroup.containsKey("GroupExpiry")) {
            assertEquals(expectedGroup.get("GroupExpiry"), actualResponse.getGroupExpiry().substring(0, 10));
        }
        if (expectedGroup.containsKey("GroupRecipientCount")) {
            assertEquals(Integer.parseInt(expectedGroup.get("GroupRecipientCount")), actualResponse.getGroupRecipientCount());
        }
    }

    public void getV1DataroomSettingsEndpoint(String userType, String endpoint, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DataRoomSettingsResponse dataRoomSettingsResponse =
                GET(httpCode, endpoint, SharedDM.dataRoomGuid.get(), userType, true, DataRoomSettingsResponse.class);
        assertEquals(dataRoomSettingsResponse.getStatus().getStatusCode(),
                statusCode, "Wrong error code returned by API for SVC sent file settings");
        assertEquals(dataRoomSettingsResponse.getStatus().getStatusMessage(),
                statusMsg, "Wrong error message returned by API for SVC sent file settings");
        DataRoomSettings actualDRSettings = dataRoomSettingsResponse.getDataRoomSettings();
        Map<String, String> expectedDRSettings = convertDataTableToMap(dataTable);
        validateDataRoomSettings(expectedDRSettings, actualDRSettings);
    }

    public void postV1DataroomSettingsUpdateEndpoint(String drOwnerUsername, String endpoint, String shareGuid, int httpCode, int statusCode, String statusMsg, Boolean isIncludeShareGuid, DataTable dataTable) {
        Base.waitForSeconds(1);
        Map<String, String> requestForm = new HashMap<>();
        if (dataTable != null && !dataTable.asMaps().isEmpty()) {
            Map<String, String> settings = convertDataTableToMap(dataTable);
            requestForm.putAll(settings);
        }
        requestForm.put("DataRoomGuid", isIncludeShareGuid ? shareGuid : SharedDM.dataRoomGuid.get());
        UpdateDataRoomSettingsSVCResponse response = POST(
                httpCode, endpoint, drOwnerUsername, requestForm, null, true, UpdateDataRoomSettingsSVCResponse.class);
        assertEquals(response.getStatus().getStatusCode(), statusCode, "Unexpected API status code");
        assertEquals(response.getStatus().getStatusMessage(), statusMsg, "Unexpected API status message");
    }

    public void postV1DataroomSettingsUpdateEndpoint(String drOwnerUsername, String endpoint, String shareGuid, String groupShareGuid, int httpCode, int statusCode, String statusMsg, Boolean isIncludeShareGuid, Boolean isIncludeGroupShareGuid, DataTable dataTable) {
        Map<String, String> requestForm = new HashMap<>();
        if (dataTable != null && !dataTable.asMaps().isEmpty()) {
            Map<String, String> settings = convertDataTableToMap(dataTable);
            requestForm.putAll(settings);
        }
        requestForm.put("DataRoomGuid", isIncludeShareGuid ? shareGuid : SharedDM.dataRoomGuid.get());
        requestForm.put("DefaultGroupGuid", isIncludeGroupShareGuid ? groupShareGuid : SharedDM.dataRoomGroupGuid.get());
        UpdateDataRoomSettingsSVCResponse response = POST(
                httpCode, endpoint, drOwnerUsername, requestForm, null, true, UpdateDataRoomSettingsSVCResponse.class);
        assertEquals(response.getStatus().getStatusCode(), statusCode, "Unexpected API status code");
        assertEquals(response.getStatus().getStatusMessage(), statusMsg, "Unexpected API status message");
    }

    public void postV1DataroomAddRecipientEndpoint(String drOwnerUsername, String endpoint, String guestUsername, int httpCode, int statusCode, String statusMsg, Boolean isGroup, DataTable dataTable) {
        Base.waitForSeconds(2);
        Map<String, String> requestForm = new HashMap<>();
        if (dataTable != null && !dataTable.asMaps().isEmpty()) {
            Map<String, String> settings = convertDataTableToMap(dataTable);
            requestForm.putAll(settings);
        }
        if (isGroup) {
            requestForm.put("GroupGuid", SharedDM.dataRoomGroupGuid.get());
        }
        requestForm.put("DataRoomGuid", SharedDM.dataRoomGuid.get());
        requestForm.put("RecipientEmail", FileUtils.getUserCredentialsFromJsonFile(guestUsername).get("username"));
        DataRoomAddGuestSVCResponse dataRoomAddGuestSVCResponse = POST(httpCode, endpoint, drOwnerUsername, requestForm, null, true, DataRoomAddGuestSVCResponse.class);
        assertEquals(dataRoomAddGuestSVCResponse.getStatus().getStatusCode(),
                statusCode, "Invalid error code returned by API for data room add guest");
        assertEquals(dataRoomAddGuestSVCResponse.getStatus().getStatusMessage(),
                statusMsg,
                "Invalid error message returned by API for data room add guest");
    }

    public void postV1DataroomRemoveRecipientEndpoint(String drOwnerUsername, String endpoint, String guestUsername, String shareGuid, int httpCode, int statusCode, String statusMsg, Boolean includedGuid) {
        Base.waitForSeconds(2);
        Map<String, String> requestForm = new HashMap<>();
        requestForm.put("DataRoomGuid", includedGuid ? shareGuid : SharedDM.dataRoomGuid.get());
        requestForm.put("RecipientEmail", FileUtils.getUserCredentialsFromJsonFile(guestUsername).get("username"));
        DataRoomRemoveGuestSVCResponse response = POST(httpCode, endpoint, drOwnerUsername, requestForm, null, true, DataRoomRemoveGuestSVCResponse.class);
        assertEquals(response.getStatus().getStatusCode(),
                statusCode, "Invalid error code returned by API for data room remove guest");
        assertEquals(response.getStatus().getStatusMessage(),
                statusMsg,
                "Invalid error message returned by API for data room remove guest");
    }

    public void postV1DataroomGroupCreateEndpoint(String drOwnerUsername, String endpoint, String shareGuid, int httpCode, int statusCode, String statusMsg, Boolean includedGuid, DataTable dataTable) {
        Map<String, String> requestForm = new HashMap<>();
        if (dataTable != null && !dataTable.asMaps().isEmpty()) {
            Map<String, String> settings = convertDataTableToMap(dataTable);
            requestForm.putAll(settings);
        }
        requestForm.put("DataRoomGuid", includedGuid ? shareGuid : SharedDM.dataRoomGuid.get());
        DataRoomCreateGroupSVCResponse dataRoomCreateGroupSVCResponse = POST(httpCode, endpoint, drOwnerUsername, requestForm, null, true, DataRoomCreateGroupSVCResponse.class);
        if (statusCode == 10000) {
            SharedDM.dataRoomGroupGuid.set(dataRoomCreateGroupSVCResponse.getGroupGuid());
        }
        assertEquals(dataRoomCreateGroupSVCResponse.getStatus().getStatusCode(),
                statusCode, "Invalid error code returned by API for creating group in data room");
        assertEquals(dataRoomCreateGroupSVCResponse.getStatus().getStatusMessage(),
                statusMsg,
                "Invalid error message returned by API for creating group in data room");
    }

    public void getV1DataroomGroupEndpoint(String drOwnerUsername, String endpoint, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DataRoomGroupListResponse getDataRoomGroupsListResponse =
                GET(httpCode, endpoint, SharedDM.dataRoomGuid.get(), drOwnerUsername, true, DataRoomGroupListResponse.class);
        assertEquals(getDataRoomGroupsListResponse.getStatus().getStatusCode(),
                statusCode, "Invalid error code for svc: get list of groups in DR");
        assertEquals(getDataRoomGroupsListResponse.getStatus().getStatusMessage(),
                statusMsg, "Invalid error msg for svc: get list of groups in DR");
        DataRoomGroupList.Group dataRoomGroupList = getDataRoomGroupsListResponse.getGroups().get(0);
        Map<String, String> expectedDRGroupsList = convertDataTableToMap(dataTable);
        validateDataRoomGroupListResponse(expectedDRGroupsList, dataRoomGroupList);
    }

    public void postV1DataroomAddRecipientEndpoint(String drOwnerUsername, String endpoint, String guestUsername, String shareGuid, String groupGuid, int httpCode, int statusCode, String statusMsg, Boolean isIncludeShareGuid, Boolean isIncludeGroupGuid, DataTable dataTable) {
        Map<String, String> requestForm = new HashMap<>();
        if (dataTable != null && !dataTable.asMaps().isEmpty()) {
            Map<String, String> settings = convertDataTableToMap(dataTable);
            requestForm.putAll(settings);
        }
        requestForm.put("GroupGuid", isIncludeGroupGuid ? groupGuid : SharedDM.dataRoomGroupGuid.get());
        requestForm.put("DataRoomGuid", isIncludeShareGuid ? shareGuid : SharedDM.dataRoomGuid.get());
        requestForm.put("RecipientEmail", FileUtils.getUserCredentialsFromJsonFile(guestUsername).get("username"));
        DataRoomAddGuestSVCResponse dataRoomAddGuestSVCResponse = POST(
                httpCode, endpoint, drOwnerUsername, requestForm, null, true, DataRoomAddGuestSVCResponse.class);
        assertEquals(dataRoomAddGuestSVCResponse.getStatus().getStatusCode(), statusCode, "Unexpected API status code");
        assertEquals(dataRoomAddGuestSVCResponse.getStatus().getStatusMessage(), statusMsg, "Unexpected API status message");
    }

    public void callEndpointWithoutRequestForm(String drOwnerUsername, String endpoint, int httpCode, int statusCode, String statusMsg) {
        Map<String, String> requestForm = new HashMap<>();
        StatusResponse dataRoomAddGuestSVCResponse = POST(httpCode, endpoint, drOwnerUsername, requestForm, null, true, StatusResponse.class);
        assertEquals(dataRoomAddGuestSVCResponse.getStatus().getStatusCode(),
                statusCode, "Invalid error code returned by API when request form is not passed");
        assertEquals(dataRoomAddGuestSVCResponse.getStatus().getStatusMessage(),
                statusMsg,
                "Invalid error message returned by API when request form is not passed");
    }

    public void postV1DataRoomGroupDeleteEndpoint(String drOwnerUsername, String endpoint, String dataRoomShareGuid, String groupShareGuid, int httpCode, int statusCode, String statusMsg, Boolean isIncludeDRShareGuid, Boolean isIncludeGroupGuid) {
        Map<String, String> requestForm = new HashMap<>();
        requestForm.put("GroupGuid", isIncludeGroupGuid ? groupShareGuid : SharedDM.dataRoomGroupGuid.get());
        requestForm.put("DataRoomGuid", isIncludeDRShareGuid ? dataRoomShareGuid : SharedDM.dataRoomGuid.get());
        StatusResponse response = POST(
                httpCode, endpoint, drOwnerUsername, requestForm, null, true, StatusResponse.class);
        assertEquals(response.getStatus().getStatusCode(), statusCode, "Unexpected API status code");
        assertEquals(response.getStatus().getStatusMessage(), statusMsg, "Unexpected API status message");
    }

    public void postV1DataroomDeleteEndpoint(String drOwnerUsername, String endpoint, String dataRoomShareGuid, int httpCode, int statusCode, String statusMsg, Boolean isIncludeDRShareGuid) {
        Map<String, String> requestForm = new HashMap<>();
        requestForm.put("DataRoomGuid", isIncludeDRShareGuid ? dataRoomShareGuid : SharedDM.dataRoomGuid.get());
        StatusResponse sharedDeleteResponse = POST(httpCode, endpoint, drOwnerUsername, requestForm, null, true, StatusResponse.class);
        Assert.assertEquals(sharedDeleteResponse.getStatus().getStatusMessage(),
                statusMsg, "Invalid error message returned by API for data room deletion");
        Assert.assertEquals(sharedDeleteResponse.getStatus().getStatusCode(),
                statusCode, "Invalid error message returned by API for data room deletion");
    }
}
