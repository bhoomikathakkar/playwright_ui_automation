package digifyE2E.pages.landingPage;

import com.microsoft.playwright.options.LoadState;
import digifyE2E.pages.AdminSettings.AdminSettingsPage;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.dataRooms.CreateDataRoomPage;
import digifyE2E.pages.documentSecurity.ManageSentFilesPage;
import digifyE2E.pages.documentSecurity.ViewReceivedFilesPage;
import digifyE2E.pages.mySettings.MySettingsPage;
import digifyE2E.testDataManager.SharedDM;
import io.cucumber.datatable.DataTable;

import java.util.Map;

import static org.testng.Assert.*;

public class HomePage extends CommonUIActions {

    public static final String lnkCreateDataRoom = getXpathForContainsText("Create Data Room", "p");
    public static final String lnkManageSentFile = getXpathForContainsText("Manage Sent File", "p");
    public static final String lnkSendFilesCard = "(//a[@class='btn digify-btn-blue btn-block'])[1]";
    public static final String lnkCreateDataRoomCard = "(//a[@class='btn digify-btn-blue btn-block'])[2]";
    public static final String txtSendFilesHeading = "(//h4[text()=' Send Files '])[1]";
    public static final String txtHomePageTitle = getXpathForContainsText("Home", "h4");
    public static final String lnkSendFiles = "//li[@id='dsb_sen']/a";
    public static final String lnkViewReceivedFiles = "//li[@id='dsb_sen']/following-sibling::li[2]/a";
    public static final String lnkCreateDataroom = "//li[@id='dsb_cre']/a";
    public static final String lnkManageDataRoom = "//li[@id='dsb_cre']/following-sibling::li/a";
    public static final String lblDataRooms = "//li[@id='dsb_cre']/../../a//div/p";
    public static final String lblDocumentSecurity = "//li[@id='dsb_sen']/../../a//div/p";
    public static final String lblIntegrations = "//div[@class='sidebar-menu']/pg-menu-items/ul/li[4]/a//div/p";
    public static final String lnkOverview = "//div[@class='sidebar-menu']/pg-menu-items/ul/li[4]/ul/li[1]/a";
    public static final String lnkDeveloper = "//div[@class='sidebar-menu']/pg-menu-items/ul/li[4]/ul/li[2]/a";
    public static final String lnkHome = "//div[@class='sidebar-menu']/pg-menu-items/ul/li[1]/a";
    public static String HOME_PAGE_URL = "/#/home";
    public static String WHITELABEL_HOME_PAGE_URL = WHITELABEL_URL + "/#/home";

    public static void navigateToPageFromHomePage(String pageType) {
        switch (pageType) {
            case "SendFiles":
                clickElement(getXpathForContainsText("Send Files", "p"));
                waitForTitleToAppear(txtSendFilesHeading);
                break;
            case "ManageSentFiles":
                clickElement(getXpathForContainsText("Manage Sent Files", "p"));
                waitForTitleToAppear(ManageSentFilesPage.txtHeadingManageSentFile);
                break;
            case "ViewReceivedFiles":
                page().waitForLoadState(LoadState.DOMCONTENTLOADED);
                clickElement(getXpathForContainsText("View Received Files", "p"));
                waitForTitleToAppear(ViewReceivedFilesPage.txtViewReceivedFileHeading);
                assertEquals(page().innerText(ViewReceivedFilesPage.txtViewReceivedFileHeading).trim(),
                        "View Received Files");
                break;
            case "ManageDataRoom":
                clickElement(getXpathForContainsText("Manage Data Rooms", "p"));
                waitForTitleToAppear(getXpathForTextEquals
                        (" Manage Data Rooms ", "h4"));
                break;
            case "Admin settings":
                waitUntilSelectorAppears(lnkUserProfile);
                clickElement(lnkUserProfile);
                waitUntilSelectorAppears("//*[@id='dpd_adm']/span");
                clickElement(getXpathForContainsText("Admin Settings", "span"));
                waitForTitleToAppear(AdminSettingsPage.txtAdminSettingsTitle);
                assertTrue(isElementVisible(AdminSettingsPage.txtAdminSettingsTitle));
                break;
            case "CreateDataRoom":
                CreateDataRoomPage.navigateToCreateDRPage();
                break;
            default:
                fail("No match found for page navigation from home page");
        }
    }

    public static void waitForTitleToAppear(String titleSelector) {
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears(titleSelector);
    }


