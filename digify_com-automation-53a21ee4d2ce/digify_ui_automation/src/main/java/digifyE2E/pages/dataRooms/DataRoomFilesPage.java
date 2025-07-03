package digifyE2E.pages.dataRooms;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.ElementState;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.FileViewer;
import digifyE2E.pages.documentSecurity.ManageSentFilesPage;
import digifyE2E.pages.documentSecurity.SendFilesPage;
import digifyE2E.pages.landingPage.PricingPage;
import digifyE2E.testDataManager.SharedDM;
import digifyE2E.utils.LogUtils;
import digifyE2E.utils.RandomUtils;

import java.io.IOException;

import static digifyE2E.pages.dataRooms.CreateDataRoomPage.txtDRFiles;
import static org.testng.Assert.*;

public class DataRoomFilesPage extends CommonUIActions {

    static final String txtFolderPageHeading = "//*[@id='page-title-header']/div[1]/div/div[1]/app-data-room-doc-breadcrumb/div/div[3]/p";
    static final String btnQnaDrawer = "//button[@id='drf-btn-qna']";
    static final String lstLatestQuesInInbox = "(//div[@class='each-question']/div/div)[1]";
    static final String DRQnASubject = "QA Question" + "-" + RandomUtils.getRandomString(3, false);
    static final String DRQnAMsg = "Test Msg" + "-" + RandomUtils.getRandomString(3, false);
    static final String DRQnAReply = "Test Reply" + "-" + RandomUtils.getRandomString(3, false);
    static final String drdSelectGuestInQnA = "//div[@class='c-btn']/../..";
    static final String btnCreateNewInQnA = "//div[@class='inbox-header']/../div/div[2]/button[2]";
    static final String btnCopy = "(//span[@class='txt']/../..)[2]";
    static final String btnDismissModal = "//button[@aria-label='Close']";
    static final String btnInboxInQnA = "//button[@class='button-close btn']/../div[2]/ul/li/a";


    public static void validateBlankFilesTabInDR() {
        waitUntilSelectorAppears(getXpathForContainsText("Files", "p"));
        assertTrue(isElementVisible(getXpathForContainsText("Drag and drop files or folders here", "p")));
    }

    public static void closeQnADrawer() {
        clickElement("//button[@class='button-close btn']");
    }

    public static void accessFileInDR() {
        String lnkFileLocator = getXpathForContainsText(SharedDM.getNewFileNameWithExtension(), "a");
        page().waitForLoadState(LoadState.LOAD);
        assertEquals(getElementText(lnkFileLocator), SharedDM.getNewFileNameWithExtension());
        clickAndSwitchToNewTab(lnkFileLocator);
        page().waitForLoadState(LoadState.LOAD);
    }


    public static void waitForFileUploadToCompleteInDR() {
        String txtDRFileEncryption = "//*[@id='dataRoomList']/ngx-datatable/div/datatable-body//span[contains(@class,'render-status')]";
        waitUntilSelectorAppears(getXpathForTextEquals("Notify guests after uploading your files?", "h4"));
        clickElement("//button[@class='btn digify-social-btn']");
        waitUntilSelectorAppears(getXpathForTextEquals(" Upload completed ", "p"));
        clickElement(getXpathForContainsText(" Close ", "span"));
        waitForSelectorStateChange(txtDRFileEncryption, ElementState.HIDDEN);
    }

    private static void selectMoveCopyOptionOnFile() {
        selectFirstCheckbox();
        selectOptionFromFloatingMenu("move/copy");
        waitUntilSelectorAppears(getXpathForContainsText("Move/Copy", "h4"));
    }

    public static void doMoveORCopyFileToAFolder(String optionType) {
        String lnkFolderInMoveCopyModal = "(//div[@class='file-info-data']/div[2]/div/div/div/a)[1]";
        String btnMove = "(//span[@class='txt']/../..)[1]";
        String txtTargetLocationLabel = getXpathForTextEquals(" Target location: " + DataRoomFolderManager.getLatestFolderName() + " ", "div");
        switch (optionType) {
            case "move":
                selectMoveCopyOptionOnFile();
                clickElement(lnkFolderInMoveCopyModal);
                waitUntilSelectorAppears(txtTargetLocationLabel);
                assertTrue(isElementVisible(txtTargetLocationLabel));
                clickElement(btnMove);
                waitUntilSelectorAppears(CreateDataRoomPage.txtDRFiles);
                break;
            case "copy":
                selectMoveCopyOptionOnFile();
                clickElement(lnkFolderInMoveCopyModal);
                waitUntilSelectorAppears(txtTargetLocationLabel);
                assertTrue(isElementVisible(txtTargetLocationLabel));
                clickElement(btnCopy);
                waitUntilSelectorAppears(CreateDataRoomPage.txtDRFiles);
                break;
            default:
                fail("No match found for move or copy option: " + optionType);
        }
    }

    public static void copyFileInDRWhenEncryptedStorageIsFull() {
        selectMoveCopyOptionOnFile();
        clickElement(btnCopy);
    }

    public static void validateMovedFileInFolder() {
        String lnkFileNameInFolder = getXpathForTextEquals(" " + SharedDM.getNewFileNameWithExtension() + " ", "a");
        waitForSelectorStateChange(lnkFileNameInFolder, ElementState.HIDDEN);
        selectFirstCheckbox();
        selectOptionFromFloatingMenu("view in dr");
        assertEquals(getElementText(txtFolderPageHeading), DataRoomFolderManager.getLatestFolderName());
        assertEquals(getElementText(lnkFileNameInFolder), SharedDM.getNewFileNameWithExtension());
    }

    public static void validateCopiedFileInFolder() {
        String lnkFileNameInFolder = getXpathForTextEquals(" " + SharedDM.getNewFileNameWithExtension() + " ", "a");
        clickElement(getXpathForTextEquals(" " + DataRoomFolderManager.getLatestFolderName() + " ", "a"));
        assertEquals(getElementText(txtFolderPageHeading), DataRoomFolderManager.getLatestFolderName());
        assertEquals(getElementText(lnkFileNameInFolder), SharedDM.getNewFileNameWithExtension());
    }

    public static void clickAndValidateReplaceFile() {
        page().waitForLoadState(LoadState.LOAD);
        clickElement(btnCommonButtonToCreate);
    }

    public static void clickOnReplaceFileButton() {
        clickElement(getXpathForTextEquals("Replace file & skip notifications ", "span") + "/../..");
    }

    public static void replaceNewVersionFileAndValidate() {
        String lblVersionOnReplaceNewVersion = "//span[@class= 'datatable-header-cell-label draggable' and text()='Version']";
        waitUntilSelectorAppears(getXpathForTextEquals(" Uploaded successfully ", "p"));
        clickOnReplaceFileButton();
        waitUntilSelectorAppears(lblVersionOnReplaceNewVersion);
        assertTrue(isElementVisible(lblVersionOnReplaceNewVersion));
        waitUntilSelectorAppears(ManageSentFilesPage.txtCurrentVersion);
        assertTrue(isElementVisible("(//em[contains(text(),'– Current version')])[1]")); //current version label
    }

    public static void getOptionFromMoreFloatingMenuInDR(String optionType) {
        String lnkDRMoreFloatingMenu = "//button[@class='toolbar-item' and text()=' More ']";
        switch (optionType.toLowerCase()) {
            case "version history":
                selectValueFromDropdownList(lnkDRMoreFloatingMenu, lnkFloatingMenuList, optionType);
                page().waitForLoadState(LoadState.LOAD);
                waitUntilSelectorAppears("//datatable-footer//p");
                page().waitForLoadState(LoadState.DOMCONTENTLOADED);
                waitForSeconds(1); //Added hard wait to load the elements on the page
                break;
            case "manage access", "replace file":
                selectValueFromDropdownList(lnkDRMoreFloatingMenu, lnkFloatingMenuList, optionType);
                break;
            case "analytics":
                CommonUIActions.selectValueFromDropdownList(lnkDRMoreFloatingMenu, CommonUIActions.lnkFloatingMenuList, optionType);
                waitUntilSelectorAppears(getXpathForContainsText("Analytics for \"" + SharedDM.getNewFileNameWithExtension() + "\"", "h4"));
                break;
            case "rearrange":
                CommonUIActions.selectValueFromDropdownList(lnkDRMoreFloatingMenu, CommonUIActions.lnkFloatingMenuList, optionType);
                waitUntilSelectorAppears(getXpathForTextEquals("Rearrange", "h4"));
                break;
            case "change name & description":
                CommonUIActions.selectValueFromDropdownList(lnkDRMoreFloatingMenu, CommonUIActions.lnkFloatingMenuList, optionType);
                waitUntilSelectorAppears(getXpathForTextEquals("Change name & description", "h4"));
                break;
            case "version history for existing file":
                CommonUIActions.selectValueFromDropdownList(lnkDRMoreFloatingMenu, CommonUIActions.lnkFloatingMenuList, "version history");
                waitUntilSelectorAppears("//div[contains(@class,'page-title-text')]");
                page().waitForLoadState(LoadState.DOMCONTENTLOADED);
                page().waitForLoadState(LoadState.LOAD);
                break;
            case "question":
                clickElement("//i[@class='fa-comment-alt-lines far']/..");
                waitUntilSelectorAppears(btnCreateNewInQnA);
                page().waitForLoadState(LoadState.LOAD);
                assertTrue(isElementVisible(btnInboxInQnA));
                closeQnADrawer();
                break;
            case "manage access is not visible":
                assertFalse(isElementVisible("//div[@class='cdk-overlay-pane']/div/a/div/i[contains(@class,'fa-users-medical')]/../.."));
                break;
            case "analytics is not visible":
                assertFalse(isElementVisible("//div[@class='cdk-overlay-pane']/div/a/div/i[contains(@class,'fa-chart-bar far')]/../.."));
                break;
            case "download":
                clickElement("//i[@class='fa-arrow-to-bottom far']/..");
                CommonUIActions.validateAndCloseDownloadPopupBlockerModal();
                break;
            case "link":
                clickElement("//i[@class='fa-link far']/..");
                assertEquals(getElementText("//span[@class='sub-text']"),
                        "Use this link to point your data room guests directly to this file.");
                page().waitForLoadState(LoadState.LOAD);
                assertEquals(getElementText("//app-copy-link//h4"), "Get file link");
                CommonUIActions.manageLinkModal();
                break;
            case "edit-->link":
                clickElement("//i[@class='fa-link far']/..");
                assertEquals(getElementText("//span[@class='sub-text']"),
                        "This link can be accessed by data room guests. Only data room owners can invite new guests.");
                page().waitForLoadState(LoadState.LOAD);
                assertEquals(getElementText("//app-copy-link//h4"), "Get file link");
                CommonUIActions.manageLinkModal();
                break;
            default:
                fail("No match found in the more floating menu: " + optionType);
        }
    }

    public static void uploadSameFileInReplaceModal() {
        String btnUploadFileInReplaceModal = "//input[@data-ao-id='ds-replace-sm']";
        page().setInputFiles(btnUploadFileInReplaceModal, SharedDM.getNewFilePath());
    }

    public static void uploadNewFileInReplaceModal(String filePath) throws IOException {
        String btnUploadFileInReplaceModal = "(//input[@type='file'])[3]";
        CommonUIActions.setFileName();
        page().setInputFiles(btnUploadFileInReplaceModal, CommonUIActions.getDuplicateFileOf(filePath, SharedDM.getNewFileName()));
        LogUtils.infoLog("File " + SharedDM.getNewFileName() + " uploaded successfully");
        waitForSeconds(2);
    }

    public static void openQnADrawer() {
        clickElement(btnQnaDrawer);
        waitUntilSelectorAppears(btnCreateNewInQnA);
        page().waitForLoadState(LoadState.LOAD);
        assertTrue(isElementVisible(btnInboxInQnA));
    }

    public static void askANewQuestionInQnADrawer() {
        String txtEnterSubject = "//input[@placeholder='Enter subject']";
        String txtMessage = "//div[@dir='auto']/div";

        clickElement(btnCreateNewInQnA);
        clickElement(drdSelectGuestInQnA);
        clickElement(getXpathForTextEquals("Select All", "span") + "/../../label");
        inputIntoTextField(txtEnterSubject, DRQnASubject);
        inputIntoTextField(txtMessage, DRQnAMsg);
        clickElement(CommonUIActions.btnSubmit);
        waitUntilSelectorAppears(getXpathForTextEquals(" Reply ", "button"));
        closeQnADrawer();
    }

    public static void openQuesInQnA() {
        String txtValidateQuesTitle = "//span[@class='question-title']";
        String txtQuestionTitleInQuestion = "//span[@class='question-title']";
        clickElement(btnQnaDrawer);
        assertEquals(getElementText(lstLatestQuesInInbox), DRQnASubject);
        clickElement(lstLatestQuesInInbox);
        waitUntilSelectorAppears(txtValidateQuesTitle);
        page().waitForLoadState(LoadState.LOAD);
        assertEquals(getElementText(txtQuestionTitleInQuestion), DRQnASubject);
    }

