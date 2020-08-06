/**
 * @Package: site.ckylin.security
 * @author: CKylinMC
 * @description:
 * @date: 2020-08-06 15:35
 */
package site.ckylin.security;

import site.ckylin.Helper;

import java.util.regex.Pattern;

public class Validator {
    public static boolean checkMobilePhone(String phone) {
        return Pattern.matches("^1[0-9]{10}$", phone);
    }

    public static boolean checkEmail(String email) {
        return Pattern.matches("[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+", email);
    }

    public static int passwordRater(String pass) {
        if (pass.length() < 6) return -1;
        if (Helper.isNaN(pass)) return -1;
        int rate = 0;
        if (pass.length() > 10) rate++;
        if (Pattern.matches("[0-9]", pass)) rate++;
        if (Pattern.matches("[a-z]", pass)) rate++;
        if (Pattern.matches("[A-Z]", pass)) rate++;
        if (Pattern.matches("[`~!@#$^&*()=|{}':;',\\\\[\\\\].<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？ ]", pass)) rate++;
        if (pass.contains("12345") || pass.contains("54321") || pass.contains("passw") || pass.contains("abcd")) rate--;
        if (rate < 0) rate = 0;
        return rate;
    }
}
