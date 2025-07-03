package digifyE2E.cucumber.stepDefinitions.landingPage;

import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.landingPage.PricingPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Arrays;

public class PricingPageStepDefinition extends CommonHelper {
    @Given("I navigates to pricing page")
    public void userNavigatesToPricingPage() {
        pricingPage.navigateToPricingPage();
    }

    @Then("I clicks {string} on {string} modal")
    public void userClicksOnModal(String getButtonInModal, String getModalOnPricingPage) {
        pricingPage.validateModalOnPricingPage(getModalOnPricingPage);
        pricingPage.clickModalButtonOnPricingPage(getButtonInModal);
    }

    @Then("system should show {string} modal on pricing page")
    public void systemShouldShowModalOnPricingPage(String getModalOnPricing) {
        pricingPage.validateModalOnPricingPage(getModalOnPricing);
    }

    @When("I click on buy now button for {string} plan")
    public void userClicksOnBuyNowButtonForThe(String planType) {
        pricingPage.clickBuyNowBtnOnPricingPage(planType);
    }

    @Then("I navigates to pricing checkout page")
    public void userNavigatesToPricingCheckoutPage() {
        pricingPage.navigateToPricingCheckoutPage();
    }

    @And("I select {string} plan with default value")
    public void userSelectPlanWithDefaultValue(String planSubscription) {
        pricingPage.selectPlanWithDefaultValue(planSubscription);
    }

    @And("I clicks continue on {string} modal on pricing checkout page")
    public void clickOnContinueOnBillingDetailsCountryModal(String getModalOnPricingChkPage) {
        pricingPage.validateModalOnPricingPage(getModalOnPricingChkPage);
    }

    @And("I verify the initial price for the {string} plan")
    public void userVerifyTheInitialPriceForThePlan(String planType) {
        PricingPage.navigateToPricingCheckoutPageAsExistingUser();
        pricingPage.validatePlanDetails(planType);
    }

    @And("I select the extra addons {string}")
    public void userSelectTheExtraAddons(String addon) {
        pricingPage.selectExtraAddon(Arrays.asList(addon.split(",")));
    }

    @And("I click on button {string}")
    public void userClickOnButton(String buttonType) {
        PricingPage.clickOnPricingPageCTAButton(buttonType);
    }

    @And("I login into the application from pricing page as {string}")
    public void userLoginIntoTheApplicationFromPricingPage(String userType) {
        loginPage.loginIntoPricingPage(userType);
    }

    @And("system should redirect user to home page from pricing page")
    public void systemShouldRedirectUserToHomePageFromPricingPage() {
        pricingPage.navigateToHomePageFromPricingPage();
    }

    //todo: Remove hard coded value from pricing json file, and calculate the price based on the no of increased feature type
    @And("system should show updated plan details {string}")
    @And("I validated pricing checkout page summary for {string} plan")
    public void systemShouldShowUpdatedPlanDetails(String upgradedPlanType) {
        pricingPage.validateUpdatedPlanDetails(upgradedPlanType);
    }

    @And("I select the extra addons for team plan:")
    @And("I validate the added extra addons:")
    public void userSelectTheExtraAddonsForTeamPlan(DataTable dataTable) {
        pricingPage.selectExtraAddonsForTeamPlan(dataTable);
    }

    @And("I close the modal")
    public void iCloseTheModal() {
        clickElement(CommonUIActions.btnCloseModal);
    }

    @And("I validated the selected addons for {string} plan")
    public void iValidatedTheSelectedAddonsForProPlan(String planType) {
        pricingPage.validatePlanDetails(planType);
    }

    @Then("I validate encrypted storage value on pricing checkout page for {string} plan user")
    public void iValidateEncryptedStorageValueOnPricingCheckoutPageForPlanUser(String planType) {
        pricingPage.validateEncryptedStorageOnPricingCheckoutPage(planType);
    }

    @And("I validated {string} included with plan")
    public void iValidatedIncludedWithPlan(String drQuota) {
        pricingPage.validateDRQuotaIncludedInPlan(drQuota);
    }

    @And("I added unlimited DR quota")
    @And("I remove unlimited DR quota")
    public void iAddedUnlimitedDRQuota() {
        pricingPage.selectUnlimitedDRCheckbox();
    }

    @And("I verify that DR quota input is disabled")
    public void iVerifyThatDRQuotaInputGettingDisabled() {
        pricingPage.validateDRQuotaInputDisabled();
    }

    @And("I validate thank you page for plan upgrade")
    public void iValidateThankYouPageForPlanUpgrade() {
        pricingPage.validateUpgradePlanThankYouPage();
    }

    @And("I validate disabled premium feature add ons {string} on pricing checkout page")
    @And("I validate enabled premium feature add ons {string} on pricing checkout page")
    public void iValidatedPremiumFeatureAddOnsOnPricingCheckoutPage(String addonList) {
        pricingPage.validatePremiumFeatureAddons(Arrays.asList(addonList.split(",")));
    }

    @And("I navigate to pricing checkout page as existing user")
    public void iNavigateToPricingCheckoutPageAsExistingUser() {
        PricingPage.navigateToPricingCheckoutPageAsExistingUser();
    }

    @Then("I verify plan name, price and {int} features listed on {string} plan card")
    public void iVerifyFeaturesListedOnPlanCard(int noOFFeatures, String planType) {
        PricingPage.validateTotalNoOFFeatureListedOnPlanCard(noOFFeatures, planType);
    }

    @Then("I verify {string} feature from full feature comparison table on the pricing page")
    public void iVerifyFullFeatureComparisonTableOnThePricingPage(String featureType) {
        PricingPage.validateFullFeatureComparisonTableOnPricingPage(featureType);
    }

    @Then("I verify all header options and navigation on pricing page")
    public void iVerifyAllHeaderOptionsAndNavigationOnPricingPage() {
        PricingPage.validateAllHeaderMenuOptionAndNavigation();
    }

    @Then("I verify all footer options and navigation on pricing page")
    public void iVerifyAllFooterOptionsAndNavigationOnPricingPage() {
        PricingPage.validateAllFooterMenuOptionAndNavigation();
    }

    @And("I verify localization dropdown")
    public void iVerifyLocalizationDropdown() {
        PricingPage.validateLocalizationDropdownOnPricingPage();
    }

    @And("I add new user work email address")
    public void iAddNewUserWorkEmailAddress() {
        pricingPage.addNewUserEmail();
    }

    @Then("I click next button")
    public void iClickNextButton() {
        pricingPage.clickNextButton();
    }

    @And("I select button: use this email address")
    public void iSelectButtonUseThisEmailAddress() {
        pricingPage.selectUseThisEmailBtn();
    }
}