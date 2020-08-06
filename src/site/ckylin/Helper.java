/**
 * @Package: site.ckylin
 * @author: CKylinMC
 * @description: Helper辅助类
 * @date: 2020-06-29 9:58
 */

package site.ckylin;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//import site.ckylin.soso.utils.Utils;

/**
 * 常用工具 辅助类
 */
public class Helper {
    private static final String VERSION = "v0.3";
    /**
     * 是否使用控制字符清屏
     */
    public static boolean CLS_BY_CTLCHAR_MODE = true;

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
     * 菜单工具方法选择
     *
     * @param min        最小索引
     * @param max        最大索引(最大可输入数+1)
     * @param selections 所有选项(剩余所有选项)
     * @return 输入的选择
     */
    public static synchronized int select(int min, int max, String... selections) {
        if (selections.length == 0) {
            return 0;
        }
        for (String selection : selections) {
            System.out.println("\t" + selection);
        }
        System.out.println("==============================");
        return inputInt("请输入选择: ", min, max);
    }

    /**
     * 菜单工具方法选择
     *
     * @param min        最小索引
     * @param max        最大索引(最大可输入数+1)
     * @param selections 所有选项(剩余所有选项)
     * @return 输入的选择
     */
    public static synchronized int selects(int min, int max, String[] selections) {
        if (selections.length == 0) {
            return 0;
        }
        for (String selection : selections) {
            System.out.println("\t" + selection);
        }
        System.out.println("==============================");
        return inputInt("请输入选择: ", min, max);
    }

    /**
     * 暂停等待用户
     */
    public static synchronized void pause() {
        pause("回车返回");
    }

    /**
     * 暂停等待用户
     *
     * @param tip 暂停时要显示的提示
     */
    public static void pause(String tip) {
        System.out.println(tip);
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }

    /**
     * 字符串输入工具方法 支持最小、最大长度验证
     *
     * @param tip       字符串输入提示
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @return 字符串结果
     */
    public static synchronized String inputString(String tip, int minLength, int maxLength) {
        while (true) {
            String str = inputString(tip);
            if (str.length() > maxLength) {
                System.out.println("字数过多(<=" + maxLength + ")");
                continue;
            }
            if (str.length() < minLength) {
                System.out.println("字数过少(>=" + minLength + ")");
                continue;
            }
            return str;
        }
    }

    /**
     * 字符串输入工具方法 支持最大长度
     *
     * @param tip       字符串输入提示
     * @param maxLength 最大长度
     * @return 字符串结果
     */
    public static synchronized String inputString(String tip, int maxLength) {
        while (true) {
            String str = inputString(tip);
            if (str.length() > maxLength) {
                System.out.println("字数过多(<=" + maxLength + ")");
                continue;
            }
            return str;
        }
    }

