@uploadFileSettings @svc @documentSecuritySvc @regression

Feature:Validate v1/file/settings endpoint for updating file settings, covering both success and error scenarios.


  Scenario Outline: Call v1/file/settings to update file settings, Update Watermark and Print, Download settings.
    Given "<sender>" calls "v1/file/upload" to share "PdfFile.pdf" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute            | value           |
      | permission           | Restrict        |
      | watermark            | true            |
      | Watermark_text       | Test Automation |
      | Watermark_text_line2 | Line 2          |
      | Watermark_opacity    | 0.5             |
      | Watermark_color      | Red             |
      | Watermark_size       | Tile            |
      | Download             | 0               |
      | Print                | 5               |
      | PersistentProtection | false           |
      | RestrictForwarding   | true            |
    When "<sender>" calls "v1/file/settings" to update file settings and expects 200, 15000, "Successfully updated file settings"
      | attribute            | value             |
      | FileName             | PdfFile.pdf       |
      | watermark            | true              |
      | Watermark_text       | Test CONFIDENTIAL |
      | Watermark_text_line2 | DO NOT COPY       |
      | Watermark_opacity    | 0.3               |
      | Watermark_color      | Blue              |
      | Watermark_size       | Footer            |
      | Download             | -1                |
      | Print                | -1                |
      | PersistentProtection | false             |
    And "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute            | value             |
      | watermark            | true              |
      | Watermark_text       | Test CONFIDENTIAL |
      | Watermark_text_line2 | DO NOT COPY       |
      | Watermark_opacity    | 0.3               |
      | Watermark_color      | blue              |
      | Watermark_size       | footer            |
      | Download             | -1                |
      | Print                | -1                |
      | PersistentProtection | false             |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |


  Scenario Outline: Call v1/file/settings to update file settings, Disable: Watermark, and Print and Enable: Download, Expiry, ScreenShield and TOA settings.
    When "<sender>" calls "v1/file/upload" to share "ImageFile.png" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute            | value             |
      | permission           | Restrict          |
      | watermark            | true              |
      | Watermark_text       | Test CONFIDENTIAL |
      | ScreenShield         | false             |
      | TermsOfAccess        | false             |
      | PersistentProtection | false             |
      | Download             | 0                 |
      | Print                | -1                |
      | RestrictForwarding   | false             |
    When "<sender>" calls "v1/file/settings" to update file settings and expects 200, 15000, "Successfully updated file settings"
      | attribute            | value         |
      | FileName             | ImageFile.png |
      | permission           | Restrict      |
      | Watermark            | false         |
      | ScreenShield         | true          |
      | TermsOfAccess        | true          |
      | Download             | -1            |
      | Print                | 0             |
      | Expiry               | Destruct      |
      | DestructSeconds      | 1500          |
      | PersistentProtection | false         |
      | RestrictForwarding   | false         |
    And "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute            | value    |
      | permission           | Restrict |
      | Watermark            | false    |
      | ScreenShield         | true     |
      | TermsOfAccess        | true     |
      | Download             | -1       |
      | Print                | 0        |
      | PersistentProtection | false    |
      | Expiry               | destruct |
      | DestructSeconds      | 1500     |
      | RestrictForwarding   | false    |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |


  Scenario Outline: Call v1/file/settings to update file settings, Disable: TOA, Expiry, ScreenShield, Download, Print and Enable: Watermark settings.
    Given "<sender>" calls "v1/file/upload" to share "CsvFile.csv" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute            | value    |
      | permission           | Restrict |
      | ScreenShield         | true     |
      | Expiry               | Destruct |
      | DestructSeconds      | 1500     |
      | TermsOfAccess        | true     |
      | watermark            | false    |
      | Download             | -1       |
      | Print                | -1       |
      | PersistentProtection | false    |
      | RestrictForwarding   | true     |
    When "<sender>" calls "v1/file/settings" to update file settings and expects 200, 15000, "Successfully updated file settings"
      | attribute            | value             |
      | FileName             | CsvFile.csv       |
      | Permission           | Restrict          |
      | ScreenShield         | false             |
      | Expiry               | Off               |
      | TermsOfAccess        | false             |
      | watermark            | true              |
      | Watermark_text       | Test CONFIDENTIAL |
      | Download             | 0                 |
      | Print                | 0                 |
      | PersistentProtection | false             |
      | RestrictForwarding   | true              |
    And "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute            | value             |
      | permission           | Restrict          |
      | ScreenShield         | false             |
      | Expiry               | off               |
      | TermsOfAccess        | false             |
      | watermark            | true              |
      | Watermark_text       | Test CONFIDENTIAL |
      | Download             | 0                 |
      | Print                | 0                 |
      | PersistentProtection | false             |
      | RestrictForwarding   | true              |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |


  Scenario Outline: Call v1/file/settings to update file settings, Enable: TOA, and update Expiry settings.
    Given "<sender>" calls "v1/file/upload" to share "ImageFile.png" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute              | value      |
      | permission             | Restrict   |
      | Expiry                 | Fixeddate  |
      | ExpiryDate             | 2026-12-29 |
      | TermsOfAccess          | false      |
      | RestrictForwarding     | true       |
      | AdditionalVerification | true       |
    When "<sender>" calls "v1/file/settings" to update file settings and expects 200, 15000, "Successfully updated file settings"
      | attribute          | value         |
      | FileName           | ImageFile.png |
      | Permission         | Restrict      |
      | TermsOfAccess      | true          |
      | Expiry             | Destruct      |
      | DestructSeconds    | 1500          |
      | RestrictForwarding | true          |
    And "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute          | value    |
      | permission         | Restrict |
      | TermsOfAccess      | true     |
      | Expiry             | destruct |
      | DestructSeconds    | 1500     |
      | RestrictForwarding | true     |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |


  Scenario Outline: Call v1/file/settings to update passkey file settings, Update Watermark, ScreenShield, TOA, Expiry, Print and Download settings.
    Given "<sender>" calls "v1/file/upload" to share "ExcelFile.xlsx" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute            | value           |
      | permission           | Passkey         |
      | Password             | test098         |
      | watermark            | true            |
      | Watermark_text       | Test Automation |
      | Watermark_text_line2 | Line 2          |
      | Watermark_opacity    | 0.3             |
      | Watermark_color      | Red             |
      | Watermark_size       | Tile            |
      | Download             | 0               |
      | Print                | 5               |
      | TermsOfAccess        | false           |
      | ScreenShield         | true            |
      | Expiry               | Destruct        |
      | DestructSeconds      | 1500            |
      | PersistentProtection | false           |
      | RestrictForwarding   | true            |
    When "<sender>" calls "v1/file/settings" to update file settings and expects 200, 15000, "Successfully updated file settings"
      | attribute            | value             |
      | FileName             | ExcelFile.xlsx    |
      | watermark            | true              |
      | Watermark_text       | Test CONFIDENTIAL |
      | Watermark_text_line2 | DO NOT COPY       |
      | Watermark_opacity    | 0.2               |
      | Watermark_color      | Blue              |
      | Watermark_size       | Footer            |
      | Download             | -1                |
      | Print                | -1                |
      | PersistentProtection | false             |
      | TermsOfAccess        | true              |
      | ScreenShield         | false             |
      | Expiry               | Destruct          |
      | DestructSeconds      | 2000              |
    And "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute            | value             |
      | watermark            | true              |
      | Watermark_text       | Test CONFIDENTIAL |
      | Watermark_text_line2 | DO NOT COPY       |
      | Watermark_opacity    | 0.2               |
      | Watermark_color      | blue              |
      | Watermark_size       | footer            |
      | Download             | -1                |
      | Print                | -1                |
      | PersistentProtection | false             |
      | TermsOfAccess        | true              |
      | ScreenShield         | false             |
      | Expiry               | destruct          |
      | DestructSeconds      | 2000              |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |


  Scenario Outline: Call v1/file/settings to update public file settings, Update Watermark, ScreenShield, TOA, Expiry, Print and Download settings.
    Given "<sender>" calls "v1/file/upload" to share "ExcelFile.xlsx" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute            | value           |
      | permission           | Passkey         |
      | Password             | test098         |
      | watermark            | true            |
      | Watermark_text       | Test Automation |
      | Watermark_text_line2 | Line 2          |
      | Watermark_opacity    | 0.5             |
      | Watermark_color      | Blue            |
      | Watermark_size       | Footer          |
      | Download             | -1              |
      | Print                | -1              |
      | Expiry               | Destruct        |
      | DestructSeconds      | 1500            |
      | PersistentProtection | false           |
      | RestrictForwarding   | true            |
    When "<sender>" calls "v1/file/settings" to update file settings and expects 200, 15000, "Successfully updated file settings"
      | attribute            | value             |
      | FileName             | ExcelFile.xlsx    |
      | watermark            | true              |
      | Watermark_text       | Test CONFIDENTIAL |
      | Watermark_opacity    | 0.4               |
      | Watermark_color      | Grey              |
      | Watermark_size       | Center            |
      | Download             | 0                 |
      | Print                | 0                 |
      | PersistentProtection | false             |
      | TermsOfAccess        | true              |
      | ScreenShield         | true              |
      | Expiry               | Fixeddate         |
      | ExpiryDate           | 2026-12-29        |
    And "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute            | value             |
      | watermark            | true              |
      | Watermark_text       | Test CONFIDENTIAL |
      | Watermark_opacity    | 0.4               |
      | Watermark_color      | grey              |
      | Watermark_size       | diagonal          |
      | Download             | 0                 |
      | Print                | 0                 |
      | PersistentProtection | false             |
      | TermsOfAccess        | true              |
      | ScreenShield         | true              |
      | Expiry               | fixeddate         |
      | ExpiryDate           | 2026-12-29        |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |


  Scenario Outline: Call POST v1/file/settings endpoint to validate error message: 15014 Expiry date must be set after Start date
    Given "<sender>" calls "v1/file/upload" to share "PdfFile.pdf" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute       | value    |
      | permission      | Restrict |
      | Expiry          | Destruct |
      | DestructSeconds | 1500     |
    When "<sender>" calls "v1/file/settings" to update file settings and expects 400, 15014, "Expiry date must be set after Start date"
      | attribute  | value          |
      | FileName   | PdfFile2KB.pdf |
      | permission | Restrict       |
      | Expiry     | Fixeddate      |
      | StartDate  | 2026-12-29     |
      | ExpiryDate | 2026-12-28     |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |


  Scenario Outline: Call POST v1/file/settings endpoint to validate error message: 15015 Expiry date cannot be set in the past
    Given "<sender>" calls "v1/file/upload" to share "PdfFile.pdf" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute       | value    |
      | permission      | Restrict |
      | Expiry          | Destruct |
      | DestructSeconds | 2000     |
    When "<sender>" calls "v1/file/settings" to update file settings and expects 400, 15015, "Expiry date cannot be set in the past"
      | attribute  | value          |
      | FileName   | PdfFile2KB.pdf |
      | permission | Restrict       |
      | Expiry     | Fixeddate      |
      | StartDate  | 2025-04-12     |
      | ExpiryDate | 2025-04-13     |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call POST v1/file/settings endpoint to validate error message: 15019 Expiry date cannot be blank
    Given "<sender>" calls "v1/file/upload" to share "PdfFile2KB.pdf" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute  | value      |
      | permission | Restrict   |
      | Expiry     | Fixeddate  |
      | ExpiryDate | 2026-12-29 |
    When "<sender>" calls "v1/file/settings" to update file settings and expects 400, 15019, "Expiry date must not be blank"
      | attribute  | value          |
      | FileName   | PdfFile2KB.pdf |
      | permission | Restrict       |
      | Expiry     | Fixeddate      |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call POST v1/file/settings endpoint to validate error message: 15016 File name is null
    Given "<sender>" calls "v1/file/upload" to share "PdfFile2KB.pdf" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute     | value    |
      | permission    | Restrict |
      | TermsOfAccess | true     |
    When "<sender>" calls "v1/file/settings" to update file settings and expects 400, 15016, "File name is null"
      | attribute          | value    |
      | permission         | Restrict |
      | FileName           |          |
      | RestrictForwarding | true     |
      | TermsOfAccess      | false    |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call POST v1/file/settings endpoint to validate error message: 15017 File name contains invalid character
    Given "<sender>" calls "v1/file/upload" to share "PdfFile2KB.pdf" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute     | value    |
      | permission    | Restrict |
      | TermsOfAccess | true     |
    When "<sender>" calls "v1/file/settings" to update file settings and expects 400, 15017, "File name contains invalid character"
      | attribute          | value            |
      | permission         | Restrict         |
      | FileName           | PdfFile2KB<>.pdf |
      | RestrictForwarding | true             |
      | TermsOfAccess      | false            |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call POST v1/file/settings endpoint to validate error message: 15018 File extension is incorrect
    Given "<sender>" calls "v1/file/upload" to share "PdfFile2KB.pdf" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute  | value  |
      | permission | Public |
    When "<sender>" calls "v1/file/settings" to update file settings and expects 400, 15018, "File extension is incorrect"
      | attribute          | value           |
      | permission         | public          |
      | FileName           | PdfFile2KB.p@df |
      | RestrictForwarding | true            |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call POST v1/file/settings endpoint to validate error message: 15020 Watermark opacity must be between 0.1 and 0.5 (opacity is 0.9)
    Given "<sender>" calls "v1/file/upload" to share "PdfFile2KB.pdf" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute          | value    |
      | Permission         | Restrict |
      | RestrictForwarding | true     |
      | watermark          | true     |
      | Watermark_opacity  | 0.5      |
    When "<sender>" calls "v1/file/settings" to update file settings and expects 400, 15020, "Watermark opacity must be between 0.1 and 0.5"
      | attribute          | value          |
      | FileName           | PdfFile2KB.pdf |
      | permission         | Restrict       |
      | RestrictForwarding | true           |
      | watermark          | true           |
      | Watermark_opacity  | 0.9            |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |


  Scenario Outline: Call POST v1/file/settings endpoint to validate error message: 15020 Watermark opacity must be between 0.1 and 0.5 (opacity is 0.0)
    Given "<sender>" calls "v1/file/upload" to share "PdfFile2KB.pdf" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute          | value          |
      | FileName           | PdfFile2KB.pdf |
      | Permission         | Restrict       |
      | RestrictForwarding | true           |
      | watermark          | true           |
      | Watermark_opacity  | 0.5            |
    When "<sender>" calls "v1/file/settings" to update file settings and expects 400, 15020, "Watermark opacity must be between 0.1 and 0.5"
      | attribute          | value          |
      | FileName           | PdfFile2KB.pdf |
      | permission         | Restrict       |
      | RestrictForwarding | true           |
      | watermark          | true           |
      | Watermark_opacity  | 0.0            |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call POST v1/file/settings endpoint to validate error message: 15022 PPAD cannot be enabled for public file
    Given "<sender>" calls "v1/file/upload" to share "PdfFile2KB.pdf" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute            | value  |
      | permission           | Public |
      | RestrictForwarding   | true   |
      | PersistentProtection | false  |
    When "<sender>" calls "v1/file/settings" to update file settings and expects 400, 15022, "PPAD cannot be enabled for public file"
      | attribute            | value          |
      | FileName             | PdfFile2KB.pdf |
      | PersistentProtection | true           |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |


  Scenario Outline: Call POST v1/file/settings endpoint to validate error message: 15021 PPAD can only be enabled if downloading is allowed
    Given "<sender>" calls "v1/file/upload" to share "PdfFile2KB.pdf" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute            | value    |
      | permission           | Restrict |
      | RestrictForwarding   | true     |
      | PersistentProtection | false    |
    When "<sender>" calls "v1/file/settings" to update file settings and expects 400, 15021, "PPAD can only be enabled if downloading is allowed"
      | attribute            | value          |
      | FileName             | PdfFile2KB.pdf |
      | PersistentProtection | true           |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |


  Scenario: Call POST v1/file/settings endpoint to validate error message: 15011 File is deleted
    Given "apiBussAdminKey" calls "v1/file/upload" to share "PdfFile2KB.pdf" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute            | value    |
      | permission           | Restrict |
      | RestrictForwarding   | true     |
      | PersistentProtection | false    |
    When "apiBussAdminKey" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Then "apiBussAdminKey" calls "v1/file/settings" to update file settings and expects 400, 15011, "File is deleted"
      | attribute     | value          |
      | FileName      | PdfFile2KB.pdf |
      | TermsOfAccess | true           |


  Scenario Outline: Call POST v1/file/settings endpoint to validate error message: 15009 User doesn't have permission
    Given "<sender>" calls "v1/file/upload" to share "PdfFile2KB.pdf" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute            | value    |
      | permission           | Restrict |
      | RestrictForwarding   | true     |
      | PersistentProtection | false    |
    When "apiTeamPlanMemberKey" calls "v1/file/settings" to update file settings and expects 400, 15009, "User doesn't have permission"
      | attribute     | value          |
      | FileName      | PdfFile2KB.pdf |
      | TermsOfAccess | true           |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |


  Scenario: Call POST v1/file/settings endpoint to validate error message: 15008 File record is not found
    Given "apiNegativeTestUser" calls "v1/file/settings" to update file settings with invalid shareGuid "56b0627fe1" and expects 400, 15008, "File record is not found"
      | attribute | value |

  Scenario: Call POST v1/file/settings endpoint to validate error message: 15006 Request body is null
    Then "apiNegativeTestUser" calls "v1/file/settings" to update file settings with no request body and expects 400, 15006, "Request body is null"
      | attribute | value |
