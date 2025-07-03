package digifyE2E.pages.documentSecurity;

import com.microsoft.playwright.options.ElementState;
import com.microsoft.playwright.options.LoadState;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.FileViewer;
import digifyE2E.testDataManager.SharedDM;
import digifyE2E.utils.LogUtils;

import static org.testng.Assert.*;

public class ViewReceivedFilesPage extends CommonUIActions {

    public static final String txtViewReceivedFileHeading = "//div[@class='page-title-header new-toolbar']/div//div/h4";
    public static final String btnRemove = getXpathForContainsText("Remove", "span");
    public static final String ddSortByFilter =
            getXpathForContainsText("View Received Files", "h4") + "/../following-sibling::node()/pg-select/div";

    private static void getFilterOptionInVRFPage(String sortByFilterType) {
        String ddSelectedSortByFilter =
                getXpathForContainsText("View Received Files", "h4") + "/../following-sibling::node()/pg-select/div/div/div[2]";
        waitUntilSelectorAppears(ddSortByFilter);
        CommonUIActions.selectValueFromDropdownList(ddSortByFilter, SendFilesPage.ddElementList, sortByFilterType);
        waitUntilSelectorAppears(ddSelectedSortByFilter);
        assertTrue(isElementVisible(ddSelectedSortByFilter));
        assertEquals(getElementText(ddSelectedSortByFilter), "Sort by: " + sortByFilterType);
    }

    public static void setFilterOptionInSortBy(String filterTypeInSortByFilter) {
        getFilterOptionInVRFPage(filterTypeInSortByFilter);
    }

    public static void selectMenuOption(String optionType, boolean isCM, boolean acceptTOA, boolean isHiddenDownload) {
        if (isCM) {
            clickElement(CommonUIActions.drdMoreOptionsCM);
        }
        switch (optionType.toLowerCase()) {
            case "view":
                handleViewOption(isCM);
            case "download":
                handleDownloadOption(isCM, acceptTOA, isHiddenDownload);
                break;
            case "link":
                handleLinkOption(isCM);
                break;
            case "remove":
                handleRemoveOption(isCM);
                break;
            default:
                fail("Unsupported option type: " + optionType);
        }
    }

    private static void handleRemoveOption(boolean isCM) {
        boolean isRemoveVisible = isCM
                ? isElementVisible("//div[@class='dropdown']/div/a/div/i[contains(@class,'fa-minus')]/../..")
                : isElementVisible("//div[@class='text-right']//button/i[contains(@class,'fa-minus-circle far')]/..");

        if (isRemoveVisible) {
            if (isCM) {
                clickElement("//div[@class='dropdown']/div/a/div/i[contains(@class,'fa-minus')]/../..");
            } else {
                clickElement("//div[@class='text-right']//button/i[contains(@class,'fa-minus-circle far')]/..");
            }
            waitUntilSelectorAppears(CommonUIActions.commonModalHeader);
            assertEquals(getElementText(CommonUIActions.commonModalHeader), "Remove File");
            assertEquals(getElementText(CommonUIActions.btnCommonModalBody), "If you remove this file here, it will still be accessible by the owner and other recipients.");
        } else {
            fail("Remove option is not available for a file");
        }
    }

    private static void handleLinkOption(boolean isCM) {
        if (isCM) {
            clickElement(CommonUIActions.lnkLinkContextMenu);
        } else {
            clickElement(CommonUIActions.lnkLinkFM);
        }
        assertTrue(isElementVisible(CommonUIActions.txtCommonModalTitle));
        assertEquals(getElementText(CommonUIActions.txtCommonModalTitle), "Get link");
    }

