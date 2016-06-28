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
package org.jbromo.model.jpa.testutil.builder.field;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.Embedded;

import org.jbromo.common.ListUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.common.invocation.AnnotationUtil;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.model.jpa.util.EntityUtil;
import org.junit.Assert;

import lombok.extern.slf4j.Slf4j;

/**
 * Define a string field builder.
 * @author qjafcunuas
 */
@Slf4j
public class FieldEmbeddedBuilder extends AbstractFieldBuilder<Object> {

    /**
     * Default constructor.
     * @param fieldBuilderFactory the field builder factory to used.
     */
    FieldEmbeddedBuilder(final FieldBuilderFactory fieldBuilderFactory) {
        super(fieldBuilderFactory);
    }

    @Override
    protected Class<?> getManagedClass() {
        return Embedded.class;
    }

    @Override
    public List<ValidationValue<Object>> getValidationErrorValues(final Field field) {
        final List<ValidationValue<Object>> values = ListUtil.toList();
        List<ValidationValue<Object>> embeddedValues = null;
        AbstractFieldBuilder<Object> fieldBuilder;
        Object value = null;
        for (final Field embeddedField : EntityUtil.getPersistedFields(field.getType())) {
            fieldBuilder = getFieldBuilderFactory().getBuilder(embeddedField);
            embeddedValues = fieldBuilder.getValidationErrorValues(embeddedField);
            for (final ValidationValue<Object> embeddedValue : embeddedValues) {
                value = nextRandom(true, field);
                fieldBuilder.setFieldValue(value, embeddedField, embeddedValue);
                values.add(new ValidationValue<Object>(embeddedValue, value));
            }
        }
        return values;
    }

    @Override
    public List<ValidationValue<Object>> getValidationSuccessValues(final Field field) {
        Assert.fail("Not implemented");
        return null;
    }

    @Override
    public void setFieldValue(final Object to, final Field field, final Object fieldValue) {
        try {
            final Object toValue = InvocationUtil.getValue(to, field);
            Assert.assertNotNull("Null value return by getter on embedded field " + field, toValue);
            AbstractFieldBuilder<Object> fieldBuilder = null;
            Object embeddedValue = null;
            for (final Field embeddedField : EntityUtil.getPersistedFields(fieldValue.getClass())) {
                // Read value from field.
                embeddedValue = InvocationUtil.getValue(fieldValue, embeddedField);
                // Set value into to object.
                fieldBuilder = getFieldBuilderFactory().getBuilder(embeddedField);
                fieldBuilder.setFieldValue(toValue, embeddedField, embeddedValue);
            }
        } catch (final InvocationException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Override
    public Object nextRandom(final boolean nullable, final Field field) {
        final boolean returnNull = nullable && !AnnotationUtil.isNotNull(field);
        if (RandomUtil.isNull(returnNull)) {
            return ObjectUtil.newInstance(field.getType());
        }
        try {
            final Object object = field.getType().newInstance();
            Object fieldValue;
            for (final Field embeddedField : EntityUtil.getPersistedFields(field.getType())) {
                fieldValue = getFieldBuilderFactory().getBuilder(embeddedField).nextRandom(nullable, embeddedField);
                getFieldBuilderFactory().getBuilder(embeddedField).setFieldValue(object, embeddedField, fieldValue);
            }
            return object;
        } catch (final Exception e) {
            log.error("Cannot invocate field " + field, e);
            Assert.fail("Cannot invocate field " + field);
            return null;
        }
    }

}
