package digifyE2E.testDataManager;

import digifyE2E.utils.RandomUtils;

public class PricingUserEmailManager {

    private static String email;

    public static void generateNewEmail() {
        email = "pricinguser-" + RandomUtils.getRandomString(4, false) + "@maildrop.cc";
    }

    public static String getEmail() {
        return email;
    }

    public static void clear() {
        email = null;
    }
}

