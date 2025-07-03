package digifyE2E.cucumber.stepDefinitions.documentSecurity;

import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.documentSecurity.ManageSentFilesPage;
import digifyE2E.pages.documentSecurity.SendFilesPage;
import digifyE2E.pages.landingPage.HomePage;
import digifyE2E.pages.landingPage.LoginPage;
import digifyE2E.utils.FileUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;


public class SendFileStepDefinition extends CommonHelper {


    @When("I navigate to Manage Sent File page")
    public void i_navigate_to_Manage_Sent_File_page() {
        ManageSentFilesPage.navigateToManageSentFilePage();
    }

    @Then("I search the file and view the file")
    public void i_search_the_file_and_view_the_file() {
        CommonUIActions.searchFile();
    }

    @Then("I click on {string} button to send the file")
    public void iClickOnButtonAndSendTheFile(String sendFileOption) {
        SendFilesPage.selectFileSendOption(sendFileOption);
    }

    @And("I copy the file link")
    public void iCopyTheFileLink() {
        SendFilesPage.copyAndSaveSentFileLink();
    }

    @And("I select {string} in screen shield dropdown")
    public void iSelectInScreenShieldDropdown(String getScreenShieldType) {
        SendFilesPage.getSendFileSettings(Arrays.asList(getScreenShieldType.split(",")));
    }

    @And("I select {string} in screen shield")
    public void iSelectViewableAreaInScreenShield(String getViewableArea) {
        SendFilesPage.getSendFileSettings(Arrays.asList(getViewableArea.split(",")));
    }

    @And("I update file expiry to {string} minutes")
    public void iUpdateFileExpiryToMinutes(String expiryMinutes) {
        SendFilesPage.inputExpiryMinutes(expiryMinutes);
    }

    @Then("I upload a {string}")
    @Then("I duplicate {string} file and upload the new file")
    @Then("I upload a file {string} in send files")
    public void iUploadA(String fileName) throws IOException {
        CommonUIActions.uploadFile(Collections.singletonList("src/main/resources/testFiles/" + fileName));
    }

    @And("I select all files")
    @And("I select all items in the list")
    @And("I select all data rooms")
    public void iSelectAllFiles() {
        CommonUIActions.selectAllCheckBox(CommonUIActions.chkAllCheckBox);
    }

    @Then("Application shows error message {string} if click on Send button")
    public void applicationShowsErrorMessageIfClickOnSendButton(String fileUploadErrorMsg) {
        sendFilesPage.validateFileUploadErrorMsgIfClickOnSendBtn(fileUploadErrorMsg);
    }

    @And("I add {string} as recipient")
    public void iAddAsRecipient(String recipientType) {
        LoginPage.userCredentials = FileUtils.getUserCredentialsFromJsonFile(recipientType);
        SendFilesPage.addRecipientInSendFile(recipientType);
    }

    @And("I add a new recipient {string} in manage sent file")
    public void iAddANewRecipientInManageSentFile(String recipientType) {
        ManageSentFilesPage.addRecipientInFileThroughMSF(recipientType, btnCommonButtonToCreate, true, false);
    }


    @Then("I uncheck the enforce verification")
    @Then("I select the stricter email verification checkbox")
    @Then("I enable enforce email verification")
    public void iSelectTheStricterEmailVerificationCheckbox() {
        CommonUIActions.validateAndEnableEnforceEmailVerification();
    }

    @And("I select {string} from preset dropdown")
    public void userSelectPresetFromDropdown(String presetName) {
        sendFilesPage.selectPresetFromDropdown(presetName);
    }

    @Then("I validate custom preset {string}")
    public void userValidateCustomPresetAsPerSettings(String presetState) {
        sendFilesPage.validateCustomPreset(presetState);
    }

    @And("I validate document security pages for {string}")
    public void userValidateDocumentSecurityPages(String userRole) {
        sendFilesPage.validateDSPages(userRole);
    }

