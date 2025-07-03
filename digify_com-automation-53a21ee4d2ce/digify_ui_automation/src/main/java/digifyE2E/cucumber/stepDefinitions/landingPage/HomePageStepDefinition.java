package digifyE2E.cucumber.stepDefinitions.landingPage;

import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import digifyE2E.pages.documentSecurity.ManageSentFilesPage;
import digifyE2E.pages.landingPage.HomePage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class HomePageStepDefinition extends CommonHelper {

    @Then("I validate the following card on dashboard")
    @Then("I validate following blank card on the dashboard")
    public void iValidateBlankStateCardOnTheDashboard(DataTable dataTable) {
        HomePage.validateCardTypeOnDashboard(dataTable);
    }

    @Then("I select create data room button on {string} card")
    @And("I select send files button on {string} card")
    public void iClickOnButtonTypeOnCard(String cardType) {
        HomePage.selectCardButton(cardType);
    }

    @And("I navigate back to home page by clicking on home button")
    public void iNavigateBackToHomePageByClickingOnHomeButton() {
        HomePage.clickOnHomeButton();
    }

    @Then("I validate the recent shared file in document security card")
    public void iValidateTheRecentSharedFileInDocumentSecurityCard() {
        HomePage.assertRecentFileInDSCard();
    }

    @And("I select view all and navigates to MSF")
    public void iSelectViewAllAndNavigatesToMSF() {
        HomePage.selectDSViewAll();
    }

    @And("I select view analytics for the file and navigates to file analytics")
    public void iSelectViewAnalyticsForTheFileAndNavigatesToFileAnalytics() {
        HomePage.clickFileAnalyticsOnDSCard();
        ManageSentFilesPage.checkFileAnalyticsTitle();
    }

    @And("I click on {string} and validate page navigation")
    public void iClickOnShortcutIconAndValidatePageNavigation(String shortcutIcon) {
        HomePage.selectShortcutIcons(shortcutIcon);
    }
}
