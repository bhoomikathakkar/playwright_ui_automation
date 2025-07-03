package digifyE2E.cucumber.stepDefinitions.landingPage;

import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import digifyE2E.pages.landingPage.HomePage;
import digifyE2E.pages.landingPage.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LandingPageStepDef extends CommonHelper {

    @When("I login as {string}")
    public void iLoginAs(String userType) {
        loginPage.loginWithValidUserCreds(userType);
    }

    @When("I login in file viewer as {string}")
    public void iLoginInFileViewerAs(String userType) {
        loginPage.loginInFileViewer(userType);
    }

    @Given("I navigates to trial page")
    public void userNavigatesToTrialPage() {
        trialPage.navigateToTrialPage();
    }

    @Given("I navigate to {string} page from home page")
    public void iNavigateToPageFromHomePage(String pageType) {
        HomePage.navigateToPageFromHomePage(pageType);
    }

    @And("I logout from the application")
    public void iLogoutFromTheApplication() {
        LoginPage.getLogoutFromApplication();
    }

    @And("I logout from file viewer")
    public void iLogoutFromFileViewer() {
        LoginPage.getLogoutFromFileViewer();
    }

    @When("{string} access unavailable file")
    public void accessUnavailableFile(String userType) {
        loginPage.loginInFileWhichIsNotAvailable(userType, "destructive image");
    }

    @Then("I click on {string}")
    public void iClickOn(String thirdPartySignInBtn) {
        LoginPage.clickOnSignInBtnTypeAndValidateModal(thirdPartySignInBtn);
    }

    @Given("I am on the login page")
    @Given("I navigate to login page")
    public void iNavigateToLoginPage() {
        loginPage.navigateToLoginPage();
    }

    @When("I login in microsoft account")
    public void iLoginInInMicrosoftAccount() {
        loginPage.loginInMicrosoftAccount();
    }

    @Then("I login in the application successfully")
    public void iLoginInTheApplicationSuccessfully() {
        loginPage.checkHPAfterLoginThroughMS();
    }

    @Given("I navigates to wordpress site")
    public void iNavigatesToWordpressSite() {
        trialPage.accessWordpressSite();
    }

    @When("{string} access preview not available file")
    public void accessPreviewNotAvailableFile(String userType) {
        loginPage.loginInFileWhichIsNotAvailable(userType, "preview");
    }

    @Given("I am on the white label login page")
    public void iAmOnTheWhiteLabelLoginPage() {
        loginPage.navigateToWhiteLabelLoginPage();
    }
}

