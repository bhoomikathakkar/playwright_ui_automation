@deleteDataRoom @svc @dataRoomSvc @regression

Feature: Validate v1/dataroom/delete endpoint for deleting a DR, covering both success and error scenarios.

  Scenario Outline: Call "v1/dataroom/delete" to delete a data room and expects: 16000 Successfully dismissed data room group
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                |
      | DataRoomName           | TestDataRoomBySVC001 |
      | Access                 | Any                  |
      | AdditionalVerification | true                 |
      | DefaultPermission      | NoAccess             |
      | Screen_shield          | Individual           |
    Then "<drOwner>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |


  Scenario: Call "v1/dataroom/delete" to delete data room without request form and expects error: 80005 DataRoom request is null
    Given "apiNegativeTestUser" calls "v1/dataroom/delete" to delete a data room without request form and expects 400 80005 "DataRoom request is null"

  Scenario: Call "v1/dataroom/delete" to delete a data room with null DR shareGuid and expects error: 80006 Data room guid is empty
    Given "apiNegativeTestUser" calls "v1/dataroom/delete" to delete a data room with null DR shareGuid "" and expects 400 80006 "Data room guid is empty"

  Scenario: Call "v1/dataroom/delete" to delete a data room with invalid DR shareGuid and expects error: 80007 Data room is not found
    Given "apiNegativeTestUser" calls "v1/dataroom/delete" to delete a data room with invalid DR shareGuid "f65dd2f576" and expects 400 80007 "Data room is not found"

  Scenario: Call "v1/dataroom/delete" to delete a data room with DR File shareGuid and expects error: 80008 It is not a data room
    Given "apiNegativeTestUser" calls "v1/dataroom/delete" to delete a data room with DR File shareGuid "0389e5293d3442568f6f01b8fc5af332" and expects 400 80008 "It is not a data room"

  Scenario: Call "v1/dataroom/delete" to delete a data room with already deleted DR shareGuid and expects error: 80012 Data room is deleted
    Given "apiNegativeTestUser" calls "v1/dataroom/delete" to delete a data room with already deleted DR shareGuid "5f3b7633045144b9bedb7420340c5e0f" and expects 400 80012 "Data room is deleted"

  Scenario: Call "v1/dataroom/delete" to delete a data room by unauthorized user and expects: 80009 User is not authorized to delete data room
    Given "apiTeamAdminKey" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                |
      | DataRoomName           | TestDataRoomBySVC001 |
      | Access                 | Any                  |
      | AdditionalVerification | true                 |
      | DefaultPermission      | NoAccess             |
      | Screen_shield          | Individual           |
    Then "unauthorizedUserAPIKey" calls "v1/dataroom/delete" to delete a data room and expects 403 80009 "User is not authorized to delete data room"