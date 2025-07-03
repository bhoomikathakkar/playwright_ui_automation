package digifyE2E.cucumber.stepDefinitions.apiStepDefinitions.stepHelpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import digifyE2E.pages.Base;
import digifyE2E.restAssured.BaseRestHandler;
import digifyE2E.restAssured.entities.*;
import digifyE2E.testDataManager.SharedDM;
import digifyE2E.utils.FileUtils;
import digifyE2E.utils.LogUtils;
import io.cucumber.datatable.DataTable;
import io.restassured.specification.MultiPartSpecification;

import java.io.File;
import java.util.*;

import static digifyE2E.utils.LogUtils.infoLog;
import static org.testng.Assert.assertEquals;

public class DocumentSecurityAPIHelper extends BaseRestHandler {

    public static final String V_1_FILE_PREAUTHURL_CREATE = "v1/file/preauthurl/create";

    @Deprecated
    public void invalidReqBodyForPreAuthUrl(String fileSenderEmail, String recipientEmail, int linkExpiry, int statusCode) {
        //wrong email
        Map<String, String> reqWrongEmail = new HashMap<>();
        reqWrongEmail.put("Guid", SharedDM.fileGUID.get());
        reqWrongEmail.put("RecipientUserEmail", "wrong.email.com");
        reqWrongEmail.put("LinkExpiry", String.valueOf(linkExpiry));
        StatusResponse statusResponse =
                POST(statusCode, V_1_FILE_PREAUTHURL_CREATE, fileSenderEmail, reqWrongEmail, null, true, StatusResponse.class);
        assertEquals(statusResponse.getStatus().getStatusCode(), 16008, "wrong error code returned by API for null GUID");
        assertEquals(statusResponse.getStatus().getStatusMessage(), "Please enter a valid RecipientUserEmail.", "wrong error message returned by API for invalid email");

        //wrong GUID
        Map<String, String> reqWrongGUID = new HashMap<>();
        reqWrongGUID.put("Guid", "2000000e000f48c49f4fb3f021500002");
        reqWrongGUID.put("RecipientUserEmail", FileUtils.getUserCredentialsFromJsonFile(recipientEmail).get("username"));
        reqWrongGUID.put("LinkExpiry", String.valueOf(linkExpiry));
        StatusResponse statusResponse1 =
                POST(statusCode, V_1_FILE_PREAUTHURL_CREATE, fileSenderEmail, reqWrongGUID, null, true, StatusResponse.class);
        assertEquals(statusResponse1.getStatus().getStatusCode(), 16012, "wrong error message returned by API for invalid GUID");
        assertEquals(statusResponse1.getStatus().getStatusMessage(), "Please enter a Guid to an existing sent file that you own.", "wrong error message returned by API for invalid GUID");

        //wrong expiry
        Map<String, String> reqInvalidLnkExp = new HashMap<>();
        reqInvalidLnkExp.put("Guid", SharedDM.fileGUID.get());
        reqInvalidLnkExp.put("RecipientUserEmail", FileUtils.getUserCredentialsFromJsonFile(recipientEmail).get("username"));
        reqInvalidLnkExp.put("LinkExpiry", String.valueOf(-1));
        StatusResponse statusResponse2 = POST(statusCode, V_1_FILE_PREAUTHURL_CREATE, fileSenderEmail, reqInvalidLnkExp, null, true, StatusResponse.class);
        assertEquals(statusResponse2.getStatus().getStatusMessage(), "Please enter a valid LinkExpiry.", "wrong error message returned by API for invalid link expiry");
        assertEquals(statusResponse2.getStatus().getStatusCode(), 16009, "wrong error message returned by API for invalid link expiry");

        //null guid
        Map<String, String> reqNullGUID = new HashMap<>();
        reqNullGUID.put("Guid", "");
        reqNullGUID.put("RecipientUserEmail", FileUtils.getUserCredentialsFromJsonFile(recipientEmail).get("username"));
        reqNullGUID.put("LinkExpiry", String.valueOf(100));
        StatusResponse statusResponse3 = POST(statusCode, V_1_FILE_PREAUTHURL_CREATE, fileSenderEmail, reqNullGUID, null, true, StatusResponse.class);
        assertEquals(statusResponse3.getStatus().getStatusCode(), 16006, "wrong error code returned by API for null GUID");
        assertEquals(statusResponse3.getStatus().getStatusMessage(), "Please enter a Guid.", "wrong error message returned by API for null GUID");

        //null email
        Map<String, String> reqNullRecipientEmail = new HashMap<>();
        reqNullRecipientEmail.put("Guid", SharedDM.fileGUID.get());
        reqNullRecipientEmail.put("RecipientUserEmail", null);
        reqNullRecipientEmail.put("LinkExpiry", String.valueOf(100));
        StatusResponse statusResponse4 = POST(statusCode, V_1_FILE_PREAUTHURL_CREATE, fileSenderEmail, reqNullRecipientEmail, null, true, StatusResponse.class);
        assertEquals(statusResponse.getStatus().getStatusCode(), 16008, "wrong error code returned by API for null RecipientEmail");
        assertEquals(statusResponse4.getStatus().getStatusMessage(), "Please enter a RecipientUserEmail.", "wrong error message returned by API for null RecipientEmail");

        //link expiry more than 7 days
        Map<String, String> exceededLinkExpValue = new HashMap<>();
        exceededLinkExpValue.put("Guid", SharedDM.fileGUID.get());
        exceededLinkExpValue.put("RecipientUserEmail", FileUtils.getUserCredentialsFromJsonFile(recipientEmail).get("username"));
        exceededLinkExpValue.put("LinkExpiry", String.valueOf(605000));
        StatusResponse statusResponse5 = POST(statusCode, V_1_FILE_PREAUTHURL_CREATE, fileSenderEmail, exceededLinkExpValue, null, true, StatusResponse.class);
        assertEquals(statusResponse5.getStatus().getStatusCode(), 16011, "wrong error code returned by API for link expiry cannot be more than 7 days");
        assertEquals(statusResponse5.getStatus().getStatusMessage(), "LinkExpiry cannot be more than 7 days.", "wrong error message returned by API for link expiry cannot be more than 7 days");
    }

    @Deprecated
    public void validateDeletedFileErrorForDSPreAuthUrl(String fileSenderEmail, String recipientEmail, int linkExpiry, int statusCode) {
        //deleted shared file
        Map<String, String> deletedSharedFile = new HashMap<>();
        deletedSharedFile.put("Guid", SharedDM.fileGUID.get());
        deletedSharedFile.put("RecipientUserEmail", FileUtils.getUserCredentialsFromJsonFile(recipientEmail).get("username"));
        deletedSharedFile.put("LinkExpiry", String.valueOf(linkExpiry));
        StatusResponse statusResponse = POST(statusCode, V_1_FILE_PREAUTHURL_CREATE, fileSenderEmail, deletedSharedFile, null, true, StatusResponse.class);
        assertEquals(statusResponse.getStatus().getStatusMessage(), "File is deleted.", "Wrong response for a pre auth url of deleted shared file");
        assertEquals(statusResponse.getStatus().getStatusCode(), 16015, "Wrong response for a pre auth url of deleted shared file");
    }

    @Deprecated
    public void validateFilePermErrorForDSPreAuthUrl(String fileSenderEmail, String recipientEmail, int linkExpiry, int statusCode) {
        //public files
        Map<String, String> publicPermission = new HashMap<>();
        publicPermission.put("Guid", SharedDM.fileGUID.get());
        publicPermission.put("RecipientUserEmail", FileUtils.getUserCredentialsFromJsonFile(recipientEmail).get("username"));
        publicPermission.put("LinkExpiry", String.valueOf(linkExpiry));
        StatusResponse statusResponse = POST(statusCode, V_1_FILE_PREAUTHURL_CREATE, fileSenderEmail, publicPermission, null, true, StatusResponse.class);
        assertEquals(statusResponse.getStatus().getStatusMessage(), "File must not have the permission 'Anyone with the link (no email verification)'.", "Pre-auth url can not be generated for public files");
        assertEquals(statusResponse.getStatus().getStatusCode(), 16017, "Pre-auth url can not be generated for public files");
    }

