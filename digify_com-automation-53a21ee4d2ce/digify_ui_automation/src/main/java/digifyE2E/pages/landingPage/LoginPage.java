package digifyE2E.pages.landingPage;

import com.microsoft.playwright.options.ElementState;
import com.microsoft.playwright.options.LoadState;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.utils.EnvUtils;
import digifyE2E.utils.FileUtils;
import digifyE2E.utils.LogUtils;
import digifyE2E.utils.RandomUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.testng.Assert.*;


public class LoginPage extends CommonUIActions {

    public static final String newUser = "testqa" + RandomUtils.getRandomString(4, false);
    public static final String btnNext = "//span[@class='txt' and text()='Next ']";
    public static final String LOGIN_PAGE_URL = "/#/access/login";
    public static final String WHITELABEL_LOGIN_PAGE_URL = WHITELABEL_URL + "/#/access/login";
    public static final String txtUsername = "//input[@id='username']";
    public static final String btnSignIn = "//*[@id='enter_password']/div[2]/button";
    public static final String txtFileViewerPassword = "(//input[@type='password'])[1]";
    public static final String btnViewFile = "//pg-tab-body[@class='tab-pane active']/div/div[2]/form/div[2]/button";
    public static final String btnSubmit = "//*[@id='enter_email']/div[2]/button";
    public static final String txtPassword = "//*[@id='password']";
    public static final String txtFileLockPassword = "(//input[@type='password'])[2]";
    static final String btnSignInWithSSO = "//div[@class='sso-login']/button";
    public static Map<String, String> userCredentials = new HashMap<>();
    public static Map<String, String> senderAccLocCred = new HashMap<>();

    public static void getVerificationCodeAsNewUser() {
        waitUntilSelectorAppears(txtVerifyYourIdentity);
        inputIntoTextField(txtUsername, newUser.concat("@maildrop.cc"));
        LogUtils.infoLog("New user is- " + newUser.concat("@maildrop.cc"));
        page().waitForLoadState(LoadState.LOAD);
        clickElement(btnNext);
        waitUntilSelectorAppears("//div[contains(@class,'email-address')]/p"); //validate modal sub-header
    }

    public static void getLogoutFromApplication() {
        page().waitForLoadState(LoadState.LOAD);
        if (isElementVisible(lnkUserProfile)) {
            clickElement(lnkUserProfile);
        } else {
            page().reload();
            waitForSelectorStateChange(lnkUserProfile, ElementState.STABLE);
            clickElement(lnkUserProfile);
        }
        dispatchClickElement(lnkLogout);
        if (isElementVisible(imgLoginPageBrandingLogo)) {
            deleteBrowserSessionData();
        } else {
            page().waitForLoadState(LoadState.LOAD);
            waitUntilSelectorAppears(imgLoginPageBrandingLogo);
            deleteBrowserSessionData();
        }
    }

    public static void getLogoutFromFileViewer() {
        waitUntilSelectorAppears(drdFileViewerUserProfile);
        clickElement(drdFileViewerUserProfile);
        clickElement(lnkLogout);
        if (isElementVisible(getXpathForContainsText("Verify your identity to view the file",
                "p"))) {
            LogUtils.infoLog("Login page is loaded");
        }
        closeCurrentTab();
    }

    public static void clickOnSignInBtnTypeAndValidateModal(String thirdPartySignInBtn) {
        String txtSignIn = getXpathForTextEquals("Sign in", "span");
        switch (thirdPartySignInBtn.toLowerCase()) {
            case "sign in with google":
                clickAndSwitchToNewTab(getXpathForContainsText("Sign in with Google", "span"));
                waitUntilSelectorAppears(txtSignIn);
                assertTrue(isElementVisible(txtSignIn));
                assertTrue(isElementVisible(getXpathForContainsText("Sign in with Google", "div")));
                assertTrue(page().url().contains("/v3/signin"), page().url());
                closeCurrentTab();
                break;
            case "sign in with microsoft":
                clickAndSwitchToNewTab(btnMSSignIn);
                waitUntilSelectorAppears(getXpathForTextEquals("Sign in", "div"));
                assertTrue(page().url().contains("//login.microsoftonline.com"), page().url());
                break;
            case "login page-->ms sign-in":
                clickAndSwitchToNewTab("(//button[@id='msal'])[1]");
                waitUntilSelectorAppears(getXpathForTextEquals("Sign in", "div"));
                assertTrue(page().url().contains("//login.microsoftonline.com"), page().url());
                break;
            default:
                fail("No match found for the third party sign in button: " + thirdPartySignInBtn);
        }
    }

