package digifyE2E.pages.dataRooms;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.options.ElementState;
import com.microsoft.playwright.options.LoadState;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.documentSecurity.SendFilesPage;
import digifyE2E.pages.landingPage.HomePage;
import digifyE2E.pages.landingPage.LoginPage;
import digifyE2E.testDataManager.SharedDM;
import digifyE2E.utils.FileUtils;
import digifyE2E.utils.LogUtils;
import io.cucumber.datatable.DataTable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static digifyE2E.pages.MailDropEmailService.getOTPFromMailDrop;
import static org.testng.Assert.*;

public class CreateDataRoomPage extends CommonUIActions {

    public static final String drdDRUserInfo = "//app-avatar";
    public static final String chkConfirmationDeleteDR = "//label[@for='confirmation']";
    public static final String btnDeleteInWarningModal = "//button[@class='btn digify-btn-warning']";
    public static final String txtGuestHeading = getXpathForTextEquals(" Guests ", "h4");
    public static final String txtCreateDataRoomTitle = "//app-create-dataroom/div/div[1]//h4";
    static final String txtDRFiles = "//*[@id='page-title-header']//app-data-room-doc-breadcrumb//p";
    static final String txtViewableAreaScreenShieldDR = getXpathForContainsText("Viewable Area", "p");
    static final String containerExpirySection = "//div[@id='expiry']";
    static final String txtEnterDataRoomName = "//*[@id='cre_nam']/div/div/div[2]/div[1]/input";
    static final String containerBannerUpload = "//div[contains(text(),'Banner Image')]/../../div[2]/div";
    static final String btnCreateDataRoom = "//*[@id='cre_btn']";
    static final String drdDrTOA = "(//*[@id='cre_dgp']/following-sibling::div/div/div[2]/pg-select/div)[1]";
    static final String containerWatermarkSection = "//div[@id=' allow-watermark']";
    static final String tglQnA = "//*[@id='cre_qna']/div/div[2]/pg-switch";
    static final String containerQnA = "//*[@id='cre_qna']/div/div[2]/div";
    static final String tglQnAEveryone = "//*[@id='qandaselectioneveryone']/../label";
    static final String tglQnAGroup = "//*[@id='qandaselectiongroup']/../label";
    static final String tglQnAOwners = "//*[@id='qandaselectionowners']/../label";
    static final String txtLookingForSomething = "(//div[@class='sub-dataroom-layout has-error']/div/div[2]/p)[1]";
    static final String lnkGuestTabInDR = getXpathForTextEquals("GUESTS", "a");
    static final String lnkAboutTabInDR = getXpathForTextEquals("ABOUT PAGE", "a");
    static final String lnkFilesTabInDR = getXpathForTextEquals("FILES", "a");
    static final String lnkAnalyticsTabInDr = getXpathForTextEquals("ANALYTICS", "a");
    static final String lnkSettingsTabInDr = getXpathForTextEquals("SETTINGS", "a");
    static final String lblReqAdditionalInfo = "//label[@for='reqAdditionalInfo']";
    static final String tglAboutPage = "//div[@id='cre_abt']/div/div[2]/div/pg-switch/span";
    static final String lblAboutPageLanding = "//input[@id='landing-about-page']/../label";

    private static void addCustomToAContent() {
        inputIntoTextField("//div[@id='termsofaccess']/div/div[2]/div[2]/textarea",
                "All information contained in these documents is confidential.");
    }

    public static void addDrName() {
        inputIntoTextField(txtEnterDataRoomName, SharedDM.getDataRoomName());
    }

    public static void clickOnCreateDrBtn() {
        clickElement(btnCreateDataRoom);
        waitUntilSelectorAppears(txtDRFiles);
        closeIntercomModal();
    }

    private static void setQnA(String qnaOption) {
        String qnaModalTitle = getXpathForContainsText
                ("Your guests will see the names of other guests in the data room", "h4");
        switch (qnaOption) {
            case "qna toggle on":
                if (isElementVisible(containerQnA)) {
                    assertTrue(isElementVisible("(//*[@id='cre_qna']/div/div[2]/div/div/div)[1]"));
                } else {
                    clickElement(tglQnA);
                    waitForSelectorStateChange(tglQnA, ElementState.STABLE);
                    assertTrue(isElementVisible(containerQnA));
                }
                break;
            case "qna toggle off":
                if (isElementVisible(containerQnA)) {
                    clickElement(tglQnA);
                    waitForSelectorStateChange(tglQnA, ElementState.STABLE);
                }
                page().waitForLoadState(LoadState.LOAD);
                LogUtils.infoLog("QNA toggle is not enabled");
                break;
            case "qna group":
                dispatchClickElement(tglQnAGroup);
                waitUntilSelectorAppears(qnaModalTitle);
                assertTrue(isElementVisible(qnaModalTitle));
                clickElement(btnOk);
                break;
            case "qna everyone":
                clickElement(tglQnAEveryone);
                waitUntilSelectorAppears(qnaModalTitle);
                assertTrue(isElementVisible(qnaModalTitle));
                clickElement(btnOk);
                break;
            case "qna owners":
                clickElement(tglQnAOwners);
                break;
            default:
                fail("DR QnA option is not found: " + qnaOption);
        }
    }

    public static void addDomain() {
        inputIntoTextField("//input[@formcontrolname='domain']", "@vomoto.com");
        inputIntoTextField("//input[@formcontrolname='domain']", "@gmail.com");
        clickElement(getXpathForContainsText("Add Domain", "button"));
        waitUntilSelectorAppears("//*[@id='domain']/div/div[2]/div/ul");
    }

    private static void selectAccessTypeInDR(String drAccessOption) {
        switch (drAccessOption.trim()) {
            case "Only people I specify", "Anyone with the link or file (email verification)":
                clickElement(getXpathForContainsText(
                        drAccessOption, "li"));
                break;
            case "Only people from domains I specify":
                clickElement(getXpathForContainsText(
                        drAccessOption, "li"));
                addDomain();
                break;
            default:
                fail("Access type does not match in data room " + drAccessOption);
        }
    }

