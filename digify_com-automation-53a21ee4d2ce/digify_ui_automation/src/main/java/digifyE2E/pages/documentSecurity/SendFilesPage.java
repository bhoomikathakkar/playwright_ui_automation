package digifyE2E.pages.documentSecurity;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.options.ElementState;
import com.microsoft.playwright.options.LoadState;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.dataRooms.CreateDataRoomPage;
import digifyE2E.testDataManager.SharedDM;
import digifyE2E.utils.FileUtils;
import digifyE2E.utils.LogUtils;
import io.cucumber.datatable.DataTable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.*;

public class SendFilesPage extends CommonUIActions {

    public static final String ddElementList = ".cdk-overlay-pane>div>div>ul>li";
    public static final String lnkFileLink = "//*[@id='copy-link-pre']/ng-scrollbar/div/p/span";
    public static final String drdFileViewerProfile = "//div[@id='profileDropdown1 ']/span";
    public static final String btnClose = "//button[@class='btn digify-btn-blue' and contains(text(),'Close')]";
    public static final String drdDSWatermarkColor = "//pg-select[@id='share-watermark-color']";
    public static final String drdDSWatermarkPosition = "//div[@id='allow-watermark']/div/div[2]/div/div[3]/div[4]/div[1]/pg-select";
    public static final String containerSendFileDynamicWatermark = "//div[@id='allow-watermark']/div/div[2]/div";
    public static final String rdo20PercViewableArea = "//*[@id='screenshotprotection20']/../label";
    public static final String rdo35PercViewableArea = "//*[@id='screenshotprotection35']/../label";
    public static final String rdo50PercViewableArea = "//*[@id='screenshotprotection50']/../label";
    public static final String txtAddRecipient = "//input[@id='input']";
    static final String drdPermission = "//*[@id='share-copyright']";
    static final String drdDynamicWatermark = "//*[@id='share-watermark']";
    static final String drdScreenShield = "//*[@id='share-screenshield']";
    static final String drdExpiry = "//*[@id='share-expiry']";
    static final String txtExpiryMinutes = "//*[@id='destuct']/div/div[2]/div[1]/div[3]/div/div[1]/input";
    static final String btnSendAndNotify = "//*[@id='sen_btn']/button[1]/btn-spinner/span[1]";
    static final String drdPreset = "//*[@id='share-preset']";
    static final String txtFileSentTitle = getXpathForContainsText("File sent", "span");
    static final String txtShareLink = getXpathForContainsText("Share link", "span");
    public static String txtRecipient = getXpathForContainsText("Recipient", "*");
    //move the below to CommonUIActions page based on usability
    public static String drdUpload = "#root-select-dropdown";
    public static String txtSendFileHeading = ".dropdown-menu:has-text('Document Security')";
    public static String btnSendFiles = "//div[@class='dropdown-item']//span[@class='overflow-ellipsis'][1]";
    static String txtDRHeading = ".dropdown-menu:has-text('DATA ROOMS YOU OWN')";
    static String txtDRList = "//div[@class='dropdown-item']";
    static String btnImport = "//app-import-file-from-digify-modal//button//span[@class='txt']";


    public static void addRecipientInSendFile(String userType) {
        waitUntilSelectorAppears(txtRecipient);
        assertTrue(isElementVisible(txtRecipient));
        clickElement(txtAddRecipient);
        inputIntoTextField(txtAddRecipient, FileUtils.getUserCredentialsFromJsonFile(userType).get("username"));
        keyPress("Enter");
        if (!isElementVisible("(//nz-tag[@nzmode='closeable'])[1]")) {
            inputIntoTextField(txtAddRecipient, FileUtils.getUserCredentialsFromJsonFile(userType).get("username"));
            keyPress("Enter");
        }
        waitUntilSelectorAppears("(//nz-tag[@nzmode='closeable'])[1]");
    }