    public static void validateCardTypeOnDashboard(DataTable dataTable) {
        page().waitForLoadState(LoadState.LOAD);
        page().waitForLoadState(LoadState.DOMCONTENTLOADED);
        waitForSeconds(3); //Initially home page takes time to load all content
        dataTable.asMaps().forEach(row -> {
            for (Map.Entry<String, String> column : row.entrySet()) {
                switch (column.getKey().toLowerCase()) {
                    case "document security":
                        assertTrue(isElementVisible("//div[@class='recent-shared']/div[1]"));//document security container
                        assertTrue(isElementVisible("(//div[@class='recent-list-container']/div/div[2]/div/div/div[1])[1]"));
                        assertEquals(getElementText(
                                        "(//div[@class='recent-list-container']/div/div[2]/div/div/div[1])[1]"),
                                "Document Security");
                        assertTrue(isElementVisible("(//div[@class='recent-list-container']/div/div[2]/div/div/div[2])[1]"));
                        assertEquals(getElementText(
                                        "(//div[@class='recent-list-container']/div/div[2]/div/div/div[2])[1]"),
                                "Protect, track and send your important files.");
                        assertTrue(isElementVisible(lnkSendFilesCard));
                        break;
                    case "data room":
                        assertTrue(isElementVisible("//div[@class='recent-shared']/div[2]"));//data room container
                        assertTrue(isElementVisible("(//div[@class='recent-list-container']/div/div[2]/div/div/div[1])[2]"));
                        assertEquals(getElementText(
                                        "(//div[@class='recent-list-container']/div/div[2]/div/div/div[1])[2]"),
                                "Data Room");
                        assertTrue(isElementVisible("(//div[@class='recent-list-container']/div/div[2]/div/div/div[2])[2]"));
                        assertEquals(getElementText(
                                        "(//div[@class='recent-list-container']/div/div[2]/div/div/div[2])[2]"),
                                "Keep control of your files in a secure repository.");
                        assertTrue(isElementVisible(lnkCreateDataRoomCard));
                        break;
                    case "top files in last 7 days":
                        assertTrue(isElementVisible("(//div[@class='recent-shared']/../div[3]/dashboard-analytics-card)[1]"));
                        assertTrue(isElementVisible("(//div[@class='recent-shared']/../div[3]/dashboard-analytics-card)[1]/div/h4"));
                        assertEquals(getElementText(
                                        "(//div[@class='recent-shared']/../div[3]/dashboard-analytics-card)[1]/div/h4"),
                                "Top Files in Last 7 Days");
                        assertTrue(isElementVisible("(//div[@class='recent-shared']/../div[3]/dashboard-analytics-card)[1]/div[2]"));
                        assertEquals(getElementText(
                                        "(//div[@class='recent-shared']/../div[3]/dashboard-analytics-card)[1]/div[2]"),
                                "No activities in the last 7 days");
                        break;
                    case "top data rooms in last 7 days":
                        assertTrue(isElementVisible("(//div[@class='recent-shared']/../div[3]/dashboard-analytics-card)[2]"));
                        assertTrue(isElementVisible("(//div[@class='recent-shared']/../div[3]/dashboard-analytics-card)[2]/div/h4"));
                        assertEquals(getElementText(
                                        "(//div[@class='recent-shared']/../div[3]/dashboard-analytics-card)[1]/div/h4"),
                                "Top Data Rooms in Last 7 Days");
                        assertTrue(isElementVisible("(//div[@class='recent-shared']/../div[3]/dashboard-analytics-card)[2]/div[2]"));
                        assertEquals(getElementText(
                                        "(//div[@class='recent-shared']/../div[3]/dashboard-analytics-card)[2]/div[2]"),
                                "No activities in the last 7 days");
                        break;
                    case "show admin settings":
                        assertTrue(isElementVisible(getXpathForContainsText(
                                "//div[contains(@class,'dashboard-shortcut-block')]/div", "Admin Settings")));
                        break;
                    case "hidden admin settings":
                        assertFalse(isElementVisible(getXpathForContainsText(
                                "//div[contains(@class,'dashboard-shortcut-block')]/div", "Admin Settings")));
                        break;
                    case "my settings":
                        assertTrue(isElementVisible(getXpathForContainsText(
                                "//div[contains(@class,'dashboard-shortcut-block')]/div", "My Settings")));
                        break;
                    default:
                        fail("No action matched for the card type in home page" + column);
                }
            }
        });
    }

    public static void selectCardButton(String cardType) {
        switch (cardType.toLowerCase()) {
            case "recent sent files":
                clickElement(lnkSendFilesCard);
                waitForTitleToAppear(txtSendFilesHeading);
                break;
            case "recent data rooms":
                clickElement(lnkCreateDataRoomCard);
                waitForTitleToAppear(CreateDataRoomPage.txtCreateDataRoomTitle);
                break;
            default:
                fail("Dashboard card type is invalid: " + cardType);
        }
    }

    public static void clickOnHomeButton() {
        clickElement("//a[@href='#/home']");
        waitForTitleToAppear(txtHomePageTitle);
    }

    public static void selectDSViewAll() {
        clickElement("(//div[@class='recent-list-container'])[1]/div/div[1]/div[2]/a");
        waitForTitleToAppear(ManageSentFilesPage.txtHeadingManageSentFile);
        ManageSentFilesPage.assertRecentFileInMSF();
    }

