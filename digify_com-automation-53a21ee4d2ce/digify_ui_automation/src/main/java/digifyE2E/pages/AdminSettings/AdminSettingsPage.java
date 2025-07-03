package digifyE2E.pages.AdminSettings;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ElementState;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.dataRooms.ManageDataRoomPage;
import digifyE2E.pages.documentSecurity.ManageSentFilesPage;
import digifyE2E.pages.documentSecurity.SendFilesPage;
import digifyE2E.pages.landingPage.HomePage;
import digifyE2E.testDataManager.SharedDM;
import digifyE2E.utils.FileUtils;
import digifyE2E.utils.LogUtils;
import digifyE2E.utils.RandomUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static digifyE2E.utils.RandomUtils.convertHexToRgbString;
import static org.testng.Assert.*;


public class AdminSettingsPage extends CommonUIActions {
    public static final String BRAND_COLOR = "#" + RandomUtils.getRandomString(6, true);
    static final String newMemberEmail = "autoMem_" + RandomUtils.getRandomString(3, false) + "@vomoto.com";
    static final String drdPresetPermission = "//app-create-preset-modal//pg-select[@formcontrolname='permissions']";
    static final String drdPresetWatermark = "//app-create-preset-modal//pg-select[@formcontrolname='watermark']";
    static final String drdPresetPrint = "//app-create-preset-modal//pg-select[@formcontrolname='allowPrint']";
    static final String drdPresetTOA = "//app-create-preset-modal//pg-select[@formcontrolname='terms']";
    static final String drdPresetScreenshield = "//app-create-preset-modal//pg-select[@formcontrolname='screenShield']";
    static final String drdPresetExpiry = "//app-create-preset-modal//pg-select[@formcontrolname='expiry']";
    static final String drdPresetAccess = "//app-create-preset-modal//pg-select[@formcontrolname='access']";
    static final String drdPresetExpiryFixDate = "//app-create-preset-modal//pg-select[@formcontrolname='expiryFixDate']";
    static final String toastMember = "//pg-message-container/div";
    static final String btnContinueCancellationInStep = getXpathForContainsText("Continue cancellation", "button");
    static final String btnKeepSubscriptionInStep = getXpathForContainsText("Keep subscription", "button");
    private static final String TEXT_COLOR = "#" + RandomUtils.getRandomString(6, true);
    public static String txtAdminSettingsTitle = getXpathForContainsText("Admin Settings", "h4");
    public static String urlMembersTab = "/#/a/m";
    public static String urlBillingTab = "/#/a/tb";
    public static String urlOrgTermsExpanded = "/a/g?termsExpand=true";
    public static String urlChangeLogoAndColors = "/#/a/b";
    public static String lblBrandColor = "//form[@name='colorForm']/div[1]/label";
    public static String btnLogoChange = "(//button[@class='btn digify-btn-blue'])[1]";
    public static String btnColorChange = "(//button[@class='btn digify-btn-blue'])[2]";
    public static String lblChangeColor = "(//div[contains(@class,'col-sm')]/div/p)[2]";
    public static String lblChangeLogo = "//div[@id='brd_log']/div/div/p";
    public static String txtBrandColorInputBox = "(//div[@class='color-picker'])[1]/input[1]";
    public static String txtTextColorInputBox = "(//div[@class='color-picker'])[2]/input[1]";
    public static String lnkChangeLogo = "//div[@routerlink='/a/b']";
    static String btnPauseSubscription = getXpathForContainsText("Pause subscription", "span");
    static String txtSubCancellationSteps = "//app-cancellation-reason//div[2]/div[1]/p";
    static String txtPresetName = "//app-create-preset-modal/div[2]/div/div[1]/form/div[1]/div[2]/div/input";
    static String btnCreate = getXpathForContainsText("Create", "span");
    static String btnSave = getXpathForContainsText("Save", "span");
    static String btnEditMember = "//app-edit-mem-name/div[2]/div[2]/button[2]";
    static String firstPresetInDocSecurity = "//*[@id='tabpresetlist']/table/tbody/tr[1]/td[1]/p";
    static String txtUserLicense = "//*[@id='mem_new']/div[1]/div";
    private static Map<String, String> userCredentialsAdminSettingsPage = new HashMap<>();
    private static Map<String, String> memberCredentialsAdminSettingsPage = new HashMap<>();

    public static void navigateToTabInAdminSettings(String tabType) {
        switch (tabType.toLowerCase()) {
            case "billing":
                page().waitForLoadState(LoadState.DOMCONTENTLOADED);
                clickElement("//*[@id='adm_bil']/a");
                waitForURLContaining(urlBillingTab);
                break;
            case "members":
                clickElement("//*[@id='adm_mem']/a");
                waitUntilSelectorAppears("//*[@id='mem_new']/div/div");
                assertEquals(getElementText("(//*[@id='mem_new']/div[1]/p)[1]"), "NEW MEMBER");
                break;
            case "doc security":
                page().waitForLoadState(LoadState.LOAD);
                clickElement("//*[@id='adm_doc']/a");
                waitUntilSelectorAppears("//*[@id='fteampreset']/div[1]/div[1]/p[1]");
                assertEquals(getElementText("//*[@id='fteampreset']/div[1]/div[1]/p[1]"), "PRESET");
                break;
            case "admin":
                clickElement("//*[@id='adm_gen']/a");
                waitForURLContaining("/#/a/g");
                waitUntilSelectorAppears("//*[@id='fteam']/div/div[1]/div[1]/div/p[1]");
                String headingText = getElementText("//*[@id='fteam']/div/div[1]/div[1]/div/p[1]");
                assertEquals(headingText.substring(0, headingText.lastIndexOf(":")), "Account Name");
                break;
            case "branding":
                clickElement("//*[@id='adm_brd']/a");
                waitForURLContaining("/#/a/b");
                waitUntilSelectorAppears("(//div[@id='brd_log']/div)[1]");
                assertTrue(isElementVisible(lblChangeColor));
                assertEquals(getElementText(lblChangeLogo),
                        "Change Logo");
                assertEquals(getElementText(lblChangeColor),
                        "Change Color");
                break;
            default:
                fail("tab not found in admin settings");
        }
    }

