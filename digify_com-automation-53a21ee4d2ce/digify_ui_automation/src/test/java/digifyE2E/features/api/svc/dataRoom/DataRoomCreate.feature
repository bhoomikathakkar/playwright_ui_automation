@dataRoomCreate @svc @dataRoomSvc @regression

Feature: Validate /v1/dataroom/create endpoint to create a data room, covering both success and error scenarios.

  Scenario Outline: Call /v1/dataroom/create to create a data with Restrict access, all features enabled and DownloadPDF permission.
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                     |
      | DataRoomName           | TestDataRoomBySVC         |
      | Access                 | Restrict                  |
      | DefaultPermission      | DownloadPDF               |
      | Screen_shield          | On                        |
      | TermsOfAccess          | true                      |
      | AboutPage              | true                      |
      | AdditionalVerification | true                      |
      | HideBanner             | false                     |
      | DataRoomExpiry         | 2026-12-15T00:00:00+08:00 |
      | FileIndex              | true                      |
      | Notification           | Ask                       |
      | GuestList              | All                       |
      | Watermark              | true                      |
      | Watermark_text         | Test Data                 |
      | Watermark_text_line2   | DO NOT MAKE COPY          |
      | Watermark_opacity      | 0.4                       |
      | Watermark_color        | Blue                      |
      | Watermark_size         | Footer                    |
      | FileStorageBinding     | Digify/S3/EU              |
    When "<drOwner>" calls dataroom "v1/dataroom/settings" endpoint, expecting 200, 17100, "Successfully retrieved data room settings"
      | attribute              | value                     |
      | DataRoomName           | TestDataRoomBySVC         |
      | Access                 | Restrict                  |
      | DefaultPermission      | DownloadPDF               |
      | Screen_shield          | On                        |
      | TermsOfAccess          | true                      |
      | AboutPage              | true                      |
      | AdditionalVerification | true                      |
      | HideBanner             | false                     |
      | DataRoomExpiry         | 2026-12-15T00:00:00+08:00 |
      | FileIndex              | true                      |
      | Notification           | Ask                       |
      | GuestList              | All                       |
      | Watermark              | true                      |
      | Watermark_text         | Test Data                 |
      | Watermark_text_line2   | DO NOT MAKE COPY          |
      | Watermark_opacity      | 0.4                       |
      | Watermark_color        | Blue                      |
      | Watermark_size         | Footer                    |
      | FileStorageBinding     | Digify/S3/SG              |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to create a data with Any access, all features enabled and Contributor permission.
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute                   | value                     |
      | DataRoomName                | TestDataRoomBySVC         |
      | Access                      | Any                       |
      | DefaultPermission           | Contributor               |
      | Screen_shield               | On                        |
      | TermsOfAccess               | true                      |
      | AboutPage                   | true                      |
      | AdditionalVerification      | true                      |
      | HideBanner                  | false                     |
      | DataRoomExpiry              | 2026-12-15T08:09:00+08:00 |
      | FileIndex                   | true                      |
      | Notification                | Ask                       |
      | GuestList                   | All                       |
      | Watermark                   | true                      |
      | Watermark_text              | Test Data                 |
      | Watermark_text_line2        | DO NOT MAKE COPY          |
      | Watermark_opacity           | 0.5                       |
      | Watermark_color             | Grey                      |
      | Watermark_size              | Center                    |
      | FileStorageBinding          | Digify/S3/EU              |
      | DisableSpreadsheetWatermark | true                      |
    When "<drOwner>" calls dataroom "v1/dataroom/settings" endpoint, expecting 200, 17100, "Successfully retrieved data room settings"
      | attribute                   | value                     |
      | DataRoomName                | TestDataRoomBySVC         |
      | Access                      | Any                       |
      | DefaultPermission           | Contributor               |
      | Screen_shield               | On                        |
      | TermsOfAccess               | true                      |
      | AboutPage                   | true                      |
      | AdditionalVerification      | true                      |
      | HideBanner                  | false                     |
      | DataRoomExpiry              | 2026-12-15T08:09:00+08:00 |
      | FileIndex                   | true                      |
      | Notification                | Ask                       |
      | GuestList                   | All                       |
      | Watermark                   | true                      |
      | Watermark_text              | Test Data                 |
      | Watermark_text_line2        | DO NOT MAKE COPY          |
      | Watermark_opacity           | 0.5                       |
      | Watermark_color             | Grey                      |
      | Watermark_size              | Center                    |
      | FileStorageBinding          | Digify/S3/SG              |
      | DisableSpreadsheetWatermark | true                      |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to create a data with Domain access, all features enabled and Print permission.
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute                   | value                     |
      | DataRoomName                | TestDataRoomBySVC         |
      | Access                      | Domain                    |
      | Domain                      | @vomoto.com               |
      | DefaultPermission           | Print                     |
      | DefaultPrintCount           | 5                         |
      | Screen_shield               | On                        |
      | TermsOfAccess               | true                      |
      | AboutPage                   | true                      |
      | AdditionalVerification      | true                      |
      | HideBanner                  | false                     |
      | DataRoomExpiry              | 2026-12-15T08:09:00+08:00 |
      | FileIndex                   | true                      |
      | Notification                | Ask                       |
      | GuestList                   | All                       |
      | Watermark                   | true                      |
      | Watermark_text              | Test Data                 |
      | Watermark_text_line2        | DO NOT MAKE COPY          |
      | Watermark_opacity           | 0.5                       |
      | Watermark_color             | Grey                      |
      | Watermark_size              | Center                    |
      | FileStorageBinding          | Digify/S3/EU              |
      | DisableSpreadsheetWatermark | true                      |
    When "<drOwner>" calls dataroom "v1/dataroom/settings" endpoint, expecting 200, 17100, "Successfully retrieved data room settings"
      | attribute                   | value                     |
      | DataRoomName                | TestDataRoomBySVC         |
      | Access                      | Domain                    |
      | Domain                      | @vomoto.com               |
      | DefaultPermission           | Print                     |
      | DefaultPrintCount           | 5                         |
      | Screen_shield               | On                        |
      | TermsOfAccess               | true                      |
      | AboutPage                   | true                      |
      | AdditionalVerification      | true                      |
      | HideBanner                  | false                     |
      | DataRoomExpiry              | 2026-12-15T08:09:00+08:00 |
      | FileIndex                   | true                      |
      | Notification                | Ask                       |
      | GuestList                   | All                       |
      | Watermark                   | true                      |
      | Watermark_text              | Test Data                 |
      | Watermark_text_line2        | DO NOT MAKE COPY          |
      | Watermark_opacity           | 0.5                       |
      | Watermark_color             | Grey                      |
      | Watermark_size              | Center                    |
      | FileStorageBinding          | Digify/S3/SG              |
      | DisableSpreadsheetWatermark | true                      |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to create a data with Restrict access, AboutPage-Off, Screen_shield-Off, TOA-On and DownloadPDF permission.
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | DefaultPermission | DownloadPDF       |
      | Screen_shield     | Off               |
      | TermsOfAccess     | true              |
      | AboutPage         | false             |
    When "<drOwner>" calls dataroom "v1/dataroom/settings" endpoint, expecting 200, 17100, "Successfully retrieved data room settings"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | DefaultPermission | DownloadPDF       |
      | Screen_shield     | Off               |
      | TermsOfAccess     | true              |
      | AboutPage         | false             |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to create a data with Restrict access, Expiry-On, AboutPage-On,HideBanner-On,ScreenShield-On and DownloadOriginal permission.
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value                     |
      | DataRoomName      | TestDataRoomBySVC         |
      | Access            | Restrict                  |
      | DefaultPermission | DownloadOriginal          |
      | AboutPage         | true                      |
      | HideBanner        | true                      |
      | DataRoomExpiry    | 2026-12-15T00:00:00+08:00 |
      | Screen_shield     | On                        |
    When "<drOwner>" calls dataroom "v1/dataroom/settings" endpoint, expecting 200, 17100, "Successfully retrieved data room settings"
      | attribute         | value                     |
      | DataRoomName      | TestDataRoomBySVC         |
      | Access            | Restrict                  |
      | DefaultPermission | DownloadOriginal          |
      | HideBanner        | true                      |
      | AboutPage         | true                      |
      | DataRoomExpiry    | 2026-12-15T00:00:00+08:00 |
      | Screen_shield     | On                        |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to create a DR with Restrict access, Watermark-On,FileIndex-On, Notification-Ask, GuestList-All and Print permission.
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | DefaultPermission | Print             |
      | DefaultPrintCount | 10                |
      | FileIndex         | true              |
      | Notification      | Ask               |
      | GuestList         | All               |
      | Watermark         | true              |
    When "<drOwner>" calls dataroom "v1/dataroom/settings" endpoint, expecting 200, 17100, "Successfully retrieved data room settings"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | DefaultPermission | Print             |
      | DefaultPrintCount | 10                |
      | FileIndex         | true              |
      | Notification      | Ask               |
      | GuestList         | All               |
      | Watermark         | true              |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to create a DR with Restrict access,Watermark-with all settings, No FileIndex, Notification-Off, GuestList-Hide and Contributor permission.
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute            | value             |
      | DataRoomName         | TestDataRoomBySVC |
      | Access               | Restrict          |
      | DefaultPermission    | Contributor       |
      | Watermark            | true              |
      | Watermark_text       | Test Data         |
      | Watermark_text_line2 | DO NOT MAKE COPY  |
      | Watermark_opacity    | 0.4               |
      | Watermark_color      | Blue              |
      | Watermark_size       | Footer            |
      | FileIndex            | false             |
      | Notification         | Off               |
      | GuestList            | Hide              |
    When "<drOwner>" calls dataroom "v1/dataroom/settings" endpoint, expecting 200, 17100, "Successfully retrieved data room settings"
      | attribute            | value             |
      | DataRoomName         | TestDataRoomBySVC |
      | Access               | Restrict          |
      | DefaultPermission    | Contributor       |
      | Watermark            | true              |
      | Watermark_text       | Test Data         |
      | Watermark_text_line2 | DO NOT MAKE COPY  |
      | Watermark_opacity    | 0.4               |
      | Watermark_color      | Blue              |
      | Watermark_size       | Footer            |
      | FileIndex            | false             |
      | Notification         | Off               |
      | GuestList            | Hide              |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to create a DR with Restrict, HideBanner-Off,Watermark-with all settings, Notification-On, GuestList-Group and NoAccess permission.
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute            | value             |
      | DataRoomName         | TestDataRoomBySVC |
      | Access               | Restrict          |
      | DefaultPermission    | NoAccess          |
      | Watermark            | true              |
      | Watermark_text       | Test Data Only    |
      | Watermark_text_line2 | DO NOT MAKE COPY  |
      | Watermark_opacity    | 0.1               |
      | Watermark_color      | Red               |
      | Watermark_size       | Center            |
      | Notification         | On                |
      | GuestList            | Group             |
      | HideBanner           | true              |
    When "<drOwner>" calls dataroom "v1/dataroom/settings" endpoint, expecting 200, 17100, "Successfully retrieved data room settings"
      | attribute            | value             |
      | DataRoomName         | TestDataRoomBySVC |
      | Access               | Restrict          |
      | DefaultPermission    | NoAccess          |
      | Watermark            | true              |
      | Watermark_text       | Test Data Only    |
      | Watermark_text_line2 | DO NOT MAKE COPY  |
      | Watermark_opacity    | 0.1               |
      | Watermark_color      | Red               |
      | Watermark_size       | Center            |
      | Notification         | On                |
      | GuestList            | Group             |
      | HideBanner           | true              |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to create a data with Domain access, Watermark-On, Screen_shield-On, TOA-On and View permission.
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute         | value                      |
      | DataRoomName      | TestDataRoomBySVC-Domain43 |
      | Access            | Domain                     |
      | Domain            | @vomoto.com,@gmail.com     |
      | DefaultPermission | View                       |
      | Screen_shield     | On                         |
      | TermsOfAccess     | true                       |
      | Watermark         | true                       |
      | Watermark_text    | FOR TESTING ONLY           |
      | Watermark_opacity | 0.5                        |
      | Watermark_color   | Grey                       |
      | Watermark_size    | Tile                       |
      | AboutPage         | false                      |
    When "<drOwner>" calls dataroom "v1/dataroom/settings" endpoint, expecting 200, 17100, "Successfully retrieved data room settings"
      | attribute         | value                      |
      | DataRoomName      | TestDataRoomBySVC-Domain43 |
      | Access            | Domain                     |
      | Domain            | @vomoto.com,@gmail.com     |
      | DefaultPermission | View                       |
      | Screen_shield     | On                         |
      | TermsOfAccess     | true                       |
      | Watermark         | true                       |
      | Watermark_text    | FOR TESTING ONLY           |
      | Watermark_opacity | 0.5                        |
      | Watermark_color   | Grey                       |
      | Watermark_size    | Tile                       |
      | AboutPage         | false                      |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call /v1/dataroom/create to create a data with Any access,Watermark-Off Screen_shield-Individual, TOA-Off, AboutPage-On, HideBanner-On and Download permission.
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                 |
      | DataRoomName           | TestDataRoomBySVC-Any |
      | Access                 | Any                   |
      | AdditionalVerification | true                  |
      | DefaultPermission      | NoAccess              |
      | Screen_shield          | Individual            |
      | TermsOfAccess          | false                 |
      | Watermark              | false                 |
      | AboutPage              | true                  |
      | HideBanner             | true                  |
    When "<drOwner>" calls dataroom "v1/dataroom/settings" endpoint, expecting 200, 17100, "Successfully retrieved data room settings"
      | attribute              | value                 |
      | DataRoomName           | TestDataRoomBySVC-Any |
      | Access                 | Any                   |
      | DefaultPermission      | NoAccess              |
      | Screen_shield          | Individual            |
      | TermsOfAccess          | false                 |
      | Watermark              | false                 |
      | AboutPage              | true                  |
      | HideBanner             | true                  |
      | AdditionalVerification | true                  |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario: Call /v1/dataroom/create to create a DataRoom without request and validate error: 10005 DataRoom request is null
    Given "apiNegativeTestUser" calls "v1/dataroom/create" to create a data room without request body and expects 400, 10005, "DataRoom request is null"
      | attribute | value |

  Scenario: Call /v1/dataroom/create to create a DataRoom without DataRoom name and validate error: 10006 Data room name is empty
    Given "apiNegativeTestUser" calls "v1/dataroom/create" to create a DR without data room name and expects 400, 10006, "Data room name is empty"
      | attribute    | value |
      | DataRoomName |       |
      | Access       | Any   |

  Scenario: Call /v1/dataroom/create to create a DataRoom with invalid DataRoom name and validate error: 10007 Data room name contains invalid character
    Given "apiNegativeTestUser" calls "v1/dataroom/create" to create a DR with invalid data room and expects 400, 10007, "Data room name contains invalid character"
      | attribute    | value               |
      | DataRoomName | TestInvalidDRName<> |
      | Access       | Any                 |


  Scenario: Call /v1/dataroom/create to validate error: 10011 User is view only user
    Given "enterpriseViewerAPIKey" calls "v1/dataroom/create" to create a data room and expects 400, 10011, "User is view only user"
      | attribute    | value |
      | DataRoomName | SvcDR |
      | Access       | Any   |

  Scenario: Call /v1/dataroom/create to create a DataRoom with invalid Access type and validate error: 10013 Incorrect access type
    Given "apiNegativeTestUser" calls "v1/dataroom/create" to create a DR with invalid access type and expects 400, 10013, "Incorrect access type"
      | attribute    | value                 |
      | DataRoomName | IncorrectAccessTypeDR |
      | Access       | Test                  |

  Scenario: Call /v1/dataroom/create to create a DataRoom with incorrect permission and validate error: 10015 Incorrect default permission type
    Given "apiNegativeTestUser" calls "v1/dataroom/create" to create a DR with invalid permission type and expects 400, 10015, "Incorrect default permission type"
      | attribute         | value                     |
      | DataRoomName      | IncorrectPermissionTypeDR |
      | Access            | Any                       |
      | DefaultPermission | Down                      |

  Scenario: Call /v1/dataroom/create to create a DataRoom with incorrect print count and validate error: 10016 Default print count value must be -1 or between 0 and 9999(count<-1)
    Given "apiNegativeTestUser" calls "v1/dataroom/create" to create a DR with invalid print count and expects 400, 10016, "Default print count value must be -1 or between 0 and 9999"
      | attribute         | value               |
      | DataRoomName      | InvalidPrintCountDR |
      | Access            | Restrict            |
      | DefaultPermission | Print               |
      | DefaultPrintCount | -100                |

  Scenario: Call /v1/dataroom/create to create a DataRoom with incorrect print count and validate error: 10016 Default print count value must be -1 or between 0 and 9999(count>9999)
    Given "apiNegativeTestUser" calls "v1/dataroom/create" to create a DR with invalid print count and expects 400, 10016, "Default print count value must be -1 or between 0 and 9999"
      | attribute         | value               |
      | DataRoomName      | InvalidPrintCountDR |
      | Access            | Restrict            |
      | DefaultPermission | Print               |
      | DefaultPrintCount | 10001               |

  Scenario: Call /v1/dataroom/create to validate error: 10018 Error in creating data room
    Given "apiNegativeTestUser" calls "v1/dataroom/create" to create a data room and expects 400, 10018, "Error in creating data room"
      | attribute    | value   |
      | DataRoomName | ErrorDR |
      | Access       | Domain  |
      | Domain       |         |

  Scenario: Call /v1/dataroom/create to create a data for user without Watermark addon and validate error:10019 Watermark is a premium feature, upgrade plan to access the feature
    Given "proPlanWithoutAddonsAPIKey" calls "v1/dataroom/create" to create a DR without watermark addon and expects 400, 10019, "Watermark is a premium feature, upgrade plan to access the feature"
      | attribute    | value             |
      | DataRoomName | TestDataRoomBySVC |
      | Access       | Restrict          |
      | Watermark    | true              |

  Scenario: Call /v1/dataroom/create to create a data for user without ScreenShield addon and validate error:10021 Screenshield is a premium feature, upgrade plan to access the feature
    Given "proPlanWithoutAddonsAPIKey" calls "v1/dataroom/create" to create a DR without ScreenShield addon and expects 400, 10021, "Screenshield is a premium feature, upgrade plan to access the feature"
      | attribute     | value             |
      | DataRoomName  | TestDataRoomBySVC |
      | Access        | Restrict          |
      | Screen_shield | On                |

  Scenario: Call /v1/dataroom/create to create a data and validate error: 10024 Watermark opacity must be between 0.1 and 0.5 (opacity<0.1)
    Given "apiNegativeTestUser" calls "v1/dataroom/create" to create a DR with invalid watermark opacity and expects 400, 10024, "Watermark opacity must be between 0.1 and 0.5"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | Watermark         | true              |
      | Watermark_opacity | 0.0               |

  Scenario: Call /v1/dataroom/create to create a data and validate error: 10024 Watermark opacity must be between 0.1 and 0.5 (opacity>0.1)
    Given "apiNegativeTestUser" calls "v1/dataroom/create" to create a DR with invalid watermark opacity and expects 400, 10024, "Watermark opacity must be between 0.1 and 0.5"
      | attribute         | value             |
      | DataRoomName      | TestDataRoomBySVC |
      | Access            | Restrict          |
      | Watermark         | true              |
      | Watermark_opacity | 0.8               |


  Scenario: Call /v1/dataroom/create to validate error: 10025 You are no longer an admin
    Given "nonAdminAPIKey" calls "v1/dataroom/create" to create a data room and expects 400, 10025, "You are no longer an admin"
      | attribute    | value |
      | DataRoomName | SvcDR |
      | Access       | Any   |


  Scenario: Call /v1/dataroom/create to validate error: 10002 Billing status is inactive
    Given "inactiveBillingStatusAPIKey" calls "v1/dataroom/create" to create a data room and expects 403, 10002, "Billing status is inactive"
      | attribute    | value |
      | DataRoomName | SvcDR |
      | Access       | Any   |