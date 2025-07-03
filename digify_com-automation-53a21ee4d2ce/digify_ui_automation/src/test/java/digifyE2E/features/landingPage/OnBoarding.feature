@onBoarding @regression
Feature: Verify on-boarding flow for different user types

  Background: Navigate to Digify login page
    Given I am on the login page

  Scenario Outline: Verify user can close the intercom quest modal
    Given I login as "<userPlanType>"
    And User validate quest banner on home page
    When User access view quest
    And Team Admin validates quest challenges when all are already completed
    Then I close quest
    And I logout from the application
    Examples:
      | userPlanType          |
      | doneQuestTaskTrialAdm |
      | doneQuestTaskTeamAdm  |
      | doneQuestTaskProAdm   |
      | doneQuestTaskEntAdm   |
      | doneQuestTaskBussAdm  |

  Scenario Outline: Verify all Quest Challenges and exit the tutorial at step 4 for a team admin to prevent quest completion
    Given I login as "<userPlanType>"
    And User validate quest banner on home page
    When User access view quest
    And Team admin validate view quest tasks when 3 task are pending
    And Team member chooses Challenge 1: Send a file, and exit the tutorial at step 4
    And I navigate back to home page by clicking on home button
    And User access view quest
    Then Team member chooses Challenge 2: Create a data room, and exit the tutorial at step 4
    And I navigate back to home page by clicking on home button
    And User access view quest
    Then Team admin chooses Challenge 3: Set up your team, and exit the tutorial at step 4
    And I access home page
    When User access view quest
    And Team admin validate view quest tasks when 3 task are pending
    Then I close quest
    And I logout from the application
    Examples:
      | userPlanType            |
      | questNotStartedTeamAdm  |
      | questNotStartedProAdm   |
      | questNotStartedTrialAdm |
      | questNotStartedBussAdm  |
      | questNotStartedEntAdm   |

  Scenario Outline: Verify Quest Challenge 1 for Team Admin when tasks are already completed
    Given I login as "<userPlanType>"
    And User validate quest banner on home page
    When User access view quest
    And Team Admin validates quest challenges when all are already completed
    And Team admin chooses Challenge 1: Send a file, and completes the quest
    And I upload a file "PPSMTestFile.ppsm" in send files
    Then I enable dynamic watermark and add additional settings
      | date and time | ip address | color | position |
      | checked       | checked    | red   | Top left |
    And I add the recipient "recipientUser" and send the file using the "get link & skip notification" option
    * I copy the file link
    * I navigate to "ManageSentFiles" page from home page
    * I select "Date (Newest first)" from the sort by filter in Manage sent file
    * I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    * I logout from the application
    When I access the file link as existing user
    And I login in file viewer as "recipientUser"
    * I wait until file is loaded in file viewer
    Then I validate the file name
    And I logout from file viewer
    Examples:
      | userPlanType          |
      | doneQuestTaskTrialAdm |
      | doneQuestTaskTeamAdm  |
      | doneQuestTaskProAdm   |
      | doneQuestTaskEntAdm   |
      | doneQuestTaskBussAdm  |

  Scenario Outline: Verify Quest Challenge 2 for Team Admin when tasks are already completed
    Given I login as "<userPlanType>"
    And User validate quest banner on home page
    When User access view quest
    And Team Admin validates quest challenges when all are already completed
    Then Team admin chooses Challenge 2: Create a data room, and completes the quest
    And I input data room name
    And I enable "Download (Original)" in data room
    And I validate "Download (Original),qna toggle on,about page toggle on" in data room
    Then I click on create data room
    * I store the data room link in memory
    Then I validate "files,about,guests,analytics,settings" tabs are enabled
    And I upload a "PdfFile2KB.pdf" file in DR
    And I navigate to data room "settings" page
    Then I validate "Download (Original),qna toggle on,about page toggle on" in data room
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "View" in data room
    * I logout from data room
    When I login as "recipientUser" and access the same data room
    Then guest validate DR permission "view"
    * I logout from data room
    Examples:
      | userPlanType          |
      | doneQuestTaskTrialAdm |
      | doneQuestTaskTeamAdm  |
      | doneQuestTaskEntAdm   |
      | doneQuestTaskBussAdm  |

  Scenario: Verify Quest Challenge 2 for Pro Team Admin when tasks are already completed
    Given I login as "doneQuestTaskProAdm"
    And User validate quest banner on home page
    When User access view quest
    And Team Admin validates quest challenges when all are already completed
    Then Team admin chooses Challenge 2: Create a data room, and completes the quest
    And I input data room name
    And I enable "Download (Original)" in data room
    And I validate "Download (Original),about page toggle on" in data room
    Then I click on create data room
    And I store the data room link in memory
    Then I validate "files,about,guests,analytics,settings" tabs are enabled
    Then I upload a "PdfFile2KB.pdf" file in DR
    And I navigate to data room "settings" page
    And I validate "Download (Original),about page toggle on" in data room
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "Download (PDF)" in data room
    * I logout from data room
    When I login as "recipientUser" and access the same data room
    Then guest validate DR permission "download_pdf"
    * I logout from data room

  Scenario Outline: Verify Quest Challenge 3 for Team Admin when tasks are already completed
    Given I login as "<userPlanType>"
    When User access view quest
    And Team Admin validates quest challenges when all are already completed
    Then Team admin chooses Challenge 3: Set up your team, and completes the quest
    And I navigate to "branding" tab from members tab in admin settings
    When I expand "change logo" in branding tab
    Then I upload the "imgBrandLogo.png" as the branding logo and validate the update
    And I logout from the application
    Examples:
      | userPlanType          |
      | doneQuestTaskTrialAdm |
      | doneQuestTaskTeamAdm  |
      | doneQuestTaskProAdm   |
      | doneQuestTaskEntAdm   |
      | doneQuestTaskBussAdm  |

  Scenario Outline: Verify Quest Challenge 1 for Team Member when tasks are already completed
    Given I login as "<userPlanType>"
    And User validate quest banner on home page
    When User access view quest
    And Team member validate view quest tasks when all tasks are completed
    Then I validate that send file challenge and create dataroom challenge appear, and setup your team challenge does not appear for non admin user
    And Team member chooses Challenge 1: Send a file, and completes the quest
    And I upload a file "PPSMTestFile.ppsm" in send files
    Then I enable dynamic watermark and add additional settings
      | date and time | ip address | color | position |
      | checked       | checked    | red   | Top left |
    And I add the recipient "recipientUser" and send the file using the "get link & skip notification" option
    * I copy the file link
    * I navigate to "ManageSentFiles" page from home page
    * I select "Date (Newest first)" from the sort by filter in Manage sent file
    * I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    * I logout from the application
    When I access the file link as existing user
    And I login in file viewer as "recipientUser"
    * I wait until file is loaded in file viewer
    Then I validate the file name
    And I logout from file viewer
    Examples:
      | userPlanType          |
      | doneQuestTaskTrialMem |
      | doneQuestTaskTeamMem  |
      | doneQuestTaskProMem   |
      | doneQuestTaskEntMem   |
      | doneQuestTaskBussMem  |

  Scenario: Verify Quest Challenge 1 for Pro-Team Member when tasks are already completed
    Given I login as "doneQuestTaskProMem"
    And User validate quest banner on home page
    When User access view quest
    And Team member validate view quest tasks when all tasks are completed
    Then I validate that send file challenge and create dataroom challenge appear, and setup your team challenge does not appear for non admin user
    And Team member chooses Challenge 1: Send a file, and completes the quest
    And I upload a file "PPSMTestFile.ppsm" in send files
    Then I enable dynamic watermark and add additional settings
      | date and time | ip address | color | position |
      | checked       | checked    | red   | Top left |
    And I add the recipient "recipientUser" and send the file using the "get link & skip notification" option
    * I copy the file link
    * I navigate to "ManageSentFiles" page from home page
    * I select "Date (Newest first)" from the sort by filter in Manage sent file
    * I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    * I logout from the application
    When I access the file link as existing user
    And I login in file viewer as "recipientUser"
    * I wait until file is loaded in file viewer
    Then I validate the file name
    And I logout from file viewer

  Scenario Outline: Verify Quest Challenge 2 for Team Member when tasks are already completed
    Given I login as "<userPlanType>"
    And User validate quest banner on home page
    When User access view quest
    And Team member validate view quest tasks when all tasks are completed
    Then I validate that send file challenge and create dataroom challenge appear, and setup your team challenge does not appear for non admin user
    Then Team member chooses Challenge 2: Create a data room, and completes the quest
    And I input data room name
    And I enable "Download (Original)" in data room
    And I validate "Download (Original),qna toggle on,about page toggle on" in data room
    Then I click on create data room
    * I store the data room link in memory
    Then I validate "files,about,guests,analytics,settings" tabs are enabled
    And I upload a "PdfFile2KB.pdf" file in DR
    And I navigate to data room "settings" page
    Then I validate "Download (Original),qna toggle on,about page toggle on" in data room
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "View" in data room
    * I logout from data room
    When I login as "recipientUser" and access the same data room
    Then guest validate DR permission "view"
    * I logout from data room
    Examples:
      | userPlanType          |
      | doneQuestTaskTrialMem |
      | doneQuestTaskTeamMem  |
      | doneQuestTaskEntMem   |
      | doneQuestTaskBussMem  |

  Scenario: Verify Quest Challenge 2 for Pro Team Member when tasks are already completed
    Given I login as "doneQuestTaskProMem"
    And User validate quest banner on home page
    When User access view quest
    And Team member validate view quest tasks when all tasks are completed
    Then I validate that send file challenge and create dataroom challenge appear, and setup your team challenge does not appear for non admin user
    Then Team member chooses Challenge 2: Create a data room, and completes the quest
    And I input data room name
    And I enable "Download (Original)" in data room
    And I validate "Download (Original),about page toggle on" in data room
    Then I click on create data room
    * I store the data room link in memory
    Then I validate "files,about,guests,analytics,settings" tabs are enabled
    And I upload a "PdfFile2KB.pdf" file in DR
    And I navigate to data room "settings" page
    Then I validate "Download (Original),qna toggle on,about page toggle on" in data room
    And I navigate to data room "guests" page
    Then I add "recipientUser" guest with permission as "View" in data room
    * I logout from data room
    When I login as "recipientUser" and access the same data room
    Then guest validate DR permission "view"
    * I logout from data room

  Scenario Outline: Team Admin verify pending Quest Challenge 1 and visit all tutorial steps
    Given I login as "<userPlanType>"
    And User validate quest banner on home page
    When User access view quest
    And Team admin validate view quest tasks when 1 of 3 task are done
    Then Team admin chooses Challenge 1: Send a file, but not completes the quest
    And I access home page
    When User access view quest
    Then Team admin chooses Challenge 2: Create a data room but not completes the quest
    And I access home page
    When User access view quest
    Then Team admin chooses Challenge 3: Set up your team, but not completes the quest
    And I logout from the application
    Examples:
      | userPlanType            |
      | inProgressQuestTrialAdm |
      | inProgressQuestTeamAdm  |
      | inProgressQuestProAdm   |
      | inProgressQuestEntAdm   |
      | inProgressQuestBussAdm  |

  Scenario Outline: Team Member verify Quest Challenges 1 & 2 and visit all tutorial steps
    Given I login as "<userPlanType>"
    And User validate quest banner on home page
    When User access view quest
    And Team member validate view quest tasks when all tasks are pending
    Then I validate that send file challenge and create dataroom challenge appear, and setup your team challenge does not appear for non admin user
    Then Team member chooses Challenge 1: Send a file, but not completes the quest
    And I navigate back to home page by clicking on home button
    When User access view quest
    Then Team member chooses Challenge 2: Create a data room but not completes the quest
    And I logout from the application
    Examples:
      | userPlanType            |
      | questNotStartedTrialMem |
      | questNotStartedTeamMem  |
      | questNotStartedProMem   |
      | questNotStartedEntMem   |
      | questNotStartedBussMem  |

  Scenario Outline: As a user, select 'mark as complete' in introductory quest and validate the modal
    Given I login as "<userPlanType>"
    And User validate quest banner on home page
    When I select mark as complete option in introductory quest
    And I validate the mark as complete modal
    Then I select continue exploring option
    And I logout from the application
    Examples:
      | userPlanType          |
      | doneQuestTaskTrialAdm |
      | doneQuestTaskTrialMem |
      | doneQuestTaskTeamAdm  |
      | doneQuestTaskTeamMem  |
      | doneQuestTaskProAdm   |
      | doneQuestTaskProMem   |
      | doneQuestTaskEntAdm   |
      | doneQuestTaskEntMem   |
      | doneQuestTaskBussMem  |
      | doneQuestTaskBussAdm  |

  Scenario: Introductory quest should not be available for free plan user
    Given I login as "questTaskFreeUser"
    Then I validate introductory quest banner is not visible on home page
    And I logout from the application

  Scenario Outline: As a team admin, change team brand color in branding
    Given I login as "<userPlanType>"
    And I navigate to "branding" tab in "Admin settings" page
    When I expand "change color" in branding tab
    Then I successfully change the "brand color"
    And I logout from the application
    Examples:
      | userPlanType          |
      | doneQuestTaskTrialAdm |
      | doneQuestTaskTeamAdm  |
      | doneQuestTaskProAdm   |
      | doneQuestTaskBussAdm  |
      | doneQuestTaskEntAdm   |

  Scenario Outline: As a team admin, change team text color in branding
    Given I login as "<userPlanType>"
    And I navigate to "branding" tab in "Admin settings" page
    When I expand "change color" in branding tab
    Then I successfully change the "text color"
    And I logout from the application
    Examples:
      | userPlanType          |
      | doneQuestTaskTrialAdm |
      | doneQuestTaskTeamAdm  |
      | doneQuestTaskProAdm   |
      | doneQuestTaskBussAdm  |
      | doneQuestTaskEntAdm   |

  Scenario Outline: Delete all data rooms from manage DR page
    When I login as "<userPlanType>"
    And I navigate to manage data room page
    Then I delete all data rooms from manage data room page
    And I logout from the application
    Examples:
      | userPlanType          |
      | doneQuestTaskTrialAdm |
      | doneQuestTaskTrialMem |
      | doneQuestTaskTeamAdm  |
      | doneQuestTaskTeamMem  |
      | doneQuestTaskProAdm   |
      | doneQuestTaskProMem   |
      | doneQuestTaskBussAdm  |
      | doneQuestTaskBussMem  |
      | doneQuestTaskEntAdm   |
      | doneQuestTaskEntMem   |

  Scenario Outline: Delete all files from manage sent files
    When I login as "<userPlanType>"
    And I navigate to "ManageSentFiles" page from home page
    Then I delete all files
    And I logout from the application
    Examples:
      | userPlanType          |
      | doneQuestTaskTrialAdm |
      | doneQuestTaskTrialMem |
      | doneQuestTaskTeamAdm  |
      | doneQuestTaskTeamMem  |
      | doneQuestTaskProAdm   |
      | doneQuestTaskProMem   |
      | doneQuestTaskEntAdm   |
      | doneQuestTaskEntMem   |
      | doneQuestTaskBussMem  |
      | doneQuestTaskBussAdm  |


