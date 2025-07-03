@shareSingleFile @svc @documentSecuritySvc @regression

Feature: Validate v1/file/share endpoint for sharing a file to a recipient, covering both success and error scenarios.

  Scenario Outline: Call the v1/file/share endpoint to share a file with a recipient, ensuring Restrict, TOA, Download, Print and ScreenShield settings.
    Given "<sender>" calls "v1/file/share" to share a file "TestPdfFile.pdf" with "recipientUser" and expects 201, 10000, "Successfully shared document"
      | attribute            | value                              |
      | Permission           | Restrict                           |
      | PersistentProtection | false                              |
      | Url                  | https://www.orimi.com/pdf-test.pdf |
      | TermsOfAccess        | true                               |
      | ScreenShield         | true                               |
      | Download             | -1                                 |
      | Print                | -1                                 |
      | RestrictForwarding   | true                               |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute            | value    |
      | Permission           | Restrict |
      | TermsOfAccess        | true     |
      | ScreenShield         | true     |
      | Download             | -1       |
      | Print                | -1       |
      | PersistentProtection | false    |
      | RestrictForwarding   | true     |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call the v1/file/share endpoint to share a file ensuring Watermark, Print and Restrict settings and without Download
    Given "<sender>" calls "v1/file/share" to share a file "TestDocxFile.docx" with "recipientUser" and expects 201, 10000, "Successfully shared document"
      | attribute            | value                                                      |
      | Permission           | Restrict                                                   |
      | PersistentProtection | false                                                      |
      | RestrictForwarding   | true                                                       |
      | Url                  | https://filesamples.com/samples/document/docx/sample3.docx |
      | watermark            | true                                                       |
      | Watermark_text       | Test Automation                                            |
      | Watermark_text_line2 | Line 2                                                     |
      | Watermark_opacity    | 0.5                                                        |
      | Watermark_color      | Red                                                        |
      | Watermark_size       | Center                                                     |
      | Download             | 0                                                          |
      | Print                | 5                                                          |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute            | value           |
      | permission           | Restrict        |
      | PersistentProtection | false           |
      | RestrictForwarding   | true            |
      | watermark            | true            |
      | Watermark_text       | Test Automation |
      | Watermark_text_line2 | Line 2          |
      | Watermark_opacity    | 0.5             |
      | Watermark_color      | red             |
      | Watermark_size       | diagonal        |
      | Download             | 0               |
      | Print                | 5               |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call the v1/file/share endpoint to share a file with download enabled
    Given "<sender>" calls "v1/file/share" to share a file "TestExcelFile.xls" with "recipientUser" and expects 201, 10000, "Successfully shared document"
      | attribute            | value                                                    |
      | permission           | restrict                                                 |
      | Url                  | https://filesamples.com/samples/document/xls/sample2.xls |
      | watermark            | false                                                    |
      | ScreenShield         | false                                                    |
      | TermsOfAccess        | false                                                    |
      | PersistentProtection | true                                                     |
      | Download             | -1                                                       |
      | RestrictForwarding   | false                                                    |
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
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call the v1/file/share endpoint to share a file ensuring TOA, Expiry(Destruct) ScreenShield and Print settings and without Download
    Given "<sender>" calls "v1/file/share" to share a file "TestExcelFile.xls" with "recipientUser" and expects 201, 10000, "Successfully shared document"
      | attribute            | value                                                    |
      | permission           | restrict                                                 |
      | Url                  | https://filesamples.com/samples/document/xls/sample2.xls |
      | ScreenShield         | true                                                     |
      | Expiry               | Destruct                                                 |
      | DestructSeconds      | 1500                                                     |
      | TermsOfAccess        | true                                                     |
      | Download             | 0                                                        |
      | Print                | -1                                                       |
      | PersistentProtection | false                                                    |
      | RestrictForwarding   | true                                                     |
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
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call the v1/file/share endpoint to share a file ensuring TOA, Expiry (FixedDate) and Print settings are enabled and Download is disabled
    Given "<sender>" calls "v1/file/share" to share a file "TestPPTFile.ppt" with "recipientUser" and expects 201, 10000, "Successfully shared document"
      | attribute              | value                                                    |
      | permission             | restrict                                                 |
      | Url                    | https://filesamples.com/samples/document/ppt/sample2.ppt |
      | Expiry                 | Fixeddate                                                |
      | ExpiryDate             | 2026-12-29                                               |
      | TermsOfAccess          | true                                                     |
      | Download               | 0                                                        |
      | Print                  | 10                                                       |
      | PersistentProtection   | false                                                    |
      | RestrictForwarding     | true                                                     |
      | AdditionalVerification | true                                                     |
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
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call the v1/file/share endpoint to share a file with Passkey and Password settings
    Given "<sender>" calls "v1/file/share" to share a file "TestPPTFile.ppt" with "recipientUser" and expects 201, 10000, "Successfully shared document"
      | attribute  | value                                                    |
      | Permission | Passkey                                                  |
      | Url        | https://filesamples.com/samples/document/ppt/sample2.ppt |
      | Password   | test098                                                  |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute  | value   |
      | permission | Passkey |
      | password   | test098 |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call the v1/file/share endpoint to share a Public File with Expiry, Download, and Print settings
    Given "<sender>" calls "v1/file/share" to share a file "TestPdfFile.pdf" with "recipientUser" and expects 201, 10000, "Successfully shared document"
      | attribute       | value                              |
      | Permission      | Public                             |
      | Url             | https://www.orimi.com/pdf-test.pdf |
      | RequestEmail    | false                              |
      | Expiry          | Destruct                           |
      | DestructSeconds | 600                                |
      | Download        | -1                                 |
      | Print           | -1                                 |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute       | value    |
      | Permission      | Public   |
      | Download        | -1       |
      | Print           | -1       |
      | Expiry          | destruct |
      | DestructSeconds | 600      |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call the v1/file/share endpoint to share a public file with a recipient, ensuring TOA, ScreenShield, Download, Watermark settings are enabled.
    Given "<sender>" calls "v1/file/share" to share a file "TestPdfFile.pdf" with "recipientUser" and expects 201, 10000, "Successfully shared document"
      | attribute            | value                              |
      | Permission           | Public                             |
      | PersistentProtection | false                              |
      | Url                  | https://www.orimi.com/pdf-test.pdf |
      | TermsOfAccess        | true                               |
      | ScreenShield         | true                               |
      | Download             | -1                                 |
      | RestrictForwarding   | false                              |
      | watermark            | true                               |
      | Watermark_text       | Test Automation                    |
      | Watermark_text_line2 | Line 2                             |
      | Watermark_opacity    | 0.4                                |
      | Watermark_color      | Grey                               |
      | Watermark_size       | Footer                             |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute            | value           |
      | Permission           | Public          |
      | PersistentProtection | false           |
      | TermsOfAccess        | true            |
      | ScreenShield         | true            |
      | Download             | -1              |
      | RestrictForwarding   | false           |
      | watermark            | true            |
      | Watermark_text       | Test Automation |
      | Watermark_text_line2 | Line 2          |
      | Watermark_opacity    | 0.4             |
      | Watermark_color      | grey            |
      | Watermark_size       | footer          |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call the v1/file/share endpoint to share a file with Passkey,Download, Expiry, Watermark, ScreenShield, TOA and Password settings are enabled
    Given "<sender>" calls "v1/file/share" to share a file "TestPPTFile.ppt" with "recipientUser" and expects 201, 10000, "Successfully shared document"
      | attribute            | value                                                    |
      | Permission           | Passkey                                                  |
      | Url                  | https://filesamples.com/samples/document/ppt/sample2.ppt |
      | Password             | test098                                                  |
      | Expiry               | Destruct                                                 |
      | DestructSeconds      | 600                                                      |
      | TermsOfAccess        | true                                                     |
      | ScreenShield         | true                                                     |
      | Download             | -1                                                       |
      | watermark            | true                                                     |
      | Watermark_text       | Test Automation                                          |
      | Watermark_text_line2 | Line 2                                                   |
      | Watermark_opacity    | 0.4                                                      |
      | Watermark_color      | Blue                                                     |
      | Watermark_size       | Tile                                                     |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute            | value           |
      | permission           | Passkey         |
      | password             | test098         |
      | Expiry               | destruct        |
      | DestructSeconds      | 600             |
      | TermsOfAccess        | true            |
      | ScreenShield         | true            |
      | Download             | -1              |
      | watermark            | true            |
      | Watermark_text       | Test Automation |
      | Watermark_text_line2 | Line 2          |
      | Watermark_opacity    | 0.4             |
      | Watermark_color      | blue            |
      | Watermark_size       | small           |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call the v1/file/share endpoint to share a Public File to a recipient with Request Email enabled
    Given "<sender>" calls "v1/file/share" to share a file "TestPdfFile00.pdf" with "recipientUser" and expects 201, 10000, "Successfully shared document"
      | attribute    | value                              |
      | permission   | Public                             |
      | Url          | https://www.orimi.com/pdf-test.pdf |
      | RequestEmail | true                               |
      | Download     | -1                                 |
      | Print        | 0                                  |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute    | value  |
      | permission   | Public |
      | RequestEmail | true   |
      | Print        | 0      |
      | Download     | -1     |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call the v1/file/share endpoint to share a Public File to a recipient with additional verification enabled
    Given "<sender>" calls "v1/file/share" to share a file "TestVideoFile.mp4" with "recipientUser" and expects 201, 10000, "Successfully shared document"
      | attribute              | value                                                        |
      | permission             | Public                                                       |
      | Url                    | https://filesamples.com/samples/video/mp4/sample_960x540.mp4 |
      | AdditionalVerification | true                                                         |
      | Download               | 0                                                            |
      | Print                  | 0                                                            |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute              | value  |
      | permission             | Public |
      | AdditionalVerification | false  |
      | Print                  | 0      |
      | Download               | 0      |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call the v1/file/share endpoint to share a Restrict File to a recipient with additional verification enabled
    Given "<sender>" calls "v1/file/share" to share a file "TestPdfFile.pdf" with "recipientUser" and expects 201, 10000, "Successfully shared document"
      | attribute              | value                              |
      | permission             | Restrict                           |
      | Url                    | https://www.orimi.com/pdf-test.pdf |
      | AdditionalVerification | true                               |
      | Download               | -1                                 |
      | Print                  | -1                                 |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute              | value    |
      | permission             | Restrict |
      | AdditionalVerification | true     |
      | Print                  | -1       |
      | Download               | -1       |
    Then "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario: Call the v1/file/share endpoint to validate error message: 10022 Expiry date must be set after Start date
    Given "apiNegativeTestUser" calls "v1/file/share" to share a file "TestPdfFile.pdf" with "recipientUser" and expects 400, 10022, "Expiry date must be set after Start date"
      | attribute  | value                              |
      | permission | restrict                           |
      | Url        | https://www.orimi.com/pdf-test.pdf |
      | Expiry     | Fixeddate                          |
      | StartDate  | 2026-12-29                         |
      | ExpiryDate | 2026-12-28                         |

  Scenario: Call the v1/file/share endpoint to validate error message: 10023 Expiry date cannot be set in the past
    Given "apiNegativeTestUser" calls "v1/file/share" to share a file "TestPdfFile.pdf" with "recipientUser" and expects 400, 10023, "Expiry date cannot be set in the past"
      | attribute  | value                              |
      | permission | restrict                           |
      | Url        | https://www.orimi.com/pdf-test.pdf |
      | Expiry     | Fixeddate                          |
      | StartDate  | 2025-04-12                         |
      | ExpiryDate | 2025-04-13                         |

  Scenario: Call the v1/file/share endpoint to validate error message: 10024 Expiry date must not be blank
    Given "apiNegativeTestUser" calls "v1/file/share" to share a file "TestPdfFile.pdf" with "recipientUser" and expects 400, 10024, "Expiry date must not be blank"
      | attribute  | value                              |
      | permission | restrict                           |
      | Url        | https://www.orimi.com/pdf-test.pdf |
      | Expiry     | Fixeddate                          |


  Scenario: Call the v1/file/share endpoint to validate error message: 10004 File name is null
    Given "apiNegativeTestUser" calls "v1/file/share" to share a file "" with "recipientUser" and expects 400, 10004, "File name is null"
      | attribute          | value                              |
      | permission         | restrict                           |
      | Url                | https://www.orimi.com/pdf-test.pdf |
      | RestrictForwarding | true                               |

  Scenario: Call the v1/file/share endpoint to validate error message: 10027 Watermark opacity must be between 0.1 and 0.5
    Given "apiNegativeTestUser" calls "v1/file/share" to share a file "TestPdfFile.pdf" with "recipientUser" and expects 400, 10027, "Watermark opacity must be between 0.1 and 0.5"
      | attribute          | value                              |
      | permission         | restrict                           |
      | Url                | https://www.orimi.com/pdf-test.pdf |
      | RestrictForwarding | true                               |
      | watermark          | true                               |
      | Watermark_opacity  | 0.9                                |

  Scenario: Call the v1/file/share endpoint to validate error message: 10027 Watermark opacity must be between 0.1 and 0.5 (opacity is 0.0)
    Given "apiNegativeTestUser" calls "v1/file/share" to share a file "TestPdfFile.pdf" with "recipientUser" and expects 400, 10027, "Watermark opacity must be between 0.1 and 0.5"
      | attribute          | value                              |
      | permission         | restrict                           |
      | Url                | https://www.orimi.com/pdf-test.pdf |
      | RestrictForwarding | true                               |
      | watermark          | true                               |
      | Watermark_opacity  | 0.0                                |

  Scenario:Call the v1/file/share endpoint to validate error message: 10031 PPAD cannot be enabled for public file
    Given "apiNegativeTestUser" calls "v1/file/share" to share a file "TestPdfFile.pdf" with "recipientUser" and expects 400, 10031, "PPAD cannot be enabled for public file"
      | attribute            | value                              |
      | permission           | Public                             |
      | Url                  | https://www.orimi.com/pdf-test.pdf |
      | RestrictForwarding   | true                               |
      | PersistentProtection | true                               |


  Scenario:Call the v1/file/share endpoint to validate error message: 10029 PPAD can only be enabled if downloading is allowed
    Given "apiNegativeTestUser" calls "v1/file/share" to share a file "TestPdfFile.pdf" with "recipientUser" and expects 400, 10029, "PPAD can only be enabled if downloading is allowed"
      | attribute            | value                              |
      | permission           | restrict                           |
      | Url                  | https://www.orimi.com/pdf-test.pdf |
      | RestrictForwarding   | true                               |
      | PersistentProtection | true                               |

  Scenario:Call the v1/file/share endpoint to validate error message: 10005 Url is null
    Given "apiNegativeTestUser" calls "v1/file/share" to share a file "TestPdfFile.pdf" with "recipientUser" and expects 400, 10005, "URL is null"
      | attribute            | value    |
      | permission           | restrict |
      | Url                  |          |
      | RestrictForwarding   | true     |
      | PersistentProtection | true     |

  Scenario:Call the v1/file/share endpoint to validate error message: 10013 Url is invalid
    Given "apiNegativeTestUser" calls "v1/file/share" to share a file "TestPdfFile.pdf" with "recipientUser" and expects 400, 10013, "URL is invalid"
      | attribute            | value                  |
      | permission           | restrict               |
      | Url                  | orimi.com/pdf-test.pdf |
      | RestrictForwarding   | true                   |
      | PersistentProtection | true                   |

  Scenario:Call the v1/file/share endpoint to validate error message: 10026 An extension is required in the file name
    Given "apiNegativeTestUser" calls "v1/file/share" to share a file "TestPdfFile" with "recipientUser" and expects 400, 10026, "An extension is required in the file name"
      | attribute            | value                              |
      | permission           | restrict                           |
      | Url                  | https://www.orimi.com/pdf-test.pdf |
      | RestrictForwarding   | true                               |
      | PersistentProtection | true                               |

  Scenario:Call the v1/file/share endpoint to validate error message: 10004 File name contains invalid character
    Given "apiNegativeTestUser" calls "v1/file/share" to share a file "TestPdfFile<>.pdf" with "recipientUser" and expects 400, 10004, "File name contains invalid character"
      | attribute            | value                              |
      | permission           | restrict                           |
      | Url                  | https://www.orimi.com/pdf-test.pdf |
      | RestrictForwarding   | true                               |
      | PersistentProtection | true                               |

  Scenario: Call the v1/file/share endpoint to validate error message: 10008 Password is null
    Given "apiNegativeTestUser" calls "v1/file/share" to share a file "TestPdfFile.pdf" with "recipientUser" and expects 400, 10008, "Password is null"
      | attribute  | value                              |
      | Permission | Passkey                            |
      | Url        | https://www.orimi.com/pdf-test.pdf |
      | Password   |                                    |