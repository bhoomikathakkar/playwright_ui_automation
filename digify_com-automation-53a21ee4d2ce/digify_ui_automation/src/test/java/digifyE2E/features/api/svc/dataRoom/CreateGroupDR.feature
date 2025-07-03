@createGroupDR @svc @dataRoomSvc @regression

Feature: Validate /v1/dataroom/group/create endpoint to create a group in DR, covering both success and error scenarios.

  Scenario Outline: Call /v1/dataroom/group/create to create a group in data room with Print Permission
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value             |
      | DataRoomName           | TestDataRoomBySVC |
      | Access                 | Any               |
      | AdditionalVerification | true              |
      | DefaultPermission      | NoAccess          |
      | Screen_shield          | Individual        |
      | TermsOfAccess          | false             |
      | Watermark              | false             |
      | AboutPage              | true              |
      | HideBanner             | true              |
    When "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute  | value |
      | GroupName  | hello |
      | Permission | Print |
      | PrintCount | 20    |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/group/create to create a group in data room with NoAccess Permission
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | DefaultPermission | View              |
      | AboutPage         | true              |
      | HideBanner        | false             |
    When "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute  | value    |
      | GroupName  | DR-Group |
      | Permission | NoAccess |
    Then "<drOwner>" calls "v1/dataroom/group" to validate group in DR, expecting 200, 20000, "Successfully retrieved data room group list"
      | attribute  | value    |
      | GroupName  | DR-Group |
      | Permission | NoAccess |
    And "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/group/create to create a group in data room with DownloadPDF Permission
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | DefaultPermission | Contributor       |
      | AboutPage         | true              |
      | HideBanner        | false             |
    When "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute  | value       |
      | GroupName  | DR-Group    |
      | Permission | DownloadPDF |
    Then "<drOwner>" calls "v1/dataroom/group" to validate group in DR, expecting 200, 20000, "Successfully retrieved data room group list"
      | attribute  | value       |
      | GroupName  | DR-Group    |
      | Permission | DownloadPDF |
    And "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/group/create to create a group in data room with DownloadOriginal Permission
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Any               |
      | DefaultPermission | Print             |
      | DefaultPrintCount | 10                |
      | AboutPage         | false             |
      | HideBanner        | true              |
      | Watermark         | true              |
    When "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute  | value            |
      | GroupName  | DR-Group         |
      | Permission | DownloadOriginal |
    Then "<drOwner>" calls "v1/dataroom/group" to validate group in DR, expecting 200, 20000, "Successfully retrieved data room group list"
      | attribute  | value            |
      | GroupName  | DR-Group         |
      | Permission | DownloadOriginal |
    And "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/group/create to create a group in data room with Contributor Permission
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Any               |
      | DefaultPermission | DownloadPDF       |
      | AboutPage         | false             |
      | HideBanner        | true              |
      | Watermark         | true              |
    When "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute  | value       |
      | GroupName  | DR-Group    |
      | Permission | Contributor |
    Then "<drOwner>" calls "v1/dataroom/group" to validate group in DR, expecting 200, 20000, "Successfully retrieved data room group list"
      | attribute  | value       |
      | GroupName  | DR-Group    |
      | Permission | Contributor |
    And "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/group/create to create a group in data room with View Permission
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Any               |
      | DefaultPermission | NoAccess          |
      | AboutPage         | false             |
      | HideBanner        | false             |
      | Watermark         | false             |
    When "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute  | value        |
      | GroupName  | DR-Group-001 |
      | Permission | View         |
    Then "<drOwner>" calls "v1/dataroom/group" to validate group in DR, expecting 200, 20000, "Successfully retrieved data room group list"
      | attribute  | value        |
      | GroupName  | DR-Group-001 |
      | Permission | View         |
    And "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/group/create to create a group with Expiry and View Permission in data room
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Any               |
      | DefaultPermission | NoAccess          |
      | AboutPage         | false             |
      | HideBanner        | false             |
      | Watermark         | false             |
    When "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute   | value        |
      | GroupName   | DR-Group-001 |
      | Permission  | View         |
      | GroupExpiry | 2026-12-29   |
    Then "<drOwner>" calls "v1/dataroom/group" to validate group in DR, expecting 200, 20000, "Successfully retrieved data room group list"
      | attribute   | value        |
      | GroupName   | DR-Group-001 |
      | Permission  | View         |
      | GroupExpiry | 2026-12-29   |
    And "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/group/create to create a group in data room with Expiry and Print Permission
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value             |
      | DataRoomName           | TestDataRoomBySVC |
      | Access                 | Domain            |
      | Domain                 | @gmail.com        |
      | AdditionalVerification | true              |
      | DefaultPermission      | Print             |
      | DefaultPrintCount      | 999               |
      | Screen_shield          | On                |
      | HideBanner             | true              |
    When "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute   | value             |
      | GroupName   | Group with Expiry |
      | Permission  | Print             |
      | PrintCount  | 900               |
      | GroupExpiry | 2026-12-29        |
    Then "<drOwner>" calls "v1/dataroom/group" to validate group in DR, expecting 200, 20000, "Successfully retrieved data room group list"
      | attribute   | value             |
      | GroupName   | Group with Expiry |
      | Permission  | Print             |
      | PrintCount  | 900               |
      | GroupExpiry | 2026-12-29        |
    And "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/group/create with no request body and verify error 10005 Request data is null
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Any               |
      | DefaultPermission | NoAccess          |
    Then "<drOwner>" calls "/v1/dataroom/group/create" with no request body and expects error 400, 10005, "Request data is null"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario: Call /v1/dataroom/group/create with empty DR guid and verify error 10006 Data room guid is empty
    Given "apiNegativeTestUser" calls "/v1/dataroom/group/create" with invalid shareGuid "" and expects error 400, 10006, "Data room guid is empty"
      | attribute  | value     |
      | GroupName  | TestGroup |
      | Permission | View      |

  Scenario: Call /v1/dataroom/group/create with expired DR guid and verify error 10018 Data room is expired
    Given "apiNegativeTestUser" calls "/v1/dataroom/group/create" with expired DR shareGuid "8968193f2b734cd3a16d762df53367e4" and expects error 403, 10018, "Data room is expired"
      | attribute  | value     |
      | GroupName  | TestGroup |
      | Permission | View      |

  Scenario: Call /v1/dataroom/group/create with Invalid DR guid and verify error 10007 Data room is not found
    Given "apiNegativeTestUser" calls "/v1/dataroom/group/create" with invalid shareGuid "1f993f1814c" and expects error 400, 10007, "Data room is not found"
      | attribute  | value     |
      | GroupName  | TestGroup |
      | Permission | View      |

  Scenario: Call /v1/dataroom/group/create with Invalid DR guid and verify error 10008 It is not a data room
    Given "apiNegativeTestUser" calls "/v1/dataroom/group/create" with invalid shareGuid "1f993f1814c14fd6a1447e068d94e7ad" and expects error 400, 10008, "It is not a data room"
      | attribute  | value     |
      | GroupName  | TestGroup |
      | Permission | View      |

  Scenario Outline: Call /v1/dataroom/group/create with a blank group name and verify error 10009: Group name is empty
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Any               |
      | DefaultPermission | NoAccess          |
      | AboutPage         | false             |
      | HideBanner        | false             |
      | Watermark         | false             |
    When "<drOwner>" calls "/v1/dataroom/group/create" with a blank group name and expects error 400, 10009, "Group name is empty"
      | attribute  | value |
      | GroupName  |       |
      | Permission | View  |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner         |
      | apiTeamAdminKey |

  Scenario Outline: Call /v1/dataroom/group/create with invalid character group name and verify error 10011:Group name contains invalid character
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | DefaultPermission | Print             |
    When "<drOwner>" calls "/v1/dataroom/group/create" with invalid character group name and expects error 400, 10011, "Group name contains invalid character"
      | attribute  | value          |
      | GroupName  | InvalidGroup<> |
      | Permission | DownloadPDF    |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/group/create with empty group permission and verify error 10012 Group permission is empty
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | DefaultPermission | Print             |
    When "<drOwner>" calls "/v1/dataroom/group/create" with blank group permission and expects error 400, 10012, "Group permission is empty"
      | attribute  | value     |
      | GroupName  | TestGroup |
      | Permission |           |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/group/create with incorrect group permission and verify error 10013 Incorrect permission type
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | DefaultPermission | Print             |
    And "<drOwner>" calls "/v1/dataroom/group/create" with invalid group permission and expects error 400, 10013, "Incorrect permission type"
      | attribute  | value     |
      | GroupName  | TestGroup |
      | Permission | Down      |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/group/create with invalid print count in group and verify error 10014 Print count value must be -1 or between 1 and 9999
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | DefaultPermission | Print             |
    When "<drOwner>" calls "/v1/dataroom/group/create" with invalid print count and expects error 400, 10014, "Print count value must be -1 or between 1 and 9999"
      | attribute  | value     |
      | GroupName  | TestGroup |
      | Permission | Print     |
      | PrintCount | -2        |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/group/create with invalid print count in group and verify error 10014 Print count value must be -1 or between 1 and 9999
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | DefaultPermission | Print             |
    When "<drOwner>" calls "/v1/dataroom/group/create" with invalid print count and expects error 400, 10014, "Print count value must be -1 or between 1 and 9999"
      | attribute  | value     |
      | GroupName  | TestGroup |
      | Permission | Print     |
      | PrintCount | 10000     |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  @ignore #This scenario will not work, as Dev commented out the code few years back and not sure about the reason of doing it.
  Scenario Outline: Call /v1/dataroom/group/create create group and verify error 10015 Group expiry must be greater than today's date
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | DefaultPermission | Print             |
    When "<drOwner>" calls "/v1/dataroom/group/create" with a past expiry date and expects error 400, 10015, "Group expiry must be greater than today's date"
      | attribute   | value      |
      | GroupName   | TestGroup  |
      | Permission  | Print      |
      | PrintCount  | 10000      |
      | GroupExpiry | 2024-12-29 |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/group/create to create group with unauthorized user and verify error 10014 Print count value must be -1 or between 1 and 9999
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | DefaultPermission | Print             |
    When "proPlanWithoutAddonsAPIKey" calls "/v1/dataroom/group/create" to create a group in DR and expects 403, 10016, "User is not authorized to create data room group"
      | attribute  | value     |
      | GroupName  | TestGroup |
      | Permission | Print     |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |