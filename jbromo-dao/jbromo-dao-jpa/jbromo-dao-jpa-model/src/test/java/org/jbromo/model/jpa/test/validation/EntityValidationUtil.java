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
package org.jbromo.model.jpa.test.validation;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.jbromo.common.ClassUtil;
import org.jbromo.common.CollectionUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.invocation.AnnotationUtil;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.model.jpa.util.EntityUtil;
import org.junit.Assert;

/**
 * Validate entity utility (annotation ...).
 *
 * @author qjafcunuas
 *
 */
public final class EntityValidationUtil {

    /**
     * Default constructor.
     */
    private EntityValidationUtil() {
        super();
    }

    /**
     * Define primitive and extended persisted class.
     */
    static final Set<Class<?>> PRIMITIVES = new HashSet<Class<?>>();

    /**
     * Build static initialization.
     */
    static {
        PRIMITIVES.add(Boolean.class);
        PRIMITIVES.add(boolean.class);
        PRIMITIVES.add(Short.class);
        PRIMITIVES.add(short.class);
        PRIMITIVES.add(Integer.class);
        PRIMITIVES.add(int.class);
        PRIMITIVES.add(Long.class);
        PRIMITIVES.add(long.class);
        PRIMITIVES.add(Enum.class);
        PRIMITIVES.add(BigDecimal.class);
        PRIMITIVES.add(BigInteger.class);
        PRIMITIVES.add(Double.class);
        PRIMITIVES.add(double.class);
        PRIMITIVES.add(Float.class);
        PRIMITIVES.add(float.class);
        PRIMITIVES.add(Calendar.class);
        PRIMITIVES.add(String.class);
        PRIMITIVES.add(Byte.class);
        PRIMITIVES.add(byte.class);
    }

    /**
     * Check if entity annotations are valid. Ie. :
     * <ol>
     * <li>Nullable in column are to true when not null (and reverse)</li>
     * <li>No eager in mapping</li>
     * <li>String has length and @Size identical</li>
     * </ol>
     *
     * @param clazz
     *            the entity class to check
     * @return true if class is an entity, else false.
     */
    public static boolean checkClass(final Class<?> clazz) {
        if (EntityUtil.isEntity(clazz) || EntityUtil.isEmbeddable(clazz)) {
            for (final Field field : EntityUtil.getPersistedFields(clazz)) {
                checkPrimitive(clazz, field);
                checkNullable(clazz, field);
                checkLazy(clazz, field);
                checkString(clazz, field);
                checkDigits(clazz, field);
                checkEmbedded(clazz, field);
            }
            return true;
        }
        return false;
    }

    /**
     * Check coherence between @Column(nullable) or @JoinColumn(nullable) and @NotNull
     * annotation.
     *
     * @param entityClass
     *            the entityClass to test.
     * @param field
     *            the field of the entity class.
     */
    private static void checkNullable(final Class<?> entityClass,
            final Field field) {
        if (EntityUtil.isPrimaryKey(field) || EntityUtil.isEmbedded(field)
                || EntityUtil.isMapsId(field)) {
            return;
        }
        final boolean hasNotNullAnnotation = AnnotationUtil.isNotNull(field);
        final boolean isNullable = EntityUtil.isNullable(field);
        Assert.assertFalse(
                "Incoherence on field "
                        + field.getName()
                        + " for entity "
                        + entityClass
                        + " betwwen @NotNull and @Column(nullable) or @JoinColumn(nullable) or @ManyToOne(optional)",
                hasNotNullAnnotation == isNullable);
        if (field.getType().isPrimitive()) {
            Assert.assertTrue("Primitive field " + field.getName()
                    + " for entity " + entityClass
                    + " must have @NotNull annotation", hasNotNullAnnotation);
            Assert.assertTrue("Primitive field " + field.getName()
                    + " for entity " + entityClass
                    + " must have @Column(nullable) annotation", !isNullable);
        }
    }

    /**
     * Check valid digit.<br>
     * Check if :
     * <ul>
     * <li>fraction and integer in @Digits are present</li>
     * <li>precision and scale in @Column are present</li>
     * <li>the fraction in @Digits = scale in @Column</li>
     * <li>the integer in @Digits + the fraction in @Digits = precision in@Column
     * </li>
     * </ul>
     *
     * @param entityClass
     *            the entity class to test.
     * @param field
     *            the field
     */
    private static void checkDigits(final Class<?> entityClass,
            final Field field) {
        if (!field.getType().isAssignableFrom(BigDecimal.class)) {
            return;
        }
        final Integer fraction = AnnotationUtil.getDigitsFraction(field);
        final Integer integer = AnnotationUtil.getDigitsInteger(field);
        final Integer scale = EntityUtil.getColumnScale(field);
        final Integer precision = EntityUtil.getColumnPrecision(field);

        // fraction not null.
        Assert.assertNotNull(
                "No fraction value on @Digits annotation on field "
                        + field.getName() + " for entity " + entityClass,
                fraction);
        // integer not null.
        Assert.assertNotNull("No integer value on @Digits annotation on field "
                + field.getName() + " for entity " + entityClass, integer);
        // scale not null.
        Assert.assertNotNull("No scale value on @Column annotation on field "
                + field.getName() + " for entity " + entityClass, scale);
        // precision not null.
        Assert.assertNotNull(
                "No precision value on @Column annotation on field "
                        + field.getName() + " for entity " + entityClass,
                precision);
        // scale == fraction.
        Assert.assertTrue(
                "No coherence between on @Column(scale=" + scale
                        + ") and @Digits(fraction=" + fraction + ") on field "
                        + field.getName() + " for entity " + entityClass,
                fraction.equals(scale));
        // precision = fraction + integer.
        Assert.assertTrue("No coherence between on @Column(precision=" + scale
                + ") and @Digits(fraction=" + fraction + ",integer=" + integer
                + ") on field " + field.getName() + " for entity "
                + entityClass, precision.equals(fraction + integer));
    }

    /**
     * Check if the @Size is identical to @Column(length) (or not present in
     * both).
     *
     * @param entityClass
     *            the entity class to test.
     * @param field
     *            the field
     */
    private static void checkString(final Class<?> entityClass,
            final Field field) {
        if (!field.getType().isAssignableFrom(String.class)) {
            return;
        }
        // Max
        final Integer maxSize = AnnotationUtil.getSizeMax(field);
        final Integer length = EntityUtil.getColumnLength(field);
        Assert.assertTrue("No cohenrence on field  " + field.getName()
                + " for entity " + entityClass + "on @Column(length=" + length
                + ") and @Size(max=" + length + ")",
                ObjectUtil.equals(maxSize, length));
    }

    /**
     * Check if entity has Lazy member.
     *
     * @param entityClass
     *            the entity class to test.
     * @param field
     *            the field
     */
    private static void checkLazy(final Class<?> entityClass, final Field field) {
        if (EntityUtil.isEntity(field.getType())
                || CollectionUtil.isCollection(field.getType())) {
            Assert.assertTrue("Field  " + field.getName()
                    + " isn't on lazy loading for entity " + entityClass,
                    EntityUtil.isLazy(field));
        }
    }

    /**
     * Check if entity has Embedded member.
     *
     * @param entityClass
     *            the entity class to test.
     * @param field
     *            the field
     */
    private static void checkEmbedded(final Class<?> entityClass,
            final Field field) {
        if (!EntityUtil.isEntity(field.getType())
                && !CollectionUtil.isCollection(field.getType())) {
            if (EntityUtil.isEmbedded(field)) {
                Assert.assertTrue("Field " + field
                        + " should have @Valid annotation.",
                        AnnotationUtil.isValid(field));
                checkClass(field.getClass());
            } else if (EntityUtil.isEmbeddedId(field)) {
                String name;
                for (final Field one : EntityUtil
                        .getPersistedFields(entityClass)) {
                    if (EntityUtil.isMapsId(one)) {
                        name = EntityUtil.getMapsIdValue(one);
                        Assert.assertNotNull(InvocationUtil.getField(
                                field.getType(), name));
                    }
                }
                checkClass(field.getClass());
            } else if (!PRIMITIVES.contains(field.getType())) {
                if (ClassUtil.isAssignableFrom(field.getType(), Date.class)) {
                    Assert.fail("Use Calendar instead of Date for Field "
                            + field);
                } else {
                    Assert.fail("Field " + field
                            + " should probably have @Embedded annotation");
                }
            }
        }
    }

    /**
     * Check if entity has primitive member.
     *
     * @param entityClass
     *            the entity class to test.
     * @param field
     *            the field
     */
    private static void checkPrimitive(final Class<?> entityClass,
            final Field field) {
        // Entity shouldn't have primitive member, because the dao.findAll(E
        // criteria) method should accept field that can be null.
        Assert.assertFalse("Don't used primitive field: " + field, field
                .getType().isPrimitive());
    }

}
