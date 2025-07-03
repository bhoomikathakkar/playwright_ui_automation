@dataRoomSettings @regression @dataRoom
Feature: Edit data room settings after creating a data room

  Background: Login with valid credentials
    Given I am on the login page

  Scenario Outline: Edit dynamic watermark settings on data room settings
    When I login as "<userPlanType>"
    And I navigate to "CreateDataRoom" page from home page
    And I added data room name
    When I add additional settings of dynamic watermark
      | date and time | ip address | color | position |
      | checked       | checked    | blue  | tile     |
    And I create a data room
    And I navigate to data room "settings" page
    Then I edit the additional settings of dynamic watermark
      | date and time | ip address | color | position |
      | unchecked     | unchecked  | red   | center   |
    Then I validate dynamic watermark additional settings in data room settings page
      | date and time | ip address | color | position |
      | unchecked     | unchecked  | red   | center   |
    And I save the data room settings
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType         |
      | teamPlanAdmin        |
      | businessPlanAdmin    |
      | upgradedProPlanAdmin |


  Scenario Outline: Create data room with different advanced settings and update the advance settings in DR settings page
    When I login as "<userPlanType>"
    And I create a data room and select <drFeatureType>
    And I upload a "PdfFile.pdf" file in DR
    And I navigate to data room "settings" page
    When I update data room settings <editDrFeatureType>
    And I save the data room settings
    And I navigate to data room "files" page
    Then Validate <validateFeatureSettings> in files tab
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType         | drFeatureType                                                           | editDrFeatureType                     | validateFeatureSettings       |
      | upgradedProPlanAdmin | "adv settings-->disable file index"                                     | "adv settings-->enable file index"    | "file index should appear"    |
      | upgradedProPlanAdmin | "adv settings-->disable modified date"                                  | "adv settings-->enable modified date" | "modified date should appear" |
      | teamPlanAdmin        | "adv settings-->disable file index,disable-->editing with MS office"    | "adv settings-->enable file index"    | "file index should appear"    |
      | teamPlanAdmin        | "adv settings-->disable modified date,disable-->editing with MS office" | "adv settings-->enable modified date" | "modified date should appear" |
      | businessPlanAdmin    | "adv settings-->disable file index,disable-->editing with MS office"    | "adv settings-->enable file index"    | "file index should appear"    |
      | businessPlanAdmin    | "adv settings-->disable modified date,disable-->editing with MS office" | "adv settings-->enable modified date" | "modified date should appear" |

  @fileViewer @dev
  Scenario Outline: Create DR with screen shield settings enable for all and update it to enable individually in gp then validate changes on file viewer and in DR settings
    When I login as "<userPlanType>"
    And I create a data room with "screen shield-->enable for all,20% viewable area" enabled
    And I navigate to data room "settings" page
    Then I validate "screen shield-->enable for all,20% viewable area" in data room
    And I updated feature to "screen shield-->enable individually in gp,50% viewable area" in DR Settings
    Then I validate "screen shield-->enable individually in gp,50% viewable area" in data room
    And I navigate to data room "files" page
    And I upload a "PdfFile.pdf" file in DR
    And I select the first file checkbox on the page
    When I view data room's file in the file viewer
    And I wait until file is loaded in file viewer
    Then I validate that screen shield is not appearing in file viewer
    And I close the current tab
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType         |
      | teamPlanAdmin        |
      | upgradedProPlanAdmin |
      | businessPlanAdmin    |

  Scenario Outline: Create data room with TOA options and verify that DR owner is able to update TOA options in DR settings page
    When I login as "<userPlanType>"
    And I create a data room with "<drFeatureType>" enabled
    Then I validate TOA, continue and agree TOA as "dr owner"
    And I navigate to data room "settings" page
    Then I validate "<validateFeature>" option in TOA dropdown
    And I updated feature to "<editDrFeatureType>" in DR Settings
    Then I validate "<validateUpdatedFeature>" option in TOA dropdown
    And I logout from data room
    Examples:
      | userPlanType      | drFeatureType          | validateFeature        | editDrFeatureType      | validateUpdatedFeature |
      | teamPlanAdmin     | Use organization terms | use organization terms | Use custom terms       | use custom terms       |
      | teamPlanAdmin     | Use custom terms       | use custom terms       | Use organization terms | use organization terms |
      | businessPlanAdmin | Use organization terms | use organization terms | Use custom terms       | use custom terms       |
      | businessPlanAdmin | Use custom terms       | use custom terms       | Use organization terms | use organization terms |
      | proPlanAdmin      | Use organization terms | use organization terms | Use custom terms       | use custom terms       |
      | proPlanAdmin      | Use custom terms       | use custom terms       | Use organization terms | use organization terms |


  Scenario Outline: Create data room with expiry enabled and update expiry date in DR Settings and verify the updated expiry date in DR info
    When I login as "<userPlanType>"
    And I navigate to "CreateDataRoom" page from home page
    And I added data room name
    And I enable "Expiry" in data room
    And I validate "expiry" in data room
    When I edit data room expiry date to "3" more days
    And I create a data room
    And I navigate to data room "settings" page
    And I edit data room expiry date to "7" more days
    And I save the data room settings
    And I navigate to data room "files" page
    Then I verified expiry date "7" in data room info
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |


  Scenario: Create data room with TOA options and verify that DR co-owner is able to update TOA options in DR settings page
    When I login as "teamPlanAdmin"
    And I create a data room with "Use organization terms" enabled
    Then I validate TOA, continue and agree TOA as "dr owner"
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "Co-Owner" in data room
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    Then I validate TOA, continue and agree TOA as "dr co-owner"
    And I navigate to data room "settings" page
    Then I validate "use organization terms" option in TOA dropdown
    And I updated feature to "Use custom terms" in DR Settings
    Then I validate "use custom terms" option in TOA dropdown
    And I logout from data room

  Scenario: As a DR co-owner, edit data room settings
    When I login as "teamPlanAdmin"
    And I create a data room with "screen shield-->enable for all,50% viewable area" enabled
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "Co-Owner" in data room
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    Then I validate "guests,analytics,settings" tabs are enabled
    And I upload a "PdfFile.pdf" file in DR
    And I create a folder when ms editing is enabled
    And I navigate to data room "settings" page
    And I validate "screen shield-->enable for all,50% viewable area" option in TOA dropdown
    And I updated feature to "screen shield-->enable individually in gp" in DR Settings
    Then I validate "screen shield-->enable individually in gp" option in TOA dropdown
    And I logout from data room

  Scenario Outline: Add group permission in default guest permission and verify guest permission
    When I login as "<userPlanType>"
    And I create DR with default settings and upload a "PdfFile.pdf" file
    And I navigate to "groups" tab in data room "guests" page
    Then I create a group and select "<permissionType>" permission
    And I save the group
    And I navigate to data room "settings" page
    And I updated feature to "Group permissions" in DR Settings
    And I select the latest group in dr default guest permission
    And I save the data room settings
    And I navigate to "groups" tab in data room "guests" page
    Then I validate default group tag for a group
    And I navigate to "guests" tab in data room "guests" page
    And I add new guest "recipientUser" email in guest page
    Then I validate the selected default group in add new guest page
    And I click on add and send notification button
    And I logout from data room
    And I login as "recipientUser" and access the same data room
    Then guest validate DR permission "<validateDrPermissionAsGuest>"
    And I logout from data room
    And I login as "<userPlanType>" and access the same data room
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType         | permissionType      | validateDrPermissionAsGuest |
      | teamPlanAdmin        | Download (PDF)      | download_pdf                |
      | businessPlanAdmin    | Edit                | edit                        |
      | upgradedProPlanAdmin | Download (Original) | download_original           |
      | teamPlanAdmin        | View                | view                        |
      | businessPlanAdmin    | Print               | print                       |
      | upgradedProPlanAdmin | No Access           | no access                   |

  Scenario Outline: Add group permission in default guest permission and verify group can not be deleted
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I navigate to "groups" tab in data room "guests" page
    Then I create a group and select "<permissionType>" permission
    And I save the group
    And I navigate to data room "settings" page
    And I updated feature to "Group permissions" in DR Settings
    And I select the latest group in dr default guest permission
    And I save the data room settings
    And I navigate to "groups" tab in data room "guests" page
    Then I validate default group tag for a group
    Then I verify that the delete option is not visible for the group
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType         | permissionType      |
      | teamPlanAdmin        | Download (PDF)      |
      | businessPlanAdmin    | Edit                |
      | upgradedProPlanAdmin | Download (Original) |

  @fileViewer @dev
  Scenario Outline: Create data room with Excel What-If Analysis and update the settings
    When I login as "<userPlanType>"
    And I create DR with "<featureType>" and upload a file "CsvFile.csv"
    And I access the file in data room
    Then I validate that what-if excel should appear in the file viewer
    And I close the current tab
    And I navigate to data room "settings" page
    Then I updated feature to "disable-->what-if excel" in DR Settings
    And I save the data room settings
    And I navigate to data room "files" page
    And I access the file in data room
    And I wait until file is loaded in file viewer
    Then I validate that what-if excel should not appear in the file viewer
    And I close the current tab
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType         | featureType              |
      | teamPlanAdmin        | what-if excel            |
      | businessPlanAdmin    | what-if excel            |
      | upgradedProPlanAdmin | pro plan-->what-if excel |


  Scenario Outline: Create a DR with about page enabled, update DR settings and validate that about tab should not appear
    When I login as "<userPlanType>"
    And I create a data room with default settings
    Then I validate that about tab appears in the data room
    And I navigate to data room "settings" page
    Then I updated feature to "disable-->about page" in DR Settings
    And I save the data room settings
    Then I validate that about page should not appear in data room
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | proPlanAdmin      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  Scenario Outline: Create a DR without about page enabled, update DR settings and validate that about tab should appear in DR
    When I login as "<userPlanType>"
    And I create a data room and select "disable-->about page"
    Then I validate that about page should not appear in data room
    And I navigate to data room "settings" page
    Then I updated feature to "enable-->about page" in DR Settings
    And I save the data room settings
    Then I validate that about tab appears in the data room
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | proPlanAdmin      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  Scenario Outline: Disable banner image in DR settings, it should not show for guest if it is disabled by owner
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I store the data room link in memory
    And I validate that banner image appears in DR
    And I navigate to data room "settings" page
    Then I updated feature to "disable-->banner image" in DR Settings
    And I save the data room settings
    Then I validate, banner image should not appear in data room
    And I navigate to data room "guests" page
    And I add "recipientUser" guest with permission as "Edit" in data room
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    Then I validate, banner image should not appear in data room
    And I logout from data room
    Examples:
      | userPlanType      |
      | proPlanAdmin      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  Scenario Outline: Enable banner image in DR settings, it should appear for guest if it is disabled by owner
    When I login as "<userPlanType>"
    And I create a data room and select "disable-->banner image"
    And I store the data room link in memory
    Then I validate, banner image should not appear in data room
    And I navigate to data room "settings" page
    Then I updated feature to "enable-->banner image" in DR Settings
    And I save the data room settings
    And I validate that banner image appears in DR
    And I navigate to data room "guests" page
    And I add "recipientUser" guest with permission as "Edit" in data room
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    And I validate that banner image appears in DR
    And I logout from data room
    Examples:
      | userPlanType      |
      | proPlanAdmin      |
      | teamPlanAdmin     |
      | businessPlanAdmin |