    @Deprecated
    public void uploadFileWithSettings(String userType, String fileName, String permission, String recipientEmail, int download, int print) {
        JsonObject fileSettings = new JsonObject();
        fileSettings.addProperty("Permission", permission);
        fileSettings.addProperty("Download", download);
        fileSettings.addProperty("Print", print);
        fileSettings.addProperty("Expiry", "off");
        fileSettings.addProperty("Recipients", "[{\"email\":\"" + FileUtils.getUserCredentialsFromJsonFile(recipientEmail).get("username") + "\"}]");
        File file = new File("src/main/resources/testFiles/" + fileName);
        List<MultiPartSpecification> multipartSpecs = getMultipartSpecifications(file, "FileSettings", fileSettings.toString());
        DocumentSecurityFileUploadResponse documentSecurityFileUploadResponse =
                POST(201, "v1/file/upload", userType, null, multipartSpecs, true, DocumentSecurityFileUploadResponse.class);
        assertEquals(documentSecurityFileUploadResponse.getStatus().getStatusCode(), 40000, "wrong error code returned by API for a DS file upload");
        assertEquals(documentSecurityFileUploadResponse.getStatus().getStatusMessage(), "Successfully shared uploaded document", "wrong error message returned by API for a DS file upload");

        SharedDM.fileGUID.set(documentSecurityFileUploadResponse.getGuid());
    }

    @Deprecated
    public void generateDSPreAuthUrl(String fileSenderEmail, String recipientEmail, int linkExpiry, String isBrowserLock) {
        try {
            // Prepare request form
            Map<String, String> requestForm = new HashMap<>();
            requestForm.put("Guid", SharedDM.fileGUID.get());
            requestForm.put("RecipientUserEmail", FileUtils.getUserCredentialsFromJsonFile(recipientEmail).get("username"));
            requestForm.put("LinkExpiry", String.valueOf(linkExpiry));
            requestForm.put("browserLock", isBrowserLock);
            PreAuthURLResponse preAuthUrlResponse = POST(200, V_1_FILE_PREAUTHURL_CREATE, fileSenderEmail, requestForm, null, true, PreAuthURLResponse.class);
            SharedDM.preAuthUrl.set(preAuthUrlResponse.getPreAuthUrl());
            assertEquals(preAuthUrlResponse.getStatus().getStatusCode(), 16000, "wrong error code returned by API for a DS pre auth url");
            assertEquals(preAuthUrlResponse.getStatus().getStatusMessage(), "Pre-auth URL generated.", "wrong error message returned by API for a DS pre auth url");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while generating PreAuth URL", e);
        }
    }

    public void postV1FileUploadWithoutFileSettings(String userType, String endpoint, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        Map<String, String> settings = convertDataTableToMap(dataTable);
        JsonObject fileSettings = new JsonObject();
        settings.forEach(fileSettings::addProperty);
        fileSettings.addProperty("Recipients", "[{\"email\":\"" + FileUtils.getUserCredentialsFromJsonFile("recipientUser").get("username") + "\"}]");
        File file = new File("src/main/resources/testFiles/" + "PdfFile.pdf");
        List<MultiPartSpecification> multipartSpecs = getMultipartSpecifications(file, "FileSettings1", fileSettings.toString());
        DocumentSecurityFileUploadResponse documentSecurityFileUploadResponse =
                POST(httpCode, endpoint, userType, null, multipartSpecs, false, DocumentSecurityFileUploadResponse.class);
        assertEquals(documentSecurityFileUploadResponse.getStatus().getStatusCode(), statusCode, "Mismatch in status code");
        assertEquals(documentSecurityFileUploadResponse.getStatus().getStatusMessage(), statusMsg, "Mismatch in status message");
    }

