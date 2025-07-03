package digifyE2E.pages.dataRooms;

import com.microsoft.playwright.options.ElementState;
import com.microsoft.playwright.options.LoadState;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.documentSecurity.SendFilesPage;
import digifyE2E.utils.LogUtils;

import java.util.List;

import static org.testng.Assert.*;

public class ManageDataRoomPage extends CommonUIActions {

    public static final String txtManageDRHeading = getXpathForContainsText("Manage Data Rooms", "h4");
    public static final String lnkManageDRAnalyticsFM = "//i[@class='fa-chart-bar far']/..";
    public static final String lnkManageDRAccessFM = "//i[@class='fa-users-medical far']/..";
    public static final String lnkManageDRSettingsFM = "//a//i[@class='fa-cog far']/..";
    public static final String lnkManageDRLeaveFM = "//i[@class='fa-minus-circle far']/..";

    public static void openDRListInDR() {
        String lnkGoToDataRoomList = "//*[contains(@id, 'dropdown-animated')]/a/span[contains(text(), 'Go to Data Room List')]";
        clickElement(CreateDataRoomPage.drdDRUserInfo);
        clickElement(lnkGoToDataRoomList);
        waitUntilSelectorAppears(txtManageDRHeading);
        waitUntilManageDRPageLoad();
    }

    public static void searchDRFromManageDR(String dataRoomName) {
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears(CommonUIActions.btnSearch);
        clickElement(CommonUIActions.btnSearch);
        inputIntoTextField("(//input[@placeholder='Search'])[1]", dataRoomName);
        keyPress("Enter");
    }

    public static void clickOnFirstSearchedDRInManageDR() {
        String lnkSearchedDRFirstItem = "//*[@class='file-info-data-detail']/div/div/div/a";
        clickAndSwitchToNewTab(lnkSearchedDRFirstItem); // Click the link that opens the new tab
        page().waitForLoadState(LoadState.LOAD);
    }

    public static void UnsharedDR() {
        page().waitForLoadState(LoadState.LOAD);
        clickElement("(//div[@class='clear']/div/pg-switch)[1]");
        validateToastMsg(CommonUIActions.txtCommonToastMsg, "Data room unshared.");
    }

    public static void openClonedDataRoomFromManageDR(String clonedDataRoomName) {
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears(getXpathForContainsText(clonedDataRoomName, "a") + "/../..");
        clickAndSwitchToNewTab(getXpathForContainsText(clonedDataRoomName, "a"));
        waitUntilSelectorAppears(getXpathForContainsText("Files", "p"));
    }

    public static void waitUntilManageDRPageLoad() {
        int i = 1;
        while (i <= 3) {
            String processingBar = page().getAttribute("//div[@class='pace-progress']", "data-progress-text");
            if (processingBar.equals("100%")) {
                LogUtils.infoLog("Manage Data Room page loading is completed");
                break;
            }
            waitForSeconds(60 * i++);
        }
    }

    public static void clickOnBrandLogo() {
        waitUntilSelectorAppears("(//img[@class='dlogo brand-logo'])[1]");
        clickElement("(//img[@class='dlogo brand-logo'])[1]");
        waitUntilSelectorAppears(getXpathForContainsText("Home", "h4"));
        assertTrue(isElementVisible(getXpathForContainsText("Home", "h4")));
    }

    public static void revokeOwnAccessOfDR() {
        assertTrue(isElementVisible(CommonUIActions.commonModalHeader));
        assertEquals(getElementText(CommonUIActions.commonModalHeader),
                "You are revoking your own access to this data room");
        assertTrue(isElementVisible(CommonUIActions.btnCommonModalBody));
        assertEquals(getElementText(CommonUIActions.btnCommonModalBody),
                "You will need to reach out to the data room owner if you need access again. Do you want to continue?");
        clickElement("//button[@class='btn digify-btn-blue']");
        waitUntilSelectorAppears(
                getXpathForContainsText("Access to data room(s) revoked.", "p"));
    }