    public static void selectFileSendOption(String fileSendOptionType) {
        String txtEmailError = "//app-share/div[1]//div[3]//div[2]/div[3]/span";
        switch (fileSendOptionType.toLowerCase()) {
            case "send & notify recipients":
                if (isElementVisible(txtEmailError)) {
                    inputIntoTextField(txtAddRecipient, FileUtils.getUserCredentialsFromJsonFile("recipientUser").get("username"));
                    keyPress("Enter");
                    clickSendFileAndNotifyBtn();
                } else {
                    clickSendFileAndNotifyBtn();
                }

                waitUntilSelectorAppears(txtFileSentTitle);
                break;
            case "get link & skip notification":
                page().waitForLoadState(LoadState.LOAD);
                try {
                    if (isElementVisible(txtEmailError)) {
                        inputIntoTextField(txtAddRecipient, FileUtils.getUserCredentialsFromJsonFile("recipientUser").
                                get("username"));
                        keyPress("Enter");
                        clickOnGetLinkSkipNotificationBtn();
                    } else {
                        clickOnGetLinkSkipNotificationBtn();
                    }
                    waitUntilSelectorAppears(txtShareLink);
                } catch (Exception e) {
                    clickOnGetLinkSkipNotificationBtn();
                    page().waitForLoadState(LoadState.LOAD);
                    waitUntilSelectorAppears(txtShareLink);
                }
                break;
            default:
                fail("No match found in the send files: " + fileSendOptionType);
        }
    }

    public static void getSendFileSettings(List<String> dsFeatures) {
        String drdAccess = "//div[@id='sen_acc']/div/div[2]/pg-select";
        dsFeatures.forEach(feature -> {
            switch (feature.trim()) {
                case "TOA-->On", "TOA-->Off":
                    selectOptionFromToa(feature);
                    break;
                case "turn on upgrade", "Turn off":
                    page().waitForLoadState(LoadState.LOAD);
                    waitUntilSelectorAppears(drdScreenShield);
                    waitForSelectorStateChange(drdScreenShield, ElementState.STABLE);
                    selectValueFromDropdownList(drdScreenShield, ddElementList, feature);
                    if (isElementVisible("//*[@id='sen_sec']/div[6]/div[1]/div[2]/div")) {
                        selectValueFromDropdownList(drdScreenShield, ddElementList, feature);
                    } else {
                        LogUtils.infoLog("permission selected successfully");
                    }
                    break;
                case "Only people I specify", "Anyone with the link or file (email verification)",
                     "Anyone with the link (no email verification)":
                    selectValueFromDropdownList(drdAccess, ddElementList, feature);
                    break;
                case "Only people from email domains I specify":
                    selectValueFromDropdownList(drdAccess, ddElementList, feature);
                    domainInDS();
                    break;
                case "dynamic watermark-->On", "dynamic watermark-->Off":
                    selectDynamicWatermarkOption(feature);
                    break;
                case "Allow downloading", "Don't allow downloading":
                    waitForSelectorStateChange(drdPermission, ElementState.STABLE);
                    selectValueFromDropdownList(drdPermission, ddElementList, feature);
                    if (isElementVisible("//*[@id='sen_sec']/div[2]/div[1]/div[2]/div")) { //error message locator
                        selectValueFromDropdownList(drdPermission, ddElementList, feature);
                    } else {
                        LogUtils.infoLog("permission compatible with file format");
                    }
                    break;
                case "Don't allow printing", "Allow printing":
                    String drdPrint = "//pg-select[@id='share-print']";
                    waitForSelectorStateChange(drdPrint, ElementState.STABLE);
                    selectValueFromDropdownList(drdPrint, ddElementList, feature);
                    break;
                case "Require passcode":
                    String chkRequirePasscode = "//input[@id='requestSimplePassword']/../label";
                    waitUntilSelectorAppears(chkRequirePasscode);
                    clickElement(chkRequirePasscode);
                    inputIntoTextField("//input[@placeholder='Enter passcode']", "0123");
                    assertEquals(getElementText(
                                    "//input[@placeholder='Enter passcode']/../../div[2]/label"),
                            "For security reasons, please share the passcode directly with your recipients outside of Digify. Passcode is case-sensitive.");
                    break;
                case "req additional info":
                    String chkReqAddInfo = "//input[@id='reqAdditionalInfo']/..";
                    waitUntilSelectorAppears(chkReqAddInfo);
                    clickElement(chkReqAddInfo);
                    break;
                case "rai->name":
                    waitUntilSelectorAppears("//label[@for='ai-name']");
                    clickElement("//label[@for='ai-name']");
                    break;
                case "rai->phone number":
                    clickElement("//label[@for='ai-phone-number']");
                    break;
                case "rai->company":
                    clickElement("//label[@for='ai-company']");
                    break;
                case "rai->job role":
                    clickElement("//label[@for='ai-job-role']");
                    break;
                case "20% viewable area":
                    clickElement(rdo20PercViewableArea);
                    break;
                case "35% viewable area":
                    clickElement(rdo35PercViewableArea);
                    break;
                case "50% viewable area":
                    clickElement(rdo50PercViewableArea);
                    break;
                case "Expire after first access":
                    String txtExpiryIsEnabled = "//*[@id='share-expiry']/div/div/div[2]";
                    waitUntilSelectorAppears(drdExpiry);
                    selectValueFromDropdownList(drdExpiry, ddElementList, feature);
                    assertEquals(getElementText(txtExpiryIsEnabled), feature);
                    break;
                case "ppad->turn off":
                    String ppad = "//*[@id='sen_sec']/div[2]/div[2]/div[2]/div/pg-select";
                    if (isElementVisible(ppad)) {
                        selectValueFromDropdownList(ppad, ddElementList, "Turn off");
                    } else {
                        LogUtils.infoLog("The permission is other than allow download so skip this step");
                    }
                    break;
                default:
                    fail("DS settings does not match with the list item: " + feature);
            }
        });
    }

