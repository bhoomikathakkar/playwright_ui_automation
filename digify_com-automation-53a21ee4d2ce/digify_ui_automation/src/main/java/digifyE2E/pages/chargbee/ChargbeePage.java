package digifyE2E.pages.chargbee;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.ElementState;
import com.microsoft.playwright.options.LoadState;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.landingPage.PricingPage;
import digifyE2E.testDataManager.PricingUserEmailManager;
import digifyE2E.testDataManager.SharedDM;
import digifyE2E.utils.FileUtils;
import digifyE2E.utils.RandomUtils;

import java.util.HashMap;
import java.util.Map;
import static digifyE2E.pages.landingPage.PricingPage.getTotalAddonValue;
import static org.testng.Assert.*;


public class ChargbeePage extends CommonUIActions {

    private static final String txtCBFirstName = "//*[@id='customer[first_name]']";
    private static final String txtCBLastName = "//*[@id='customer[last_name]']";
    private static final String txtCBEmail = "//*[@id='customer[email]']";
    public static String CBNewUserFName = "autoF" + "-" + RandomUtils.getRandomString(2, false);
    public static String CBNewUserLName = "autoL" + "-" + RandomUtils.getRandomString(2, false);
    public static String CBNewUserEmail = "autoemail" + "-" + RandomUtils.getRandomString(4, false) + "@maildrop.cc";
    public static String CBStateBillingInfo = "autoState" + "-" + RandomUtils.getRandomString(2, false);
    public static Map<String, Double> planDetails = new HashMap<>();


    public static void validateChargeBeeCheckoutPage() {
        page().waitForURL("https://digify-test.chargebee.com/**");
        assertTrue(isElementVisible("//*[@id='cb-payment-method-title']"));
    }

    public static void fillAccountInfoOnChargeBee() {
        inputIntoTextField(txtCBFirstName, CBNewUserFName);
        inputIntoTextField(txtCBLastName, CBNewUserLName);
    }

    private static double addGST(double amount) {
        return amount + (amount * 0.08);
    }


    public void fillBillingInfoOnChargeBee(String country) {
        String ddBillCountry = "//select[@id='billing_address[country]']";
        String CBFNameBillingInfo = "autoFBill" + "-" + RandomUtils.getRandomString(2, false);
        String CBAddressBillingInfo = "autoAdd" + "-" + RandomUtils.getRandomString(2, false);
        String CBCityBillingInfo = "autoCity" + "-" + RandomUtils.getRandomString(2, false);
        String CBPhoneBillingInfo = RandomUtils.getRandomString(8, true);

        inputIntoTextField("//*[@id='billing_address[first_name]']", CBFNameBillingInfo);
        inputIntoTextField("//*[@id='billing_address[line1]']", CBAddressBillingInfo);
        inputIntoTextField("//*[@id='billing_address[city]']", CBCityBillingInfo);
        selectValueFromSelectDropdownList(ddBillCountry, country);
        inputIntoTextField("//*[@id='billing_address[email]']", PricingUserEmailManager.getEmail());
        inputIntoTextField("//*[@id='billing_address[phone]']", CBPhoneBillingInfo);
        inputIntoTextField("//*[@id='billing_address[state]']", CBStateBillingInfo);
    }

    public void checkTermsAndConditionOnCB() {
        clickElement("//*[@id='cb-checkout-legal-options']");
    }

    public void fillPaymentInfoOnCB(String country) {
        inputIntoTextField("//*[@id='card[first_name]']", CBNewUserFName);
        inputIntoTextField("//*[@id='card[last_name]']", CBNewUserLName);
        selectValueFromSelectDropdownList("//*[@id='card[billing_country]']", country);
        inputIntoTextField("//*[@id='card[billing_state]']", CBStateBillingInfo);
        clickElement("//div[@id='cb-test-cards']");
        waitUntilSelectorAppears("//div[@class='panel-heading']");
        clickElement("//*[@id='cb-test-cards']/div/ul/li[1]");
    }

    public void clickOnSubscribeOnCB() {
        Locator btnSubscribe = page().locator("//input[@value='Subscribe']");
        btnSubscribe.elementHandle().waitForElementState(ElementState.ENABLED);
        btnSubscribe.click();
        page().waitForLoadState(LoadState.LOAD);
        waitForURLContaining("/thankyou");
    }

    public void validateThankYouPage(String planType) {
        waitUntilSelectorAppears("h5");
        waitUntilSelectorAppears("p");
        assertEquals(getElementText("h5").toLowerCase(),
                "thank you for subscribing to the digify " + planType + " plan");
        isElementVisible(getXpathForContainsText(
                "To continue, please click on the button in the validation email sent to", "p"));
    }

    public void validateAccountInfoOnChargeBee(String firstName, String lastName) {
        assertEquals(getInputValue(txtCBFirstName), firstName);
        assertEquals(getInputValue(txtCBLastName), lastName);

    }

    public void validatePlanPriceOnChargeBeePage(String planType) {
        planDetails = FileUtils.getPlanDetailsFromJsonFile(planType);
        switch (planType.toLowerCase()) {
            case "proplanannual":
                assertEquals(PricingPage.getElementValueAsDouble("//*[@id='cb-order-summary-main-list']/ul/li[1]/div[2]"), getTotalAddonValue("planTotalValue"));
                assertEquals(PricingPage.getElementValueAsDouble("//*[@id='cb-order-summary-main-list']/ul/li[6]/div[2]"), getTotalAddonValue("proWatermarkAddon"));
                assertEquals(PricingPage.getElementValueAsDouble("//*[@id='cb-order-summary-main-list']/ul/li[7]/div[2]"),getTotalAddonValue("proScreenShieldAddon"));
                assertEquals(PricingPage.getElementValueAsDouble("//*[@id='cb-order-total']/div[2]"), addGST(getTotalAddonValue("planTotalValue", "proWatermarkAddon","proScreenShieldAddon")));
                break;
            case "teamplanannual":
                assertEquals(PricingPage.getElementValueAsDouble("//*[@id='cb-order-summary-main-list']/ul/li[1]/div[2]"), getTotalAddonValue("planTotalValue"));
                assertEquals(PricingPage.getElementValueAsDouble("//*[@id='cb-order-summary-main-list']/ul/li[2]/div[2]"), getTotalAddonValue("teamUserAddon"));
                assertEquals(PricingPage.getElementValueAsDouble("//*[@id='cb-order-summary-main-list']/ul/li[4]/div[2]"), getTotalAddonValue("teamDataRoomAddon"));
                assertEquals(PricingPage.getElementValueAsDouble("//*[@id='cb-order-total']/div[2]"), addGST(getTotalAddonValue("planTotalValue", "teamUserAddon", "teamDataRoomAddon")));
                break;
            default:
                fail("planType not matched");
        }
    }

    public void validateEmailIdOnChargeBee(String emailId) {
        assertEquals(getInputValue(txtCBEmail), emailId);
    }
}