    public static void enableDRFeatures(List<String> drFeatures) {
        String ddEnableForAllFilesAndGuestSS = "//div[@class='cdk-overlay-pane']/div/div/ul/li[text() =' Enable for all files and guests ']";
        String ddEnableIndividuallyInGP = "//div[@class='cdk-overlay-pane']/div/div/ul/li[text() =' Enable individually for files or guests ']";
        String tglAccess = "//*[@id='cre_sec']/div[1]/div[1]/div[2]/pg-select/div";
        String drdDRPermission = "//*[@id='cre_dgp']/div/div[2]/pg-select/div";
        String tglWhatIfExcel = "//div[contains(text(),'Excel What-If Analysis')]/../../div[2]/div/pg-switch";

        String tglBannerImg = "//div[contains(text(),'Banner Image')]/../../div[2]/pg-switch";
        String tglMsOfficeEditor = "//*[@id='cre_pre']/div[3]/div/div[2]/div/pg-switch/span";
        String txtAboutMsOffice = "//*[@id='cre_pre']/div[3]/div[1]/div[2]/div[2]";
        String tglAdvanceSettings = "//div[contains(text(),'Advanced Settings')]/../../div[2]/pg-switch";
        String containerAdvanceSettingsOptions = "//div[contains(text(),'Use defaults')]/../../../div[2]/div[2]";
        String tglFileIndex = "//div[contains(text(),'File Index')]/../../div[1]/pg-switch";
        String tglModifiedDate = "//div[contains(text(),'Modified Date')]/../../div[2]/pg-switch";

        drFeatures.forEach(feature -> {
            page().waitForLoadState(LoadState.LOAD);
            switch (feature.trim()) {
                case "dynamic watermark":
                    waitUntilSelectorAppears(getXpathForContainsText("Dynamic", "div"));
                    clickElement("(//div[contains(text(),'Dynamic')]//following::div[1]//span)[1]");
                    waitUntilSelectorAppears(containerWatermarkSection);
                    break;
                case "screen shield-->enable for all":
                    clickElement(getXpathForContainsText("Screen Shield", "div") + "/../../div[2]/pg-select/div");
                    clickElement(ddEnableForAllFilesAndGuestSS);
                    waitForSelectorStateChange(ddEnableForAllFilesAndGuestSS, ElementState.STABLE);
                    waitUntilSelectorAppears(txtViewableAreaScreenShieldDR);
                    break;
                case "screen shield-->enable individually in gp":
                    waitForSeconds(1);
                    clickElement(getXpathForContainsText("Screen Shield", "div") + "/../../div[2]/pg-select/div");
                    clickElement(ddEnableIndividuallyInGP);
                    waitForSelectorStateChange(ddEnableIndividuallyInGP, ElementState.STABLE);
                    waitUntilSelectorAppears(txtViewableAreaScreenShieldDR);
                    break;
                case "Expiry":
                    clickElement("(//div[contains(text(),'Data Room Expiry')]//following::div[1]//span)[1]");
                    waitUntilSelectorAppears(containerExpirySection);
                    break;
                case "Only people I specify", "Anyone with the link or file (email verification)",
                     "Only people from domains I specify":
                    clickElement(tglAccess);
                    waitForSeconds(1);
                    selectAccessTypeInDR(feature);
                    waitForSelectorStateChange(tglAccess, ElementState.STABLE);
                    validateEnforceVerificationTooltip();
                    break;
                case "No Access", "Download (Original)", "Print", "Download (PDF)", "View", "Edit", "Group permissions":
                    clickElement(drdDRPermission);
                    selectPermissionTypeInDR(feature);
                    break;
                case "Use organization terms":
                    clickElement(drdDrTOA);
                    clickElement(getXpathForContainsText(feature, "span") + "/../../..");
                    waitForSelectorStateChange(drdDrTOA, ElementState.STABLE);
                    break;
                case "Use custom terms":
                    clickElement(drdDrTOA);
                    clickElement(getXpathForContainsText(feature, "span") + "/../../..");
                    waitForSelectorStateChange(drdDrTOA, ElementState.STABLE);
                    addCustomToAContent();
                    break;
                case "what-if excel":
                    page().querySelector(tglWhatIfExcel).isEnabled();
                    assertEquals(getElementText("//div[contains(text(),' Excel What-If Analysis ')]/../../div[2]/div[2]"),
                            "Modify and preview spreadsheet changes in the file viewer without saving. Not available in MS editor mode, if enabled.");
                    break;
                case "pro plan-->what-if excel":
                    page().querySelector(tglWhatIfExcel).isEnabled();
                    assertEquals(getElementText("//div[contains(text(),' Excel What-If Analysis ')]/../../div[2]/div[2]"),
                            "You and your guests can modify the cell values in the spreadsheet, but not save or download the changes.");
                    break;
                case "disable-->what-if excel":
                    clickElement(tglWhatIfExcel);
                    break;
                case "enable-->about page":
                    if (!isElementVisible(lblAboutPageLanding)) {
                        clickElement(tglAboutPage);
                        waitForSelectorStateChange(tglAboutPage, ElementState.STABLE);
                        assertTrue(isElementVisible(lblAboutPageLanding));
                    } else {
                        assertTrue(isElementVisible(lblAboutPageLanding));
                    }
                    break;
                case "disable-->about page":
                    clickElement(tglAboutPage);
                    waitForSelectorStateChange(tglAboutPage, ElementState.STABLE);
                    assertFalse(isElementVisible(lblAboutPageLanding));
                    break;
                case "disable-->banner image":
                    waitForSelectorStateChange(tglBannerImg, ElementState.STABLE);
                    waitForSeconds(1);
                    clickElement(tglBannerImg);
                    waitForSelectorStateChange(tglBannerImg, ElementState.STABLE);
                    assertFalse(isElementVisible(containerBannerUpload));
                    break;
                case "enable-->banner image":
                    waitForSelectorStateChange(tglBannerImg, ElementState.STABLE);
                    waitForSeconds(1);
                    clickElement(tglBannerImg);
                    waitForSelectorStateChange(tglBannerImg, ElementState.STABLE);
                    assertTrue(isElementVisible(containerBannerUpload));
                    break;
                case "enable-->Editing with MS office":
                    if (isElementVisible(txtAboutMsOffice)) {
                        assertEquals(getElementText(txtAboutMsOffice), normalizeSpaceForString(
                                "Owners and editors can edit files in the data room. A Microsoft Office license is required and subject to Microsoft's terms. Learn more"));
                    } else {
                        clickElement(tglMsOfficeEditor);
                        waitUntilSelectorAppears(txtAboutMsOffice);
                        assertEquals(getElementText(txtAboutMsOffice), normalizeSpaceForString(
                                "Owners and editors can edit files in the data room. A Microsoft Office license is required and subject to Microsoft's terms. Learn more"));
                    }
                    break;
                case "disable-->editing with MS office":
                    clickElement(tglMsOfficeEditor);
                    assertFalse(isElementVisible(txtAboutMsOffice));
                    break;
                case "adv settings-->disable file index":
                    clickElement(tglAdvanceSettings);
                    waitForSelectorStateChange(containerAdvanceSettingsOptions, ElementState.STABLE);
                    clickElement(tglFileIndex);
                    waitForSeconds(2);
                    waitForSelectorStateChange(tglFileIndex, ElementState.STABLE);
                    waitForSelectorStateChange(containerAdvanceSettingsOptions, ElementState.STABLE);
                    break;
                case "adv settings-->enable file index":
                    assertTrue(isElementVisible(containerAdvanceSettingsOptions));
                    clickElement(tglFileIndex);
                    waitForSeconds(2);
                    waitForSelectorStateChange(tglFileIndex, ElementState.STABLE);
                    waitForSelectorStateChange(containerAdvanceSettingsOptions, ElementState.STABLE);
                    break;
                case "adv settings-->disable modified date":
                    clickElement(tglAdvanceSettings);
                    waitForSelectorStateChange(containerAdvanceSettingsOptions, ElementState.STABLE);
                    clickElement(tglModifiedDate);
                    break;
                case "adv settings-->enable modified date":
                    waitForSelectorStateChange(containerAdvanceSettingsOptions, ElementState.STABLE);
                    assertTrue(isElementVisible(containerAdvanceSettingsOptions));
                    clickElement(tglModifiedDate);
                    break;
                case "guest list-->show everyone":
                    clickElement(tglAdvanceSettings);
                    waitUntilSelectorAppears(containerAdvanceSettingsOptions);
                    selectValueFromDropdownList(
                            "(//div[contains(@class,'advanced-section-header option')])[3]/../pg-select",
                            SendFilesPage.ddElementList, "Show everyone");
                    break;
                case "guest list-->show owners and same people in a group":
                    clickElement(tglAdvanceSettings);
                    waitUntilSelectorAppears(containerAdvanceSettingsOptions);
                    selectValueFromDropdownList(
                            "(//div[contains(@class,'advanced-section-header option')])[3]/../pg-select",
                            SendFilesPage.ddElementList, "Show owners, and people in the same group");
                    break;
                case "20% viewable area", "35% viewable area", "50% viewable area":
                    SendFilesPage.getSendFileSettings(Collections.singletonList(feature));
                    break;
                case "qna toggle on", "qna toggle off", "qna group", "qna everyone", "qna owners":
                    setQnA(feature);
                    break;
                case "dynamic watermark upgrade":
                    page().waitForLoadState(LoadState.LOAD);
                    clickElement("(//span[contains(@class, 'new-premium-addons-label')])[1]/../div/div");
                    break;
                case "screen shield upgrade":
                    page().waitForLoadState(LoadState.LOAD);
                    clickElement("(//span[contains(@class, 'new-premium-addons-label')])[2]/../div/div");
                    break;
                case "q&a upgrade":
                    page().waitForLoadState(LoadState.LOAD);
                    clickElement("(//span[contains(@class, 'new-premium-addons-label')])[4]/../div/div");
                    break;
                case "req additional info", "disable->rai":
                    waitForSeconds(2);
                    waitUntilSelectorAppears(lblReqAdditionalInfo);
                    clickElement(lblReqAdditionalInfo);
                    break;
                case "rai->name":
                    clickElement(CommonUIActions.lblName);
                    break;
                case "rai->phone":
                    clickElement(CommonUIActions.lblPhone);
                    break;
                case "rai->job":
                    clickElement(CommonUIActions.lblJob);
                    break;
                case "rai->company":
                    clickElement(CommonUIActions.lblCompany);
                    break;
                case "enforce email verification":
                    CommonUIActions.validateAndEnableEnforceEmailVerification();
                    break;
                default:
                    fail("No match found for data room feature type" + feature);
            }
        });
    }