    public static void viewFirstExistingDRFromManageDR() {
        waitUntilSelectorAppears(ManageDataRoomPage.txtManageDRHeading);
        assertTrue(isElementVisible(ManageDataRoomPage.txtManageDRHeading));
        CommonUIActions.selectFirstCheckbox();
        clickAndSwitchToNewTab("(//div[@class='file-info-data-detail']/div/div/div/a)[1]");//first data room locator
    }

    public static void navigateToManageDRPage() {
        clickElement(getXpathForContainsText("Manage Data Rooms", "p"));
        waitForManageDRToLoad();
    }

    public static void waitForManageDRToLoad() {
        page().waitForLoadState(LoadState.LOAD);
        assertEquals(getElementText("//app-root-dataroom//div[1]/div[1]//h4"), "Manage Data Rooms");
        int retryCount = 0;
        while (retryCount < 5) {
            if (isElementVisible("//app-root-dataroom/div/div/div[2]/div/div[2]/div/div/img")) {
                try {
                    waitForSelectorStateChange("//app-root-dataroom/div/div/div[2]/div/div[2]/div/div/img", ElementState.HIDDEN);
                    break;
                } catch (Exception e) {
                    LogUtils.infoLog("Manage DR page taking time to load");
                }
            }
            retryCount++;
            waitForSeconds(1);
        }
    }

    public static void deleteAllDR() {
        if (!isElementVisible(CommonUIActions.chkAllCheckBox)) {
            LogUtils.infoLog("No data room or files to delete");
            return;
        }
        CommonUIActions.selectAllCheckBox(CommonUIActions.chkAllCheckBox);
        clickElement(getXpathForContainsText("Delete", "a"));
        clickElement("//app-delete//label[@for='confirmation']");
        clickElement("//app-delete/div[2]/div[2]/button[2]");
        int retryCount = 0;
        while (retryCount < 10) {
            if (isElementVisible("//ngb-modal-window"))
                try {
                    waitForSelectorStateChange("//ngb-modal-window", ElementState.HIDDEN);
                } catch (Exception e) {
                    LogUtils.infoLog("Delete DR event continue...");
                }
            retryCount++;
        }
    }


    public static String getMenuLocator(String optionType, boolean isCM) {
        if (isCM) {
            clickElement(CommonUIActions.drdMoreOptionsCM);
            waitForSelectorStateChange("//div[@class='dropdown-menu file-option-dropdown dropdown-menu-right bg-white no-padding show']", ElementState.STABLE);
        }
        switch (optionType.toLowerCase()) {
            case "view":
                return isCM
                        ? "//div[@class='dropdown']/div/a/div/i[contains(@class,'fa-eye far')]/../.."
                        : CommonUIActions.lnkViewFloatingMenu;
            case "analytics":
                return isCM
                        ? "//div[@class='dropdown']/div/a/div/i[contains(@class,'fa-chart-bar')]/../.."
                        : lnkManageDRAnalyticsFM;
            case "access":
            case "manage access":
                return isCM
                        ? "//div[@class='dropdown']/div/a/div/i[contains(@class,'fa-users-medical')]/../.."
                        : lnkManageDRAccessFM;
            case "settings":
                return isCM
                        ? "//div[@class='dropdown']/div/a/div/i[contains(@class,'fa-cog far')]/../.."
                        : lnkManageDRSettingsFM;
            case "clone dr":
                if (isCM) {
                    return "//div[@class='dropdown']/div/a/div/i[contains(@class,'fa-clone far')]/../..";
                } else {
                    fail("Invalid optionType for Floating Menu: " + optionType);
                }
                break;
            default:
                fail("Invalid optionType: " + optionType);
        }
        return null;
    }


