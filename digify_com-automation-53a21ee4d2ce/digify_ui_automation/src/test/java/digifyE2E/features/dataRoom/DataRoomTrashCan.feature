@dataRoomTrashCan @regression @dataRoom
Feature: Data room trash can functionality

  Background: Login with valid credentials
    Given I am on the login page

  @sanity
  Scenario Outline: Move file in a trash can
    When I login as "<userPlanType>"
    Given I create DR with "View" permission and upload a file "DocumentFile2.odt"
    When I select the first file checkbox on the page
    And I select "Trash" from the floating menu
    And I moved the item to trash can
    And I click on trash can and navigate to trash can
    Then I validate the trashed "file" in trash can
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType         |
      | teamPlanAdmin        |
      | businessPlanAdmin    |
      | upgradedProPlanAdmin |

  Scenario Outline: Move folder in a trash can
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I create a folder when ms editing is enabled
    And I select the first item in data room files tab
    And I select "Trash" from the floating menu
    And I moved the item to trash can
    And I click on trash can and navigate to trash can
    Then I validate the trashed "folder" in trash can
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  Scenario: As a pro plan dr owner, move folder in a trash can
    When I login as "upgradedProPlanAdmin"
    And I create a data room with default settings
    And I create a folder
    And I select the first item in data room files tab
    And I select "Trash" from the floating menu
    And I moved the item to trash can
    And I click on trash can and navigate to trash can
    Then I validate the trashed "folder" in trash can
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room

  Scenario Outline: Empty trash can as DR Owner
    When I login as "<userPlanType>"
    And I create DR with "Download (PDF)" permission and upload a file "OdsFile.ods"
    And I create a folder when ms editing is enabled
    And I select all items in the list
    And I select "Trash" from the floating menu
    When I moved all items to trash can
    And I click on trash can and navigate to trash can
    Then I validate the trashed "file" in trash can
    And I empty the trash can
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  Scenario: As a pro plan user, empty trash can as DR Owner
    When I login as "upgradedProPlanAdmin"
    And I create DR with "Download (PDF)" permission and upload a file "OdsFile.ods"
    And I create a folder
    And I select all items in the list
    And I select "Trash" from the floating menu
    When I moved all items to trash can
    And I click on trash can and navigate to trash can
    Then I validate the trashed "file" in trash can
    And I empty the trash can
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room

  @fileViewer @dev
  Scenario Outline: Delete a read marked file, restore it from the trash can, and ensure that it retains its status
    When I login as "<userPlanType>"
    And I create DR with "Download (PDF)" permission and upload a file "ImageFile.png"
    And I select the file
    And I view data room's file in the file viewer
    And I wait until file is loaded in file viewer
    And I click on folder tree icon in file viewer and select "data room"
    Then I validate that file is marked as read
    And I select the file
    And I select "Trash" from the floating menu
    When I moved the item to trash can
    And I click on trash can and navigate to trash can
    And I select the first trashed item
    And I select "Restore" from the floating menu
    And I restore the item
    Then I validate restored trashed "file" in data room files tab
    And I validate that file is marked as read
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType         |
      | teamPlanAdmin        |
      | businessPlanAdmin    |
      | upgradedProPlanAdmin |


  Scenario Outline: Delete unread file and folder, restore all trashed item from trash can and ensure that it retains its status
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I upload a "TextFile.txt" file in DR
    And I validate that file is marked as unread
    And I create a folder when ms editing is enabled
    And I validate that folder is marked as unread
    And I select all items in the list
    And I select "Trash" from the floating menu
    When I moved all items to trash can
    And I click on trash can and navigate to trash can
    And I select all items in the list
    And I select "Restore" from the floating menu
    And I restore the item
    Then I validate restored trashed "file" in data room files tab
    And I validate that file is marked as unread
    Then I validate restored trashed "folder" in data room files tab
    And I validate that folder is marked as unread
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  Scenario: As a pro plan user, delete unread file/folder and restore all trashed item from trash can and ensure that it retains its status
    When I login as "upgradedProPlanAdmin"
    And I create DR with "Download (Original)" permission and upload a file "TextFile.txt"
    And I validate that file is marked as unread
    And I create a folder
    And I validate that folder is marked as unread
    And I select all items in the list
    And I select "Trash" from the floating menu
    When I moved all items to trash can
    And I click on trash can and navigate to trash can
    And I select all items in the list
    And I select "Restore" from the floating menu
    And I restore the item
    Then I validate restored trashed "file" in data room files tab
    And I validate that file is marked as unread
    Then I validate restored trashed "folder" in data room files tab
    And I validate that folder is marked as unread
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room

  Scenario Outline: Trashed item as DR owner and login as editor, trashed item should not appear for editor in trash can
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I store the data room link in memory
    And I create a folder when ms editing is enabled
    And I select the first item in data room files tab
    And I select "Trash" from the floating menu
    When I moved the item to trash can
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "Edit" in data room
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    And I click on trash can and navigate to trash can
    Then I validate empty trash can
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  Scenario: Trashed item as a pro plan DR owner and login as editor, trashed item should not appear for editor in trash can
    When I login as "upgradedProPlanAdmin"
    And I create a data room with default settings
    And I store the data room link in memory
    And I create a folder
    And I select the first item in data room files tab
    And I select "Trash" from the floating menu
    When I moved the item to trash can
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "Edit" in data room
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    And I click on trash can and navigate to trash can
    Then I validate empty trash can
    And I logout from data room

  Scenario Outline: Trashed item as DR owner and editor, owner's trashed item should not appear for editor in trash can
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I store the data room link in memory
    And I create a folder when ms editing is enabled
    And I select the first item in data room files tab
    And I select "Trash" from the floating menu
    When I moved the item to trash can
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "Edit" in data room
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    And I upload a "ImageJPGFile.jpg" file in DR
    And I select the first item in data room files tab
    Then I select "Trash" from the floating menu
    And I moved the item to trash can
    And I click on trash can and navigate to trash can
    And Editor's 1 trashed item should appear in the trash can
    Then I validate the trashed "file" in trash can
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |


  Scenario: Trashed item as a pro plan DR owner and editor, owner's trashed item should not appear for editor in trash can
    When I login as "upgradedProPlanAdmin"
    And I create a data room with default settings
    And I store the data room link in memory
    And I create a folder
    And I select the first item in data room files tab
    And I select "Trash" from the floating menu
    When I moved the item to trash can
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "Edit" in data room
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    And I upload a "WMVTestFile.wmv" file in DR
    And I select the first item in data room files tab
    Then I select "Trash" from the floating menu
    And I moved the item to trash can
    And I click on trash can and navigate to trash can
    And Editor's 1 trashed item should appear in the trash can
    Then I validate the trashed "file" in trash can
    And I logout from data room

  Scenario Outline: Permanently delete the trashed items from trash can
    When I login as "<userPlanType>"
    And I create DR with "Download (Original)" and upload a file "ImageFile.png"
    And I create a folder when ms editing is enabled
    And I select all items in the list
    And I select "Trash" from the floating menu
    When I moved all items to trash can
    And I click on trash can and navigate to trash can
    And I select all items in the list
    And I select "delete permanently" from the floating menu
    Then I delete items permanently from trash can
    And I validate empty trash can
    And I navigate to data room "files tab from trash can" page
    Then I validate that files tab is blank
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  Scenario: As a pro plan user, permanently delete the trashed items from trash can
    When I login as "upgradedProPlanAdmin"
    And I create DR with "Download (Original)" and upload a file "ImageFile.png"
    And I create a folder
    And I select all items in the list
    And I select "Trash" from the floating menu
    When I moved all items to trash can
    And I click on trash can and navigate to trash can
    And I select all items in the list
    And I select "delete permanently" from the floating menu
    Then I delete items permanently from trash can
    And I validate empty trash can
    And I navigate to data room "files tab from trash can" page
    Then I validate that files tab is blank
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room

  Scenario Outline: File/Folder moves to trash should retain previous read/unread status
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I upload a "ImageFile.png" file in DR
    Then I validate that file is marked as unread
    And I create a folder when ms editing is enabled
    Then I validate that folder is marked as unread
    And I view the folder
    And I navigate to data room file's tab from folder tree
    Then I validate folder should be marked as read
    And I select all items in the list
    And I select "Trash" from the floating menu
    When I moved all items to trash can
    And I click on trash can and navigate to trash can
    Then I validate that file should be marked as unread in trash can
    And I validate that folder should be marked as read in trash can
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  Scenario: As a pro plan user, move file/Folder to trash and validate that it should retain previous read/unread status
    When I login as "proPlanAdmin"
    And I create a data room with default settings
    And I upload a "ImageFile.png" file in DR
    Then I validate that file is marked as unread
    And I create a folder
    Then I validate that folder is marked as unread
    And I view the folder
    And I navigate to data room file's tab from folder tree
    Then I validate folder should be marked as read
    And I select all items in the list
    And I select "Trash" from the floating menu
    When I moved all items to trash can
    And I click on trash can and navigate to trash can
    Then I validate that file should be marked as unread in trash can
    And I validate that folder should be marked as read in trash can

  Scenario Outline: If parent folder does not exists then the restored files will go to DR root level
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I store the data room link in memory
    And I create a folder when ms editing is enabled
    And I view the latest folder
    Then I upload a "ImageFile.png" file in DR
    And I select the file
    And I select "Trash" from the floating menu
    And I moved the item to trash can
    And I navigate to data room "files" page
    And I select the latest folder
    And I select "Trash" from the floating menu
    And I moved the item to trash can
    And I click on trash can and navigate to trash can
    Then I select file from trash
    And I select "Restore" from the floating menu
    And I validate the content of restore modal when the original location of the file or folder does not exist
    And I restore the item
    Then I validate restored trashed "file" in data room files tab
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  Scenario Outline: If parent folder does not exists then the restored folder will go to DR root level
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I store the data room link in memory
    And I create a folder when ms editing is enabled
    And I view the latest folder
    Then I create a folder when ms editing is enabled
    And I select the latest folder
    And I select "Trash" from the floating menu
    And I moved the item to trash can
    And I navigate to data room "files" page
    And I select the previously created folder
    And I select "Trash" from the floating menu
    And I moved the item to trash can
    And I click on trash can and navigate to trash can
    And I select the latest created folder in trash can
    And I select "Restore" from the floating menu
    And I validate the content of restore modal when the original location of the file or folder does not exist
    And I restore the item
    Then I validate restored trashed "folder" in data room files tab
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  Scenario: Validate as pro plan user, if parent folder does not exists then the restored folder will go to DR root level
    When I login as "proPlanAdmin"
    And I create a data room with default settings
    And I store the data room link in memory
    And I create a folder
    And I view the latest folder
    Then I create a folder
    And I select the latest folder
    And I select "Trash" from the floating menu
    And I moved the item to trash can
    And I navigate to data room "files" page
    And I select the previously created folder
    And I select "Trash" from the floating menu
    And I moved the item to trash can
    And I click on trash can and navigate to trash can
    And I select the latest created folder in trash can
    And I select "Restore" from the floating menu
    And I validate the content of restore modal when the original location of the file or folder does not exist
    And I restore the item
    Then I validate restored trashed "folder" in data room files tab
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room

  Scenario Outline: Editor can only restore but cannot delete any file or folder permanently
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I store the data room link in memory
    And I create a folder when ms editing is enabled
    And I select the first item in data room files tab
    And I select "Trash" from the floating menu
    When I moved the item to trash can
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "Edit" in data room
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    And I upload a "ImageFile.png" file in DR
    And I select the first item in data room files tab
    Then I select "Trash" from the floating menu
    And I moved the item to trash can
    And I click on trash can and navigate to trash can
    And I select the first trashed item
    Then I validate that permanently delete option does not appear for editor
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |


  Scenario: For a pro plan user, add a guest as editor and validate that editor can only restore but cannot delete any file or folder permanently
    When I login as "proPlanAdmin"
    And I create a data room with default settings
    And I store the data room link in memory
    And I create a folder
    And I select the first item in data room files tab
    And I select "Trash" from the floating menu
    When I moved the item to trash can
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "Edit" in data room
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    And I upload a "ImageFile.png" file in DR
    And I select the first item in data room files tab
    Then I select "Trash" from the floating menu
    And I moved the item to trash can
    And I click on trash can and navigate to trash can
    And I select the first trashed item
    Then I validate that permanently delete option does not appear for editor
    And I logout from data room




