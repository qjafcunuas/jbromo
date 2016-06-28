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
import java.math.BigDecimal;
import java.util.List;

import org.jbromo.common.ListUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.common.invocation.AnnotationUtil;
import org.jbromo.model.jpa.util.EntityUtil;

/**
 * Define a BigDecimal field builder.
 * @author qjafcunuas
 */
public class FieldBigDecimalBuilder extends AbstractFieldBuilder<BigDecimal> {

    /**
     * Default constructor.
     * @param fieldBuilderFactory the field builder factory to used.
     */
    FieldBigDecimalBuilder(final FieldBuilderFactory fieldBuilderFactory) {
        super(fieldBuilderFactory);
    }

    @Override
    public List<ValidationValue<BigDecimal>> getValidationErrorValues(final Field field) {
        return getValidationValues(field, false);
    }

    @Override
    public List<ValidationValue<BigDecimal>> getValidationSuccessValues(final Field field) {
        return getValidationValues(field, true);
    }

    /**
     * Return values for annotation.
     * @param field the field.
     * @param valid if true, return valid values, else return non valid values.
     * @return the values.
     */
    private List<ValidationValue<BigDecimal>> getValidationValues(final Field field, final boolean valid) {
        final List<ValidationValue<BigDecimal>> values = ListUtil.toList();
        ValidationValue<BigDecimal> value;
        // Read annotation.
        final Integer integer = AnnotationUtil.getDigitsInteger(field);
        final Integer fraction = AnnotationUtil.getDigitsInteger(field);

        // integer/fraction
        final int add = valid ? 0 : 1;
        values.add(new ValidationValue<BigDecimal>(field, "integer value", RandomUtil.nextFullBigDecimal(integer + add, fraction)));
        values.add(new ValidationValue<BigDecimal>(field, "fraction value", RandomUtil.nextFullBigDecimal(integer, fraction + add)));

        // min
        value = min(field, false);
        if (value != null) {
            values.add(value);
        }
        // max
        value = max(field, false);
        if (value != null) {
            values.add(value);
        }

        // null value.
        addNull(field, values, valid);
        return values;
    }

    /**
     * Return value for Min annotation.
     * @param field the field.
     * @param valid if true, return the value, else return the next bigger value.
     * @return the value.
     */
    private ValidationValue<BigDecimal> min(final Field field, final boolean valid) {
        final BigDecimal value = AnnotationUtil.getDecimalMin(field);
        if (value == null) {
            return null;
        } else if (valid) {
            return new ValidationValue<BigDecimal>(field, "DecimalMin=" + value, value);
        } else {
            final Integer fraction = AnnotationUtil.getDigitsInteger(field);
            final BigDecimal val = new BigDecimal(Math.pow(10, -fraction));
            return new ValidationValue<BigDecimal>(field, "DecimalMin=" + value, value.add(val.negate()));
        }
    }

    /**
     * Return value for Max annotation.
     * @param field the field.
     * @param valid if true, return the value, else return the next bigger value.
     * @return the value.
     */
    private ValidationValue<BigDecimal> max(final Field field, final boolean valid) {
        final BigDecimal value = AnnotationUtil.getDecimalMax(field);
        if (value == null) {
            return null;
        } else if (valid) {
            return new ValidationValue<BigDecimal>(field, "DecimalMax=" + value, value);
        } else {
            final Integer fraction = AnnotationUtil.getDigitsInteger(field);
            final BigDecimal val = new BigDecimal(Math.pow(10, -fraction));
            return new ValidationValue<BigDecimal>(field, "DecimalMax=" + value, value.add(val));
        }
    }

    @Override
    public BigDecimal nextRandom(final boolean nullable, final Field field) {
        final boolean returnNull = nullable && EntityUtil.isNullable(field);
        final BigDecimal min = AnnotationUtil.getDecimalMin(field);
        final BigDecimal max = AnnotationUtil.getDecimalMax(field);
        final Integer integer = AnnotationUtil.getDigitsInteger(field);
        final Integer fraction = AnnotationUtil.getDigitsInteger(field);

        return RandomUtil.nextBigDecimal(returnNull, integer, fraction, min, max);
    }
}
