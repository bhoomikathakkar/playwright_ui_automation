package digifyE2E.cucumber.stepDefinitions.common;

import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import digifyE2E.pages.FileViewer;
import digifyE2E.pages.landingPage.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Arrays;

import static org.testng.AssertJUnit.assertTrue;

public class FileViewerStepDefinitions extends CommonHelper {
    FileViewer fileViewer = new FileViewer();

    @And("I validate that what-if excel should not appear in the file viewer")
    public void iValidateThatWhatIfExcelShouldNotAppearInTheFileViewer() {
        FileViewer.validateWhatIfExcelHeader(false);
    }

    @And("I validate that what-if excel should appear in the file viewer")
    public void iValidateThatWhatIfExcelShouldAppearInTheFileViewer() {
        FileViewer.validateWhatIfExcelHeader(true);
    }

    @And("I select display preset name on file viewer")
    public void userSelectDisplayPresetNameOnFileViewer() {
        FileViewer.selectPresetNameDisplayOnFileViewer();
    }

    @And("I view the file in file viewer")
    public void iViewTheFileInFileViewer() {
        FileViewer.viewFileInFileViewer(false);
    }

    @And("I view data room's file in the file viewer")
    public void iViewTheDRFileInFileViewer() {
        FileViewer.viewFileInFileViewer(false);
    }

    @And("I validate the file name")
    public void iValidateTheFileName() {
        FileViewer.validateFileName();
    }

    @And("I validate file expiry time on file viewer")
    public void iValidateFileExpiryTimeOnFileViewer() {
        String expiryContainer = "//countdown";
        waitUntilSelectorAppears(expiryContainer);
        assertTrue("Expecting expiry time to be present on the file viewer",
                isElementVisible(expiryContainer));
    }

    @And("I validate the preset name on file viewer {string}")
    public void iValidateThePresetName(String presetName) {
        FileViewer.validatePresetNameOnFileViewer(presetName);
    }

    @Then("I Request access for DR  from file viewer")
    public void iRequestAccessForDR() {
        FileViewer.requestAccessForDR();
    }
    @Then("I Request access for DS from file viewer")
    public void iRequestAccessForDS() {
        FileViewer.requestAccessForDS();
    }

    @Then("I use another email to access DR or DS from file viewer")
    public void iUseAnotherEmailToAccessDR() {
        FileViewer.useAnotherEmailToAccessDR();
    }

    @And("I click on {string} link from file viewer")
    public void iClickOnLinkFromFileViewer(String link) {
        FileViewer.exitFromFileViewer(link);
    }

    @And("I validate {string} in file viewer")
    public void iValidateInFileViewer(String fileViewerBtnType) {
        FileViewer.validateButtonsInFileViewer(Arrays.asList(fileViewerBtnType.split(",")));
    }

    @Then("I validate screen shield in file viewer")
    public void iValidateScreenShieldInFileViewer() {
        FileViewer.verifyScreenShieldContainerInFV(true);
    }

    @Then("I validate screen shield should not appear in file viewer")
    public void iValidateScreenShieldShouldNotAppearFileViewer() {
        FileViewer.verifyScreenShieldContainerInFV(false);
    }

    @And("I logout from file viewer as sender")
    public void iLogoutFromFileViewerAsSender() {
        FileViewer.doLogoutFromFileViewerAsSender();
    }

    @Then("Verify an error message {string} displayed in the file viewer")
    public void verifyAnErrorMessageDisplayedInFileViewer(String errorMsg1) {
        FileViewer.validateFileErrorMessage(errorMsg1.trim());
    }

    @Then("Verify two error messages {string}, {string} and error code {string} in file viewer")
    public void verifyTwoErrorMessagesAndErrorCodeInFileViewer(String errorMsg1, String errorMsg2, String errorCode) {
        FileViewer.validateFileErrorMessage(errorMsg1.trim());
        FileViewer.validateFileErrorMessage(errorMsg2.trim());
        FileViewer.validateFileErrorMessage(errorCode.trim());
    }

    @Then("Verify two error messages {string} and {string} displayed in file viewer")
    public void verifyThreeErrorMessagesDisplayedInFileViewer(String errorMsg1, String errorMsg2) {
        FileViewer.validateFileErrorMessage(errorMsg1.trim());
        FileViewer.validateFileErrorMessage(errorMsg2.trim());
    }
    @Then("Verify three error messages {string}, {string} and {string} displayed in file viewer")
    public void verifyThreeErrorMessagesDisplayedInFileViewer(String errorMsg1, String errorMsg2, String errorCode) {
        FileViewer.validateFileErrorMessage(errorMsg1.trim());
        FileViewer.validateFileErrorMessage(errorMsg2.trim());
        FileViewer.validateFileErrorMessage(errorCode.trim());
    }

    @And("I fill up the new user signup form in file viewer")
    public void iFillUpTheNewUserSignupFormInFileViewer() {
        FileViewer.fillSignupFormInFileViewer();
    }

    @And("I navigate to file list from file viewer")
    public void iNavigateToFileListFromFileViewer() {
        FileViewer.navigateToFileListFromFileViewer();
    }

    @And("I download the file")
    public void iDownloadTheFile() {
        FileViewer.downloadFileInFileViewer();
    }

    @And("view file as sender")
    public void viewFileAsSender() {
        FileViewer.viewFileAsSender();
    }

    @And("I validate screen shield on file viewer")
    public void iValidateScreenShieldOnFileViewer() {
        FileViewer.verifyScreenShieldContainerInFV(true);
    }

    @And("I validate that screen shield is not appearing in file viewer")
    public void iValidateScreenShieldShouldNotAppearInFileViewer() {
        FileViewer.verifyScreenShieldContainerInFV(false);
    }

    @And("I navigate to received files page from file viewer")
    public void iNavigateToReceivedFilesPageFromFileViewer() {
        FileViewer.goToViewReceivedFilesFromFileViewer();
    }

    @And("I validate file version in file viewer")
    public void iValidateFileVersionInFileViewer() {
        FileViewer.doFileVersionValidationInFileViewer();
    }

    @And("Input verification code in file viewer as {string}")
    @And("I access the file, as {string} with OTP")
    public void accessTheFileInputOTPValidationCodeAndClickOnVerifyButton(String user) {
        FileViewer.authenticateUserWithOTP(user);
    }

    @And("I access the file link as a new user")
    public void iAccessTheFileLinkAsANewUser() {
        FileViewer.navigateToSentFileLink(true);
    }

    @And("I access the file link as existing user")
    public void iAccessTheFileLink() {
        FileViewer.navigateToSentFileLink(false);
    }

    @And("I select the viewing option {string}")
    public void iSelectTheViewingOption(String fileViewingOptionType) {
        FileViewer.selectFileViewingOption(fileViewingOptionType);
    }

    @Then("I access the file link as existing user {string} and generate OTP")
    public void iAccessTheFileLinkAsExistingUserAndGenerateOTP(String getUserType) {
        fileViewer.navigateToSentFileLinkAndGenerateOTP(getUserType);
    }

    @And("I access the file link and entered passcode")
    public void iAccessTheFileLinkAndEnteredPasscode() {
        FileViewer.navigateToSecuredFileLink(true);
    }

    @And("I access the file link")
    public void iAccessTheFileLinkEnteredEmail() {
        FileViewer.navigateToSecuredFileLink(false);
    }

    @And("I agree TOA and continue as {string}")
    public void iAgreeTOAAndContinueAs(String userType) {
        FileViewer.agreeAndContinueTOAInFV(userType, true);
    }

    @And("I verify that TOA is not present")
    public void iVerifyThatTOAIsNotPresent() {
        FileViewer.agreeAndContinueTOAInFV(null, false);
    }

    @And("I click on brand logo and verify that user not redirected to home page")
    public void iClickOnBrandLogoAndVerifyThatUserNotRedirectedToHomePage() {
        FileViewer.validateBrandLogoRedirection();
    }

    @And("I validated {string} error in file viewer")
    @And("I validated {string} error page on file viewer")
    public void iValidatedErrorPageOnFileViewer(String permissionType) {
        FileViewer.validateErrorPage(permissionType);
    }

