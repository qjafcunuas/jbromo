/*
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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

/**
 * Define Set Utility.
 *
 * @author qjafcunuas
 *
 */
@Slf4j
public final class SetUtil {

    /**
     * Default constructor.
     */
    private SetUtil() {
        super();
    }

    /**
     * Return true if set contains same elements.
     *
     * @param <M>
     *            the model element of the collection.
     * @param one
     *            the first list.
     * @param two
     *            the second list.
     * @return true/false.
     */
    public static <M> boolean containsAll(final Set<M> one, final Set<M> two) {
        return CollectionUtil.containsAll(one, two, false);
    }

    /**
     * Return true if set is null or empty.
     *
     * @param set
     *            the set to check.
     * @return true/false.
     */
    public static boolean isEmpty(final Set<?> set) {
        return CollectionUtil.isEmpty(set);
    }

    /**
     * Return true if set is not null and not empty.
     *
     * @param set
     *            the set to check.
     * @return true/false.
     */
    public static boolean isNotEmpty(final Set<?> set) {
        return CollectionUtil.isNotEmpty(set);
    }

    /**
     * Build a set from an array.
     *
     * @param <T>
     *            the element type.
     * @param values
     *            the values.
     * @return the set
     */
    @SafeVarargs
    public static <T> Set<T> toSet(final T... values) {
        final Set<T> set = toSet();
        if (ArrayUtil.isEmpty(values)) {
            return set;
        }
        for (final T value : values) {
            set.add(value);
        }
        return set;
    }

    /**
     * Build an empty set.
     *
     * @param <T>
     *            the element type.
     * @return the empty set.
     */
    public static <T> Set<T> toSet() {
        return new HashSet<T>();
    }

    /**
     * Build an HashSet from a collection.
     *
     * @param <T>
     *            the element type.
     * @param values
     *            the values.
     * @return true/false.
     */
    public static <T> Set<T> toSet(final Collection<T> values) {
        if (CollectionUtil.isEmpty(values)) {
            return toSet();
        }
        return new HashSet<T>(values);
    }

    /**
     * Is assignable to a Set.
     *
     * @param type
     *            the type to check
     * @return true if type is Set.
     */
    public static boolean isSet(final Class<?> type) {
        return ClassUtil.isAssignableFrom(type, Set.class);
    }

    /**
     * Is assignable to a Set.
     *
     * @param obj
     *            the obj to check
     * @return true if type is Set.
     */
    public static boolean isSet(final Object obj) {
        return ClassUtil.isInstance(obj, Set.class);
    }

    /**
     * Re-set objects. It can be used when objects has been added in the Set
     * object when result of hashCode method has changed.
     *
     * @param <O>
     *            the object type.
     * @param objects
     *            the objects to re-set.
     */
    public static <O> void reSet(final Set<O> objects) {
        try {
            if (isNotEmpty(objects)) {
                final Set<O> set = new HashSet<O>();
                set.addAll(objects);
                objects.clear();
                objects.addAll(set);
            }
        } catch (final Exception e) {
            log.error("Cannot reset objects", e);
        }
    }

    /**
     * Return true if set is not null and has only one elements.
     *
     * @param set
     *            the set to check.
     * @return true/false.
     */
    public static boolean hasOneElement(final Set<?> set) {
        return CollectionUtil.hasOneElement(set);
    }

    /**
     * Return true if set contains more than one elements.
     *
     * @param set
     *            the set to check.
     * @return true/false.
     */
    public static boolean hasElements(final Set<?> set) {
        return CollectionUtil.hasElements(set);
    }

}
