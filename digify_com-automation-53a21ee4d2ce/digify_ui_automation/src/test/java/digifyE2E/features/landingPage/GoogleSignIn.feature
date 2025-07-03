@googleSignIn @regression @landingPage @smoke
Feature: Validate google login modal on pricing page and trial page

  Scenario: Verify google login modal on the trial page
    Given I navigates to trial page
    Then I click on "sign in with google"
    And I close the current tab

  Scenario Outline: Verify google login modal on the pricing page
    Given I navigates to pricing page
    When I click on buy now button for "<planType>" plan
    And I navigates to pricing checkout page
    And I click on button "continue to checkout"
    Then I click on "sign in with google"
    And I close the modal
    And I close the current tab
    Examples:
      | planType |
      | pro      |
      | team     |