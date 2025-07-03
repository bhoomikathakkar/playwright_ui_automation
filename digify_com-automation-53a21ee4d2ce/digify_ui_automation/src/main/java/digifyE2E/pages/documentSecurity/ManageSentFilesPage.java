package digifyE2E.pages.documentSecurity;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.ElementState;
import com.microsoft.playwright.options.LoadState;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.dataRooms.CreateDataRoomPage;
import digifyE2E.pages.landingPage.HomePage;
import digifyE2E.testDataManager.SharedDM;
import digifyE2E.utils.FileUtils;
import digifyE2E.utils.LogUtils;
import io.cucumber.datatable.DataTable;
import org.testng.Assert;

import java.io.File;
import java.util.List;
import java.util.Map;

import static digifyE2E.pages.documentSecurity.SendFilesPage.*;
import static org.testng.Assert.*;

public class ManageSentFilesPage extends CommonUIActions {

    public static final String txtCurrentVersion = "(//em[(text()=' – Current version ')])[1]";
    public static final String txtHeadingManageSentFile = getXpathForTextEquals(" Manage Sent Files ", "h4");
    public static final String lblFileVersionCommentInFileViewer = "//div[contains(@class, 'file-size-comment')][contains(text(), 'File V2')]";
    public static final String btnAddInAddRecipient = "//button[@class='btn digify-btn-blue']";
    static final String txtFileVersionComment = "//textarea[@placeholder='Enter message (optional)']";
    static final String lblFileVersionComment = "(//span[contains(@class, 'file-size-comment')])[1]/../../div[3][contains(text(),'File V2')]";
    static final String btnSendNotification = getXpathForTextEquals(" Send notification ", "button");
    static final String btnSkipNotification = "//button[@class='btn digify-btn-secondary']";
    public static String btnAddBulkRecipient = "//pg-tab-body[1]/div[2]/div[3]/button[1]";
    public static String txtBulkRecipient = "//*[@id='input']";
    public static File recipientListExcelFile = new File(new File("src/main/resources/testUserDetails/BulkEmail.xlsx").getAbsolutePath());
    public static String analyticsModalTitle = "//div[@class='modal-content']/div/h4";
    static String getNewRecipientUsername = "";
    static String fileSelector = "//div[@class='file-name overflow-ellipsis']//p";

    public static void navigateToManageSentFilePage() {
        clickElement(HomePage.lnkManageSentFile);
        waitUntilSelectorAppears(getXpathForContainsText("Manage Sent File", "h4"));
    }

    public static void updateSentFileSettings(List<String> dsFeatures) {
        String drdPermissionOnDsSetting = "(//div[contains(@class, 'share-setting')])/div/div[2]/pg-select";
        String drdPrintPermOnDsSettings = "//div[@id='nodownload']/div/pg-select";
        waitForSeconds(1);
        dsFeatures.forEach(feature -> {
            switch (feature.trim()) {
                case "TOA-->On", "TOA-->Off":
                    updateTOASetting(feature);
                    break;
                case "Don't allow downloading", "Allow downloading":
                    selectValueFromDropdownList(
                            drdPermissionOnDsSetting, SendFilesPage.ddElementList, feature);
                    break;
                case "Don't allow printing", "Allow printing":
                    selectValueFromDropdownList(
                            drdPrintPermOnDsSettings, SendFilesPage.ddElementList, feature);
                    break;
                case "Turn on Upgrade", "Turn off":
                    page().waitForLoadState(LoadState.LOAD);
                    selectValueFromDropdownList(
                            "//div[contains(text(),'Screen Shield')]/../../div[2]/pg-select",
                            SendFilesPage.ddElementList, feature);
                    waitForSeconds(1);
                    break;
                case "watermark-->on":
                    selectValueFromDropdownList(
                            "//span[contains(text(),'Dynamic')]/../../../div[2]/pg-select",
                            SendFilesPage.ddElementList, "Turn on Upgrade");
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
                default:
                    fail("Invalid option for updating file settings: " + feature);
            }
        });
    }

    public static void editNoOfRecipientLimit(String noOfRecipient) {
        clearValue("(//input[@name='printLimit'])[1]");
        inputIntoTextField("(//input[@name='printLimit'])[1]", noOfRecipient);
    }

