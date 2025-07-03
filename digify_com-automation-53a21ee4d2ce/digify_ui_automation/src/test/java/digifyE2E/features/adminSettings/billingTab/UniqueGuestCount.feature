@uniqueGuestCount @regression @billingTab @adminSettings
Feature: Verify unique guest count on billing tab in admin settings page

  Background: Navigate to Digify login page
    Given I am on the login page

  Scenario Outline: Verify that unique guest count display same as number of guests in DR irrespective of guest permission
    When I login as "presetUser"
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    And I validate "Team" plan name in plan details and usage
    And I check "UserWithNoGuest" unique guest quota
    And I validate empty guest list page
    And I create a data room with "dynamic watermark" enabled
    And I navigate to data room "guests" page
    And I add "recipientUser" guest with permission as "<guestPermission>" in data room
    And I click on the brand logo and navigated to home page
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    And I check "<guestQuotaInfo>" unique guest quota
    And I validate guest list on unique guest page for added "Guest" "recipientUser"
    And I navigate to manage data room page
    And I delete all data rooms from manage data room page
    And I logout from the application
    Examples:
      | guestPermission | guestQuotaInfo        |
      | Download (PDF)  | UserWith1Guest        |
      | No Access       | UserWithNoAccessGuest |


  Scenario: Verify that unique guest count shouldn't update when existing team member added to DR
    When I login as "OwnerWith1Member"
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    And I validate "team" plan name in plan details and usage
    And I check "UserWithNoGuest" unique guest quota
    And I validate empty guest list page
    And I create a data room with "dynamic watermark" enabled
    And I navigate to data room "guests" page
    And I add "ExistingMember" guest with permission as "Edit" in data room
    And I click on the brand logo and navigated to home page
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    And I check "UserWith1MemberGuest" unique guest quota
    And I validate guest list on unique guest page for added "MemberGuest" "ExistingMember"
    And I navigate to manage data room page
    And I delete all data rooms from manage data room page
    And I logout from the application


  Scenario: Verify that unique guest count shouldn't update when same guest added to more than 1 DR
    When I login as "presetUser"
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    And I validate "team" plan name in plan details and usage
    And I check "UserWithNoGuest" unique guest quota
    And I validate empty guest list page
    And I create a data room with "Only people from domains I specify" enabled
    And I navigate to data room "guests" page
    And I add "recipientUser" guest with permission as "View" in data room
    And I click on the brand logo and navigated to home page
    And I create a data room with "dynamic watermark" enabled
    And I navigate to data room "guests" page
    And I add "recipientUser" guest with permission as "Download (PDF)" in data room
    And I click on the brand logo and navigated to home page
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    And I check "UserWithSameGuestInManyDR" unique guest quota
    And I validate guest list on unique guest page for added "Guests" "recipientUser"
    And I navigate to manage data room page
    And I delete all data rooms from manage data room page
    And I logout from the application

  Scenario: Verify that DR guest shouldn't remove from unique guest list if user unshared data room
    When I login as "presetUser"
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    And I validate "team" plan name in plan details and usage
    And I check "UserWithNoGuest" unique guest quota
    And I validate empty guest list page
    And I create a data room with "dynamic watermark" enabled
    And I navigate to data room "guests" page
    And I add "recipientUser" guest with permission as "View" in data room
    Then I navigate to manage data room
    And I unshared data room
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    And I check "UserWithUnsharedDRGuest" unique guest quota
    And I validate guest list on unique guest page for added "Guest" "recipientUser"
    And I navigate to manage data room page
    And I delete all data rooms from manage data room page
    And I logout from the application


  Scenario: Verify the view access modal on unique guest list page
    When I login as "presetUser"
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    And I validate "team" plan name in plan details and usage
    And I check "UserWithNoGuest" unique guest quota
    And I validate empty guest list page
    And I create a data room with "screen shield-->enable for all" enabled
    And I navigate to data room "guests" page
    And I add "recipientUser" guest with permission as "View" in data room
    And I click on the brand logo and navigated to home page
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    And I check "UserWith1Guest" unique guest quota
    And I validate guest list on unique guest page for added "Guest" "recipientUser"
    And I validate view access modal
    And I navigate to manage data room page
    And I delete all data rooms from manage data room page
    And I logout from the application


  Scenario: Verify that unique guest quota should update when user remove guest from DR
    When I login as "presetUser"
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    And I validate "team" plan name in plan details and usage
    And I check "UserWithNoGuest" unique guest quota
    And I validate empty guest list page
    And I create a data room with "dynamic watermark" enabled
    And I navigate to data room "guests" page
    And I add "recipientUser" guest with permission as "View" in data room
    And I click on the brand logo and navigated to home page
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    And I check "UserWith1Guest" unique guest quota
    And I validate guest list on unique guest page for added "Guest" "recipientUser"
    And I navigate to manage data room page
    And I search the data room
    And I click on the first searched data room
    And I navigate to data room "guests" page
    And I select the guest and click on "Remove guest" from the floating menu
    And I close the current tab
    Then I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    And I check "UserWithNoGuest" unique guest quota
    And I validate empty guest list page
    And I navigate to manage data room page
    And I delete all data rooms from manage data room page
    And I logout from the application

  @fixLogic @ignore
  Scenario:Validate guest count updates and quota errors on member removal without transfer and restoration.
    When I login as "autoUniqueAdmin"
    And I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    And I check "UserWithFullQuota" unique guest quota
    And I navigate to "members" tab in "Admin settings" page
    And I click on "remove member" for the "autoUniqueMember" team member
    And I validate remove member "autoUniqueMember" modal
    And I select "Remove and delete in 30 days,don't transfer" for the "autoUniqueMember" in remove member modal and remove member
    And I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    Then I check "scheduleDeletionMem" unique guest quota
    And I navigate to "ManageDataRoom" page from home page
    And I view the existing data room
    And I navigate to "guests" tab in data room "guests" page
    And I add new guest "recipientUser" email in guest page
    And I click on add and send notification button
    And I click on back button from add new guest page
    And I click on the brand logo and navigated to home page
    And I navigate to "billing" tab in "Admin settings" page
    And I expand "plan details and usage" in billing tab
    And I check "UserWithFullQuota" unique guest quota
    And I navigate to "members" tab in "Admin settings" page
    And I click on "restore account" for the "autoUniqueMember" team member
    Then System should show no dr guest quota left paywall
    And I navigate to "ManageDataRoom" page from home page
    And I view the existing data room
    And I navigate to data room "guests" page
    And I remove the guest "recipientUser" from guest list
    And I click on the brand logo and navigated to home page
    And I navigate to "members" tab in "Admin settings" page
    And I click on "restore account" for the "autoUniqueMember" team member
    And I navigate to "billing" tab from members tab in admin settings
    And  I expand "plan details and usage" in billing tab
    And I check "UserWithFullQuota" unique guest quota
    And I logout from the application