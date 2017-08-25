package com.flipkart.lyrics.helper;

import java.util.*;

import static java.lang.Character.isISOControl;

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

    public static void checkState(boolean condition, String format, Object... args) {
        if (!condition) {
            throw new IllegalStateException(String.format(format, args));
        }
    }

    /** Returns the string literal representing {@code value}, including wrapping double quotes. */
    public static String stringLiteralWithDoubleQuotes(String value, String indent) {
        StringBuilder result = new StringBuilder(value.length() + 2);
        result.append('"');
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            // trivial case: single quote must not be escaped
            if (c == '\'') {
                result.append("'");
                continue;
            }
            // trivial case: double quotes must be escaped
            if (c == '\"') {
                result.append("\\\"");
                continue;
            }
            // default case: just let character literal do its work
            result.append(characterLiteralWithoutSingleQuotes(c));
            // need to append indent after linefeed?
            if (c == '\n' && i + 1 < value.length()) {
                result.append("\"\n").append(indent).append(indent).append("+ \"");
            }
        }
        result.append('"');
        return result.toString();
    }

    static String characterLiteralWithoutSingleQuotes(char c) {
        // see https://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.6
        switch (c) {
            case '\b': return "\\b"; /* \u0008: backspace (BS) */
            case '\t': return "\\t"; /* \u0009: horizontal tab (HT) */
            case '\n': return "\\n"; /* \u000a: linefeed (LF) */
            case '\f': return "\\f"; /* \u000c: form feed (FF) */
            case '\r': return "\\r"; /* \u000d: carriage return (CR) */
            case '\"': return "\"";  /* \u0022: double quote (") */
            case '\'': return "\\'"; /* \u0027: single quote (') */
            case '\\': return "\\\\";  /* \u005c: backslash (\) */
            default:
                return isISOControl(c) ? String.format("\\u%04x", (int) c) : Character.toString(c);
        }
    }

    public static <T> Set<T> union(Set<T> a, Set<T> b) {
        Set<T> result = new LinkedHashSet<>();
        result.addAll(a);
        result.addAll(b);
        return result;
    }
}