    public static void addReplyInQnA() {
        String txtEnterReplyInQnA = "//div[@class='link-content-list']/../../div/div[3]/div";
        String btnSendReplyQnA = "//div[@class='send-reply']/button";
        String txtValidateReplyTxtInQuestionChat = "//*[@id='detailsPart']/div[6]/app-qna-reply-list/div/div[2]";
        String txtNoOfRepliesInQnA = "//div[@class='no-of-replies']";

        clickElement(getXpathForTextEquals(" Reply ", "button"));
        inputIntoTextField(txtEnterReplyInQnA, DRQnAReply);
        clickElement(btnSendReplyQnA);
        waitUntilSelectorAppears(txtNoOfRepliesInQnA);
        assertEquals(getElementText(txtValidateReplyTxtInQuestionChat), DRQnAReply);
        clickElement(btnQnaDrawer);
    }

    public static void markQuestionAsResolvedInQnA() {
        String btnMarkQuesAsResolved = "//app-qna-question-details/div[1]/div[2]/button[2]";
        int retryCount = 7;
        int i = 0;
        Locator markAsResolvedButton = page().locator(btnMarkQuesAsResolved);

        while (i < retryCount) {
            try {
                clickElement(btnQnaDrawer);// Open the QnA drawer
                clickElement(lstLatestQuesInInbox);//select the latest question
                markAsResolvedButton.waitFor(new Locator.WaitForOptions()// Wait for the "Mark as resolved" button to be visible and enabled
                        .setState(WaitForSelectorState.VISIBLE).setTimeout(10000));
                markAsResolvedButton.click();// Click the button to mark the question as resolved
                clickElement(btnQnaDrawer);// Close the QnA drawer
                break;
            } catch (Exception e) {
                LogUtils.infoLog("Attempt " + (i + 1) + " failed. Reloading the page and retrying...");
                page().reload();
                page().waitForLoadState();
                waitForSeconds(3);//Add a short wait time after reload to allow all elements to load
                i++;
            }
        }
        //Throw an exception, if the loop completes without success.
        if (i == retryCount) {
            throw new RuntimeException("Failed to mark the question as resolved after " + retryCount + " attempts.");
        }
    }

    public static void validateGuestListOptionInQnA(String qnaOption, Boolean isGuestPresentInGroup, Boolean isMultipleGuest) {
        clickElement(btnCreateNewInQnA);
        switch (qnaOption) {
            case "qna everyone":
                if (isMultipleGuest) {
                    assertTrue(isElementVisible(drdSelectGuestInQnA));
                } else {
                    assertFalse(isElementVisible(drdSelectGuestInQnA));
                }
                break;
            case "qna group":
                if (isGuestPresentInGroup && isMultipleGuest) {
                    assertTrue(isElementVisible(drdSelectGuestInQnA));
                    break;
                } else if (isMultipleGuest) {
                    int retryCount = 1;
                    do {
                        page().reload();
                        waitForSeconds(3);
                        openQnADrawer();
                        clickElement(btnCreateNewInQnA);

                    } while (isElementVisible(drdSelectGuestInQnA) && retryCount++ <= 15);
                    assertFalse(isElementVisible(drdSelectGuestInQnA));
                }
                break;
            case "qna owner":
                if (isElementVisible(drdSelectGuestInQnA)) {
                    int retryCount = 1;
                    do {
                        page().reload();
                        openQnADrawer();
                        clickElement(btnCreateNewInQnA);

                    } while (isElementVisible(drdSelectGuestInQnA) && retryCount++ <= 10);
                    assertFalse(isElementVisible(drdSelectGuestInQnA));
                }
                break;
        }
    }

    public static void validateNewBtnInFilesTab(boolean isMsEditingEnabled) {
        String drdNewInFilesTab = "//button[@id='dataroomfile-new-dropdown']";
        if (isMsEditingEnabled) {
            assertTrue(isElementVisible(drdNewInFilesTab));
        } else {
            assertFalse(isElementVisible(drdNewInFilesTab));
        }
    }

    private static String getFileName() {
        return getElementText("//a[contains(text(), '" + SharedDM.getNewFileName() + "')]");
    }

    public static void validateFileOrFolderStatusInFilesTab(boolean isFile, boolean isUnread) {
        String txtFileFolderLink = "//a[@tooltipclass='xs-hide-tooltip']";
        page().reload();
        page().waitForLoadState(LoadState.LOAD);
        if (isFile && isUnread) {
            String lnkUnreadFile = "//a[@class='with-links bold' and contains(text(),'" + getFileName() + "')]";
            if (!isElementVisible(lnkUnreadFile)) {
                int retryCount = 3;
                int i = 0;
                while (i < retryCount) {
                    try {
                        waitUntilSelectorAppears(lnkUnreadFile);
                        break;
                    } catch (Exception e) {
                        page().reload();
                        i++;
                        waitForSeconds(2);
                    }
                    page().waitForLoadState(LoadState.DOMCONTENTLOADED);
                    page().waitForLoadState(LoadState.LOAD);
                }
                waitForSelectorStateChange(txtFileFolderLink, ElementState.STABLE);
                assertTrue(isElementVisible(lnkUnreadFile));//file unread state
            }
        } else if (isFile) {
            String fileReadState = "//a[@class='with-links' and contains(text(),'" + getFileName() + "')]";//file read state
            page().waitForLoadState(LoadState.LOAD);
            page().waitForLoadState(LoadState.DOMCONTENTLOADED);
            waitForSeconds(1);
            waitForSelectorStateChange(txtFileFolderLink, ElementState.STABLE);
            assertTrue(isElementVisible(fileReadState));
        } else if (isUnread) {
            waitForSelectorStateChange(txtFileFolderLink, ElementState.STABLE);
            assertTrue(isElementVisible("//a[@class='with-links bold' and contains(text(),'" + DataRoomFolderManager.getLatestFolderName() + "')]"));
        } else {
            waitForSelectorStateChange(txtFileFolderLink, ElementState.STABLE);
            assertTrue(isElementVisible("//a[@class='with-links' and contains(text(),'" + DataRoomFolderManager.getLatestFolderName() + "')]"));
        }
    }

    public static void viewFolderInDR() {
        clickElement("//a[@class='with-links bold' and contains(text(),'" + DataRoomFolderManager.getLatestFolderName() + "')]");
        waitUntilSelectorAppears("//p[contains(text(),'" + DataRoomFolderManager.getLatestFolderName() + "')]");
    }

    public static void navigateToDRRootFromFileViewerFolderTree() {
        clickElement("//div[@class='dropdown-toggle']/i");
        clickElement("//span[contains(text(),'" + SharedDM.getDataRoomName() + "')]/../..");
    }

    public static void validateFileOrFolderStatusInTrash(boolean isFile, boolean isUnread) {
        if (isFile && isUnread) {
            assertTrue(isElementVisible("//p[contains(@class, 'with-links bold')]/span[contains(text(),'" + SharedDM.getNewFileName() + "')]"));//file unread state
        } else if (isFile) {
            assertTrue(isElementVisible("//p[contains(@class, 'with-links')]/span[contains(text(),'" + SharedDM.getNewFileName() + "')]"));//file read state
        } else if (isUnread) {
            assertTrue(isElementVisible("//p[contains(@class, 'with-links bold')]/span[contains(text(),'" + DataRoomFolderManager.getLatestFolderName() + "')]"));
        } else {
            assertTrue(isElementVisible("//p[contains(@class, 'with-links')]/span[contains(text(),'" + DataRoomFolderManager.getLatestFolderName() + "')]"));
        }
    }

    public static void goBackFromVersionHistoryPage() {
        waitUntilSelectorAppears(getXpathForContainsText("Version history", "h4"));
        clickElement("//div[@class='go-back-arrow cursor']");
        waitUntilSelectorAppears(CreateDataRoomPage.txtDRFiles);
        waitForSelectorStateChange("//*[@id='dataRoomList']/ngx-datatable/div/datatable-body//span", ElementState.HIDDEN);
    }

    /**
     * Method will receive permission type
     *
     * @param permissionType
     */
    public static void validateContextMenuOptionBasedOnPermission(String userType, String permissionType) {
        String lnkContextMenuViewOption = "(//div[@class='cdk-overlay-pane']/div/a/div)[1]";
        String lnkContextMenuViewQues = "(//div[@class='cdk-overlay-pane']/div/a/div)[contains(text(),'View Questions')]";
        String lnkContextMenuGetLink = "(//div[@class='cdk-overlay-pane']/div/a/div)[contains(text(),'Get File Link')]";
        String lnkContextMenuDownload = "(//div[@class='cdk-overlay-pane']/div/a/div)[contains(text(),'Download')]";
        String drdContextMenuDownload = "(//div[@class='cdk-overlay-pane']/div/a/div/div)[contains(text(),'Download')]";
        String drdContextMenuMoreAction = "(//div[@class='cdk-overlay-pane']/div/a/div/div)[contains(text(),'More Actions')]";
        String lnkContextMenuVersionHistory = "(//div[@class='cdk-overlay-pane']/div/a/div)[contains(text(),'Version')]";
        String lnkContextMenuTrashCan = "(//div[@class='cdk-overlay-pane']/div/a/div)[contains(text(),'Move to Trash')]";

        boolean isNonProPlanUser = !(userType.equalsIgnoreCase("proPlanAdmin") || userType.toLowerCase().contains("pro"));
        switch (permissionType) {
            case "View":
                clickElement("//div[@class='file-info-data-detail']/div/div/div/a[contains(text(),'" + getFileName() + "')]/ancestor::datatable-body-cell/../datatable-body-cell[5]/div/div/a[2]/a");
                isElementVisible(lnkContextMenuViewOption);
                assertEquals(getElementText(lnkContextMenuViewOption), "View");
                if (isNonProPlanUser) {
                    assertTrue(isElementVisible(lnkContextMenuViewQues));
                } else {
                    assertFalse(isElementVisible(lnkContextMenuViewQues));
                }
                assertTrue(isElementVisible(lnkContextMenuGetLink));
                assertFalse(isElementVisible(lnkContextMenuDownload));
                assertFalse(isElementVisible(drdContextMenuDownload));
                assertFalse(isElementVisible(drdContextMenuMoreAction));
                assertFalse(isElementVisible(lnkContextMenuTrashCan));
                assertFalse(isElementVisible(lnkContextMenuVersionHistory));
                break;
            case "Edit":
                page().waitForLoadState(LoadState.LOAD);
                clickElement("//div[@class='file-info-data-detail']/div/div/div/a[contains(text(),'" + getFileName() + "')]/ancestor::datatable-body-cell/../datatable-body-cell[5]/div/div/a/a[1]");
                assertTrue(isElementVisible(lnkContextMenuViewOption));
                if (isNonProPlanUser) {
                    assertTrue(isElementVisible(lnkContextMenuViewQues));
                } else {
                    assertFalse(isElementVisible(lnkContextMenuViewQues));
                }
                assertTrue(isElementVisible(lnkContextMenuGetLink));
                assertTrue(isElementVisible(drdContextMenuDownload));
                assertTrue(isElementVisible(drdContextMenuMoreAction));
                assertTrue(isElementVisible(lnkContextMenuTrashCan));
                assertTrue(isElementVisible(lnkContextMenuVersionHistory));
                break;
            case "Pro-->Edit":
                page().waitForLoadState(LoadState.LOAD);
                clickElement("//div[@class='file-info-data-detail']/div/div/div/a[contains(text(),'" + getFileName() + "')]/ancestor::datatable-body-cell/../datatable-body-cell[5]/div/div/a");
                assertFalse(isElementVisible(lnkContextMenuViewQues));
                assertTrue(isElementVisible(lnkContextMenuGetLink));
                assertTrue(isElementVisible(drdContextMenuDownload));
                assertTrue(isElementVisible(drdContextMenuMoreAction));
                assertTrue(isElementVisible(lnkContextMenuTrashCan));
                assertTrue(isElementVisible(lnkContextMenuVersionHistory));
                break;
            case "Download (PDF)":
                if (isNonProPlanUser) {
                    clickElement("//div[@class='file-info-data-detail']/div/div/div/a[contains(text(),'" + getFileName() + "')]/ancestor::datatable-body-cell/../datatable-body-cell[5]/div/div/a[2]/a");
                    assertTrue(isElementVisible(lnkContextMenuViewQues));
                } else {
                    clickElement("//div[@class='file-info-data-detail']/div/div/div/a[contains(text(),'" + getFileName() + "')]/ancestor::datatable-body-cell/../datatable-body-cell[5]/div/div/a");
                    assertFalse(isElementVisible(lnkContextMenuViewQues));
                }
                assertTrue(isElementVisible(lnkContextMenuViewOption));
                assertTrue(isElementVisible(lnkContextMenuGetLink));
                assertTrue(isElementVisible(lnkContextMenuDownload));
                assertFalse(isElementVisible(drdContextMenuDownload));
                assertFalse(isElementVisible(drdContextMenuMoreAction));
                assertFalse(isElementVisible(lnkContextMenuTrashCan));
                assertFalse(isElementVisible(lnkContextMenuVersionHistory));
                break;
            case "Pro-->Download (PDF)":
                clickElement("//div[@class='file-info-data-detail']/div/div/div/a[contains(text(),'" + getFileName() + "')]/ancestor::datatable-body-cell/../datatable-body-cell[5]/div/div/a");
                assertFalse(isElementVisible(lnkContextMenuViewQues));
                assertTrue(isElementVisible(lnkContextMenuViewOption));
                assertTrue(isElementVisible(lnkContextMenuGetLink));
                assertTrue(isElementVisible(lnkContextMenuDownload));
                assertFalse(isElementVisible(drdContextMenuDownload));
                assertFalse(isElementVisible(drdContextMenuMoreAction));
                assertFalse(isElementVisible(lnkContextMenuTrashCan));
                assertFalse(isElementVisible(lnkContextMenuVersionHistory));
                break;
            case "Download (Original)":
                clickElement("//div[@class='file-info-data-detail']/div/div/div/a[contains(text(),'" + getFileName() + "')]/ancestor::datatable-body-cell/../datatable-body-cell[5]/div/div/a/a");
                assertTrue(isElementVisible(lnkContextMenuViewOption));
                if (isNonProPlanUser) {
                    assertTrue(isElementVisible(lnkContextMenuViewQues));
                } else {
                    assertFalse(isElementVisible(lnkContextMenuViewQues));
                }
                assertTrue(isElementVisible(lnkContextMenuGetLink));
                assertTrue(isElementVisible(drdContextMenuDownload));
                assertFalse(isElementVisible(drdContextMenuMoreAction));
                assertFalse(isElementVisible(lnkContextMenuTrashCan));
                assertFalse(isElementVisible(lnkContextMenuVersionHistory));
                break;
            case "Print":
                if (isNonProPlanUser) {
                    clickElement("//div[@class='file-info-data-detail']/div/div/div/a[contains(text(),'" + getFileName() + "')]/ancestor::datatable-body-cell/../datatable-body-cell[5]/div/div/a[2]/a");
                    assertTrue(isElementVisible(lnkContextMenuViewQues));
                } else {
                    clickElement("//div[@class='file-info-data-detail']/div/div/div/a[contains(text(),'" + getFileName() + "')]/ancestor::datatable-body-cell/../datatable-body-cell[5]/div/div/a");
                    assertFalse(isElementVisible(lnkContextMenuViewQues));
                }
                assertTrue(isElementVisible(lnkContextMenuViewOption));
                assertTrue(isElementVisible(lnkContextMenuGetLink));
                assertFalse(isElementVisible(lnkContextMenuDownload));
                assertFalse(isElementVisible(drdContextMenuDownload));
                assertFalse(isElementVisible(drdContextMenuMoreAction));
                assertFalse(isElementVisible(lnkContextMenuTrashCan));
                assertFalse(isElementVisible(lnkContextMenuVersionHistory));
                break;
            case "Pro-->Print":
                clickElement("//div[@class='file-info-data-detail']/div/div/div/a[contains(text(),'" + getFileName() + "')]/ancestor::datatable-body-cell/../datatable-body-cell[5]/div/div/a");
                assertFalse(isElementVisible(lnkContextMenuViewQues));
                assertTrue(isElementVisible(lnkContextMenuViewOption));
                assertTrue(isElementVisible(lnkContextMenuGetLink));
                assertFalse(isElementVisible(lnkContextMenuDownload));
                assertFalse(isElementVisible(drdContextMenuDownload));
                assertFalse(isElementVisible(drdContextMenuMoreAction));
                assertFalse(isElementVisible(lnkContextMenuTrashCan));
                assertFalse(isElementVisible(lnkContextMenuVersionHistory));
                break;
            case "No Access":
                CreateDataRoomPage.validateDRAccessAsGuest("no access");
                break;
            default:
                fail("permission type is not found: " + permissionType);
        }
    }

