package com.flipkart.lyrics.helper;

import com.flipkart.lyrics.specs.Modifier;

import java.util.*;

public class Util {
    /**
     * Modifier.DEFAULT doesn't exist until Java 8, but we want to run on earlier releases.
     */
    public static final Modifier DEFAULT;

    static {
        Modifier def = null;
        try {
            def = Modifier.valueOf("DEFAULT");
        } catch (IllegalArgumentException ignored) {
        }
        DEFAULT = def;
    }

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

    public static void checkState(boolean condition, String format, Object... args) {
        if (!condition) {
            throw new IllegalStateException(String.format(format, args));
        }
    }

    public static <T> Set<T> immutableSet(Collection<T> set) {
        return Collections.unmodifiableSet(new LinkedHashSet<>(set));
    }

    public static <K, V> Map<K, V> immutableMap(Map<K, V> map) {
        return Collections.unmodifiableMap(new LinkedHashMap<>(map));
    }
}
