package digifyE2E;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/java/digifyE2E/Features",
        glue = "digifyE2E/cucumber",
        tags = "(@focus and (not @ignore))",
        monochrome = true,
        plugin = {"html:target/cucumber_reports/cucumberHTMLReport.html",
                "json:target/cucumber_reports/cucumberJSONReport.json"}
)

public class CucumberTestRunner extends AbstractTestNGCucumberTests {


}
