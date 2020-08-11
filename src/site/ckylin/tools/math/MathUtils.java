/**
 * @Package: site.ckylin.tools.math
 * @author: CKylinMC
 * @description:
 * @date: 2020-08-07 18:52
 */
package site.ckylin.tools.math;

public class MathUtils {
    /**
     * 生成范围内随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机数整数
     */
    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    /**
     * 概率事件方法
     *
     * @param chance 概率(百分之几)
     * @return 判定是否成功
     */
    public static boolean hasChance(int chance) {
        return (Math.random() * 100) + 1 <= chance;
    }

    /**
     * 阶乘
     *
     * @param num   阶乘值
     * @param count 阶数
     * @return 结果
     */
    public static int factorial(int num, int count) {
        if (--count <= 0) return num;
        return num * factorial(num, count);
    }

    /**
     * 任意长度随机整数字符串生成
     *
     * @param len 长度
     * @return 数值字符串
     */
    public static String intGenerator(int len) {
        int min = factorial(10, len - 1);
        int max = min * 10;
        return (int) (Math.random() * (max - min + 1) + min) + "";
    }

    /**
     * 小数剪裁
     *
     * @param floatValue 要操作的小数
     * @param length     小数后保留长度
     * @return 剪裁后的小数
     */
    public static float decimalLength(float floatValue, int length) {
        int len = factorial(10, length);
        return (float) Math.floor(floatValue * len) / len;
    }


    /**
     * 修复小数精度缺失
     *
     * @param decimal 要修复的小数
     * @return 修复后的小数
     */
    public static double fixDecimal(double decimal) {
        decimal *= 100;
        decimal = Math.round(decimal);
        return decimal / 100;
    }

    /**
     * 检查字符串是否不是数字
     * <a href="https://www.cnblogs.com/gaoyoubo/archive/2010/09/15/1965080.html">参考自 "Java实现isNaN - Ｍe疯子_(~ 的博客"</a>
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean isNaN(String value) {
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