    /**
     * 字符串输入工具方法
     *
     * @param tip 字符串输入提示
     * @return 字符串结果
     */
    public static synchronized String inputString(String tip) {
        System.out.print(tip);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * 隐式输入，如：密码  效果类似Linux登陆 支持最大长度
     *
     * @param tip       输入提示
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @return 输入的结果
     */
    public static synchronized String inputInvisibly(String tip, int minLength, int maxLength) {
        while (true) {
            String str = inputInvisibly(tip);
            if (str.length() > maxLength) {
                System.out.println("字数过多(<=" + maxLength + ")");
                continue;
            }
            if (str.length() < minLength) {
                System.out.println("字数过少(>=" + minLength + ")");
                continue;
            }
            return str;
        }
    }

    /**
     * 隐式输入，如：密码  效果类似Linux登陆
     *
     * @param tip 输入提示
     * @return 输入的结果
     */
    public static synchronized String inputInvisibly(String tip) {
        Console console = System.console();
        return new String(console.readPassword(tip));
    }

    /**
     * 数值输入，支持最小值限制
     *
     * @param tip 输入提示
     * @param min 最小值
     * @return 输入的数字
     */
    public static synchronized int inputInt(String tip, int min) {
        while (true) {
            int input = inputInt(tip);
            if (input >= min) {
                return input;
            }
            System.out.println("输入超过范围(>=" + min + ")!");
        }
    }

    /**
     * 数值输入，支持数值范围限制
     *
     * @param tip 输入提示
     * @param min 最小值
     * @param max 最大值
     * @return 输入的数字
     */
    public static synchronized int inputInt(String tip, int min, int max) {
        while (true) {
            int input = inputInt(tip);
            if (input >= min && input < max) {
                return input;
            }
            System.out.println("输入超过范围(" + min + "-" + (max - 1) + ")!");
        }
    }

    /**
     * 数值输入
     *
     * @param tip 输入提示
     * @return 输入的数字
     */
    public static synchronized int inputInt(String tip) {
        while (true) {
            System.out.print(tip);
            Scanner sc = new Scanner(System.in);
            try {
                return sc.nextInt();
            } catch (Exception e) {
                System.out.println("请输入正确的数字!");
            }
        }
    }

    /**
     * 浮点数输入方法，支持最小值限制
     *
     * @param tip 输入提示
     * @param min 最小值
     * @return 输入的浮点数
     */
    public static synchronized float inputFloat(String tip, float min) {
        while (true) {
            float input = inputFloat(tip);
            if (input >= min) {
                return input;
            }
            System.out.println("输入超过范围(>=" + min + ")!");
        }
    }

    /**
     * 浮点数输入方法，支持范围限制
     *
     * @param tip 输入提示
     * @param min 最小值
     * @param max 最大值
     * @return 输入的浮点数
     */
    public static synchronized float inputFloat(String tip, float min, float max) {
        while (true) {
            float input = inputFloat(tip);
            if (input >= min && input < max) {
                return input;
            }
            System.out.println("输入超过范围(" + min + "-" + (max - 1) + ")!");
        }
    }

    /**
     * 浮点数输入方法
     *
     * @param tip 输入提示
     * @return 输入的浮点数
     */
    public static synchronized float inputFloat(String tip) {
        while (true) {
            System.out.print(tip);
            Scanner sc = new Scanner(System.in);
            try {
                return sc.nextFloat();
            } catch (Exception e) {
                System.out.println("请输入正确的数字!");
            }
        }
    }

    /**
     * 默认为TRUE标记
     */
    public static final int CONFIRM_TRUE = 1;
    /**
     * 默认为FALSE标记
     */
    public static final int CONFIRM_FALSE = 0;
    /**
     * 无默认值标记
     */
    public static final int CONFIRM_IGNORED = -1;

    /**
     * 请求确认
     *
     * @param tip 输入提示
     * @return 是否确认
     */
    public static synchronized boolean confirm(String tip) {
        return confirm(tip, CONFIRM_IGNORED);
    }

    /**
     * 请求确认，支持默认值直接回车
     *
     * @param tip        输入提示
     * @param defaultVal 默认值标记
     * @return 是否确认
     */
    public static synchronized boolean confirm(String tip, int defaultVal) {
        while (true) {
            String optTip = String.format(" [%s/%s]", defaultVal == CONFIRM_TRUE ? "Y" : "y",
                    defaultVal == CONFIRM_FALSE ? "N" : "n");
            String str = inputString(tip + optTip, defaultVal != CONFIRM_IGNORED ? 0 : 1, 1).toLowerCase();
            if (str.startsWith("y")) {
                return true;
            }
            if (str.startsWith("n")) {
                return false;
            }
            if (defaultVal != CONFIRM_IGNORED) {
                return defaultVal == CONFIRM_TRUE;
            }
        }
    }

    /**
     * 清屏
     */
    public static synchronized void cls() {
        if (CLS_BY_CTLCHAR_MODE) System.out.println("\033[2J\033[H");
        else for (int i = 0; i < 200; i++) System.out.println();
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
     * 创建文件夹
     *
     * @param name 文件夹路径
     * @return 是否成功
     */
    public static boolean mkdirs(String name) {
        return new File(name).mkdirs();
    }

    /**
     * 创建空白文件
     *
     * @param path 文件路径
     * @return 返回是否执行了创建
     */
    public static synchronized boolean touch(String path) {
        File f = new File(path);
        if (f.exists()) {
            return false;
        }
        try {
            return f.createNewFile();
        } catch (IOException ignored) {
            return false;
        }
    }

    /**
     * 读取文件内容
     *
     * @param path 文件路径
     * @return 文件内容
     */
    public static synchronized String readFile(String path) {
        File f = new File(path);
        if (!f.exists() || !f.isFile()) {
            //Utils.error("File not found.");
            return null;
        }
        StringBuilder contents = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(f))) {
            String line;
            boolean firstLine = true;
            while ((line = bufferedReader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                } else {
                    contents.append("\n");
                }
                contents.append(line);
            }
        } catch (IOException e) {
            //Utils.error("Errored while opening file.");
            e.printStackTrace();
        }
        return contents.toString();
    }

    /**
     * 写入文件
     *
     * @param path  文件路径
     * @param lines 每行内容组成的数组
     */
    public static synchronized void writeFile(String path, String[] lines) {
        touch(path);
        try (PrintStream ps = new PrintStream(path)) {
            for (String line : lines) {
                ps.println(line);
            }
        } catch (FileNotFoundException ignored) {
        }
    }

