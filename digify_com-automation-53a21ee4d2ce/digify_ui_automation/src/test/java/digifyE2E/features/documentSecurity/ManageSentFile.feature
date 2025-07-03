@manageSentFile @regression @documentSecurity
Feature: Perform different actions from Manage Sent file

  Background: Navigate to Digify login page
    Given I am on the login page

  @smoke @prod @fileViewer @sanity
  Scenario Outline: Replace a file version and validate file version as a sender and recipient
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "PdfFile.pdf" file
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    When I click on more from the floating menu and select "version history"
    Then I click on replace file button and navigates to replace new version for file page
    And I upload a "PdfFile.pdf"
    And I added file version comment
    And I click on "replace file and notify recipients" button on replace new version page
    And I wait until file is encrypted in manage sent file
    And I navigate back to manage sent file page
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "version history"
    And I validate file version history
    And I logout from the application
    And I access the file link as existing user
    When I login in file viewer as "recipientUser"
    And I wait until file is loaded in file viewer
    Then I validate file version in file viewer
    And I logout from file viewer
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @smoke @prod
  Scenario Outline: Replace file with incorrect file type and check error
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page from home page
    And I upload a file "PdfFile.pdf" in send files
    Then I select "Allow downloading" in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    When I click on more from the floating menu and select "version history"
    Then I click on replace file button and navigates to replace new version for file page
    And I upload a "OdsFile.ods"
    And I click on replace and skip notification on replace new version page and validate error messages
    And I validate "file must be in the same format error" and dismiss the modal
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @smoke @prod
  Scenario Outline: Add new recipient in a file from manage sent files
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page from home page
    And I upload a file "MOVVideoFile.mov" in send files
    Then I select "Allow downloading,ppad->turn off" in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    Then I select "recipient" from the floating menu
    And I add a new recipient "recipientUser1" in manage sent file
    And I click "<notificationType>" "<messageType>" to recipients
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "manage recipients"
    Then I verify the added recipient in manage recipients page
    And I logout from the application
    Examples:
      | userPlanType      | notificationType  | messageType    |
      | teamPlanAdmin     | send notification | withMessage    |
      | teamPlanAdmin     | skip notification | withoutMessage |
      | businessPlanAdmin | send notification | withoutMessage |
      | businessPlanAdmin | skip notification | withMessage    |
      | proPlanAdmin      | send notification | withMessage    |
      | proPlanAdmin      | skip notification | withoutMessage |

  Scenario Outline: Send file with additional dynamic watermark settings, update watermark settings and verify in sent file's setting page
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page from home page
    When I upload a file "ImageJPEGFile.jpeg" in send files
    Then I enable dynamic watermark and add additional settings
      | date and time | ip address | color | position |
      | checked       | checked    | red   | Top left |
    And I add "recipientUser" as recipient
    Then I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    And I validate dynamic watermark additional settings on sent file's settings page
      | date and time | ip address | color | position |
      | checked       | checked    | red   | Top left |
    When I update dynamic watermark additional settings
      | date and time | ip address | color | position |
      | unchecked     | unchecked  | blue  | center   |
    And I save the sent file settings
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    Then I validate dynamic watermark additional settings on sent file's settings page
      | date and time | ip address | color | position |
      | unchecked     | unchecked  | blue  | center   |
    And I logout from the application
    Examples:
      | userPlanType         |
      | teamPlanAdmin        |
      | businessPlanAdmin    |
      | upgradedProPlanAdmin |

  @fileViewer
  Scenario Outline: Send file with different access and verify as recipient by logging in file viewer
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "ImageJPGFile.jpg" file
    Then I select "Allow downloading,ppad->turn off,Allow printing" in send files
    And I select "Only people I specify" in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I logout from the application
    And I access the file link as existing user
    But "recipientUser1" access unavailable file
    Then Verify an error message "Access denied" displayed in the file viewer
    And I logout from file viewer
    And I navigate to login page
    And I login as "<userPlanType>"
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I select the first file checkbox on the page
    Then I select "recipient" from the floating menu
    And I add a new recipient "recipientUser1" in manage sent file
    And I click "send notification" "withoutMessage" to recipients
    And I logout from the application
    And I access the file link as existing user
    Then I login in file viewer as "recipientUser1"
    And I wait until file is loaded in file viewer
    And I validate the file name
    Then I validate "enabled-->print button,enabled-->download button" in file viewer
    And I logout from file viewer
    Examples:
      | userPlanType         |
      | teamPlanAdmin        |
      | businessPlanAdmin    |
      | upgradedProPlanAdmin |

  Scenario Outline: Send file with limited no. of recipients, update recipient limit in settings page and add more recipient
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page from home page
    And I upload a file "AudioTestFile.wav" in send files
    When I select "Allow downloading,ppad->turn off" in send files
    Then I select "Anyone with the link or file (email verification)" in send files
    Then I input "1" in recipient limit
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    When I validate feature "Anyone with the link or file (email verification)" on sent file's settings page
    Then I update recipient limit to "2"
    And I save the sent file settings
    And I select the first file checkbox on the page
    Then I select "recipient" from the floating menu
    And I add a new recipient "recipientUser1" in manage sent file
    And I click "send notification" "withMessage" to recipients
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @fileViewer
  Scenario Outline: Update file permission settings and validate as recipient in file viewer
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "<fileType>" file
    Then I select "<permissionType>" in permission dropdown
    And I add "<no of prints>" in the number of prints
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    When I update "<editPermissionType>" in settings for the sent file
    And I update "<update no of prints>" in the number of prints
    When I validate feature "<validatePermissionType>" on sent file's settings page
    Then I save the sent file settings
    And I logout from the application
    When I access the file link as existing user
    Then I login in file viewer as "recipientUser"
    And I wait until file is loaded in file viewer
    And I validate the file name
    Then I validate "<fileViewerBtnAsPerPermission>" in file viewer
    And I logout from file viewer
    Examples:
      | userPlanType      | permissionType                               | no of prints | editPermissionType                     | update no of prints | validatePermissionType                 | fileViewerBtnAsPerPermission                      | fileType          |
      | teamPlanAdmin     | Don't allow downloading,Don't allow printing | null         | Allow downloading,Allow printing       | null                | Allow downloading,Allow printing       | enabled-->print button,enabled-->download button  | ImageFile.png     |
      | teamPlanAdmin     | Allow downloading,ppad->turn off             | null         | Don't allow downloading,Allow printing | 3                   | Don't allow downloading,Allow printing | enabled-->print button,disabled-->download button | PPSMTestFile.ppsm |
      | businessPlanAdmin | Don't allow downloading,Don't allow printing | null         | Allow downloading,Allow printing       | null                | Allow downloading,Allow printing       | enabled-->print button,enabled-->download button  | ImageFile.png     |
      | businessPlanAdmin | Allow downloading,ppad->turn off             | null         | Don't allow downloading,Allow printing | 3                   | Don't allow downloading,Allow printing | enabled-->print button,disabled-->download button | PPSMTestFile.ppsm |
      | proPlanAdmin      | Don't allow downloading,Don't allow printing | null         | Allow downloading,Allow printing       | null                | Allow downloading,Allow printing       | enabled-->print button,enabled-->download button  | ImageFile.png     |
      | proPlanAdmin      | Allow downloading,ppad->turn off             | null         | Don't allow downloading,Allow printing | 3                   | Don't allow downloading,Allow printing | enabled-->print button,disabled-->download button | PPSMTestFile.ppsm |

  @fileViewer
  Scenario Outline: Send file with TOA enabled and disable TOA from settings and validate in file settings and as recipient by viewing file in file viewer
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page from home page
    And I upload a file "MOBITestFile.mobi" in send files
    Then I select "TOA-->On" in send files
    And I add "recipientUser" as recipient
    Then I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    When I update "TOA-->Off" in settings for the sent file
    Then I validate feature "TOA-->Off" on sent file's settings page
    And I save the sent file settings
    And I logout from the application
    And I access the file link as existing user
    When I login in file viewer as "recipientUser"
    And I wait until file is loaded in file viewer
    Then I verify that TOA is not present
    And I validate the file name
    And I logout from file viewer
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @fileViewer
  Scenario Outline: Send file with screen shield, update screen shield viewable area and verify in settings page and as recipient in file viewer
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "PPTMTestFile.pptm" file
    And I select "TOA-->Off" in send files
    And I select "turn on upgrade" in screen shield dropdown
    Then I select "20% viewable area" in screen shield
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    When I update screen shield to "35% viewable area" in settings for the sent file
    Then I validate feature "35% viewable area" on sent file's settings page
    And I save the sent file settings
    And I logout from the application
    And I access the file link as existing user
    Then I login in file viewer as "recipientUser"
    And I wait until file is loaded in file viewer
    And I validate the file name
    Then I validate screen shield on file viewer
    And I logout from file viewer
    Examples:
      | userPlanType         |
      | teamPlanAdmin        |
      | businessPlanAdmin    |
      | upgradedProPlanAdmin |

  @fileViewer
  Scenario Outline: Send file with screen shield, disable screen shield and verify in settings page and as recipient in file viewer
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page from home page
    And I select "Allow downloading,ppad->turn off" in send files
    And I upload a file "DocumentFile.docx" in send files
    And I select "turn on upgrade" in screen shield dropdown
    Then I select "20% viewable area" in screen shield
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    When I update screen shield to "Turn off" in settings for the sent file
    Then I validate feature "Screen Shield-->Off" on sent file's settings page
    And I save the sent file settings
    And I logout from the application
    And I access the file link as existing user
    Then I login in file viewer as "recipientUser"
    And I wait until file is loaded in file viewer
    And I validate the file name
    Then I validate that screen shield is not appearing in file viewer
    And I logout from file viewer
    Examples:
      | userPlanType         |
      | teamPlanAdmin        |
      | businessPlanAdmin    |
      | upgradedProPlanAdmin |

  Scenario Outline: Bulk add recipients to sent file using recipients floating menu option and validate same on manage access page
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "ExcelFile.xlsx" file
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I select "recipient" from the floating menu
    When I added 10 recipients from excel file "BulkEmail.xlsx"
    And I click <notificationType> <messageType> to recipients
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "manage recipients"
    Then I validated added 10 recipients on manage recipients page
    And I remove all the recipients
    And I logout from the application
    Examples:
      | userPlanType      | notificationType    | messageType      |
      | teamPlanAdmin     | "send notification" | "withMessage"    |
      | teamPlanAdmin     | "skip notification" | "withoutMessage" |
      | businessPlanAdmin | "send notification" | "withoutMessage" |
      | businessPlanAdmin | "skip notification" | "withMessage"    |
      | proPlanAdmin      | "send notification" | "withMessage"    |
      | proPlanAdmin      | "skip notification" | "withMessage"    |

  Scenario Outline:Bulk add recipients to sent file using more option manage access and validate same on manage access page
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page from home page
    And I upload a file "ImageFile.png" in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "manage recipients"
    And I click on Add recipient button from manage recipients page
    When I added 10 recipients from excel file "BulkEmail.xlsx"
    And I click <notificationType> <messageType> to recipients
    Then I validated added 10 recipients on manage recipients page
    And I remove all the recipients
    And I logout from the application
    Examples:
      | userPlanType      | notificationType    | messageType      |
      | teamPlanAdmin     | "send notification" | "withMessage"    |
      | teamPlanAdmin     | "skip notification" | "withoutMessage" |
      | businessPlanAdmin | "send notification" | "withoutMessage" |
      | businessPlanAdmin | "skip notification" | "withMessage"    |
      | proPlanAdmin      | "send notification" | "withMessage"    |
      | proPlanAdmin      | "skip notification" | "withMessage"    |

  Scenario Outline:Verify that user should find recipient already exists error when user try to add duplicate recipient using bulk invite
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page from home page
    And I upload a file "pptFile.pptx" in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I select "recipient" from the floating menu
    When I added 10 recipients from excel file "BulkEmail.xlsx"
    And I click "skip notification" "withoutMessage" to recipients
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "manage recipients"
    Then I validated added 10 recipients on manage recipients page
    And I navigate back to manage sent file page
    And I select the first file checkbox on the page
    And I select "recipient" from the floating menu
    And I re-added same 10 recipients from excel file "BulkEmail.xlsx"
    And I validate 10 "recipients" already exists error modal
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  Scenario Outline: Add sender's email in bulk invite and validate the error message
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page from home page
    And I upload a file "pptFile.pptx" in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I select "recipient" from the floating menu
    When I added "<userPlanType>" in add recipient modal
    And I validate the "add yourself as recipient" error message
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  Scenario Outline: Add existing and new recipient and validate the error message on add recipient
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page from home page
    And I upload a file "pptFile.pptx" in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I select "recipient" from the floating menu
    When I added "recipientUser,recipientUser1" in add recipient modal
    And I click on add button
    And I validate 1 "existing recipient" already exists error modal
    And I click "skip notification" "withoutMessage" to recipients
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  Scenario: For the sent file, validate premium feature upgrade modal on file settings page
    When I login as "proPlanAdmin"
    And I navigate to "SendFiles" page and upload a "pptFile.pptx" file
    And I add "recipientUser" as recipient
    Then I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    Then I validate premium feature label for dynamic watermark and screen shield on sent file's settings page
    And I select "watermark-->on" on sent file's settings page
    Then I validate premium feature upgrade modal for "dynamic watermark"
    And I close the modal
    And I select "Turn on Upgrade" on sent file's settings page
    Then I validate premium feature upgrade modal for "screen shield"
    And I close the modal
    And I logout from the application

  @smoke @prod
  Scenario Outline: Delete all files from manage sent files
    When I login as "<userPlanType>"
    And I navigate to "ManageSentFiles" page from home page
    Then I delete all files
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @smoke
  Scenario: Delete all files from manage sent files page for upgraded pro plan user
    When I login as "upgradedProPlanAdmin"
    And I navigate to "ManageSentFiles" page from home page
    Then I delete all files
    And I logout from the application

  @fileViewer
  Scenario Outline: Update TOA in file settings and validate TOA should appear again
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page from home page
    And I upload a file "MOBITestFile.mobi" in send files
    Then I select "TOA-->On" in send files
    And I add "recipientUser" as recipient
    Then I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    When I update "TOA-->Off" in settings for the sent file
    Then I validate feature "TOA-->Off" on sent file's settings page
    And I save the sent file settings
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    When I update "TOA-->On" in settings for the sent file
    Then I validate feature "TOA-->On" on sent file's settings page
    And I save the sent file settings
    And I logout from the application
    And I access the file link as existing user
    When I login in file viewer as "recipientUser"
    Then I agree TOA and continue as "recipient"
    And I wait until file is loaded in file viewer
    And I validate the file name
    And I logout from file viewer
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  Scenario Outline: Verify that user should able to replace a file from existing send files using import from digify option
    Given I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "<fileType>" file
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    When I click on more from the floating menu and select "version history"
    And I click on replace file button and navigates to replace new version for file page
    And I replace a file using "import from existing files logo" option from "sent file" list in send file
    And I added file version comment
    And I click on "replace file and notify recipients" button on replace new version page
    And I navigate back to manage sent file page
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "version history"
    And I validate file version history
    And I logout from the application
    Examples:
      | userPlanType      | fileType                    |
      | teamPlanAdmin     | ImageFile.png,ImageFile.png |
      | businessPlanAdmin | ImageFile.png,ImageFile.png |
      | proPlanAdmin      | ImageFile.png,ImageFile.png |

  @fixLogic
  Scenario Outline: Verify that user should able to replace a file from existing data room using import from digify option
    Given I login as "<userPlanType>"
    And I create a data room with default settings
    And I navigate to data room "files" page
    And I upload a "ImageJPEGFile.jpeg,ImageJPEGFile.jpeg" file in DR
    And I navigate to manage data room
    When I navigate to "SendFiles" page and upload a "ImageJPEGFile.jpeg" file
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    Then I click on more from the floating menu and select "replace file"
    And I replace a file using "import from existing files dropdown" option from "data room" list in send file
    And I added file version comment
    And I click on "replace file and skip notification" button on replace new version page
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "version history"
    And I validate file version history
    And I navigate back to manage sent file page
    And I navigate to "ManageDataRoom" page from home page
    And I search the data room
    Then I click on the first searched data room
    And I close the current tab
    And I select "delete" in the Manage DR floating menu to open a modal
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  Scenario Outline: Verify that user should find file must be in same format error when try to replace file with different file type
    Given I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "ImageJPEGFile.jpeg,DocumentFile.docx" file
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    Then I click on more from the floating menu and select "replace file"
    And I replace a different file using "import from existing files dropdown" option from "sent file" list in send file
    And I validate "file must be in the same format error" and dismiss the modal
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @fileViewer @dev
  Scenario Outline: Verify the context menu options view as recipient in manage sent files page
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page from home page
    And I select "Allow downloading,ppad->turn off" in send files
    And I upload a file "DocumentFile.docx" in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    Then I click and validate context menu option "view->as recipient"
    And I wait until file is loaded in file viewer
    And I logout from file viewer as sender
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @fileViewer @dev
  Scenario Outline: Verify the context menu options view as owner in manage sent files page
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page from home page
    And I select "Allow downloading,ppad->turn off" in send files
    And I upload a file "DocumentFile.docx" in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    Then I click and validate context menu option "view->as owner"
    And I wait until file is loaded in file viewer
    And I logout from file viewer as sender
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  Scenario Outline: Verify the context menu options download as recipient in manage sent files page
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page from home page
    And I select "Allow downloading,ppad->turn off" in send files
    And I upload a file "DocumentFile.docx" in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click and validate context menu option "download->as recipient"
    And I validate and close the download popup blocker modal
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  Scenario Outline: Verify the context menu options download as owner in manage sent files page
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page from home page
    And I select "Allow downloading,ppad->turn off" in send files
    And I upload a file "DocumentFile.docx" in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click and validate context menu option "download->as owner"
    And I validate and close the download popup blocker modal
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  Scenario Outline: Verify the context menu option get file link and delete in manage sent file page
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page from home page
    And I select "Allow downloading,ppad->turn off" in send files
    And I upload a file "ImageJPEGFile.jpeg" in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click and validate context menu option "get file link"
    And I click and validate context menu option "delete"
    And I delete the file
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  Scenario Outline: Verify the context menu option file rename,file replace,version history, add recipient and manage access in manage sent file page
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page from home page
    And I select "Allow downloading,ppad->turn off" in send files
    And I upload a file "ImageJPEGFile.jpeg" in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click and validate context menu option "more actions->rename"
    And I click and validate context menu option "more actions->replace"
    And I upload a "ImageJPEGFile.jpeg"
    And I added file version comment
    And I click on "replace file and notify recipients" button on replace new version page
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click and validate context menu option "version history"
    And I validate file version history
    And I navigate back to manage sent file page
    And I wait until file is encrypted in manage sent file
    And I click and validate context menu option "more actions->add recipient"
    And  I added 10 recipients from excel file "BulkEmail.xlsx"
    And I click "skip notification" "withMessage" to recipients
    And I select the first file checkbox on the page
    And I click and validate context menu option "manage recipients"
    Then I validated added 10 recipients on manage recipients page
    And I navigate back to manage sent file page
    And I click and validate context menu option "delete"
    And I delete the file
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @fileViewer @bug-PROD-10530
  Scenario Outline: Verify the context menu option settings and analytics in manage sent file page
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page from home page
    And I select "Allow downloading,ppad->turn off" in send files
    And I upload a file "ImageFile.png" in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click and validate context menu option "settings"
    Then I validate feature "Allow downloading" on sent file's settings page
    And I logout from the application
    And I access the file link as existing user
    And I login in file viewer as "recipientUser"
    And I wait until file is loaded in file viewer
    And I validate the file name
    Then I download the file
    And I validate and close the download popup blocker modal
    And I logout from file viewer
    Then I am on the login page
    And I login as "<userPlanType>"
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I select the first file checkbox on the page
    And I click and validate context menu option "analytics"
    Then I verify "views" count "1" on analytics overview
    * I verify "downloads" count "1" on analytics overview
    * I verify "visited" percentage "100%" on analytics overview
    * I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |