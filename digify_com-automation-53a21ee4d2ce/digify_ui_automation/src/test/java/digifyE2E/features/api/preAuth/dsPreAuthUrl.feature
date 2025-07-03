@dsPreAuthUrl @api @regression
Feature: Generate and validate document security pre-auth URL

  Scenario Outline: Generate pre auth url for a recipient and validate the pre-auth link
    Given "<userType>" shares the file "<fileName>" with "<recipientEmail>" using file settings: "<permission>", <download>,<print>
    When I "<userType>" generate the pre-auth URL for the shared file with attributes "<recipientEmail>",<linkExpiry>,"<isBrowserLock>"
    Then I access the pre-auth url in the browser
    And I validate "enabled-->print button,enabled-->download button" in file viewer
    And "<userType>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | userType        | fileName       | permission | recipientEmail | download | print | linkExpiry | isBrowserLock |
      | apiTeamAdminKey | PdfFile2KB.pdf | restrict   | recipientUser  | -1       | -1    | 200        | false         |
      | apiBussAdminKey | PdfFile2KB.pdf | restrict   | recipientUser  | -1       | -1    | 200        | false         |
      | apiProAdminKey  | PdfFile2KB.pdf | restrict   | recipientUser  | -1       | -1    | 200        | false         |

  Scenario Outline: Generate pre auth url for a recipient and validate the expired pre auth url
    Given "<userType>" shares the file "<fileName>" with "<recipientEmail>" using file settings: "<permission>", <download>,<print>
    When I "<userType>" generate the pre-auth URL for the shared file with attributes "<recipientEmail>",<linkExpiry>,"<isBrowserLock>"
    Then I access the pre-auth url in the browser when link is expired
    And I validated "link expired" error in file viewer
    And "<userType>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | userType        | fileName       | permission | recipientEmail | download | print | linkExpiry | isBrowserLock |
      | apiTeamAdminKey | PdfFile2KB.pdf | restrict   | recipientUser  | 0        | 0     | 1          | false         |
      | apiBussAdminKey | PdfFile2KB.pdf | restrict   | recipientUser  | 0        | 0     | 1          | false         |
      | apiProAdminKey  | PdfFile2KB.pdf | restrict   | recipientUser  | 0        | 0     | 1          | false         |

  Scenario Outline: Generate pre auth url for a recipient and validate the pre-auth link when browser lock is true
    Given "<userType>" shares the file "<fileName>" with "<recipientEmail>" using file settings: "<permission>", <download>,<print>
    When I "<userType>" generate the pre-auth URL for the shared file with attributes "<recipientEmail>",<linkExpiry>,"<isBrowserLock>"
    Then I access the pre-auth url in the browser
    And I validate "disabled-->print button,enabled-->download button" in file viewer
    Then Re-access pre-auth URL in a new browser window
    And I validated "browser lock" error in file viewer
    And "<userType>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | userType        | fileName       | permission | recipientEmail | download | print | linkExpiry | isBrowserLock |
      | apiTeamAdminKey | PdfFile2KB.pdf | restrict   | recipientUser  | -1       | 0     | 200        | true          |
      | apiBussAdminKey | PdfFile2KB.pdf | restrict   | recipientUser  | -1       | 0     | 200        | true          |
      | apiProAdminKey  | PdfFile2KB.pdf | restrict   | recipientUser  | -1       | 0     | 200        | true          |

  Scenario Outline: Generate preAuthUrl for a recipient, access the preAuthUrl as logged-in recipient and access the link in multiple browser
    Given "<userType>" shares the file "<fileName>" with "<recipientEmail>" using file settings: "<permission>", <download>,<print>
    When I "<userType>" generate the pre-auth URL for the shared file with attributes "<recipientEmail>",<linkExpiry>,"<isBrowserLock>"
    * I am on the login page
    * I login as "recipientUser"
    Then I access the pre auth file in the new tab
    And I validate "enabled-->print button,enabled-->download button" in file viewer
    And Re-access pre-auth URL in a new browser window
    Then I validate "enabled-->print button,enabled-->download button" in file viewer
    And "<userType>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | userType        | fileName       | permission | recipientEmail | download | print | linkExpiry | isBrowserLock |
      | apiTeamAdminKey | PdfFile2KB.pdf | restrict   | recipientUser  | -1       | -1    | 200        | false         |
      | apiBussAdminKey | PdfFile2KB.pdf | restrict   | recipientUser  | -1       | -1    | 200        | false         |
      | apiProAdminKey  | PdfFile2KB.pdf | restrict   | recipientUser  | -1       | -1    | 200        | false         |

  Scenario Outline: Validate 400 status code for a pre auth url
    Given "<userType>" shares the file "<fileName>" with "<recipientEmail>" using file settings: "<permission>", <download>,<print>
    Then I "<userType>" POST to the create preAuthURL, invalid combinations of "<recipientEmail>", <linkExpiry> and expect 400
    And "<userType>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | userType        | fileName       | permission | recipientEmail | download | print | linkExpiry |
      | apiTeamAdminKey | PdfFile2KB.pdf | restrict   | recipientUser  | -1       | 0     | 200        |
      | apiBussAdminKey | PdfFile2KB.pdf | restrict   | recipientUser  | -1       | 0     | 200        |
      | apiProAdminKey  | PdfFile2KB.pdf | restrict   | recipientUser  | -1       | 0     | 200        |


  Scenario Outline: Validate 400 status code for a pre auth url of a public file
    Given "<userType>" shares the file "<fileName>" with "<recipientEmail>" using file settings: "<permission>", <download>,<print>
    Then I "<userType>" POST to the create preAuthURL for a public file with attributes "<recipientEmail>", <linkExpiry> and expect 400
    And "<userType>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | userType        | fileName       | permission | recipientEmail | download | print | linkExpiry |
      | apiTeamAdminKey | PdfFile2KB.pdf | public     | recipientUser  | 0        | 0     | 200        |
      | apiBussAdminKey | PdfFile2KB.pdf | public     | recipientUser  | 0        | 0     | 200        |
      | apiProAdminKey  | PdfFile2KB.pdf | public     | recipientUser  | 0        | 0     | 200        |

  Scenario Outline: Validate 400 status code for a pre auth url of a deleted file
    Given "<userType>" shares the file "<fileName>" with "<recipientEmail>" using file settings: "<permission>", <download>,<print>
    When "<userType>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Then I "<userType>" POST to the create preAuthURL for a deleted shared file with attributes "<recipientEmail>", <linkExpiry> and expect 400
    Examples:
      | userType        | fileName       | permission | recipientEmail | download | print | linkExpiry |
      | apiTeamAdminKey | PdfFile2KB.pdf | public     | recipientUser  | 0        | 0     | 200        |
      | apiBussAdminKey | PdfFile2KB.pdf | public     | recipientUser  | 0        | 0     | 200        |
      | apiProAdminKey  | PdfFile2KB.pdf | public     | recipientUser  | 0        | 0     | 200        |