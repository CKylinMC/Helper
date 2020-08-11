/**
 * @Package: site.ckylin.tools.security
 * @author: CKylinMC
 * @description:
 * @date: 2020-08-06 15:57
 */
package site.ckylin.tools.security;

import java.io.UnsupportedEncodingException;

/**
 * The type Filter.
 */
public class Filter {
    /**
     * HTML转义
     * <a href="https://blog.csdn.net/xiaoxiangshenjian/article/details/75944237">参考自 "java实现HTML标签转义和反转义(StringEscapeUtils)"</a>
     *
     * @param source the source
     * @return the string
     */
    public static String encodeHTML(String source) {
        if (source == null) {
            return "";
        }
        String html = "";
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            switch (c) {
                case '<':
                    buffer.append("&lt;");
                    break;
                case '>':
                    buffer.append("&gt;");
                    break;
                case '&':
                    buffer.append("&amp;");
                    break;
                case '"':
                    buffer.append("&quot;");
                    break;
                case 10:
                case 13:
                    break;
                default:
                    buffer.append(c);
            }
        }
        html = buffer.toString();
        return html;
    }

    /**
     * URL编码
     * <a href="https://www.cnblogs.com/haha12/p/4344992.html">参考自 "java实现url转码、解码"</a>
     *
     * @param str the str
     * @return the string
     */
    public static String encodeURL(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * URL解码
     * <a href="https://www.cnblogs.com/haha12/p/4344992.html">参考自 "java实现url转码、解码"</a>
     *
     * @param str the str
     * @return the string
     */
    public static String decodeURL(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}