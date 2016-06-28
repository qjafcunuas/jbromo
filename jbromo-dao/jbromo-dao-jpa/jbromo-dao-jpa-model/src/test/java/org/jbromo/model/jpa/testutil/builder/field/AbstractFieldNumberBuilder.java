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

import org.jbromo.common.ListUtil;
import org.jbromo.common.invocation.AnnotationUtil;

/**
 * Define a number field builder.
 * @param <N> the number type.
 * @author qjafcunuas
 */
public abstract class AbstractFieldNumberBuilder<N extends Number> extends AbstractFieldBuilder<N> {

    /**
     * Default constructor.
     * @param fieldBuilderFactory the field builder factory to used.
     */
    AbstractFieldNumberBuilder(final FieldBuilderFactory fieldBuilderFactory) {
        super(fieldBuilderFactory);
    }

    /**
     * Return maximum value.
     * @param value the value.
     * @param valid if true, return the value, else return the next bigger value.
     * @return the next value.
     */
    protected abstract N max(final Number value, final boolean valid);

    /**
     * Return minimum value.
     * @param value the value.
     * @param valid if true, return the value, else return the previous bigger value.
     * @return the previous value.
     */
    protected abstract N min(final Number value, final boolean valid);

    @Override
    public List<ValidationValue<N>> getValidationErrorValues(final Field field) {
        return getValidationValues(field, false);
    }

    @Override
    public List<ValidationValue<N>> getValidationSuccessValues(final Field field) {
        return getValidationValues(field, true);
    }

    /**
     * Return values for annotation.
     * @param field the field.
     * @param valid if true, return valid number, else return non valid number.
     * @return the values.
     */
    private List<ValidationValue<N>> getValidationValues(final Field field, final boolean valid) {
        final List<ValidationValue<N>> values = ListUtil.toList();
        // Max.
        ValidationValue<N> value = max(field, valid);
        if (value != null) {
            values.add(value);
        }
        // Min.
        value = min(field, valid);
        if (value != null) {
            values.add(value);
        }
        // null value.
        addNull(field, values, valid);
        return values;
    }

    /**
     * Return the max value.
     * @param field the field.
     * @return the value.
     */
    protected Number getMax(final Field field) {
        return AnnotationUtil.getMax(field);
    }

    /**
     * Return the min value.
     * @param field the field.
     * @return the value.
     */
    protected Number getMin(final Field field) {
        return AnnotationUtil.getMin(field);
    }

    /**
     * Return value for Max annotation.
     * @param field the field.
     * @param valid if true, return the value, else return the next bigger value.
     * @return the value.
     */
    private ValidationValue<N> max(final Field field, final boolean valid) {
        final Number value = getMax(field);
        if (value == null) {
            return null;
        } else {
            return new ValidationValue<N>(field, "@Max=" + value, max(value, valid));
        }
    }

    /**
     * Return value for Min annotation.
     * @param field the field.
     * @param valid if true, return the value, else return the previous bigger value.
     * @return the value.
     */
    private ValidationValue<N> min(final Field field, final boolean valid) {
        final Number value = getMin(field);
        if (value == null) {
            return null;
        } else {
            return new ValidationValue<N>(field, "@Max=" + value, min(value, valid));
        }
    }

}