    @And("I validate {string} feature option is disable on send file page")
    public void iValidateIsDisableOnSendFilePage(String presetFeature) {
        sendFilesPage.validateDisableElementOnSendFilePage(Arrays.asList(presetFeature.split(",")));
    }

    @And("I select {string} in send files")
    @And("I select {string} in permission dropdown")
    @And("I unselect {string} feature in send files")
    @And("I select {string} feature in send files")
    public void iSelectSettingsInSendFiles(String dsFeatureType) {
        SendFilesPage.getSendFileSettings(Arrays.asList(dsFeatureType.split(",")));
    }

    @When("I enable dynamic watermark and add additional settings and validate the excel warning modal")
    @When("I enable dynamic watermark and add additional settings")
    public void iEnableDynamicWatermarkAndAddAdditionalSettings(DataTable dataTable) {
        SendFilesPage.getDynamicWatermarkSettings(dataTable, true);
    }

    @And("I add {string} in the number of prints")
    @And("I update {string} in the number of prints")
    public void iAddInTheNumberOfPrints(String noOfPrint) {
        SendFilesPage.addNoOfPrintValue(noOfPrint);
    }

    @Then("I input {string} in recipient limit")
    public void iInputInRecipientLimit(String noOfRecipient) {
        SendFilesPage.addNoOfRecipientLimit(noOfRecipient);
    }

    @Then("I validate the file format error message")
    public void iValidateTheFileFormatErrorMessage() {
        SendFilesPage.validateFileFormatErrorIfPermIsAllowPrint();
    }

    @Then("I select {string} and add passcode for security reasons")
    public void iSelectAndAddPasscodeForSecurityReasons(String securityOptionsForFileAccess) {
        SendFilesPage.getSendFileSettings(Arrays.asList(securityOptionsForFileAccess.split(",")));
    }

    @Then("I validate premium feature upgrade modal for {string}")
    public void iValidatePremiumFeatureUpgradeModalFor(String premiumUpgradeFeatureType) {
        SendFilesPage.validatePremiumUpgradeModalInDSForProPlan(premiumUpgradeFeatureType);
    }

    @Then("System should show {string} modal for admin")
    public void systemShouldShowModalForAdmin(String modalType) {
        SendFilesPage.getStorageFullyConsumedModal(modalType, true);
    }

    @And("I click on continue button on the modal and wait until file is sent")
    public void iClickOnContinueButtonOnTheModalAndWaitUntilFileIsSent() {
        SendFilesPage.clickContinueOnModalAndWaitUntilFileIsSent();
    }

    @And("I click on get link & skip notification button to get the warning modal")
    public void iClickOnGetLinkSkipNotificationButtonToGetTheWarningModal() {
        SendFilesPage.clickOnGetLinkSkipNotificationBtn();
    }

    @And("I validate the {string} error message")
    public void iValidateTheErrorMessage(String sendFileErrorMsgType) {
        SendFilesPage.validateErrorMsgForSendFiles(sendFileErrorMsgType);
    }

    @And("I click on send & notify recipients button")
    public void iClickOnSendNotifyRecipientsButton() {
        SendFilesPage.clickSendFileAndNotifyBtn();
    }

    @And("I click on add button")
    public void iClickOnAddButton() {
        ManageSentFilesPage.clickAddBtnOnAddRecipientModal();
    }

    @When("I navigate to sendFiles page using send another file button")
    public void iNavigateToSFUsingSendAnotherFileBtn() {
        SendFilesPage.navigateToSFUsingSendAnotherBtn();
    }

    @When("I upload {int} file using {string} option from {string} list")
    public void iNavigateToPageAndUploadFileUsingImportFromExistingFilesOptionFromList(int noOfFile, String importOption, String sourceFile) {
        ManageSentFilesPage.selectSendFileImportOption(importOption, true);
        SendFilesPage.selectMultipleFileInIEFModal(noOfFile, sourceFile);
    }

