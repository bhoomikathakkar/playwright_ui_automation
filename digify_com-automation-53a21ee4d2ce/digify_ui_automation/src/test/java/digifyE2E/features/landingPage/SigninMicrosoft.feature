@signInMicrosoft @regression @landingPage @smoke
Feature: Verify Microsoft sign-in on trial page, and login as existing user

  Scenario: Check error message when an existing user starts a trial with microsoft account
    Given I navigates to trial page
    Then I click on "sign in with microsoft"
    When I login in microsoft account
    Then error message appears as Unable to start your trial
    And I click on go to digify app
    And I logout from the application

  Scenario: Login with microsoft account credentials to verify successful login
    Given I navigate to login page
    Then I click on "login page-->ms sign-in"
    When I login in microsoft account
    Then I login in the application successfully
    And I logout from the application

  @ignore #wip
  Scenario: For an existing microsoft account user, subscribe to pro plan from pricing page
    Given I navigates to pricing page
    When I click on buy now button for "pro" plan
    And I navigates to pricing checkout page
    And I click on button "continue to checkout"
    Then I click on "sign in with microsoft"
    When I login in microsoft account
    And I click on button "logout"
    And I close the current tab

  @ignore #wip
  Scenario: For an existing microsoft account user, subscribe to team plan from pricing page
    Given I navigates to pricing page
    When I click on buy now button for "team" plan
    And I navigates to pricing checkout page
    And I click on button "continue to checkout"
    Then I click on "sign in with microsoft"
    When I login in microsoft account
    And I click on button "logout"
    And I close the current tab
