package digifyE2E.cucumber.stepDefinitions.documentSecurity;

import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.documentSecurity.ManageSentFilesPage;
import digifyE2E.pages.documentSecurity.SendFilesPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Arrays;
import java.util.Collections;


public class ManageSentFilesStefDefinition extends CommonHelper {

    @And("I click on replace file button and navigates to replace new version for file page")
    public void iClickOnReplaceFileButtonAndNavigatesToReplaceNewVersionForFilePage() {
        manageSentFilesPage.clickOnReplaceFileAndGoToReplaceNewVerPage(false);
    }

    @And("I added file version comment")
    public void iAddedFileVersionComment() {
        manageSentFilesPage.addFileVersionComment();
    }

    @And("I validate file version history")
    public void iValidateFileVersionHistory() {
        manageSentFilesPage.doValidateFileVersionHistory();
    }

    @And("I select {string} from the sort by filter in Manage sent file")
    public void iSelectFromTheSortByFilterInManageSentFile(String filterTypeInSortByFilter) {
        manageSentFilesPage.setFilterOptionInSortBy(filterTypeInSortByFilter);
    }

    @And("I click on {string} button on replace new version page")
    public void iClickOnButtonOnReplaceNewVersionPage(String getFileReplaceBtnType) {
        manageSentFilesPage.clickOnReplaceNewVersionBtnType(getFileReplaceBtnType);
    }

    @And("I click on more from the floating menu and select {string}")
    public void iClickOnMoreFromTheFloatingMenuAndSelect(String optionType) {
        manageSentFilesPage.getOptionFromMoreFloatingMenuInDS(optionType);
    }

    @And("I wait until file is encrypted in manage sent file")
    public void iWaitUntilFileIsEncryptedInManageSentFile() {
        manageSentFilesPage.waitUntilFileIsEncryptedInDS();
    }

    @Then("I click on replace and skip notification on replace new version page and validate error messages")
    public void iClickOnReplaceAndSkipNotificationOnReplaceNewVersionPageAndValidateErrorMessages() {
        manageSentFilesPage.clickOnReplaceBtnAndValidateErrorMsgs();

    }

    @Then("I revoke the file access")
    public void iRevokeTheFileAccess() {
        manageSentFilesPage.revokeFileAccessInManageAccess();
    }

    @And("I delete the file")
    public void iDeleteTheFile() {
        ManageSentFilesPage.deleteFileInMSF();
    }

    @And("I remove the recipient")
    public void iRemoveTheRecipient() {
        ManageSentFilesPage.removeRecipientFromManageAccessPage(true);
    }

    @When("I update {string} in settings for the sent file")
    public void iUpdateInSettingsForTheSentFile(String dsFeatureType) {
        ManageSentFilesPage.updateSentFileSettings(Arrays.asList(dsFeatureType.split(",")));
    }

    @Then("I save the sent file settings")
    public void iSaveTheSentFileSettings() {
        ManageSentFilesPage.saveSentFileSettings();
    }

    @Then("I validate dynamic watermark additional settings on sent file's settings page")
    public void iValidateDynamicWatermarkAdditionalSettingsOnSentFileSSettingsPage(DataTable dataTable) {
        ManageSentFilesPage.validateWatermarkSettingsOnSentFileSettings(dataTable);
    }

    @When("I update dynamic watermark additional settings")
    public void iUpdateDynamicWatermarkAdditionalSettings(DataTable dataTable) {
        SendFilesPage.getDynamicWatermarkSettings(dataTable, false);
    }

    @Then("I add {string} as new recipient in sent file's manage access page")
    public void iAddNewRecipientInSentFileSManageAccessPages(String recipientType) {
        ManageSentFilesPage.addRecipientInFileThroughMSF(recipientType, CommonUIActions.commonDigifyBlueBtn, true, false);
    }

    @Then("I validate feature {string} on sent file's settings page")
    @Then("I validate feature {string} on sent file page")
    public void iValidateFeatureOnSentFilesSettingPage(String settingType) {
        ManageSentFilesPage.validateSettingsOfSentFileOnSettingsPage(Arrays.asList(settingType.split(",")));
    }

    @Then("I validate feature {string} domain on sent file's settings page")
    public void iValidateDomainOnSentFilesSettingsPage(String selectedDomainType) {
        ManageSentFilesPage.validateSettingsOfSentFileOnSettingsPage(Arrays.asList(selectedDomainType.split(",")));
    }

    @Then("I added {string} as a recipient but got a exceeded recipient limit by {string} error")
    public void iAddedRecipientUserAsARecipientButGotAExceededRecipientLimitByError(String recipientEmail, String exceededRecipientLimitNo) {
        ManageSentFilesPage.verifyExcLimitErrorByAddingRecipient(recipientEmail, CommonUIActions.commonDigifyBlueBtn, exceededRecipientLimitNo);

    }

    @Then("I update recipient limit to {string}")
    public void iUpdateRecipientLimitTo(String recipientLimitValue) {
        ManageSentFilesPage.editNoOfRecipientLimit(recipientLimitValue);
    }

    @Then("I select {string} on sent file's settings page")
    @Then("I update screen shield to {string} in settings for the sent file")
    public void iUpdateScreenShieldToInSettingsForTheSentFile(String settingsType) {
        ManageSentFilesPage.updateSentFileSettings(Collections.singletonList(settingsType));
    }

    @And("I added {int} recipients from excel file {string}")
    public void iAddedRecipientsFromExcelFile(Integer noOfRecipient, String file) {
        ManageSentFilesPage.addRecipientsFromExcelFileInDS(noOfRecipient, file, true);
    }

    @And("I click {string} {string} to recipients")
    public void iSendNotificationToRecipients(String notificationType, String messageType) {
        ManageSentFilesPage.notifyRecipients(notificationType, messageType);
    }

    @And("I validated added {int} recipients on manage recipients page")
    public void iValidatedAddedRecipientsOnManageRecipientsPage(int noOfRecipient) {
        manageSentFilesPage.validateRecipientListOnManageRecipientsPage(noOfRecipient);
    }

    @Then("I verify the added recipient in manage recipients page")
    public void iVerifyTheAddedRecipientInManageRecipientPage() {
        ManageSentFilesPage.validateRecipientEmailOnManageAccess();
    }

    @And("I remove all the recipients")
    public void iRemoveAllTheRecipients() {
        ManageSentFilesPage.removeRecipientFromManageAccessPage(false);
    }

    @When("I click on Add recipient button from manage recipients page")
    public void iClickOnAddRecipientButtonFromManageRecipientsPage() {
        ManageSentFilesPage.openAddRecipientInManageRec();
    }

    @Then("I navigate back to manage sent file page")
    public void iNavigateBackToManageSentFilePage() {
        manageSentFilesPage.clickBackArrowInManageRecipient();
    }

    @And("I re-added same {int} recipients from excel file {string}")
    public void iReAddedSameRecipientsFromExcelFile(int noOfRecipient, String file) {
        ManageSentFilesPage.addRecipientsFromExcelFileInDS(noOfRecipient, file, false);
    }

    @And("I add a new recipient {string} in manage sent file when credits are fully consumed")
    public void iAddANewRecipientInManageSentFileWhenCreditsAreFullyConsumed(String recipientType) {
        ManageSentFilesPage.addRecipientInFileThroughMSF(recipientType, ManageSentFilesPage.btnAddInAddRecipient, true, true);
    }

    @And("I click on replace file button and navigates to replace new version for file page when there is no credits")
    public void iClickOnReplaceFileButtonAndNavigatesToReplaceNewVersionForFilePageWhenThereIsNoCredits() {
        manageSentFilesPage.clickOnReplaceFileAndGoToReplaceNewVerPage(true);
    }

    @And("I click on replace file button on version history page")
    public void iClickOnReplaceFileButtonOnVersionHistoryPage() {
        ManageSentFilesPage.clickOnReplaceBtnInVersionHist();
    }

    @Then("System should show {string} modal for the non admin")
    public void systemShouldShowModalForTheNonAdmin(String modalType) {
        SendFilesPage.getStorageFullyConsumedModal(modalType, false);
    }

    @And("I click on restore button")
    public void iClickOnRestoreButton() {
        CommonUIActions.clickOnRestoreIconOnVH();
    }

    @And("I validated {string} paywall on manage sent file page")
    public void iValidatedPaywall(String paywallType) {
        ManageSentFilesPage.validateUpgradePaywall(paywallType);
    }

    @When("I added {string} in add recipient modal")
    public void iAddedInAddRecipientModal(String emailIds) {
        ManageSentFilesPage.addEmailIdInAddRecModal(Arrays.asList(emailIds.split(",")));
    }

    @And("I replace a file using {string} option from {string} list in send file")
    public void iReplaceFileUsingOptionFromList(String importOption, String sourceFile) {
        ManageSentFilesPage.selectSendFileImportOption(importOption, false);
        ManageSentFilesPage.selectSingleFileFromIEFModal(sourceFile, true);
    }

    @And("I replace a different file using {string} option from {string} list in send file")
    public void replaceFileWithDiffFileExtension(String importOption, String sourceFile) {
        ManageSentFilesPage.selectSendFileImportOption(importOption, false);
        ManageSentFilesPage.selectSingleFileFromIEFModalError(sourceFile);
    }

    @And("I click and validate context menu option {string}")
    public void iSelectContextMenuInMSF(String menuOption) {
        ManageSentFilesPage.selectMoreOptions(menuOption);
    }

    @Then("I delete all files")
    public void iDeleteAllFiles() {
        ManageSentFilesPage.deleteAllFilesInMSF();
    }
}
