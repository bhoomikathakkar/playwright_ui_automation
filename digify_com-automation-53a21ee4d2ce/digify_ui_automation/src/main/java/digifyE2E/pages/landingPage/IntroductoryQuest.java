package digifyE2E.pages.landingPage;

import com.microsoft.playwright.Frame;
import digifyE2E.pages.AdminSettings.AdminSettingsPage;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.pages.landingPage.constants.*;
import digifyE2E.utils.LogUtils;

import static org.testng.Assert.*;

public class IntroductoryQuest extends CommonUIActions {
    public static final String btnViewQuest = "(//button[@class='btn digify-default-btn'])[1]";
    public static final String btnMarkAsComplete = "(//button[@class='btn digify-default-btn'])[2]";
    public static final String btnContinueExp = "//div[@class='ob-container']/div/button[1]";
    public static final String txtNoOfSteps = "((//div[@data-testid='checklist-task-list']/div/div[1])[2]/div/div[1])[1]";
    public static final String containerQuestBanner = "//div[contains(@class,'oflow-container')]";
    public static final String txtQuestBannerInfo = "//div[@class='oflow-info']";
    public static final String containerFeatureMarker = "//div[@data-testid='positioner-mask']";
    private static final String txtRemainingTask = "//div[@data-testid='checklist-remaining-time']/p";
    public final String modalTourStepIntercom = "(//div[contains(@class,'intercom-tour-step')])[1]";
    public final String tglCardHeader = "(//div[@data-testid='collapsible-card-header'])[1]";
    public final String txtInfo1 = "(//div[contains(@class,'intercom-tour-step intercom-tour-step-pointer intercom')]//div[contains(@class,'intercom-block-paragraph')])[1]";
    public final String txtInfo2 = "(//div[contains(@class,'intercom-tour-step intercom-tour-step-pointer intercom')]//div[contains(@class,'intercom-block-paragraph')])[2]/b";
    public final String txtTourSteps = "(//div[contains(@class,'intercom-authored-container intercom')]/div[3]/div)[1]";
    public final String btnToProceed = "//button[@data-testid='tour-next-step-button']";
    public final String btnShowMeHow = "//button[@data-testid='checklist-task-action-button']";

    public static void verifyIntroBannerHidden() {
        assertFalse(isElementVisible(btnViewQuest));
        assertFalse(isElementVisible(txtQuestBannerInfo));
    }

    public static void selectViewQuest() {
        clickElement(btnViewQuest);
        waitForSeconds(3);
    }

    public static void selectMarkAsComplete() {
        clickElement(btnMarkAsComplete);
        waitForSeconds(2);
    }

    /**
     * Validates the details of 'quest' based on the user role and task progress.
     */
    public static void validateViewQuestDetails(Boolean isTeamAdmin, Boolean isTaskCompleted, int stepsDone, int totalSteps) {
        // Validate common elements
        validateTextInFrame("//div[@data-testid='checklist-task-list']/div/h2", "Show us you're a pro!");
        validateTextInFrame("(//div[@data-testid='checklist-task-list']/div/div[2])[1]/div/div[1]", null);
        validateTextInFrame("(//div[@data-testid='checklist-task-list']/div/div[2])[1]/div/div[2]", null);
        validateTextInFrame("//div[@data-testid='checklist-progress-details']", null);
        validateTextInFrame(txtRemainingTask, null);
        validateTextInFrame(txtNoOfSteps, null);

        // Dynamic text content based on user type and task completion
        String questTitle = "(//div[@data-testid='checklist-task-list']/div/div[1])[1]/div/div";
        String questUserInfo = isTeamAdmin
                ? "Complete these challenges to secure your files and set up your team on Digify."
                : "Complete these challenges to secure your files.";
        String stepsText = getStepsText(isTeamAdmin, isTaskCompleted, stepsDone, totalSteps);

        validateTextInFrame(questTitle, questUserInfo);
        validateTextInFrame(txtNoOfSteps, stepsText);
    }

    /**
     * Generates the text for steps completed or remaining based on user role and task state.
     */
    private static String getStepsText(Boolean isTeamAdmin, Boolean isTaskCompleted, int stepsDone, int totalSteps) {
        if (isTaskCompleted) {
            // Return the progress if the task is completed (e.g: "1 of 3 done")
            return stepsDone + " of " + totalSteps + " done";
        } else {
            // Return remaining steps if the task is not completed (e.g: "3 steps")
            return isTeamAdmin ? "3 steps" : "2 steps";
        }
    }

    /**
     * Retrieves a browser frame by its name.
     */
    public static Frame getFrameByName(String frameName) {
        return page().frames().stream()
                .filter(f -> f.name().equals(frameName))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Frame not found: " + frameName));
    }

    /**
     * Validates the visibility and content of an element inside a frame based on XPath.
     */
    public static void validateTextInFrame(String xpath, String expectedText) {
        Frame frame = getFrameWithElement(xpath);// Auto-detect frame and verify text
        verifyTextByXPath(frame, xpath, expectedText);
    }

    /**
     * Verifies that a specific element in a frame contains the expected text content.
     */
    public static void verifyTextByXPath(Frame frame, String xpath, String expectedText) {
        boolean isVisible = (boolean) frame.evaluate("(xpath) => {" +
                "const el = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;" +
                "return el && !!(el.offsetWidth || el.offsetHeight || el.getClientRects().length);" +
                "}", xpath);

        assertTrue(isVisible, "Element not visible: " + xpath);
        LogUtils.infoLog("Element is visible: " + xpath);
        if (expectedText == null) {
            LogUtils.infoLog("Skipping text match (expected text is null)\n");
            return;
        }
        String actualText = (String) frame.evaluate("(xpath) => {" +
                "const el = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;" +
                "return el ? el.textContent.trim() : null;" +
                "}", xpath);

        assertNotNull(actualText, "Text is null for XPath: " + xpath);
        assertFalse(actualText.isEmpty(), "Text is empty for XPath: " + xpath);
        assertTrue(actualText.contains(expectedText),
                "Text mismatch.\nExpected: " + expectedText + "\nActual: " + actualText);
        LogUtils.infoLog("Text matched: " + expectedText + "\n");
    }

    public static void closeIntercomChallengeModal() {
        clickElementInFrame("//span[@aria-label='Close']", null);
    }

    /**
     * Clicks an element within a frame and optionally checks whether it is expanded.
     */
    public static void clickElementInFrame(String xpath, String xpathToCheckExpanded) {
        Frame frame = getFrameWithElement(xpath);
        clickByXPath(frame, xpath, xpathToCheckExpanded);// Perform the click action
    }

    /**
     * Performs a click on an element inside a frame and handles expansion behavior if applicable.
     */
    private static void clickByXPath(Frame frame, String xpathToClick, String xpathToCheckExpanded) {
        boolean isVisible = (boolean) frame.evaluate("(xpath) => {" +
                "const el = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;" +
                "return el && !!(el.offsetWidth || el.offsetHeight || el.getClientRects().length);" +
                "}", xpathToClick);
        assertTrue(isVisible, "Element not visible: " + xpathToClick);
        frame.waitForSelector(xpathToClick);
        if (xpathToCheckExpanded != null && !xpathToCheckExpanded.isEmpty()) {
            boolean isExpanded = (boolean) frame.evaluate("(xpath) => {" +
                    "const el = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;" +
                    "return el ? el.getAttribute('aria-expanded') === 'true' : false;" +
                    "}", xpathToCheckExpanded);
            if (!isExpanded) {
                frame.locator(xpathToClick).click();
                LogUtils.infoLog("Clicked element to expand: " + xpathToClick);
            } else {
                LogUtils.infoLog("Element is already expanded: " + xpathToClick);
            }
        } else {
            frame.locator(xpathToClick).click();
            LogUtils.infoLog("Clicked element: " + xpathToClick);
        }
        LogUtils.infoLog("Continuing with the next steps...");
    }

