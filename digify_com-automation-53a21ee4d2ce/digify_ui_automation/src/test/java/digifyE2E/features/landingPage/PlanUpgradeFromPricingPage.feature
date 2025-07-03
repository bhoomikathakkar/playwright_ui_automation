@planUpgradeInPricingPage @regression @landingPage @smoke
Feature: Upgrade user plan from pricing page

  Background: Navigate to Digify pricing page
    Given I navigates to pricing page

  #Todo: Verify the price based on plan type
  Scenario Outline: Freemium user try to upgrades the plan to PRO/Team plan
    When I click on buy now button for "<planType>" plan
    And I navigates to pricing checkout page
    And I click on button "continue to checkout"
    And I login into the application from pricing page as "freemiumUser"
    And I clicks continue on "Company billing details" modal on pricing checkout page
    And I click on button "continue to checkout"
    And I verify FName "freemium", LName "user" on ChargeBee Acc Info section
    And I verify email "auto_freemiumuser@vomoto.com" on chargeBee Acc Info section
    And I access home page
    And I logout from the application
    Examples:
      | planType |
      | pro      |
      | team     |

  Scenario:Free plan user upgrades the plan to PRO plan annually and check pricing summary
    When I click on buy now button for "pro" plan
    And I navigates to pricing checkout page
    Then I select the extra addons "watermark,screen shield"
    And I click on button "continue to checkout"
    And I login into the application from pricing page as "freePlanUser"
    And I clicks continue on "Company billing details" modal on pricing checkout page
    And system should show updated plan details "proPlanAnnual"
    And I click on button "continue to checkout"
    And I verify FName "free", LName "plan" on ChargeBee Acc Info section
    And I verify email "freeplan_upgradeuser@vomoto.com" on chargeBee Acc Info section
    And I validate plan prices on chargebee page "proPlanAnnual"
    And I access home page
    And I logout from the application

  Scenario:Free plan user upgrades the plan to Team plan annually and check pricing summary
    When I click on buy now button for "team" plan
    And I navigates to pricing checkout page
    Then I select the extra addons for team plan:
      | AddonType  | NumberRequired | DefaultSelected |
      | adminUsers | 5              | 3               |
      | adminDR    | 15             | 10              |
    And I click on button "continue to checkout"
    And I login into the application from pricing page as "freePlanUser"
    And I clicks continue on "Company billing details" modal on pricing checkout page
    And system should show updated plan details "teamPlanAnnual"
    And I click on button "continue to checkout"
    And I verify FName "free", LName "plan" on ChargeBee Acc Info section
    And I verify email "freeplan_upgradeuser@vomoto.com" on chargeBee Acc Info section
    And I validate plan prices on chargebee page "teamPlanAnnual"
    And I access home page
    And I logout from the application