    public static void assertRecentFileInDSCard() {
        assertTrue(isElementVisible("(//div[@class='file-name d-flex text-truncate'])/a[contains(text(),'" + SharedDM.getNewFileNameWithExtension() + "')]"));
    }

    public static void clickFileAnalyticsOnDSCard() {
        assertTrue(isElementVisible("(//div[@class='file-name d-flex text-truncate'])/a[contains(text(),'" + SharedDM.getNewFileNameWithExtension() + "')]/../../../div[3]/a"));
        clickElement("(//div[@class='file-name d-flex text-truncate'])/a[contains(text(),'" + SharedDM.getNewFileNameWithExtension() + "')]/../../../div[3]/a");
    }

    public static void selectShortcutIcons(String shortcutIcon) {
        String lnkInviteMembers = "//div[@routerlink='/a/m']";
        String txtInviteMembers = "//div[@routerlink='/a/m']/div[2]";
        String lnkChangeTOA = "//a[@routerlink='/a/g']";
        String txtChangeTOA = "//a[@routerlink='/a/g']/div[2]";

        String txtChangeLogo = "//div[@routerlink='/a/b']/div[2]";
        String lnkChangeProfile = "//div[@routerlink='/h/g']";
        String txtChangeProfile = "//div[@routerlink='/h/g']/div[2]";
        String lnkChangePassword = "(//div[@routerlink='/h/s'])[1]";
        String txtChangePassword = "(//div[@routerlink='/h/s']/div[2])[1]";
        String lnkSetup2fa = "(//div[@routerlink='/h/s'])[2]";
        String txtSetup2fa = "(//div[@routerlink='/h/s']/div[2])[2]";
        switch (shortcutIcon.toLowerCase()) {
            case "invite members":
                assertTrue(isElementVisible(lnkInviteMembers));
                assertTrue(isElementVisible("//div[@routerlink='/a/m']/div[1]"));
                assertTrue(isElementVisible(txtInviteMembers));
                assertEquals(getElementText(txtInviteMembers), "Invite Members");
                clickElement(lnkInviteMembers);
                waitForURLContaining(AdminSettingsPage.urlMembersTab);
                break;
            case "change terms of access":
                assertTrue(isElementVisible(lnkChangeTOA));
                assertTrue(isElementVisible("//a[@routerlink='/a/g']/div[1]"));
                assertTrue(isElementVisible(txtChangeTOA));
                assertEquals(getElementText(txtChangeTOA), "Change Terms Of Access");
                clickElement(lnkChangeTOA);
                waitForURLContaining(AdminSettingsPage.urlOrgTermsExpanded);
                break;
            case "change logo and colors":
                waitForSeconds(5);
                assertTrue(isElementVisible(AdminSettingsPage.lnkChangeLogo));
                assertTrue(isElementVisible("//div[@routerlink='/a/b']/div[1]"));
                assertTrue(isElementVisible(txtChangeLogo));
                assertEquals(getElementText(txtChangeLogo), "Change Logo & Colors");
                clickElement(AdminSettingsPage.lnkChangeLogo);
                waitForURLContaining(AdminSettingsPage.urlChangeLogoAndColors);
                waitForSeconds(5);
                break;
            case "change profile":
                assertTrue(isElementVisible(lnkChangeProfile));
                assertTrue(isElementVisible("//div[@routerlink='/h/g']/div[1]"));
                assertTrue(isElementVisible(txtChangeProfile));
                assertEquals(getElementText(txtChangeProfile), "Change Profile");
                clickElement(lnkChangeProfile);
                waitForURLContaining(MySettingsPage.urlGeneral);
                break;
            case "change password":
                assertTrue(isElementVisible(lnkChangePassword));
                assertTrue(isElementVisible("(//div[@routerlink='/h/s']/div[2])[1]"));
                assertTrue(isElementVisible(txtChangePassword));
                assertEquals(getElementText(txtChangePassword), "Change Password");
                clickElement(txtChangePassword);
                waitForURLContaining(MySettingsPage.urlSecurity);
                break;
            case "setup 2fa":
                assertTrue(isElementVisible(lnkSetup2fa));
                assertTrue(isElementVisible("(//div[@routerlink='/h/s']/div[2])[1]"));
                assertTrue(isElementVisible(txtSetup2fa));
                assertEquals(getElementText(txtSetup2fa), "Set Up 2FA");
                clickElement(txtSetup2fa);
                waitForURLContaining(MySettingsPage.urlSecurity);
                break;
            default:
                fail("No action matched for the shortcut icons in home page" + shortcutIcon);
        }
    }

    public void accessHomePage() {
        visit(HomePage.HOME_PAGE_URL, true);
        waitForTitleToAppear(txtHomePageTitle);
        assertTrue(isElementVisible(txtHomePageTitle));
    }
}
