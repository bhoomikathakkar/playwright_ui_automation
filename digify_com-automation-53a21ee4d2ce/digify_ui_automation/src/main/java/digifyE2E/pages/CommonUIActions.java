package digifyE2E.pages;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.options.ElementState;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;
import digifyE2E.pages.dataRooms.CreateDataRoomPage;
import digifyE2E.pages.documentSecurity.ManageSentFilesPage;
import digifyE2E.pages.landingPage.PricingPage;
import digifyE2E.testDataManager.SharedDM;
import digifyE2E.utils.LogUtils;
import digifyE2E.utils.RandomUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static org.testng.Assert.*;

public class CommonUIActions extends Base {

    public static final String lnkViewFloatingMenu = "//i[@class='fa-eye far']/..";
    public static final String lnkFloatingMenuList = "//div[@class='cdk-overlay-pane']/div/a/div";
    public static final String lnkGoToFileList = "//div[@id='dropdown-animated2']/a[1]/span";
    public static final String chkAllCheckBox = "//input[@class='datatable-checkbox']";
    public static final String lnkDSMoreFloatingMenu = getXpathForContainsText("More", "a");
    public static final String txtNewUserFName = "//form//input[@formcontrolname='firstName']";
    public static final String txtNewUserLName = "//form//input[@formcontrolname='lastName']";
    public static final String btnMSSignIn = "(//button[contains(@class,'btn btn-full social-login')])[2]";
    public static final String txtGPHeading = getXpathForContainsText("Granular Permissions", "h4");
    public static final String btnContinue = getXpathForContainsText("Continue", "button");
    public static final String btnCommonBtnForContinue = "//span[@class='txt']";
    public static final String btnDeleteDR = "//button[@class='btn digify-btn-secondary']";
    public static final String txtAccessDeniedErrorMsg = getXpathForContainsText(
            "Access denied", "p");
    public static final String btnVerify = "//button//btn-spinner//span[contains(text(),'Verify')]/../..";
    public static final String txtCommonToastMsg = "//pg-message-container//p";
    public static final String dwIpAddElement = "//label[@for='wk-ip']";
    public static final String dwDateAndTimeElement = "//label[@for='wk-time']";
    public static final String txtGuestTOAHeader = "(//div[@class='modal-header']/h4)[2]";
    public static final String txtToAModalBody = "(//div[@class='modal-body']/div/div/div)[1]";
    public static final String txtPremiumFeatureTitle = "//div[@class='modal-content']/app-addon-premium-modal/div/h4";
    public static final String txtPremiumFeatureContent = "//div[@class='premium-content']/p[2]";
    public static final String btnCommonSave = "(//button[contains(@class, 'btn digify-btn-blue')])[1]";
    public static final String btnCommonCancel = "(//button[@class='btn digify-btn-secondary'])[2]";
    public static final String txtCommonModalTitle = "//h4[@class='bold']";
    public static final String commonModalHeader = "//div[not(@hidden)]//div[@class='modal-header']/h4";
    public static final String commonDigifyBlueBtn = "(//button[@class='btn digify-btn-blue'])[2]";
    public static final String lnkDeleteFloatingMenu = "//i[@class='fa-trash far']/..";
    public static final String lnkLinkFloatingMenu = "//i[@class='fa-link far']/..";
    public static final String drdMoreOptionsCM = "(//div[@class='options-dropdown dropdown']/a)[1]";
    public static final String lnkDownloadFM = "//div[@class='text-right']//button/i[contains(@class,'fa-arrow-to-bottom')]/..";
    public static final String lnkLinkFM = "//div[@class='text-right']//button/i[contains(@class,'fa-link far')]/..";
    public static final String btnCommonModalBody = "//div[@class='modal-body']/p";
    public static final String btnCommonModalCancel = "//button[@class='btn digify-btn-secondary modal-cancel-button']";
    public static final String btnSubmit = "//span[@class='txt']/../..";
    public static final String closeTOAModal = "(//button[@class='close'])[2]";
    public static String btnCloseModal = "//button[@class='close']";
    public static String lblName = "//label[@for='ai-name']";
    public static String lblPhone = "//label[@for='ai-phone-number']";
    public static String lblJob = "//label[@for='ai-job-role']";
    public static String lblCompany = "//label[@for='ai-company']";
    public static String lblEmail = "//label[@for='ai-email']";
    public static String btnSearch = "//*[@class='far fa-search']/..";
    public static String lnkLinkContextMenu = "//div[@class='dropdown']/div/a/div/i[contains(@class,'fa-link far')]/../..";
    public String NewUserEmail = "autoemail" + "-" + RandomUtils.getRandomString(4, false) + "@maildrop.cc";

    public static void validateToastMsg(String locator, String msg) {
        assertEquals(getElementText(locator), msg);
        page().waitForSelector(locator).waitForElementState(ElementState.HIDDEN);
    }

    public static String getTooltipValue(String hoveredElement, String tooltipTextLocator) {
        page().hover(hoveredElement);
        waitUntilSelectorAppears(tooltipTextLocator);
        return getElementText(tooltipTextLocator);
    }


