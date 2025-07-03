package digifyE2E.cucumber.stepDefinitions.landingPage;

import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TrialPageStepDefinition extends CommonHelper {

    @Then("error message appears as Unable to start your trial")
    public void errorMessageAppearsAsUnableToStartYourTrial() {
        trialPage.validateTrialErrorMsg();
    }

    @And("I click on go to digify app")
    public void iClickOnGoToDigifyApp() {
        trialPage.navigateToHomeFromTrialErrorPage();
    }

    @When("I enter first name and last name")
    public void iEnterFirstNameAndLastName() {
        trialPage.enterFirstAndLastName();
    }

    @And("I enter email of {string} and password of {string}")
    public void iEnterEmailOfUserTypeAndPasswordOfPasswordType(String emailType, String passwordType) {
        trialPage.enterEmailOfUserTypeAndPasswordOfPasswordType(emailType, passwordType);
    }

    @Then("I verify the trial page field error message {string}")
    public void iVerifyTheTrialFieldErrorMessageEmailType(String emailPwdType) {
        trialPage.verifyTheTrialFieldErrorMessageEmailType(emailPwdType);
    }

    @When("I click on START FREE TRIAL button on wordpress site")
    public void iClickOnStartFreeTrialButton() {
        trialPage.clickOnStartFreeTrialBtnOnWordpressSite();
    }

    @When("I enter valid email id")
    public void iEnterValidEmail() {
        trialPage.enterValidEmailId();
    }

    @And("I enter password as: {string}")
    public void iEnterPasswordInTextField(String passwordType) {
        trialPage.enterPasswordInTextField(passwordType);
    }
}