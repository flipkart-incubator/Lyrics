package com.flipkart.lyrics.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class Util {
    public static void checkArgument(boolean condition, String format, Object... args) {
        if (!condition) {
            throw new IllegalArgumentException(String.format(format, args));
        }
    }

    public static String join(String separator, List<String> parts) {
        if (parts.isEmpty()) {
            return "";
        } else {
            StringBuilder result = new StringBuilder();
            result.append(parts.get(0));

            for (int i = 1; i < parts.size(); ++i) {
                result.append(separator).append(parts.get(i));
            }

            return result.toString();
        }
    }

    public static <T> T checkNotNull(T reference, String format, Object... args) {
        if (reference == null) {
            throw new NullPointerException(String.format(format, args));
        } else {
            return reference;
        }
    }

    public static <T> List<T> immutableList(Collection<T> collection) {
        return Collections.unmodifiableList(new ArrayList<>(collection));
    }
}
