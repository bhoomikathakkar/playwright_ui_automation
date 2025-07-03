@dataRoomGroups @regression @dataRoom
Feature: Verify data room group features

  Background: Navigate to Digify login page
    Given I am on the login page

  @prod @smoke
  Scenario Outline: Create a group with default settings in data room
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I navigate to data room "guests" page
    And I navigate to "groups" tab
    Then I create a group with default settings
    And I save the group
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @smoke
  Scenario Outline: Create a group with different permission in data room
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I navigate to data room "guests" page
    And I navigate to "groups" tab
    Then I create a group and select "<permissionType>" permission
    And I save the group
    And I navigate to data room "settings" page
    Then I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      | permissionType       |
      | teamPlanAdmin     | No Access            |
      | teamPlanAdmin     | Print                |
      | teamPlanAdmin     | View                 |
      | teamPlanAdmin     | Download (PDF)       |
      | teamPlanAdmin     | Download (Original)  |
      | teamPlanAdmin     | Granular Permissions |
      | teamPlanAdmin     | Edit                 |
      | businessPlanAdmin | No Access            |
      | businessPlanAdmin | Print                |
      | businessPlanAdmin | View                 |
      | businessPlanAdmin | Download (PDF)       |
      | businessPlanAdmin | Download (Original)  |
      | businessPlanAdmin | Granular Permissions |
      | businessPlanAdmin | Edit                 |
      | proPlanAdmin      | No Access            |
      | proPlanAdmin      | Print                |
      | proPlanAdmin      | View                 |
      | proPlanAdmin      | Download (PDF)       |
      | proPlanAdmin      | Download (Original)  |
      | proPlanAdmin      | Granular Permissions |
      | proPlanAdmin      | Edit                 |

  @prod @smoke
  Scenario Outline: Create a group, edit the group and add guest when data room has no guests
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I navigate to data room "guests" page
    And I navigate to "groups" tab
    Then I create a group and select "Download (PDF)" permission
    And I save the group
    And I select the group
    And I add guest "recipientUser" to the group when there are no existing guests in the data room
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @smoke
  Scenario Outline: Create a new group, edit the group and rename it
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I navigate to data room "guests" page
    And I navigate to "groups" tab
    And I create a group with default settings
    And I save the group
    And I manage the group
    Then I rename the group
    And I save the group
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |


  Scenario Outline: Create a group with expiry enabled
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I navigate to data room "guests" page
    And I navigate to "groups" tab
    Then I create a group with expiry on fixed date and time
    And I save the group
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  @smoke
  Scenario Outline: Edit the permission of a group and validate owner and guest
    When I login as "<userPlanType>"
    And I create DR and upload a file "ImageFile.png"
    And I navigate to data room "guests" page
    Then I add a guest "recipientUser"
    And I return to guest link
    And I navigate to "groups" tab
    Then I create a group and select "<permissionType>" permission and add existing guest "recipientUser" in a group
    And I save the group
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    Then I validate guest "<userPlanType>" file permission access "<permissionType>" from file context menu
    And I logout from data room
    And I login as "<userPlanType>" and access the same data room
    And I navigate to "groups" tab in data room "guests" page
    And I manage the group
    Then I edit the permission "<editPermission>" of a group
    And I save the group
    And I logout from data room
    When I login as "recipientUser" and access the same data room
    Then I validate guest "recipientUser" file permission access "<updatedGroupPermission>" from file context menu
    And I logout from data room
    When I login as "<userPlanType>" and access the same data room
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      | permissionType      | editPermission      | updatedGroupPermission |
      | teamPlanAdmin     | View                | Edit                | Edit                   |
      | teamPlanAdmin     | Edit                | Download (PDF)      | Download (PDF)         |
      | businessPlanAdmin | Download (PDF)      | View                | View                   |
      | businessPlanAdmin | Print               | Download (Original) | Download (Original)    |
      | proPlanAdmin      | No Access           | Edit                | Pro-->Edit             |
      | proPlanAdmin      | Download (Original) | Download (PDF)      | Pro-->Download (PDF)   |

  Scenario Outline: Remove guest from group and validate guest permission
    When I login as "<userPlanType>"
    And I create DR with "<drPermissionAs>" and upload a file "ImageFile.png"
    And I navigate to data room "guests" page
    Then I add a guest "recipientUser1"
    And I return to guest link
    And I navigate to "groups" tab
    Then I create a group and select "<groupPermission>" permission and add existing guest "recipientUser1" in a group
    And I save the group
    And I manage the group
    And I remove the guest "recipientUser1" from the group
    And I save the group
    And I logout from data room
    When I login as "recipientUser1" and access the same data room
    Then I validate guest "recipientUser1" file permission access "<updatedGroupPermission>" from file context menu
    And I logout from data room
    When I login as "<userPlanType>" and access the same data room
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      | drPermissionAs      | groupPermission     | updatedGroupPermission |
      | teamPlanAdmin     | Edit                | No Access           | No Access              |
      | businessPlanAdmin | Download (Original) | View                | View                   |
      | proPlanAdmin      | View                | Edit                | Pro-->Edit             |
      | teamPlanAdmin     | Edit                | Download (PDF)      | Download (PDF)         |
      | businessPlanAdmin | Download (Original) | Download (Original) | Download (Original)    |
      | proPlanAdmin      | View                | Print               | Pro-->Print            |

  Scenario Outline: After group deletion, guest should have group permission only
    When I login as "<userPlanType>"
    And I create DR with "<drPermissionAs>" and upload a file "ImageFile.png"
    And I navigate to data room "guests" page
    Then I add a guest "recipientUser1"
    And I return to guest link
    And I navigate to "groups" tab
    And I create a group and select "<groupPermission>" permission and add existing guest "recipientUser1" in a group
    And I save the group
    Then I delete the group
    And I logout from data room
    When I login as "recipientUser1" and access the same data room
    Then I validate guest "recipientUser1" file permission access "<updatedGuestPermission>" from file context menu
    And I logout from data room
    When I login as "<userPlanType>" and access the same data room
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      | drPermissionAs      | groupPermission | updatedGuestPermission |
      | teamPlanAdmin     | Download (PDF)      | No Access       | No Access              |
      | businessPlanAdmin | No Access           | View            | View                   |
      | proPlanAdmin      | View                | Edit            | Pro-->Edit             |
      | teamPlanAdmin     | Edit                | Download (PDF)  | Download (PDF)         |
      | businessPlanAdmin | Download (Original) | Download (PDF)  | Download (PDF)         |
      | proPlanAdmin      | View                | Print           | Pro-->Print            |

  Scenario Outline: Remove group from manage guest, guest will have data room permission and not group permission
    When I login as "<userPlanType>"
    And I create DR with "<drPermissionAs>" and upload a file "ImageFile.png"
    And I navigate to data room "guests" page
    Then I add a guest "recipientUser1"
    And I return to guest link
    And I navigate to "groups" tab
    And I create a group and select "<groupPermission>" permission and add existing guest "recipientUser1" in a group
    And I save the group
    And I navigate to "guests" tab in data room "guests" page
    Then I manage the guest "recipientUser1"
    And I remove the selected group
    And I save changes on manage guest page
    And I logout from data room
    When I login as "recipientUser1" and access the same data room
    Then I validate guest "recipientUser1" file permission access "<updatedGuestPermission>" from file context menu
    And I logout from data room
    When I login as "<userPlanType>" and access the same data room
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      | drPermissionAs      | groupPermission     | updatedGuestPermission |
      | teamPlanAdmin     | View                | No Access           | View                   |
      | businessPlanAdmin | No Access           | Download (Original) | No Access              |
      | proPlanAdmin      | Download (PDF)      | Edit                | Pro-->Download (PDF)   |
      | teamPlanAdmin     | Edit                | Download (PDF)      | Edit                   |
      | businessPlanAdmin | Download (Original) | View                | Download (Original)    |
      | proPlanAdmin      | View                | Print               | View                   |

  Scenario Outline: Copy permission from an existing guest while creating a group
    When I login as "<userPlanType>"
    And I create DR with "<drPermissionAs>" and upload a file "PPSMTestFile.ppsm"
    And I navigate to data room "guests" page
    Then I add a guest "recipientUser1" with permission as "<guestPermission>"
    And I return to guest link
    And I copy the guest name "recipientUser1"
    And I copy the guest "recipientUser1" permission
    And I navigate to "groups" tab
    And I create a group and select "<groupPermission>" permission and add existing guest "recipientUser1" in a group
    Then I select copy permission and select "Another guest" from the option
    And I select the guest from "recipientUser1" from copy permission modal
    Then I validate the group permission on copy permission modal which is same as "<guestPermission>"
    And I save the group
    And I logout from data room
    When I login as "recipientUser1" and access the same data room
    Then I validate guest "recipientUser1" file permission access "<updatedGuestPermission>" from file context menu
    And I logout from data room
    When I login as "<userPlanType>" and access the same data room
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      | drPermissionAs      | guestPermission     | groupPermission     | updatedGuestPermission |
      | teamPlanAdmin     | Edit                | View                | View                | View                   |
      | businessPlanAdmin | View                | Download (Original) | Download (Original) | Download (Original)    |
      | teamPlanAdmin     | Download (PDF)      | No Access           | No Access           | No Access              |
      | businessPlanAdmin | Download (Original) | Edit                | Edit                | Edit                   |
      | proPlanAdmin      | Print               | Download (PDF)      | Download (PDF)      | Pro-->Download (PDF)   |
      | proPlanAdmin      | No Access           | Print               | Print               | Pro-->Print            |

  Scenario Outline: Copy permission from an existing group while creating a new group and verify as guest
    When I login as "<userPlanType>"
    And I create DR with "<drPermissionAs>" and upload a file "PPSMTestFile.ppsm"
    And I navigate to data room "guests" page
    Then I add a guest "recipientUser1"
    And I return to guest link
    And I navigate to "groups" tab
    And I create a group and select "<groupPermission>" permission
    And I save the group
    And I create another group
    And I select the guest in a group
    Then I select copy permission and select "Another group" from the option
    And I select the last created group in copy permission modal
    And I save the group
    Then I validate the group permission should be "<copiedExistingGroupPermission>"
    And I logout from data room
    When I login as "recipientUser1" and access the same data room
    Then I validate guest "recipientUser1" file permission access "<updatedGroupPermission>" from file context menu
    And I logout from data room
    When I login as "<userPlanType>" and access the same data room
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      | drPermissionAs      | groupPermission     | copiedExistingGroupPermission | updatedGroupPermission |
      | teamPlanAdmin     | Edit                | View                | View                          | View                   |
      | businessPlanAdmin | View                | Download (Original) | Download (Original)           | Download (Original)    |
      | teamPlanAdmin     | Download (PDF)      | No Access           | No Access                     | No Access              |
      | businessPlanAdmin | Download (Original) | Edit                | Edit                          | Edit                   |
      | proPlanAdmin      | Print               | Download (PDF)      | Download (PDF)                | Pro-->Download (PDF)   |
      | proPlanAdmin      | No Access           | Print               | Print                         | Pro-->Print            |

  Scenario Outline: Select guest in copy permission when there is no existing guest but group is available, verify the message on copy permission modal
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I navigate to data room "guests" page
    And I navigate to "groups" tab
    And I create a group
    And I save the group
    And I create another group
    And I select copy permission and select "Another guest" from the option
    Then I validate that correct message appears if "Another guest" is selected
    And I select cancel button on copy permission modal
    And I save the group
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  Scenario Outline: Select group in copy permission when there is no existing group but guest is available, verify the message on copy permission modal
    When I login as "<userPlanType>"
    And I create a data room with default settings
    And I navigate to data room "guests" page
    Then I add a guest "recipientUser1"
    And I return to guest link
    And I navigate to "groups" tab
    And I create a group
    And I select copy permission and select "Another group" from the option
    Then I validate that correct message appears if "Another group" is selected
    And I select cancel button on copy permission modal
    And I save the group
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      |
      | teamPlanAdmin     |
      | businessPlanAdmin |
      | proPlanAdmin      |

  Scenario Outline: Copy permission from an existing group while editing a group and verify as guest
    When I login as "<userPlanType>"
    And I create DR with "<drPermissionAs>" and upload a file "PPSMTestFile.ppsm"
    And I navigate to data room "guests" page
    Then I add a guest "recipientUser1"
    And I return to guest link
    And I navigate to "groups" tab
    And I create a group and select "<groupPermission>" permission
    And I save the group
    And I create another group
    And I select the guest in a group
    And I save the group
    Then I edit the recently created group
    Then I select copy permission and select "Another group" from the option
    And I select the last created group in copy permission modal
    And I save the group
    Then I validate the group permission should be "<copiedExistingGroupPermission>"
    And I logout from data room
    When I login as "recipientUser1" and access the same data room
    Then I validate guest "recipientUser1" file permission access "<updatedGroupPermission>" from file context menu
    And I logout from data room
    When I login as "<userPlanType>" and access the same data room
    And I navigate to data room "settings" page
    And I "delete" data room from advanced settings
    And I logout from data room
    Examples:
      | userPlanType      | drPermissionAs      | groupPermission     | copiedExistingGroupPermission | updatedGroupPermission |
      | teamPlanAdmin     | Edit                | View                | View                          | View                   |
      | businessPlanAdmin | View                | Download (Original) | Download (Original)           | Download (Original)    |
      | teamPlanAdmin     | Download (PDF)      | No Access           | No Access                     | No Access              |
      | businessPlanAdmin | Download (Original) | Edit                | Edit                          | Edit                   |
      | proPlanAdmin      | Print               | Download (PDF)      | Download (PDF)                | Pro-->Download (PDF)   |
      | proPlanAdmin      | No Access           | Print               | Print                         | Pro-->Print            |