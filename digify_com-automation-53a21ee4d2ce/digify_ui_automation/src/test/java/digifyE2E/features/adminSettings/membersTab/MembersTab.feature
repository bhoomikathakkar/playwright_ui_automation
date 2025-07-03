@membersTab @regression @adminSettings
Feature: Add and remove team members from admin settings

  Background: Navigate to Digify login page
    Given I am on the login page

    @focus
  Scenario Outline: Add and remove team members from admin settings members tab
    When I login as "adminWith10MemberQuota"
    Then I navigate to "members" tab in "Admin settings" page
    And I validated available member licence as per userType "adminWith10MemberQuota"
    And I added member <memberType> to team : <userType>
    And I click on "Invite" button in section
    Then I validate "new member warning" modal on admin settings page and close the modal
    And I validated added member details as per <permissionType>
    And I "Cancel" invite
    And I validate "cancel invite" modal on admin settings page and confirm cancel
    And I logout from the application
    Examples:
      | memberType          | permissionType    | userType     |
      | "Restricted User"   | "Restricted User" | "New Member" |
      | "User  Recommended" | "User"            | "New Member" |
      | "Admin"             | "Admin"           | "New Member" |


  Scenario Outline: Verify that user is not able to add other team member
    When I login as "adminWith10MemberQuota"
    Then I navigate to "members" tab in "Admin settings" page
    And I validated available member licence as per userType "adminWith10MemberQuota"
    And I added member <memberType> to team : <userType>
    And I click on "Invite" button in section
    Then I validate "cannot invite member" modal on admin settings page
    And I logout from the application
    Examples:
      | memberType          | userType          |
      | "Restricted User"   | "Existing Member" |
      | "User  Recommended" | "Existing Member" |
      | "Admin"             | "Existing Member" |


  Scenario Outline: Verify that user is not able to add member when user quota full
    When I login as "adminWithMemberQuotaFull"
    Then I navigate to "members" tab in "Admin settings" page
    And I validated available member licence as per userType "adminWithMemberQuotaFull"
    When I added member <memberType> to team : <userType>
    And I click on "Invite" button in section
    Then I validate "user quota full" modal on admin settings page
    And I logout from the application
    Examples:
      | memberType          | userType     |
      | "Restricted User"   | "New Member" |
      | "User  Recommended" | "New Member" |
      | "Admin"             | "New Member" |


  Scenario Outline: Verify that user is able to edit access of existing team member
    When I login as "OwnerWith1Member"
    Then I navigate to "members" tab in "Admin settings" page
    And I click on "manage member" for the "ExistingMember" team member
    And I update role to <userRole> for "ExistingMember" team member
    Then I validate updated role <userRole> in user list for team member
    And I logout from the application
    And I am on the login page
    And I login as "ExistingMember"
    And I validate document security pages for <userRole>
    And I validate data room pages for <userRole>
    And I logout from the application
    Examples:
      | userRole            |
      | "Restricted User"   |
      | "User  Recommended" |
      | "Admin"             |