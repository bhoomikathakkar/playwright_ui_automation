package digifyE2E.pages.landingPage;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.LoadState;
import digifyE2E.pages.Base;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.testDataManager.PricingUserEmailManager;
import digifyE2E.testDataManager.SharedDM;
import digifyE2E.utils.EnvUtils;
import digifyE2E.utils.FileUtils;
import digifyE2E.utils.LogUtils;
import digifyE2E.utils.RandomUtils;
import io.cucumber.datatable.DataTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.*;

public class PricingPage extends CommonUIActions {

    private static final String lblDwdPlans = "//app-checkout//form[1]/div[2]/div[2]/div[2]/span//p";
    private static final String lnkContactSupport = "//app-checkout//form[1]/div[2]/div[2]/div[2]/span//a";
    private static final String lblPlanTotalPrice = "//app-checkout/div/div[2]/div[2]/div/span/p";
    private static final String lblPlanUser = "//app-root/app-checkout//form[2]/div[1]/div/div[1]/div[1]/span[1]";
    private static final String lblPlanUserLine2 = "//app-root/app-checkout//form[2]/div[1]/div/div[1]/div[1]/p";
    private static final String lblPlanUserCount = "//form[2]/div[1]//div[1]/div[2]/nz-input-number/div[2]/input";
    private static final String lblUserIncluded = "//app-checkout//form[2]/div[1]/div/div[1]/div[1]/span[2]";
    private static final String lblDRIncluded = "//app-checkout//form[2]/div[1]/div/div[2]/div[1]/span[2]";
    private static final String lblDRGuestIncluded = "//app-checkout//form[2]/div[1]/div/div[3]/div[1]/span[2]";
    private static final String btnGoToApp = "//li[@class='nav-item nav-btn']/a[text()=' Go To App ']";
    private static final String txtDynamicWatermarkAddonValue = "//p[contains(text(),'Dynamic Watermark')]/following-sibling::p[@class='item-price']";
    private static final String lblMonthlyBill = "//p[@class='billed-text m-0']";
    private static final String lblAnnuallyBill = "//div[@class='position-relative']/p";
    private static final String lblAnnualDiscount = "//div[@class='position-relative']/span";
    private static final String btnPlanSwitch = "//input[@role='switch']";
    private static final String btnProBuyNow = "//a[@class='tw-block tw-text-center btn digify-default-btn buy-btn']";
    private static final String btnTeamBuyNow = "//a[@class='tw-block tw-text-center btn digify-default-btn buy-btn bg-red']";
    private static final String drdLocal = "//app-language-drop-down[@type='contact']//i";
    private final String bannerCookie="//div[@id='hs-eu-cookie-confirmation']";
    private final String bannerPrivacyConsent="//div[@class='cky-consent-bar']";
    public static Map<String, Double> planDetails = new HashMap<>();
    public static Map<String, String> proPlanCardInfo = new HashMap<>();
    public static Map<String, String> teamPlanCardInfo = new HashMap<>();
    public static Map<String, String> customPlanCardInfo = new HashMap<>();
    public static Map<String, String> featureTable = new HashMap<>();

    public static void typeUserCredsOnSubscriptionModal(String username, String password) {
        page().waitForLoadState(LoadState.LOAD);
        inputIntoTextField("//input[@type='text']", username);
        clickElement(getXpathForContainsText("Next", "button"));
        inputIntoTextField("//input[@type='password']", password);
        clickElement(getXpathForContainsText("Next", "button"));
    }


    public static double getTotalAddonValue(String... addonTypes) {
        int totalAddonValue = 0;
        for (String addonType : addonTypes) {
            totalAddonValue += planDetails.get(addonType);
            LogUtils.infoLog("Total addon value for " + addonType + ": " + planDetails.get(addonType));
        }
        LogUtils.infoLog("Total addon value for all addons: " + totalAddonValue);
        return totalAddonValue;
    }

    public static String getFeatureValue(String... featureType) {
        String featureValue = "";
        for (String feature : featureType) {
            featureValue += featureTable.get(feature);
        }
        return featureValue;
    }

    public static void navigateToPricingCheckoutPageAsExistingUser() {
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears(getXpathForContainsText(
                "Modify your plan", "h4"));
        assertTrue(isElementVisible(getXpathForContainsText(
                "Modify your plan", "h4")));
    }

    public static void clickOnPricingPageCTAButton(String buttonType) {
        String lnkPricingPageLogout = "//a[@class='email-link' and contains(text(),'Log out')]";
        switch (buttonType.toLowerCase()) {
            case "continue to checkout":
                clickElement(getXpathForContainsText("Continue to checkout", "p"));
                page().waitForLoadState(LoadState.LOAD);
                break;
            case "logout":
                keyPress("PageUp");
                waitUntilSelectorAppears(lnkPricingPageLogout);
                clickElement(lnkPricingPageLogout);
                page().waitForURL("**/pricing/#/plans");
                waitUntilSelectorAppears("//li[@class='nav-item nav-btn']/a[text()=' Start free trial ']");
                deleteBrowserSessionData();
                break;
            case "start free trial":
                clickElement("(//a[@class='header-btn btn-primary'])[1]");
                break;
            case "start your free trial":
                clickElement(getXpathForContainsText("Start your free trial", "span"));
                break;
            case "go to app":
                clickElement(btnGoToApp);
                break;
            default:
                fail("No relevant button found on page: " + buttonType);
        }
    }

