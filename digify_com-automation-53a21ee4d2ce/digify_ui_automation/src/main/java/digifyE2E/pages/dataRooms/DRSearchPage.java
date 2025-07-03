package digifyE2E.pages.dataRooms;

import com.microsoft.playwright.options.LoadState;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.FileViewer;
import digifyE2E.testDataManager.SharedDM;

import java.util.List;

import static digifyE2E.pages.dataRooms.DataRoomFilesPage.*;
import static org.testng.Assert.*;

public class DRSearchPage extends CommonUIActions {
    public static String txtSearchResultText = "//div[@class='search-item overflow-ellipsis']";
    public static String btnSearch = "//button[@id='search-btn']";

    public static void navigateToDRSearchPage() {
        clickElement("//button[@class='btn digify-btn-secondary search-btn']");
        assertEquals(getElementText("//h4[@class='color-extreme-grey normal']"), "Search in \"" + SharedDM.getDataRoomName() + "\"");
    }

    public static void searchUsingFileName() {
        inputIntoTextField("//input[@type='search']", SharedDM.getNewFileName());
        keyPress("Enter");
    }

    public static void validateFileInSearchResult(String fileExtension) {
        assertEquals(getElementText(txtSearchResultText), "Showing results for \"" + SharedDM.getNewFileName() + "\"");
        assertEquals(getElementText("//datatable-row-wrapper[1]//datatable-body-cell[2]//div[@class='file-info-data-detail']//a//span[2]"), SharedDM.getNewFileName() + fileExtension);
    }

    public static void applyFilterType(List<String> filterType) {
        filterType.forEach(filterOption -> {
            clickElement("//*[@id='type-filter']/pg-select");
            switch (filterOption) {
                case "pdf":
                    clickElement("//div[@class='cdk-overlay-pane']//ul/li[2]");
                    break;
                case "documents":
                    clickElement("//div[@class='cdk-overlay-pane']//ul/li[3]");
                    break;
                case "spreadsheets":
                    clickElement("//div[@class='cdk-overlay-pane']//ul/li[4]");
                    break;
                case "presentations":
                    clickElement("//div[@class='cdk-overlay-pane']//ul/li[5]");
                    break;
                case "images":
                    clickElement("//div[@class='cdk-overlay-pane']//ul/li[6]");
                    break;
                case "audio files":
                    clickElement("//div[@class='cdk-overlay-pane']//ul/li[7]");
                    break;
                case "videos":
                    clickElement("//div[@class='cdk-overlay-pane']//ul/li[8]");
                    break;
                case "text files":
                    clickElement("//div[@class='cdk-overlay-pane']//ul/li[9]");
                    break;
                default:
                    fail("invalid filter type" + filterType);
            }
            clickOnSearchButton();
            assertEquals(getElementText(txtSearchResultText), "Showing results");
            assertEquals(getElementText("//datatable-row-wrapper[1]//datatable-body-cell[2]//div[@class='file-info-data-detail']//div[@class='hide-datatable-xs']//div[@class='sub-text']"), "Files");
        });
    }

    public static void clickOnSearchButton() {
        clickElement(btnSearch);
    }

    public static void validateSearchResult(String noOfFiles) {
        assertEquals(getElementText(txtSearchResultText), "Showing results");
        CommonUIActions.selectAllCheckBox("//input[@class='datatable-checkbox']");
        assertEquals(getElementText("//div[@class='selected-row']"), noOfFiles + " Selected");
    }