    private static void updateTOASetting(String toaOption) {
        String txtTOAFeature = getXpathForContainsText("Terms of Access", "div");
        String drdTOA = "//div[contains(text(),'Terms of Access')]/../../div[2]/pg-select";
        String txtTOAOverview = "//div[@id='termsofaccess']/div/div[2]/div/span";
        String lnkTOAPreview = "//div[@id='termsofaccess']/div/div[2]/div/span/span";

        assertEquals(getElementText(txtTOAFeature), normalizeSpaceForString("Terms of Access  $"));
        assertTrue(isElementVisible(txtTOAFeature));
        waitUntilSelectorAppears(drdTOA);

        if (toaOption.equalsIgnoreCase("toa-->on")) {
            selectValueFromDropdownList(drdTOA, SendFilesPage.ddElementList, "Turn on Upgrade");
            waitForSelectorStateChange(drdTOA, ElementState.STABLE);
            waitUntilSelectorAppears(txtTOAOverview);
            assertEquals(getElementText(txtTOAOverview), normalizeSpaceForString(
                    "When enabled, your recipients must accept these terms of access to access files. Preview"));
            assertTrue(isElementVisible(lnkTOAPreview));
        } else {
            selectValueFromDropdownList(drdTOA,
                    SendFilesPage.ddElementList, "Turn off");
            waitForSelectorStateChange(drdTOA, ElementState.STABLE);
            assertFalse(isElementVisible(txtTOAOverview));
            assertFalse(isElementVisible(lnkTOAPreview));
        }
    }

    public static void saveSentFileSettings() {
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears(btnSubmit);
        assertTrue(isElementVisible(btnSubmit));
        clickElement(btnSubmit);
        validateToastMsg(CommonUIActions.txtCommonToastMsg, "File settings updated.");
        page().waitForLoadState(LoadState.LOAD);
    }

    public static void validateWatermarkSettingsOnSentFileSettings(DataTable dataTable) {
        waitUntilSelectorAppears(SendFilesPage.containerSendFileDynamicWatermark);
        assertTrue(isElementVisible(SendFilesPage.containerSendFileDynamicWatermark));
        dataTable.asMaps().forEach(row -> {
            for (Map.Entry<String, String> column : row.entrySet()) {
                switch (column.getKey().toLowerCase()) {
                    case "color":
                        switch (column.getValue().toLowerCase()) {
                            case "gray":
                                assertEquals(getElementText(SendFilesPage.drdDSWatermarkColor), "Gray");
                                break;
                            case "red":
                                assertEquals(getElementText(SendFilesPage.drdDSWatermarkColor), "Red");
                                break;
                            case "blue":
                                assertEquals(getElementText(SendFilesPage.drdDSWatermarkColor), "Blue");
                                break;
                            default:
                                fail("Validation failed for dw color, value is: " + column.getValue());
                        }
                        break;
                    case "position":
                        switch (column.getValue().toLowerCase()) {
                            case "tile":
                                assertEquals(getElementText(SendFilesPage.drdDSWatermarkPosition), "Tile");
                                break;
                            case "center":
                                assertEquals(getElementText(SendFilesPage.drdDSWatermarkPosition), "Center");
                                break;
                            case "top right":
                                assertEquals(getElementText(SendFilesPage.drdDSWatermarkPosition), "Top right");
                                break;
                            case "top left":
                                assertEquals(getElementText(SendFilesPage.drdDSWatermarkPosition), "Top left");
                                break;
                            default:
                                fail("Validation failed for dw position, value is: " + column.getValue());
                        }
                        break;
                    case "date and time":
                        switch (column.getValue().toLowerCase()) {
                            case "checked":
                                assertTrue(isChecked(CommonUIActions.dwDateAndTimeElement));
                                break;
                            case "unchecked":
                                waitUntilSelectorAppears("//*[@id='wk-time']");
                                boolean isDateAndTimeChecked = (boolean) page().evaluate("element => element.checked",
                                        page().querySelector("//*[@id='wk-time']"));
                                assertFalse(isDateAndTimeChecked, "date and time checkbox is not checked");
                                break;
                            default:
                                fail("Validation failed for date and time, value is: " + column.getValue());
                        }
                        break;
                    case "ip address":
                        switch (column.getValue().toLowerCase()) {
                            case "checked":
                                assertTrue(isChecked(CommonUIActions.dwIpAddElement));
                                break;
                            case "unchecked":
                                boolean isIpAddChecked = (boolean) page().evaluate("element => element.checked",
                                        page().querySelector("//*[@id='wk-ip']"));
                                assertFalse(isIpAddChecked, "IP address checkbox is not checked");
                                break;
                            default:
                                fail("Validation failed for ip address, value is: " + column.getValue());
                        }
                        break;
                    default:
                        fail("Invalid option for validating dynamic watermark settings: " + column.getKey());
                }
            }
        });
    }

