@updateDataRoomSettings @svc @dataRoomSvc @regression

Feature: Validate /v1/dataroom/settings/update endpoint for updating DR settings, covering both success and error scenarios.

  Scenario Outline: Call /v1/dataroom/create to create a DR & update DR settings with access-Restrict, Watermark-Off Screen_shield-Individual, TOA-Off, AboutPage-On, HideBanner-On and Download permission.
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value         |
      | DataRoomName           | DataRoomBySVC |
      | Access                 | Any           |
      | AdditionalVerification | true          |
      | DefaultPermission      | NoAccess      |
      | Screen_shield          | Individual    |
      | TermsOfAccess          | false         |
      | Watermark              | false         |
      | AboutPage              | true          |
      | HideBanner             | true          |
    When "<drOwner>" calls "v1/dataroom/settings/update" to update dataroom settings and expects 200, 17200, "Successfully updated data room settings"
      | attribute              | value       |
      | DataRoomName           | UpdatedDR   |
      | Access                 | Restrict    |
      | AdditionalVerification | true        |
      | DefaultPermission      | DownloadPDF |
      | Screen_shield          | Individual  |
      | TermsOfAccess          | false       |
      | Watermark              | false       |
      | AboutPage              | true        |
      | HideBanner             | true        |
    Then "<drOwner>" calls dataroom "v1/dataroom/settings" endpoint, expecting 200, 17100, "Successfully retrieved data room settings"
      | attribute              | value       |
      | DataRoomName           | UpdatedDR   |
      | Access                 | Restrict    |
      | AdditionalVerification | true        |
      | DefaultPermission      | DownloadPDF |
      | Screen_shield          | Individual  |
      | TermsOfAccess          | false       |
      | Watermark              | false       |
      | AboutPage              | true        |
      | HideBanner             | true        |
    And "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to create a data with Access- Any, AboutPage-Off,HideBanner-Off,ScreenShield-On, Updated expiry, DW-All Settings and Print permission.
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value                     |
      | DataRoomName      | TestDataRoomBySVC         |
      | Access            | Restrict                  |
      | DefaultPermission | DownloadOriginal          |
      | AboutPage         | true                      |
      | HideBanner        | true                      |
      | DataRoomExpiry    | 2026-12-15T00:00:00+08:00 |
      | Screen_shield     | On                        |
    When "<drOwner>" calls "v1/dataroom/settings/update" to update dataroom settings and expects 200, 17200, "Successfully updated data room settings"
      | attribute            | value                     |
      | DataRoomName         | UpdatedDR-001             |
      | Access               | Any                       |
      | DefaultPermission    | Print                     |
      | DefaultPrintCount    | 10                        |
      | HideBanner           | false                     |
      | AboutPage            | false                     |
      | DataRoomExpiry       | 2026-12-29T00:00:00+08:00 |
      | Screen_shield        | Off                       |
      | Watermark            | true                      |
      | Watermark_text       | Test Data                 |
      | Watermark_text_line2 | DO NOT MAKE COPY          |
      | Watermark_opacity    | 0.4                       |
      | Watermark_color      | Blue                      |
      | Watermark_size       | Footer                    |
    Then "<drOwner>" calls dataroom "v1/dataroom/settings" endpoint, expecting 200, 17100, "Successfully retrieved data room settings"
      | attribute            | value                     |
      | DataRoomName         | UpdatedDR-001             |
      | Access               | Any                       |
      | DefaultPermission    | Print                     |
      | DefaultPrintCount    | 10                        |
      | HideBanner           | false                     |
      | AboutPage            | false                     |
      | DataRoomExpiry       | 2026-12-29T00:00:00+08:00 |
      | Screen_shield        | Off                       |
      | Watermark            | true                      |
      | Watermark_text       | Test Data                 |
      | Watermark_text_line2 | DO NOT MAKE COPY          |
      | Watermark_opacity    | 0.4                       |
      | Watermark_color      | Blue                      |
      | Watermark_size       | Footer                    |
    And "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to update DR settings as- Access-Domain,Expiry-Off, DW-Update Settings and update PrintCount in permission.
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute            | value                     |
      | DataRoomName         | TestDataRoomBySVC         |
      | Access               | Any                       |
      | DefaultPermission    | Print                     |
      | PrintCount           | 10                        |
      | DataRoomExpiry       | 2026-12-15T00:00:00+08:00 |
      | Watermark            | true                      |
      | Watermark_text       | Test Data                 |
      | Watermark_text_line2 | DO NOT MAKE COPY          |
      | Watermark_opacity    | 0.3                       |
      | Watermark_color      | Red                       |
      | Watermark_size       | Center                    |
    When "<drOwner>" calls "v1/dataroom/settings/update" to update dataroom settings and expects 200, 17200, "Successfully updated data room settings"
      | attribute            | value               |
      | DataRoomName         | UpdatedDR-002       |
      | Access               | Any                 |
      | DefaultPermission    | Print               |
      | DefaultPrintCount    | 30                  |
      | DataRoomExpiry       | 0001-01-01T00:00:00 |
      | Watermark            | true                |
      | Watermark_text       | Testing Data        |
      | Watermark_text_line2 | DO NOT MAKE CHANGES |
      | Watermark_opacity    | 0.5                 |
      | Watermark_color      | Grey                |
      | Watermark_size       | Tile                |
    Then "<drOwner>" calls dataroom "v1/dataroom/settings" endpoint, expecting 200, 17100, "Successfully retrieved data room settings"
      | attribute            | value               |
      | DataRoomName         | UpdatedDR-002       |
      | Access               | Any                 |
      | DefaultPermission    | Print               |
      | DefaultPrintCount    | 30                  |
      | DataRoomExpiry       | 0001-01-01T00:00:00 |
      | Watermark            | true                |
      | Watermark_text       | Testing Data        |
      | Watermark_text_line2 | DO NOT MAKE CHANGES |
      | Watermark_opacity    | 0.5                 |
      | Watermark_color      | Grey                |
      | Watermark_size       | Tile                |
    And "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to update DR settings as- Access- Restrict, Watermark-Off,FileIndex-Off, Notification-On, GuestList-Group and Contributor permission.
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Domain            |
      | Domain            | @gmail.com        |
      | DefaultPermission | NoAccess          |
      | DefaultPrintCount | 10                |
      | FileIndex         | true              |
      | Notification      | Ask               |
      | GuestList         | All               |
      | Watermark         | true              |
    When "<drOwner>" calls "v1/dataroom/settings/update" to update dataroom settings and expects 200, 17200, "Successfully updated data room settings"
      | attribute         | value         |
      | DataRoomName      | UpdatedDR-003 |
      | Access            | Restrict      |
      | DefaultPermission | Contributor   |
      | FileIndex         | false         |
      | Notification      | On            |
      | GuestList         | Group         |
      | Watermark         | false         |
    Then "<drOwner>" calls dataroom "v1/dataroom/settings" endpoint, expecting 200, 17100, "Successfully retrieved data room settings"
      | attribute         | value         |
      | DataRoomName      | UpdatedDR-003 |
      | Access            | Restrict      |
      | DefaultPermission | Contributor   |
      | FileIndex         | false         |
      | Notification      | On            |
      | GuestList         | Group         |
      | Watermark         | false         |
    And "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to update DR settings as- Access- Any, Restrict-On, ScreenShield-Off,FileIndex-On, Notification-Ask, GuestList-All and NoAccess permission.
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value             |
      | DataRoomName           | TestDataRoomBySVC |
      | Access                 | Restrict          |
      | DefaultPermission      | Print             |
      | DefaultPrintCount      | 10                |
      | FileIndex              | true              |
      | Notification           | On                |
      | GuestList              | All               |
      | Watermark              | true              |
      | AdditionalVerification | false             |
      | Screen_shield          | On                |
    When "<drOwner>" calls "v1/dataroom/settings/update" to update dataroom settings and expects 200, 17200, "Successfully updated data room settings"
      | attribute              | value         |
      | DataRoomName           | UpdatedDR-003 |
      | Access                 | Any           |
      | DefaultPermission      | NoAccess      |
      | FileIndex              | false         |
      | Notification           | Ask           |
      | GuestList              | Group         |
      | Watermark              | false         |
      | AdditionalVerification | true          |
      | Screen_shield          | Off           |
    Then "<drOwner>" calls dataroom "v1/dataroom/settings" endpoint, expecting 200, 17100, "Successfully retrieved data room settings"
      | attribute              | value         |
      | DataRoomName           | UpdatedDR-003 |
      | Access                 | Any           |
      | DefaultPermission      | NoAccess      |
      | FileIndex              | false         |
      | Notification           | Ask           |
      | GuestList              | Group         |
      | Watermark              | false         |
      | AdditionalVerification | true          |
      | Screen_shield          | Off           |
    And "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |


  Scenario Outline: Call /v1/dataroom/create to create a DR & update DR settings with default group permission,access-Restrict, Screen_shield-On, TOA-On, Expiry-Off.
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value         |
      | DataRoomName           | DataRoomBySVC |
      | Access                 | Restrict      |
      | AdditionalVerification | true          |
      | DefaultPermission      | View          |
      | Screen_shield          | Individual    |
      | TermsOfAccess          | true          |
      | DataRoomExpiry         | Off           |
    When "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute  | value            |
      | GroupName  | TestSvcGroup     |
      | Permission | DownloadOriginal |
    Then "<drOwner>" calls "v1/dataroom/settings/update" to update dataroom settings by adding group as default permission and expects 200, 17200, "Successfully updated data room settings"
      | attribute              | value      |
      | DataRoomName           | UpdatedDR  |
      | Access                 | Restrict   |
      | AdditionalVerification | true       |
      | Screen_shield          | Individual |
      | TermsOfAccess          | true       |
      | DataRoomExpiry         | Off        |
    And "<drOwner>" calls dataroom "v1/dataroom/settings" endpoint, expecting 200, 17100, "Successfully retrieved data room settings"
      | attribute              | value               |
      | DataRoomName           | UpdatedDR           |
      | Access                 | Restrict            |
      | AdditionalVerification | true                |
      | Screen_shield          | Individual          |
      | TermsOfAccess          | true                |
      | DataRoomExpiry         | 0001-01-01T00:00:00 |
      | DefaultPermission      | DownloadOriginal    |
    And "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to edit DataRoom without request and validate error: 17205 Request body is null
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | DefaultPermission | Print             |
    When "<drOwner>" calls "v1/dataroom/settings/update" to edit data room without request body and expects 400, 17205, "Request body is null"
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to update DR settings without DR name and validate error: 17215 Data room name is empty
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | DefaultPermission | Print             |
    When "<drOwner>" calls "v1/dataroom/settings/update" to update dataroom settings without DR name and expects 400, 17215, "Data room name is empty"
      | attribute         | value    |
      | DataRoomName      |          |
      | Access            | Any      |
      | DefaultPermission | NoAccess |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to update DR settings with invalid DataRoom name and validate error: 17216 Data room name is invalid
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | DefaultPermission | View              |
    When "<drOwner>" calls "v1/dataroom/settings/update" to update dataroom settings without invalid DR name and expects 400, 17216, "Data room name is invalid"
      | attribute         | value           |
      | DataRoomName      | InvalidDRName<> |
      | Access            | Any             |
      | DefaultPermission | NoAccess        |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario: Call /v1/dataroom/create to enable Watermark without the add-on, and validate error: 17217 Watermark is a premium feature, upgrade plan to access the feature
    Given "proPlanWithoutAddonsAPIKey" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute    | value             |
      | DataRoomName | DR with watermark |
      | Access       | Restrict          |
    When "proPlanWithoutAddonsAPIKey" calls "v1/dataroom/settings/update" to enable Watermark in DR settings without the add-on expects 400, 17217, "Watermark is a premium feature, upgrade plan to access the feature"
      | attribute         | value             |
      | DataRoomName      | DR with watermark |
      | Access            | Any               |
      | DefaultPermission | NoAccess          |
      | Watermark         | true              |
    Then "proPlanWithoutAddonsAPIKey" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"

  Scenario: Call /v1/dataroom/create to create a data for user without ScreenShield addon and validate error: 17218 Screenshield is a premium feature, upgrade plan to access the feature
    Given "proPlanWithoutAddonsAPIKey" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute    | value                |
      | DataRoomName | DR with screenShield |
      | Access       | Restrict             |
    When "proPlanWithoutAddonsAPIKey" calls "v1/dataroom/settings/update" to enable ScreenShield in DR settings without the add-on expects 400, 17218, "Screenshield is a premium feature, upgrade plan to access the feature"
      | attribute     | value                |
      | DataRoomName  | DR with screenShield |
      | Access        | Any                  |
      | Screen_shield | On                   |
    Then "proPlanWithoutAddonsAPIKey" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"

  Scenario Outline: Call /v1/dataroom/create to validate error: 10011 User is view only user
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 400, 10011, "User is view only user"
      | attribute    | value |
      | DataRoomName | SvcDR |
      | Access       | Any   |
    Examples:
      | drOwner                |
      | enterpriseViewerAPIKey |


  Scenario Outline: Call /v1/dataroom/create to edit DataRoom with invalid Access type and validate error: 17221 Incorrect access type
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute    | value                 |
      | DataRoomName | IncorrectAccessTypeDR |
      | Access       | Any                   |
    When "<drOwner>" calls "v1/dataroom/settings/update" to edit DR settings with invalid Access type and expects 400, 17221, "Incorrect access type"
      | attribute    | value                  |
      | DataRoomName | DR with invalid access |
      | Access       | Test                   |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to edit DataRoom with empty domain and validate error: 17222 Domain is empty
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute    | value      |
      | DataRoomName | Test DR    |
      | Access       | Print      |
      | Access       | Domain     |
      | Domain       | @gmail.com |
    When "<drOwner>" calls "v1/dataroom/settings/update" to edit DR with empty domain and expects 400, 17222, "Domain is empty"
      | attribute    | value   |
      | DataRoomName | Test DR |
      | Access       | Domain  |
      | Domain       |         |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to create a DataRoom with incorrect permission and validate error: 17224 Incorrect default permission type
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value   |
      | DataRoomName      | Test DR |
      | DefaultPermission | View    |
      | Access            | Any     |
    When "<drOwner>" calls "v1/dataroom/settings/update" to edit DR with invalid permission type and expects 400, 17224, "Incorrect default permission type"
      | attribute         | value   |
      | DataRoomName      | Test DR |
      | DefaultPermission | Test<>  |
      | Access            | Any     |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to edit DR with incorrect print count and validate error: 17225 Default print count value must be -1 or between 0 and 9999(count<-1)
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value              |
      | DataRoomName      | Test DR-With Print |
      | DefaultPermission | View               |
      | Access            | Any                |
    When "<drOwner>" calls "v1/dataroom/settings/update" to edit DR with invalid print count and expects 400, 17225, "Default print count value must be -1 or between 0 and 9999"
      | attribute         | value   |
      | DataRoomName      | Test DR |
      | DefaultPermission | Print   |
      | DefaultPrintCount | -20     |
      | Access            | Any     |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to create a DataRoom with incorrect print count and validate error: 10016 Default print count value must be -1 or between 0 and 9999(count>9999)
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value               |
      | DataRoomName      | InvalidPrintCountDR |
      | Access            | Restrict            |
      | DefaultPermission | View                |
    When "<drOwner>" calls "v1/dataroom/settings/update" to edit DR with invalid print count and expects 400, 17225, "Default print count value must be -1 or between 0 and 9999"
      | attribute         | value    |
      | DataRoomName      | Test DR  |
      | DefaultPermission | Print    |
      | DefaultPrintCount | 100001   |
      | Access            | Restrict |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |


  Scenario Outline: Call /v1/dataroom/create to edit DR with invalid watermark opacity and validate error: 17226 Watermark opacity must be between 0.1 and 0.5 (opacity<0.1)
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | Watermark         | true              |
      | Watermark_opacity | 0.2               |
    When "<drOwner>" calls "v1/dataroom/settings/update" to edit DR with invalid watermark opacity and expects 400, 17226, "Watermark opacity must be between 0.1 and 0.5"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | Watermark         | true              |
      | Watermark_opacity | 0.0               |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to edit DR with invalid watermark opacity and validate error: 17226 Watermark opacity must be between 0.1 and 0.5 (opacity>0.1)
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | Watermark         | true              |
      | Watermark_opacity | 0.2               |
    When "<drOwner>" calls "v1/dataroom/settings/update" to edit DR with invalid watermark opacity and expects 400, 17226, "Watermark opacity must be between 0.1 and 0.5"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | Watermark         | true              |
      | Watermark_opacity | 0.8               |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to edit DR with invalid DR ShareGuid and validate error: 17207 Data room is not found
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | Watermark         | true              |
      | Watermark_opacity | 0.2               |
    When "<drOwner>" calls "v1/dataroom/settings/update" to edit DR with invalid DR ShareGuid "3667474" and expects 400, 17207, "Data room is not found"
      | attribute    | value             |
      | DataRoomName | TestDataRoomBySVC |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to edit DR with null DR ShareGuid and validate error: 17206 Data room guid is empty
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | Watermark         | true              |
      | Watermark_opacity | 0.2               |
    When "<drOwner>" calls "v1/dataroom/settings/update" to edit DR with null DR ShareGuid "" and expects 400, 17206, "Data room guid is empty"
      | attribute    | value             |
      | DataRoomName | TestDataRoomBySVC |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to edit DR with DR file ShareGuid and validate error: 17208 It is not a data room
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | Watermark         | true              |
      | Watermark_opacity | 0.2               |
    When "<drOwner>" calls "v1/dataroom/settings/update" to edit DR with DR file ShareGuid "0389e5293d3442568f6f01b8fc5af332" and expects 400, 17208, "It is not a data room"
      | attribute    | value             |
      | DataRoomName | TestDataRoomBySVC |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |