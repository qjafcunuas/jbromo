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

import org.apache.commons.lang3.ClassUtils;


/**
 * Define utility on class.
 *
 * @author qjafcunuas
 *
 */
public final class ClassUtil {

    /**
     * Default constructor.
     */
    private ClassUtil() {
        super();
    }

    /**
     * Return class object.
     *
     * @param <O>
     *            the object type.
     * @param object
     *            the object to get class.
     * @return the object class.
     */
    @SuppressWarnings("unchecked")
    public static <O extends Object> Class<O> getClass(final O object) {
        if (object == null) {
            return null;
        }
        if (java.lang.reflect.Proxy.isProxyClass(object.getClass())) {
            return (Class<O>) object.getClass().getInterfaces()[0];
        } else {
            return (Class<O>) object.getClass();
        }
    }

    /**
     * Return class objects.
     *
     * @param objects
     *            the objects to get class.
     * @return the objects class.
     */
    public static Class<?>[] getClass(final Object... objects) {
        if (objects == null) {
            return null;
        }
        final Class<?>[] classes = new Class<?>[objects.length];
        for (int i = 0; i < objects.length; i++) {
            classes[i] = getClass(objects[i]);
        }
        return classes;
    }

    /**
     * Is instance of a class.
     *
     * @param obj
     *            the object to test if it is an instance of a class type.
     * @param type
     *            the type to check
     * @return true if type is of class type.
     */
    public static boolean isInstance(final Object obj, final Class<?> type) {
        return type != null && obj != null && type.isInstance(obj);
    }

    /**
     * Is assignable to a class.
     *
     * @param obj
     *            the class to test if it is assignable of another class type.
     * @param type
     *            the type to check
     * @return true if type is List.
     */
    public static boolean isAssignableFrom(final Class<?> obj,
            final Class<?> type) {
        return type != null && obj != null && type.isAssignableFrom(obj);
    }

    /**
     * Return true if a class is primitive or primitive boxed (Integer, Long
     * ...).
     *
     * @param theClass
     *            the class to test.
     * @return true/false.
     */
    public static boolean isPrimitive(final Class<?> theClass) {
        if (theClass == null) {
            return false;
        }
        return ClassUtils.isPrimitiveOrWrapper(theClass);
    }

}