    public static void validateSettingsOfSentFileOnSettingsPage(List<String> settingType) {
        String selectedTOAOption = "//div[contains(text(),'Terms of Access')]/../../div[2]/pg-select/div/div/div[2]";
        page().waitForLoadState(LoadState.LOAD);
        settingType.forEach(featureType -> {
            switch (featureType.trim()) {
                case "Only people from email domains I specify":
                    assertTrue(isElementVisible(
                            "(//div[contains(text(),'Only people from email domains I specify')])[1]"));
                    break;
                case "Anyone with the link or file (email verification)":
                    assertTrue(isElementVisible(getXpathForContainsText(
                            "Anyone with the link or file (email verification)", "div")));
                    break;
                case "vomoto.com", "gmail.com":
                    assertEquals(getElementText(
                            "//div[contains(@class,'domain-content')]/p[contains(text(),'"+featureType+"')]"), featureType);
                    break;
                case "Anyone with the link (no email verification)":
                    assertTrue(isElementVisible(
                            getXpathForContainsText(
                                    "Anyone with the link (no email verification)", "div")));
                    break;
                case "Require passcode":
                    ElementHandle chkRequirePasscode = page().querySelector("//input[@id='requestSimplePassword']");
                    assertTrue(chkRequirePasscode.isDisabled());
                    break;
                case "req additional info":
                    ElementHandle chkReqAddInfo = page().querySelector("//input[@id='reqAdditionalInfo']");
                    assertTrue(chkReqAddInfo.isDisabled());
                    break;
                case "rai->email":
                    waitUntilSelectorAppears(CommonUIActions.lblEmail);
                    assertTrue(isDisabled(CommonUIActions.lblEmail));
                    break;
                case "rai->name":
                    assertTrue(isDisabled(CommonUIActions.lblName));
                    break;
                case "rai->phone":
                    assertTrue(isDisabled(CommonUIActions.lblPhone));
                    break;
                case "rai->company":
                    assertTrue(isDisabled(CommonUIActions.lblCompany));
                    break;
                case "rai->job":
                    assertTrue(isDisabled(CommonUIActions.lblJob));
                    break;
                case "Allow downloading":
                    assertTrue(isElementVisible(getXpathForContainsText(
                            "Allow downloading", "div")));
                    break;
                case "Don't allow downloading":
                    assertTrue(isElementVisible("//div[contains(text(),'Permissions')]/../../../div/div[2]/pg-select"));
                    break;
                case "Don't allow printing":
                    assertTrue(isElementVisible(getXpathForContainsText(
                            "Don't allow printing", "div")));
                    break;
                case "Allow printing":
                    assertTrue(isElementVisible(getXpathForContainsText(
                            "Allow printing", "div")));
                    break;
                case "TOA-->On":
                    waitForSelectorStateChange(selectedTOAOption, ElementState.STABLE);
                    assertTrue(isElementVisible(selectedTOAOption));
                    waitForSeconds(1);
                    assertEquals(getElementText(selectedTOAOption), "Turn on");
                    break;
                case "TOA-->Off":
                    waitForSelectorStateChange(selectedTOAOption, ElementState.STABLE);
                    assertTrue(isElementVisible(selectedTOAOption));
                    waitForSeconds(1);
                    assertEquals(getElementText(selectedTOAOption), "Turn off");
                    break;
                case "20% viewable area":
                    assertTrue(isElementVisible(SendFilesPage.rdo20PercViewableArea));
                    break;
                case "35% viewable area":
                    assertTrue(isElementVisible(SendFilesPage.rdo35PercViewableArea));
                    break;
                case "50% viewable area":
                    assertTrue(isElementVisible(SendFilesPage.rdo50PercViewableArea));
                    break;
                case "Screen Shield-->On":
                    waitForSeconds(1);
                    assertTrue(isElementVisible(getXpathForTextEquals("Viewable Area", "p")));
                    break;
                case "Screen Shield-->Off":
                    waitForSeconds(1);
                    assertFalse(isElementVisible(getXpathForTextEquals("Viewable Area", "p")));
                    break;
                default:
                    fail("validation failed on sent file's settings page for feature: " + featureType);
            }
        });
    }

    public static void openAddRecipientInManageRec() {
        clickElement(btnCommonButtonToCreate);
        waitUntilSelectorAppears(getXpathForContainsText("Add recipient", "h4"));
        page().waitForLoadState(LoadState.LOAD);
        assertEquals(getElementText("//app-add-file-recipient-component/div/pg-tabset//pg-tab-body[1]/div[2]/div[1]"), "Copy and paste the list of recipients' emails below, separated by spaces or commas. Duplicate or malformed email addresses will be removed automatically. Copying from Word/Excel?");
    }

    private static void addOneRecipientInExistingFile(String getNewRecipientUsername, String btnAddWebLocator) {
        inputIntoTextField(SendFilesPage.txtAddRecipient, getNewRecipientUsername);
        page().keyboard().press("Enter");
        clickElement(btnAddWebLocator);
    }

    public static void addRecipientInFileThroughMSF(String userType, String btnAddWebLocator, boolean isRecipientExist, boolean isCreditsConsumed) {
        getNewRecipientUsername = FileUtils.getUserCredentialsFromJsonFile(userType).get("username");
        page().waitForLoadState(LoadState.LOAD);
        if (isRecipientExist && !isCreditsConsumed) {
            addOneRecipientInExistingFile(getNewRecipientUsername, btnAddWebLocator);
            validateToastMsg(CommonUIActions.txtCommonToastMsg, "1 recipient added");
        } else if (isRecipientExist) {
            addOneRecipientInExistingFile(getNewRecipientUsername, btnAddWebLocator);
        }
    }

    public static void verifyExcLimitErrorByAddingRecipient(String userEmail, String btnAddWebLocator, String exceededLimitNo) {
        String txtExceededRecipientLimitError = "//label[contains(@class, 'digify-error-text')]";
        addOneRecipientInExistingFile(FileUtils.getUserCredentialsFromJsonFile(userEmail).get("username"), btnAddWebLocator);
        page().waitForLoadState(LoadState.LOAD);
        waitUntilSelectorAppears(txtExceededRecipientLimitError);
        assertEquals(getElementText(txtExceededRecipientLimitError),
                "You have exceeded your specified recipient limit by " + exceededLimitNo + " email(s). To continue, please remove emails or increase the recipient limit.");
        assertFalse(page().querySelector(btnAddWebLocator).isEnabled());
        clickElement(CommonUIActions.btnDeleteDR);
    }

    public static void removeRecipientFromManageAccessPage(Boolean recipient) {
        if (recipient) {
            clickElement("//datatable-body//input[@type='checkbox']");
            clickElement("//div[@class='recipient-toolbar']//a[contains(text(),'Remove Recipient')]");
            waitUntilSelectorAppears(getXpathForContainsText("Remove", "span"));
            assertEquals(getElementText("//app-recipient-delete-modal/div[2]/p"),
                    normalizeSpaceForString("The recipient will no longer have access to this file. Their analytics will be hidden. Learn more"));
            clickElement(getXpathForContainsText("Remove", "span"));
            validateToastMsg(CommonUIActions.txtCommonToastMsg, "Recipient removed.");
        } else {
            if (!isElementVisible("//datatable-body//input[@type='checkbox']")) {
                LogUtils.infoLog("No data room or files to delete");
                return;
            }
            CommonUIActions.selectAllCheckBox("//datatable-body//input[@type='checkbox']");
            clickElement("//div[@class='recipient-toolbar']//a[contains(text(),'Remove Recipient')]");
            waitUntilSelectorAppears(getXpathForContainsText("Remove", "span"));
            assertEquals(getElementText("//app-recipient-delete-modal/div[2]/p"), normalizeSpaceForString(
                    "The recipients will no longer have access to this file. Their analytics will be hidden. Learn more"));
            clickElement(getXpathForContainsText("Remove", "span"));
            validateToastMsg(CommonUIActions.txtCommonToastMsg, "Recipients removed.");
        }
    }

    public static void validateAddRecipientModal() {
        assertTrue(isElementVisible(getXpathForContainsText("Add recipient", "h4").trim()));
        assertEquals(getElementText("//app-add-file-recipient-component/div/pg-tabset//pg-tab-body[1]/div[2]/div[1]"),
                normalizeSpaceForString("Copy and paste the list of recipients' emails below, separated by spaces or commas. Duplicate or malformed email addresses will be removed automatically.  Copying from Word/Excel?"));
    }

    public static void addEmailIdInAddRecModal(List<String> emailIds) {
        for (String emailId : emailIds) {
            inputIntoTextField(SendFilesPage.txtAddRecipient,
                    FileUtils.getUserCredentialsFromJsonFile(emailId).get("username"));
            keyPress("Enter");
        }
    }


    public static void clickAddBtnOnAddRecipientModal() {
        waitUntilSelectorAppears(btnAddBulkRecipient);
        clickElement(btnAddBulkRecipient);
    }

    public static void addRecipientsFromExcelFileInDS(Integer noOfRecipient, String file, Boolean recipientAdded) {
        validateAddRecipientModal();
        CommonUIActions.fillRecipientsFromExcel(noOfRecipient, file, txtBulkRecipient);
        clickAddBtnOnAddRecipientModal();
        if (recipientAdded) {
            validateToastMsg(CommonUIActions.txtCommonToastMsg, noOfRecipient + " recipients added");
        } else {
            LogUtils.infoLog("Recipients already exist");
        }
    }

