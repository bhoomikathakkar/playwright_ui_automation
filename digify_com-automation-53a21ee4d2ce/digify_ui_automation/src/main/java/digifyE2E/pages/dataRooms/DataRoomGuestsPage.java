package digifyE2E.pages.dataRooms;

import com.microsoft.playwright.options.LoadState;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.documentSecurity.ManageSentFilesPage;
import digifyE2E.pages.documentSecurity.SendFilesPage;
import digifyE2E.pages.landingPage.LoginPage;
import digifyE2E.testDataManager.SharedDM;
import digifyE2E.utils.FileUtils;
import digifyE2E.utils.LogUtils;

import java.util.Map;
import java.util.Objects;

import static org.testng.Assert.*;

public class DataRoomGuestsPage extends CommonUIActions {

    public static final String btnGoToDrList = "(//div[@id='dropdown-animated']/a/span)[1]";
    public static final String txtTitleAddNewGuest = "//div[@class='file-name']/div/h4/span[1]";
    static final String btnAddGuest = "//button[@id='addGuest']";
    static final String btnAddAndSendNotification = "//span[@class='txt' and text()='Add & send notification ']";
    static final String txtGuestAddedMsg = "//a[@id='check-file-visibility']/../../div/div";
    public static String getGuestUsername = "";
    public static String getGuestName = "";
    public static String getGuestPermission;
    private static String time;

    public static void addGuestEmailInAddGuest(String guestUsername) {
        String txtEnterEmailId = "//input[@formcontrolname='guestEmail']";
        String btnAddEmail = "//button[@class='btn digify-btn-blue domain-btn']";
        String listRecipientList = "//div[@class='domains recipient-list']";

        LoginPage.userCredentials = getGuestUsernameFromJson(guestUsername);
        inputIntoTextField(txtEnterEmailId, LoginPage.userCredentials.get("username"));
        clickElement(btnAddEmail);
        waitUntilSelectorAppears(listRecipientList);
    }

    public static Map<String, String> getGuestUsernameFromJson(String guestUsername) {
        return FileUtils.getUserCredentialsFromJsonFile(guestUsername);
    }

    public static void navigateToAddNewGuestPage(Boolean isGuestExists) {
        if (!isGuestExists) {
            clickElement(btnAddGuest);
        } else {
            clickElement(getXpathForContainsText("Add guest", "button"));
        }
        waitUntilSelectorAppears(txtTitleAddNewGuest);
        assertEquals(getElementText(txtTitleAddNewGuest), "Add New Guest");


    }

    public static void addGuestsFromExcelToBulkInvite(int noOfGuest, String file) {
        clickElement(getXpathForContainsText("Bulk invitation", "a"));
        assertEquals(getElementText("//pg-tab-body//h4"), "Bulk invitation");
        assertEquals(getElementText("//pg-tab-body[1]/div[2]/div[1]"), normalizeSpaceForString(
                "Copy and paste the list of guests' emails below, separated by spaces or commas. Duplicate or malformed email addresses will be removed automatically.   Copying from Word/Excel?"));
        fillRecipientsFromExcel(noOfGuest, file, ManageSentFilesPage.txtBulkRecipient);
        clickElement("//pg-tab-body[1]/div[2]/div[3]/button[2]");
    }

    public static void addGuest(String guestUsername, String permissionType, Boolean isGuestExists, Integer noOfRecipientReqInBulkInv, String file, Boolean isBulkRecipient) {
        isGuestExists = (isGuestExists != null) ? isGuestExists : false;
        noOfRecipientReqInBulkInv = (noOfRecipientReqInBulkInv != null) ? noOfRecipientReqInBulkInv : 0;
        file = (file != null) ? file : "";
        isBulkRecipient = (isBulkRecipient != null) ? isBulkRecipient : false;
        navigateToAddNewGuestPage(isGuestExists);
        if (isBulkRecipient) {
            addGuestsFromExcelToBulkInvite(noOfRecipientReqInBulkInv, file);
            if (permissionType != null) {
                selectPermissionSettingsInAddGuest(permissionType);
            }
        } else {
            addGuestEmailInAddGuest(guestUsername);
            if (permissionType != null) {
                selectPermissionSettingsInAddGuest(permissionType);
            }
            notifyDRGuest(true);
        }
    }


    public static void clickAddAndSendNotificationBtn() {
        clickElement(btnAddAndSendNotification);
    }

