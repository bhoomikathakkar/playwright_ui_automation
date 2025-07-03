@sendFileAnalytics @fileViewer @regression @documentSecurity
Feature: Validate Sent file analytics features

  Background: Navigate to Digify login page
    Given I am on the login page

  @smoke @prod
  Scenario Outline:As an owner, verify file views and downloads count on analytics page.
    When I login as "<userPlanType>"
    Then I navigate to "SendFiles" page and upload a "ImageFile.png" file
    And I select "Allow downloading,ppad->turn off" in permission dropdown
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I logout from the application
    And I access the file link as existing user
    And I login in file viewer as "recipientUser"
    And I wait until file is loaded in file viewer
    And I validate the file name
    Then I download the file
    And I validate and close the download popup blocker modal
    And I logout from file viewer
    Then I am on the login page
    And I login as "<userPlanType>"
    And I navigate to "ManageSentFiles" and select "Date (Newest first)" from sort by filter
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "analytics"
    And I verify "views" count "2" on analytics overview
    And I verify "downloads" count "2" on analytics overview
    And I verify "visited" percentage "100%" on analytics overview
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |