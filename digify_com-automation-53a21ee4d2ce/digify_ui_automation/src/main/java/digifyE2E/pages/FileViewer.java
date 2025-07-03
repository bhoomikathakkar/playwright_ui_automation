package digifyE2E.pages;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.ElementState;
import com.microsoft.playwright.options.LoadState;
import digifyE2E.pages.documentSecurity.ManageSentFilesPage;
import digifyE2E.pages.documentSecurity.SendFilesPage;
import digifyE2E.pages.documentSecurity.ViewReceivedFilesPage;
import digifyE2E.pages.landingPage.LoginPage;
import digifyE2E.testDataManager.SharedDM;
import digifyE2E.utils.FileUtils;
import digifyE2E.utils.LogUtils;
import digifyE2E.utils.RandomUtils;
import org.testng.Assert;

import java.util.List;

import static digifyE2E.pages.MailDropEmailService.getOTPFromMailDrop;
import static digifyE2E.pages.landingPage.LoginPage.*;
import static org.testng.Assert.*;

public class FileViewer extends CommonUIActions {

    public static final String btnPrintInFileViewer = "(//i[contains(@class,'fa-print')]/../..)[1]";
    public static final String txtFileProcessingError = getXpathForContainsText("We are processing this file", "*");
    public static final String btnDownloadInFileViewer = "(//i[contains(@class,'fa-arrow-to-bottom')]/../..)[1]";
    public static final String btnRAIContinue = "//form//button[@class='btn digify-btn-blue']";
    public static final String componentPageLoader = "//div[@class='pageloader clearfix']";
    public static final String imgFileViewerBrandingLogo = "//div[@class='brand-logo']";


    public static void validateWhatIfExcelHeader(boolean isWhatIfExcelEnabled) {
        String containerWhatIfExcel = "//div[contains(@class, 'what-if-header')]";
        if (isWhatIfExcelEnabled) {
            waitUntilSelectorAppears(containerWhatIfExcel);
            assertTrue(isElementVisible(containerWhatIfExcel));
            assertEquals(getElementText("//div[contains(@class, 'what-if-header')]/div/span"),
                    "Excel What-If Analysis Mode enabled. You can edit this spreadsheet, but not save or download your changes.");
        } else {
            assertFalse(isElementVisible(containerWhatIfExcel));
        }
    }

    public static void selectPresetNameDisplayOnFileViewer() {
        dispatchClickElement("//app-create-preset-modal//input[@id='showonViewerInput']");
    }

    public static void fileLoadInFileViewer() {
        int retryCount = 0;
        int maxRetries = 3;
        page().waitForLoadState(LoadState.DOMCONTENTLOADED);
        while (retryCount < maxRetries) {
            if (isElementVisible(txtFileProcessingError)) {
                LogUtils.infoLog("File processing error detected. Reloading page... Attempt " + (retryCount + 1));
                page().reload();
                waitForSeconds(10 * (retryCount + 1));
                retryCount++;
                if (retryCount == maxRetries && isElementVisible(txtFileProcessingError)) {
                    throw new RuntimeException("File processing error persists after " + maxRetries + " attempts. Unable to proceed.");
                }
            } else {
                break;// If error is no longer visible, exit the loop
            }
        }
        // Ensure specific elements appear in the file viewer
        waitUntilSelectorAppears(imgFileViewerBrandingLogo);
        waitUntilSelectorAppears(containerFileViewerFooter);
        LogUtils.infoLog("File viewer is loaded and ready for interaction.");
    }


    public static void viewFileInFileViewer(boolean isViewReceivedFiles) {
        if (isViewReceivedFiles) {
            clickAndSwitchToNewTab("(//i[contains(@class,'fa-eye far')])[1]/..");
            page().waitForLoadState(LoadState.LOAD);
        } else {
            clickAndSwitchToNewTab(lnkViewFloatingMenu);
            page().waitForLoadState(LoadState.LOAD);
        }
        fileLoadInFileViewer();
    }