    private static void selectOptionFromToa(String toaOption) {
        String txtTOAFeature = "//div[@id='sen_sec']/div[4]/div[1]/div[1]/div";
        String drdTOA = "//pg-select[@id='share-term-of-access']";
        String txtTOAOverview = "//div[@id='termsofaccess']/div/div[2]/div/span";
        String lnkTOAPreview = "//div[@id='termsofaccess']/div/div[2]/div/span/span";

        assertEquals(getElementText(txtTOAFeature), normalizeSpaceForString("Terms of Access  $"));
        assertTrue(isElementVisible(txtTOAFeature));
        if (toaOption.equalsIgnoreCase("toa-->on")) {
            selectValueFromDropdownList(drdTOA, ddElementList, "Turn on Upgrade");
            waitUntilSelectorAppears(txtTOAOverview);
            assertEquals(getElementText(txtTOAOverview), normalizeSpaceForString(
                    "When enabled, your recipients must accept these terms of access to access files. Preview"));
            assertTrue(isElementVisible(lnkTOAPreview));
            CreateDataRoomPage.validateToAPreview(false);
        } else {
            selectValueFromDropdownList(drdTOA, ddElementList, "Turn off");
            assertFalse(isElementVisible(txtTOAOverview));
            assertFalse(isElementVisible(lnkTOAPreview));
        }
    }

    public static void addNoOfPrintValue(String noOfPrintValues) {
        if (noOfPrintValues == null || "null".equals(noOfPrintValues)) {
            return;
        }
        clearValue("//input[@name='printLimit']");
        inputIntoTextField("//input[@name='printLimit']", noOfPrintValues);
    }

    private static void selectDynamicWatermarkOption(String dwOption) {
        waitForSeconds(1); // selected field throwing unsupported error due to element not set in FE component
        if (dwOption.equals("dynamic watermark-->On")) {
            selectValueFromDropdownList(drdDynamicWatermark, ddElementList, "turn on upgrade");
        } else {
            selectValueFromDropdownList(drdDynamicWatermark, ddElementList, "Turn off");
        }
    }

    public static void getDynamicWatermarkSettings(DataTable dataTable, boolean isDwEnabled) {
        page().waitForLoadState(LoadState.LOAD);

        if (isDwEnabled) {
            getSendFileSettings(Collections.singletonList("dynamic watermark-->On"));
            waitUntilSelectorAppears(containerSendFileDynamicWatermark);//watermark container
        }
        dataTable.asMaps().forEach(row -> {
            for (Map.Entry<String, String> column : row.entrySet()) {
                switch (column.getKey().toLowerCase()) {
                    case "color":
                        switch (column.getValue().toLowerCase()) {
                            case "gray", "red", "blue":
                                selectValueFromDropdownList(drdDSWatermarkColor,
                                        SendFilesPage.ddElementList, column.getValue().toLowerCase());
                                break;
                            default:
                                fail("DS dynamic watermark color does not match with the value: " + column.getKey());
                        }
                        break;
                    case "position":
                        switch (column.getValue().toLowerCase()) {
                            case "tile", "top right", "top left":
                                waitUntilSelectorAppears(drdDSWatermarkPosition);
                                selectValueFromDropdownList(drdDSWatermarkPosition,
                                        ddElementList, column.getValue().toLowerCase());
                                if (isElementVisible(getXpathForContainsText("Watermarks for Excel will vary", "h4"))) {
                                    validateWarningModals("excel warning");
                                    page().waitForLoadState(LoadState.LOAD);
                                }
                                break;
                            case "center":
                                selectValueFromDropdownList(drdDSWatermarkPosition,
                                        ddElementList, column.getValue().toLowerCase());
                                break;
                            default:
                                fail("dynamic watermark position does not match with the value: " + column.getKey());
                        }
                        break;
                    case "date and time":
                        switch (column.getValue().toLowerCase()) {
                            case "checked":
                                waitUntilSelectorAppears(CommonUIActions.dwDateAndTimeElement);
                                clickElement(CommonUIActions.dwDateAndTimeElement);
                                break;
                            case "unchecked":
                                waitUntilSelectorAppears(CommonUIActions.dwDateAndTimeElement);
                                if (isChecked(CommonUIActions.dwDateAndTimeElement)) {
                                    clickElement(CommonUIActions.dwDateAndTimeElement);
                                } else {
                                    assertFalse(isChecked(CommonUIActions.dwDateAndTimeElement));
                                }
                                break;
                            default:
                                fail("date and time checkbox is not found: " + column.getKey());
                        }
                        break;
                    case "ip address":
                        switch (column.getValue().toLowerCase()) {
                            case "checked":
                                clickElement(CommonUIActions.dwIpAddElement);
                                break;
                            case "unchecked":
                                if (isChecked(CommonUIActions.dwIpAddElement)) {
                                    clickElement(CommonUIActions.dwIpAddElement);
                                } else {
                                    assertFalse(isChecked(CommonUIActions.dwIpAddElement));
                                }
                                break;
                            default:
                                fail("Ip address checkbox is not found: " + column.getKey());
                        }
                        break;
                    default:
                        fail("dynamic watermark setting not found: " + column.getKey());
                }
            }
        });
    }

    public static void inputExpiryMinutes(String getExpiryMinutes) {
        inputIntoTextField(txtExpiryMinutes, getExpiryMinutes);
    }

    public static void copyAndSaveSentFileLink() {
        SharedDM.setSentFileLink(getElementText(lnkFileLink));
        LogUtils.infoLog("Link of sent file is: " + SharedDM.getSentFileLink());
    }


    public static void addNoOfRecipientLimit(String noOfRecipient) {
        waitUntilSelectorAppears(getXpathForContainsText("No. of recipients", "span"));
        inputIntoTextField("//input[@name='recipientLimit']", noOfRecipient);
    }

    private static void domainInDS() {
        waitForSelectorStateChange("//input[@name='domain']", ElementState.STABLE);
        inputIntoTextField("//input[@name='domain']", "@vomoto.com");
        dispatchClickElement(getXpathForContainsText("Add Domain", "button"));
    }

    public static void validateFileFormatErrorIfPermIsAllowPrint() {
        String fileFormatErrorMsg = "//pg-select[@id='share-print']/../div[1]";
        waitUntilSelectorAppears(fileFormatErrorMsg);
        assertEquals(getElementText(fileFormatErrorMsg), normalizeSpaceForString(
                "Some uploaded file formats cannot be printed. Learn more"));
    }

    public static void validatePremiumUpgradeModalInDSForProPlan(String premiumUpgradeFeatureType) {
        String lnkLearnMore = "//a[@rel='noopener noreferrer']";
        CommonUIActions.validatePremiumUpgradeModalContent();
        assertTrue(isElementVisible(lnkLearnMore));
        assertEquals(getElementText(lnkLearnMore), "Learn more about this feature");

        switch (premiumUpgradeFeatureType.toLowerCase()) {
            case "screen shield":
                assertEquals(getElementText(
                                CommonUIActions.txtPremiumFeatureTitle).trim(),
                        "Get Screen Shield for $50/mo");
                break;
            case "dynamic watermark":
                assertEquals(getElementText(
                                CommonUIActions.txtPremiumFeatureTitle).trim(),
                        "Get Dynamic Watermark for $50/mo");
                break;
            default:
                fail("Not a valid premium upgrade feature in DS: " + premiumUpgradeFeatureType);
        }
    }

    public static void validatePremiumFeatureUpgradeLabelForDS() {
        assertTrue(isElementVisible("(//span[contains(@class,'premium-addons-label')])[2]"));
        assertTrue(isElementVisible("(//span[contains(@class,'premium-addons-label')])[3]"));
    }

    public static void clickContinueOnModalAndWaitUntilFileIsSent() {
        clickElement(CommonUIActions.btnContinue);
        waitUntilSelectorAppears(txtShareLink);
    }

    public static void clickOnGetLinkSkipNotificationBtn() {
        dispatchClickElement("//*[@id='sen_btn']/button[2]/btn-spinner/span[1]");
    }

    /**
     * Validate all modals related to storage fully consumed
     */
    public static void getStorageFullyConsumedModal(String modalType, boolean isAdmin) {
        String txtEncryptedModalSubTitle = "//p[@class='premium-txt']";
        String txtEncryptedModalTitle = "//div[@class='premium-img']/../h4";
        switch (modalType.toLowerCase()) {
            case "insufficient credits for adding one new recipient":
                assertEquals(getElementText(
                                "(//div[@class='slide-left tab-content animated']/pg-tab-body[3]/div[2]/div/div)[1]"),
                        "1 recipient could not be added");
                assertEquals(getElementText(
                                "//div[@class='slide-left tab-content animated']/pg-tab-body[3]/div[2]/div[2]"),
                        "You have insufficient credits on your account. To add the remaining recipient, please add more credits to your plan.");
                clickElement(CommonUIActions.btnCommonSave);
                break;
            case "no encrypted storage left":
                assertEquals(getElementText(txtEncryptedModalTitle),
                        "No encrypted storage left");
                if (isAdmin) {
                    assertEquals(getElementText(txtEncryptedModalSubTitle),
                            "To create a new version, please add more encrypted storage quota to your plan.");
                } else {
                    assertEquals(getElementText(txtEncryptedModalSubTitle),
                            "To create a new version, please contact your admin to add more encrypted storage quota to your plan.");
                    assertTrue(isElementVisible("(//button[contains(text(),'OK')])[1]"));
                }
                break;
            default:
                fail("Storage fully consumed modal does not match: " + modalType);
        }
    }

    public static void validateErrorMsgForSendFiles(String errorMsgOnSendFiles) {
        switch (errorMsgOnSendFiles.toLowerCase()) {
            case "file upload":
                assertEquals(getElementText("(//div[contains(@class,'digify-error-text')])[1]"),
                        "Please upload at least one file.");
                break;
            case "setting incompatible with watermark + anyone with the link(no email verification)":
                assertEquals(getElementText("//pg-select[@id='share-accessLevel']/../div"),
                        "This setting is incompatible with the Dynamic Watermark option “Email address of viewer”. Learn more");
                break;
            case "add email address":
                assertEquals(getElementText("(//span[contains(@class,'digify-error-text')])[2]"),
                        "Please enter an email address to send a notification.");
                break;
            case "add yourself as recipient":
                assertEquals(getElementText("//label[contains(@class,'digify-error-text')]"),
                        "You cannot add yourself (file owner) as a recipient.");
                clickElement(CommonUIActions.btnDeleteDR);
                break;
            default:
                fail("Error message does not matched in send files: " + errorMsgOnSendFiles);
        }
    }

    public static void clickSendFileAndNotifyBtn() {
        clickElement(btnSendAndNotify);
    }

    public static void selectMultipleFileInIEFModal(Integer noOfFile, String sourceFile) {
        String txtSelectedFile = ".selected-text";
        String chkFile = "//*[@id='switch']";
        waitUntilSelectorAppears(drdUpload);
        forceClickElement(drdUpload);
        if (sourceFile.equals("sent file")) {
            waitForSelectorAppearsWithTimeout(txtSendFileHeading, 80);
            clickElement(btnSendFiles);
            page().waitForLoadState(LoadState.LOAD);
            CommonUIActions.selectCheckboxPerUserInputIEDigifyModal(chkFile, noOfFile);
            validateNoOfFileSelectedMsg(noOfFile, txtSelectedFile);
        } else if (sourceFile.equals("data room")) {
            selectLastDRFromList(txtDRHeading, txtDRList, true);
            page().waitForLoadState(LoadState.LOAD);
            CommonUIActions.selectCheckboxPerUserInputIEDigifyModal(chkFile, noOfFile);
            validateNoOfFileSelectedMsg(noOfFile, txtSelectedFile);
        }
        clickElement(btnImport);
    }