    public static void clickToExpandSectionInAdminSettings(String billingTabList) {
        switch (billingTabList.toLowerCase()) {
            case "plan details and usage":
                String btnExpandPlanDetail = "//*[@aria-controls='collapsePlan']";
                int maxAttempts = 3;
                int i = 1;
                waitUntilSelectorAppears(
                        getXpathForContainsText("Plan Details & Usage", "p"));
                // Wait for the expand button for plan details to appear in the DOM and be interactable
                page().waitForSelector(btnExpandPlanDetail,
                        new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));

                while (i <= maxAttempts) {
                    boolean isExpanded = Boolean.parseBoolean(getAttributeValue(btnExpandPlanDetail, "aria-expanded"));
                    if (isExpanded) {
                        break;
                    }
                    clickElement(btnExpandPlanDetail);

                    isExpanded = Boolean.parseBoolean(getAttributeValue(btnExpandPlanDetail, "aria-expanded"));
                    if (isExpanded) {
                        break;
                    }
                    page().reload();
                    i++;
                }
                if (!Boolean.parseBoolean(getAttributeValue(btnExpandPlanDetail, "aria-expanded"))) {
                    throw new RuntimeException("Failed to expand the plan details after " + maxAttempts + " attempts.");
                }
                waitUntilSelectorAppears("(//*[@class='info-name'])[1]");
                assertEquals(getElementText("(//*[@class='info-name'])[1]").toLowerCase(), "plan");
                break;
            case "cancel subscription":
                validateCancelSubscriptionSection();
                clickElement("//button[@aria-controls='collapseCancel']");
                waitUntilSelectorAppears("//span[@data-ao-id='csubbtn']/../..");
                assertFalse(isElementVisible(btnPauseSubscription));
                assertEquals(getElementText("//app-team-billing//div[2]/div[4]/div[1]/div[1]/p"),
                        "Cancel Subscription");
                assertEquals(getElementText("//*[@id='collapseCancel']/div/span"),
                        "If you proceed, when your current billing cycle ends on " + "Dec 30, 2025:");
                assertEquals(getElementText("//*[@id='collapseCancel']//div[1]/ul/li[1]"),
                        "All files shared will be disabled");
                assertEquals(getElementText("//*[@id='collapseCancel']//div[1]/ul/li[2]"),
                        "All data rooms will be disabled");
                assertEquals(getElementText("//*[@id='collapseCancel']//div[1]/ul/li[3]"),
                        "All team members will not be able to send files or create data rooms");
                break;
            case "change terms of access":
                assertEquals(getElementText("//*[@id='gen_ter']/div[1]/p"),
                        "Change Terms of Access");
                clickElement("//*[@id='gen_ter']/div[2]/button");
                assertEquals(getElementText("//*[@id='collapseTerms']/p"),
                        "Organization Terms");
                break;
            case "disband team":
                page().waitForLoadState(LoadState.DOMCONTENTLOADED);
                assertTrue(isElementVisible("//*[@id='fteam']/div/div[4]/div[1]/div/div[1]/p"));
                assertEquals(getElementText("//*[@id='fteam']/div/div[4]/div[1]/div/div[1]/p"), "Disband Team");
                clickElement("//button[@aria-controls='collapseDTMem']");
                assertEquals(getElementText("//*[@id='collapseDTMem']/div/p[1]"), "Member accounts, if any, will be converted to free viewer accounts.");
                assertEquals(getElementText("//*[@id='collapseDTMem']/div/p[2]"), "Any team analytics, presets and branding will be permanently erased.");
                break;
            case "change logo":
                page().waitForLoadState(LoadState.DOMCONTENTLOADED);
                assertTrue(isElementVisible("//button[@id='brd_btn_log']"));
                clickElement("//button[@id='brd_btn_log']");
                validateChangeLogoPanel();
                break;
            case "change color":
                page().waitForLoadState(LoadState.DOMCONTENTLOADED);
                assertTrue(isElementVisible("//button[@aria-controls='collapseChangeColor']"));
                clickElement("//button[@aria-controls='collapseChangeColor']");
                validateBrandColorPanel();
                validateTextColorPanel();
                break;
            default:
                fail("Billing tab list does not matched");
        }
    }

    private static void validateBrandColorPanel() {

        String lblPickColor = "(//label[@class='cursor color-blue'])";
        assertTrue(isElementVisible(lblBrandColor));
        assertEquals(getElementText(lblBrandColor), "Brand color");
        assertTrue(isElementVisible("//form[@name='colorForm']/div[1]/label/i"));//info icon
        assertTrue(isElementVisible("(//div[@class='color-picker'])[1]"));
        assertTrue(isElementVisible(txtBrandColorInputBox));
        assertTrue(isElementVisible("(//div[@class='color-picker'])[1]/input[2]")); //color box
        assertTrue(isElementVisible(lblPickColor));
        assertEquals(getElementText(lblPickColor), "Pick color");
    }

    private static void validateTextColorPanel() {
        String lblTextColor = "//form[@name='colorForm']/div[2]/label";
        String lblPickColor = "//input[@id='text-color']/following-sibling::label";
        assertTrue(isElementVisible(lblTextColor));
        assertEquals(getElementText(lblTextColor), "Text color");
        assertTrue(isElementVisible("(//div[@class='color-picker'])[2]"));
        assertTrue(isElementVisible("(//div[@class='color-picker'])[2]/input[1]"));//color input box
        assertTrue(isElementVisible("(//div[@class='color-picker'])[2]/input[2]")); //color box
        assertTrue(isElementVisible(lblPickColor));
        assertEquals(getElementText(lblPickColor), "Pick color");
    }

    private static void validateChangeLogoPanel() {
        assertEquals(getElementText("//div[@id='collapseChangeLogo']//div/p"),
                "A PNG image file with a transparent background and dimensions of 400 x 144 is recommended. Learn more");
        assertTrue(isElementVisible(btnLogoChange));
        assertEquals(getElementText(btnLogoChange), "Change");
        assertTrue(isElementVisible("//img[@class='logo-block']"));
    }


    public static void validateCancelSubscriptionSection() {
        String lblCancelSubscription = "//div[@id='collapseCancel']/../div/p";
        waitForURLContaining(urlBillingTab);
        waitUntilSelectorAppears(lblCancelSubscription);
        assertEquals(getElementText(lblCancelSubscription), "Cancel Subscription");
    }

    public static void validatePlanTypeInPlanDetails(String planType) {
        String txtPlanTypeName = "(//*[@class='value'])[1]";
        waitUntilSelectorAppears(txtPlanTypeName);
        String txtActualPlanTypeName = getElementText(txtPlanTypeName).toLowerCase();
        switch (planType.toLowerCase()) {
            case "pro":
                assertEquals(txtActualPlanTypeName, "pro");
                break;
            case "team":
                assertEquals(txtActualPlanTypeName, "team");
                break;
            case "business":
                assertEquals(txtActualPlanTypeName, "business");
                break;
            default:
                fail(planType + ":Plan type name does not matched");
        }
    }

    public static void navigateToPricingPageClickingOnModifyBtn() {
        clickElement(getXpathForContainsText("Modify plan", "button"));
    }


    public static void clickBtnInAdminSettingPage(String buttonType) {
        switch (buttonType.toLowerCase()) {
            case "cancel subscription":
                clickElement("//button[contains(@class,'btn digify-btn-warning')]");
                break;
            case "continue cancellation":
                clickElement(btnContinueCancellationInStep);
                break;
            case "feedback continue cancellation":
                dispatchClickElement(btnCommonBtnForContinue);
                break;
            case "got it", "ok":
                clickElement(btnOk);
                break;
            case "remove":
                page().waitForLoadState(LoadState.DOMCONTENTLOADED);
                clickElement("(//button[@class='btn digify-btn-blue btn-cons'])[2]");
                break;
            case "invite":
                clickElement(getXpathForTextEquals("Invite ", "span"));
                break;
            case "keep subscription":
                clickElement(btnKeepSubscriptionInStep);
                break;
            case "keep subscription on step 3":
                clickElement(btnCommonCancel);
                break;
            case "continue to cancel subscription":
                clickElement(getXpathForContainsText("Continue to cancel subscription", "button"));
                break;
            case "restore subscription":
                clickElement("//app-delete/div[2]/div[2]/button[2]/btn-spinner/span[1]");
                validateToastMsg(toastMember, "Subscription restored.Close");
                break;
            case "disband":
                clickElement("//*[@id='collapseDTMem']/div/div/button/btn-spinner/span[1]");
                break;
            case "disband and delete team":
                clickElement("//app-delete-mem-modal/div[2]/div[4]/button[2]");
                break;
            default:
                fail("Invalid option type: " + buttonType);
        }
    }

    public static void userSelectPlanCancellationReason(String planCancelReason) {
        waitUntilSelectorAppears(txtSubCancellationSteps);
        assertEquals(getElementText(txtSubCancellationSteps).toLowerCase(), "step 1/4");
        assertEquals(getElementText("//*[@id='ngb-nav-6-panel']//h3"), "Cancel subscription");
        switch (planCancelReason.toLowerCase()) {
            case "fundraising completed":
                clickElement(getXpathForContainsText("Fundraising completed", "div"));
                break;
            case "project ended":
                clickElement(getXpathForContainsText("Project ended", "div"));
                break;
            case "project paused":
                clickElement(getXpathForContainsText("Project paused", "div"));
                break;
            case "not using enough":
                clickElement(getXpathForContainsText("Not using enough", "div"));
                break;
            case "product features or issues":
                clickElement(getXpathForContainsText("Product features or issues", "div"));
                break;
            case "too expensive":
                clickElement(getXpathForContainsText("Too expensive", "div"));
                break;
            case "others":
                clickElement(getXpathForContainsText("Others", "div"));
                inputIntoTextField("//div/div[2]/div[3]/input", "automation test other reason");
                break;
            default:
                fail("Invalid cancellation reason: " + planCancelReason);
        }
    }

    public static void userValidateDocumentListAsPerPlanCancelReason(String cancellationReason) {
        waitUntilSelectorAppears(txtSubCancellationSteps);
        assertEquals(getElementText(txtSubCancellationSteps).toLowerCase(), "step 2/4");
        List<ElementHandle> fileType = page().querySelectorAll("div.sub-customer-text");
        switch (cancellationReason.toLowerCase()) {
            case "fundraising completed doc":
                assertEquals(getElementText("//app-reason-fundraising/div[1]/div/h3"),
                        "Protect files that matter");
                assertEquals(fileType.get(0).textContent().trim(), "Investor updates");
                assertEquals(fileType.get(1).textContent().trim(), "HR files");
                assertEquals(fileType.get(2).textContent().trim(), "Sales & marketing data");
                break;
            case "project ended doc":
                assertEquals(getElementText("//app-reason-project/div[1]/div/h3"),
                        "Sources of data leaks are often internal");
                assertEquals(fileType.get(0).textContent().trim(), "Investor updates");
                assertEquals(fileType.get(1).textContent().trim(), "HR files");
                assertEquals(fileType.get(2).textContent().trim(), "Sales & marketing data");
                break;
            case "project paused doc":
                assertEquals(getElementText("//app-reason-project-paused/div[1]/div/h3"),
                        "You can't put a pause on document security");
                assertEquals(fileType.get(0).textContent().trim(), "Sensitive communications");
                assertEquals(fileType.get(1).textContent().trim(), "HR files");
                assertEquals(fileType.get(2).textContent().trim(), "Intellectual property");
                break;
            case "not using enough doc":
                assertEquals(getElementText("//app-reason-lack-of-use/div[1]/div/h3"),
                        "Don't put your sensitive documents at risk");
                assertEquals(fileType.get(0).textContent().trim(), "Advanced data security");
                assertEquals(fileType.get(1).textContent().trim(), "Control access to your files");
                assertEquals(fileType.get(2).textContent().trim(), "Monitor file activity");
                break;
            case "product features or issues doc":
                assertEquals(getElementText("//app-reason-product/div[1]/div[1]/h3"),
                        "Give us a chance to set things right");
                assertEquals(getElementText("//app-reason-product/div[1]/div[1]/div/div/div[1]"),
                        "Let's chat! Our customer success is here to help.");
                break;
            case "too expensive doc":
                assertEquals(getElementText("//app-reason-expensive/div[1]/div[1]/h3"),
                        "Give us a chance to set things right");
                assertEquals(getElementText("//app-reason-expensive/div[1]/div[1]/div/div/div[1]"),
                        "Let's chat! Our customer success is here to help.");
                break;
            case "others doc":
                assertEquals(getElementText("//app-reason-others/div[1]/div[1]/h3"),
                        "Before you cancel...");
                assertEquals(getElementText("//app-reason-others/div[1]/div[1]/div/div/div[1]"),
                        "Let's chat! Our customer success is here to help.");
                break;
            default:
                fail("Invalid cancellation reason on document list page: " + cancellationReason);
        }
    }

    private static void getProjectPauseDuration(String projectPauseDuration) {
        clickElement("//pg-select/div/span");
        List<ElementHandle> durationList = page().querySelectorAll(".pg-select-dropdown-menu-item");
        for (ElementHandle durationToSelect : durationList) {
            if (durationToSelect.textContent().trim().equals(projectPauseDuration)) {
                durationToSelect.click();
                break;
            }
        }
    }

    public static void userValidateFeedbackFormAsPerPlanCancelReason(String feedbackType, String projectPauseDuration) {
        waitUntilSelectorAppears(txtSubCancellationSteps);
        assertEquals(getElementText(txtSubCancellationSteps).toLowerCase(), "step 3/4");
        List<ElementHandle> feedbackTypeList = page().querySelectorAll("div.form-title");
        String txtAppReason = "//div[2]/div/h3";
        switch (feedbackType.toLowerCase()) {
            case "fundraising completed feedback":
                assertEquals(getElementText(txtAppReason), "Can you provide us some feedback?");
                assertEquals(feedbackTypeList.get(0).textContent().trim(), "What you liked about Digify");
                assertEquals(feedbackTypeList.get(1).textContent().trim(), "How we can improve");
                assertEquals(feedbackTypeList.get(2).textContent().trim(), "Any other comments");
                assertEquals(feedbackTypeList.get(3).textContent().trim(), "Amount raised");
                assertEquals(feedbackTypeList.get(4).textContent().trim(), "PR article link");
                break;
            case "project ended feedback", "product features or issues feedback", "too expensive feedback",
                 "others feedback", "not using enough feedback":
                assertEquals(getElementText(txtAppReason), "Can you provide us some feedback?");
                assertEquals(feedbackTypeList.get(0).textContent().trim(), "What you liked about Digify");
                assertEquals(feedbackTypeList.get(1).textContent().trim(), "How we can improve");
                assertEquals(feedbackTypeList.get(2).textContent().trim(), "Any other comments");
                break;
            case "project paused feedback":
                assertEquals(getElementText(txtAppReason), "Can you provide us some feedback?");
                assertEquals(feedbackTypeList.get(0).textContent().trim(), "What you liked about Digify");
                assertEquals(feedbackTypeList.get(1).textContent().trim(), "How we can improve");
                assertEquals(feedbackTypeList.get(2).textContent().trim(), "Any other comments");
                assertEquals(feedbackTypeList.get(3).textContent().trim(), "When do you plan to resubscribe?");
                getProjectPauseDuration(projectPauseDuration);
                break;
            default:
                fail("Invalid cancellation reason: " + feedbackType);
        }
    }

    public static void confirmCancellation() {
        page().waitForLoadState(LoadState.LOAD);
        assertEquals(getElementText("//app-cancellation-confirm/div/h3"), "Confirm your cancellation");
        assertEquals(getElementText(txtSubCancellationSteps).toLowerCase(), "step 4/4");
        boolean isAppCancellationConfirmModal = isElementVisible("//app-cancellation-confirm/div/div[1]/div[1]/b");
        if (!(isAppCancellationConfirmModal)) {
            waitUntilSelectorAppears("//app-cancellation-confirm/div/div[1]/div[1]/b");
        }
        assertEquals(getElementText("//app-cancellation-confirm/div/div[1]/div[1]"),
                "If you choose to proceed, your subscription will be canceled on Dec 30, 2025.");
        assertEquals(getElementText("//app-cancellation-confirm/div/div[1]/div[2]"),
                "Thereafter, all your files and data room will be disabled. All team members will not be able to send files or create data rooms. We'll retain your files and data rooms for 24 months after this date, in case you decide to resubscribe to our service.");
        dispatchClickElement("//*[@id='confirm_cancel']");
    }

    public static void validateCancellationSchedulePage() {
        page().waitForLoadState(LoadState.LOAD);
        assertEquals(getElementText("//div[@class='schedual-box']/div[2]"),
                "Subscription scheduled for cancellation");
        assertEquals(getElementText("//div[@class='schedual-box']/div[3]"),
                "Your subscription will be canceled on Dec 30, 2025. If you change your mind before that, you can always undo your cancellation.");
    }

    public static void validateRemoveCancellationOnBillingTab() {
        assertEquals(getElementText("//app-team-billing/div/div/div[3]/div[1]/div/p"), "Remove Cancellation");
        assertEquals(getElementText("//app-team-billing//div[3]/div[1]/div/div"),
                "Your subscription has been scheduled for cancellation on Dec 30, 2025.");
    }

    public static void validateRemoveCancellationModal() {
        assertEquals(getElementText("//app-delete/div[1]/h4"), "Remove cancellation");
        assertEquals(getElementText("//app-delete/div[2]/div[1]"),
                "Your subscription will be restored. Your next billing is on Dec 30, 2025.");
    }

    public static void selectRoleTypeForMember(String memberType, String email) {
        inputIntoTextField(ManageSentFilesPage.txtBulkRecipient, email);
        keyPress("Enter");
        clickElement("//*[@id='mem_new']//form/pg-select/div/span");
        switch (memberType) {
            case "Restricted User":
                clickElement("//div/div/ul/li[1]/div/p[1]");
                break;
            case "User  Recommended":
                clickElement("//div/div/ul/li[2]/div/p[1]");
                break;
            case "Admin":
                clickElement("//div/div/ul/li[3]/div/p[1]");
                break;
            default:
                fail("Invalid member type is passed: " + memberType);
        }
    }

    public static void validateNewMemberWarningModal() {
        validateToastMsg(toastMember, "1 user invited.Close");
        waitUntilSelectorAppears("//app-new-user-no-existing-dr-access-modal/div[1]/h4");
        assertEquals(getElementText("//app-new-user-no-existing-dr-access-modal/div[1]/h4"),
                "New admins/users will not get access to existing data rooms");
        assertEquals(getElementText("//app-new-user-no-existing-dr-access-modal/div[2]/div[1]").replace("\u00A0", " "),
                "The data room owner must invite the new admins/users to the data room. Learn more");
        assertEquals(getElementText("//app-new-user-no-existing-dr-access-modal//div[2]//label"),
                "Don't show again");
        clickElement(btnOk);
    }

    public static void validatedAddedMemberDetails(String roleType) {
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears(txtUserLicense);
        assertEquals(getElementText(txtUserLicense),
                "You've used 2/10 available user licenses.");

        switch (roleType.toLowerCase()) {
            case "restricted user":
                assertEquals(getElementText("//*[@id='tabMembers']//datatable-row-wrapper[1]/datatable-body-row//datatable-body-cell[2]//p"),
                        "Restricted User");
                break;
            case "user":
                assertEquals(getElementText("//*[@id='tabMembers']//datatable-row-wrapper[1]/datatable-body-row//datatable-body-cell[2]//p"),
                        "User");
                break;
            case "admin":
                assertEquals(getElementText("//*[@id='tabMembers']//datatable-row-wrapper[1]/datatable-body-row//datatable-body-cell[2]//p"),
                        "Admin");
                break;
            default:
                LogUtils.infoLog("member type not matched");
                assertEquals(getElementText("//*[@id='tabMembers']//datatable-row-wrapper[1]/datatable-body-row//datatable-body-cell[3]//p"),
                        "-");
                assertEquals(getElementText("//*[@id='tabMembers']//datatable-row-wrapper[1]/datatable-body-row//datatable-body-cell[4]//div[1]/p"),
                        "Invite Sent");
                waitUntilSelectorAppears("//datatable-footer/div//b");
                assertEquals(getElementText("//datatable-footer//div/div/div"),
                        "Showing  1 to 2  of 2 members");
        }
    }

    public static void performActionOnNewMemInviteSent(String inviteType) {
        clickElement("//a[@class='dropdown-toggle option-dropdown-toggle']/i");
        if (inviteType.equals("Cancel")) {
            clickElement(getXpathForContainsText("Cancel Invite", "a"));
        } else if (inviteType.equals("Resend")) {
            clickElement(getXpathForContainsText("Resend Invite", "a"));
        } else {
            fail("Invalid option for inviting member: " + inviteType);
        }
    }

    public static void validateCancelInviteModalAndConfirmCancel() {
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears("//app-cancel-invite-modal/div[1]/h4");
        assertEquals(getElementText("//app-cancel-invite-modal/div[1]/h4"), "Cancel invitation");
        assertEquals(getElementText("//app-cancel-invite-modal/div[2]/div[1]/div"),
                "This will cancel " + newMemberEmail.toLowerCase() + "'s invitation to join team's team. Do you want to continue?");
        clickElement(getXpathForContainsText("Yes", "button"));
        validateToastMsg(toastMember, "Invitation canceled.Close");
        retryUntilSelectorAppearsAndThenAssert(7, txtUserLicense,
                "You've used  1 of 10  available user licenses.");
        retryUntilSelectorAppearsAndThenAssert(7, "//datatable-footer//div/div/div",
                "Showing  1 to 1  of 1 member");
    }

    public static void addMember(String memberType, String userType) {
        switch (userType.toLowerCase()) {
            case "new member":
                selectRoleTypeForMember(memberType, newMemberEmail);
                break;
            case "existing member":
                userCredentialsAdminSettingsPage = FileUtils.getUserCredentialsFromJsonFile("teamPlanAdmin");
                selectRoleTypeForMember(memberType, Objects.requireNonNull(userCredentialsAdminSettingsPage).get("username"));
                break;
            default:
                fail("Invalid option for inviting member: " + userType);
        }
    }

    public static void validateCannotInviteMemberModal() {
        waitUntilSelectorAppears("//app-warning-modal/div/div[1]/h4");
        assertEquals(getElementText("//app-warning-modal/div/div[1]/h4"),
                userCredentialsAdminSettingsPage.get("username") + " cannot be invited");
        assertEquals(getElementText("//app-warning-modal//div[2]/div[1]/div"),
                "The user is a member of another team.");
        clickElement("//app-warning-modal//div[2]/div[2]/button[2]");
    }

    private static void validateUserQuotaFullModal() {
        waitUntilSelectorAppears("//app-warning-modal/div/div[1]/h4");
        assertEquals(getElementText("//app-warning-modal/div/div[1]/h4"),
                "Not enough user quota");
        assertEquals(getElementText("//app-warning-modal//div[2]/div[1]/div"),
                "You are trying to invite 1 member to your team, but you have 0 quota available");
        assertEquals(getElementText("//app-warning-modal/div/div[2]/div[2]/a[3]"),
                "Add quota");
        clickElement("//app-warning-modal/div/div[1]/button/span");
    }

    public static void validateModalOnAdminSettings(String modalType) {
        switch (modalType.toLowerCase()) {
            case "cannot invite member":
                validateCannotInviteMemberModal();
                break;
            case "cancel invite":
                validateCancelInviteModalAndConfirmCancel();
                break;
            case "user quota full":
                validateUserQuotaFullModal();
                assertEquals(getElementText("//*[@id='mem_new']/div[1]/p[2]"),
                        "You've reached your quota. Upgrade your plan to invite more users.");
                break;
            case "new member warning":
                validateNewMemberWarningModal();
                break;
            case "remove cancellation":
                validateRemoveCancellationModal();
                break;
            default:
                fail("Invalid option for modal validation on admin settings page: " + modalType);
        }
    }

    public static void validateAvailableMemberLicenceAsPerUserType(String userType) {
        switch (userType) {
            case "adminWith10MemberQuota":
                retryUntilSelectorAppearsAndThenAssert(5,
                        txtUserLicense, "You’ve used  1 of 10  available user licenses.");
                break;
            case "adminWithMemberQuotaFull":
                retryUntilSelectorAppearsAndThenAssert(5,
                        txtUserLicense, "You’ve used  1 of 1  available user licenses.");
                break;
        }
    }

    public static void createPreset(String presetName) {
        if (isElementVisible("//*[@id='tabpresetlist']/table/tbody/tr[2]/td[2]/div/button[4]")) {
            clickElement("//*[@id='tabpresetlist']/table/tbody/tr[2]/td[2]/div/button[4]");
        }
        if (isElementVisible(txtCommonToastMsg)) {
            page().waitForSelector(txtCommonToastMsg).waitForElementState(ElementState.HIDDEN);
        }
        page().waitForLoadState(LoadState.LOAD);
        clickElement(getXpathForContainsText("Create Preset", "button"));
        waitUntilSelectorAppears("//app-create-preset-modal/div[1]/h4");
        assertEquals(getElementText("//app-create-preset-modal/div[1]/h4"), "Create Preset");
        clearValue(txtPresetName);
        inputIntoTextField(txtPresetName, presetName);
    }

    public static void validateCreatedPreset(String presetName) {
        page().reload();
        waitForSeconds(1);
        page().waitForLoadState(LoadState.DOMCONTENTLOADED);
        waitUntilSelectorAppears("//div[@id='tabpresetlist']");
        waitUntilSelectorAppears(firstPresetInDocSecurity);
        assertEquals(getElementText(firstPresetInDocSecurity), presetName);
    }

    public static void removePreset(String presetName) {
        assertEquals(getElementText(firstPresetInDocSecurity), presetName);
        clickElement("//*[@id='tabpresetlist']/table/tbody/tr[1]/td[2]/div/button[4]");
        page().waitForLoadState(LoadState.LOAD);
        validateToastMsg(txtCommonToastMsg, "Preset deleted.");
    }

    public static void removeNewPresetAfterReorder(String presetName) {
        assertEquals(getElementText("//*[@id='tabpresetlist']/table/tbody/tr[2]/td[1]/p"), presetName);
        clickElement("//*[@id='tabpresetlist']/table/tbody/tr[2]/td[2]/div/button[4]");
        page().waitForLoadState(LoadState.LOAD);
        validateToastMsg(txtCommonToastMsg, "Preset deleted.");
    }

    public static void createPresetWithDifferentFeatures(List<String> presetFeatures) {
        presetFeatures.forEach(feature -> {
            switch (feature.trim()) {
                case "Allow downloading", "Don't allow downloading":
                    selectValueFromDropdownList(drdPresetPermission,
                            SendFilesPage.ddElementList, feature);
                    break;
                case "Allow printing":
                    waitUntilSelectorAppears(drdPresetPrint);
                    selectValueFromDropdownList(drdPresetPrint,
                            SendFilesPage.ddElementList, feature);
                    break;
                case "Watermark on":
                    selectValueFromDropdownList(drdPresetWatermark,
                            SendFilesPage.ddElementList, "Turn on");
                    break;
                case "TOA on":
                    selectValueFromDropdownList(drdPresetTOA,
                            SendFilesPage.ddElementList, "Turn on");
                    break;
                case "Screenshield on":
                    waitForSeconds(1);
                    selectValueFromDropdownList(drdPresetScreenshield,
                            SendFilesPage.ddElementList, "Turn on");
                    page().waitForLoadState(LoadState.LOAD);
                    waitUntilSelectorAppears(
                            "(//pg-select[@formcontrolname='screenShield']/div/div/div[2])[text()=' Turn on ']");
                    break;
                case "Expire after first access":
                    selectValueFromDropdownList(drdPresetExpiry, SendFilesPage.ddElementList, feature);
                    break;
                case "Only people from email domains I specify":
                    CommonUIActions.selectValueFromDropdownList(drdPresetAccess, SendFilesPage.ddElementList, feature);
                    CommonUIActions.selectValueFromDropdownList("//pg-select[@formcontrolname='forceVerificationDomain']",
                            SendFilesPage.ddElementList, "Turn on");
                    break;
                case "Only people I specify":
                    CommonUIActions.selectValueFromDropdownList(drdPresetAccess, SendFilesPage.ddElementList, feature);
                    break;
                case "Expire on fixed date and time":
                    CommonUIActions.selectValueFromDropdownList(drdPresetExpiry, SendFilesPage.ddElementList, feature);
                    CommonUIActions.selectValueFromDropdownList(drdPresetExpiryFixDate, SendFilesPage.ddElementList,
                            "60 days after sharing");
                    break;
                case "Anyone with the link (no email verification)":
                    CommonUIActions.selectValueFromDropdownList(drdPresetAccess, SendFilesPage.ddElementList, feature);
                    waitForSeconds(1);//Getting element not attached to dom exception
                    break;
                case "turn on-->require add. info for public access": //anyone with the link(no email verification)
                    waitUntilSelectorAppears("//pg-select[@formcontrolname='requireAdditionalInfoPublic']");
                    CommonUIActions.selectValueFromDropdownList("//pg-select[@formcontrolname='requireAdditionalInfoPublic']",
                            SendFilesPage.ddElementList, "Turn on");
                    break;
                case "turn on-->require add. info for restricted access": //only people I specify
                    waitUntilSelectorAppears("//pg-select[@formcontrolname='requireAdditionalInfoRestricted']");
                    CommonUIActions.selectValueFromDropdownList("//pg-select[@formcontrolname='requireAdditionalInfoRestricted']",
                            SendFilesPage.ddElementList, "Turn on");
                    break;
                case "turn on-->require add. info for email verification access": //anyone with the link(email verification)
                    waitUntilSelectorAppears("//pg-select[@formcontrolname='requireAdditionalInfoRestricted']");
                    CommonUIActions.selectValueFromDropdownList("//pg-select[@formcontrolname='requireAdditionalInfoRestricted']",
                            SendFilesPage.ddElementList, "Turn on");
                    break;
                case "turn on-->require add. info for domain access": //only domain I specify
                    waitUntilSelectorAppears("//pg-select[@formcontrolname='requireAdditionalInfoDomain']");
                    CommonUIActions.selectValueFromDropdownList("//pg-select[@formcontrolname='requireAdditionalInfoDomain']",
                            SendFilesPage.ddElementList, "Turn on");
                    break;
                case "turn off-->require add. info for public access": //anyone with the link(no email verification)
                    waitUntilSelectorAppears("//pg-select[@formcontrolname='requireAdditionalInfoPublic']");
                    waitForSeconds(1);
                    CommonUIActions.selectValueFromDropdownList("//pg-select[@formcontrolname='requireAdditionalInfoPublic']",
                            SendFilesPage.ddElementList, "Turn off");
                    break;
                case "turn off-->require add. info for restricted access": //only people I specify
                    waitUntilSelectorAppears("//pg-select[@formcontrolname='requireAdditionalInfoRestricted']");
                    CommonUIActions.selectValueFromDropdownList("//pg-select[@formcontrolname='requireAdditionalInfoRestricted']",
                            SendFilesPage.ddElementList, "Turn off");
                    break;
                case "turn off-->require add. info for email verification access": //anyone with the link(email verification)
                    waitUntilSelectorAppears("//pg-select[@formcontrolname='requireAdditionalInfoRestricted']");
                    CommonUIActions.selectValueFromDropdownList("//pg-select[@formcontrolname='requireAdditionalInfoRestricted']",
                            SendFilesPage.ddElementList, "Turn off");
                    break;
                case "turn off-->require add. info for domain access": //only domain I specify
                    waitUntilSelectorAppears("//pg-select[@formcontrolname='requireAdditionalInfoDomain']");
                    CommonUIActions.selectValueFromDropdownList("//pg-select[@formcontrolname='requireAdditionalInfoDomain']",
                            SendFilesPage.ddElementList, "Turn off");
                    break;
                case "turn on-->require passcode":
                    waitForSeconds(1);
                    waitUntilSelectorAppears("//pg-select[@formcontrolname='simplePassword']");
                    CommonUIActions.selectValueFromDropdownList("//pg-select[@formcontrolname='simplePassword']",
                            SendFilesPage.ddElementList, "Turn on");
                    break;
                default:
                    fail("Feature not found in preset: " + feature);
            }
        });
    }

    public static void editPreset(String presetName, Boolean updatingPresetName, String newPresetName) {
        page().reload();
        waitForSeconds(1);
        page().waitForLoadState(LoadState.DOMCONTENTLOADED);
        if (!updatingPresetName) {
            assertEquals(getElementText("//*[@id='tabpresetlist']/table/tbody/tr[1]/td[1]/p"), presetName);
            clickElement("//*[@id='tabpresetlist']/table/tbody/tr[1]/td[2]/div/button[3]");
        } else {
            clickElement("//*[@id='tabpresetlist']/table/tbody/tr[1]/td[2]/div/button[3]");
            clearValue(txtPresetName);
            inputIntoTextField(txtPresetName, newPresetName);
            page().waitForLoadState(LoadState.LOAD);
        }
    }

    public static void EnableOrDisableCustomPreset(String presetState) {
        switch (presetState) {
            case "Enable", "Disable":
                assertEquals(getElementText("//*[@id='tabpresetlist']/table/tbody/tr[2]/td[1]/p"), "Custom");
                break;
            case "Enable after reorder", "Disable after reorder":
                assertEquals(getElementText("//*[@id='tabpresetlist']/table/tbody/tr[1]/td[1]/p"), "Custom");
                break;
            default:
                fail("Preset state change action not found: " + presetState);
        }
        clickElement("//*[@id='tabpresetlist']//pg-switch");
        waitForSelectorStateChange(CommonUIActions.txtCommonToastMsg, ElementState.HIDDEN);
    }

    public static void removeExistingPreset(String presetName) {
        assertEquals(getElementText(firstPresetInDocSecurity), presetName);
        clickElement("//*[@id='tabpresetlist']/table/tbody/tr[1]/td[2]/div/button[4]");
        validateToastMsg(CommonUIActions.txtCommonToastMsg, "Preset deleted.");
    }

    public static void validateCustomTermSettings() {
        assertEquals(getElementText("//*[@id='collapseTerms']/div/div[4]/p[1]"),
                "Custom Terms (Data Rooms Only)");
        assertEquals(getElementText("//*[@id='collapseTerms']/div/div[4]/p[2]"),
                "Allow team members to customize terms for data rooms they create.");
        assertEquals(getElementText("//*[@id='collapseTerms']//div[4]//btn-spinner/span[1]"),
                "Disable custom terms");
    }

    public static void updateCustomTermState(String termState) {
        switch (termState.toLowerCase()) {
            case "disable":
                clickElement("//*[@id='collapseTerms']//div[4]//button");
                assertEquals(getElementText("//app-cus-toa-disable-modal/div[1]/h4"),
                        "Disable custom terms");
                assertEquals(getElementText("//app-cus-toa-disable-modal/div[2]/div[1]/span"),
                        "If you proceed, only organization terms can be used for all new files/data rooms in the organization. Data rooms with existing custom terms will not be affected, unless they are turned off individually.");
                clickElement("//app-cus-toa-disable-modal/div[2]/div[2]/button[2]");
                validateToastMsg(CommonUIActions.txtCommonToastMsg, "Custom terms disabled.");
                break;
            case "enable":
                clickElement("//*[@id='collapseTerms']//div[4]//button");
                validateToastMsg(CommonUIActions.txtCommonToastMsg, "Custom terms enabled.");
                break;
            default:
                fail("Invalid option for custom preset: " + termState);
        }
    }

    public static void validateOrganizationTerm() {
        assertEquals(getElementText("//*[@id='collapseTerms']/p"), "Organization Terms");
        assertEquals(getElementText("//*[@id='collapseTerms']/div/div[1]/p"),
                "These terms will apply to all files and data rooms in the organization, unless custom terms are selected.");
    }

    public static void modifyOrganizationTerm() {
        inputIntoTextField("//*[@id='TCForm']/div/textarea", "Newly added content in organization term");
        clickElement("//*[@id='collapseTerms']/div/div[3]/button");
        assertEquals(getElementText("//app-toa-confirm-modal/div[1]/h4"), "Please confirm changes");
        assertEquals(getElementText("//app-toa-confirm-modal//div[2]/div[1]/span"),
                "These updated terms will apply to new and existing files/data rooms using organization terms. Recipients/guests must agree to the Terms of Access again, even if they have already done so previously.");
        assertEquals(getElementText("//app-toa-confirm-modal//div[2]/div[2]/span"),
                "Data rooms with custom terms will not be affected.");
        dispatchClickElement("//app-toa-confirm-modal/div[2]/div[3]/button[2]");
    }

    public static void updateTeamMember(String action, String memberName) {
        memberCredentialsAdminSettingsPage = FileUtils.getUserCredentialsFromJsonFile(memberName);
        String member = memberCredentialsAdminSettingsPage.get("username");
        waitForURLContaining("/#/a/m");
        waitUntilSelectorAppears("//*[@id='" + member + "']");
        clickElement("//*[@id='" + member + "']");

        switch (action) {
            case "manage member":
                clickElement(getXpathForContainsText("Manage Member", "a"));
                break;
            case "remove member":
                clickElement(getXpathForContainsText("Remove Member", "a"));
                break;
            case "restore account":
                clickElement(getXpathForContainsText("Restore Account", "a"));
                assertEquals(getElementText("//app-restore-account//h4"), "Restore account");
                clickElement("//div[@class='modal-body']//button[2]");
                break;
            default:
                fail("No action matched with input" + action);
        }
    }

    public static void updateUserRole(String userRole, String newMemberEmail) {
        memberCredentialsAdminSettingsPage = FileUtils.getUserCredentialsFromJsonFile(newMemberEmail);
        assertEquals(getElementText("//app-edit-mem-name/div[1]/h4"),
                "Manage Member");
        assertEquals(getElementText("//app-edit-mem-name//form/div[3]/div[1]"),
                "Role");
        waitUntilSelectorAppears("//app-edit-mem-name//pg-select[@formcontrolname='permission']");
        String ddElementListMemberRole = ".cdk-overlay-pane>div>div>ul>li>div>p";
        switch (userRole) {
            case "Restricted User":
                CommonUIActions.selectValueFromDropdownList("//app-edit-mem-name//pg-select[@formcontrolname='permission']",
                        ddElementListMemberRole, userRole);
                clickElement(btnEditMember);
                assertEquals(getElementText("//app-delete-mem-modal/div[1]/h4"), "Downgrade member");
                assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[1]"),
                        "default member ( " + memberCredentialsAdminSettingsPage.
                                get("username") + " ) will be downgraded to a Restricted User. They won't be able to send new files or create new data rooms.");
                clickElement("//app-delete-mem-modal/div[2]/div[2]/button[2]");
                break;
            case "User  Recommended", "Admin":
                CommonUIActions.selectValueFromDropdownList("//app-edit-mem-name//pg-select[@formcontrolname='permission']",
                        ddElementListMemberRole, userRole);
                clickElement(btnEditMember);
                break;
            default:
                fail("Invalid option for selecting user role: " + userRole);
        }
        validateToastMsg(CommonUIActions.txtCommonToastMsg, "Member updated.");
    }

    public static void validateUpdatedUserRoleForTeamMember(String userRole) {
        switch (userRole) {
            case "Restricted User", "Admin" ->
                    assertEquals(getElementText("//*[@id='tabMembers']//datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[2]//p"), userRole);
            case "User  Recommended" ->
                    assertEquals(getElementText("//*[@id='tabMembers']//datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[2]//p"), "User");
        }
        assertEquals(getElementText("//*[@id='tabMembers']//datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[3]//p"), "Not enabled");
    }

    public static void launchBillingPortal() {
        assertEquals(getElementText("(//*[contains(@class, 'billing-sub-heading')])[1]"), "Billing Portal");
        assertEquals(getElementText("(//*[contains(@class, 'subtitle-text')])[1]"),
                "Download invoices, change credit card and billing details.");
        clickElement(CommonUIActions.btnSubmit);
    }

    public static void validateInvoice() {
        waitForURLContaining("https://digify-test.chargebeeportal.com/portal/account/");
        assertEquals(getElementText("//*[@id='cb-portal-account-title']/span"), "Account Information");
        assertEquals(getElementText("//*[@id='cb-portal-subscription-title']/span[1]"), "Subscription");
        assertEquals(getElementText("//*[@id='cb-portal-subscription-title']/span[2]"), "Active");
        clickElement("//*[@id='header']/div/div/ul/li/a");
    }

    private static String getGuestQuotaCountOnPlanDetails() {
        return getElementText("//*[@id='collapsePlan']//app-team-change-plan//div[5]/span[2]").
                replace("View guest list", " ").trim();
    }

    public static void checkUniqueGuestQuota(String quotaInfo) {
        assertEquals(getElementText("//*[@id='collapsePlan']//app-team-change-plan//div[5]/span[1]"),
                "Data Room Guests");
        switch (quotaInfo) {
            case "UserWithNoGuest", "UserWith1MemberGuest":
                String guestQuotaCount = AdminSettingsPage.getGuestQuotaCountOnPlanDetails();
                boolean flag = !guestQuotaCount.trim().equals("0/200");
                if (flag) {
                    ManageDataRoomPage.navigateToManageDRPage();
                    ManageDataRoomPage.deleteAllDR();
                    HomePage.navigateToPageFromHomePage("Admin settings");
                    AdminSettingsPage.navigateToTabInAdminSettings("billing");
                    AdminSettingsPage.clickToExpandSectionInAdminSettings("plan details and usage");
                    guestQuotaCount = AdminSettingsPage.getGuestQuotaCountOnPlanDetails();
                }
                assertEquals(guestQuotaCount, "0/200");
                break;
            case "UserWith1Guest", "UserWithSameGuestInManyDR", "UserWithNoAccessGuest", "UserWithUnsharedDRGuest":
                assertEquals(AdminSettingsPage.getGuestQuotaCountOnPlanDetails(), "1/200");
                break;
            case "UserWithFullQuota":
                assertEquals(AdminSettingsPage.getGuestQuotaCountOnPlanDetails(), "200/200");
                break;
            case "scheduleDeletionMem":
                assertEquals(AdminSettingsPage.getGuestQuotaCountOnPlanDetails(), "199/200");
                break;
            default:
                fail("Invalid option for selecting guest with different quota: " + quotaInfo);
        }
    }

    private static void navigateToUniqueGuestListPage() {
        clickElement("//*[@id='collapsePlan']//app-team-change-plan//div[5]/span[2]/a");
        page().waitForLoadState(LoadState.LOAD);
        waitForSelectorStateChange("//app-team-billing/app-team-guest-list/section/div[2]/p", ElementState.STABLE);
        assertEquals(getElementText("//app-team-billing/app-team-guest-list/section/div[1]/p"), "View Guest List");
    }

    public static void validateEmptyGuestListPage() {
        navigateToUniqueGuestListPage();
        assertEquals(getElementText("//app-team-billing/app-team-guest-list/section/div[2]/p"),
                "Guest quota used:0/200. Guests are counted when added to data rooms. Team members are not included in the count.");
        assertEquals(getElementText("//app-team-billing/app-team-guest-list/section/div[2]/div/p"),
                "Your team has not added any guests yet");
        page().waitForLoadState(LoadState.LOAD);
    }

    public static void validateDRValueInPlanDetails(String drValueInPlanDetails) {
        String dataRoomValueText = getElementText("(//*[@class='value'])[4]");
        int index = dataRoomValueText.indexOf('/');
        assertNotEquals(index, -1); // Ensure '/' exists
        String textAfterSlash = dataRoomValueText.substring(index + 1).trim();
        assertEquals(textAfterSlash, drValueInPlanDetails);
    }

    public static void validateGuestListOnUniqueGuestPage(String userType, String userEmail) {
        Map<String, String> billingCredentialsAdminSettingsPage = FileUtils.getUserCredentialsFromJsonFile(userEmail);
        switch (userType) {
            case "Guest", "Guests":
                navigateToUniqueGuestListPage();
                assertEquals(getElementText("//app-team-billing/app-team-guest-list/section/div[2]/p"),
                        "Guest quota used:1/200. Guests are counted when added to data rooms. Team members are not included in the count.");
                assert billingCredentialsAdminSettingsPage != null;
                assertEquals(getElementText("//app-team-billing/app-team-guest-list//p[@class='guest-email text-truncate']"),
                        billingCredentialsAdminSettingsPage.get("username"));
                assertEquals(getElementText("//app-team-billing/app-team-guest-list//a[@class='view-access-link']"),
                        "View access");
                break;
            case "MemberGuest":
                validateEmptyGuestListPage();
                break;
            default:
                fail("Invalid user type: " + userType);
        }
    }

    public static void validateViewAccessModal() {
        clickElement("//a[@class='view-access-link']");
        assertEquals(getElementText("//app-team-guest-access-modal/div[1]/h3").substring(0, 25),
                "View data room access for");
        waitUntilSelectorAppears("//datatable-selection//div[@class='dr-details']//p[1]");
        assertEquals(getElementText("//datatable-selection//div[@class='dr-details']//p[1]"), SharedDM.getDataRoomName());
        clickElement("//button[@class='close']");
    }

    public static void validatePresetToastMsg(String presetAction, String presetName) {
        switch (presetAction) {
            case "created":
                clickElement(btnCreate);
                validateToastMsg(CommonUIActions.txtCommonToastMsg, "Preset added.");
                validateCreatedPreset(presetName);
                break;
            case "edited":
                clickElement(btnSave);
                validateToastMsg(CommonUIActions.txtCommonToastMsg, "Preset updated.");
                break;
        }
    }

    public static void validateInvoiceInfoForBusinessPlanAdmin() {
        String txtInvoiceInfoForBussPlanAdmin = "//*[@id='collapsePlan']/div/app-team-change-plan/div/div[10]";
        assertTrue(isElementVisible(txtInvoiceInfoForBussPlanAdmin));
        assertEquals(getElementText(txtInvoiceInfoForBussPlanAdmin),
                "To see available add-ons, please refer to your invoice.");
    }

    public static void validatePlanChangeCardForBusinessPlanAdmin() {
        String txtMakeChangesToYourPlan = "(//*[@class='no-dataroom-block']/p)[1]";
        String txtContactForAssistance = "(//*[@class='no-dataroom-block']/p)[2]";
        assertTrue(isElementVisible(txtMakeChangesToYourPlan));
        assertEquals(getElementText(txtMakeChangesToYourPlan), "Want to make changes to your plan?");
        assertTrue(isElementVisible(txtContactForAssistance));
        assertEquals(getElementText(txtContactForAssistance), "We're here to help. Please contact support for assistance.");
        assertTrue(isElementVisible(CommonUIActions.commonDigifyBlueBtn));
        assertEquals(getElementText(CommonUIActions.commonDigifyBlueBtn), "Contact support");
    }

    public static void validateWaitlistPlanPageAndSelectOption(String userInterestOptionType) {
        String txtHibernationPlanTitle = "//*[@id='Title-2']/*[name()='tspan']";
        waitForURLContaining("/#/c/r");
        assertTrue(isElementVisible(txtHibernationPlanTitle));
        assertEquals(getElementText(txtHibernationPlanTitle),
                "Resume anytime with ease");
        assertEquals(getElementText("//*[@id='Subtext']/*[name()='tspan']"),
                "Pause your subscription for just $30/month");
        assertEquals(getElementText("(//*[@id='Text-4']/*[name()='tspan'])[1]"),
                "Safeguard your data");
        assertEquals(getElementText("(//*[@id='Text-4']/*[name()='tspan'])[2]"),
                "during business or legal downtime");
        assertEquals(getElementText("//*[@id='Text-3']/*[name()='tspan'][1]"),
                "Reduce costs");
        assertEquals(getElementText("//*[@id='Text-3']/*[name()='tspan'][2]"),
                "while keeping your account ready");
        assertEquals(getElementText("(//*[@id='Text-2']/*[name()='tspan'])[1]"),
                "Quickly retrieve your data");
        assertEquals(getElementText("(//*[@id='Text-2']/*[name()='tspan'])[2]"),
                "for legal matters (disputes or negotiations)");
        assertEquals(getElementText("(//*[@id='Text']/*[name()='tspan'])[1]"),
                "Reactivate easily");
        assertEquals(getElementText("(//*[@id='Text']/*[name()='tspan'])[2]"),
                "with just one click when you’re ready");

        getUserHibernationPlanInterest(userInterestOptionType);
    }

    private static void getUserHibernationPlanInterest(String userInterestOptionType) {
        page().waitForLoadState(LoadState.LOAD);
        if (userInterestOptionType.equalsIgnoreCase("i'm interested")) {
            clickElement("(//*[@class='buttons']/button)[2]");
            assertTrue(isElementVisible("//*[@id='Title']/*[name()='tspan']"));
            assertEquals(getElementText("(//*[@id='Subtext-2']/*[name()='tspan'])[1]"),
                    "Our Digify Hibernation Plan is still in development and you’ll be among the first");
            assertEquals(getElementText("(//*[@id='Subtext-2']/*[name()='tspan'])[2]"),
                    "to know once it’s ready.");
            assertEquals(getElementText("(//*[@id='Subtext']/*[name()='tspan'])[1]"),
                    "In the meantime, if you choose to cancel, we’ll save your data for up to 24");
            assertEquals(getElementText("(//*[@id='Subtext']/*[name()='tspan'])[2]"),
                    "months until the Hibernation Plan becomes available.");
        } else {
            clickElement("(//*[@class='buttons']/button)[1]");
            page().waitForLoadState(LoadState.LOAD);
        }
    }

    public static void selectCancellationButton(String cancellationBtnType) {
        page().waitForLoadState(LoadState.LOAD);
        if (cancellationBtnType.equalsIgnoreCase("continue cancellation")) {
            clickElement(getXpathForContainsText("Continue Cancellation", "button"));
        } else {
            clickElement(getXpathForContainsText("Keep Subscription", "button"));
            waitUntilSelectorAppears(txtAdminSettingsTitle);
            assertTrue(isElementVisible(txtAdminSettingsTitle));
        }
    }

    public static void selectDisbandTeamOption(String disbandOption, String userType) {
        assertEquals(getElementText("//app-delete-mem-modal/div[1]/h4"), "Disband team");
        switch (disbandOption) {
            case "disband and delete team in 30 days":
                assertEquals(getElementText("//app-delete-mem-modal//label[@for='withDelete']"), "Disband & delete team in 30 days");
                assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[2]/div[1]/div[2]/div"), "All accounts will be disabled immediately. Admins can sign in before the deletion date to restore the team if needed.");
                clickElement("//app-delete-mem-modal//label[@for='withDelete']");
                switch (userType) {
                    case "proDisbandUser", "proWithTeamDisbandAdmin", "teamWithAdminDataDisband" -> {
                        assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[1]"), "What would you like to do with the \"pro's team\" team?");
                        assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[2]/div[2]/div[1]"), "The \"pro's team\" team and its member accounts, including any files and data rooms, will be scheduled for deletion on " + CommonUIActions.getFutureDate("30", false) + " .");
                    }
                    case "teamDisbandUser" -> {
                        assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[1]"), "What would you like to do with the \"team's team\" team?");
                        assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[2]/div[2]/div[1]"), "The \"team's team\" team and its member accounts, including any files and data rooms, will be scheduled for deletion on " + CommonUIActions.getFutureDate("30", false) + " .");
                    }
                    case "teamWithTeamDisbandAdmin" -> {
                        assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[1]"), "What would you like to do with the \"team auto's team\" team?");
                        assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[2]/div[2]/div[1]"), "The \"team auto's team\" team and its member accounts, including any files and data rooms, will be scheduled for deletion on " + CommonUIActions.getFutureDate("30", false) + " .");
                    }
                    case "proWithTeamDataDisbandAdmin" -> {
                        assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[1]"), "What would you like to do with the \"pro auto's team\" team?");
                        assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[2]/div[2]/div[1]"), "The \"pro auto's team\" team and its member accounts, including any files and data rooms, will be scheduled for deletion on " + CommonUIActions.getFutureDate("30", false) + " .");
                    }
                    case "teamWithTeamDataDisbandAdmin" -> {
                        assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[1]"), "What would you like to do with the \"team plan's team\" team?");
                        assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[2]/div[2]/div[1]"), "The \"team plan's team\" team and its member accounts, including any files and data rooms, will be scheduled for deletion on " + CommonUIActions.getFutureDate("30", false) + " .");
                    }
                    default -> fail("Invalid option for selecting user in team disband: " + userType);
                }
                assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[2]/div[2]/div[2]"), "Digify will retain a record of your deletion request and any contracts. You'll still be liable for any outstanding charges with Digify.");
                assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[2]/div[2]/div[3]"), "To continue, please type: DELETE ACCOUNT");
                inputIntoTextField("//*[@id='text']", "DELETE ACCOUNT");
                assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[3]/span"),
                        "Trying to join a different team? Choose “Disband team now” instead. Learn more");

                break;
            case "disband team now":
                switch (userType) {
                    case "proDisbandUser", "proWithTeamDisbandAdmin" ->
                            assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[1]"), "What would you like to do with the \"pro's team\" team?");
                    case "teamDisbandUser" ->
                            assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[1]"), "What would you like to do with the \"team's team\" team?");
                    case "teamWithTeamDisbandAdmin" ->
                            assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[1]"), "What would you like to do with the \"team auto's team\" team?");
                    default -> fail("Invalid option for selecting disbanded user: " + userType);
                }
                assertEquals(getElementText("//app-delete-mem-modal//label[@for='withoutDelete']"), "Disband team now");
                assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[2]/div[1]/div[1]/div"), "All accounts will be converted to free viewer accounts. Thereafter, members may each join different teams.");
                assertTrue(isChecked("//*[@id='withoutDelete']"));
                break;
            default:
                fail("No disband option matched: " + disbandOption);
        }
    }

    public static void dismissDisbandTeamModal() {
        clickElement("//app-delete-mem-modal/div[2]/div[3]/button[1]");
    }

    public static void updateAndSaveTOA() {
        inputIntoTextField("//textarea[@formcontrolname='termText']", "Added test data" + RandomUtils.getRandomString(5, false));
        clickElement(CommonUIActions.commonDigifyBlueBtn);
        assertEquals(getElementText(CommonUIActions.commonModalHeader), "Please confirm changes");
        clickElement("//button[@class='btn profile-btn']/../button[2]");
        waitForSeconds(1);
        page().waitForLoadState(LoadState.LOAD);
        page().waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    public static void reorderCustomPreset() {
        page().waitForLoadState(LoadState.LOAD);
        forceClickElement("//*[@id='tabpresetlist']//tr[2]/td[2]/div/button[2]");
        validateToastMsg(CommonUIActions.txtCommonToastMsg, "Preset order updated.");
    }

    public static void validateRemoveMemberModal(String memberType) {
        memberCredentialsAdminSettingsPage = FileUtils.getUserCredentialsFromJsonFile(memberType);
        assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[1]"), "What would you like to do with autouni mem ( " + memberCredentialsAdminSettingsPage.get("username") + " ) account?");
        assertEquals(getElementText("//label[@for='withoutDelete']"), "Remove member now");
        assertEquals(getElementText("//label[@for='withoutDelete']//following-sibling::div"), "You'll no longer be able to manage this member's account. After removal, they can join another team.");
        assertEquals(getElementText("//label[@for='withDelete']"), "Remove & delete member in 30 days");
        assertEquals(getElementText("//label[@for='withDelete']//following-sibling::div"), "This member's account, files and data rooms will be immediately disabled, then deleted in 30 days.");
    }

    public static void selectRemoveMemberOptions(List<String> deleteOptions, String memberType) {
        memberCredentialsAdminSettingsPage = FileUtils.getUserCredentialsFromJsonFile(memberType);
        deleteOptions.forEach(feature -> {
            switch (feature.trim()) {
                case "Remove and delete in 30 days":
                    waitUntilSelectorAppears("//label[@for='withDelete']");
                    clickElement("//label[@for='withDelete']");
                    assertEquals(getElementText("//label[@for='withDelete']//following-sibling::div"), "This member's account, files and data rooms will be immediately disabled, then deleted in 30 days.");
                    assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[2]/div[2]/div[1]"), "This member account (" + memberCredentialsAdminSettingsPage.get("username") + ") will be scheduled for deletion on " + CommonUIActions.getFutureDate("30", false) + " .");
                    assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[2]/div[2]/div[2]"), "To continue, please type: DELETE ACCOUNT");
                    inputIntoTextField("//*[@id='text']", "DELETE ACCOUNT");
                    break;
                case "don't transfer":
                    assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[3]/div/div"), "What would you like to do with files and data rooms owned by this member?");
                    clickElement("//app-delete-mem-modal/div[2]/div[3]/div/pg-select");
                    clickElement("//*[@id='cdk-overlay-1']//li[2]");
                    assertEquals(getElementText("//app-delete-mem-modal/div[2]/div[3]/div/p"), "This member will continue to own their existing files and data rooms, even after their removal from your team.");
                    break;
                default:
                    fail("invalid option type" + feature);
            }
        });
        clickElement("//app-delete-mem-modal/div[2]/div[4]/button[2]");
    }

    public static void validateDRGuestQuotaLeftPaywall() {
        assertEquals(getElementText("//app-no-licenses-modal//h4"), "No data room guest quota left");
        assertEquals(getElementText("//div[@class='sub-text content-block']"), "To restore this member account, please add more data room guest quota to your plan.");
        clickElement("//button[@class='close']");
    }

    public static void validateDRQuota(String drQuota) {
        assertEquals(getElementText("//*[@id='collapsePlan']//app-team-change-plan//div[4]/span[2]"), "0/" + drQuota);
    }

    public static void validatePremiumFeatureAddon(List<String> addonList) {
        addonList.forEach(addon -> {
            switch (addon.trim()) {
                case "single sign on":
                    assertTrue(isElementVisible("//span[contains(text(),' Single Sign-On')]"));
                    assertTrue(isElementVisible("//span[contains(text(),' Single Sign-On')]//following::img[@src='assets/img/icon-check.svg']"));
                    break;
                case "advance branding":
                    assertTrue(isElementVisible("//span[contains(text(),' Advanced Branding with White-label URL')]"));
                    assertTrue(isElementVisible("//span[contains(text(),' Advanced Branding with White-label URL')]//following::img[@src='assets/img/icon-check.svg']"));
                    break;
                case "site integration api":
                    assertTrue(isElementVisible("//span[contains(text(),'Site Integrations APIs, Sandbox and Support')]"));
                    assertTrue(isElementVisible("//span[contains(text(),' Site Integrations APIs, Sandbox and Support')]//following::img[@src='assets/img/icon-check.svg']"));
                    break;
                default:
                    fail("invalid add on type" + addon);
            }
        });
    }

    public static void changeBrandingLogo(String fileName) throws IOException {
        String btnSaveChangesLogo = "//button[@class='btn digify-btn-blue btn-cons']";
        String previousImageSrc = getAttributeValue("//img[@class='logo-block']", "src");
        CommonUIActions.uploadFile(CommonUIActions.getFileList(fileName, "src/main/resources/testFiles/"));
        assertTrue(isElementVisible(btnSaveChangesLogo));
        assertEquals(getElementText(btnSaveChangesLogo), "Save changes");
        clickElement(btnSaveChangesLogo);
        page().waitForLoadState(LoadState.DOMCONTENTLOADED);
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears(lblChangeLogo);
        String newImgSrc = getAttributeValue("//img[@class='logo-block']", "src");
        AdminSettingsPage.clickToExpandSectionInAdminSettings("change logo");
        waitUntilSelectorAppears("//img[@class='logo-block']");
        assertNotEquals(newImgSrc, previousImageSrc);
        assertTrue(isElementVisible(btnLogoChange));
    }

    public static void updateColor(String colorForm) {
        switch (colorForm.toLowerCase()) {
            case "brand color":
                assertTrue(isDisabled(btnColorChange));
                inputIntoTextField(txtBrandColorInputBox, BRAND_COLOR);
                assertTrue(isElementEnabled(btnColorChange));
                clickElement(btnColorChange);
                page().waitForLoadState(LoadState.DOMCONTENTLOADED);
                waitForSeconds(3);
                String expectedRgb = convertHexToRgbString(BRAND_COLOR); // "rgb(0, 0, 255)"
                assertTrue(getAttributeValue("//div[@class='sidebar-menu']", "style")
                        .contains(expectedRgb), "Side bar color does not match.");
                assertTrue(getAttributeValue("//div[contains(@class,'sidebar-header')]", "style")
                        .contains(expectedRgb), "Side bar header color does not match.");
                break;
            case "text color":
                assertTrue(isDisabled(btnColorChange));
                inputIntoTextField(txtTextColorInputBox, TEXT_COLOR);
                assertTrue(isElementEnabled(btnColorChange));
                clickElement(btnColorChange);
                page().waitForLoadState(LoadState.DOMCONTENTLOADED);
                waitForSeconds(3);
                String expectedTextColorRgb = RandomUtils.convertHexToRgbString(TEXT_COLOR); // "rgb(0, 0, 255)"
                assertTrue(getAttributeValue(HomePage.lnkSendFiles, "style").contains(expectedTextColorRgb),
                        "Send files text color does not match.");
                assertTrue(getAttributeValue(HomePage.lnkManageSentFile, "style").contains(expectedTextColorRgb),
                        "Manage sent file text color does not match.");
                assertTrue(getAttributeValue(HomePage.lnkViewReceivedFiles, "style").contains(expectedTextColorRgb),
                        "View received files text color does not match.");
                assertTrue(getAttributeValue(HomePage.lblDataRooms, "style").contains(expectedTextColorRgb),
                        "Data Room label text color does not match.");
                assertTrue(getAttributeValue(HomePage.lnkCreateDataroom, "style").contains(expectedTextColorRgb),
                        "Create DR text color does not match.");
                assertTrue(getAttributeValue(HomePage.lnkManageDataRoom, "style").contains(expectedTextColorRgb),
                        "Manage DR text color does not match.");
                assertTrue(getAttributeValue(HomePage.lblIntegrations, "style").contains(expectedTextColorRgb),
                        "Integrations label text color does not match.");
                assertTrue(getAttributeValue(HomePage.lnkOverview, "style").contains(expectedTextColorRgb),
                        "Overview text color does not match.");
                assertTrue(getAttributeValue(HomePage.lnkDeveloper, "style").contains(expectedTextColorRgb),
                        "Developer text color does not match.");
                assertTrue(getAttributeValue(HomePage.lnkHome, "style").contains(expectedTextColorRgb),
                        "Home text color does not match.");
                assertTrue(getAttributeValue(HomePage.lblDocumentSecurity, "style").contains(expectedTextColorRgb),
                        "Document Security label text color does not match.");
                break;
            default:
                fail("Change color options does not matched");
        }
    }
}