    /**
     * @param OriginalFilePath path of file to duplicate
     * @param newFileName      name of the new file to create
     * @return path of new Duplicate file
     * @throws IOException ignore
     */
    public static Path getDuplicateFileOf(String OriginalFilePath, String newFileName) throws IOException {
        Path dupPath = Paths.get("target/classes/testFiles/" + newFileName.trim() + OriginalFilePath.substring(OriginalFilePath.lastIndexOf('.')));

        Files.copy(Paths.get(OriginalFilePath), dupPath);
        SharedDM.setNewFileNameWithExtension(newFileName + OriginalFilePath.substring(OriginalFilePath.lastIndexOf('.')));
        SharedDM.setNewFilePath(dupPath.toAbsolutePath());
        return SharedDM.getNewFilePath();
    }

    /**
     * @param filePaths relative path of file to upload in send files page and in data room
     * @throws IOException ignore
     */
    public static void uploadFile(List<String> filePaths) throws IOException {
        String btnUploadFile = "//input[@type='file']";  // Example selector
        setFileName();
        if (filePaths.size() == 1) { // Single file upload
            page().setInputFiles(btnUploadFile, getDuplicateFileOf(filePaths.get(0), SharedDM.getNewFileName()));
            LogUtils.infoLog("File " + SharedDM.getNewFileName() + " uploaded successfully");
        } else if (filePaths.size() > 1) { // Multiple file upload
            Path[] paths = new Path[filePaths.size()];
            for (int i = 0; i < filePaths.size(); i++) {
                paths[i] = getDuplicateFileOf(filePaths.get(i), SharedDM.getNewFileName() + "_" + i);
            }
            page().setInputFiles(btnUploadFile, paths);
            LogUtils.infoLog("Multiple files uploaded successfully.");
        } else {
            fail("There is no file to upload");
        }
    }

    public static List<String> getFileList(String fileName, String dirPath) {
        String[] fileNames = fileName.split("\\s*,\\s*");
        List<String> fullFilePaths = new ArrayList<>();
        for (String name : fileNames) {
            fullFilePaths.add(dirPath + name);
        }
        return fullFilePaths;
    }

    public static void setFileName() {
        SharedDM.setNewFileName("AutoFileUp_" + RandomUtils.getRandomString(5, false));
    }

    public static void searchFile() {
        String btnSearchMSF = "//*[@id='search']/div/input";
        String txtSearchText = "//form[contains(@id,'search')]/div/input";
        clickElement(btnSearchMSF);
        inputIntoTextField(txtSearchText, SharedDM.getNewFileName());
        page().keyboard().press("Enter");
    }

    /**
     * Get all the values from the dropdown list and then select the specific value from the dropdown
     *
     * @param ddFeatureType   select the dropdown on a page
     * @param featureMenuList get all the values from the dropdown
     * @param ddValueToSelect Option to select in a dropdown
     */

    public static void selectValueFromDropdownList(String ddFeatureType, String featureMenuList, String ddValueToSelect) {
        clickElement(ddFeatureType);
        waitUntilSelectorAppears(featureMenuList);
        page().waitForLoadState(LoadState.DOMCONTENTLOADED);
        waitForSeconds(2); // Hard wait to allow for dropdown elements to load completely
        List<ElementHandle> elements = page().querySelectorAll(featureMenuList);
        if (elements == null || elements.isEmpty()) {
            throw new NoSuchElementException("Dropdown options not found for selector: " + featureMenuList);
        }
        for (ElementHandle ele : elements) {
            String elementText = ele.textContent().toLowerCase().trim();
            LogUtils.infoLog("Dropdown option: " + elementText);
            if (elementText.equals(ddValueToSelect.toLowerCase().trim())) {
                if (ele.isVisible()) {
                    ele.click();
                    return;
                } else {
                    LogUtils.infoLog("Matching element found but not visible: " + elementText);
                }
            }
        }
        throw new NoSuchElementException("Value not found in dropdown: " + ddValueToSelect);
    }


    public static void selectValueFromSelectDropdownList(String ddFeatureType, String ddValueToSelect) {
        clickElement(ddFeatureType);
        ElementHandle element = page().querySelector(ddFeatureType);
        element.selectOption(new SelectOption().setLabel(ddValueToSelect)
                , new ElementHandle.SelectOptionOptions().setForce(true));
    }


    //todo move send file and dr
    public static void selectOptionFromFloatingMenu(String optionType) {
        switch (optionType.toLowerCase()) {
            case "view", "view in dr":
                clickElement(lnkViewFloatingMenu);
                break;
            case "link":
                clickElement(lnkLinkFloatingMenu);
                break;
            case "more":
                clickElement(lnkDSMoreFloatingMenu);
                break;
            case "remove":
                String btnRemove = getXpathForContainsText("Remove", "button");
                waitUntilSelectorAppears(btnRemove);
                clickElement(btnRemove);
                break;
            case "remove guest":
                selectFirstCheckbox();
                clickElement(getXpathForContainsText("Remove Guest", "a"));
                waitUntilSelectorAppears("//app-data-room-delete-guest//h4");
                assertEquals(getElementText("//app-data-room-delete-guest//h4"), "Remove guest");
                clickElement("//app-data-room-delete-guest//button//span[contains(text(),'Remove')]");
                validateToastMsg(txtCommonToastMsg, "Guest removed.");
                break;
            case "move/copy":
                clickElement(getXpathForContainsText("Move/Copy", "button"));
                waitUntilSelectorAppears(getXpathForTextEquals(" Move/Copy ", "h4"));
                break;
            case "delete ds":
                clickElement(getXpathForContainsText("Delete", "*"));
                break;
            case "recipient":
                clickElement(getXpathForContainsText("Recipient", "a"));
                waitUntilSelectorAppears(getXpathForTextEquals(" Manage recipients ", "a"));
                waitForSeconds(2);
                break;
            case "preview guest access":
                clickElement(getXpathForContainsText("Preview Guest Access", "a"));
                page().waitForLoadState(LoadState.LOAD);
                waitUntilSelectorAppears(getXpathForContainsText("Preview access", "h3"));
                break;
            case "delete group":
                selectFirstCheckbox();
                clickElement(getXpathForContainsText("Delete", "a") + "[1]");
                clickElement(CreateDataRoomPage.btnDeleteInWarningModal);
                break;
            case "manage guest link":
                selectFirstCheckbox();
                clickElement(getXpathForContainsText("Manage Guest", "a"));
                break;
            case "trash":
                clickElement(lnkDeleteFloatingMenu);
                break;
            case "restore":
                clickElement(getXpathForContainsText("Restore", "a"));
                break;
            case "delete permanently":
                clickElement(getXpathForContainsText("Delete Permanently", "a"));
                break;
            default:
                fail("Option not found in the floating menu: " + optionType);
        }
    }

    public static void selectAllCheckBox(String locator) {
        if (isElementVisible(locator)) {
            waitForSelectorStateChange(locator, ElementState.STABLE);
            page().querySelectorAll(locator).forEach(ElementHandle::click);
        } else {
            LogUtils.infoLog("No list found on page to delete");
        }
    }

    public static void selectFirstCheckbox() {
        String chkFirstGlobalCheckBox = "(//input[@class='datatable-checkbox'])[1]";
        clickElement(chkFirstGlobalCheckBox);
    }

    public static void validateFileStatsHeaderItems(String analyticsStatsItemInHeader, String getCountOfItem) {
        page().waitForLoadState(LoadState.LOAD);
        switch (analyticsStatsItemInHeader.toLowerCase()) {
            case "views":
                String txtOverviewStatsViews = "(//div[@class='summary-container']/div[2]/div/div/p)[1]";
                assertTrue(isElementVisible(txtOverviewStatsViews));
                assertEquals(getElementText(txtOverviewStatsViews).toLowerCase(), "views");
                assertEquals(getElementText("(//div[@class='summary-container']/div[2]/div/div/h4)[1]"), getCountOfItem);
                break;
            case "downloads":
                String txtOverviewStatsDownloads = "(//div[@class='summary-container']/div/div[3]/div/p)[3]";
                assertTrue(isElementVisible(txtOverviewStatsDownloads));
                assertEquals(getElementText(txtOverviewStatsDownloads).toLowerCase(), "downloads");
                assertEquals(getElementText("(//div[@class='summary-container']/div/div[3]/div/h4)[3]"), getCountOfItem);
                break;
            case "visited":
                String txtOverviewStatsVisited = "(//div[@class='summary-container']/div[1]/div/div/p)[1]";
                assertTrue(isElementVisible(txtOverviewStatsVisited));
                assertEquals(getElementText(txtOverviewStatsVisited).toLowerCase(), "visited");
                assertEquals(getElementText("(//div[@class='summary-container']/div[1]/div/div/h4)[1]"), getCountOfItem);
                break;
            default:
                fail("Analytics overview module not found: " + analyticsStatsItemInHeader);
        }
    }

    public static void clickOnBtnInModal(String modalBtnType, String modalType) {
        switch (modalType.toLowerCase()) {
            case "review nested files/folders with differing permissions":
                waitUntilSelectorAppears("//h4[contains(text(),'Review nested files/folders with differing permissions')]");
                switch (modalBtnType.toLowerCase()) {
                    case "set all permissions to":
                        clickElement("//button[contains(text(),'Set all permissions to')]");
                        break;
                    case "keep different permissions":
                        clickElement("//button[contains(text(),' Keep different permissions')]");
                        break;
                    default:
                        fail("Button not found in the review nested items modal: " + modalBtnType);
                }
                break;
            default:
                fail("Match not found for the button in modal for DR and DS " + modalType);
        }
    }

    public static void clickOnActionBtnAndLink(String actionBtnType) {
        switch (actionBtnType.toLowerCase()) {
            case "save gp":
                clickElement(btnSubmit);
                page().waitForLoadState(LoadState.LOAD);
                waitUntilSelectorAppears(getXpathForContainsText("permission updated", "p"));
                break;
            case "close gp":
                page().waitForLoadState(LoadState.LOAD);
                clickElement(btnDeleteDR);
                if (isElementVisible("//div[@class='modal-content']/div/h4")) {
                    clickElement("(//div[@class='modal-body']/div/div/button)[1]");
                    page().waitForLoadState(LoadState.LOAD);
                    clickElement(btnDeleteDR);
                }
                page().waitForLoadState(LoadState.LOAD);
                break;
            case "reset gp":
                clickElement("//button[@class='btn digify-btn-secondary']/../a");
                break;
            case "edit gp":
                clickElement("//span[text()='Manage Granular Permissions']/..");
                waitUntilSelectorAppears(txtGPHeading);
                break;
            case "cancel gp":
                clickElement(btnDeleteDR);
                break;
            case "cancel":
                clickElement(getXpathForContainsText("Cancel", "span"));
                break;
            case "cancel button": //When there is no change in GP screen, click on gp cancel
                page().waitForLoadState(LoadState.LOAD);
                clickElement(btnCommonCancel);
                if (isElementVisible("//div[@class='modal-content']/div/h4")) {
                    clickElement("(//div[@class='modal-body']/div/div/button)[1]");
                    page().waitForLoadState(LoadState.LOAD);
                    clickElement(btnDeleteDR);
                }
                page().waitForLoadState(LoadState.LOAD);
                break;
            default:
                fail("Match not found for the button: " + actionBtnType);
        }
    }