    public void postV1FileUploadEndpoint(String userType, String endpoint, String fileName, String recipientEmail, int expectedHttpStatusCode, int expectedApiStatusCode, String expectedApiStatusMessage, DataTable dataTable) {
        boolean skipFileSettings = (dataTable == null || dataTable.asMaps().isEmpty());
        boolean skipFile = (fileName == null || fileName.trim().isEmpty());
        JsonObject fileSettings = new JsonObject();
        if (!skipFileSettings) {
            Map<String, String> settings = convertDataTableToMap(dataTable);
            settings.forEach(fileSettings::addProperty);
            if (recipientEmail != null && !recipientEmail.trim().isEmpty()) {
                try {
                    String email = FileUtils.getUserCredentialsFromJsonFile(recipientEmail).get("username");
                    fileSettings.addProperty("Recipients", "[{\"email\":\"" + email + "\"}]");
                } catch (Exception e) {
                    infoLog("Invalid recipientEmail: " + recipientEmail + " -> " + e.getMessage());
                    fileSettings.addProperty("Recipients", "[]");
                }
            }
        }
        File file = null;
        if (!skipFile) {
            file = new File("src/main/resources/testFiles/" + fileName);
            if (!file.exists()) {
                infoLog("File not found: " + file.getPath());
                file = null;
            }
        } else {
            infoLog("Skipping file part (fileName is empty or null)");
        }
        infoLog("Settings JSON: " + fileSettings);
        List<MultiPartSpecification> multipartSpecs;
        if (file == null && skipFileSettings) {
            multipartSpecs = Collections.emptyList(); // No parts at all
        } else if (file == null) {
            multipartSpecs = getMultipartSpecifications(null, "FileSettings", fileSettings.toString());
        } else if (skipFileSettings) {
            multipartSpecs = getMultipartSpecifications(file, null, null);
        } else {
            multipartSpecs = getMultipartSpecifications(file, "FileSettings", fileSettings.toString());
        }
        DocumentSecurityFileUploadResponse response =
                POST(expectedHttpStatusCode, endpoint, userType, null, multipartSpecs, true, DocumentSecurityFileUploadResponse.class);

        assertEquals(response.getStatus().getStatusCode(), expectedApiStatusCode, "Unexpected API status code");
        assertEquals(response.getStatus().getStatusMessage(), expectedApiStatusMessage, "Unexpected API status message");

        if (expectedApiStatusCode == 40000) {
            SharedDM.svcDocumentSecurityFile.set(response.getLink());
            SharedDM.fileGUID.set(response.getGuid());
        }
    }

    public void getV1FileSettingsEndpoint(String userType, String endpoint, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        Base.waitForSeconds(1);
        DocumentSecurityFileSettingsResponse fileSettingsResponse =
                GET(httpCode, endpoint, SharedDM.fileGUID.get(), userType, true, DocumentSecurityFileSettingsResponse.class);
        assertEquals(fileSettingsResponse.getStatus().getStatusCode(),
                statusCode, "Wrong error code returned by API for SVC sent file settings");
        assertEquals(fileSettingsResponse.getStatus().getStatusMessage(),
                statusMsg, "Wrong error message returned by API for SVC sent file settings");
        FileSettings actualFileSettings = fileSettingsResponse.getFileSettings();
        Map<String, String> expectedFileSettings = convertDataTableToMap(dataTable);
        validateGetV1FileSettingsEndpoint(expectedFileSettings, actualFileSettings);
    }

