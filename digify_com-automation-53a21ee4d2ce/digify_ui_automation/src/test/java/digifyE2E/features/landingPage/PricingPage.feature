@pricingPage @regression @landingPage @smoke
Feature: Verify pricing page features

  Background: Navigate to Digify pricing page
    Given I navigates to pricing page

  Scenario Outline: Verify when new user purchase Pro or Team plan from pricing page
    When I click on buy now button for "<planType>" plan
    And I navigates to pricing checkout page
    Then I select "annual" plan with default value
    And I click on button "continue to checkout"
    Then system should show "subscription email address" modal on pricing page
    And I add new user work email address
    Then I click next button
    And system should show "subscribe with user work email" modal on pricing page
    And I select button: use this email address
    And I clicks continue on "Company billing details" modal on pricing checkout page
    And I add account information on chargebee page
    And I verify FName "CB_NEW_USER", LName "CB_NEW_USER" on ChargeBee Acc Info section
    And I add billing information on chargbee page with country "Singapore"
    And I agrees to terms and conditions on chargebee page
    And I add payment information on chargbee page with country "Singapore"
    And I subscribe the plan
    And I navigate to thank you page "<planType>"
    Examples:
      | planType |
      | pro      |
      | team     |

  Scenario: Select more users, data rooms and data room guests for team plan
    When I click on buy now button for "team" plan
    And I navigates to pricing checkout page
    And I select "annual" plan with default value
    And I select the extra addons for team plan:
      | AddonType  | NumberRequired | DefaultSelected |
      | adminUsers | 5              | 3               |
      | adminDR    | 15             | 10              |
    And system should show updated plan details "teamPlanAnnual"
    And I validated pricing checkout page summary for "teamPlanAnnual" plan
    And I close the current tab

  Scenario: Select watermark and screen-shield addons for a pro plan
    When I click on buy now button for "pro" plan
    And I navigates to pricing checkout page
    And I select "annual" plan with default value
    And I select the extra addons "watermark,screen shield"
    And system should show updated plan details "proPlanAnnual"
    And I validated pricing checkout page summary for "proPlanAnnual" plan
    And I close the current tab

  Scenario: Validate encrypted storage value on pricing checkout page for pro plan user
    When I click on buy now button for "pro" plan
    And I navigates to pricing checkout page
    Then I validate encrypted storage value on pricing checkout page for "pro" plan user
    And I close the current tab

  Scenario: Validate encrypted storage value on pricing checkout page for team plan user
    When I click on buy now button for "team" plan
    And I navigates to pricing checkout page
    Then I validate encrypted storage value on pricing checkout page for "team" plan user
    And I close the current tab

  Scenario Outline: Verify the disabled premium features addons on pricing checkout page
    When I click on buy now button for "<planType>" plan
    And I navigates to pricing checkout page
    And I click on button "continue to checkout"
    And I login into the application from pricing page as "<planUser>"
    And I clicks "modify plan" on "you already have an existing subscription" modal
    And I navigate to pricing checkout page as existing user
    And I validate disabled premium feature add ons "disabled->single sign on,disabled->advance branding,disabled->site integration api" on pricing checkout page
    And I click on button "logout"
    Examples:
      | planType | planUser           |
      | pro      | proPremiumFeature  |
      | team     | teamPremiumFeature |

  Scenario Outline: On pricing page, verify pro,team, enterprise plan price and feature details on plan card
    Then I verify plan name, price and <featureCount> features listed on "<planType>" plan card
    Examples:
      | planType   | featureCount |
      | pro        | 11           |
      | team       | 8            |
      | enterprise | 12           |

  Scenario Outline: On pricing page, verify feature comparison table
    Then I verify "<featureName>" feature from full feature comparison table on the pricing page
    Examples:
      | featureName        |
      | account            |
      | documentSecurity   |
      | fileTracking       |
      | dataRoom           |
      | email              |
      | customBranding     |
      | admin              |
      | supportedFormat    |
      | storageIntegration |
      | apiAccess          |
      | trainingSupport    |
      | compliance         |

  Scenario:  On pricing page, verify header and footer links,navigation and section content
    Then I verify all header options and navigation on pricing page
    And I verify all footer options and navigation on pricing page
    And I verify localization dropdown
