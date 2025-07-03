@dataRoomGuest @regression @dataRoom
Feature: Add guest with different permissions and access in data room and validate data room as guest

  Background: Navigate to Digify login page
    Given I am on the login page

  @smoke @prod
  Scenario Outline: Add guest in data room
    When I login as "<userPlanType>"
    And I create a data room with "dynamic watermark" enabled
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "Download (PDF)" in data room
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  Scenario: As upgraded pro plan user, add guest in a DR
    When I login as "upgradedProPlanAdmin"
    And I create a data room with "dynamic watermark" enabled
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "Download (PDF)" in data room
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room

  @sanity
  Scenario Outline: Accept TOA as guest
    When I login as "<userPlanType>"
    And I create a data room with <drFeatures> enabled
    And I validate TOA, continue and agree TOA as "dr owner"
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "View" in data room
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    Then I validate TOA, continue and agree TOA as "guest"
    And I logout from data room
    Examples:
      | drFeatures               | userPlanType      |
      | "Use organization terms" | teamPlanAdmin     |
      | "Use custom terms"       | businessPlanAdmin |
      | "Use organization terms" | businessPlanAdmin |
      | "Use custom terms"       | teamPlanAdmin     |
      | "Use organization terms" | proPlanAdmin      |
      | "Use custom terms"       | proPlanAdmin      |

  Scenario Outline: Decline TOA as guest and verify guest should not able to access data room
    When I login as "<userPlanType>"
    And I create a data room with <drFeatures> enabled
    And I validate TOA, continue and agree TOA as "dr owner"
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "View" in data room
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    Then I decline ToA as guest
    And I refresh the data room and close ToA
    And I logout from data room
    Examples:
      | drFeatures               | userPlanType      |
      | "Use organization terms" | teamPlanAdmin     |
      | "Use custom terms"       | businessPlanAdmin |
      | "Use organization terms" | businessPlanAdmin |
      | "Use custom terms"       | teamPlanAdmin     |
      | "Use organization terms" | proPlanAdmin      |
      | "Use custom terms"       | proPlanAdmin      |

  Scenario Outline: About page should not appear for guest if it is disabled by owner
    When I login as "<userPlanType>"
    And I create a data room and select "disable-->about page"
    And I store the data room link in memory
    Then I validate that about page should not appear in data room
    And I navigate to data room "guests" page
    And I add "recipientUser" guest with permission as "Edit" in data room
    And I logout from data room
    And I login as "recipientUser" and access the same data room
    Then I validate that about page should not appear in data room
    And I logout from data room
    Examples:
      | userPlanType      |
      | proPlanAdmin      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  Scenario Outline: Banner image should not show for guest if it is disabled by owner
    When I login as "<userPlanType>"
    And I create a data room and select "disable-->banner image"
    And I store the data room link in memory
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

  Scenario Outline: Login in data room as non specified domain guest
    When I login as "<userPlanType>"
    And I create a data room with "Only people from domains I specify" enabled
    And I store the data room link in memory
    And I logout from data room
    And I login as "nonSpecifiedDomainUser" and access the same data room
    Then I validate data room "access denied" error message
    And I logout from data room
    Examples:
      | userPlanType      |
      | proPlanAdmin      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  Scenario Outline: Access an unshared data room as guest
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I store the data room link in memory
    And I navigate to "guests" tab in data room "guests" page
    And I add "recipientUser" guest with permission as "View" in data room
    Then I navigate to manage data room
    And I select "Date (Newest first)" from the sort by filter in Manage data room
    And I select the first data room
    And I unshared data room
    And I logout from the application
    Then I login as "recipientUser" and access the same data room
    And guest validate DR permission "Unshared_DR"
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | proPlanAdmin      |
      | businessPlanAdmin |

  Scenario Outline: Access a revoked data room as guest
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I store the data room link in memory
    And I navigate to "guests" tab in data room "guests" page
    And I add "recipientUser" guest with permission as "View" in data room
    And I return to guest link
    Then I select the guest and click on "Remove guest" from the floating menu
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    Then guest validate DR permission "Access Denied"
    And I logout from data room
    Examples:
      | userPlanType      |
      | proPlanAdmin      |
      | teamPlanAdmin     |
      | businessPlanAdmin |


  Scenario Outline: As a guest, raise a request access for the data room
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I store the data room link in memory
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    And guest validate DR permission "Access Denied"
    Then I Request access for DR  from file viewer
    And I logout from the application
    Examples:
      | userPlanType      |
      | proPlanAdmin      |
      | teamPlanAdmin     |
      | businessPlanAdmin |


  Scenario Outline: Verify that guest can use another email if current used email does not have access of a data room
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I store the data room link in memory
    And I navigate to "guests" tab in data room "guests" page
    And I add "recipientUser" guest with permission as "View" in data room
    And I logout from data room
    Then I login as "recipientUser1" and access the same data room
    And guest validate DR permission "Access Denied"
    Then I use another email to access DR or DS from file viewer
    And I login as "recipientUser" and access the same data room
    And I logout from data room
    Examples:
      | userPlanType      |
      | proPlanAdmin      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  @update #this can be merge with other test case
  Scenario Outline: Verify that guest can access manage DR page from access denied error page
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I store the data room link in memory
    And I navigate to "guests" tab in data room "guests" page
    And I add "recipientUser" guest with permission as "View" in data room
    And I return to guest link
    Then I select the guest and click on "Remove guest" from the floating menu
    And I logout from data room
    Then I login as "recipientUser" and access the same data room
    And guest validate DR permission "Access Denied"
    And I click on "go to data room list" link from file viewer
    And I logout from the application
    Examples:
      | userPlanType      |
      | proPlanAdmin      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  @sanity
  Scenario Outline: Login in data room as guest and validate data room permission
    When I login as "<userPlanType>"
    And I create DR with "<drPermission>" and upload a file "PPTMTestFile.pptm"
    And I store the data room link in memory
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "<guestPermission>" in data room
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    Then guest validate DR permission "<validateDrPermissionAsGuest>"
    And I logout from data room
    Examples:
      | userPlanType         | drPermission   | guestPermission | validateDrPermissionAsGuest |
      | teamPlanAdmin        | View           | View            | view                        |
      | teamPlanAdmin        | Print          | View            | view                        |
      | teamPlanAdmin        | Download (PDF) | Download (PDF)  | download_pdf                |
      | businessPlanAdmin    | View           | View            | view                        |
      | businessPlanAdmin    | Print          | View            | view                        |
      | businessPlanAdmin    | Download (PDF) | Download (PDF)  | download_pdf                |
      | upgradedProPlanAdmin | View           | View            | view                        |
      | upgradedProPlanAdmin | Print          | View            | view                        |
      | upgradedProPlanAdmin | Download (PDF) | Download (PDF)  | download_pdf                |

  @fileViewer
  Scenario Outline: Validate DR and file viewer permission as guest
    When I login as "<userPlanType>"
    And I create DR with "<settingType>,<drPermission>" and upload a file "<fileType>"
    And I store the data room link in memory
    And I navigate to data room "settings" page
    And I validate "<accessTypeInSettings>" in data room
    And I logout from data room
    Then I login as "recipientUser" and access the same data room
    And guest validate DR permission "<validateDrPermissionAsGuest>"
    And I access the file in data room
    And I wait until file is loaded in file viewer
    And I validate the file name
    And I validate "<validateFileViewerBtn>" in file viewer
    And I logout from file viewer
    Examples:
      | userPlanType         | settingType                                                                        | drPermission        | accessTypeInSettings                              | validateDrPermissionAsGuest | validateFileViewerBtn                              | fileType           |
      | teamPlanAdmin        | Anyone with the link or file (email verification),disable-->editing with MS office | View                | Anyone with the link or file (email verification) | view                        | disabled-->print button,disabled-->download button | ImageFile.png      |
      | teamPlanAdmin        | Anyone with the link or file (email verification),disable-->editing with MS office | Edit                | Anyone with the link or file (email verification) | edit                        | enabled-->print button,enabled-->download button   | PdfFile.pdf        |
      | teamPlanAdmin        | Anyone with the link or file (email verification),disable-->editing with MS office | Print               | Anyone with the link or file (email verification) | print                       | enabled-->print button,disabled-->download button  | pptFile.pptx       |
      | teamPlanAdmin        | Anyone with the link or file (email verification),disable-->editing with MS office | Download (PDF)      | Anyone with the link or file (email verification) | download_pdf                | enabled-->print button,enabled-->download button   | CsvFile.csv        |
      | businessPlanAdmin    | Anyone with the link or file (email verification),disable-->editing with MS office | View                | Anyone with the link or file (email verification) | view                        | disabled-->print button,disabled-->download button | TextFile.txt       |
      | businessPlanAdmin    | Anyone with the link or file (email verification),disable-->editing with MS office | Edit                | Anyone with the link or file (email verification) | edit                        | enabled-->print button,enabled-->download button   | DocumentFile.docx  |
      | businessPlanAdmin    | Anyone with the link or file (email verification),disable-->editing with MS office | Print               | Anyone with the link or file (email verification) | print                       | enabled-->print button,disabled-->download button  | ImageJPGFile.jpg   |
      | businessPlanAdmin    | Anyone with the link or file (email verification),disable-->editing with MS office | Download (PDF)      | Anyone with the link or file (email verification) | download_pdf                | enabled-->print button,enabled-->download button   | PPTMTestFile.pptm  |
      | upgradedProPlanAdmin | Anyone with the link or file (email verification)                                  | Print               | Anyone with the link or file (email verification) | print                       | enabled-->print button,disabled-->download button  | ImageJPEGFile.jpeg |
      | upgradedProPlanAdmin | Anyone with the link or file (email verification)                                  | Download (Original) | Anyone with the link or file (email verification) | download_pdf                | enabled-->print button,enabled-->download button   | PdfFile2KB.pdf     |

  @update #Might be duplicate
  Scenario Outline: As a guest, validate access denied error while accessing data room
    When I login as "<userPlanType>"
    And I create DR with "Only people I specify" and upload a file "Mp3TestFile.mp3"
    And I store the data room link in memory
    And I navigate to data room "settings" page
    And I validate "Only people I specify" in data room
    And I logout from data room
    Then I login as "recipientUser" and access the same data room
    And guest validate DR permission "access denied"
    And I logout from data room
    Examples:
      | userPlanType      |
      | proPlanAdmin      |
      | teamPlanAdmin     |
      | businessPlanAdmin |

  Scenario Outline: As a guest, validate updated data room expiry date in data room info
    When I login as "<userPlanType>"
    And I create a data room with feature "Expiry"
    And I store the data room link in memory
    And I navigate to data room "settings" page
    And I validate "expiry" in data room
    When I edit data room expiry date to "3" more days
    And I save the data room settings
    Then I verified expiry date "3" in data room info
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "View" in data room
    And I reload the page
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    Then I verified expiry date "3" in data room info
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @fileViewer
  Scenario Outline: Login as guest in data room and validate updated permission in data room and in file viewer
    When I login as "<userPlanType>"
    And I create DR with "<drPermission>" and upload a file "ImageJPGFile.jpg"
    And I store the data room link in memory
    And I navigate to data room "settings" page
    And I update data room permission to "<updateDrPermission>"
    And I save the data room settings
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "<guestPermission>" in data room
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    Then guest validate DR permission "<validateDrPermissionAsGuest>"
    And I access the file in data room
    And I validate the file name
    And I wait until file is loaded in file viewer
    And I validate "<validateFileViewerBtn>" in file viewer
    And I logout from file viewer
    Examples:
      | userPlanType         | drPermission   | updateDrPermission | guestPermission | validateDrPermissionAsGuest | validateFileViewerBtn                              |
      | teamPlanAdmin        | View           | Print              | Print           | print                       | disabled-->download button,enabled-->print button  |
      | teamPlanAdmin        | Print          | View               | View            | view                        | disabled-->print button,disabled-->download button |
      | teamPlanAdmin        | Download (PDF) | Edit               | Edit            | edit                        | enabled-->print button,enabled-->download button   |
      | businessPlanAdmin    | View           | Print              | Print           | print                       | disabled-->download button,enabled-->print button  |
      | businessPlanAdmin    | Print          | View               | View            | view                        | disabled-->print button,disabled-->download button |
      | businessPlanAdmin    | Download (PDF) | Edit               | Edit            | edit                        | enabled-->print button,enabled-->download button   |
      | upgradedProPlanAdmin | View           | Download (PDF)     | Download (PDF)  | download_pdf                | enabled-->download button,enabled-->print button   |
      | proPlanAdmin         | Print          | Edit               | Edit            | edit                        | enabled-->print button,enabled-->download button   |

  Scenario Outline: Login as guest in data room and validate updated data room access
    When I login as "<userPlanType>"
    And I create DR with "<drAccessAs>,<drPermission>" and upload a file "AudioTestFile.wav"
    And I store the data room link in memory
    And I navigate to data room "settings" page
    And I update data room permission to "<updateDrAccess>"
    And I enable enforce email verification
    And I save the data room settings
    Then I validate and continue enforce email verification modal as data room owner
    And I validate "<validateUpdatedDrAccess>" in data room
    And I logout from data room
    When I login as "<maildropUser>" and access the same data room
    Then I validate the enforce email verification modal as guest
    And I access the data room as "<maildropUser>" with OTP
    Then guest validate DR permission "<validateDrPermissionAsGuest>"
    And I logout from data room
    Examples:
      | userPlanType      | drAccessAs                         | drPermission | updateDrAccess                                    | validateUpdatedDrAccess                           | validateDrPermissionAsGuest | maildropUser         |
      | teamPlanAdmin     | Only people I specify              | View         | Anyone with the link or file (email verification) | Anyone with the link or file (email verification) | view                        | maildropTeamUser     |
      | teamPlanAdmin     | Only people from domains I specify | Edit         | Anyone with the link or file (email verification) | Anyone with the link or file (email verification) | edit                        | maildropTeamUser     |
      | businessPlanAdmin | Only people I specify              | View         | Anyone with the link or file (email verification) | Anyone with the link or file (email verification) | view                        | maildropBusinessUser |
      | businessPlanAdmin | Only people from domains I specify | Edit         | Anyone with the link or file (email verification) | Anyone with the link or file (email verification) | edit                        | maildropBusinessUser |
      | proPlanAdmin      | Only people I specify              | Print        | Anyone with the link or file (email verification) | Anyone with the link or file (email verification) | print                       | maildropProUser      |
      | proPlanAdmin      | Only people from domains I specify | Edit         | Anyone with the link or file (email verification) | Anyone with the link or file (email verification) | edit                        | maildropProUser      |

  Scenario Outline: Login in data room as guest when data room access is updated from domain I specify to Only people I specify
    When I login as "<userPlanType>"
    And I create DR with "Only people from domains I specify" and upload a file "Mp3TestFile.mp3"
    And I store the data room link in memory
    And I navigate to data room "settings" page
    And I update data room permission to "Only people I specify"
    And I save the data room settings
    And I validate "Only people I specify" in data room
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    Then guest validate DR permission "access denied"
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |
