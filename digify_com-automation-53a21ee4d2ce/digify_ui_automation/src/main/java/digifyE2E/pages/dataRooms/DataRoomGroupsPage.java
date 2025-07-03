package digifyE2E.pages.dataRooms;

import com.microsoft.playwright.options.ElementState;
import com.microsoft.playwright.options.LoadState;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.documentSecurity.SendFilesPage;
import digifyE2E.pages.landingPage.LoginPage;
import digifyE2E.utils.LogUtils;
import digifyE2E.utils.RandomUtils;

import static org.testng.Assert.*;

public class DataRoomGroupsPage extends CommonUIActions {

    public static final String txtInputGroupName = "(//div[@class='setting-input'])[1]/div/input";
    public static final String txtGroupTitle = "//div[@class='file-name']/div/h4";
    public static final String drdGroupMembers = "//*[@class='c-btn']";
    public static final String txtNoGroupCenterPageTitle = getXpathForTextEquals(" You have not created any groups yet. ", "p") + "/../div/button";
    static final String txtGroupTabHeading = getXpathForTextEquals(" Groups ", "h4");
    static final String newGroupName = "updatedGroupName" + "-" + RandomUtils.getRandomString(3, true);

    public static void selectPermissionSettingsInGroup(String grpPermissionType) {
        if (grpPermissionType == null || grpPermissionType.trim().isEmpty()) {
            LogUtils.infoLog("Permission type cannot be null or empty.");
            return;
        }
        clickElement("(//div[@class='setting-input'])[3]/pg-select");// Click the group permission selector
        switch (grpPermissionType) {
            case "No Access", "View", "Print", "Download (PDF)", "Edit", "Download (Original)", "Granular Permissions":
                CreateDataRoomPage.selectPermissionTypeInDR(grpPermissionType);
                break;
            default:
                fail("No match found for group settings: " + grpPermissionType);
        }
    }

    public static void selectAccessExpiryInGroup() {
        String drdGroupExpiry = "(//div[@class='setting-input'])[4]/pg-select";
        clickElement(drdGroupExpiry);
        clickElement(getXpathForTextEquals(" Expire on fixed date and time ", "li"));
    }

    public static void saveGroupSettings() {
        waitUntilSelectorAppears(txtGroupTitle);
        waitUntilSelectorAppears(CommonUIActions.btnSubmit);
        clickElement(CommonUIActions.btnSubmit);
        waitUntilSelectorAppears(txtGroupTabHeading);
    }

    public static void accessRecentlyCreatedGroup() {
        waitUntilSelectorAppears(txtGroupTabHeading);
        clickElement("//div[@class='file-name']/a[contains(text(),'" + DataRoomGroupManager.getLatestGroupName() + "')]");
        waitUntilSelectorAppears(txtGroupTitle);
    }

    public static void clickAddNewGuestLinkInGroup() {
        String addNewGuestLink = "(//a[text()=' Add new guests '])[2]";
        assertTrue(isElementVisible(addNewGuestLink));
        clickElement(addNewGuestLink);
        waitUntilSelectorAppears(DataRoomGuestsPage.txtTitleAddNewGuest);
    }

    private static void selectExistingSingleGuestInGroup(String guestUsername) {
        clickElement(drdGroupMembers);
        LoginPage.userCredentials = DataRoomGuestsPage.getGuestUsernameFromJson(guestUsername);
        clickElement("//ul[@class='lazyContainer']/li/div//following-sibling::p[contains(text(),'" + LoginPage.userCredentials.get("username") + "')]");
        clickElement(drdGroupMembers);
    }

    public static void addSingleGuestInGroup(String guestUsername, Boolean isGuestExistInDR) {
        if (isGuestExistInDR) {
            DataRoomGroupsPage.selectExistingSingleGuestInGroup(guestUsername);
        } else {
            clickAddNewGuestLinkInGroup();
            DataRoomGuestsPage.addGuestEmailInAddGuest(guestUsername);
            clickElement(getXpathForTextEquals(" No group selected ", "div") + "/..");
            clickElement(getXpathForTextEquals(DataRoomGroupManager.getLatestGroupName(), "p"));
            clickElement(DataRoomGuestsPage.btnAddAndSendNotification);
            waitUntilSelectorAppears(DataRoomGuestsPage.txtGuestAddedMsg);
        }
    }

    public static void addAllGuestInDrGroup() {
        clickElement(drdGroupMembers);
        clickElement("(//input[@type='checkbox']/../label)[1]");
        keyPress("Tab");
    }

    public static void renameGroup() {
        inputIntoTextField(txtInputGroupName, newGroupName);
    }

    public static void removeGuestFromGroup(String guestUsername) {
        DataRoomGroupsPage.selectExistingSingleGuestInGroup(guestUsername);
        assertTrue(isElementVisible("(//div[@class='c-btn']/span)[1]"));
    }

