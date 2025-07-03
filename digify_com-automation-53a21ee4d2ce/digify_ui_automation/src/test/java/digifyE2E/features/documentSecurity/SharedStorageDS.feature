@sharedStorageDS @regression @documentSecurity

Feature: Verify the scenarios when document security storage is fully consumed

  Background: Navigate to Digify login page
    Given I am on the login page

  @ignore  #credit reset every month so not applicable
  Scenario Outline: Send a file when there is no credits left, sender should see add credits paywall
    And I login as "<userPlanType>"
    When I navigate to "SendFiles" page from home page
    Then System should show "no credits left to send file" paywall for team admin
    And I click on "add document security credits" button and navigates to pricing page
    And I click on button "logout"
    Examples:
      | userPlanType             |
      | storageFullTeamPlanAdmin |
      | storageFullProPlanAdmin  |

  @ignore  #credit reset every month so not applicable
  Scenario Outline:Add a recipient to an existing file when there is no credits left, sender should see insufficient credits modal
    And I login as "<userPlanType>"
    When I navigate to "ManageSentFiles" page from home page
    And I select the first file checkbox on the page
    Then I select "recipient" from the floating menu
    And I add a new recipient "recipientUser1" in manage sent file when credits are fully consumed
    Then System should show "insufficient credits for adding one new recipient" modal for admin
    And I logout from the application
    Examples:
      | userPlanType             |
      | storageFullTeamPlanAdmin |
      | storageFullProPlanAdmin  |

  @ignore  #credit reset every month so not applicable
  Scenario Outline: Replace file an existing file when there is no credits left, sender should get a paywall
    And I login as "<userPlanType>"
    When I navigate to "ManageSentFiles" page from home page
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "replace file"
    Then System should show "no credits left to replace file" paywall for team admin
    And I click on "add credits" button and navigates to pricing page
    And I click on button "logout"
    Examples:
      | userPlanType             |
      | storageFullTeamPlanAdmin |
      | storageFullProPlanAdmin  |

  @ignore  #credit reset every month so not applicable
  Scenario Outline: Replace file in version history for an existing file when there is no credits left, sender should get a paywall
    And I login as "<userPlanType>"
    When I navigate to "ManageSentFiles" page from home page
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "version history"
    And I click on replace file button and navigates to replace new version for file page when there is no credits
    Then System should show "no credits left to replace file" paywall for team admin
    And I click on "add credits" button and navigates to pricing page
    And I click on button "logout"
    Examples:
      | userPlanType             |
      | storageFullTeamPlanAdmin |
      | storageFullProPlanAdmin  |

  @ignore  #credit reset every month so not applicable
  Scenario Outline: Navigate to send files page as a team member when there is no credits, owner should see contact admin paywall
    And I login as "<userPlanType>"
    When I navigate to "SendFiles" page from home page
    Then System should show "no credits left to send file" paywall for the team user
    And I logout from the application
    Examples:
      | userPlanType        |
      | storageFullProUser  |
      | storageFullTeamUser |

  Scenario Outline: Navigate to send files page as an admin when encrypted storage is fully consumed, admin should see add quota paywall
    And I login as "<userPlanType>"
    When I navigate to "SendFiles" page from home page
    Then System should show "no encrypted storage left" paywall for team admin
    And I click on "add quota" button and navigates to pricing page
    And I clicks "modify plan" on "you already have an existing subscription" modal
    And I click on button "logout"
    Examples:
      | userPlanType                      |
      | encryptedStorageFullProPlanAdmin  |
      | encryptedStorageFullTeamPlanAdmin |

  Scenario Outline: Navigate to send files page as non-admin when encrypted storage is fully consumed, non-admin should see error screen
    And I login as "<userPlanType>"
    When I navigate to "SendFiles" page from home page
    Then System should show "no encrypted storage left" paywall for the team user
    And I logout from the application
    Examples:
      | userPlanType                     |
      | encryptedStorageFullProNonAdmin  |
      | encryptedStorageFullTeamNonAdmin |

  Scenario Outline: Admin replace an existing file when encrypted storage is fully consumed, admin should see a paywall
    And I login as "<userPlanType>"
    When I navigate to "ManageSentFiles" page from home page
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "replace file"
    Then System should show "no encrypted storage left" modal for admin
    And I click on "add quota" button and navigates to pricing page
    And I clicks "modify plan" on "you already have an existing subscription" modal
    And I click on button "logout"
    Examples:
      | userPlanType                      |
      | encryptedStorageFullProPlanAdmin  |
      | encryptedStorageFullTeamPlanAdmin |

  Scenario Outline: Non-admin replace an existing file when encrypted storage is fully consumed, non-admin should see a modal
    And I login as "<userPlanType>"
    When I navigate to "ManageSentFiles" page from home page
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "replace file"
    Then System should show "no encrypted storage left" modal for the non admin
    And I close the modal
    And I logout from the application
    Examples:
      | userPlanType                     |
      | encryptedStorageFullTeamNonAdmin |
      | encryptedStorageFullProNonAdmin  |

  Scenario Outline: Admin replace an existing file from version history when encrypted storage is fully consumed, admin should see a paywall
    And I login as "<userPlanType>"
    When I navigate to "ManageSentFiles" page from home page
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "version history"
    And I click on replace file button on version history page
    Then System should show "no encrypted storage left" modal for admin
    And I click on "add quota" button and navigates to pricing page
    And I click on button "logout"
    Examples:
      | userPlanType                      |
      | encryptedStorageFullProPlanAdmin  |
      | encryptedStorageFullTeamPlanAdmin |

  Scenario Outline: Non-admin replace an existing file from version history when encrypted storage is fully consumed, admin should see a paywall
    And I login as "<userPlanType>"
    When I navigate to "ManageSentFiles" page from home page
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "version history"
    And I click on replace file button on version history page
    Then System should show "no encrypted storage left" modal for the non admin
    And I close the modal
    And I logout from the application
    Examples:
      | userPlanType                     |
      | encryptedStorageFullTeamNonAdmin |
      | encryptedStorageFullProNonAdmin  |

  Scenario Outline: Admin restores an existing file version when encrypted storage is fully consumed, admin should see a paywall
    And I login as "<userPlanType>"
    When I navigate to "ManageSentFiles" page from home page
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "version history"
    And I click on restore button
    Then System should show "no encrypted storage left" modal for admin
    And I click on "add quota" button and navigates to pricing page
    And I click on button "logout"
    Examples:
      | userPlanType                      |
      | encryptedStorageFullProPlanAdmin  |
      | encryptedStorageFullTeamPlanAdmin |

  Scenario Outline: Non-admin restores an existing file version when encrypted storage is fully consumed, non-admin should only see got it button
    And I login as "<userPlanType>"
    When I navigate to "ManageSentFiles" page from home page
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "version history"
    And I click on restore button
    Then System should show "no encrypted storage left" modal for the non admin
    And I close the modal
    And I logout from the application
    Examples:
      | userPlanType                     |
      | encryptedStorageFullTeamNonAdmin |
      | encryptedStorageFullProNonAdmin  |