    public static void validateTotalNoOFFeatureListedOnPlanCard(int noOFFeatures, String planType) {
        switch (planType) {
            case "pro":
                proPlanCardInfo = FileUtils.getPlanInfoFromJsonFile("pro");

                String lblProPlanPrice = "//div[@class='product-bg'][1]//div[@class='price-number d-inline']";
                String lblProPlanDuration = "//div[@class='product-bg'][1]//div[@class='price']/p";

                assertEquals(countTotalNoOfElements("//div[@class='product-bg'][1]//ul[2]//li"), noOFFeatures);
                assertEquals(getElementText("//div[@class='product-bg'][1]//div[@class='pricing-card-block']/p"), proPlanCardInfo.get("planName"));
                assertEquals(getElementText(lblProPlanPrice), proPlanCardInfo.get("planMonthlyPrice"));
                assertEquals(getElementText(lblProPlanDuration), proPlanCardInfo.get("planLabelText"));
                verifySwitchPlanInfo();
                clickElement(btnPlanSwitch);
                assertEquals(getElementText(lblProPlanPrice), proPlanCardInfo.get("planAnnualPrice"));
                assertEquals(getElementText(lblProPlanDuration), proPlanCardInfo.get("planLabelText"));
                assertEquals(getElementText("//div[@class='product-bg'][1]//ul[1]//li[1]//p"), proPlanCardInfo.get("planUser"));
                assertEquals(getElementText("//div[@class='product-bg'][1]//ul[1]//li[2]//p"), proPlanCardInfo.get("planDR"));
                assertEquals(getElementText("//div[@class='product-bg'][1]//ul[1]//li[3]//p"), proPlanCardInfo.get("planDRGuest"));
                assertEquals(getElementText("//div[@class='product-bg'][1]//ul[2]//li[1]//p"), proPlanCardInfo.get("proDS"));
                assertEquals(getElementText("//div[@class='product-bg'][1]//ul[2]//li[2]//p"), proPlanCardInfo.get("proLogo"));
                assertEquals(getElementText("//div[@class='product-bg'][1]//ul[2]//li[3]//p"), proPlanCardInfo.get("proToa"));
                assertEquals(getElementText("//div[@class='product-bg'][1]//ul[2]//li[4]//p"), proPlanCardInfo.get("proDRGroup"));
                assertEquals(getElementText("//div[@class='product-bg'][1]//ul[2]//li[5]//p"), proPlanCardInfo.get("proDRBanner"));
                assertEquals(getElementText("//div[@class='product-bg'][1]//ul[2]//li[6]//p"), proPlanCardInfo.get("proAboutPage"));
                assertEquals(getElementText("//div[@class='product-bg'][1]//ul[2]//li[7]//p"), proPlanCardInfo.get("proComfortLetter"));
                assertEquals(getElementText("//div[@class='product-bg'][1]//ul[2]//li[8]//p"), proPlanCardInfo.get("proGP"));
                assertEquals(getElementText("//div[@class='product-bg'][1]//ul[2]//li[9]//p"), proPlanCardInfo.get("proStorageLocation"));
                assertEquals(getElementText("//div[@class='product-bg'][1]//ul[2]//li[10]//p"), proPlanCardInfo.get("proOptionalFeature"));
                assertEquals(getElementText("//div[@class='product-bg'][1]//ul[2]//li[11]//p"), proPlanCardInfo.get("proChatSupport"));
                assertTrue(isElementVisible(btnProBuyNow));
                assertTrue(isElementVisible("(//div[@class='buy-quote-btn text-center']/a[2])[1]"));
                break;

            case "team":
                teamPlanCardInfo = FileUtils.getPlanInfoFromJsonFile("team");

                String lblTeamPlanPrice = "//div[@class='product-bg'][2]//div[@class='price-number d-inline']";
                String lblTeamPlanDuration = "//div[@class='product-bg'][2]//div[@class='price']/p";

                assertEquals(countTotalNoOfElements("//div[@class='product-bg'][2]//ul[2]//li"), noOFFeatures);
                assertEquals(getElementText("//div[@class='product-bg'][2]//div[@class='pricing-card-block']/p"), teamPlanCardInfo.get("planName"));
                assertEquals(getElementText(lblTeamPlanPrice), teamPlanCardInfo.get("planMonthlyPrice"));
                assertEquals(getElementText(lblTeamPlanDuration), teamPlanCardInfo.get("planLabelText"));
                verifySwitchPlanInfo();
                clickElement(btnPlanSwitch);
                assertEquals(getElementText(lblTeamPlanPrice), teamPlanCardInfo.get("planAnnualPrice"));
                assertEquals(getElementText(lblTeamPlanDuration), teamPlanCardInfo.get("planLabelText"));
                assertEquals(getElementText("//div[@class='product-bg'][2]//ul[1]//li[1]//p"), teamPlanCardInfo.get("planUser"));
                assertEquals(getElementText("//div[@class='product-bg'][2]//ul[1]//li[2]//p"), teamPlanCardInfo.get("planDR"));
                assertEquals(getElementText("//div[@class='product-bg'][2]//ul[1]//li[3]//p"), teamPlanCardInfo.get("planDRGuest"));
                assertEquals(getElementText("//div[@class='product-bg'][2]//ul[2]//li[1]//p"), teamPlanCardInfo.get("featureLabelText"));
                assertEquals(getElementText("//div[@class='product-bg'][2]//ul[2]//li[2]//p"), teamPlanCardInfo.get("watermark"));
                assertEquals(getElementText("//div[@class='product-bg'][2]//ul[2]//li[3]//p"), teamPlanCardInfo.get("screenShield"));
                assertEquals(getElementText("//div[@class='product-bg'][2]//ul[2]//li[4]//p"), teamPlanCardInfo.get("teamGP"));
                assertEquals(getElementText("//div[@class='product-bg'][2]//ul[2]//li[5]//p"), teamPlanCardInfo.get("msEditing"));
                assertEquals(getElementText("//div[@class='product-bg'][2]//ul[2]//li[6]//p"), teamPlanCardInfo.get("qna"));
                assertEquals(getElementText("//div[@class='product-bg'][2]//ul[2]//li[7]//p"), teamPlanCardInfo.get("phoneSupport"));
                assertEquals(getElementText("//div[@class='product-bg'][2]//ul[2]//li[8]//p"), teamPlanCardInfo.get("guestSupport"));
                assertTrue(isElementVisible(btnTeamBuyNow));
                assertTrue(isElementVisible("(//div[@class='buy-quote-btn text-center']/a[2])[2]"));
                break;

            case "enterprise":
                customPlanCardInfo = FileUtils.getPlanInfoFromJsonFile("enterprise");

                String lblCustomPlanPrice = "//div[@class='product-bg'][3]//div[@class='price']//div";
                String lblCustomPlanDuration = "//div[@class='product-bg'][3]//div[@class='price']//p";

                assertEquals(countTotalNoOfElements("//div[@class='product-bg'][3]//ul[2]//li"), noOFFeatures);
                assertEquals(getElementText("//div[@class='product-bg'][3]//div[@class='pricing-card-block']/p"), customPlanCardInfo.get("planName"));
                assertEquals(getElementText(lblCustomPlanPrice), customPlanCardInfo.get("planMonthlyPrice"));
                assertEquals(getElementText(lblCustomPlanDuration), customPlanCardInfo.get("planLabelText"));
                verifySwitchPlanInfo();
                clickElement(btnPlanSwitch);
                assertEquals(getElementText(lblCustomPlanPrice), customPlanCardInfo.get("planMonthlyPrice"));
                assertEquals(getElementText(lblCustomPlanDuration), customPlanCardInfo.get("planLabelText"));
                assertEquals(getElementText("//div[@class='product-bg'][3]//ul[1]//li[1]//p"), customPlanCardInfo.get("planUser"));
                assertEquals(getElementText("//div[@class='product-bg'][3]//ul[1]//li[2]//p"), customPlanCardInfo.get("planDR"));
                assertEquals(getElementText("//div[@class='product-bg'][3]//ul[1]//li[3]//p"), customPlanCardInfo.get("planDRGuest"));
                assertEquals(getElementText("//div[@class='product-bg'][3]//ul[2]//li[1]//p"), customPlanCardInfo.get("featureLabelText"));
                assertEquals(getElementText("//div[@class='product-bg'][3]//ul[2]//li[2]//p"), customPlanCardInfo.get("whiteLabel"));
                assertEquals(getElementText("//div[@class='product-bg'][3]//ul[2]//li[3]//p"), customPlanCardInfo.get("branding"));
                assertEquals(getElementText("//div[@class='product-bg'][3]//ul[2]//li[4]//p"), customPlanCardInfo.get("api"));
                assertEquals(getElementText("//div[@class='product-bg'][3]//ul[2]//li[5]//p"), customPlanCardInfo.get("drStorageLocation"));
                assertEquals(getElementText("//div[@class='product-bg'][3]//ul[2]//li[6]//p"), customPlanCardInfo.get("sso"));
                assertEquals(getElementText("//div[@class='product-bg'][3]//ul[2]//li[7]//p"), customPlanCardInfo.get("domain"));
                assertEquals(getElementText("//div[@class='product-bg'][3]//ul[2]//li[8]//p"), customPlanCardInfo.get("compliance"));
                assertEquals(getElementText("//div[@class='product-bg'][3]//ul[2]//li[9]//p"), customPlanCardInfo.get("legal"));
                assertEquals(getElementText("//div[@class='product-bg'][3]//ul[2]//li[10]//p"), customPlanCardInfo.get("manager"));
                assertEquals(getElementText("//div[@class='product-bg'][3]//ul[2]//li[11]//p"), customPlanCardInfo.get("support"));
                assertEquals(getElementText("//div[@class='product-bg'][3]//ul[2]//li[12]//p"), customPlanCardInfo.get("guarantees"));
                assertTrue(isElementVisible(getXpathForContainsText(" Book a call ", "a")));
                assertTrue(isElementVisible("(//div[@class='buy-quote-btn text-center']/a[2])[3]"));
                break;
            default:
                fail("invalid plan type" + planType);
        }
    }

    public static void verifySwitchPlanInfo() {
        assertEquals(getElementText(lblMonthlyBill), "Billed Monthly");
        assertEquals(getElementText(lblAnnuallyBill), "Billed Annually");
        assertEquals(getElementText(lblAnnualDiscount), "Save 30%!");
    }

