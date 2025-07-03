package digifyE2E.cucumber;

import digifyE2E.pages.Base;
import digifyE2E.utils.FileUtils;
import digifyE2E.utils.LogUtils;
import io.cucumber.java.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;


public class CucumberHooks extends Base {

    public static Set<String> failingTags = new LinkedHashSet<>();

    @BeforeAll
    public static void beforeAll() {
    }

    @AfterAll
    public static void afterAll() {
        if (!failingTags.isEmpty()) {
            StringBuilder retryTags = new StringBuilder();
            failingTags.forEach(ft -> {
                if (!retryTags.isEmpty()) retryTags.append(" or ");
                retryTags.append(ft);
            });
            LogUtils.infoLog("[Retry Tags]: " + retryTags);
        }
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        if (!scenario.getSourceTagNames().stream().anyMatch(tag -> tag.equalsIgnoreCase("@api")))
            getBrowserInstance();
    }

    @After
    public void afterScenario(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                Path screenshotPath = FileUtils.takeScreenshot(scenario.getId());
                scenario.attach(Files.readAllBytes(screenshotPath), "image/png", "Screenshot");
                failingTags.add(scenario.getSourceTagNames().stream().findFirst().get());
            }
        } catch (Exception e) {
            LogUtils.infoLog("Hook failed");
            e.printStackTrace();
        } finally {
            deleteBrowserSessionData();
            closeAllOpenPages();
        }
    }
}
