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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EmbeddedId;

import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.model.jpa.util.EntityUtil;
import org.junit.Assert;

/**
 * Define a string field builder.
 *
 * @author qjafcunuas
 *
 */
@Slf4j
public class FieldEmbeddedIdBuilder extends FieldEmbeddedBuilder {

    @Override
    protected Class<?> getManagedClass() {
        return EmbeddedId.class;
    }

    /**
     * Default constructor.
     *
     * @param fieldBuilderFactory
     *            the field builder factory to used.
     */
    FieldEmbeddedIdBuilder(final FieldBuilderFactory fieldBuilderFactory) {
        super(fieldBuilderFactory);
    }

    @Override
    public List<ValidationValue<Object>> getValidationErrorValues(
            final Field field) {
        // No error values.
        return new ArrayList<ValidationValue<Object>>();
    }

    @Override
    public List<ValidationValue<Object>> getValidationSuccessValues(
            final Field field) {
        return new ArrayList<ValidationValue<Object>>();
    }

    @Override
    public void setFieldValue(final Object to, final Field field,
            final Object fieldValue) {
        try {
            final Object toValue = InvocationUtil.getValue(to, field);
            Object embeddedValue = null;
            for (final Field embeddedField : EntityUtil
                    .getPersistedFields(fieldValue.getClass())) {
                // Read value from field.
                embeddedValue = InvocationUtil.getValue(fieldValue,
                        embeddedField);
                injectValue(toValue, embeddedField, embeddedValue);
            }
        } catch (final InvocationException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Override
    public Object nextRandom(final boolean nullable, final Field field) {
        try {
            final Object object = field.getType().newInstance();
            Object embeddedValue;
            for (final Field embeddedField : EntityUtil
                    .getPersistedFields(field.getType())) {
                embeddedValue = getFieldBuilderFactory().getBuilder(
                        embeddedField).nextRandom(false, embeddedField);
                injectValue(object, embeddedField, embeddedValue);
            }
            return object;
        } catch (final Exception e) {
            log.error("Cannot invocate field " + field, e);
            Assert.fail("Cannot invocate field " + field);
            return null;
        }
    }

}
