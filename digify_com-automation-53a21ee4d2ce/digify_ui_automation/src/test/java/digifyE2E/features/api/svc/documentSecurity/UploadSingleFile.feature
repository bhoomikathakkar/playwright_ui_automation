@uploadSingleFile @svc @documentSecuritySvc @regression

Feature: Validate v1/file/upload endpoint for sharing a file with recipient via upload, covering both success and error scenarios.

  Scenario Outline: Call the v1/file/upload endpoint to share a Restrict File ensuring that Watermark and Print settings are enabled and Download is disabled.
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser2" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute            | value           |
      | permission           | restrict        |
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
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute            | value           |
      | permission           | Restrict        |
      | Watermark            | true            |
      | Watermark_text       | Test Automation |
      | Watermark_text_line2 | Line 2          |
      | Watermark_opacity    | 0.5             |
      | Watermark_color      | red             |
      | Watermark_size       | small           |
      | Download             | 0               |
      | Print                | 5               |
      | PersistentProtection | false           |
      | RestrictForwarding   | true            |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | file           |
      | svcApiTeamAdminKey | PdfFile2KB.pdf |
      | apiBussAdminKey    | pptFile.pptx   |

  Scenario Outline: Call the v1/file/upload endpoint to share a Restrict File with Download enabled.
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser2" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute            | value    |
      | permission           | restrict |
      | watermark            | false    |
      | ScreenShield         | false    |
      | TermsOfAccess        | false    |
      | PersistentProtection | true     |
      | Download             | -1       |
      | RestrictForwarding   | false    |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute            | value    |
      | permission           | Restrict |
      | Watermark            | false    |
      | ScreenShield         | false    |
      | TermsOfAccess        | false    |
      | Download             | -1       |
      | PersistentProtection | true     |
      | RestrictForwarding   | false    |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | file           |
      | svcApiTeamAdminKey | PdfFile2KB.pdf |
      | apiBussAdminKey    | pptFile.pptx   |

  Scenario Outline: Call the v1/file/upload endpoint to share a Restrict File ensuring that Expiry (Destruct), TOA, ScreenShield and Print settings are enabled and Download is disable.
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser2" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute            | value    |
      | permission           | restrict |
      | ScreenShield         | true     |
      | Expiry               | Destruct |
      | DestructSeconds      | 1500     |
      | TermsOfAccess        | true     |
      | Download             | 0        |
      | Print                | -1       |
      | PersistentProtection | false    |
      | RestrictForwarding   | true     |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute            | value    |
      | permission           | Restrict |
      | ScreenShield         | true     |
      | Expiry               | destruct |
      | DestructSeconds      | 1500     |
      | TermsOfAccess        | true     |
      | Download             | 0        |
      | Print                | -1       |
      | PersistentProtection | false    |
      | RestrictForwarding   | true     |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | file           |
      | svcApiTeamAdminKey | PdfFile2KB.pdf |
      | apiBussAdminKey    | pptFile.pptx   |

  Scenario Outline: Call the v1/file/upload endpoint to share a Restrict File ensuring that Expiry (FixedDate), TOA, Add. Verification and Print settings are enabled and Download is disable.
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser2" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute              | value      |
      | permission             | restrict   |
      | Expiry                 | Fixeddate  |
      | ExpiryDate             | 2026-12-29 |
      | TermsOfAccess          | true       |
      | Download               | 0          |
      | Print                  | 10         |
      | PersistentProtection   | false      |
      | RestrictForwarding     | true       |
      | AdditionalVerification | true       |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute              | value      |
      | permission             | Restrict   |
      | Expiry                 | fixeddate  |
      | ExpiryDate             | 2026-12-29 |
      | TermsOfAccess          | true       |
      | Download               | 0          |
      | Print                  | 10         |
      | PersistentProtection   | false      |
      | RestrictForwarding     | true       |
      | AdditionalVerification | true       |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | file           |
      | svcApiTeamAdminKey | PdfFile2KB.pdf |
      | apiBussAdminKey    | pptFile.pptx   |

  Scenario Outline: Call the v1/file/upload endpoint to share a Passkey File ensuring that Password is enabled.
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser2" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute  | value   |
      | Permission | Passkey |
      | Password   | test098 |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute  | value   |
      | permission | Passkey |
      | password   | test098 |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | file           |
      | svcApiTeamAdminKey | PdfFile2KB.pdf |
      | apiBussAdminKey    | pptFile.pptx   |

  Scenario Outline: Call the v1/file/upload endpoint to share a Passkey File ensuring that Watermark, ScreenShield and Print, Expiry settings are enabled and Download is disabled.
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser2" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute            | value           |
      | permission           | Passkey         |
      | password             | test098         |
      | watermark            | true            |
      | Watermark_text       | Test Automation |
      | Watermark_text_line2 | Line 2          |
      | Watermark_opacity    | 0.4             |
      | Watermark_color      | Grey            |
      | Watermark_size       | Footer          |
      | Download             | 0               |
      | Print                | -1              |
      | PersistentProtection | false           |
      | ScreenShield         | true            |
      | Expiry               | Destruct        |
      | DestructSeconds      | 1500            |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute            | value           |
      | permission           | Passkey         |
      | password             | test098         |
      | Watermark            | true            |
      | Watermark_text       | Test Automation |
      | Watermark_text_line2 | Line 2          |
      | Watermark_opacity    | 0.4             |
      | Watermark_color      | grey            |
      | Watermark_size       | footer          |
      | Download             | 0               |
      | Print                | -1              |
      | PersistentProtection | false           |
      | RestrictForwarding   | false           |
      | ScreenShield         | true            |
      | Expiry               | destruct        |
      | DestructSeconds      | 1500            |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | file           |
      | svcApiTeamAdminKey | PdfFile2KB.pdf |
      | apiBussAdminKey    | pptFile.pptx   |

  Scenario Outline: Call the v1/file/upload endpoint to share a Public File ensuring that Expiry (Destruct), Download, and Print settings are enabled and RequestEmail is disabled.
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser2" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute       | value    |
      | Permission      | Public   |
      | RequestEmail    | false    |
      | Expiry          | Destruct |
      | DestructSeconds | 600      |
      | Download        | -1       |
      | Print           | -1       |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute       | value  |
      | Permission      | Public |
      | Download        | -1     |
      | Print           | -1     |
      | DestructSeconds | 600    |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | file           |
      | svcApiTeamAdminKey | PdfFile2KB.pdf |
      | apiBussAdminKey    | pptFile.pptx   |

  Scenario Outline: Call the v1/file/upload endpoint to share a Public File ensuring that Watermark, ScreenShield and Print, Download settings are enabled.
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser2" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute            | value           |
      | permission           | Public          |
      | watermark            | true            |
      | Watermark_text       | Test Automation |
      | Watermark_text_line2 | Line 2          |
      | Watermark_opacity    | 0.4             |
      | Watermark_color      | Blue            |
      | Watermark_size       | Center          |
      | Download             | -1              |
      | Print                | -1              |
      | PersistentProtection | false           |
      | ScreenShield         | true            |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute            | value           |
      | permission           | Public          |
      | Watermark            | true            |
      | Watermark_text       | Test Automation |
      | Watermark_text_line2 | Line 2          |
      | Watermark_opacity    | 0.4             |
      | Watermark_color      | blue            |
      | Watermark_size       | diagonal        |
      | Download             | -1              |
      | Print                | -1              |
      | PersistentProtection | false           |
      | RestrictForwarding   | false           |
      | ScreenShield         | true            |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | file           |
      | svcApiTeamAdminKey | PdfFile2KB.pdf |
      | apiBussAdminKey    | pptFile.pptx   |

  Scenario Outline: Call the v1/file/upload endpoint to share a Public File ensuring that Print is disabled, and Download, Request Email settings are enabled.
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute    | value  |
      | permission   | Public |
      | RequestEmail | true   |
      | Download     | -1     |
      | Print        | 0      |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute    | value  |
      | permission   | Public |
      | RequestEmail | true   |
      | Print        | 0      |
      | Download     | -1     |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | file           |
      | svcApiTeamAdminKey | PdfFile2KB.pdf |
      | apiBussAdminKey    | pptFile.pptx   |

  Scenario Outline: Call the v1/file/upload endpoint to share a Public File ensuring that  Download and Print are disabled, Additional Verification settings is enabled.
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute              | value  |
      | permission             | Public |
      | AdditionalVerification | true   |
      | Download               | 0      |
      | Print                  | 0      |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute              | value  |
      | permission             | Public |
      | AdditionalVerification | false  |
      | Print                  | 0      |
      | Download               | 0      |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | file           |
      | svcApiTeamAdminKey | PdfFile2KB.pdf |
      | apiBussAdminKey    | pptFile.pptx   |

  Scenario Outline: Call the v1/file/upload endpoint to share a Restrict File ensuring that Download, Additional Verification and Print settings are enabled.
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser2" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute              | value    |
      | permission             | Restrict |
      | AdditionalVerification | true     |
      | Download               | -1       |
      | Print                  | -1       |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute              | value    |
      | permission             | Restrict |
      | AdditionalVerification | true     |
      | Print                  | -1       |
      | Download               | -1       |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | file           |
      | svcApiTeamAdminKey | PdfFile2KB.pdf |
      | apiBussAdminKey    | pptFile.pptx   |

  Scenario: Call the v1/file/upload endpoint to validate error message: 40022 Expiry date must be set after Start date
    Given "apiNegativeTestUser" calls "v1/file/upload" to share "PdfFile2KB.pdf" with "recipientUser" and expects 400, 40022, "Expiry date must be set after Start date"
      | attribute  | value      |
      | permission | restrict   |
      | Expiry     | Fixeddate  |
      | StartDate  | 2026-12-29 |
      | ExpiryDate | 2026-12-28 |

  Scenario: Call the v1/file/upload endpoint to validate error message: 40023 Expiry date cannot be set in the past
    Given "apiNegativeTestUser" calls "v1/file/upload" to share "PdfFile.pdf" with "recipientUser" and expects 400, 40023, "Expiry date cannot be set in the past"
      | attribute  | value      |
      | permission | restrict   |
      | Expiry     | Fixeddate  |
      | StartDate  | 2025-04-12 |
      | ExpiryDate | 2025-04-13 |

  Scenario: Call the v1/file/upload endpoint to validate error message: 40024 Expiry date cannot be blank
    Given "apiNegativeTestUser" calls "v1/file/upload" to share "PdfFile2KB.pdf" with "recipientUser" and expects 400, 40024, "Expiry date must not be blank"
      | attribute  | value     |
      | permission | restrict  |
      | Expiry     | Fixeddate |

  Scenario: Call the v1/file/upload endpoint to validate error message: 40026 Watermark opacity must be between 0.1 and 0.5 (opacity is 0.9)
    Given "apiNegativeTestUser" calls "v1/file/upload" to share "PdfFile2KB.pdf" with "recipientUser" and expects 400, 40026, "Watermark opacity must be between 0.1 and 0.5"
      | attribute          | value    |
      | permission         | restrict |
      | RestrictForwarding | true     |
      | watermark          | true     |
      | Watermark_opacity  | 0.9      |

  Scenario: Call the v1/file/upload endpoint to validate error message: 40026 Watermark opacity must be between 0.1 and 0.5 (opacity is 0.0)
    Given "apiNegativeTestUser" calls "v1/file/upload" to share "PdfFile.pdf" with "recipientUser" and expects 400, 40026, "Watermark opacity must be between 0.1 and 0.5"
      | attribute          | value    |
      | permission         | restrict |
      | RestrictForwarding | true     |
      | watermark          | true     |
      | Watermark_opacity  | 0.0      |

  Scenario: Call the v1/file/upload endpoint to validate error message: 40028 PPAD cannot be enabled for public file
    Given "apiNegativeTestUser" calls "v1/file/upload" to share "PdfFile2KB.pdf" with "recipientUser" and expects 400, 40028, "PPAD cannot be enabled for public file"
      | attribute            | value  |
      | permission           | Public |
      | RestrictForwarding   | true   |
      | PersistentProtection | true   |

  Scenario: Call the v1/file/upload endpoint to validate error message: 40029 PPAD can only be enabled if downloading is allowed
    Given "apiNegativeTestUser" calls "v1/file/upload" to share "PdfFile.pdf" with "recipientUser" and expects 400, 40029, "PPAD can only be enabled if downloading is allowed"
      | attribute            | value    |
      | permission           | restrict |
      | RestrictForwarding   | true     |
      | PersistentProtection | true     |

  Scenario: Call the v1/file/upload endpoint to validate error message: 40002 FileSettings cannot be found
    Given "apiNegativeTestUser" calls "v1/file/upload" without FileSettings and expects 400, 40002, "FileSettings cannot be found"
      | attribute  | value    |
      | permission | restrict |

  Scenario: Call the v1/file/upload endpoint to validate error message: 40015 Error in Upload Document
    Given "apiNegativeTestUser" calls "/v1/file/upload" to share file with recipient without request form and expects 400, 40015, "Error in Upload Document"

  Scenario: Call the v1/file/upload endpoint to validate error message: 40005 Password is null for file with passkey permission
    Given "apiNegativeTestUser" calls "v1/file/upload" to share "PdfFile.pdf" with "recipientUser" and expects 400, 40005, "Password is null for file with passkey permission"
      | attribute  | value   |
      | Permission | Passkey |
      | Password   |         |
