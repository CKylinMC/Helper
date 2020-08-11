/**
 * @Package: site.ckylin.tools.varutils
 * @author: CKylinMC
 * @description:
 * @date: 2020-08-11 12:26
 */
package site.ckylin.tools.varutils;

import java.util.Collection;

/**
 * The type Any.
 */
public class Any {
    /**
     * Contains boolean.
     *
     * @param search the search
     * @param coll   the coll
     * @return the boolean
     */
    public static boolean contains(String search, Collection<String> coll) {
        for (String item : coll) {
            if (Is.empty(item)) continue;
            if (item.contains(search)) return true;
        }
        return false;
    }

    /**
     * Contains boolean.
     *
     * @param search the search
     * @param coll   the coll
     * @return the boolean
     */
    public static boolean contains(String search, String... coll) {
        for (String item : coll) {
            if (Is.empty(item)) continue;
            if (item.contains(search)) return true;
        }
        return false;
    }

    /**
     * Starts with boolean.
     *
     * @param search the search
     * @param coll   the coll
     * @return the boolean
     */
    public static boolean startsWith(String search, Collection<String> coll) {
        for (String item : coll) {
            if (Is.empty(item)) continue;
            if (item.startsWith(search)) return true;
        }
        return false;
    }

    /**
     * Starts with boolean.
     *
     * @param search the search
     * @param coll   the coll
     * @return the boolean
     */
    public static boolean startsWith(String search, String... coll) {
        for (String item : coll) {
            if (Is.empty(item)) continue;
            if (item.startsWith(search)) return true;
        }
        return false;
    }

    /**
     * Ends with boolean.
     *
     * @param search the search
     * @param coll   the coll
     * @return the boolean
     */
    public static boolean endsWith(String search, Collection<String> coll) {
        for (String item : coll) {
            if (Is.empty(item)) continue;
            if (item.endsWith(search)) return true;
        }
        return false;
    }

    /**
     * Ends with boolean.
     *
     * @param search the search
     * @param coll   the coll
     * @return the boolean
     */
    public static boolean endsWith(String search, String... coll) {
        for (String item : coll) {
            if (Is.empty(item)) continue;
            if (item.endsWith(search)) return true;
        }
        return false;
    }

    /**
     * Starts with boolean.
     *
     * @param needle the needle
     * @param coll   the coll
     * @return the boolean
     */
    public static boolean startsWithAny(String needle, Collection<String> coll) {
        for (String item : coll) {
            if (Is.empty(item)) continue;
            if (needle.startsWith(item)) return true;
        }
        return false;
    }

    /**
     * Starts with boolean.
     *
     * @param needle the needle
     * @param coll   the coll
     * @return the boolean
     */
    public static boolean startsWithAny(String needle, String... coll) {
        for (String item : coll) {
            if (Is.empty(item)) continue;
            if (needle.startsWith(item)) return true;
        }
        return false;
    }

    /**
     * Ends with boolean.
     *
     * @param needle the needle
     * @param coll   the coll
     * @return the boolean
     */
    public static boolean endsWithAny(String needle, Collection<String> coll) {
        for (String item : coll) {
            if (Is.empty(item)) continue;
            if (needle.endsWith(item)) return true;
        }
        return false;
    }

    /**
     * Ends with boolean.
     *
     * @param needle the needle
     * @param coll   the coll
     * @return the boolean
     */
    public static boolean endsWithAny(String needle, String... coll) {
        for (String item : coll) {
            if (Is.empty(item)) continue;
            if (needle.endsWith(item)) return true;
        }
        return false;
    }
}
