@dataRoomFiles @regression @dataRoom
Feature: Verify data room files functionality

  Background: Navigate to Digify login page
    Given I am on the login page

  @smoke @prod @sanity
  Scenario Outline: In a data room, move file in a folder
    When I login as "<userPlanType>"
    And I create DR with default settings and upload a "PdfFile.pdf" file
    And I create a folder when ms editing is enabled
    Then I move file in a folder
    And I validate moved file in a folder
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  @smoke @prod
  Scenario: As a pro plan user, move file in a folder in a data room
    When I login as "proPlanAdmin"
    And I create DR with default settings and upload a "PdfFile.pdf" file
    And I create a folder
    Then I move file in a folder
    And I validate moved file in a folder
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room

  @smoke @prod @sanity
  Scenario Outline: In a data room, copy file in a folder
    When I login as "<userPlanType>"
    And I create DR with default settings and upload a "PdfFile.pdf" file
    And I create a folder when ms editing is enabled
    Then I copy file in a folder
    And I validate copied file in a folder
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  @smoke @prod
  Scenario: As pro plan user, copy file in a folder in a DR
    When I login as "proPlanAdmin"
    And I create DR with default settings and upload a "PdfFile.pdf" file
    And I create a folder
    Then I copy file in a folder
    And I validate copied file in a folder
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room

  @fileViewer @dev
  Scenario Outline: New file/folder status should be unread and when owner view the file/folder, it should be marked as read
    When I login as "<userPlanType>"
    And I create DR with default settings and upload a "TextFile.txt" file
    Then I validate that file is marked as unread
    And I select the first file checkbox on the page
    When I view data room's file in the file viewer
    And I wait until file is loaded in file viewer
    And I validate the file name
    And I validate "<validateFileViewerBtn>" in file viewer
    And I click on folder tree icon in file viewer and select "data room"
    Then I validate that file is marked as read
    And I create a folder when ms editing is enabled
    Then I validate that folder is marked as unread
    And I view the folder
    And I navigate to data room file's tab from folder tree
    Then I validate folder should be marked as read
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    Then I logout from data room
    Examples:
      | userPlanType      | validateFileViewerBtn                            |
      | teamPlanAdmin     | enabled-->print button,enabled-->download button |
      | businessPlanAdmin | enabled-->print button,enabled-->download button |

  @fileViewer @dev
  Scenario: As pro plan owner, view the new file/folder when it is unread, file should be marked as read
    When I login as "proPlanAdmin"
    And I create DR with default settings and upload a "TextFile.txt" file
    Then I validate that file is marked as unread
    And I select the first file checkbox on the page
    When I view data room's file in the file viewer
    And I wait until file is loaded in file viewer
    And I validate the file name
    And I validate "enabled-->print button,enabled-->download button" in file viewer
    And I click on folder tree icon in file viewer and select "data room"
    Then I validate that file is marked as read
    And I create a folder
    Then I validate that folder is marked as unread
    And I view the folder
    And I navigate to data room file's tab from folder tree
    Then I validate folder should be marked as read
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    Then I logout from data room

  @fileViewer @dev
  Scenario Outline: Read copied file should be marked as unread when copied to a folder
    When I login as "<userPlanType>"
    And I create DR with "Download (Original)" permission and upload a file "PdfFile2KB.pdf"
    And I select the first file checkbox on the page
    When I view data room's file in the file viewer
    And I wait until file is loaded in file viewer
    And I click on folder tree icon in file viewer and select "data room"
    Then I validate that file is marked as read
    And I create a folder when ms editing is enabled
    Then I copy file in a folder
    And I view the folder
    Then I validate that file is marked as unread
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    Then I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  @fileViewer @dev
  Scenario: As pro plan user, read copied file should be marked as unread when copied to a folder
    When I login as "proPlanAdmin"
    And I create DR with "Download (Original)" permission and upload a file "PdfFile2KB.pdf"
    And I select the first file checkbox on the page
    When I view data room's file in the file viewer
    And I wait until file is loaded in file viewer
    And I click on folder tree icon in file viewer and select "data room"
    Then I validate that file is marked as read
    And I create a folder
    Then I copy file in a folder
    And I view the folder
    Then I validate that file is marked as unread
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    Then I logout from data room

  @fileViewer @dev
  Scenario Outline: Move read file, it should retain it's original status
    When I login as "<userPlanType>"
    And I create DR with "Edit" permission and upload a file "ImageJPEGFile.jpeg"
    And I select the file
    And I view data room's file in the file viewer
    And I wait until file is loaded in file viewer
    And I click on folder tree icon in file viewer and select "data room"
    And I create a folder when ms editing is enabled
    Then I move file in a folder
    And I view the folder
    Then I validate that file is marked as read
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    Then I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  @fileViewer @dev
  Scenario: As pro plan user, move read file, it should retain it's original status
    When I login as "proPlanAdmin"
    And I create DR with "Edit" permission and upload a file "ImageJPEGFile.jpeg"
    And I select the file
    And I view data room's file in the file viewer
    And I wait until file is loaded in file viewer
    And I click on folder tree icon in file viewer and select "data room"
    And I create a folder
    Then I move file in a folder
    And I view the folder
    Then I validate that file is marked as read
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    Then I logout from data room

  @fileViewer @dev @update #To view the replaced file after update
  Scenario Outline: When a read file is replaced in version history then file is marked as unread in DR Files.
    When I login as "<userPlanType>"
    And I create DR with "Edit" permission and upload a file "TextFile.txt"
    And I select the file
    And I view data room's file in the file viewer
    And I wait until file is loaded in file viewer
    And I click on folder tree icon in file viewer and select "data room"
    And I select the file
    Then I select "version history" option from More floating menu in DR
    And I click on Replace file button
    Then I upload the same file in replace new version modal and replace the file
    And I click on back button from version history page
    Then I validate that file is marked as unread
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |


  Scenario Outline: Verify that user should able to replace file from existing DS file using import from digify option
    Given I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "<fileType>" file
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to Manage Sent File page
    And I wait until file is encrypted in manage sent file
    When I create DR with "Edit" permission and upload a file "<replaceFileType>"
    And I select the file
    And I select "version history" option from More floating menu in DR
    And I click on Replace file button
    Then I replace a file using "import from existing files dropdown" option from "sent file" list in data room
    And I click on replace file and skip notification button on replace file modal
    And I click on back button from version history page
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    Then I logout from data room
    Examples:
      | userPlanType      | fileType                                     | replaceFileType |
      | teamPlanAdmin     | ImageFile.png,TextFile.txt,AudioTestFile.wav | ImageFile.png   |
      | businessPlanAdmin | CsvFile.csv,ImageFile.png                    | CsvFile.csv     |
      | proPlanAdmin      | ImageFile.png,ImageFile.png                  | ImageFile.png   |


  Scenario Outline: Verify that user should able to replace file from existing DR file using import from digify option
    Given I login as "<userPlanType>"
    And I navigate to manage data room page
    And I delete all data rooms from manage data room page
    And I create DR with "Edit" permission and upload a file "<fileType>"
    And I navigate to manage data room
    When I create DR with "Edit" and upload a file "<replaceFileType>"
    And I select the file
    And I select "version history" option from More floating menu in DR
    And I click on Replace file button
    Then I replace a file using "import from existing files logo" option from "data room" list in data room
    And I click on replace file and skip notification button on replace file modal
    And I click on back button from version history page
    And I navigate to manage data room
    And I select all data rooms
    And I select "delete" in the Manage DR floating menu to open a modal
    Then I logout from data room
    Examples:
      | userPlanType      | fileType                                     | replaceFileType |
      | teamPlanAdmin     | ImageFile.png,TextFile.txt,AudioTestFile.wav | ImageFile.png   |
      | businessPlanAdmin | CsvFile.csv,ImageFile.png                    | CsvFile.csv     |
      | proPlanAdmin      | ImageFile.png,ImageFile.png                  | ImageFile.png   |


  Scenario Outline: As a guest with edit permission, upload new files and delete files in data room
    When I login as "<userPlanType>"
    And I create DR with "View" permission and upload a file "ImageJPEGFile.jpeg"
    And I navigate to "guests" tab in data room "guests" page
    And I add a guest "recipientUser" with permission as "Edit"
    And I logout from data room
    And I login as "recipientUser" and access the same data room
    Then I upload a "AVIVideoFile.avi" file in DR
    And I create a folder when ms editing is enabled
    And I select all items in the list
    Then I select "trash" from the floating menu
    And I moved all items to trash can
    And I logout from data room
    And I login as "<userPlanType>" and access the same data room
    And I navigate to data room "settings" page
    Then I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |


  Scenario Outline: As a guest with edit permission, replace file and restore version in data room
    When I login as "<userPlanType>"
    And I create DR with "View" permission and upload a file "ImageJPEGFile.jpeg"
    And I select the file
    Then I select "version history" option from More floating menu in DR
    And I click on Replace file button
    Then I upload the same file in replace new version modal and replace the file
    And I navigate to "guests" tab in data room "guests" page
    And I add a guest "recipientUser" with permission as "Edit"
    And I logout from data room
    And I login as "recipientUser" and access the same data room
    And I select the file
    Then I select "version history" option from More floating menu in DR
    And I click on restore button and validate restored file toast message
    And I logout from data room
    And I login as "<userPlanType>" and access the same data room
    And I navigate to data room "settings" page
    Then I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

       #Bug-PROD-10185 raised for analytics - version modal
  Scenario Outline: Validate data room file context menu for data room owner
    When I login as "<userPlanType>"
    And I create DR and upload a file "ImageFile.png"
    #Then I open the context menu for a file, select "analytics" and validate it
   # And I navigate back to files tab by selecting back button
    Then I open the context menu for a file, select "version history" and validate it
    And I navigate back to files tab by selecting back button
    Then I open the context menu for a file, select "view" and validate it
    And I close the current tab
    Then I open the context menu for a file, select "view questions" and validate it
    Then I open the context menu for a file, select "link" and validate it
    Then I open the context menu for a file, select "manage access" and validate it
    And I close granular permission page
    And I open the context menu for a file, select "download-->download_pdf" and validate it
    And I open the context menu for a file, select "download-->download_original" and validate it
    Then I open the context menu for a file, select "more actions-->change name" and validate it
    And I close the modal in files tab
    Then I open the context menu for a file, select "more actions-->rearrange" and validate it
    And I close the modal in files tab
    And I open the context menu for a file, select "more actions-->replace file" and validate it
    And I close the modal in files tab
    And I open the context menu for a file, select "more actions-->move/copy" and validate it
    And I close the modal in files tab
    And I open the context menu for a file, select "move to trash" and validate it
    And I close the modal in files tab
    And I navigate to data room "settings" page
    Then I "delete" data room from advanced settings
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  Scenario: Validate data room file context menu for pro plan data room owner
    When I login as "proPlanAdmin"
    And I create DR and upload a file "ImageFile.png"
   # Then I open the context menu for a file, select "analytics" and validate it
   # And I navigate back to files tab by selecting back button
    Then I open the context menu for a file, select "version history" and validate it
    And I navigate back to files tab by selecting back button
    Then I open the context menu for a file, select "view" and validate it
    And I close the current tab
    Then I open the context menu for a file, select "link" and validate it
    And I open the context menu for a file, select "download-->download_pdf" and validate it
    And I open the context menu for a file, select "download-->download_original" and validate it
    Then I open the context menu for a file, select "more actions-->change name" and validate it
    And I close the modal in files tab
    Then I open the context menu for a file, select "more actions-->rearrange" and validate it
    And I close the modal in files tab
    And I open the context menu for a file, select "more actions-->replace file" and validate it
    And I close the modal in files tab
    And I open the context menu for a file, select "more actions-->move/copy" and validate it
    And I close the modal in files tab
    And I open the context menu for a file, select "move to trash" and validate it
    And I close the modal in files tab
    And I navigate to data room "settings" page
    Then I "delete" data room from advanced settings
    And I logout from the application

    #Bug-PROD-10185 raised for analytics - version modal
  Scenario Outline: Validate data room file context menu for data room co-owner
    When I login as "<userPlanType>"
    And I create DR and upload a file "ImageFile.png"
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "<guestType>" guest with permission as "Co-Owner" in data room
    And I logout from data room
    When I login as "<guestType>" and access the same data room
   # Then I open the context menu for a file, select "analytics" and validate it
   # And I navigate back to files tab by selecting back button
    Then I open the context menu for a file, select "version history" and validate it
    And I navigate back to files tab by selecting back button
    Then I open the context menu for a file, select "manage access" and validate it
    And I close granular permission page
    Then I open the context menu for a file, select "view" and validate it
    And I close the current tab
    Then I open the context menu for a file, select "view questions" and validate it
    Then I open the context menu for a file, select "link" and validate it
    And I open the context menu for a file, select "download-->download_pdf" and validate it
    And I open the context menu for a file, select "download-->download_original" and validate it
    Then I open the context menu for a file, select "more actions-->change name" and validate it
    And I close the modal in files tab
    Then I open the context menu for a file, select "more actions-->rearrange" and validate it
    And I close the modal in files tab
    Then I open the context menu for a file, select "more actions-->replace file" and validate it
    And I close the modal in files tab
    Then I open the context menu for a file, select "more actions-->move/copy" and validate it
    And I close the modal in files tab
    Then I open the context menu for a file, select "move to trash" and validate it
    And I close the modal in files tab
    Then I logout from data room
    Examples:
      | userPlanType      | guestType            |
      | teamPlanAdmin     | recipientUser        |
      | businessPlanAdmin | maildropBusinessUser |

    #Bug-PROD-10185 raised for analytics - version modal
  Scenario: Validate data room file context menu for pro plan data room co-owner
    When I login as "proPlanAdmin"
    And I create DR and upload a file "ImageFile.png"
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "maildropProUser" guest with permission as "Co-Owner" in data room
    And I logout from data room
    When I login as "maildropProUser" and access the same data room
    #Then I open the context menu for a file, select "analytics" and validate it
    #And I navigate back to files tab by selecting back button
    Then I open the context menu for a file, select "version history" and validate it
    And I navigate back to files tab by selecting back button
    Then I open the context menu for a file, select "view" and validate it
    And I close the current tab
    Then I open the context menu for a file, select "link" and validate it
    And I open the context menu for a file, select "download-->download_pdf" and validate it
    And I open the context menu for a file, select "download-->download_original" and validate it
    Then I open the context menu for a file, select "more actions-->change name" and validate it
    And I close the modal in files tab
    Then I open the context menu for a file, select "more actions-->rearrange" and validate it
    And I close the modal in files tab
    Then I open the context menu for a file, select "more actions-->replace file" and validate it
    And I close the modal in files tab
    Then I open the context menu for a file, select "more actions-->move/copy" and validate it
    And I close the modal in files tab
    Then I open the context menu for a file, select "move to trash" and validate it
    And I close the modal in files tab
    Then I logout from data room


  Scenario Outline: Validate data room file context menu for data room guest permission as Edit
    When I login as "<userPlanType>"
    And I create DR and upload a file "ImageFile.png"
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "<guestType>" guest with permission as "Edit" in data room
    And I logout from data room
    When I login as "<guestType>" and access the same data room
    Then I open the context menu for a file, select "version history" and validate it
    And I navigate back to files tab by selecting back button
    Then I open the context menu for a file, select "view" and validate it
    And I close the current tab
    Then I open the context menu for a file, select "view questions" and validate it
    Then I open the context menu for a file, select "edit--> get file link" and validate it
    And I open the context menu for a file, select "download-->download_pdf" and validate it
    And I open the context menu for a file, select "download-->download_original" and validate it
    Then I open the context menu for a file, select "more actions-->change name" and validate it
    And I close the modal in files tab
    Then I open the context menu for a file, select "more actions-->rearrange" and validate it
    And I close the modal in files tab
    Then I open the context menu for a file, select "more actions-->replace file" and validate it
    And I close the modal in files tab
    Then I open the context menu for a file, select "more actions-->move/copy" and validate it
    And I close the modal in files tab
    Then I open the context menu for a file, select "move to trash" and validate it
    And I close the modal in files tab
    Then I open the context menu for the file and validate that the option "analytics is not visible"
    Then I open the context menu for the file and validate that the option "manage access is not visible"
    Then I logout from data room
    Examples:
      | userPlanType      | guestType            |
      | teamPlanAdmin     | recipientUser        |
      | businessPlanAdmin | maildropBusinessUser |

  Scenario: Validate data room file context menu for pro plan data room guest permission as Edit
    When I login as "proPlanAdmin"
    And I create DR and upload a file "ImageFile.png"
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "maildropProUser" guest with permission as "Edit" in data room
    And I logout from data room
    When I login as "maildropProUser" and access the same data room
    Then I open the context menu for a file, select "version history" and validate it
    And I navigate back to files tab by selecting back button
    Then I open the context menu for a file, select "view" and validate it
    And I close the current tab
    Then I open the context menu for a file, select "edit--> get file link" and validate it
    And I open the context menu for a file, select "download-->download_pdf" and validate it
    And I open the context menu for a file, select "download-->download_original" and validate it
    Then I open the context menu for a file, select "more actions-->change name" and validate it
    And I close the modal in files tab
    Then I open the context menu for a file, select "more actions-->rearrange" and validate it
    And I close the modal in files tab
    Then I open the context menu for a file, select "more actions-->replace file" and validate it
    And I close the modal in files tab
    Then I open the context menu for a file, select "more actions-->move/copy" and validate it
    And I close the modal in files tab
    Then I open the context menu for a file, select "move to trash" and validate it
    And I close the modal in files tab
    Then I open the context menu for the file and validate that the option "analytics is not visible"
    Then I open the context menu for the file and validate that the option "manage access is not visible"
    Then I logout from data room


  Scenario Outline: Validate data room file floating menu for data room owner
    Given I login as "<userPlanType>"
    When I create DR and upload a file "ImageFile.png"
    And I select the file
    And I select "manage access" option from More floating menu in DR
    And I validate manage access page for a data room file
    And I close granular permission page
    And I select the file
    And I select "change name & description" option from More floating menu in DR
    And I close the modal in files tab
    And I select "rearrange" option from More floating menu in DR
    And I close the modal in files tab
    Then I select "link" from floating menu in DR
    Then I select "question" from floating menu in DR
    And I select the file
    Then I select "download" from floating menu in DR
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    Then I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |


  Scenario Outline: Validate data room file floating menu for pro plan data room owner
    Given I login as "<userPlanType>"
    When I create DR and upload a file "ImageFile.png"
    And I select the file
    And I select "change name & description" option from More floating menu in DR
    And I close the modal in files tab
    And I select "rearrange" option from More floating menu in DR
    And I close the modal in files tab
    Then I select "link" from floating menu in DR
    Then I select "download" from floating menu in DR
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    Then I logout from data room
    Examples:
      | userPlanType |
      | proPlanAdmin |

    #Bug-PROD-10185 raised for analytics - version modal
  Scenario Outline: Validate data room file floating menu for data room co-owner
    Given I login as "<userPlanType>"
    When I create DR and upload a file "ImageFile.png"
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "<guestType>" guest with permission as "Co-Owner" in data room
    And I logout from data room
    When I login as "<guestType>" and access the same data room
    And I select the file
    Then I select "link" from floating menu in DR
    Then I select "manage access" option from More floating menu in DR
    And I validate manage access page for a data room file
    And I close granular permission page
    And I select the file
    Then I select "version history" option from More floating menu in DR
    And I navigate back to files tab by selecting back button
   # And I select the file
   # Then I select "analytics" option from More floating menu in DR
  #  And I navigate back to files tab by selecting back button
    And I select the file
    Then I select "change name & description" option from More floating menu in DR
    And I close the modal in files tab
    And I select the file
    Then I select "rearrange" option from More floating menu in DR
    And I close the modal in files tab
    Then I select "question" from floating menu in DR
    And I select the file
    Then I select "download" from floating menu in DR
    Then I logout from data room
    Examples:
      | userPlanType      | guestType            |
      | teamPlanAdmin     | recipientUser        |
      | businessPlanAdmin | maildropBusinessUser |

#Bug-PROD-10185 raised for analytics - version modal
  Scenario: Validate data room file floating menu for pro plan data room co-owner
    Given I login as "proPlanAdmin"
    When I create DR and upload a file "ImageFile.png"
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "maildropProUser" guest with permission as "Co-Owner" in data room
    And I logout from data room
    When I login as "maildropProUser" and access the same data room
    And I select the file
    Then I select "link" from floating menu in DR
    Then I select "version history" option from More floating menu in DR
    And I navigate back to files tab by selecting back button
   # And I select the file
   # Then I select "analytics" option from More floating menu in DR
  #  And I navigate back to files tab by selecting back button
    And I select the file
    Then I select "change name & description" option from More floating menu in DR
    And I close the modal in files tab
    And I select the file
    Then I select "rearrange" option from More floating menu in DR
    And I close the modal in files tab
    Then I select "download" from floating menu in DR
    Then I logout from data room


  Scenario Outline: Validate data room file floating menu for data room guest permission as Edit
    Given I login as "<userPlanType>"
    When I create DR and upload a file "ImageFile.png"
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "<guestType>" guest with permission as "Edit" in data room
    And I logout from data room
    When I login as "<guestType>" and access the same data room
    And I select the file
    Then I select "edit-->link" from floating menu in DR
    Then I select "version history" option from More floating menu in DR
    And I navigate back to files tab by selecting back button
    And I select the file
    Then I select "change name & description" option from More floating menu in DR
    And I close the modal in files tab
    And I select the file
    Then I select "rearrange" option from More floating menu in DR
    And I close the modal in files tab
    Then I select "question" from floating menu in DR
    And I select the file
    Then I select "download" from floating menu in DR
    And I select the file
    And I open the floating menu for the file and validate that the option "analytics is not visible"
    Then I open the floating menu for the file and validate that the option "manage access is not visible"
    And I logout from data room
    Examples:
      | userPlanType      | guestType            |
      | teamPlanAdmin     | recipientUser        |
      | businessPlanAdmin | maildropBusinessUser |


  Scenario: Validate data room file floating menu for pro plan data room guest permission as Edit
    Given I login as "proPlanAdmin"
    When I create DR and upload a file "ImageFile.png"
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "maildropProUser" guest with permission as "Edit" in data room
    And I logout from data room
    When I login as "maildropProUser" and access the same data room
    And I select the file
    Then I select "change name & description" option from More floating menu in DR
    And I close the modal in files tab
    Then I select "edit-->link" from floating menu in DR
    Then I select "version history" option from More floating menu in DR
    And I navigate back to files tab by selecting back button
    And I select the file
    Then I select "rearrange" option from More floating menu in DR
    And I close the modal in files tab
    And I select the file
    Then I select "download" from floating menu in DR
    And I select the file
    And I open the floating menu for the file and validate that the option "analytics is not visible"
    Then I open the floating menu for the file and validate that the option "manage access is not visible"
    And I logout from data room