    public static void validateFullFeatureComparisonTableOnPricingPage(String featureType) {
        featureTable = FileUtils.getFeatureComparisonTable(featureType);

        page().waitForLoadState(LoadState.LOAD);
        assertEquals(getElementText("//div[@class='full-feature']//h3"), "Full feature comparison");
        switch (featureType) {
            case "account":
                assertEquals(getFeatureComparisonTableHeadingText(getFeatureValue("acc")), getFeatureValue("acc"));
                assertEquals(getFeatureComparisonTableHeadingText(getFeatureValue("pro")), getFeatureValue("pro"));
                assertEquals(getFeatureComparisonTableHeadingText(getFeatureValue("team")), getFeatureValue("team"));
                assertEquals(getFeatureComparisonTableHeadingText(getFeatureValue("enterprise")), getFeatureValue("enterprise"));
                assertEquals(getFeatureComparisonTableRowText(getFeatureValue("users")), getFeatureValue("users"));
                assertEquals(getFeatureComparisonTableCustomText(1, getFeatureValue("users"), getFeatureValue("proUser")), getFeatureValue("proUser"));
                assertEquals(getFeatureComparisonTableCustomText(1, getFeatureValue("users"), getFeatureValue("teamUser")), getFeatureValue("teamUser"));
                assertEquals(getFeatureComparisonTableCustomText(1, getFeatureValue("users"), getFeatureValue("enterpriseUser")), getFeatureValue("enterpriseUser"));
                assertEquals(getFeatureComparisonTableRowText(getFeatureValue("encryptedStorage")), getFeatureValue("encryptedStorage"));
                assertEquals(getFeatureComparisonTableRowText(getFeatureValue("proES")), getFeatureValue("proES"));
                assertEquals(getFeatureComparisonTableRowText(getFeatureValue("teamES")), getFeatureValue("teamES"));
                assertEquals(getFeatureComparisonTableCustomText(1, getFeatureValue("encryptedStorage"), getFeatureValue("enterpriseES")), getFeatureValue("enterpriseES"));
                assertEquals(getFeatureComparisonTableMainRowText(1, 4, getFeatureValue("dataRooms")), getFeatureValue("dataRooms"));
                assertEquals(getFeatureComparisonTableCustomText(1, getFeatureValue("dataRooms"), getFeatureValue("proDR")), getFeatureValue("proDR"));
                assertEquals(getFeatureComparisonTableCustomText(1, getFeatureValue("dataRooms"), getFeatureValue("teamDR")), getFeatureValue("teamDR"));
                assertEquals(getFeatureComparisonTableCustomText(1, getFeatureValue("dataRooms"), getFeatureValue("enterpriseDR")), getFeatureValue("enterpriseDR"));
                verifySimilarFeature("unlimitedDR", "proUnlimitedDR", "teamUnlimitedDR", 1, null, false);
                assertEquals(getFeatureComparisonTableRowText(getFeatureValue("drGuest")), getFeatureValue("drGuest"));
                assertEquals(getFeatureComparisonTableCustomText(1, getFeatureValue("drGuest"), getFeatureValue("proDRGuest")), getFeatureValue("proDRGuest"));
                assertEquals(getFeatureComparisonTableRowText(getFeatureValue("teamDRGuest")), getFeatureValue("teamDRGuest"));
                assertEquals(getFeatureComparisonTableCustomText(1, getFeatureValue("drGuest"), getFeatureValue("enterpriseDRGuest")), getFeatureValue("enterpriseDRGuest"));
                assertEquals(getFeatureComparisonTableRowText(getFeatureValue("mediaBandwidth")), getFeatureValue("mediaBandwidth"));
                assertEquals(getFeatureComparisonTableSimilarText(1, getFeatureValue("mediaBandwidth"), getFeatureValue("proMediaBandwidth"), 1), getFeatureValue("proMediaBandwidth"));
                assertEquals(getFeatureComparisonTableSimilarText(1, getFeatureValue("mediaBandwidth"), getFeatureValue("teamMediaBandwidth"), 2), getFeatureValue("teamMediaBandwidth"));
                assertEquals(getFeatureComparisonTableCustomText(1, getFeatureValue("mediaBandwidth"), getFeatureValue("enterpriseMediaBandwidth")), getFeatureValue("enterpriseMediaBandwidth"));
                verifySimilarFeature("fileSizeLimit", "proFSL", "teamFSL", 1, "enterpriseFSL", true);
                verifySimilarFeature("fileSizeLimit", "proFSL", "teamFSL", 1, "enterpriseFSL", true);
                break;

            case "documentSecurity":
                assertEquals(getFeatureComparisonTableRowHeadingText(getFeatureValue("ds")), getFeatureValue("ds"));
                assertEquals(getFeatureComparisonTableRowText(getFeatureValue("credit")), getFeatureValue("credit"));
                assertEquals(getFeatureComparisonTableRowText(getFeatureValue("proCredit")), getFeatureValue("proCredit"));
                assertEquals(getFeatureComparisonTableRowText(getFeatureValue("teamCredit")), getFeatureValue("teamCredit"));
                assertEquals(getFeatureComparisonTableCustomText(2, getFeatureValue("credit"), getFeatureValue("enterpriseCredit")), getFeatureValue("enterpriseCredit"));
                verifyTickFeature("fileEncryption", 2, 3);
                verifyTickFeature("passKeyEncryption", 2, 3);
                assertEquals(getElementText("//tbody[2]//td[contains(text(),'" + getFeatureValue("customTOANDA") + "')]"), getFeatureValue("customTOANDA"));
                assertTrue(getFeatureComparisonTableTickMark(2, getFeatureValue("customTOANDA"), 1));
                assertTrue(getFeatureComparisonTableTickMark(2, getFeatureValue("customTOANDA"), 2));
                assertTrue(getFeatureComparisonTableTickMark(2, getFeatureValue("customTOANDA"), 3));
                verifyTickFeature("access", 2, 3);
                verifyTickFeature("dsFileVersioning", 2, 3);
                verifyTickFeature("printAndDwdPermission", 2, 3);
                verifyTickFeature("oneTimeSharing", 2, 3);
                verifyTickFeature("fileExpiry", 2, 3);
                assertEquals(getFeatureComparisonTableRowText(getFeatureValue("watermark")), getFeatureValue("watermark"));
                assertEquals(getFeatureComparisonTableCustomText(2, getFeatureValue("watermark"), getFeatureValue("proWatermark")), getFeatureValue("proWatermark"));
                assertTrue(getFeatureComparisonTableTickMark(2, getFeatureValue("watermark"), 2));
                assertTrue(getFeatureComparisonTableTickMark(2, getFeatureValue("watermark"), 3));
                assertEquals(getFeatureComparisonTableRowText(getFeatureValue("proScreenShield")), getFeatureValue("proScreenShield"));
                assertTrue(getFeatureComparisonTableTickMark(2, getFeatureValue("proScreenShield"), 2));
                assertTrue(getFeatureComparisonTableTickMark(2, getFeatureValue("proScreenShield"), 3));
                break;

            case "fileTracking":
                assertEquals(getFeatureComparisonTableRowHeadingText(getFeatureValue("fileTrack")), getFeatureValue("fileTrack"));
                assertEquals(getFeatureComparisonTableMainRowText(3, 2, getFeatureValue("emailNotification")), getFeatureValue("emailNotification"));
                assertTrue(getFeatureComparisonTableTickMark(3, getFeatureValue("emailNotification"), 1));
                assertTrue(getFeatureComparisonTableTickMark(3, getFeatureValue("emailNotification"), 2));
                assertTrue(getFeatureComparisonTableTickMark(3, getFeatureValue("emailNotification"), 3));
                verifyTickFeature("requestEmail", 3);
                verifyTickFeature("trackViews", 3);
                verifyTickFeature("fileAnalytics", 3);
                verifyTickFeature("locationIP", 3);
                break;
            case "dataRoom":
                assertEquals(getElementText("//tbody[4]//th[@class='text-uppercase table-sub-header']"), getFeatureValue("dr"));
                verifyTickFeature("dragNDrop", 4);
                verifyTickFeature("fileNFolderIndexing", 4);
                verifyTickFeature("drNGuestExpiry", 4);
                verifyTickFeature("roleBasedPermission", 4);
                verifyTickFeature("guestGrp", 4);
                verifyTickFeature("folderLevelGP", 4);
                assertTrue(getFeatureComparisonTableInfoIcon(getFeatureValue("folderLevelGP")));
                assertEquals(getFeatureComparisonTableRowText(getFeatureValue("fileLevelGP")), getFeatureValue("fileLevelGP"));
                assertTrue(getFeatureComparisonTableInfoIcon(getFeatureValue("fileLevelGP")));
                assertFalse(getFeatureComparisonTableTickMark(4, getFeatureValue("fileLevelGP"), 1));
                assertTrue(getFeatureComparisonTableTickMark(4, getFeatureValue("fileLevelGP"), 2));
                assertTrue(getFeatureComparisonTableTickMark(4, getFeatureValue("fileLevelGP"), 3));
                assertEquals(getElementText("//tbody[4]//td[contains(text(),'" + getFeatureValue("customTOA") + "')]"), getFeatureValue("customTOA"));
                assertTrue(getFeatureComparisonTableTickMark(4, getFeatureValue("customTOA"), 1));
                assertTrue(getFeatureComparisonTableTickMark(4, getFeatureValue("customTOA"), 2));
                assertTrue(getFeatureComparisonTableTickMark(4, getFeatureValue("customTOA"), 3));
                assertEquals(getFeatureComparisonTableRowText(getFeatureValue("msEditing")), getFeatureValue("msEditing"));
                assertFalse(getFeatureComparisonTableTickMark(4, getFeatureValue("msEditing"), 1));
                assertTrue(getFeatureComparisonTableTickMark(4, getFeatureValue("msEditing"), 2));
                assertTrue(getFeatureComparisonTableTickMark(4, getFeatureValue("msEditing"), 3));
                verifyTickFeature("drAnalytics", 4);
                assertEquals(getFeatureComparisonTableRowText(getFeatureValue("activityLog")), getFeatureValue("guestActivityLog"));
                assertTrue(getFeatureComparisonTableTickMark(4, getFeatureValue("activityLog"), 1));
                assertTrue(getFeatureComparisonTableTickMark(4, getFeatureValue("activityLog"), 2));
                assertTrue(getFeatureComparisonTableTickMark(4, getFeatureValue("activityLog"), 3));
                verifyTickFeature("drNotification", 4);
                assertEquals(getFeatureComparisonTableRowText(getFeatureValue("qna")), getFeatureValue("qna"));
                assertTrue(getFeatureComparisonTableInfoIcon(getFeatureValue("qna")));
                assertFalse(getFeatureComparisonTableTickMark(4, getFeatureValue("qna"), 1));
                assertTrue(getFeatureComparisonTableTickMark(4, getFeatureValue("qna"), 2));
                assertTrue(getFeatureComparisonTableTickMark(4, getFeatureValue("qna"), 3));
                verifyTickFeature("drBanner", 4);
                assertTrue(getFeatureComparisonTableInfoIcon(getFeatureValue("drBanner")));
                verifyTickFeature("drAboutPage", 4);
                assertTrue(getFeatureComparisonTableInfoIcon(getFeatureValue("drAboutPage")));
                verifyTickFeature("disableDR", 4);
                verifyUnTickFeature("drStorageLocation", 4, null, false);
                assertTrue(getFeatureComparisonTableInfoIcon(getFeatureValue("drStorageLocation")));
                break;

            case "email":
                assertEquals(getFeatureComparisonTableRowHeadingText(getFeatureValue("emailText")), getFeatureValue("emailText"));
                verifyTickFeature("gmailPlugin", 5);
                verifyTickFeature("outlookPlugin", 5);
                break;
            case "customBranding":
                assertEquals(getFeatureComparisonTableRowHeadingText(getFeatureValue("customBranding")), getFeatureValue("customBranding"));
                verifyTickFeature("brandColor", 6);
                verifySimilarFeature("customLoginScreen", "proCustomLogin", "teamCustomLogin", 6, null, false);
                verifySimilarFeature("customBrandingEmail", "proCustomBrandingEmail", "teamCustomBrandingEmail", 6, null, false);
                verifySimilarFeature("whiteLabelDomain", "proWhiteLabelDomain", "teamWhiteLabelDomain", 6, null, false);
                break;

            case "admin":
                assertEquals(getFeatureComparisonTableRowHeadingText(getFeatureValue("adminText")), getFeatureValue("adminText"));
                verifyTickFeature("adminTeamControl", 7);
                verifyTickFeature("orgStorageLocation", 7);
                assertTrue(getFeatureComparisonTableInfoIcon(getFeatureValue("orgStorageLocation")));
                verifyTickFeature("enforce2FA", 7);
                verifyTickFeature("securityPreset", 7);
                verifyTickFeature("teamWideFileAnalytics", 7);
                break;

            case "supportedFormat":
                assertEquals(getFeatureComparisonTableRowHeadingText(getFeatureValue("formatText")), getFeatureValue("formatText"));
                verifyTickFeature("image", 8);
                verifyTickFeature("pdf", 8);
                verifyTickFeature("document", 8);
                verifyTickFeature("spreadsheet", 8);
                verifyTickFeature("presentation", 8);
                assertEquals(getFeatureComparisonTableRowText(getFeatureValue("video")), getFeatureValue("videoText"));
                assertEquals(getAttributeValue("//td[contains(text(),'" + getFeatureValue("video") + "')]//a", "href"), getFeatureValue("videoLink"));
                assertTrue(getFeatureComparisonTableTickMark(8, getFeatureValue("video"), 1));
                assertTrue(getFeatureComparisonTableTickMark(8, getFeatureValue("video"), 2));
                assertTrue(getFeatureComparisonTableTickMark(8, getFeatureValue("video"), 3));
                verifyTickFeature("audio", 8);
                verifyTickFeature("notes", 8);
                verifyTickFeature("otherFormat", 8);
                break;

            case "storageIntegration":
                assertEquals(getFeatureComparisonTableRowHeadingText(getFeatureValue("storageText")), getFeatureValue("storageText"));
                verifyTickFeature("dropbox", 9);
                verifyTickFeature("googleDrive", 9);
                verifyTickFeature("oneDrive", 9);
                verifyTickFeature("box", 9);
                break;

            case "apiAccess":
                assertEquals(getFeatureComparisonTableRowHeadingText(getFeatureValue("api")), getFeatureValue("api"));
                assertEquals(getFeatureComparisonTableRowText(getFeatureValue("freeApiAccess")), getFeatureValue("freeApiAccess"));
                assertTrue(getFeatureComparisonTableInfoIcon(getFeatureValue("freeApiAccess")));
                assertEquals(getFeatureComparisonTableCustomText(10, getFeatureValue("freeApiAccess"), getFeatureValue("proFreeApi")), getFeatureValue("proFreeApi"));
                assertEquals(getFeatureComparisonTableSimilarText(10, getFeatureValue("freeApiAccess"), getFeatureValue("teamFreeApi"), 1), getFeatureValue("teamFreeApi"));
                assertEquals(getFeatureComparisonTableSimilarText(10, getFeatureValue("freeApiAccess"), getFeatureValue("enterpriseFreeApi"), 2), getFeatureValue("enterpriseFreeApi"));
                verifySimilarFeature("apiTechSupport", "proApiTechSupport", "teamApiTechSupport", 10, null, false);
                verifySimilarFeature("seamlessLogin", "proLoginApi", "teamLoginApi", 10, null, false);
                verifySimilarFeature("sandboxAcc", "proSandboxAcc", "teamSandboxAcc", 10, null, false);
                verifyTickFeature("apiCustomWorkflow", 10);
                verifyTickFeature("apiFileEmbed", 10);
                verifyTickFeature("zapIntegration", 10);
                break;

            case "trainingSupport":
                assertEquals(getFeatureComparisonTableRowHeadingText(getFeatureValue("trainingAndSupport")), getFeatureValue("trainingAndSupport"));
                verifyTickFeature("emailChat", 11);
                assertEquals(getFeatureComparisonTableRowText(getFeatureValue("phoneSupport")), getFeatureValue("phoneSupport"));
                assertFalse(getFeatureComparisonTableTickMark(11, getFeatureValue("phoneSupport"), 1));
                assertTrue(getFeatureComparisonTableTickMark(11, getFeatureValue("phoneSupport"), 2));
                assertTrue(getFeatureComparisonTableTickMark(11, getFeatureValue("phoneSupport"), 3));
                assertEquals(getFeatureComparisonTableRowText(getFeatureValue("guestSupport")), getFeatureValue("guestSupport"));
                assertFalse(getFeatureComparisonTableTickMark(11, getFeatureValue("guestSupport"), 1));
                assertTrue(getFeatureComparisonTableTickMark(11, getFeatureValue("guestSupport"), 2));
                assertTrue(getFeatureComparisonTableTickMark(11, getFeatureValue("guestSupport"), 3));
                verifyUnTickFeature("prioritySupport", 11, "enterprisePriority", true);
                verifyUnTickFeature("successManager", 11, "enterpriseSuccessManager", true);
                verifyUnTickFeature("serviceGuarantee", 11, null, false);
                verifyUnTickFeature("sla", 11, null, false);
                break;

            case "compliance":
                assertEquals(getFeatureComparisonTableRowHeadingText(getFeatureValue("complianceText")), getFeatureValue("complianceText"));
                verifyTickFeature("isoCompliance", 12);
                verifySimilarFeature("sso", "proSSO", "teamSSO", 12, null, false);
                assertTrue(getFeatureComparisonTableInfoIcon(getFeatureValue("sso")));
                verifyUnTickFeature("msa", 12, null, false);
                verifyUnTickFeature("hipaaBaa", 12, null, false);
                verifyUnTickFeature("scim", 12, "enterpriseSCIM", true);
                verifyUnTickFeature("saml", 12, "enterpriseSAML", true);
                verifyUnTickFeature("unlimitedEV", 12, "enterpriseEV", true);
                verifyUnTickFeature("domainInvite", 12, "enterpriseDomain", true);
                assertTrue(getFeatureComparisonTableInfoIcon(getFeatureValue("domainInvite")));
                verifyUnTickFeature("complianceAssurance", 12, "enterpriseCA", true);
                assertTrue(getFeatureComparisonTableInfoIcon(getFeatureValue("complianceAssurance")));
                verifyUnTickFeature("legalReview", 12, "enterpriseLR", true);
                assertTrue(getFeatureComparisonTableInfoIcon(getFeatureValue("legalReview")));
                break;

            default:
                fail(featureType);
        }
    }

