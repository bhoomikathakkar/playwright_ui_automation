package digifyE2E.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import digifyE2E.utils.LogUtils;

import java.util.Objects;

import static digifyE2E.playwright.DigifyPlaywrightWrapper.page;

public class BrowserManager {
    public static final String BROWSER_NAME = System.getProperty("BROWSER_NAME");
    public static final boolean HEADLESS_BROWSER = Boolean.parseBoolean(System.getProperty("HEADLESS"));
    private static Browser browser;
    private static BrowserContext browserContext;

    /**
     * Get browser
     *
     * @return browser
     */
    public static Browser getBrowser() {
        if (!Objects.nonNull(browser)) {
            String browserName = Objects.nonNull(BROWSER_NAME) ? BROWSER_NAME.toLowerCase() : "chrome";
            switch (browserName) {
                case "chrome":
                    browser = Playwright.create().chromium().launch(
                            new BrowserType.LaunchOptions().setHeadless(HEADLESS_BROWSER));
                    LogUtils.infoLog("Executing on Chrome");
                    break;
                case "firefox":
                    browser = Playwright.create().firefox().launch(
                            new BrowserType.LaunchOptions().setHeadless(HEADLESS_BROWSER));
                    LogUtils.infoLog("Executing on firefox");
                    break;
                case "edge":
                    browser = Playwright.create().chromium().launch(
                            new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(HEADLESS_BROWSER));
                    LogUtils.infoLog("Executing on Edge");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid browser name: " + BROWSER_NAME);
            }
        }
        return browser;
    }

    /**
     * Initialize a browser
     */
    public static void initializeBrowser() {
        browser = getBrowser();
        Browser.NewContextOptions nc = new Browser.NewContextOptions();
        nc.setViewportSize(null);
        nc.setViewportSize(1280, 720); //HD resolution
        //nc.setViewportSize(1920, 1080); //FHD resolution
        browserContext = browser.newContext(nc);
    }

    /**
     * Get a browser instance
     *
     * @return browserContext
     */
    public static BrowserContext getBrowserInstance() {
        if (Objects.isNull(browser)) initializeBrowser();
        return browserContext;
    }

    protected static void deleteBrowserSessionData() {
        try {
            LogUtils.infoLog("Browser Local storage " + page().evaluate("window.localStorage.length"));
            LogUtils.infoLog("Browser Local storage after clear " + page().evaluate("window.localStorage.clear()"));
            LogUtils.infoLog("Browser sessionStorage storage after clear " + page().evaluate("window.sessionStorage.clear()"));
            page().context().clearCookies();
        } catch (Exception ignore) {
        }
    }
}