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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jbromo.common.test.common.ConstructorUtil;
import org.jbromo.common.test.logger.MockLogger;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for the Collection Util class.
 * @author qjafcunuas
 */
public class CollectionUtilTest {

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        ConstructorUtil.executePrivate(CollectionUtil.class);
    }

    /**
     * Test the containsAll method with sameSize parameter to false.
     */
    @Test
    public void containsAll() {
        // null, null, false
        Assert.assertFalse(CollectionUtil.containsAll((Collection<Boolean>) null, (Collection<Boolean>) null, false));
        // not null, null, false
        Assert.assertFalse(CollectionUtil.containsAll(BooleanUtil.ALL, (Collection<Boolean>) null, false));
        // null, not null, false
        Assert.assertFalse(CollectionUtil.containsAll((Collection<Boolean>) null, BooleanUtil.ALL, false));

        // same
        Assert.assertTrue(CollectionUtil.containsAll(BooleanUtil.ALL, BooleanUtil.ALL, false));

        // empty
        Assert.assertTrue(CollectionUtil.containsAll(ListUtil.toList(), ListUtil.toList(), false));

        // Not null.
        final List<Integer> integers = new ArrayList<>();
        final List<String> strings = new ArrayList<>();
        for (int i = IntegerUtil.INT_0; i < IntegerUtil.INT_100; i++) {
            integers.add(i);
            strings.add("string " + i);
        }
        final Set<Integer> set = new HashSet<>();
        set.addAll(integers);
        final Set<Integer> anotherset = new HashSet<>();
        anotherset.addAll(integers);

        Assert.assertTrue(CollectionUtil.containsAll(strings, strings, false));
        Assert.assertTrue(CollectionUtil.containsAll(integers, integers, false));
        Assert.assertTrue(CollectionUtil.containsAll(integers, set, false));
        Assert.assertTrue(CollectionUtil.containsAll(set, integers, false));

        // Compare set.
        Assert.assertTrue(CollectionUtil.containsAll(anotherset, set, false));
        Assert.assertTrue(CollectionUtil.containsAll(set, anotherset, false));
        anotherset.remove(IntegerUtil.INT_0);
        Assert.assertFalse(CollectionUtil.containsAll(anotherset, set, false));
        Assert.assertFalse(CollectionUtil.containsAll(set, anotherset, false));
        anotherset.add(IntegerUtil.INT_1000);
        Assert.assertFalse(CollectionUtil.containsAll(anotherset, set, false));
        Assert.assertFalse(CollectionUtil.containsAll(set, anotherset, false));

        // List contains 0 twice.
        integers.add(IntegerUtil.INT_0);
        Assert.assertTrue(CollectionUtil.containsAll(integers, set, false));
        Assert.assertTrue(CollectionUtil.containsAll(set, integers, false));

        set.add(IntegerUtil.INT_1000);
        Assert.assertFalse(CollectionUtil.containsAll(integers, set, false));
        Assert.assertFalse(CollectionUtil.containsAll(set, integers, false));
        set.remove(Integer.valueOf(IntegerUtil.INT_1000));
        set.remove(Integer.valueOf(IntegerUtil.INT_1));
        Assert.assertFalse(CollectionUtil.containsAll(integers, set, false));
        Assert.assertFalse(CollectionUtil.containsAll(set, integers, false));
    }

    /**
     * Test the containsAll method with sameSize parameter to true.
     */
    @Test
    public void containsAllSameSize() {
        // null, null, true
        Assert.assertFalse(CollectionUtil.containsAll((Collection<Boolean>) null, (Collection<Boolean>) null, true));
        // not null, null, true
        Assert.assertFalse(CollectionUtil.containsAll(BooleanUtil.ALL, (Collection<Boolean>) null, true));
        // null, not null, true
        Assert.assertFalse(CollectionUtil.containsAll((Collection<Boolean>) null, BooleanUtil.ALL, true));

        // same
        Assert.assertTrue(CollectionUtil.containsAll(BooleanUtil.ALL, BooleanUtil.ALL, true));

        // empty
        Assert.assertTrue(CollectionUtil.containsAll(ListUtil.toList(), ListUtil.toList(), true));

        // Not null.

        // Build list integer 0 to 99.
        // Build list string 'string0' to 'string99'.
        // Build 2 set integer 0 to 99.
        final List<Integer> integers = new ArrayList<>();
        final List<String> strings = new ArrayList<>();
        for (int i = IntegerUtil.INT_0; i < IntegerUtil.INT_100; i++) {
            integers.add(i);
            strings.add("string " + i);
        }
        final Set<Integer> set = new HashSet<>();
        set.addAll(integers);
        final Set<Integer> anotherset = new HashSet<>();
        anotherset.addAll(integers);

        // Compare set : same size, same elements.
        Assert.assertTrue(CollectionUtil.containsAll(anotherset, set, true));
        Assert.assertTrue(CollectionUtil.containsAll(set, anotherset, true));
        // Compare set : not same size.
        anotherset.remove(IntegerUtil.INT_0);
        Assert.assertFalse(CollectionUtil.containsAll(anotherset, set, true));
        Assert.assertFalse(CollectionUtil.containsAll(set, anotherset, true));
        // Compare set : same size, not same elements.
        anotherset.add(IntegerUtil.INT_1000);
        Assert.assertFalse(CollectionUtil.containsAll(anotherset, set, true));
        Assert.assertFalse(CollectionUtil.containsAll(set, anotherset, true));

        // Compare same collection.
        Assert.assertTrue(CollectionUtil.containsAll(strings, strings, true));
        Assert.assertTrue(CollectionUtil.containsAll(integers, integers, true));
        // Compare list with set.
        Assert.assertTrue(CollectionUtil.containsAll(integers, set, true));
        Assert.assertTrue(CollectionUtil.containsAll(set, integers, true));

        // List contains 0 twice.
        final List<Integer> anotherIntegers = new ArrayList<>(integers);
        // Not same size.
        anotherIntegers.add(IntegerUtil.INT_0);
        Assert.assertFalse(CollectionUtil.containsAll(integers, anotherIntegers, true));
        Assert.assertFalse(CollectionUtil.containsAll(anotherIntegers, integers, true));
        // Same size.
        anotherIntegers.remove(IntegerUtil.INT_1);
        Assert.assertFalse(CollectionUtil.containsAll(integers, anotherIntegers, true));
        Assert.assertFalse(CollectionUtil.containsAll(anotherIntegers, integers, true));

        set.add(IntegerUtil.INT_1000);
        Assert.assertFalse(CollectionUtil.containsAll(integers, set, true));
        Assert.assertFalse(CollectionUtil.containsAll(set, integers, true));
        set.remove(Integer.valueOf(IntegerUtil.INT_1000));
        set.remove(Integer.valueOf(IntegerUtil.INT_1));
        Assert.assertFalse(CollectionUtil.containsAll(integers, set, true));
        Assert.assertFalse(CollectionUtil.containsAll(set, integers, true));
    }

    /**
     * Test the containsAll method with sameSize parameter to true.
     */
    @Test
    public void containsAllArray() {
        Boolean[] array = null;
        // null, null, true
        Assert.assertFalse(CollectionUtil.containsAll((Collection<Boolean>) null, array, true));
        // not null, null, true
        Assert.assertFalse(CollectionUtil.containsAll(BooleanUtil.ALL, array, true));
        // null, not null, true
        array = new Boolean[] {Boolean.TRUE};
        Assert.assertFalse(CollectionUtil.containsAll((Collection<Boolean>) null, array, true));
        // not null, not null, true
        array = new Boolean[] {Boolean.TRUE};
        Assert.assertFalse(CollectionUtil.containsAll(BooleanUtil.ALL, array, true));
        array = new Boolean[] {Boolean.TRUE, Boolean.FALSE};
        Assert.assertTrue(CollectionUtil.containsAll(BooleanUtil.ALL, array, false));
        Assert.assertTrue(CollectionUtil.containsAll(BooleanUtil.ALL, array, true));
        array = new Boolean[] {Boolean.TRUE, Boolean.FALSE, Boolean.FALSE};
        Assert.assertTrue(CollectionUtil.containsAll(BooleanUtil.ALL, array, false));
        Assert.assertFalse(CollectionUtil.containsAll(BooleanUtil.ALL, array, true));
    }

    /**
     * Test the isCollection method.
     */
    @Test
    public void isCollectionObject() {
        Assert.assertFalse(CollectionUtil.isCollection((Object) null));
        Assert.assertTrue(CollectionUtil.isCollection(new ArrayList<>()));
        Assert.assertTrue(CollectionUtil.isCollection(new HashSet<>()));
        Assert.assertFalse(CollectionUtil.isCollection(new HashMap<>()));
    }

    /**
     * Test the isCollection method.
     */
    @Test
    public void isCollectionClass() {
        Assert.assertFalse(CollectionUtil.isCollection((Class<?>) null));
        Assert.assertTrue(CollectionUtil.isCollection(List.class));
        Assert.assertTrue(CollectionUtil.isCollection(Set.class));
        Assert.assertFalse(CollectionUtil.isCollection(Integer.class));
    }

    /**
     * Test the isEmpty method.
     */
    @Test
    public void isEmpty() {
        List<Integer> list = null;
        Assert.assertTrue(CollectionUtil.isEmpty(list));
        list = new ArrayList<>();
        Assert.assertTrue(CollectionUtil.isEmpty(list));
        list.add(IntegerUtil.INT_0);
        Assert.assertFalse(CollectionUtil.isEmpty(list));
    }

    /**
     * Test the isNotEmpty method.
     */
    @Test
    public void isNotEmpty() {
        List<Integer> list = null;
        Assert.assertFalse(CollectionUtil.isNotEmpty(list));
        list = new ArrayList<>();
        Assert.assertFalse(CollectionUtil.isNotEmpty(list));
        list.add(IntegerUtil.INT_0);
        Assert.assertTrue(CollectionUtil.isNotEmpty(list));
    }

    /**
     * Test the toCollection method.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void toCollection() {
        final List<Integer> list = new ArrayList<>();
        for (int i = IntegerUtil.INT_0; i < IntegerUtil.INT_100; i++) {
            list.add(i);
        }
        // to Set
        final Set<Integer> set = CollectionUtil.toCollection(list, HashSet.class);
        Assert.assertTrue(CollectionUtil.containsAll(list, set, false));
        // to List
        final List<Integer> anotherList = CollectionUtil.toCollection(list, ArrayList.class);
        Assert.assertEquals(list, anotherList);
        // Class not instantiable.
        final MockLogger logger = MockLogger.mock(ObjectUtil.class);
        logger.setEnabledSlf4jLog(false);
        try {
            Assert.assertNull(CollectionUtil.toCollection(list, List.class));
            Assert.assertEquals(1, logger.getLogged().size());
        } finally {
            MockLogger.unmock(ObjectUtil.class);
        }
    }

    /**
     * Test the addAll method.
     */
    @Test
    public void addAll() {
        CollectionUtil.addAll(null, IntegerUtil.INT_0, IntegerUtil.INT_1);
        final List<Integer> list = new ArrayList<>();
        CollectionUtil.addAll(list, IntegerUtil.INT_0, IntegerUtil.INT_1);
        Assert.assertTrue(list.contains(IntegerUtil.INT_0));
        Assert.assertTrue(list.contains(IntegerUtil.INT_1));
    }

    /**
     * Test the indexOf method.
     */
    @Test
    public void indexOf() {
        final List<Integer> list = new ArrayList<>();
        for (int i = IntegerUtil.INT_0; i < IntegerUtil.INT_100; i++) {
            list.add(i);
        }
        Assert.assertTrue(CollectionUtil.indexOf(list, IntegerUtil.INT_0) == list.indexOf(IntegerUtil.INT_0));
        Assert.assertTrue(CollectionUtil.indexOf(list, IntegerUtil.INT_10) == list.indexOf(IntegerUtil.INT_10));
        Assert.assertTrue(CollectionUtil.indexOf(list, list.size() - 1) == list.indexOf(list.size() - 1));
        Assert.assertTrue(CollectionUtil.indexOf(list, IntegerUtil.INT_1024) == -1);
    }

    /**
     * Test the contains method.
     */
    @Test
    public void contains() {
        final List<Object> list = new ArrayList<>();
        Assert.assertFalse(CollectionUtil.contains(null, null));
        Assert.assertFalse(CollectionUtil.contains(null, Integer.class));
        Assert.assertFalse(CollectionUtil.contains(list, null));

        CollectionUtil.addAll(list, IntegerUtil.INT_0, StringUtil.SPACE);
        Assert.assertTrue(CollectionUtil.contains(list, Integer.class));
        Assert.assertTrue(CollectionUtil.contains(list, String.class));
        Assert.assertFalse(CollectionUtil.contains(list, Long.class));
    }

    /**
     * Test the toString(Collection, separator) method.
     */
    @Test
    public void toStringCollection() {
        final List<Integer> list = ListUtil.toList(1, 2);
        Assert.assertEquals(CollectionUtil.toString(list, StringUtil.SPACE), "1 2");
        Assert.assertEquals(CollectionUtil.toString(list, StringUtil.COMMA), "1,2");
    }

    /**
     * Test the identical method.
     */
    @Test
    public void identical() {
        // null, null
        Assert.assertFalse(CollectionUtil.identical((Collection<Boolean>) null, (Collection<Boolean>) null));
        // not null, null
        Assert.assertFalse(CollectionUtil.identical(BooleanUtil.ALL, (Collection<Boolean>) null));
        // null, not null
        Assert.assertFalse(CollectionUtil.identical((Collection<Boolean>) null, BooleanUtil.ALL));

        // Empty
        Assert.assertFalse(CollectionUtil.identical(BooleanUtil.EMPTY, BooleanUtil.ALL));
        Assert.assertFalse(CollectionUtil.identical(BooleanUtil.ALL, BooleanUtil.EMPTY));

        // Same collection and not empty.
        Assert.assertTrue(CollectionUtil.identical(BooleanUtil.ALL, BooleanUtil.ALL));

        // Not same size
        List<Integer> one = ListUtil.toList(RandomUtil.nextInt());
        List<Integer> two = ListUtil.toList();
        Assert.assertFalse(CollectionUtil.identical(one, two));

        // Same size, not same elements
        one = ListUtil.toList(0, 1);
        two = ListUtil.toList(0, 2);
        Assert.assertFalse(CollectionUtil.identical(one, two));

        // Same size, same elements, not same order
        one = ListUtil.toList(0, 1);
        two = ListUtil.toList(1, 0);
        Assert.assertFalse(CollectionUtil.identical(one, two));

        // Same size, same elements, same order
        one = ListUtil.toList(0, 1);
        two = ListUtil.toList(0, 1);
        Assert.assertTrue(CollectionUtil.identical(one, two));

        // List with null element
        one = ListUtil.toList(0, 1, null);
        two = ListUtil.toList(0, 1, null);
        Assert.assertTrue(CollectionUtil.identical(one, two));
    }

    /**
     * Test the get method.
     */
    @Test
    public void get() {
        // null object/collection.
        Assert.assertNull(CollectionUtil.get(null, null));
        Assert.assertNull(CollectionUtil.get(null, new Object()));
        Assert.assertNull(CollectionUtil.get(SetUtil.toSet(), null));

        final Object o = new Object() {
            @Override
            public int hashCode() {
                return -IntegerUtil.INT_13;
            }

            @Override
            public boolean equals(final Object o) {
                return o.hashCode() == -IntegerUtil.INT_13;
            }
        };

        final Collection<Object> col = SetUtil.toSet();
        col.add(o);
        Assert.assertNull(CollectionUtil.get(col, new Object()));
        Assert.assertEquals(o, CollectionUtil.get(col, o));

        // Distinct object from o, but with same hashCode and equals method.
        final Object o2 = new Object() {
            @Override
            public int hashCode() {
                return -IntegerUtil.INT_13;
            }

            @Override
            public boolean equals(final Object o) {
                return o.hashCode() == -IntegerUtil.INT_13;
            }
        };
        Assert.assertSame(o, CollectionUtil.get(col, o2));
        Assert.assertNotSame(o2, CollectionUtil.get(col, o2));

    }

}