    public static void notifyDRGuest(boolean isAddingGuestSuccessful) {
        clickAddAndSendNotificationBtn();
        if (isAddingGuestSuccessful) {
            waitUntilSelectorAppears(txtGuestAddedMsg);
            page().waitForLoadState(LoadState.LOAD);
        } else {
            LogUtils.infoLog("Error occurred while adding a new guest");
        }
    }

    public static void selectAccessExpiryInEditGuest(String expiryType) {
        String drdAccessExpiry = "//app-data-room-manage-guest/div[2]/div/div/div/div[4]/div/div[2]/pg-select/div/span";
        switch (expiryType.toLowerCase()) {
            case "expire on fixed date and time":
                waitUntilSelectorAppears(drdAccessExpiry);
                clickElement(drdAccessExpiry);
                clickElement(getXpathForTextEquals(" Expire on fixed date and time ", "*"));
                break;
            case "off":
                clickElement(drdAccessExpiry);
                clickElement(getXpathForTextEquals(" Off ", "*"));
                break;
            default:
                fail("No match found for access expiry in edit guest page:" + expiryType);

        }
    }

    private static void selectPermissionSettingsInAddGuest(String addGuestPermissionType) {
        String drdAddGuestPermission = "(//div[@id='targetPermission']/div/div[3]/div/div/div[2]/pg-select/div)[1]";
        switch (addGuestPermissionType) {
            case "No Access", "View", "Print", "Download (Original)", "Edit", "Download (PDF)", "Co-Owner",
                 "Granular Permissions":
                clickElement(drdAddGuestPermission);
                CreateDataRoomPage.selectPermissionTypeInDR(addGuestPermissionType);
                break;
            default:
                fail("No match found for permissions in add guest settings: " + addGuestPermissionType);
        }
    }

    public static void clickOnReturnToGuestLink() {
        waitUntilSelectorAppears("(//div[contains(@class,'send-msg-head')])[2]");
        clickElement("//div[@class='notify-skipped-button-wrapper']/../p/a");
        waitUntilSelectorAppears(CreateDataRoomPage.txtGuestHeading);
    }

    public static void selectCustomStartOREndDateTime(String locator, String days, String time) {
        page().waitForLoadState(LoadState.LOAD);
        clickElement(getXpathForTextEquals(" Custom ", "li"));
        page().setViewportSize(1920, 1080);
        waitUntilSelectorAppears(locator);
        clickElement(locator);
        waitUntilSelectorAppears(".fullcalendartbody");
        selectFutureDateFromCalendar(days);
        selectTimeFromCalendar(time,
                "//*[@id='expiry']//div[2]//div[2]/div[2]/div/pg-timepicker//span/i");
        page().setViewportSize(1280, 720);
    }

    public static void selectAndValidateDateAndTime(String days, String time) {
        String drdDaysToSelect = "//*[@id='fixed-expiry-val']/div/span";
        String txtExpiryMsg = "//*[@id='expiry']/div/div[2]/div/div/div";
        String futureDateTime = getFutureDate(days, true);
        String expectedMsg = "Guest access will be scheduled to expire on  " + futureDateTime + "  . Date & time are based on your browser's time zone. ";
        String drdStartDate = "//*[@id='expiry']/div/div[2]/div/div[2]/div[1]/div/pg-datepicker/span/input";

        switch (days.toLowerCase()) {
            case "1 day":
                page().waitForLoadState(LoadState.LOAD);
                clickElement(drdDaysToSelect);
                clickElement(getXpathForTextEquals(" 1 day ", "li"));
                assertEquals(getElementText(txtExpiryMsg), normalizeSpaceForString(expectedMsg));
                break;
            case "7 days":
                clickElement(drdDaysToSelect);
                clickElement(getXpathForTextEquals(" 7 days ", "li"));
                assertEquals(getElementText(txtExpiryMsg), expectedMsg);
                break;
            case "30 days":
                clickElement(drdDaysToSelect);
                clickElement(getXpathForTextEquals(" 30 days ", "li"));
                assertEquals(getElementText(txtExpiryMsg), normalizeSpaceForString(expectedMsg));
                break;
            case "60 days":
                clickElement(drdDaysToSelect);
                clickElement(getXpathForTextEquals(" 60 days ", "li"));
                assertEquals(getElementText(txtExpiryMsg), normalizeSpaceForString(expectedMsg));
                break;
            case "90 days":
                clickElement(drdDaysToSelect);
                clickElement(getXpathForTextEquals(" 90 days ", "li"));
                assertEquals(getElementText(txtExpiryMsg), normalizeSpaceForString(expectedMsg));
                break;
            case "5 custom":
                String drdEndDate = "//*[@id='expiry']/div/div[2]/div/div[4]/div[1]/div/pg-datepicker/span/input";
                clickElement(drdDaysToSelect);
                selectCustomStartOREndDateTime(drdEndDate, days, time);
                break;
            case "custom 7", "custom 1":
                clickElement(drdDaysToSelect);
                selectCustomStartOREndDateTime(drdStartDate, days, time);
                break;
            default:
                fail("Days not found in expiry: " + days);

        }
    }