    public static void validateEncryptedStorageError(String errorPage, Boolean isAdmin) {
        switch (errorPage.toLowerCase()) {
            case "files tab":
                waitUntilSelectorAppears(getXpathForTextEquals("Notify guests after uploading your files?", "h4"));
                clickElement("//button[@class='btn digify-social-btn']");
                waitUntilSelectorAppears("//p[contains(@class, 'progress-error')]");
                assertEquals(getElementText("//p[contains(@class, 'progress-error')]"), "One or more files failed to upload.");
                if (isAdmin) {
                    assertEquals(getElementText("(//p[contains(@class,'file-element-title')])[2]/../p[2]"), "Encrypted storage quota exceeded. Add more encrypted storage quota");
                } else {
                    assertEquals(getElementText("//p[contains(@class, 'file-element-sub error')]"), "Encrypted storage quota exceeded. Please contact your admin to add more encrypted storage quota to your plan.");
                    clickElement(getXpathForTextEquals(" Close ", "span"));
                }
                break;
            case "replace file modal":
                if (isAdmin) {
                    assertEquals(getElementText("//app-premium-upgrade-modal/div[1]/h4"), "No encrypted storage left");
                    assertEquals(getElementText("//app-premium-upgrade-modal/div[2]/div[1]/p"), "To create a new version, please add more encrypted storage quota to your plan.");
                } else {
                    assertEquals(getElementText("//app-premium-upgrade-modal/div[1]/h4"), "No encrypted storage left");
                    assertEquals(getElementText("//app-premium-upgrade-modal/div[2]/div[1]/p"), "To create a new version, please contact your admin to add more encrypted storage quota to your plan.");
                    clickElement("//app-premium-upgrade-modal/div[2]/div[2]/div/button[1]");
                    dismissReplaceFileModal();
                }
                break;
            case "copy file modal":
                if (isAdmin) {
                    assertEquals(getElementText("//span[contains(@class, 'digify-error-text')]"), "Encrypted storage quota exceeded. Add more encrypted storage quota");
                } else {
                    assertEquals(getElementText("//span[contains(@class, 'digify-error-text')]"), "Encrypted storage quota exceeded. Please contact your admin to add more encrypted storage quota to your plan.");
                    dismissReplaceFileModal();
                }
                break;
            default:
                fail("Modal not found: " + errorPage);
        }
    }

    public static void clickOnAddEncryptQuotaLink(String errorPage) {
        switch (errorPage.toLowerCase()) {
            case "files tab":
                assertTrue(isElementVisible("(//p[contains(@class,'file-element-title')])[2]/../p[2]/a"));
                clickAndSwitchToNewTab("(//p[contains(@class,'file-element-title')])[2]/../p[2]/a");
                waitForURLContaining("/pricing/#/review");
                PricingPage.navigateToPricingCheckoutPageAsExistingUser();
                break;
            case "replace file modal":
                clickAndSwitchToNewTab("//app-premium-upgrade-modal/div[2]/div[2]//button");
                waitForURLContaining("/pricing/#/review");
                break;
            case "copy file modal":
                assertTrue(isElementVisible("//span[contains(@class, 'digify-error-text')]/a"));
                clickAndSwitchToNewTab("//span[contains(@class, 'digify-error-text')]/a");
                waitForURLContaining("/pricing/#/review");
                PricingPage.navigateToPricingCheckoutPageAsExistingUser();
                break;
            default:
                fail("option type does not matched for encrypting storage link: " + errorPage);
        }
    }

    public static void validateSubscriptionPaywallInDR(String paywallType) {
        String txtSubEnd = "//app-premium-upgrade-modal/div[1]/h4";
        String btnMayBeLater = "//app-premium-upgrade-modal/div[2]/div[2]/div/button[1]";
        String txtHeading = "(//div[@class='sub-dataroom-layout has-error']//p[1])[1]";
        String txtSubHeading = "//div[@class='sub-dataroom-layout has-error']//p[2]";
        String btnGoToDRList = "//div[@class='sub-dataroom-layout has-error']//button";

        switch (paywallType) {
            case "subscription ended":
                assertEquals(getElementText(txtSubEnd), "Subscription ended");
                assertEquals(getElementText("//app-premium-upgrade-modal/div[2]/div[1]/p"), "To allow your guests to access this data room, please contact your admin to upgrade your plan.");
                clickElement(btnMayBeLater);
                break;
            case "SUB_EXP":
                assertEquals(getElementText("//page-container/div//div[2]/p[1]"), "This data room is not available.");
                assertEquals(getElementText("//page-container/div//div[2]/p[2]"), "SUB_EXP");
                clickElement("//page-container//div[2]/button");
                break;
            case "contact your administrator to upgrade plan":
                assertEquals(getElementText("//div[@class='no-dataroom-block']/div[1]/p[1]"), "Create a Data Room in Minutes");
                assertEquals(getElementText("//div[@class='no-dataroom-block']/div[1]/p[2]"), "A secure repository for protecting and tracking your files.");
                assertEquals(getElementText("//div[contains(@class,'green-check-list')][1]//p[1]"), "Protect Documents at Scale");
                assertEquals(getElementText("//div[contains(@class,'green-check-list')][1]//p[2]"), "Upload and organize files in bulk with automated indexing for easy organization.");
                assertEquals(getElementText("//div[contains(@class,'green-check-list')][2]//p[1]"), "Simplify and Automate Processes");
                assertEquals(getElementText("//div[contains(@class,'green-check-list')][2]//p[2]"), "Require guests to agree to terms before viewing and automatically watermark files with their email addresses.");
                assertEquals(getElementText("//div[contains(@class,'green-check-list')][3]//p[1]"), "Track User Activity");
                assertEquals(getElementText("//div[contains(@class,'green-check-list')][3]//p[2]"), "Receive notifications when your files are initially viewed and review analytics for each guest.");
                assertEquals(getElementText("//div[contains(@class,'card-body')][1]//p[contains(@class, 'no-margin')]"), "Contact your administrator to upgrade your plan.");
                assertEquals(getElementText("//div[contains(@class,'card-body')][1]//a[contains(text(), 'Learn more about data rooms')]"), "Learn more about data rooms");
                break;
            case "subscription ended upgrade":
                assertEquals(getElementText("//app-premium-upgrade-modal/div[1]/h4"), "Subscription ended");
                assertEquals(getElementText("//app-premium-upgrade-modal//div[2]//p"), "To allow your guests to access this data room, please upgrade your plan.");
                assertEquals(getElementText("//app-premium-upgrade-modal//button[2]"), "Upgrade account");
                clickElement(btnMayBeLater);
                break;
            case "no access folder":
                assertEquals(getElementText(txtHeading), "You do not have permission here");
                assertEquals(getElementText(txtSubHeading), "Please check with the owner if you need access.");
                clickElement(btnGoToDRList);
                break;
            case "no access dr":
                assertEquals(getElementText(txtHeading), "Looking for something?");
                assertEquals(getElementText(txtSubHeading), "To access a specific file or folder, please check with the owner for the direct link.");
                clickElement(btnGoToDRList);
                break;
            case "expired dr":
                assertEquals(getElementText(txtHeading), "This data room is not available.");
                assertEquals(getElementText("(//div[@class='sub-dataroom-layout has-error']//p[1])[2]"), "Please check with the owner if you need access. Learn more");
                assertEquals(getElementText(txtSubHeading), "ITM_EXP");
                clickElement(btnGoToDRList);
                break;
            case "subscription expired dr":
                assertEquals(getElementText(txtHeading), "This data room is not available.");
                assertEquals(getElementText("(//div[@class='sub-dataroom-layout has-error']//p[1])[2]"), "Please check with the owner if you need access. Learn more");
                assertEquals(getElementText(txtSubHeading), "SUB_EXP");
                clickElement(btnGoToDRList);
                break;
            default:
                fail("Error message is invalid for: " + paywallType);
        }
    }

    public static void dismissReplaceFileModal() {
        clickElement(btnDismissModal);
    }

    public static void expandUploadFileDropdown() {
        clickElement("//*[@id='dataroom-upload-dropdown']/i");
    }

    public static void copyAndSaveDRFileORFolderLinkInMemory() {
        CommonUIActions.selectFirstCheckbox();
        clickElement(getXpathForContainsText("Link", "button"));
        SharedDM.setSentDRFileOrFolderLink(getElementText(SendFilesPage.lnkFileLink));
        LogUtils.infoLog("Dataroom file/folder link is: " + SharedDM.getSentDRFileOrFolderLink());
        clickElement(CommonUIActions.btnCloseModal);
    }

    public static void viewLatestFolderInDR() {
        clickElement("//a[@class='with-links bold' and contains(text(),'" + DataRoomFolderManager.getLatestFolderName() + "')]");
    }

    public static void selectLatestFolder() {
        clickElement("//a[contains(text(), '" + DataRoomFolderManager.getLatestFolderName() + "')]/../../../..");
    }

    public static void selectPreviousFolder() {
        clickElement("//a[contains(text(), '" + DataRoomFolderManager.getPreviousFolderName() + "')]/../../../..");
    }

    public static void validateTextAndClickOnExportButton(String sectionText) {
        switch (sectionText) {
            case "file index":
                assertEquals(getElementText("//p[@class='step-heading-alt no-margin all-caps']"), "Files");
                assertEquals(getElementText("//p[contains(text(),'File Index')]"), "File Index");
                assertEquals(getElementText("//app-data-room-exports//div[3]/div[2]/p[2]"), "Download a list of all files and folders in the data room in CSV format.");
                clickElement("//app-data-room-exports//div[3]/div[2]/button");
                break;
            case "data room":
                assertEquals(getElementText("//app-data-room-exports//div[3]/div[3]/p[1]"), "Data Room Files");
                assertEquals(getElementText("//app-data-room-exports//div[3]/div[3]/p[2]"), "Download all files in the data room.");
                clickElement("//app-data-room-exports//div[3]/div[3]/button");
                break;
            case "activity log":
                assertEquals(getElementText("//p[@class='step-heading-alt no-margin']"), "USERS");
                assertEquals(getElementText("//p[contains(text(),'Activity Log')]"), "Activity Log");
                assertEquals(getElementText("//app-data-room-exports//div[4]/div[2]/p[2]"), "Download a spreadsheet of all activities in the data room.");
                clickElement("//app-data-room-exports//div[4]/div[2]/button");
                break;
            case "guest list":
                assertEquals(getElementText("//p[contains(text(),'Guest List')]"), "Guest List");
                assertEquals(getElementText("//app-data-room-exports//div[4]/div[3]/p[2]"), "Download a list of guests invited to the data room and their status.");
                clickElement("//app-data-room-exports//div[4]/div[3]/button");
                break;
            default:
                fail("No export option found on export dr page" + sectionText);
        }
    }

