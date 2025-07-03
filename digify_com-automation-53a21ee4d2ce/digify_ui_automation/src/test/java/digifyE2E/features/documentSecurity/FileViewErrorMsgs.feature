@fileViewerErrorMsg @fileViewer @regression @documentSecurity
Feature: Verify error messages in file viewer

  Background: Navigate to Digify login page
    Given I am on the login page

  @smoke @prod
  Scenario Outline: Sender revokes file's access, verify error page in the receiver's file viewer
    When I login as "<userPlanType>"
    Then I navigate to "SendFiles" page from home page
    And I upload a file "ImageFile.png" in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage sent file
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "manage recipients"
    Then I revoke the file access
    And I logout from the application
    When I access the file link as existing user
    Then "recipientUser" access unavailable file
    And I validate the file name
    Then Verify two error messages "This file is not available", "Please check with the owner if you need access." and error code "ITM_RVK" in file viewer
    And I logout from file viewer
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @smoke @prod
  Scenario Outline: As a recipient, access a deleted file and verify error message in file viewer
    When I login as "<userPlanType>"
    Then I navigate to "SendFiles" page from home page
    And I upload a file "PdfFile4MB.pdf" in send files
    And I select "Allow downloading,ppad->turn off" in permission dropdown
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage sent file
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    Then I select "delete ds" from the floating menu
    And I delete the file
    And I logout from the application
    When I access the file link as existing user
    And "recipientUser" access unavailable file
    Then Verify two error messages "This file is not available", "Please check with the owner if you need access." and error code "ITM_DEL" in file viewer
    And I logout from file viewer
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @smoke @prod
  Scenario Outline: Send a file to a user who does not have the access of file
    When I login as "<userPlanType>"
    Then I navigate to "SendFiles" page from home page
    And I upload a file "PdfFile4MB.pdf" in send files
    And I select "Allow downloading,ppad->turn off" in permission dropdown
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I logout from the application
    Then I access the file link as existing user
    And "recipientUser1" access unavailable file
    Then Verify an error message "Access denied" displayed in the file viewer
    And I logout from file viewer
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @smoke @prod
  Scenario Outline: As a recipient access unsupported file and verify error message in file viewer
    When I login as "<userPlanType>"
    Then I navigate to "SendFiles" page from home page
    And I upload a file "zipFile.zip" in send files
    And I select "Allow downloading,ppad->turn off" in permission dropdown
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" page from home page
    And I logout from the application
    And I access the file link as existing user
    Then "recipientUser" access preview not available file
    And I validate the file name
    Then Verify two error messages "Preview not available" and "This file format is not supported." displayed in file viewer
    And I logout from file viewer
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |


  Scenario Outline: As recipient access file when recipient have access and when sender remove access
    When I login as "<userPlanType>"
    Then I navigate to "SendFiles" page from home page
    And I upload a file "pptFile.pptx" in send files
    And I select "Allow downloading,ppad->turn off,Allow printing" in permission dropdown
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I logout from the application
    And I access the file link as existing user
    Then I login in file viewer as "recipientUser"
    And I validate the file name
    Then I validate "enabled-->print button,enabled-->download button" in file viewer
    And I logout from file viewer
    And I navigate to login page
    Then I login as "<userPlanType>"
    And I navigate to "ManageSentFiles" page from home page
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "manage recipients"
    And I remove the recipient
    And I logout from the application
    And I access the file link as existing user
    And "recipientUser" access unavailable file
    And I validate the file name
    And Verify an error message "Access denied" displayed in the file viewer
    And I logout from file viewer
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @bug #PROD-9591
  Scenario Outline: Recipient can request access when they don't have for the file
    When I login as "<userPlanType>"
    Then I navigate to "SendFiles" page and upload a "ImageFile.png" file
    And I select "Allow downloading,ppad->turn off" in permission dropdown
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I logout from the application
    Then I access the file link as existing user
    And "recipientUser1" access unavailable file
    And Verify an error message "Access denied" displayed in the file viewer
    Then I Request access for DS from file viewer
    And I logout from file viewer
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |


  Scenario Outline: Verify that recipient can use another email if used email don't have access of DS
    When I login as "<userPlanType>"
    Then I navigate to "SendFiles" page and upload a "ImageFile.png" file
    And I select "Allow downloading,ppad->turn off,Allow printing" in permission dropdown
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" page from home page
    And I wait until file is encrypted in manage sent file
    And I logout from the application
    Then I access the file link as existing user
    And "recipientUser1" access unavailable file
    Then Verify an error message "Access denied" displayed in the file viewer
    And I use another email to access DR or DS from file viewer
    Then I login in file viewer as "recipientUser"
    And I validate the file name
    Then I validate "enabled-->print button,enabled-->download button" in file viewer
    And I logout from file viewer
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |


  Scenario Outline: Verify that recipient can access view received files page from access denied error page
    When I login as "<userPlanType>"
    Then I navigate to "SendFiles" page and upload a "ImageFile.png" file
    And I select "Allow downloading,ppad->turn off" in permission dropdown
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" page from home page
    And I wait until file is encrypted in manage sent file
    And I logout from the application
    Then I access the file link as existing user
    And "recipientUser1" access unavailable file
    Then Verify an error message "Access denied" displayed in the file viewer
    And I click on "go to file list" link from file viewer
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @dev
  Scenario: As a recipient access a file from a sender whose subscription is expired
    When I access the file link when "sender subscription is expired"
    And I "recipientAccessSubExpFile" login in subscription expired file
    Then Verify two error messages "This file is not available", "Please check with the owner if you need access." and error code "SUB_EXP" in file viewer
    And I logout from file viewer

  @dev
  Scenario: As a recipient access a file from a sender whose account is deleted
    When I access the file link when "sender account is deleted"
    Then Verify three error messages "Invalid Link", "Problem with the link" and "Visit another network" displayed in file viewer
    And I close the current tab

  @dev
  Scenario: As a owner access a file using direct link when account is locked
    When I access the file link when "sender account is locked"
    And I "lockedTeamAdmin" login into file viewer as locked user
    Then I validate "reset password" error page
    And I close the current tab

  @dev
  Scenario: As a document security recipient, access expired file and validate error messages
    When I access the file link when "document security expired file"
    And I "expiredFileRecipient" login in expired document security file
    Then Verify two error messages "This file is not available", "Please check with the owner if you need access." and error code "ITM_EXP" in file viewer
    And I logout from file viewer

  @dev
  Scenario: As a data room guest, access expired file and validate error messages
    When I access the file link when "data room expired file"
    And I "expiredFileRecipient" login in expired data room file
    Then Verify two error messages "This file is not available", "Please check with the owner if you need access." and error code "ITM_EXP" in file viewer
    And I logout from file viewer