    public static void createDataRoom(String dataRoomName, List<String> drFeatures) {
        clickElement(HomePage.lnkCreateDataRoom);
        inputIntoTextField(txtEnterDataRoomName, dataRoomName);
        if (Objects.nonNull(drFeatures) && !drFeatures.isEmpty())
            enableDRFeatures(drFeatures);
        clickOnCreateDrBtn();
    }

    public static String getDrNameHeading() {
        clickElement("//div[@id='drInfoDropdown']/span"); //Data room title
        return getElementText("//div[@id='drInfoDropdown']/following-sibling::div/div/div/div[1]");
    }

    public static void verifyFeaturesSettings(List<String> validateFeatureType) {
        validateFeatureType.forEach(feature -> {
            switch (feature.trim()) {
                case "dynamic watermark":
                    waitUntilSelectorAppears(getXpathForContainsText
                            ("Display", "p"));
                    break;
                case "screen shield-->enable for all":
                    assertEquals(getElementText
                                    ("//*[@id='cre_sec']/div[6]/div/div[2]/pg-select/div/div/div[2]"),
                            "Enable for all files and guests");
                    waitUntilSelectorAppears(txtViewableAreaScreenShieldDR);
                    break;
                case "screen shield-->enable individually in gp":
                    assertEquals(getElementText
                                    ("//*[@id='cre_sec']/div[6]/div/div[2]/pg-select/div/div/div[2]"),
                            "Enable individually for files or guests");
                    waitUntilSelectorAppears(txtViewableAreaScreenShieldDR);
                    break;
                case "20% viewable area":
                    isChecked("//*[@id='screenshotprotection20']");
                    break;
                case "35% viewable area":
                    isChecked("//*[@id='screenshotprotection35']");
                    break;
                case "50% viewable area":
                    isChecked("//*[@id='screenshotprotection50']");
                    break;
                case "expiry":
                    waitUntilSelectorAppears(containerExpirySection);
                    assertTrue(isElementVisible(containerExpirySection));
                    break;
                case "Anyone with the link or file (email verification)", "Only people from domains I specify",
                     "Only people I specify":
                    waitForSelectorStateChange(getXpathForContainsText(feature, "div"), ElementState.STABLE);
                    waitUntilSelectorAppears(getXpathForContainsText(feature, "div"));
                    break;
                case "No Access", "Download (PDF)", "Print", "View", "Edit", "Download (Original)":
                    assertTrue(isElementVisible(getXpathForContainsText(feature, "div")));
                    break;
                case "qna toggle on":
                    assertTrue(isElementVisible(containerQnA));
                    assertTrue(isElementVisible(tglQnA));
                    break;
                case "qna toggle off":
                    assertFalse(isElementVisible(containerQnA));
                    break;
                case "qna everyone":
                    assertTrue(page().querySelector(tglQnAEveryone).isEnabled());
                    break;
                case "qna group":
                    assertTrue(page().querySelector(tglQnAGroup).isEnabled());
                    break;
                case "qna owners":
                    assertTrue(page().querySelector(tglQnAOwners).isEnabled());
                    break;
                case "req additional info":
                    assertTrue(isDisabled(lblReqAdditionalInfo));
                    break;
                case "rai->name":
                    assertTrue(isChecked(CommonUIActions.lblName));
                    break;
                case "rai->phone":
                    assertTrue(isChecked(CommonUIActions.lblPhone));
                    break;
                case "rai->job":
                    assertTrue(isChecked(CommonUIActions.lblJob));
                    break;
                case "rai->company":
                    assertTrue(isChecked(CommonUIActions.lblCompany));
                    break;
                case "about page toggle on":
                    assertTrue(isElementVisible(tglAboutPage));
                    assertTrue(isElementVisible(lblAboutPageLanding));
                    break;
                case "enforce email verification":
                    assertTrue(CommonUIActions.isChecked("//input[@id='verification']"));
                    break;
                default:
                    fail("No match found for data room feature type validation: " + feature);
            }
        });
    }


    public static void navigateToDataRoomTabs(String drTabs) {
        switch (drTabs.toLowerCase()) {
            case "files":
                page().waitForLoadState(LoadState.LOAD);
                clickElement(lnkFilesTabInDR);
                if (isElementVisible(CommonUIActions.btnCommonBtnForContinue)) {
                    SettingsPage.saveDrSettings();
                }
                if (!isElementVisible(txtDRFiles)) {
                    clickElement(lnkFilesTabInDR);
                }
                waitUntilSelectorAppears(txtDRFiles);
                break;
            case "about":
                clickElement(lnkAboutTabInDR);
                break;
            case "guests":
                clickElement(lnkGuestTabInDR);
                if (!isElementVisible(txtGuestHeading)) {
                    page().reload();
                    page().waitForLoadState(LoadState.LOAD);
                    clickElement(lnkGuestTabInDR);
                }
                break;
            case "analytics":
                clickElement(lnkAnalyticsTabInDr);
                break;
            case "settings":
                waitForSeconds(3); //due to fast execution the navigation getting skipped
                clickElement(lnkSettingsTabInDr);
                if (!isElementVisible(SettingsPage.txtDrSettingsTitle)) {
                    page().reload();
                    page().waitForLoadState(LoadState.LOAD);
                }
                waitUntilSelectorAppears(SettingsPage.txtDrSettingsTitle);
                break;
            case "files tab from trash can":
                clickElement("(//span[text()='Folders'])[1]");
                waitUntilSelectorAppears(txtDRFiles);
                break;
            case "export":
                clickElement("//a//p[contains(text(),'Export')]");
                waitUntilSelectorAppears(getXpathForContainsText(" Export ", "h4"));
                break;
            default:
                fail("No match found for data room tabs: " + drTabs);
        }
    }


    public static void doLogoutFromDR() {
        page().waitForLoadState(LoadState.LOAD);
        waitForSeconds(2);
        boolean isLogoutClicked = clickLogoutLinkInDRUserInfoDropdown(); // Attempt to click on the logout link under user info dropdown

        if (!isLogoutClicked) {
            page().reload();
            isLogoutClicked = clickLogoutLinkInDRUserInfoDropdown();
        }
        if (!isLogoutClicked) {
            LogUtils.infoLog("Logout link not clicked even after reload.");
            return; // Exit method
        }
        waitForSelectorAppearsWithTimeout(imgLoginPageBrandingLogo, 80);
    }

    private static boolean clickLogoutLinkInDRUserInfoDropdown() {
        if (!isElementVisible(drdDRUserInfo)) {
            waitUntilSelectorAppears(drdDRUserInfo);
        }
        clickElement(drdDRUserInfo);
        if (!isElementVisible(CommonUIActions.lnkLogout)) {
            return false;
        }
        clickElement(CommonUIActions.lnkLogout);
        return true;
    }