    public static void validateExportModalOnDRExportPage(String modalType) {
        String txtHeadingExportModal = "//h4[@class='bold text-left dr-export-header']";
        String txtSubHeadingExportModal = "//p[@class='text-left']";
        switch (modalType) {
            case "export file index":
                assertEquals(getElementText(txtHeadingExportModal), "Exporting file index");
                assertEquals(getElementText(txtSubHeadingExportModal), "This may take up to 10 minutes, depending on your data room size. Once the export is complete, you'll receive an email with the file index.");
                break;
            case "export data room":
                assertEquals(getElementText(txtHeadingExportModal), "Exporting data room files");
                assertEquals(getElementText(txtSubHeadingExportModal), "This may take up to 10 minutes, depending on your data room size. Once the export is complete, you'll receive an email with a download link.");
                break;
            case "export activity log":
                assertEquals(getElementText(txtHeadingExportModal), "Exporting activity log");
                assertEquals(getElementText(txtSubHeadingExportModal), "This may take up to 10 minutes, depending on your data room size. Once the export is complete, you'll receive an email with the activity log.");
                break;
            case "export guest list":
                assertEquals(getElementText(txtHeadingExportModal), "Exporting guest list");
                assertEquals(getElementText(txtSubHeadingExportModal), "This may take up to 10 minutes, depending on the number of guests. Once the export is complete, you'll receive an email with the guest list.");
                break;
            default:
                fail("No modal exists" + modalType);
        }
        clickElement("//div[@class='modal-body']//div/button");
    }

    public static void selectMenuOptionFromMoreMenu(String moreMenuOption) {
        clickElement("//*[@id='quick-access-dropdown']");
        switch (moreMenuOption) {
            case "export":
                clickElement("//div[@class='dropdown']//a//div[contains(text(), 'Export ')]");
                break;
            case "export file index":
                clickElement("//div[@class='dropdown']//a//div[contains(text(), ' Export File Index ')]");
                break;
            case "data room link":
                clickElement("//div[@aria-labelledby='quick-access-dropdown']/a[2]");
                break;
            default:
                fail("No option found from more menu: " + moreMenuOption);
        }
    }

    public static void selectGoToDRList() {
        clickElement(CreateDataRoomPage.drdDRUserInfo);
        clickElement(DataRoomGuestsPage.btnGoToDrList);
        ManageDataRoomPage.waitForManageDRToLoad();
    }

    public static void fileUploadInDR(String fileName) throws IOException {
        CommonUIActions.uploadFile(
                CommonUIActions.getFileList(fileName, "src/main/resources/testFiles/"));
        DataRoomFilesPage.waitForFileUploadToCompleteInDR();
    }

    public static void copyAndSaveDataRoomLink() {
        DataRoomFilesPage.selectMenuOptionFromMoreMenu("data room link");
        SharedDM.setSentDataRoomLink(getElementText(SendFilesPage.lnkFileLink));
        LogUtils.infoLog("Link of dataroom is: " + SharedDM.getSentDataRoomLink());
        clickElement(CommonUIActions.btnCloseModal);
    }

    public static void verifyAdvanceSettOptionInFilesTab(String isAdvanceSettingOptionPresent) {
        String txtIndex = "(//span[@class='datatable-header-cell-label draggable'])[2]";
        String txtFileIndexNoOnFilesPage = "(//div[@class='overflow-hidden']/p)[1]";
        String txtDateOnFilesPage = getXpathForTextEquals("Date", "span");
        String txtModifiedDateOnFilesPage = "//div[contains(@class,'datatable-body-cell-label')]/div/p";
        switch (isAdvanceSettingOptionPresent.toLowerCase()) {
            case "file index should not appear":
                page().waitForLoadState(LoadState.DOMCONTENTLOADED);
                page().reload();
                page().waitForLoadState(LoadState.DOMCONTENTLOADED);
                assertFalse(isElementVisible(txtIndex));
                assertFalse(isElementVisible(txtFileIndexNoOnFilesPage));
                break;
            case "file index should appear":
                page().waitForLoadState(LoadState.DOMCONTENTLOADED);
                page().reload();
                page().waitForLoadState(LoadState.DOMCONTENTLOADED);
                assertTrue(isElementVisible(txtIndex));
                assertTrue(isElementVisible(txtFileIndexNoOnFilesPage));
                break;
            case "modified date should not appear":
                page().waitForLoadState(LoadState.DOMCONTENTLOADED);
                assertFalse(isElementVisible(txtModifiedDateOnFilesPage));
                assertFalse(isElementVisible(txtDateOnFilesPage));
                break;
            case "modified date should appear":
                page().waitForLoadState(LoadState.DOMCONTENTLOADED);
                assertTrue(isElementVisible(txtModifiedDateOnFilesPage));
                assertTrue(isElementVisible(txtDateOnFilesPage));
                break;
            default:
                fail("Invalid option for selecting advance settings options: " + isAdvanceSettingOptionPresent);
        }
    }

    public static void getContextMenuOptions(String optionType) {
        clickElement("(//a[@tooltipclass='xs-hide-tooltip']/a)[1]");
        String btnMoreActions = "(//div[@class='context-sub-menu'])[2]";
        switch (optionType.toLowerCase()) {
            case "view":
                clickAndSwitchToNewTab("//div[@class='cdk-overlay-pane']/div/a/div/i[contains(@class,'fa-eye far')]/../..");
                FileViewer.fileLoadInFileViewer();
                break;
            case "view questions":
                clickElement("(//div[@class='cdk-overlay-pane']/div/a/div/i[contains(@class,'fa-comment-alt-lines')]/../..)[1]");
                waitUntilSelectorAppears(btnCreateNewInQnA);
                page().waitForLoadState(LoadState.LOAD);
                assertTrue(isElementVisible(btnInboxInQnA));
                closeQnADrawer();
                break;
            case "manage access":
                clickElement("//div[@class='cdk-overlay-pane']/div/a/div/i[contains(@class,'fa-users-medical')]/../..");
                GranularPermissionPage.validateManageAccessPageForFile();
                break;
            case "manage access is not visible":
                assertFalse(isElementVisible("//div[@class='cdk-overlay-pane']/div/a/div/i[contains(@class,'fa-users-medical')]/../.."));
                break;
            case "analytics":
                clickElement("//div[@class='cdk-overlay-pane']/div/a/div/i[contains(@class,'fa-chart-bar')]/../..");
                waitUntilSelectorAppears("//div[@class='page-title-text']");
                assertTrue(isElementVisible("//div[@class='page-title-text']"));
                break;
            case "analytics is not visible":
                assertFalse(isElementVisible("//div[@class='cdk-overlay-pane']/div/a/div/i[contains(@class,'fa-chart-bar')]/../.."));
                break;
            case "version history":
                clickElement("//div[@class='cdk-overlay-pane']/div/a/div/i[contains(@class,'fa-history far')]/../..");
                waitUntilSelectorAppears("//h4[@class='color-almost-dark normal']");
                assertTrue(isElementVisible("//h4[@class='color-almost-dark normal']"));
                break;
            case "move to trash":
                clickElement("(//div[@class='cdk-overlay-pane']/div/a/div/i[contains(@class,'fa-trash')]/../..)[1]");
                waitUntilSelectorAppears(CommonUIActions.txtCommonModalTitle);
                assertEquals(getElementText(CommonUIActions.txtCommonModalTitle), "Move file/folder to trash?");
                break;
            case "link":
                clickElement("//div[@class='cdk-overlay-pane']/div/a/div/i[contains(@class,'fa-link far')]/../..");
                assertEquals(getElementText("//span[@class='sub-text']"),
                        "Use this link to point your data room guests directly to this file.");
                page().waitForLoadState(LoadState.LOAD);
                assertEquals(getElementText("//app-copy-link//h4"), "Get file link");
                CommonUIActions.manageLinkModal();
                break;
            case "edit--> get file link":
                clickElement("//div[@class='cdk-overlay-pane']/div/a/div/i[contains(@class,'fa-link far')]/../..");
                assertEquals(getElementText("//span[@class='sub-text']"),
                        "This link can be accessed by data room guests. Only data room owners can invite new guests.");
                page().waitForLoadState(LoadState.LOAD);
                assertEquals(getElementText("//app-copy-link//h4"), "Get file link");
                CommonUIActions.manageLinkModal();
                break;
            case "more actions-->change name":
                clickElement(btnMoreActions);
                clickElement("(//ul[@class='submenu'])[2]/li/div/i[contains(@class,'dropdown-item-icon fa-pen far')]/..");
                waitUntilSelectorAppears(CommonUIActions.txtCommonModalTitle);
                assertEquals(getElementText(CommonUIActions.txtCommonModalTitle), "Change name & description");
                break;
            case "more actions-->replace file":
                clickElement(btnMoreActions);
                clickElement("(//ul[@class='submenu'])[2]/li/div/i[contains(@class,'dropdown-item-icon fa-redo far')]/..");
                waitUntilSelectorAppears("//div[@class='modal-content']/app-replace-file/div/h4");
                break;
            case "more actions-->rearrange":
                clickElement(btnMoreActions);
                clickElement("(//ul[@class='submenu'])[2]/li/div/i[contains(@class,'dropdown-item-icon fa-sort-alt far')]/..");
                waitUntilSelectorAppears("//div[@class='modal-content']/app-data-room-reorder-index/div/div/h4");
                assertEquals(getElementText("//div[@class='modal-content']/app-data-room-reorder-index/div/div/h4"), "Rearrange");
                break;
            case "more actions-->move/copy":
                clickElement(btnMoreActions);
                clickElement("(//ul[@class='submenu'])[2]/li/div/i[contains(@class,'dropdown-item-icon fa-folders far')]/..");
                waitUntilSelectorAppears(getXpathForContainsText("Move/Copy", "h4"));
                break;
            case "download-->download_pdf":
                clickElement("(//div[@class='context-sub-menu'])[1]");
                clickElement("((//ul[@class='submenu'])[1]/li/div/i[contains(@class,'dropdown-item-icon')]/..)[1]");
                validateToastMsg(CommonUIActions.txtCommonToastMsg, "Preparing to download.");
                CommonUIActions.validateAndCloseDownloadPopupBlockerModal();
                break;
            case "download-->download_original":
                clickElement("(//div[@class='context-sub-menu'])[1]");
                clickElement("((//ul[@class='submenu'])[1]/li/div/i[contains(@class,'dropdown-item-icon')]/..)[2]");
                validateToastMsg(CommonUIActions.txtCommonToastMsg, "Preparing to download.");
                if (isElementVisible("//*[@id='downloadBrowserBlockerModal']/h4")) {
                    CommonUIActions.validateAndCloseDownloadPopupBlockerModal();
                } else {
                    LogUtils.infoLog("Download browser blocker modal does not appear");
                }
                break;
            default:
                fail("Invalid optionType: " + optionType);
        }
    }

    public static void closeModalInFilesTab() {
        clickElement("//button[@type='button']/span");
    }

    public static void navigateToFilesByClickingBackBtn() {
        clickElement("//i[contains(@class,'far fa-chevron')]/..");
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears(getXpathForContainsText("Files", "p"));
    }

    public static void visitDRPreAuthUrl(Boolean isAboutPageEnabled, boolean isLinkExpired) {
        visit(SharedDM.preAuthUrl.get(), false);
        page().waitForLoadState(LoadState.LOAD);
        page().waitForLoadState(LoadState.DOMCONTENTLOADED);
        if (isLinkExpired) {
            waitForSeconds(2);
            LogUtils.infoLog("Pre-auth file link is expired");
            return;
        }
        waitUntilSelectorAppears(CreateDataRoomPage.txtDRFiles);
        assertTrue(isElementVisible(CreateDataRoomPage.txtDRFiles));
        if (isAboutPageEnabled) {
            assertTrue(isElementVisible(CreateDataRoomPage.lnkAboutTabInDR));
        } else {
            LogUtils.infoLog("Data room about page is disabled by data room owner");
        }
    }

    public static void getBlankDR() {
        String imgDrEmptyLogo = "//div[@class='no-dataroom-block']/div/img";
        String txtEmptyDrTitle = "(//div[@class='no-dataroom-block']/div/p)[1]";
        waitUntilSelectorAppears(imgDrEmptyLogo);
        assertTrue(isElementVisible(imgDrEmptyLogo));
        assertTrue(isElementVisible(txtEmptyDrTitle));
        assertEquals(getElementText(txtEmptyDrTitle), "This data room is empty");
    }

    public static void revisitDRPreAuthUrl(boolean isBrowserLockEnabled, boolean isAboutPageEnabled) {
        if (isBrowserLockEnabled) {
            openNewWindow();
            visit(SharedDM.preAuthUrl.get(), false);
            page().waitForLoadState(LoadState.LOAD);
        } else {
            openNewWindow();
            visitDRPreAuthUrl(isAboutPageEnabled, false);
        }
    }

    public static void validateFilesTab() {
        waitForSeconds(5);
        waitUntilSelectorAppears(txtDRFiles);
        assertTrue(isElementVisible(txtDRFiles));
    }
}
