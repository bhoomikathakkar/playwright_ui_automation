@dataRoomAdditionalInfo @regression @dataRoom
Feature: Verify data room require additional information feature

  Background: Navigate to Digify login page
    Given I am on the login page

  @fileViewer
  Scenario Outline: Create a data room with required additional info and access DR file as guest
    When I login as "<userPlanType>"
    And I create a data room with feature "<featureType>"
    And I validate and continue require additional info modal as data room owner
    And I upload a "ImageFile.png" file in DR
    And I store the DR file link in memory
    And I navigate to data room "guests" page
    And I add "recipientUser" guest with permission as "Download (PDF)" in data room
    And I navigate to data room "settings" page
    Then I validate "<featureType>" in data room
    And I logout from data room
    And I login as "recipientUser" and access the same data room file link
    And Verified the email field is disabled on the Request Additional Info form in File Viewer
    And Entered "<reqFeature>" in the Request Additional Info form in File Viewer
    And I wait until file is loaded in file viewer
    And I logout from data room
    And I login as "<userPlanType>" and access the same data room file link
    And I click on folder tree icon in file viewer and select "data room"
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      | featureType                                                                                                | reqFeature              |
      | proPlanAdmin      | Only people I specify,req additional info,rai->name,rai->phone                                             | rai->name,rai->phone    |
      | teamPlanAdmin     | Anyone with the link or file (email verification),dynamic watermark,req additional info,rai->name,rai->job | rai->name, rai->job     |
      | businessPlanAdmin | Only people from domains I specify,req additional info,rai->company,rai->phone                             | rai->company,rai->phone |


  Scenario Outline: Create a data room with required additional info and access DR as guest
    When I login as "<userPlanType>"
    And I create a data room with feature "<featureType>"
    And I validate and continue require additional info modal as data room owner
    And I upload a "ImageFile.png" file in DR
    And I store the data room link in memory
    And I navigate to data room "guests" page
    And I add "recipientUser" guest with permission as "View" in data room
    And I navigate to data room "settings" page
    Then I validate "<featureType>" in data room
    And I logout from data room
    And I login as "recipientUser" and access the data room using direct link
    And Verified the email field is disabled on the Request Additional Info form in File Viewer
    And Entered "<reqFeature>" in the Request Additional Info form in File Viewer
    And I logout from data room
    And I login as "<userPlanType>" and access the data room using direct link
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      | featureType                                                                                                | reqFeature                        |
      | proPlanAdmin      | Only people from domains I specify,req additional info,rai->name,rai->phone                                | rai->name,rai->phone              |
      | teamPlanAdmin     | Anyone with the link or file (email verification),dynamic watermark,req additional info,rai->name,rai->job | rai->name, rai->job               |
      | businessPlanAdmin | Only people I specify,req additional info,rai->company,rai->phone,rai->name                                | rai->company,rai->phone,rai->name |


  Scenario Outline: Create a data room with required additional info and access DR folder as guest
    When I login as "<userPlanType>"
    And I create a data room with feature "<featureType>"
    And I validate and continue require additional info modal as data room owner
    And I create a folder when ms editing is enabled
    And I store the DR folder link in memory
    And I navigate to data room "guests" page
    And I add "recipientUser" guest with permission as "Print" in data room
    And I navigate to data room "settings" page
    Then I validate "<featureType>" in data room
    And I logout from data room
    And I login as "recipientUser" and access the same data room folder link
    And Verified the email field is disabled on the Request Additional Info form in File Viewer
    And Entered "<reqFeature>" in the Request Additional Info form in File Viewer
    And I logout from data room
    And I login as "<userPlanType>" and access the same data room folder link
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      | featureType                                                                 | reqFeature                        |
      | teamPlanAdmin     | Only people from domains I specify,req additional info,rai->name,rai->phone | rai->name,rai->phone              |
      | businessPlanAdmin | Only people I specify,req additional info,rai->company,rai->phone,rai->name | rai->company,rai->phone,rai->name |


  Scenario Outline: Create a data room with required additional info and access DR folder as guest for pro plan user
    When I login as "proPlanAdmin"
    And I create a data room with feature "<featureType>"
    And I validate and continue require additional info modal as data room owner
    And I create a folder
    And I store the DR folder link in memory
    And I navigate to data room "guests" page
    And I add "recipientUser" guest with permission as "Print" in data room
    And I navigate to data room "settings" page
    Then I validate "<featureType>" in data room
    And I logout from data room
    And I login as "recipientUser" and access the same data room folder link
    And Verified the email field is disabled on the Request Additional Info form in File Viewer
    And Entered "<reqFeature>" in the Request Additional Info form in File Viewer
    And I logout from data room
    And I login as "proPlanAdmin" and access the same data room folder link
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | featureType                                                                              | reqFeature         |
      | Anyone with the link or file (email verification),req additional info,rai->name,rai->job | rai->name,rai->job |

  @fileViewer
  Scenario Outline: Create a data room with required additional info + TOA  and access DR file as guest
    When I login as "<userPlanType>"
    And I create a data room with feature "<featureType>"
    And I validate TOA, continue and agree TOA as "dr owner"
    And I validate and continue require additional info modal as data room owner
    And I upload a "ImageFile.png" file in DR
    And I store the DR file link in memory
    And I navigate to data room "guests" page
    And I add "recipientUser" guest with permission as "Download (PDF)" in data room
    And I navigate to data room "settings" page
    Then I validate "<validateFeatureType>" in data room
    And I validate "Use organization terms" option in TOA dropdown
    And I logout from data room
    And I login as "recipientUser" and access the same data room file link
    And I agree TOA and continue as "guest"
    And Verified the email field is disabled on the Request Additional Info form in File Viewer
    And Entered "<reqFeature>" in the Request Additional Info form in File Viewer
    And I wait until file is loaded in file viewer
    And I logout from data room
    And I login as "<userPlanType>" and access the same data room file link
    And I wait until file is loaded in file viewer
    And I click on folder tree icon in file viewer and select "data room"
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      | featureType                                                                                                                       | validateFeatureType                                                                                        | reqFeature              |
      | proPlanAdmin      | Only people I specify,Use organization terms,req additional info,rai->name,rai->phone                                             | Only people I specify,req additional info,rai->name,rai->phone                                             | rai->name,rai->phone    |
      | teamPlanAdmin     | Anyone with the link or file (email verification),Use organization terms,dynamic watermark,req additional info,rai->name,rai->job | Anyone with the link or file (email verification),dynamic watermark,req additional info,rai->name,rai->job | rai->name,rai->job      |
      | businessPlanAdmin | Only people from domains I specify,Use organization terms,req additional info,rai->company,rai->phone                             | Only people from domains I specify,req additional info,rai->company,rai->phone                             | rai->company,rai->phone |

  Scenario Outline: Create a data room with required additional info + enforce email verification and access DR as guest
    When I login as "<userPlanType>"
    And I create a data room with feature "<featureType>"
    And I validate and continue enforce email verification modal
    And I validate and continue require additional info modal as data room owner
    And I upload a "ImageFile.png" file in DR
    And I store the data room link in memory
    And I navigate to data room "guests" page
    And I add "<maildropUser>" guest with permission as "Download (PDF)" in data room
    And I navigate to data room "settings" page
    Then I validate "<featureType>" in data room
    And I logout from data room
    And I login as "<maildropUser>" and access the data room using direct link
    And I validate the enforce email verification modal as guest
    And I access the data room as "<maildropUser>" with OTP
    And Verified the email field is disabled on the Request Additional Info form in File Viewer
    And Entered "<reqFeature>" in the Request Additional Info form in File Viewer
    And I logout from data room
    And I login as "<userPlanType>" and access the data room using direct link
    And  I validate and continue enforce email verification modal
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      | featureType                                                                                                                           | reqFeature              | maildropUser         |
      | proPlanAdmin      | Only people I specify,enforce email verification,req additional info,rai->name,rai->phone                                             | rai->name,rai->phone    | maildropProUser      |
      | teamPlanAdmin     | Anyone with the link or file (email verification),enforce email verification,dynamic watermark,req additional info,rai->name,rai->job | rai->name,rai->job      | maildropTeamUser     |
      | businessPlanAdmin | Only people from domains I specify,enforce email verification,req additional info,rai->company,rai->phone                             | rai->company,rai->phone | maildropBusinessUser |

  @fileViewer
  Scenario Outline: Edit DR and disable RAI then access DR file as guest
    When I login as "<userPlanType>"
    And I create a data room with feature "<featureType>"
    And I validate and continue require additional info modal as data room owner
    And I upload a "ImageFile.png" file in DR
    And I store the DR file link in memory
    And I navigate to data room "guests" page
    And I add "recipientUser" guest with permission as "Download (PDF)" in data room
    And I navigate to data room "settings" page
    Then I validate "<featureType>" in data room
    And I updated feature to "disable->rai" in DR Settings
    And I save the data room settings
    And I logout from data room
    And I login as "recipientUser" and access the same data room file link
    And I wait until file is loaded in file viewer
    And I logout from data room
    And I login as "<userPlanType>" and access the same data room file link
    And I wait until file is loaded in file viewer
    Then I click on folder tree icon in file viewer and select "data room"
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      | featureType                                                                                                |
      | proPlanAdmin      | Only people I specify,req additional info,rai->name,rai->phone                                             |
      | teamPlanAdmin     | Anyone with the link or file (email verification),dynamic watermark,req additional info,rai->name,rai->job |
      | businessPlanAdmin | Only people I specify,req additional info,rai->company,rai->phone                                          |

  Scenario Outline: Verify the clone DR with required additional info enable
    When I login as "<userPlanType>"
    And I create a data room with feature "<featureType>"
    And I validate and continue require additional info modal as data room owner
    And I "clone" data room from advanced settings
    Then I verify the required additional info not checked
    And I create cloned data room with default settings
    And I select "Date (Newest first)" from the sort by filter in Manage data room
    And I validate cloned data room in manage data room and opened it
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      | featureType                                                                                                |
      | proPlanAdmin      | Only people I specify,req additional info,rai->name,rai->phone                                             |
      | teamPlanAdmin     | Anyone with the link or file (email verification),dynamic watermark,req additional info,rai->name,rai->job |
      | businessPlanAdmin | Only people from domains I specify,req additional info,rai->company,rai->phone                             |
