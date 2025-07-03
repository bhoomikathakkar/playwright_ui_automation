package digifyE2E.pages.landingPage;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.options.LoadState;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.utils.FileUtils;
import digifyE2E.utils.LogUtils;
import digifyE2E.utils.RandomUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class TrialPage extends CommonUIActions {

    static final String email = "//input[@formcontrolname='userEmail']";
    static final String password = "//input[@formcontrolname='newPassword']";
    static final String firstName = "//input[@formcontrolname='firstName']";
    static final String lastName = "//input[@formcontrolname='lastName']";
    public static Map<String, String> userCredentials = new HashMap<>();

    public void navigateToTrialPage() {
        String txt7dayFreeTrial = "//div[@class='trail-header-text']";
        visit(PRICING_PAGE_URL, false);
        PricingPage.clickOnPricingPageCTAButton("start free trial");
        isElementVisible(txt7dayFreeTrial);
    }

    public void navigateToHomeFromTrialErrorPage() {
        clickElement(getXpathForTextEquals(" Go to Digify ", "a"));
        waitForURLContaining("/home");
    }

    public void validateTrialErrorMsg() {
        try {
            if (isElementVisible(CommonUIActions.btnMSSignIn)) {
                clickElement(CommonUIActions.btnMSSignIn);
            }
        } catch (Exception e) {
            LogUtils.infoLog("Error clicking element: " + e.getMessage());
        }
        waitForURLContaining("/trial/st");
        waitUntilSelectorAppears(getXpathForContainsText("Unable to start your trial", "h5"));
    }

    public void enterFirstAndLastName() {
        inputIntoTextField(firstName, RandomUtils.getRandomString(4, false));
        inputIntoTextField(lastName, RandomUtils.getRandomString(4, false));
    }

    public String getPassword(String passwordType) {
        switch (passwordType.toLowerCase()) {
            case "length_less_than_10" -> {
                return RandomUtils.getRandomString(5, false);
            }
            case "valid_password" -> {
                return RandomUtils.getRandomString(4, false)
                        + "#$"
                        + RandomUtils.getRandomString(4, true);
            }
            case "without_number" -> {
                return RandomUtils.getRandomString(10, false);
            }
            case "without_letter" -> {
                return RandomUtils.getRandomString(10, true);
            }
            case "without_symbol" -> {
                return RandomUtils.getRandomString(5, true)
                        + RandomUtils.getRandomString(5, false);
            }
            case "first_name_as_password" -> {
                return getInputValue(firstName);
            }
            case "last_name_as_password" -> {
                return getInputValue(lastName);
            }
            case "email_as_password" -> {
                return getInputValue(email);
            }
            case "domain_as_password" -> {
                return getInputValue(email).substring(getInputValue(email).length() - 13);
            }
            case "with_space_password" -> {
                return RandomUtils.getRandomString(8, true) + "  ";
            }
            case "3consecutive_character" -> {
                return RandomUtils.getRandomString(3, true)
                        + "bbb"
                        + "#"
                        + RandomUtils.getRandomString(4, false);
            }
            default -> fail("password type not matched: " + passwordType);
        }
        return "default_password";
    }

    public void enterEmailOfUserTypeAndPasswordOfPasswordType(String emailType, String passwordType) {
        switch (emailType.toLowerCase()) {
            case "invalid_email":
                inputIntoTextField(email, RandomUtils.getRandomString(5, true));
                clearValue(password);
                inputIntoTextField(password, getPassword(passwordType));
                break;
            case "registered_email":
                userCredentials = FileUtils.getUserCredentialsFromJsonFile(emailType.toLowerCase());
                inputIntoTextField(email, userCredentials.get("username"));
                clearValue(password);
                inputIntoTextField(password, getPassword(passwordType));
                break;
            case "valid_email":
                inputIntoTextField(email, "autoemail" + "-" + RandomUtils.getRandomString(3, true) + "@maildrop.cc");
                clearValue(password);
                inputIntoTextField(password, getPassword(passwordType));
                break;
            case "empty_email":
                inputIntoTextField(email, "");
                clearValue(password);
                inputIntoTextField(password, "");
                break;
            default:
                fail("email type not matched: " + emailType);
        }
    }

    public void verifyTheTrialFieldErrorMessageEmailType(String emailPwdType) {
        String emailErrorMsg = "//app-trial//div[2]/form/div[1]/div[2]";
        String pwdErrorMsg = "//app-trial//div[2]/form/div[3]/div[2]";
        String pwdErrorMsg1 = "//app-trial//div[2]/form/div[3]/div[3]";
        switch (emailPwdType.toLowerCase()) {
            case "invalid_email":
                assertEquals(getElementText(emailErrorMsg),
                        normalizeSpaceForString("Please enter a valid email address."));
                break;
            case "registered_email", "valid_password":
                assertEquals(getElementText(emailErrorMsg),
                        normalizeSpaceForString("Email address is already in use. Log in"));
                break;
            case "valid_email":
                LogUtils.infoLog("The enter email is valid");
                break;
            case "length_less_than_10", "without_number", "without_letter", "without_symbol":
                assertEquals(getElementText(pwdErrorMsg),
                        normalizeSpaceForString("Your password must be at least 10 characters, with 1 letter, 1 number, and 1 symbol."));
                break;
            case "first_name_as_password", "last_name_as_password", "with_space_password":
                waitUntilSelectorAppears(pwdErrorMsg);
                waitUntilSelectorAppears(pwdErrorMsg1);
                assertEquals(getElementText(pwdErrorMsg),
                        normalizeSpaceForString("Your password must be at least 10 characters, with 1 letter, 1 number, and 1 symbol."));
                assertEquals(getElementText(pwdErrorMsg1), normalizeSpaceForString(
                        "Your password cannot contain your name, email address, domain, spaces and non-English characters."));
                break;
            case "email_as_password", "domain_as_password":
                waitUntilSelectorAppears(pwdErrorMsg);
                assertEquals(getElementText(pwdErrorMsg),
                        normalizeSpaceForString("Your password cannot contain your name, email address, domain, spaces and non-English characters."));
                break;
            case "empty_email":
                assertEquals(getElementText(emailErrorMsg), normalizeSpaceForString("Please provide your email address."));
                break;
            case "empty_password":
                assertEquals(getElementText(pwdErrorMsg), normalizeSpaceForString("Password cannot be blank."));
                break;
            case "3consecutive_character":
                assertEquals(getElementText(pwdErrorMsg),
                        normalizeSpaceForString("Your password cannot have 3 consecutive identical characters."));
                break;
            default:
                fail("email and password type not match: " + emailPwdType);
        }
    }

    public void accessWordpressSite() {
        try {
            visit(WORDPRESS_URL, false);
        } catch (Exception e) {
            clickElement(getXpathForTextEquals("Start free trial", "span"));
        }
        String pageHeading = "//section[1]/div[2]//div[2]/div/h1";
        waitUntilSelectorAppears(pageHeading);
        assertEquals(getElementText(pageHeading),
                normalizeSpaceForString("Document security & analytics made simple"));
        isElementVisible("(//span[text()='LOG IN'])[2]/../..");
        isElementVisible("(//span[text()='BOOK DEMO'])[2]/../..");
    }

    public void clickOnStartFreeTrialBtnOnWordpressSite() {
        clickElement("((//a[@href='/m/trial'])[1]/span/span[text()='START FREE TRIAL'])");
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears("//div[@class='trail-header-text']");
    }

    public void enterValidEmailId() {
        page().waitForLoadState(LoadState.LOAD);
        List<ElementHandle> elements = page().querySelectorAll("//input[@formcontrolname='userEmail']");
        if (!elements.isEmpty()) {
            ElementHandle element = elements.get(0);
            element.click();
            element.fill("auto_" + RandomUtils.getRandomString(4, true) + "@maildrop.cc");
        }
    }

    public void enterPasswordInTextField(String passwordType) {
        inputIntoTextField(password, getPassword(passwordType));
    }
}