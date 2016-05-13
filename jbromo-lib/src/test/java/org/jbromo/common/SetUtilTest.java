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
import java.util.Set;

import org.jbromo.common.test.common.ConstructorUtil;
import org.junit.Assert;
import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Test for the List Util class.
 * @author qjafcunuas
 */
public class SetUtilTest {

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        ConstructorUtil.executePrivate(SetUtil.class);
    }

    /**
     * Define an entity with Integer primary key.
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class MyObject {

        /**
         * The primary key.
         */
        private Integer primaryKey;
    };

    /**
     * Test the toSet() method.
     */
    @Test
    public void toSet() {
        Assert.assertNotNull(SetUtil.toSet());
        Assert.assertTrue(ClassUtil.isInstance(SetUtil.toSet(), Set.class));
        Assert.assertTrue(SetUtil.toSet().isEmpty());
    }

    /**
     * Test the toSet([]) method.
     */
    @Test
    public void toSetArray() {
        // Null collection.
        Assert.assertNotNull(SetUtil.toSet((Object[]) null));

        // Empty collection.
        Assert.assertTrue(SetUtil.toSet((Object[]) null).isEmpty());

        // Not empty collection.
        final Integer[] values = new Integer[IntegerUtil.INT_100];
        for (int i = IntegerUtil.INT_0; i < IntegerUtil.INT_100; i++) {
            values[i] = i;
        }
        final Set<Integer> integers = SetUtil.toSet(values);
        for (int i = IntegerUtil.INT_0; i < IntegerUtil.INT_100; i++) {
            Assert.assertTrue(integers.contains(i));
        }
        Assert.assertFalse(integers.contains(values.length));
    }

    /**
     * Test the toSet(Set) method.
     */
    @Test
    public void toSetCollection() {
        // Null collection.
        Assert.assertNotNull(SetUtil.toSet((Collection<?>) null));

        // Empty collection.
        Assert.assertTrue(SetUtil.toSet((Collection<?>) null).isEmpty());

        // Not empty collection.
        final Set<Integer> values = new HashSet<Integer>();
        for (int i = IntegerUtil.INT_0; i < IntegerUtil.INT_100; i++) {
            values.add(i);
        }
        final Set<Integer> integers = SetUtil.toSet(values);
        Assert.assertTrue(SetUtil.containsAll(values, integers));
        Assert.assertFalse(integers.contains(values.size()));
    }

    /**
     * Test the containsAll method.
     */
    @Test
    public void containsAll() {
        final Set<Integer> integers = new HashSet<Integer>();
        final Set<String> strings = new HashSet<String>();
        for (int i = IntegerUtil.INT_0; i < IntegerUtil.INT_100; i++) {
            integers.add(i);
            strings.add("string " + i);
        }
        final Set<Integer> anotherList = new HashSet<Integer>();
        anotherList.addAll(integers);

        Assert.assertFalse(SetUtil.containsAll((Set<?>) null, null));
        Assert.assertFalse(SetUtil.containsAll(integers, null));
        Assert.assertFalse(SetUtil.containsAll(null, integers));

        Assert.assertTrue(SetUtil.containsAll(strings, strings));
        Assert.assertTrue(SetUtil.containsAll(integers, integers));
        Assert.assertTrue(SetUtil.containsAll(integers, anotherList));

        anotherList.add(IntegerUtil.INT_1000);
        Assert.assertFalse(SetUtil.containsAll(integers, anotherList));
        anotherList.remove(Integer.valueOf(IntegerUtil.INT_1000));
        anotherList.remove(Integer.valueOf(IntegerUtil.INT_1));
        Assert.assertFalse(SetUtil.containsAll(integers, anotherList));
    }

    /**
     * Test the isEmpty method.
     */
    @Test
    public void isEmpty() {
        Set<Integer> set = null;
        Assert.assertTrue(SetUtil.isEmpty(set));
        set = new HashSet<Integer>();
        Assert.assertTrue(SetUtil.isEmpty(set));
        set.add(IntegerUtil.INT_0);
        Assert.assertFalse(SetUtil.isEmpty(set));
    }

    /**
     * Test the isNotEmpty method.
     */
    @Test
    public void isNotEmpty() {
        Set<Integer> set = null;
        Assert.assertFalse(SetUtil.isNotEmpty(set));
        set = new HashSet<Integer>();
        Assert.assertFalse(SetUtil.isNotEmpty(set));
        set.add(IntegerUtil.INT_0);
        Assert.assertTrue(SetUtil.isNotEmpty(set));
    }

    /**
     * Test the isSet(class) method.
     */
    @Test
    public void isSetClass() {
        Assert.assertFalse(SetUtil.isSet((Set<?>) null));
        Assert.assertFalse(SetUtil.isSet(ArrayList.class));
        Assert.assertTrue(SetUtil.isSet(HashSet.class));
    }

    /**
     * Test the isSet(object) method.
     */
    @Test
    public void isSetObject() {
        Assert.assertFalse(SetUtil.isSet((Object) null));
        Assert.assertFalse(SetUtil.isSet(new ArrayList<Object>()));
        Assert.assertTrue(SetUtil.isSet(new HashSet<Object>()));
    }

    /**
     * Test the reSet method.
     */
    @Test
    public void reSet() {
        try {
            SetUtil.reSet(null);
            SetUtil.reSet(new HashSet<MyObject>());
        } catch (final Exception e) {
            Assert.fail(e.getMessage());
        }

        final Set<MyObject> objects = new HashSet<MyObject>();
        final Set<MyObject> clone = new HashSet<MyObject>();
        MyObject object;
        for (int i = IntegerUtil.INT_1; i < IntegerUtil.INT_100; i++) {
            object = new MyObject();
            objects.add(object);
            object.setPrimaryKey(i);
            object = new MyObject(i);
            clone.add(object);
        }
        Assert.assertFalse(SetUtil.containsAll(objects, clone));
        SetUtil.reSet(objects);
        Assert.assertTrue(SetUtil.containsAll(objects, clone));
    }

    /**
     * Test hasOneElement method.
     */
    @Test
    public void hasOneElement() {
        Assert.assertFalse(SetUtil.hasOneElement(null));
        Assert.assertFalse(SetUtil.hasOneElement(new HashSet<MyObject>()));
        Assert.assertTrue(SetUtil.hasOneElement(SetUtil.toSet(1)));
        Assert.assertFalse(SetUtil.hasOneElement(SetUtil.toSet(1, 2)));
    }

    /**
     * Test hasElements method.
     */
    @Test
    public void hasElements() {
        Assert.assertFalse(SetUtil.hasElements(null));
        Assert.assertFalse(SetUtil.hasElements(new HashSet<MyObject>()));
        Assert.assertFalse(SetUtil.hasElements(SetUtil.toSet(1)));
        Assert.assertTrue(SetUtil.hasElements(SetUtil.toSet(1, 2)));
    }

}
