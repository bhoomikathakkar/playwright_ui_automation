package digifyE2E.cucumber.stepDefinitions.adminSettings;

import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import digifyE2E.pages.AdminSettings.AdminSettingsPage;
import digifyE2E.pages.landingPage.HomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.util.Arrays;

public class AdminSettingsStepDefinition extends CommonHelper {

    @Then("I clicked on modify plan in billing tab and redirected to pricing review and checkout page")
    public void userRedirectToPricingCheckoutPageByClickingOnModifyPlanButton() {
        AdminSettingsPage.navigateToPricingPageClickingOnModifyBtn();
    }

    @When("I click on {string} button in section")
    @And("I click on {string} button in modal")
    public void userClickOnButtonInSection(String buttonType) {
        AdminSettingsPage.clickBtnInAdminSettingPage(buttonType);
    }

    @And("I select {string} as cancellation reason in subscription cancellation flow")
    public void userSelectCancellationReason(String planCancelReason) {
        AdminSettingsPage.userSelectPlanCancellationReason(planCancelReason);
    }

    @And("I validate document list in subscription cancellation flow step 2 as per {string}")
    public void userValidateDocumentListAsPerPlanCancelReason(String documentType) {
        AdminSettingsPage.userValidateDocumentListAsPerPlanCancelReason(documentType);
    }

    @And("I validate feedback form as per {string} and select {string}")
    public void userValidateFeedbackFormAsPerPlanCancelReason(String feedbackType, String projectPauseDuration) {
        AdminSettingsPage.userValidateFeedbackFormAsPerPlanCancelReason(feedbackType, projectPauseDuration);
    }

    @And("I confirm my subscription cancellation")
    public void userConfirmCancellation() {
        AdminSettingsPage.confirmCancellation();
    }

    @Then("I validate thank you page for cancelled subscription")
    public void userValidateThankYouPageForCancelledSubscription() {
        AdminSettingsPage.validateCancellationSchedulePage();
    }

    @And("I validate remove cancellation on billing tab")
    public void userValidateRemoveCancellationOnBillingTab() {
        AdminSettingsPage.validateRemoveCancellationOnBillingTab();
    }

    @And("I added member {string} to team : {string}")
    public void userAddedMemberToTeam(String memberType, String userType) {
        AdminSettingsPage.addMember(memberType, userType);
    }

    @And("I validated added member details as per {string}")
    public void userValidatedAddedMemberDetailsAsPerMemberType(String permissionType) {
        AdminSettingsPage.validatedAddedMemberDetails(permissionType);
    }

    @And("I {string} invite")
    public void userCancelOrResendInvite(String inviteType) {
        AdminSettingsPage.performActionOnNewMemInviteSent(inviteType);
    }

    @Then("I validate {string} modal on admin settings page")
    @And("I validate {string} modal on admin settings page and confirm cancel")
    @And("I validate {string} modal on admin settings page and close the modal")
    public void userValidateModalOnAdminSettings(String modalType) {
        AdminSettingsPage.validateModalOnAdminSettings(modalType);
    }

    @And("I validated available member licence as per userType {string}")
    public void userValidatedAvailableMemberLicenceAsPerUserType(String userType) {
        AdminSettingsPage.validateAvailableMemberLicenceAsPerUserType(userType);
    }

    @And("I create preset with {string}")
    public void userClickOnCreatePresetButton(String presetName) {
        AdminSettingsPage.createPreset(presetName);
    }

    @And("I remove the preset {string}")
    public void userRemoveThePreset(String presetName) {
        AdminSettingsPage.removePreset(presetName);
    }

    @And("I Added feature {string}")
    @And("I Updated feature {string}")
    public void userCreatePresetWithVariousSettings(String presetFeature) {
        AdminSettingsPage.createPresetWithDifferentFeatures(Arrays.asList(presetFeature.split(",")));
    }

    @When("I edit the preset {string}")
    public void userEditThePreset(String presetName) {
        AdminSettingsPage.editPreset(presetName, false, null);
    }

