package digifyE2E.pages.dataRooms;

import com.microsoft.playwright.options.ElementState;
import com.microsoft.playwright.options.LoadState;
import digifyE2E.pages.CommonUIActions;
import io.cucumber.datatable.DataTable;

import java.util.Map;

import static org.testng.Assert.*;

public class SettingsPage extends CommonUIActions {

    static final String txtDrSettingsTitle = getXpathForContainsText(" Data Room Settings ", "h4");

    public static void getLatestGroupInDRSettings() {
        assertTrue(isElementVisible(txtDrSettingsTitle));
        clickElement("//div[@id='cre_dgp']/div[4]/div[2]/pg-select");
        clickElement("//div[contains(@class,'detailed-options')]/p[contains(text(),'" + DataRoomGroupManager.getLatestGroupName() + "')]/../..");
    }

    public static void getDRAdvSettingsOptionAndPerformAction(String advanceSettingsOptions) {
        String btnTransferOnAdvSettings = "//button[@class='btn digify-btn-blue' and text()=' Transfer ']";
        String drdSelectMember = "//pg-select[@formcontrolname='transferEmail']/div";
        String txtGuestEmailOnMembersTab = "//div[@class='recipient-data']/div[2]/div[2]/span[contains(text(), '" + DataRoomGuestsPage.getGuestUsername + "')]";
        String chkTransferCheck = "//div[@class='checkbox__new digify-checkbox__new']";
        String btnTransfer = "//button[@type='submit']";
        switch (advanceSettingsOptions.toLowerCase()) {
            case "clone":
                selectCloneOptionFromDRAdvSettings(true);
                break;
            case "clone(no dr quota)":
                selectCloneOptionFromDRAdvSettings(false);
                break;
            case "transfer":
                clickElement(btnTransferOnAdvSettings);
                waitUntilSelectorAppears(getXpathForContainsText
                        ("Transfer data room ownership", "h4"));
                clickElement(drdSelectMember);
                assertEquals(getElementText(txtGuestEmailOnMembersTab), DataRoomGuestsPage.getGuestUsername);
                clickElement(txtGuestEmailOnMembersTab);
                waitUntilSelectorAppears(getXpathForContainsText
                        ("I understand that this action cannot be undone.", "label"));
                clickElement(chkTransferCheck);
                clickElement(btnTransfer);
                waitUntilSelectorAppears(getXpathForContainsText("Files", "p"));
                break;
            case "transfer dr when no team member is a guest":
                clickElement(btnTransferOnAdvSettings);
                waitUntilSelectorAppears(CommonUIActions.commonModalHeader);
                assertEquals(getElementText(CommonUIActions.commonModalHeader),
                        "No team member is a guest of this data room");
                assertEquals(getElementText("//div[contains(@class,'subtitle-text normal')]"),
                        "To proceed, please invite a team member to your data room. You can only transfer the ownership of this data room to another team member. Learn more");
                clickElement("//div[@class='modal-body']/div/div[2]/button");
                break;
            case "delete":
                waitUntilSelectorAppears(CommonUIActions.btnDeleteDR);
                clickElement(CommonUIActions.btnDeleteDR);
                CommonUIActions.selectDeleteInDeleteItemModal();
                waitUntilSelectorAppears(ManageDataRoomPage.txtManageDRHeading);
                break;
            default:
                fail("Advance settings options not found: " + advanceSettingsOptions);
        }
    }

    public static void goToAdvancedInDRAndSelectOption(String advanceSettingsOptions) {
        String btnAdvSettings = getXpathForTextEquals(" Advanced Settings ", "p");
        clickElement(btnAdvSettings);
        waitUntilSelectorAppears(getXpathForTextEquals(" Advanced Settings ", "h4"));
        getDRAdvSettingsOptionAndPerformAction(advanceSettingsOptions);
    }

