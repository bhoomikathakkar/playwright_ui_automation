@removeRecipientDR @svc @dataRoomSvc @regression

Feature: Validate /v1/dataroom/removerecipient endpoint for removing a guest from a DR, covering both success and error scenarios.

  Scenario Outline: Call /v1/dataroom/removerecipient to remove guest from data room and validate 60000 Successfully removed recipient
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                     |
      | DataRoomName           | TestDataRoomBySVC-Remove2 |
      | Access                 | Any                       |
      | AdditionalVerification | true                      |
      | DefaultPermission      | NoAccess                  |
      | Screen_shield          | Individual                |
      | TermsOfAccess          | false                     |
      | Watermark              | false                     |
      | AboutPage              | true                      |
      | HideBanner             | true                      |
    When "<drOwner>" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser" in DR and expects 201, 50000, "Successfully added recipient"
      | attribute  | value |
      | Permission | Print |
    Then "<drOwner>" calls "/v1/dataroom/removerecipient" to remove recipient "recipientUser" from DR and expects 201, 60000, "Successfully removed recipient"
    And "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario: Call /v1/dataroom/removerecipient to remove guest with no data room request form and validate error: 60005 DataRoom request is null
    Given "apiTeamAdminKey" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                   |
      | DataRoomName           | TestDataRoomBySVC-guest |
      | Access                 | Any                     |
      | AdditionalVerification | true                    |
      | DefaultPermission      | View                    |
    When "apiTeamAdminKey" calls "/v1/dataroom/addrecipient" to add recipient "recipientUser" in DR and expects 201, 50000, "Successfully added recipient"
      | attribute  | value |
      | Permission | Print |
    Then "apiTeamAdminKey" calls "/v1/dataroom/removerecipient" to remove recipient from DR with no request form and expects 400, 60005, "DataRoom request is null"
    And "apiTeamAdminKey" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"


  Scenario: Call /v1/dataroom/removerecipient to remove owner from data room and validate error: 60006 Data room guid is empty
    Given "apiTeamAdminKey" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                   |
      | DataRoomName           | TestDataRoomBySVC-guest |
      | Access                 | Any                     |
      | AdditionalVerification | true                    |
      | DefaultPermission      | View                    |
    When "apiTeamAdminKey" calls "/v1/dataroom/removerecipient" to remove recipient "apiTeamPlanAdminCred" from DR with null DR shareGuid "" and expects 400, 60006, "Data room guid is empty"
    Then "apiTeamAdminKey" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"

  Scenario: Call /v1/dataroom/removerecipient to remove DR guest and validate error: 60007 Recipient's email is empty
    Given "apiTeamAdminKey" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                    |
      | DataRoomName           | TestDataRoomBySVC-guest2 |
      | Access                 | Any                      |
      | AdditionalVerification | true                     |
      | DefaultPermission      | View                     |
    When "apiTeamAdminKey" calls "/v1/dataroom/addrecipient" to add recipient "apiTeamPlanMemberCred" in DR and expects 201, 50000, "Successfully added recipient"
      | attribute  | value    |
      | Permission | NoAccess |
    Then "apiTeamAdminKey" calls "/v1/dataroom/removerecipient" to remove recipient "blankEmail" from DR and expects 400, 60007, "Recipient's email is empty"
    And "apiTeamAdminKey" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"

  Scenario: Call /v1/dataroom/removerecipient to remove DR guest and validate error: 60008 The email address (email) is not valid
    Given "apiTeamAdminKey" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                    |
      | DataRoomName           | TestDataRoomBySVC-guest2 |
      | Access                 | Any                      |
      | AdditionalVerification | true                     |
      | DefaultPermission      | View                     |
    When "apiTeamAdminKey" calls "/v1/dataroom/addrecipient" to add recipient "apiTeamPlanMemberCred" in DR and expects 201, 50000, "Successfully added recipient"
      | attribute  | value    |
      | Permission | NoAccess |
    Then "apiTeamAdminKey" calls "/v1/dataroom/removerecipient" to remove recipient "invalidEmailId" from DR and expects 400, 60008, "The email address (jesskusermaildrop.cc) is not valid"
    And "apiTeamAdminKey" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"

  Scenario: Call /v1/dataroom/removerecipient to remove DR guest and validate error: 60009 Data room is not found
    Given "apiTeamAdminKey" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                    |
      | DataRoomName           | TestDataRoomBySVC-guest2 |
      | Access                 | Any                      |
      | AdditionalVerification | true                     |
      | DefaultPermission      | View                     |
    When "apiTeamAdminKey" calls "/v1/dataroom/addrecipient" to add recipient "apiTeamPlanMemberCred" in DR and expects 201, 50000, "Successfully added recipient"
      | attribute  | value    |
      | Permission | NoAccess |
    Then "apiTeamAdminKey" calls "/v1/dataroom/removerecipient" to remove recipient "apiTeamPlanAdminCred" from DR with invalid DR shareGuid "0389e5293d" and expects 400, 60009, "Data room is not found"
    And "apiTeamAdminKey" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"

  Scenario: Call /v1/dataroom/removerecipient to remove DR guest and validate error: 60010 It is not a data room
    Given "apiTeamAdminKey" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                    |
      | DataRoomName           | TestDataRoomBySVC-guest2 |
      | Access                 | Any                      |
      | AdditionalVerification | true                     |
      | DefaultPermission      | View                     |
    When "apiTeamAdminKey" calls "/v1/dataroom/addrecipient" to add recipient "apiTeamPlanMemberCred" in DR and expects 201, 50000, "Successfully added recipient"
      | attribute  | value    |
      | Permission | NoAccess |
    Then "apiTeamAdminKey" calls "/v1/dataroom/removerecipient" to remove recipient "apiTeamPlanAdminCred" from DR with invalid DR shareGuid "0389e5293d3442568f6f01b8fc5af332" and expects 400, 60010, "It is not a data room"
    And "apiTeamAdminKey" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"

  Scenario: Call /v1/dataroom/removerecipient to remove non-existing guest from DR and validate error: 60012 Recipient doesn’t have access to data room
    Given "apiTeamAdminKey" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                    |
      | DataRoomName           | TestDataRoomBySVC-guest2 |
      | Access                 | Any                      |
      | AdditionalVerification | true                     |
      | DefaultPermission      | View                     |
    When "apiTeamAdminKey" calls "/v1/dataroom/removerecipient" to remove recipient "recipientUser" from DR and expects 400, 60012, "Recipient doesn't have access to data room"
    Then "apiTeamAdminKey" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"


  Scenario: Call /v1/dataroom/removerecipient to remove owner from data room and validate error: 60013 Recipient is data room owner
    Given "apiTeamAdminKey" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                    |
      | DataRoomName           | TestDataRoomBySVC-guest2 |
      | Access                 | Any                      |
      | AdditionalVerification | true                     |
      | DefaultPermission      | View                     |
    When "apiTeamAdminKey" calls "/v1/dataroom/removerecipient" to remove recipient "apiTeamPlanAdminCred" from DR and expects 400, 60013, "Recipient is data room owner"
    Then "apiTeamAdminKey" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"

  Scenario: Call /v1/dataroom/removerecipient to remove guest by unauthorized user from data room and validate error: 60011 User is not authorized to remove recipient
    Given "apiTeamAdminKey" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                    |
      | DataRoomName           | TestDataRoomBySVC-guest2 |
      | Access                 | Any                      |
      | AdditionalVerification | true                     |
      | DefaultPermission      | View                     |
    When "apiTeamAdminKey" calls "/v1/dataroom/addrecipient" to add recipient "apiTeamPlanMemberCred" in DR and expects 201, 50000, "Successfully added recipient"
      | attribute  | value |
      | Permission | Print |
    Then "apiTeamPlanMemberKey" calls "/v1/dataroom/removerecipient" to remove recipient "apiTeamPlanMemberCred" from DR and expects 400, 60011, "User is not authorized to remove recipient"
    And "apiTeamAdminKey" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"

  Scenario: Call /v1/dataroom/removerecipient to remove themselves from data room and validate error: 60014 User cannot remove themselves
    Given "apiTeamAdminKey" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                    |
      | DataRoomName           | TestDataRoomBySVC-guest2 |
      | Access                 | Any                      |
      | AdditionalVerification | true                     |
      | DefaultPermission      | View                     |
    When "apiTeamAdminKey" calls "/v1/dataroom/addrecipient" to add recipient "apiTeamPlanMemberCred" in DR and expects 201, 50000, "Successfully added recipient"
      | attribute  | value   |
      | Permission | Coowner |
    Then "apiTeamPlanMemberKey" calls "/v1/dataroom/removerecipient" to remove recipient "apiTeamPlanMemberCred" from DR and expects 400, 60014, "User cannot remove themselves"
    And "apiTeamAdminKey" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"

