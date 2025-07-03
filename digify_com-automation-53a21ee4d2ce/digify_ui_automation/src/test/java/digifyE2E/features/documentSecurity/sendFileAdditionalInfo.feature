@sendFileAdditionalInfo @regression @documentSecurity
Feature: Send file with require additional information feature

  Background: Navigate to Digify login page
    Given I am on the login page

  @fileViewer
  Scenario: Empty/null fields in require additional information form should throw error
    When I login as "teamPlanAdmin"
    And I navigate to "SendFiles" page and upload a "ImageJPEGFile.jpeg" file
    And I select "Allow downloading,ppad->turn off,Only people I specify,req additional info" in permission dropdown
    And I validate feature "rai->email" on sent file page
    And I select "rai->name,rai->phone number,rai->company,rai->job role" feature in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage sent file
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    Then I validate feature "req additional info,rai->email,rai->name,rai->phone,rai->company,rai->job" on sent file's settings page
    And I logout from the application
    And I access the file link as existing user
    And I login in file viewer as "recipientUser"
    And Verified the email field is disabled on the Request Additional Info form in File Viewer
    Then Submit the RAI form with all fields "rai->name,rai->phone,rai->company,rai->job" blank in File Viewer
    And I validated error below each fields "name_err,phone_err,company_err,job_err"

  @fileViewer
  Scenario Outline: Verify file sent with require additional information for different access types
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "<fileType>" file
    And I select "<permissionType>,<accessType>,req additional info" in permission dropdown
    And I validate feature "rai->email" on sent file page
    And I select "<reqFields>" feature in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage sent file
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    Then I validate feature "<expReqFields>" on sent file's settings page
    And I logout from the application
    And I access the file link as existing user
    And I login in file viewer as "recipientUser"
    And Verified the email field is disabled on the Request Additional Info form in File Viewer
    Then Entered "<fillReqFields>" in the Request Additional Info form in File Viewer
    And I wait until file is loaded in file viewer
    And I logout from file viewer
    Examples:
      | userPlanType      | fileType           | permissionType                                 | accessType                                        | reqFields                                              | expReqFields                                                              | fillReqFields                              |
      | teamPlanAdmin     | ImageJPEGFile.jpeg | Allow downloading,ppad->turn off               | Only people I specify                             | rai->name,rai->phone number,rai->company,rai->job role | req additional info,rai->email,rai->name,rai->phone,rai->company,rai->job | rai->name,rai->phone,rai->company,rai->job |
      | businessPlanAdmin | pdfFile2KB.pdf     | Don't allow downloading,dynamic watermark-->On | Only people from email domains I specify          | rai->company,rai->job role                             | req additional info,rai->email,rai->name,rai->phone                       | rai->company, rai->job                     |
      | proPlanAdmin      | ImageFile.png      | Don't allow downloading,Allow printing         | Anyone with the link or file (email verification) | rai->name,rai->phone number                            | req additional info,rai->email,rai->name,rai->phone                       | rai->name,rai->phone                       |

  @fileViewer @bug-PROD-10367
  Scenario Outline: Verify the file send with require additional information with access anyone with the link (no email verification)
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "ImageFile.png" file
    And I select "Anyone with the link (no email verification),Allow downloading,ppad->turn off" in send files
    And I validate feature "rai->email" on sent file page
    And I select "rai->name,rai->phone number" feature in send files
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage sent file
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    Then I validate feature "req additional info,rai->email,rai->name,rai->phone" on sent file's settings page
    And I logout from the application
    And I access the file link
    And Entered "rai->invalid email" in the Request Additional Info form in File Viewer
    And I validated error below each fields "invalidEmail_err"
    Then Entered "rai->email,rai->name,rai->phone" in the Request Additional Info form in File Viewer
    And I wait until file is loaded in file viewer
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @fileViewer
  Scenario: Verify email field appears only if other fields are disabled in require additional information form
    When I login as "proPlanAdmin"
    And I navigate to "SendFiles" page and upload a "ImageJPEGFile.jpeg" file
    And I select "Allow downloading,ppad->turn off,Only people I specify,req additional info" in permission dropdown
    And I validate feature "rai->email" on sent file page
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage sent file
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    Then I validate feature "req additional info,rai->email,rai->name,rai->phone,rai->company,rai->job" on sent file's settings page
    And I logout from the application
    And I access the file link as existing user
    And I login in file viewer as "recipientUser"
    Then I validated that the email field is disabled on the request additional info form and i submit the form
    And I wait until file is loaded in file viewer
    And I logout from file viewer


  Scenario Outline: Validate the require additional information feature on the settings page when a file is sent without it
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "<fileType>" file
    And I select "<permissionType>,<accessType>" in permission dropdown
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage sent file
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    Then I validate feature "req additional info" on sent file's settings page
    And I logout from the application
    Examples:
      | userPlanType      | fileType           | permissionType                                    | accessType                                        |
      | teamPlanAdmin     | ImageJPEGFile.jpeg | dynamic watermark-->Off                           | Only people I specify                             |
      | businessPlanAdmin | pdfFile2KB.pdf     | Don't allow downloading,Expire after first access | Only people from email domains I specify          |
      | proPlanAdmin      | ImageFile.png      | Don't allow downloading,Don't allow downloading   | Anyone with the link or file (email verification) |

  @fileViewer
  Scenario Outline: Verify the sent file with TOA + require additional information
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "<fileType>" file
    And I select "<permissionType>,<accessType>,req additional info" in permission dropdown
    And I validate feature "rai->email" on sent file page
    And I select "<reqFields>" feature in send files
    And I add "recipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage sent file
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    Then I validate feature "<expReqFields>" on sent file's settings page
    And I validate feature "TOA-->On" on sent file's settings page
    And I logout from the application
    And I access the file link as existing user
    And I login in file viewer as "recipientUser"
    And I agree TOA and continue as "recipient"
    And Verified the email field is disabled on the Request Additional Info form in File Viewer
    Then Entered "<fillReqFields>" in the Request Additional Info form in File Viewer
    And I wait until file is loaded in file viewer
    And I logout from file viewer
    Examples:
      | userPlanType      | fileType           | permissionType                                   | accessType                                        | reqFields                                              | expReqFields                                                              | fillReqFields                              |
      | teamPlanAdmin     | ImageJPEGFile.jpeg | TOA-->On                                         | Only people I specify                             | rai->name,rai->phone number,rai->company,rai->job role | req additional info,rai->email,rai->name,rai->phone,rai->company,rai->job | rai->name,rai->phone,rai->company,rai->job |
      | businessPlanAdmin | pdfFile2KB.pdf     | Don't allow downloading,TOA-->On                 | Only people from email domains I specify          | rai->company,rai->job role                             | req additional info,rai->email,rai->name,rai->phone                       | rai->company, rai->job                     |
      | proPlanAdmin      | ImageFile.png      | Don't allow downloading,Allow printing ,TOA-->On | Anyone with the link or file (email verification) | rai->name,rai->phone number                            | req additional info,rai->email,rai->name,rai->phone                       | rai->name,rai->phone                       |

  @fileViewer
  Scenario Outline: Verify the sent file with enforce email verification + require additional information
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "<fileType>" file
    And I select "<permissionType>,<accessType>,req additional info" in permission dropdown
    And I select the stricter email verification checkbox
    And I validate feature "rai->email" on sent file page
    And I select "<reqFields>" feature in send files
    And I add "maildropRecipientUser" as recipient
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage sent file
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    Then I validate feature "<expReqFields>" on sent file's settings page
    And I logout from the application
    And I access the file link as existing user "maildropRecipientUser" and generate OTP
    And I access the file, as "maildropRecipientUser" with OTP
    And Verified the email field is disabled on the Request Additional Info form in File Viewer
    Then Entered "<fillReqFields>" in the Request Additional Info form in File Viewer
    And I wait until file is loaded in file viewer
    And I logout from file viewer
    Examples:
      | userPlanType      | fileType           | permissionType                         | accessType                                        | reqFields                                              | expReqFields                                                              | fillReqFields                              |
      | teamPlanAdmin     | ImageJPEGFile.jpeg | Don't allow downloading                | Only people I specify                             | rai->name,rai->phone number,rai->company,rai->job role | req additional info,rai->email,rai->name,rai->phone,rai->company,rai->job | rai->name,rai->phone,rai->company,rai->job |
      | businessPlanAdmin | pdfFile2KB.pdf     | Don't allow downloading                | Only people from email domains I specify          | rai->company,rai->job role                             | req additional info,rai->email,rai->name,rai->phone                       | rai->company, rai->job                     |
      | proPlanAdmin      | ImageFile.png      | Don't allow downloading,Allow printing | Anyone with the link or file (email verification) | rai->name,rai->phone number                            | req additional info,rai->email,rai->name,rai->phone                       | rai->name,rai->phone                       |

  @fileViewer
  Scenario Outline: Verify the sent file with passcode + require additional information
    When I login as "<userPlanType>"
    And I navigate to "SendFiles" page and upload a "DocumentFile2.odt" file
    And I select "Anyone with the link (no email verification)" in send files
    And I select "Require passcode" and add passcode for security reasons
    And I validate feature "rai->email" on sent file page
    And I select "<reqFields>" feature in send files
    And I click on "get link & skip notification" button to send the file
    And I copy the file link
    And I navigate to "ManageSentFiles" page from home page
    And I select "Date (Newest first)" from the sort by filter in Manage sent file
    And I wait until file is encrypted in manage sent file
    And I select the first file checkbox on the page
    And I click on more from the floating menu and select "settings"
    Then I validate feature "Anyone with the link (no email verification), Require passcode,req additional info" on sent file's settings page
    And I logout from the application
    And I access the file link and entered passcode
    Then Entered "<fillReqFields>" in the Request Additional Info form in File Viewer
    And I wait until file is loaded in file viewer
    And I close the current tab
    Examples:
      | userPlanType      | reqFields                                              | fillReqFields                                         |
      | teamPlanAdmin     | rai->name,rai->phone number,rai->company,rai->job role | rai->email,rai->name,rai->phone,rai->company,rai->job |
      | businessPlanAdmin | rai->company,rai->job role                             | rai->email, rai->company,rai->job                     |
      | proPlanAdmin      | rai->name,rai->phone number                            | rai->email,rai->name,rai->phone                       |


  Scenario Outline: Delete all files from manage sent files
    When I login as "<userPlanType>"
    And I navigate to "ManageSentFiles" page from home page
    Then I delete all files
    And I logout from the application
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |