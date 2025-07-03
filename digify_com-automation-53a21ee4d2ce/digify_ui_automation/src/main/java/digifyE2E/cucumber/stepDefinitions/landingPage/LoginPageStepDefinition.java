package digifyE2E.cucumber.stepDefinitions.landingPage;

import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import digifyE2E.pages.landingPage.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginPageStepDefinition extends CommonHelper {

    @When("I enter email of {string}")
    public void iEnterEmailOfUserType(String userType) {
        loginPage.enterEmailOfUserType(userType);
    }

    @Then("I verify the error message {string}")
    @Then("I validate {string} error page")
    public void iVerifyTheErrorMessageUserType(String userType) {
        loginPage.validateErrorMessage(userType);
    }

    @Given("I login with invalid {string}")
    public void iLoginWithInvalid(String passwordType) {
        loginPage.loginWithInValidPwd(passwordType);
    }

    @Given("I login as SSOUser {string}")
    public void iLoginAsSSOUser(String userType) {
        loginPage.loginWithSSOUserCreds(userType);
    }

    @And("I login as {string} and validate home page after team restore")
    public void iLoginAsAndCheckHomePage(String userType) {
        loginPage.checkHomePageAfterTeamRestore(userType);
    }

    @When("I access the dr link when {string}")
    public void iAccessTheDrLinkWhen(String drStatus) {
        LoginPage.accessSubExpiredOrAccDeletedFileLink(drStatus);
    }
}
