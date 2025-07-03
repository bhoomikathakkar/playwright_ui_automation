@sendFileWhitelabel @regression @documentSecurity
Feature: Send file with different features

  Background: Navigate to whitelabel login page
    Given I am on the white label login page

  Scenario: Send file with allow download and view the file as sender
    When I login as "whitelabelAdmin"
    And I navigate to "SendFiles" page and upload a "ExcelFile.xlsx" file
    And I select "Allow downloading" in permission dropdown
    Then I select "Only people I specify" in send files
    And I add "recipientWhitelabelUser" as recipient
    And I click on "Send & notify recipients" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    Then view file as sender
    And I select the viewing option "preview file as recipient"
    And I validate the file name
    And I logout from file viewer as sender

  Scenario: Verify that import option "import from digify" and third party option should not available on send file and replace file page
    When I login as "whitelabelAdmin"
    And I navigate to "SendFiles" page from home page
    Then I validated import using digify and other third party options not present on page
    And I upload a file "PdfFile.pdf" in send files
    When I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "version history"
    Then I click on replace file button and navigates to replace new version for file page
    And  I validated import using digify and other third party options not present on page
    And I dismiss the replace file  modal
    And I logout from the application

  Scenario: Verify that clicking on the brand logo in the file viewer does not redirect the user to the home page.
    When I login as "whitelabelAdmin"
    And I navigate to "SendFiles" page and upload a "ExcelFile.xlsx" file
    And I select "Allow downloading" in permission dropdown
    Then I select "Only people I specify" in send files
    And I add "recipientWhitelabelUser" as recipient
    And I click on "Send & notify recipients" button to send the file
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    Then view file as sender
    And I select the viewing option "preview file as recipient"
    And I click on brand logo and verify that user not redirected to home page
    And I logout from file viewer as sender

  Scenario Outline: Send file with different permission settings and validate as recipient in file viewer using direct link
    When I login as "whitelabelAdmin"
    And I navigate to "SendFiles" page and upload a "<fileType>" file
    When I select "<permissionType>" in permission dropdown
    And I add "<no of prints>" in the number of prints
    And I add "recipientWhitelabelUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage sent file
    And I wait until file is encrypted in manage sent file
    And I logout from the application
    When I access the file link as existing user
    Then I login in file viewer as "recipientWhitelabelUser"
    And I validate the file name
    Then I validate "<fileViewerBtnAsPerPermission>" in file viewer
    And I logout from file viewer
    Examples:
      | permissionType                               | no of prints | fileViewerBtnAsPerPermission                       | fileType       |
      | Don't allow downloading,Allow printing       | 4            | enabled-->print button,disabled-->download button  | TextFile.txt   |
      | Don't allow downloading,Don't allow printing | null         | disabled-->print button,disabled-->download button | PdfFile4MB.pdf |
      | Don't allow downloading,Allow printing       | 1            | enabled-->print button,disabled-->download button  | pptFile.pptx   |
      | Don't allow downloading,Don't allow printing | null         | disabled-->print button,disabled-->download button | ImageFile.png  |