    public static String getFutureTime() {
        String getTime = "//*[@id='expiry']//div[2]//div[2]/div[2]//pg-timepicker/span/input";
        return getInputValue(getTime);
    }

    public static void saveChangesOnEditGuestPage() {
        dispatchClickElement(getXpathForContainsText("Save", "span"));
        waitUntilSelectorAppears(getXpathForTextEquals("Guest permission updated.", "p"));
    }

    public static void validateGuestExpiry(String days) {
        String txtExpiryDate = "//div[@class='file-name']//span//span";
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears(txtExpiryDate);
        assertEquals(getElementText(txtExpiryDate).replace("· ", ""),
                getFutureDate(days, true).substring(0, getFutureDate(days, true).length() - 8).trim());
    }

    public static void validateFutureAccessDate() {
        selectOptionFromFloatingMenu("manage guest link");
        time = getFutureTime();
        saveChangesOnEditGuestPage();
    }

    public static void validateFutureAccess(String days) {
        String txtViewAfter = "//page-container//div[2]/p";
        waitUntilSelectorAppears(txtViewAfter);
        String expectedMsg = getElementText(txtViewAfter + "[1]");
        String futureDate = getFutureDate(days, true);
        assertEquals(expectedMsg.substring(0, expectedMsg.length() - 2), "Available for viewing after " + futureDate.substring(0, futureDate.length() - 9) + " " + time + " ");
        assertEquals(getElementText("//page-container//div[2]/span"),
                normalizeSpaceForString(" Please check back later. "));
        assertEquals(getElementText(txtViewAfter + "[2]"),
                normalizeSpaceForString("Date & time are based on your browser's time zone."));
        clickElement("//button[contains(@class,'btn digify-btn-blue')]");//go to data room list button
    }

    public static void validateGuestUsernameOnMembersTab(String userType) {
        getGuestUsername = Objects.requireNonNull(FileUtils.getUserCredentialsFromJsonFile(userType)).get("username");
        String lnkMembersTab = "//li[@id='adm_mem']/a";
        String txtGuestUsername = "//div[@class='recipient-data-detail']/div/span[contains(text(), '" + getGuestUsername + "')]";
        clickElement(lnkMembersTab);
        waitUntilSelectorAppears("//*[@id='tabMembers']/div[2]/div/div/div/p");
        assertEquals(getElementText(txtGuestUsername), getGuestUsername.trim());
    }

    public static void selectTabInGuestPageOfDR(String getTabInGuestPage) {
        switch (getTabInGuestPage.toLowerCase()) {
            case "guests":
                clickElement("//div[@id='guest-container']/div/ul/li[1]/a");
                break;
            case "groups":
                clickElement("//div[@id='guest-container']/div/ul/li[2]/a");
                break;
            case "permission overview":
                clickElement("//div[@id='guest-container']/div/ul/li[3]/a");
                break;
            case "guestlist":
                clickElement("//*[@id='targetPermission']/div[2]/div/div[2]/div/p/a");
                break;
            default:
                fail("Match not found in the guest tab in data room: " + getTabInGuestPage);
        }
    }

    public static void validateGuestAccessPreview(String drPermissionType) {
        String txtFilesHeading = "//ol[@class='breadcrumb']/li";
        String txtFileName = SharedDM.getNewFileNameWithExtension();
        String txtFirstFileInGuestPA = "//div[@class='file-name block overflow-ellipsis']//div[1]";
        switch (drPermissionType.trim()) {
            case "No Access":
                String txtNoAccessPreviewMsg = "//p[contains(@class,'msg-1')]";
                waitUntilSelectorAppears(txtNoAccessPreviewMsg);
                assertEquals(getElementText(txtNoAccessPreviewMsg), "No access to files/folders on root level");
                clickElement(btnCloseModal);
                break;
            case "Edit", "View":
                waitUntilSelectorAppears(txtFilesHeading);
                assertEquals(getElementText(txtFilesHeading), "FILES");
                assertTrue(getElementText(txtFirstFileInGuestPA).contains(txtFileName));
                clickElement(btnCloseModal);
                break;
            default:
                fail("no match for: " + drPermissionType);
        }
    }

