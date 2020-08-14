/**
 * @Package: site.ckylin.tools
 * @author: CKylinMC
 * @description: Helper辅助类
 * @date: 2020-06-29 9:58
 */

package site.ckylin.tools;

import org.apache.commons.codec.binary.Base64;
import site.ckylin.tools.varutils.Is;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//import site.ckylin.soso.utils.Utils;

/**
 * 常用工具 辅助类
 */
public class Helper {
    private static final String VERSION = "v0.5";

    /**
     * 等待
     *
     * @param ms 毫秒数
     */
    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * Base64编码
     *
     * @param str 要编码的字符串
     * @return 编码后的字符串
     */
    public static String base64_encode(String str) {
        if (str == null) return "";
        return Base64.encodeBase64String(str.getBytes());
    }

    /**
     * URL安全的Base64编码
     *
     * @param str 要编码的字符串
     * @return 编码后的字符串
     */
    public static String base64_encode(String str, boolean urlSafe) {
        if (!urlSafe) return base64_encode(str);
        return Base64.encodeBase64URLSafeString(str.getBytes());
    }

    /**
     * Base64编码
     *
     * @param bt 要编码的数据
     * @return 编码后的数据
     */
    public static byte[] base64_encode(byte[] bt) {
        return Base64.encodeBase64(bt);
    }

    /**
     * Base64解码
     *
     * @param encoded 已编码的字符串
     * @return 已解码的字符串
     */
    public static String base64_decode(String encoded) {
        if (encoded == null) return "";
        return new String(Base64.decodeBase64(encoded));
    }

    public static void main(String[] args) {
        System.out.println("Helper辅助类 " + VERSION + "\n请在项目中引入：\nimport site.ckylin.Helper;");
    }

    /**
     * 使用默认格式(<code>yyyy-MM-dd</code>)解析日期
     *
     * @param dateStr the date str
     * @return the date
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy-MM-dd");
    }

    /**
     * 解析日期
     *
     * @param dateStr the date str
     * @param format  the format
     * @return the date
     */
    public static Date parseDate(String dateStr, String format) {
        try {
            return new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 返回指定值，若指定值为空则返回另一个值
     *
     * @param <T>      the type parameter
     * @param obj      the obj
     * @param fallback the fallback
     * @return the t
     */
    public static <T> T getOrDefault(T obj, T fallback) {
        if (obj == null) return fallback;
        return obj;
    }

    /**
     * 修复Tomcat传参中文乱码
     *
     * @param str the str
     * @return the string
     */
    public static String fixChinese(String str) {
        return new String(str.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    /**
     * 判断字符串是否为空
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean isEmpty(String value) {
        return Is.empty(value);
    }
}

