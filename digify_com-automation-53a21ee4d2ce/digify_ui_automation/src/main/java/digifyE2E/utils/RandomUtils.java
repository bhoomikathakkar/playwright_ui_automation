package digifyE2E.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomUtils {

    /**
     * @param length   no of length required
     * @param isNumber if it is a number or an alphanumeric
     * @return
     */
    public static String getRandomString(int length, boolean isNumber) {
        if (isNumber) {
            return RandomStringUtils.random(length, false, true);
        } else {
            return RandomStringUtils.random(length, true, true);
        }
    }


    //TODO: should not receive random string
    public static String convertHexToRgbString(String hex) {
        hex = hex.startsWith("#") ? hex.substring(1) : hex;
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        return String.format("rgb(%d, %d, %d)", r, g, b);
    }


}
