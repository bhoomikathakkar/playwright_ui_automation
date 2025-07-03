package digifyE2E.cucumber.stepDefinitions.dataRoom;

import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.dataRooms.DataRoomFilesPage;
import digifyE2E.pages.dataRooms.DataRoomFolderManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;

public class FilesStepDefinition extends CommonHelper {

    @When("I open the question in QnA and reply to it")
    public void iOpenTheQuestionInQnAAndReplyToIt() {
        DataRoomFilesPage.openQuesInQnA();
        DataRoomFilesPage.addReplyInQnA();
    }

    @And("I mark the question as resolved")
    public void iMarkTheQuestionAsResolved() {
        DataRoomFilesPage.markQuestionAsResolvedInQnA();
    }

    @Then("open QnA and view the reply for the same question")
    public void openQnAAndViewTheReplyForTheSameQuestion() {
        DataRoomFilesPage.openQuesInQnA();
    }

    @And("I upload a {string} file in DR")
    public void iUploadAFileInDR(String fileName) throws IOException {
        DataRoomFilesPage.fileUploadInDR(fileName);
    }

    @Then("I move file in a folder")
    public void iMoveFileInAFolder() {
        DataRoomFilesPage.doMoveORCopyFileToAFolder("move");
    }

    @Then("I copy file in a folder")
    public void iCopyFileInAFolder() {
        DataRoomFilesPage.doMoveORCopyFileToAFolder("copy");
    }

    @And("I validate moved file in a folder")
    public void iValidateMovedFileInAFolder() {
        DataRoomFilesPage.validateMovedFileInFolder();
    }

    @And("I validate copied file in a folder")
    public void iValidateCopiedFileInAFolder() {
        DataRoomFilesPage.validateCopiedFileInFolder();
    }

    @And("I click on Replace file button")
    public void iClickOnReplaceFileButton() {
        DataRoomFilesPage.clickAndValidateReplaceFile();
    }

    @And("I upload the same file in replace new version modal and replace the file")
    public void iUploadAFileInReplaceNewVersionModal() throws IOException {
        DataRoomFilesPage.uploadSameFileInReplaceModal();
        DataRoomFilesPage.replaceNewVersionFileAndValidate();
    }

    @Then("I open the floating menu for the file and validate that the option {string}")
    @Then("I select {string} from floating menu in DR")
    @Then("I select {string} option from More floating menu in DR")
    public void iSelectOptionFromMoreFloatingMenuInDR(String optionType) {
        DataRoomFilesPage.getOptionFromMoreFloatingMenuInDR(optionType);
    }

    @Then("I validate that files tab is blank")
    public void iValidateThatFilesTabIsBlank() {
        DataRoomFilesPage.validateBlankFilesTabInDR();
    }

    @And("I access the file in data room")
    public void iAccessTheFileInDataRoom() {
        DataRoomFilesPage.accessFileInDR();
    }

    @Then("I validate, new dropdown should appear in the files tab")
    public void iValidateNewDropdownShouldAppearInTheFilesTab() {
        DataRoomFilesPage.validateNewBtnInFilesTab(true);
    }

    @Then("I open QnA drawer and clicked on new question, guest list should not be visible to the guest")
    public void iOpenQnADrawerAndClickedOnNewQuestionGuestListShouldNotBeVisibleToTheGuest() {
        DataRoomFilesPage.openQnADrawer();
        DataRoomFilesPage.validateGuestListOptionInQnA("qna group",
                false, true);
    }

    @Then("I open QnA drawer and clicked on new question, guest list should be visible to the guest")
    public void iOpenQnADrawerAndClickedOnNewQuestionGuestListShouldBeVisibleToTheGuest() {
        DataRoomFilesPage.openQnADrawer();
        DataRoomFilesPage.validateGuestListOptionInQnA("qna group",
                true, true);
    }

    @Then("I open QnA, guest list is not visible if qna is set to owners and there are no other guests")
    public void iOpenQnAGuestListIsNotVisibleIfQnaIsSetToOwnersAndThereAreNoOtherGuests() {
        DataRoomFilesPage.openQnADrawer();
        DataRoomFilesPage.validateGuestListOptionInQnA("qna owner",
                false, false);
    }

    @And("I close the Qna drawer")
    public void iCloseTheQnaDrawer() {
        DataRoomFilesPage.closeQnADrawer();
    }

    @Then("I validate that file is marked as unread")
    public void iValidateThatFileOrFolderState() {
        DataRoomFilesPage.validateFileOrFolderStatusInFilesTab(true, true);
    }

    @Then("I validate that file is marked as read")
    public void iValidateThatFileIsMarkedAsRead() {
        DataRoomFilesPage.validateFileOrFolderStatusInFilesTab(true, false);
    }

    @Then("I validate that folder is marked as unread")
    public void iValidateThatFolderIsMarkedAsUnread() {
        DataRoomFilesPage.validateFileOrFolderStatusInFilesTab(false, true);
    }

    @And("I view the folder")
    public void iViewTheFolder() {
        DataRoomFilesPage.viewFolderInDR();
    }

    @Then("I validate folder should be marked as read")
    public void iValidateFolderShouldBeMarkedAsRead() {
        DataRoomFilesPage.validateFileOrFolderStatusInFilesTab(false, false);
    }

    @And("I navigate to data room file's tab from folder tree")
    public void iNavigateToDataRoomFileSTabFromFolderTree() {
        DataRoomFilesPage.navigateToDRRootFromFileViewerFolderTree();
    }

    @Then("I validate that file should be marked as unread in trash can")
    public void iValidateThatFileShouldBeMarkedAsUnreadInTrashCan() {
        DataRoomFilesPage.validateFileOrFolderStatusInTrash(true, true);
    }

    @And("I validate that folder should be marked as read in trash can")
    public void iValidateThatFolderShouldBeMarkedAsReadInTrashCan() {
        DataRoomFilesPage.validateFileOrFolderStatusInTrash(false, false);
    }

    @And("I click on back button from version history page")
    public void iClickOnBackButtonFromVersionHistoryPage() {
        DataRoomFilesPage.goBackFromVersionHistoryPage();
    }

    @And("I upload a {string} file in replace new version page when encrypted storage is fully consumed")
    public void iUploadAFileInReplaceNewVersionPageWhenEncryptedStorageIsFullyConsumed(String fileName) throws IOException {
        DataRoomFilesPage.uploadNewFileInReplaceModal("src/main/resources/testFiles/" + fileName);
        DataRoomFilesPage.clickOnReplaceFileButton();
    }

    @And("I click on replace file and skip notification button on replace file modal")
    public void selectReplaceFileAndSkipNotificationButton() {
        DataRoomFilesPage.clickOnReplaceFileButton();
    }

    @Then("I validate the encrypted storage quota exceeded error message on {string} for team admin")
    public void iValidateTheEncryptedStorageQuotaExceededErrorMessageOnForTeamAdmin(String errorPageType) {
        DataRoomFilesPage.validateEncryptedStorageError(errorPageType, true);
    }

    @Then("I validate the encrypted storage quota exceeded error message on {string} for non admin")
    public void iValidateTheEncryptedStorageQuotaExceededErrorMessageOnForNonAdmin(String errorPageType) {
        DataRoomFilesPage.validateEncryptedStorageError(errorPageType, false);
    }

    @And("I click on add more encrypted storage quota link on {string} and navigated to pricing checkout page")
    public void iClickOnAddMoreEncryptedStorageQuotaLinkOnAndNavigatedToPricingCheckoutPage(String errorPageType) {
        DataRoomFilesPage.clickOnAddEncryptQuotaLink(errorPageType);
    }

    @Then("I select the file and select move and copy option and copy the file in data room")
    public void iSelectMoveCopyOptionAndCopyTheFileInDataRoom() {
        DataRoomFilesPage.copyFileInDRWhenEncryptedStorageIsFull();
    }

    @And("I validated {string} paywall in data room")
    @And("I validated {string} error page in data room")
    public void iValidatedPaywallInDataRoom(String paywallType) {
        DataRoomFilesPage.validateSubscriptionPaywallInDR(paywallType);
    }

    @And("I dismiss the replace file  modal")
    public void iDismissTheReplaceFileModal() {
        DataRoomFilesPage.dismissReplaceFileModal();
    }

    @And("I Expand upload file dropdown")
    public void iExpandUploadFileDropdown() {
        DataRoomFilesPage.expandUploadFileDropdown();
    }

    @And("I store the DR file link in memory")
    @And("I store the DR folder link in memory")
    public void iStoreTheDRFileLinkInMemory() {
        DataRoomFilesPage.copyAndSaveDRFileORFolderLinkInMemory();
    }

    @And("I view the latest folder")
    public void iViewTheLatestFolder() {
        DataRoomFilesPage.viewLatestFolderInDR();
    }

    @And("I select the previously created folder")
    public void iSelectThePreviouslyCreatedFolder() {
        DataRoomFilesPage.selectPreviousFolder();
    }

    @And("I select the latest folder")
    public void iSelectTheLatestFolder() {
        DataRoomFilesPage.selectLatestFolder();
    }

    @Then("I validated {string} text and click on export file index button")
    public void iValidatedTextAndClickOnExportButton(String sectionText) {
        DataRoomFilesPage.validateTextAndClickOnExportButton(sectionText);
    }

    @And("I validated {string} modal on data room export page")
    public void iValidatedModalOnDataRoomExportPage(String modalType) {
        DataRoomFilesPage.validateExportModalOnDRExportPage(modalType);
    }

    @And("I select {string} from more option of context menu")
    public void iSelectFromMoreOptionOfFloatingMenu(String moreMenuOption) {
        DataRoomFilesPage.selectMenuOptionFromMoreMenu(moreMenuOption);
    }

    @And("I navigate to data room list from data room")
    public void iNavigateToDataRoomListFromDataRoom() {
        DataRoomFilesPage.selectGoToDRList();
    }

    @And("open QnA drawer")
    public void openQnADrawer() {
        DataRoomFilesPage.openQnADrawer();
    }

