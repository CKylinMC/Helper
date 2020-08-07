/**
 * @Package: site.ckylin.variable
 * @author: CKylinMC
 * @description:
 * @date: 2020-08-07 18:59
 */
package site.ckylin.variable;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayUtils {
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
}
