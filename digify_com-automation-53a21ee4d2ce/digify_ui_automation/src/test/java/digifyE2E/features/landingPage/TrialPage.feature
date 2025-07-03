@trialPage @regression @landingPage
Feature: Verify Trial page field validations

  Scenario: On wordpress site, start trial with email password with less than 10 chars
    Given I navigates to wordpress site
    And I click on START FREE TRIAL button on wordpress site
    When I enter valid email id
    Then I enter first name and last name
    And I enter password as: "Length_Less_Than_10"
    And I click on button "Start your free trial"
    And I verify the trial page field error message "Length_Less_Than_10"
    And I close the current tab


  Scenario Outline: On trial page, validate email field and password field
    Given I navigates to pricing page
    And I click on button "start free trial"
    When I enter first name and last name
    And I enter email of <emailType> and password of <passwordType>
    And I click on button "Start your free trial"
    Then I verify the trial page field error message <emailType>
    And I verify the trial page field error message <passwordType>
    Examples:
      | emailType          | passwordType             |
      | "Invalid_Email"    | "Length_Less_Than_10"    |
      | "Registered_Email" | "Valid_Password"         |
      | "Valid_Email"      | "Without_Letter"         |
      | "Valid_Email"      | "Without_Symbol"         |
      | "Valid_Email"      | "First_Name_As_Password" |
      | "Valid_Email"      | "Last_Name_As_Password"  |
      | "Valid_Email"      | "Email_As_Password"      |
      | "Valid_Email"      | "Domain_As_Password"     |
      | "Valid_Email"      | "With_Space_Password"    |
      | "Valid_Email"      | "Without_Number"         |
      | "Empty_Email"      | "Empty_Password"         |
      | "Valid_Email"      | "3Consecutive_Character" |

  Scenario: On wordpress site, start trial with email password with first name as password
    Given I navigates to wordpress site
    When I click on START FREE TRIAL button on wordpress site
    When I enter valid email id
    Then I enter first name and last name
    And I enter password as: "First_Name_As_Password"
    And I click on button "Start your free trial"
    And I verify the trial page field error message "First_Name_As_Password"
    And I close the current tab