    public static void validateFileName() {
        String actualFileName =
                getElementText(getXpathForContainsText(SharedDM.getNewFileNameWithExtension(), "p"));
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears(imgFileViewerBrandingLogo);
        Assert.assertEquals(SharedDM.getNewFileNameWithExtension().trim(), actualFileName.trim());
        page().waitForLoadState(LoadState.LOAD);
    }

    public static void validatePresetNameOnFileViewer(String presetName) {
        assertEquals(getElementText("//p[contains(@class,'viewer-label')]"), presetName); //preset label
    }

    public static void requestAccessForDR() {
        clickElement(getXpathForContainsText("Request access", "button"));
        waitUntilSelectorAppears("//pg-tab-body[1]//div//h4");
        assertEquals(getElementText("//pg-tab-body[1]//div//h4"), "Request access");
        assertEquals(getElementText("//pg-tab-body[1]/div//p"), "Let the owner know why you need access.");
        inputIntoTextField("//pg-tab-body[1]//textarea", "Please provide me access");
        clickElement(getXpathForContainsText("Send request", "span"));
        assertEquals(getElementText("//pg-tab-body[2]/div//h4"), "Request sent");
        clickElement(getXpathForTextEquals(" Close ", "button"));
    }

    public static void requestAccessForDS() {
        clickElement(getXpathForContainsText("Request access", "button"));
        waitUntilSelectorAppears("//div[@role='tabpanel'][1]//h4");
        assertEquals(getElementText("//div[@role='tabpanel'][1]//h4"), "Request access");
        assertEquals(getElementText("//div[@role='tabpanel'][1]//p"), "Let the owner know why you need access.");
        inputIntoTextField("//div[@role='tabpanel'][1]//textarea", "Please provide me access");
        clickElement(getXpathForContainsText("Send request", "span"));
        assertEquals(getElementText("//div[@role='tabpanel'][2]//h4"), "Request sent");
        clickElement(getXpathForTextEquals(" Close ", "button"));
    }

    public static void useAnotherEmailToAccessDR() {
        clickElement(getXpathForContainsText("Use another email", "a"));
    }

    public static void exitFromFileViewer(String optionToExitFromFileViewer) {
        switch (optionToExitFromFileViewer) {
            case "go to file list":
                clickElement(getXpathForContainsText("go to file list", "a"));
                assertTrue(isElementVisible(getXpathForContainsText("View Received Files", "h4")));
                break;
            case "go to data room list":
                clickElement(getXpathForContainsText("go to data room list", "a"));
                assertTrue(isElementVisible(getXpathForContainsText("Manage Data Rooms", "h4")));
                break;
            default:
                fail("Invalid option for navigating to received files or manage data room page from file viewer: " + optionToExitFromFileViewer);
        }
    }


    public static void validateButtonsInFileViewer(List<String> fileViewerBtnType) {
        waitForSeconds(1);
        String dwdBtn = getAttributeValue(btnDownloadInFileViewer, "class");
        ElementHandle printBtn = page().querySelector(btnPrintInFileViewer);
        fileViewerBtnType.forEach(fvBtn -> {
            switch (fvBtn) {
                case "enabled-->download button":
                    page().waitForLoadState(LoadState.LOAD);
                    page().waitForLoadState(LoadState.DOMCONTENTLOADED);
                    assertTrue(page().locator(btnDownloadInFileViewer).isEnabled(), "Button should be enabled but it is disabled.");
                    break;
                case "enabled-->print button":
                    page().waitForLoadState(LoadState.LOAD);

                    page().waitForLoadState(LoadState.LOAD);
                    page().waitForLoadState(LoadState.DOMCONTENTLOADED);
                    waitUntilSelectorAppears(btnPrintInFileViewer);
                    assertTrue(printBtn.isEnabled());
                    break;

                case "disabled-->download button":
                    page().waitForLoadState(LoadState.LOAD);
                    page().waitForLoadState(LoadState.LOAD);
                    Locator progressBar1 = page().locator("//div[@class='pace-progress']");
                    page().waitForCondition(() -> "100%".equals(progressBar1.getAttribute("data-progress-text")));
                    assertTrue(page().locator(btnDownloadInFileViewer).isDisabled());
                    break;

                case "disabled-->print button":
                    page().waitForLoadState(LoadState.LOAD);
                    if (isElementVisible(txtFileProcessingError)) {
                        page().reload();
                    } else {
                        page().waitForLoadState(LoadState.LOAD);
                        assertFalse(printBtn.isEnabled());
                    }
                    break;
                default:
                    fail("File viewer button does not matched: " + fvBtn);
            }
        });
    }

