@replaceFile @svc @documentSecuritySvc @regression

Feature: Validate v1/file/share endpoint for replacing a file, covering both success and error scenarios.

  Scenario Outline: Call "v1/file/replace" endpoint to replace a file which has Watermark, ScreenShield, TOA and Expiry enabled
    Given "<sender>" calls "v1/file/share" to share a file "TestPdfFile56.pdf" with "recipientUser" and expects 201, 10000, "Successfully shared document"
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
      | permission           | Restrict |
      | ScreenShield         | true     |
      | TermsOfAccess        | true     |
      | Download             | -1       |
      | Print                | -1       |
      | RestrictForwarding   | true     |
      | PersistentProtection | false    |
    Then "<sender>" calls replace endpoint "v1/file/replace" to replace with "https://www.orimi.com/pdf-test.pdf", expecting 200, 11000, "Successfully replaced file"
      | attribute        | value             |
      | Url              |                   |
      | ReplaceFileName  | TestPdfFile56.pdf |
      | ReplaceComment   | File V2           |
      | NotifyRecipients | false             |
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call "v1/file/replace" endpoint to replace a file with notify recipient true
    Given "<sender>" calls "v1/file/share" to share a file "TestExcelFile.xls" with "recipientUser2" and expects 201, 10000, "Successfully shared document"
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
    Then "<sender>" calls replace endpoint "v1/file/replace" to replace with "https://filesamples.com/samples/document/xls/sample3.xls", expecting 200, 11000, "Successfully replaced file"
      | attribute        | value             |
      | ReplaceFileName  | TestExcelFile.xls |
      | ReplaceComment   | File V2           |
      | NotifyRecipients | true              |
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | apiBussAdminKey    |
      | svcApiTeamAdminKey |

  Scenario Outline: Call "v1/file/replace" endpoint to replace a file with different file type (not with same file format)
    Given "<sender>" calls "v1/file/share" to share a file "TestPdfFile.pdf" with "recipientUser" and expects 201, 10000, "Successfully shared document"
      | attribute            | value                                                    |
      | permission           | restrict                                                 |
      | Url                  | https://filesamples.com/samples/document/pdf/sample2.pdf |
      | PersistentProtection | false                                                    |
      | RestrictForwarding   | true                                                     |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute            | value    |
      | permission           | Restrict |
      | PersistentProtection | false    |
      | RestrictForwarding   | true     |
    Then "<sender>" calls replace endpoint "v1/file/replace" with different file type, expecting error 400, 11018, "File type is not the same"
      | attribute        | value                                                    |
      | Url              | https://filesamples.com/samples/document/xls/sample3.xls |
      | ReplaceFileName  | TestPdfFile.xls                                          |
      | ReplaceComment   | File V2                                                  |
      | NotifyRecipients | true                                                     |
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call "v1/file/replace" endpoint to replace a file that has passkey enabled
    Given "<sender>" calls "v1/file/share" to share a file "TestPdfFile.pdf" with "recipientUser2" and expects 201, 10000, "Successfully shared document"
      | attribute  | value                                                    |
      | Permission | Passkey                                                  |
      | Password   | 123                                                      |
      | Url        | https://filesamples.com/samples/document/pdf/sample2.pdf |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute  | value   |
      | permission | Passkey |
      | password   | 123     |
    Then "<sender>" calls replace endpoint "v1/file/replace" with different file type, expecting error 400, 11016, "Passcode file is not supported"
      | attribute        | value                                                    |
      | Url              | https://filesamples.com/samples/document/pdf/sample1.pdf |
      | ReplaceFileName  | TestPdfFile.pdf                                          |
      | ReplaceComment   | File V2                                                  |
      | NotifyRecipients | false                                                    |
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario: Call "v1/file/replace" endpoint to replace a file with invalid share guid
    Given "apiNegativeTestUser" calls replace endpoint "v1/file/replace" with invalid ShareGUID "130aff26d4d4b", expecting 400, 11012, "Unable to find original share record from ShareGuid"
      | attribute        | value                              |
      | Url              | https://www.orimi.com/pdf-test.pdf |
      | ReplaceFileName  | TestPdfFile.pdf                    |
      | ReplaceComment   | File V2                            |
      | NotifyRecipients | false                              |

  Scenario: Call "v1/file/replace" endpoint to replace a file with null ShareGUID
    Given "apiNegativeTestUser" calls replace endpoint "v1/file/replace" with invalid ShareGUID "", expecting 400, 11006, "Share guid is null"
      | attribute        | value                              |
      | Url              | https://www.orimi.com/pdf-test.pdf |
      | ReplaceFileName  | TestPdfFile.pdf                    |
      | ReplaceComment   | File V2                            |
      | NotifyRecipients | false                              |

  Scenario Outline: Call "v1/file/replace" endpoint to replace a file with null Url
    Given "<sender>" calls "v1/file/share" to share a file "TestPdfFile.pdf" with "<recipient>" and expects 201, 10000, "Successfully shared document"
      | attribute  | value                              |
      | Permission | Passkey                            |
      | Password   | 123                                |
      | Url        | https://www.orimi.com/pdf-test.pdf |
    Then "<sender>" calls replace endpoint "v1/file/replace" with null url, expecting 400, 11008, "URL is null"
      | attribute        | value           |
      | Url              |                 |
      | ReplaceFileName  | TestPdfFile.pdf |
      | ReplaceComment   | File V2         |
      | NotifyRecipients | false           |
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call "v1/file/replace" endpoint to replace a file with null Replace file name
    Given "<sender>" calls "v1/file/share" to share a file "TestPdfFile.pdf" with "recipientUser2" and expects 201, 10000, "Successfully shared document"
      | attribute  | value                              |
      | Permission | Passkey                            |
      | Password   | 123                                |
      | Url        | https://www.orimi.com/pdf-test.pdf |
    Then "<sender>" calls replace endpoint "v1/file/replace" with null replace file name, expecting 400, 11007, "Replace file name is null"
      | attribute        | value                              |
      | Url              | https://www.orimi.com/pdf-test.pdf |
      | ReplaceFileName  |                                    |
      | ReplaceComment   | File V2                            |
      | NotifyRecipients | false                              |
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call "v1/file/replace" endpoint to replace a file with invalid url
    Given "<sender>" calls "v1/file/share" to share a file "TestPdfFile.pdf" with "recipientUser" and expects 201, 10000, "Successfully shared document"
      | attribute  | value                              |
      | Permission | Passkey                            |
      | Password   | 123                                |
      | Url        | https://www.orimi.com/pdf-test.pdf |
    Then "<sender>" calls replace endpoint "v1/file/replace" with invalid url, expecting 400, 11009, "URL is invalid"
      | attribute        | value                  |
      | Url              | orimi.com/pdf-test.pdf |
      | ReplaceFileName  | TestPdfFile.pdf        |
      | ReplaceComment   | File V2                |
      | NotifyRecipients | false                  |
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario: Call "v1/file/replace" endpoint to replace a file with data room file and validate error
    Then "apiNegativeTestUser" calls replace endpoint "v1/file/replace" with DR-File ShareGUID "0389e5293d3442568f6f01b8fc5af332", expecting 400, 11012, "Unable to find original share record from ShareGuid"
      | attribute       | value                              |
      | Url             | https://www.orimi.com/pdf-test.pdf |
      | ReplaceFileName | TestPdfFile.pdf                    |
      | ReplaceComment  | File V2                            |

  Scenario Outline: Call "v1/file/replace" endpoint to replace a file when replace comment contains invalid character
    Given "<sender>" calls "v1/file/share" to share a file "TestPdfFile.pdf" with "recipientUser" and expects 201, 10000, "Successfully shared document"
      | attribute  | value                              |
      | Permission | Restricted                         |
      | Url        | https://www.orimi.com/pdf-test.pdf |
    Then "<sender>" calls replace endpoint "v1/file/replace" with invalid comment, expecting 400, 11011, "Replace comment contains invalid character"
      | attribute        | value                              |
      | Url              | https://www.orimi.com/pdf-test.pdf |
      | ReplaceFileName  | TestPdfFile.pdf                    |
      | ReplaceComment   | File V2<>                          |
      | NotifyRecipients | false                              |
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario: Call "v1/file/replace" endpoint to replace a deleted file
    Given "apiNegativeTestUser" calls replace endpoint "v1/file/replace" with DR-File ShareGUID "82e9ec3ed6b54ac4866ccad1bd7b0b6f", expecting 400, 11015, "File has been deleted"
      | attribute       | value                              |
      | ReplaceFileName | TestPdfFile.pdf                    |
      | Url             | https://www.orimi.com/pdf-test.pdf |