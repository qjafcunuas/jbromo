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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.common.invocation.InvocationUtil.AccessType;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * Define utility class on object.
 * @author qjafcunuas
 */
@Slf4j
@UtilityClass
public final class ObjectUtil {

    /**
     * Return true if two objects are null or, not null and equals.
     * @param obj1 first object.
     * @param obj2 second object.
     * @return true/false.
     */
    public static boolean notNullAndEquals(final Object obj1, final Object obj2) {
        if (obj1 == null || obj2 == null) {
            return false;
        } else {
            return obj1.equals(obj2);
        }
    }

    /**
     * Return true if two objects are equals.
     * @param obj1 first object.
     * @param obj2 second object.
     * @return true/false.
     */
    public static boolean equals(final Object obj1, final Object obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        } else if (obj1 == null || obj2 == null) {
            return false;
        } else {
            return obj1.equals(obj2);
        }
    }

    /**
     * Return true if two objects are null of if two object are not null.
     * @param obj1 first object.
     * @param obj2 second object.
     * @return true/false.
     */
    public static boolean nullOrNotNull(final Object obj1, final Object obj2) {
        return obj1 != null && obj2 != null || obj1 == null && obj2 == null;
    }

    /**
     * Compare two comparable objects.
     * @param o1 the first object
     * @param o2 the other object
     * @param <E> the type of comparable element
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @SuppressWarnings("unchecked")
    public static <E> int compare(final Comparable<E> o1, final Comparable<E> o2) {
        if (o1 == null && o2 == null) {
            return 0;
        } else if (o1 == null) {
            return -1;
        } else if (o2 == null) {
            return 1;
        } else {
            return o1.compareTo((E) o2);
        }
    }

    /**
     * Compare two objects, by comparator if object is comparable, else by toString method.
     * @param o1 the first object
     * @param o2 the other object
     * @param <O> the type of comparable element
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @SuppressWarnings("unchecked")
    public static <O> int compare(final O o1, final O o2) {
        if (o1 == null && o2 == null) {
            return 0;
        } else if (o1 == null) {
            return -1;
        } else if (o2 == null) {
            return 1;
        } else if (Comparable.class.isInstance(o1)) {
            return compare(Comparable.class.cast(o1), Comparable.class.cast(o2));
        } else {
            final String s1 = o1.toString();
            final String s2 = o2.toString();
            return s1.compareTo(s2);
        }
    }

    /**
     * Return a new instance of a class.
     * @param <O> the object type.
     * @param objectClass the object class
     * @return the new object instance.
     */
    public static <O> O newInstance(final Class<O> objectClass) {
        try {
            return objectClass.getConstructor().newInstance();
        } catch (final Exception e) {
            log.error("Cannot instantiate class {}", objectClass, e);
            return null;
        }
    }

    /**
     * Return a new instance of a class.
     * @param <O> the object type.
     * @param objectClass the object class
     * @param parameters the constructor parameters.
     * @return the new object instance.
     */
    public static <O> O newInstance(final Class<O> objectClass, final Object... parameters) {
        try {
            final Constructor<O> constructor = InvocationUtil.getConstructor(objectClass, ClassUtil.getClass(parameters));
            return constructor.newInstance(parameters);
        } catch (final Exception e) {
            log.error("Cannot instantiate class {} with parameters {}", new Object[] {objectClass, parameters, e});
            return null;
        }
    }

    /**
     * Cast an object.
     * @param <O> the object type.
     * @param object the object to cast.
     * @param toClass the class to cast.
     * @return the casted object.
     */
    public static <O> O cast(final Object object, final Class<O> toClass) {
        return toClass.cast(object);
    }

    /**
     * Return true if objects are the same (one == two).
     * @param one one object.
     * @param two another object.
     * @return true/false.
     */
    public static boolean same(final Object one, final Object two) {
        return one == two;
    }

    /**
     * Return true if object is primitive or primitive boxed (Integer, Long ...).
     * @param object the object to test.
     * @return true/false.
     */
    public static boolean isPrimitive(final Object object) {
        if (object == null) {
            return false;
        }
        return ClassUtils.isPrimitiveOrWrapper(object.getClass());
    }

    /**
     * Dump object in logs.
     * @param object the object to dump.
     * @return the dumped string.
     */
    public static StringBuilder dump(final Object object) {
        if (object == null) {
            log.info("Null object!");
            return null;
        }
        final StringBuilder builder = new StringBuilder(object.toString());
        try {
            dump(object, "    ", builder, SetUtil.toSet());
            log.info(builder.toString());
            return builder;
        } catch (final InvocationException e) {
            log.warn("Cannot dump object " + object, e);
            return null;
        }
    }

    /**
     * Dump object into a builder.
     * @param object the object to dump.
     * @param prefix the prefix to used
     * @param builder the object for dumping.
     * @param already contains object already dumped.
     * @exception InvocationException exception.
     */
    private static void dump(final Object object, final String prefix, final StringBuilder builder,
            final Set<Object> already) throws InvocationException {
        if (object == null || already.contains(object)) {
            return;
        }
        already.add(object);
        Object value;
        for (final Field field : InvocationUtil.getFields(object.getClass())) {
            value = InvocationUtil.getValue(object, field, AccessType.FIELD, false);
            builder.append("\r\n");
            builder.append(prefix);
            builder.append(field.getName());
            builder.append(" : ");
            builder.append(value);
            if (!isPrimitive(value) && !ClassUtil.isInstance(value, String.class)) {
                dump(value, prefix.concat("    "), builder, already);
            }
        }
    }

}
