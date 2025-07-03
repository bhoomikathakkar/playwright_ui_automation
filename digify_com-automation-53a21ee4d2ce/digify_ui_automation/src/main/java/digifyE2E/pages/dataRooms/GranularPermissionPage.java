package digifyE2E.pages.dataRooms;

import com.microsoft.playwright.options.LoadState;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.documentSecurity.SendFilesPage;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GranularPermissionPage extends CommonUIActions {

    enum PermissionTypeInGP {NO_ACCESS, VIEW, PRINT, DOWNLOAD_PDF, DOWNLOAD_ORIGINAL, EDIT}

    public static void openGAPOnAddGuestPage() {
        page().waitForLoadState(LoadState.LOAD);
        clickElement("//*[@id='targetPermission']/div[2]/div/div[2]/div/div[3]/a");
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears("//div[@class='modal-header']/h3");
    }

    public static int getGPCheckboxNum(String gpPermTypeAndRowNum) {
        String row = gpPermTypeAndRowNum.trim().split(",")[0];
        String permissionTypeGP = gpPermTypeAndRowNum.trim().split(",")[1];
        int level = PermissionTypeInGP.valueOf(permissionTypeGP.toUpperCase()).ordinal();
        int rowNumber = Integer.parseInt(row.toLowerCase().split("row")[1]);
        return (rowNumber > 1 ? ((rowNumber - 1) * 6) + 1 : rowNumber) + level;
    }

    public static void setPermissionInGP(String setPermInGP) {
        String chkFileEditGP = "(//label[contains(@class,'permission-radio')]/span/i)[" + getGPCheckboxNum(setPermInGP) + "]";
        clickElement(chkFileEditGP);
    }

    public static void validatePermissionOnGP(String validatePermissionOnGP) {
        String chkFileEditGP = "(//label[contains(@class,'permission-radio')]/span/i)[" + getGPCheckboxNum(validatePermissionOnGP) + "]";
        page().waitForLoadState(LoadState.LOAD);
        assertTrue(page().locator(chkFileEditGP).isChecked());
    }

    public static void clickGPLink(boolean drGroup) {
        if (drGroup) {
            clickElement("(//div[@class='clear'])[1]/div/a"); //todo:make it same
        } else {
            clickElement("(//a[@role='button'])[2]");
        }
        waitUntilSelectorAppears(CommonUIActions.txtGPHeading);
        waitUntilSelectorAppears("//div[contains(@class,'tree-title')]");
    }

    public static int getPermissionWithColumnInGP(String clickOnPermissionInGP) {
        List<String> parts = Arrays.asList(clickOnPermissionInGP.split(","));
        return Integer.parseInt(parts.get(0));
    }

    public static void selectPermissionInGPPage(String getPermissionWithColumnInGP) {
        int permissionColumn = getPermissionWithColumnInGP(getPermissionWithColumnInGP);
        String chkFileEditGP = "(//label[@class='permission-radio']/span/i)[" + permissionColumn + "]";
        clickElement(chkFileEditGP);
    }

    public static void selectViewAsInGP(String viewAsInGP) {
        CommonUIActions.selectValueFromDropdownList("//pg-select[@formcontrolname='overview']",
                SendFilesPage.ddElementList, viewAsInGP.trim().toLowerCase());
    }

    public static void selectEditPermInModal() {
        waitUntilSelectorAppears(getXpathForContainsText
                ("Set \"Edit\" permissions for nested files/folders?", "h4"));
        assertEquals(getElementText("//div[@class='sub-text content-block']/div"),
                "Your guests will be able to upload and replace any file in the folder. They will also be able to delete files they've uploaded.");
        clickElement("(//div[@class='modal-content'])[2]/div[2]/div[2]/button[2]");
    }

    public static void validateManageAccessPageForFile() {
        String txtManageAccessHeader = "//div[@class='modal-header relative']";
        waitUntilSelectorAppears(txtManageAccessHeader);
        assertTrue(isElementVisible(txtManageAccessHeader));
    }
}