    @When("I navigate to {string} page and upload {int} file using {string} option from {string} list")
    public void iNavigateToPageAndUploadNoOfFileFileUsingOptionFromList(String pageType, int noOfFile, String importOption, String sourceFile) {
        HomePage.navigateToPageFromHomePage(pageType);
        ManageSentFilesPage.selectSendFileImportOption(importOption, true);
        SendFilesPage.selectMultipleFileInIEFModal(noOfFile, sourceFile);
    }

    @And("I click on {string} option")
    public void iClickOnOption(String importOption) {
        ManageSentFilesPage.selectSendFileImportOption(importOption, true);
    }

    @Then("I validated send file empty file list page")
    public void iValidatedSendFileEmptyFileListPage() {
        SendFilesPage.validateEmptySendFileListWarningMsg();
    }

    @Then("I validate empty DR list from existing file modal")
    public void iValidateEmptyDRListFromExistingFileModal() {
        SendFilesPage.validateEmptyDRListWarningMsg();
    }

    @And("I again click on {string} option and upload same file as existing from {string} list")
    public void iAgainClickOnOptionAndUploadSameFileAsExistingFromList(String importOption, String sourceFile) {
        ManageSentFilesPage.selectSendFileImportOption(importOption, true);
        SendFilesPage.selectMultipleFileInIEFModal(1, sourceFile);
    }

    @And("I validate {string} and remove files from page")
    @And("I validate {string} and dismiss the modal")
    public void iValidateAndRemoveFilesFromPage(String errorType) {
        SendFilesPage.validateDuplicateErrorAndRemoveFilesOnSendFilePage(errorType);
    }

    @And("I navigate back to send file page using send another file button")
    public void iNavigateBackToSendFilePageUsingSendAnotherFileButton() {
        SendFilesPage.navigateToSFUsingSendAnotherBtn();
    }

    @Then("I validate {string} preset selected on send file page")
    public void iValidatePresetSelectedAndDisableOnSendFilePage(String presetName) {
        SendFilesPage.validateSelectedPresetOnSF(presetName);
    }

    @Then("I validate {string} feature option is auto selected on send file page")
    public void iValidateFeatureOptionIsAutoSelectedOnSendFilePage(String featureType) {
        SendFilesPage.validateFeaturesOnSendFile(Arrays.asList(featureType.split(",")));
    }

    @And("I add the recipient {string} and send the file using the {string} option")
    public void iAddTheRecipientRecipientUserAndSendTheFileUsingTheGetLinkSkipNotificationOption(String recipientType, String sendFileOption) {
        LoginPage.userCredentials = FileUtils.getUserCredentialsFromJsonFile(recipientType);
        SendFilesPage.addRecipientInSendFile(recipientType);
        SendFilesPage.selectFileSendOption(sendFileOption);
        SendFilesPage.copyAndSaveSentFileLink();
    }

    @Then("I upload a file {string} and select features {string} in send files")
    public void iUploadAFileAndSelectFeaturesInSendFiles(String fileName, String dsFeatureType) throws IOException {
        CommonUIActions.uploadFile(Collections.singletonList("src/main/resources/testFiles/" + fileName));
        SendFilesPage.getSendFileSettings(Arrays.asList(dsFeatureType.split(",")));
    }

    @Given("as {string}, I send a {string} file with {string} feature to {string} using {string}")
    public void asISendAFileWithFeatureToUsing(String userType, String fileName, String featureType, String recipientUsername, String sendFileOption) throws IOException {
        loginPage.loginWithValidUserCreds(userType);
        HomePage.navigateToPageFromHomePage("SendFiles");
        CommonUIActions.uploadFile(CommonUIActions.getFileList(fileName, "src/main/resources/testFiles/"));
        SendFilesPage.getSendFileSettings(Arrays.asList(featureType.split(",")));
        LoginPage.userCredentials = FileUtils.getUserCredentialsFromJsonFile(recipientUsername);
        SendFilesPage.addRecipientInSendFile(recipientUsername);
        SendFilesPage.selectFileSendOption(sendFileOption);
    }
}