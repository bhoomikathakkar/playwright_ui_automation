@createDataRoom @regression @dataRoom
Feature: Create Data room with different features

  Background: Navigate to Digify login page
    Given I am on the login page

  @smoke @prod
  Scenario Outline: Create a data room, search the data room and open in a new tab
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I navigate to manage data room
    And I search the data room
    Then I click on the first searched data room
    And I close the current tab
    And I select "delete" in the Manage DR floating menu to open a modal
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @smoke @prod @sanity
  Scenario Outline: Create data room with different add-ons
    When I login as "<userPlanType>"
    And I create a data room with "Anyone with the link or file (email verification),screen shield-->enable for all,Expiry,dynamic watermark" enabled
    And I navigate to data room "settings" page
    Then I validate "Anyone with the link or file (email verification),screen shield-->enable for all,expiry,dynamic watermark" in data room
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  Scenario: As upgraded pro plan user, create a DR with different add-ons and validate on data room settings page
    When I login as "upgradedProPlanAdmin"
    And I create a data room with "Anyone with the link or file (email verification),screen shield-->enable for all,Expiry,dynamic watermark" enabled
    And I navigate to data room "settings" page
    Then I validate "Anyone with the link or file (email verification),screen shield-->enable for all,expiry,dynamic watermark" in data room
    And I "delete" data room from advanced settings
    And I logout from data room

  @fileViewer @dev
  Scenario Outline: Create data room without Excel What-If Analysis and verify spreadsheet file
    When I login as "<userPlanType>"
    And I create DR with "disable-->what-if excel,disable-->editing with MS office" and upload a file "ExcelFile.xlsx"
    And I access the file in data room
    And I wait until file is loaded in file viewer
    Then I validate that what-if excel should not appear in the file viewer
    And I close the current tab
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  @fileViewer @dev
  Scenario: As a pro plan admin, create data room without Excel What-If Analysis and verify spreadsheet file
    When I login as "upgradedProPlanAdmin"
    And I create DR with "disable-->what-if excel" and upload a file "CsvFile.csv"
    And I access the file in data room
    And I wait until file is loaded in file viewer
    Then I validate that what-if excel should not appear in the file viewer
    And I close the current tab
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room

  @fileViewer @dev
  Scenario Outline: Create data room with Excel What-If Analysis and verify spreadsheet file
    When I login as "<userPlanType>"
    And I create DR with "<featureType>" and upload a file "ExcelFile.xlsx"
    And I access the file in data room
    And I wait until file is loaded in file viewer
    Then I validate that what-if excel should appear in the file viewer
    And I close the current tab
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      | featureType                                    |
      | teamPlanAdmin     | what-if excel,disable-->editing with MS office |
      | businessPlanAdmin | what-if excel,disable-->editing with MS office |

  @fileViewer @dev
  Scenario: As a pro plan admin, create data room with Excel What-If Analysis and verify spreadsheet file
    When I login as "proPlanAdmin"
    And I create DR with "pro plan-->what-if excel" and upload a file "CsvFile.csv"
    And I access the file in data room
    And I wait until file is loaded in file viewer
    Then I validate that what-if excel should appear in the file viewer
    And I close the current tab
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room

  Scenario Outline: Create data room with MS editing and create editable files
    When I login as "<userPlanType>"
    And I create a data room with "enable-->Editing with MS office" enabled
    And I navigate to data room "Files" page
    Then I validate, new dropdown should appear in the files tab
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  @fileViewer @dev @sanity
  Scenario Outline: Create data room with screen shield enabled in data room and verify in file viewer
    When I login as "<userPlanType>"
    And I create a data room with "screen shield-->enable for all" enabled
    And I upload a "PdfFile4MB.pdf" file in DR
    And I access the file in data room
    And I wait until file is loaded in file viewer
    Then I validate screen shield in file viewer
    And I close the current tab
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  @fileViewer @dev
  Scenario: As a pro plan admin, create data room with screen shield enabled in data room and verify in file viewer
    When I login as "upgradedProPlanAdmin"
    And I create a data room with "screen shield-->enable for all" enabled
    And I upload a "CsvFile.csv" file in DR
    And I access the file in data room
    And I wait until file is loaded in file viewer
    Then I validate screen shield in file viewer
    And I close the current tab
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room

  Scenario Outline: Create data room with different advanced settings
    When I login as "<userPlanType>"
    And I create a data room and select "<drFeatureType>"
    And I upload a "Mp3TestFile.mp3" file in DR
    Then Validate "<validateExpectedOutcome>" in files tab
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType         | drFeatureType                                                         | validateExpectedOutcome         |
      | upgradedProPlanAdmin | adv settings-->disable file index                                     | file index should not appear    |
      | upgradedProPlanAdmin | adv settings-->disable modified date                                  | modified date should not appear |
      | teamPlanAdmin        | adv settings-->disable modified date,disable-->editing with MS office | modified date should not appear |
      | teamPlanAdmin        | adv settings-->disable file index,disable-->editing with MS office    | file index should not appear    |
      | businessPlanAdmin    | adv settings-->disable file index,disable-->editing with MS office    | file index should not appear    |
      | businessPlanAdmin    | adv settings-->disable modified date,disable-->editing with MS office | modified date should not appear |

  @sanity
  Scenario Outline: Create data room with different dynamic watermark settings
    When I login as "<userPlanType>"
    And I navigate to "CreateDataRoom" page from home page
    And I added data room name
    Then I add additional settings of dynamic watermark
      | date and time | ip address | color | position |
      | checked       | checked    | blue  | tile     |
    And I create a data room
    And I navigate to data room "settings" page
    Then I validate dynamic watermark additional settings in data room settings page
      | date and time | ip address | color | position |
      | checked       | checked    | blue  | tile     |
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType         |
      | teamPlanAdmin        |
      | upgradedProPlanAdmin |
      | businessPlanAdmin    |

  Scenario Outline: Create data room with expiry date and time
    When I login as "<userPlanType>"
    And I navigate to "CreateDataRoom" page from home page
    And I added data room name
    And I enable "Expiry" in data room
    And I validate "expiry" in data room
    Then I edit data room expiry date to "5" more days
    And I create a data room
    Then I verified expiry date "5" in data room info
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | proPlanAdmin      |
      | businessPlanAdmin |


  Scenario: Validate premium feature upgrade modal on data room page
    When I login as "proPlanAdmin"
    And I navigate to "CreateDataRoom" page from home page
    Then I validate premium feature label for dynamic watermark, screen shield and Q&A on create DR page
    And I click on upgrade for "dynamic watermark upgrade" feature in data room
    Then I validate premium feature upgrade modal for "dynamic watermark" in DR
    And I close the modal
    And I click on upgrade for "screen shield upgrade" feature in data room
    Then I validate premium feature upgrade modal for "screen shield" in DR
    And I close the modal
    And I click on upgrade for "q&a upgrade" feature in data room
    Then I validate premium feature upgrade modal for "q&a" in DR
    And I close the modal
    And I logout from data room


  Scenario Outline: As a DR co-owner, validate data room tab access, upload a file and create folder
    When I login as "teamPlanAdmin"
    And I create a data room with "Only people I specify" enabled
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "<guestType>" guest with permission as "<guestPermission>" in data room
    And I logout from data room
    When I login as "<guestType>" and access the same data room
    Then I validate "<drTabs>" tabs are enabled
    And I upload a "PdfFile.pdf" file in DR
    And I create a folder when ms editing is enabled
    Then I logout from data room
    Examples:
      | guestType      | guestPermission | drTabs                                |
      | recipientUser  | Co-Owner        | files,about,guests,analytics,settings |
      | recipientUser1 | Edit            | files,about                           |


  Scenario Outline: Create data room with guest list and select option as show everyone
    When I login as "<userPlanType>"
    And I create a data room and select "guest list-->show everyone"
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "<guest1>" guest with permission as "Co-Owner" in data room
    Then I return to guest link
    And I add another guest "<guest2>" with permission as "Download (PDF)" in DR
    Then I return to guest link
    And I add another guest "<guest3>" with permission as "View" in DR
    And I logout from data room
    And I login as "<guest2>" and access the same data room
    Then I validate "guests" tabs are enabled
    And I navigate to data room "guests" page
    Then I validate user "<guest1>" on guest page
    And I validate user "<guest2>" on guest page
    And I validate user "<guest3>" on guest page
    And I logout from data room
    And I login as "<userPlanType>" and access the same data room
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType         | guest1              | guest2         | guest3         |
      | teamPlanAdmin        | recipientUser       | recipientUser1 | recipientUser2 |
      | businessPlanAdmin    | businessPlanUser    | recipientUser1 | recipientUser2 |
      | upgradedProPlanAdmin | upgradedProPlanUser | recipientUser1 | recipientUser2 |

  Scenario Outline: Create data room with guest list and select option as show owners and people in same group
    When I login as "<userPlanType>"
    And I create a data room and select "guest list-->show owners and same people in a group"
    And I store the data room link in memory
    And I navigate to data room "guests" page
    And I navigate to "groups" tab
    Then I create a group and select "Download (PDF)" permission
    And I save the group
    And I select the group
    And I add guest "<guest2>" to the group when there are no existing guests in the data room
    And I navigate to data room "guests" page
    And I add another guest "<guest1>" with permission as "Co-Owner" in DR
    Then I return to guest link
    And I add another guest "<guest3>" with permission as "View" in DR
    And I logout from data room
    And I login as "<guest2>" and access the same data room
    Then I validate "guests" tabs are enabled
    And I navigate to data room "guests" page
    Then I validate user "<guest1>" on guest page
    And I validate user "<guest2>" on guest page
    And I validate user "<guest3>" should not be visible on guest page
    And I logout from data room
    And I login as "<userPlanType>" and access the same data room
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType         | guest1              | guest2         | guest3         |
      | teamPlanAdmin        | recipientUser       | recipientUser1 | recipientUser2 |
      | businessPlanAdmin    | businessPlanUser    | recipientUser1 | recipientUser2 |
      | upgradedProPlanAdmin | upgradedProPlanUser | recipientUser1 | recipientUser2 |


  Scenario: As a guest access a expired dr using direct link
    When I access the dr link when "dr is expired"
    And I login as "expiredDRGuest" and access the data room using direct link
    Then I validated "expired dr" error page in data room
    And I logout from the application


  Scenario: As a guest access a subscription expired user dr using direct link
    When I access the dr link when "subscription expired"
    And I login as "expiredDRGuest" and access the data room using direct link
    Then I validated "subscription expired dr" error page in data room
    And I logout from the application

  Scenario Outline: Validate error message when data room is created with default guest permission as group permission
    When I login as "<userPlanType>"
    And I navigate to "CreateDataRoom" page from home page
    And I added data room name
    And I enable "Group permissions" in data room
    Then I validate the "set up group permissions later" warning modal
    And I select ok button in the modal
    Then I create a data room
    And I navigate to data room "settings" page
    And I updated feature to "Group permissions" in DR Settings
    Then I validate data room "no groups available" error message
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType         |
      | teamPlanAdmin        |
      | businessPlanAdmin    |
      | upgradedProPlanAdmin |
    
    