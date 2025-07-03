@disbandRestoreTeam @regression @adminSettings
Feature: Disband and Restore Team

  Background: Navigate to Digify login page
    Given I am on the login page

  Scenario Outline: Verify that user should able to disband and restore team with 0 members
    When I login as "<userType>"
    And I navigate to "admin" tab in "Admin settings" page
    And I expand "disband team" in admin tab
    And I click on "disband" button in section
    Then I select "disband and delete team in 30 days" option from disband team modal for user "<userType>"
    And I click on "disband and delete team" button in modal
    And I validated "Member account schedule for deletion" modal on login screen for user "<userType>"
    When I login as "<userType>"
    Then I validated "Restore team" modal on login screen for user "<userType>"
    And I validated "Confirm account restoration" modal on login screen for user "<userType>"
    And I confirm team restoration from email as "<mailDropUser>" user
    And I login as "<userType>" and validate home page after team restore
    And I logout from the application
    Examples:
      | userType        | mailDropUser   |
      | proDisbandUser  | proplandisband |
      | teamDisbandUser | teamdisband    |

  Scenario Outline: When the admin disbands the team, ensure team members can find the account schedule for deletion screen and access their accounts after the admin restores the team.
    When I login as "<userType>"
    And I navigate to "admin" tab in "Admin settings" page
    And I expand "disband team" in admin tab
    And I click on "disband" button in section
    When I select "disband and delete team in 30 days" option from disband team modal for user "<userType>"
    And I click on "disband and delete team" button in modal
    Then I validated "Member account schedule for deletion" modal on login screen for user "<userType>"
    And I login as "<memberType>"
    And I validated "account schedule for deletion by team admin" modal on login screen for user "<memberType>"
    When I login as "<userType>"
    And I validated "Restore team" modal on login screen for user "<userType>"
    And I validated "Confirm account restoration" modal on login screen for user "<userType>"
    And I confirm team restoration from email as "<mailDropUser>" user
    And I login as "<userType>" and validate home page after team restore
    And I logout from the application
    Then I login as "<memberType>"
    And I logout from the application
    Examples:
      | userType                 | mailDropUser            | memberType                |
      | proWithTeamDisbandAdmin  | proautowithteamdisband  | proWithTeamDisbandMember  |
      | teamWithTeamDisbandAdmin | teamautowithteamdisband | teamWithTeamDisbandMember |

  Scenario Outline: Verify that Disband Team Now option is pre-selected in the disband team modal for admin users.
    When I login as "<userType>"
    And I navigate to "admin" tab in "Admin settings" page
    And I expand "disband team" in admin tab
    And I click on "disband" button in section
    Then I validated "disband team now" option from disband team modal for user "<userType>"
    And I dismiss the disband team modal
    And I logout from the application
    Examples:
      | userType                 |
      | proDisbandUser           |
      | teamDisbandUser          |
      | proWithTeamDisbandAdmin  |
      | teamWithTeamDisbandAdmin |

  Scenario Outline:After the team disbands or restores, ensure that team members find the upgrade account paywall in DS and the subscription ends in the existing DR, given that the user has sent files and DR before.
    When I login as "<userType>"
    And I navigate to "admin" tab in "Admin settings" page
    And I expand "disband team" in admin tab
    And I click on "disband" button in section
    When I select "disband and delete team in 30 days" option from disband team modal for user "<userType>"
    And I click on "disband and delete team" button in modal
    Then I validated "Member account schedule for deletion" modal on login screen for user "<userType>"
    And I login as "<memberType>"
    And I validated "account schedule for deletion by team admin" modal on login screen for user "<memberType>"
    When I login as "<userType>"
    And I validated "Restore team" modal on login screen for user "<userType>"
    And I validated "Confirm account restoration" modal on login screen for user "<userType>"
    And I confirm team restoration from email as "<mailDropUser>" user
    Then I login as "<memberType>"
    And  I navigate to Manage Sent File page
    And I validated "contact admin to upgrade account" paywall on manage sent file page
    And I navigate to "ManageDataRoom" page from home page
    And I view the existing data room
    And I validated "subscription ended" paywall in data room
    And I validated "SUB_EXP" error page in data room
    And I logout from the application
    Examples:
      | userType                     | mailDropUser                   | memberType                    |
      | proWithTeamDataDisbandAdmin  | proauto_with_teamdata_disband  | proWithTeamDataDisbandMember  |
      | teamWithTeamDataDisbandAdmin | teamauto_with_teamdata_disband | teamWithTeamDataDisbandMember |

  Scenario Outline:After the team disbands or restores, ensure that admin find the upgrade account paywall in DS and in manage DR, given that the user has sent files and DR before.
    When I login as "<userType>"
    And I navigate to "admin" tab in "Admin settings" page
    And I expand "disband team" in admin tab
    And I click on "disband" button in section
    When I select "disband and delete team in 30 days" option from disband team modal for user "<userType>"
    And I click on "disband and delete team" button in modal
    Then I validated "Member account schedule for deletion" modal on login screen for user "<userType>"
    And I login as "<memberType>"
    And I validated "account schedule for deletion by team admin" modal on login screen for user "<memberType>"
    When I login as "<userType>"
    And I validated "Restore team" modal on login screen for user "<userType>"
    And I validated "Confirm account restoration" modal on login screen for user "<userType>"
    And I confirm team restoration from email as "<mailDropUser>" user
    And I login as "<userType>" and validate home page after team restore
    Then I navigate to Manage Sent File page
    And I validated "upgrade account" paywall on manage sent file page
    And I navigate to "ManageDataRoom" page from home page
    And I view the existing data room
    And I validated "subscription ended upgrade" paywall in data room
    And I validated "SUB_EXP" error page in data room
    And I logout from the application
    Examples:
      | userType                 | mailDropUser                    | memberType                     |
      | teamWithAdminDataDisband | teamauto_with_admindata_disband | teamWithAdminDataDisbandMember |

  Scenario: After the team disbands or restores, ensure that team members find the contact your administrator paywall in DS and in manage DR, given that the user has sent files and DR before.
    When I login as "teamWithAdminDataDisband"
    And I navigate to "admin" tab in "Admin settings" page
    And I expand "disband team" in admin tab
    And I click on "disband" button in section
    When I select "disband and delete team in 30 days" option from disband team modal for user "teamWithAdminDataDisband"
    And I click on "disband and delete team" button in modal
    Then I validated "Member account schedule for deletion" modal on login screen for user "teamWithAdminDataDisband"
    And I login as "teamWithAdminDataDisbandMember"
    And I validated "account schedule for deletion by team admin" modal on login screen for user "teamWithAdminDataDisbandMember"
    When I login as "teamWithAdminDataDisband"
    And I validated "Restore team" modal on login screen for user "teamWithAdminDataDisband"
    And I validated "Confirm account restoration" modal on login screen for user "teamWithAdminDataDisband"
    And I confirm team restoration from email as "teamauto_with_admindata_disband" user
    And I login as "teamWithAdminDataDisbandMember"
    Then I navigate to Manage Sent File page
    And I validated "contact your administrator to upgrade plan" paywall on manage sent file page
    And I navigate to "ManageDataRoom" page from home page
    And I validated "contact your administrator to upgrade plan" paywall in data room
    And I logout from the application