    public static void navigateToDataRoomEntity(boolean asNewUser, boolean isDR, Boolean isFileOrFolderLink) {
        if (isDR) {
            LogUtils.infoLog("Navigating to the DR: " + SharedDM.getSentDataRoomLink());
            CommonUIActions.visitDRLink();
        } else if (isFileOrFolderLink) {
            LogUtils.infoLog("Navigating to the DR file/folder link now: " + SharedDM.getSentDRFileOrFolderLink());
            visit(SharedDM.getSentDRFileOrFolderLink(), false);
        }

        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears(getXpathForTextEquals(" Verify your identity to view the data room ", "p"));
        LogUtils.infoLog("Navigating to DR link: " + page().url());
        if (asNewUser)
            LoginPage.getVerificationCodeAsNewUser();
    }

    public static void selectPermissionTypeInDR(String drPermissionType) {
        switch (drPermissionType) {
            case "No Access", "Granular Permissions", "Co-Owner", "Edit", "Download (Original)", "View", "Print",
                 "Download (PDF)", "Group permissions":
                clickElement(getXpathForTextEquals(drPermissionType, "*"));
                break;
            default:
                fail("Permission type is not found: " + drPermissionType);
        }
    }

    public static void createClonedDataRoom(String clonedDataRoomName, List<String> drFeatures) {
        waitUntilSelectorAppears(getXpathForContainsText("Clone", "h4"));
        inputIntoTextField(txtEnterDataRoomName, clonedDataRoomName);

        if (Objects.nonNull(drFeatures) && !drFeatures.isEmpty())
            enableDRFeatures(drFeatures);

        clickElement(getXpathForContainsText("Clone data room", "span") + "/../..");
        waitUntilSelectorAppears(getXpathForContainsText
                ("Cloning data room now", "p"));
        clickElement(getXpathForContainsText
                ("Go to data room list", "button"));
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears(getXpathForContainsText("Manage Data Rooms", "p"));
    }

    public static void closeIntercomModal() {
        ElementHandle iframe = page().querySelector("#intercom-container");
        if (iframe != null) {
            iframe.evaluate("this.style.display = 'none'");
        }
    }

    public static void validateAndContinueWithTOA(String drUserType) {
        if (drUserType.equalsIgnoreCase("dr owner") ||
                drUserType.equalsIgnoreCase("dr co-owner")) {
            waitUntilSelectorAppears(CommonUIActions.commonModalHeader);
            assertEquals(getElementText(CommonUIActions.commonModalHeader),
                    "You've turned on “Terms of Access”");
            assertTrue(isElementVisible(CommonUIActions.txtToAModalBody));
            clickElement("//input[@id='skip-terms']/..");
            clickElement(CommonUIActions.btnContinue);
            waitUntilSelectorAppears(getXpathForContainsText("Files", "p"));
        } else {
            waitUntilSelectorAppears(CommonUIActions.txtGuestTOAHeader);
            assertEquals(getElementText(CommonUIActions.txtGuestTOAHeader), "Terms of Access");
            assertTrue(isElementVisible(CommonUIActions.txtToAModalBody));
            CommonUIActions.agreeToA();
            waitUntilSelectorAppears(getXpathForContainsText("Files", "p"));
        }
    }

    public static void declineTOA() {
        waitUntilSelectorAppears(CommonUIActions.txtGuestTOAHeader);
        clickElement(CommonUIActions.btnDeleteDR);
        waitUntilSelectorAppears(CommonUIActions.txtAccessDeniedErrorMsg);
        waitForSeconds(2);
        assertEquals(getElementText(getXpathForContainsText("You need to accept these Terms of Access before you can access this data room.","p")),
                normalizeSpaceForString("You need to accept these Terms of Access before you can access this data room."));
    }

    public static void refreshDataRoomAndCloseToA() {
        clickElement(getXpathForTextEquals("Refresh", "button"));
        waitUntilSelectorAppears(CommonUIActions.txtGuestTOAHeader);
        clickElement(CommonUIActions.closeTOAModal);
    }

    public static void validateAboutPageTabInDR(boolean isAboutPageEnabled) {
        String lnkGetFileLinkAboutPage = "//i[contains(@class,'far fa-link')]/..";
        if (isAboutPageEnabled) {
            waitUntilSelectorAppears(lnkAboutTabInDR);
            assertTrue(isElementVisible(lnkAboutTabInDR));
            clickElement(lnkAboutTabInDR);
            waitUntilSelectorAppears(lnkGetFileLinkAboutPage);
            assertTrue(isElementVisible(lnkGetFileLinkAboutPage));
        } else {
            assertFalse(isElementVisible(lnkAboutTabInDR));
        }
    }

    public static void validateBannerImageVisibilityInDR(boolean isBannerImagePresent) {
        String imgDrBanner = "//app-data-room-banner[@dataroomtab='document']/div";
        clickElement(lnkFilesTabInDR);
        page().waitForLoadState(LoadState.LOAD);
        if (isBannerImagePresent) {
            assertTrue(isElementVisible(imgDrBanner));
        } else {
            assertFalse(isElementVisible(imgDrBanner));
        }
    }

    public static void validateDrErrorMsg(String drErrorMsg) {
        switch (drErrorMsg.toLowerCase()) {
            case "access denied":
                page().waitForLoadState(LoadState.LOAD);
                waitUntilSelectorAppears(CommonUIActions.txtAccessDeniedErrorMsg);
                waitUntilSelectorAppears(getXpathForContainsText("Request access", "button"));
                assertTrue(isElementVisible(getXpathForTextEquals(
                        "Request access", "button")));
                break;
            case "no groups available":
                assertEquals(getElementText("(//div[contains(@class, 'digify-error-text')])[2]"),
                        "No groups available. Create new group");
                break;
            default:
                fail("No match found for the data room error message: " + drErrorMsg);
        }
    }