    public static void validateRecipientEmailOnManageAccess() {
        String txtNewRecipientEmailIdOnManageAccess = "//div[@class='file-name text-truncate']/../div[2]/p[contains(text(), '" +
                getNewRecipientUsername + "')]";
        assertEquals(getElementText(txtNewRecipientEmailIdOnManageAccess), getNewRecipientUsername.trim());
    }

    public static void notifyRecipients(String notificationType, String messageType) {
        assertEquals(getElementText("//pg-tab-body[3]//div[1]/h4"),
                "Send a notification to your recipients?");
        if (messageType.equals("withMessage")) {
            inputIntoTextField("//div[@class='form-group']/textarea",
                    "Please check the file");
        } else if (messageType.equals("withoutMessage")) {
            LogUtils.infoLog("Notifying recipient without message");
        } else {
            LogUtils.infoLog("Invalid message type: " + messageType);
        }
        switch (notificationType) {
            case "send notification":
                clickElement(btnSendNotification);
                validateToastMsg(CommonUIActions.txtCommonToastMsg,
                        "Email notification sent.");
                break;
            case "skip notification":
                clickElement(btnSkipNotification);
                break;
            default:
                fail("Invalid option for notifying user: " + notificationType);
        }
    }

    public static void clickOnReplaceBtnInVersionHist() {
        waitUntilSelectorAppears(btnCommonButtonToCreate);
        assertTrue(isElementVisible(btnCommonButtonToCreate));
        waitForSeconds(1);//waiting for page to be fully loaded
        clickElement(btnCommonButtonToCreate);
    }

    public static void validateUpgradePaywall(String paywallType) {
        String headingText1 = "(//div[contains(@class, 'manage-document-tab')]//p)[1]";
        String headingText2 = "(//div[contains(@class, 'manage-document-tab')]//p)[2]";
        String headingText3 = "//div[contains(@class, 'about-doc-security')]//div[contains(@class, 'green-check-list')][1]/p";
        String headingText4 = "//div[contains(@class, 'about-doc-security')]//div[contains(@class, 'green-check-list')][2]/p";
        String headingText5 = "//div[contains(@class, 'about-doc-security')]//div[contains(@class, 'green-check-list')][3]/p";

        switch (paywallType) {
            case "contact admin to upgrade account":
                assertEquals(getElementText(headingText1), "You can't see your files here, but they're waiting for you");
                assertEquals(getElementText(headingText2), "Upgrade your account now to access your files.");
                assertEquals(getElementText(headingText3), "Manage your sent files");
                assertEquals(getElementText(headingText4), "View analytics for your sent files");
                assertEquals(getElementText(headingText5), "Allow your recipients to access your files");
                assertEquals(getElementText("//div[contains(@class, 'card-body')]//div[contains(@class, 'about-doc-security-sub-text')][1]").trim(), "Contact your team admin to upgrade");
                break;
            case "upgrade account":
                assertEquals(getElementText(headingText1), "You can't see your files here, but they're waiting for you");
                assertEquals(getElementText(headingText2), "Upgrade your account now to access your files.");
                assertEquals(getElementText(headingText3), "Manage your sent files");
                assertEquals(getElementText(headingText4), "View analytics for your sent files");
                assertEquals(getElementText(headingText5), "Allow your recipients to access your files");
                assertEquals(getElementText("//div[contains(@class,'card-body')]//div[contains(@class, 'no-doc-security')]//button").trim(), "Upgrade account");
                assertEquals(getElementText("//div[contains(@class,'card-body')]//div[contains(@class, 'no-doc-security')]//a").trim(), "Talk to sales");
                break;
            case "contact your administrator to upgrade plan":
                assertEquals(getElementText(headingText1), "Document Security");
                assertEquals(getElementText(headingText2), "Protect, track and send your important files.");
                assertEquals(getElementText("(//div[contains(@class, 'card-body')]//p)[3]"), "Control Files After Sending");
                assertEquals(getElementText("(//div[contains(@class, 'card-body')]//p)[4]"), "Restrict access, copy, downloads and print. Discourage screen capture.");
                assertEquals(getElementText("(//div[contains(@class, 'card-body')]//p)[5]"), "Simplify and Automate Processes");
                assertEquals(getElementText("(//div[contains(@class, 'card-body')]//p)[6]"), "Require recipients to agree to terms before viewing. Automatically watermark files with their email addresses.");
                assertEquals(getElementText("(//div[contains(@class, 'card-body')]//p)[7]"), "Track View Activity");
                assertEquals(getElementText("(//div[contains(@class, 'card-body')]//p)[8]"), "Detailed analytics show if your recipient viewed your files, when and for how long.");
                assertEquals(getElementText("//div[contains(@class, 'card-body')][1]//p[contains(@class, 'no-margin')]").trim(), "Contact your administrator to upgrade your plan.");
                assertEquals(getElementText("//div[contains(@class, 'card-body')][1]//a[contains(text(), 'Learn more about document security')]").trim(), "Learn more about document security");
                break;
            default:
                fail("No account upgrade paywall found on manage sent files/DR: " + paywallType);
        }
    }


