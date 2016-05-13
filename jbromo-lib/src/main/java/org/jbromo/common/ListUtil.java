/*-
 * Copyright (C) 2013-2014 The JBromo Authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jbromo.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Define List Utility.
 * @author qjafcunuas
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ListUtil {

    /**
     * Return true if list contains same elements.
     * @param <M> the model element of the list.
     * @param one the first list.
     * @param two the second list.
     * @param sameSize if true, collections must have same size; else collections can not have the same size.
     * @return true/false.
     */
    public static <M> boolean containsAll(final List<M> one, final List<M> two, final boolean sameSize) {
        return CollectionUtil.containsAll(one, two, sameSize);
    }

    /**
     * Return true if list is null or empty.
     * @param list the list to check.
     * @return true/false.
     */
    public static boolean isEmpty(final List<?> list) {
        return CollectionUtil.isEmpty(list);
    }

    /**
     * Return true if list is not null and not empty.
     * @param list the list to check.
     * @return true/false.
     */
    public static boolean isNotEmpty(final List<?> list) {
        return CollectionUtil.isNotEmpty(list);
    }

    /**
     * Return true if list is not null and has only one elements.
     * @param list the list to check.
     * @return true/false.
     */
    public static boolean hasOneElement(final List<?> list) {
        return CollectionUtil.hasOneElement(list);
    }

    /**
     * Return true if list contains more than one elements.
     * @param list the list to check.
     * @return true/false.
     */
    public static boolean hasElements(final List<?> list) {
        return CollectionUtil.hasElements(list);
    }

    /**
     * Build an array list from an array.
     * @param <T> the element type.
     * @param values the values.
     * @return true/false.
     */
    @SafeVarargs
    public static <T> List<T> toList(final T... values) {
        if (ArrayUtil.isEmpty(values)) {
            return new ArrayList<>();
        }
        final List<T> list = new ArrayList<>(values.length + IntegerUtil.INT_10);
        for (final T value : values) {
            list.add(value);
        }
        return list;
    }

    /**
     * Build an unmodifiable list from an array.
     * @param <T> the element type.
     * @param values the values.
     * @return true/false.
     */
    @SafeVarargs
    public static <T> List<T> toUnmodifiableList(final T... values) {
        if (ArrayUtil.isEmpty(values)) {
            return Collections.unmodifiableList(new ArrayList<T>());
        }
        return Collections.unmodifiableList(toList(values));
    }

    /**
     * Build an array list from a collection.
     * @param <T> the element type.
     * @param values the values.
     * @return true/false.
     */
    public static <T> List<T> toList(final Collection<T> values) {
        if (CollectionUtil.isEmpty(values)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(values);
    }

    /**
     * Build an unmodifiable list from a collection.
     * @param <T> the element type.
     * @param values the values.
     * @return true/false.
     */
    public static <T> List<T> toUnmodifiableList(final Collection<T> values) {
        if (CollectionUtil.isEmpty(values)) {
            return Collections.unmodifiableList(new ArrayList<T>());
        }
        return Collections.unmodifiableList(toList(values));
    }

    /**
     * Is assignable to a List.
     * @param type the type to check
     * @return true if type is List.
     */
    public static boolean isList(final Class<?> type) {
        return ClassUtil.isAssignableFrom(type, List.class);
    }

    /**
     * Is assignable to a List.
     * @param obj the obj to check
     * @return true if type is List.
     */
    public static boolean isList(final Object obj) {
        return ClassUtil.isInstance(obj, List.class);
    }

    /**
     * Return the las element of a list (null if empty).
     * @param <M> the type element.
     * @param list the list.
     * @return the last element.
     */
    public static <M> M getLast(final List<M> list) {
        if (isEmpty(list)) {
            return null;
        } else {
            return list.get(list.size() - 1);
        }
    }

}
