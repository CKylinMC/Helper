/**
 * @Package: site.ckylin.tools.variable
 * @author: CKylinMC
 * @description:
 * @date: 2020-08-07 18:58
 */
package site.ckylin.tools.varutils;

import java.io.UnsupportedEncodingException;

public class Converter {
    /**
     * 字符串转数值型，无法转型时返回0
     *
     * @param str 要转型的字符串
     * @return 数值型
     */
    public static int str2int(String str) {
        return str2int(str, 0);
    }

    /**
     * 字符串转数值型，自定义失败返回值
     *
     * @param str      要转型的字符串
     * @param fallback 失败返回值
     * @return 数值型
     */
    public static int str2int(String str, int fallback) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    /**
     * 字符串转long型，无法转型时返回0
     *
     * @param str 要转型的字符串
     * @return long型
     */
    public static long str2long(String str) {
        return str2long(str, 0);
    }

    /**
     * 字符串转long型，自定义失败返回值
     *
     * @param str      要转型的字符串
     * @param fallback 失败返回值
     * @return long型
     */
    public static long str2long(String str, long fallback) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    /**
     * 字符串转double型，无法转型时返回0
     *
     * @param str 要转型的字符串
     * @return double型
     */
    public static double str2double(String str) {
        return str2double(str, 0);
    }

    /**
     * 字符串转double型，自定义失败返回值
     *
     * @param str      要转型的字符串
     * @param fallback 失败返回值
     * @return double型
     */
    public static double str2double(String str, double fallback) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    /**
     * 字符串转float型，无法转型时返回0
     *
     * @param str 要转型的字符串
     * @return float型
     */
    public static float str2float(String str) {
        return str2float(str, 0);
    }

    /**
     * 字符串转float型，自定义失败返回值
     *
     * @param str      要转型的字符串
     * @param fallback 失败返回值
     * @return float型
     */
    public static float str2float(String str, float fallback) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    /**
     * 转换字符串编码
     *
     * @param str       要转码的字符串
     * @param fromCodec 源编码
     * @param toCodec   目标编码
     * @return 转码后的字符串
     */
    public static String iconv(String str, String fromCodec, String toCodec) {
        try {
            return new String(str.getBytes(fromCodec), toCodec);
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    /**
     * 把中文转成Unicode码
     * <a href="https://blog.csdn.net/dxswzj/article/details/38416311">参考自: "java后台传数据到前台乱码问题 中文转成Unicode码"</a>
     *
     * @param str chineseChar
     * @return result
     */
    public static String chineseToUnicode(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int chr1 = (char) str.charAt(i);
            if ((chr1 >= 19968 && chr1 <= 171941) || chr1 == 12289) {//汉字范围 \u4e00-\u9fa5 (中文)  12289 是“、”
                result += "\\u" + Integer.toHexString(chr1);
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }
}
