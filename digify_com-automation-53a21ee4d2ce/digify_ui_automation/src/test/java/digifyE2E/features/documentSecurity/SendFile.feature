@sendFile @regression @documentSecurity
Feature: Send file with different features

  Background: Navigate to Digify login page
    Given I am on the login page

  @smoke @prod @sanity
  Scenario Outline: Send file with allow download and view the file as sender
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "ExcelFile.xlsx" file
    And I select "Allow downloading,ppad->turn off" in permission dropdown
    Then I select "Only people I specify" in send files
    And I add "recipientUser" as recipient
    And I click on "Send & notify recipients" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    Then view file as sender
    And I select the viewing option "preview file as recipient"
    And I validate the file name
    And I logout from file viewer as sender
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @smoke @fileViewer
  Scenario Outline: Send a file to a recipient with dynamic watermark (default settings) and expiry enabled
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "ImageFile.png" file
    And I select "Allow downloading,ppad->turn off" in permission dropdown
    And I select "Expire after first access" in send files
    And I update file expiry to "20" minutes
    And I select "dynamic watermark-->On" in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I logout from the application
    And I access the file link as existing user
    Then I login in file viewer as "recipientUser"
    And I wait until file is loaded in file viewer
    And I validate the file name
    And I validate file expiry time on file viewer
    And I logout from file viewer
    Examples:
      | userPlanType         |
      | teamPlanAdmin        |
      | businessPlanAdmin    |
      | upgradedProPlanAdmin |

  @prod @smoke @fileViewer @sanity
  Scenario Outline: Send a file to a recipient with screen shield enabled
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "pptFile.pptx" file
    Then I select "Allow downloading,ppad->turn off" in permission dropdown
    And I select "turn on upgrade" in screen shield dropdown
    And I select "50% viewable area" in screen shield
    Then I select "Only people I specify" in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
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


  @smoke @prod
  Scenario Outline: Unsuccessful in sending a file
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page from home page
    Then Application shows error message "Please upload at least one file." if click on Send button
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @fileViewer @sanity
  Scenario Outline: Send file with different permission settings and validate as recipient in file viewer
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "<fileType>" file
    When I select "<permissionType>" in permission dropdown
    And I add "<no of prints>" in the number of prints
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage sent file
    And I wait until file is encrypted in manage sent file
    And I logout from the application
    When I access the file link as existing user
    Then I login in file viewer as "recipientUser"
    And I wait until file is loaded in file viewer
    And I validate the file name
    Then I validate "<fileViewerBtnAsPerPermission>" in file viewer
    And I logout from file viewer
    Examples:
      | userPlanType      | permissionType                               | no of prints | fileViewerBtnAsPerPermission                       | fileType          |
      | teamPlanAdmin     | Don't allow downloading,Don't allow printing | null         | disabled-->print button,disabled-->download button | MOBITestFile.mobi |
      | teamPlanAdmin     | Don't allow downloading,Allow printing       | 2            | enabled-->print button,disabled-->download button  | PPTMTestFile.pptm |
      | businessPlanAdmin | Don't allow downloading,Allow printing       | 4            | enabled-->print button,disabled-->download button  | TextFile.txt      |
      | businessPlanAdmin | Don't allow downloading,Don't allow printing | null         | disabled-->print button,disabled-->download button | PdfFile4MB.pdf    |
      | proPlanAdmin      | Don't allow downloading,Allow printing       | 1            | enabled-->print button,disabled-->download button  | pptFile.pptx      |
      | proPlanAdmin      | Don't allow downloading,Don't allow printing | null         | disabled-->print button,disabled-->download button | ImageFile.png     |

  @fileViewer @sanity
  Scenario Outline: Send file with TOA enabled and validate feature and as recipient validate in file viewer
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "ImageJPEGFile.jpeg" file
    And I select "TOA-->On" in send files
    And I add "recipientUser" as recipient
    Then I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage sent file
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    And I validate feature "TOA-->On" on sent file's settings page
    And I logout from the application
    And I access the file link as existing user
    Then I login in file viewer as "recipientUser"
    And I agree TOA and continue as "recipient"
    And I wait until file is loaded in file viewer
    And I validate the file name
    Then I validate "disabled-->print button,disabled-->download button" in file viewer
    And I logout from file viewer
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @sanity
  Scenario Outline: Send file with additional dynamic watermark settings and validate watermark settings on sent file's settings page
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "PPSMTestFile.ppsm" file
    And I select "Allow downloading,ppad->turn off" in send files
    Then I enable dynamic watermark and add additional settings
      | date and time | ip address | color | position |
      | checked       | checked    | red   | Top left |
    And I add "recipientUser" as recipient
    Then I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage sent file
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    Then I validate dynamic watermark additional settings on sent file's settings page
      | date and time | ip address | color | position |
      | checked       | checked    | red   | Top left |
    And I logout from the application
    Examples:
      | userPlanType         |
      | teamPlanAdmin        |
      | businessPlanAdmin    |
      | upgradedProPlanAdmin |

  Scenario Outline: Send file with anyone with the link no email verification(require passcode) access and verify in settings page and as recipient by accessing file viewer
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "DocumentFile2.odt" file
    And I select "Anyone with the link (no email verification)" in send files
    Then I select "Require passcode" and add passcode for security reasons
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage sent file
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    Then I validate feature "Anyone with the link (no email verification), Require passcode,req additional info" on sent file's settings page
    And I logout from the application
    And I access the file link and entered passcode
    Then I close the current tab
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  Scenario Outline: Send file with anyone with the link no email verification(require passcode) access and verify in settings page and as recipient by accessing file viewer.
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "ImageGIFFile.gif" file
    Then I select "Anyone with the link (no email verification)" in send files
    And I unselect "req additional info" feature in send files
    And I select "Require passcode" and add passcode for security reasons
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage sent file
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    And I validate feature "Anyone with the link (no email verification),Require passcode,req additional info" on sent file's settings page
    And I logout from the application
    When I access the file link and entered passcode
    Then I close the current tab
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  Scenario Outline: Send file with anyone with the link no email verification access and verify in settings page and as recipient by accessing file viewer
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "ImageJPEGFile.jpeg" file
    Then I select "Anyone with the link (no email verification)" in send files
    And I unselect "req additional info" feature in send files
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage sent file
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    Then I validate feature "Anyone with the link (no email verification), req additional info" on sent file's settings page
    And I logout from the application
    And I access the file link
    Then I close the current tab
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @fileViewer
  Scenario Outline: Send file with Only domain I specify access and verify as recipient by logging in file viewer
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "OdsFile.ods" file
    And I select "Only people from email domains I specify" in send files
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    Then I navigate to "ManageSentFiles" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage sent file
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    Then I validate feature "Only people from email domains I specify" on sent file's settings page
    Then I validate feature "vomoto.com" domain on sent file's settings page
    And I logout from the application
    And I access the file link as existing user
    But "nonSpecifiedDomainUser" access unavailable file
    Then Verify an error message "Access denied" displayed in the file viewer
    And I logout from file viewer
    And I access the file link as existing user
    Then I login in file viewer as "recipientUser1"
    And I wait until file is loaded in file viewer
    And I validate the file name
    And I logout from file viewer
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  Scenario Outline: Send file with limited no. of recipients, add more recipient in manage recipients and verify limit exceeds error
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "ImageJPGFile.jpg" file
    When I select "Anyone with the link or file (email verification)" in send files
    Then I input "1" in recipient limit
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    Then I navigate to "ManageSentFiles" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage sent file
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "manage recipients"
    And I click on Add recipient button from manage recipients page
    Then I added 'recipientUser1' as a recipient but got a exceeded recipient limit by '1' error
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  Scenario Outline: Verify the error message if any media file is uploaded with print permission
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "AudioWMAFile.wma" file
    When I select "Allow printing" in send files
    Then I validate the file format error message
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  Scenario: Validate premium feature upgrade modal on send files page
    When I login as "proPlanAdmin"
    And I navigate to "SendFiles" page from home page
    Then I validate premium feature label for dynamic watermark and screen shield on send files page
    And I select "dynamic watermark-->On" in send files
    Then I validate premium feature upgrade modal for "dynamic watermark"
    And I close the modal
    And I select "turn on upgrade" in screen shield dropdown
    Then I validate premium feature upgrade modal for "screen shield"
    And I close the modal
    And I logout from the application

  Scenario Outline: Verify the warning modal when file is send with TOA enabled and anyone with the link(no email verification)
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "ImageJPEGFile.jpeg" file
    And I select "Allow downloading,ppad->turn off" in permission dropdown
    And I select "TOA-->On,Anyone with the link (no email verification)" in send files
    And I unselect "req additional info" feature in send files
    And I click on get link & skip notification button to get the warning modal
    Then I validate the "anyone with the link(no email)+toa" warning modal
    And I click on continue button on the modal and wait until file is sent
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  Scenario Outline: Verify the warning modal when file is send with Expiry enabled and anyone with the link(no email verification)
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "ImageJPEGFile.jpeg" file
    And I select "Allow downloading,ppad->turn off" in permission dropdown
    And I select "Expire after first access,Anyone with the link (no email verification)" in send files
    And I unselect "req additional info" feature in send files
    And I click on get link & skip notification button to get the warning modal
    Then I validate the "anyone with the link(no email)+expiry" warning modal
    And I click on continue button on the modal and wait until file is sent
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  Scenario Outline: Verify the warning modal when file is send with excel file and watermark enabled
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "ExcelFile.xlsx" file
    Then I enable dynamic watermark and add additional settings and validate the excel warning modal
      | date and time | ip address | color | position |
      | checked       | checked    | red   | Top left |
    And I click on "ok" button in modal
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I logout from the application
    Examples:
      | userPlanType         |
      | teamPlanAdmin        |
      | businessPlanAdmin    |
      | upgradedProPlanAdmin |

  Scenario Outline: Verify the error message when settings is anyone with the link (no email verification) and watermark enabled
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "ImageJPEGFile.jpeg" file
    And I select "dynamic watermark-->On,Anyone with the link (no email verification)" in send files
    And I validate the "setting incompatible with watermark + anyone with the link(no email verification)" error message
    And I logout from the application
    Examples:
      | userPlanType         |
      | teamPlanAdmin        |
      | businessPlanAdmin    |
      | upgradedProPlanAdmin |

  Scenario Outline: Verify the error message when user send the file without any file and without any recipient
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page from home page
    And I click on send & notify recipients button
    And I validate the "add email address" error message
    And I validate the "file upload" error message
    And I logout from the application
    Examples:
      | userPlanType         |
      | teamPlanAdmin        |
      | businessPlanAdmin    |
      | upgradedProPlanAdmin |

  @fileViewer
  Scenario Outline: Update TOA in admin settings and validate that TOA should appear again for the recipient
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "ImageJPEGFile.jpeg" file
    And I select "TOA-->On" in send files
    And I add "recipientUser" as recipient
    Then I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage sent file
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    And I validate feature "TOA-->On" on sent file's settings page
    And I logout from the application
    And I access the file link as existing user
    Then I login in file viewer as "recipientUser"
    And I agree TOA and continue as "recipientUser"
    And I wait until file is loaded in file viewer
    And I validate the file name
    And I logout from file viewer
    Then I am on the login page
    And I login as "<userPlanType>"
    When I navigate to "admin" tab in "Admin settings" page
    And I expand "change terms of access" in admin tab
    Then I update the terms of access
    And I logout from the application
    And I access the file link as existing user
    Then I login in file viewer as "recipientUser"
    And I agree TOA and continue as "recipientUser"
    And I wait until file is loaded in file viewer
    And I validate the file name
    And I logout from file viewer
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @fixLogic
  Scenario Outline: Verify that user should able to send single/multiple file(s) from existing DS file using import from digify option
    Given I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "<fileType>" file
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    Then I navigate to sendFiles page using send another file button
    And I upload <noOfFile> file using "import from existing files dropdown" option from "sent file" list
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to Manage Sent File page
    And I wait until file is encrypted in manage sent file
    And I delete all files
    And I logout from the application
    Examples:
      | userPlanType      | noOfFile | fileType                                                      |
      | teamPlanAdmin     | 1        | ImageJPEGFile.jpeg,pptFile.pptx                               |
      | businessPlanAdmin | 3        | ImageJPEGFile.jpeg,pptFile.pptx,pdfFile.pdf,DocumentFile.docx |
      | proPlanAdmin      | 2        | pdfFile.pdf,ImageFile.png                                     |

  @fixLogic
  Scenario Outline: Verify that user should able to send single/multiple file(s) from existing DR file using import from digify option
    Given I login as "<userPlanType>"
    When I create a data room with default settings
    And I navigate to data room "files" page
    And I upload a "<fileType>" file in DR
    And I navigate to manage data room
    Then I navigate to "SendFiles" page and upload <noOfFile> file using "import from existing files logo" option from "data room" list
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "ManageDataRoom" page from home page
    And I search the data room
    Then I click on the first searched data room
    And I close the current tab
    And I select "delete" in the Manage DR floating menu to open a modal
    And I logout from the application
    Examples:
      | userPlanType      | noOfFile | fileType                                                      |
      | teamPlanAdmin     | 1        | ImageJPEGFile.jpeg,pptFile.pptx                               |
      | businessPlanAdmin | 3        | ImageJPEGFile.jpeg,pptFile.pptx,pdfFile.pdf,DocumentFile.docx |
      | proPlanAdmin      | 2        | pdfFile.pdf,ImageFile.png                                     |


  Scenario: Verify the empty file list page for DS and DR in import from digify modal on send file page
    Given I login as "importDigifyAdmin"
    When I navigate to "SendFiles" page from home page
    And I click on "import from existing files logo" option
    Then I validated send file empty file list page
    And I dismiss the import from existing file modal
    And I create a data room with default settings
    And I navigate to manage data room
    When I navigate to "SendFiles" page from home page
    And I click on "import from existing files logo" option
    Then I validate empty DR list from existing file modal
    And I dismiss the import from existing file modal
    And I navigate to manage data room page
    And I delete all data rooms from manage data room page
    And I logout from the application


  Scenario: Verify the duplicate file error message on send file page when user upload a file using import from digify option
    Given I login as "proPlanAdmin"
    And I navigate to "SendFiles" page and upload a "DocumentFile.docx" file
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to Manage Sent File page
    And I wait until file is encrypted in manage sent file
    Then I navigate to "SendFiles" page and upload 1 file using "import from existing files logo" option from "sent file" list
    And I again click on "import from existing files logo" option and upload same file as existing from "sent file" list
    And I validate "duplicate file error" and remove files from page
    And I logout from the application

  Scenario Outline: Verify that user should able to send multiple file to many recipients from send file page
    Given I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "<fileType>" file
    When  I added <noOfRecipient> recipients from excel file "BulkEmail.xlsx" on send file page
    And I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "manage recipients"
    Then I validated added <noOfRecipient> recipients on manage recipients page
    And I navigate back to manage sent file page
    And I logout from the application
    Examples:
      | userPlanType      | noOfRecipient | fileType                                                      |
      | teamPlanAdmin     | 3             | ImageJPEGFile.jpeg,pptFile.pptx                               |
      | businessPlanAdmin | 10            | ImageJPEGFile.jpeg,pptFile.pptx,pdfFile.pdf,DocumentFile.docx |
      | proPlanAdmin      | 5             | pdfFile.pdf,ImageFile.png,ExcelFile.xlsx                      |

  Scenario Outline: Verify the Remembered fields after sending file without preset on send file page
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "ImageJPEGFile.jpeg" file
    And I select "Don't allow downloading,Allow printing,TOA-->On" in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate back to send file page using send another file button
    Then I validate "Don't allow downloading,Allow printing,TOA-->On" feature option is auto selected on send file page
    And I upload a file "ImageJPEGFile.jpeg" in send files
    And  I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    Then I validate feature "Don't allow downloading,Allow printing,TOA-->On" on sent file's settings page
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |
