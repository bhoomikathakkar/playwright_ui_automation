package digifyE2E.cucumber.stepDefinitions.dataRoom;

import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.FileViewer;
import digifyE2E.pages.dataRooms.CreateDataRoomPage;
import digifyE2E.pages.dataRooms.DataRoomFilesPage;
import digifyE2E.pages.dataRooms.DataRoomGuestsPage;
import digifyE2E.testDataManager.SharedDM;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class CreateDataRoomStepDefinition extends CommonHelper {

    @When("I create a data room with default settings")
    public void i_create_a_data_room_with_default_settings() {
        CreateDataRoomPage.createDataRoom(SharedDM.getDataRoomName(), null);
    }

    /**
     * creates a DR with all the features enabled which
     * are passed as the comma separated list
     *
     * @param drFeatures is a comma separated list
     *                   ex: "Screen Shield , Expiry"
     */
    @Given("I create a data room with {string} enabled")
    @Given("I create a data room and select {string}")
    @Given("I create a data room with feature {string}")
    public void iCreateADataRoomWithFeatureTypeEnabled(String drFeatures) {
        CreateDataRoomPage.createDataRoom(SharedDM.getDataRoomName(),
                Arrays.asList(drFeatures.split(",")));
    }

    @And("I validate {string} in data room")
    public void iValidateValidateFeatureTypeInDataRoom(String validateFeatureSettings) {
        CreateDataRoomPage.verifyFeaturesSettings(Arrays.asList(validateFeatureSettings.split(",")));
    }

    @Then("I navigate to data room {string} page")
    public void iNavigateToDataRoomPage(String dataRoomTabsType) {
        CreateDataRoomPage.navigateToDataRoomTabs(dataRoomTabsType);
    }

    @And("I logout from data room")
    public void iLogoutFromDataRoom() {
        CreateDataRoomPage.doLogoutFromDR();
    }

    @Then("I login as {string} and access the same data room")
    public void iLoginAsAndAccessTheSameDataRoom(String user) {
        CreateDataRoomPage.navigateToDataRoomEntity(false, true, false);
        loginPage.loginInDRThroughDRLink(user);
    }

    @Then("I login as {string} and access the data room using direct link")
    public void iLoginAsAndAccessTheDataRoomUsingDL(String user) {
        loginPage.loginInDRThroughDRLink(user);
    }

    @Given("I create DR with {string} and upload a file {string}")
    @Given("I create DR with {string} permission and upload a file {string}")
    public void iCreateDRWithDrFeaturesAndUploadAFile(String drFeatures, String fileName) throws IOException {
        CreateDataRoomPage.createDataRoom(SharedDM.getDataRoomName(),
                Arrays.asList(drFeatures.split(",")));
        CreateDataRoomPage.navigateToDataRoomTabs("files");
        DataRoomFilesPage.copyAndSaveDataRoomLink();
        DataRoomFilesPage.fileUploadInDR(fileName);
    }

    @Given("I create DR with default settings and upload a {string} file")
    public void iCreateDRWithDefaultSettingsAndUploadAFile(String fileName) throws IOException {
        CreateDataRoomPage.createDataRoom(SharedDM.getDataRoomName(), null);
        CreateDataRoomPage.navigateToDataRoomTabs("files");
        DataRoomFilesPage.copyAndSaveDataRoomLink();
        DataRoomFilesPage.fileUploadInDR(fileName);
    }

    @And("guest validate DR permission {string}")
    public void guestValidateDRAccessWhenDRPermissionIs(String validateDRAccessAsGuest) {
        CreateDataRoomPage.validateDRAccessAsGuest(validateDRAccessAsGuest);
    }

    @And("I navigate to {string} tab in data room {string} page")
    public void iNavigateToTabInDataRoomPage(String getTabInGuestPage, String dataRoomTabsType) {
        CreateDataRoomPage.navigateToDataRoomTabs(dataRoomTabsType);
        DataRoomGuestsPage.selectTabInGuestPageOfDR(getTabInGuestPage);
    }

    @And("I delete items permanently from trash can")
    public void iDeleteItemsPermanentlyFromTrashCan() {
        CommonUIActions.selectDeleteInDeleteItemModal();
    }

    @And("I validate TOA, continue and agree TOA as {string}")
    public void iValidateAndContinueTOAAs(String drUserType) {
        CreateDataRoomPage.validateAndContinueWithTOA(drUserType);
    }

    @Then("I decline ToA as guest")
    public void iDeclineToAAsGuest() {
        CreateDataRoomPage.declineTOA();
    }

    @And("I refresh the data room and close ToA")
    public void iRefreshTheDataRoomAndCloseToA() {
        CreateDataRoomPage.refreshDataRoomAndCloseToA();
    }

    @And("I validate that about page should not appear in data room")
    public void iValidateThatAboutPageShouldNotAppearInDataRoom() {
        CreateDataRoomPage.validateAboutPageTabInDR(false);
    }

    @Then("I validate that about tab appears in the data room")
    public void iValidateThatAboutTabAppearsInTheDataRoom() {
        CreateDataRoomPage.validateAboutPageTabInDR(true);
    }

    @Then("I validate, banner image should not appear in data room")
    public void iValidateBannerImageShouldNotAppearInDataRoom() {
        CreateDataRoomPage.validateBannerImageVisibilityInDR(false);
    }

    @And("I validate that banner image appears in DR")
    public void iValidateThatBannerImageAppearsInDR() {
        CreateDataRoomPage.validateBannerImageVisibilityInDR(true);
    }

    @Then("I validate data room {string} error message")
    public void iValidateDataRoomErrorMessage(String drErrorMsg) {
        CreateDataRoomPage.validateDrErrorMsg(drErrorMsg);
    }

    @Then("I add additional settings of dynamic watermark")
    public void iAddAdditionalSettingsOfDynamicWatermark(DataTable dataTable) {
        CreateDataRoomPage.getDynamicWatermarkSettings(dataTable, true);
    }

    @Then("I edit the additional settings of dynamic watermark")
    public void iEditAdditionalSettingsOfDynamicWatermark(DataTable dataTable) {
        CreateDataRoomPage.getDynamicWatermarkSettings(dataTable, false);
    }

    @And("I input data room name")
    @And("I added data room name")
    public void iAddedDataRoomName() {
        CreateDataRoomPage.addDrName();
    }

    @Then("I create a data room")
    public void iCreateADataRoom() {
        CreateDataRoomPage.clickOnCreateDrBtn();
    }

    @And("I select {string} option in data room")
    public void userSelectTOAOptionInDataRoom(String termName) {
        CreateDataRoomPage.enableDRFeatures(Collections.singletonList(termName));
    }

    @And("I validate {string} option in TOA dropdown")
    public void userValidateSelectedTOAOption(String termName) {
        CreateDataRoomPage.validateSelectedToaOption(termName);
    }

    @And("I validate data room pages for {string}")
    public void userValidateDataroomPages(String userRole) {
        createDataRoomPage.validateDRAsPerUserRole(userRole);
    }


    @Then("QnA feature should not be visible to a user")
    public void qnaFeatureShouldNotBeVisibleToAUser() {
        CreateDataRoomPage.validateQnADrawer(false);
    }

    @Then("I edit data room expiry date to {string} more days")
    public void iEditDataRoomExpiryDateToMoreDaysAndTimeToHrsMore(String cldDRExpiryDateToAdd) {
        CreateDataRoomPage.selectDataRoomExpiryDate(cldDRExpiryDateToAdd);
    }

    @Then("I verified expiry date {string} in data room info")
    public void iVerifiedExpiryDateInDataRoomInfo(String expiryDays) {
        CreateDataRoomPage.getDrNameHeading();
        CreateDataRoomPage.validateDrExpiryDateInDrInfo(expiryDays, "//div[@class='dropdown open show']/div[2]/div/div/div[2]");
    }

    @And("I click on upgrade for {string} feature in data room")
    @And("I enable {string} in data room")
    public void iEnableFeaturesInDataRoom(String featureType) {
        CreateDataRoomPage.enableDRFeatures(Collections.singletonList(featureType));
    }

    @Then("I validate and continue enforce email verification modal as data room owner")
    public void iValidateAndContinueEnforceEmailVerificationModalAsDataRoomOwner() {
        CreateDataRoomPage.validateAndContinueEnforceEmailVerificationModal(true);
    }

    @Then("I validate and continue enforce email verification modal")
    public void iValidateAndContinueEnforceEmailVerificationModal() {
        CreateDataRoomPage.validateAndContinueEnforceEmailVerificationModal(false);
    }

    @Then("I validate the enforce email verification modal as guest")
    public void iValidateTheEnforceEmailVerificationModalAsGuest() {
        CreateDataRoomPage.validateVerificationCodeModal();
    }

    @And("I access the data room as {string} with OTP")
    public void iAccessTheDataRoomAsWithOTP(String guestEmail) {
        CreateDataRoomPage.authenticateDrGuestWithOTP(guestEmail);
    }

    @Then("I validate premium feature label for dynamic watermark, screen shield and Q&A on create DR page")
    public void iValidatePremiumFeatureLabelForDynamicWatermarkScreenShieldAndQAOnCreateDRPage() {
        CreateDataRoomPage.validatePremiumFeatureUpgradeLabelForDR();
    }

    @Then("I validate premium feature upgrade modal for {string} in DR")
    public void iValidatePremiumFeatureUpgradeModalForInDR(String upgradePremiumFeatureType) {
        CreateDataRoomPage.validatePremiumUpgradeModalInDRForProPlan(upgradePremiumFeatureType);
    }

    @Then("I validate {string} tabs are enabled")
    public void iValidateTabsAreEnabled(String drTabsType) {
        CreateDataRoomPage.validateDrTabs(Arrays.asList(drTabsType.split(",")));
    }

    @And("I click on folder tree icon in file viewer and select {string}")
    public void iClickOnFolderTreeIconInFileViewerAndSelectOption(String fileOptionType) {
        FileViewer.clickFolderTreeIconInFV();
        FileViewer.selectOptionInFolderTreeInFV(fileOptionType);
    }

    @And("I click on {string} button and navigates to pricing page")
    public void iClickOnButtonAndNavigatesToPricingPage(String quotaBtnType) {
        CommonUIActions.clickStorageExceedBtnAndNavigateToPricingPage(quotaBtnType);
    }

    @And("I create DR and upload a file {string}")
    public void iCreateDRAndUploadAFile(String fileName) throws IOException {
        CreateDataRoomPage.createDataRoom(SharedDM.getDataRoomName(), null);
        DataRoomFilesPage.copyAndSaveDataRoomLink();
        DataRoomFilesPage.fileUploadInDR(fileName);
    }

    @Then("I login as {string} and access the same data room file link")
    public void iLoginAsAndAccessTheSameDataRoomFileLink(String userType) {
        CreateDataRoomPage.navigateToDataRoomEntity(false, false, true);
        loginPage.loginInDRThroughDRLink(userType);
    }

    @Then("I login as {string} and access the same data room folder link")
    public void iLoginAsAndAccessTheSameDataRoomFolderLink(String userType) {
        CreateDataRoomPage.navigateToDataRoomEntity(false, false, true);
        loginPage.loginInDRThroughDRLink(userType);
    }

    @And("I select ok button in the modal")
    public void iSelectOkButtonInTheModal() {
        clickElement(btnOk);
    }

    @And("I click on restore button and validate restored file toast message")
    public void iClickOnRestoreButtonAndValidateRestoredFileToastMessage() {
        CommonUIActions.clickOnRestoreIconOnVH();
        CommonUIActions.selectRestoreBtnAndValidateToast();
    }

    @And("I validate and continue require additional info modal as data room owner")
    public void iValidateAndContinueRequireAdditionalInfoModalAsDataRoomOwner() {
        CreateDataRoomPage.validateAndContinueRAIModalASOwner();
    }

    @Then("I click on create data room")
    public void iClickOnCreateDataRoom() {
        CreateDataRoomPage.selectCreateDRBtn();
    }
}
