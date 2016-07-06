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

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import lombok.experimental.UtilityClass;

/**
 * Define Collection Utility.
 * @author qjafcunuas
 */
@UtilityClass
public final class CollectionUtil {

    /**
     * Return true if collections contains same elements, even if one collection contains more than one a same elements and the other collection
     * contains only once it.
     * @param <M> the model element type of the collection.
     * @param one the first collection.
     * @param two the second collection.
     * @param sameSize if true, collections must have same size; else collections can not have the same size.
     * @return true/false.
     */
    @SuppressWarnings("unchecked")
    public static <M> boolean containsAll(final Collection<M> one, final Collection<M> two, final boolean sameSize) {
        if (one == null || two == null) {
            return false;
        }
        if (ObjectUtil.same(one, two)) {
            return true;
        }
        if (SetUtil.isSet(one) && SetUtil.isSet(two)) {
            return containsAll(Set.class.cast(one), Set.class.cast(two));
        }
        if (sameSize) {
            return one.size() == two.size() && one.containsAll(two) && two.containsAll(one);
        } else {
            final Set<M> setOne = SetUtil.toSet(one);
            final Set<M> setTwo = SetUtil.toSet(two);
            return containsAll(setOne, setTwo);
        }
    }

    /**
     * Return true if collections contains same elements, even if one collection contains more than one a same elements and the other collection
     * contains only once it.
     * @param <M> the model element type of the collection.
     * @param one the first collection.
     * @param two the second collection.
     * @param sameSize if true, collections must have same size; else collections can not have the same size.
     * @return true/false.
     */
    private static <M> boolean containsAll(final Set<M> one, final Set<M> two) {
        return one.size() == two.size() && one.containsAll(two);
    }

    /**
     * Return true if collection and array contains same elements.
     * @param <M> the model element type.
     * @param one the collection.
     * @param two the array.
     * @param sameSize if true, collections must have same size; else collections can not have the same size.
     * @return true/false.
     */
    public static <M> boolean containsAll(final Collection<M> one, final M[] two, final boolean sameSize) {
        if (one == null || two == null) {
            return false;
        }
        final Collection<M> col = ListUtil.toList(two);
        return containsAll(one, col, sameSize);
    }

    /**
     * Return true if collections contains same ordered elements.
     * @param <M> the model element type of the collection.
     * @param one the first collection.
     * @param two the second collection. not have the same size.
     * @return true/false.
     */
    public static <M> boolean identical(final Collection<M> one, final Collection<M> two) {
        if (one == null || two == null) {
            return false;
        }
        if (ObjectUtil.same(one, two)) {
            return true;
        }
        if (one.size() != two.size()) {
            return false;
        }
        final List<M> oneList = ListUtil.toList(one);
        final List<M> twoList = ListUtil.toList(two);
        for (int i = 0; i < oneList.size(); i++) {
            if (!ObjectUtil.equals(oneList.get(i), twoList.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return true if collection contains an element of a class.
     * @param <M> the model element type.
     * @param col the collection.
     * @param aClass the class.
     * @return true/false.
     */
    public static <M> boolean contains(final Collection<M> col, final Class<?> aClass) {
        if (col == null || aClass == null) {
            return false;
        }
        for (final M one : col) {
            if (aClass.isInstance(one)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is assignable to a collection or a set.
     * @param type the type to check
     * @return true if type is Collection, or Set
     */
    public static boolean isCollection(final Class<?> type) {
        return ClassUtil.isAssignableFrom(type, Collection.class);
    }

    /**
     * Is instance of a collection.
     * @param obj the obj to check
     * @return true if type is Collection.
     */
    public static boolean isCollection(final Object obj) {
        return ClassUtil.isInstance(obj, Collection.class);
    }

    /**
     * Return true if collection is null or empty.
     * @param col the collection to check.
     * @return true/false.
     */
    public static boolean isEmpty(final Collection<?> col) {
        return col == null || col.isEmpty();
    }

    /**
     * Return true if collection is not null and not empty.
     * @param col the collection to check.
     * @return true/false.
     */
    public static boolean isNotEmpty(final Collection<?> col) {
        return !isEmpty(col);
    }

    /**
     * Return a collection from a list.
     * @param <M> the model of the element in the collection.
     * @param <C> the collection type to return.
     * @param from the collection of element to add to the returned collection.
     * @param toClass the collection type to return.
     * @return the collection.
     */
    @SuppressWarnings("unchecked")
    public static <C extends Collection<M>, M> C toCollection(final Collection<M> from, final Class<C> toClass) {
        if (toClass.equals(from.getClass())) {
            return (C) from;
        } else {
            final C values = ObjectUtil.newInstance(toClass);
            if (values != null) {
                values.addAll(from);
            }
            return values;
        }

    }

    /**
     * Add all elements into a collection.
     * @param <T> the element type.
     * @param to the collection to add all objects.
     * @param elements the elements to add to the collection.
     */
    @SafeVarargs
    public static <T> void addAll(final Collection<T> to, final T... elements) {
        if (to != null) {
            for (final T one : elements) {
                to.add(one);
            }
        }
    }

    /**
     * Return true if collection is not null and has only one elements.
     * @param col the collection to check.
     * @return true/false.
     */
    public static boolean hasOneElement(final Collection<?> col) {
        return col != null && col.size() == 1;
    }

    /**
     * Return true if collection contains more than one elements.
     * @param col the collection to check.
     * @return true/false.
     */
    public static boolean hasElements(final Collection<?> col) {
        return col != null && col.size() > 1;
    }

    /**
     * Return the index of element in a collection, or -1 if element is not in the collection.
     * @param <O> the object type.
     * @param col the collection.
     * @param element the element.
     * @return the index
     */
    public static <O> int indexOf(final Collection<O> col, final O element) {
        int i = 0;
        for (final O one : col) {
            if (ObjectUtil.equals(one, element)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * Joins the elements of the provided collection into a single String containing the provided list of elements.
     * @param collection the collection.
     * @param separator the separator.
     * @return the string.
     */
    public static String toString(final Collection<?> collection, final String separator) {
        return StringUtils.join(collection, separator);
    }

    /**
     * Return the object from the collection.
     * @param <O> the object type.
     * @param collection the collection.
     * @param tofind the object to find in the collection.
     * @return the object.
     */
    public static <O> O get(final Collection<O> collection, final O tofind) {
        if (collection == null || tofind == null) {
            return null;
        }
        for (final O one : collection) {
            if (tofind.equals(one)) {
                return one;
            }
        }
        return null;
    }

}
