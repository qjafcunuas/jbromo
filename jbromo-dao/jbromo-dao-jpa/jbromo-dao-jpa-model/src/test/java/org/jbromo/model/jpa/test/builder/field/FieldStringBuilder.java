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
import java.util.List;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.ListUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.common.invocation.AnnotationUtil;
import org.jbromo.model.jpa.util.EntityUtil;

/**
 * Define a string field builder.
 *
 * @author qjafcunuas
 *
 */
public class FieldStringBuilder extends AbstractFieldBuilder<String> {

    /**
     * Default constructor.
     *
     * @param fieldBuilderFactory
     *            the field builder factory to used.
     */
    FieldStringBuilder(final FieldBuilderFactory fieldBuilderFactory) {
        super(fieldBuilderFactory);
    }

    @Override
    public List<ValidationValue<String>> getValidationErrorValues(
            final Field field) {
        return getValidationValues(field, false);
    }

    @Override
    public List<ValidationValue<String>> getValidationSuccessValues(
            final Field field) {
        return getValidationValues(field, true);
    }

    /**
     * Return value for SizeMax annotation.
     *
     * @param field
     *            the field.
     * @param valid
     *            if true, return string with max length, else return string
     *            with max+1 length.
     * @return the value.
     */
    private ValidationValue<String> sizeMax(final Field field,
            final boolean valid) {
        final Integer value = AnnotationUtil.getSizeMax(field);
        if (value == null) {
            return null;
        } else {
            final int add = valid ? 0 : 1;
            final String str = RandomUtil.nextString(value + add);
            return new ValidationValue<String>(field, "SizeMax=" + value, str);
        }
    }

    /**
     * Return value for SizeMin annotation.
     *
     * @param field
     *            the field.
     * @param valid
     *            if true, return string with min length, else return string
     *            with min-1 length.
     * @return the value.
     */
    private ValidationValue<String> sizeMin(final Field field,
            final boolean valid) {
        final Integer value = AnnotationUtil.getSizeMin(field);
        if (value == null || value < IntegerUtil.INT_1) {
            return null;
        } else {
            final int add = valid ? 0 : -1;
            return new ValidationValue<String>(field, "SizeMin=" + value,
                    RandomUtil.nextString(value + add));
        }
    }

    /**
     * Return values for annotation.
     *
     * @param field
     *            the field.
     * @param valid
     *            if true, return valid string, else return non valid string.
     * @return the values.
     */
    private List<ValidationValue<String>> getValidationValues(
            final Field field, final boolean valid) {
        final List<ValidationValue<String>> values = ListUtil.toList();
        // Max size.
        ValidationValue<String> value = sizeMax(field, valid);
        if (value != null) {
            values.add(value);
        }
        // Min size.
        value = sizeMin(field, valid);
        if (value != null) {
            values.add(value);
        }
        // Null value.
        addNull(field, values, valid);
        return values;
    }

    @Override
    public String nextRandom(final boolean nullable, final Field field) {
        final Integer min = AnnotationUtil.getSizeMin(field);
        final Integer max = AnnotationUtil.getSizeMax(field);
        final boolean returnNull = nullable && EntityUtil.isNullable(field);
        return RandomUtil.nextString(returnNull, min, max);
    }

}