    public static void selectGuestInGuestTab() {
        clickElement("(//input[@class='datatable-checkbox'])[2]");
    }

    public static void goBackFromAddGuestPage() {
        waitUntilSelectorAppears(txtTitleAddNewGuest);
        clickElement("//div[@class='go-back-arrow cursor']");
        waitUntilSelectorAppears(CreateDataRoomPage.txtGuestHeading);
    }

    public static void validateAddedBulkGuestOnGuestsPage(int noOfGuest) {
        page().waitForLoadState(LoadState.LOAD);
        getRecipientFromExcelAndValidateRecipientList(noOfGuest, "//datatable-body-cell[2]/div/div/div[2]//div[2]/span",
                String.valueOf(ManageSentFilesPage.recipientListExcelFile));
    }

    public static void clickOnManageGuestOnGuestPage(String guestUsername) {
        getGuestUsername = Objects.requireNonNull(FileUtils.getUserCredentialsFromJsonFile(guestUsername)).get("username");
        waitUntilSelectorAppears(CreateDataRoomPage.txtGuestHeading);
        assertTrue(isElementVisible("//div[@class='file-name']/span[contains(text(),'" + getGuestUsername + "')]"));
        selectFirstCheckbox();
        clickElement("//div[@class='text-right']/a[contains(text(),'Manage Guest')]");
    }

    public static void performActionOnManageGuestPage(Boolean isGroupAdded, String selectGroup) {
        if (isGroupAdded) {
            selectValueFromDropdownList(
                    "//div[@class='setting-name']/div[contains(text(),'Group')]/../following-sibling::div/pg-select",
                    SendFilesPage.ddElementList, selectGroup);
        } else {
            LogUtils.infoLog("Group is not selected for the guest..");
            //Todo: Implement handling for Permission and Expiry fields in future.
        }
    }

    public static void getGuestNameFromGuestPage(String guestUsername) {
        LoginPage.userCredentials = DataRoomGuestsPage.getGuestUsernameFromJson(guestUsername);
        getGuestName = getElementText("//div[@class='file-name' and contains(.,'" + LoginPage.userCredentials.get("username") + "')]/../div/a");
    }

    public static void getGuestPermissionFromGuestPage(String guestUsername) {
        LoginPage.userCredentials = DataRoomGuestsPage.getGuestUsernameFromJson(guestUsername);
        getGuestPermission = getElementText(
                "//div[@class='file-name' and contains(.,'" + LoginPage.userCredentials.get("username") + "')]/../../../../../../../datatable-body-cell[4]/div/div/div/a");
    }

    public static void validateEmailIdOnGuestTab(String guestUsername) {
        getGuestUsername = Objects.requireNonNull(FileUtils.getUserCredentialsFromJsonFile(guestUsername)).get("username");
        assertEquals(getElementText("//div[@class='file-name']/span[contains(text(),'" + getGuestUsername + "')]"), getGuestUsername);
    }

    public static void validateEmailIdNotPresentOnGuestTab(String guestUsername) {
        getGuestUsername = Objects.requireNonNull(FileUtils.getUserCredentialsFromJsonFile(guestUsername)).get("username");
        assertFalse(page().isVisible("//div[@class='file-name']/span[contains(text(),'" + getGuestUsername + "')]"));
    }

    public static void validateSelectedDefaultGroup() {
        assertEquals(getElementText(
                        "(//div[@class='share-setting guest-group-padding'])[2]/div/div[2]/pg-select/div/div/div[2]"),
                DataRoomGroupManager.getLatestGroupName());
        assertEquals(getElementText(
                        "(//div[@class='share-setting guest-group-padding'])[2]/div/div[2]/p"),
                "This group's permissions will apply to the guest(s). Manage group");
    }

    public static void removeSingleGuest(String userType) {
        getGuestUsername = Objects.requireNonNull(FileUtils.getUserCredentialsFromJsonFile(userType)).get("username");
        clickElement("//button//i[@class='far fa-search']");
        inputIntoTextField("//form//input[@formcontrolname='value']", getGuestUsername);
        keyPress("Enter");
        selectOptionFromFloatingMenu("remove guest");
    }
}
