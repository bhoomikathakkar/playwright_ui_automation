package digifyE2E.pages;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Frame;
import com.microsoft.playwright.options.LoadState;
import digifyE2E.utils.LogUtils;
import org.testng.Assert;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailDropEmailService extends CommonUIActions {

    static final String btnViewMailbox = "//*[@id='navbar']/div/div[3]/form/button";
    static final String btnRefreshMailbox = "//*[@id='gatsby-focus-wrapper']/div/main/div/div[1]/div/div/div[1]/div[1]/button";
    static final String txtInputEmail = "//*[@id='navbar']/div/div[3]/form/div/input";
    static final String btnFirstMsg = "(//*[@id='gatsby-focus-wrapper']/div/main/div/div[1]/div/div/div[1]/div[2]/div)[1]";
    public static String MAILDROP_URL = "https://maildrop.cc/";

    public static void navigateToMailDropApp() {
        openNewWindow();
        visit(MAILDROP_URL, false);
        waitUntilSelectorAppears(btnViewMailbox);
        page().waitForLoadState(LoadState.LOAD);
    }

    public static void openMailDropForUser(String userType) {
        navigateToMailDropApp();
        page().focus(txtInputEmail);
        clickElement(txtInputEmail);
        waitForSeconds(2); //Adding hard wait because this method is not working on every machine, and it is a third party application.
        inputIntoTextField(txtInputEmail, userType);
        clickElement(btnViewMailbox);
        page().waitForLoadState(LoadState.LOAD);
        int i = 0;
        while (!isElementVisible("//*[@id='gatsby-focus-wrapper']/div/main/div/div[1]/div/div/div[1]/div[1]/div")) {
            clickElement(btnRefreshMailbox);
            if (i > 10)
                break;
            i++;
        }
        clickElement(btnFirstMsg);
        page().waitForLoadState(LoadState.LOAD);
    }

    public static String getOTPFromMailDrop(String user) {
        String txtVerificationCode = "//html/body/table/tbody/tr/td/center/table/tbody/tr/td/table[1]/tbody/tr/td/p/b";
        String sixDigitOTPCode = "";
        openMailDropForUser(user);

        int maxRetries = 3;
        int retryCount = 0;
        Frame desiredFrame = null;

        while (retryCount < maxRetries) {
            List<Frame> frames = page().frames();

            for (Frame frame : frames) {
                if (frame.url().equals("about:srcdoc") || frame.url().equals("about:blank")) {
                    frame.waitForURL("about:srcdoc");
                    desiredFrame = frame;
                    break;
                }
            }
            if (desiredFrame != null) {
                LogUtils.infoLog("Switched to frame with URL: " + desiredFrame.url());
                desiredFrame.evaluate("() => window.location.hash = '#document'");// Navigate within the iframe's content
                ElementHandle elementHandle = desiredFrame.querySelector(txtVerificationCode);// Fetch text from the selector within the iframe's content
                if (elementHandle != null) {
                    sixDigitOTPCode = elementHandle.innerText();
                    LogUtils.infoLog("Verification Code: " + sixDigitOTPCode);
                } else {
                    LogUtils.infoLog("Element not found with locator: " + txtVerificationCode);
                }
                break;
            } else {
                LogUtils.infoLog("Desired frame not found. Retrying...");
                retryCount++;
                waitForSeconds(1); // Wait for 1 second to perform retry mechanism correctly
            }
        }
        if (desiredFrame == null) {
            LogUtils.infoLog("Failed to find desired frame after " + maxRetries + " retries.");
        }
        LogUtils.infoLog("Verification Code on mail drop: " + sixDigitOTPCode);
        cleanupMailDrop();
        Assert.assertEquals(sixDigitOTPCode.length(), 6, "unexpected OTP from Mail drop");
        return sixDigitOTPCode;
    }

    public static String getAccountRestoreLink(String user) {
        String txtRestoreLink = "//html/body/table/tbody/tr/td/center/table/tbody/tr/td/table[1]/tbody/tr/td/p";
        String restoreAccountLink = "";
        openMailDropForUser(user);

        int maxRetries = 3;
        int retryCount = 0;
        Frame desiredFrame = null;
        while (retryCount < maxRetries) {
            List<Frame> frames = page().frames();

            for (Frame frame : frames) {
                if (frame.url().equals("about:srcdoc") || frame.url().equals("about:blank")) {
                    frame.waitForURL("about:srcdoc");
                    desiredFrame = frame;
                    break;
                }
            }
            if (desiredFrame != null) {
                LogUtils.infoLog("Switched to frame with URL: " + desiredFrame.url());
                desiredFrame.evaluate("() => window.location.hash = '#document'"); // Navigate within the iframe's content
                ElementHandle elementHandle = desiredFrame.querySelector(txtRestoreLink); // Fetch text from the selector within the iframe's content
                if (elementHandle != null) {
                    String emailContent = elementHandle.innerText(); // Get the text content
                    LogUtils.infoLog("Verification link: " + emailContent);

                    // Use regex to extract the restore link
                    String urlRegex = "https://staging-x\\.digifyteam\\.com/a/#/access/restore/\\w+";
                    Pattern pattern = Pattern.compile(urlRegex);
                    Matcher matcher = pattern.matcher(emailContent);

                    if (matcher.find()) {
                        restoreAccountLink = matcher.group(); // Extract the URL
                        LogUtils.infoLog("Extracted restore link: " + restoreAccountLink);
                    } else {
                        LogUtils.infoLog("Restore link not found in the email content.");
                    }
                } else {
                    LogUtils.infoLog("Element not found with locator: " + txtRestoreLink);
                }
                break;
            } else {
                LogUtils.infoLog("Desired frame not found. Retrying...");
                retryCount++;
                waitForSeconds(1); // Wait for 1 second to perform retry mechanism correctly
            }
        }
        if (desiredFrame == null) {
            LogUtils.infoLog("Failed to find desired frame after " + maxRetries + " retries.");
        }
        LogUtils.infoLog("Restore link on mail drop: " + restoreAccountLink);
        cleanupMailDrop();

        return restoreAccountLink; // Return the extracted URL
    }

    public static void cleanupMailDrop() {
        String btnDeleteInMailDropApp = "//*[@id='gatsby-focus-wrapper']/div/main/div/div[1]/div/div/div[2]/div[1]/div/div[2]/button[2]";
        String btnYesDeleteInMailDropApp = "//*[@id='gatsby-focus-wrapper']/div/main/div/div[1]/div/div/div[4]/div/div[2]/button[2]";
        page().waitForLoadState(LoadState.LOAD);
        clickElement(btnDeleteInMailDropApp);
        clickElement(btnYesDeleteInMailDropApp);
        waitUntilSelectorAppears(btnRefreshMailbox);
        closeCurrentTab();
        page().waitForLoadState(LoadState.LOAD);
    }

}

