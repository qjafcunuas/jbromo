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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jbromo.common.test.common.ConstructorUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for the List Util class.
 * @author qjafcunuas
 */
public class ListUtilTest {

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        ConstructorUtil.executePrivate(ListUtil.class);
    }

    /**
     * Test the toArrayList([]) method.
     */
    @Test
    public void toListArray() {
        // Null collection.
        Assert.assertNotNull(ListUtil.toList((Object[]) null));

        // Empty collection.
        Assert.assertTrue(ListUtil.toList((Object[]) null).isEmpty());

        // Not empty collection.
        final Integer[] values = new Integer[IntegerUtil.INT_100];
        for (int i = IntegerUtil.INT_0; i < IntegerUtil.INT_100; i++) {
            values[i] = i;
        }
        final List<Integer> integers = ListUtil.toList(values);
        for (int i = IntegerUtil.INT_0; i < IntegerUtil.INT_100; i++) {
            Assert.assertTrue(integers.contains(i));
        }
        Assert.assertFalse(integers.contains(values.length));
    }

    /**
     * Test the toArrayList(Set) method.
     */
    @Test
    public void toListCollection() {
        // Null collection.
        Assert.assertNotNull(ListUtil.toList((Collection<?>) null));

        // Empty collection.
        Assert.assertTrue(ListUtil.toList((Collection<?>) null).isEmpty());

        // Not empty collection.
        final Set<Integer> values = new HashSet<Integer>();
        for (int i = IntegerUtil.INT_0; i < IntegerUtil.INT_100; i++) {
            values.add(i);
        }
        final List<Integer> integers = ListUtil.toList(values);
        Assert.assertTrue(CollectionUtil.containsAll(values, integers, true));
        Assert.assertFalse(integers.contains(values.size()));
    }

    /**
     * Test the equcontainsAllals method.
     */
    @Test
    public void containsAll() {
        final List<Integer> integers = new ArrayList<Integer>();
        final List<String> strings = new ArrayList<String>();
        for (int i = IntegerUtil.INT_0; i < IntegerUtil.INT_100; i++) {
            integers.add(i);
            strings.add("string " + i);
        }
        final List<Integer> anotherList = new ArrayList<Integer>();
        anotherList.addAll(integers);

        Assert.assertFalse(ListUtil.containsAll((List<?>) null, null, true));
        Assert.assertFalse(ListUtil.containsAll(integers, null, true));
        Assert.assertFalse(ListUtil.containsAll(null, integers, true));

        Assert.assertTrue(ListUtil.containsAll(strings, strings, true));
        Assert.assertTrue(ListUtil.containsAll(integers, integers, true));
        Assert.assertTrue(ListUtil.containsAll(integers, anotherList, true));

        anotherList.add(IntegerUtil.INT_1000);
        Assert.assertFalse(ListUtil.containsAll(integers, anotherList, true));
        anotherList.remove(Integer.valueOf(IntegerUtil.INT_1000));
        anotherList.remove(Integer.valueOf(IntegerUtil.INT_1));
        Assert.assertFalse(ListUtil.containsAll(integers, anotherList, true));
    }

    /**
     * Test the isEmpty method.
     */
    @Test
    public void isEmpty() {
        List<Integer> list = null;
        Assert.assertTrue(ListUtil.isEmpty(list));
        list = new ArrayList<Integer>();
        Assert.assertTrue(ListUtil.isEmpty(list));
        list.add(IntegerUtil.INT_0);
        Assert.assertFalse(ListUtil.isEmpty(list));
    }

    /**
     * Test the isNotEmpty method.
     */
    @Test
    public void isNotEmpty() {
        List<Integer> list = null;
        Assert.assertFalse(ListUtil.isNotEmpty(list));
        list = new ArrayList<Integer>();
        Assert.assertFalse(ListUtil.isNotEmpty(list));
        list.add(IntegerUtil.INT_0);
        Assert.assertTrue(ListUtil.isNotEmpty(list));
    }

    /**
     * Test the hasOneElement method.
     */
    @Test
    public void hasOneElement() {
        List<Integer> list = null;
        Assert.assertFalse(ListUtil.hasOneElement(list));
        list = new ArrayList<Integer>();
        Assert.assertFalse(ListUtil.hasOneElement(list));
        list.add(IntegerUtil.INT_0);
        Assert.assertTrue(ListUtil.hasOneElement(list));
        list.add(IntegerUtil.INT_1);
        Assert.assertFalse(ListUtil.hasOneElement(list));
        list.add(IntegerUtil.INT_2);
        Assert.assertFalse(ListUtil.hasOneElement(list));
    }

    /**
     * Test the hasElements method.
     */
    @Test
    public void hasElements() {
        List<Integer> list = null;
        Assert.assertFalse(ListUtil.hasElements(list));
        list = new ArrayList<Integer>();
        Assert.assertFalse(ListUtil.hasElements(list));
        list.add(IntegerUtil.INT_0);
        Assert.assertFalse(ListUtil.hasElements(list));
        list.add(IntegerUtil.INT_1);
        Assert.assertTrue(ListUtil.hasElements(list));
        list.add(IntegerUtil.INT_2);
        Assert.assertTrue(ListUtil.hasElements(list));
    }

    /**
     * Test the isList(class) method.
     */
    @Test
    public void isListClass() {
        Assert.assertFalse(ListUtil.isList((Class<?>) null));
        Assert.assertTrue(ListUtil.isList(ArrayList.class));
        Assert.assertFalse(ListUtil.isList(HashSet.class));
    }

    /**
     * Test the isList(object) method.
     */
    @Test
    public void isListObject() {
        Assert.assertFalse(ListUtil.isList((Object) null));
        Assert.assertTrue(ListUtil.isList(new ArrayList<Object>()));
        Assert.assertFalse(ListUtil.isList(new HashSet<Object>()));
    }

    /**
     * Test the toUnmodifiableList(array) method.
     */
    @Test
    public void toUnmodifiableArray() {
        // Null array.
        List<Integer> list = ListUtil.toUnmodifiableList((Integer[]) null);
        try {
            list.add(null);
            Assert.fail("Should not added object in an unmodifiable list");
        } catch (final UnsupportedOperationException e) {
            Assert.assertTrue(true);
        }

        // Empty array.
        list = ListUtil.toUnmodifiableList(new Integer[0]);
        try {
            list.add(null);
            Assert.fail("Should not added object in an unmodifiable list");
        } catch (final UnsupportedOperationException e) {
            Assert.assertTrue(true);
        }

        // Not empty array.
        list = ListUtil.toUnmodifiableList(1, 2);
        try {
            list.add(null);
            Assert.fail("Should not added object in an unmodifiable list");
        } catch (final UnsupportedOperationException e) {
            Assert.assertTrue(true);
        }

    }

    /**
     * Test the toUnmodifiableList(collection) method.
     */
    @Test
    public void toUnmodifiableCollection() {
        // Null collection.
        Collection<Integer> col = null;
        List<Integer> list = ListUtil.toUnmodifiableList(col);
        try {
            list.add(null);
            Assert.fail("Should not added object in an unmodifiable list");
        } catch (final UnsupportedOperationException e) {
            Assert.assertTrue(true);
        }

        // Empty collection.
        col = ListUtil.toList();
        list = ListUtil.toUnmodifiableList(col);
        try {
            list.add(null);
            Assert.fail("Should not added object in an unmodifiable list");
        } catch (final UnsupportedOperationException e) {
            Assert.assertTrue(true);
        }

        // Not empty collection.
        col = ListUtil.toList(1, 2);
        list = ListUtil.toUnmodifiableList(col);
        try {
            list.add(null);
            Assert.fail("Should not added object in an unmodifiable list");
        } catch (final UnsupportedOperationException e) {
            Assert.assertTrue(true);
        }
    }

    /**
     * Test the getLast method.
     */
    @Test
    public void getLast() {
        Assert.assertNull(ListUtil.getLast(null));
        Assert.assertNull(ListUtil.getLast(ListUtil.toList()));
        Assert.assertEquals(ListUtil.getLast(ListUtil.toList(IntegerUtil.INT_0)), Integer.valueOf(IntegerUtil.INT_0));
        Assert.assertEquals(ListUtil.getLast(ListUtil.toList(IntegerUtil.INT_0, IntegerUtil.INT_1)), Integer.valueOf(IntegerUtil.INT_1));
        Assert.assertEquals(ListUtil.getLast(ListUtil.toList(IntegerUtil.INT_0, IntegerUtil.INT_1, IntegerUtil.INT_2)),
                            Integer.valueOf(IntegerUtil.INT_2));
    }

}