    @When("I edit the preset {string} and updating name {string}")
    public void userEditThePresetWithName(String presetName, String newPresetName) {
        AdminSettingsPage.editPreset(presetName, true, newPresetName);
    }

    @When("I {string} custom preset")
    public void userToggleCustomPreset(String presetState) {
        AdminSettingsPage.EnableOrDisableCustomPreset(presetState);
    }

    @And("I remove the existing preset {string}")
    public void userRemoveTheExistingPreset(String presetName) {
        AdminSettingsPage.removeExistingPreset(presetName);
    }

    @And("I validate custom term settings in admin tab")
    public void userValidateCustomTermSettings() {
        AdminSettingsPage.validateCustomTermSettings();
    }

    @And("I {string} the custom term in admin tab")
    public void userDisableTheCustomTerm(String termState) {
        AdminSettingsPage.updateCustomTermState(termState);
    }

    @And("I validate organization term in admin tab")
    public void userValidateOrganizationTerm() {
        AdminSettingsPage.validateOrganizationTerm();
    }

    @Then("I modify organization term in admin tab")
    public void userModifyOrganizationTerm() {
        AdminSettingsPage.modifyOrganizationTerm();
    }

    @And("I click on {string} for the {string} team member")
    public void userUpdateTeamMember(String action, String memberName) {
        AdminSettingsPage.updateTeamMember(action, memberName);
    }

    @And("I update role to {string} for {string} team member")
    public void updateUserRole(String userRole, String memberEmail) {
        AdminSettingsPage.updateUserRole(userRole, memberEmail);
    }

    @Then("I validate updated role {string} in user list for team member")
    public void userValidateUpdatedUserRoleForTeamMember(String userRole) {
        AdminSettingsPage.validateUpdatedUserRoleForTeamMember(userRole);
    }

    @And("I go to billing portal")
    public void userLaunchBillingPortal() {
        AdminSettingsPage.launchBillingPortal();
    }

    @Then("I validate invoice")
    public void userValidateInvoice() {
        AdminSettingsPage.validateInvoice();
    }

    @When("I check {string} unique guest quota")
    public void userCheckUniqueGuestQuota(String quotaInfo) {
        AdminSettingsPage.checkUniqueGuestQuota(quotaInfo);
    }

    @And("I validate empty guest list page")
    public void userValidateEmptyGuestListPage() {
        AdminSettingsPage.validateEmptyGuestListPage();
    }

    @And("I validate guest list on unique guest page for added {string} {string}")
    public void validateGuestListOnUniqueGuestPageForAddedGuest(String userType, String userEmail) {
        AdminSettingsPage.validateGuestListOnUniqueGuestPage(userType, userEmail);
    }

    @And("I validate view access modal")
    public void iValidateViewAccessModal() {
        AdminSettingsPage.validateViewAccessModal();
    }

    @And("I validate {string} preset {string}")
    public void iValidatedCreateOrEditPresetSuccessToastMessage(String presetAction, String presetName) {
        AdminSettingsPage.validatePresetToastMsg(presetAction, presetName);
    }

    @Then("I validate data rooms value {string} in plan details")
    public void iValidateDataRoomsValueInPlanDetails(String drValueInPlanDetail) {
        AdminSettingsPage.validateDRValueInPlanDetails(drValueInPlanDetail);
    }

    @And("I validate invoice information in plan details for business plan admin")
    public void iValidateInvoiceInformationInPlanDetailsForBusinessPlanAdmin() {
        AdminSettingsPage.validateInvoiceInfoForBusinessPlanAdmin();
    }

    @And("I validate the contact support card for business plan admin user on billing tab")
    public void iValidateTheContactSupportCardForBusinessPlanAdminOnBillingTab() {
        AdminSettingsPage.validatePlanChangeCardForBusinessPlanAdmin();
    }

    @Then("I navigate to {string} tab in {string} page")
    public void iNavigateToTabInPageAndExpand(String tabTypeInAdminSettings, String pageType) {
        HomePage.navigateToPageFromHomePage(pageType);
        AdminSettingsPage.navigateToTabInAdminSettings(tabTypeInAdminSettings);
    }

