package digifyE2E.pages.dataRooms;

import digifyE2E.pages.CommonUIActions;
import digifyE2E.utils.LogUtils;
import digifyE2E.utils.RandomUtils;

import java.util.Stack;

import static org.testng.Assert.assertTrue;

public class DataRoomFolderManager extends CommonUIActions {

    private static final Stack<String> folderNameStack = new Stack<>();

    public static void createFolder(boolean isEditingEnabled) throws Exception {
        String folderName = "TestFolder" + RandomUtils.getRandomString(4, false);
        String folderNameValidationLocator = getXpathForTextEquals(" " + folderName + " ", "a");
        waitUntilSelectorAppears(CreateDataRoomPage.txtDRFiles);
        try {
            if (isEditingEnabled) {
                clickElement("//button[@id='dataroomfile-new-dropdown']");
                clickElement("//div[@aria-labelledby='dataroomfile-new-dropdown']/div");
            } else {
                clickElement(getXpathForContainsText("New Folder", "button"));
            }
            waitUntilSelectorAppears(getXpathForTextEquals("New folder", "h4"));
            folderNameStack.push(folderName);  // Add folder to the stack
            inputIntoTextField("//input[@placeholder='Enter folder name']", folderName);
            clickElement(CommonUIActions.btnSubmit);
            waitUntilSelectorAppears(folderNameValidationLocator);
            assertTrue(isElementVisible(folderNameValidationLocator),
                    "Folder name not visible in Data Room.");
        } catch (Exception e) {
            throw new Exception("Error creating folder: " + folderName, e);
        }
    }

    // Get the latest generated folder name
    public static String getLatestFolderName() {
        LogUtils.infoLog("Checking latest folder name. Is stack empty? " + folderNameStack.isEmpty());
        if (!folderNameStack.isEmpty()) {
            String latestFolderName = folderNameStack.peek();
            LogUtils.infoLog("Latest folder name: " + latestFolderName);
            return latestFolderName;
        } else {
            LogUtils.infoLog("No folder name found, stack is empty.");
            return null;
        }
    }


    // Get the second last generated folder name
    public static String getPreviousFolderName() {
        return folderNameStack.size() > 1 ? folderNameStack.get(folderNameStack.size() - 2) : null;
    }

    // Get a specific folder name by index
    public static String getFolderNameByIndex(int index) {
        return (index >= 0 && index < folderNameStack.size()) ? folderNameStack.toArray(new String[0])[index] : null;
    }
}
