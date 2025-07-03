package digifyE2E.pages.landingPage.constants;

public enum Quests {
    challenge1("Challenge 1: Send a file", "Manage access, distribution and tracking of your files easily with Document Security.", "Send a file to complete this challenge.", ""),
    challenge2("Challenge 2: Create a data room", "Manage and safeguard a large number of files with granular access permissions.", "Create a data room to complete this challenge.", "Manage and safeguard a large number of files with granular permissions."),
    challenge3("Challenge 3: Set up your team", "Upload your company logo, and invite your coworkers to your team.", "", "");

    public final String title;
    public final String info1;
    public final String info2;
    public final String tmInfo;

    Quests(String title, String info1, String info2, String tmInfo) {
        this.title = title;
        this.info1 = info1;
        this.info2 = info2;
        this.tmInfo = tmInfo;
    }
}