    /**
     * Finds and returns the frame containing the specified XPath element.
     */
    public static Frame getFrameWithElement(String xpath) {
        for (Frame frame : page().frames()) {
            try {
                Object found = frame.evaluate("(xpath) => {" +
                        "const el = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;" +
                        "return !!(el);" +
                        "}", xpath);
                if (Boolean.TRUE.equals(found)) {
                    LogUtils.infoLog("Found element in frame: " + frame.name());
                    return frame;
                }
            } catch (Exception ignored) {
            }
        }
        throw new AssertionError("No frame contains the element for XPath: " + xpath);
    }

    public static void closeViewQuestIntercomModal() {
        clickElementInFrame("//i[@color='headerButtonColor']/..", null);
    }

    /**
     * Checks if an element is visible in the DOM using XPath.
     */
    public static boolean isElementVisibleByXPath(String xpath) {
        Frame frame = getFrameWithElement(xpath);
        // Check if the element is visible using JavaScript execution in the browser
        boolean isVisible = (boolean) frame.evaluate("(xpath) => {" +
                "const el = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;" +
                "return el && !!(el.offsetWidth || el.offsetHeight || el.getClientRects().length);" +
                "}", xpath);
        return isVisible;
    }

    /**
     * Verifies that a specific challenge is not visible in the Intercom frame.
     */
    public static void validateChallengesTypeOnViewQuest() {
        Frame intercomFrame = getFrameByName("intercom-messenger-frame");
        String ele = "(//div[@data-testid='collapsible-card-header'])[3]/div/h3/div/div";
        boolean isElementInFrame = (boolean) intercomFrame.evaluate("(xpath) => {" +
                "const el = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;" +
                "return el !== null;" +
                "}", ele);
        assertFalse(isElementInFrame, "Element should not be available, but it is.");
    }

    public static void validateMarkAsCompleteModal() {
        assertTrue(isElementVisible("//div[@class='ob-container']"));
        assertTrue(isElementVisible("//div[@class='ob-container']/button")); //close button
        assertTrue(isElementVisible("//div[@class='ob-container']/img"));
        assertEquals(getElementText("//div[@class='ob-container']/h4"), MarkAsCompleteQuest.title.info1);
        assertEquals(getElementText("//div[@class='ob-container']/p"), MarkAsCompleteQuest.title.info2);
        assertTrue(isElementVisible(btnContinueExp));
        assertEquals(getElementText(btnContinueExp), MarkAsCompleteQuest.title.info3);
        assertTrue(isElementVisible("//div[@class='ob-container']/div/button[2]"));
        assertEquals(getElementText("//div[@class='ob-container']/div/button[2]"), MarkAsCompleteQuest.title.info4);
    }


