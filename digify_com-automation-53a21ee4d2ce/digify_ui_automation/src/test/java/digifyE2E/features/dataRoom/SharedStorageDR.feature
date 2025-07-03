@sharedStorageDR @regression @dataRoom
Feature: Verify the scenarios when data room storage is fully consumed

  Background: Navigate to Digify login page
    Given I am on the login page

  Scenario Outline: Create a DR when there is no DR quota left, owner should see add quota paywall
    And I login as "<userPlanType>"
    When I navigate to "CreateDataRoom" page from home page
    Then System should show "no data room quota left" paywall for team admin
    And I click on "add data room quota" button and navigates to pricing page
    And I clicks "modify plan" on "you already have an existing subscription" modal
    And I click on button "logout"
    Examples:
      | userPlanType             |
      | storageFullTeamPlanAdmin |
      | storageFullProPlanAdmin  |

  Scenario Outline: Add a guest in the existing DR when there is not enough guest quota, owner should see add quota paywall
    And I login as "<userPlanType>"
    When I navigate to "ManageDataRoom" page from home page
    And I view the existing data room
    And I navigate to data room "guests" page
    And I try to add a new guest "recipientUser" in data room when guest quota is full
    Then System should show "not enough dr guest quota" paywall for team admin
    And I click on "add data room guest quota" button and navigates to pricing page
    And I clicks "modify plan" on "you already have an existing subscription" modal
    And I click on button "logout"
    Examples:
      | userPlanType             |
      | storageFullTeamPlanAdmin |
      | storageFullProPlanAdmin  |

  Scenario Outline: clone a data room when there is no DR quota left,owner should see add quota paywall
    And I login as "<userPlanType>"
    When I navigate to "ManageDataRoom" page from home page
    And I view the existing data room
    And I navigate to data room "settings" page
    And I "clone(no dr quota)" data room from advanced settings
    Then System should show "clone-->no data room quota left" paywall for team admin
    And I click on "clone-->no data room quota left" button and navigates to pricing page
    And I clicks "modify plan" on "you already have an existing subscription" modal
    And I click on button "logout"
    Examples:
      | userPlanType             |
      | storageFullTeamPlanAdmin |
      | storageFullProPlanAdmin  |

  Scenario Outline: Navigate to create data room page as a team member when there is no DR quota, owner should see contact admin paywall
    And I login as "<userPlanType>"
    When I navigate to "CreateDataRoom" page from home page
    Then System should show "no data room quota left" paywall for the team user
    And I logout from the application
    Examples:
      | userPlanType        |
      | storageFullProUser  |
      | storageFullTeamUser |

  @ignore #Bug-PROD-9247 is raised for this scenario
  Scenario Outline: Team admin DR owner should see a paywall when they try to upload files if encrypted storage quota is fully consumed
    And I login as "<userPlanType>"
    When I navigate to "ManageDataRoom" page from home page
    And I view the existing data room
    And I upload a "PdfFile.pdf" file in DR when encrypted storage is fully consumed
    Then I validate the encrypted storage quota exceeded error message on "files tab" for team admin
    And I click on add more encrypted storage quota link on "files tab" and navigated to pricing checkout page
    And I click on button "logout"
    Examples:
      | userPlanType                      |
      | encryptedStorageFullProPlanAdmin  |
      | encryptedStorageFullTeamPlanAdmin |

  Scenario Outline: Non admin DR owner should see a paywall when they try to upload files if encrypted storage quota is fully consumed
    And I login as "<userPlanType>"
    When I navigate to "ManageDataRoom" page from home page
    And I view the existing data room
    And I upload a "PdfFile.pdf" file in DR when encrypted storage is fully consumed
    Then I validate the encrypted storage quota exceeded error message on "files tab" for non admin
    And I logout from data room
    Examples:
      | userPlanType                     |
      | encryptedStorageFullTeamNonAdmin |
      | encryptedStorageFullProNonAdmin  |

  Scenario: Pro plan admin DR owner should see a paywall when they try to replace file if encrypted storage quota is fully consumed
    And I login as "encryptedStorageFullProPlanAdmin"
    When I navigate to "ManageDataRoom" page from home page
    And I view the existing data room
    And I select the file
    And I select "replace file" option from More floating menu in DR
    And I upload a "ImageFile.png" file in replace new version page when encrypted storage is fully consumed
    Then I validate the encrypted storage quota exceeded error message on "replace file modal" for team admin
    And I click on add more encrypted storage quota link on "replace file modal" and navigated to pricing checkout page
    And I clicks "modify plan" on "you already have an existing subscription" modal
    And I click on button "logout"

  Scenario: Team plan admin DR owner should see a paywall when they try to replace file if encrypted storage quota is fully consumed
    And I login as "encryptedStorageFullTeamPlanAdmin"
    When I navigate to "ManageDataRoom" page from home page
    And I view the existing data room
    And I select the file
    And I select "replace file" option from More floating menu in DR
    And I upload a "PdfFile.pdf" file in replace new version page when encrypted storage is fully consumed
    Then I validate the encrypted storage quota exceeded error message on "replace file modal" for team admin
    And I click on add more encrypted storage quota link on "replace file modal" and navigated to pricing checkout page
    And I clicks "modify plan" on "you already have an existing subscription" modal
    And I click on button "logout"

  Scenario Outline: Non admin DR owner should see a error message when they try to replace file if encrypted storage quota is fully consumed
    And I login as "<userPlanType>"
    When I navigate to "ManageDataRoom" page from home page
    And I view the existing data room
    And I select the file
    And I select "replace file" option from More floating menu in DR
    And I upload a "PdfFile.pdf" file in replace new version page when encrypted storage is fully consumed
    Then I validate the encrypted storage quota exceeded error message on "replace file modal" for non admin
    And I logout from data room
    Examples:
      | userPlanType                     |
      | encryptedStorageFullTeamNonAdmin |
      | encryptedStorageFullProNonAdmin  |

  Scenario Outline: Team admin DR owner should see a paywall modal when they try to replace file from version history if encrypted storage quota is fully consumed
    And I login as "<userPlanType>"
    When I navigate to "ManageDataRoom" page from home page
    And I view the existing data room
    And I select the file
    And I select "version history for existing file" from floating menu in DR
    And I click on replace file button on version history page
    Then System should show "no encrypted storage left" modal for admin
    And I click on "add quota" button and navigates to pricing page
    And I clicks "modify plan" on "you already have an existing subscription" modal
    And I click on button "logout"
    Examples:
      | userPlanType                      |
      | encryptedStorageFullProPlanAdmin  |
      | encryptedStorageFullTeamPlanAdmin |

  Scenario Outline: Non admin DR owners should see a error message modal when they try to replace file from version history if encrypted storage quota is fully consumed
    And I login as "<userPlanType>"
    When I navigate to "ManageDataRoom" page from home page
    And I view the existing data room
    And I select the file
    And I select "version history for existing file" from floating menu in DR
    And I click on replace file button on version history page
    Then System should show "no encrypted storage left" modal for the non admin
    And I close the modal
    And I logout from data room
    Examples:
      | userPlanType                     |
      | encryptedStorageFullTeamNonAdmin |
      | encryptedStorageFullProNonAdmin  |

  Scenario Outline: Team admin DR owners should see a paywall modal when they try to restore a file from version history if encrypted storage quota is fully consumed
    And I login as "<userPlanType>"
    When I navigate to "ManageDataRoom" page from home page
    And I view the existing data room
    And I select the file
    And I select "version history for existing file" from floating menu in DR
    And I click on restore button
    Then System should show "no encrypted storage left" modal for admin
    And I click on "add quota" button and navigates to pricing page
    And I clicks "modify plan" on "you already have an existing subscription" modal
    And I click on button "logout"
    Examples:
      | userPlanType                      |
      | encryptedStorageFullProPlanAdmin  |
      | encryptedStorageFullTeamPlanAdmin |

  Scenario Outline: Non admin DR owners should see a error message modal when they try to restore a file from version history if encrypted storage quota is fully consumed
    And I login as "<userPlanType>"
    When I navigate to "ManageDataRoom" page from home page
    And I view the existing data room
    And I select the file
    And I select "version history for existing file" from floating menu in DR
    And I click on restore button
    Then System should show "no encrypted storage left" modal for the non admin
    And I close the modal
    And I logout from data room
    Examples:
      | userPlanType                     |
      | encryptedStorageFullTeamNonAdmin |
      | encryptedStorageFullProNonAdmin  |

  @ignore #Bug-PROD-9247 is raised for this scenario
  Scenario Outline: Data room owners who is a team admin should see a paywall when they try to copy file across the DR if encrypted storage quota is fully consumed
    And I login as "<userPlanType>"
    When I navigate to "ManageDataRoom" page from home page
    And I view the existing data room
    Then I select the file and select move and copy option and copy the file in data room
    Then I validate the encrypted storage quota exceeded error message on "copy file modal" for team admin
    And I click on add more encrypted storage quota link on "copy file modal" and navigated to pricing checkout page
    And I clicks "modify plan" on "you already have an existing subscription" modal
    And I click on button "logout"
    Examples:
      | userPlanType                      |
      | encryptedStorageFullProPlanAdmin  |
      | encryptedStorageFullTeamPlanAdmin |

  Scenario Outline: Data room owners who is a non admin should see a error message when they try to copy file across the DR if encrypted storage quota is fully consumed
    And I login as "<userPlanType>"
    When I navigate to "ManageDataRoom" page from home page
    And I view the existing data room
    Then I select the file and select move and copy option and copy the file in data room
    Then I validate the encrypted storage quota exceeded error message on "copy file modal" for non admin
    And I logout from data room
    Examples:
      | userPlanType                     |
      | encryptedStorageFullTeamNonAdmin |
      | encryptedStorageFullProNonAdmin  |

  Scenario Outline: Add a guest through bulk invite in the existing DR when there is not enough guest quota, owner should see add quota paywall
    And I login as "<userPlanType>"
    When I navigate to "ManageDataRoom" page from home page
    And I view the existing data room
    And I navigate to "guests" tab in data room "guests" page
    Then I added same 2 guest again from file "BulkEmail.xlsx" which were already added in DR
    And I click on add and send notification button
    Then System should show "not enough dr guest quota" paywall for team admin
    And I click on "add data room guest quota" button and navigates to pricing page
    And I clicks "modify plan" on "you already have an existing subscription" modal
    And I click on button "logout"
    Examples:
      | userPlanType             |
      | storageFullTeamPlanAdmin |
      | storageFullProPlanAdmin  |