    public static void deleteGroup() {
        CommonUIActions.selectFirstCheckbox();
        clickElement("//div[@class='text-right']/a[3]");
        waitUntilSelectorAppears(CommonUIActions.txtCommonModalTitle);
        assertTrue(isElementVisible(CommonUIActions.txtCommonModalTitle));
        assertEquals(getElementText(CommonUIActions.txtCommonModalTitle), "Delete group");
        assertEquals(getElementText("//div[@class='modal-body']/div/p"),
                "This action cannot be undone. After the group is deleted, group members will retain their existing file permissions.");
        clickElement(CreateDataRoomPage.btnDeleteInWarningModal);
        waitUntilSelectorAppears(txtNoGroupCenterPageTitle);
        assertTrue(isElementVisible(txtNoGroupCenterPageTitle));
    }

    public static void selectCopyPermission() {
        assertTrue(isElementVisible(
                "//div[@id='copy-permission']"));
        assertEquals(getElementText(
                        "//div[@id='copy-permission']/div/div/span"),
                "You can copy permissions from an existing guest/group. Copy permissions");
        waitForSelectorStateChange("//div[@id='copy-permission']//a", ElementState.STABLE);
        dispatchClickElement("//div[@id='copy-permission']//a");
        waitUntilSelectorAppears("//div[@class='modal-content']/app-data-room-copy-permission");
        assertTrue(isElementVisible("//div[@class='modal-content']/app-data-room-copy-permission"));
        assertEquals(getElementText("//div[@class='modal-content']/app-data-room-copy-permission/div/h4"),
                "Copy permissions from:");
        assertTrue(isElementVisible("//div[@class='copy-permission-content']/pg-select"));
    }

    public static void selectOptionFromCopyPermission(String copyPermissionOption) {
        CommonUIActions.selectValueFromDropdownList("//div[@class='copy-permission-content']/pg-select", SendFilesPage.ddElementList, copyPermissionOption.trim());
    }

    public static void selectGuestOrGroupInCopyPermission(Boolean isGuest, String guestUsername, Boolean isGroup) {
        if (isGuest != null && isGuest) {
            if (guestUsername != null && !guestUsername.isEmpty()) {
                clickElement(
                        "//div[@class='item']//p[contains(text(),'" + DataRoomGuestsPage.getGuestName + "')]");
                clickElement(btnCommonButtonToCreate);
            } else {
                throw new IllegalArgumentException("Guest username cannot be null or empty when isGuest is true.");
            }
        } else if (isGroup != null && isGroup) {
            clickElement("//div[@class='item']//p[contains(text(),'" + DataRoomGroupManager.getPreviousGroupName() + "')]");
            clickElement(btnCommonButtonToCreate);
        } else {
            throw new IllegalArgumentException("Either isGuest or isGroup must be true.");
        }
    }

    public static void validateCopiedGuestPermOnCreateGroup(String copiedPermission) {
        assertTrue(isElementVisible(
                "//div[@class='setting-input']/pg-select/div/div/div[contains(text(),'" +
                        DataRoomGuestsPage.getGuestPermission + "')]"));
    }

    public static void validateCopiedPermInGroupTab(String copiedPermission) {
        page().waitForLoadState(LoadState.LOAD);
        page().waitForLoadState(LoadState.DOMCONTENTLOADED);
        waitForSeconds(1); //Added a wait to load the page
        waitUntilSelectorAppears(txtGroupTabHeading);
        assertEquals(DataRoomGroupManager.getCurrentGroupPermission(), copiedPermission);
    }

    public static void validateMsgOnCopyPermModal(String copyPermissionOptionType) {
        String txtCopyModalMsg = "//div[@class='copy-permission-content']/div/div/p";
        if (copyPermissionOptionType.equalsIgnoreCase("Another guest")) {
            assertTrue(isElementVisible(txtCopyModalMsg));
            assertEquals(getElementText(txtCopyModalMsg), "You have not added any guests yet");
        } else {
            assertTrue(isElementVisible(txtCopyModalMsg));
            assertEquals(getElementText(txtCopyModalMsg), "You have not created any groups yet");
        }
    }

    public static void selectCancelBtnOnCopyPermission() {
        clickElement("//div[@class='copy-permission-footer']/button[1]");
        waitUntilSelectorAppears(txtGroupTitle);
    }

    public static void selectPreviouslyCreatedGroupInCopyPermission() {
        clickElement("//div[@class='ps-content']/div/div/div/p[contains(text(),'" + DataRoomGroupManager.getPreviousGroupName() + "')]");
        clickElement(btnCommonButtonToCreate);
    }

    public static void validateDefaultGroupTag() {
        assertTrue(isElementVisible("//div[@tooltipclass='xs-hide-tooltip']/span"));
        assertEquals(getElementText("//div[@tooltipclass='xs-hide-tooltip']/span"),
                "Default group");
    }

    public static void validateDeleteFloatingMenuOptionIsNotVisible() {
        CommonUIActions.selectFirstCheckbox();
        assertFalse(isElementVisible("//i[@class='fa-trash far']"));
    }
}