    private void validateGetV1FileSettingsEndpoint(Map<String, String> expectedFileSettings, FileSettings actualFileSettings) {
        // Define the allowed keys (normalized to lowercase for case-insensitivity)
        List<String> allowedKeys = Arrays.asList("permission", "watermark", "watermark_text", "watermark_text_line2", "watermark_opacity", "watermark_color", "watermark_size", "screenshield", "download", "expiry", "destructseconds", "additionalverification", "password", "print", "persistentprotection", "termsofaccess", "restrictforwarding", "expirydate", "requestemail");
        infoLog("file settings is::" + actualFileSettings.toString());
        List<String> unexpectedKeys =
                expectedFileSettings.keySet().stream().filter(key -> !allowedKeys.contains(key.toLowerCase())) // Normalize keys to lowercase for comparison
                        .toList();
        if (!unexpectedKeys.isEmpty()) {
            throw new IllegalArgumentException("Unexpected keys found in expectedFileSettings: " + unexpectedKeys);
        }
        if (expectedFileSettings.containsKey("Permission"))
            assertEquals(expectedFileSettings.get("Permission"), actualFileSettings.getPermission());
        if (expectedFileSettings.containsKey("Watermark"))
            assertEquals(Boolean.parseBoolean(expectedFileSettings.get("Watermark")), actualFileSettings.isWatermark());
        if (expectedFileSettings.containsKey("Watermark_text"))
            assertEquals(expectedFileSettings.get("Watermark_text"), actualFileSettings.getWatermark_text());
        if (expectedFileSettings.containsKey("Watermark_text_line2"))
            assertEquals(expectedFileSettings.get("Watermark_text_line2"), actualFileSettings.getWatermark_text_line2());
        if (expectedFileSettings.containsKey("Watermark_opacity"))
            assertEquals(Double.parseDouble(expectedFileSettings.get("Watermark_opacity")), actualFileSettings.getWatermark_opacity());
        if (expectedFileSettings.containsKey("Watermark_color"))
            assertEquals(expectedFileSettings.get("Watermark_color"), actualFileSettings.getWatermark_color());
        if (expectedFileSettings.containsKey("Watermark_size"))
            assertEquals(expectedFileSettings.get("Watermark_size"), actualFileSettings.getWatermark_size());
        if (expectedFileSettings.containsKey("ScreenShield"))
            assertEquals(Boolean.parseBoolean(expectedFileSettings.get("ScreenShield")), actualFileSettings.isScreenShield());
        if (expectedFileSettings.containsKey("Download"))
            assertEquals(Integer.parseInt(expectedFileSettings.get("Download")), actualFileSettings.getDownload());
        if (expectedFileSettings.containsKey("PersistentProtection"))
            assertEquals(Boolean.parseBoolean(expectedFileSettings.get("PersistentProtection")), actualFileSettings.isPersistentProtection());
        if (expectedFileSettings.containsKey("Expiry"))
            assertEquals(expectedFileSettings.get("Expiry"), actualFileSettings.getExpiry());
        if (expectedFileSettings.containsKey("DestructSeconds"))
            assertEquals(Integer.parseInt(expectedFileSettings.get("DestructSeconds")), actualFileSettings.getDestructSeconds());
        if (expectedFileSettings.containsKey("AdditionalVerification"))
            assertEquals(Boolean.parseBoolean(expectedFileSettings.get("AdditionalVerification")), actualFileSettings.isAdditionalVerification());
        if (expectedFileSettings.containsKey("Password"))
            assertEquals(expectedFileSettings.get("Password"), actualFileSettings.getPermission());
        if (expectedFileSettings.containsKey("Print"))
            assertEquals(Integer.parseInt(expectedFileSettings.get("Print")), actualFileSettings.getPrint());
        if (expectedFileSettings.containsKey("RestrictForwarding"))
            assertEquals(Boolean.parseBoolean(expectedFileSettings.get("RestrictForwarding")), actualFileSettings.isRestrictForwarding());
        if (expectedFileSettings.containsKey("TermsOfAccess"))
            assertEquals(Boolean.parseBoolean(expectedFileSettings.get("TermsOfAccess")), actualFileSettings.isTermsOfAccess());
        if (expectedFileSettings.containsKey("RequestEmail"))
            assertEquals(Boolean.parseBoolean(expectedFileSettings.get("RequestEmail")), actualFileSettings.isRequestEmail());
        if (expectedFileSettings.containsKey("fixedDate"))
            assertEquals(expectedFileSettings.get("fixedDate"), actualFileSettings.getExpiryDate());
    }

    public void postV1FileReplaceUploadEndpoint(String userType, String endpoint, String fileName, String fileGuid, int httpCode, int statusCode, String statusMsg, boolean isIncludeFileGuid, DataTable dataTable) {
        Map<String, String> settings = convertDataTableToMap(dataTable);
        JsonObject ReplaceFileSettings = new JsonObject();
        settings.forEach(ReplaceFileSettings::addProperty);
        ReplaceFileSettings.addProperty("Guid", isIncludeFileGuid ? fileGuid : SharedDM.fileGUID.get());
        File file = new File("src/main/resources/testFiles/" + fileName);
        List<MultiPartSpecification> multipartSpecs = getMultipartSpecifications(file, "ReplaceFileSettings", ReplaceFileSettings.toString());
        DocumentSecurityFileReplaceResponse documentSecurityFileReplaceResponse =
                POST(httpCode, endpoint, userType, null, multipartSpecs, true, DocumentSecurityFileReplaceResponse.class);
        assertEquals(documentSecurityFileReplaceResponse.getStatus().getStatusCode(),
                statusCode, "Wrong error code returned by API for a DS file upload");
        assertEquals(documentSecurityFileReplaceResponse.getStatus().getStatusMessage(),
                statusMsg, "Wrong error message returned by API for a DS file upload");
        if (documentSecurityFileReplaceResponse.getStatus().getStatusCode() == 12000) {
            SharedDM.svcDocumentSecurityFile.set(documentSecurityFileReplaceResponse.getLink());
            assertEquals(documentSecurityFileReplaceResponse.getVersion(), 2, "Invalid version returned by API for a DS file replace");
            SharedDM.fileGUID.set(documentSecurityFileReplaceResponse.getGuid());
        }
    }

