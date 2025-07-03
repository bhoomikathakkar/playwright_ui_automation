package digifyE2E.cucumber.stepDefinitions.dataRoom;

import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import digifyE2E.pages.dataRooms.CreateDataRoomPage;
import digifyE2E.pages.dataRooms.ManageDataRoomPage;
import digifyE2E.pages.documentSecurity.ManageSentFilesPage;
import digifyE2E.pages.documentSecurity.SendFilesPage;
import digifyE2E.testDataManager.SharedDM;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ManageDataRoomStepDefinition extends CommonHelper {

    @Then("I navigate to manage data room")
    public void i_navigate_to_manage_data_room() {
        ManageDataRoomPage.openDRListInDR();
    }

    @And("I replace a file using {string} option from {string} list in data room")
    public void iReplaceFileUsingOptionFromList(String importOption, String sourceFile) {
        ManageSentFilesPage.selectSendFileImportOption(importOption, false);
        SendFilesPage.selectSingleFileFromIEFModal(sourceFile, false);
    }

    @Then("I search the data room")
    public void i_search_the_data_room() {
        ManageDataRoomPage.searchDRFromManageDR(SharedDM.getDataRoomName());
    }

    @Then("I click on the first searched data room")
    public void i_click_on_the_first_searched_data_room() {
        ManageDataRoomPage.clickOnFirstSearchedDRInManageDR();
        Assert.assertEquals(CreateDataRoomPage.getDrNameHeading(), SharedDM.getDataRoomName());
    }

    @And("I select {string} from the sort by filter in Manage data room")
    public void iSelectFromTheSortByFilterInManageDataRoom(String filterTypeInSortByFilter) {
        manageDataRoomPage.selectFilterOptionInSortByMDR(filterTypeInSortByFilter);
    }

    @Then("I validate cloned data room in manage data room and opened it")
    public void iValidateClonedDataRoomInManageDataRoomAndOpenedIt() {
        ManageDataRoomPage.openClonedDataRoomFromManageDR(SharedDM.getClonedDataRoomName());
    }

    @Given("I navigate to manage data room page")
    public void userNavigateToManageDataRoomPage() {
        ManageDataRoomPage.navigateToManageDRPage();
    }

    @And("I delete all data rooms from manage data room page")
    public void userDeleteTheDataRooms() {
        ManageDataRoomPage.deleteAllDR();
    }

    @And("I unshared data room")
    public void iUnsharedDataRoom() {
        ManageDataRoomPage.UnsharedDR();
    }

    @And("I click on the brand logo and navigated to home page")
    public void iClickOnTheBrandLogoAndNavigatedToHomePage() {
        ManageDataRoomPage.clickOnBrandLogo();
    }

    @Then("I select {string} from the floating menu and switch to {string} tab in DR")
    public void iSelectFromFloatingMenuAndSwitchToDrTab(String floatingMenuOptionType, String drTabType) {
        clickAndSwitchToNewTab(ManageDataRoomPage.getMenuLocator(floatingMenuOptionType, false));
        CreateDataRoomPage.validateDrTabs(Collections.singletonList(drTabType));
    }

    @Then("I select {string} in the Manage DR floating menu to open a modal")
    public void iSelectInTheManageDRFloatingMenuToOpenAModal(String floatingMenuOptionType) {
        ManageDataRoomPage.selectMenuOptionToOpenModal(floatingMenuOptionType, false);
    }

    @And("I verify that {string} option in floating menu is not visible")
    public void iVerifyThatOptionInFloatingMenuIsNotVisible(String optionType) {
        List<String> optionList = Arrays.asList(optionType.split(","));
        ManageDataRoomPage.verifyMenuNotVisible(optionList, false);
    }

    @Then("I revoke my own access to this data room")
    public void iRevokeMyOwnAccessToThisDataRoom() {
        ManageDataRoomPage.revokeOwnAccessOfDR();
    }

    @And("I view the existing data room")
    public void iViewTheExistingDataRoom() {
        ManageDataRoomPage.viewFirstExistingDRFromManageDR();
    }

    @Then("I select {string} from the context menu and switch to {string} tab in DR")
    public void iSelectFromTheContextMenuAndSwitchToTabInDR(String contextMenuOptionType, String drTabType) {
        clickAndSwitchToNewTab(ManageDataRoomPage.getMenuLocator(contextMenuOptionType, true));
        CreateDataRoomPage.validateDrTabs(Collections.singletonList(drTabType));
    }

    @Then("I select {string} in the Manage DR context menu to open a modal")
    public void iSelectInTheManageDRContextMenuToOpenAModal(String contextMenuOption) {
        ManageDataRoomPage.selectMenuOptionToOpenModal(contextMenuOption, true);
    }

    @And("I verify that {string} option in context menu is not visible")
    public void iVerifyThatOptionInContextMenuIsNotVisible(String optionType) {
        List<String> optionList = Arrays.asList(optionType.split(","));
        ManageDataRoomPage.verifyMenuNotVisible(optionList, false);
    }
}
