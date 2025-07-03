package digifyE2E.pages.landingPage.constants;

public enum MarkAsCompleteQuest {
    title("Achievement unlocked!", "You've conquered the introductory quests! Now, you're ready to get the most out of Digify.", "Continue exploring", "Mark as complete");

    public final String info1;
    public final String info2;
    public final String info3;
    public final String info4;

    MarkAsCompleteQuest(String info1, String info2, String info3, String info4) {
        this.info1 = info1;
        this.info2 = info2;
        this.info3 = info3;
        this.info4 = info4;
    }
}
