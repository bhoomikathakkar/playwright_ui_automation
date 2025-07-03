@manageDataRoom @regression @dataRoom
Feature: Verify data room files functionality

  Background: Login with valid credentials
    Given I am on the login page

  @smoke @prod
  Scenario Outline: Delete all data rooms from manage DR page
    When I login as "<userPlanType>"
    And I navigate to manage data room page
    Then I delete all data rooms from manage data room page
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @smoke
  Scenario: Delete all the data rooms from manage DR page for upgraded pro plan user
    When I login as "upgradedProPlanAdmin"
    And I navigate to manage data room page
    Then I delete all data rooms from manage data room page
    And I logout from the application

  Scenario Outline: Leave the data room as guest and verify the data room access after access revoke
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I store the data room link in memory
    And I navigate to "guests" tab in data room "guests" page
    And I add a guest "recipientUser" with permission as "<permissionType>"
    And I logout from data room
    Then I am on the login page
    And I login as "recipientUser"
    And I navigate to "ManageDataRoom" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage data room
    And I select the first data room
    Then I verify that the delete option is not visible for the DR
    And I select the first data room
    And I select "leave" in the Manage DR floating menu to open a modal
    Then I revoke my own access to this data room
    And I visit the data room link
    Then I validate data room "access denied" error message
    And I logout from data room
    And I login as "<userPlanType>" and access the same data room
    And I navigate to data room "settings" page
    Then I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType         | permissionType      |
      | businessPlanAdmin    | Edit                |
      | upgradedProPlanAdmin | Download (Original) |
      | teamPlanAdmin        | View                |


  Scenario Outline: Validate manage data room context menu for data room owner
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I navigate to data room list from data room
    And I select "Date (Newest first)" from the sort by filter in Manage data room
    Then I select "view" from the context menu and switch to "files" tab in DR
    And I close the current tab
    Then I select "analytics" from the context menu and switch to "analytics" tab in DR
    And I close the current tab
    Then I select "settings" from the context menu and switch to "settings" tab in DR
    And I close the current tab
    Then I select "manage access" from the context menu and switch to "access" tab in DR
    And I close the current tab
    Then I select "link" in the Manage DR context menu to open a modal
    And I select "delete" in the Manage DR context menu to open a modal
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |


  Scenario Outline: Validate manage data room floating menu for data room owner
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I navigate to data room list from data room
    And I select "Date (Newest first)" from the sort by filter in Manage data room
    And I select the first data room
    Then I select "view" from the floating menu and switch to "files" tab in DR
    And I close the current tab
    Then I select "analytics" from the floating menu and switch to "analytics" tab in DR
    And I close the current tab
    Then I select "settings" from the floating menu and switch to "settings" tab in DR
    And I close the current tab
    Then I select "access" from the floating menu and switch to "access" tab in DR
    And I close the current tab
    Then I select "link" in the Manage DR floating menu to open a modal
    And I select "delete" in the Manage DR floating menu to open a modal
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  Scenario Outline: Validate manage data room floating menu for data room co-owner
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "<guestType>" guest with permission as "Co-Owner" in data room
    And I logout from data room
    When I login as "<guestType>" and access the same data room
    And I navigate to data room list from data room
    And I select "Date (Newest first)" from the sort by filter in Manage data room
    And I select the first data room
    Then I select "view" from the floating menu and switch to "files" tab in DR
    And I close the current tab
    Then I select "analytics" from the floating menu and switch to "analytics" tab in DR
    And I close the current tab
    Then I select "settings" from the floating menu and switch to "settings" tab in DR
    And I close the current tab
    Then I select "access" from the floating menu and switch to "access" tab in DR
    And I close the current tab
    Then I select "link" in the Manage DR floating menu to open a modal
    And I verify that "delete" option in floating menu is not visible
    And I select "leave" in the Manage DR floating menu to open a modal
    And I close the modal
    And I logout from the application
    Examples:
      | userPlanType      | guestType            |
      | teamPlanAdmin     | recipientUser        |
      | businessPlanAdmin | maildropBusinessUser |
      | proPlanAdmin      | maildropProUser      |

  Scenario Outline: Validate manage data room context menu for data room co-owner
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "<guestType>" guest with permission as "Co-Owner" in data room
    And I logout from data room
    When I login as "<guestType>" and access the same data room
    And I navigate to data room list from data room
    And I select "Date (Newest first)" from the sort by filter in Manage data room
    Then I select "view" from the context menu and switch to "files" tab in DR
    And I close the current tab
    Then I select "analytics" from the context menu and switch to "analytics" tab in DR
    And I close the current tab
    Then I select "settings" from the context menu and switch to "settings" tab in DR
    And I close the current tab
    Then I select "manage access" from the context menu and switch to "access" tab in DR
    And I close the current tab
    Then I select "link" in the Manage DR context menu to open a modal
    And I verify that "delete" option in context menu is not visible
    And I select "revoke my access" in the Manage DR context menu to open a modal
    And I close the modal
    And I logout from the application
    Examples:
      | userPlanType      | guestType            |
      | teamPlanAdmin     | recipientUser        |
      | businessPlanAdmin | maildropBusinessUser |
      | proPlanAdmin      | maildropProUser      |


  Scenario Outline: Validate manage data room floating menu for data room edit permission guest
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "<guestType>" guest with permission as "Edit" in data room
    And I logout from data room
    When I login as "<guestType>" and access the same data room
    And I navigate to data room list from data room
    And I select "Date (Newest first)" from the sort by filter in Manage data room
    And I select the first data room
    Then I select "view" from the floating menu and switch to "files" tab in DR
    And I close the current tab
    Then I select "link" in the Manage DR floating menu to open a modal
    And I verify that "delete,analytics,settings,access" option in floating menu is not visible
    And I select "leave" in the Manage DR floating menu to open a modal
    And I close the modal
    And I logout from the application
    Examples:
      | userPlanType      | guestType            |
      | teamPlanAdmin     | recipientUser        |
      | businessPlanAdmin | maildropBusinessUser |
      | proPlanAdmin      | maildropProUser      |

  Scenario Outline: Validate manage data room context menu for data room edit permission guest
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "<guestType>" guest with permission as "Edit" in data room
    And I logout from data room
    When I login as "<guestType>" and access the same data room
    And I navigate to data room list from data room
    And I select "Date (Newest first)" from the sort by filter in Manage data room
    Then I select "view" from the context menu and switch to "files" tab in DR
    And I close the current tab
    Then I select "link" in the Manage DR context menu to open a modal
    And I verify that "delete,analytics,settings,manage access" option in context menu is not visible
    And I select "revoke my access" in the Manage DR context menu to open a modal
    And I close the modal
    And I logout from the application
    Examples:
      | userPlanType      | guestType            |
      | teamPlanAdmin     | recipientUser        |
      | businessPlanAdmin | maildropBusinessUser |
      | proPlanAdmin      | maildropProUser      |