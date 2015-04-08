package ee.esutoniagodesu.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public final class JCString {

    /**
     * Asendab Stringis \n tühikuga
     */
    public static String newlineRemoved(String string) {
        if (string == null) return null;
        return string.replaceAll("\n", " ");
    }

    public static String removeTailSmart(String s, int removeFrom, String replacement) {
        if (s == null || s.length() < 1 || removeFrom < 0) return s;
        if (s.length() > removeFrom) {
            int t = s.indexOf(" ", removeFrom);
            if (t == -1) t = s.indexOf("\t", removeFrom);
            if (t == -1) t = s.indexOf("\n", removeFrom);

            if (t > 0 && t < removeFrom + 20) {
                s = s.substring(0, t);
            } else {
                s = s.substring(0, removeFrom);
            }

            if (replacement != null) s += " " + replacement;
        }
        return s;
    }

    public static boolean isOverweight(String s, int limit) throws IllegalArgumentException, UnsupportedEncodingException {
        if (limit < 1) throw new IllegalArgumentException("bytes < 1");
        if (s == null) return false;
        byte[] b = s.getBytes("UTF-8");
        return b.length > limit;
    }

    public static String getExtension(String string) {
        if (string == null) return null;
        String extension = "";
        int i = string.lastIndexOf('.');
        if (i > 0) {
            extension = string.substring(i + 1);
        }
        return extension;
    }

    public static String[] trim(String[] arr) {
        for (int i = 0; i < arr.length; i++)
            arr[i] = arr[i].trim();

        return arr;
    }

    public static String replaceLast(String string, String from, String to) {
        int lastIndex = string.lastIndexOf(from);
        if (lastIndex < 0) return string;
        String tail = string.substring(lastIndex).replaceFirst(from, to);
        return string.substring(0, lastIndex) + tail;
    }

    public static String join(String delimiter, Serializable... strings) {
        StringBuilder result = null;
        for (Serializable s : strings) {
            if (s != null) {
                if (result == null) {
                    result = new StringBuilder(s.toString());
                } else {
                    result.append(delimiter).append(s);
                }
            }
        }

        return result != null && result.length() > 0 ? result.toString() : null;
    }
}
