package digifyE2E.testDataManager;


import digifyE2E.cucumber.stepDefinitions.stepHelpers.CommonHelper;
import digifyE2E.utils.RandomUtils;
import lombok.Getter;
import lombok.Setter;
import java.nio.file.Path;

public class SharedDM {
    @Getter
    private static final String DataRoomName = "DRTest" + "-" + RandomUtils.getRandomString(4, false);
    @Getter
    private static final String ClonedDataRoomName = "CLD-DRTest" + "-" + RandomUtils.getRandomString(3, false);
    @Getter
    @Setter
    private static String sentFileLink;
    @Getter
    @Setter
    private static String newFileName;
    @Getter
    @Setter
    private static Path newFilePath;
    @Getter
    @Setter
    private static String newFileNameWithExtension;
    @Getter
    @Setter
    private static String sentDataRoomLink;
    @Getter
    @Setter
    private static String sentDRFileOrFolderLink;
    @Getter
    @Setter
    private static String planQuotationLink;
    @Getter
    @Setter
    private static String receivedFileLink;

    //for API steps
    public static ThreadLocal<String> fileGUID=new ThreadLocal<>();
    public static ThreadLocal<String> preAuthUrl=new ThreadLocal<>();
    public static ThreadLocal<String> dataRoomGuid=new ThreadLocal<>();
    public static ThreadLocal<String> dataRoomFileGUID=new ThreadLocal<>();
    public static ThreadLocal<String> svcDocumentSecurityFile=new ThreadLocal<>();
    public static ThreadLocal<String> dataRoomGroupGuid=new ThreadLocal<>();
}
