package digifyE2E.pages.landingPage.constants;

public enum SendFileQuest {
    step1("Explore our all-in-one Document Security for quick and secure file sharing.", "Click Send Files now!", "1 of 5", ""),
    step2("Upload your files", ", or import files from your existing Digify data rooms or other cloud storage providers.", "2 of 5", ""),
    step3("Dynamic Watermark", "Use our Dynamic Watermark feature to automatically mark sensitive documents with recipient’s information to deter unauthorized sharing.", "3 of 5", ""),
    step4("Choose how you want to send your files.", "“Send & notify recipients” sends an email notification to your recipients via Digify.", "“Get link & skip notification” gives you a secure link that you can share directly with your recipients outside Digify.", "4 of 5"),
    step5("Now, it's your turn to give it a go!", "Experience Document Security’s robust protection and file insights", "5 of 5", "");


    public final String info1;
    public final String info2;
    public final String info3;
    public final String info4;

    SendFileQuest(String info1, String info2, String info3, String info4) {
        this.info1 = info1;
        this.info2 = info2;
        this.info3 = info3;
        this.info4 = info4;
    }
}
