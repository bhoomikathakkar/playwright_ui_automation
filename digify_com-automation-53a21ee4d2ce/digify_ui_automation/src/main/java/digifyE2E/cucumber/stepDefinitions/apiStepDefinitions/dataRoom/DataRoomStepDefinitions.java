package digifyE2E.cucumber.stepDefinitions.apiStepDefinitions.dataRoom;

import digifyE2E.cucumber.stepDefinitions.apiStepDefinitions.stepHelpers.DataRoomAPIHelper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class DataRoomStepDefinitions {

    private final DataRoomAPIHelper DR_HELPER = new DataRoomAPIHelper();

    @Given("As {string} create a DR via POST dataRoomCreateEndpoint with settings: {string}, {string}, {string}, {string}")
    public void asCreateADRViaPOSTToDataRoomCreateEndpointWithSettings(String drOwner, String permissionType, String accessType, String screenShield, String aboutPage) {
        DR_HELPER.createDataRoomBySvcEndpoint(drOwner, permissionType, accessType, screenShield, aboutPage);
    }

    @When("I {string} generate pre-auth url of the data room with inputs: {string},{int},{string}")
    public void iGeneratePreAuthUrlOfTheDataRoomWithInputs(String drOwner, String recipientEmail, int linkExpiry, String isBrowserLock) {
        DR_HELPER.generateDRPreAuthUrl(drOwner, recipientEmail, linkExpiry, isBrowserLock);
    }

    @And("I {string} add a guest {string} in DR via POST dataRoomAddRecipient endpoint with permission {string}")
    public void iAddAGuestInDRViaPOSTDataRoomAddRecipientEndpointWithPermission(String drOwner, String guestUsername, String permissionType) {
        DR_HELPER.addGuestInDRBySvcEndpoint(drOwner, guestUsername, permissionType);
    }

    @Then("I {string} upload a file {string} in DR via POST dataRoomUploadFile endpoint")
    public void iUploadAFileInDRViaPOSTDataRoomUploadFileEndpoint(String drOwner, String fileName) {
        DR_HELPER.uploadFileInDRBySvcEndpoint(drOwner, fileName);
    }

    @When("I {string} generate pre-auth url of the data room file with inputs: {string},{int},{string}")
    public void iGeneratePreAuthUrlOfTheDataRoomFileWithInputs(String drOwner, String recipientEmail, int linkExpiry, String isBrowserLock) {
        DR_HELPER.generateDRFilePreAuthUrl(drOwner, recipientEmail, linkExpiry, isBrowserLock);
    }


    @When("I {string} POST to the create DR preAuthURL, invalid combinations of {int}, {string} and expect {int}")
    public void iPOSTToTheCreateDRPreAuthURLInvalidCombinationsOfAndExpect(String userType, int linkExpiry, String guestUsername, int statusCode) {
        DR_HELPER.invalidReqBodyForDRPreAuth(userType, linkExpiry, guestUsername, statusCode);
    }

    @When("I {string} POST to the create DR preAuthURL, when data room is deleted {int}, {string} and expect {int}")
    public void iPOSTToTheCreateDRPreAuthURLWhenDataRoomIsDeletedAndExpect(String userType, int linkExpiry, String guestUsername, int statusCode) {
        DR_HELPER.validateDeleteResForDRPreAuth(userType, linkExpiry, guestUsername, statusCode);
    }

    @When("Data room owner {string} trashed the data room file")
    public void dataRoomOwnerTrashedTheDataRoomFile(String userType) {
        DR_HELPER.deleteDataRoomFile(userType);
    }

    @When("I {string} POST to the create DR file preAuthURL when data room file is trashed {int}, {string} and expect {int}")
    public void iPOSTToTheCreateDRFilePreAuthURLWhenDataRoomFileIsTrashedAndExpect(String userType, int linkExpiry, String guestUsername, int statusCode) {
        DR_HELPER.validateDeleteResForDRFilePreAuth(userType, linkExpiry, guestUsername, statusCode);
    }

    @Then("I {string} POST to the create DR file preAuthURL, when guest permission is no access with {int}, {string} and expect {int}")
    public void iPOSTToTheCreateDRFilePreAuthURLWhenGuestPermIsNoAccessAndExpect(String userType, int linkExpiry, String guestUsername, int statusCode) {
        DR_HELPER.validateGuestNoAccessResp(userType, linkExpiry, guestUsername, statusCode);
    }

    @Then("I {string} POST to the create DR file preAuthURL, when guest does not belong to a DR with {int}, {string} and expect {int}")
    public void iPOSTToTheCreateDRFilePreAuthURLWhenGuestNotBelongToADRAndExpect(String userType, int linkExpiry, String guestUsername, int statusCode) {
        DR_HELPER.validateUserNotGuestResp(userType, linkExpiry, guestUsername, statusCode);
    }

    @Then("I {string} POST to the create DR file preAuthURL, when owner does not belong to a DR with {int}, {string} and expect {int}")
    public void iPOSTToTheCreateDRFilePreAuthURLWhenOwnerNotBelongToADRAndExpect(String userType, int linkExpiry, String guestUsername, int statusCode) {
        DR_HELPER.validateInvalidDrOwner(userType, linkExpiry, guestUsername, statusCode);
    }

    @Given("As {string} create a DR via POST dataRoomCreateEndpoint with settings: {string}, {string}, {string}")
    public void asCreateADRViaPOSTDataRoomCreateEndpointWithSettings(String drOwner, String permissionType, String accessType, String screenShield) {
        DR_HELPER.createDataRoomBySvcEndpoint(drOwner, permissionType, accessType, screenShield);
    }

    @When("I {string} POST to the create DR preAuthURL, with invalid GUID {int}, {string} and expect {int}")
    public void iPOSTToTheCreateDRPreAuthURLWithInvalidGUIDLinkExpiryAndExpect(String userType, int linkExpiry, String drOwnerEmail, int statusCode) {
        DR_HELPER.validateInvalidGuidResponse(userType, linkExpiry, drOwnerEmail, statusCode);
    }

    @Then("{string} calls dataroom {string} endpoint, expecting {int}, {int}, {string}")
    public void callsDataroomEndpointExpecting(String drOwnerUsername, String endpoint, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DR_HELPER.getV1DataroomSettingsEndpoint(drOwnerUsername, endpoint, httpCode, statusCode, statusMsg, dataTable);
    }

    @Then("{string} calls {string} to add recipient {string} in DR and expects {int}, {int}, {string}")
    public void callsToAddRecipientInDRAndExpects(String drOwnerUsername, String endpoint, String guestUsername, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DR_HELPER.postV1DataroomAddRecipientEndpoint(drOwnerUsername, endpoint, guestUsername, httpCode, statusCode, statusMsg, false, dataTable);
    }

    @Given("{string} calls {string} to create a DR with invalid data room and expects {int}, {int}, {string}")
    @Given("{string} calls {string} to create a DR with invalid access type and expects {int}, {int}, {string}")
    @Given("{string} calls {string} to create a DR with invalid print count and expects {int}, {int}, {string}")
    @Given("{string} calls {string} to create a DR with invalid permission type and expects {int}, {int}, {string}")
    @Given("{string} calls {string} to create a DR without data room name and expects {int}, {int}, {string}")
    @Given("{string} calls {string} to create a DR without watermark addon and expects {int}, {int}, {string}")
    @Given("{string} calls {string} to create a data room without request body and expects {int}, {int}, {string}")
    @Given("{string} calls {string} to create a DR without ScreenShield addon and expects {int}, {int}, {string}")
    @Given("{string} calls {string} to create a DR with invalid watermark opacity and expects {int}, {int}, {string}")
    @Given("{string} calls {string} to create a data room and expects {int}, {int}, {string}")
    public void callsToCreateADataRoomAndExpects(String drOwnerUsername, String endpoint, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DR_HELPER.postV1DataroomCreateEndpoint(drOwnerUsername, endpoint, httpCode, statusCode, statusMsg, dataTable);
    }

    @Then("{string} calls {string} with a past expiry date and expects error {int}, {int}, {string}")
    @Then("{string} calls {string} with invalid group permission and expects error {int}, {int}, {string}")
    @Then("{string} calls {string} with blank group permission and expects error {int}, {int}, {string}")
    @Then("{string} calls {string} with invalid character group name and expects error {int}, {int}, {string}")
    @Then("{string} calls {string} with invalid print count and expects error {int}, {int}, {string}")
    @Then("{string} calls {string} with a blank group name and expects error {int}, {int}, {string}")
    @Then("{string} calls {string} to create a group in DR and expects {int}, {int}, {string}")
    public void callsToCreateAGroupInDRAndExpects(String drOwnerUsername, String endpoint, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DR_HELPER.postV1DataroomGroupCreateEndpoint(drOwnerUsername, endpoint, null, httpCode, statusCode, statusMsg, false, dataTable);
    }

    @Then("{string} calls {string} with expired DR shareGuid {string} and expects error {int}, {int}, {string}")
    @Then("{string} calls {string} with invalid shareGuid {string} and expects error {int}, {int}, {string}")
    public void callsWithInvalidShareGuidAndExpectsError(String drOwnerUsername, String endpoint, String shareGuid, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DR_HELPER.postV1DataroomGroupCreateEndpoint(drOwnerUsername, endpoint, shareGuid, httpCode, statusCode, statusMsg, true, dataTable);
    }

    @Then("{string} calls {string} to add recipient {string} in DR Group and expects {int}, {int}, {string}")
    public void callsToAddRecipientInDRGroupAndExpects(String drOwnerUsername, String endpoint, String guestUsername, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DR_HELPER.postV1DataroomAddRecipientEndpoint(drOwnerUsername, endpoint, guestUsername, httpCode, statusCode, statusMsg, true, dataTable);
    }

    @Then("{string} calls {string} to validate group in DR, expecting {int}, {int}, {string}")
    public void callsToValidateGroupInDRExpecting(String drOwnerUsername, String endpoint, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DR_HELPER.getV1DataroomGroupEndpoint(drOwnerUsername, endpoint, httpCode, statusCode, statusMsg, dataTable);
    }

    @Then("{string} calls {string} to add recipient {string} in deleted DR, shareGuid {string} and expects {int}, {int}, {string}")
    @Then("{string} calls {string} to add recipient {string} in DR with invalid shareGuid {string} and expects {int}, {int}, {string}")
    public void callsToAddRecipientInDRWithInvalidShareGuidAndExpects(String drOwnerUsername, String endpoint, String guestUsername, String shareGuid, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DR_HELPER.postV1DataroomAddRecipientEndpoint(drOwnerUsername, endpoint, guestUsername, shareGuid, null, httpCode, statusCode, statusMsg, true, false, dataTable);
    }

    @Then("{string} calls {string} to add recipient {string} in DR Group with invalid group guid {string} and expects {int}, {int}, {string}")
    public void callsToAddRecipientInDRGroupWithInvalidGroupGuidAndExpects(String drOwnerUsername, String endpoint, String guestUsername, String groupGuid, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DR_HELPER.postV1DataroomAddRecipientEndpoint(drOwnerUsername, endpoint, guestUsername, null, groupGuid, httpCode, statusCode, statusMsg, false, true, dataTable);
    }

    @Then("{string} calls {string} to add recipient {string} in expired group {string} and DR guid {string} and expects {int}, {int}, {string}")
    public void callsToAddRecipientInDRGroupWithInvalidGroupGuidDRGuidAndExpects(String drOwnerUsername, String endpoint, String guestUsername, String groupGuid, String shareGuid, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DR_HELPER.postV1DataroomAddRecipientEndpoint(drOwnerUsername, endpoint, guestUsername, shareGuid, groupGuid, httpCode, statusCode, statusMsg, true, true, dataTable);
    }

    @Then("{string} calls {string} to add recipient {string} in expired DR {string} and expects {int}, {int}, {string}")
    public void callsToAddRecipientInExpiredDRAndExpects(String drOwnerUsername, String endpoint, String guestUsername, String shareGuid, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DR_HELPER.postV1DataroomAddRecipientEndpoint(drOwnerUsername, endpoint, guestUsername, shareGuid, null, httpCode, statusCode, statusMsg, true, false, dataTable);

    }

    @Then("{string} calls {string} with no request body and expects error {int}, {int}, {string}")
    @Then("{string} calls {string} to delete a data room without request form and expects {int} {int} {string}")
    @Then("{string} calls {string} to delete a group without request form and expects {int} {int} {string}")
    @Then("{string} calls {string} to remove recipient from DR with no request form and expects {int}, {int}, {string}")
    @Then("{string} calls {string} to add recipient in DR with no request form and expects {int}, {int}, {string}")
    @Given("{string} calls {string} to edit data room without request body and expects {int}, {int}, {string}")
    public void callsToAddRecipientInDRWithNoRequestFormAndExpects(String drOwnerUsername, String endpoint, int httpCode, int statusCode, String statusMsg) {
        DR_HELPER.callEndpointWithoutRequestForm(drOwnerUsername, endpoint, httpCode, statusCode, statusMsg);
    }

    @Then("{string} calls {string} to remove recipient {string} from DR with null DR shareGuid {string} and expects {int}, {int}, {string}")
    @Then("{string} calls {string} to remove recipient {string} from DR with invalid DR shareGuid {string} and expects {int}, {int}, {string}")
    public void callsToRemoveRecipientFromDRWithInvalidDRShareGuidAndExpects(String drOwnerUsername, String endpoint, String guestUsername, String shareGuid, int httpCode, int statusCode, String statusMsg) {
        DR_HELPER.postV1DataroomRemoveRecipientEndpoint(drOwnerUsername, endpoint, guestUsername, shareGuid, httpCode, statusCode, statusMsg, true);
    }

    @Then("{string} calls {string} to remove recipient {string} from DR and expects {int}, {int}, {string}")
    public void callsToRemoveRecipientFromDRAndExpects(String drOwnerUsername, String endpoint, String guestUsername, int httpCode, int statusCode, String statusMsg) {
        DR_HELPER.postV1DataroomRemoveRecipientEndpoint(drOwnerUsername, endpoint, guestUsername, null, httpCode, statusCode, statusMsg, false);
    }

    @When("{string} calls {string} to enable ScreenShield in DR settings without the add-on expects {int}, {int}, {string}")
    @When("{string} calls {string} to update dataroom settings without invalid DR name and expects {int}, {int}, {string}")
    @When("{string} calls {string} to enable Watermark in DR settings without the add-on expects {int}, {int}, {string}")
    @When("{string} calls {string} to update dataroom settings without DR name and expects {int}, {int}, {string}")
    @When("{string} calls {string} to edit DR with invalid watermark opacity and expects {int}, {int}, {string}")
    @When("{string} calls {string} to edit DR settings with invalid Access type and expects {int}, {int}, {string}")
    @When("{string} calls {string} to edit DR with invalid permission type and expects {int}, {int}, {string}")
    @When("{string} calls {string} to edit DR with empty domain and expects {int}, {int}, {string}")
    @When("{string} calls {string} to edit DR with invalid print count and expects {int}, {int}, {string}")
    @Given("{string} calls {string} to update dataroom settings and expects {int}, {int}, {string}")
    public void callsToUpdateDataroomSettingsAndExpects(String drOwnerUsername, String endpoint, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DR_HELPER.postV1DataroomSettingsUpdateEndpoint(drOwnerUsername, endpoint, null, httpCode, statusCode, statusMsg, false, dataTable);
    }

    @When("{string} calls {string} to edit DR with DR file ShareGuid {string} and expects {int}, {int}, {string}")
    @When("{string} calls {string} to edit DR with null DR ShareGuid {string} and expects {int}, {int}, {string}")
    @When("{string} calls {string} to edit DR with invalid DR ShareGuid {string} and expects {int}, {int}, {string}")
    public void callsToEditDRWithInvalidDRShareGuidAndExpects(String drOwnerUsername, String endpoint, String shareGuid, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DR_HELPER.postV1DataroomSettingsUpdateEndpoint(drOwnerUsername, endpoint, shareGuid, httpCode, statusCode, statusMsg, true, dataTable);
    }

    @Then("{string} calls {string} to delete the group and expects {int} {int} {string}")
    public void callsToDeleteTheGroupAndExpects(String drOwnerUsername, String endpoint, int httpCode, int statusCode, String statusMsg) {
        DR_HELPER.postV1DataRoomGroupDeleteEndpoint(drOwnerUsername, endpoint, null, null, httpCode, statusCode, statusMsg, false, false);
    }

    @When("{string} calls {string} to update dataroom settings by adding group as default permission and expects {int}, {int}, {string}")
    public void callsToUpdateDataroomSettingsByAddingGroupAsDefaultPermissionAndExpects(String drOwnerUsername, String endpoint, int httpCode, int statusCode, String statusMsg, DataTable dataTable) {
        DR_HELPER.postV1DataroomSettingsUpdateEndpoint(drOwnerUsername, endpoint, null, null, httpCode, statusCode, statusMsg, false, false, dataTable);
    }

    @Then("{string} calls {string} to delete a group with null groupGuid {string} and expects {int} {int} {string}")
    @Then("{string} calls {string} to delete the group with invalid groupShareGuid {string} and expects {int} {int} {string}")
    public void callsToDeleteTheGroupWithInvalidGroupShareGuidAndExpects(String drOwnerUsername, String endpoint, String groupShareGuid, int httpCode, int statusCode, String statusMsg) {
        DR_HELPER.postV1DataRoomGroupDeleteEndpoint(drOwnerUsername, endpoint, null, groupShareGuid, httpCode, statusCode, statusMsg, false, true);
    }

    @Then("{string} calls {string} to delete the group with DR File shareGuid {string} and expects {int} {int} {string}")
    @Then("{string} calls {string} to delete a group with null DR shareGuid {string} and expects {int} {int} {string}")
    @Then("{string} calls {string} to delete the group with invalid DR shareGuid {string} and expects {int} {int} {string}")
    @Then("{string} calls {string} to delete a group with cloned failed DR shareGuid {string} and expects {int} {int} {string}")
    public void callsToDeleteAGroupWithClonedFailedDRShareGuidAndExpects(String drOwnerUsername, String endpoint, String dataRoomGuid, int httpCode, int statusCode, String statusMsg) {
        DR_HELPER.postV1DataRoomGroupDeleteEndpoint(drOwnerUsername, endpoint, dataRoomGuid, null, httpCode, statusCode, statusMsg, true, false);
    }

    @Then("{string} calls {string} to delete a data room and expects {int} {int} {string}")
    public void callsToDeleteDataRoomAndExpects(String drOwnerUsername, String endpoint, int httpCode, int statusCode, String statusMsg) {
        DR_HELPER.postV1DataroomDeleteEndpoint(drOwnerUsername, endpoint, null, httpCode, statusCode, statusMsg, false);
    }

    @Then("{string} calls {string} to delete a data room with already deleted DR shareGuid {string} and expects {int} {int} {string}")
    @Then("{string} calls {string} to delete a data room with DR File shareGuid {string} and expects {int} {int} {string}")
    @Then("{string} calls {string} to delete a data room with invalid DR shareGuid {string} and expects {int} {int} {string}")
    @Then("{string} calls {string} to delete a data room with null DR shareGuid {string} and expects {int} {int} {string}")
    public void callsToDeleteADataRoomWithNullDRShareGuidAndExpects(String drOwnerUsername, String endpoint, String dataRoomShareGuid, int httpCode, int statusCode, String statusMsg) {
        DR_HELPER.postV1DataroomDeleteEndpoint(drOwnerUsername, endpoint, dataRoomShareGuid, httpCode, statusCode, statusMsg, true);
    }

    public void callsToDeleteADataRoomWithAlreadyDeletedDRShareGuidAndExpects(String arg0, String arg1, String arg2, int arg3, int arg4, String arg5) {
    }
}
