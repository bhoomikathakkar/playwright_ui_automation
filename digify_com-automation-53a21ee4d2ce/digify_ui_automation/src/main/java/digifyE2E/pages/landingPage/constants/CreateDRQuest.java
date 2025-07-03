package digifyE2E.pages.landingPage.constants;

public enum CreateDRQuest {
    step1("Explore our secure data room for file sharing, and granular access control.", "Click Create Data Rooms to get started!", "1 of 5", ""),
    step2("Set a Default Guest Permission so that new guests without assigned permissions won’t have more access to your data room than intended.", "2 of 5", "", ""),
    step3("Customize the branding of your data room with an About page to communicate important information to your guests.", "3 of 5", "", ""),
    step4("Enable Question & Answer to allow guests to get clarifications on your data room files.", "4 of 5", "", ""),
    step5("Now, it's your turn to give it a go!", "Discover Digify's Data Rooms for secure sharing and managing of files with granular permissions", "5 of 5", "Create a data room now");


    public final String info1;
    public final String info2;
    public final String info3;
    public final String info4;

    CreateDRQuest(String info1, String info2, String info3, String info4) {
        this.info1 = info1;
        this.info2 = info2;
        this.info3 = info3;
        this.info4 = info4;
    }
}
