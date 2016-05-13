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
 * Define JUnit EnumUtil class.
 * @author qjafcunuas
 */
public class EnumUtilTest {

    /**
     * An enum for test.
     * @author qjafcunuas
     */
    private enum MyEnum {
        /** One value. */
        ONE, /** Another value. */
        TWO
    }

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        ConstructorUtil.executePrivate(EnumUtil.class);
    }

    /**
     * Test getValues method.
     */
    @Test
    public void getValues() {
        Assert.assertNull(EnumUtil.getValues(null));
        final MyEnum[] values = EnumUtil.getValues(MyEnum.class);
        Assert.assertNotNull(values);
        Assert.assertTrue(ArrayUtil.containsAll(MyEnum.values(), values, true));
    }

    /**
     * Test getConstants method.
     */
    @Test
    public void getConstants() {
        Assert.assertNull(EnumUtil.getConstants(null));
        final Enum<?>[] values = EnumUtil.getConstants(MyEnum.class);
        Assert.assertNotNull(values);
        Assert.assertTrue(ArrayUtil.containsAll(MyEnum.values(), values, true));
    }

}