    public static String setWebLocatorAccordingToFileViewerError(String errorImageType) {
        String setErrorImageLocator = "";
        switch (errorImageType) {
            case "preview":
                setErrorImageLocator = "//p[contains(text(),'Preview not available')]/../../div/img";
                break;
            case "destructive image":
                setErrorImageLocator = "//*[@id='destruct-image']";
                break;
            default:
                fail("Please provide valid image type for a file viewer: " + errorImageType);
        }
        return setErrorImageLocator;
    }

    public static void lockAccFileLink(String senderAccount) {
        assertTrue(isElementVisible(getXpathForContainsText("Verify your identity to view the file",
                "p")));
        senderAccLocCred = FileUtils.getUserCredentialsFromJsonFile(senderAccount);
        inputIntoTextField(txtUsername, senderAccLocCred.get("username"));
        clickElement(btnSubmit);
        inputIntoTextField(txtFileLockPassword, senderAccLocCred.get("password"));
        clickElement(btnViewFile);
    }

    public static void accessSubExpiredOrAccDeletedFileLink(String senderAccountStatus) {
        switch (senderAccountStatus.toLowerCase()) {
            case "sender subscription is expired":
                visit(EnvUtils.getBaseUrl() + "/#/access/f/r/3f05d429b161434a9aea1fb733696b0b", false);
                break;
            case "sender account is deleted":
                visit(EnvUtils.getBaseUrl() + "/#/access/f/r/2c72dee6fd7e41b4b355e64dc18be015", false);
                break;
            case "sender account is locked":
                visit(EnvUtils.getBaseUrl() + "/#/access/f/r/38a04fce38f84f53aa08a427187fc688", false);
                break;
            case "dr is expired":
                visit(EnvUtils.getBaseUrl() + "/#/access/f/d/820428e5b58944d4a88c6e57c986d8b0/2", false);
                break;
            case "subscription expired":
                visit(EnvUtils.getBaseUrl() + "/#/access/f/d/f1b243dd6b414399b4c892998327b50c/2", false);
                break;
            case "document security expired file":
                visit(EnvUtils.getBaseUrl() + "/#/f/r/aa502492e4df4cd8af60134b8b40d37f", false);
                break;
            case "data room expired file":
                visit(EnvUtils.getBaseUrl() + "/#/f/d2/98ccfd3368974908a3fc22b6a29ed919", false);
                break;
            default:
                fail("Url does not match: " + senderAccountStatus);
        }
    }

    public void loginInExpiredFile(String recipientUsername, boolean isDataRoomFile) {
        if (!isDataRoomFile) {
            assertTrue(isElementVisible(getXpathForContainsText("Verify your identity to view the file",
                    "p")));
        } else {
            assertTrue(isElementVisible(getXpathForContainsText("Verify your identity to view the data room",
                    "p")));
        }
        userCredentials = FileUtils.getUserCredentialsFromJsonFile(recipientUsername);
        getUserCredsToLoginInFileViewer(userCredentials.get("username"), userCredentials.get("password"));
        clickElement(btnViewFile);
        handleActiveSessionButton();
        page().waitForLoadState(LoadState.LOAD);
    }

    public void typeUserCredsOnLoginScreen(String username, String password) {
        clearValue(txtUsername);
        inputIntoTextField(txtUsername, username);
        clickElement(btnSubmit);
        inputIntoTextField(txtPassword, password);
    }

    public void getUserCredsToLoginInFileViewer(String username, String password) {
        page().reload();
        page().waitForLoadState(LoadState.LOAD);
        inputIntoTextField(txtUsername, username);
        clickElement(btnSubmit);
        inputIntoTextField(txtFileViewerPassword, password);
    }


    public void login(String userType) {
        boolean isWhitelabelAdmin = userType.equals("whitelabelAdmin");
        String loginPageHeadingText = isWhitelabelAdmin ? "Sign in to your account" : "Sign in to your Digify account";
        String homePageUrl = isWhitelabelAdmin ? HomePage.WHITELABEL_HOME_PAGE_URL : HomePage.HOME_PAGE_URL;

        if (isElementVisible(getXpathForContainsText(loginPageHeadingText, "p"))) {
            userCredentials = FileUtils.getUserCredentialsFromJsonFile(userType);
            if (userCredentials == null) {
                LogUtils.infoLog("Credentials for " + userType + " not found.");
                return;
            }
            typeUserCredsOnLoginScreen(userCredentials.get("username"), userCredentials.get("password"));
            clickElement(btnSignIn);
            handleActiveSessionButton();
        } else {
            visit(homePageUrl, !isWhitelabelAdmin);
        }
    }

