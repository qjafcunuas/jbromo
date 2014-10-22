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

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.LongUtil;
import org.jbromo.common.test.common.ConstructorUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for the Annotation util class.
 *
 * @author qjafcunuas
 *
 */
@Slf4j
public class AnnotationUtilTest {

    /**
     * Define an object for test.
     *
     * @author qjafcunuas
     *
     */
    @Target({ ElementType.METHOD, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    private @interface OneAnnotation {
    }

    /**
     * Define an object for test.
     *
     * @author qjafcunuas
     *
     */
    @Target({ ElementType.METHOD, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @OneAnnotation
    private @interface OneTwoAnnotation {
    }

    /**
     * Define one class with one annotation.
     *
     * @author qjafcunuas
     *
     */
    @OneAnnotation
    private class OneClass {

    }

    /**
     * Define a super class of OneClass.
     *
     * @author qjafcunuas
     *
     */
    private class OneSuperClass extends OneClass {

    }

    /**
     * Define one class with inherited annotation.
     *
     * @author qjafcunuas
     *
     */
    @OneTwoAnnotation
    private class OneTwoClass {

    }

    /**
     * Define a super class of OneTwoClass.
     *
     * @author qjafcunuas
     *
     */
    private class OneTwoSuperClass extends OneTwoClass {

    }

    /**
     * Define one class with inherited annotation on method.
     *
     * @author qjafcunuas
     *
     */
    static final class MethodClass {
        /** one method. */
        @OneAnnotation
        public void one() {
        };

        /** one two method. */
        @OneTwoAnnotation
        public void oneTwo() {
        };

        /** no method. */
        public void no() {
        };

        /** The one method object. */
        private static Method oneMethod;
        /** The oneTwo method object. */
        private static Method oneTwoMethod;
        /** The no method object. */
        private static Method noMethod;

        /** Initialize method object. */
        static {
            try {
                oneMethod = InvocationUtil.getMethod(MethodClass.class, "one");
                oneTwoMethod = InvocationUtil.getMethod(MethodClass.class,
                        "oneTwo");
                noMethod = InvocationUtil.getMethod(MethodClass.class, "no");
            } catch (final InvocationException e) {
                log.error("Cannot found method", e);
                Assert.fail("Cannot found method");
            }
        }
    }

    /**
     * Define one class with one annotation, and methods with annotation.
     *
     * @author qjafcunuas
     *
     */
    @OneAnnotation
    static final class MethodOneClass {
        /** one method. */
        public void one() {
        };

        /** one two method. */
        @OneTwoAnnotation
        public void oneTwo() {
        };

        /** The one method object. */
        private static Method oneMethod;
        /** The oneTwo method object. */
        private static Method oneTwoMethod;

        /** Initialize method object. */
        static {
            try {
                oneMethod = InvocationUtil.getMethod(MethodOneClass.class,
                        "one");
                oneTwoMethod = InvocationUtil.getMethod(MethodOneClass.class,
                        "oneTwo");
            } catch (final InvocationException e) {
                log.error("Cannot found method", e);
                Assert.fail("Cannot found method");
            }
        }
    }

    /**
     * A class used for validation constraint annotation.
     *
     * @author qjafcunuas
     *
     */
    @Getter
    static final class ValidationConstraint {
        /** field with no constraint. */
        private Integer noConstraint;
        /** size min annotation. */
        @Size(min = 0)
        private Integer sizeMin;
        /** size max annotation. */
        @Size(max = IntegerUtil.INT_100)
        private Integer sizeMax;
        /** size min/max annotation. */
        @Size(min = 1, max = IntegerUtil.INT_1000)
        private Integer sizeMinMax;
        /** min annotation. */
        @Min(value = LongUtil.LONG_0)
        private Long min;
        /** max annotation. */
        @Max(value = LongUtil.LONG_100)
        private Long max;
        /** min/max annotation. */
        @Min(value = LongUtil.LONG_1)
        @Max(value = LongUtil.LONG_1000)
        private Long minMax;
        /** decimalMin annotation. */
        @DecimalMin(value = "0.00")
        private BigDecimal decimalMin;
        /** decimalMax annotation. */
        @DecimalMax(value = "100.00")
        private BigDecimal decimalMax;
        /** decimalMin/decimalMax annotation. */
        @DecimalMin(value = "1.00")
        @DecimalMax(value = "1000.00")
        private BigDecimal decimalMinMax;
        /** digits annotation. */
        @Digits(integer = 1, fraction = 2)
        private Integer digits;
        /** NotNull annotation. */
        @NotNull
        private Integer notNull;
        /** Valid annotation. */
        @Valid
        private Integer valid;
    }

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        ConstructorUtil.executePrivate(AnnotationUtil.class);
    }

    /**
     * Test the getAnnotation on class method.
     */
    @Test
    public void getAnnotationClass() {
        Assert.assertNull(AnnotationUtil.getAnnotation(String.class,
                OneAnnotation.class));
        Assert.assertNotNull(AnnotationUtil.getAnnotation(OneClass.class,
                OneAnnotation.class));
        Assert.assertNotNull(AnnotationUtil.getAnnotation(OneSuperClass.class,
                OneAnnotation.class));
        Assert.assertNull(AnnotationUtil.getAnnotation(OneClass.class,
                OneTwoAnnotation.class));
        Assert.assertNull(AnnotationUtil.getAnnotation(OneSuperClass.class,
                OneTwoAnnotation.class));

        Assert.assertNotNull(AnnotationUtil.getAnnotation(OneTwoClass.class,
                OneAnnotation.class));
        Assert.assertNotNull(AnnotationUtil.getAnnotation(
                OneTwoSuperClass.class, OneAnnotation.class));
        Assert.assertNotNull(AnnotationUtil.getAnnotation(OneTwoClass.class,
                OneTwoAnnotation.class));
        Assert.assertNotNull(AnnotationUtil.getAnnotation(
                OneTwoSuperClass.class, OneTwoAnnotation.class));
    }

    /**
     * Test the getAnnotation on method method.
     */
    @Test
    public void getAnnotationMethod() {
        // MethodClass
        Assert.assertNotNull(AnnotationUtil.getAnnotation(
                MethodClass.oneMethod, OneAnnotation.class));
        Assert.assertNull(AnnotationUtil.getAnnotation(MethodClass.oneMethod,
                OneTwoAnnotation.class));

        Assert.assertNotNull(AnnotationUtil.getAnnotation(
                MethodClass.oneTwoMethod, OneAnnotation.class));
        Assert.assertNotNull(AnnotationUtil.getAnnotation(
                MethodClass.oneTwoMethod, OneTwoAnnotation.class));

        Assert.assertNull(AnnotationUtil.getAnnotation(MethodClass.noMethod,
                OneAnnotation.class));
        Assert.assertNull(AnnotationUtil.getAnnotation(MethodClass.noMethod,
                OneTwoAnnotation.class));

        // MethodOneClass for method only
        Assert.assertNull(AnnotationUtil.getAnnotation(
                MethodOneClass.oneMethod, OneAnnotation.class));
        Assert.assertNull(AnnotationUtil.getAnnotation(
                MethodOneClass.oneMethod, OneTwoAnnotation.class));

        Assert.assertNotNull(AnnotationUtil.getAnnotation(
                MethodOneClass.oneTwoMethod, OneAnnotation.class));
        Assert.assertNotNull(AnnotationUtil.getAnnotation(
                MethodOneClass.oneTwoMethod, OneTwoAnnotation.class));

        // MethodOneClass for method and class
        Assert.assertNotNull(AnnotationUtil.getAnnotation(
                MethodOneClass.oneMethod, MethodOneClass.class,
                OneAnnotation.class));
        Assert.assertNull(AnnotationUtil.getAnnotation(
                MethodOneClass.oneMethod, MethodOneClass.class,
                OneTwoAnnotation.class));

        Assert.assertNotNull(AnnotationUtil.getAnnotation(
                MethodOneClass.oneTwoMethod, MethodOneClass.class,
                OneAnnotation.class));
        Assert.assertNotNull(AnnotationUtil.getAnnotation(
                MethodOneClass.oneTwoMethod, MethodOneClass.class,
                OneTwoAnnotation.class));
    }

    /**
     * Test getSizeMax method.
     */
    @Test
    public void getSizeMax() {
        Field field = InvocationUtil.getField(ValidationConstraint.class,
                "noConstraint");
        Assert.assertNull(AnnotationUtil.getSizeMax(field));

        field = InvocationUtil.getField(ValidationConstraint.class, "sizeMax");
        Assert.assertTrue(AnnotationUtil.getSizeMax(field) == IntegerUtil.INT_100);

        field = InvocationUtil.getField(ValidationConstraint.class,
                "sizeMinMax");
        Assert.assertTrue(AnnotationUtil.getSizeMax(field) == IntegerUtil.INT_1000);
    }

    /**
     * Test getSizeMin method.
     */
    @Test
    public void getSizeMin() {
        Field field = InvocationUtil.getField(ValidationConstraint.class,
                "noConstraint");
        Assert.assertNull(AnnotationUtil.getSizeMin(field));

        field = InvocationUtil.getField(ValidationConstraint.class, "sizeMin");
        Assert.assertTrue(AnnotationUtil.getSizeMin(field) == IntegerUtil.INT_0);

        field = InvocationUtil.getField(ValidationConstraint.class,
                "sizeMinMax");
        Assert.assertTrue(AnnotationUtil.getSizeMin(field) == IntegerUtil.INT_1);
    }

    /**
     * Test getMax method.
     */
    @Test
    public void getMax() {
        Field field = InvocationUtil.getField(ValidationConstraint.class,
                "noConstraint");
        Assert.assertNull(AnnotationUtil.getMax(field));

        field = InvocationUtil.getField(ValidationConstraint.class, "max");
        Assert.assertTrue(AnnotationUtil.getMax(field) == LongUtil.LONG_100);

        field = InvocationUtil.getField(ValidationConstraint.class, "minMax");
        Assert.assertTrue(AnnotationUtil.getMax(field) == LongUtil.LONG_1000);
    }

    /**
     * Test getMin method.
     */
    @Test
    public void getMin() {
        Field field = InvocationUtil.getField(ValidationConstraint.class,
                "noConstraint");
        Assert.assertNull(AnnotationUtil.getMin(field));

        field = InvocationUtil.getField(ValidationConstraint.class, "min");
        Assert.assertTrue(AnnotationUtil.getMin(field) == LongUtil.LONG_0);

        field = InvocationUtil.getField(ValidationConstraint.class, "minMax");
        Assert.assertTrue(AnnotationUtil.getMin(field) == LongUtil.LONG_1);
    }

    /**
     * Test getDecimalMax method.
     */
    @Test
    public void getDecimalMax() {
        Field field = InvocationUtil.getField(ValidationConstraint.class,
                "noConstraint");
        Assert.assertNull(AnnotationUtil.getDecimalMax(field));

        field = InvocationUtil.getField(ValidationConstraint.class,
                "decimalMax");
        Assert.assertEquals(AnnotationUtil.getDecimalMax(field),
                new BigDecimal("100.00"));

        field = InvocationUtil.getField(ValidationConstraint.class,
                "decimalMinMax");
        Assert.assertEquals(AnnotationUtil.getDecimalMax(field),
                new BigDecimal("1000.00"));
    }

    /**
     * Test getDecimalMin method.
     */
    @Test
    public void getDecimalMin() {
        Field field = InvocationUtil.getField(ValidationConstraint.class,
                "noConstraint");
        Assert.assertNull(AnnotationUtil.getDecimalMin(field));

        field = InvocationUtil.getField(ValidationConstraint.class,
                "decimalMin");
        Assert.assertEquals(AnnotationUtil.getDecimalMin(field),
                new BigDecimal("0.00"));

        field = InvocationUtil.getField(ValidationConstraint.class,
                "decimalMinMax");
        Assert.assertEquals(AnnotationUtil.getDecimalMin(field),
                new BigDecimal("1.00"));
    }

    /**
     * Test getDigitsInteger method.
     */
    @Test
    public void getDigitsInteger() {
        Field field = InvocationUtil.getField(ValidationConstraint.class,
                "noConstraint");
        Assert.assertNull(AnnotationUtil.getDigitsInteger(field));

        field = InvocationUtil.getField(ValidationConstraint.class, "digits");
        Assert.assertTrue(AnnotationUtil.getDigitsInteger(field) == 1);
    }

    /**
     * Test getDigitsFraction method.
     */
    @Test
    public void getDigitsFraction() {
        Field field = InvocationUtil.getField(ValidationConstraint.class,
                "noConstraint");
        Assert.assertNull(AnnotationUtil.getDigitsFraction(field));

        field = InvocationUtil.getField(ValidationConstraint.class, "digits");
        Assert.assertTrue(AnnotationUtil.getDigitsFraction(field) == 2);
    }

    /**
     * Test isNotNull method.
     */
    @Test
    public void isNotNull() {
        Field field = InvocationUtil.getField(ValidationConstraint.class,
                "noConstraint");
        Assert.assertFalse(AnnotationUtil.isNotNull(field));

        field = InvocationUtil.getField(ValidationConstraint.class, "notNull");
        Assert.assertTrue(AnnotationUtil.isNotNull(field));
    }

    /**
     * Test isValid method.
     */
    @Test
    public void isValid() {
        Field field = InvocationUtil.getField(ValidationConstraint.class,
                "noConstraint");
        Assert.assertFalse(AnnotationUtil.isValid(field));

        field = InvocationUtil.getField(ValidationConstraint.class, "valid");
        Assert.assertTrue(AnnotationUtil.isValid(field));
    }

}