    public static void selectCloneOptionFromDRAdvSettings(boolean isEnoughDRQuota) {
        String btnCloneOnAdvSettings = "//button[@class='btn digify-btn-blue' and text()=' Clone ']";
        if (isEnoughDRQuota) {
            clickElement(btnCloneOnAdvSettings);
            waitUntilSelectorAppears(getXpathForContainsText
                    ("Your data room will not be accessible during cloning", "h4"));
            clickElement(getXpathForContainsText("Continue", "button"));
        } else {
            clickElement(btnCloneOnAdvSettings);
        }
    }

    public static void validateWatermarkSettingsOnDRSettings(DataTable dataTable) {
        String selectedDwColor = "//*[@id=' allow-watermark']/div/div/div[3]/pg-select/div/div/div[2]";
        String selectedDwPosition = "//*[@id=' allow-watermark']/div/div/div[3]/div[4]/div[1]/pg-select/div/div/div[2]";
        dataTable.asMaps().forEach(row -> {
            for (Map.Entry<String, String> column : row.entrySet()) {
                switch (column.getKey().toLowerCase()) {
                    case "color":
                        switch (column.getValue().toLowerCase()) {
                            case "gray":
                                assertEquals(getElementText(selectedDwColor), "Gray");
                                break;
                            case "red":
                                assertEquals(getElementText(selectedDwColor), "Red");
                                break;
                            case "blue":
                                assertEquals(getElementText(selectedDwColor), "Blue");
                                break;
                            default:
                                fail("Validation failed for dw color, value is: " + column.getValue());
                        }
                        break;
                    case "position":
                        switch (column.getValue().toLowerCase()) {
                            case "tile":
                                assertEquals(getElementText(selectedDwPosition), "Tile");
                                break;
                            case "center":
                                assertEquals(getElementText(selectedDwPosition), "Center");
                                break;
                            case "top right":
                                assertEquals(getElementText(selectedDwPosition), "Top right");
                                break;
                            default:
                                fail("Validation failed for dw position, value is: " + column.getValue());
                        }
                        break;
                    case "date and time":
                        switch (column.getValue().toLowerCase()) {
                            case "checked":
                                assertTrue(isChecked(CommonUIActions.dwDateAndTimeElement));
                                waitForSeconds(1);
                                break;
                            case "unchecked":
                                waitForSeconds(3); //the element not updated and returning wrong state
                                waitUntilSelectorAppears("//*[@id='wk-time']");
                                boolean isDateAndTimeChecked = (boolean) page().evaluate("element => element.checked",
                                        page().querySelector("//*[@id='wk-time']"));
                                assertFalse(isDateAndTimeChecked, "date and time checkbox is not checked");
                                break;
                            default:
                                fail("Validation failed for date and time, value is: " + column.getValue());
                        }
                        break;
                    case "ip address":
                        switch (column.getValue().toLowerCase()) {
                            case "checked":
                                assertTrue(isChecked(CommonUIActions.dwIpAddElement));
                                break;
                            case "unchecked":
                                boolean isIpAddChecked = (boolean) page().evaluate("element => element.checked",
                                        page().querySelector("//*[@id='wk-ip']"));
                                assertFalse(isIpAddChecked, "IP address checkbox is not checked");
                                break;
                            default:
                                fail("Validation failed for ip address, value is: " + column.getValue());
                        }
                        break;
                }
            }
        });
    }


    public static void saveDrSettings() {
        page().waitForLoadState(LoadState.LOAD);
        waitForSeconds(1);
        waitUntilSelectorAppears(CommonUIActions.btnCommonSave);
        waitForSelectorStateChange(CommonUIActions.btnCommonSave, ElementState.ENABLED);
        assertTrue(isElementVisible(CommonUIActions.btnCommonSave));
        clickElement(CommonUIActions.btnCommonSave);
        validateToastMsg(CommonUIActions.txtCommonToastMsg, "Data room settings updated.");
        page().waitForLoadState(LoadState.LOAD);
    }
}
