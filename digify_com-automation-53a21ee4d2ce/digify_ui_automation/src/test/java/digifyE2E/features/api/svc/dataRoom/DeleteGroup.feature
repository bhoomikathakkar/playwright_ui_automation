@deleteGroup @svc @dataRoomSvc @regression
Feature: Validate v1/dataroom/group/delete endpoint for deleting a group in DR, covering both success and error scenarios.

  Scenario Outline: Call "v1/dataroom/group/delete" to delete a group and expects: 16000 Successfully dismissed data room group
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                |
      | DataRoomName           | TestDataRoomBySVC001 |
      | Access                 | Any                  |
      | AdditionalVerification | true                 |
      | DefaultPermission      | NoAccess             |
      | Screen_shield          | Individual           |
    When "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute  | value       |
      | GroupName  | hello       |
      | Permission | DownloadPDF |
    Then "<drOwner>" calls "v1/dataroom/group/delete" to delete the group and expects 200 16000 "Successfully dismissed data room group"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call "v1/dataroom/group/delete" to delete group without request form and expects error: 16006 Request data is null
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value              |
      | DataRoomName           | DataRoomGroupBySVC |
      | Access                 | Restrict           |
      | AdditionalVerification | true               |
      | DefaultPermission      | View               |
    When "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute  | value        |
      | GroupName  | TestSvcGroup |
      | Permission | Contributor  |
    Then "<drOwner>" calls "v1/dataroom/group/delete" to delete a group without request form and expects 400 16006 "Request data is null"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call "v1/dataroom/group/delete" to delete group with null DR shareGuid and expects error: 16007 Data room guid is empty
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value              |
      | DataRoomName           | DataRoomGroupBySVC |
      | Access                 | Restrict           |
      | AdditionalVerification | true               |
      | DefaultPermission      | View               |
    When "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute  | value        |
      | GroupName  | TestSvcGroup |
      | Permission | Contributor  |
    Then "<drOwner>" calls "v1/dataroom/group/delete" to delete a group with null DR shareGuid "" and expects 400 16007 "Data room guid is empty"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call "v1/dataroom/group/delete" to delete the group with invalid DR shareGuid and expects error: 16008 Data room is not found
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value              |
      | DataRoomName           | DataRoomGroupBySVC |
      | Access                 | Restrict           |
      | AdditionalVerification | true               |
      | DefaultPermission      | View               |
      | Screen_shield          | Individual         |
    When "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute  | value        |
      | GroupName  | TestSvcGroup |
      | Permission | Contributor  |
    Then "<drOwner>" calls "v1/dataroom/group/delete" to delete the group with invalid DR shareGuid "f65dd2f576" and expects 400 16008 "Data room is not found"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |


  Scenario Outline: Call "v1/dataroom/group/delete" to delete the group with DR File shareGuid and expects error: 16009 It is not a data room
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value              |
      | DataRoomName           | DataRoomGroupBySVC |
      | Access                 | Restrict           |
      | AdditionalVerification | true               |
      | DefaultPermission      | View               |
      | Screen_shield          | Individual         |
    When "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute  | value        |
      | GroupName  | TestSvcGroup |
      | Permission | Contributor  |
    Then "<drOwner>" calls "v1/dataroom/group/delete" to delete the group with DR File shareGuid "c6505541e80146c7b8fd05a1119168d2" and expects 400 16009 "It is not a data room"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call "v1/dataroom/group/delete" to delete group with cloned failed DR shareGuid and expects error: 16012 Clone data room fail
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value              |
      | DataRoomName           | DataRoomGroupBySVC |
      | Access                 | Restrict           |
      | AdditionalVerification | true               |
      | DefaultPermission      | View               |
      | Screen_shield          | Individual         |
    When "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute  | value        |
      | GroupName  | TestSvcGroup |
      | Permission | Contributor  |
    Then "<drOwner>" calls "v1/dataroom/group/delete" to delete a group with cloned failed DR shareGuid "27cba909fdf449b595bbbebf59825226" and expects 400 16012 "Clone data room fail"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call "v1/dataroom/group/delete" to delete group with null GroupGuid and expects error: 16015 GroupGuid is empty
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value              |
      | DataRoomName           | DataRoomGroupBySVC |
      | Access                 | Restrict           |
      | AdditionalVerification | true               |
      | DefaultPermission      | View               |
      | Screen_shield          | Individual         |
    When "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute  | value        |
      | GroupName  | TestSvcGroup |
      | Permission | Contributor  |
    Then "<drOwner>" calls "v1/dataroom/group/delete" to delete a group with null groupGuid "" and expects 400 16015 "GroupGuid is empty"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call "v1/dataroom/group/delete" to delete the group with invalid group shareGuid and expects error: 16017 Group is default group
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value              |
      | DataRoomName           | DataRoomGroupBySVC |
      | Access                 | Restrict           |
      | AdditionalVerification | true               |
      | DefaultPermission      | View               |
      | Screen_shield          | Individual         |
    When "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute  | value        |
      | GroupName  | TestSvcGroup |
      | Permission | Contributor  |
    Then "<drOwner>" calls "v1/dataroom/group/delete" to delete the group with invalid groupShareGuid "f65dd2f576" and expects 400 16016 "Group is not found"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |

  Scenario Outline: Call "v1/dataroom/group/delete" to delete a default group and expects error: 16017 Group is default group
    Given "<drOwner>" calls "v1/dataroom/create" to create a data room and expects 201, 10000, "Successfully created data room"
      | attribute              | value                |
      | DataRoomName           | DataRoomGroupBySVCho |
      | Access                 | Restrict             |
      | AdditionalVerification | true                 |
      | DefaultPermission      | View                 |
      | Screen_shield          | Individual           |
    And "<drOwner>" calls "/v1/dataroom/group/create" to create a group in DR and expects 201, 10000, "Successfully created data room group"
      | attribute  | value        |
      | GroupName  | TestSvcGroup |
      | Permission | Contributor  |
    When "<drOwner>" calls "v1/dataroom/settings/update" to update dataroom settings by adding group as default permission and expects 200, 17200, "Successfully updated data room settings"
      | attribute              | value      |
      | DataRoomName           | UpdatedDR  |
      | Access                 | Restrict   |
      | AdditionalVerification | true       |
      | Screen_shield          | Individual |
    Then "<drOwner>" calls "v1/dataroom/group/delete" to delete the group and expects 403 16017 "Group is default group"
    Examples:
      | drOwner            |
      | svcApiTeamAdminKey |
      | apiBussAdminKey    |