    public static String getFeatureComparisonTableHeadingText(String heading) {
        return getElementText("//tr[@class='table-heading']/th[contains(text(),'" + heading + "')]");
    }

    public static String getFeatureComparisonTableRowText(String rowText) {
        return getElementText("//tr//td[contains(text(),'" + rowText + "')]");
    }

    public static String getFeatureComparisonTableMainRowText(int tbody, int rowNum, String rowText) {
        return getElementText("//tbody[" + tbody + "]//tr[" + rowNum + "]//td[contains(text(),'" + rowText + "')]");
    }

    public static String getFeatureComparisonTableCustomText(int tbody, String mainRow, String rowText) {
        return getElementText("//tbody[" + tbody + "]//td[contains(text(),'" + mainRow + "')]//following-sibling::td[contains(text(),'" + rowText + "')]");
    }

    public static String getFeatureComparisonTableSimilarText(int tbody, String mainRow, String rowText, int colNum) {
        return getElementText("//tbody[" + tbody + "]//td[contains(text(),'" + mainRow + "')]//following-sibling::td[contains(text(),'" + rowText + "')][" + colNum + "]");
    }

    public static boolean getFeatureComparisonTableTickMark(int tbody, String rowText, int colNum) {
        return isElementVisible("//tbody[" + tbody + "]//td[contains(text(),'" + rowText + "')]//following-sibling::td[" + colNum + "]//i[@class='far fa-check']");
    }

    public static boolean getFeatureComparisonTableInfoIcon(String rowText) {
        return isElementVisible("//td[contains(text(),'" + rowText + "')]//i[@class='far fa-question-circle']");
    }

    public static String getFeatureComparisonTableRowHeadingText(String rowHeading) {
        return getElementText("//th[@class='text-uppercase table-sub-header'][contains(text(),'" + rowHeading + "')]");
    }

    public static void validateAllFooterMenuOptionAndNavigation() {
        page().waitForLoadState(LoadState.LOAD);
        assertTrue(isElementVisible("//section[@class='footer-bg']"));
        getAllLinkAndCheckNavigationForPricingPageFooter();
        assertEquals(getElementText("//p[contains(text(),'Resources')]"), "Resources");
        assertEquals(getElementText("//p[contains(text(),'Solutions')]"), "Solutions");
        assertEquals(getElementText("//p[contains(text(),'Company')]"), "Company");
        assertEquals(getElementText("//p[contains(text(),'Contact')]"), "Contact");
        assertTrue(isElementVisible("//li[@id='contact-numbers']//i"));
        assertEquals(getElementText("//li[@id='contact-numbers']//p[1]"), "(415) 851-1300North America");
        assertEquals(getElementText("//li[@id='contact-numbers']//p[2]"), "(+65) 6890 6699Asia");
        assertEquals(getElementText("//li[@id='contact-numbers']//p[3]"), "(+44) 20 3129 8218UK");
        assertEquals(getElementText("//li[@id='contact-numbers']//p[4]"), "(+61) 2 8317 3321Australia");
        assertTrue(isElementVisible("//i[@class='fas fa-envelope fs-18 p-r-10']"));
        assertEquals(getAttributeValue("//a[contains(text(),'sales@digify.com')]", "href"), "mailto:sales@digify.com");
        assertEquals(getAttributeValue("//a[contains(text(),'support@digify.com')]", "href"), "mailto:support@digify.com");
        assertTrue(isElementVisible("//i[@class='fab fa-linkedin fs-18']"));
        assertEquals(getAttributeValue("//a[@href='https://www.linkedin.com/company/3318986']", "href"), "https://www.linkedin.com/company/3318986");
        assertTrue(isElementVisible("//img[@src='assets/img/ISO-27001@2x.png']"));
    }

    public static void validateAllHeaderMenuOptionAndNavigation() {
        assertTrue(isElementVisible("//nav[@class='nav-bar']"));
        getAllLinkAndCheckNavigationForPricingPageHeader();
    }

    public static void validateLocalizationDropdownOnPricingPage() {

        assertTrue(isElementVisible("//app-language-drop-down[@type='contact']"));
        assertEquals(getElementText("//app-language-drop-down[@type='contact']//p"), "EN");
        clickElement(drdLocal);
        clickElement("//div[@role='menu']//button[1]");
        assertEquals(page().url(), EnvUtils.getBaseUrl().replace("/a", "") + "/pricing/#/plans");
        selectLocaleAndValidatePage(2);
        selectLocaleAndValidatePage(3);
        selectLocaleAndValidatePage(4);
        clickElement(drdLocal);
        clickElement("//div[@role='menu']//button[5]");
        assertEquals(page().url(), EnvUtils.getBaseUrl().replace("/a", "") + "/pricing/pt-pt/#/plans");
        selectLocaleAndValidatePage(6);
        selectLocaleAndValidatePage(7);
        selectLocaleAndValidatePage(8);
        selectLocaleAndValidatePage(9);
        selectLocaleAndValidatePage(10);
        selectLocaleAndValidatePage(11);
        clickElement(drdLocal);
        assertEquals(getElementText("//div[@role='menu']//a"), "Laporkan kesalahan");
        clickAndSwitchToNewTab("//div[@role='menu']//a");
        assertEquals(page().url(), "https://docs.google.com/forms/d/e/1FAIpQLSeQ-wBu0uEvh86YSMfC-kjhlnbbcgOrUeBlrDZRDhx3fUamEg/closedform");
    }

    public static void selectLocaleAndValidatePage(int button) {
        clickElement(drdLocal);
        clickElement("//div[@role='menu']//button[" + button + "]");
        assertEquals(page().url(), EnvUtils.getBaseUrl().replace("/a", "") + "/pricing/" + getElementText("//app-language-drop-down[@type='contact']//p").toLowerCase() + "/#/plans");
    }

    public static void getAllLinkAndCheckNavigationForPricingPageHeader() {
        List<ElementHandle> elements = getListOfMenuItems("//div[@class='inner-header container container-md']//a");
        for (ElementHandle ele : elements) {
            String elementText = ele.textContent().trim();
            String hrefValue = ele.getAttribute("href");
            if (hrefValue != null && !hrefValue.isBlank()) {
                Page newTab = page().context().newPage();
                if (hrefValue.equals("/pricing/#/plans") || hrefValue.equals("/m/trial")) {
                    newTab.navigate(EnvUtils.getBaseUrl().replace("/a", "") + hrefValue);
                } else {
                    newTab.navigate(hrefValue);
                }
                newTab.waitForLoadState();
                if (!newTab.url().contains(hrefValue)) {
                    LogUtils.infoLog("Navigation failed for menu item: " + elementText);
                } else {
                    LogUtils.infoLog("Navigation successful for menu item: " + elementText);
                }
                newTab.close();
            }
        }
    }

    public static void getAllLinkAndCheckNavigationForPricingPageFooter() {
        List<ElementHandle> elements = getListOfMenuItems("//section[@class='footer-section']//a");
        for (ElementHandle ele : elements) {
            String elementText = ele.textContent().trim();
            String hrefValue = ele.getAttribute("href");
            if (hrefValue != null && !hrefValue.isBlank()) {
                Page newTab = page().context().newPage();
                if (hrefValue.equals("https://help.digify.com/en/")) {
                    newTab.navigate(hrefValue);
                } else {
                    newTab.navigate(EnvUtils.getBaseUrl().replace("/a", "") + hrefValue);
                    if (!newTab.url().equals(EnvUtils.getBaseUrl().replace("/a", "") + hrefValue)) {
                        LogUtils.infoLog("Navigation failed for menu item: " + elementText);
                    } else {
                        LogUtils.infoLog("Navigation successful for menu item: " + elementText);
                    }
                }
                newTab.waitForLoadState();
                newTab.close();
            }
        }
    }

    public static List<ElementHandle> getListOfMenuItems(String webLocator) {
        List<ElementHandle> elements = page().querySelectorAll(webLocator);
        if (elements == null || elements.isEmpty()) {
            throw new NoSuchElementException("links not found for the given selector.");
        }
        return elements;
    }