    public static void verifyScreenShieldContainerInFV(boolean isScreenShieldVisible) {
        String screenShieldContainer = "//*[@id='screenshield']/div[1]";
        if (isScreenShieldVisible) {
            page().waitForLoadState(LoadState.LOAD);
            waitUntilSelectorAppears(screenShieldContainer);
            assertTrue(isElementVisible(screenShieldContainer));
        } else {
            page().waitForLoadState(LoadState.LOAD);
            assertFalse(isElementVisible(screenShieldContainer));
        }
    }

    public static void doLogoutFromFileViewerAsSender() {
        clickElement(drdFileViewerUserProfile);
        clickElement(lnkLogout);
        waitUntilSelectorAppears("//span[@class='owner-nameplate']");
        closeCurrentTab();
    }

    public static void validateFileErrorMessage(String errorMsg) {
        String txtNoAccessToViewFileMsg = getXpathForContainsText("You have not been invited to view this file.", "p");
        String txtFileNotAvailableMsg = getXpathForContainsText("This file is not available", "p");
        String txtPreviewNotAvailable = getXpathForContainsText("Preview not available", "p");
        String txtContactOwnerErrorMsg = getXpathForContainsText("Please check with the owner if you need access.", "p");
        String txtFileFormatNotSupported = getXpathForContainsText("This file format is not supported.", "p");
        String txtItmRvkErrorCode = getXpathForContainsText("ITM_RVK", "p");
        String txtItmDeleteErrorCode = getXpathForContainsText("ITM_DEL", "p");
        String txtSubExpErrorCode = getXpathForContainsText("SUB_EXP", "p");
        String txtProblemWithTheLink = "//pg-tab-body[@class='tab-pane active']/div/div/p";
        String txtInvalidLink = "(//pg-tab-body[@class='tab-pane active']/div/div/div/p)[1]";
        String txtVisitAnotherNetwork = "(//pg-tab-body[@class='tab-pane active']/div/div/div/p)[2]";
        String btnGoToFileList = getXpathForContainsText("Go to file list", "span");
        String btnDownload = getXpathForContainsText("Download", "button");
        String txtItmExpErrorCode = getXpathForContainsText("ITM_EXP", "p");
        switch (errorMsg) {
            case "Access denied":
                if (isElementVisible(txtFileProcessingError)) {
                    page().reload();
                } else {
                    waitUntilSelectorAppears(txtAccessDeniedErrorMsg);
                    assertTrue(isElementVisible(txtAccessDeniedErrorMsg));
                }
                break;
            case "You have not been invited to view this file.":
                waitUntilSelectorAppears(txtNoAccessToViewFileMsg);
                assertTrue(isElementVisible(txtNoAccessToViewFileMsg));
                waitUntilSelectorAppears(btnGoToFileList);
                assertTrue(isElementVisible(btnGoToFileList));
                break;
            case "This file is not available":
                waitUntilSelectorAppears(txtFileNotAvailableMsg);
                assertTrue(isElementVisible(txtFileNotAvailableMsg));
                waitUntilSelectorAppears(btnGoToFileList);
                assertTrue(isElementVisible(btnGoToFileList));
                break;
            case "Preview not available":
                waitUntilSelectorAppears(txtPreviewNotAvailable);
                assertTrue(isElementVisible(txtPreviewNotAvailable));
                waitUntilSelectorAppears(btnDownload);
                assertTrue(isElementVisible(btnDownload));
                break;
            case "Please check with the owner if you need access.":
                waitUntilSelectorAppears(txtContactOwnerErrorMsg);
                assertTrue(isElementVisible(txtContactOwnerErrorMsg));
                break;
            case "This file format is not supported.":
                waitUntilSelectorAppears(txtFileFormatNotSupported);
                assertTrue(isElementVisible(txtFileFormatNotSupported));
                break;
            case "ITM_RVK":
                waitUntilSelectorAppears(txtItmRvkErrorCode);
                assertTrue(isElementVisible(txtItmRvkErrorCode));
                break;
            case "ITM_DEL":
                waitUntilSelectorAppears(txtItmDeleteErrorCode);
                assertTrue(isElementVisible(txtItmDeleteErrorCode));
                break;
            case "SUB_EXP":
                waitUntilSelectorAppears(txtSubExpErrorCode);
                assertTrue(isElementVisible(txtSubExpErrorCode));
                break;
            case "Invalid Link":
                waitUntilSelectorAppears(txtInvalidLink);
                assertTrue(isElementVisible(txtInvalidLink));
                break;
            case "Problem with the link":
                waitUntilSelectorAppears(txtProblemWithTheLink);
                assertTrue(isElementVisible(txtProblemWithTheLink));
                break;
            case "Visit another network":
                waitUntilSelectorAppears(txtVisitAnotherNetwork);
                assertTrue(isElementVisible(txtVisitAnotherNetwork));
                break;
            case "ITM_EXP":
                waitUntilSelectorAppears(txtItmExpErrorCode);
                assertTrue(isElementVisible(txtItmExpErrorCode));
                break;
            default:
                fail("No match found for the error messages: " + errorMsg);
        }
    }

    public static void fillSignupFormInFileViewer() {
        String txtPasswordSignupForm = "//form//input[@formcontrolname='newPassword']";
        waitUntilSelectorAppears(getXpathForContainsText(
                "Create an optional free account to skip email verification next time.", "div"));
        inputIntoTextField(txtNewUserFName, "Auto-" + RandomUtils.getRandomString(2, false));
        inputIntoTextField(txtNewUserLName, "-Test" + RandomUtils.getRandomString(2, false));
        inputIntoTextField(txtPasswordSignupForm, "neWm@tion412");
        clickElement(btnSubmit);
    }

    public static void navigateToFileListFromFileViewer() {
        page().waitForLoadState(LoadState.LOAD);
        if (isElementVisible(drdFileViewerUserProfile)) {
            clickElement(drdFileViewerUserProfile);
        } else {
            page().reload();
            waitForSelectorStateChange(drdFileViewerUserProfile, ElementState.STABLE);
            waitUntilSelectorAppears(drdFileViewerUserProfile);
            clickElement(drdFileViewerUserProfile);
        }
        clickElement(getXpathForContainsText("Go to file list", "span"));
        waitForURLContaining("/#/r");
        if (!isElementVisible(getXpathForContainsText("View Received Files", "h4"))) {
            page().reload();
            int retryCount = 0;
            while (retryCount < 5) {
                waitForSelectorStateChange(drdFileViewerUserProfile, ElementState.STABLE);
                dispatchClickElement(drdFileViewerUserProfile);
                dispatchClickElement(getXpathForContainsText("Go to File List", "span"));
                retryCount++;
            }
        }
    }

    public static void downloadFileInFileViewer() {
        clickElement(btnDownloadInFileViewer);
        waitUntilSelectorAppears
                (getXpathForContainsText("Preparing to download.", "p"));
        waitUntilSelectorAppears(btnDownloadInFileViewer);
    }

    public static void viewFileAsSender() {
        clickAndSwitchToNewTab(lnkViewFloatingMenu);
    }