    public static void selectMenuOptionToOpenModal(String optionType, boolean isCM) {
        if (isCM) {
            clickElement(CommonUIActions.drdMoreOptionsCM);
        }
        switch (optionType.toLowerCase()) {
            case "link":
                if (isCM) {
                    clickElement(CommonUIActions.lnkLinkContextMenu);
                } else {
                    clickElement(CommonUIActions.lnkLinkFloatingMenu);
                }
                assertTrue(isElementVisible(CommonUIActions.txtCommonModalTitle));
                assertEquals(getElementText(CommonUIActions.txtCommonModalTitle), "Get data room link");
                clickElement(CommonUIActions.btnCloseModal);
                break;
            case "delete":
                boolean isDeleteVisible = isCM
                        ? isElementVisible("//div[@class='dropdown']/div/a/div/i[contains(@class,'fa-trash far')]/../..")
                        : isElementVisible(CommonUIActions.lnkDeleteFloatingMenu);

                if (isDeleteVisible) {
                    if (isCM) {
                        clickElement("//div[@class='dropdown']/div/a/div/i[contains(@class,'fa-trash far')]/../..");
                    } else {
                        clickElement(CommonUIActions.lnkDeleteFloatingMenu);
                    }
                    waitUntilSelectorAppears(getXpathForContainsText("Delete", "h4"));
                    clickElement(CreateDataRoomPage.chkConfirmationDeleteDR);
                    clickElement(CreateDataRoomPage.btnDeleteInWarningModal);
                    assertTrue(isElementVisible(ManageDataRoomPage.txtManageDRHeading));
                } else {
                    LogUtils.infoLog("No DR to delete");
                }
                break;
            case "leave":
            case "revoke my access":
                if (isCM) {
                    clickElement("//div[@class='dropdown']/div/a/div/i[contains(@class,'fa-minus-circle')]/../..");
                } else {
                    clickElement(lnkManageDRLeaveFM);
                }
                break;
            default:
                fail("Invalid optionType: " + optionType);
        }
    }


    public static void verifyMenuNotVisible(List<String> optionType, boolean isCM) {
        optionType.forEach(option -> {
            String elementPath;
            switch (option.trim().toLowerCase()) {
                case "delete":
                    elementPath = isCM
                            ? "//div[@class='dropdown']/div/a/div/i[contains(@class,'fa-trash far')]/../.."
                            : CommonUIActions.lnkDeleteFloatingMenu;
                    break;
                case "analytics":
                    elementPath = isCM
                            ? "//div[@class='dropdown']/div/a/div/i[contains(@class,'fa-chart-bar')]/../.."
                            : lnkManageDRAnalyticsFM;
                    break;
                case "access":
                case "manage access":
                    elementPath = isCM
                            ? "//div[@class='dropdown']/div/a/div/i[contains(@class,'fa-users-medical')]/../.."
                            : lnkManageDRAccessFM;
                    break;
                case "settings":
                    elementPath = isCM
                            ? "//div[@class='dropdown']/div/a/div/i[contains(@class,'fa-cog far')]/../.."
                            : lnkManageDRSettingsFM;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid optionType: " + option);
            }
            assertFalse(isElementVisible(elementPath));
        });
    }

    public void selectFilterOptionInSortByMDR(String sortByFilterType) {
        String ddSortByFilter =
                getXpathForContainsText("Manage Data Room", "h4") + "/../following-sibling::node()/pg-select/div";
        String ddSelectedSortByFilter =
                getXpathForContainsText("Manage Data Room", "h4") + "/../following-sibling::node()/pg-select/div/div/div[2]";
        waitUntilManageDRPageLoad();
        waitUntilSelectorAppears(ddSortByFilter);
        CommonUIActions.selectValueFromDropdownList(ddSortByFilter, SendFilesPage.ddElementList, sortByFilterType);
        waitUntilSelectorAppears(ddSelectedSortByFilter);
        assertTrue(isElementVisible(ddSelectedSortByFilter));
        assertEquals(getElementText(ddSelectedSortByFilter), "Sort by: " + sortByFilterType);
        waitForSeconds(2);//Added hard wait for the dropdown to reflect the changes.
    }
}