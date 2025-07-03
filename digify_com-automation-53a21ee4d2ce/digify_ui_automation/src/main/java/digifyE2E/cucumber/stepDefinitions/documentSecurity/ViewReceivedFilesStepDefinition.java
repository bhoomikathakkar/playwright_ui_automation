package digifyE2E.cucumber.stepDefinitions.documentSecurity;

import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import digifyE2E.pages.documentSecurity.ViewReceivedFilesPage;
import digifyE2E.testDataManager.SharedDM;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class ViewReceivedFilesStepDefinition extends CommonHelper {

    @And("I delete all received files")
    public void iDeleteAllReceivedFiles() {
        viewReceivedFilesPage.doRemoveFilesFromViewReceivedFiles();
    }

    @And("I select {string} from the sort by filter in View Received Files")
    public void iSelectFromTheSortByFilterInViewReceivedFiles(String sortByFilterOption) {
        ViewReceivedFilesPage.setFilterOptionInSortBy(sortByFilterOption);
    }

    @Then("I select {string} from the context menu")
    public void iSelectOptionInTheContextMenu(String contextMenuOption) {
        ViewReceivedFilesPage.selectMenuOption(contextMenuOption, true, false, false);
    }

    @Then("I validate that the download option is hidden in the context menu")
    public void iValidateHiddenDownloadOptionInTheContextMenu() {
        ViewReceivedFilesPage.selectMenuOption("download", true, false, true);
    }

    @Then("I validate that the download option is hidden in the floating menu")
    public void iValidateHiddenDownloadOptionInTheFloatingMenu() {
        ViewReceivedFilesPage.selectMenuOption("download", false, false, true);
    }

    @Then("I copy the file link and visit the link in new tab")
    public void iCopyTheFileLinkAndVisitTheLinkInNewTab() {
        ViewReceivedFilesPage.copyAndSaveReceivedLink();
        openNewTabAndSwitchToPage();
        visit(SharedDM.getReceivedFileLink(), false);
    }

    @Then("I remove the file")
    public void iRemoveTheFile() {
        viewReceivedFilesPage.removeFile();
    }

    @Then("I select the download option from the context menu and cancel the TOA")
    public void iSelectTheDownloadOptionFromTheContextMenuAndCancelTheTOA() {
        ViewReceivedFilesPage.selectMenuOption("download", true, false, false);
    }

    @Then("I select the Download option from the context menu and agree to the TOA")
    public void iSelectTheDownloadOptionFromTheContextMenuAndAgreeToTheTOA() {
        ViewReceivedFilesPage.selectMenuOption("download", true, true, false);
    }

    @Then("I select {string} option from the floating menu in view received file")
    public void iSelectTheOptionFromTheFloatingMenuInViewReceivedFile(String floatingMenuOption) {
        ViewReceivedFilesPage.selectMenuOption(floatingMenuOption, false, false, false);
    }

    @And("I verify files are present in view received files")
    public void iNavigateVRFToAndVerifyFilesAre() {
        viewReceivedFilesPage.validateReceivedFiles("present");
    }

    @And("I verify files are not present in view received files")
    public void iVerifyFilesAreNotPresent() {
        viewReceivedFilesPage.validateReceivedFiles("not present");
    }
}
