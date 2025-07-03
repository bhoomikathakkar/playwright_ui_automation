@userSubscription @regression @billingTab @adminSettings
Feature: Cancel/Pause and reactivate user subscription on admin settings billing tab

  Background: Navigate to Digify login page
    Given I am on the login page

  Scenario Outline: The user is interested in canceling their plan and proceeding with the subscription cancellation
    When I login as "subscriptionCancelAndReactivate"
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "cancel subscription" in billing tab
    Then I click on "Cancel Subscription" button in section
    And I navigated to hibernation plan waitlist page and select "i'm interested" option
    And I select "continue cancellation" on plan hibernation user interest page
    And I select <planCancelReason> as cancellation reason in subscription cancellation flow
    And I click on "Continue cancellation" button in section
    And I validate document list in subscription cancellation flow step 2 as per <documentType>
    And I click on "Continue cancellation" button in section
    And I validate feedback form as per <feedbackType> and select "1 - 3 months"
    And I click on "Feedback continue cancellation" button in section
    And I confirm my subscription cancellation
    And I click on "Cancel Subscription" button in section
    Then I validate thank you page for cancelled subscription
    And I click on "Got it" button in section
    And I validate remove cancellation on billing tab
    And I click on "Remove" button in section
    And I validate "Remove cancellation" modal on admin settings page
    And I click on "Restore Subscription" button in modal
    And I logout from the application
    Examples:
      | planCancelReason             | documentType                     | feedbackType                          |
      | "Fundraising Completed"      | "Fundraising Completed Doc"      | "Fundraising Completed Feedback"      |
      | "Project Ended"              | "Project Ended Doc"              | "Project Ended Feedback"              |
      | "Project Paused"             | "Project Paused Doc"             | "Project Paused Feedback"             |
      | "Not Using Enough"           | "Not Using Enough Doc"           | "Not Using Enough Feedback"           |
      | "Product Features Or Issues" | "Product Features Or Issues Doc" | "Product Features Or Issues Feedback" |
      | "Too Expensive"              | "Too Expensive Doc"              | "Too Expensive Feedback"              |
      | "Others"                     | "Others Doc"                     | "Others Feedback"                     |

  Scenario: The user choose not interested option to cancel the subscription but proceeding with the subscription cancellation
    When I login as "subscriptionCancelAndReactivate"
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "cancel subscription" in billing tab
    Then I click on "Cancel Subscription" button in section
    And I navigated to hibernation plan waitlist page and select "not interested" option
    And I select "Project paused" as cancellation reason in subscription cancellation flow
    And I click on "Continue cancellation" button in section
    And I validate document list in subscription cancellation flow step 2 as per "Project Paused Doc"
    And I click on "Continue cancellation" button in section
    And I validate feedback form as per "Project Paused Feedback" and select "1 - 3 months"
    And I click on "Feedback continue cancellation" button in section
    And I confirm my subscription cancellation
    And I click on "Cancel Subscription" button in section
    Then I validate thank you page for cancelled subscription
    And I click on "Got it" button in section
    And I validate remove cancellation on billing tab
    And I click on "Remove" button in section
    And I validate "Remove cancellation" modal on admin settings page
    And I click on "Restore Subscription" button in modal
    And I logout from the application

  Scenario: Verify that user is not interested in canceling the subscription and proceed with keeping the subscription
    When I login as "subscriptionCancelAndReactivate"
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "cancel subscription" in billing tab
    Then I click on "Cancel Subscription" button in section
    And I navigated to hibernation plan waitlist page and select "not interested" option
    And I click on "keep subscription" button in section
    Then I validate cancel subscription section in billing tab
    And I logout from the application

  Scenario Outline: Verify that user can cancel and re-activate subscription by selecting different project duration
    When I login as "subscriptionCancelAndReactivate"
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "cancel subscription" in billing tab
    Then I click on "Cancel Subscription" button in section
    And I navigated to hibernation plan waitlist page and select "i'm interested" option
    And I select "continue cancellation" on plan hibernation user interest page
    And I select "Project Paused" as cancellation reason in subscription cancellation flow
    And I click on "continue cancellation" button in section
    And I validate document list in subscription cancellation flow step 2 as per "Project Paused Doc"
    And I click on "Continue cancellation" button in section
    And I validate feedback form as per "Project Paused Feedback" and select <projectPauseDuration>
    And I click on "Feedback continue cancellation" button in section
    And I confirm my subscription cancellation
    And I click on "Cancel Subscription" button in section
    Then I validate thank you page for cancelled subscription
    And I click on "Got it" button in section
    And I validate remove cancellation on billing tab
    And I click on "Remove" button in section
    And I validate "Remove cancellation" modal on admin settings page
    And I click on "Restore Subscription" button in modal
    And I logout from the application
    Examples:
      | projectPauseDuration |
      | "1 - 3 months"       |
      | "3 - 6 months"       |
      | "6 - 12 months"      |
      | "≥ 1 year"           |

  Scenario: Verify that if user select keep subscription from step 1 then subscription will not get cancelled
    When I login as "subscriptionCancelAndReactivate"
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "cancel subscription" in billing tab
    Then I click on "Cancel Subscription" button in section
    And I navigated to hibernation plan waitlist page and select "i'm interested" option
    And I select "continue cancellation" on plan hibernation user interest page
    And I select "Not Using Enough" as cancellation reason in subscription cancellation flow
    And I click on "Keep Subscription" button in section
    Then I validate cancel subscription section in billing tab
    And I logout from the application

  Scenario: Verify that if user select keep subscription from step 2 then subscription will not get cancelled
    When I login as "subscriptionCancelAndReactivate"
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "cancel subscription" in billing tab
    Then I click on "Cancel Subscription" button in section
    And I navigated to hibernation plan waitlist page and select "i'm interested" option
    And I select "continue cancellation" on plan hibernation user interest page
    And I select "Project Paused" as cancellation reason in subscription cancellation flow
    And I click on "Continue cancellation" button in section
    And I validate document list in subscription cancellation flow step 2 as per "Project Paused Doc"
    And I click on "Keep Subscription" button in section
    Then I validate cancel subscription section in billing tab
    And I logout from the application

  Scenario: Verify that if user select keep subscription from step 3 then subscription will not get cancelled
    When I login as "subscriptionCancelAndReactivate"
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "cancel subscription" in billing tab
    Then I click on "Cancel Subscription" button in section
    And I navigated to hibernation plan waitlist page and select "i'm interested" option
    And I select "continue cancellation" on plan hibernation user interest page
    And I select "Project Ended" as cancellation reason in subscription cancellation flow
    And I click on "Continue cancellation" button in section
    And I validate document list in subscription cancellation flow step 2 as per "Project Ended Doc"
    And I click on "Continue cancellation" button in section
    And I validate feedback form as per "Project Ended Feedback" and select "1 - 3 months"
    And I click on "keep subscription on step 3" button in section
    Then I validate cancel subscription section in billing tab
    And I logout from the application

  Scenario: Verify that if user select keep subscription from step 4 then subscription will not get cancelled
    When I login as "subscriptionCancelAndReactivate"
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "cancel subscription" in billing tab
    Then I click on "Cancel Subscription" button in section
    And I navigated to hibernation plan waitlist page and select "i'm interested" option
    And I select "continue cancellation" on plan hibernation user interest page
    And I select "Fundraising Completed" as cancellation reason in subscription cancellation flow
    And I click on "Continue cancellation" button in section
    And I validate document list in subscription cancellation flow step 2 as per "Fundraising Completed Doc"
    And I click on "Continue cancellation" button in section
    And I validate feedback form as per "Fundraising Completed Feedback" and select "1 - 3 months"
    And I click on "Feedback continue cancellation" button in section
    And I confirm my subscription cancellation
    And I click on "Keep Subscription" button in section
    Then I validate cancel subscription section in billing tab
    And I logout from the application

