@customOrganizationTerms @regression @adminSettings
Feature: Change TOA settings

  Background: Navigate to Digify login page
    Given I am on the login page

  Scenario: Verify that custom terms settings enable by default in admin settings
    When I login as "presetUser"
    Then I navigate to "admin" tab in "Admin settings" page
    And I expand "change terms of access" in admin tab
    And I validate custom term settings in admin tab
    And I logout from the application

  Scenario: Verify that user should find custom term as option in DR create page when its enabled
    When I login as "presetUser"
    Then I navigate to "admin" tab in "Admin settings" page
    And I expand "change terms of access" in admin tab
    And I validate custom term settings in admin tab
    Then I navigate to "CreateDataRoom" page from home page
    And I select "Use custom terms" option in data room
    And I validate "use custom terms" option in TOA dropdown
    And I logout from the application

  Scenario: Verify that user shouldn't find custom term as option in DR create page when its disabled
    When I login as "presetUser"
    Then I navigate to "admin" tab in "Admin settings" page
    And I expand "change terms of access" in admin tab
    And I validate custom term settings in admin tab
    And I "Disable" the custom term in admin tab
    Then I navigate to "CreateDataRoom" page from home page
    And I validate "no custom terms" option in TOA dropdown
    Then I navigate to "admin" tab in "Admin settings" page
    And I expand "change terms of access" in admin tab
    And I "Enable" the custom term in admin tab
    And I logout from the application

  Scenario: Verify that user can modify organization terms and changes get reflected in preview
    When I login as "presetUser"
    Then I navigate to "admin" tab in "Admin settings" page
    And I expand "change terms of access" in admin tab
    And I validate organization term in admin tab
    Then I modify organization term in admin tab
    And  I navigate to "CreateDataRoom" page from home page
    And I select "Use organization terms" option in data room
    And I validate "modified-->organization terms" option in TOA dropdown
    And I logout from the application