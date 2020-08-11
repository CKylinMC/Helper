/**
 * @Package: site.ckylin.tools.varutils
 * @author: CKylinMC
 * @description:
 * @date: 2020-08-11 13:05
 */
package site.ckylin.tools.varutils;

public class Is {
    /**
     * 判断字符串是否为空
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean empty(String value) {
        if (value == null) return true;
        return value.trim().length() < 1;
    }

    /**
     * 检查字符串是否不是数字
     * <a href="https://www.cnblogs.com/gaoyoubo/archive/2010/09/15/1965080.html">参考自 "Java实现isNaN - Ｍe疯子_(~ 的博客"</a>
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean NaN(String value) {
        boolean flag = false;
        if (value != null && !"".equals(value.trim())) {
            //如果没有小数点
            if (!value.contains(".")) {
                if (!value.matches("\\d+")) {
                    //不是数字
                    flag = true;
                }
            } else if (value.indexOf(".") == 1) {
                String[] s_arr = value.split("\\.");
                String s1 = s_arr[0];
                String s2 = s_arr[1];
                if ((!s1.matches("\\d+")) || (!s2.matches("\\d+"))) {
                    //不是数字
                    flag = true;
                }
            }
        } else {
            flag = true;
        }
        return flag;
    }


}