    public static void goToViewReceivedFilesFromFileViewer() {
        waitUntilSelectorAppears(SendFilesPage.drdFileViewerProfile);
        clickElement(drdFileViewerUserProfile);
        clickElement(lnkGoToFileList);
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears(ViewReceivedFilesPage.txtViewReceivedFileHeading);
        assertTrue(isElementVisible(ViewReceivedFilesPage.txtViewReceivedFileHeading));
    }

    public static void doFileVersionValidationInFileViewer() {
        clickElement(SendFilesPage.drdFileViewerProfile);
        clickElement(getXpathForContainsText("View versions", "a"));
        waitUntilSelectorAppears(getXpathForContainsText("View versions", "h4"));
        assertTrue(isElementVisible(ManageSentFilesPage.txtCurrentVersion));
        assertTrue(isElementVisible(ManageSentFilesPage.lblFileVersionCommentInFileViewer));
        assertTrue(isElementVisible(ManageSentFilesPage.txtCurrentVersion));
        assertEquals(getElementText(ManageSentFilesPage.
                lblFileVersionCommentInFileViewer), "File V2");
        clickElement(SendFilesPage.btnClose);
    }

    public static void authenticateUserWithOTP(String user) {
        String txtOTPBox = "//input[@autocomplete='one-time-code'][%d]";
        String otp = getOTPFromMailDrop(user.toLowerCase().contains("new user") ?
                LoginPage.newUser :
                FileUtils.getUserCredentialsFromJsonFile(user).get("username").replace("@maildrop.cc", ""));
        for (int i = 0; i < otp.length(); i++) {
            char digit = otp.charAt(i);
            ElementHandle otpInputField = page().querySelector(String.format(txtOTPBox, i + 1)); //Find the OTP input field
            if (otpInputField != null) {
                otpInputField.press(String.valueOf(digit)); //press the digit into it
            } else {
                LogUtils.infoLog("OTP input field not found for digit " + digit);
            }
        }
        clickElement(btnVerify);
        if (isElementVisible(SendFilesPage.drdFileViewerProfile)) {
            waitUntilSelectorAppears(containerFileViewerFooter);
        } else {
            LogUtils.infoLog("The dropdown cover by new user signup modal");
            waitUntilSelectorAppears(SendFilesPage.drdFileViewerProfile);
            waitUntilSelectorAppears(containerFileViewerFooter);
        }
    }

    public static void navigateToSentFileLink(boolean asNewUser) {
        LogUtils.infoLog("Navigating to the file link now: " + SharedDM.getSentFileLink());
        visit(SharedDM.getSentFileLink(), false);
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears(txtVerifyYourIdentity);
        LogUtils.infoLog("Navigating to sent file link: " + page().url());
        if (asNewUser) {
            LoginPage.getVerificationCodeAsNewUser();
            page().waitForLoadState(LoadState.LOAD);
            waitUntilSelectorAppears(btnVerify);
            waitUntilSelectorAppears(getXpathForContainsText
                    ("resend verification code", "a"));
        }
    }

    public static void selectFileViewingOption(String optionType) {
        String txtFileOwnerViewingChoices = getXpathForContainsText("Your viewing choices as the file owner",
                "h4");
        switch (optionType.toLowerCase()) {
            case "preview file as recipient":
                waitUntilSelectorAppears(txtFileOwnerViewingChoices);
                clickElement(getXpathForContainsText("Preview file as recipient", "button"));
                break;
            case "view original file":
                waitUntilSelectorAppears(txtFileOwnerViewingChoices);
                clickElement(getXpathForContainsText("View original file", "button"));
                break;

            default:
                LogUtils.infoLog("No match found in the file viewing option for sender: " + optionType);
        }
    }

