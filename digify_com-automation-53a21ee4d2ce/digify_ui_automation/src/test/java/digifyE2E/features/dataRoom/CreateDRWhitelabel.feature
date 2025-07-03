@createDRWhitelabel @regression @dataRoom
Feature: On a whitelabel site, create and access data room with different features types and validate negative scenarios

  Background: Navigate to whitelabel login page
    Given I am on the white label login page

  Scenario: Create data room with different add-ons
    When I login as "whitelabelAdmin"
    And I create a data room with "Anyone with the link or file (email verification),screen shield-->enable for all,Expiry,dynamic watermark" enabled
    And I navigate to data room "settings" page
    Then I validate "Anyone with the link or file (email verification),screen shield-->enable for all,expiry,dynamic watermark" in data room
    And I "delete" data room from advanced settings
    And I logout from data room

  Scenario: Create data room with different dynamic watermark settings
    When I login as "whitelabelAdmin"
    And I navigate to "CreateDataRoom" page from home page
    And I added data room name
    Then I add additional settings of dynamic watermark
      | date and time | ip address | color | position |
      | checked       | checked    | blue  | tile     |
    And I create a data room
    And I navigate to data room "settings" page
    Then I validate dynamic watermark additional settings in data room settings page
      | date and time | ip address | color | position |
      | checked       | checked    | blue  | tile     |
    And I "delete" data room from advanced settings
    And I logout from data room

  Scenario:Create data room with expiry date and time
    When I login as "whitelabelAdmin"
    And I navigate to "CreateDataRoom" page from home page
    And I added data room name
    And I enable "Expiry" in data room
    And I validate "expiry" in data room
    Then I edit data room expiry date to "5" more days
    And I create a data room
    Then I verified expiry date "5" in data room info
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room

  Scenario: Verify that third party option should not available on replace file DR page
    When I login as "whitelabelAdmin"
    And I create DR with "Edit" and upload a file "TextFile.txt"
    And I select the file
    And I select "version history" option from More floating menu in DR
    And I click on Replace file button
    Then I validated third party options not present on page
    And I dismiss the replace file  modal
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room

  Scenario: Verify that third party option should not available in upload file dropdown on files tab
    When I login as "whitelabelAdmin"
    And I create a data room with default settings
    And I navigate to data room "files" page
    And I Expand upload file dropdown
    Then I validated third party options not present on page
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room

  @fileViewerWhiteLabel
  Scenario: As DR guest with no access permission,check error page and validate "go to file list" button should not available on file viewer
    When I login as "whitelabelAdmin"
    And I create DR with "Edit" and upload a file "PdfFile.pdf"
    And I store the DR file link in memory
    And I navigate to data room "guests" page
    And I add "recipientWhitelabelUser" guest with permission as "No Access" in data room
    And I logout from data room
    Then I login as "recipientWhitelabelUser" and access the same data room file link
    And I validated "no access" error page on file viewer
    And I logout from file viewer
    And I login as "whitelabelAdmin" and access the same data room
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room

  Scenario: As DR guest with no access permission, check error page while access folder using direct link
    When I login as "whitelabelAdmin"
    And I create a data room with default settings
    And I create a folder when ms editing is enabled
    And I store the DR folder link in memory
    And I navigate to data room "guests" page
    And I add "recipientWhitelabelUser" guest with permission as "No Access" in data room
    And I logout from data room
    Then I login as "recipientWhitelabelUser" and access the same data room folder link
    And I validated "no access folder" error page in data room
    And I logout from data room
    And I login as "whitelabelAdmin"
    And I navigate to "ManageDataRoom" page from home page
    And I delete all data rooms from manage data room page
    And I logout from the application

  Scenario: As DR guest with no access permission, check error page while access DR using direct link
    When I login as "whitelabelAdmin"
    And I create a data room with default settings
    And I store the data room link in memory
    And I navigate to data room "guests" page
    And I add "recipientWhitelabelUser" guest with permission as "No Access" in data room
    And I logout from data room
    Then I login as "recipientWhitelabelUser" and access the same data room
    And I validated "no access dr" error page in data room
    And I logout from data room
    And I login as "whitelabelAdmin"
    And I navigate to "ManageDataRoom" page from home page
    And I delete all data rooms from manage data room page
    And I logout from the application