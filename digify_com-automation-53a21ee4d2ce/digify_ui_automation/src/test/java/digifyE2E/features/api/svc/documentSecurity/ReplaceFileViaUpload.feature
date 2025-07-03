@replaceFileViaUpload @svc @documentSecuritySvc @regression

Feature: Validate v1/file/replace/upload endpoint for replacing a file via upload, covering both success and error scenarios.

  Scenario Outline: Call "v1/file/replace/upload" endpoint to replace a file which has Watermark, ScreenShield, TOA and Expiry enabled
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute       | value    |
      | permission      | restrict |
      | watermark       | true     |
      | ScreenShield    | false    |
      | TermsOfAccess   | false    |
      | Expiry          | destruct |
      | DestructSeconds | 1500     |
      | Download        | -1       |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute       | value    |
      | permission      | Restrict |
      | Watermark       | true     |
      | ScreenShield    | false    |
      | TermsOfAccess   | false    |
      | Expiry          | destruct |
      | DestructSeconds | 1500     |
      | Download        | -1       |
    Then "<sender>" calls "v1/file/replace/upload" to share "<replaceFile>" and expects 200, 12000, "Successfully replaced file"
      | attribute        | value   |
      | ReplaceComment   | File V2 |
      | NotifyRecipients | false   |
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | file           | replaceFile    |
      | svcApiTeamAdminKey | PdfFile2KB.pdf | PdfFile.pdf    |
      | apiBussAdminKey    | ExcelFile.xlsx | ExcelFile.xlsx |

  Scenario Outline: Call "v1/file/replace/upload" endpoint to replace a file with notify recipient true
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute  | value    |
      | permission | restrict |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute  | value    |
      | permission | Restrict |
    Then "<sender>" calls "v1/file/replace/upload" to share "<replaceFile>" and expects 200, 12000, "Successfully replaced file"
      | attribute        | value   |
      | ReplaceComment   | File V2 |
      | NotifyRecipients | true    |
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | file           | replaceFile    |
      | svcApiTeamAdminKey | PdfFile2KB.pdf | PdfFile.pdf    |
      | apiBussAdminKey    | ExcelFile.xlsx | ExcelFile.xlsx |

  Scenario Outline: Call "v1/file/replace/upload" endpoint to replace a file with different file type, media file
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "<recipient>" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute  | value    |
      | permission | restrict |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute  | value    |
      | permission | Restrict |
    Then "<sender>" calls "v1/file/replace/upload" to replace "<replaceFile>" and expects error 400, <statusCode>, "<statusMsg>"
      | attribute        | value   |
      | ReplaceComment   | File V2 |
      | NotifyRecipients | true    |
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | recipient     | file             | replaceFile      | statusCode | statusMsg                   |
      | svcApiTeamAdminKey | recipientUser | ImageFile.png    | PdfFile2KB.pdf   | 12022      | File type is not the same   |
      | svcApiTeamAdminKey | recipientUser | MP4VideoFile.mp4 | MP4VideoFile.mp4 | 12013      | Media file is not supported |
      | apiBussAdminKey    | recipientUser | Mp3TestFile.mp3  | Mp3TestFile.mp3  | 12013      | Media file is not supported |

  Scenario Outline: Call "v1/file/replace/upload" endpoint to replace a file that has passkey enabled and expect error
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute  | value   |
      | Permission | Passkey |
      | Password   | 123     |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute  | value   |
      | permission | Passkey |
      | password   | 123     |
    Then "<sender>" calls "v1/file/replace/upload" to replace "<replaceFile>" and expects error 400, 12013, "Passcode file is not supported"
      | attribute        | value   |
      | ReplaceComment   | File V2 |
      | NotifyRecipients | false   |
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | file           | replaceFile    |
      | svcApiTeamAdminKey | PdfFile2KB.pdf | PdfFile.pdf    |
      | apiBussAdminKey    | ExcelFile.xlsx | ExcelFile.xlsx |

  Scenario: Call "v1/file/replace/upload" endpoint to replace a file with invalid share guid and expect error
    Given "apiNegativeTestUser" calls "v1/file/replace/upload" to replace "PdfFile2KB.pdf" with invalid ShareGUID "130aff26d4d4b" and expects 400, 12009, "Unable to find original share record from ShareGuid"
      | attribute        | value   |
      | ReplaceComment   | File V2 |
      | NotifyRecipients | false   |

  Scenario: Call "v1/file/replace/upload" endpoint to replace a file with null ShareGUID and expect error
    Given "apiNegativeTestUser" calls "v1/file/replace/upload" to replace "ImageFile.png" with invalid ShareGUID "" and expects 400, 12007, "Share guid is null"
      | attribute        | value   |
      | ReplaceComment   | File V2 |
      | NotifyRecipients | false   |

  Scenario Outline: Call "v1/file/replace/upload" endpoint to replace a file with invalid FileSettings attribute name and expect error
    Given "<sender>" calls "v1/file/upload" to share "ImageFile.png" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute  | value   |
      | Permission | Passkey |
      | Password   | 123     |
    Then "<sender>" calls "v1/file/replace/upload" to replace "ImageFile.png" with invalid attribute name and expects error 400, 12005, "ReplaceFileSettings cannot be found"
      | attribute        | value   |
      | ReplaceComment   | File V2 |
      | NotifyRecipients | false   |
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario: Call "v1/file/replace/upload" endpoint to replace a file with no request form and expect error
    Given "apiNegativeTestUser" calls "v1/file/replace/upload" to replace file with no request form and expects error 400, 12025, "Error in replace file via upload"

  Scenario: Call "v1/file/replace/upload" endpoint to replace a file with data room file and expect error
    Given "apiNegativeTestUser" calls "v1/file/replace/upload" to replace "ImageFile.png" with invalid ShareGUID "dc982caaa14f4d18b8e45bda108fc265" and expects 400, 12009, "Unable to find original share record from ShareGuid"
      | attribute        | value   |
      | ReplaceComment   | File V2 |
      | NotifyRecipients | false   |

  Scenario Outline: Call "v1/file/replace/upload" endpoint to replace a file with invalid character in comment and expect error
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute  | value      |
      | Permission | Restricted |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute  | value      |
      | permission | Restricted |
    Then "<sender>" calls "v1/file/replace/upload" to replace "<file>" with invalid comment and expects 400, 12008, "Replace comment contains invalid character"
      | attribute        | value      |
      | ReplaceComment   | File V<>!2 |
      | NotifyRecipients | false      |
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | file           |
      | svcApiTeamAdminKey | PdfFile2KB.pdf |
      | apiBussAdminKey    | ExcelFile.xlsx |

  Scenario Outline: Call "v1/file/replace/upload" endpoint to validate error message - file.path is not an acceptable file type
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute  | value      |
      | Permission | Restricted |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute  | value      |
      | permission | Restricted |
    Then "<sender>" calls "v1/file/replace/upload" to replace "<replaceFile>" with invalid file path and expects 400, 12020, "ImageHeicFile.heic is not an acceptable file type"
      | attribute        | value   |
      | ReplaceComment   | File V2 |
      | NotifyRecipients | false   |
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | file           | replaceFile        |
      | svcApiTeamAdminKey | PdfFile2KB.pdf | ImageHeicFile.heic |

  Scenario Outline: Call "v1/file/replace/upload" endpoint to validate error message - file.path does not contain any data
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute  | value      |
      | Permission | Restricted |
    When "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute  | value      |
      | permission | Restricted |
    Then "<sender>" calls "v1/file/replace/upload" to replace "<replaceFile>" with blank file path and expects 400, 12019, "BlankFile.txt does not contain any data"
      | attribute        | value   |
      | ReplaceComment   | File V2 |
      | NotifyRecipients | false   |
    And "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Examples:
      | sender             | file           | replaceFile   |
      | svcApiTeamAdminKey | PdfFile2KB.pdf | BlankFile.txt |

  Scenario Outline: Call "v1/file/replace/upload" endpoint to replace a deleted file and expect error
    Given "<sender>" calls "v1/file/upload" to share "<file>" with "recipientUser" and expects 201, 40000, "Successfully shared uploaded document"
      | attribute  | value      |
      | Permission | Restricted |
    And "<sender>" calls "v1/file/settings" to get file settings and expects 200, 20000, "Successfully returned file settings"
      | attribute  | value      |
      | permission | Restricted |
    When "<sender>" calls "v1/file/delete" to delete a file and expects 200, 30000, "Successfully deleted file record"
    Then "<sender>" calls "v1/file/replace/upload" to replace "<replaceFile>" with blank file path and expects 400, 12012, "File has been deleted"
      | attribute        | value   |
      | ReplaceComment   | File V2 |
      | NotifyRecipients | false   |
    Examples:
      | sender             | file           | replaceFile    |
      | svcApiTeamAdminKey | PdfFile2KB.pdf | PdfFile2KB.pdf |