    public static void navigateToSecuredFileLink(Boolean isPasscodeRequired) {
        String btnContinue = "//form[@id='enter_passkey']//button[@class='btn digify-btn-blue']";
        LogUtils.infoLog("Navigating to the secured file link: " + SharedDM.getSentFileLink());
        visit(SharedDM.getSentFileLink(), false);
        page().waitForLoadState(LoadState.LOAD);
        if (isPasscodeRequired) {
            waitUntilSelectorAppears(getXpathForContainsText("Passkey Protected File", "p"));
            inputIntoTextField("//input[@id='file_passkey']", "0123");
            clickElement(btnContinue);
            page().waitForLoadState(LoadState.LOAD);
            waitUntilSelectorAppears(containerFileViewerFooter);
        }
    }

    public static void agreeAndContinueTOAInFV(String fileViewerUserType, Boolean isTOAEnabled) {
        if (!isTOAEnabled) {
            return;
        }
        if (fileViewerUserType.equalsIgnoreCase("sender as recipient")) {
            waitUntilSelectorAppears(commonModalHeader);
            assertEquals(getElementText(commonModalHeader), "\"Terms of Access\" option enabled");
            assertTrue(isElementVisible(txtToAModalBody));
            clickElement("//input[@id='skip-terms']/..");
            clickElement(btnContinue);
            waitUntilSelectorAppears(SendFilesPage.containerFileViewerFooter);
        } else {
            acceptTOAAsRecipient();
            waitUntilSelectorAppears(SendFilesPage.containerFileViewerFooter);
        }
    }

    public static void acceptTOAAsRecipient() {
        waitUntilSelectorAppears("(//div[@class='modal-header']/h4)[1]");
        assertEquals(getElementText("(//div[@class='modal-header']/h4)[1]"), "Terms of Access");
        assertTrue(isElementVisible(txtToAModalBody));
        agreeToA();
    }

    public static void clickFolderTreeIconInFV() {
        String btnFolderTreeIcon = "//*[@id='toolbarContainer']/uv-viewer-toolbar/div/div/div[1]/div[1]/button";
        assertTrue(isElementVisible(btnFolderTreeIcon));
        clickElement(btnFolderTreeIcon);
        waitUntilSelectorAppears("//div[@id='dropdown-basic']"); //dropdown menu
    }

    public static void selectOptionInFolderTreeInFV(String optionType) {
        switch (optionType.toLowerCase()) {
            case "data room":
                waitForSeconds(3);//waiting for elements to load completely
                clickElement("//span[text()='Go to data room']/..");
                waitUntilSelectorAppears(getXpathForContainsText("Files", "p"));
                page().waitForLoadState(LoadState.DOMCONTENTLOADED);
                break;
            case "file":
                //Todo: Add code for selecting and navigating to a file from file viewer folder tree icon
                LogUtils.infoLog("select file");
                break;
            case "folder":
                //Todo: Add code for selecting and navigating to a folder from file viewer folder tree icon
                LogUtils.infoLog("select folder");
                break;
            default:
                LogUtils.infoLog("option does not matched in folder tree");
        }
    }

    public static void validateBrandLogoRedirection() {
        clickElement("//app-viewer-wrapper//div[@class='brand-logo']");
        assertTrue(page().url().contains("a/#/f/s/"));
    }