    public void loginWithValidUserCreds(String userType) {
        login(userType);
        if (isElementVisible(getXpathForContainsText("Home", "h4"))) {
            page().waitForLoadState(LoadState.LOAD);
        } else {
            LogUtils.infoLog("Home page not loaded after login.");
        }
    }

    public void loginInFileViewer(String userType) {
        userCredentials = FileUtils.getUserCredentialsFromJsonFile(userType);
        getUserCredsToLoginInFileViewer(userCredentials.get("username"), userCredentials.get("password"));
        clickElement(btnViewFile);
        handleActiveSessionButton();
        page().waitForLoadState(LoadState.LOAD);
        int i = 1;
        while (isElementVisible("//*[@id='destruct-image']/div[1]/p[1]")
                && i <= 3) {
            page().reload();
            waitForSeconds(5 * i++);
        }
        waitUntilSelectorAppears(containerFileViewerFooter);
    }

    public void loginInFileWhichIsNotAvailable(String userType, String errorImgWebLocator) {
        userCredentials = FileUtils.getUserCredentialsFromJsonFile(userType);
        getUserCredsToLoginInFileViewer(userCredentials.get("username"), userCredentials.get("password"));
        clickElement(btnViewFile);
        handleActiveSessionButton();
        page().waitForLoadState(LoadState.LOAD);
        int maxRetries = 7;
        int retries = 0;
        boolean fileListViewVisible = false;

        while (!fileListViewVisible && retries < maxRetries) {
            fileListViewVisible = isElementVisible(setWebLocatorAccordingToFileViewerError(errorImgWebLocator));

            if (!fileListViewVisible) {
                page().reload();
                retries++;
                waitForSeconds(1);
            }
        }
        if (fileListViewVisible) {
            LogUtils.infoLog("Brand logo loaded on page");
        } else {
            LoginPage.getLogoutFromFileViewer();
        }
    }

    public void loginInDRThroughDRLink(String userType) {
        userCredentials = FileUtils.getUserCredentialsFromJsonFile(userType);
        getUserCredsToLoginInFileViewer(userCredentials.get("username"), userCredentials.get("password"));
        clickElement("(//button[@class='btn digify-btn-blue btn-block'])[2]");
        handleActiveSessionButton();
        page().waitForLoadState(LoadState.LOAD);
        page().waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    public void loginIntoPricingPage(String userType) {
        userCredentials = FileUtils.getUserCredentialsFromJsonFile(userType);
        PricingPage.typeUserCredsOnSubscriptionModal(Objects.requireNonNull(userCredentials).get("username"), userCredentials.get("password"));
        handleActiveSessionButton();
    }

    public void navigateToLoginPage() {
        visit(LOGIN_PAGE_URL, true);
        waitForURLContaining("/access/login");
        assertTrue(page().url().contains("/access/login"));
        waitUntilSelectorAppears("//div[@class='slide-left tab-content animated']/pg-tab-body/div/div/p[contains(text(),'Sign in to your Digify account')]");
        assertTrue(isElementVisible("//div[@class='slide-left tab-content animated']/pg-tab-body/div/div/p[contains(text(),' Sign in to your Digify account ')]"));
        waitForSeconds(2);
    }

    public void navigateToWhiteLabelLoginPage() {
        visit(WHITELABEL_LOGIN_PAGE_URL, false);
        waitForURLContaining("/access/login");
        assertTrue(page().url().contains("/access/login"));
    }

    public void loginInMicrosoftAccount() {
        userCredentials = FileUtils.getUserCredentialsFromJsonFile("microsoftAccountUser");
        inputIntoTextField("//input[@type='email']", userCredentials.get("username"));
        clickElement("//input[@type='submit']");
        waitUntilSelectorAppears("//div[@id='loginHeader']");
        clickElement("//input[@type='password']");
        inputIntoTextField("//input[@type='password']", userCredentials.get("password"));
        clickElement("//button[@type='submit']");
        clickElement("//button[@id='declineButton']");
        closeCurrentTab();
        page().waitForLoadState(LoadState.LOAD);
    }

    public void checkHPAfterLoginThroughMS() {
        if (isElementVisible(btnMSSignIn)) {
            clickElement(btnMSSignIn);
            waitForURLContaining("/home");
        }
        waitForURLContaining("/home");
        waitUntilSelectorAppears(getXpathForContainsText("Home", "h4"));
        assertTrue(page().url().contains("/#/home"));
    }

    public void emailValidation(String username) {
        isElementVisible(getXpathForContainsText("Sign in to your Digify account", "p"));
        inputIntoTextField(txtUsername, username);
        clickElement(btnSubmit);
    }

    public void loginWithSSOUserCreds(String userType) {
        if (isElementVisible(getXpathForContainsText("Sign in to your Digify account", "p"))) {
            LogUtils.infoLog("User Creds are: " + FileUtils.getUserCredentialsFromJsonFile(userType));
            userCredentials = FileUtils.getUserCredentialsFromJsonFile(userType);
            typeUserCredsOnLoginScreen(Objects.requireNonNull(userCredentials).get("username"), userCredentials.get("password"));
            waitUntilSelectorAppears(btnSignInWithSSO);
            clickElement(btnSignInWithSSO);
            waitUntilSelectorAppears("//*[@id='okta-signin-username']");
            assertTrue(page().url().contains("https://dev-990678.oktapreview.com/"));
            inputIntoTextField("//*[@id='okta-signin-username']", Objects.requireNonNull(userCredentials).get("username"));
            inputIntoTextField("//*[@id='okta-signin-password']", Objects.requireNonNull(userCredentials).get("password"));
            clickElement("//*[@id='okta-signin-submit']");
            handleActiveSessionButton();
        } else {
            visit(HomePage.HOME_PAGE_URL, true);
        }
        isElementVisible(getXpathForContainsText("Home", "h4"));
    }

    public void loginWithInValidPwd(String passwordType) {
        try {
            isElementVisible(getXpathForContainsText("Sign in to your Digify account", "p"));
            Map<String, String> userCredentials = FileUtils.getUserCredentialsFromJsonFile("invalidPasswordUser");
            if (userCredentials == null) {
                LogUtils.infoLog("Invalid password user credentials not found");
                return;
            }
            String username = userCredentials.get("username");
            String password;
            switch (passwordType.toLowerCase()) {
                case "invalid_password_user":
                    password = userCredentials.get("password");
                    break;
                case "without_number":
                    password = RandomUtils.getRandomString(6, false);
                    break;
                default:
                    LogUtils.infoLog("Invalid password type");
                    return;
            }
            typeUserCredsOnLoginScreen(username, password);
            clickElement(btnSignIn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enterEmailOfUserType(String userType) {
        switch (userType.toLowerCase()) {
            case "without_@":
                emailValidation(RandomUtils.getRandomString(4, false) + "vomoto.com");
                break;
            case "without_domain":
                emailValidation(RandomUtils.getRandomString(4, false) + "@vomotocom");
                break;
            case "non_registered":
                String email = RandomUtils.getRandomString(4, false) + "@vomoto.com";
                emailValidation(email);
                break;
            case "empty_email":
                emailValidation("");
                break;
            default:
                fail("Invalid option type: " + userType);
        }
    }

    public void validateErrorMessage(String userType) {
        String txtInvalidErrorMsg = "//*[@id='enter_email']/div[2]/print-form-error/div/div";
        switch (userType.toLowerCase()) {
            case "without_@", "without_domain":
                assertEquals(getElementText(txtInvalidErrorMsg), "Please enter a valid email address.");
                break;
            case "empty_email":
                assertEquals(getElementText(txtInvalidErrorMsg), "Email address cannot be blank.");
                break;
            case "non_registered":
                assertEquals(getElementText("//*[@id='enter_email']/label"),
                        "We couldn't find an account for this email address. Please enter a different email or sign up for an account.");
                break;
            case "invalid_password_user", "without_number", "reset password":
                String txtPassError = "//*[@id='enter_password']/div[@class='digify-error-text']";
                if (isElementVisible(txtPassError)) {
                    waitUntilSelectorAppears(txtPassError);
                    assertEquals(getElementText(txtPassError), "Wrong password.");
                } else {
                    waitUntilSelectorAppears(getXpathForTextEquals(" Reset Password ", "button"));
                    clickElement("//button[@class='close pull-right']/span");
                    LogUtils.infoLog("Maximum attempt limit reached hence redirected to reset password page");
                }
                break;
            default:
                fail("Invalid option type: " + userType);
        }
    }

    public void checkHomePageAfterTeamRestore(String userType) {
        login(userType);
        if (isElementVisible(getXpathForContainsText("Home", "h4"))) {
            page().waitForLoadState(LoadState.LOAD);
        } else {
            LogUtils.infoLog("Team Restoration pending");
            validateDisbandErrorModalOnLoginPage("Restore team", userType);
            validateDisbandErrorModalOnLoginPage("Confirm account restoration", userType);
            int atIndex = userCredentials.get("username").indexOf('@');
            confirmTeamRestorationEmail(userCredentials.get("username").substring(0, atIndex));
            loginWithValidUserCreds(userType);
        }
    }
}