    @And("I ask a question and add all guests")
    public void iAskAQuestionAndAddAllGuests() {
        DataRoomFilesPage.askANewQuestionInQnADrawer();
    }

    @Then("I validate guest {string} file permission access {string} from file context menu")
    public void iValidateGuestFileAccessBasedOnGroupPermission(String userType, String groupPermission) {
        DataRoomFilesPage.validateContextMenuOptionBasedOnPermission(userType, groupPermission);
    }

    @And("I upload a {string} file in DR when encrypted storage is fully consumed")
    public void uploadFileInDRWhenEncryptedStorageIsFull(String fileName) throws IOException {
        CommonUIActions.uploadFile(
                CommonUIActions.getFileList(fileName, "src/main/resources/testFiles/")
        );
    }

    @And("I store the data room link in memory")
    public void iStoreTheDataRoomLinkInMemory() {
        DataRoomFilesPage.copyAndSaveDataRoomLink();
    }

    @And("I create a folder")
    public void iCreateAFolder() throws Exception {
        DataRoomFolderManager.createFolder(false);
    }

    @And("I create a folder when ms editing is enabled")
    public void iCreateAFolderWhenMsEditingIsEnabled() throws Exception {
        DataRoomFolderManager.createFolder(true);
    }

    @Then("Validate {string} in files tab")
    public void validateAppearInFilesTab(String isAdvanceSettingOptionPresent) {
        DataRoomFilesPage.verifyAdvanceSettOptionInFilesTab(isAdvanceSettingOptionPresent);
    }

    @Then("I open the context menu for the file and validate that the option {string}")
    @Then("I open the context menu for a file, select {string} and validate it")
    public void iOpenTheContextMenuForAFileSelectAndValidateIt(String contextMenuOption) {
        DataRoomFilesPage.getContextMenuOptions(contextMenuOption);
    }

    @And("I navigate back to files tab by selecting back button")
    public void iNavigateBackToFilesTabBySelectingBackButton() {
        DataRoomFilesPage.navigateToFilesByClickingBackBtn();
    }

    @And("I close the modal in files tab")
    public void iCloseTheModalInFilesTab() {
        DataRoomFilesPage.closeModalInFilesTab();
    }

    @Then("I access the data room pre-auth url in the browser")
    public void iAccessTheDataRoomPreAuthUrlInTheBrowser() {
        DataRoomFilesPage.visitDRPreAuthUrl(true, false);
    }

    @And("I validate the blank data room when no file is uploaded")
    public void iValidateTheBlankDataRoomWhenNoFileIsUploaded() {
        DataRoomFilesPage.getBlankDR();
    }


    @Then("I access the data room pre-auth url in the browser when link is expired")
    public void iAccessTheDataRoomPreAuthUrlInTheBrowserWhenLinkIsExpired() {
        DataRoomFilesPage.visitDRPreAuthUrl(false, true);
    }

    @And("I access data room pre auth link again in the new browser window when browser lock is true")
    public void iAccessDataRoomPreAuthLinkAgainInTheNewBrowserWindowWhenBrowserLockIsTrue() {
        DataRoomFilesPage.revisitDRPreAuthUrl(true, true);
    }

    @And("I access data room pre auth link again in the new browser window when browser lock is disabled")
    public void iAccessDataRoomPreAuthLinkAgainInTheNewBrowserWindowWhenBrowserLockIsDisabled() {
        DataRoomFilesPage.revisitDRPreAuthUrl(false, false);
    }

    @And("I access data room pre-auth link in new tab")
    public void iAccessDataRoomPreAuthLinkInNewTab() {
        openNewTabAndSwitchToPage();
        DataRoomFilesPage.visitDRPreAuthUrl(true, true);
    }

    @And("I validate the files tab in data room")
    public void iValidateTheFilesTabInDataRoom() {
        DataRoomFilesPage.validateFilesTab();
    }

    @Then("I access the data room pre-auth url in the browser when about page is disabled")
    public void iAccessTheDataRoomPreAuthUrlInTheBrowserWhenAboutPageIsDisabled() {
        DataRoomFilesPage.visitDRPreAuthUrl(false, false);
    }

    @Then("I access data room pre-auth link in new tab when about page is disabled for the data room")
    public void iAccessDataRoomPreAuthLinkInNewTabWhenAboutPageIsDisabledForTheDataRoom() {
        openNewTabAndSwitchToPage();
        DataRoomFilesPage.visitDRPreAuthUrl(false, true);
    }

    @Then("I access data room pre-auth link again in the new browser window when browser lock is true and about page is disabled")
    public void iAccessDataRoomPreAuthLinkAgainInTheNewBrowserWindowWhenBrowserLockIsTrueAndAboutPageIsDisabled() {
        DataRoomFilesPage.revisitDRPreAuthUrl(true, false);
    }

    @Then("I access data room pre-auth link again in the new browser window when browser lock is disabled and about page is disabled for the DR")
    public void iAccessDataRoomPreAuthLinkAgainInTheNewBrowserWindowWhenBrowserLockIsDisabledAndAboutPageIsDisabledForTheDR() {
        DataRoomFilesPage.revisitDRPreAuthUrl(false, false);
    }
}
