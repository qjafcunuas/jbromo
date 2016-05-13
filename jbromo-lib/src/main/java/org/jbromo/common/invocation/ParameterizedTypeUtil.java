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
package org.jbromo.common.invocation;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.jbromo.common.ListUtil;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Invocation tools.
 * @author qjafcunuas
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ParameterizedTypeUtil {

    /**
     * Return a parameterized class.
     * @param <T> the type of the class.
     * @param object the object that have parameterized class.
     * @param position the position in the parameterized class.
     * @return the parameterized class.
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClass(final Object object, final int position) {
        final Type[] types = getTypes(object.getClass());
        if (position >= types.length) {
            final Object[] args = new Object[] {Integer.valueOf(position), Integer.valueOf(types.length), object.getClass()};
            log.debug("Asked position {} is bigger than {} elements from the parameteriezd generic class {}", args);
            return null;
        }
        return (Class<T>) types[position];
    }

    /**
     * Return parameterized types of a class.
     * @param klass the class.
     * @return the types.
     */
    private static Type[] getTypes(final Class<?> klass) {
        try {
            return ((ParameterizedType) klass.getGenericSuperclass()).getActualTypeArguments();
        } catch (final ClassCastException e) {
            return getTypes(klass.getSuperclass());
        }
    }

    /**
     * Return a parameterized class.
     * @param <T> the type of the class.
     * @param object the object that have parameterized class.
     * @param genericClass the generic class of the objet to find parameter.
     * @param position the position of the parameter in the generic class.
     * @return the parameterized class.
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClass(final Object object, final Class<?> genericClass, final int position) {
        try {
            Class<?> cls = object.getClass();
            while (!(cls.getSuperclass() == null || cls.getSuperclass().equals(genericClass))) {
                cls = cls.getSuperclass();
            }

            if (cls.getSuperclass() == null) {
                throw new RuntimeException("Unexpected exception occurred.");
            }

            return (Class<T>) ((ParameterizedType) cls.getGenericSuperclass()).getActualTypeArguments()[position];
        } catch (final Exception e) {
            log.error("Cannot find parameterized type", e);
            return null;
        }
    }

    /**
     * Return generic class type of a field.
     * @param field the field to get generic type.
     * @return the generic class.
     */
    public static List<Class<?>> getGenericType(final Field field) {
        final ParameterizedType ptype = (ParameterizedType) field.getGenericType();
        final List<Class<?>> list = ListUtil.toList();
        for (final Type type : ptype.getActualTypeArguments()) {
            list.add((Class<?>) type);
        }
        return list;
    }

    /**
     * Return generic class type of a field.
     * @param <T> the type field.
     * @param field the field to get generic type.
     * @param position the position of the generic type.
     * @return the generic class.
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getGenericType(final Field field, final int position) {
        final ParameterizedType ptype = (ParameterizedType) field.getGenericType();
        return (Class<T>) ptype.getActualTypeArguments()[position];
    }

}