    public static void selectSendFileImportOption(String importOption, boolean isSendFile) {
        switch (importOption) {
            case "import from existing files dropdown":
                clickElement("//*[@id='upload-dropdown']");
                clickElement("//*[@id='importDigify']");
                String txtImportModalHeading = getElementText("//app-import-file-from-digify-modal//h4");
                if (isSendFile) {
                    assertEquals(txtImportModalHeading, "Import files from:");
                } else {
                    assertEquals(txtImportModalHeading, "Import file from:");
                }
                break;
            case "import from existing files logo":
                clickElement("//img[@src='assets/img/digify/digify-logo.svg']");
                break;
            default:
                fail("Invalid option for selecting import option: " + importOption);
        }
    }

    public static void selectSingleFileFromIEFModal(String sourceFile, boolean isSendReplace) {
        String fileNameIP = getElementText("//app-replace-file/div[1]/h4");
        waitUntilSelectorAppears(drdUpload);
        forceClickElement(drdUpload);

        if (sourceFile.equals("sent file")) {
            waitForSelectorAppearsWithTimeout(txtSendFileHeading, 80);
            clickElement(btnSendFiles);
            String fileList = "//div[@class='file-info-data']";
            waitUntilSelectorAppears(fileList);
            replaceFileFromIEModal(sourceFile, fileNameIP, fileSelector, isSendReplace);

        } else if (sourceFile.equals("data room")) {
            selectLastDRFromList(txtDRHeading, txtDRList, false);
            page().waitForLoadState(LoadState.LOAD);
            replaceFileFromIEModal(sourceFile, fileNameIP, fileSelector, isSendReplace);
        }
        if (isElementEnabled(btnImport)) {
            clickElement(btnImport);
        } else {
            replaceFileFromIEModal(sourceFile, fileNameIP, fileSelector, isSendReplace);
            clickElement(btnImport);
        }
    }

    public static void selectSingleFileFromIEFModalError(String sourceFile) {
        waitUntilSelectorAppears(drdUpload);
        forceClickElement(drdUpload);
        if (sourceFile.equals("sent file")) {
            waitForSelectorAppearsWithTimeout(txtSendFileHeading, 80);
            clickElement(btnSendFiles);
            String fileList = "//div[@class='file-info-data']";
            waitUntilSelectorAppears(fileList);
            List<ElementHandle> dsList = page().querySelectorAll(fileSelector);
            dsList.get(1).click();
        } else if (sourceFile.equals("data room")) {
            selectLastDRFromList(txtDRHeading, txtDRList, false);
            page().waitForLoadState(LoadState.LOAD);
            List<ElementHandle> dsList = page().querySelectorAll(fileSelector);
            dsList.get(0).click();
        }
        clickElement(btnImport);
    }

    public static void selectMoreOptions(String menuOption) {
        rightClickElementUsingMouse("//*[@id='sentTable']//datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[2]/div/div/div[2]/div");
        String btnView = ".context-sub-menu:has-text('View')";
        String btnDownload = ".context-sub-menu:has-text('Download')";
        String btnMoreActions = ".context-sub-menu:has-text('More Actions')";
        switch (menuOption) {
            case "view->as recipient":
                clickElement(btnView);
                clickAndSwitchToNewTab("//a[div[contains(., 'View')]]//following-sibling::ul//a[contains(text(), 'As Recipient')]");
                break;
            case "view->as owner":
                clickElement(btnView);
                clickAndSwitchToNewTab("//a[div[contains(., 'View')]]//following-sibling::ul//a[contains(text(), 'As Owner (Original File)')]");
                break;
            case "download->as recipient":
                clickElement(btnDownload);
                clickElement("//a[div[contains(., 'Download')]]//following-sibling::ul//a[contains(text(), 'As Recipient')]");
                validateToastMsg(CommonUIActions.txtCommonToastMsg, "Preparing to download.");
                break;
            case "download->as owner":
                clickElement(btnDownload);
                clickElement("//a[div[contains(., 'Download')]]//following-sibling::ul//a[contains(text(), 'As Owner (Original File)')]");
                validateToastMsg(CommonUIActions.txtCommonToastMsg, "Preparing to download.");
                break;
            case "get file link":
                clickElement("//a[div[contains(., 'Get File Link')]]");
                page().waitForLoadState(LoadState.LOAD);
                assertEquals(getElementText("//app-copy-link//h4"), "Get link");
                CommonUIActions.manageLinkModal();
                break;
            case "delete":
                clickElement("//a[div[contains(., 'Delete')]]");
                break;
            case "more actions->rename":
                clickElement(btnMoreActions);
                clickElement("//a[div[contains(., 'More Actions')]]//following-sibling::ul//a[contains(text(), 'Rename')]");
                assertEquals(getElementText("//app-rename-file//h4"), "Rename File");
                inputIntoTextField("//input[@formcontrolname='name']", "File Rename");
                clickElement("//button[@class='btn digify-btn-blue']");
                validateToastMsg(CommonUIActions.txtCommonToastMsg, "File renamed.");
                break;
            case "more actions->replace":
                clickElement(btnMoreActions);
                clickElement("//a[div[contains(., 'More Actions')]]//following-sibling::ul//a[contains(text(), 'Replace file')]");
                break;
            case "more actions->add recipient":
                page().waitForLoadState(LoadState.LOAD);
                clickElement(btnMoreActions);
                clickElement("//a[div[contains(., 'More Actions')]]//following-sibling::ul//a[contains(text(), 'Add Recipient')]");
                break;
            case "manage recipients":
                clickElement("//a[div[contains(., 'Manage recipients')]]");
                break;
            case "version history":
                clickElement("//a[div[contains(., 'Version History')]]");
                break;
            case "settings":
                clickElement("//a[div[contains(., 'Settings')]]");
                break;
            case "analytics":
                clickElement("//a[div[contains(., 'Analytics')]]");
                page().waitForLoadState(LoadState.LOAD);
                if (isElementVisible(analyticsModalTitle)) {
                    assertEquals(getElementText(analyticsModalTitle), "Showing analytics for the latest version by default");
                    clickElement(SendFilesPage.btnClose);
                }
                checkFileAnalyticsTitle();
                break;
            default:
                fail("Context menu option not found: " + menuOption);
        }
    }

    public static void checkFileAnalyticsTitle() {
        HomePage.waitForTitleToAppear("//div[@class='page-title-text']/h4");
        waitUntilSelectorAppears(getXpathForTextEquals
                (" Analytics for \"" + SharedDM.getNewFileNameWithExtension() + "\" ", "h4"));
    }

    public static void deleteFileInMSF() {
        waitUntilSelectorAppears(getXpathForContainsText("Delete file", "*"));
        clickElement(CreateDataRoomPage.btnDeleteInWarningModal);
    }

    public static void deleteAllFilesInMSF() {
        if (!isElementVisible(CommonUIActions.chkAllCheckBox)) {
            LogUtils.infoLog("No files to delete");
            return;
        }
        CommonUIActions.selectAllCheckBox(CommonUIActions.chkAllCheckBox);
        CommonUIActions.selectOptionFromFloatingMenu("delete ds");
        deleteFileInMSF();
    }

    public static void assertRecentFileInMSF() {
        assertTrue(isElementVisible("(//div[@class='file-name text-truncate'])/a[contains(text(),'" + SharedDM.getNewFileNameWithExtension() + "')]"));
    }

    public void addFileVersionComment() {
        inputIntoTextField(txtFileVersionComment, "File V2");
    }

    public void doValidateFileVersionHistory() {
        Assert.assertTrue(isElementVisible(txtCurrentVersion));
        Assert.assertEquals(CommonUIActions.getElementText(lblFileVersionComment), "File V2");
    }

    private void selectFilterOptionInSortByMSF(String sortByFilterType) {
        String ddSortByFilter =
                getXpathForContainsText("Manage Sent File", "h4") + "/../following-sibling::node()/pg-select/div";
        String ddSelectedSortByFilter =
                getXpathForContainsText("Manage Sent File", "h4") + "/../following-sibling::node()/pg-select/div/div/div[2]";
        waitUntilSelectorAppears(ddSortByFilter);
        CommonUIActions.selectValueFromDropdownList(ddSortByFilter, SendFilesPage.ddElementList, sortByFilterType);
        waitUntilSelectorAppears(ddSelectedSortByFilter);
        assertTrue(isElementVisible(ddSelectedSortByFilter));
        assertEquals(getElementText(ddSelectedSortByFilter), "Sort by: " + sortByFilterType);
    }

    public void setFilterOptionInSortBy(String filterTypeInSortByFilter) {
        selectFilterOptionInSortByMSF(filterTypeInSortByFilter);
    }