    public static double getElementValueAsDouble(String webLocator) {
        waitUntilSelectorAppears(webLocator);
        Pattern pattern = Pattern.compile("(?:\\$\\s*)?(\\d+(?:\\.\\d{1,4})?)");
        Matcher matcher = pattern.matcher(getElementText(webLocator).replace(",", ""));
        if (matcher.find())
            return Double.parseDouble(matcher.group(1));

        return 0.0;
    }

    private static void verifyTickFeature(String featureKey, int row, int... columnsWithTicks) {
        String value = getFeatureValue(featureKey);
        assertEquals(getFeatureComparisonTableRowText(value), value);

        for (int col : columnsWithTicks) {
            assertTrue(getFeatureComparisonTableTickMark(row, value, col));
        }
    }

    private static void verifyUnTickFeature(String key, int row, String customText, boolean isCustomText) {
        assertEquals(getFeatureComparisonTableRowText(getFeatureValue(key)), getFeatureValue(key));
        assertFalse(getFeatureComparisonTableTickMark(row, getFeatureValue(key), 1));
        assertFalse(getFeatureComparisonTableTickMark(row, getFeatureValue(key), 2));
        if (isCustomText) {
            assertEquals(getFeatureComparisonTableCustomText(row, getFeatureValue(key), getFeatureValue(customText)), getFeatureValue(customText));
        } else {
            assertTrue(getFeatureComparisonTableTickMark(row, getFeatureValue(key), 3));
        }
    }

    private static void verifySimilarFeature(String key, String proKey, String teamKey, int row, String customText, boolean isCustomText) {
        String base = getFeatureValue(key);
        String pro = getFeatureValue(proKey);
        String team = getFeatureValue(teamKey);
        String enterprise = getFeatureValue(customText);
        assertEquals(getFeatureComparisonTableRowText(base), base);
        assertEquals(getFeatureComparisonTableSimilarText(row, base, pro, 1), pro);
        assertEquals(getFeatureComparisonTableSimilarText(row, base, team, 2), team);
        if (isCustomText) {
            assertEquals(getFeatureComparisonTableSimilarText(row, base, enterprise, 3), enterprise);
        } else {
            assertTrue(getFeatureComparisonTableTickMark(row, base, 3));
        }
    }

    public void navigateToPricingPage() {
        visit(PRICING_PAGE_URL, false);
        waitUntilSelectorAppears(getXpathForContainsText(
                "Choose a plan that best fits your needs", "p"));

        if (isElementVisible(btnGoToApp)) {
            clickElement(btnGoToApp);
            waitForURLContaining("/home");
            LoginPage.getLogoutFromApplication();
            visit(PRICING_PAGE_URL, false);
        }
        if (isElementVisible("//div[@class='message']")) {
            clickElement("//div[@class='message']/div[2]");
        }
        waitUntilSelectorAppears(getXpathForContainsText("Billed Monthly", "p"));
        waitUntilSelectorAppears(getXpathForContainsText("Billed Annually", "p"));

        if (waitForSelectorVisibleWithTimeout(bannerCookie, 3)) {
            acceptCookie();
        }
        if (waitForSelectorVisibleWithTimeout(bannerPrivacyConsent, 3)) {
            acceptPrivacyConsent();
        }
    }

    public void acceptCookie(){
        waitUntilSelectorAppears(bannerCookie);
        clickElement("//button[@id='hs-eu-confirmation-button']");
    }

    public void acceptPrivacyConsent(){
        waitUntilSelectorAppears(bannerPrivacyConsent);
        clickElement("(//button[@class='cky-btn cky-btn-accept'])[1]");
    }

    public void clickBuyNowBtnOnPricingPage(String planType) {
        switch (planType.toLowerCase()) {
            case "pro":
                waitUntilSelectorAppears(getXpathForTextEquals(" PRO ", "p"));
                clickElement(btnProBuyNow);
                break;
            case "team":
                waitUntilSelectorAppears(getXpathForTextEquals(" TEAM ", "p"));
                clickElement(btnTeamBuyNow);
                break;
            default:
                fail("Match not found for the buy now button on pricing page: " + planType);
        }
    }

