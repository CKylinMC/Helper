/**
 * @Package: site.ckylin.tools.console
 * @author: CKylinMC
 * @description:
 * @date: 2020-08-07 18:50
 */
package site.ckylin.tools.console;

import java.io.Console;
import java.util.Scanner;

public class Input {
    /**
     * 是否使用控制字符清屏
     */
    public static boolean CLS_BY_CTLCHAR_MODE = true;

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
}
