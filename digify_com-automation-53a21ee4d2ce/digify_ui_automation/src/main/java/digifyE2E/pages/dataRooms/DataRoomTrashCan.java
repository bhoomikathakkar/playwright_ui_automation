package digifyE2E.pages.dataRooms;

import com.microsoft.playwright.ElementHandle;
import digifyE2E.pages.CommonUIActions;
import digifyE2E.testDataManager.SharedDM;
import digifyE2E.utils.LogUtils;

import java.util.List;

import static org.testng.Assert.*;

public class DataRoomTrashCan extends CommonUIActions {

    static final String txtTrashCanHeader = "//section[@class='trash-can-header sticky']/span/h5";
    static final String txtTrashEmpty = getXpathForContainsText("Trash can is empty", "p");
    static final String trashedItemLocator = "//div[@class='visible']/datatable-body";

    public static void clickAndNavigateToTrashCan() {
        clickElement(getXpathForContainsText("Trash Can", "div"));
        waitUntilSelectorAppears(txtTrashCanHeader);
        assertEquals(getElementText(txtTrashCanHeader), "Trash Can");

    }

    public static void moveItemToTrashCan() {
        assertEquals(getElementText(CommonUIActions.txtCommonModalTitle), "Move file/folder to trash?");
        clickElement("//span[@class='txt' and contains(text(),'Move to trash')]");
        waitUntilSelectorAppears(getXpathForContainsText("Moved to trash.", "p"));
    }

    public static void validateTrashedItemInTrash(String trashedItem) {
        if (trashedItem.equalsIgnoreCase("file")) {
            assertEquals(getElementText(getXpathForContainsText(SharedDM.getNewFileNameWithExtension(), "span"))
                    , SharedDM.getNewFileNameWithExtension());
        } else if (trashedItem.equalsIgnoreCase("folder"))
            assertEquals(getElementText(getXpathForContainsText(DataRoomFolderManager.getLatestFolderName(), "span"))
                    , DataRoomFolderManager.getLatestFolderName());
        else {
            LogUtils.infoLog("Trashed item does not found in trash can");
        }
    }

    public static void moveAllItemsToTrashCan() {
        assertEquals(getElementText(CommonUIActions.txtCommonModalTitle), "Move files/folders to trash?");
        clickElement("//span[@class='txt' and contains(text(),'Move to trash')]");
        waitUntilSelectorAppears(getXpathForContainsText
                ("Moved to trash.", "p"));
        waitUntilSelectorAppears(getXpathForContainsText(
                "Drag and drop files or folders here", "p"));
    }

    public static void validateTrashedItemInDRFiles(String trashedItem) {
        clickElement(CreateDataRoomPage.lnkFilesTabInDR);
        page().reload();
        if (trashedItem.equalsIgnoreCase("file")) {
            assertEquals(getElementText(getXpathForContainsText(SharedDM.getNewFileNameWithExtension(), "a"))
                    , SharedDM.getNewFileNameWithExtension());
        } else if (trashedItem.equalsIgnoreCase("folder"))
            assertEquals(getElementText(getXpathForContainsText(DataRoomFolderManager.getLatestFolderName(), "a"))
                    , DataRoomFolderManager.getLatestFolderName());
        else {
            LogUtils.infoLog("Trashed item does not found in trash can");
        }
    }

    public static void validateEmptyTrashCan() {
        waitUntilSelectorAppears(txtTrashCanHeader);
        waitUntilSelectorAppears(txtTrashEmpty);
        assertTrue(isElementVisible(txtTrashEmpty), "Trash can is empty message appears");
        assertTrue(isElementVisible(getXpathForContainsText
                        ("Removed files/folders will appear here", "p")),
                "Message should be - Removed files/folders will appear here");
    }

    public static void emptyTrashNow() {
        String lnkEmptyTrashNow = "//section[@class='trash-can-header sticky']/p/a";
        assertEquals(
                getElementText(lnkEmptyTrashNow), "Empty trash now");
        clickElement(lnkEmptyTrashNow);
        CommonUIActions.selectDeleteInDeleteItemModal();
        validateEmptyTrashCan();
    }

    public static void selectRestoreBtnInModal(boolean isMultipleItems) {
        if (isMultipleItems) {
            waitUntilSelectorAppears(getXpathForContainsText
                    ("Restore items", "h4"));
            clickElement("//span[@class='txt']"); // TODO: 4/3/2024 create common
        } else {
            waitUntilSelectorAppears(getXpathForContainsText
                    ("Restore item", "h4"));
            clickElement("//span[@class='txt']");
        }
    }

    public static boolean getAllItemsFromTrashCan(int ItemCount) {
        boolean flag = false;
        List<ElementHandle> elements = page().querySelectorAll(trashedItemLocator);
        int count = elements.size();
        assertTrue(isElementVisible(trashedItemLocator));
        if (count == ItemCount) {
            flag = true;
        }
        return flag;
    }

    public static void validateTrashedItemAsEditor(int trashedItemCount) {
        waitUntilSelectorAppears(txtTrashCanHeader);
        waitUntilSelectorAppears(trashedItemLocator);
        if (getAllItemsFromTrashCan(trashedItemCount)) {
            LogUtils.infoLog(trashedItemCount + " trashed item appears in the trash can ");
        }
    }

    public static void selectFileInTrash() {
        clickElement("//*[contains(text(), '" + SharedDM.getNewFileNameWithExtension() + "')]");
    }

    public static void selectLatestFolderInTrash() {
        clickElement("//span[contains(text(), '" + DataRoomFolderManager.getLatestFolderName() + "')]/../../../..");
    }

    public static void selectPreviousFolderInTrash() {
        clickElement("//span[contains(text(), '" + DataRoomFolderManager.getPreviousFolderName() + "')]/../../../..");
    }


    public static void validateNoOriginalLocRestoreModal() {
        assertEquals(
                getElementText(CommonUIActions.btnCommonModalBody),
                "This file/folder's original location no longer exists. If you continue, this file/folder will be restored to the \"" + SharedDM.getDataRoomName() + "\" data room root level and inherit its permissions.");
    }

    public static void validatePermDeleteOptForEditor() {
        assertFalse(isElementVisible(getXpathForContainsText("Delete Permanently", "a")));
    }
}