    private static void handleDownloadOption(boolean isCM, boolean acceptTOA, boolean isDownloadHidden) {
        String downloadXpathCM = "//div[@class='dropdown']/div/a/div/i[contains(@class,'fa-arrow-to-bottom far')]/../..";
        String downloadSelector = isCM ? downloadXpathCM : CommonUIActions.lnkDownloadFM;

        boolean isDownloadVisible = isElementVisible(downloadSelector);
        LogUtils.infoLog("Download button visibility: " + isDownloadVisible);

        if (isDownloadVisible) {
            clickElement(downloadSelector);
            LogUtils.infoLog("Clicked download button.");

            if (isElementVisible(CommonUIActions.txtGuestTOAHeader)) {
                LogUtils.infoLog("TOA modal is visible.");
                if (acceptTOA) {
                    FileViewer.acceptTOAAsRecipient();
                    LogUtils.infoLog("Accepted TOA.");
                } else {
                    clickElement(CommonUIActions.closeTOAModal);
                    LogUtils.infoLog("TOA modal closed. Download cancelled.");
                    return;
                }
            }
            waitUntilSelectorAppears(getXpathForContainsText("Preparing to download.", "p"));
            LogUtils.infoLog("Preparing to download...");
            CommonUIActions.validateAndCloseDownloadPopupBlockerModal();
        } else {
            LogUtils.infoLog("Download button is not visible.");

            if (isDownloadHidden) {
                boolean isHidden = isElementHidden(downloadSelector);
                LogUtils.infoLog("Download button hidden check (should be true): " + isHidden);
                assertTrue(isHidden);
            }
            LogUtils.infoLog("File download is not supported for this file.");
        }
    }

    private static void handleViewOption(boolean isCM) {
        if (isCM) {
            clickAndSwitchToNewTab("//div[@class='dropdown']/div/a/div/i[contains(@class,'fa-eye far')]/../..");
        } else {
            clickAndSwitchToNewTab("(//i[contains(@class,'fa-eye far')])[1]/..");
        }
        page().waitForLoadState(LoadState.LOAD);
        FileViewer.fileLoadInFileViewer();
    }

    public static void copyAndSaveReceivedLink() {
        SharedDM.setReceivedFileLink(getElementText(SendFilesPage.lnkFileLink));
        LogUtils.infoLog("Link of received file: " + SharedDM.getReceivedFileLink());
    }

    public void doRemoveFilesFromViewReceivedFiles() {
        page().waitForLoadState(LoadState.DOMCONTENTLOADED);
        if (!isElementVisible(CommonUIActions.chkAllCheckBox)) {
            LogUtils.infoLog("There is no files in view received files page");
            return;
        }

        CommonUIActions.selectAllCheckBox(CommonUIActions.chkAllCheckBox);
        CommonUIActions.selectOptionFromFloatingMenu("remove");
        clickElement(btnRemove);
        int retryCount = 0;
        while (retryCount < 10) {
            if (isElementVisible("//ngb-modal-window"))
                try {
                    waitForSelectorStateChange("//ngb-modal-window", ElementState.HIDDEN);
                    LogUtils.infoLog("Element state changed");
                } catch (Exception e) {
                    LogUtils.infoLog("Deleting received files event failed...");
                }
            retryCount++;
        }
    }

    public void validateReceivedFiles(String fileState) {
        String txtFirstEmptyMessage = "//div[@class='card-body text-center']/div/div/div/div/span";
        String txtSecondEmptyMessage = "//div[@class='card-body text-center']/div/div/div/div/div/span";
        String txtNameHeader = "//datatable-header-cell[@role='columnheader' and @title='Name']/div/span/span";
        String txtOwnerHeader = "//datatable-header-cell[@role='columnheader' and @title='Owner']/div/span/span";
        String txtDateHeader = "//datatable-header-cell[@role='columnheader' and @title='Date']/div/span/span";

        switch (fileState.toLowerCase()) {
            case "not present":
                assertTrue(isElementVisible("//div[@class='card-body text-center']/div/div/img")); //no-receive-files logo
                assertTrue(isElementVisible(txtFirstEmptyMessage));
                assertTrue(isElementVisible(txtSecondEmptyMessage));
                assertEquals(getElementText(txtFirstEmptyMessage), "You have not received any files.");
                assertEquals(getElementText(txtSecondEmptyMessage), "Files shared with you will appear here.");
                break;
            case "present":
                assertTrue(isElementVisible(txtNameHeader));
                assertTrue(isElementVisible(txtOwnerHeader));
                assertTrue(isElementVisible(txtDateHeader));
                assertEquals(getElementText(txtNameHeader), "Name");
                assertEquals(getElementText(txtOwnerHeader), "Owner");
                assertEquals(getElementText(txtDateHeader), "Date");
                assertTrue(isElementVisible(CommonUIActions.btnSearch));
                assertTrue(isElementVisible(ddSortByFilter));
                break;
            default:
                fail("file state option is invalid");
        }
    }

    public void removeFile() {
        clickElement(CommonUIActions.btnSubmit);
        validateToastMsg(CommonUIActions.txtCommonToastMsg, "File removed.");
    }
}