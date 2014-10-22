/*
 * Copyright (C) 2013-2014 The JBromo Authors.
import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.Embedded;

import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.ListUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.common.invocation.AnnotationUtil;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.model.jpa.util.EntityUtil;
import org.junit.Assert;
n
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
import java.util.List;

import javax.persistence.Embedded;

import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.ListUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.common.invocation.AnnotationUtil;
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
public class FieldEmbeddedBuilder extends AbstractFieldBuilder<Object> {

    /**
     * Default constructor.
     *
     * @param fieldBuilderFactory
     *            the field builder factory to used.
     */
    FieldEmbeddedBuilder(final FieldBuilderFactory fieldBuilderFactory) {
        super(fieldBuilderFactory);
    }

    @Override
    protected Class<?> getManagedClass() {
        return Embedded.class;
    }

    @Override
    public List<ValidationValue<Object>> getValidationErrorValues(
            final Field field) {
        final List<ValidationValue<Object>> values = ListUtil.toList();
        List<ValidationValue<Object>> embeddedValues = null;
        AbstractFieldBuilder<Object> fieldBuilder;
        Object value = null;
        for (final Field embeddedField : EntityUtil.getPersistedFields(field
                .getType())) {
            fieldBuilder = getFieldBuilderFactory().getBuilder(embeddedField);
            embeddedValues = fieldBuilder
                    .getValidationErrorValues(embeddedField);
            for (final ValidationValue<Object> embeddedValue : embeddedValues) {
                value = nextRandom(true, field);
                fieldBuilder.setFieldValue(value, embeddedField, embeddedValue);
                values.add(new ValidationValue<Object>(embeddedValue, value));
            }
        }
        return values;
    }

    @Override
    public List<ValidationValue<Object>> getValidationSuccessValues(
            final Field field) {
        Assert.fail("Not implemented");
        return null;
    }

    @Override
    public void setFieldValue(final Object to, final Field field,
            final Object fieldValue) {
        try {
            final Object toValue = InvocationUtil.getValue(to, field);
            Assert.assertNotNull(
                    "Null value return by getter on embedded field " + field,
                    toValue);
            AbstractFieldBuilder<Object> fieldBuilder = null;
            Object embeddedValue = null;
            for (final Field embeddedField : EntityUtil
                    .getPersistedFields(fieldValue.getClass())) {
                // Read value from field.
                embeddedValue = InvocationUtil.getValue(fieldValue,
                        embeddedField);
                // Set value into to object.
                fieldBuilder = getFieldBuilderFactory().getBuilder(
                        embeddedField);
                fieldBuilder.setFieldValue(toValue, embeddedField,
                        embeddedValue);
            }
        } catch (final InvocationException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Override
    public Object nextRandom(final boolean nullable, final Field field) {
        final boolean returnNull = nullable && !AnnotationUtil.isNotNull(field);
        if (returnNull && RandomUtil.isNull()) {
            try {
                return ObjectUtil.newInstance(field.getType());
            } catch (final MessageLabelException e) {
                log.error("Cannot create new instance of field " + field, e);
                Assert.fail("Cannot create new instance for field " + field);
                return null;
            }
        }
        try {
            final Object object = field.getType().newInstance();
            Object fieldValue;
            for (final Field embeddedField : EntityUtil
                    .getPersistedFields(field.getType())) {
                fieldValue = getFieldBuilderFactory().getBuilder(embeddedField)
                        .nextRandom(nullable, embeddedField);
                getFieldBuilderFactory().getBuilder(embeddedField)
                        .setFieldValue(object, embeddedField, fieldValue);
            }
            return object;
        } catch (final Exception e) {
            log.error("Cannot invocate field " + field, e);
            Assert.fail("Cannot invocate field " + field);
            return null;
        }
    }

}
