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

import org.jbromo.common.test.common.ConstructorUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for the Boolean util class.
 * @author qjafcunuas
 */
public class BooleanUtilTest {

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        ConstructorUtil.executePrivate(BooleanUtil.class);
    }

    /**
     * Test the isTrue method.
     */
    @Test
    public void isTrue() {
        Assert.assertTrue(BooleanUtil.isTrue(Boolean.TRUE));
        Assert.assertFalse(BooleanUtil.isTrue(Boolean.FALSE));
        Assert.assertFalse(BooleanUtil.isTrue(null));
    }

    /**
     * Test the isFalse method.
     */
    @Test
    public void isFalse() {
        Assert.assertFalse(BooleanUtil.isFalse(Boolean.TRUE));
        Assert.assertTrue(BooleanUtil.isFalse(Boolean.FALSE));
        Assert.assertFalse(BooleanUtil.isFalse(null));
    }

    /**
     * Test the isNull method.
     */
    @Test
    public void isNull() {
        Assert.assertFalse(BooleanUtil.isNull(Boolean.TRUE));
        Assert.assertFalse(BooleanUtil.isNull(Boolean.FALSE));
        Assert.assertTrue(BooleanUtil.isNull(null));
    }

    /**
     * Test the isNullOrTrue method.
     */
    @Test
    public void isNullOrTrue() {
        Assert.assertTrue(BooleanUtil.isNullOrTrue(Boolean.TRUE));
        Assert.assertFalse(BooleanUtil.isNullOrTrue(Boolean.FALSE));
        Assert.assertTrue(BooleanUtil.isNullOrTrue(null));
    }

    /**
     * Test the isNullOrFalse method.
     */
    @Test
    public void isNullOrFalse() {
        Assert.assertFalse(BooleanUtil.isNullOrFalse(Boolean.TRUE));
        Assert.assertTrue(BooleanUtil.isNullOrFalse(Boolean.FALSE));
        Assert.assertTrue(BooleanUtil.isNullOrFalse(null));
    }

    /**
     * Test field ALL.
     */
    @Test
    public void fieldAll() {
        Assert.assertNotNull(BooleanUtil.ALL);
        Assert.assertTrue(BooleanUtil.ALL.contains(Boolean.FALSE));
        Assert.assertTrue(BooleanUtil.ALL.contains(Boolean.TRUE));
        try {
            BooleanUtil.ALL.add(true);
            Assert.fail("Should not add element into this list");
        } catch (final Exception e) {
            Assert.assertTrue(true);
        }
    }

    /**
     * Test field EMPTY.
     */
    @Test
    public void fieldEmpty() {
        Assert.assertNotNull(BooleanUtil.EMPTY);
        Assert.assertFalse(BooleanUtil.EMPTY.contains(Boolean.FALSE));
        Assert.assertFalse(BooleanUtil.EMPTY.contains(Boolean.TRUE));
        try {
            BooleanUtil.EMPTY.add(true);
            Assert.fail("Should not add element into this list");
        } catch (final Exception e) {
            Assert.assertTrue(true);
        }
    }

}
