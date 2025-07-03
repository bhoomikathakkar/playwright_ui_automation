package digifyE2E.utils;

import java.util.Objects;

public class EnvUtils {
    public static final String ENV = System.getProperty("TEST_ENV");

    public static String getBaseUrl() {

        switch (Objects.nonNull(ENV) ? ENV : "staging") {
            case "prod":
                return "https://digify.com/a";
            case "devmn":
                return "https://staging-x.digifyteam.com/dev/mn";
            case "devgy":
                return "https://staging-x.digifyteam.com/dev/gy";
            case "staging":
            default:
                return "https://staging-x.digifyteam.com/a";
        }
    }

    public static String getTestEnv() {
        return ENV;
    }
}