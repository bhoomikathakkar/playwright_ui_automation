@modifyPlan @regression @adminSettings
Feature: Validate user plan details and upgrade user plan from admin settings

  Background: Navigate to Digify login page
    Given I am on the login page

  @smoke
  Scenario Outline: Upgrade a pro plan user with add-ons and validate prices on pricing checkout page
    When I login as <userType>
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    And I validate "pro" plan name in plan details and usage
    Then I clicked on modify plan in billing tab and redirected to pricing review and checkout page
    And I clicks "modify plan" on "you already have an existing subscription" modal
    And I verify the initial price for the <planType> plan
    And I select the extra addons "watermark,screen shield"
    And I click on button "continue to checkout"
    And I clicks "cancel" on "pro rated charges" modal
    And I click on button "logout"
    Examples:
      | userType                   | planType         |
      | "proPlanAnnuallyAdminUser" | "proPlanAnnual"  |
      | "proPlanMonthlyAdminUser"  | "proPlanMonthly" |

  @smoke
  Scenario Outline:Upgrade team plan user with add-ons and validate prices on pricing checkout page
    When I login as <userType>
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    And I validate "team" plan name in plan details and usage
    Then I clicked on modify plan in billing tab and redirected to pricing review and checkout page
    And I clicks "modify plan" on "you already have an existing subscription" modal
    And I verify the initial price for the <planType> plan
    And I validate the added extra addons:
      | AddonType    | NumberRequired | DefaultSelected |
      | adminUsers   | 5              | 3               |
      | adminDRGuest | 15             | 10              |
    And I click on button "continue to checkout"
    And I clicks "cancel" on "pro rated charges" modal
    And I click on button "logout"
    Examples:
      | userType                    | planType          |
      | "teamPlanAnnuallyAdminUser" | "teamPlanAnnual"  |
      | "teamPlanMonthlyAdminUser"  | "teamPlanMonthly" |

  @smoke
  Scenario: Validate business plan user details on billing page
    When I login as "businessPlanAdmin"
    Then I navigate to "billing" tab in "Admin settings" page
    And I go to billing portal
    Then I validate invoice
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    And I validate "business" plan name in plan details and usage
    Then I validate data rooms value "Unlimited" in plan details
    And I validate invoice information in plan details for business plan admin
    And I validate the contact support card for business plan admin user on billing tab
    And I logout from the application

  Scenario: Verify that billing invoice should load properly
    When I login as "OwnerWith1Member"
    Then I navigate to "billing" tab in "Admin settings" page
    And I go to billing portal
    Then I validate invoice
    And I logout from the application

  Scenario Outline: Verify that user can add or remove unlimited dataroom addons on pricing modify plan page.
    When I login as "<userType>"
    And I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    And I validate DR quota "<drQuota>"
    And I clicked on modify plan in billing tab and redirected to pricing review and checkout page
    And I clicks "modify plan" on "you already have an existing subscription" modal
    And I validated "<drQuota>" included with plan
    And I validate enabled premium feature add ons "enabled->sso,enabled->branding,enabled->site api" on pricing checkout page
    Then I added unlimited DR quota
    And I verify that DR quota input is disabled
    And I click on button "continue to checkout"
    And I validate thank you page for plan upgrade
    And I expand "plan details and usage" in billing tab
    And I validate DR quota "Unlimited"
    And I clicked on modify plan in billing tab and redirected to pricing review and checkout page
    And I clicks "modify plan" on "you already have an existing subscription" modal
    And I remove unlimited DR quota
    And I click on button "continue to checkout"
    And I validate thank you page for plan upgrade
    And I expand "plan details and usage" in billing tab
    And I validate DR quota "<drQuota>"
    And I logout from the application
    Examples:
      | userType        | drQuota |
      | proUnlimitedDR  | 3       |
      | teamUnlimitedDR | 10      |

  Scenario Outline: Verify the premium features plan add ons on billing tab and pricing checkout page
    When I login as "<userType>"
    And I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    Then I validate Premium feature add ons "single sign on,advance branding,site integration api" on billing tab
    And I clicked on modify plan in billing tab and redirected to pricing review and checkout page
    And I clicks "modify plan" on "you already have an existing subscription" modal
    And I validate disabled premium feature add ons "disabled->single sign on,disabled->advance branding,disabled->site integration api" on pricing checkout page
    And I click on button "logout"
    Examples:
      | userType           |
      | proPremiumFeature  |
      | teamPremiumFeature |