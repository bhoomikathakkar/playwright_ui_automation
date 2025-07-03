package digifyE2E.cucumber.stepDefinitions.dataRoom;

import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import digifyE2E.pages.dataRooms.DRSearchPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import java.util.Arrays;

public class DRSearchStepDefinition extends CommonHelper {

    @And("I navigate to search page")
    public void iNavigateToSearchPage() {
        DRSearchPage.navigateToDRSearchPage();
    }

    @Then("I search a file using file name")
    public void iSearchAFileUsing() {
        DRSearchPage.searchUsingFileName();
    }

    @And("I validate file in search result {string}")
    public void iValidateFileInSearchResult(String fileExtension) {
        DRSearchPage.validateFileInSearchResult(fileExtension);
    }

    @Then("I apply filter Type {string} and validate result set")
    public void iApplyFilterType(String filterType) {
        DRSearchPage.applyFilterType(Arrays.asList(filterType.split(",")));
    }

    @And("I click on search button")
    public void iClickOnSearchButton() {
        DRSearchPage.clickOnSearchButton();
    }

    @Then("I validate {string} return in search result")
    public void iValidateReturnInSearchResult(String noOfFiles) {
        DRSearchPage.validateSearchResult(noOfFiles);
    }

    @Then("I validate floating menu option {string}")
    @Then("I validate context menu option {string}")
    public void iValidateFloatingContextMenuOption(String menuOption) {
        DRSearchPage.validateDRSearchFloatingContextMenuOption(Arrays.asList(menuOption.split(",")));
    }
}
