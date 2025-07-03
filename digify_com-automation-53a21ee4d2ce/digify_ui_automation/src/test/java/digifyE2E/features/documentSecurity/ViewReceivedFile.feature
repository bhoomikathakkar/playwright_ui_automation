@viewReceivedFile @regression @smoke @documentSecurity
Feature: Perform different actions from View received file

  Background: Navigate to Digify login page
    Given I am on the login page

  Scenario Outline: As a recipient, validate the download, link, and remove options in context menu on the received files page
    Given as "<userPlanType>", I send a "<fileType>" file with "Allow downloading,ppad->turn off,Allow printing" feature to "recipientUser" using "get link & skip notification"
    And I navigate to "ManageSentFiles" page from home page
    And I wait until file is encrypted in manage sent file
    And I logout from the application
    Then I am on the login page
    And I login as "recipientUser"
    * I navigate to "ViewReceivedFiles" page from home page
    And I verify files are present in view received files
    And I select "Date (Newest first)" from the sort by filter in View Received Files
    And I select "remove" from the context menu
    And I close the modal
    Then I select "download" from the context menu
    And I select "link" from the context menu
    Then I copy the file link and visit the link in new tab
    And I wait until file is loaded in file viewer
    Then I validate the file name
    Then I validate "enabled-->print button,enabled-->download button" in file viewer
    And I close the current tab
    And I close the modal
    And I select "remove" from the context menu
    Then I remove the file
    And I logout from the application
    Examples:
      | userPlanType      | fileType       |
      | teamPlanAdmin     | ImageFile.png  |
      | proPlanAdmin      | ImageFile.png  |
      | businessPlanAdmin | PdfFile2KB.pdf |


  Scenario Outline: As a recipient, validate the download option should be hidden in context menu
    Given as "<userPlanType>", I send a "<fileType>" file with "<feature>" feature to "recipientUser" using "get link & skip notification"
    And I navigate to "ManageSentFiles" page from home page
    And I wait until file is encrypted in manage sent file
    And I logout from the application
    Then I am on the login page
    And I login as "recipientUser"
    * I navigate to "ViewReceivedFiles" page from home page
    And I verify files are present in view received files
    And I select "Date (Newest first)" from the sort by filter in View Received Files
    Then I validate that the download option is hidden in the context menu
    And I logout from the application
    Examples:
      | userPlanType      | fileType          | feature                                      |
      | proPlanAdmin      | DocumentFile.docx | Don't allow downloading,Don't allow printing |
      | businessPlanAdmin | PdfFile4MB.pdf    | Don't allow downloading,Allow printing       |

  Scenario Outline: As a recipient, download a TOA enabled file from the context menu on the received files page
    Given as "<userPlanType>", I send a "<fileType>" file with "Allow downloading,ppad->turn off,TOA-->On" feature to "recipientUser" using "get link & skip notification"
    And I navigate to "ManageSentFiles" page from home page
    And I wait until file is encrypted in manage sent file
    And I logout from the application
    And I am on the login page
    When I login as "recipientUser"
    * I navigate to "ViewReceivedFiles" page from home page
    And I verify files are present in view received files
    And I select "Date (Newest first)" from the sort by filter in View Received Files
    Then I select the Download option from the context menu and agree to the TOA
    And I logout from the application
    Examples:
      | userPlanType      | fileType       |
      | teamPlanAdmin     | ImageFile.png  |
      | businessPlanAdmin | PdfFile2KB.pdf |
      | proPlanAdmin      | ImageFile.png  |

  Scenario Outline: As a recipient, try to download a TOA-enabled file via context menu and disagree TOA
    Given as "<userPlanType>", I send a "<fileType>" file with "Allow downloading,ppad->turn off,TOA-->On" feature to "recipientUser" using "get link & skip notification"
    And I navigate to "ManageSentFiles" page from home page
    And I wait until file is encrypted in manage sent file
    And I logout from the application
    And I am on the login page
    When I login as "recipientUser"
    * I navigate to "ViewReceivedFiles" page from home page
    And I verify files are present in view received files
    And I select "Date (Newest first)" from the sort by filter in View Received Files
    Then I select the download option from the context menu and cancel the TOA
    And I logout from the application
    Examples:
      | userPlanType      | fileType       |
      | teamPlanAdmin     | ImageFile.png  |
      | proPlanAdmin      | ImageFile.png  |
      | businessPlanAdmin | PdfFile2KB.pdf |

  Scenario Outline: As a recipient, validate file link and remove floating menu option on the view received files page
    Given as "<userPlanType>", I send a "<fileType>" file with "<feature>" feature to "recipientUser" using "get link & skip notification"
    * I navigate to "ManageSentFiles" page from home page
    * I wait until file is encrypted in manage sent file
    And I logout from the application
    * I am on the login page
    And I login as "recipientUser"
    * I navigate to "ViewReceivedFiles" page from home page
    And I verify files are present in view received files
    * I select "Date (Newest first)" from the sort by filter in View Received Files
    * I select the first file checkbox on the page
    Then I select "remove" option from the floating menu in view received file
    And I close the modal
    And I select "link" option from the floating menu in view received file
    * I copy the file link and visit the link in new tab
    * I wait until file is loaded in file viewer
    And I validate the file name
    Then I validate "<fileViewerButton>" in file viewer
    And I close the current tab
    And I close the modal
    And I select "remove" option from the floating menu in view received file
    Then I remove the file
    Examples:
      | userPlanType      | fileType          | feature                                      | fileViewerButton                                   |
      | teamPlanAdmin     | ImageFile.png     | Allow downloading,ppad->turn off             | enabled-->print button,enabled-->download button   |
      | businessPlanAdmin | PdfFile4MB.pdf    | Don't allow downloading,Allow printing       | enabled-->print button,disabled-->download button  |
      | proPlanAdmin      | DocumentFile.docx | Don't allow downloading,Don't allow printing | disabled-->print button,disabled-->download button |

  Scenario Outline: As a recipient, validate download and view floating menu option on the view received files page
    Given as "<userPlanType>", I send a "<fileType>" file with "Allow downloading,ppad->turn off,Allow printing" feature to "recipientUser" using "get link & skip notification"
    * I navigate to "ManageSentFiles" page from home page
    * I wait until file is encrypted in manage sent file
    And I logout from the application
    * I am on the login page
    When I login as "recipientUser"
    * I navigate to "ViewReceivedFiles" page from home page
    And I verify files are present in view received files
    * I select "Date (Newest first)" from the sort by filter in View Received Files
    * I select the first file checkbox on the page
    Then I select "view" option from the floating menu in view received file
    * I wait until file is loaded in file viewer
    And I validate the file name
    Then I validate "enabled-->print button,enabled-->download button" in file viewer
    * I close the current tab
    Then I select "download" option from the floating menu in view received file
    Examples:
      | userPlanType      | fileType       |
      | proPlanAdmin      | ImageFile.png  |
      | businessPlanAdmin | PdfFile2KB.pdf |
      | teamPlanAdmin     | TextFile.txt   |

  Scenario Outline: As a recipient, validate download option should be hidden in floating menu
    Given as "<userPlanType>", I send a "<fileType>" file with "<feature>" feature to "recipientUser" using "get link & skip notification"
    * I navigate to "ManageSentFiles" page from home page
    * I wait until file is encrypted in manage sent file
    And I logout from the application
    * I am on the login page
    When I login as "recipientUser"
    * I navigate to "ViewReceivedFiles" page from home page
    And I verify files are present in view received files
    * I select "Date (Newest first)" from the sort by filter in View Received Files
    * I select the first file checkbox on the page
    Then I validate that the download option is hidden in the floating menu
    And I logout from the application
    Examples:
      | userPlanType      | fileType       | feature                                      |
      | businessPlanAdmin | PdfFile2KB.pdf | Don't allow downloading,Don't allow printing |
      | teamPlanAdmin     | TextFile.txt   | Don't allow downloading,Allow printing       |


  @prod @fileViewer @dev @sanity
  Scenario Outline: As a recipient, access the 'Received Files' page to view and validate file in file viewer
    Given as "<userPlanType>", I send a "<fileType>" file with "<feature>" feature to "recipientUser" using "get link & skip notification"
    And I navigate to "ManageSentFiles" page from home page
    And I wait until file is encrypted in manage sent file
    And I logout from the application
    Then I am on the login page
    And I login as "recipientUser"
    * I navigate to "ViewReceivedFiles" page from home page
    And I verify files are present in view received files
    And I select "Date (Newest first)" from the sort by filter in View Received Files
    And I select the first file checkbox on the page
    Then I select "view" option from the floating menu in view received file
    And I wait until file is loaded in file viewer
    And I validate the file name
    Then I validate "<fileViewerButton>" in file viewer
    And I logout from file viewer
    Examples:
      | userPlanType      | fileType          | feature                                         | fileViewerButton                                   |
      | teamPlanAdmin     | ImageFile.png     | Allow downloading,ppad->turn off,Allow printing | enabled-->print button,enabled-->download button   |
      | businessPlanAdmin | PdfFile.pdf       | Don't allow downloading,Don't allow printing    | disabled-->print button,disabled-->download button |
      | proPlanAdmin      | DocumentFile.docx | Don't allow downloading,Allow printing          | enabled-->print button,disabled-->download button  |

  @fileViewer @dev
  Scenario Outline: As a recipient access excel file from view received files page and validate file viewer
    Given as "<userPlanType>", I send a "ExcelFile.xlsx" file with "<featureType>" feature to "recipientUser" using "get link & skip notification"
    And I navigate to "ManageSentFiles" page from home page
    And I wait until file is encrypted in manage sent file
    And I logout from the application
    Then I am on the login page
    And I login as "recipientUser"
    * I navigate to "ViewReceivedFiles" page from home page
    And I verify files are present in view received files
    And I select "Date (Newest first)" from the sort by filter in View Received Files
    And I select the first file checkbox on the page
    Then I select "view" option from the floating menu in view received file
    And I wait until file is loaded in file viewer
    And I validate the file name
    Then I validate "<fileViewerButton>" in file viewer
    And I logout from file viewer
    Examples:
      | userPlanType      | featureType                                     | fileViewerButton                                   |
      | proPlanAdmin      | Allow downloading,ppad->turn off,Allow printing | enabled-->print button,enabled-->download button   |
      | businessPlanAdmin | Don't allow downloading,Don't allow printing    | disabled-->print button,disabled-->download button |
      | teamPlanAdmin     | Don't allow downloading,Allow printing          | enabled-->print button,disabled-->download button  |

  @prod
  Scenario: Delete all files from view received files
    When I login as "recipientUser"
    Given I navigate to "ViewReceivedFiles" page from home page
    Then I delete all received files
    Then I verify files are not present in view received files
    And I logout from the application