    /**
     * Validates the introductory quest banner and closes the intercom modal if visible.
     */
    public void validateIntroductoryQuestBanner() {
        waitForSeconds(7);
        boolean isIntercomVisible = false;

        int timeoutInSeconds = 5;
        int pollIntervalMs = 500;
        int maxTries = (timeoutInSeconds * 1000) / pollIntervalMs;
        for (int i = 0; i < maxTries; i++) {
            try {
                if (isElementVisibleByXPath(modalTourStepIntercom)) {
                    isIntercomVisible = true;
                    break;
                }
            } catch (AssertionError e) {
                LogUtils.infoLog("Caught assertion error : " + e);
            } catch (Exception e) {
                LogUtils.infoLog("Exception error: " + e);
            }

            try {
                Thread.sleep(pollIntervalMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (isIntercomVisible) {
            closeIntercomChallengeModal();
        } else {
            assertTrue(isElementVisibleByXPath(containerQuestBanner));
            assertTrue(isElementVisibleByXPath(txtQuestBannerInfo));
            assertEquals(getElementText("//div[@class='oflow-info']/span[2]"),
                    "Make the most of Digify with introductory quests");
            assertTrue(isElementVisibleByXPath("//div[@class='oflow-info']/span[1]"));
            assertTrue(isElementVisibleByXPath("//div[@class='oflow-btns']"));
            assertTrue(isElementVisibleByXPath("//div[@class='red-circle']"));
            assertTrue(isElementVisibleByXPath(btnViewQuest));
            assertTrue(isElementVisibleByXPath(btnMarkAsComplete));
            assertEquals(getElementText(btnViewQuest), "View quests");
            assertEquals(getElementText(btnMarkAsComplete), "Mark as complete");
        }
    }

    /**
     * Validates that the task status matches one of the allowed statuses.
     */
    public void validateTaskStatus(String validateStatus) {
        if (validateStatus.equalsIgnoreCase("Completed") ||
                validateStatus.equalsIgnoreCase("About 4 minutes left") ||
                validateStatus.equalsIgnoreCase("About 4 minutes") ||
                validateStatus.equalsIgnoreCase("About 6 minutes")) {
            validateTextInFrame(txtRemainingTask, validateStatus);
        } else {
            fail("Task status does not match: " + validateStatus);
        }
    }

    public void selectContinueExploring() {
        clickElement(btnContinueExp);
        validateIntroductoryQuestBanner();
    }

    /**
     * Selects and validates a challenge within the Intercom frame.
     */
    public void selectIntercomChallenge(String intercomChallengeOption, Boolean isChallengeCompleted, Boolean isTeamMember) {
        Frame intercomFrame = getFrameByName("intercom-messenger-frame");
        waitForSeconds(3);
        switch (intercomChallengeOption.toLowerCase()) {
            case "challenge 1: send a file":
                waitForSeconds(5);
                validateTextInFrame(tglCardHeader, null);
                validateTextInFrame("(//div[@data-testid='collapsible-card-header'])[1]/div/h3/div/div", Quests.challenge1.title);
                if (isChallengeCompleted) {
                    clickElementInFrame(tglCardHeader, "(//div[@data-testid='collapsible-card-header'])[1]/../../..");
                }
                validateTextInFrame("((//div[@data-testid='collapsible-card-content'])[1]/div/div/div/div)[1]", Quests.challenge1.info1);
                validateTextInFrame("((//div[@data-testid='collapsible-card-content'])[1]/div/div/div/div)[3]", Quests.challenge1.info2);
                waitForSeconds(2);
                clickElementInFrame(btnShowMeHow, null);//show me how
                break;
            case "challenge 2: create a data room":
                waitForSeconds(5);
                validateTextInFrame("(//div[@data-testid='collapsible-card-header'])[2]", null);
                validateTextInFrame("(//div[@data-testid='collapsible-card-header'])[2]/div/h3/div/div", Quests.challenge2.title);
                if (isChallengeCompleted) {
                    clickElementInFrame("(//div[@data-testid='collapsible-card-header'])[2]", "(//div[@data-testid='collapsible-card-header'])[2]/../../..");
                }
                clickElementInFrame(tglCardHeader, null);
                waitForSeconds(2);
                clickElementInFrame("(//div[@data-testid='collapsible-card-header'])[2]", "(//div[@data-testid='collapsible-card-header'])[2]/../../..");
                waitForSeconds(3);
                if (isTeamMember) {
                    validateTextInFrame("((//div[@data-testid='collapsible-card-content'])[2]/div/div/div/div)[1]", Quests.challenge2.tmInfo);
                } else {
                    validateTextInFrame("((//div[@data-testid='collapsible-card-content'])[2]/div/div/div/div)[1]", Quests.challenge2.info1);
                }
                validateTextInFrame("((//div[@data-testid='collapsible-card-content'])[2]/div/div/div/div)[3]", Quests.challenge2.info2);
                waitForSeconds(2);
                clickElementInFrame(btnShowMeHow, null);//show me how
                break;

            case "challenge 3: set up your team":
                verifyTextByXPath(intercomFrame, "(//div[@data-testid='collapsible-card-header'])[3]", null);
                verifyTextByXPath(intercomFrame, "(//div[@data-testid='collapsible-card-header'])[3]/div/h3/div/div", Quests.challenge3.title);
                if (isChallengeCompleted) {
                    clickElementInFrame("(//div[@data-testid='collapsible-card-header'])[3]", "(//div[@data-testid='collapsible-card-header'])[3]/../../..");
                }
                clickElementInFrame(tglCardHeader, null);
                waitForSeconds(2);
                clickElementInFrame("(//div[@data-testid='collapsible-card-header'])[3]", "(//div[@data-testid='collapsible-card-header'])[3]/../../..");
                validateTextInFrame("((//div[@data-testid='collapsible-card-content'])[3]/div/div/div/div)[1]", Quests.challenge3.info1);
                waitForSeconds(2);
                clickElementInFrame(btnShowMeHow, null);//show me how
                break;
            default:
                fail("Incorrect challenge type");
        }
    }

    /**
     * Completes the "Set Up Your Team" quest by executing a defined number of steps.
     */
    protected void completeSetupYourTeamChallenge(int stepsToRun) {
        if (stepsToRun >= 1) { //change logo and colors
            page().waitForLoadState();
            waitForURLContaining("/#/home");
            waitForSeconds(10);
            validateTextInFrame(containerFeatureMarker, null);
            validateTextInFrame(modalTourStepIntercom, null);//modal
            validateTextInFrame(txtInfo1, SetupYourTeamQuest.step1.info1);
            validateTextInFrame(txtInfo2, SetupYourTeamQuest.step1.info2);
            validateTextInFrame(txtTourSteps, SetupYourTeamQuest.step1.info3);
            waitForSeconds(3);
            HomePage.selectShortcutIcons("change logo and colors");
        }
        if (stepsToRun >= 2) { //change logo
            page().waitForLoadState();
            waitForURLContaining("/#/a/b");
            waitForSeconds(5);
            validateTextInFrame(containerFeatureMarker, null);
            validateTextInFrame(modalTourStepIntercom, null);//modal
            validateTextInFrame(txtInfo1, SetupYourTeamQuest.step2.info1);
            validateTextInFrame("(//div[contains(@class,'intercom-tour-step intercom-tour-step-pointer intercom')]//div[contains(@class,'intercom-block-paragraph')])[2]", SetupYourTeamQuest.step2.info2);
            validateTextInFrame(txtTourSteps, SetupYourTeamQuest.step2.info3);
            waitForSeconds(3);
            clickNext();
        }
        if (stepsToRun >= 3) { //members tab
            page().waitForLoadState();
            waitForURLContaining("/#/a/b");
            waitForSeconds(5);
            validateTextInFrame(containerFeatureMarker, null);
            validateTextInFrame(modalTourStepIntercom, null);//modal
            validateTextInFrame(txtInfo1, SetupYourTeamQuest.step3.info1);
            validateTextInFrame(txtInfo2, SetupYourTeamQuest.step3.info2);
            validateTextInFrame(txtTourSteps, SetupYourTeamQuest.step3.info3);
            waitForSeconds(3);
            AdminSettingsPage.navigateToTabInAdminSettings("members");
        }
        if (stepsToRun >= 4) { //new member
            page().waitForLoadState();
            waitForURLContaining("/#/a");
            waitForSeconds(5);
            validateTextInFrame(containerFeatureMarker, null);
            validateTextInFrame(modalTourStepIntercom, null);//modal
            validateTextInFrame(txtInfo1, SetupYourTeamQuest.step4.info1);
            validateTextInFrame(txtTourSteps, SetupYourTeamQuest.step4.info2);
            waitForSeconds(3);
            if (stepsToRun == 4) {
                closeIntercomChallengeModal();
                return;
            }
            clickNext();
        }
        if (stepsToRun >= 5) { //set up team now
            page().waitForLoadState();
            waitForURLContaining("/#/a");
            waitForSeconds(5);
            validateTextInFrame(modalTourStepIntercom, null);//modal
            validateTextInFrame("//div[contains(@class,'intercom-scrollable intercom')]/div/h2", SetupYourTeamQuest.step5.info1);
            validateTextInFrame("(//div[@data-testid='checklist-task-list']/div/div)[1]/div/div", SetupYourTeamQuest.step5.info2);
            validateTextInFrame("//img[@data-testid='img']", null);
            validateTextInFrame(txtTourSteps, SetupYourTeamQuest.step5.info3);
            waitForSeconds(3);
            clickElementInFrame("(//div[contains(@class,'intercom-authored-container intercom')]/div[3]/div)[2]", null);
        }
    }

    /**
     * Completes the "Create Data Room" quest by executing a defined number of steps.
     */
    protected void completeCreateDataRoomChallenge(int stepsToRun) {
        if (stepsToRun >= 1) { //create data room
            page().waitForLoadState();
            waitForURLContaining("/#/home");
            waitForSeconds(10);
            validateTextInFrame(containerFeatureMarker, null);
            validateTextInFrame(modalTourStepIntercom, null);//modal
            validateTextInFrame(txtInfo1, CreateDRQuest.step1.info1);
            validateTextInFrame(txtInfo2, CreateDRQuest.step1.info2);
            validateTextInFrame(txtTourSteps, CreateDRQuest.step1.info3);
            waitForSeconds(3);
        }
        if (stepsToRun >= 2) { //set default guest permission
            HomePage.navigateToPageFromHomePage("CreateDataRoom");
            page().waitForLoadState();
            waitForURLContaining("/#/dr/c");
            waitForSeconds(5);
            validateTextInFrame(containerFeatureMarker, null);
            validateTextInFrame(modalTourStepIntercom, null);//modal
            validateTextInFrame(txtInfo1, CreateDRQuest.step2.info1);
            validateTextInFrame(txtTourSteps, CreateDRQuest.step2.info2);
            waitForSeconds(3);
            clickNext();
        }
        if (stepsToRun >= 3) { //about page
            page().waitForLoadState();
            waitForURLContaining("/#/dr/c");
            waitForSeconds(5);
            validateTextInFrame(containerFeatureMarker, null);
            validateTextInFrame(modalTourStepIntercom, null);//modal
            validateTextInFrame(txtInfo1, CreateDRQuest.step3.info1);
            validateTextInFrame(txtTourSteps, CreateDRQuest.step3.info2);
            waitForSeconds(3);
            clickNext();

        }
        if (stepsToRun >= 4) { //question & answer
            page().waitForLoadState();
            waitForURLContaining("/#/dr/c");
            waitForSeconds(5);
            validateTextInFrame(containerFeatureMarker, null);
            validateTextInFrame(modalTourStepIntercom, null);//modal
            validateTextInFrame(txtInfo1, CreateDRQuest.step4.info1);
            validateTextInFrame(txtTourSteps, CreateDRQuest.step4.info2);
            waitForSeconds(3);
            if (stepsToRun == 4) {
                closeIntercomChallengeModal();
                return;
            }
            clickNext();
        }
        if (stepsToRun >= 5) { //create a data room now
            page().waitForLoadState();
            waitForURLContaining("/#/dr/c");
            waitForSeconds(5);
            validateTextInFrame(modalTourStepIntercom, null);//modal
            validateTextInFrame("//div[contains(@class,'intercom-scrollable intercom')]/div/h2", CreateDRQuest.step5.info1);
            validateTextInFrame("//div[contains(@class,'intercom-scrollable intercom')]/div/h2/following-sibling::div[1]", CreateDRQuest.step5.info2);
            validateTextInFrame("//img[@data-testid='img']", null);
            validateTextInFrame(txtTourSteps, CreateDRQuest.step5.info3);
            validateTextInFrame(btnToProceed, CreateDRQuest.step5.info4);
            waitForSeconds(3);
            clickElementInFrame("(//div[contains(@class,'intercom-authored-container intercom')]/div[3]/div)[2]", null);
        }
    }

    protected void clickNext() {
        clickElementInFrame(btnToProceed, null);
    }

    /**
     * Completes the "Send a File" challenge by executing a defined number of steps.
     */
    protected void completeSendFileChallenge(int stepsToRun) {
        if (stepsToRun >= 1) { //send files
            page().waitForLoadState();
            waitForURLContaining("/#/home");
            waitForSeconds(10);
            validateTextInFrame(containerFeatureMarker, null);
            validateTextInFrame(modalTourStepIntercom, null);//modal
            validateTextInFrame(txtInfo1,
                    SendFileQuest.step1.info1);
            validateTextInFrame(txtInfo2,
                    SendFileQuest.step1.info2);
            validateTextInFrame(txtTourSteps, SendFileQuest.step1.info3);
            waitForSeconds(3);
            HomePage.navigateToPageFromHomePage("SendFiles");
        }
        if (stepsToRun >= 2) { //upload files
            page().waitForLoadState();
            waitForURLContaining("/#/sf");
            waitForSeconds(5);
            validateTextInFrame(containerFeatureMarker, null);
            validateTextInFrame(modalTourStepIntercom, null);//modal
            validateTextInFrame("(//div[contains(@class,'intercom-tour-step intercom-tour-step-pointer intercom')]//div[contains(@class,'intercom-block-paragraph')])[1]/b", SendFileQuest.step2.info1);
            validateTextInFrame(txtInfo1, SendFileQuest.step2.info2);
            validateTextInFrame(txtTourSteps, SendFileQuest.step2.info3);
            waitForSeconds(3);
            clickNext();
        }
        if (stepsToRun >= 3) { //dynamic watermark
            page().waitForLoadState();
            waitForURLContaining("/#/sf");
            waitForSeconds(5);
            validateTextInFrame(containerFeatureMarker, null);
            validateTextInFrame(modalTourStepIntercom, null);//modal
            validateTextInFrame("(//div[contains(@class,'intercom-tour-step intercom-tour-step-pointer intercom')]//div[contains(@class,'intercom-block-paragraph')])[1]/b", SendFileQuest.step3.info1);
            validateTextInFrame(txtInfo1, SendFileQuest.step3.info2);
            validateTextInFrame(txtTourSteps, SendFileQuest.step3.info3);
            waitForSeconds(3);
            clickNext();
        }
        if (stepsToRun >= 4) { //choose to send your files
            page().waitForLoadState();
            waitForURLContaining("/#/sf");
            waitForSeconds(5);
            validateTextInFrame(containerFeatureMarker, null);
            validateTextInFrame(modalTourStepIntercom, null);//modal
            validateTextInFrame(txtInfo1, SendFileQuest.step4.info1);
            validateTextInFrame("(//div[contains(@class,'intercom-tour-step intercom-tour-step-pointer intercom')]//div[contains(@class,'intercom-block-paragraph')])[2]",
                    SendFileQuest.step4.info2);
            validateTextInFrame("(//div[contains(@class,'intercom-tour-step intercom-tour-step-pointer intercom')]//div[contains(@class,'intercom-block-paragraph')])[3]",
                    SendFileQuest.step4.info3);
            validateTextInFrame(txtTourSteps, SendFileQuest.step4.info4);
            waitForSeconds(3);
            if (stepsToRun == 4) {
                closeIntercomChallengeModal();
                return;
            }
            clickNext();
        }
        if (stepsToRun >= 5) { //send file now
            page().waitForLoadState();
            waitForURLContaining("/#/sf");
            waitForSeconds(5);
            validateTextInFrame(modalTourStepIntercom, null);//modal
            validateTextInFrame("//div[contains(@class,'intercom-scrollable intercom')]/div/h2", SendFileQuest.step5.info1);
            validateTextInFrame("//div[contains(@class,'intercom-scrollable intercom')]/div/h2/following-sibling::div[1]", SendFileQuest.step5.info2);
            validateTextInFrame("//img[@data-testid='img']", null);
            validateTextInFrame(txtTourSteps, SendFileQuest.step5.info3);
            validateTextInFrame(btnToProceed, "Send a file now");
            waitForSeconds(3);
            clickElementInFrame(btnToProceed, null);
            waitForSeconds(2);
        }
    }
}