    //ToDo: Add more permission check in upcoming sprint
    public static void validateErrorPage(String permissionType) {
        String txtErrorPageHeader = "//*[@id='destruct-image']/div/p[contains(@class,'file-viewer-header')]";
        String txtErrorPageSubHeader = "//*[@id='destruct-image']/div/p[2]";
        String txtDrLinkExpHeader = "(//*[@class='sub-dataroom-layout has-error']/div/div[2]/p)[1]";
        String txtDrLinkExpSubHeader = "(//*[@class='sub-dataroom-layout has-error']/div/div[2]/p)[2]";
        String txtDrLinkExpCode = "(//*[@class='sub-dataroom-layout has-error']/div/div[2]/p)[3]";
        switch (permissionType) {
            case "no access":
                assertEquals(getElementText(txtErrorPageHeader), "You do not have permission here");
                assertEquals(getElementText(txtErrorPageSubHeader), "Please check with the owner if you need access.");
                assertTrue(isElementHidden("//*[@id='destruct-image']/div[2]/button/span[contains(text(),'Go to file list')]"));
                break;
            case "link expired":
                assertEquals(getElementText(txtErrorPageHeader), "This file is not available");
                assertEquals(getElementText(txtErrorPageSubHeader), "Please check with the owner if you need access.");
                assertTrue(isElementVisible("//*[@id='destruct-image']/div/p[3]"));
                assertEquals(getElementText("//*[@id='destruct-image']/div/p[3]"), "LNK_EXP");
                break;
            case "browser lock":
                assertEquals(getElementText(txtErrorPageHeader), "This file is not available");
                assertEquals(getElementText(txtErrorPageSubHeader), "Please check with the owner if you need access.");
                assertTrue(isElementVisible("//*[@id='destruct-image']/div/p[3]"));
                assertEquals(getElementText("//*[@id='destruct-image']/div/p[3]"), "BRW_LOC");
                break;
            case "dr-->link expired":
                assertEquals(getElementText(txtDrLinkExpHeader), "This data room is not available.");
                assertEquals(getElementText("//*[@class='sub-dataroom-layout has-error']/div/div[2]/span/p"), "Please check with the owner if you need access.");
                assertTrue(isElementVisible("(//*[@class='sub-dataroom-layout has-error']/div/div[2]/p)[2]"));
                assertEquals(getElementText("(//*[@class='sub-dataroom-layout has-error']/div/div[2]/p)[2]"), "LNK_EXP");
                break;
            case "dr-->browser lock":
                assertEquals(getElementText(txtDrLinkExpHeader), "This data room is not available.");
                assertEquals(getElementText("//*[@class='sub-dataroom-layout has-error']/div/div[2]/span/p"), "Please check with the owner if you need access.");
                assertTrue(isElementVisible("(//*[@class='sub-dataroom-layout has-error']/div/div[2]/p)[2]"));
                assertEquals(getElementText("(//*[@class='sub-dataroom-layout has-error']/div/div[2]/p)[2]"), "BRW_LOC");
                break;
            default:
                fail("Invalid error page found for: " + permissionType);
        }
    }

    public static void checkEmailFieldAndProceedRAI(boolean isContinueRequired) {
        boolean isEmailDisabled = isDisabled("//*[@id='ai-email']");
        if (!isEmailDisabled) {
            throw new AssertionError("Email field is not disabled as expected.");
        }
        if (isContinueRequired) {
            clickElement(btnRAIContinue);
        }
    }

    public static void fillRAIFormInFV(List<String> inputFields, boolean isFieldErrorGenerationRequired) {
        if (isFieldErrorGenerationRequired) {
            clickElement(btnRAIContinue);
        } else {
            inputFields.forEach(featureType -> {
                String email = "//*[@id='ai-email']";
                switch (featureType.trim()) {
                    case "rai->email":
                        clearValue(email);
                        inputIntoTextField(email, (RandomUtils.getRandomString(3, true) + "@vomoto.com"));
                        break;
                    case "rai->invalid email":
                        clearValue(email);
                        inputIntoTextField(email, RandomUtils.getRandomString(3, true));
                        break;
                    case "rai->name":
                        inputIntoTextField("//*[@id='ai-name']", RandomUtils.getRandomString(3, false));
                        break;
                    case "rai->phone":
                        inputIntoTextField("//*[@id='ai-phone-number']", RandomUtils.getRandomString(7, true));
                        break;
                    case "rai->company":
                        inputIntoTextField("//*[@id='ai-company']", RandomUtils.getRandomString(5, false));
                        break;
                    case "rai->job":
                        inputIntoTextField("//*[@id='ai-job-role']", RandomUtils.getRandomString(4, false));
                        break;
                    default:
                        fail("unable to fill request additional info form" + featureType);
                }
                clickElement(btnRAIContinue);
            });
        }
    }

    public static void validateErrorMsgInRAIForm(List<String> inputErrFields) {
        inputErrFields.forEach(featureType -> {
            switch (featureType.trim()) {
                case "email_err":
                    assertEquals(getElementText("//input[@id='ai-email']/following-sibling::div[@class='input-error']//div[@class='digify-error-text']"), "This field cannot be blank.");
                    break;
                case "invalidEmail_err":
                    assertEquals(getElementText("//input[@id='ai-email']/following-sibling::div[@class='input-error']//div[@class='digify-error-text']"), "Please enter a valid email address.");
                    break;
                case "name_err":
                    assertEquals(getElementText("//input[@id='ai-name']/following-sibling::div[@class='input-error']//div[@class='digify-error-text']"), "This field cannot be blank.");
                    break;
                case "phone_err":
                    assertEquals(getElementText("//input[@id='ai-phone-number']/following-sibling::div[@class='input-error']//div[@class='digify-error-text']"), "This field cannot be blank.");
                    break;
                case "company_err":
                    assertEquals(getElementText("//*[@id='ai-company']//following-sibling::div[@class='input-error']//div[@class='digify-error-text']"), "This field cannot be blank.");
                    break;
                case "job_err":
                    assertEquals(getElementText("//*[@id='ai-job-role']//following-sibling::div[@class='input-error']//div[@class='digify-error-text']"), "This field cannot be blank.");
                    break;
                default:
                    fail("Invalid field type: " + featureType);
            }
        });

    }

    public static void waitForFileLoad() {
        waitForElementHidden(componentPageLoader);
        page().waitForLoadState(LoadState.LOAD);
        page().waitForLoadState(LoadState.DOMCONTENTLOADED);
        waitForSeconds(1);
    }

    public static void visitPreAuthUrl(boolean retryOnFailure, boolean isLinkExpired) {
        visit(SharedDM.preAuthUrl.get(), false);
        page().waitForLoadState(LoadState.LOAD);
        if (isLinkExpired) {
            waitForSeconds(2);
            LogUtils.infoLog("Pre-auth file link is expired");
        }
        if (retryOnFailure) {
            int retryCount = 1;
            while (isElementVisible("//*[@id='destruct-image']/div[1]/p[1]") && retryCount <= 3) {
                page().reload();
                waitForSeconds(5 * retryCount++);
            }
            page().waitForLoadState(LoadState.DOMCONTENTLOADED);
            waitUntilSelectorAppears(containerFileViewerFooter);
        }
        waitForSeconds(5); //Hard wait for the page to load the element
        page().waitForLoadState(LoadState.LOAD);
        page().waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    public static void visitDRFilePreAuthUrl() {
        visit(SharedDM.preAuthUrl.get(), false);
        page().waitForLoadState(LoadState.LOAD);
        waitForSeconds(3); //Hard wait for the page to load the element
        fileLoadInFileViewer();
        waitForSeconds(5); //Hard wait for the page to load the element
        page().waitForLoadState(LoadState.LOAD);
        page().waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    public void getVerificationCodeForExistingUser(String userType) {
        waitUntilSelectorAppears(txtVerifyYourIdentity);
        inputIntoTextField(txtUsername, FileUtils.getUserCredentialsFromJsonFile(userType)
                .get("username"));
        clickElement(btnNext);
        inputIntoTextField(txtFileViewerPassword, FileUtils.getUserCredentialsFromJsonFile(userType)
                .get("password"));
        clickElement(btnViewFile);
        handleActiveSessionButton();
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears(getXpathForTextEquals
                ("Verify your email", "h4"));
    }

    public void navigateToSentFileLinkAndGenerateOTP(String getUserType) {
        LogUtils.infoLog("Navigating to the file link now: " + SharedDM.getSentFileLink());
        visit(SharedDM.getSentFileLink(), false);
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears(txtVerifyYourIdentity);
        LogUtils.infoLog("Navigating to sent file link: " + page().url());
        getVerificationCodeForExistingUser(getUserType);
    }
}