    public static void validateNoOfFileSelectedMsg(int noOfFile, String selectedFile) {
        if (noOfFile == 1) {
            assertEquals(getElementText(selectedFile), noOfFile + " file selected");
        } else {
            assertEquals(getElementText(selectedFile), noOfFile + " files selected");
        }
    }

    public static void navigateToSFUsingSendAnotherBtn() {
        clickElement("//a[contains(@class, 'digify-default-btn')]");
    }

    public static void validateEmptySendFileListWarningMsg() {
        waitUntilSelectorAppears(drdUpload);
        forceClickElement(drdUpload);
        waitForSelectorAppearsWithTimeout(txtSendFileHeading, 80);
        clickElement(btnSendFiles);
        assertEquals(getElementText("//div[contains(text(),' You have not uploaded any files yet ')]"), "You have not uploaded any files yet");
    }

    public static void validateEmptyDRListWarningMsg() {
        waitUntilSelectorAppears(drdUpload);
        forceClickElement(drdUpload);
        waitForSelectorAppearsWithTimeout(txtDRHeading, 60);
        List<ElementHandle> drList = page().querySelectorAll(txtDRList);
        drList.get(drList.size() - 1).click();
        assertEquals(getElementText("//div[contains(text(),'This data room is empty')] "), "This data room is empty");
    }

    public static void selectSingleFileFromIEFModal(String sourceFile, boolean isSendReplace) {
        String userFileInput = getElementText("//app-replace-file/div[1]/h4");
        String fileSelector = "//div[@class='file-name overflow-ellipsis']//p";
        waitUntilSelectorAppears(drdUpload);
        forceClickElement(drdUpload);

        if (sourceFile.equals("sent file")) {
            waitForSelectorAppearsWithTimeout(txtSendFileHeading, 80);
            clickElement(btnSendFiles);
            String fileList = "//div[@class='file-info-data']";
            waitUntilSelectorAppears(fileList);
            replaceFileFromIEModal(sourceFile, userFileInput, fileSelector, isSendReplace);

        } else if (sourceFile.equals("data room")) {
            selectLastDRFromList(txtDRHeading, txtDRList, false);
            page().waitForLoadState(LoadState.LOAD);
            replaceFileFromIEModal(sourceFile, userFileInput, fileSelector, isSendReplace);
        }
        if (isElementEnabled(btnImport)) {
            clickElement(btnImport);
        } else {
            replaceFileFromIEModal(sourceFile, userFileInput, fileSelector, isSendReplace);
            clickElement(btnImport);
        }

    }

