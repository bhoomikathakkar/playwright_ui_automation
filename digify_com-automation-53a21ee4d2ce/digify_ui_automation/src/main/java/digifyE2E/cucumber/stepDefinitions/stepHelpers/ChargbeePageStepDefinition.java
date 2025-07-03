package digifyE2E.cucumber.stepDefinitions.stepHelpers;

import digifyE2E.pages.chargbee.ChargbeePage;
import io.cucumber.java.en.And;


public class ChargbeePageStepDefinition extends CommonHelper {



    @And("I add account information on chargebee page")
    public void userAddAccountInformationOnChargebeePage() {
        ChargbeePage.validateChargeBeeCheckoutPage();
        ChargbeePage.fillAccountInfoOnChargeBee();
    }

    @And("I add billing information on chargbee page with country {string}")
    public void userAddBillingInformationOnChargbeePage(String country) {
        chargbeePage.fillBillingInfoOnChargeBee(country);
    }

    @And("I add payment information on chargbee page with country {string}")
    public void userAddPaymentInformationOnChargbeePage(String country) {
        chargbeePage.fillPaymentInfoOnCB(country);
    }

    @And("I subscribe the plan")
    public void userSubscribeThePlan() {
        chargbeePage.clickOnSubscribeOnCB();
    }

    @And("I navigate to thank you page {string}")
    public void userNavigateToThankYouPage(String planType) {
        chargbeePage.validateThankYouPage(planType);
    }

    @And("I validate plan prices on chargebee page {string}")
    public void userValidatePlanPricesOnChargebeePage(String planType) {
        chargbeePage.validatePlanPriceOnChargeBeePage(planType);
    }

    @And("I access home page")
    public void userAccessHomePage() {
        homePage.accessHomePage();
    }


    @And("I verify FName {string}, LName {string} on ChargeBee Acc Info section")
    public void userVerifyFNameLNameOnChargeBeeAccInfoSection(String fName, String lName) {
        ChargbeePage.validateChargeBeeCheckoutPage();
        chargbeePage.validateAccountInfoOnChargeBee(
                fName.equalsIgnoreCase("CB_NEW_USER") ? ChargbeePage.CBNewUserFName : fName,
                lName.equalsIgnoreCase("CB_NEW_USER") ? ChargbeePage.CBNewUserLName : lName);
    }

    @And("I agrees to terms and conditions on chargebee page")
    public void userAgreesToTermsAndConditionsOnChargebeePage() {
        chargbeePage.checkTermsAndConditionOnCB();
    }

    @And("I verify email {string} on chargeBee Acc Info section")
    public void iVerifyEmailOnChargeBeeAccInfoSection(String emailId) {
        chargbeePage.validateEmailIdOnChargeBee(emailId);
    }
}