    public static void selectDeleteInDeleteItemModal() {
        clickElement(CreateDataRoomPage.chkConfirmationDeleteDR);
        clickElement(CreateDataRoomPage.btnDeleteInWarningModal);
    }

    /**
     * return formated future data use to check expiry of guest based on dropdown value
     * The method is used thorughout app , for disband flow we need a date without time so updated method accordingly
     * based on user input the method will return date format
     *
     * @param days
     * @return
     */
    public static String getFutureDate(String days, boolean includeTime) {
        int daysToAdd = Integer.parseInt(days.replaceAll("\\D", ""));
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime futureDate = today.plusDays(daysToAdd);
        DateTimeFormatter formatter;
        if (includeTime) {
            formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a");
        } else {
            formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        }
        String formattedDate = futureDate.format(formatter);
        formattedDate = formattedDate.replace("am", "AM").replace("pm", "PM");
        // Trim the month abbreviation to the first 3 characters
        String[] parts = formattedDate.split(" ", 2); // Split into [month, rest of the date]
        String month = parts[0].substring(0, 3); // Trim month to first 3 characters
        formattedDate = month + " " + parts[1];  // Rebuild the date string
        LogUtils.infoLog("Expected date =" + formattedDate);
        return formattedDate;
    }

    /**
     * select future date based on number of days provided by user from calendar
     *
     * @param days
     */

    public static void selectFutureDateFromCalendar(String days) {
        int daysToAdd = Integer.parseInt(days.replaceAll("\\D", ""));
        LocalDateTime currentDateAndTime = LocalDateTime.now();
        LocalDateTime futureDate = currentDateAndTime.plusDays(daysToAdd);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = futureDate.format(formatter);
        int futureMonth = futureDate.getMonthValue();
        String selectMonth = "//div[1]/pg-datepicker-scroller/div/div/div/a[" + futureMonth + "]";
        LogUtils.infoLog("selected month is -" + selectMonth);

        String date = String.format("td[title='%s']", formattedDate);
        List<ElementHandle> titleElements = page().querySelectorAll(date);
        for (ElementHandle titleElement : titleElements) {
            String tdTextContent = titleElement.textContent();
            String tdValue = formattedDate.substring(formattedDate.length() - 2);
            int dayOfMonth = Integer.parseInt(tdValue);

            if (dayOfMonth < 10 && tdValue.startsWith("0")) {
                dayOfMonth = Integer.parseInt(tdValue.substring(1));
            }
            if (tdTextContent.equals(String.valueOf(dayOfMonth))) {
                titleElement.click();
                break;
            }
        }
    }

    /**
     * select hours based on given input
     *
     * @param timeToAdd
     */
    public static void selectTimeFromCalendar(String timeToAdd, String drdTimePickerLocator) {
        clickElement(drdTimePickerLocator);
        List<ElementHandle> hoursMins = page().querySelectorAll(".select-panel > ul:nth-child(1) > li");
        // Select hour from first 24 elements
        for (int i = 0; i < 25; i++) {
            ElementHandle hourElement = hoursMins.get(i);
            if (hourElement.textContent().trim().equals(String.valueOf(Integer.parseInt(timeToAdd.replaceAll("\\D", ""))))) {
                hourElement.scrollIntoViewIfNeeded();
                hourElement.dispatchEvent("click");
                break;
            }
        }
    }

    public static void pageReload() {
        page().reload();
        page().waitForLoadState(LoadState.LOAD);
    }

    public static void agreeToA() {
        clickElement("//input[@type='checkbox']//following::label[@class='checkbox-label']");
        clickElement(btnCommonBtnForContinue);
    }

    public static List<String> getCellValuesFromExcel(int numberOfEmails, String filePath) {
        List<String> emails = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            int rowNum = 1;
            int emailsRead = 0;
            while (rowNum <= sheet.getLastRowNum() && emailsRead < numberOfEmails) {
                Row row = sheet.getRow(rowNum);
                if (row != null) {
                    Cell cell = row.getCell(0);
                    if (cell != null && cell.getCellType() == CellType.STRING) {
                        emails.add(cell.getStringCellValue());
                        emailsRead++;
                    }
                }
                rowNum++;
            }
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return emails;
    }

    public static void fillRecipientsFromExcel(Integer noOfRecipient, String file, String inputFieldWebLocator) {
        if (noOfRecipient == null || noOfRecipient <= 0) {
            throw new IllegalArgumentException("Number of recipients must be a positive integer.");
        }
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty.");
        }
        if (inputFieldWebLocator == null || inputFieldWebLocator.isEmpty()) {
            throw new IllegalArgumentException("Input field web locator cannot be null or empty.");
        }

        File filePath = new File("src/main/resources/testUserDetails/" + file);
        if (!filePath.exists() || !filePath.isFile()) {
            throw new IllegalArgumentException("Invalid file path: " + filePath.getAbsolutePath());
        }

        try {
            List<String> recipientEmailList = getCellValuesFromExcel(noOfRecipient, filePath.getAbsolutePath());
            if (recipientEmailList.isEmpty()) {
                throw new IllegalArgumentException("No recipient emails found in the file.");
            }

            for (String email : recipientEmailList) {
                inputIntoTextField(inputFieldWebLocator, email);
                //added hard wait to reflect the user email id on UI
                waitForSeconds(1);
                keyPress("Enter");
                waitForSeconds(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * fetch recipient from respective page and validate with excel file added recipient
     *
     * @param noOfRecipient
     * @param webLocator
     * @param filePath
     */
    public static void getRecipientFromExcelAndValidateRecipientList(Integer noOfRecipient, String webLocator, String filePath) {
        List<String> recipientEmailList = getCellValuesFromExcel(noOfRecipient, String.valueOf(filePath));
        List<ElementHandle> recipientListOnPage = page().querySelectorAll(webLocator);
        IntStream.range(0, recipientListOnPage.size()).forEach(i -> assertEquals(recipientEmailList.get(i), recipientListOnPage.get(i).textContent().trim()));
    }


    /**
     * Use to validate guest already exists modal for DS and DR
     *
     * @param noOfUser
     * @param userType
     */
    public static void validateUserAlreadyExistsModal(Integer noOfUser, String userType) {
        String lblGuestHeadingText = "//pg-tab-body[2]/div[2]/div[1]/h4";
        String lblGuestSubText = "//pg-tabset/div/div/div/pg-tab-body[2]/div[2]/div[2]";
        String btnGotItBulkModal = "//app-bulk-invite-recipient//pg-tabset//pg-tab-body[2]/div[2]/div[4]/button";
        String lblRecipientHeadingText = "//pg-tabset/div/div/div/pg-tab-body[2]/div[2]/div[1]/div";
        String btnCloseBulkModal = getXpathForContainsText("Close", "button");
        switch (userType) {
            case "guest":
                assertEquals(getElementText(lblGuestHeadingText), noOfUser + " guest already exist");
                assertEquals(getElementText(lblGuestSubText), "The following guest will not be added again.");
                page().waitForLoadState(LoadState.LOAD);
                getRecipientFromExcelAndValidateRecipientList(noOfUser, "//div[@id='copy-link-pre'][2]/p", String.valueOf(ManageSentFilesPage.recipientListExcelFile));
                clickElement(btnGotItBulkModal);
                break;
            case "recipient":
                assertEquals(getElementText(lblRecipientHeadingText), noOfUser + " recipient already exist");
                assertEquals(getElementText(lblGuestSubText), "The following recipient will not be added again.");
                page().waitForLoadState(LoadState.LOAD);
                getRecipientFromExcelAndValidateRecipientList(noOfUser, "//*[@id='copy-link-pre']/p", String.valueOf(ManageSentFilesPage.recipientListExcelFile));
                clickElement(btnCloseBulkModal);
                break;
            case "guests":
                assertEquals(getElementText(lblGuestHeadingText), noOfUser + " guests already exist");
                assertEquals(getElementText(lblGuestSubText), "The following guests will not be added again.");
                page().waitForLoadState(LoadState.LOAD);
                getRecipientFromExcelAndValidateRecipientList(noOfUser, "//div[@id='copy-link-pre'][2]/p", String.valueOf(ManageSentFilesPage.recipientListExcelFile));
                clickElement(btnGotItBulkModal);
                break;
            case "recipients":
                assertEquals(getElementText(lblRecipientHeadingText), noOfUser + " recipients already exists");
                assertEquals(getElementText(lblGuestSubText), "The following recipients will not be added again.");
                page().waitForLoadState(LoadState.LOAD);
                getRecipientFromExcelAndValidateRecipientList(noOfUser, "//*[@id='copy-link-pre']/p", String.valueOf(ManageSentFilesPage.recipientListExcelFile));
                clickElement(btnCloseBulkModal);
                break;
            case "existing recipient":
                assertEquals(getElementText(lblRecipientHeadingText), noOfUser + " recipient already exist");
                assertEquals(getElementText(lblGuestSubText), "The following recipient will not be added again.");
                page().waitForLoadState(LoadState.LOAD);
                assertTrue(isElementVisible("//span[@class='important-message text-left']"));
                clickElement(btnContinue);
                break;
            default:
                fail("user already exists modal is not found: " + userType);
        }
    }

    public static void validateEnforceVerificationTooltip() {
        assertEquals(getTooltipValue("//label[contains(text(),'Enforce email verification for every view session')]/i",
                        "//label[contains(text(),'Enforce email verification for every view session')]/ngb-tooltip-window/div[2]"),
                "Prevent account sharing by requiring an extra one-time verification code for every viewing session for all logged in users.");
    }

    public static void validatePremiumUpgradeModalContent() {
        assertTrue(isElementVisible(CommonUIActions.txtPremiumFeatureTitle));
        assertTrue(isElementVisible(CommonUIActions.txtPremiumFeatureContent));
        assertEquals(getElementText(
                        CommonUIActions.txtPremiumFeatureContent),
                "Next, you will see the adjusted full subscription fee for your plan. However, only pro-rated charges will be billed today after checkout.");
        assertTrue(isElementVisible(
                "//span[contains(@class, 'txt') and text()='Continue to checkout ']"));
    }

    /**
     * Validate all paywall, when storage is fully consumed
     *
     * @param paywallType
     * @param isTeamAdmin
     */
    public static void validatePaywall(String paywallType, boolean isTeamAdmin) {
        String txtNoDRQuotaLeft = "(//div[@class='text-center']/div/div/span)[1]";
        String txtAddMoreDrQuota = "(//div[@class='text-center']/div/div/span)[2]";
        switch (paywallType.toLowerCase()) {
            case "no data room quota left":
                assertTrue(isElementVisible(txtNoDRQuotaLeft));
                assertEquals(getElementText(txtNoDRQuotaLeft),
                        "No data room quota left");
                if (isTeamAdmin) {
                    assertEquals(getElementText(txtAddMoreDrQuota),
                            "To create new data rooms, please add more data room quota to your plan");
                    assertTrue(isElementVisible(
                            "//div[@class='text-center']/div/div[2]/button[2]"));//add quota button
                } else {
                    assertEquals(getElementText(txtAddMoreDrQuota),
                            "To create new data rooms, please contact your admin to add more data room quota to your plan.");
                    assertFalse(isElementVisible(
                            "//div[@class='text-center']/div/div[2]/button[2]"));
                }
                break;
            case "not enough dr guest quota":
                assertTrue(isElementVisible("//div[@class='premium-img']/../h4"));
                assertEquals(getElementText("//div[@class='premium-img']/../h4"),
                        "Not enough data room guest quota");
                assertEquals(getElementText("//p[@class='premium-txt']"),
                        "To add these guests, please add more data room guest quota to your plan.");
                break;
            case "clone-->no data room quota left":
                assertEquals(getElementText(CommonUIActions.commonModalHeader), "No data room quota left");
                assertEquals(getElementText("//div[@class='modal-header']/../div[2]/div/div"),
                        "To clone new data rooms, please add more data room quota to your plan.");
                break;
            case "no credits left to send file":
                assertEquals(getElementText(
                                getXpathForContainsText("No credits left to send files", "span")),
                        "No credits left to send files");
                if (isTeamAdmin) {
                    assertEquals(getElementText(
                                    getXpathForContainsText("To continue sending files, please add more credits to your plan.",
                                            "span")),
                            "To continue sending files, please add more credits to your plan.");
                    assertTrue(isElementVisible(getXpathForContainsText(
                            "Learn more about credits", "a")));
                } else {
                    assertEquals(getElementText(
                                    getXpathForContainsText("No credits left to send files", "span")),
                            "No credits left to send files");
                    assertEquals(getElementText("(//div[contains(@class, 'clearfix')])[3]/div/div/span[2]"),
                            "To continue sending files, please contact your admin to add more credits to your plan.");
                }
                break;
            case "no credits left to replace file":
                assertEquals(getElementText(
                                getXpathForContainsText("No credits left to replace file", "span")),
                        "No credits left to replace file");
                assertEquals(getElementText(
                                getXpathForContainsText("To create a new version, please add more credits to your plan.",
                                        "span")),
                        "To create a new version, please add more credits to your plan.");
                assertTrue(isElementVisible(
                        getXpathForContainsText("Learn more about credits", "a")));
                break;
            case "no encrypted storage left":
                assertEquals(getElementText(
                                getXpathForContainsText("No encrypted storage left", "span")),
                        "No encrypted storage left");
                if (isTeamAdmin) {
                    assertEquals(getElementText(
                                    getXpathForContainsText("To send files, please add more encrypted storage quota to your plan.",
                                            "span")),
                            "To send files, please add more encrypted storage quota to your plan.");
                } else {
                    assertEquals(getElementText(
                                    getXpathForContainsText(
                                            "To send files, please contact your admin to add more encrypted storage quota to your plan.",
                                            "span")),
                            "To send files, please contact your admin to add more encrypted storage quota to your plan.");
                }
                break;
            default:
                fail("Paywall type does not matched: " + paywallType);
        }
    }


    public static void clickStorageExceedBtnAndNavigateToPricingPage(String quotaBtnType) {
        switch (quotaBtnType.toLowerCase()) {
            case "add data room guest quota":
                clickAndSwitchToNewTab("(//span[@class='txt']/../..)[3]");
                waitForURLContaining("/pricing/#/review");
                PricingPage.navigateToPricingCheckoutPageAsExistingUser();
                break;
            case "add data room quota":
                clickElement("//div[@class='text-center']/div/div[2]/button[2]");
                waitForURLContaining("/pricing/#/review");
                PricingPage.navigateToPricingCheckoutPageAsExistingUser();
                break;
            case "clone-->no data room quota left":
                clickElement("//div[@class='modal-header']/../div[2]/div[2]/a[3]");
                waitForURLContaining("/pricing/#/review");
                break;
            case "add document security credits":
                clickAndSwitchToNewTab(getXpathForContainsText("Add credits", "button"));
                waitForURLContaining("/pricing/#/review");
                PricingPage.navigateToPricingCheckoutPageAsExistingUser();
                break;
            case "add credits":
                clickAndSwitchToNewTab(getXpathForContainsText("Add credits", "button"));
                waitForURLContaining("/pricing/#/review");
                break;
            case "add quota":
                clickAndSwitchToNewTab(getXpathForContainsText("Add quota", "button"));
                break;
            default:
                fail("Storage exceed button does not matched: " + quotaBtnType);
        }
    }

    public static void clickOnRestoreIconOnVH() {
        assertTrue(isElementVisible("(//i[@class='far fa-redo']/..)[2]"));
        clickElement("(//i[@class='far fa-redo']/..)[2]");
    }

    public static void selectRestoreBtnAndValidateToast() {
        clickElement(CommonUIActions.commonDigifyBlueBtn);
        waitUntilSelectorAppears(CommonUIActions.txtCommonToastMsg);
        page().waitForLoadState(LoadState.DOMCONTENTLOADED);
        validateToastMsg(CommonUIActions.txtCommonToastMsg, "File restored.");
    }

    public static void validateDisbandErrorModalOnLoginPage(String modalType, String userType) {

        switch (modalType) {
            case "Member account schedule for deletion":
                String HeadingText = "//div[@class='tab-content-wrapper']/div/pg-tab-body[5]";
                switch (userType) {
                    case "proDisbandUser", "proWithTeamDisbandAdmin", "teamWithAdminDataDisband" ->
                            assertEquals(getElementText(HeadingText),
                                    "Member accounts scheduled for deletion You've asked us to delete the \"pro's team\" team and its member accounts from Digify's servers. The deletion is scheduled for " + getFutureDate("30", false) + " . A confirmation email has been sent to all members.");
                    case "teamDisbandUser" -> assertEquals(getElementText(HeadingText),
                            "Member accounts scheduled for deletion You've asked us to delete the \"team's team\" team and its member accounts from Digify's servers. The deletion is scheduled for " + getFutureDate("30", false) + " . A confirmation email has been sent to all members.");
                    case "teamWithTeamDisbandAdmin" -> assertEquals(getElementText(HeadingText),
                            "Member accounts scheduled for deletion You've asked us to delete the \"team auto's team\" team and its member accounts from Digify's servers. The deletion is scheduled for " + getFutureDate("30", false) + " . A confirmation email has been sent to all members.");
                    case "proWithTeamDataDisbandAdmin" -> assertEquals(getElementText(HeadingText),
                            "Member accounts scheduled for deletion You've asked us to delete the \"pro auto's team\" team and its member accounts from Digify's servers. The deletion is scheduled for " + getFutureDate("30", false) + " . A confirmation email has been sent to all members.");
                    case "teamWithTeamDataDisbandAdmin" -> assertEquals(getElementText(HeadingText),
                            "Member accounts scheduled for deletion You've asked us to delete the \"team plan's team\" team and its member accounts from Digify's servers. The deletion is scheduled for " + getFutureDate("30", false) + " . A confirmation email has been sent to all members.");
                    default -> fail("user not found for disband error modal: " + userType);
                }
                page().reload();
                break;
            case "Restore team":
                assertEquals(getElementText("//app-login//div[@class='modal-body']//p/div[1]"), "Your team is scheduled for deletion on " + getFutureDate("30", false));
                assertEquals(getElementText("//app-login/div[8]//div[2]/div/div[2]/p"), "You can still restore your team and its member accounts before the deletion date.");
                clickElement("//app-login/div[8]//div[2]/div/div[3]/button");
                break;
            case "Confirm account restoration":
                waitForSeconds(3);
                page().waitForLoadState(LoadState.LOAD);
                assertEquals(getElementText("//app-login/div[8]//div[2]/div/div[1]/p"), "Confirm account restoration");
                assertEquals(getElementText("//app-login/div[8]//div[2]/div/div[2]/p"), "Please click on the link in the email we've just sent you to restore your account.");
                break;
            case "account schedule for deletion by team admin":
                assertEquals(getElementText("//app-login/div[8]//div[2]/div/div[1]/p/div[2]"), "Your account has been scheduled for deletion by your team admin");
                assertEquals(getElementText("//app-login/div[8]//div[2]/div/div[2]/p"), "If you feel that this is an error, please contact your team admin to restore your account.");
                clickElement("//app-login/div[8]//div[2]/div/div[4]/a");
                break;
            default:
                fail("No relevant disband/restore modal found: " + modalType);

        }

    }

    public static void confirmTeamRestorationEmail(String user) {
        visitAndVerify(MailDropEmailService.getAccountRestoreLink(user));
    }

    public static void visitAndVerify(String urlToOpen) {
        visit(urlToOpen, false);
        page().bringToFront();
        try {
            assertEquals(getElementText("//app-account-restore/div/div[3]//div[1]/div[2]/h4"), "Your team and its member accounts have been restored");
            assertEquals(getElementText("//app-account-restore/div/div[3]//div[1]/div[3]/p"), "Please sign in to continue.");
            clickElement("//app-account-restore/div/div[3]//div[1]/div[4]/button");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void validateAndCloseDownloadPopupBlockerModal() {
        assertEquals(getElementText("//*[@id='downloadBrowserBlockerModal']/h4"), "Find your downloaded file");
        assertEquals(getElementText("//app-download-browser-blocker/div[2]/p"), normalizeSpaceForString(
                "Look for your file in your Downloads folder. If you don't see it, check if your browser blocked the download."));
        assertEquals(getElementText("//div[@class='modal-body']/ul/li[1]"),
                normalizeSpaceForString("In the address bar, click the \"Pop-up blocked\" icon."));
        assertEquals(getElementText("//div[@class='modal-body']/ul/li[2]"), normalizeSpaceForString(
                "Select \"Always allow pop-ups and redirects\" for this site and click \"Done\"."));
        assertEquals(getElementText("//div[@class='modal-body']/ul/li[3]/span"), normalizeSpaceForString("Try downloading your file again."));
        assertTrue(isElementVisible("//div[@class='download-btn']//button"));
        clickElement("//div[@id='downloadBrowserBlockerModal']/button");
    }

    public static void validateHiddenOptions() {
        assertTrue(isElementHidden("//div[@id='sen_sel']//img[@src='assets/img/digify/digify-logo.svg']"));
        assertTrue(isElementHidden("//div[@id='sen_sel']//img[@src='assets/img/digify/icon-dropbox-upload.svg']"));
        assertTrue(isElementHidden("//div[@id='sen_sel']//img[@src='assets/img/digify/icon-gdrive-upload.svg']"));
        assertTrue(isElementHidden("//div[@id='sen_sel']//img[@src='assets/img/digify/icon-onedrive-upload.svg']"));
        assertTrue(isElementHidden("//div[@id='sen_sel']//img[@src='assets/img/digify/icon-box-upload.svg']"));
    }

    public static void selectCheckboxPerUserInputIEDigifyModal(String webLocator, int noOfItem) {
        waitUntilSelectorAppears(webLocator);
        List<ElementHandle> checkboxes = page().querySelectorAll(webLocator);
        if (noOfItem <= 0) {
            System.out.println("Invalid input! Please enter a valid number.");
            return;
        } else if (noOfItem > checkboxes.size()) {
            System.out.printf("You can only select up to %d files.%n", checkboxes.size());
            return;
        }
        for (int i = 0; i < noOfItem; i++) { // Select the specified number of checkboxes
            checkboxes.get(i).click();
        }

    }

    public static void dismissImportFromExistingFileModal() {
        clickElement("//button[@class='btn digify-btn-secondary secondary-btn-lg']");
    }

    public static void validateWarningModals(String modalType) {
        String txtWarningModalBodyTitle = "(//div[@class='warning-modal modal-body text-center']/div/div)[1]";
        switch (modalType.toLowerCase()) {
            case "excel warning":
                waitUntilSelectorAppears(CommonUIActions.commonModalHeader);
                assertEquals(getElementText(CommonUIActions.commonModalHeader), "Watermarks for Excel will vary");
                assertEquals(getElementText(txtWarningModalBodyTitle),
                        "When printed or downloaded, the file will be converted to PDF and your selected watermark position will apply. In the file viewer, the watermark is centered for enhanced security. Learn more");
                break;
            case "anyone with the link(no email)+toa":
                waitUntilSelectorAppears(CommonUIActions.commonModalHeader);
                assertEquals(getElementText(CommonUIActions.commonModalHeader),
                        "Anyone with the link (no email verification) + Terms of Access");
                assertEquals(getElementText(txtWarningModalBodyTitle),
                        "As your recipients' identities are unverified, the Terms of Access feature will work differently. Learn more");
                break;
            case "anyone with the link(no email)+expiry":
                waitUntilSelectorAppears(CommonUIActions.commonModalHeader);
                assertEquals(getElementText(CommonUIActions.commonModalHeader),
                        "Potential issue with \"Expire After Opening\" setting");
                assertEquals(getElementText(txtWarningModalBodyTitle),
                        "You're sending a file to multiple recipients without email verification. After the file expires for the first recipient, it will no longer be accessible by other recipients. Learn more");
                break;
            case "set up group permissions later":
                waitUntilSelectorAppears(CommonUIActions.commonModalHeader);
                assertEquals(getElementText(CommonUIActions.commonModalHeader),
                        "Please set up Group Permissions later");
                assertEquals(getElementText(txtWarningModalBodyTitle),
                        "After you've created the data room and defined Group Permissions, you can change the default permission in Settings. Choose any other default permission for now. Learn more");
                break;
            default:
                fail("Warning modal does not matched with the option: " + modalType);
        }
    }

    public static void visitDRLink() {
        visit(SharedDM.getSentDataRoomLink(), false);
    }

    public static void manageLinkModal() {
        assertFalse(getElementText("//*[@id='copy-link-pre']/ng-scrollbar/div/p/span").isEmpty());
        clickElement("//app-copy-link//i");
        validateToastMsg(CommonUIActions.txtCommonToastMsg, "Link copied.");
        clickElement("//app-copy-link//button");
    }

    public static void validateAndEnableEnforceEmailVerification() {
        String chkEnforceEmailVerification = "//label[@for='verification']";
        waitUntilSelectorAppears(getXpathForContainsText(
                "Enforce email verification for every view session", "label"));
        waitForSeconds(1);
        assertTrue(isElementVisible(chkEnforceEmailVerification));
        clickElement(chkEnforceEmailVerification);
    }
}
