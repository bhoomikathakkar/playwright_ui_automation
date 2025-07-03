package digifyE2E.cucumber.stepDefinitions.dataRoom;

import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import digifyE2E.pages.dataRooms.DataRoomTrashCan;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class TrashCanStepDefinition extends CommonHelper {

    @And("I moved the item to trash can")
    public void iMovedTheItemToTrashCan() {
        DataRoomTrashCan.moveItemToTrashCan();
    }

    @And("I click on trash can and navigate to trash can")
    public void iClickOnTrashCanAndNavigateToTrashCan() {
        DataRoomTrashCan.clickAndNavigateToTrashCan();
    }

    @Then("I validate the trashed {string} in trash can")
    public void iValidateTheTrashedItemInTrashCan(String trashedItem) {
        DataRoomTrashCan.validateTrashedItemInTrash(trashedItem);
    }

    @And("I empty the trash can")
    public void iEmptyTheTrashCan() {
        DataRoomTrashCan.emptyTrashNow();
    }

    @And("I moved all items to trash can")
    public void iMovedAllItemsToTrashCan() {
        DataRoomTrashCan.moveAllItemsToTrashCan();
    }

    @And("I restore the item")
    public void iRestoreTheItem() {
        DataRoomTrashCan.selectRestoreBtnInModal(false);
    }

    @And("I restore the items")
    public void iRestoreTheItems() {
        DataRoomTrashCan.selectRestoreBtnInModal(true);
    }

    @Then("I validate restored trashed {string} in data room files tab")
    public void iValidateRestoredTrashedInDataRoomFilesTab(String trashedItem) {
        DataRoomTrashCan.validateTrashedItemInDRFiles(trashedItem);
    }

    @And("I validate empty trash can")
    public void iValidateEmptyTrashCan() {
        DataRoomTrashCan.validateEmptyTrashCan();
    }

    @And("Editor's {int} trashed item should appear in the trash can")
    public void EditorSTrashedItemShouldAppearInTheTrashCan(int trashedItemCount) {
        DataRoomTrashCan.validateTrashedItemAsEditor(trashedItemCount);
    }

    @Then("I select file from trash")
    public void iSelectFileFromTrash() {
        DataRoomTrashCan.selectFileInTrash();
    }

    @And("I validate the content of restore modal when the original location of the file or folder does not exist")
    public void iValidateTheContentOfRestoreModalWhenTheOriginalLocationOfTheFileOrFolderDoesNotExist() {
        DataRoomTrashCan.validateNoOriginalLocRestoreModal();
    }

    @And("I select the latest created folder in trash can")
    @And("I select the latest created folder")
    public void iSelectTheLatestCreatedFolderInTrashCan() {
        DataRoomTrashCan.selectLatestFolderInTrash();
    }

    @And("I select the previously created folder in trash")
    public void iSelectThePreviouslyCreatedFolderInTrash() {
        DataRoomTrashCan.selectPreviousFolderInTrash();
    }

    @Then("I validate that permanently delete option does not appear for editor")
    public void iValidateThatPermanentlyDeleteOptionDoesNotAppearForEditor() {
        DataRoomTrashCan.validatePermDeleteOptForEditor();
    }
}
