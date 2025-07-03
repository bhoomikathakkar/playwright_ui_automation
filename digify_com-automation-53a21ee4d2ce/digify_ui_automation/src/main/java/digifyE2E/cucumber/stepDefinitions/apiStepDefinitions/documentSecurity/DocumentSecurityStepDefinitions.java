package digifyE2E.cucumber.stepDefinitions.apiStepDefinitions.documentSecurity;

import digifyE2E.cucumber.stepDefinitions.apiStepDefinitions.stepHelpers.DocumentSecurityAPIHelper;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DocumentSecurityStepDefinitions {

    private final DocumentSecurityAPIHelper DS_HELPER = new DocumentSecurityAPIHelper();

    @Given("{string} shares the file {string} with {string} using file settings: {string}, {int},{int}")
    public void sharesAFileWithRecipientUsingFileSettingsAs(String senderUsername, String fileName, String recipientEmail, String permissionType, int print, int download) throws JsonProcessingException {
        DS_HELPER.uploadFileWithSettings(senderUsername, fileName, permissionType, recipientEmail, print, download);
    }

    @When("I {string} generate the pre-auth URL for the shared file with attributes {string},{int},{string}")
    public void generateThePreAuthUrlOfTheSharedFileWithInputFieldsIsBrowserLock(String fileSenderEmail, String recipientEmail, int linkExpiry, String isBrowserLock) throws JsonProcessingException {
        DS_HELPER.generateDSPreAuthUrl(fileSenderEmail, recipientEmail, linkExpiry, isBrowserLock);
    }

    @Then("I {string} POST to the create preAuthURL, invalid combinations of {string}, {int} and expect {int}")
    public void iPOSTToTheCreatePreAuthURLInvalidCombinationsOfAndExpect(String fileSenderEmail, String recipientEmail, int linkExpiry, int statusCode) {
        DS_HELPER.invalidReqBodyForPreAuthUrl(fileSenderEmail, recipientEmail, linkExpiry, statusCode);
    }

    @Then("I {string} POST to the create preAuthURL for a public file with attributes {string}, {int} and expect {int}")
    public void iPOSTToTheCreatePreAuthURLForAPublicFileWithAttributesAndExpect(String fileSenderEmail, String recipientEmail, int linkExpiry, int statusCode) {
        DS_HELPER.validateFilePermErrorForDSPreAuthUrl(fileSenderEmail, recipientEmail, linkExpiry, statusCode);
    }

    @Then("I {string} POST to the create preAuthURL for a deleted shared file with attributes {string}, {int} and expect {int}")
    public void iPOSTToTheCreatePreAuthURLForADeletedSharedFileWithAttributesLinkExpiryAndExpect(String senderUsername, String recipientEmail, int linkExpiry, int statusCode) {
        DS_HELPER.validateDeletedFileErrorForDSPreAuthUrl(senderUsername, recipientEmail, linkExpiry, statusCode);
    }

    @When("{string} calls {string} to share {string} with {string} and expects {int}, {int}, {string}")
    public void callsToShareWithAndExpects(String senderUsername, String endpoint, String fileName, String recipientEmail, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DS_HELPER.postV1FileUploadEndpoint(senderUsername, endpoint, fileName, recipientEmail, httpCode, statusCode, statusMsg, dataTable);
    }

    @When("{string} calls {string} without FileSettings and expects {int}, {int}, {string}")
    public void callsWithoutFileSettingsAndExpects(String senderUsername, String endpoint, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DS_HELPER.postV1FileUploadWithoutFileSettings(senderUsername, endpoint, httpCode, statusCode, statusMsg, dataTable);
    }

    @Then("{string} calls {string} to replace {string} with invalid ShareGUID {string} and expects {int}, {int}, {string}")
    public void callsWithInvalidShareGUIDAndExpects(String senderUsername, String endpoint, String file, String shareGuid, int httpcode, int statusCode, String statusMsg, DataTable dataTable) {
        DS_HELPER.postV1FileReplaceUploadEndpoint(senderUsername, endpoint, file, shareGuid, httpcode, statusCode, statusMsg, true, dataTable);
    }

    @When("{string} calls {string} endpoint to remove the {string} from the file and expects {int}, {int}, {string}")
    public void callsEndpointToRemoveTheFromTheFileAndExpects(String senderUsername, String endpoint, String recipient, int httpCode, int statusCode, String statusMsg) {
        DS_HELPER.postV1FileRecipientRemoveEndpoint(senderUsername, endpoint, recipient, null, httpCode, statusCode, statusMsg, false);
    }

    @When("{string} calls {string} to remove the {string} from the file with file shareGuid {string} and expects {int}, {int}, {string}")
    public void callsEndpointToRemoveTheFromTheFileWithGuidAndExpects(String senderUsername, String endpoint, String recipient, String fileShareGuid, int httpCode, int statusCode, String statusMsg) {
        DS_HELPER.postV1FileRecipientRemoveEndpoint(senderUsername, endpoint, recipient, fileShareGuid, httpCode, statusCode, statusMsg, true);
    }

    @Then("{string} calls {string} to replace {string} and expects error {int}, {int}, {string}")
    @Then("{string} calls {string} to replace {string} with invalid file path and expects {int}, {int}, {string}")
    @Then("{string} calls {string} to replace {string} with blank file path and expects {int}, {int}, {string}")
    @Then("{string} calls {string} to replace {string} with invalid comment and expects {int}, {int}, {string}")
    @Then("{string} calls {string} to share {string} and expects {int}, {int}, {string}")
    public void callsToShareAndExpects(String senderUsername, String endpoint, String replaceFileName, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DS_HELPER.postV1FileReplaceUploadEndpoint(senderUsername, endpoint, replaceFileName,null, httpCode, statusCode, statusMsg,false, dataTable);
    }

    @Then("{string} calls {string} endpoint when request is blank and expects {int}, {int}, {string}")
    public void callsEndpointWhenRequestIsBlankAndExpects(String senderUsername, String endpoint, int httpCode, int statusCode, String statusMsg) {
        DS_HELPER.validateBlankRequestInEndpoint(senderUsername, endpoint, httpCode, statusCode, statusMsg);

    }

    @When("{string} calls {string} endpoint to add a {string} in a file and expects {int}, {int}, {string}")
    public void callsEndpointToAddRecInAFileAndExpects(String senderUsername, String endpoint, String recipientEmail, int httpCode, int statusCode, String statusMsg) {
        DS_HELPER.postV1FileRecipientAddEndpoint(senderUsername, endpoint, recipientEmail, null, httpCode, statusCode, statusMsg, false);
    }

    @Given("{string} calls {string} endpoint to add a {string} in a file when file shareGuid is {string} and expects {int}, {int}, {string}")
    public void callsEndpointToAddAInAFileWhenFileShareGuidIsAndExpects(String senderUsername, String endpoint, String recipientEmail, String fileShareGuid, int httpCode, int statusCode, String statusMsg) {
        DS_HELPER.postV1FileRecipientAddEndpoint(senderUsername, endpoint, recipientEmail, fileShareGuid, httpCode, statusCode, statusMsg, true);
    }

    @Given("{string} calls {string} to share a file {string} with {string} and expects {int}, {int}, {string}")
    public void callsToShareAFileWithAndExpects(String senderUsername, String endpoint, String fileName, String recipientEmail, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DS_HELPER.postV1FileShareEndpoint(senderUsername, endpoint, fileName, recipientEmail, httpCode, statusCode, statusMsg, dataTable);
    }

    @Then("{string} calls {string} to get file settings and expects {int}, {int}, {string}")
    public void callV1ToGetFileSettings(String senderUsername, String endpoint, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DS_HELPER.getV1FileSettingsEndpoint(senderUsername, endpoint, httpCode, statusCode, statusMsg, dataTable);
    }

    @Then("{string} calls replace endpoint {string} to replace with {string}, expecting {int}, {int}, {string}")
    public void callsToReplaceAViaShareAndExpects(String senderUsername, String endpoint, String fileUrl, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DS_HELPER.postV1FileReplaceEndpoint(senderUsername, endpoint, fileUrl, httpCode, statusCode, statusMsg, dataTable);
    }

    @Then("{string} calls {string} with invalid guid {string} and expects error {int}, {int}, {string}")
    public void callsWithInvalidGuidAndExpectsError(String senderUsername, String endpoint, String fileShareGuid, int httpCode, int statusCode, String statusMsg) {
        DS_HELPER.getV1FileSettingsEndpointForInvalidRequest(senderUsername, endpoint, fileShareGuid, httpCode, statusCode, statusMsg, true);
    }

    @Then("{string} calls {string} with invalid file owner and expects error {int}, {int}, {string}")
    public void callsWithInvalidFileOwnerAndExpectsError(String senderUsername, String endpoint, int httpCode, int statusCode, String statusMsg) {
        DS_HELPER.getV1FileSettingsEndpointForInvalidRequest(senderUsername, endpoint, null, httpCode, statusCode, statusMsg, false);
    }

    @Then("{string} calls replace endpoint {string} with DR-File ShareGUID {string}, expecting {int}, {int}, {string}")
    @Then("{string} calls replace endpoint {string} with invalid ShareGUID {string}, expecting {int}, {int}, {string}")
    public void callsReplaceEndpointWithInvalidShareGUIDAndExpects(String senderUsername, String endpoint, String shareGuid, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DS_HELPER.postV1FileReplaceEndpointForInvalidRequest(senderUsername, endpoint, shareGuid, httpCode, statusCode, statusMsg, true, dataTable);
    }

    @Then("{string} calls replace endpoint {string} with invalid url, expecting {int}, {int}, {string}")
    @Then("{string} calls replace endpoint {string} with null url, expecting {int}, {int}, {string}")
    @Then("{string} calls replace endpoint {string} with null replace file name, expecting {int}, {int}, {string}")
    @Then("{string} calls replace endpoint {string} with invalid comment, expecting {int}, {int}, {string}")
    public void callsReplaceEndpointWithInvalidCommentAndExpects(String senderUsername, String endpoint, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DS_HELPER.postV1FileReplaceEndpointForInvalidRequest(senderUsername, endpoint, null, httpCode, statusCode, statusMsg, false, dataTable);
    }

    @Then("{string} calls replace endpoint {string} with different file type, expecting error {int}, {int}, {string}")
    @Then("{string} calls replace endpoint {string} to replace with {string}, expecting error {int}, {int}, {string}")
    @Then("{string} calls replace endpoint {string} with deleted file, expecting {int}, {int}, {string}")
    public void callsReplaceEndpointWithDeletedFileAndExpects(String senderUsername, String endpoint, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DS_HELPER.postV1FileReplaceEndpoint(senderUsername, endpoint, null, httpCode, statusCode, statusMsg, dataTable);
    }

    @Then("{string} calls {string} to update file settings and expects {int}, {int}, {string}")
    public void callsToUpdateSettingsAndExpects(String senderUsername, String endpoint, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DS_HELPER.postV1FileSettingsEndpoint(senderUsername, endpoint,null, httpCode, statusCode, statusMsg,false, dataTable);
    }

    @Then("{string} calls {string} to update file settings with invalid shareGuid {string} and expects {int}, {int}, {string}")
    public void callsToUpdateFileSettingsWithInvalidShareGuidAndExpects(String senderUsername, String endpoint, String fileGuid, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DS_HELPER.postV1FileSettingsEndpoint(senderUsername, endpoint, fileGuid, httpCode, statusCode, statusMsg, true, dataTable);
    }

    @Then("{string} calls {string} to update file settings with no request body and expects {int}, {int}, {string}")
    public void callsToUpdateFileSettingsWithNoRequestBodyAndExpects(String senderUsername, String endpoint, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DS_HELPER.postV1FileSettingsWithNoRequestBody(senderUsername, endpoint, httpCode, statusCode, statusMsg, dataTable);
    }

    @When("{string} calls {string} to delete a file and expects {int}, {int}, {string}")
    public void callsToDeleteAFileAndExpects(String senderUsername, String endpoint, int httpCode, int statusCode, String statusMsg) {
        DS_HELPER.postV1DeleteFileEndpoint(senderUsername, endpoint, null,httpCode, statusCode, statusMsg,false);
    }
    
    @Given("{string} call the delete endpoint with invalid {string} ShareGUID and verify HTTP {int}, status code {int}, and {string} status message")
    @Given("{string} call the delete endpoint with a null {string} ShareGUID and verify HTTP {int}, status code {int}, and {string} status message")
    public void callTheDeleteEndpointWithANullGUIDAndVerifyHTTPStatusCodeAndStatusMessage(String senderUsername, String shareGuid, int httpCode, int statusCode, String statusMsg) {
    }

    @When("{string} calls {string} to share file with recipient without request form and expects {int}, {int}, {string}")
    @Then("{string} calls {string} to replace file with no request form and expects error {int}, {int}, {string}")
    @Given("{string} calls {string} with empty request form and expects {int}, {int}, {string} status message")
    public void callsWithEmptyRequestFormAndExpectsStatusMessage(String senderUsername, String endpoint, int httpCode, int statusCode, String statusMsg) {
        DS_HELPER.validateBlankRequestInEndpoint(senderUsername, endpoint,httpCode, statusCode, statusMsg);
    }

    @When("{string} calls {string} to delete an already deleted file and expects {int}, {int}, {string}")
    public void callsToDeleteAnAlreadyDeletedFileAndExpects(String senderUsername, String endpoint, int httpCode, int statusCode, String statusMsg) {
        DS_HELPER.postV1DeleteFileEndpoint(senderUsername, endpoint, null, httpCode, statusCode, statusMsg, false);
    }

    @Given("{string} calls {string} to delete another owner's file, ShareGUID {string} and expects HTTP {int}, status code {int}, and status message {string}")
    @Given("{string} calls {string} with null {string} ShareGUID and expects {int}, {int}, {string}")
    @Given("{string} calls {string} with invalid {string} ShareGUID and expects {int}, {int}, {string}")
    public void callsWithInvalidShareGUIDAndExpects(String senderUsername, String endpoint, String shareGuid, int httpCode, int statusCode, String statusMsg) {
        DS_HELPER.postV1DeleteFileEndpoint(senderUsername, endpoint, shareGuid, httpCode, statusCode, statusMsg, true);
    }

    @Then("{string} calls {string} to replace {string} with invalid attribute name and expects error {int}, {int}, {string}")
    public void callsToReplaceWithInvalidAttributeNameAndExpectsError(String senderUsername, String endpoint, String replaceFileName, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DS_HELPER.postV1FileReplaceUploadWithInvalidAttName(senderUsername, endpoint, replaceFileName,null, httpCode, statusCode, statusMsg,false, dataTable);
    }
}