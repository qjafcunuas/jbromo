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

import java.lang.reflect.Array;
import java.util.Collection;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Define array Utility.
 * @author qjafcunuas
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ArrayUtil {

    /**
     * Return an array from a collection.
     * @param <M> the model of the element in the collection.
     * @param from the collection of element to add to the returned array.
     * @param modelClass the model class.
     * @return the array.
     */
    @SuppressWarnings("unchecked")
    public static <M> M[] toArray(final Collection<M> from, final Class<M> modelClass) {
        return from.toArray((M[]) Array.newInstance(modelClass, from.size()));
    }

    /**
     * Return true if array is null or empty.
     * @param <M> the element type.
     * @param elements the array to check.
     * @return true/false.
     */
    public static <M> boolean isEmpty(final M[] elements) {
        return elements == null || elements.length == 0;
    }

    /**
     * Return true if collection and array contains same elements.
     * @param <M> the model element type.
     * @param one the collection.
     * @param two the array.
     * @param sameSize if true, collections must have same size; else collections can not have the same size.
     * @return true/false.
     */
    public static <M> boolean containsAll(final M[] one, final M[] two, final boolean sameSize) {
        if (one == null || two == null) {
            return false;
        }
        if (ObjectUtil.same(one, two)) {
            return true;
        }
        final Collection<M> col = ListUtil.toList(one);
        return CollectionUtil.containsAll(col, two, sameSize);
    }

}
