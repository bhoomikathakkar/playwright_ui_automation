@dataRoomGranularPermission @regression @dataRoom
Feature: Verify data room granular permission feature

  Background: Navigate to Digify login page
    Given I am on the login page

    #There will be no changes in the GP permission while adding a guest, gp will follow the data room permission
  Scenario Outline: Create data room with different permissions and add guest with GP permission, guest will have DR default permission
    When I login as "<userPlanType>"
    And I create DR with <drPermissionAs> and upload a file "ImageFile.png"
    And I navigate to data room "guests" page
    When I add "recipientUser" guest with permission as "Granular Permissions" in data room
    And I open guest access preview on add guest page
    And I validate guest access preview based on permission <drPermissionAs>
    And I navigate to "permission overview" tab
    Then I validate data room permission "<validateDrGP>" on GP page
    Then I validate file permission "<validateFileGP>" on GP page
    And I click on the "cancel button" button
    And I logout from data room
    Then I login as "recipientUser" and access the same data room
    And guest validate DR permission "<validateDrPermAsGuest>"
    And I logout from data room
    Then I login as "<userPlanType>" and access the same data room
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      | drPermissionAs | validateDrGP   | validateFileGP | validateDrPermAsGuest |
      | businessPlanAdmin | "No Access"    | row1,NO_ACCESS | row2,NO_ACCESS | No Access             |
      | teamPlanAdmin     | "Edit"         | row1,EDIT      | row2,EDIT      | EDIT                  |
      | teamPlanAdmin     | "View"         | row1,VIEW      | row2,VIEW      | View                  |


  Scenario Outline: Create a group in DR with granular permission
    When I login as "<userPlanType>"
    And I create DR with <drPermissionAs> and upload a file "ImageFile.png"
    And I navigate to "groups" tab in data room "guests" page
    And I create a group and select "Granular Permissions" permission
    And I click on the "edit gp" button
    Then I validate data room permission "<validateDrGP>" on GP page
    Then I validate file permission "<validateFileGP>" on GP page
    And I click on the "cancel gp" button
    And I save the group
    And I select the group
    And I add guest "recipientUser" to the group when there are no existing guests in the data room
    And I logout from data room
    Then I login as "recipientUser" and access the same data room
    And guest validate DR permission "<validateDrPermAsGuest>"
    And I logout from data room
    Then I login as "<userPlanType>" and access the same data room
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType  | drPermissionAs | validateDrGP   | validateFileGP | validateDrPermAsGuest |
      | teamPlanAdmin | "No Access"    | row1,NO_ACCESS | row2,NO_ACCESS | No Access             |
      | proPlanAdmin  | "Print"        | row1,PRINT     | row1,PRINT     | Print                 |


  Scenario Outline: Add guest in a group, update the group GP and set all permission to same and delete the group
      #Expected: Guest should still have the GP of the group as before and guest should be able to view/download based on the GP
    When I login as "<userPlanType>"
    And I create DR with <drPermissionAs> and upload a file "ImageFile.png"
    And I navigate to "groups" tab in data room "guests" page
    And I create a group and select "Granular Permissions" permission
    And I save the group
    And I select the group
    And I add guest "recipientUser" to the group when there are no existing guests in the data room
    And I navigate to "groups" tab
    And I click on gp link for the group
    Then I set GP for data room "<setGPForDRTo>"
    And I click on "Set all permissions to" in "review nested files/folders with differing permissions" modal
    And I click on the "save gp" button
    Then I validate data room permission "<validateDrGP>" on GP page
    Then I validate file permission "<validateFileGP>" on GP page
    And I click on the "close gp" button
    And I select the group and click on "delete group" from the floating menu
    And I logout from data room
    Then I login as "recipientUser" and access the same data room
    And guest validate DR permission "<validateDrPermAsGuest>"
    And I logout from data room
    Examples:
      | userPlanType      | drPermissionAs | setGPForDRTo      | validateDrGP      | validateFileGP    | validateDrPermAsGuest |
      | teamPlanAdmin     | "View"         | row1,NO_ACCESS    | row1,NO_ACCESS    | row2,NO_ACCESS    | No Access             |
      | teamPlanAdmin     | "No Access"    | row1,PRINT        | row1,PRINT        | row2,PRINT        | Print                 |
      | teamPlanAdmin     | "No Access"    | row1,VIEW         | row1,VIEW         | row2,VIEW         | View                  |
      | businessPlanAdmin | "Print"        | row1,DOWNLOAD_PDF | row1,DOWNLOAD_PDF | row2,DOWNLOAD_PDF | Download_pdf          |


  Scenario Outline: Add guest in a group, set the group GP to Edit and set all permission to same and delete the group
      #Expected: Guest should still have the GP of the group as before and guest should be able to view/download based on the GP
    When I login as "<userPlanType>"
    And I create DR with <drPermissionAs> and upload a file "ImageFile.png"
    And I navigate to "groups" tab in data room "guests" page
    And I create a group and select "Granular Permissions" permission
    And I save the group
    And I select the group
    And I add guest "recipientUser" to the group when there are no existing guests in the data room
    And I navigate to "groups" tab
    And I click on gp link for the group
    Then I set GP for data room "<setGPForDRTo>"
    And I click on set edit permission to edit button
    And I click on the "save gp" button
    Then I validate data room permission "<validateDrGP>" on GP page
    Then I validate file permission "<validateFileGP>" on GP page
    And I click on the "close gp" button
    And I select the group and click on "delete group" from the floating menu
    And I logout from data room
    Then I login as "recipientUser" and access the same data room
    And guest validate DR permission "<validateDrPermAsGuest>"
    And I logout from data room
    Examples:
      | userPlanType      | drPermissionAs | setGPForDRTo | validateDrGP | validateFileGP | validateDrPermAsGuest |
      | teamPlanAdmin     | "View"         | row1,EDIT    | row1,EDIT    | row2,EDIT      | EDIT                  |
      | businessPlanAdmin | "No Access"    | row1,EDIT    | row1,EDIT    | row2,EDIT      | Edit                  |


  Scenario Outline: Add guest with no access permission and check preview as guest
    When I login as "<userPlanType>"
    And I create DR with <drPermissionAs> and upload a file "ImageFile.png"
    And I navigate to "guests" tab in data room "guests" page
    And I add "recipientUser" guest with permission as "No Access" in data room
    And I logout from data room
    Then I login as "recipientUser" and access the same data room
    And guest validate DR permission "<validateDrPermAsGuest>"
    And I logout from data room
    Examples:
      | userPlanType  | drPermissionAs | validateDrPermAsGuest |
      | teamPlanAdmin | "View"         | No Access             |
      | proPlanAdmin  | "Print"        | No Access             |


  Scenario Outline: Add Bulk guest in DR with different permission
    When I login as "<userPlanType>"
    And I create DR with "View" and upload a file "ImageFile.png"
    And I navigate to "guests" tab in data room "guests" page
    When I add 5 guest with permission as <permissionType> from excel file "BulkEmail.xlsx" in data room
    And I notify guest to visit DR
    And I return to guest link
    Then I validated added 5 guests on guests page
    And I logout from data room
    Examples:
      | userPlanType      | permissionType |
      | teamPlanAdmin     | "No Access"    |
      | proPlanAdmin      | "Edit"         |
      | businessPlanAdmin | "View"         |

  Scenario Outline:Verify that user should find guest already exists error when user try to add duplicate guest using bulk invite
    When I login as "<userPlanType>"
    And I create DR with "View" and upload a file "ImageFile.png"
    And I navigate to "guests" tab in data room "guests" page
    When I add 5 guest with permission as "View" from excel file "BulkEmail.xlsx" in data room
    Then I added same 5 guest again from "BulkEmail.xlsx" excel file in bulk invitation
    And I validate 5 "guests" already exists error modal
    And I notify guest to visit DR
    And I return to guest link
    Then I validated added 5 guests on guests page
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | proPlanAdmin      |
      | businessPlanAdmin |


  Scenario Outline:Verify that user should find guest already exists error when user try to add existing guest using bulk invite
    When I login as "<userPlanType>"
    And I create DR with "View" and upload a file "PdfFile.pdf"
    And I navigate to "guests" tab in data room "guests" page
    Then I add 5 guest with permission as "View" from excel file "BulkEmail.xlsx" in data room
    And I notify guest to visit DR
    And I return to guest link
    Then I added same 5 guest again from file "BulkEmail.xlsx" which were already added in DR
    And I validate 5 "guests" already exists error modal
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | proPlanAdmin      |
      | businessPlanAdmin |

  Scenario Outline: Add guest in a data room with GP permission and, update GP for the guest
    When I login as "<userPlanType>"
    And I create DR with <drPermissionAs> and upload a file "ImageFile.png"
    And I navigate to "guests" tab in data room "guests" page
    And I add "recipientUser" guest with permission as "Granular Permissions" in data room
    And I return to guest link
    And I click on gp link for the guest
    Then I set GP for data room "<setGPForDRTo>"
    And I click on "Set all permissions to" in "review nested files/folders with differing permissions" modal
    And I click on the "save gp" button
    Then I validate data room permission "<validateDrGP>" on GP page
    Then I validate file permission "<validateFileGP>" on GP page
    And I click on the "close gp" button
    And I click on gp link for the guest
    Then I set GP for data room "<setNewGPForDRTo>"
    And I click on "keep different permissions" in "review nested files/folders with differing permissions" modal
    And I click on the "save gp" button
    Then I validate data room permission "<validateNewGPForDr>" on GP page
    Then I validate file permission "<validateNewGPForFile>" on GP page
    And I click on the "close gp" button
    And I logout from data room
    Then I login as "recipientUser" and access the same data room
    And guest validate DR permission "<validateDrPermAsGuest>"
    And I logout from data room
    Examples:
      | userPlanType      | drPermissionAs | setGPForDRTo | validateDrGP | validateFileGP | validateDrPermAsGuest | setNewGPForDRTo   | validateNewGPForDr | validateNewGPForFile |
      | teamPlanAdmin     | "View"         | row1,PRINT   | row1,PRINT   | row2,PRINT     | Print                 | row1,VIEW         | row1,VIEW          | row2,PRINT           |
      | businessPlanAdmin | "No Access"    | row1,VIEW    | row1,VIEW    | row2,VIEW      | View                  | row1,DOWNLOAD_PDF | row1,DOWNLOAD_PDF  | row2,VIEW            |

  Scenario Outline: Add guest with expiry and check preview as guest
    When I login as "<userPlanType>"
    And I create DR with <drPermissionAs> and upload a file "ImageFile.png"
    And I navigate to "guests" tab in data room "guests" page
    And I add "recipientUser" guest with permission as "<permissionType>" in data room
    And I navigate to "guestList" tab
    Then I select the guest and click on "manage guest link" from the floating menu
    And I add <expiry> to guest for next <days> <time>
    Then I validate guest expiry <days>
    And I logout from data room
    And I login as "recipientUser" and access the same data room
    And guest validate DR permission "<validateDrPermAsGuest>"
    And I logout from data room
    Examples:
      | userPlanType      | drPermissionAs        | permissionType      | validateDrPermAsGuest | expiry                          | days       | time |
      | teamPlanAdmin     | "View"                | View                | View                  | "Expire on fixed date and time" | "1 day"    | "7"  |
      | teamPlanAdmin     | "Edit"                | Edit                | Edit                  | "Expire on fixed date and time" | "30 days"  | "2"  |
      | businessPlanAdmin | "Print"               | Download (PDF)      | Download_pdf          | "Expire on fixed date and time" | "5 Custom" | "1"  |
      | proPlanAdmin      | "Download (Original)" | Download (Original) | Download_original     | "Expire on fixed date and time" | "30 days"  | "5"  |


  Scenario Outline: Add guest with future access and check preview as guest
    When I login as "<userPlanType>"
    And I create DR with <drPermissionAs> and upload a file "ImageFile.png"
    And I navigate to "guests" tab in data room "guests" page
    And I add "recipientUser" guest with permission as "<permissionType>" in data room
    And I navigate to "guestList" tab
    When I select the guest and click on "manage guest link" from the floating menu
    And I add "Expire on fixed date and time" to guest for next <days> <time>
    And I validate future access on edit guest page
    Then I logout from data room
    And I login as "recipientUser" and access the same data room
    And guest validate future access for <days> days
    And I logout from data room
    Examples:
      | userPlanType      | drPermissionAs        | permissionType      | days       | time |
      | teamPlanAdmin     | "View"                | View                | "Custom 7" | "7"  |
      | businessPlanAdmin | "Download (Original)" | Download (Original) | "Custom 1" | "2"  |