    public static void validateDRSearchFloatingContextMenuOption(List<String> menuOption) {
        String btnMore = "(//button//i[@class='fa-ellipsis-h far'])[2]";
        String btnClose = "//button[@aria-label='Close']";
        String btnMoreActions = "//div[contains(text(),' More Actions ')]";
        String btnContextMenu = "//a[@class='option-dropdown-toggle padding-5']";
        String btnDownload = "//div[@class='context-sub-menu'][contains(text(),' Download ')]";
        menuOption.forEach(menu -> {
            switch (menu) {
                case "parent folder":
                    clickAndSwitchToNewTab("//button//i[@class='fa-folder far']");
                    validateMenuOptParentFolder();
                    break;
                case "go to parent folder":
                    clickElement(btnContextMenu);
                    clickAndSwitchToNewTab("//div[contains(text(),' Go to Parent Folder ')]");
                    validateMenuOptParentFolder();
                    break;
                case "view":
                    clickAndSwitchToNewTab("//button//i[@class='fa-eye far']");
                    FileViewer.validateFileName();
                    break;
                case "cm->view":
                    clickElement(btnContextMenu);
                    clickAndSwitchToNewTab("//div[contains(text(),'View')]");
                    FileViewer.validateFileName();
                    break;
                case "edit":
                    clickAndSwitchToNewTab("//button//i[@class='fa-file-edit far']");
                    break;
                case "cm->edit":
                    clickElement(btnContextMenu);
                    clickAndSwitchToNewTab("//div[contains(text(),' Edit ')]");
                    break;
                case "download":
                    clickElement("//button//i[@class='fa-arrow-to-bottom far']");
                    CommonUIActions.validateAndCloseDownloadPopupBlockerModal();
                    break;
                case "question":
                    clickElement("//button//i[@class='fa-comment-alt-lines far']");
                    validateMenuOptQuestion();
                    break;
                case "view questions":
                    clickElement(btnContextMenu);
                    clickElement("//div[contains(text(),' View Questions')]");
                    validateMenuOptQuestion();
                    break;
                case "link":
                    clickElement("//button//i[@class='fa-link far']");
                    validateMenuOptFileLink();
                    break;
                case "get file link":
                    clickElement(btnContextMenu);
                    clickElement("//div[contains(text(),' Get File Link ')]");
                    validateMenuOptFileLink();
                    break;
                case "move or copy":
                    clickElement("//button//i[@class='fa-folders far']");
                    validateMenuOptMoveCopy(btnClose);
                    break;
                case "cm->move or copy":
                    clickElement(btnContextMenu);
                    clickElement(btnMoreActions);
                    clickElement("//ul[@class='submenu']//li[3]//div[contains(text(),'Move/Copy')]");
                    validateMenuOptMoveCopy(btnClose);
                    break;
                case "trash":
                    clickElement("//button//i[@class='fa-trash far']");
                    validateMenuOptTrash();
                    break;
                case "cm->move to trash":
                    clickElement(btnContextMenu);
                    clickElement(btnMoreActions);
                    clickElement("//div[contains(text(),' Move to Trash ')]");
                    validateMenuOptTrash();
                    break;
                case "fm->change name and description":
                    clickElement(btnMore);
                    clickElement("//*[@class='cdk-overlay-pane']/div/a[1]");
                    validateMenuOptFileNameDescription(btnClose);
                    break;
                case "cm->change name and description":
                    clickElement(btnContextMenu);
                    clickElement(btnMoreActions);
                    clickElement("//ul[@class='submenu']//li[1]//div[contains(text(),'Change Name & Description')]");
                    validateMenuOptFileNameDescription(btnClose);
                    break;
                case "fm->replace file":
                    clickElement(btnMore);
                    clickElement("//*[@class='cdk-overlay-pane']/div/a[2]");
                    validateMenuOptReplaceFile(btnClose);
                    break;
                case "cm->replace file":
                    clickElement(btnContextMenu);
                    clickElement(btnMoreActions);
                    clickElement("//ul[@class='submenu']//li[2]//div[contains(text(),'Replace file')]");
                    validateMenuOptReplaceFile(btnClose);
                    break;
                case "fm->manage access":
                    clickElement(btnMore);
                    validateMenuOptManageAccess();
                    break;
                case "cm->manage access":
                    clickElement(btnContextMenu);
                    validateMenuOptManageAccess();
                    break;
                case "analytics":
                    clickElement(btnMore);
                    validateMenuOptAnalytics();
                    break;
                case "cm->analytics":
                    clickElement(btnContextMenu);
                    validateMenuOptAnalytics();
                    break;
                case "cm->version history":
                    clickElement(btnContextMenu);
                    validateMenuOptVersionHistory();
                    break;
                case "version history":
                    clickElement(btnMore);
                    validateMenuOptVersionHistory();
                    break;
                case "cm->download pdf":
                    clickElement(btnContextMenu);
                    clickElement(btnDownload);
                    clickElement("//ul[@class='submenu']//div[contains(text(),'Download (PDF)')]");
                    CommonUIActions.validateAndCloseDownloadPopupBlockerModal();
                    break;
                case "cm->download original":
                    clickElement(btnContextMenu);
                    clickElement(btnDownload);
                    clickElement("//ul[@class='submenu']//div[contains(text(),' Download (Original) ')]");
                    CommonUIActions.validateAndCloseDownloadPopupBlockerModal();
                    break;

                default:
                    fail("invalid menu option: " + menuOption);
            }
        });
    }

    public static void validateMenuOptParentFolder() {
        assertEquals(getElementText("//app-data-room-doc-breadcrumb//p"), "Files");
    }

    public static void validateMenuOptQuestion() {
        waitUntilSelectorAppears(btnCreateNewInQnA);
        page().waitForLoadState(LoadState.LOAD);
        assertTrue(isElementVisible(btnInboxInQnA));
        clickElement(btnQnaDrawer);
    }

    public static void validateMenuOptFileLink() {
        assertEquals(getElementText("//span[@class='sub-text']"),
                "Use this link to point your data room guests directly to this file.");
        page().waitForLoadState(LoadState.LOAD);
        assertEquals(getElementText("//app-copy-link//h4"), "Get file link");
        CommonUIActions.manageLinkModal();
    }

    public static void validateMenuOptTrash() {
        waitUntilSelectorAppears(CommonUIActions.txtCommonModalTitle);
        assertEquals(getElementText(CommonUIActions.txtCommonModalTitle), "Move file/folder to trash?");
        clickElement(CommonUIActions.btnCommonModalCancel);
    }

    public static void validateMenuOptFileNameDescription(String btnClose) {
        waitUntilSelectorAppears(CommonUIActions.txtCommonModalTitle);
        assertEquals(getElementText(CommonUIActions.txtCommonModalTitle), "Change name & description");
        clickElement(btnClose);
    }

    public static void validateMenuOptAnalytics() {
        clickAndSwitchToNewTab("//div[contains(text(),' Analytics ')]");
        waitUntilSelectorAppears("//div[@class='page-title-text']");
        assertTrue(isElementVisible("//div[@class='page-title-text']"));
    }

    public static void validateMenuOptVersionHistory() {
        clickAndSwitchToNewTab("//div[contains(text(),' Version History ')]");
        waitUntilSelectorAppears("//div[contains(@class,'page-title-text')]");
        page().waitForLoadState(LoadState.DOMCONTENTLOADED);
        page().waitForLoadState(LoadState.LOAD);
    }

    public static void validateMenuOptReplaceFile(String btnClose) {
        waitUntilSelectorAppears("//div[@class='modal-content']/app-replace-file/div/h4");
        clickElement(btnClose);
    }

    public static void validateMenuOptMoveCopy(String btnClose) {
        waitUntilSelectorAppears(getXpathForContainsText("Move/Copy", "h4"));
        clickElement(btnClose);
    }

    public static void validateMenuOptManageAccess() {
        clickAndSwitchToNewTab("//div[contains(text(),' Manage access ')]");
        assertEquals(getElementText("//form/pg-select//div[2]"), "View by folders/files");
    }
}
