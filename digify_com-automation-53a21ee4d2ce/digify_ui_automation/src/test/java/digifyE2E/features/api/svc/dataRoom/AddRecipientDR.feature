@addRecipientDR @svc @dataRoomSvc @regression

Feature: Validate the /v1/dataroom/addrecipient endpoint for adding a guest in a DR, covering both success and error scenarios.

  Scenario Outline: Call /v1/dataroom/addrecipient to add guest with Print permission in data room
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                   |
      | DataRoomName           | TestDataRoomBySVC-guest |
      | Access                 | Any                     |
      | AdditionalVerification | true                    |
      | DefaultPermission      | View                    |
    Then "<drOwner>" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser" in DR and expects 201, 50000, "Successfully added recipient"
      | attribute  | value |
      | Permission | Print |
      | PrintCount | 10    |
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/addrecipient to add guest with View permission in data room
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                   |
      | DataRoomName           | TestDataRoomBySVC-guest |
      | Access                 | Any                     |
      | AdditionalVerification | true                    |
      | DefaultPermission      | View                    |
    Then "<drOwner>" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser" in DR and expects 201, 50000, "Successfully added recipient"
      | attribute  | value |
      | Permission | View  |
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/addrecipient to add guest with DownloadPDF permission in data room
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                   |
      | DataRoomName           | TestDataRoomBySVC-guest |
      | Access                 | Any                     |
      | AdditionalVerification | true                    |
      | DefaultPermission      | View                    |
    Then "<drOwner>" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser" in DR and expects 201, 50000, "Successfully added recipient"
      | attribute  | value       |
      | Permission | DownloadPDF |
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/addrecipient to add guest with Download Original permission in data room
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                   |
      | DataRoomName           | TestDataRoomBySVC-guest |
      | Access                 | Any                     |
      | AdditionalVerification | true                    |
      | DefaultPermission      | View                    |
    Then "<drOwner>" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser" in DR and expects 201, 50000, "Successfully added recipient"
      | attribute  | value            |
      | Permission | DownloadOriginal |
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/addrecipient to add guest with Contributor permission in data room
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                   |
      | DataRoomName           | TestDataRoomBySVC-guest |
      | Access                 | Any                     |
      | AdditionalVerification | true                    |
      | DefaultPermission      | View                    |
    Then "<drOwner>" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser" in DR and expects 201, 50000, "Successfully added recipient"
      | attribute  | value       |
      | Permission | Contributor |
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario: Call /v1/dataroom/addrecipient to add guest with Co-owner permission in data room
    Given "apiTeamAdminKey" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                   |
      | DataRoomName           | TestDataRoomBySVC-guest |
      | Access                 | Any                     |
      | AdditionalVerification | true                    |
      | DefaultPermission      | View                    |
    Then "apiTeamAdminKey" calls "/v1/dataroom/addrecipient" to add recipient "apiTeamPlanMemberCred" in DR and expects 201, 50000, "Successfully added recipient"
      | attribute  | value   |
      | Permission | Coowner |

  Scenario Outline: Call /v1/dataroom/addrecipient to add guest with expiry in a data room
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                    |
      | DataRoomName           | TestDataRoomBySVC-guest2 |
      | Access                 | Any                      |
      | AdditionalVerification | true                     |
      | DefaultPermission      | View                     |
    Then "<drOwner>" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser" in DR and expects 201, 50000, "Successfully added recipient"
      | attribute  | value                    |
      | Permission | Contributor              |
      | UserExpiry | 2026-12-29T08:01:22.000Z |
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/addrecipient to add guest in a group in a data room
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value                |
      | DataRoomName      | TestDataRoomBySVC-11 |
      | Access            | Restrict             |
      | DefaultPermission | Contributor          |
      | AboutPage         | true                 |
      | HideBanner        | false                |
    When "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute   | value                    |
      | GroupName   | DR-Group                 |
      | Permission  | DownloadPDF              |
      | GroupExpiry | 2026-12-29T08:01:22.000Z |
    Then "<drOwner>" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser" in DR Group and expects 201, 50000, "Successfully added recipient to a group"
      | attribute | value |
    And "<drOwner>" calls "v1/dataroom/group" to validate group in DR, expecting 200, 20000, "Successfully retrieved data room group list"
      | attribute           | value       |
      | GroupName           | DR-Group    |
      | Permission          | NoAccess    |
      | Permission          | DownloadPDF |
      | GroupExpiry         | 2026-12-29  |
      | GroupRecipientCount | 1           |
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario: Call /v1/dataroom/addrecipient to add guest with no data room request form and validate error: 50005 DataRoom request is null
    Given "apiNegativeTestUser" calls "/v1/dataroom/addrecipient" to add recipient in DR with no request form and expects 400, 50005, "DataRoom request is null"

  Scenario Outline: Call /v1/dataroom/addrecipient to add guest with incorrect permission type in DR and validate error: 50007 Recipient's email is empty
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                    |
      | DataRoomName           | TestDataRoomBySVC-guest2 |
      | Access                 | Any                      |
      | AdditionalVerification | true                     |
      | DefaultPermission      | View                     |
    Then "<drOwner>" calls "/v1/dataroom/addrecipient" to add recipient "blankEmail" in DR and expects 400, 50007, "Recipient's email is empty"
      | attribute  | value |
      | Permission | View  |
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/addrecipient to add guest with incorrect permission type in DR and validate error: 50008 The email address (email) is not valid
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                    |
      | DataRoomName           | TestDataRoomBySVC-guest2 |
      | Access                 | Any                      |
      | AdditionalVerification | true                     |
      | DefaultPermission      | View                     |
    Then "<drOwner>" calls "/v1/dataroom/addrecipient" to add recipient "invalidEmailId" in DR and expects 400, 50008, "The email address (jesskusermaildrop.cc) is not valid"
      | attribute  | value |
      | Permission | View  |
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/addrecipient to add guest with owner permission type in DR and validate error: 50009 Data room can have only one owner
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                    |
      | DataRoomName           | TestDataRoomBySVC-guest2 |
      | Access                 | Any                      |
      | AdditionalVerification | true                     |
      | DefaultPermission      | View                     |
    Then "<drOwner>" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser" in DR and expects 400, 50009, "Data room can have only one owner"
      | attribute  | value |
      | Permission | Owner |
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/addrecipient to add guest with incorrect permission type in DR and validate error: 50011 Incorrect permission type
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                    |
      | DataRoomName           | TestDataRoomBySVC-guest2 |
      | Access                 | Any                      |
      | AdditionalVerification | true                     |
      | DefaultPermission      | View                     |
    Then "<drOwner>" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser" in DR and expects 400, 50011, "Incorrect permission type"
      | attribute  | value |
      | Permission | Contr |
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/addrecipient to add guest with invalid print count value in DR (PrintCount<-1)
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                    |
      | DataRoomName           | TestDataRoomBySVC-guest2 |
      | Access                 | Any                      |
      | AdditionalVerification | true                     |
      | DefaultPermission      | View                     |
    Then "<drOwner>" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser" in DR and expects 400, 50012, "Default print count value must be -1 or between 0 and 9999"
      | attribute  | value |
      | Permission | Print |
      | PrintCount | -2    |
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/addrecipient to add guest with invalid print count value in DR (PrintCount>0)
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                    |
      | DataRoomName           | TestDataRoomBySVC-guest2 |
      | Access                 | Any                      |
      | AdditionalVerification | true                     |
      | DefaultPermission      | View                     |
    Then "<drOwner>" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser" in DR and expects 400, 50012, "Default print count value must be -1 or between 0 and 9999"
      | attribute  | value  |
      | Permission | Print  |
      | PrintCount | 100000 |
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario: Call /v1/dataroom/addrecipient to add guest with invalid shareGuid and validate error: 50015 It is not a data room
    Given "apiNegativeTestUser" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser2" in DR with invalid shareGuid "1f993f1814c14fd6a1447e068d94e7ad" and expects 400, 50015, "It is not a data room"
      | attribute  | value   |
      | Permission | Coowner |


  Scenario: Call /v1/dataroom/addrecipient to add guest and validate error: 50016 User is not authorized to add recipient
    Given "apiTeamAdminKey" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute    | value                    |
      | DataRoomName | TestDataRoomBySVC-guest2 |
      | Access       | Any                      |
    Then "apiNegativeTestUser" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser" in DR and expects 400, 50016, "User is not authorized to add recipient"
      | attribute  | value |
      | Permission | View  |

  Scenario: Call /v1/dataroom/addrecipient to add guest with invalid shareGuid and validate error: 50014 Data room is not found
    Given "apiNegativeTestUser" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser2" in DR with invalid shareGuid "1f993f181" and expects 400, 50014, "Data room is not found"
      | attribute  | value   |
      | Permission | Coowner |

  Scenario: Call /v1/dataroom/addrecipient to add guest with null shareGuid and validate error: 50006 Data room guid is empty
    Given "apiNegativeTestUser" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser2" in DR with invalid shareGuid "" and expects 400, 50006, "Data room guid is empty"
      | attribute  | value   |
      | Permission | Coowner |


  Scenario: Call /v1/dataroom/addrecipient to add guest with deleted DR shareGuid and validate error: 50017 Folder access disabled
    Given "apiNegativeTestUser" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser1" in deleted DR, shareGuid "7502f7d08db64bea938f3bb15a9e76d2" and expects 400, 50017, "Folder access disabled"
      | attribute | value |

  Scenario Outline: Call /v1/dataroom/addrecipient to add guest with Coowner permission (who is not a team member) in DR and validate error: 50018 Co-owner permission cannot apply to recipient
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                    |
      | DataRoomName           | TestDataRoomBySVC-guest2 |
      | Access                 | Any                      |
      | AdditionalVerification | true                     |
      | DefaultPermission      | View                     |
    Then "<drOwner>" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser2" in DR and expects 400, 50018, "Co-owner permission cannot apply to recipient"
      | attribute  | value   |
      | Permission | Coowner |
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/addrecipient to add existing guest again in DR and validate error: 50021 This user is already a recipient of the data room
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                    |
      | DataRoomName           | TestDataRoomBySVC-guest2 |
      | Access                 | Any                      |
      | AdditionalVerification | true                     |
      | DefaultPermission      | View                     |
    When "<drOwner>" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser" in DR and expects 201, 50000, "Successfully added recipient"
      | attribute  | value |
      | Permission | View  |
    Then "<drOwner>" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser" in DR and expects 400, 50021, "This user is already a recipient of the data room"
      | attribute  | value |
      | Permission | Print |
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |


  Scenario: Call /v1/dataroom/addrecipient to add guest in expired group and validate error: 50023 Data room is expired
    Given "apiNegativeTestUser" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser" in expired DR "8968193f2b734cd3a16d762df53367e4" and expects 400, 50023, "Data room is expired"
      | attribute    | value                |
      | DataRoomName | TestDataRoomBySVC-11 |

  Scenario Outline: Call /v1/dataroom/addrecipient to add guest with both group and basic permission and validate error: 50025 Group is not found
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value                |
      | DataRoomName      | TestDataRoomBySVC-11 |
      | Access            | Restrict             |
      | DefaultPermission | Contributor          |
    When "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute  | value       |
      | GroupName  | DR-Group    |
      | Permission | DownloadPDF |
    Then "<drOwner>" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser" in DR Group and expects 400, 50024, "User cannot have both basic permission and group permission"
      | attribute  | value       |
      | Permission | DownloadPDF |
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/addrecipient to add guest in non-existing group and validate error: 50025 Group is not found
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value                |
      | DataRoomName      | TestDataRoomBySVC-11 |
      | Access            | Restrict             |
      | DefaultPermission | Contributor          |
      | AboutPage         | true                 |
      | HideBanner        | false                |
    When "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute  | value       |
      | GroupName  | DR-Group    |
      | Permission | DownloadPDF |
    Then "<drOwner>" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser" in DR Group with invalid group guid "488888" and expects 400, 50025, "Group is not found"
      | attribute | value |
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario: Call /v1/dataroom/addrecipient to add guest in expired group and validate error: 50026 Group has expired
    Given "apiNegativeTestUser" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser" in expired group "ae9c6181fdaf4e68982cc97746fca77a" and DR guid "dc8a8a7326164092834fa7269f340cc7" and expects 400, 50026, "Group has expired"
      | attribute    | value                |
      | DataRoomName | TestDataRoomBySVC-11 |