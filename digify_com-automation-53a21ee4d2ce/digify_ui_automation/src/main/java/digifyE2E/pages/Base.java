package digifyE2E.pages;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ElementState;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.MouseButton;
import com.microsoft.playwright.options.WaitForSelectorState;
import digifyE2E.playwright.DigifyPlaywrightWrapper;
import digifyE2E.utils.EnvUtils;
import digifyE2E.utils.LogUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

import static org.testng.Assert.assertEquals;

/**
 * Store all the wrapper methods in this class
 */
public class Base extends DigifyPlaywrightWrapper {

    public static final String PRICING_PAGE_URL = "https://staging-x.digifyteam.com/pricing/#/plans";
    public static final String WHITELABEL_URL = "https://vdr.l3space.com/a";
    public static final String WORDPRESS_URL = "https://staging-x.digifyteam.com/";
    public static final String lnkUserProfile = "//*[@id='profileDropdown']/div/div/p[1]/i";
    public static final String txtVerifyYourIdentity = getXpathForContainsText("Verify your", "p");
    public static final String containerFileViewerFooter = "//lib-viewer-footer[@class='tw-scope']";
    public static final String lnkLogout = "//div[@role='menu']/a[2]";
    public static final String imgLoginPageBrandingLogo = "(//img[@class='dlogo'])[1]";
    public static final String btnCommonButtonToCreate = "//button[@class='btn digify-btn-blue']";
    public static String drdFileViewerUserProfile = "//*[@id='profileDropdowncurrentUser']";
    public static String btnOk = getXpathForContainsText("OK", "button");


    /**
     * navigate to a given url after appending it to the base URL for the execution env
     *
     * @param pageUrl           url to append after Base URL.
     * @param appendWithBaseURL if false then provide URL will not be appended with base.
     *                          Use for external pages
     */
    public static void visit(String pageUrl, boolean appendWithBaseURL) {
        String url = appendWithBaseURL ? EnvUtils.getBaseUrl() + pageUrl :
                pageUrl;
        LogUtils.infoLog("Navigating to url-> " + url);
        page().navigate(url);
        page().waitForLoadState(LoadState.LOAD);
    }

    protected static void clickAndSwitchToNewTab(String locator) {
        Page newTab = getBrowserInstance().waitForPage(
                () -> clickElement(locator)
        );
        setCurrentPage(newTab);
    }

    protected static void openNewWindow() {
        setCurrentPage(getBrowser().newPage());
    }

    protected static void clickElement(String webLocator) {
        LogUtils.infoLog("Clicking on webLocator-> " + webLocator);
        waitUntilSelectorAppears(webLocator);
        waitForSelectorStateChange(webLocator, ElementState.ENABLED);
        page().click(webLocator);
    }

    protected static void dispatchClickElement(String webLocator) {
        LogUtils.infoLog("Clicking on weblocator->" + webLocator);
        try {
            page().locator(webLocator).dispatchEvent("click");
        } catch (PlaywrightException e) {
            e.getCause();
        }

    }

    protected static void waitUntilSelectorAppears(String webLocator) {
        page().waitForSelector(webLocator);
        isElementVisible(webLocator);
        if (Objects.nonNull(page().querySelector(webLocator))) {
            try {
                page().querySelector(webLocator).scrollIntoViewIfNeeded(new ElementHandle.ScrollIntoViewIfNeededOptions().setTimeout(2000));
            } catch (PlaywrightException ignore) {
            }
        }
    }

    protected static boolean isElementEnabled(String webLocator) {
        page().waitForSelector(webLocator);
        return page().locator(webLocator).isEnabled();
    }

    protected static void waitForSelectorAppearsWithTimeout(String webLocator, int timeOutSec) {
        page().locator(webLocator).waitFor(new Locator.WaitForOptions().setTimeout(timeOutSec * 1000));
    }

    protected static void forceClickElement(String webLocator) {
        page().locator(webLocator).click(new Locator.ClickOptions().setForce(true));
    }


    public static boolean isElementVisible(String webLocator) {
        try {
            page().waitForSelector(webLocator,
                    new Page.WaitForSelectorOptions()
                            .setTimeout(3000).setState(WaitForSelectorState.VISIBLE));
            return true;
        } catch (TimeoutError ignore) {
            return false;
        }
    }

    protected static void inputIntoTextField(String xpath, String text) {
        waitUntilSelectorAppears(xpath);
        page().locator(xpath).clear();
        page().fill(xpath, text);
    }

    public static String getElementText(String webLocator) {
        waitUntilSelectorAppears(webLocator);
        return StringUtils.normalizeSpace(page().locator(webLocator).textContent());
    }


    public static String getXpathForContainsText(String textInXpath, String webElementType) {
        return "//" +
                (Objects.nonNull(webElementType) ? webElementType : "*")
                + "[contains(text(),'" + textInXpath + "')]";
    }

    public static String getXpathForTextEquals(String textInXpath, String webElementType) {
        return "(//" +
                (Objects.nonNull(webElementType) ? webElementType : "*")
                + "[text()='" + textInXpath + "'])";
    }

    public static void keyPress(String key) {
        page().keyboard().press(key);
    }

    /**
     * Should not be used to wait for elements in normal step defS
     *
     * @param delayInSeconds seconds to wait for.
     */
    @Deprecated
    public static void waitForSeconds(int delayInSeconds) {
        page().waitForTimeout(delayInSeconds * 1000);
    }

    public static String getInputValue(String webLocator) {
        waitUntilSelectorAppears(webLocator);
        return page().locator(webLocator).inputValue();
    }

    public static void clearValue(String webLocator) {
        waitUntilSelectorAppears(webLocator);
        page().locator(webLocator).clear();
    }

    public static boolean isChecked(String webLocator) {
        page().locator(webLocator).isChecked();
        return true;
    }

    public static boolean isDisabled(String webLocator) {
        page().locator(webLocator).isDisabled();
        return true;
    }

    protected static void waitForURLContaining(String substring) {
        page().waitForURL(currentUrl -> currentUrl.contains(substring));
    }

    public static void waitForSelectorStateChange(String locator, ElementState stateOfElement) {
        page().waitForSelector(locator).waitForElementState(stateOfElement);
    }

    public static String normalizeSpaceForString(String text) {
        return StringUtils.normalizeSpace(text);
    }

    public static boolean isElementHidden(String webLocator) {
        return page().locator(webLocator).isHidden();
    }

    public static boolean isAttributeNotPresent(String webLocator, String attribute) {
        return page().locator(webLocator).getAttribute(attribute) == null;
    }

    public static void waitForElementHidden(String locator) {
        page().waitForSelector(locator, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN));
        LogUtils.infoLog("Element is hidden");
    }

    public static void retryUntilSelectorAppearsAndThenAssert(int retryCount, String assertLocator, String expectedResult) {
        int i = 0;
        while (i < retryCount) {
            if (getElementText(assertLocator).equals(expectedResult)) {
                assertEquals(getElementText(assertLocator), expectedResult);
                break;
            } else {
                page().reload();
                i++;
            }

        }
    }

    public static void rightClickElementUsingMouse(String webLocator) {
        page().click(webLocator, new Page.ClickOptions()
                .setButton(MouseButton.RIGHT));
    }

    public static void waitForSecondsInApi(long sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * use to click on element for number of times
     *
     * @param selector
     * @param numberOfClicks
     */
    public static void clickNNumberOfTimes(String selector, int numberOfClicks) {
        numberOfClicks = Math.abs(numberOfClicks);
        while (numberOfClicks > 0) {
            page().locator(selector).click();
            numberOfClicks--;
        }
    }

    protected static void openNewTabAndSwitchToPage() {
        Page newTab = getBrowserInstance().waitForPage(
                () -> {
                    Page tempTab = getBrowserInstance().newPage();
                    tempTab.bringToFront();
                }
        );
        setCurrentPage(newTab);
    }

    public static int countTotalNoOfElements(String locator) {
        List<ElementHandle> elements = page().querySelectorAll(locator);
        return elements.size();
    }

    public static String getAttributeValue(String selector, String attribute) {
        return page().locator(selector).getAttribute(attribute);
    }

    /**
     * Handle active login session while logging in the application
     */
    public void handleActiveSessionButton() {
        String btnActiveSession = getXpathForContainsText("Continue to sign in", "*");
        try {
            page().click(btnActiveSession, new Page.ClickOptions().setTimeout(3000));
        } catch (Exception ignore) {
        }
    }

    protected boolean waitForSelectorVisibleWithTimeout(String webLocator, int timeOutSec) {
        try {
            page().locator(webLocator).waitFor(
                    new Locator.WaitForOptions()
                            .setTimeout(timeOutSec * 1000)
                            .setState(WaitForSelectorState.VISIBLE)
            );
            return true;
        } catch (Exception e) {
            LogUtils.infoLog("Timeout waiting for visible selector: " + webLocator);
            LogUtils.infoLog("Exception: " + e.getMessage());
            return false;
        }
    }
}