package com.ivitera.velocity.validator.utils;

import java.util.*;

/**
 * Pomocne metody pro praci se seznamy.
 */
public class Lists {

    /**
     * Vytvori {@link ArrayList} ze vsech zadanych hodnot, ktere nejsou {@code null}.
     * Nikdy nevraci {@code null} a nevyhazuje NPE.
     */
    public static <T> ArrayList<T> arrayList(T... values) {
        return arrayList(values == null ? Collections.<T>emptyList() : Arrays.asList(values));
    }

    /**
     * Vytvori {@link ArrayList} ze vsech zadanych hodnot, ktere nejsou {@code null}.
     * Nikdy nevraci {@code null} a nevyhazuje NPE.
     */
    public static <T> ArrayList<T> arrayList(Collection<? extends T> values) {
        ArrayList<T> result = new ArrayList<T>();
        if (values != null) {
            for (T value : values) {
                if (value != null) {
                    result.add(value);
                }
            }
        }
        return result;
    }

    /**
     * Vytvori prazdny {@link LinkedList}.
     */
    public static <T> LinkedList<T> linkedList() {
        return new LinkedList<T>();
    }

    /**
     * Vytvori {@link LinkedList} ze vsech zadanych hodnot, ktere nejsou {@code null}.
     * Nikdy nevraci {@code null} a nevyhazuje NPE.
     */
    public static <T> LinkedList<T> linkedList(T... values) {
        return linkedList(values == null ? Collections.<T>emptyList() : Arrays.asList(values));
    }

    /**
     * Vytvori {@link LinkedList} ze vsech zadanych hodnot, ktere nejsou {@code null}.
     * Nikdy nevraci {@code null} a nevyhazuje NPE.
     */
    public static <T> LinkedList<T> linkedList(Collection<? extends T> values) {
        LinkedList<T> result = new LinkedList<T>();
        if (values != null) {
            for (T value : values) {
                if (value != null) {
                    result.add(value);
                }
            }
        }
        return result;
    }
}
