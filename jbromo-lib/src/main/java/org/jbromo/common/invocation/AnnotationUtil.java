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
package org.jbromo.common.invocation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.interceptor.InvocationContext;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jbromo.common.ClassUtil;
import org.jbromo.common.ObjectUtil;

/**
 * Annotation tools.
 *
 * @author qjafcunuas
 */
public final class AnnotationUtil {

    /**
     * private default constructor.
     */
    private AnnotationUtil() {
        super();
    }

    /**
     * Get Max Size in Size annotation.
     *
     * @param field
     *            the field
     * @return the max size
     */
    public static Integer getSizeMax(final Field field) {
        final Size s = field.getAnnotation(Size.class);
        return s == null ? null : s.max();
    }

    /**
     * Get Max Size in Size annotation.
     *
     * @param field
     *            the field
     * @return the max size
     */
    public static Integer getSizeMin(final Field field) {
        final Size s = field.getAnnotation(Size.class);
        return s == null ? null : s.min();
    }

    /**
     * Get min value in Min annotation.
     *
     * @param field
     *            the field
     * @return the max size
     */
    public static Long getMin(final Field field) {
        final Min m = field.getAnnotation(Min.class);
        return m == null ? null : m.value();
    }

    /**
     * Get max value in Max annotation.
     *
     * @param field
     *            the field
     * @return the max size
     */
    public static Long getMax(final Field field) {
        final Max m = field.getAnnotation(Max.class);
        return m == null ? null : m.value();
    }

    /**
     * Get decimal value from DecimalMin annotation.
     *
     * @param field
     *            the field
     * @return the decimal min value
     */
    public static BigDecimal getDecimalMin(final Field field) {
        final DecimalMin dm = field.getAnnotation(DecimalMin.class);
        return dm == null ? null : new BigDecimal(dm.value());
    }

    /**
     * Get decimal value from DecimalMax annotation.
     *
     * @param field
     *            the field
     * @return the decimal max value
     */
    public static BigDecimal getDecimalMax(final Field field) {
        final DecimalMax dm = field.getAnnotation(DecimalMax.class);
        return dm == null ? null : new BigDecimal(dm.value());
    }

    /**
     * Get fraction value from Digits annotation.
     *
     * @param field
     *            the field
     * @return the fraction
     */
    public static Integer getDigitsFraction(final Field field) {
        final Digits digit = field.getAnnotation(Digits.class);
        return digit == null ? null : digit.fraction();
    }

    /**
     * Get Integer value from Digits annotation.
     *
     * @param field
     *            the field
     * @return the integer
     */
    public static Integer getDigitsInteger(final Field field) {
        final Digits digit = field.getAnnotation(Digits.class);
        return digit == null ? null : digit.integer();
    }

    /**
     * Get not null value in NotNull annotation.
     *
     * @param field
     *            the field
     * @return true if not null, false if null authorized
     */
    public static boolean isNotNull(final Field field) {
        return field.getAnnotation(NotNull.class) != null;
    }

    /**
     * Return true if Valid annotation is define on a field.
     *
     * @param field
     *            the field
     * @return true/false.
     */
    public static boolean isValid(final Field field) {
        return field.getAnnotation(Valid.class) != null;
    }

    /**
     * Return annotation from a method, or if not found for the class of the
     * method.
     *
     * @param <A>
     *            the annotation type to get.
     * @param method
     *            the method to get the annotation.
     * @param annotation
     *            the annotation to get.
     * @return the found annotation or null.
     */
    public static <A extends Annotation> A getAnnotation(final Method method,
            final Class<A> annotation) {
        // Has method this annotation ?
        for (final Annotation one : method.getAnnotations()) {
            if (annotation.isInstance(one)) {
                // yes, return it.
                return ObjectUtil.cast(one, annotation);
            }
        }
        // no annotation found on the method.
        // Try to search on inherited annotations from method's annotations.
        A found;
        for (final Annotation one : method.getAnnotations()) {
            found = getAnnotation(ClassUtil.getClass(one), annotation);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    /**
     * Return annotation from a method, or if not found for the class of the
     * method.
     *
     * @param <A>
     *            the annotation type to get.
     * @param method
     *            the method to get the annotation.
     * @param methodClass
     *            the class of the object on which is run the method (not where
     *            the method is defined, but the eventually inheritance object).
     * @param annotation
     *            the annotation to get.
     * @return the found annotation or null.
     */
    public static <A extends Annotation> A getAnnotation(final Method method,
            final Class<?> methodClass, final Class<A> annotation) {
        // Has method this annotation ?
        for (final Annotation one : method.getAnnotations()) {
            if (annotation.isInstance(one)) {
                // yes, return it.
                return ObjectUtil.cast(one, annotation);
            }
        }
        // no annotation found on the method.
        // Try to search on inherited annotations from method's annotations.
        A found;
        for (final Annotation one : method.getAnnotations()) {
            found = getAnnotation(ClassUtil.getClass(one), annotation);
            if (found != null) {
                return found;
            }
        }
        return getAnnotation(methodClass, annotation);
    }

    /**
     * Return annotation from a class.
     *
     * @param <A>
     *            the annotation type to get.
     * @param aClass
     *            the class to get the annotation.
     * @param annotation
     *            the annotation to get.
     * @return the found annotation or null.
     */
    public static <A extends Annotation> A getAnnotation(final Class<?> aClass,
            final Class<A> annotation) {
        return getAnnotation(aClass, annotation,
                new HashSet<Class<Annotation>>());
    }

    /**
     * Return annotation from a context.
     *
     * @param <A>
     *            the annotation type to get.
     * @param context
     *            the invocation context.
     * @param annotation
     *            the annotation to get.
     * @return the found annotation or null.
     */
    public static <A extends Annotation> A getAnnotation(
            final InvocationContext context, final Class<A> annotation) {
        return getAnnotation(context.getMethod(), context.getTarget()
                .getClass(), annotation);
    }

    /**
     * Return annotation from a class.
     *
     * @param <A>
     *            the annotation type to get.
     * @param aClass
     *            the class to get the annotation.
     * @param annotation
     *            the annotation to get.
     * @param alreadyScanned
     *            for not scanning multiple times same annotation recursively
     *            (for example, annotation Documented is recursive to itself).
     * @return the found annotation or null.
     */
    private static <A extends Annotation> A getAnnotation(
            final Class<?> aClass, final Class<A> annotation,
            final Set<Class<Annotation>> alreadyScanned) {

        // Has the class this annotation ?
        for (final Annotation one : aClass.getAnnotations()) {
            if (annotation.isInstance(one)) {
                // yes, return it.
                return ObjectUtil.cast(one, annotation);
            }
        }
        // The annotation is not found on the class.
        // Perhaps class annotations inherit of the searched annotation.
        // Try to find it.
        A found;
        Class<Annotation> oneClass;
        for (final Annotation one : aClass.getAnnotations()) {
            oneClass = ClassUtil.getClass(one);
            if (!alreadyScanned.contains(oneClass)) {
                alreadyScanned.add(oneClass);
                found = getAnnotation(oneClass, annotation, alreadyScanned);
                if (found != null) {
                    return found;
                }
            }
        }
        // All class annotations doesn't inherit of the searched annotation.
        // Perhaps superclass have the searched annotation.
        if (aClass.getSuperclass() != null) {
            return getAnnotation(aClass.getSuperclass(), annotation,
                    new HashSet<Class<Annotation>>());
        } else {
            return null;
        }
    }

}
