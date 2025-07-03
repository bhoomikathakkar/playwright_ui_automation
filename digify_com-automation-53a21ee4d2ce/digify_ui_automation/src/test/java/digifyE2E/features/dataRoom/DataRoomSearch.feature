@dataRoomSearch @regression @dataRoom
Feature: Search in Data room with different filters and access floating and context menu

  Background: Navigate to Digify login page
    Given I am on the login page

  Scenario Outline:Verify that user should able to search using file name
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I upload a "<fileType>" file in DR
    And I navigate to search page
    Then I search a file using file name
    And I validate file in search result "<fileExtension>"
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType   | fileType      | fileExtension |
      | proSearchUser  | ImageFile.png | .png          |
      | teamSearchUser | pdfFile.pdf   | .pdf          |


  Scenario Outline: Verify that user should able to search using type filter
    When I login as "<userPlanType>"
    And I create a data room with "<featureType>" enabled
    And I upload a "<fileType>" file in DR
    And I navigate to search page
    Then I apply filter Type "<filterType>" and validate result set
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType   | featureType                                       | fileType                                                    | filterType                                      |
      | proSearchUser  | Anyone with the link or file (email verification) | pdfFile.pdf,ImageFile.png,MOVVideoFile.mov,AudioWMAFile.wma | pdf,images,videos,audio files                   |
      | teamSearchUser | disable-->editing with MS office                  | ExcelFile.xlsx,DocumentFile.docx,pptFile.pptx,TextFile.txt  | spreadsheets,documents,presentations,text files |


  Scenario Outline: Verify that user should find all DR files in result when apply default filter
    When I login as "<userPlanType>"
    And I create a data room with "<featureType>" enabled
    And I upload a "<fileType>" file in DR
    And I navigate to search page
    And I click on search button
    Then I validate "<noOfFiles>" return in search result
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType   | featureType                                       | fileType                                        | noOfFiles |
      | proSearchUser  | Anyone with the link or file (email verification) | pdfFile.pdf,ImageFile.png                       | 2         |
      | teamSearchUser | disable-->editing with MS office                  | ExcelFile.xlsx,MOVVideoFile.mov,WMVTestFile.wmv | 3         |


  Scenario Outline: Verify the floating menu options on data room search page
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I upload a "<fileType>" file in DR
    And I navigate to search page
    And I click on search button
    And I validate "<noOfFiles>" return in search result
    Then I validate floating menu option "download,link,move or copy,trash,fm->change name and description,fm->replace file"
    And I validate floating menu option "parent folder"
    And I close the current tab
    And I validate floating menu option "version history"
    And I close the current tab
    And I validate floating menu option "analytics"
    And I close the current tab
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType   | fileType      | noOfFiles |
      | proSearchUser  | ImageFile.png | 1         |
      | teamSearchUser | pdfFile.pdf   | 1         |

  @ignore #Editing file takes longer time to encrypt, to handle it later
  Scenario: Verify floating menu options manage access, edit, questions for team plan user on DR search page
    When I login as "teamSearchUser"
    And I create DR with "enable-->Editing with MS office" and upload a file "pptFile.pptx"
    And I navigate to search page
    And I click on search button
    And I validate "1" return in search result
    Then I validate floating menu option "fm->manage access"
    And I close the current tab
    And I validate floating menu option "question"
    And I validate floating menu option "edit"
    And I close the current tab
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room

  @fileViewer
  Scenario Outline: Verify the context menu options on data room search page
    When I login as "<userPlanType>"
    And I create DR with "<featureType>" and upload a file "<fileType>"
    And I navigate to search page
    And I click on search button
    And I validate "<noOfFiles>" return in search result
    Then I validate context menu option "get file link,cm->change name and description,cm->download pdf,cm->download original,cm->move to trash,cm->replace file,cm->move or copy"
    And I validate context menu option "cm->view"
    And I close the current tab
    And I validate context menu option "cm->version history"
    And I close the current tab
    And I validate context menu option "cm->analytics"
    And I close the current tab
    And I validate context menu option "go to parent folder"
    And I close the current tab
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType   | featureType                                       | fileType          | noOfFiles |
      | proSearchUser  | Anyone with the link or file (email verification) | DocumentFile.docx | 1         |
      | teamSearchUser | disable-->editing with MS office                  | pptFile.pptx      | 1         |

  @ignore #Editing file takes longer time to encrypt, to handle it later
  Scenario: Verify context menu options manage access, edit, questions for team plan user on DR search page
    When I login as "teamSearchUser"
    And I create DR with "enable-->Editing with MS office" and upload a file "DocumentFile.docx"
    And I navigate to search page
    And I click on search button
    And I validate "1" return in search result
    Then I validate context menu option "view questions"
    And I validate context menu option "cm->manage access"
    And I close the current tab
    And I validate context menu option "cm->edit"
    And I close the current tab
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room

  Scenario Outline: Delete all data rooms from manage DR page
    When I login as "<userPlanType>"
    And I navigate to manage data room page
    Then I delete all data rooms from manage data room page
    And I logout from the application
    Examples:
      | userPlanType   |
      | teamSearchUser |
      | proSearchUser  |