    public static void getDynamicWatermarkSettings(DataTable dataTable, boolean isDwEnabled) {
        page().waitForLoadState(LoadState.LOAD);
        waitForSeconds(2);
        String dwColorElement = "//*[@id=' allow-watermark']/div/div/div[3]/pg-select/div/div";
        String dwPositionElement = "//*[@id=' allow-watermark']/div/div/div[3]/div[4]/div[1]/pg-select/div";
        if (isDwEnabled) {
            enableDRFeatures(Collections.singletonList("dynamic watermark"));
            waitUntilSelectorAppears(containerWatermarkSection);
        }
        dataTable.asMaps().forEach(row -> {
            for (Map.Entry<String, String> column : row.entrySet()) {
                switch (column.getKey().toLowerCase()) {
                    case "color":
                        switch (column.getValue().toLowerCase()) {
                            case "gray", "red", "blue":
                                CommonUIActions.selectValueFromDropdownList(dwColorElement,
                                        SendFilesPage.ddElementList, column.getValue().toLowerCase());
                                break;
                            default:
                                fail("dynamic watermark color does not match with the value: " + column.getKey());
                        }
                        break;
                    case "position":
                        switch (column.getValue().toLowerCase()) {
                            case "tile", "top right":
                                waitUntilSelectorAppears(dwPositionElement);
                                CommonUIActions.selectValueFromDropdownList(dwPositionElement,
                                        SendFilesPage.ddElementList, column.getValue().toLowerCase());
                                if (isElementVisible(getXpathForContainsText("Watermarks for Excel will vary", "h4"))) {
                                    clickElement(btnOk);
                                    page().waitForLoadState(LoadState.LOAD);
                                }
                                break;
                            case "center":
                                CommonUIActions.selectValueFromDropdownList(dwPositionElement,
                                        SendFilesPage.ddElementList, column.getValue().toLowerCase());
                                break;
                            default:
                                fail("dynamic watermark position does not match with the value: " + column.getKey());
                        }
                        break;
                    case "date and time":
                        switch (column.getValue().toLowerCase()) {
                            case "checked":
                                waitUntilSelectorAppears(CommonUIActions.dwDateAndTimeElement);
                                clickElement(CommonUIActions.dwDateAndTimeElement);
                                break;
                            case "unchecked":
                                waitUntilSelectorAppears(CommonUIActions.dwDateAndTimeElement);
                                if (isChecked(CommonUIActions.dwDateAndTimeElement)) {
                                    clickElement(CommonUIActions.dwDateAndTimeElement);
                                } else {
                                    assertFalse(isChecked(CommonUIActions.dwDateAndTimeElement));
                                }
                                break;
                            default:
                                fail("date and time checkbox is not found: " + column.getKey());
                        }
                        break;
                    case "ip address":
                        switch (column.getValue().toLowerCase()) {
                            case "checked":
                                clickElement(CommonUIActions.dwIpAddElement);
                                break;
                            case "unchecked":
                                if (isChecked(CommonUIActions.dwIpAddElement)) {
                                    clickElement(CommonUIActions.dwIpAddElement);
                                } else {
                                    assertFalse(isChecked(CommonUIActions.dwIpAddElement));
                                }
                                break;
                            default:
                                fail("Ip address checkbox is not found: " + column.getKey());
                        }
                        break;
                }
            }
        });
    }

    public static void validateToAPreview(boolean isUpdated) {
        clickElement("//*[@id='termsofaccess']//div[2]//span/span");
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears(getXpathForContainsText("Preview Terms of Access", "h4"));
        String previewText = getElementText("//app-terms-of-access/div[2]/div[2]/div[1]/div/div");

        int retryCount = 0;
        while (retryCount < 5) {
            if (isUpdated) {
                if (!previewText.isEmpty()) {
                    assertEquals(previewText, "Newly added content in organization term");
                    break;
                }
            } else {
                if (!previewText.isEmpty()) {
                    assertEquals(previewText.substring(0, 50), "===== ADMIN TO EDIT SECTION BEFORE FIRST USE =====");
                    break;
                }
            }
            retryCount++;
        }
        clickElement("//app-terms-of-access/div[2]/div[2]/div[3]/div[2]/button");
    }

    public static void validateSelectedToaOption(String toaOption) {
        String drdSelectedToaOption = "//*[@id='cre_sec']/div[3]/div[1]/div[2]/pg-select/div/div/div[2]";
        String txtToaInfo = "(//div[@id='termsofaccess']/div/div[2]/div/span)[1]";
        switch (toaOption.toLowerCase()) {
            case "use custom terms":
                assertTrue(isElementVisible(drdSelectedToaOption));
                waitUntilSelectorAppears(drdSelectedToaOption);
                assertEquals(getElementText(drdSelectedToaOption).toLowerCase(),
                        "use custom terms");
                assertEquals(getElementText(txtToaInfo),
                        "Custom terms will only apply to this data room.");
                break;
            case "no custom terms":
                List<ElementHandle> toaList = page().querySelectorAll(SendFilesPage.ddElementList);
                for (ElementHandle elementHandle : toaList) {
                    assertNotEquals(elementHandle.textContent(), "Use custom terms");
                }
                break;
            case "use organization terms":
                assertTrue(isElementVisible(drdSelectedToaOption));
                assertEquals(getElementText(drdSelectedToaOption).toLowerCase(),
                        "use organization terms");
                assertEquals(getElementText(txtToaInfo), normalizeSpaceForString(
                        "These account-wide legal terms are set by your organization.  Preview"));
                validateToAPreview(false);
                break;
            case "modified-->organization terms":
                assertTrue(isElementVisible(drdSelectedToaOption));
                assertEquals(getElementText(drdSelectedToaOption).toLowerCase(),
                        "use organization terms");
                assertEquals(getElementText(txtToaInfo), normalizeSpaceForString(
                        "These account-wide legal terms are set by your organization. Preview"));
                validateToAPreview(true);
        }
    }

    public static void navigateToCreateDRPage() {
        clickElement(getXpathForContainsText("Create Data Room", "p"));
        HomePage.waitForTitleToAppear(txtCreateDataRoomTitle);
        assertEquals(getElementText(txtCreateDataRoomTitle), "Create Data Room");
    }

    public static void validateQnADrawer(Boolean isQnaDrawerVisible) {
        String btnQnAInHeader = "//*[@id='drf_btn_drg']/../button[2]";
        if (isQnaDrawerVisible) {
            assertTrue(isElementVisible(DataRoomFilesPage.btnQnaDrawer));
            assertTrue(isElementVisible(btnQnAInHeader));
        } else {
            assertFalse(isElementVisible(DataRoomFilesPage.btnQnaDrawer));
            assertFalse(isElementVisible(btnQnAInHeader));
        }
    }

    public static void selectDataRoomExpiryDate(String days) {
        page().waitForLoadState(LoadState.LOAD);
        page().setViewportSize(1920, 1080);
        waitUntilSelectorAppears("//*[@id='expiry']/div[1]/div/div[1]/div[1]/div/pg-datepicker/span/input");
        clickElement("//*[@id='expiry']/div[1]/div/div[1]/div[1]/div/pg-datepicker/span/input");

        waitUntilSelectorAppears(".fullcalendartbody");
        CommonUIActions.selectFutureDateFromCalendar(days);
        page().setViewportSize(1280, 720);
        page().waitForLoadState(LoadState.LOAD);
    }

    public static void validateDrExpiryDateInDrInfo(String days, String drExpiryDateLocator) {
        waitUntilSelectorAppears(drExpiryDateLocator);
        String futureDateTime = CommonUIActions.getFutureDate(days, true);
        String actualDate = getElementText(drExpiryDateLocator);
        assertEquals(actualDate.replace("· ", "").trim(), "Expires on " +
                futureDateTime.substring(0, futureDateTime.length() - 8).trim());
    }

    public static void validateAndContinueEnforceEmailVerificationModal(Boolean whileUpdatingDrSettings) {
        String txtEnforceEmailTitleOnModal = "(//div[@class='modal-header']/div/h4)[1]";
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears(txtEnforceEmailTitleOnModal);
        assertTrue(isElementVisible(txtEnforceEmailTitleOnModal));
        clickElement(CommonUIActions.btnContinue);
        page().waitForLoadState(LoadState.LOAD);
        if (whileUpdatingDrSettings) {
            waitUntilSelectorAppears(SettingsPage.txtDrSettingsTitle);
        } else {
            waitUntilSelectorAppears(txtDRFiles);
        }
    }

    public static void validateVerificationCodeModal() {
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears(getXpathForTextEquals
                ("Verify your email", "h4"));
    }

    public static void authenticateDrGuestWithOTP(String user) {
        String txtOTPBox = "//input[@autocomplete='one-time-code'][%d]";
        String otp = getOTPFromMailDrop(user.toLowerCase().contains("new user") ?
                LoginPage.newUser :
                FileUtils.getUserCredentialsFromJsonFile(user).get("username").replace("@maildrop.cc", ""));
        for (int i = 0; i < otp.length(); i++) {
            char digit = otp.charAt(i);
            ElementHandle otpInputField = page().querySelector(String.format(txtOTPBox, i + 1)); //Find the OTP input field
            if (otpInputField != null) {
                otpInputField.press(String.valueOf(digit)); //press the digit into it
            } else {
                LogUtils.infoLog("OTP input field not found for digit " + digit);
            }
        }
        clickElement(CommonUIActions.btnVerify);
        if (!isElementVisible(txtDRFiles)) {
            clickElement(DataRoomGuestsPage.btnGoToDrList);
        } else {
            waitUntilSelectorAppears(txtDRFiles);
        }
    }

    public static void validatePremiumFeatureUpgradeLabelForDR() {
        assertTrue(isElementVisible("(//span[contains(@class, 'new-premium-addons-label')])[1]"));
        assertTrue(isElementVisible("(//span[contains(@class, 'new-premium-addons-label')])[2]"));
        assertTrue(isElementVisible("(//span[contains(@class, 'new-premium-addons-label')])[3]"));
    }

    public static void validatePremiumUpgradeModalInDRForProPlan(String premiumUpgradeFeatureType) {
        String lnkDRLearnMore = "(//a[@rel='noopener noreferrer'])[3]";

        switch (premiumUpgradeFeatureType.toLowerCase()) {
            case "dynamic watermark":
                assertEquals(getElementText(
                                CommonUIActions.txtPremiumFeatureTitle).trim(),
                        "Get Dynamic Watermark for $50/mo");
                assertTrue(isElementVisible(lnkDRLearnMore));
                assertEquals(getElementText(lnkDRLearnMore),
                        "Learn more about this feature");
                CommonUIActions.validatePremiumUpgradeModalContent();
                break;
            case "screen shield":
                assertEquals(getElementText(
                                CommonUIActions.txtPremiumFeatureTitle),
                        "Get Screen Shield for $50/mo");
                assertTrue(isElementVisible(lnkDRLearnMore));
                assertEquals(getElementText(lnkDRLearnMore),
                        "Learn more about this feature");
                CommonUIActions.validatePremiumUpgradeModalContent();
                break;
            case "q&a":
                assertEquals(getElementText(
                                "//div[@class='modal-content']/app-premium-upgrade-modal/div/h4"),
                        "Upgrade your plan");
                assertTrue(isElementVisible("//p[@class='premium-txt']/a"));
                assertEquals(getElementText("//p[@class='premium-txt']/a"),
                        "Learn more");
                assertEquals(getElementText("//p[@class='premium-txt']"),
                        normalizeSpaceForString("Question & Answer is only available for Team plan and above.  Learn more"));
                assertTrue(isElementVisible(
                        "//div[@class='premium-content']/../div[2]/div/button[1]"));
                assertTrue(isElementVisible(
                        "//div[@class='premium-content']/../div[2]/div/button[2]"));
                break;
            default:
                fail("Invalid premium upgrade feature in DR: " + premiumUpgradeFeatureType);
        }
    }

    public static void validateDrTabs(List<String> drTabsList) {
        drTabsList.forEach(feature -> {
            page().waitForLoadState(LoadState.LOAD);
            waitUntilSelectorAppears("//div[@class='dataroom-header header']//ul[@role='tablist']");
            switch (feature.trim()) {
                case "files":
                    page().waitForLoadState(LoadState.LOAD);
                    waitUntilSelectorAppears(lnkFilesTabInDR);
                    assertTrue(isElementVisible(lnkFilesTabInDR));
                    break;
                case "guests", "access":
                    assertTrue(isElementVisible(lnkGuestTabInDR));
                    break;
                case "about":
                    assertTrue(isElementVisible(lnkAboutTabInDR));
                    break;
                case "analytics":
                    assertTrue(isElementVisible(lnkAnalyticsTabInDr));
                    break;
                case "settings":
                    assertTrue(isElementVisible(lnkSettingsTabInDr));
                    break;
                default:
                    fail("Data room tab is not visible: " + feature);
            }
        });
    }

    public static void validateDRAccessAsGuest(String guestDRPermission) {
        switch (guestDRPermission.toLowerCase()) {
            case "no access":
                waitUntilSelectorAppears(txtLookingForSomething);
                assertEquals(getElementText
                                (txtLookingForSomething),
                        "Looking for something?");
                assertEquals(getElementText
                                ("(//div[@class='sub-dataroom-layout has-error']/div/div[2]/p)[2]"),
                        "To access a specific file or folder, please check with the owner for the direct link.");
                break;
            case "view", "print":
                waitUntilSelectorAppears(txtDRFiles);
                CommonUIActions.selectFirstCheckbox();
                assertTrue(isElementVisible("//button[text()=' View ']"));
                break;
            case "edit":
                String tagEditPerm = "//div[@containerclass='screenshield-tooltips']";
                waitUntilSelectorAppears(txtDRFiles);
                waitUntilSelectorAppears(tagEditPerm);
                assertEquals(getElementText(tagEditPerm), "E");
                CommonUIActions.selectFirstCheckbox();
                assertTrue(isElementVisible("//button[text()=' View ']"));
                assertTrue(isElementVisible("//button[text()=' Download ']"));
                assertTrue(isElementVisible("//button[text()=' Trash ']"));
                break;
            case "download_pdf", "download_original":
                waitUntilSelectorAppears(txtDRFiles);
                CommonUIActions.selectFirstCheckbox();
                assertTrue(isElementVisible("//button[text()=' View ']"));
                assertTrue(isElementVisible("//button[text()=' Download ']"));
                break;
            case "unshared_dr":
                waitUntilSelectorAppears("//div[@class='text-center']/div[2]/p[1]");
                assertTrue(isElementVisible("//img[@alt='dgf-error']"));
                assertEquals(getElementText("//div[@class='text-center']/div[2]/p[1]"),
                        "This data room is not available.");
                assertEquals(getElementText("//div[@class='text-center']/div[2]/span/p"),
                        "Please check with the owner if you need access. Learn more");
                assertEquals(getElementText("//div[@class='text-center']/div[2]/p[2]"),
                        "ITM_RVK");
                break;
            case "access denied":
                waitUntilSelectorAppears("//page-container//div[2]/p[1]");
                assertEquals(getElementText("//page-container//div[2]/p[1]"), "Access denied");
                assertTrue(isElementVisible(getXpathForContainsText(
                        "Request access", "button")));
                assertTrue(isElementVisible(getXpathForContainsText(
                        "Use another email", "a")));
                assertTrue(isElementVisible(getXpathForContainsText(
                        "go to data room list", "a")));
                break;
            default:
                fail("DR permission type is not found: " + guestDRPermission);
        }
    }

    public static void validateAndContinueRAIModalASOwner() {
        assertEquals(getElementText("//app-additional-info/div[1]//h4"), "You've turned on \"Require additional information\"");
        assertEquals(getElementText("//app-additional-info/div[1]//p"), "Your guests must enter the required information below before viewing the data room. Here's a preview of what they will see.");
        assertEquals(getElementText("//label[@for='skip-additional-info']"), "Don't show again for this data room");
        clickElement("//label[@for='skip-additional-info']");
        clickElement("//app-additional-info//button[@type='button']");
    }

    public static void validateRAICheckboxOnCloneDRPage() {
        page().waitForLoadState(LoadState.LOAD);
        assertTrue(isElementHidden("//*[@id='ai-email']"));
    }

    public static void selectCreateDRBtn() {
        clickElement(btnCreateDataRoom);
        waitUntilSelectorAppears(txtDRFiles);
    }

    public void validateDRAsPerUserRole(String userRole) {
        switch (userRole) {
            case "Restricted User":
                navigateToCreateDRPage();
                assertEquals(getElementText("//app-create-dataroom/div/div[2]//p[1]"),
                        "Your account is on a \"Restricted User\" role");
                assertEquals(getElementText("//app-create-dataroom/div/div[2]//p[2]"),
                        "To use this feature, please contact your admin to upgrade you to a full user.");

                checkManageDRPage();
                break;
            case "User  Recommended", "Admin":
                navigateToCreateDRPage();
                assertTrue(isElementVisible(
                        "//*[@id='cre_nam']/div/div/div[1]/div"));
                checkManageDRPage();
                break;
            default:
                fail("User role not found for DR: " + userRole);

        }
    }

    public void checkManageDRPage() {
        ManageDataRoomPage.navigateToManageDRPage();
        assertEquals(getElementText("(//div[@class='no-dataroom-block'])[2]/div/p[1]"),
                "Create your first data room");
        assertEquals(getElementText("(//div[@class='no-dataroom-block'])[2]/div/p[2]"),
                "Keep control of your files in a secure repository.");
        assertTrue(isElementVisible("(//div[@class='no-dataroom-block'])[2]/div[2]/button"));
    }
}
