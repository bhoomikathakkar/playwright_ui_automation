@dataRoomExport @regression @dataRoom
Feature: Verify data room export feature

  Background: Navigate to Digify login page
    Given I am on the login page

  Scenario Outline: Verify that user should able to export file index,DR,Activity log and DR Guest list from advance settings
    When I login as "<userPlanType>"
    And I create DR with "Edit" and upload a file "<fileType>"
    And I navigate to data room "guests" page
    And  I add "recipientUser" guest with permission as "View" in data room
    And I navigate to data room "settings" page
    And I navigate to data room "export" page
    Then I validated "file index" text and click on export file index button
    And I validated "export file index" modal on data room export page
    Then I validated "data room" text and click on export file index button
    And I validated "export data room" modal on data room export page
    Then I validated "activity log" text and click on export file index button
    And I validated "export activity log" modal on data room export page
    Then I validated "guest list" text and click on export file index button
    And I validated "export guest list" modal on data room export page
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      | fileType                         |
      | teamPlanAdmin     | ImageFile.png,ImageJPEGFile.jpeg |
      | businessPlanAdmin | ImageFile.png,ImageJPEGFile.jpeg |
      | proPlanAdmin      | pdfFile.pdf,ImageFile.png        |

  Scenario Outline: As owner,Verify that user should be able to access export page using menu option export from files tab
    When I login as "<userPlanType>"
    And I create DR with "Edit" and upload a file "<fileType>"
    And I select "export" from more option of context menu
    Then I validated "file index" text and click on export file index button
    And I validated "export file index" modal on data room export page
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      | fileType                                     |
      | teamPlanAdmin     | ImageJPEGFile.jpeg,OdsFile.ods               |
      | businessPlanAdmin | ImageFile.png,ImageJPEGFile.jpeg,pdfFile.pdf |
      | proPlanAdmin      | pdfFile.pdf,ImageFile.png                    |

  Scenario Outline: As guest, verify that user should be able to export file index using menu option export file index from files tab
    When I login as "<userPlanType>"
    And I create DR with "Edit" and upload a file "<fileType>"
    And I store the data room link in memory
    And I navigate to data room "guests" page
    And I add "recipientUser" guest with permission as "<guestPermission>" in data room
    And I logout from data room
    Then I login as "recipientUser" and access the same data room
    And I select "export file index" from more option of context menu
    And I validated "export file index" modal on data room export page
    And I logout from data room
    Examples:
      | userPlanType      | fileType                       | guestPermission     |
      | teamPlanAdmin     | ImageJPEGFile.jpeg,OdsFile.ods | Download (PDF)      |
      | businessPlanAdmin | ImageFile.png,pdfFile.pdf      | Download (Original) |
      | proPlanAdmin      | pdfFile.pdf,ImageFile.png      | Edit                |