    public static void replaceFileFromIEModal(String source, String userFileInput, String locator, boolean isSendReplace) {
        String fileInput = userFileInput.replaceAll(".*\"(.*)\"", "$1").trim();
        int dotIndex = fileInput.lastIndexOf('.');
        String userFileExtension = fileInput.substring(dotIndex + 1).trim();

        List<ElementHandle> dsList = page().querySelectorAll(locator);
        waitForSelectorStateChange(locator, ElementState.STABLE);

        int startIndex;
        if (source.equals("data room")) {
            startIndex = isSendReplace ? 1 : 0;
        } else {
            startIndex = 1;
        }
        for (int i = startIndex; i < dsList.size(); i++) {

            ElementHandle fileElement = dsList.get(i);
            String fileName = fileElement.textContent().trim();
            String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);

            if (fileExtension.equalsIgnoreCase(userFileExtension)) {

                fileElement.click();
                break;
            }
        }
    }

    public static void selectLastDRFromList(String HeadingText, String dataRoomList, boolean isSendFile) {
        waitForSelectorAppearsWithTimeout(HeadingText, 60);
        List<ElementHandle> drList = page().querySelectorAll(dataRoomList);
        if (!drList.isEmpty()) {
            if (isSendFile) {
                drList.get(drList.size() - 1).click();
            } else {
                drList.get(0).click();
            }
        } else {
            LogUtils.infoLog("Data Room list is empty");
        }
    }

    public static void validateDuplicateErrorAndRemoveFilesOnSendFilePage(String errorType) {
        page().waitForLoadState(LoadState.LOAD);
        switch (errorType) {
            case "duplicate file error":
                assertEquals(getElementText("//p[contains(text(),'Please remove this duplicated file.')]"), "Please remove this duplicated file.");
                List<ElementHandle> fileList = page().querySelectorAll("//div[contains(@class,'file-element-data')]//a");
                int i = 0;
                while (i < fileList.size()) {
                    fileList.get(i).click();
                    i++;
                }
                break;
            case "file must be in the same format error":
                assertEquals(getElementText("//p[contains(text(),'File must be in the same format as the original.')]"), "File must be in the same format as the original.");
                clickElement("//app-replace-file/div[1]/button/span");
                break;
            default:
                fail("Error message not found: " + errorType);
        }
    }


    public static void validateSelectedPresetOnSF(String presetName) {
        assertEquals(getElementText("//*[@id='share-preset']//div[2]"), "Preset: " + presetName);
    }

    public static void validateFeaturesOnSendFile(List<String> featureType) {
        featureType.forEach(feature -> {
            switch (feature.trim()) {
                case "Don't allow downloading":
                    assertEquals(getElementText("//*[@id='share-copyright']//div[2]"), feature);
                    break;
                case "Allow printing":
                        assertEquals(getElementText("//*[@id='share-print']//div[2]"), feature);
                    break;
                case "TOA-->On":
                        assertEquals(getElementText("//pg-select[@id='share-term-of-access']//div[2]"), "Turn on");
                    break;
                case "dynamic watermark-->On":
                        assertEquals(getElementText("//*[@id='share-watermark']//div[2]"), "Turn on");
                    break;
                default:
                    fail("No feature type found for validation on send files page: " + feature);
            }
        });
    }

    public void validateFileUploadErrorMsgIfClickOnSendBtn(String fileUploadErrorMsg) {
        clickSendFileAndNotifyBtn();
        assertThat(page().locator(getXpathForContainsText("Please upload at least one file.", "*"))).
                containsText(fileUploadErrorMsg);
    }

    public void selectPresetFromDropdown(String presetName) {
        CommonUIActions.selectValueFromDropdownList(drdPreset, ddElementList, presetName);
        if (isElementVisible("//*[@id='sen_sec']/div[1]/div[2]/div")) {
            CommonUIActions.selectValueFromDropdownList(drdPreset, ddElementList, presetName);
        }
        page().waitForLoadState(LoadState.LOAD);
    }

    public void validateDisableFeatureOptionOnSendFilePage(String featureOptionType, String locator, String selectedText, String labelText) {
        page().waitForLoadState(LoadState.LOAD);
        switch (featureOptionType) {
            case "dropdown":
                ElementHandle selectElement = page().querySelector(locator);
                selectElement.scrollIntoViewIfNeeded();
                assertTrue((Boolean) selectElement.evaluate("el => el.classList.contains('pg-select-disabled')"));
                assertEquals(selectElement.textContent().trim(), selectedText);
                break;
            case "checkbox":
                ElementHandle selectedCheckbox = page().querySelector(locator);
                assertTrue(selectedCheckbox.isChecked());
                assertTrue(selectedCheckbox.isDisabled());
                assertEquals(getElementText(selectedText), labelText);
                break;
            default:
                fail("No feature type found: " + featureOptionType);
        }
    }

    public void validateCustomPreset(String presetState) {
        page().waitForLoadState(LoadState.LOAD);
        clickElement(drdPreset);
        List<ElementHandle> presetList = page().querySelectorAll(SendFilesPage.ddElementList);
        waitForSelectorStateChange(SendFilesPage.ddElementList, ElementState.STABLE);
        if (presetState.equals("Enable"))
            assertEquals(presetList.get(1).textContent().trim(), "Custom");
        else {
            assertNotEquals(presetList.get(0).textContent().trim(), "Custom");
        }
        page().reload();
    }

    public void validateDSPages(String userRole) {
        switch (userRole) {
            case "Restricted User":
                navigateToSendFilePage();
                assertEquals(getElementText("//app-share/div[1]//div[2]//div/p[1]"),
                        "Your account is on a \"Restricted User\" role");
                assertEquals(getElementText("//app-share/div[1]//div[2]//div/p[2]"),
                        "To use this feature, please contact your admin to upgrade you to a full user.");

                checkManageSendFilePage();
                break;
            case "User  Recommended", "Admin":
                navigateToSendFilePage();
                assertEquals(getElementText("//*[@id='sen_sel']/span"),
                        "or drag and drop files here");
                navigateToManageSentFilePage();
                assertEquals(getElementText("//app-sent-list//div[2]/div/div[2]//div[2]//div[1]/div/span[1]"),
                        "Send your first file");
                break;
            default:
                fail("User role not found: " + userRole);
        }
    }

    public void checkManageSendFilePage() {
        navigateToManageSentFilePage();
        assertEquals(getElementText("//app-sent-list//div[2]//div[2]//div[2]//div[1]/div/span[1]"),
                "Send your first file");
        assertEquals(getElementText("//app-sent-list//div[2]//div[2]//div[2]//div[1]/div/span[2]"),
                "Protect sensitive information and never lose control of them.");
        assertTrue(isElementVisible(getXpathForContainsText("Send file", "a")));
    }

    public void navigateToSendFilePage() {
        String lnkSendFile = getXpathForContainsText("Send Files", "p");
        clickElement(lnkSendFile);
        page().waitForLoadState(LoadState.LOAD);
        int i = 1;
        while (!page().url().equals("/#/sf") && i <= 3) {
            clickElement(lnkSendFile);
            waitForSeconds(3 * i++);
        }
        assertEquals(getElementText("//app-share/div[1]/div/div[1]//h4"),
                "Send Files");
    }

    public void navigateToManageSentFilePage() {
        clickElement(getXpathForContainsText("Manage Sent Files", "p"));
        page().waitForLoadState(LoadState.LOAD);
        assertEquals(getElementText("//app-sent-list/div/div/div[1]/div[1]/div//h4"),
                "Manage Sent Files");
    }

    public void validateDisableElementOnSendFilePage(List<String> presetFeatures) {
        presetFeatures.forEach(feature -> {
            switch (feature.trim()) {
                case "Allow downloading":
                    validateDisableFeatureOptionOnSendFilePage("dropdown", "pg-select#share-copyright",
                            "Allow downloading", null);
                    break;
                case "Watermark on":
                    validateDisableFeatureOptionOnSendFilePage("dropdown",
                            "pg-select#share-watermark", "Turn on", null);
                    break;
                case "Don't allow downloading":
                    validateDisableFeatureOptionOnSendFilePage("dropdown",
                            "pg-select#share-copyright", "Don't allow downloading", null);
                    break;
                case "Allow printing":
                    validateDisableFeatureOptionOnSendFilePage("dropdown",
                            "pg-select#share-print", "Allow printing", null);
                    break;
                case "TOA on":
                    validateDisableFeatureOptionOnSendFilePage("dropdown",
                            "pg-select#share-term-of-access", "Turn on", null);
                    break;
                case "Screenshield on":
                    validateDisableFeatureOptionOnSendFilePage("dropdown",
                            "pg-select#share-screenshield", "Turn on", null);
                    break;
                case "Expire after first access":
                    validateDisableFeatureOptionOnSendFilePage("dropdown",
                            "pg-select#share-expiry", "Expire after first access", null);
                    break;
                case "Only people I specify":
                    validateDisableFeatureOptionOnSendFilePage("dropdown",
                            "pg-select#share-accessLevel", "Only people I specify", null);
                    break;
                case "Expire on fixed date and time":
                    validateDisableFeatureOptionOnSendFilePage("dropdown",
                            "pg-select#share-expiry", "Expire on fixed date and time", null);
                    validateDisableFeatureOptionOnSendFilePage("dropdown",
                            "pg-select#fixed-expiry-val", "60 days", null);
                    break;
                case "Anyone with the link (no email verification)":
                    validateDisableFeatureOptionOnSendFilePage("dropdown",
                            "pg-select#share-accessLevel", "Anyone with the link (no email verification)", null);
                    page().waitForLoadState(LoadState.LOAD);
                    validateDisableFeatureOptionOnSendFilePage("checkbox",
                            "input#requestSimplePassword", "//label[@for='requestSimplePassword']",
                            "Require passcode");
                    inputIntoTextField("//*[@type='text' and @placeholder='Enter passcode']", "1234");
                    break;
                case "Only people from email domains I specify":
                    validateDisableFeatureOptionOnSendFilePage("dropdown",
                            "pg-select#share-accessLevel", "Only people from email domains I specify", null);
                    validateDisableFeatureOptionOnSendFilePage("checkbox",
                            "input#verification", "//label[@for='verification']",
                            "Enforce email verification for every view session");
                    domainInDS();
                    break;
                default:
                    fail("Send file setting not found: " + feature);
            }
        });
    }
}