    @When("I {string} login in subscription expired file")
    public void ILoginInSubscriptionExpiredFile(String userType) {
        loginPage.loginInFileWhichIsNotAvailable(userType, "destructive image");
    }

    @When("I access the file link when {string}")
    public void iAccessTheFileLinkWhen(String senderAccountStatus) {
        LoginPage.accessSubExpiredOrAccDeletedFileLink(senderAccountStatus);
    }

    @When("I {string} login into file viewer as locked user")
    public void iLoginIntoFileViewerAsLockedUser(String senderAccount) {
        LoginPage.lockAccFileLink(senderAccount);
    }

    @Then("I access the pre-auth url in the browser")
    public void accessThePreAuthUrlInTheBrowser() {
        FileViewer.visitPreAuthUrl(true, false);
    }

    @Then("Re-access pre-auth URL in a new browser window")
    public void reAccessPreAuthUrlInANewBrowserWindow() {
        openNewWindow();
        FileViewer.visitPreAuthUrl(false, false);
    }

    @Then("I access the pre auth file in the new tab")
    public void iAccessThePreAuthFileInTheNewTab() {
        openNewTabAndSwitchToPage();
        FileViewer.visitPreAuthUrl(false, false);
    }

    @Then("I access the pre-auth url in the browser when link is expired")
    public void iAccessThePreAuthUrlInTheBrowserWhenLinkIsExpired() {
        FileViewer.visitPreAuthUrl(false, true);
    }

    @And("I access data room file pre auth link")
    public void iAccessDataRoomFilePreAuthLink() {
        FileViewer.visitDRFilePreAuthUrl();
    }

    @And("Access the DR file pre-auth URL in a new tab after logging in")
    public void accessDRFilePreAuthLinkInNewTabAfterLoggingIn() {
        openNewTabAndSwitchToPage();
        FileViewer.visitDRFilePreAuthUrl();
    }

    @And("Re-access DR file pre-auth link in a new browser window")
    public void reAccessDRFilePreAuthLinkInTheNewBrowserWindow() {
        openNewWindow();
        FileViewer.visitPreAuthUrl(false, false);
    }

    @Then("Access DR-File pre-auth url when link is expired")
    public void accessDRFilePreAuthUrlWhenLinkIsExpired() {
        FileViewer.visitPreAuthUrl(false, true);
    }

    @And("Verified the email field is disabled on the Request Additional Info form in File Viewer")
    public void iValidatedThatTheEmailFieldIsAutoFilledOnTheRequestAdditionalInfoFormInTheFileViewer() {
        FileViewer.checkEmailFieldAndProceedRAI(false);
    }

    @And("I validated that the email field is disabled on the request additional info form and i submit the form")
    public void iValidatedEmailFieldAndSubmitForm() {
        FileViewer.checkEmailFieldAndProceedRAI(true);
    }

    @And("Entered {string} in the Request Additional Info form in File Viewer")
    public void iFillUpOnReqAdditionalInfoFormInFileViewer(String inputFields) {
        FileViewer.fillRAIFormInFV(Arrays.asList(inputFields.split(",")), false);
    }

    @And("Submit the RAI form with all fields {string} blank in File Viewer")
    public void iKeepAllFieldsEmptyOnReqAdditionalInfoFormInFileViewerAndSubmitForm(String inputFields) {
        FileViewer.fillRAIFormInFV(Arrays.asList(inputFields.split(",")), true);
    }

    @And("I validated error below each fields {string}")
    public void iValidatedErrorBelowEachFields(String inputFields) {
        FileViewer.validateErrorMsgInRAIForm(Arrays.asList(inputFields.split(",")));
    }

    @And("I {string} login in expired document security file")
    public void iLoginInExpiredDocumentSecurityFile(String recipientUsername) {
        loginPage.loginInExpiredFile(recipientUsername, false);
    }

    @And("I {string} login in expired data room file")
    public void iLoginInExpiredDataRoomFile(String guestUsername) {
        loginPage.loginInExpiredFile(guestUsername, true);
    }

    @And("I wait until file is loaded in file viewer")
    public void iWaitUntilFileIsLoadedInFileViewer() {
        FileViewer.waitForFileLoad();
    }
}
