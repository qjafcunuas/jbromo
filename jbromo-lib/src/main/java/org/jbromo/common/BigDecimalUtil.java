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
package org.jbromo.common;

import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Define BigDecimal utility class.
 * @author qjafcunuas
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BigDecimalUtil {

    /**
     * Add numbers and return a BigDecimal values.
     * @param values the values to add.
     * @return the BigDecimal sum.
     */
    public static BigDecimal add(final Number... values) {
        BigDecimal sum = BigDecimal.ZERO;
        for (final Number value : values) {
            if (value == null) {
                return null;
            } else {
                if (ClassUtil.isInstance(value, Short.class)) {
                    sum = add(sum, (Short) value);
                } else if (ClassUtil.isInstance(value, Integer.class)) {
                    sum = add(sum, (Integer) value);
                } else if (ClassUtil.isInstance(value, Long.class)) {
                    sum = add(sum, (Long) value);
                } else if (ClassUtil.isInstance(value, Float.class)) {
                    sum = add(sum, (Float) value);
                } else if (ClassUtil.isInstance(value, Double.class)) {
                    sum = add(sum, (Double) value);
                } else if (ClassUtil.isInstance(value, BigInteger.class)) {
                    sum = add(sum, (BigInteger) value);
                } else if (ClassUtil.isInstance(value, BigDecimal.class)) {
                    sum = add(sum, (BigDecimal) value);
                }
            }
        }
        return sum;
    }

    /**
     * Add a Short value to a BigDecimal object.
     * @param value the BigDecimal value.
     * @param add the Short value.
     * @return the sum.
     */
    public static BigDecimal add(final BigDecimal value, final Short add) {
        if (value == null || add == null) {
            return null;
        }
        return value.add(BigDecimal.valueOf(add.longValue()));
    }

    /**
     * Add a Integer value to a BigDecimal object.
     * @param value the BigDecimal value.
     * @param add the Integer value.
     * @return the sum.
     */
    public static BigDecimal add(final BigDecimal value, final Integer add) {
        if (value == null || add == null) {
            return null;
        }
        return value.add(BigDecimal.valueOf(add.longValue()));
    }

    /**
     * Add a Long value to a BigDecimal object.
     * @param value the BigDecimal value.
     * @param add the Long value.
     * @return the sum.
     */
    public static BigDecimal add(final BigDecimal value, final Long add) {
        if (value == null || add == null) {
            return null;
        }
        return value.add(BigDecimal.valueOf(add.longValue()));
    }

    /**
     * Add a Float value to a BigDecimal object.
     * @param value the BigDecimal value.
     * @param add the Float value.
     * @return the sum.
     */
    public static BigDecimal add(final BigDecimal value, final Float add) {
        if (value == null || add == null) {
            return null;
        }
        return value.add(BigDecimal.valueOf(add.floatValue()));
    }

    /**
     * Add a Double value to a BigDecimal object.
     * @param value the BigDecimal value.
     * @param add the Double value.
     * @return the sum.
     */
    public static BigDecimal add(final BigDecimal value, final Double add) {
        if (value == null || add == null) {
            return null;
        }
        return value.add(BigDecimal.valueOf(add.doubleValue()));
    }

    /**
     * Add a BigInteger value to a BigDecimal object.
     * @param value the BigDecimal value.
     * @param add the BigInteger value.
     * @return the sum.
     */
    public static BigDecimal add(final BigDecimal value, final BigInteger add) {
        if (value == null || add == null) {
            return null;
        }
        return value.add(new BigDecimal(add));
    }

    /**
     * Add a BigDecimal value to a BigDecimal object.
     * @param value the BigDecimal value.
     * @param add the BigDecimal value.
     * @return the sum.
     */
    public static BigDecimal add(final BigDecimal value, final BigDecimal add) {
        if (value == null || add == null) {
            return null;
        }
        return value.add(add);
    }

    /**
     * Round BigDecimal with scale number after dot.
     * @param value the value to round.
     * @param scale the scale value.
     * @return rounded value.
     */
    public static BigDecimal round(final BigDecimal value, final int scale) {
        if (value == null) {
            return null;
        } else {
            return value.setScale(scale, BigDecimal.ROUND_HALF_UP);
        }
    }

}
