@qnaDataRoom @regression @dataRoom @smoke
Feature: Verify data room QnA scenarios

  Background: Navigate to digify login page
    Given I am on the login page

  @prod
  Scenario Outline: Send question as Admin to a guest and reply as guest, and resolved the question as author
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "Download (PDF)" in data room
    And I navigate to data room "files" page
    And open QnA drawer
    When I ask a question and add all guests
    Then I store the data room link in memory
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    Then I open the question in QnA and reply to it
    And I logout from data room
    When I login as "<userPlanType>" and access the same data room
    Then open QnA and view the reply for the same question
    And I mark the question as resolved
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  Scenario Outline: Create DR with QnA, send question to a guest. Disable QnA from DR Settings and verify as guest
    When I login as "<userPlanType>"
    And I create a data room with feature "qna toggle on"
    And I navigate to data room "settings" page
    And I validate "qna toggle on" in data room
    And I navigate to data room "guests" page
    When I add "recipientUser" guest with permission as "Download (PDF)" in data room
    And I navigate to data room "files" page
    And open QnA drawer
    And I ask a question and add all guests
    Then I store the data room link in memory
    And I navigate to data room "settings" page
    And I updated feature to "qna toggle off" in DR Settings
    And I save the data room settings
    And I validate "qna toggle off" in data room
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    Then QnA feature should not be visible to a user
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  Scenario Outline: Update QnA settings from everyone to group(members are not in groups) and verify guest should not appear in QnA
    When I login as "<userPlanType>"
    And I create a data room with feature "qna everyone"
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "Print" in data room
    And I click on back button from add new guest page
    Then I add another guest "recipientUser1" with permission as "Download (PDF)" in DR
    And I navigate to data room "settings" page
    And I validate "qna toggle on,qna everyone" in data room
    And I enable "qna group" in data room
    And I validate "qna group" in data room
    And I save the data room settings
    And I logout from data room
    When I login as "recipientUser1" and access the same data room
    Then I open QnA drawer and clicked on new question, guest list should not be visible to the guest
    And I close the Qna drawer
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |


  Scenario Outline: Verify QnA settings if guests are in group and QnA setting is set groups
    When I login as "<userPlanType>"
    Given I create a data room with feature "qna everyone"
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "Print" in data room
    And I click on back button from add new guest page
    Then I add another guest "recipientUser1" with permission as "Download (PDF)" in DR
    And I navigate to "groups" tab in data room "guests" page
    And I create a group with default settings
    And I save the group
    Then I manage the group
    And I select all guests in a group
    And I save the group
    And I navigate to data room "settings" page
    And I enable "qna group" in data room
    And I validate "qna group" in data room
    And I save the data room settings
    And I logout from data room
    When I login as "recipientUser1" and access the same data room
    Then I open QnA drawer and clicked on new question, guest list should be visible to the guest
    And I close the Qna drawer
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |


  Scenario Outline: Create a DR with QnA settings, then update the Q&A settings within the DR settings. Verify as a guest whether the guest list appears in the Q&A
    When I login as "<userPlanType>"
    Given I create a data room with feature "qna everyone"
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "Print" in data room
    And I navigate to data room "settings" page
    And I validate "qna toggle on,qna everyone" in data room
    And I enable "qna owners" in data room
    And I validate "qna owners" in data room
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    Then I open QnA, guest list is not visible if qna is set to owners and there are no other guests
    And I close the Qna drawer
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |