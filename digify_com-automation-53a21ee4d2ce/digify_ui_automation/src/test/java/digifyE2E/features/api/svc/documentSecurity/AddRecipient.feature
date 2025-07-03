@addRecipient @svc @documentSecuritySvc @regression

Feature: Validate v1/file/recipient/add endpoint for adding a recipient in a file, covering both success and error scenarios.

  Scenario Outline: Call "v1/file/recipient/add" endpoint to add a recipient in a file and validate success case
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "<recipient>" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    Then "<sender>" calls "v1/file/recipient/add" endpoint to add a "<newRecipient>" in a file and expects 200, 80000, "Successfully added recipient"
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | recipient      | file          | newRecipient  |
      | svcApiTeamAdminKey | recipientUser2 | CsvFile.csv   | recipientUser |
      | apiBussAdminKey    | recipientUser2 | ImageFile.png | recipientUser |

  Scenario Outline: Call "v1/file/recipient/add" endpoint to add a recipient in a passkey file and expect error: This file is public
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "<recipient>" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute  | value   |
      | Permission | Passkey |
      | Password   | test098 |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute  | value   |
      | permission | Passkey |
      | password   | test098 |
    Then "<sender>" calls "v1/file/recipient/add" endpoint to add a "<newRecipient>" in a file and expects 400, 80009, "This file is public"
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | recipient      | file           | newRecipient  |
      | svcApiTeamAdminKey | recipientUser2 | ExcelFile.xlsx | recipientUser |
      | apiBussAdminKey    | recipientUser2 | ImageFile.png  | recipientUser |

  Scenario Outline: Call "v1/file/recipient/add" endpoint to add a recipient in a public file and expect error: This file is public
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "<recipient>" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute  | value  |
      | Permission | Public |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute  | value  |
      | Permission | Public |
    Then "<sender>" calls "v1/file/recipient/add" endpoint to add a "<newRecipient>" in a file and expects 400, 80009, "This file is public"
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | recipient      | file          | newRecipient  |
      | svcApiTeamAdminKey | recipientUser2 | CsvFile.csv   | recipientUser |
      | apiBussAdminKey    | recipientUser2 | ImageFile.png | recipientUser |

  Scenario Outline: Call "v1/file/recipient/add" endpoint to add an existing recipient and expect error: User is already a recipient
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    Then "<sender>" calls "v1/file/recipient/add" endpoint to add a "recipientUser" in a file and expects 400, 80011, "This user is already a recipient of the file"
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | file          |
      | svcApiTeamAdminKey | CsvFile.csv   |
      | apiBussAdminKey    | ImageFile.png |

  Scenario Outline: Call "v1/file/recipient/add" endpoint to add a recipient for a deleted file and validate success case
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Then "<sender>" calls "v1/file/recipient/add" endpoint to add a "recipientUser" in a file and expects 400, 80011, "This user is already a recipient of the file"
    Examples:
      | sender             | file          |
      | svcApiTeamAdminKey | CsvFile.csv   |
      | apiBussAdminKey    | ImageFile.png |

  Scenario Outline: Call "v1/file/recipient/add" endpoint to add a recipient when recipient email is blank and validate error
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "<recipient>" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    Then "<sender>" calls "v1/file/recipient/add" endpoint to add a "blankEmail" in a file and expects 400, 80004, "Recipient's email is empty"
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | recipient      | file          |
      | svcApiTeamAdminKey | recipientUser2 | CsvFile.csv   |
      | apiBussAdminKey    | recipientUser2 | ImageFile.png |

  Scenario Outline: Call "v1/file/recipient/add" endpoint to add a recipient when recipient email is invalid and validate error
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "<recipient>" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
    Then "<sender>" calls "v1/file/recipient/add" endpoint to add a "invalidEmailId" in a file and expects 400, 80005, "The email address (jesskusermaildrop.cc) is not valid"
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | recipient      | file          |
      | svcApiTeamAdminKey | recipientUser2 | CsvFile.csv   |
      | apiBussAdminKey    | recipientUser2 | ImageFile.png |

  Scenario: Call "v1/file/recipient/add" endpoint to add a recipient when shareGuid is null and validate error
    Given "apiNegativeTestUser" calls "v1/file/recipient/add" endpoint to add a "recipientUser" in a file when file shareGuid is "" and expects 400, 80003, "File Guid is empty"

  Scenario: Call "v1/file/recipient/add" endpoint to add a recipient when shareGuid is invalid and validate error
    Given "apiNegativeTestUser" calls "v1/file/recipient/add" endpoint to add a "recipientUser" in a file when file shareGuid is "042f03e8b5dc" and expects 400, 80006, "File record is not found"

  Scenario: Call "v1/file/recipient/add" endpoint to add a recipient when request is blank and validate error
    Given "apiNegativeTestUser" calls "v1/file/recipient/add" endpoint when request is blank and expects 400, 80002, "The request data is empty"