    @And("I validate {string} plan name in plan details and usage")
    public void iValidatePlanNameInPlanDetailsAndUsage(String planType) {
        AdminSettingsPage.validatePlanTypeInPlanDetails(planType);
    }

    @And("I expand {string} in billing tab")
    @And("I expand {string} in admin tab")
    @And("I expand {string} in branding tab")
    public void iExpandTabInAdminSettings(String sectionTypeInAdminSettings) {
        AdminSettingsPage.clickToExpandSectionInAdminSettings(sectionTypeInAdminSettings);
    }

    @Then("I validate cancel subscription section in billing tab")
    public void iValidateCancelSubscriptionSectionInBillingTab() {
        AdminSettingsPage.validateCancelSubscriptionSection();
    }

    @And("I navigated to hibernation plan waitlist page and select {string} option")
    public void iNavigatedToHibernationPlanWaitlistPageAndSelectOption(String userInterestOptionType) {
        AdminSettingsPage.validateWaitlistPlanPageAndSelectOption(userInterestOptionType);
    }

    @And("I select {string} on plan hibernation user interest page")
    public void iSelectOnPlanHibernationUserInterestPage(String btnProceedForCancellationType) {
        AdminSettingsPage.selectCancellationButton(btnProceedForCancellationType);
    }

    @Then("I select {string} option from disband team modal for user {string}")
    @Then("I validated {string} option from disband team modal for user {string}")
    public void iSelectOptionFromDisbandTeamModal(String disbandOption, String userType) {
        AdminSettingsPage.selectDisbandTeamOption(disbandOption, userType);
    }

    @And("I dismiss the disband team modal")
    public void iDismissTheDisbandTeamModal() {
        AdminSettingsPage.dismissDisbandTeamModal();
    }

    @Then("I update the terms of access")
    public void iUpdateTheTermsOfAccess() {
        AdminSettingsPage.updateAndSaveTOA();
    }

    @And("I reorder custom preset")
    public void iReorderCustomPreset() {
        AdminSettingsPage.reorderCustomPreset();
    }

    @And("I validate remove member {string} modal")
    public void iValidateRemoveMemberModal(String memberType) {
        AdminSettingsPage.validateRemoveMemberModal(memberType);
    }

    @And("I select {string} for the {string} in remove member modal and remove member")
    public void iSelectInRemoveMemberModalAndRemoveMember(String deleteOptions, String memberType) {
        AdminSettingsPage.selectRemoveMemberOptions(Arrays.asList(deleteOptions.split(",")), memberType);
    }

    @Then("System should show no dr guest quota left paywall")
    public void systemShouldShowNoDRGuestQuotaPaywall() {
        AdminSettingsPage.validateDRGuestQuotaLeftPaywall();
    }

    @And("I navigate to {string} tab from members tab in admin settings")
    public void iNavigateToTabFromMembersTabInAdminSettings(String tabName) {
        AdminSettingsPage.navigateToTabInAdminSettings(tabName);
    }

    @And("I validate DR quota {string}")
    public void iValidateDRQuota(String drQuota) {
        AdminSettingsPage.validateDRQuota(drQuota);
    }

    @Then("I validate Premium feature add ons {string} on billing tab")
    public void iValidatePremiumFeatureAddOnsOnBillingTab(String addonList) {
        AdminSettingsPage.validatePremiumFeatureAddon(Arrays.asList(addonList.split(",")));
    }

    @Then("I remove the new preset after reorder {string}")
    public void iRemoveTheNewPresetAfterReorder(String presetName) {
        AdminSettingsPage.removeNewPresetAfterReorder(presetName);
    }

    @Then("I upload the {string} as the branding logo and validate the update")
    public void iUploadTheAsTheBrandingLogoAndValidateTheUpdate(String fileName) throws IOException {
        AdminSettingsPage.changeBrandingLogo(fileName);
    }

    @Then("I successfully change the {string}")
    public void iSuccessfullyChangeTheColor(String colorForm) {
        AdminSettingsPage.updateColor(colorForm);
    }
}