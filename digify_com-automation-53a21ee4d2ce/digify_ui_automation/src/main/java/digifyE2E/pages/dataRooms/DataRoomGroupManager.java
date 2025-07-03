package digifyE2E.pages.dataRooms;

import digifyE2E.pages.CommonUIActions;
import digifyE2E.utils.RandomUtils;

import java.util.Stack;

public class DataRoomGroupManager extends CommonUIActions {


    private static final Stack<String> groupNameStack = new Stack<>();

    public static void createGroup(Boolean isPermissionRequired, String grpPermissionType, Boolean isExpiryEnabled, Boolean isExistingGuestRequired, String guestUsername) {
        String txtInputGroupName = "(//div[@class='setting-input'])[1]/div/input";
        waitUntilSelectorAppears(DataRoomGroupsPage.txtGroupTabHeading);
        clickElement("//button[contains(text(),'Create group')]");
        waitUntilSelectorAppears(DataRoomGroupsPage.txtGroupTitle);

        String dataRoomGroupName = "DRGroup" + "-" + RandomUtils.getRandomString(4, false);
        groupNameStack.push(dataRoomGroupName);
        inputIntoTextField(txtInputGroupName, dataRoomGroupName);

        if (isPermissionRequired) {
            if (grpPermissionType != null && !grpPermissionType.isEmpty()) {
                DataRoomGroupsPage.selectPermissionSettingsInGroup(grpPermissionType);
            } else {
                throw new IllegalArgumentException("Permission type cannot be null or empty when isPermissionRequired is true.");
            }
        }
        if (isExpiryEnabled) {
            DataRoomGroupsPage.selectAccessExpiryInGroup();
        }
        if (isExistingGuestRequired) {
            if (guestUsername != null && !guestUsername.isEmpty()) {
                DataRoomGroupsPage.addSingleGuestInGroup(guestUsername, true);
            } else {
                throw new IllegalArgumentException("Guest does not exist in the data room.");
            }
        }
    }

    // Method to get the last/latest generated group name
    public static String getLatestGroupName() {
        return groupNameStack.isEmpty() ? null : groupNameStack.peek();
    }

    // Method to get the second last generated group name
    public static String getPreviousGroupName() {
        return groupNameStack.size() > 1 ? groupNameStack.get(groupNameStack.size() - 2) : null;
    }

    // Method to get a specific group name by index (0 = first, 1 = second, etc.)
    public static String getGroupNameByIndex(int index) {
        return (index >= 0 && index < groupNameStack.size()) ? groupNameStack.get(index) : null;
    }

    // Method to get group permission
    public static String getGroupPermission(String groupName) {
        return getElementText(
                "//div[@class='file-name' and contains(.,'" + groupName + "')]/../../../../../../../datatable-body-cell[3]/div/div/div/div/a");
    }

    // Method to get the latest group permission
    public static String getCurrentGroupPermission() {
        String latestGroupName = getLatestGroupName();
        return latestGroupName != null ? getGroupPermission(latestGroupName) : null;
    }

    // Method to get the previous group permission
    public static String getPreviousGroupPermission() {
        String previousGroupName = getPreviousGroupName();
        return previousGroupName != null ? getGroupPermission(previousGroupName) : null;
    }

    // Method to get group permission by index
    public static String getGroupPermissionByIndex(int index) {
        String groupName = getGroupNameByIndex(index);
        return groupName != null ? getGroupPermission(groupName) : null;
    }
}
