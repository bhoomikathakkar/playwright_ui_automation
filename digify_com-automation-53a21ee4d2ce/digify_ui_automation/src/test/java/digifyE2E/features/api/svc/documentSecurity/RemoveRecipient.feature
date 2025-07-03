@removeRecipient @svc @documentSecuritySvc @regression

Feature: Validate v1/file/recipient/remove endpoint for removing a recipient from a file, covering both success and error scenarios.


  Scenario Outline: Call "v1/file/recipient/remove" endpoint to remove a recipient and validate success case
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "<recipient>" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    Then "<sender>" calls "v1/file/recipient/remove" endpoint to remove the "<recipient>" from the file and expects 200, 90000, "Successfully removed recipient"
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | recipient      | file        |
      | svcApiTeamAdminKey | recipientUser2 | PdfFile.pdf |
      | apiBussAdminKey    | recipientUser  | xlsFile.xls |


  Scenario Outline: Call "v1/file/recipient/remove" endpoint to remove a recipient from a public file and validate error
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "<recipient>" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute  | value  |
      | Permission | Public |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute  | value  |
      | Permission | Public |
    Then "<sender>" calls "v1/file/recipient/remove" endpoint to remove the "<recipient>" from the file and expects 400, 90014, "This file is public"
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | recipient      | file         |
      | svcApiTeamAdminKey | recipientUser2 | pptFile.pptx |


  Scenario Outline: Call "v1/file/recipient/remove" endpoint to remove a recipient who is not linked to the file and validate error
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser2" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    Then "<sender>" calls "v1/file/recipient/remove" endpoint to remove the "recipientUser" from the file and expects 400, 90015, "This user is not a recipient of the file"
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | file        |
      | svcApiTeamAdminKey | PdfFile.pdf |
      | apiBussAdminKey    | PdfFile.pdf |

  Scenario: Call "v1/file/recipient/remove" endpoint to remove a recipient for a deleted file and validate error
    Given "svcApiTeamAdminKey" calls "v1/file/upload" to share "PdfFile.pdf" with "recipientUser2" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    And "svcApiTeamAdminKey" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    When "svcApiTeamAdminKey" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Then "svcApiTeamAdminKey" calls "v1/file/recipient/remove" endpoint to remove the "recipientUser2" from the file and expects 400, 90011, "File is deleted"


  Scenario Outline: Call "v1/file/recipient/remove" endpoint to remove a recipient when recipient email is blank and validate error
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser2" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    Then "<sender>" calls "v1/file/recipient/remove" endpoint to remove the "blankEmail" from the file and expects 400, 90007, "Recipient's email is empty"
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | file          |
      | svcApiTeamAdminKey | PdfFile.pdf   |
      | apiBussAdminKey    | ImageFile.png |

  Scenario: Call "v1/file/recipient/remove" endpoint to remove a recipient when recipient email is invalid and validate error
    Given "svcApiTeamAdminKey" calls "v1/file/upload" to share "PdfFile.pdf" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    When "svcApiTeamAdminKey" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    Then "svcApiTeamAdminKey" calls "v1/file/recipient/remove" endpoint to remove the "invalidEmailId" from the file and expects 400, 90008, "The email address (jesskusermaildrop.cc) is not valid"
    And "svcApiTeamAdminKey" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"

  Scenario: Call "v1/file/recipient/remove" endpoint to remove a recipient when shareGuid is null and validate error
    Given "apiNegativeTestUser" calls "v1/file/recipient/remove" to remove the "recipientUser" from the file with file shareGuid "" and expects 400, 90006, "File Guid is empty"

  Scenario: Call "v1/file/recipient/remove" endpoint to remove a recipient when shareGuid is invalid and validate error
    Given "apiNegativeTestUser" calls "v1/file/recipient/remove" to remove the "recipientUser" from the file with file shareGuid "042f03e8b5dc" and expects 400, 90009, "File record is not found"

  Scenario Outline: Call "v1/file/recipient/remove" endpoint to remove a recipient when sender have no permission and validate error
    Given "<sender>" calls "v1/file/upload" to share "PdfFile.pdf" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    Then "apiNegativeTestUser" calls "v1/file/recipient/remove" endpoint to remove the "recipientUser" from the file and expects 400, 90010, "User doesn't have permission"
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario: Call "v1/file/recipient/remove" endpoint to remove a recipient when request is blank and validate error
    Given "apiNegativeTestUser" calls "v1/file/recipient/remove" endpoint when request is blank and expects 400, 90005, "The request data is empty"