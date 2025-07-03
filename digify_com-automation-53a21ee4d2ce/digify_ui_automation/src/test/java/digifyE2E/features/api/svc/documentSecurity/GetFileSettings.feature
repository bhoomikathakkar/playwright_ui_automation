@getFileSettings @svc @documentSecuritySvc @regression

Feature: Validate v1/file/settings endpoint for validating file settings, covering both success and error scenarios.

  Scenario Outline: Call the v1/file/upload endpoint to share a File and validate file settings.
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "<recipient>" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute              | value      |
      | permission             | restrict   |
      | Expiry                 | Fixeddate  |
      | ExpiryDate             | 2026-12-29 |
      | TermsOfAccess          | true       |
      | Download               | 0          |
      | Print                  | 2          |
      | PersistentProtection   | false      |
      | RestrictForwarding     | true       |
      | AdditionalVerification | true       |
    Then "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute              | value      |
      | permission             | Restrict   |
      | Expiry                 | fixeddate  |
      | ExpiryDate             | 2026-12-29 |
      | TermsOfAccess          | true       |
      | Download               | 0          |
      | Print                  | 2          |
      | PersistentProtection   | false      |
      | RestrictForwarding     | true       |
      | AdditionalVerification | true       |
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | recipient      | file          |
      | svcApiTeamAdminKey | recipientUser2 | PdfFile.pdf   |
      | apiBussAdminKey    | recipientUser2 | ImageFile.png |

  Scenario: Validate 400 status code for the v1/file/settings endpoint when invalid GUID is passed
    Given "apiNegativeTestUser" calls "v1/file/settings" with invalid guid "1a70b3fa19c" and expects error 400, 20003, "Unable to find original share record from ShareGuid"

  Scenario Outline: Validate 400 status code for the v1/file/settings endpoint when file owner is a mismatch
    Given "apiTeamAdminKey" calls "v1/file/upload" to share "<file>" with "<recipient>" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute  | value    |
      | permission | restrict |
      | Watermark  | true     |
    Then "apiNegativeTestUser" calls "v1/file/settings" with invalid file owner and expects error 400, 20004, "File owner mismatch"
    Examples:
      | file           | recipient     |
      | PdfFile2KB.pdf | recipientUser |
