package digifyE2E.cucumber.stepDefinitions.dataRoom;

import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.dataRooms.GranularPermissionPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class GranularPermStepDef extends CommonHelper {

    @And("I open guest access preview on add guest page")
    public void iOpenGuestAccessPreviewOnAddGuestPage() {
        GranularPermissionPage.openGAPOnAddGuestPage();
    }

    @Then("I click on gp link for the group")
    public void iClickOnGpLinkForTheGroup() {
        GranularPermissionPage.clickGPLink(true);
        waitUntilSelectorAppears(CommonUIActions.txtGPHeading);
    }

    @Then("I click on gp for the group and update the GP for DR files {string}")
    public void iClickOnGpForTheGroupAndUpdateTheGPForDRFiles(String editFileGP) {
        GranularPermissionPage.selectPermissionInGPPage(editFileGP);
    }

    @And("In GP, I select {string}")
    public void inGPISelect(String viewAsInGP) {
        GranularPermissionPage.selectViewAsInGP(viewAsInGP);
    }

    @Then("I set GP for data room {string}")
    public void iSetGPForDataRoom(String setGP) {
        GranularPermissionPage.setPermissionInGP(setGP);
    }

    @Then("I validate data room permission {string} on GP page")
    public void iValidateDataRoomPermissionOnGPPage(String validateDRGP) {
        GranularPermissionPage.validatePermissionOnGP(validateDRGP);

    }

    @Then("I validate file permission {string} on GP page")
    public void iValidateFilePermissionOnGPPage(String validateFileGP) {
        GranularPermissionPage.validatePermissionOnGP(validateFileGP);
    }

    @And("I click on gp link for the guest")
    public void iClickOnGpLinkForTheGuest() {
        GranularPermissionPage.clickGPLink(false);
        waitUntilSelectorAppears(CommonUIActions.txtGPHeading);
    }

    @And("I click on set edit permission to edit button")
    public void iClickOnSetEditPermissionToEditButton() {
        GranularPermissionPage.selectEditPermInModal();
    }

    @And("I close granular permission page")
    public void iCloseGranularPermissionPage() {
        clickElement(CommonUIActions.btnDeleteDR);
    }

    @And("I validate manage access page for a data room file")
    public void iValidateManageAccessPageForADataRoomFile() {
        GranularPermissionPage.validateManageAccessPageForFile();
    }
}
