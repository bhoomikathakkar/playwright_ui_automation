package digifyE2E.playwright;

import com.microsoft.playwright.Page;

import java.util.Stack;

public class DigifyPlaywrightWrapper extends BrowserManager {

    static Stack<Page> tabs = new Stack<>();

    /**
     * To check if there is no tabs then create a one and set it to front, if tab exist then set it to front
     *
     * @return Page
     */
    public static Page page() {
        if (tabs.isEmpty())
            tabs.push(getBrowserInstance().newPage());
        return tabs.peek();
    }

    /**
     * To set the current page in front
     *
     * @param tab
     */
    protected static void setCurrentPage(Page tab) {
        tabs.push(tab);
    }

    /**
     * Close the current tab of a browser
     */
    protected static void closeCurrentTab() {
        page().close();
        tabs.pop();
    }

    public static void closeAllOpenPages() {
        tabs.forEach(Page::close);
        tabs = new Stack<>();
    }
}