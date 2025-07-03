package digifyE2E.cucumber.stepDefinitions.dataRoom;

import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import digifyE2E.pages.dataRooms.DataRoomGuestsPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GuestsStepDefinition extends CommonHelper {

    @When("I navigate to {string} tab")
    public void iNavigateToTab(String getTabInGuestPage) {
        DataRoomGuestsPage.selectTabInGuestPageOfDR(getTabInGuestPage);
    }

    @Then("I add {string} guest with permission as {string} in data room")
    public void iAddGuestWithPermissionAsInDataRoom(String guestUsername, String permissionType) {
        DataRoomGuestsPage.addGuest(guestUsername, permissionType, false, null, null, false);
    }

    @Then("I validate {string} email in members tab")
    public void iValidateEmailInMembersTab(String guestUsername) {
        DataRoomGuestsPage.validateGuestUsernameOnMembersTab(guestUsername);
    }

    @And("I validate guest access preview based on permission {string}")
    public void iValidateGuestAccessPreviewBasedOnPermissionDrFeatures(String drPermissionType) {
        DataRoomGuestsPage.validateGuestAccessPreview(drPermissionType);
    }

    @And("I select the guest")
    public void iSelectTheGuest() {
        DataRoomGuestsPage.selectGuestInGuestTab();
    }

    @And("guest validate future access for {string} days")
    public void validateGuestFutureAccessForDays(String days) {
        DataRoomGuestsPage.validateFutureAccess(days);
    }

    @And("I return to guest link")
    public void iReturnToGuestLink() {
        DataRoomGuestsPage.clickOnReturnToGuestLink();
    }

    @And("I add {string} to guest for next {string} {string}")
    public void iAddExpiryToGuestForNextDays(String expiry, String days, String timeToAdd) {
        DataRoomGuestsPage.selectAccessExpiryInEditGuest(expiry);
        DataRoomGuestsPage.selectAndValidateDateAndTime(days, timeToAdd);
        DataRoomGuestsPage.saveChangesOnEditGuestPage();
    }

    @Then("I validate guest expiry {string}")
    public void iValidateGuestExpiryDays(String days) {
        DataRoomGuestsPage.validateGuestExpiry(days);
    }

    @And("I validate future access on edit guest page")
    public void iValidateFutureAccess() {
        DataRoomGuestsPage.validateFutureAccessDate();
    }

    @Then("I add another guest {string} with permission as {string} in DR")
    public void iAddAnotherGuestWithPermissionAsInDR(String guestEmail, String permissionType) {
        DataRoomGuestsPage.addGuest(guestEmail, permissionType, true, null, null, false);
    }

    @And("I click on back button from add new guest page")
    public void iClickOnBackButtonFromAddNewGuestPage() {
        DataRoomGuestsPage.goBackFromAddGuestPage();
    }

    @And("I add {int} guest with permission as {string} from excel file {string} in data room")
    public void iAddGuestWithPermissionAsPermissionTypeInDataRoom(int noOfGuest, String permissionType, String file) {
        DataRoomGuestsPage.addGuest(null, permissionType, false, noOfGuest, file, true);
    }

    @Then("I validated added {int} guests on guests page")
    public void iValidatedAddedGuestsOnGuestsPage(int noOfGuest) {
        DataRoomGuestsPage.validateAddedBulkGuestOnGuestsPage(noOfGuest);
    }

    @And("I notify guest to visit DR")
    public void iNotifyGuest() {
        DataRoomGuestsPage.notifyDRGuest(true);
    }

    @Then("I added same {int} guest again from {string} excel file in bulk invitation")
    public void iReAddSameGuestWithPermissionAsFromExcelFileInDataRoom(int noOfGuest, String file) {
        DataRoomGuestsPage.addGuestsFromExcelToBulkInvite(noOfGuest, file);
    }

    @Then("I added same {int} guest again from file {string} which were already added in DR")
    public void iAgainAddSameGuestFromExcelFileInDataRoom(int noOfGuest, String file) {
        DataRoomGuestsPage.navigateToAddNewGuestPage(true);
        DataRoomGuestsPage.addGuestsFromExcelToBulkInvite(noOfGuest, file);
    }

    @And("I try to add a new guest {string} in data room when guest quota is full")
    public void iTryToAddANewGuestInDataRoomWhenGuestQuotaIsFull(String guestUsername) {
        DataRoomGuestsPage.navigateToAddNewGuestPage(true);
        DataRoomGuestsPage.addGuestEmailInAddGuest(guestUsername);
        DataRoomGuestsPage.notifyDRGuest(false);
    }

    @And("I add new guest {string} email in guest page")
    public void iAddNewGuestEmailInGuestPage(String guestUsername) {
        DataRoomGuestsPage.navigateToAddNewGuestPage(true);
        DataRoomGuestsPage.addGuestEmailInAddGuest(guestUsername);
    }

    @Then("I add a guest {string}")
    public void iAddAGuest(String guestUsername) {
        DataRoomGuestsPage.addGuest(guestUsername, null, false, null, null, false);
    }

    @Then("I add a guest {string} with permission as {string}")
    public void iAddAGuestWithPermissionAs(String guestUsername, String permissionType) {
        DataRoomGuestsPage.addGuest(guestUsername, permissionType, false, null, null, false);
    }

    @Then("I manage the guest {string}")
    public void iManageTheGuest(String guestUsername) {
        DataRoomGuestsPage.clickOnManageGuestOnGuestPage(guestUsername);
    }

    @And("I remove the selected group")
    public void iRemoveTheSelectedGroup() {
        DataRoomGuestsPage.performActionOnManageGuestPage(true, "No group selected");
    }

    @And("I save changes on manage guest page")
    public void iSaveChangesOnManageGuestPage() {
        DataRoomGuestsPage.saveChangesOnEditGuestPage();
    }

    @And("I copy the guest name {string}")
    public void iCopyTheGuestName(String guestUsername) {
        DataRoomGuestsPage.getGuestNameFromGuestPage(guestUsername);
    }

    @And("I copy the guest {string} permission")
    public void iCopyTheGuestPermission(String guestUsername) {
        DataRoomGuestsPage.getGuestPermissionFromGuestPage(guestUsername);
    }

    @Then("I validate user {string} on guest page")
    public void iValidateUserOnGuestPage(String guestUsername) {
        DataRoomGuestsPage.validateEmailIdOnGuestTab(guestUsername);
    }

    @And("I click on add and send notification button")
    public void iClickOnAddAndSendNotificationButton() {
        DataRoomGuestsPage.clickAddAndSendNotificationBtn();
    }

    @And("I validate user {string} should not be visible on guest page")
    public void iValidateUserShouldNotBeVisibleOnGuestPage(String guestUsername) {
        DataRoomGuestsPage.validateEmailIdNotPresentOnGuestTab(guestUsername);
    }

    @Then("I validate the selected default group in add new guest page")
    public void iValidateTheSelectedDefaultGroupInAddNewGuestPage() {
        DataRoomGuestsPage.validateSelectedDefaultGroup();
    }

    @And("I remove the guest {string} from guest list")
    public void iRemoveTheGuestFromGuestList(String userType) {
        DataRoomGuestsPage.removeSingleGuest(userType);
    }
}