    public void clickOnReplaceFileAndGoToReplaceNewVerPage(boolean isCreditsFullyConsumed) {
        if (!isCreditsFullyConsumed) {
            if (isElementVisible(CommonUIActions.btnCloseModal)) {
                clickElement(CommonUIActions.btnCloseModal);
            }
            waitUntilSelectorAppears(btnCommonButtonToCreate);
            clickElement(btnCommonButtonToCreate);
            waitUntilSelectorAppears("//div[@class='modal-dialog']//h4");
        } else {
            clickElement(btnCommonButtonToCreate);
            waitUntilSelectorAppears(getXpathForContainsText(" Replace file \"", "h4"));
        }
    }

    public void clickOnReplaceNewVersionBtnType(String getFileReplaceBtnType) {
        switch (getFileReplaceBtnType.toLowerCase()) {
            case "replace file and notify recipients":
                clickElement(getXpathForContainsText("Replace file & notify recipients", "*"));
                break;
            case "replace file and skip notification":
                clickElement(getXpathForContainsText("Replace file & skip notification", "*"));
                break;
            default:
                fail("No match found for button in replace new version page: " + getFileReplaceBtnType);
        }
    }

    public void clickOnReplaceBtnAndValidateErrorMsgs() {
        clickElement(getXpathForContainsText("Replace file & skip notification", "*"));
    }

    public void getOptionFromMoreFloatingMenuInDS(String optionType) {
        String lnkMoreInFloatingMenuMSF = getXpathForTextEquals(" More ", "a");
        switch (optionType.toLowerCase()) {
            case "version history":
                page().waitForLoadState(LoadState.LOAD);
                CommonUIActions.selectValueFromDropdownList
                        (lnkMoreInFloatingMenuMSF, CommonUIActions.lnkFloatingMenuList, optionType);
                waitUntilSelectorAppears(getXpathForContainsText("Version history for", "h4"));
                break;
            case "manage recipients":
                CommonUIActions.selectValueFromDropdownList
                        (lnkMoreInFloatingMenuMSF, CommonUIActions.lnkFloatingMenuList, optionType);
                waitUntilSelectorAppears(getXpathForContainsText("Manage Recipients", "h4"));
                break;
            case "analytics":
                CommonUIActions.selectValueFromDropdownList
                        (lnkMoreInFloatingMenuMSF, CommonUIActions.lnkFloatingMenuList, optionType.trim());
                page().waitForLoadState(LoadState.LOAD);
                if (isElementVisible(analyticsModalTitle)) {
                    assertEquals(getElementText(analyticsModalTitle), "Showing analytics for the latest version by default");
                    clickElement(SendFilesPage.btnClose);
                }
                checkFileAnalyticsTitle();
                break;
            case "settings":
                CommonUIActions.selectValueFromDropdownList
                        (lnkMoreInFloatingMenuMSF, CommonUIActions.lnkFloatingMenuList, optionType);
                waitUntilSelectorAppears(getXpathForContainsText("Settings for", "h4"));
                break;
            case "replace file":
                CommonUIActions.selectValueFromDropdownList
                        (lnkMoreInFloatingMenuMSF, CommonUIActions.lnkFloatingMenuList, optionType);
                page().waitForLoadState(LoadState.LOAD);
                break;
            case "rename":
                CommonUIActions.selectValueFromDropdownList
                        (lnkMoreInFloatingMenuMSF, CommonUIActions.lnkFloatingMenuList, optionType);
                waitUntilSelectorAppears(getXpathForTextEquals("Rename File", "h4"));
                break;
            default:
                fail("No match found in the more floating menu: " + optionType);
        }
    }

    public void waitUntilFileIsEncryptedInDS() {
        try {
            waitUntilSelectorAppears(getXpathForContainsText(SharedDM.getNewFileNameWithExtension(), "*"));
        } catch (TimeoutError ignore) {
        }
        while (isElementVisible(getXpathForContainsText("Encrypting...", "*"))) {
            waitForSeconds(1);
        }
    }

    public void revokeFileAccessInManageAccess() {
        waitUntilSelectorAppears(getXpathForContainsText("Manage Recipients for", "h4"));
        clickElement("//span[@class='ant-switch-handle']");
    }

    public void validateRecipientListOnManageRecipientsPage(Integer noOfRecipient) {
        page().waitForLoadState(LoadState.LOAD);
        CommonUIActions.getRecipientFromExcelAndValidateRecipientList(noOfRecipient, "//datatable-body-cell[2]//div[2]//div[2]//p[3]", String.valueOf(recipientListExcelFile));
    }

    public void clickBackArrowInManageRecipient() {
        clickElement("//div[@class='go-back-arrow cursor']/i");
        page().waitForLoadState(LoadState.LOAD);
        assertEquals(getElementText(txtHeadingManageSentFile),
                "Manage Sent Files");
    }
}

