@dataRoomAdvanceSettings @regression @dataRoom @smoke
Feature: Verify data room advance settings

  Background: Navigate to Digify login page
    Given I am on the login page

  Scenario Outline: Create a data room, transfer the ownership to a co-owner
  Pre-requisites: For this scenario, Guest should be added in admins team
    When I login as "<userPlanType>"
    And I navigate to "Admin settings" page from home page
    Then I validate "<guestType>" email in members tab
    And I create a data room with default settings
    And I navigate to data room "guests" page
    Then I add "<guestType>" guest with permission as "Co-Owner" in data room
    And I navigate to data room "settings" page
    Then I "transfer" data room from advanced settings
    And I store the data room link in memory
    And I logout from data room
    When I login as "<guestType>" and access the same data room
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType         | guestType           |
      | teamPlanAdmin        | recipientUser       |
      | businessPlanAdmin    | businessPlanUser    |
      | upgradedProPlanAdmin | upgradedProPlanUser |


  Scenario: Clone data room as pro plan user, also validate tooltips on the clone DR page
    When I login as "upgradedProPlanAdmin"
    And I create a data room with default settings
    And I navigate to data room "settings" page
    Then I "clone" data room from advanced settings
    And I verify the tooltip text for Enforce Email Verification DR feature
    And I create cloned data room with default settings
    And I select "Date (Newest first)" from the sort by filter in Manage data room
    Then I validate cloned data room in manage data room and opened it
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room

  @prod
  Scenario Outline: Clone the data room, also validate tooltips on the clone DR page
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I navigate to data room "settings" page
    When I "clone" data room from advanced settings
    Then I verify the tooltip text for Enforce Email Verification DR feature
    And I create cloned data room with default settings
    And I select "Date (Newest first)" from the sort by filter in Manage data room
    Then I validate cloned data room in manage data room and opened it
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  Scenario Outline: Create a data room, transfer the DR ownership when there are no team members
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I navigate to data room "settings" page
    Then I validate no team member is a guest of this DR modal in data room transfer
    And I "delete" data room from advanced settings
    And I logout from data room

    Examples:
      | userPlanType         |
      | teamPlanAdmin        |
      | businessPlanAdmin    |
      | upgradedProPlanAdmin |

