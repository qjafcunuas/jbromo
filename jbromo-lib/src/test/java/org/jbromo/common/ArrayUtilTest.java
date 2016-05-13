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

import java.util.ArrayList;
import java.util.List;

import org.jbromo.common.test.common.ConstructorUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for the Boolean util class.
 * @author qjafcunuas
 */
public class ArrayUtilTest {

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        ConstructorUtil.executePrivate(ArrayUtil.class);
    }

    /**
     * Test the toArray method.
     */
    @Test
    public void toArray() {
        final List<Integer> list = new ArrayList<Integer>();
        for (int i = IntegerUtil.INT_0; i < IntegerUtil.INT_100; i++) {
            list.add(i);
        }
        // toArray
        final Integer[] array = ArrayUtil.toArray(list, Integer.class);
        Assert.assertTrue(CollectionUtil.containsAll(list, array, true));
    }

    /**
     * Test the isEmpty method.
     */
    @Test
    public void isEmpty() {
        Assert.assertTrue(ArrayUtil.isEmpty(null));
        Assert.assertTrue(ArrayUtil.isEmpty(new Integer[0]));
        Assert.assertFalse(ArrayUtil.isEmpty(new Integer[1]));
    }

    /**
     * Test the containsAll method.
     */
    @Test
    public void containsAll() {
        final Integer[] integers = new Integer[] {1, 2};
        // null array
        Assert.assertFalse(ArrayUtil.containsAll(null, null, true));
        Assert.assertFalse(ArrayUtil.containsAll(null, null, false));
        Assert.assertFalse(ArrayUtil.containsAll(null, integers, true));
        Assert.assertFalse(ArrayUtil.containsAll(null, integers, false));
        Assert.assertFalse(ArrayUtil.containsAll(integers, null, true));
        Assert.assertFalse(ArrayUtil.containsAll(integers, null, false));

        // empty array
        Assert.assertTrue(ArrayUtil.containsAll(new Integer[] {}, new Integer[] {}, false));
        Assert.assertTrue(ArrayUtil.containsAll(new Integer[] {}, new Integer[] {}, true));
        Assert.assertFalse(ArrayUtil.containsAll(integers, new Integer[] {}, false));
        Assert.assertFalse(ArrayUtil.containsAll(integers, new Integer[] {}, true));
        Assert.assertFalse(ArrayUtil.containsAll(new Integer[] {}, integers, false));
        Assert.assertFalse(ArrayUtil.containsAll(new Integer[] {}, integers, true));

        Integer[] another = new Integer[] {1, 2};

        // same array
        Assert.assertTrue(ArrayUtil.containsAll(integers, integers, false));
        Assert.assertTrue(ArrayUtil.containsAll(integers, integers, true));
        Assert.assertTrue(ArrayUtil.containsAll(integers, another, false));
        Assert.assertTrue(ArrayUtil.containsAll(another, integers, true));
        another = new Integer[] {2, 1};
        Assert.assertTrue(ArrayUtil.containsAll(integers, another, false));
        Assert.assertTrue(ArrayUtil.containsAll(another, integers, true));

        // Not same array
        another = new Integer[] {0, 1, 2};
        Assert.assertFalse(ArrayUtil.containsAll(integers, another, false));
        Assert.assertFalse(ArrayUtil.containsAll(another, integers, true));
        another = new Integer[] {0, 1};
        Assert.assertFalse(ArrayUtil.containsAll(integers, another, false));
        Assert.assertFalse(ArrayUtil.containsAll(another, integers, true));
    }

}
