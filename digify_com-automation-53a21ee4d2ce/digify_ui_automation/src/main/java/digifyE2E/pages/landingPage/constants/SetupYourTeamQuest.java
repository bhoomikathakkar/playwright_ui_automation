package digifyE2E.pages.landingPage.constants;

public enum SetupYourTeamQuest {
    step1("Set up Digify with your brand logo and colors for a consistent, professional look that boosts brand recognition.", "Click on Edit Logo & Colors to begin.", "1 of 5", ""),
    step2("Enhance your branding by replacing the Digify logo with your own.", "Expand this section to upload your logo.", "2 of 5", ""),
    step3("Bring team members on board for collaborative efforts.", "Click on Members to continue.", "3 of 5", ""),
    step4("Whenever you're ready, add your new team members here.", "4 of 5", "", ""),
    step5("Now, it's your turn to give it a go!", "Complete these challenges to secure your files and set up your team on Digify.", "5 of 5", "");


    public final String info1;
    public final String info2;
    public final String info3;
    public final String info4;

    SetupYourTeamQuest(String info1, String info2, String info3, String info4) {
        this.info1 = info1;
        this.info2 = info2;
        this.info3 = info3;
        this.info4 = info4;
    }
}
