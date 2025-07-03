package digifyE2E.cucumber.stepDefinitions.dataRoom;

import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import digifyE2E.pages.dataRooms.DataRoomGroupManager;
import digifyE2E.pages.dataRooms.DataRoomGroupsPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class GroupsStepDefinition extends CommonHelper {

    @And("I edit the recently created group")
    @And("I manage the group")
    @And("I select the group")
    public void iSelectTheGroup() {
        DataRoomGroupsPage.accessRecentlyCreatedGroup();
    }

    @And("I add guest {string} to the group when there are no existing guests in the data room")
    public void iAddGuestInAGroupIfNoExistingGuestInDR(String getGuestDetail) {
        DataRoomGroupsPage.addSingleGuestInGroup(getGuestDetail, false);
    }

    @Then("I rename the group")
    public void iRenameTheGroup() {
        DataRoomGroupsPage.renameGroup();
    }

    @And("I add existing data room guest {string} in a group")
    public void iAddExistingDataRoomGuestInAGroup(String guestUsername) {
        DataRoomGroupsPage.addSingleGuestInGroup(guestUsername, true);
    }

    @Then("I create a group and select {string} permission")
    public void iCreateAGroupAndSelectPermission(String groupPermissionType) {
        DataRoomGroupManager.createGroup(true, groupPermissionType, false, false, null);
    }

    @Then("I create a group with default settings")
    public void iCreateAGroupWithDefaultSettings() {
        DataRoomGroupManager.createGroup(false, null, false, false, null);
    }

    @Then("I create a group and select {string} permission and add existing guest {string} in a group")
    public void iCreateAGroupAndSelectPermissionAndAddExistingGuestInAGroup(String grpPermissionType, String guestUsername) {
        DataRoomGroupManager.createGroup(true, grpPermissionType, false, true, guestUsername);
    }

    @And("I select all guests in a group")
    public void iSelectAllGuestsInAGroup() {
        DataRoomGroupsPage.addAllGuestInDrGroup();
    }

    @And("I save the group")
    public void iSaveTheGroup() {
        DataRoomGroupsPage.saveGroupSettings();
    }

    @Then("I create a group with expiry on fixed date and time")
    public void iCreateAGroupWithExpiryOnFixedDateAndTime() {
        DataRoomGroupManager.createGroup(false, null, true, false, null);
    }

    @Then("I edit the permission {string} of a group")
    public void iEditThePermissionOfAGroup(String permissionType) {
        DataRoomGroupsPage.selectPermissionSettingsInGroup(permissionType);
    }

    @And("I remove the guest {string} from the group")
    public void iRemoveTheGuestFromTheGroup(String guestUsername) {
        DataRoomGroupsPage.removeGuestFromGroup(guestUsername);
    }

    @Then("I delete the group")
    public void iDeleteTheGroup() {
        DataRoomGroupsPage.deleteGroup();
    }

    @Then("I select copy permission and select {string} from the option")
    public void iSelectCopyPermissionAndSelectFromTheOption(String copyPermissionOption) {
        DataRoomGroupsPage.selectCopyPermission();
        DataRoomGroupsPage.selectOptionFromCopyPermission(copyPermissionOption);
    }

    @And("I select the guest from {string} from copy permission modal")
    public void iSelectTheGuestFromFromCopyPermissionModal(String guestUsername) {
        DataRoomGroupsPage.selectGuestOrGroupInCopyPermission(true, guestUsername, false);
    }

    @And("I select the group from copy permission modal")
    public void iSelectTheGroupFromCopyPermissionModal() {
        DataRoomGroupsPage.selectGuestOrGroupInCopyPermission(false, null, true);
    }

    @And("I select the last created group in copy permission modal")
    public void iSelectTheLastCreatedGroupInCopyPermissionModal() {
        DataRoomGroupsPage.selectGuestOrGroupInCopyPermission(false, null, true);
    }

    @And("I select the previously created group in copy permission modal")
    public void iSelectThePreviouslyCreatedGroupInCopyPermissionModal() {
        DataRoomGroupsPage.selectPreviouslyCreatedGroupInCopyPermission();
    }

    @Then("I validate the group permission should be {string}")
    public void iValidateTheGroupPermissionShouldBe(String copiedPermType) {
        DataRoomGroupsPage.validateCopiedPermInGroupTab(copiedPermType);
    }

    @Then("I validate the group permission on copy permission modal which is same as {string}")
    public void iValidateTheGroupPermissionOnCopyPermissionModalWhichIsSameAs(String permissionType) {
        DataRoomGroupsPage.validateCopiedGuestPermOnCreateGroup(permissionType);
    }

    @And("I select the guest in a group")
    public void iSelectTheGuestInAGroup() {
        DataRoomGroupsPage.addAllGuestInDrGroup();
    }

    @Then("I validate that correct message appears if {string} is selected")
    public void iValidateThatCorrectMessageAppearsIfIsSelected(String copyPermissionOptionType) {
        DataRoomGroupsPage.validateMsgOnCopyPermModal(copyPermissionOptionType);
    }

    @And("I select cancel button on copy permission modal")
    public void iSelectCancelButton() {
        DataRoomGroupsPage.selectCancelBtnOnCopyPermission();
    }

    @Then("I validate default group tag for a group")
    public void iValidateDefaultGroupTagForAGroup() {
        DataRoomGroupsPage.validateDefaultGroupTag();
    }

    @Then("I verify that the delete option is not visible for the group")
    @Then("I verify that the delete option is not visible for the DR")
    public void iVerifyThatTheDeleteOptionIsNotVisibleForTheGroup() {
        DataRoomGroupsPage.validateDeleteFloatingMenuOptionIsNotVisible();
    }

    @And("I create a group")
    @And("I create another group")
    public void iCreateAnotherGroup() {
        DataRoomGroupManager.createGroup(false, null, false, false, null);
    }

    @And("I create another group and add a guest {string}")
    public void iCreateAnotherGroupAndAddAGuest(String guestUsername) {
        DataRoomGroupManager.createGroup(false, null, false, false, guestUsername);
    }
}
