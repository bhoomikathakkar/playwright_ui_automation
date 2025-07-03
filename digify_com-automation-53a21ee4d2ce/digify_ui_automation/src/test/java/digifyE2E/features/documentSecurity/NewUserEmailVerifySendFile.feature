@newUserEmailVerification @regression @documentSecurity
Feature: Anyone with the link permission, send file to a new user

  Background: Navigate to Digify login page
    Given I am on the login page
    And I login as "teamPlanAdmin"

  @smoke
  Scenario: Anyone with the link or file (email verification), send file to a new user and view it as new recipient and fill up signup form
    And I navigate to "SendFiles" page from home page
    And I upload a file "PdfFile4MB.pdf" in send files
    Then I select "Anyone with the link or file (email verification)" in send files
    And I click on "Get link & skip notification" button to send the file
    And I copy the file link
    And I logout from the application
    Then I access the file link as a new user
    And I access the file, as "new user" with OTP
    Then I validate the file name
    And I fill up the new user signup form in file viewer
    And I navigate to file list from file viewer
    And I logout from the application



