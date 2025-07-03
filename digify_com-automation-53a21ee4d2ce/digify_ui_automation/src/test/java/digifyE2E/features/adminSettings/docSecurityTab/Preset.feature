@preset @regression @adminSettings
Feature: Add preset with various settings

  Background: Navigate to Digify login page and login with valid credentials
    Given I am on the login page
    And I login as "presetUser"

  Scenario: Create preset and send file using same preset settings
    When I navigate to "doc security" tab in "Admin settings" page
    Then I create preset with "Preset with default settings"
    And I validate "created" preset "Preset with default settings"
    And I navigate to "SendFiles" page from home page
    And I upload a file "ExcelFile.xlsx" in send files
    And I select "Allow downloading" in permission dropdown
    And I select "Preset with default settings" from preset dropdown
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "doc security" tab in "Admin settings" page
    And I remove the preset "Preset with default settings"
    And I logout from the application

  Scenario Outline: Create preset with different permission and send file using same preset
    When I navigate to "doc security" tab in "Admin settings" page
    And I create preset with "<presetName>"
    And I Added feature "<feature>"
    And I validate "created" preset "<presetName>"
    Then I navigate to "SendFiles" page from home page
    And I select "<presetName>" from preset dropdown
    And I upload a file "PdfFile.pdf" in send files
    And I validate "<validateFeature>" feature option is disable on send file page
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "doc security" tab in "Admin settings" page
    And I remove the preset "<presetName>"
    And I logout from the application
    Examples:
      | feature                                                                                                                                              | presetName                                            | validateFeature                                                            |
      | Allow downloading,Watermark on                                                                                                                       | PresetWithAllowDwdPermissionAndWatermark              | Allow downloading,Watermark on                                             |
      | Don't allow downloading,Allow printing                                                                                                               | PresetWithNoDwdPermissionAndAllowPrint                | Don't allow downloading,Allow printing                                     |
      | TOA on,Screenshield on                                                                                                                               | PresetWithTOAAndScreenShield                          | TOA on,Screenshield on                                                     |
      | Expire after first access,Only people I specify                                                                                                      | PresetWithExpiryAndAccessPeopleSpecify                | Expire after first access,Only people I specify                            |
      | Expire on fixed date and time,Anyone with the link (no email verification),turn off-->require add. info for public access,turn on-->require passcode | PresetWithExpiryFixDateAndAccessAnyoneWithLinkNoEmail | Expire on fixed date and time,Anyone with the link (no email verification) |

  Scenario Outline: Add and edit preset without updating preset name and validate changes after edit on send file page
    When I navigate to "doc security" tab in "Admin settings" page
    And I create preset with "<presetName>"
    And I Added feature "<feature>"
    And I validate "created" preset "<presetName>"
    And I edit the preset "<presetName>"
    And I Updated feature "<updateFeature>"
    And I validate "edited" preset "<presetName>"
    And I navigate to "SendFiles" page from home page
    And I select "<presetName>" from preset dropdown
    And I upload a file "PdfFile.pdf" in send files
    And I validate "<validateFeature>" feature option is disable on send file page
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "doc security" tab in "Admin settings" page
    And I remove the preset "<presetName>"
    And I logout from the application
    Examples:
      | feature                                | presetName                               | updateFeature                                                          | validateFeature                                                                                               |
      | Allow downloading,Watermark on         | PresetWithAllowDwdPermissionAndWatermark | TOA on,Expire after first access                                       | Allow downloading,Watermark on,TOA on,Expire after first access                                               |
      | Don't allow downloading,Allow printing | PresetWithNoDwdPermissionAndAllowPrint   | Expire on fixed date and time,Only people from email domains I specify | Don't allow downloading,Allow printing,Expire on fixed date and time,Only people from email domains I specify |

  Scenario: Add and edit preset by updating preset name and validate changes after edit on send file page
    When I navigate to "doc security" tab in "Admin settings" page
    And I create preset with "PresetWithAllowDwdPermissionAndWatermark"
    And I Added feature "Allow downloading, Watermark on"
    And I validate "created" preset "PresetWithAllowDwdPermissionAndWatermark"
    And I edit the preset "PresetWithAllowDwdPermissionAndWatermark" and updating name "PresetWithAllowDwdWatermarkTOAExpiry"
    And I Updated feature "TOA on,Expire after first access"
    And I validate "edited" preset "PresetWithAllowDwdWatermarkTOAExpiry"
    And I navigate to "SendFiles" page from home page
    And I select "PresetWithAllowDwdWatermarkTOAExpiry" from preset dropdown
    And I upload a file "PdfFile.pdf" in send files
    And I validate "Allow downloading, Watermark on,TOA on,Expire after first access" feature option is disable on send file page
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate to "doc security" tab in "Admin settings" page
    And I remove the preset "PresetWithAllowDwdWatermarkTOAExpiry"
    And I logout from the application

  Scenario: Enable and disable custom preset and verify same on send file page
    When I navigate to "doc security" tab in "Admin settings" page
    And I create preset with "Default settings test preset"
    And I validate "created" preset "Default settings test preset"
    And I "Enable" custom preset
    Then I navigate to "SendFiles" page from home page
    And I validate custom preset "Enable"
    And I navigate to "doc security" tab in "Admin settings" page
    And I "Disable" custom preset
    And I navigate to "SendFiles" page from home page
    And I validate custom preset "Disable"
    And I navigate to "doc security" tab in "Admin settings" page
    And I remove the existing preset "Default settings test preset"
    And I logout from the application

  @smoke @fileViewer
  Scenario: Share a file with preset and validate preset name and conditions in file viewer
    When I navigate to "doc security" tab in "Admin settings" page
    And I create preset with "PresetWithNoDwdPermissionAndAllowPrint"
    And I select display preset name on file viewer
    Then I Added feature "Don't allow downloading,Allow printing"
    And I validate "created" preset "PresetWithNoDwdPermissionAndAllowPrint"
    And I navigate to "SendFiles" page from home page
    When I select "PresetWithNoDwdPermissionAndAllowPrint" from preset dropdown
    And I upload a file "DocumentFile.docx" in send files
    And I validate "Don't allow downloading,Allow printing" feature option is disable on send file page
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I logout from the application
    And I access the file link as existing user
    Then I login in file viewer as "recipientUser"
    And I wait until file is loaded in file viewer
    And I validate the file name
    And I validate the preset name on file viewer "PresetWithNoDwdPermissionAndAllowPrint"
    And I validate "disabled-->download button" in file viewer
    And I validate "enabled-->print button" in file viewer
    And I logout from file viewer
    And I am on the login page
    And I login as "presetUser"
    And  I navigate to "doc security" tab in "Admin settings" page
    Then I remove the preset "PresetWithNoDwdPermissionAndAllowPrint"
    And I logout from the application

  Scenario: Verify the remembered settings after sending a file with preset on send file page
    When I navigate to "doc security" tab in "Admin settings" page
    And I create preset with "PresetWithNoDwdPermissionAndAllowPrint"
    And I select display preset name on file viewer
    Then I Added feature "Don't allow downloading,Allow printing"
    And I validate "created" preset "PresetWithNoDwdPermissionAndAllowPrint"
    And I navigate to "SendFiles" page from home page
    When I select "PresetWithNoDwdPermissionAndAllowPrint" from preset dropdown
    And I upload a file "DocumentFile.docx" in send files
    And I validate "Don't allow downloading,Allow printing" feature option is disable on send file page
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I navigate back to send file page using send another file button
    Then I validate "PresetWithNoDwdPermissionAndAllowPrint" preset selected on send file page
    And I validate "Don't allow downloading,Allow printing" feature option is disable on send file page
    And I navigate to "doc security" tab in "Admin settings" page
    And I "Enable" custom preset
    And I reorder custom preset
    And I "Disable after reorder" custom preset
    Then I remove the new preset after reorder "PresetWithNoDwdPermissionAndAllowPrint"
    And I logout from the application

  Scenario: Delete all files from manage sent files
    Given I navigate to "ManageSentFiles" page from home page
    Then I delete all files
    And I logout from the application