package com.ivitera.velocity.validator.utils;

public class StringUtils {
    public static String join(Iterable<?> values, String delimiter) {
        StringBuilder buf = new StringBuilder();
        boolean first = true;
        for (Object o : values) {
            if (first) {
                first = false;
            } else {
                buf.append(delimiter);
            }
            buf.append(o);
        }
        return buf.toString();
    }
}
