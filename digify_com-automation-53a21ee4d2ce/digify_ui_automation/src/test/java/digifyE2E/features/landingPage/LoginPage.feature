@loginPage @regression @landingPage
Feature:Verify Login page field validations

  Background: Navigate to Digify login page
    Given I am on the login page

  @smoke
  Scenario: On Login page, verify that user can login with email having special characters
    Then I login as "spCharacterEmailUser"
    And I logout from the application

  Scenario Outline: On Login page, verify that user can't login with invalid email
    When I enter email of <userType>
    Then I verify the error message <userType>
    Examples:
      | userType         |
      | "Non_Registered" |
      | "Without_@"      |
      | "Without_Domain" |
      | "Empty_Email"    |

  Scenario Outline: On Login page, verify that user should find wrong password error message
    And I login with invalid <passwordType>
    Then I verify the error message <passwordType>
    Examples:
      | passwordType            |
      | "Invalid_Password_User" |
      | "Without_Number"        |

  Scenario: On Login page, verify that sso user can login into app successfully
    Given I login as SSOUser "ssoUser"
    And I logout from the application