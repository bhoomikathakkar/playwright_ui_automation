@homePage @regression @smoke
Feature:Verify home page features

  Background: Navigate to Digify login page
    Given I am on the login page

  Scenario Outline: Validate blank state recent sent files, DR, top files and top DR on dashboard
    When I login as "<userType>"
    Then I validate following blank card on the dashboard
      | Document Security             |
      | Data Room                     |
      | Top Files in Last 7 Days      |
      | Top Data Rooms in Last 7 Days |
    And I select send files button on "recent sent files" card
    And I navigate back to home page by clicking on home button
    Then I select send files button on "recent data rooms" card
    And I navigate back to home page by clicking on home button
    Then I logout from the application
    Examples:
      | userType       |
      | proNoDataAdmin |
      | teamNoDataAdm  |

  Scenario Outline: Validate recent document security files card on dashboard
    When I login as "<userType>"
    * I navigate to "ManageSentFiles" page from home page
    * I delete all files
    * I navigate back to home page by clicking on home button
    Then I validate following blank card on the dashboard
      | Document Security |
    And I select send files button on "recent sent files" card
    And I upload a file "ImageFile.png" in send files
    And I add the recipient 'recipientUser' and send the file using the 'Get Link & Skip Notification' option
    And I navigate back to home page by clicking on home button
    Then I validate the recent shared file in document security card
    And I select view all and navigates to MSF
    * I navigate back to home page by clicking on home button
    And I select view analytics for the file and navigates to file analytics
    * I navigate to "ManageSentFiles" page from home page
    * I delete all files
    * I logout from the application
    Examples:
      | userType       |
      | proNoDataAdmin |
      | teamNoDataAdm  |

  Scenario Outline: Validate admin settings card on dashboard for admin user
    When I login as "<userType>"
    And I validate the following card on dashboard
      | show admin settings |
    Then I click on "<optionType>" and validate page navigation
    And I logout from the application
    Examples:
      | userType       | optionType             |
      | proNoDataAdmin | invite members         |
      | teamNoDataAdm  | invite members         |
      | proNoDataAdmin | change terms of access |
      | teamNoDataAdm  | change terms of access |
      | proNoDataAdmin | change logo and colors |
      | teamNoDataAdm  | change logo and colors |


  Scenario Outline: Validate my settings card on dashboard for admin user
    When I login as "<userType>"
    And I validate the following card on dashboard
      | my settings |
    Then I click on "<optionType>" and validate page navigation
    * I logout from the application
    Examples:
      | userType       | optionType      |
      | proNoDataAdmin | change profile  |
      | teamNoDataAdm  | change profile  |
      | proNoDataAdmin | change password |
      | teamNoDataAdm  | change password |
      | proNoDataAdmin | setup 2fa       |
      | teamNoDataAdm  | setup 2fa       |

  Scenario Outline: Validate admin settings card on dashboard does not appear for non-admin users
    When I login as "<userType>"
    Then I validate the following card on dashboard
      | hidden admin settings |
    Examples:
      | userType      |
      | proNoDataMem  |
      | teamNoDataMem |


  Scenario Outline: Validate my settings card on dashboard for non-admin users
    When I login as "<userType>"
    Then I validate the following card on dashboard
      | my settings |
    Then I click on "<optionType>" and validate page navigation
    * I logout from the application
    Examples:
      | userType      | optionType      |
      | proNoDataMem  | change profile  |
      | teamNoDataMem | change profile  |
      | proNoDataMem  | change password |
      | teamNoDataMem | change password |
      | proNoDataMem  | setup 2fa       |
      | teamNoDataMem | setup 2fa       |


