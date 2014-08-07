package com.boot.util;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 11/7/13
 * Time: 1:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils {

    /**
     * Make sure it uses across the application!
     * @param str
     * @return
     */
    public static String normalizeEmail(String str) {
        if (str == null) {
            return null;
        }

        return str.trim().toLowerCase();
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return cs != null && cs.length() > 0;
    }


    /**
     * <p>Returns either the passed in CharSequence, or if the CharSequence is
     * empty or {@code null}, the value of {@code defaultStr}.</p>
     *
     * <pre>
     * StringUtils.defaultIfEmpty(null, "NULL")  = "NULL"
     * StringUtils.defaultIfEmpty("", "NULL")    = "NULL"
     * StringUtils.defaultIfEmpty("bat", "NULL") = "bat"
     * StringUtils.defaultIfEmpty("", null)      = null
     * </pre>
     * @param <T> the specific kind of CharSequence
     * @param str  the CharSequence to check, may be null
     * @param defaultStr  the default CharSequence to return
     *  if the input is empty ("") or {@code null}, may be null
     * @return the passed in CharSequence, or the default
     * @see StringUtils#defaultString(String, String)
     */
    public static <T extends CharSequence> T defaultIfEmpty(T str, T defaultStr) {
        return isEmpty(str) ? defaultStr : str;
    }



}
