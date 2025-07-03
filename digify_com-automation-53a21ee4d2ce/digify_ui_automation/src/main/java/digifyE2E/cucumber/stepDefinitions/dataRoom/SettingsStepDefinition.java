package digifyE2E.cucumber.stepDefinitions.dataRoom;

import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import digifyE2E.pages.dataRooms.CreateDataRoomPage;
import digifyE2E.pages.dataRooms.SettingsPage;
import digifyE2E.testDataManager.SharedDM;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Arrays;

public class SettingsStepDefinition extends CommonHelper {

    @And("I select the latest group in dr default guest permission")
    public void iSelectTheLatestGroupInDrDefaultGuestPermission() {
        SettingsPage.getLatestGroupInDRSettings();
    }

    @And("I validate no team member is a guest of this DR modal in data room transfer")
    public void iValidateNoTeamMemberIsAGuestOfThisDRModalInDataRoomTransfer() {
        CreateDataRoomPage.navigateToDataRoomTabs("settings");
        SettingsPage.goToAdvancedInDRAndSelectOption("transfer dr when no team member is a guest");
    }

    @And("I updated feature to {string} in DR Settings")
    public void iUpdatedFeatureToInDRSettings(String featureToBeEdited) {
        CreateDataRoomPage.enableDRFeatures(Arrays.asList(featureToBeEdited.split(",")));
    }

    @Then("I validate dynamic watermark additional settings in data room settings page")
    public void iValidateDynamicWatermarkAdditionalSettingsInDataRoomSettingsPage(DataTable dataTable) {
        SettingsPage.validateWatermarkSettingsOnDRSettings(dataTable);
    }

    @And("I save the data room settings")
    public void iSaveTheDataRoomSettings() {
        SettingsPage.saveDrSettings();
    }

    @When("I update data room settings {string}")
    @When("I update data room permission to {string}")
    public void iUpdateDataRoomSettingsEditDrFeatureType(String drSettingsFeatures) {
        CreateDataRoomPage.enableDRFeatures(Arrays.asList(drSettingsFeatures.split(",")));
    }

    @And("I create cloned data room with default settings")
    public void iCreateClonedDataRoomWithDefaultSettings() {
        CreateDataRoomPage.createClonedDataRoom(SharedDM.getClonedDataRoomName(), null);
    }

    @And("I {string} data room from advanced settings")
    public void iDataRoomFromAdvancedSettings(String advanceSettingsOptions) {
        CreateDataRoomPage.navigateToDataRoomTabs("settings");
        SettingsPage.goToAdvancedInDRAndSelectOption(advanceSettingsOptions);
    }

    @Then("I verify the required additional info not checked")
    public void iVerifyTheRequiredAdditionalInfoNotChecked() {
        CreateDataRoomPage.validateRAICheckboxOnCloneDRPage();
    }
}
