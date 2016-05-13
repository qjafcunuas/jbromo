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

import org.junit.Assert;
import org.junit.Test;

/**
 * Define JUnit for BigDecimalUtil class.
 * @author qjafcunuas
 */
public class BigDecimalUtilTest {

    /**
     * Test add(BigDecimal,Short) method.
     */
    @Test
    public void addShort() {
        // null + null
        BigDecimal value = null;
        Short add = null;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // not null + null
        value = BigDecimal.ZERO;
        add = null;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // null + not null
        value = null;
        add = 1;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // not null + not null.
        value = BigDecimal.ZERO;
        add = 1;
        Assert.assertEquals(BigDecimal.ONE, BigDecimalUtil.add(value, add));
    }

    /**
     * Test add(BigDecimal,Integer) method.
     */
    @Test
    public void addInteger() {
        // null + null
        BigDecimal value = null;
        Integer add = null;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // not null + null
        value = BigDecimal.ZERO;
        add = null;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // null + not null
        value = null;
        add = 1;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // not null + not null.
        value = BigDecimal.ZERO;
        add = 1;
        Assert.assertEquals(BigDecimal.ONE, BigDecimalUtil.add(value, add));
    }

    /**
     * Test add(BigDecimal,Long) method.
     */
    @Test
    public void addLong() {
        // null + null
        BigDecimal value = null;
        Long add = null;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // not null + null
        value = BigDecimal.ZERO;
        add = null;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // null + not null
        value = null;
        add = 1L;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // not null + not null.
        value = BigDecimal.ZERO;
        add = 1L;
        Assert.assertEquals(BigDecimal.ONE, BigDecimalUtil.add(value, add));
    }

    /**
     * Test add(BigDecimal,Float) method.
     */
    @Test
    public void addFloat() {
        // null + null
        BigDecimal value = null;
        Float add = null;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // not null + null
        value = BigDecimal.ZERO;
        add = null;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // null + not null
        value = null;
        add = 1.1f;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // not null + not null.
        value = BigDecimal.ZERO;
        add = 1.1f;
        Assert.assertEquals(new BigDecimal("1.1"), BigDecimalUtil.round(BigDecimalUtil.add(value, add), 1));
    }

    /**
     * Test add(BigDecimal,Double) method.
     */
    @Test
    public void addDouble() {
        // null + null
        BigDecimal value = null;
        Double add = null;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // not null + null
        value = BigDecimal.ZERO;
        add = null;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // null + not null
        value = null;
        add = 1.1;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // not null + not null.
        value = BigDecimal.ZERO;
        add = 1.1;
        Assert.assertEquals(new BigDecimal("1.1"), BigDecimalUtil.round(BigDecimalUtil.add(value, add), 1));
    }

    /**
     * Test add(BigDecimal,BigInteger) method.
     */
    @Test
    public void addBigInteger() {
        // null + null
        BigDecimal value = null;
        BigInteger add = null;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // not null + null
        value = BigDecimal.ZERO;
        add = null;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // null + not null
        value = null;
        add = BigInteger.ONE;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // not null + not null.
        value = BigDecimal.ZERO;
        add = BigInteger.ONE;
        Assert.assertEquals(BigDecimal.ONE, BigDecimalUtil.add(value, add));
    }

    /**
     * Test add(BigDecimal,BigDecimal) method.
     */
    @Test
    public void addBigDecimal() {
        // null + null
        BigDecimal value = null;
        BigDecimal add = null;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // not null + null
        value = BigDecimal.ZERO;
        add = null;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // null + not null
        value = null;
        add = BigDecimal.ONE;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // not null + not null.
        value = BigDecimal.ZERO;
        add = BigDecimal.ONE;
        Assert.assertEquals(BigDecimal.ONE, BigDecimalUtil.add(value, add));
    }

    /**
     * Test add(Number...) method.
     */
    @Test
    public void addNumber() {
        // null + null
        Number value = null;
        Number add = null;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // not null + null
        value = BigDecimal.ZERO;
        add = null;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // null + not null
        value = null;
        add = 1;
        Assert.assertNull(BigDecimalUtil.add(value, add));

        // not null + not null.
        value = BigDecimal.ZERO;
        add = 1;
        Assert.assertEquals(BigDecimal.ONE, BigDecimalUtil.add(value, add));
    }

    /**
     * Test round method.
     */
    @Test
    public void round() {
        // null
        BigDecimal value = null;
        int scale = 0;
        Assert.assertNull(BigDecimalUtil.round(value, scale));

        // not null
        value = new BigDecimal("1.23456");
        scale = 2;
        Assert.assertEquals(new BigDecimal("1.23"), BigDecimalUtil.round(value, scale));

        value = new BigDecimal("1.23499");
        scale = 2;
        Assert.assertEquals(new BigDecimal("1.23"), BigDecimalUtil.round(value, scale));

        value = new BigDecimal("1.23500");
        scale = 2;
        Assert.assertEquals(new BigDecimal("1.24"), BigDecimalUtil.round(value, scale));
    }

}
