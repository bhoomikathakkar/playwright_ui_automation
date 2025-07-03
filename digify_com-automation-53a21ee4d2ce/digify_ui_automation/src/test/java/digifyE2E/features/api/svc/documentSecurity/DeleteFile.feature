@deleteFile @svc @documentSecuritySvc @regression

Feature: Validate /v1/file/delete endpoint for deleting a file, covering both success and error scenarios.

  Scenario Outline: Call delete endpoint "/v1/file/delete" to delete a sent file
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "<recipient>" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | recipient      | file          |
      | svcApiTeamAdminKey | recipientUser2 | PdfFile.pdf   |
      | apiBussAdminKey    | recipientUser2 | ImageFile.png |

  Scenario Outline: Call delete endpoint "/v1/file/delete" for an already deleted sent file and validate API failure
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "<recipient>" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Then "<sender>" calls "v1/file/delete" to delete an already deleted file and expects 400, 30004, "File already deleted"
    Examples:
      | sender             | recipient      | file          |
      | svcApiTeamAdminKey | recipientUser2 | PdfFile.pdf   |
      | apiBussAdminKey    | recipientUser2 | ImageFile.png |

  Scenario: Call delete endpoint "/v1/file/delete" with null ShareGuid and validate API failure
    Given "apiNegativeTestUser" calls "v1/file/delete" with null "" ShareGUID and expects 400, 30002, "Share guid is null"

  Scenario: Call delete endpoint "/v1/file/delete" with invalid ShareGuid and validate API failure
    Given "apiNegativeTestUser" calls "v1/file/delete" with invalid "60544cd350574af" ShareGUID and expects 400, 30009, "Unable to delete file record"

  Scenario: Call delete endpoint "/v1/file/delete" without Request Form and validate API failure
    Given "apiNegativeTestUser" calls "v1/file/delete" with empty request form and expects 404, 30005, "File record for sender not found" status message

  Scenario: Call delete endpoint "/v1/file/delete" to verify file owner mismatch and validate API failure
    Given "apiNegativeTestUser" calls "v1/file/delete" to delete another owner's file, ShareGUID "e24de419fd6a499e90693058d12463ac" and expects HTTP 400, status code 30003, and status message "File owner mismatch"