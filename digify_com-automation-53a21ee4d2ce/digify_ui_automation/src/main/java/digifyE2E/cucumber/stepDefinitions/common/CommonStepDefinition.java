package digifyE2E.cucumber.stepDefinitions.common;

import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.documentSecurity.SendFilesPage;
import digifyE2E.pages.landingPage.HomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import java.io.IOException;


public class CommonStepDefinition extends CommonHelper {


    @And("I select the first file checkbox on the page")
    @And("I select the first datatable checkbox")
    @And("I select the first item in data room files tab")
    @And("I select the first trashed item")
    @And("I select the first data room")
    @And("I select the file")
    public void iSelectTheFile() {
        CommonUIActions.selectFirstCheckbox();
    }


    @And("I select the group and click on {string} from the floating menu")
    @And("I select {string} from the floating menu")
    @And("I select the guest and click on {string} from the floating menu")
    public void iSelectFromTheFloatingMenu(String floatingMenuOptionType) {
        CommonUIActions.selectOptionFromFloatingMenu(floatingMenuOptionType);
    }

    @And("I close the current tab")
    public void iCloseTheCurrentTab() {
        closeCurrentTab();
    }

    @Then("I verify {string} count {string} on analytics overview")
    @Then("I verify {string} percentage {string} on analytics overview")
    public void iVerifyCountOnAnalyticsPageHeaderIs(String analyticsStatsItemInHeader, String getCountOfItem) {
        CommonUIActions.validateFileStatsHeaderItems(analyticsStatsItemInHeader, getCountOfItem);
    }

    @And("I click on {string} in {string} modal")
    public void iClickOnInModal(String modalBtnType, String modalType) {
        CommonUIActions.clickOnBtnInModal(modalBtnType, modalType);
    }

    @And("I click on the {string} button")
    public void iClickOnTheButton(String actionBtnType) {
        CommonUIActions.clickOnActionBtnAndLink(actionBtnType);
    }


    @And("I reload the page")
    public void iReloadThePage() {
        CommonUIActions.pageReload();
    }

    @And("I validate {int} {string} already exists error modal")
    public void iValidateGuestAlreadyExistsModal(int noOfGuest, String userType) {
        CommonUIActions.validateUserAlreadyExistsModal(noOfGuest, userType);
    }

    @Then("I verify the tooltip text for Enforce Email Verification DR feature")
    public void iVerifyTheTooltipTextForDRFeature() {
        CommonUIActions.validateEnforceVerificationTooltip();
    }

    @And("I navigate to {string} page and upload a {string} file")
    public void iNavigateToPageAndUploadAFile(String pageType, String fileName) throws IOException {
        HomePage.navigateToPageFromHomePage(pageType);
        CommonUIActions.uploadFile(CommonUIActions.getFileList(fileName, "src/main/resources/testFiles/"));
    }

    @And("I navigate to {string} and select {string} from sort by filter")
    public void iNavigateToPageAndSelectFromSortByFilter(String pageType, String filterTypeInSortByFilter) {
        HomePage.navigateToPageFromHomePage(pageType);
        manageSentFilesPage.setFilterOptionInSortBy(filterTypeInSortByFilter);
    }

    @Then("I validate premium feature label for dynamic watermark and screen shield on sent file's settings page")
    @Then("I validate premium feature label for dynamic watermark and screen shield on send files page")
    public void iValidatePremiumFeatureLabelForDynamicWatermarkAndScreenShieldOnSendFilesPage() {
        SendFilesPage.validatePremiumFeatureUpgradeLabelForDS();
    }

    @Then("System should show {string} paywall for team admin")
    public void systemShouldShowPaywallForTeamAdmin(String paywallType) {
        CommonUIActions.validatePaywall(paywallType, true);
    }

    @Then("System should show {string} paywall for the team user")
    public void systemShouldShowPaywallForTheTeamUser(String paywallType) {
        CommonUIActions.validatePaywall(paywallType, false);
    }

    @And("I confirm team restoration from email as {string} user")
    public void iConfirmTeamRestorationFromEmail(String user) {
        CommonUIActions.confirmTeamRestorationEmail(user);

    }

    @And("I validated {string} modal on login screen for user {string}")
    @And("I validated {string} modal on account restore screen for user {string}")
    public void iValidatedModalOnLoginScreenForUser(String modalType, String userType) {
        CommonUIActions.validateDisbandErrorModalOnLoginPage(modalType, userType);
    }

    @And("I validate and close the download popup blocker modal")
    public void iCloseTheDownloadPopupBlockerModal() {
        CommonUIActions.validateAndCloseDownloadPopupBlockerModal();
    }

    @Then("I validated import using digify and other third party options not present on page")
    @Then("I validated third party options not present on page")
    public void iValidatedImportUsingDigifyAndOtherRdPartyOptions() {
        CommonUIActions.validateHiddenOptions();
    }

    @And("I dismiss the import from existing file modal")
    public void iDismissTheImportFromExistingFileModal() {
        CommonUIActions.dismissImportFromExistingFileModal();
    }

    @Then("I validate the {string} warning modal")
    public void iValidateTheWarningModal(String modalType) {
        CommonUIActions.validateWarningModals(modalType);
    }

    @And("I visit the data room link")
    public void iVisitTheDataRoomLink() {
        CommonUIActions.visitDRLink();
    }

    @And("I added {int} recipients from excel file {string} on send file page")
    public void iAddedNoOfRecipientRecipientsFromExcelFileOnSendFilePage(int noOfRecipient, String fileName) {
        CommonUIActions.fillRecipientsFromExcel(noOfRecipient, fileName, SendFilesPage.txtAddRecipient);
    }
}


