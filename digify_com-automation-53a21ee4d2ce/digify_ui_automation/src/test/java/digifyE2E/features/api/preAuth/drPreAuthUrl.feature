@drPreAuthUrl @api @regression
  Feature: Generate and validate data room pre-auth URL


  Scenario Outline: Generate pre-auth URL for an empty data room, access pre-auth URL
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>", "<aboutPage>"
    When I "<userType>" generate pre-auth url of the data room with inputs: "<drOwner>",<linkExpiry>,"<browserLock>"
    Then I access the data room pre-auth url in the browser
    And I validate the blank data room when no file is uploaded
    * I close the current tab
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType        | permission       | access   | screenShield | aboutPage | drOwner              | linkExpiry | browserLock |
      | apiTeamAdminKey | DownloadOriginal | Restrict | On           | true      | apiTeamPlanAdminCred | 500        | true        |
      | apiBussAdminKey | View             | Restrict | On           | true      | apiBussPlanAdminCred | 500        | true        |


  Scenario Outline: For PRO plan user, generate pre-auth URL for an empty data room, access pre-auth URL
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>"
    When I "<userType>" generate pre-auth url of the data room with inputs: "<drOwner>",<linkExpiry>,"<browserLock>"
    Then I access the data room pre-auth url in the browser when about page is disabled
    And I validate the blank data room when no file is uploaded
    * I close the current tab
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType       | permission  | access   | screenShield | drOwner             | linkExpiry | browserLock |
      | apiProAdminKey | DownloadPDF | Restrict | Off          | apiProPlanAdminCred | 500        | true        |

  Scenario Outline: Generate and access pre-auth URL for a data room file
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>"
    And I "<userType>" add a guest "<recipientEmail>" in DR via POST dataRoomAddRecipient endpoint with permission "<guestPermission>"
    Then I "<userType>" upload a file "<fileName>" in DR via POST dataRoomUploadFile endpoint
    When I "<userType>" generate pre-auth url of the data room file with inputs: "<recipientEmail>",<linkExpiry>,"<browserLock>"
    And I access data room file pre auth link
    Then I validate "enabled-->print button,enabled-->download button" in file viewer
    * I close the current tab
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType        | permission       | access   | screenShield | linkExpiry | browserLock | guestPermission  | fileName         | recipientEmail |
      | apiTeamAdminKey | DownloadPDF      | Restrict | On           | 500        | true        | DownloadOriginal | ImageJPGFile.jpg | recipientUser  |
      | apiBussAdminKey | Contributor      | Restrict | On           | 500        | true        | DownloadPDF      | ImageJPGFile.jpg | recipientUser  |
      | apiProAdminKey  | DownloadOriginal | Restrict | On           | 500        | true        | DownloadPDF      | ImageJPGFile.jpg | recipientUser  |


  Scenario Outline: Generate pre-auth URL for data room and file with expired link
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>"
    When I "<userType>" upload a file "<fileName>" in DR via POST dataRoomUploadFile endpoint
    Then I "<userType>" generate pre-auth url of the data room with inputs: "<drOwner>",<linkExpiry>,"<browserLock>"
    And I access the data room pre-auth url in the browser when link is expired
    And I validated "dr-->link expired" error in file viewer
    * I close the current tab
    When I "<userType>" generate pre-auth url of the data room file with inputs: "<drOwner>",<linkExpiry>,"<browserLock>"
    Then Access DR-File pre-auth url when link is expired
    And I validated "link expired" error in file viewer
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType        | permission       | access   | screenShield | drOwner              | fileName       | linkExpiry | browserLock |
      | apiTeamAdminKey | DownloadOriginal | Restrict | On           | apiTeamPlanAdminCred | PdfFile2KB.pdf | 1          | false       |
      | apiBussAdminKey | DownloadOriginal | Restrict | On           | apiBussPlanAdminCred | PdfFile2KB.pdf | 1          | false       |
      | apiProAdminKey  | DownloadOriginal | Restrict | On           | apiProPlanAdminCred  | PdfFile2KB.pdf | 1          | false       |


  Scenario Outline: Generate pre-auth URL for a data room with browser lock enabled
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>", "<aboutPage>"
    When I "<userType>" generate pre-auth url of the data room with inputs: "<drOwner>",<linkExpiry>,"<browserLock>"
    And I access the data room pre-auth url in the browser
    Then I access data room pre auth link again in the new browser window when browser lock is true
    And I validated "dr-->browser lock" error in file viewer
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType        | permission       | access   | screenShield | aboutPage | drOwner              | linkExpiry | browserLock |
      | apiTeamAdminKey | DownloadOriginal | Restrict | On           | true      | apiTeamPlanAdminCred | 200        | true        |
      | apiBussAdminKey | DownloadOriginal | Restrict | On           | true      | apiBussPlanAdminCred | 200        | true        |

  Scenario Outline: For PRO plan user, generate pre-auth URL for a data room with browser lock enabled
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>"
    When I "<userType>" generate pre-auth url of the data room with inputs: "<drOwner>",<linkExpiry>,"<browserLock>"
    And I access the data room pre-auth url in the browser when about page is disabled
    Then I access data room pre-auth link again in the new browser window when browser lock is true and about page is disabled
    And I validated "dr-->browser lock" error in file viewer
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType       | permission       | access   | screenShield | drOwner             | linkExpiry | browserLock |
      | apiProAdminKey | DownloadOriginal | Restrict | On           | apiProPlanAdminCred | 200        | true        |


  Scenario Outline: Generate a data room pre-auth URL, access as logged-in guest, and open in another browser with browser lock disabled
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>", "<aboutPage>"
    And I "<userType>" upload a file "<fileName>" in DR via POST dataRoomUploadFile endpoint
    And I "<userType>" add a guest "<recipientEmail>" in DR via POST dataRoomAddRecipient endpoint with permission "<guestPermission>"
    When I "<userType>" generate pre-auth url of the data room file with inputs: "<recipientEmail>",<linkExpiry>,"<browserLock>"
    * I am on the login page
    * I login as "recipientUser"
    And Access the DR file pre-auth URL in a new tab after logging in
    Then I validate "disabled-->print button,disabled-->download button" in file viewer
    And I validate screen shield in file viewer
    And Re-access DR file pre-auth link in a new browser window
    Then I validate "disabled-->print button,disabled-->download button" in file viewer
    And I validate screen shield in file viewer
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType        | permission       | access   | screenShield | aboutPage | linkExpiry | browserLock | guestPermission | fileName       | recipientEmail |
      | apiTeamAdminKey | DownloadOriginal | Restrict | On           | true      | 500        | false       | View            | PdfFile2KB.pdf | recipientUser  |
      | apiBussAdminKey | View             | Restrict | On           | true      | 500        | false       | View            | PdfFile2KB.pdf | recipientUser  |

  Scenario Outline: As PRO plan user, generate a data room pre-auth URL, access as logged-in guest, and open in another browser with browser lock disabled
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>"
    And I "<userType>" upload a file "<fileName>" in DR via POST dataRoomUploadFile endpoint
    And I "<userType>" add a guest "<recipientEmail>" in DR via POST dataRoomAddRecipient endpoint with permission "<guestPermission>"
    When I "<userType>" generate pre-auth url of the data room file with inputs: "<recipientEmail>",<linkExpiry>,"<browserLock>"
    * I am on the login page
    * I login as "recipientUser"
    And Access the DR file pre-auth URL in a new tab after logging in
    Then I validate "disabled-->print button,disabled-->download button" in file viewer
    And I validate screen shield in file viewer
    And Re-access DR file pre-auth link in a new browser window
    Then I validate "disabled-->print button,disabled-->download button" in file viewer
    And I validate screen shield in file viewer
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType       | permission  | access   | screenShield | linkExpiry | browserLock | guestPermission | fileName       | recipientEmail |
      | apiProAdminKey | DownloadPDF | Restrict | On           | 500        | false       | View            | PdfFile2KB.pdf | recipientUser  |


  Scenario Outline: Generate a DR file pre-auth URL, access as logged-in guest, and open in another browser with browser lock enabled
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>"
    And I "<userType>" upload a file "<fileName>" in DR via POST dataRoomUploadFile endpoint
    And I "<userType>" add a guest "<recipientEmail>" in DR via POST dataRoomAddRecipient endpoint with permission "<guestPermission>"
    When I "<userType>" generate pre-auth url of the data room file with inputs: "<recipientEmail>",<linkExpiry>,"<browserLock>"
    * I am on the login page
    * I login as "recipientUser"
    And Access the DR file pre-auth URL in a new tab after logging in
    Then I validate "enabled-->print button,enabled-->download button" in file viewer
    And I validate screen shield in file viewer
    And Re-access DR file pre-auth link in a new browser window
    Then I validated "browser lock" error in file viewer
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType        | permission       | access   | screenShield | linkExpiry | browserLock | guestPermission | fileName       | recipientEmail |
      | apiTeamAdminKey | Contributor      | Restrict | On           | 500        | true        | Contributor     | PdfFile2KB.pdf | recipientUser  |
      | apiProAdminKey  | DownloadOriginal | Restrict | On           | 500        | true        | Contributor     | PdfFile2KB.pdf | recipientUser  |
      | apiBussAdminKey | Contributor      | Restrict | On           | 500        | true        | Contributor     | PdfFile2KB.pdf | recipientUser  |


  Scenario Outline: Generate a data room pre-auth URL, access as logged-in guest, and open in another browser with browser lock enabled
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>", "<aboutPage>"
    And I "<userType>" upload a file "<fileName>" in DR via POST dataRoomUploadFile endpoint
    And I "<userType>" add a guest "<recipientEmail>" in DR via POST dataRoomAddRecipient endpoint with permission "<guestPermission>"
    When I "<userType>" generate pre-auth url of the data room with inputs: "<recipientEmail>",<linkExpiry>,"<browserLock>"
    * I am on the login page
    * I login as "recipientUser"
    Then I access data room pre-auth link in new tab
    And guest validate DR permission "<validateDrPermissionAsGuest>"
    And I access data room pre auth link again in the new browser window when browser lock is true
    Then I validated "dr-->browser lock" error in file viewer
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType        | permission  | access   | screenShield | aboutPage | validateDrPermissionAsGuest | linkExpiry | browserLock | guestPermission | fileName       | recipientEmail |
      | apiTeamAdminKey | DownloadPDF | Restrict | On           | true      | download_pdf                | 500        | true        | DownloadPDF     | PdfFile2KB.pdf | recipientUser  |
      | apiBussAdminKey | DownloadPDF | Restrict | On           | true      | download_pdf                | 500        | true        | DownloadPDF     | PdfFile2KB.pdf | recipientUser  |

  Scenario Outline: As a PRO plan user, generate a data room pre-auth URL, access as logged-in guest, and open in another browser with browser lock enabled
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>"
    And I "<userType>" upload a file "<fileName>" in DR via POST dataRoomUploadFile endpoint
    And I "<userType>" add a guest "<recipientEmail>" in DR via POST dataRoomAddRecipient endpoint with permission "<guestPermission>"
    When I "<userType>" generate pre-auth url of the data room with inputs: "<recipientEmail>",<linkExpiry>,"<browserLock>"
    * I am on the login page
    * I login as "recipientUser"
    Then I access data room pre-auth link in new tab when about page is disabled for the data room
    And guest validate DR permission "<validateDrPermissionAsGuest>"
    And I access data room pre-auth link again in the new browser window when browser lock is true and about page is disabled
    Then I validated "dr-->browser lock" error in file viewer
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType       | permission  | access   | screenShield | validateDrPermissionAsGuest | linkExpiry | browserLock | guestPermission | fileName       | recipientEmail |
      | apiProAdminKey | DownloadPDF | Restrict | On           | download_pdf                | 500        | true        | DownloadPDF     | PdfFile2KB.pdf | recipientUser  |

  Scenario Outline: Generate a DR-File pre-auth URL, access as dr owner, and open in another browser with browser lock off
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>"
    And I "<userType>" upload a file "<fileName>" in DR via POST dataRoomUploadFile endpoint
    When I "<userType>" generate pre-auth url of the data room file with inputs: "<drOwner>",<linkExpiry>,"<browserLock>"
    * I am on the login page
    * I login as "<drOwner>"
    Then Access the DR file pre-auth URL in a new tab after logging in
    And I validate "enabled-->print button,enabled-->download button" in file viewer
    And I validate screen shield in file viewer
    Then Re-access DR file pre-auth link in a new browser window
    And I validate "enabled-->print button,enabled-->download button" in file viewer
    And I validate screen shield in file viewer
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType        | permission | access   | screenShield | drOwner              | linkExpiry | browserLock | fileName       |
      | apiTeamAdminKey | View       | Restrict | On           | apiTeamPlanAdminCred | 500        | false       | PdfFile2KB.pdf |
      | apiBussAdminKey | View       | Restrict | On           | apiBussPlanAdminCred | 500        | false       | PdfFile2KB.pdf |
      | apiProAdminKey  | View       | Restrict | On           | apiProPlanAdminCred  | 500        | false       | PdfFile2KB.pdf |

  Scenario Outline: Generate a DR pre-auth URL, access as dr owner, and open in another browser with browser lock off
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>", "<aboutPage>"
    When I "<userType>" generate pre-auth url of the data room with inputs: "<drOwner>",<linkExpiry>,"<browserLock>"
    * I am on the login page
    * I login as "<drOwner>"
    And I access data room pre-auth link in new tab
    And I validate the files tab in data room
    Then I access data room pre auth link again in the new browser window when browser lock is disabled
    And I validate the files tab in data room
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType        | permission | access   | screenShield | aboutPage | drOwner              | linkExpiry | browserLock |
      | apiTeamAdminKey | View       | Restrict | On           | true      | apiTeamPlanAdminCred | 500        | false       |
      | apiBussAdminKey | View       | Restrict | On           | true      | apiBussPlanAdminCred | 500        | false       |

  Scenario Outline: As PRO plan user, generate a DR pre-auth URL, access as dr owner, and open in another browser with browser lock off
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>"
    When I "<userType>" generate pre-auth url of the data room with inputs: "<drOwner>",<linkExpiry>,"<browserLock>"
    * I am on the login page
    * I login as "<drOwner>"
    And I access data room pre-auth link in new tab when about page is disabled for the data room
    And I validate the files tab in data room
    Then I access data room pre-auth link again in the new browser window when browser lock is disabled and about page is disabled for the DR
    And I validate the files tab in data room
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType       | permission | access   | screenShield | drOwner             | linkExpiry | browserLock |
      | apiProAdminKey | View       | Restrict | On           | apiProPlanAdminCred | 500        | false       |

  Scenario Outline: Generate a DR pre-auth URL, access as dr owner, and open in another browser with browser lock on
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>", "<aboutPage>"
    When I "<userType>" generate pre-auth url of the data room with inputs: "<drOwner>",<linkExpiry>,"<browserLock>"
    * I am on the login page
    * I login as "<drOwner>"
    And I access data room pre-auth link in new tab
    And I validate the files tab in data room
    Then I access data room pre auth link again in the new browser window when browser lock is true
    And I validated "dr-->browser lock" error in file viewer
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType        | permission  | access   | screenShield | aboutPage | drOwner              | linkExpiry | browserLock |
      | apiTeamAdminKey | DownloadPDF | Restrict | On           | true      | apiTeamPlanAdminCred | 500        | true        |
      | apiBussAdminKey | DownloadPDF | Restrict | On           | true      | apiBussPlanAdminCred | 500        | true        |

  Scenario Outline: As PRO plan user, generate a DR pre-auth URL, access as dr owner, and open in another browser with browser lock on
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>"
    When I "<userType>" generate pre-auth url of the data room with inputs: "<drOwner>",<linkExpiry>,"<browserLock>"
    * I am on the login page
    * I login as "<drOwner>"
    And I access data room pre-auth link in new tab when about page is disabled for the data room
    And I validate the files tab in data room
    Then I access data room pre-auth link again in the new browser window when browser lock is true and about page is disabled
    And I validated "dr-->browser lock" error in file viewer
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType       | permission  | access   | screenShield | drOwner             | linkExpiry | browserLock |
      | apiProAdminKey | DownloadPDF | Restrict | On           | apiProPlanAdminCred | 500        | true        |

  Scenario Outline: Generate a DR-File pre-auth URL, access as dr owner, and open in another browser with browser lock on
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>"
    And I "<userType>" upload a file "<fileName>" in DR via POST dataRoomUploadFile endpoint
    When I "<userType>" generate pre-auth url of the data room file with inputs: "<drOwner>",<linkExpiry>,"<browserLock>"
    * I am on the login page
    * I login as "<drOwner>"
    Then Access the DR file pre-auth URL in a new tab after logging in
    And I validate "enabled-->print button,enabled-->download button" in file viewer
    And I validate screen shield in file viewer
    Then Re-access DR file pre-auth link in a new browser window
    And I validated "browser lock" error in file viewer
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType        | permission  | access   | screenShield | drOwner              | linkExpiry | browserLock | fileName         |
      | apiTeamAdminKey | DownloadPDF | Restrict | On           | apiTeamPlanAdminCred | 500        | true        | ImageJPGFile.jpg |
      | apiBussAdminKey | DownloadPDF | Restrict | On           | apiBussPlanAdminCred | 500        | true        | ImageJPGFile.jpg |
      | apiProAdminKey  | DownloadPDF | Restrict | On           | apiProPlanAdminCred  | 500        | true        | ImageJPGFile.jpg |


  Scenario: Generate pre-auth URL as co-owner for a data room file and access as guest
    Given As "apiTeamAdminKey" create a DR via POST dataRoomCreateEndpoint with settings: "DownloadOriginal", "Restrict", "On", "true"
    When I "apiTeamAdminKey" upload a file "TextFile.txt" in DR via POST dataRoomUploadFile endpoint
    And I "apiTeamAdminKey" add a guest "recipientUser" in DR via POST dataRoomAddRecipient endpoint with permission "DownloadPDF"
    And I "apiTeamAdminKey" add a guest "apiTeamPlanMemberCred" in DR via POST dataRoomAddRecipient endpoint with permission "Coowner"
    When I "apiTeamPlanMemberKey" generate pre-auth url of the data room file with inputs: "recipientUser",500,"true"
    And I access data room file pre auth link
    Then I validate "enabled-->print button,enabled-->download button" in file viewer
    * I close the current tab
    And "apiTeamAdminKey" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"


  Scenario Outline: Validate 400 status code for a DR pre-auth url
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>"
    When I "<userType>" POST to the create DR preAuthURL, invalid combinations of <linkExpiry>, "<drOwnerEmail>" and expect 400
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType        | drOwnerEmail         | access   | permission  | linkExpiry | screenShield |
      | apiBussAdminKey | apiBussPlanAdminCred | restrict | DownloadPDF | 200        | Off          |
      | apiTeamAdminKey | apiTeamPlanAdminCred | restrict | DownloadPDF | 200        | Off          |
      | apiProAdminKey  | apiProPlanAdminCred  | restrict | DownloadPDF | 200        | Off          |

  Scenario Outline: Validate 400 status code for a DR pre-auth url when data room is deleted
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>"
    When "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Then I "<userType>" POST to the create DR preAuthURL, when data room is deleted <linkExpiry>, "<drOwnerEmail>" and expect 400
    Examples:
      | userType        | drOwnerEmail         | access   | permission  | linkExpiry | screenShield |
      | apiBussAdminKey | apiBussPlanAdminCred | restrict | DownloadPDF | 200        | Off          |
      | apiProAdminKey  | apiProPlanAdminCred  | restrict | DownloadPDF | 200        | Off          |
      | apiTeamAdminKey | apiTeamPlanAdminCred | restrict | DownloadPDF | 200        | Off          |


  Scenario Outline: Validate 404 status code for a DR file pre-auth url when data room file is trashed
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>"
    * I "<userType>" upload a file "<fileName>" in DR via POST dataRoomUploadFile endpoint
    * I "<userType>" add a guest "<guestUsername>" in DR via POST dataRoomAddRecipient endpoint with permission "<guestPermission>"
    When Data room owner "<userType>" trashed the data room file
    Then I "<userType>" POST to the create DR file preAuthURL when data room file is trashed <linkExpiry>, "<guestUsername>" and expect 404
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType        | guestUsername | permission  | access   | screenShield | linkExpiry | fileName       | guestPermission |
      | apiBussAdminKey | recipientUser | DownloadPDF | Restrict | Off          | 500        | PdfFile2KB.pdf | View            |
      | apiTeamAdminKey | recipientUser | DownloadPDF | Restrict | On           | 500        | PdfFile2KB.pdf | View            |
      | apiProAdminKey  | recipientUser | DownloadPDF | Restrict | Off          | 500        | PdfFile2KB.pdf | View            |

  Scenario Outline: Validate 404 status code for DR pre-auth url with invalid GUID is passed
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "DownloadPDF", "restrict", "Off"
    When I "<userType>" POST to the create DR preAuthURL, with invalid GUID <linkExpiry>, "<drOwnerEmail>" and expect 404
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType        | drOwnerEmail         | linkExpiry |
      | apiBussAdminKey | apiBussPlanAdminCred | 200        |
      | apiTeamAdminKey | apiTeamPlanAdminCred | 200        |
      | apiProAdminKey  | apiProPlanAdminCred  | 200        |

  Scenario Outline: Validate 403 status code for a DR file pre-auth url when data room guest permission is no access
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>"
    * I "<userType>" upload a file "<fileName>" in DR via POST dataRoomUploadFile endpoint
    When I "<userType>" add a guest "<guestUsername>" in DR via POST dataRoomAddRecipient endpoint with permission "<guestPermission>"
    Then I "<userType>" POST to the create DR file preAuthURL, when guest permission is no access with <linkExpiry>, "<guestUsername>" and expect 403
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType        | guestUsername | permission  | access   | screenShield | linkExpiry | fileName       | guestPermission |
      | apiTeamAdminKey | recipientUser | DownloadPDF | Restrict | On           | 500        | PdfFile2KB.pdf | NoAccess        |
      | apiBussAdminKey | recipientUser | DownloadPDF | Restrict | On           | 500        | PdfFile2KB.pdf | NoAccess        |
      | apiProAdminKey  | recipientUser | DownloadPDF | Restrict | On           | 500        | PdfFile2KB.pdf | NoAccess        |

  Scenario Outline: Validate 403 status code for a DR file pre-auth url when guest does not belong to a DR
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>"
    * I "<userType>" upload a file "<fileName>" in DR via POST dataRoomUploadFile endpoint
    When I "<userType>" add a guest "<guestUsername>" in DR via POST dataRoomAddRecipient endpoint with permission "<guestPermission>"
    Then I "<userType>" POST to the create DR file preAuthURL, when guest does not belong to a DR with <linkExpiry>, "<unassociatedGuestUsername>" and expect 403
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType        | guestUsername | permission  | access   | screenShield | linkExpiry | fileName       | guestPermission | unassociatedGuestUsername |
      | apiTeamAdminKey | recipientUser | DownloadPDF | Restrict | On           | 500        | PdfFile2KB.pdf | NoAccess        | recipientUser1            |
      | apiBussAdminKey | recipientUser | DownloadPDF | Restrict | On           | 500        | PdfFile2KB.pdf | NoAccess        | recipientUser1            |
      | apiProAdminKey  | recipientUser | DownloadPDF | Restrict | On           | 500        | PdfFile2KB.pdf | NoAccess        | recipientUser1            |


  Scenario Outline: Validate 403 status code for a DR file pre-auth url when user does not belong to a data room
    Given As "<userType>" create a DR via POST dataRoomCreateEndpoint with settings: "<permission>", "<access>", "<screenShield>"
    * I "<userType>" upload a file "<fileName>" in DR via POST dataRoomUploadFile endpoint
    When I "<userType>" add a guest "<guestUsername>" in DR via POST dataRoomAddRecipient endpoint with permission "<guestPermission>"
    Then I "<unassociatedOwnerUsername>" POST to the create DR file preAuthURL, when owner does not belong to a DR with <linkExpiry>, "<guestUsername>" and expect 403
    Then "<userType>" calls "v1/dataroom/delete" to delete a data room and expects 200 80000 "Successfully deleted data room"
    Examples:
      | userType        | guestUsername | permission  | access   | screenShield | linkExpiry | fileName         | guestPermission | unassociatedOwnerUsername |
      | apiTeamAdminKey | recipientUser | DownloadPDF | Restrict | On           | 500        | ImageJPGFile.jpg | NoAccess        | apiProAdminKey            |
      | apiProAdminKey  | recipientUser | DownloadPDF | Restrict | On           | 500        | ImageJPGFile.jpg | NoAccess        | apiBussAdminKey           |
      | apiBussAdminKey | recipientUser | DownloadPDF | Restrict | On           | 500        | ImageJPGFile.jpg | NoAccess        | apiProAdminKey            |