    public void postV1FileReplaceUploadWithInvalidAttName(String userType, String endpoint, String fileName, String fileGuid, int httpCode, int statusCode, String statusMsg, boolean isIncludeFileGuid, DataTable dataTable) {
        Map<String, String> settings = convertDataTableToMap(dataTable);
        JsonObject ReplaceFileSettings = new JsonObject();
        settings.forEach(ReplaceFileSettings::addProperty);
        ReplaceFileSettings.addProperty("Guid", isIncludeFileGuid ? fileGuid : SharedDM.fileGUID.get());
        File file = new File("src/main/resources/testFiles/" + fileName);
        List<MultiPartSpecification> multipartSpecs = getMultipartSpecifications(file, "ReplaceFileSettings1", ReplaceFileSettings.toString());
        DocumentSecurityFileReplaceResponse documentSecurityFileReplaceResponse =
                POST(httpCode, endpoint, userType, null, multipartSpecs, true, DocumentSecurityFileReplaceResponse.class);
        assertEquals(documentSecurityFileReplaceResponse.getStatus().getStatusCode(),
                statusCode, "Wrong error code returned by API for a DS file upload");
        assertEquals(documentSecurityFileReplaceResponse.getStatus().getStatusMessage(),
                statusMsg, "Wrong error message returned by API for a DS file upload");
        if (documentSecurityFileReplaceResponse.getStatus().getStatusCode() == 12000) {
            SharedDM.svcDocumentSecurityFile.set(documentSecurityFileReplaceResponse.getLink());
            assertEquals(documentSecurityFileReplaceResponse.getVersion(), 2, "Invalid version returned by API for a DS file replace");
            SharedDM.fileGUID.set(documentSecurityFileReplaceResponse.getGuid());
        }
    }

    public void postV1FileReplaceEndpoint(String userType, String endpoint, String url, int expectedHttpStatusCode, int expectedApiStatusCode, String expectedApiStatusMessage, DataTable dataTable) {
        Map<String, String> requestForm = new HashMap<>();
        if (dataTable != null && !dataTable.asMaps().isEmpty()) {
            Map<String, String> settings = convertDataTableToMap(dataTable);
            requestForm.putAll(settings);
        }
        requestForm.put("Guid", SharedDM.fileGUID.get());
        if (url != null && !url.trim().isEmpty()) {
            requestForm.put("Url", url);
        }
        requestForm.forEach((k, v) -> infoLog(k + ": " + v));
        DocumentSecurityFileReplaceResponse response = POST(
                expectedHttpStatusCode, endpoint, userType, requestForm, null, true, DocumentSecurityFileReplaceResponse.class);
        assertEquals(response.getStatus().getStatusCode(), expectedApiStatusCode, "Unexpected API status code");
        assertEquals(response.getStatus().getStatusMessage(), expectedApiStatusMessage, "Unexpected API status message");
        if (expectedApiStatusCode == 11000) {
            assertEquals(response.getVersion(), 2, "Invalid version returned by API for a DS file replace");
            SharedDM.svcDocumentSecurityFile.set(response.getLink());
            SharedDM.fileGUID.set(response.getGuid());
        }
    }

    public String buildRecipientsJson(String recipientEmail) {
        try {
            String[] emails = recipientEmail.split(",");
            List<Map<String, String>> recipients = new ArrayList<>();
            for (String emailKey : emails) {
                String username = FileUtils.getUserCredentialsFromJsonFile(emailKey.trim()).get("username");

                Map<String, String> entry = new HashMap<>();
                entry.put("email", username);
                recipients.add(entry);
            }
            return new ObjectMapper().writeValueAsString(recipients);

        } catch (Exception e) {
            infoLog("Failed to build recipient list: " + e.getMessage());
            return "[]";
        }
    }

    public void postV1FileShareEndpoint(String userType, String endpoint, String fileName, String recipientEmail, int expectedHttpStatusCode, int expectedApiStatusCode, String expectedApiStatusMessage, DataTable dataTable) {
        Map<String, String> requestForm = new HashMap<>();
        if (dataTable != null && !dataTable.asMaps().isEmpty()) {
            Map<String, String> settings = convertDataTableToMap(dataTable);
            requestForm.putAll(settings);
        }
        if (recipientEmail != null && !recipientEmail.trim().isEmpty()) {
            requestForm.put("Recipients", buildRecipientsJson(recipientEmail));
        }
        if (fileName != null && !fileName.trim().isEmpty()) {
            requestForm.put("FileName", fileName);
        }
        infoLog("Request Form: " + requestForm);
        infoLog("response is:" + DocumentSecurityShareFileResponse.class);
        DocumentSecurityShareFileResponse response = POST(
                expectedHttpStatusCode, endpoint, userType, requestForm, null, true, DocumentSecurityShareFileResponse.class);
        assertEquals(response.getStatus().getStatusCode(), expectedApiStatusCode, "Unexpected API status code");
        assertEquals(response.getStatus().getStatusMessage(), expectedApiStatusMessage, "Unexpected API status message");
        if (expectedApiStatusCode == 10000) {
            SharedDM.svcDocumentSecurityFile.set(response.getLink());
            SharedDM.fileGUID.set(response.getGuid());
        }
    }

    public void postV1DeleteFileEndpoint(String userType, String endpoint, String shareGuid, int httpCode, int statusCode, String statusMsg, Boolean includeGuidKey) {
        Map<String, String> requestForm = new HashMap<>();
        requestForm.put("Guid", includeGuidKey ? shareGuid : SharedDM.fileGUID.get());
        StatusResponse status = POST(httpCode, endpoint, userType, requestForm, null, true, StatusResponse.class);
        assertEquals(status.getStatus().getStatusCode(), statusCode, "wrong error code returned by API for a DS deleted file");
        assertEquals(status.getStatus().getStatusMessage(), statusMsg, "wrong error message returned by API for a DS deleted file");
    }

    public void postV1FileRecipientRemoveEndpoint(String userType, String endpoint, String recipient, String fileShareGuid, int httpCode, int statusCode, String statusMsg, boolean includeGuidKey) {
        Base.waitForSeconds(1);
        Map<String, String> requestForm = new HashMap<>();
        requestForm.put("Guid", includeGuidKey ? fileShareGuid : SharedDM.fileGUID.get());
        requestForm.put("RecipientEmail", FileUtils.getUserCredentialsFromJsonFile(recipient).get("username"));
        DocumentSecurityRemoveRecipientResponse documentSecurityRemoveRecipientResponse = POST(httpCode, endpoint, userType, requestForm, null, false, DocumentSecurityRemoveRecipientResponse.class);
        assertEquals(documentSecurityRemoveRecipientResponse.getStatus().getStatusCode(),
                statusCode,
                "Mismatch in status code");
        assertEquals(documentSecurityRemoveRecipientResponse.getStatus().getStatusMessage(),
                statusMsg,
                "Mismatch in status message");
    }

    public void validateBlankRequestInEndpoint(String userType, String endpoint, int httpCode, int statusCode, String statusMsg) {
        StatusResponse documentSecurityFileReplaceResponse =
                POST(httpCode, endpoint, userType, null, null, true, StatusResponse.class);
        assertEquals(documentSecurityFileReplaceResponse.getStatus().getStatusCode(),
                statusCode, "Mismatch in status code");
        assertEquals(documentSecurityFileReplaceResponse.getStatus().getStatusMessage(),
                statusMsg, "Mismatch in status message");
    }

    public void postV1FileRecipientAddEndpoint(String userType, String endpoint, String recipientEmail, String fileShareGuid, int httpCode, int statusCode, String statusMsg, boolean includeGuidKey) {
        Map<String, String> requestForm = new HashMap<>();
        requestForm.put("Guid", includeGuidKey ? fileShareGuid : SharedDM.fileGUID.get());
        requestForm.put("RecipientEmail", FileUtils.getUserCredentialsFromJsonFile(recipientEmail).get("username"));
        DocumentSecurityRemoveRecipientResponse documentSecurityRemoveRecipientResponse = POST(httpCode, endpoint, userType, requestForm, null, false, DocumentSecurityRemoveRecipientResponse.class);
        assertEquals(documentSecurityRemoveRecipientResponse.getStatus().getStatusCode(),
                statusCode, "Mismatch in status code");
        assertEquals(documentSecurityRemoveRecipientResponse.getStatus().getStatusMessage(),
                statusMsg, "Mismatch in status message");
    }

    public void getV1FileSettingsEndpointForInvalidRequest(String userType, String endpoint, String fileShareGuid, int httpCode, int statusCode, String statusMsg, boolean includeGuidKey) {
        DocumentSecurityFileSettingsResponse fileSettingsResponse =
                GET(httpCode, endpoint, includeGuidKey ? fileShareGuid : SharedDM.fileGUID.get(), userType, true, DocumentSecurityFileSettingsResponse.class);
        assertEquals(fileSettingsResponse.getStatus().getStatusCode(),
                statusCode, "Wrong error code returned by API for SVC get file settings");
        assertEquals(fileSettingsResponse.getStatus().getStatusMessage(),
                statusMsg, "Wrong error message returned by API for SVC get file settings");
    }

    public void postV1FileReplaceEndpointForInvalidRequest(String userType, String endpoint, String fileShareGuid, int httpCode, int statusCode, String statusMsg, boolean includeGuidKey, DataTable dataTable) {
        Map<String, String> requestForm = new HashMap<>();
        if (dataTable != null && !dataTable.asMaps().isEmpty()) {
            Map<String, String> settings = convertDataTableToMap(dataTable);
            requestForm.putAll(settings);
        }
        requestForm.put("Guid", includeGuidKey ? fileShareGuid : SharedDM.fileGUID.get());
        DocumentSecurityFileReplaceResponse fileReplaceResponse =
                POST(httpCode, endpoint, userType, requestForm, null, false, DocumentSecurityFileReplaceResponse.class);
        infoLog(fileReplaceResponse.getStatus().getStatusMessage());
        assertEquals(fileReplaceResponse.getStatus().getStatusCode(),
                statusCode, "Wrong error code returned by API for SVC get file settings");
        assertEquals(fileReplaceResponse.getStatus().getStatusMessage(),
                statusMsg, "Wrong error message returned by API for SVC get file settings");
    }

    public void postV1FileSettingsEndpoint(String senderUsername, String endpoint, String fileGuid, int httpCode, int statusCode, String statusMsg, Boolean isIncludeFileGuid, DataTable dataTable) {
        Base.waitForSeconds(2);
        Map<String, String> requestForm = new HashMap<>();
        if (dataTable != null && !dataTable.asMaps().isEmpty()) {
            Map<String, String> settings = convertDataTableToMap(dataTable);
            requestForm.putAll(settings);
        }
        requestForm.put("Guid", isIncludeFileGuid ? fileGuid : SharedDM.fileGUID.get());
        DocumentSecurityUpdateFileSettingsRes response = POST(
                httpCode, endpoint, senderUsername, requestForm, null, true, DocumentSecurityUpdateFileSettingsRes.class);
        assertEquals(response.getStatus().getStatusCode(), statusCode, "Unexpected API status code");
        assertEquals(response.getStatus().getStatusMessage(), statusMsg, "Unexpected API status message");
        if (statusCode == 15000) {
            SharedDM.svcDocumentSecurityFile.set(response.getLink());
        }
    }

    public void postV1FileSettingsWithNoRequestBody(String senderUsername, String endpoint, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        Map<String, String> settings = convertDataTableToMap(dataTable);
        JsonObject requestBody = new JsonObject();
        settings.forEach(requestBody::addProperty);
        DocumentSecurityUpdateFileSettingsRes response = POST(
                httpCode, endpoint, senderUsername, null, null, true, DocumentSecurityUpdateFileSettingsRes.class);
        assertEquals(response.getStatus().getStatusCode(), statusCode, "Unexpected status code");
        assertEquals(response.getStatus().getStatusMessage(), statusMsg, "Unexpected status message");
    }
}




