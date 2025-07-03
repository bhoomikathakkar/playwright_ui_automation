@sendFileEmailVerify @regression @documentSecurity
Feature: Anyone with the link permission (enforce email verification), send file to an existing user and validate as recipient

  Background: Navigate to Digify login page and login with valid credentials
    Given I am on the login page
    When I login as "teamPlanAdmin"

  @smoke
  Scenario: Anyone with the link (email verify) and enforce email verification permission, send file to an existing digify user and view the file as existing recipient
    And I navigate to "SendFiles" page and upload a "ExcelFile.xlsx" file
    Then I select "Anyone with the link or file (email verification)" in send files
    And I select the stricter email verification checkbox
    And I click on "Get link & skip notification" button to send the file
    And I copy the file link
    And I logout from the application
    Then I access the file link as existing user "maildropRecipientUser" and generate OTP
    And I access the file, as "maildropRecipientUser" with OTP
    Then I validate the file name
    And I logout from file viewer



