/*
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

import org.jbromo.common.test.common.ConstructorUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for the Map Util class.
 *
 * @author qjafcunuas
 *
 */
public class MapUtilTest {

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        ConstructorUtil.executePrivate(MapUtil.class);
    }

    /**
     * Test the containsAll method.
     */
    @Test
    public void containsAll() {
        final Map<Integer, Integer> integers = new HashMap<Integer, Integer>();
        final Map<String, String> strings = new HashMap<String, String>();
        for (int i = IntegerUtil.INT_0; i < IntegerUtil.INT_100; i++) {
            integers.put(i, i + RandomUtil.nextInt(IntegerUtil.INT_100));
            strings.put("string " + i,
                    "string " + i + RandomUtil.nextInt(IntegerUtil.INT_100));
        }
        final Map<Integer, Integer> anotherMap = new HashMap<Integer, Integer>();
        anotherMap.putAll(integers);

        Assert.assertFalse(MapUtil.containsAll((Map<?, ?>) null, null));
        Assert.assertFalse(MapUtil.containsAll(integers, null));
        Assert.assertFalse(MapUtil.containsAll(null, integers));

        Assert.assertTrue(MapUtil.containsAll(strings, strings));
        Assert.assertTrue(MapUtil.containsAll(integers, integers));
        Assert.assertTrue(MapUtil.containsAll(integers, anotherMap));

        anotherMap.put(IntegerUtil.INT_1000,
                RandomUtil.nextInt(IntegerUtil.INT_100, IntegerUtil.INT_1000));
        Assert.assertFalse(MapUtil.containsAll(integers, anotherMap));
        anotherMap.keySet().remove(Integer.valueOf(IntegerUtil.INT_1000));
        anotherMap.keySet().remove(Integer.valueOf(IntegerUtil.INT_1));
        Assert.assertFalse(MapUtil.containsAll(integers, anotherMap));

        Assert.assertTrue(MapUtil.containsAll(strings,
                MapUtil.toSynchronizedMap(strings)));

    }

    /**
     * Test the isEmpty method.
     */
    @Test
    public void isEmpty() {
        Map<Integer, Integer> map = null;
        Assert.assertTrue(MapUtil.isEmpty(map));
        map = new HashMap<Integer, Integer>();
        Assert.assertTrue(MapUtil.isEmpty(map));
        map.put(IntegerUtil.INT_0, IntegerUtil.INT_0);
        Assert.assertFalse(MapUtil.isEmpty(map));
    }

    /**
     * Test the isNotEmpty method.
     */
    @Test
    public void isNotEmpty() {
        Map<Integer, Integer> map = null;
        Assert.assertFalse(MapUtil.isNotEmpty(map));
        map = new HashMap<Integer, Integer>();
        Assert.assertFalse(MapUtil.isNotEmpty(map));
        map.put(IntegerUtil.INT_0, IntegerUtil.INT_0);
        Assert.assertTrue(MapUtil.isNotEmpty(map));
    }

    /**
     * Test the hasOneElement method.
     */
    @Test
    public void hasOneElement() {
        Map<Integer, Integer> map = null;
        Assert.assertFalse(MapUtil.hasOneElement(map));
        map = new HashMap<Integer, Integer>();
        Assert.assertFalse(MapUtil.hasOneElement(map));
        map.put(IntegerUtil.INT_0, IntegerUtil.INT_0);
        Assert.assertTrue(MapUtil.hasOneElement(map));
        map.put(IntegerUtil.INT_1, IntegerUtil.INT_1);
        Assert.assertFalse(MapUtil.hasOneElement(map));
        map.put(IntegerUtil.INT_2, IntegerUtil.INT_2);
        Assert.assertFalse(MapUtil.hasOneElement(map));
    }

    /**
     * Test the hasElements method.
     */
    @Test
    public void hasElements() {
        Map<Integer, Integer> map = null;
        Assert.assertFalse(MapUtil.hasElements(map));
        map = new HashMap<Integer, Integer>();
        Assert.assertFalse(MapUtil.hasElements(map));
        map.put(IntegerUtil.INT_0, IntegerUtil.INT_0);
        Assert.assertFalse(MapUtil.hasElements(map));
        map.put(IntegerUtil.INT_1, IntegerUtil.INT_1);
        Assert.assertTrue(MapUtil.hasElements(map));
        map.put(IntegerUtil.INT_2, IntegerUtil.INT_2);
        Assert.assertTrue(MapUtil.hasElements(map));
    }

    /**
     * Test the isMap(class) method.
     */
    @Test
    public void isMapClass() {
        Assert.assertTrue(MapUtil.isMap(HashMap.class));
        Assert.assertFalse(MapUtil.isMap(HashSet.class));
    }

    /**
     * Test the isMap(object) method.
     */
    @Test
    public void isMapObject() {
        Assert.assertTrue(MapUtil.isMap(new HashMap<Object, Object>()));
        Assert.assertFalse(MapUtil.isMap(new HashSet<Object>()));
    }

    /**
     * Test the toMap() method.
     */
    @Test
    public void toMap() {
        Assert.assertNotNull(MapUtil.toMap());
        Assert.assertTrue(MapUtil.toMap().isEmpty());
        Assert.assertEquals(MapUtil.toMap().getClass(), HashMap.class);
    }

    /**
     * Test the toMap(map) method.
     */
    @Test
    public void toMapFromMap() {
        // Null map.
        Assert.assertNotNull(MapUtil.toMap(null));

        // Empty map.
        Assert.assertTrue(MapUtil.toMap(null).isEmpty());

        // Not empty map.
        final Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = IntegerUtil.INT_0; i < IntegerUtil.INT_100; i++) {
            map.put(RandomUtil.nextInt(IntegerUtil.INT_100),
                    RandomUtil.nextInt(IntegerUtil.INT_100));
        }

        final Map<Integer, Integer> hash = MapUtil.toMap(map);
        Assert.assertTrue(MapUtil.containsAll(map, hash));
        map.put(IntegerUtil.INT_1000, IntegerUtil.INT_1000);
        Assert.assertFalse(MapUtil.containsAll(map, hash));
    }

    /**
     * Test the toSynchronizedMap() method.
     */
    @Test
    public void toSynchronizedMap() {
        Assert.assertNotNull(MapUtil.toSynchronizedMap());
        Assert.assertTrue(MapUtil.toSynchronizedMap().isEmpty());
        Assert.assertEquals(MapUtil.toSynchronizedMap().getClass(), Hashtable.class);
    }

    /**
     * Test the toSynchronizedMap(map) method.
     */
    @Test
    public void toSynchronizedMapFromMap() {
        // Null map.
        Assert.assertNotNull(MapUtil.toSynchronizedMap(null));

        // Empty map.
        Assert.assertTrue(MapUtil.toSynchronizedMap(null).isEmpty());

        // Not empty map.
        final Map<Integer, Integer> map = new Hashtable<Integer, Integer>();
        for (int i = IntegerUtil.INT_0; i < IntegerUtil.INT_100; i++) {
            map.put(RandomUtil.nextInt(IntegerUtil.INT_100),
                    RandomUtil.nextInt(IntegerUtil.INT_100));
        }

        final Map<Integer, Integer> hash = MapUtil.toSynchronizedMap(map);
        Assert.assertTrue(MapUtil.containsAll(map, hash));
        map.put(IntegerUtil.INT_1000, IntegerUtil.INT_1000);
        Assert.assertFalse(MapUtil.containsAll(map, hash));
    }
}