    /**
     * 加载配置
     *
     * @param path 配置文件路径
     * @return 配置对象
     */
    public static synchronized Properties loadProperties(String path) {
        File f = new File(path);
        if (!f.exists() || !f.isFile()) {
            //Utils.error("Properties file not found.");
            return null;
        }
        Properties properties = new Properties();
        try (FileInputStream fs = new FileInputStream(f)) {
            properties.load(fs);
        } catch (IOException e) {
            //Utils.error("Errored while loading properties");
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 从类资源加载配置
     *
     * @param path   配置文件路径
     * @param loader 对应类的ClassLoader <br> <b>示范：</b><br>Static: <code> ClassName.class.getClassLoader() </code><br>Instance: <code> this.getClass() </code>
     * @return 配置对象
     */
    public static synchronized Properties loadPropertiesAsClassResource(String path, ClassLoader loader) {
        Properties properties = new Properties();
        try (InputStream is = loader.getResourceAsStream(path)) {
            properties.load(is);
        } catch (IOException e) {
            //Utils.error("Errored while loading properties");
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 从类资源加载配置
     *
     * @param path  配置文件路径
     * @param clazz 对应类的ClassLoader <br> <b>示范：</b><br>Static: <code> ClassName.class.getClassLoader() </code><br>Instance: <code> this.getClass() </code>
     * @return 配置对象
     */
    public static synchronized Properties loadPropertiesAsClassResource(String path, Class<?> clazz) {
        Properties properties = new Properties();
        try (InputStream is = clazz.getResourceAsStream(path)) {
            properties.load(is);
        } catch (IOException e) {
            //Utils.error("Errored while loading properties");
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 保存配置
     *
     * @param properties 配置对象
     * @param path       配置文件位置
     */
    public static synchronized void saveProperties(Properties properties, String path) {
        saveProperties(properties, path, "Properties file");
    }

    /**
     * 保存配置
     *
     * @param properties 配置对象
     * @param path       配置文件位置
     * @param comment    配置文件注释
     */
    public static synchronized void saveProperties(Properties properties, String path, String comment) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            properties.store(fos, comment);
        } catch (IOException e) {
            //Utils.debug("Errored while saving properties.");
            e.printStackTrace();
        }
    }

    /**
     * 将元素插入到任意类型数组的第一个位置
     *
     * @param <T>  类型
     * @param arr  数组
     * @param item 要插入的元素
     * @return 插入后的数组
     */
    public static synchronized <T> T[] insertBefore(T[] arr, T item) {
        T[] newarr = Arrays.copyOf(arr, arr.length + 1);
        newarr[0] = item;
        System.arraycopy(arr, 0, newarr, 1, arr.length);//My IDEA Editor replaced it from a for-loop...
        return newarr;
    }

    /**
     * 为数组添加标号
     *
     * @param arr        数组
     * @param startIndex 起始标号
     * @return 已插入标号的数组
     */
    public static synchronized String[] addIndex(String[] arr, int startIndex) {
        for (int i = startIndex; i < arr.length; i++) {
            arr[i] = i + ". " + arr[i];
        }
        return arr;
    }

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
     * Base64编码
     *
     * @param str 要编码的字符串
     * @return 编码后的字符串
     */
    public static String base64_encode(String str) {
        if (str == null) return "";
        return Base64.encode(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Base64解码
     *
     * @param encoded 已编码的字符串
     * @return 已解码的字符串
     */
    public static String base64_decode(String encoded) {
        if (encoded == null) return "";
        try {
            return new String(Base64.decode(encoded), StandardCharsets.UTF_8);
        } catch (Base64DecodingException e) {
            //Utils.error("Base64 decoding: failed.");
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
        System.out.println("Helper辅助类 " + VERSION + "\n请在项目中引入：\nimport site.ckylin.Helper;");
    }

    /**
     * 字符串快速转数组
     *
     * @param str the str
     * @return the array list
     */
    public static ArrayList<String> toArr(String... str) {
        return new ArrayList<>(Arrays.asList(str));
    }

    /**
     * 数组转字符串
     *
     * @param arr     the arr
     * @param joinStr the join str
     * @return the string
     */
    public static String arrayJoin(ArrayList<String> arr, String joinStr) {
        StringBuilder result = new StringBuilder();
        if (arr.size() == 1) {
            return arr.get(0);
        }
        if (arr.size() == 0) {
            return "";
        }
        for (int i = 0; i < arr.size(); i++) {
            String thisJoinString = i == arr.size() - 1 ? "" : joinStr;
            result.append(arr.get(i)).append(thisJoinString);
        }
        return result.toString();
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