    public void validateModalOnPricingPage(String getPricingPageModalTypes) {
        String lblConfirmSubEmailModal = getXpathForContainsText(" You already have an existing subscription ", "p");
        switch (getPricingPageModalTypes.toLowerCase()) {
            case "signed in before":
                //no need of this
                waitUntilSelectorAppears(getXpathForTextEquals("Have you signed in to Digify before?", "p"));
                assertTrue(isElementVisible(getXpathForContainsText("No", "button")));
                assertTrue(isElementVisible(getXpathForContainsText("Yes", "button")));
                break;
            case "subscription email address":
                waitUntilSelectorAppears(getXpathForTextEquals("Subscription email address", "h5"));
                assertTrue(isElementVisible(getXpathForContainsText("Sign up for a premium plan with this email address.", "p")));
                assertTrue(isElementVisible(getXpathForContainsText("Next", "button")));
                break;
            case "company billing details":
                waitUntilSelectorAppears(getXpathForTextEquals("Company billing details", "h5"));
                selectValueFromDropdownList("//app-company-info-modal/div[2]/form/div[2]/div/i", "//app-company-info-modal/div[2]/form/div[2]/div/ul/li", "Singapore");
                clickElement(btnContinue);
                break;
            case "pro rated charges":
                String lblAmountDue = "//app-prorated-amount-modal/div[1]/h5";
                String lblAmountDueDetail = "//app-prorated-amount-modal/div[2]/div[1]/p";
                waitUntilSelectorAppears(lblAmountDue);
                assertTrue(isElementVisible(lblAmountDue));
                assertTrue(isElementVisible(lblAmountDueDetail));
                break;
            case "confirm subscription email","you already have an existing subscription":
                page().waitForLoadState(LoadState.LOAD);
                waitUntilSelectorAppears(lblConfirmSubEmailModal);
                assertTrue(isElementVisible(lblConfirmSubEmailModal));
                break;
            case "please contact your admin":
                String lblNoChangesToCurrentPlan = "//ngb-modal-window//app-existing-user-modal/div[2]/div[1]/p";
                page().waitForLoadState(LoadState.LOAD);
                assertTrue(isElementVisible(getXpathForContainsText("Please contact your admin", "h5")));
                assertTrue(isElementVisible(lblNoChangesToCurrentPlan));
                clickElement(getXpathForContainsText(" Return to app ", "button"));
                break;
            case "these features are not included in pro plan":
                page().waitForLoadState(LoadState.LOAD);
                assertTrue(isElementVisible(getXpathForContainsText("These features are not included in the Pro Plan:", "h5")));
                break;
            case "confirm subscription email for microsoft user":
                page().waitForLoadState(LoadState.LOAD);
                waitUntilSelectorAppears(lblConfirmSubEmailModal);
                assertTrue(isElementVisible(lblConfirmSubEmailModal));
                try {
                    if (!isElementVisible(lblConfirmSubEmailModal)) {
                        clickElement(btnMSSignIn);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "modify plan":
                assertEquals(getElementText("//app-existing-user-modal/div[2]/div[1]/p[1]"),
                        "Sorry, this quotation link cannot be displayed because you have an existing subscription.");
                assertEquals(getElementText("//app-existing-user-modal/div[2]/div[1]/p[2]"),
                        "Would you like to modify your current plan instead?");
                break;
            case"subscribe with user work email":

                String expectedText = "Subscribe with " + PricingUserEmailManager.getEmail();
                assertTrue(isElementVisible(getXpathForContainsText(expectedText, "h5")));
            break;
                default:
                fail("No match found for the modal on pricing page: " + getPricingPageModalTypes);
        }
    }

    public void clickModalButtonOnPricingPage(String getModalButtonType) {
        switch (getModalButtonType.toLowerCase()) {
            case "no":
                clickElement(getXpathForContainsText("No", "button"));
                break;
            case "yes":
                clickElement(getXpathForContainsText("Yes", "button"));
                break;
            case "cancel":
                clickElement(getXpathForContainsText("Cancel", "button"));
                break;
            case "continue":
                clickElement(getXpathForContainsText("Continue", "button"));
                break;
            case "continue with the pro plan":
                clickElement(getXpathForContainsText("Continue with the Pro Plan", "button"));
                break;
            case "modify plan1":
                clickElement(getXpathForContainsText("Modify plan", "button"));
                break;
            case"modify plan":
                clickElement("//button[@class='btn digify-default-btn default-custom-btn bg-red']");
                break;
            default:
                fail("Click on valid button on modal on pricing page: " + getModalButtonType);
        }
    }

    public void navigateToPricingCheckoutPage() {
        waitUntilSelectorAppears(getXpathForContainsText(
                "Customize your plan", "h4"));
        assertTrue(isElementVisible(getXpathForContainsText(
                "Customize your plan", "h4")));
        assertEquals(getElementText("//h5[contains(text(),'Select Plan')]"), "Select Plan");
    }

    public void selectPlanWithDefaultValue(String planSubscription) {
        assertEquals(getElementText("//div[@class='checkout-label']//p[@class='main-text']"), "Plan");
        assertEquals(getElementText("//p[@class='main-text']//following-sibling::p"), "Choose which plan fits you best");

        switch (planSubscription.toLowerCase()) {
            case "annual":
                clickElement(getXpathForContainsText("annual", "p"));
                break;
            case "monthly":
                clickElement(getXpathForContainsText("Monthly", "p"));
                break;
            default:
                fail("invalid plan subscription" + planSubscription);
        }
    }

    private void validateProPlanBasicDetails() {
        assertEquals(getElementText(lblUserIncluded), "1 users included");
        assertEquals(getElementText(lblDRIncluded), "3 data rooms included");
        assertEquals(getElementText(lblDRGuestIncluded), "50 guests included");
        assertEquals(getElementText(lblPlanUser), "User");
        assertEquals(getElementText(lblPlanUserLine2), "$25/mo (per user)");
    }

    private void validateTeamPlanBasicDetails() {
        assertEquals(getElementText(lblUserIncluded), "3 users included");
        assertEquals(getElementText(lblDRIncluded), "10 data rooms included");
        assertEquals(getElementText(lblDRGuestIncluded), "200 guests included");
        assertEquals(getElementText(lblPlanUser), "User");
        assertEquals(getElementText(lblPlanUserLine2), "$25/mo (per user)");
    }

    public void validatePlanDetails(String planType) {
        planDetails = FileUtils.getPlanDetailsFromJsonFile(planType);
        String lblPlanPrice = "//div[@class='length-card selected']/div/div[2]/p";
        Double lblProDefaultUserCount = Double.valueOf(page().getAttribute(lblPlanUserCount, "step"));
        switch (planType.toLowerCase()) {
            case "proplanannual":
                assertEquals(getElementText(lblDwdPlans), "Please contact support to downgrade your plan.");
                assertTrue(isElementVisible(lnkContactSupport));
                validateProPlanBasicDetails();
                assertEquals(getElementValueAsDouble(lblPlanPrice), getTotalAddonValue("planBasicValue"));
                assertEquals(lblProDefaultUserCount, getTotalAddonValue("proDefaultUserCount"));
                assertEquals(getElementValueAsDouble(lblPlanTotalPrice),
                        getTotalAddonValue("planTotalValue"));
                break;
            case "proplanmonthly":
                validateProPlanBasicDetails();
                assertEquals(getElementValueAsDouble(lblPlanPrice),
                        getTotalAddonValue("planBasicValue"));
                assertEquals(lblProDefaultUserCount,
                        getTotalAddonValue("proDefaultUserCount"));
                assertEquals(getElementValueAsDouble(lblPlanTotalPrice),
                        getTotalAddonValue("planTotalValue"));
                break;
            case "proplanmonthlywithaddon_dw_sh":
                validateProPlanBasicDetails();
                assertTrue(isChecked("//input[@formcontrolname='watermark']"));
                assertEquals(getElementValueAsDouble(txtDynamicWatermarkAddonValue),
                        getTotalAddonValue("proDynamicWatermark"));
                assertTrue(isChecked("//input[@formcontrolname='screenShield']"));
                assertEquals(getElementValueAsDouble(
                                "//p[contains(text(),'Screen Shield')]/following-sibling::p[@class='item-price']"),
                        getTotalAddonValue("proScreenShield"));
                assertEquals(getElementValueAsDouble(lblPlanPrice),
                        getTotalAddonValue("proPlanTotalValueWithDWSH"));
                assertEquals(lblProDefaultUserCount, getTotalAddonValue("proDefaultUserCount"));
                keyPress("PageUp");
                assertEquals(getElementValueAsDouble("//div[@class='review-page']/div[2]/div/div/p"),
                        getTotalAddonValue("proPlanTotalValueWithDWSH"));
                break;
            case "teamplanannual":
                assertEquals(getElementText(lblDwdPlans), "Please contact support to downgrade your plan.");
                assertTrue(isElementVisible(lnkContactSupport));
                validateTeamPlanBasicDetails();
                assertEquals(getElementValueAsDouble(lblPlanPrice),
                        getTotalAddonValue("planBasicValue"));
                assertEquals(getElementValueAsDouble(lblPlanTotalPrice),
                        getTotalAddonValue("planTotalValue"));
                break;
            case "teamplanmonthly":
                validateTeamPlanBasicDetails();
                assertEquals(getElementValueAsDouble(lblPlanTotalPrice),
                        getTotalAddonValue("planTotalValue"));
                break;
            default:
                fail("invalid plan details: " + planType);
        }
    }

    public void selectExtraAddon(List<String> addons) {
        addons.forEach(addon -> {
            switch (addon.toLowerCase()) {
                case "watermark":
                    String watermarkLabel = "//div[@class='optional']/div/div[1]/div/p";
                    assertTrue(isElementVisible((getXpathForContainsText(" Dynamic Watermark ", "span"))));
                    assertTrue(isElementVisible(watermarkLabel));
                    assertEquals(getElementText(watermarkLabel), "$50/mo");
                    dispatchClickElement("input[type='checkbox'][formcontrolname='watermark']");
                    break;
                case "screen shield":
                    String screenShieldLabel = "//div[@class='optional']/div/div[2]/div/p";
                    assertTrue(isElementVisible((getXpathForContainsText(" Screen Shield ", "span"))));
                    assertTrue(isElementVisible(screenShieldLabel));
                    assertEquals(getElementText(screenShieldLabel), "$50/mo");
                    dispatchClickElement("input[type='checkbox'][formcontrolname='screenShield']");
                    break;
                default:
                    fail("Addons does not match: " + addon);
            }
        });
    }

    /**
     * validate redirection from pricing page to home page
     */
    public void navigateToHomePageFromPricingPage() {
        waitUntilSelectorAppears(getXpathForContainsText("Home", "h4"));
        assertTrue(isElementVisible(getXpathForContainsText("Home", "h4")));
    }

    public void validateUpdatedPlanDetails(String upgradedPlanType) {
        planDetails = FileUtils.getPlanDetailsFromJsonFile(upgradedPlanType);
        String txtAmountDue = "//div[contains(text(),' Amount Due ')]/following-sibling::div";
        String txtPlanValue = "//p[@class='text-capitalize']/../p[2]";
        switch (upgradedPlanType.toLowerCase()) {
            case "proplanannual":
                assertEquals(getElementValueAsDouble(txtPlanValue), getTotalAddonValue("planTotalValue"));
                assertEquals(getElementValueAsDouble(txtDynamicWatermarkAddonValue), getTotalAddonValue("proWatermarkAddon"));
                assertEquals(getElementValueAsDouble(txtAmountDue), getTotalAddonValue("planTotalValue", "proWatermarkAddon","proScreenShieldAddon"));
                break;
            case "teamplanannual":
                assertEquals(getElementValueAsDouble(txtPlanValue), (getTotalAddonValue("planTotalValue")));
                assertEquals(getElementValueAsDouble("//p[contains(text(),'Users')]/following-sibling::p[@class='item-price']"), getTotalAddonValue("teamUserAddon"));
                assertEquals(getElementValueAsDouble("//p[contains(text(),'Data Rooms')]/following-sibling::p[@class='item-price']"), getTotalAddonValue("teamDataRoomAddon"));
                assertEquals(getElementValueAsDouble("//p[contains(text(),'Data Room Guests')]/following-sibling::p[@class='item-price']"), getTotalAddonValue("teamDataRoomGuestAddon"));
                assertEquals(getElementValueAsDouble(txtAmountDue), getTotalAddonValue("planTotalValue", "teamUserAddon", "teamDataRoomAddon","teamDataRoomGuestAddon"));
                break;
            default:
                fail("plan details not match: " + upgradedPlanType);
        }
    }

    public void selectExtraAddonsForTeamPlan(DataTable addonDataTable) {
        addonDataTable.asMaps().forEach(row -> {
            String addonType = row.get("AddonType"); // Get the value of the "AddonType" column
            int numberRequired = Integer.parseInt(row.get("NumberRequired"));
            int defaultSelected = Integer.parseInt(row.get("DefaultSelected"));
            int count = (numberRequired - defaultSelected);
            switch (addonType) {
                case "adminUsers":
                    LogUtils.infoLog("Adding " + count + " Admin Users.");
                    String btnPricingUserAddon = "(//span[@class='ant-input-number-handler ant-input-number-handler-up'])[1]/span";
                    clickNNumberOfTimes(btnPricingUserAddon, count);
                    break;
                case "adminDR":
                    LogUtils.infoLog("Adding " + count + " Data Room");
                    String btnPricingDRAddon = "(//span[@class='ant-input-number-handler ant-input-number-handler-up'])[2]/span";
                    clickNNumberOfTimes(btnPricingDRAddon, count);
                    break;
                case "adminDRGuest":
                    LogUtils.infoLog("Adding " + count + " Admin DR Guests.");
                    String btnPricingDRGuestAddOn = "(//span[@class='ant-input-number-handler ant-input-number-handler-up'])[3]/span";
                    clickNNumberOfTimes(btnPricingDRGuestAddOn, count / 25);
                    break;
                default:
                    fail("Pricing page addons not found: " + addonType);
            }
        });
    }

    public void validateEncryptedStorageOnPricingCheckoutPage(String planType) {
        String txtEncryptedStorageDescriptionOnBreakdown = "//p[contains(text(),'Encrypted Storage')]";
        String txtTeamEncStorageBreakdown = getXpathForContainsText("$0/yr  x 5 block (included)","p");
        String txtProEncStorageBreakdown = getXpathForContainsText("$0/yr  x 1 block (included)","p")+"[2]";
        switch (planType.toLowerCase()) {
            case "pro":
                String txtProEncryptedStorageOnCheckoutPage = "(//span[@tooltipclass='digify-tooltip checkout-tooltip'])[8]";
                String lblProEncryptedStorageDescription = "(//span[@tooltipclass='digify-tooltip checkout-tooltip'])[8]/../../div[2]/p";
                assertTrue(isElementVisible(txtProEncryptedStorageOnCheckoutPage));
                assertEquals(getElementText(txtProEncryptedStorageOnCheckoutPage), "Encrypted Storage");
                assertTrue(isElementVisible(lblProEncryptedStorageDescription));
                assertEquals(getElementText(lblProEncryptedStorageDescription), "100GB included per mo. Add more");
                assertTrue(isElementVisible("(//span[@tooltipclass='digify-tooltip checkout-tooltip'])[8]/../../div[2]/p/a"));
                assertTrue(isElementVisible(txtEncryptedStorageDescriptionOnBreakdown));
                assertEquals(getElementText(txtEncryptedStorageDescriptionOnBreakdown),
                        "Encrypted Storage (per 100 GB) x 1");
                assertTrue(isElementVisible(txtProEncStorageBreakdown));
                assertEquals(getElementText(txtProEncStorageBreakdown),
                        "$0/yr x 1 block (included)");
                break;
            case "team":
                String txtTeamEncryptedStorageOnCheckoutPage = "(//span[@tooltipclass='digify-tooltip checkout-tooltip'])[6]";
                String lblTeamEncryptedStorageDescription = "(//span[@tooltipclass='digify-tooltip checkout-tooltip'])[6]/../../div[2]/p";
                assertTrue(isElementVisible(txtTeamEncryptedStorageOnCheckoutPage));
                assertEquals(getElementText(txtTeamEncryptedStorageOnCheckoutPage), "Encrypted Storage");
                assertTrue(isElementVisible(lblTeamEncryptedStorageDescription));
                assertEquals(getElementText(lblTeamEncryptedStorageDescription), "500GB included per mo. Add more");
                assertTrue(isElementVisible("(//span[@tooltipclass='digify-tooltip checkout-tooltip'])[6]/../../div[2]/p/a"));
                assertTrue(isElementVisible(txtEncryptedStorageDescriptionOnBreakdown));
                assertEquals(getElementText(txtEncryptedStorageDescriptionOnBreakdown),
                        "Encrypted Storage (per 100 GB) x 5");
                assertTrue(isElementVisible(txtTeamEncStorageBreakdown));
                assertEquals(getElementText(txtTeamEncStorageBreakdown),
                        "$0/yr x 5 block (included)");
                break;
            default:
                fail("Plan type encrypted storage value does not matched: " + planType);
        }
    }

    public void validateDRQuotaIncludedInPlan(String drQuota) {
        assertEquals(getElementText("//div[@class='add-on-row'][2]//span[@class='small-text']"), drQuota + " data rooms included");
        assertEquals(getElementText("//form[2]/div[1]//div[2]/div[1]/p[1]"), "$50/mo (per data room)");
        assertEquals(getElementText("//div[@class='add-on-row'][2]//p[@class='small-text'][2]"), "$500/mo (unlimited data rooms)");
    }

    public void selectUnlimitedDRCheckbox() {
        assertEquals(getElementText("//label[@for='unlimitedDR']"), "Unlimited data rooms");
        clickElement("//*[@id='unlimitedDR']");
    }

    public void validateDRQuotaInputDisabled() {
        assertTrue(isDisabled("//nz-input-number[@formcontrolname='dataRooms']"));
    }

    public void validateUpgradePlanThankYouPage() {
        String btnContinue = "//button[@class='btn digify-default-btn bg-red']";
        try {
            waitUntilSelectorAppears(btnContinue);
        } catch (TimeoutError ignore) {
        }
        while (isElementVisible("//img[@alt='loading']")) {
            waitForSeconds(2);
        }
        page().waitForLoadState(LoadState.LOAD);
        page().waitForLoadState(LoadState.DOMCONTENTLOADED);
        assertEquals(getElementText("h5"), "Thank you for choosing Digify");
        assertEquals(getElementText("//div[@class='content-center thank-you-wrapper']//p"), "Your plan has been updated.");
        clickElement(btnContinue);
        waitForURLContaining("/a");
    }

    public void validatePremiumFeatureAddons(List<String> addonList) {
        String chkSSO = "//input[@formcontrolname='sso']";
        String chkBranding = "//input[@formcontrolname='advBranding']";
        String chkApi = "//input[@formcontrolname='siteApi']";

        assertEquals(getElementText("//div[@class='optional premium']//h5"), "Premium Add-ons");
        assertEquals(getElementText("//div[@class='optional premium']//h5//following-sibling::p"), "Customize our software with these Premium Add-ons, offering single sign-on interactions, advanced branding, and secure API access. These add-ons require additional setup time and resources from your team.");
        assertEquals(getElementText("//div[@class='optional premium']//a"), "Contact our sales team if you need help");
        assertEquals(getElementText("//span[contains(text(),'Single Sign-On')]//following-sibling::p"), "$100/mo");
        assertEquals(getElementText("//span[contains(text(),'Advanced Branding with White-label URL')]//following-sibling::p"), "$800/mo");
        assertEquals(getElementText("//span[contains(text(),'Site Integrations APIs, Sandbox and Support')]//following-sibling::p"), "$1250/mo");

        addonList.forEach(addon -> {
            switch (addon.toLowerCase()) {
                case "disabled->single sign on":
                    assertTrue(isDisabled(chkSSO));
                    assertTrue(isChecked(chkSSO));
                    break;
                case "disabled->advance branding":
                    assertTrue(isDisabled(chkBranding));
                    assertTrue(isChecked(chkBranding));
                    break;
                case "disabled->site integration api":
                    assertTrue(isDisabled(chkApi));
                    assertTrue(isChecked(chkApi));
                    break;
                case "enabled->sso":
                    assertTrue(isAttributeNotPresent(chkSSO, "disabled"));
                    break;
                case "enabled->branding":
                    assertTrue(isAttributeNotPresent(chkBranding, "disabled"));
                    break;
                case "enabled->site api":
                    assertTrue(isAttributeNotPresent(chkApi, "disabled"));
                    break;
                default:
                    fail("Add ons not matched" + addon);
            }
        });
    }


    public void addNewUserEmail() {
        PricingUserEmailManager.generateNewEmail(); // This stores the email internally
        inputIntoTextField("//input[@placeholder='Work email address']",PricingUserEmailManager.getEmail());
    }

    public void clickNextButton() {
        clickElement(getXpathForContainsText("Next", "Button"));
    }

    public void selectUseThisEmailBtn() {
        clickElement("(//button[@class='btn digify-default-btn'])[1]");
    }
}