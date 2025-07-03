package digifyE2E.cucumber.stepDefinitions.stepHelpers;

import digifyE2E.pages.AdminSettings.AdminSettingsPage;
import digifyE2E.pages.Base;
import digifyE2E.pages.chargbee.ChargbeePage;
import digifyE2E.pages.dataRooms.CreateDataRoomPage;
import digifyE2E.pages.dataRooms.ManageDataRoomPage;
import digifyE2E.pages.documentSecurity.ManageSentFilesPage;
import digifyE2E.pages.documentSecurity.SendFilesPage;
import digifyE2E.pages.documentSecurity.ViewReceivedFilesPage;
import digifyE2E.pages.landingPage.HomePage;
import digifyE2E.pages.landingPage.LoginPage;
import digifyE2E.pages.landingPage.PricingPage;
import digifyE2E.pages.landingPage.TrialPage;


public class CommonHelper extends Base {
    public LoginPage loginPage = new LoginPage();
    public HomePage homePage = new HomePage();
    public TrialPage trialPage = new TrialPage();
    public ManageSentFilesPage manageSentFilesPage = new ManageSentFilesPage();
    public SendFilesPage sendFilesPage = new SendFilesPage();
    public PricingPage pricingPage = new PricingPage();
    public ChargbeePage chargbeePage = new ChargbeePage();
    public AdminSettingsPage adminSettingsPage = new AdminSettingsPage();
    public CreateDataRoomPage createDataRoomPage = new CreateDataRoomPage();
    public ManageDataRoomPage manageDataRoomPage = new ManageDataRoomPage();
    public ViewReceivedFilesPage viewReceivedFilesPage = new ViewReceivedFilesPage();

}