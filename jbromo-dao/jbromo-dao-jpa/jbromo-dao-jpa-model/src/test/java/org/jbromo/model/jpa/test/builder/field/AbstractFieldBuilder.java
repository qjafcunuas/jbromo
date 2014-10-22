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
package org.jbromo.model.jpa.test.builder.field;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.common.CollectionUtil;
import org.jbromo.common.IntegerUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.invocation.AnnotationUtil;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.common.invocation.ParameterizedTypeUtil;
import org.jbromo.model.jpa.compositepk.ICompositePk;
import org.jbromo.model.jpa.util.EntityUtil;
import org.junit.Assert;

/**
 * Define generic Field builder.
 *
 * @author qjafcunuas
 *
 * @param <T>
 *            the type of the field.
 */
public abstract class AbstractFieldBuilder<T> {

    /**
     * The field builder factory.
     */
    @Getter(AccessLevel.PACKAGE)
    private final FieldBuilderFactory fieldBuilderFactory;

    /**
     * Default constructor.
     *
     * @param fieldBuilderFactory
     *            the field builder factory to used.
     */
    AbstractFieldBuilder(final FieldBuilderFactory fieldBuilderFactory) {
        super();
        this.fieldBuilderFactory = fieldBuilderFactory;
    }

    /**
     * Return the managed class.
     *
     * @return the class.
     */
    protected Class<?> getManagedClass() {
        return ParameterizedTypeUtil.getClass(this, 0);
    }

    /**
     * Return non valid values for persisting entity field.
     *
     * @param field
     *            the field to get non valid values.
     * @return invalid values.
     */
    public abstract List<ValidationValue<T>> getValidationErrorValues(
            final Field field);

    /**
     * Return valid values for persisting entity field.
     *
     * @param field
     *            the field to get non valid values.
     * @return invalid values.
     */
    public abstract List<ValidationValue<T>> getValidationSuccessValues(
            final Field field);

    /**
     * Add null value into a list.
     *
     * @param field
     *            the field.
     * @param values
     *            the list.
     * @param valid
     *            true for validation success, false for validation error.
     */
    protected void addNull(final Field field,
            final List<ValidationValue<T>> values,
            final boolean valid) {
        if (valid && !AnnotationUtil.isNotNull(field)) {
            values.add(new ValidationValue<T>(field, "null value", null));
        } else if (!valid && AnnotationUtil.isNotNull(field)) {
            values.add(new ValidationValue<T>(field, "null value", null));
        }
    }

    /**
     * Return a random valid values.
     *
     * @param nullable
     *            if true, return value can be null.
     * @param field
     *            the field to get value.
     * @return the value.
     */
    public abstract T nextRandom(final boolean nullable, final Field field);

    /**
     * Return a random valid values.
     *
     * @param field
     *            the field to get value.
     * @param distinct
     *            the return value must not be equals to this parameter.
     * @return the value.
     */
    @SuppressWarnings("unchecked")
    public T nextRandom(final Field field, final T distinct) {
        if (distinct != null && CollectionUtil.isCollection(distinct)) {
            return nextRandom(field, (Collection<T>) distinct);
        }
        T value = nextRandom(false, field);
        if (distinct == null || value == null) {
            return value;
        }
        // no infinite loop.
        int count = IntegerUtil.INT_1000;
        while (value.equals(distinct) && count > IntegerUtil.INT_0) {
            value = nextRandom(false, field);
            count--;
        }
        Assert.assertFalse("Cannot generate another random value for "
                + distinct, ObjectUtil.equals(distinct, value));
        return value;
    }

    /**
     * Return a random valid values.
     *
     * @param field
     *            the field to get value.
     * @param distinct
     *            the return value must not be contained into this collection.
     * @return the value.
     */
    public T nextRandom(final Field field, final Collection<T> distinct) {
        T value = nextRandom(false, field);
        if (distinct == null || value == null) {
            return value;
        }
        // no infinite loop.
        int count = IntegerUtil.INT_1000;
        while (distinct.contains(value) && count > IntegerUtil.INT_0) {
            value = nextRandom(false, field);
            count--;
        }
        if (distinct.contains(value)) {
            return null;
        }
        return value;
    }

    /**
     * Set field value into it owner.
     *
     * @param to
     *            the owner object to set field value.
     * @param field
     *            the field of the owner to set.
     * @param fieldValue
     *            the value to set.
     */
    public void setFieldValue(final Object to, final Field field,
            final Object fieldValue) {
        try {
            if (EntityUtil.isId(field)) {
                // There is probably no setter for a primary key.
                injectValue(to, field, fieldValue);
            } else if (ICompositePk.class.isAssignableFrom(to.getClass())) {
                // There is probably no setter on an composite pk.
                injectValue(to, field, fieldValue);
            } else {
                InvocationUtil.setValue(to, field, fieldValue);
            }
        } catch (final InvocationException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Set field value into it owner.
     *
     * @param to
     *            the owner object to set field value.
     * @param field
     *            the field of the owner to set.
     * @param fieldValue
     *            the value to set.
     */
    public void setFieldValue(final Object to, final Field field,
            final ValidationValue<?> fieldValue) {
        setFieldValue(to, field, fieldValue.getValue());
    }

    /**
     * Set value to a field, directly to the field and not using setter method.
     *
     * @param object
     *            object to set value.
     * @param field
     *            the field
     * @param newValue
     *            the new value
     */
    protected void injectValue(final Object object, final Field field,
            final Object newValue) {
        try {
            InvocationUtil.injectValue(object, field, newValue);
        } catch (final InvocationException e) {
            Assert.fail("Cannot invocate InvocationUtil.injectValue");
        }
    }

}
