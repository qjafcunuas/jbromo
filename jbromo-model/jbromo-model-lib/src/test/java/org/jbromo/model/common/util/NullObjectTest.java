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
package org.jbromo.model.common.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Define JUnit INullObject class.
 * @author qjafcunuas
 */
public class NullObjectTest {

    /**
     * Test instantiation.
     */
    @Test
    public void test() {
        final INullObject oneNull = new INullObject() {
        };
        final INullObject twoNull = new INullObject() {
        };
        Assert.assertNotNull(oneNull);
        Assert.assertEquals(oneNull, oneNull);
        Assert.assertEquals(oneNull.hashCode(), oneNull.hashCode());

        Assert.assertNotNull(twoNull);
        Assert.assertEquals(twoNull, twoNull);
        Assert.assertEquals(twoNull.hashCode(), twoNull.hashCode());

        Assert.assertNotEquals(twoNull, oneNull);
        Assert.assertNotEquals(oneNull, twoNull);
        Assert.assertNotEquals(oneNull.hashCode(), twoNull.hashCode());
    }
}
