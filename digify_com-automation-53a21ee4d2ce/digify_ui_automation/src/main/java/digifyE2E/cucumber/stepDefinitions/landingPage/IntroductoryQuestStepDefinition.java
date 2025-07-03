package digifyE2E.cucumber.stepDefinitions.landingPage;

import digifyE2E.pages.landingPage.IntroductoryQuest;
import digifyE2E.pages.landingPage.constants.Quests;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class IntroductoryQuestStepDefinition extends IntroductoryQuest {

    @When("User access view quest")
    public void userAccessViewQuest() {
        selectViewQuest();
    }

    @Then("I close quest")
    public void iCloseQuest() {
        closeViewQuestIntercomModal();
    }

    @Then("I validate that send file challenge and create dataroom challenge appear, and setup your team challenge does not appear for non admin user")
    public void iValidateThatSendFileChallengeAndCreateDataroomChallengeAppearAndSetupYourTeamChallengeDoesNotAppearForNonAdminUser() {
        validateChallengesTypeOnViewQuest();
    }

    @When("I select mark as complete option in introductory quest")
    public void iSelectMarkAsCompleteInIntroductoryQuest() {
        selectMarkAsComplete();
    }

    @Then("I validate the mark as complete modal and select continue exploring button")
    public void iValidateTheMarkAsCompleteModalAndSelectContinueExploringButton() {
        validateMarkAsCompleteModal();
    }

    @And("I select continue exploring option")
    public void iSelectContinueExploringButton() {
        selectContinueExploring();
    }

    @Then("I validate the mark as complete modal")
    public void iValidateTheMarkAsCompleteModal() {
        validateMarkAsCompleteModal();
    }

    @And("User validate quest banner on home page")
    public void userValidateQuestBannerOnHomePage() {
        validateIntroductoryQuestBanner();
    }

    @Then("I validate introductory quest banner is not visible on home page")
    public void iValidateIntroductoryQuestBannerIsNotVisibleOnHomePage() {
        verifyIntroBannerHidden();
    }

    @And("Team admin chooses Challenge 1: Send a file, and completes the quest")
    public void teamAdminChoosesChallenge1SendAFileAndCompletesTheQuest() {
        selectIntercomChallenge(Quests.challenge1.title, true, false);
        completeSendFileChallenge(5);
    }

    @Then("Team admin chooses Challenge 2: Create a data room, and completes the quest")
    public void teamAdminChoosesChallenge2CreateADataRoomAndCompletesTheQuest() {
        selectIntercomChallenge(Quests.challenge2.title, true, false);
        completeCreateDataRoomChallenge(5);
    }

    @Then("Team admin chooses Challenge 3: Set up your team, and completes the quest")
    public void teamAdminChoosesChallengeSetUpYourTeamAndCompletesTheQuest() {
        selectIntercomChallenge(Quests.challenge3.title, true, false);
        completeSetupYourTeamChallenge(5);
    }

    @And("Team member chooses Challenge 1: Send a file, and completes the quest")
    public void teamMemberChoosesChallengeSendAFileAndCompletesTheQuest() {
        selectIntercomChallenge(Quests.challenge1.title, true, true);
        completeSendFileChallenge(5);
    }

    @Then("Team member chooses Challenge 2: Create a data room, and completes the quest")
    public void teamMemberChoosesChallenge2CreateADataRoomAndCompletesTheQuest() {
        selectIntercomChallenge(Quests.challenge2.title, true, true);
        completeCreateDataRoomChallenge(5);
    }

    @Then("Team admin chooses Challenge 1: Send a file, but not completes the quest")
    public void teamAdminChoosesChallenge1SendAFileButNotCompletesTheQuest() {
        selectIntercomChallenge(Quests.challenge1.title, false, false);
        completeSendFileChallenge(5);
    }

    @Then("Team admin chooses Challenge 2: Create a data room but not completes the quest")
    public void teamAdminChoosesChallenge2CreateADataRoomButNotCompletesTheQuest() {
        selectIntercomChallenge(Quests.challenge2.title, false, false);
        completeCreateDataRoomChallenge(5);
    }

    @Then("Team member chooses Challenge 1: Send a file, but not completes the quest")
    public void teamMemberChoosesChallenge1SendAFileButNotCompletesTheQuest() {
        selectIntercomChallenge(Quests.challenge1.title, false, true);
        completeSendFileChallenge(5);
    }

    @Then("Team member chooses Challenge 2: Create a data room but not completes the quest")
    public void teamMemberChoosesChallenge2CreateADataRoomButNotCompletesTheQuest() {
        selectIntercomChallenge(Quests.challenge2.title, false, true);
        completeCreateDataRoomChallenge(5);
    }

    @Then("Team admin chooses Challenge 3: Set up your team, but not completes the quest")
    public void teamAdminChoosesChallenge3SetUpYourTeamButNotCompletesTheQuest() {
        selectIntercomChallenge(Quests.challenge3.title, false, false);
        completeSetupYourTeamChallenge(5);
    }

    @And("Team Admin validates quest challenges when all are already completed")
    public void teamAdminValidateQuestChallengesWhenAllAreCompleted() {
        validateViewQuestDetails(true, true, 3, 3);
        validateTaskStatus("Completed");
    }

    @And("Team admin validate view quest tasks when 1 of 3 task are done")
    public void teamAdminValidateViewQuestTasksHeaderWhenTasksArePending() {
        validateViewQuestDetails(true, true, 1, 3);
        validateTaskStatus("About 4 minutes left");
    }

    @And("Team member validate view quest tasks when all tasks are pending")
    public void teamMemberValidateViewQuestTasksHeaderWhenTasksArePending() {
        validateViewQuestDetails(false, false, 0, 2);
        validateTaskStatus("About 4 minutes");
    }

    @And("Team member validate view quest tasks when all tasks are completed")
    public void teamMemberValidateViewQuestTasksHeaderWhenTasksAreCompleted() {
        validateViewQuestDetails(false, true, 2, 2);
        validateTaskStatus("Completed");
    }

    @And("Team member chooses Challenge 1: Send a file, and exit the tutorial at step 4")
    public void teamMemberChoosesChallengeSendAFileAndExitTheTutorialAtStep() {
        selectIntercomChallenge(Quests.challenge1.title, false, false);
        completeSendFileChallenge(4);
    }

    @Then("Team member chooses Challenge 2: Create a data room, and exit the tutorial at step 4")
    public void teamMemberChoosesChallengeCreateADataRoomAndExitTheTutorialAtStep() {
        selectIntercomChallenge(Quests.challenge2.title, false, false);
        completeCreateDataRoomChallenge(4);
    }

    @Then("Team admin chooses Challenge 3: Set up your team, and exit the tutorial at step 4")
    public void teamAdminChoosesChallengeSetUpYourTeamAndExitTheTutorialAtStep() {
        selectIntercomChallenge(Quests.challenge3.title, false, false);
        completeSetupYourTeamChallenge(4);
    }

    @And("Team admin validate view quest tasks when 3 task are pending")
    public void teamAdminValidateViewQuestTasksWhenTaskArePending() {
        validateViewQuestDetails(true, false, 0, 3);
        validateTaskStatus("About 6 minutes");
    }
}
