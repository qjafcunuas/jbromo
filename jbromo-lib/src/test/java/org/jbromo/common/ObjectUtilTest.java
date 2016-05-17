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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jbromo.common.test.common.ConstructorUtil;
import org.junit.Assert;
import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Test for the object util class.
 * @author qjafcunuas
 */
public class ObjectUtilTest {

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        ConstructorUtil.executePrivate(ObjectUtil.class);
    }

    /**
     * Define a class without constructor with no parameter.
     * @author qjafcunuas
     */
    @AllArgsConstructor
    @Getter
    private static final class NoEmptyConstructor {
        /** an object field. */
        private final String name;

        /** an object field. */
        private final Integer age;
    }

    /**
     * Define an object that is not assignable as Comparable.
     * @author qjafcunuas
     */
    @ToString
    @AllArgsConstructor
    private class NotComparableObject {
        /** A private value. */
        private final String value;
    }

    /**
     * Define class for testing dump method.
     * @author qjafcunuas
     */
    @Getter
    @Setter
    private class DumpObject {
        /** A name. */
        private String name;

        /** A year. */
        private Integer year;

        /** A child. */
        private DumpObject child;
    }

    /**
     * Test the notNullAndEquals method.
     */
    @Test
    public void notNullAndEquals() {
        Assert.assertFalse(ObjectUtil.notNullAndEquals(null, null));
        Assert.assertFalse(ObjectUtil.notNullAndEquals(BigDecimal.TEN, null));
        Assert.assertFalse(ObjectUtil.notNullAndEquals(null, BigDecimal.TEN));
        Assert.assertFalse(ObjectUtil.notNullAndEquals(BigDecimal.ONE, BigDecimal.TEN));
        Assert.assertFalse(ObjectUtil.notNullAndEquals(BigDecimal.TEN, BigDecimal.ONE));
        Assert.assertTrue(ObjectUtil.notNullAndEquals(BigDecimal.ONE, BigDecimal.ONE));
        Assert.assertFalse(ObjectUtil.notNullAndEquals(BigDecimal.ONE, Integer.MIN_VALUE));
    }

    /**
     * Test the equals method.
     */
    @Test
    public void equalsMethod() {
        Assert.assertTrue(ObjectUtil.equals(null, null));
        Assert.assertFalse(ObjectUtil.equals(null, BigDecimal.TEN));
        Assert.assertFalse(ObjectUtil.equals(BigDecimal.TEN, null));
        Assert.assertFalse(ObjectUtil.equals(BigDecimal.ONE, BigDecimal.TEN));
        Assert.assertFalse(ObjectUtil.equals(BigDecimal.TEN, BigDecimal.ONE));
        Assert.assertTrue(ObjectUtil.equals(BigDecimal.ONE, BigDecimal.ONE));
        Assert.assertFalse(ObjectUtil.equals(BigDecimal.ONE, Integer.MIN_VALUE));
    }

    /**
     * Test the buildArrayList method.
     */
    @Test
    public void nullOrNotNull() {
        Assert.assertTrue(ObjectUtil.nullOrNotNull(null, null));
        Assert.assertFalse(ObjectUtil.nullOrNotNull(null, BigDecimal.TEN));
        Assert.assertFalse(ObjectUtil.nullOrNotNull(BigDecimal.TEN, null));
        Assert.assertTrue(ObjectUtil.nullOrNotNull(BigDecimal.ONE, BigDecimal.TEN));
        Assert.assertTrue(ObjectUtil.nullOrNotNull(BigDecimal.TEN, BigDecimal.ONE));
        Assert.assertTrue(ObjectUtil.nullOrNotNull(BigDecimal.ONE, BigDecimal.ONE));
        Assert.assertTrue(ObjectUtil.nullOrNotNull(BigDecimal.ONE, Integer.MIN_VALUE));
    }

    /**
     * Test the newInstance(class) method.
     */
    @Test
    public void newInstance() {
        Assert.assertEquals(String.class, ObjectUtil.newInstance(String.class).getClass());
        Assert.assertEquals(ArrayList.class, ObjectUtil.newInstance(ArrayList.class).getClass());
        Assert.assertNull(ObjectUtil.newInstance(NoEmptyConstructor.class));
    }

    /**
     * Test the newInstance(class,objects) method.
     */
    @Test
    public void newInstanceClassObjects() {
        Assert.assertEquals(NoEmptyConstructor.class,
                            ObjectUtil.newInstance(NoEmptyConstructor.class, "name", Integer.valueOf(IntegerUtil.INT_2)).getClass());
        Assert.assertNull("Should not create an instance for class with bad parameters constructor.",
                          ObjectUtil.newInstance(NoEmptyConstructor.class, "name", "name"));
    }

    /**
     * Test the cast(object,class) method.
     */
    @SuppressWarnings("unused")
    @Test
    public void cast() {
        final Collection<Integer> collection = new ArrayList<Integer>();
        CollectionUtil.addAll(collection, IntegerUtil.INT_0, IntegerUtil.INT_1);

        try {
            final Collection<?> col = ObjectUtil.cast(collection, Collection.class);
            final Integer val = ObjectUtil.cast(IntegerUtil.INT_0, Integer.class);
            final BigDecimal bd = ObjectUtil.cast(BigDecimal.ONE, BigDecimal.class);
            final Number nb = ObjectUtil.cast(BigDecimal.ONE, Number.class);
        } catch (final Exception e) {
            Assert.fail("Cannot cast");
        }
        try {
            final List<?> list = ObjectUtil.cast(BigDecimal.ONE, List.class);
            Assert.fail("Shouldn't cast BigDecimal to list!");
        } catch (final ClassCastException e) {
            Assert.assertTrue(true);
        }
    }

    /**
     * Test the same method.
     */
    @Test
    public void same() {
        Assert.assertFalse(ObjectUtil.same("1", "2"));
        final String s1 = new String("1");
        final String s2 = new String("1");
        Assert.assertTrue(ObjectUtil.same(s1, s1));
        Assert.assertFalse(ObjectUtil.same(s1, s2));
    }

    /**
     * Test the compare(comparable,comparable) method.
     */
    @Test
    public void compareComparable() {
        // Null values
        Assert.assertEquals(ObjectUtil.compare((Integer) null, (Integer) null), 0);
        Assert.assertEquals(ObjectUtil.compare(1, (Integer) null), 1);
        Assert.assertEquals(ObjectUtil.compare((Integer) null, 1), -1);

        // Not null.
        Assert.assertEquals(ObjectUtil.compare(0, 0), 0);
        Assert.assertEquals(ObjectUtil.compare(1, 0), 1);
        Assert.assertEquals(ObjectUtil.compare(0, 1), -1);
    }

    /**
     * Test the compare(object,object) method.
     */
    @Test
    public void compareObject() {
        // Null values
        Assert.assertEquals(ObjectUtil.compare((Object) null, (Object) null), 0);
        Assert.assertEquals(ObjectUtil.compare(1, (Object) null), 1);
        Assert.assertEquals(ObjectUtil.compare((Object) null, 1), -1);

        // Comparable object
        Assert.assertEquals(ObjectUtil.compare((Object) 0, (Object) 0), 0);
        Assert.assertEquals(ObjectUtil.compare((Object) 1, (Object) 0), 1);
        Assert.assertEquals(ObjectUtil.compare((Object) 0, (Object) 1), -1);

        // Not comparable object
        Assert.assertEquals(ObjectUtil.compare(new NotComparableObject("0"), new NotComparableObject("0")), 0);
        Assert.assertEquals(ObjectUtil.compare(new NotComparableObject("1"), new NotComparableObject("0")), 1);
        Assert.assertEquals(ObjectUtil.compare(new NotComparableObject("0"), new NotComparableObject("1")), -1);
    }

    /**
     * Test isPrimitive method.
     */
    @Test
    public void isPrimitive() {
        Assert.assertFalse(ObjectUtil.isPrimitive(null));
        Assert.assertTrue(ObjectUtil.isPrimitive(0));
    }

    /**
     * Test the dump method.
     */
    @Test
    public void dump() {
        Assert.assertNull(ObjectUtil.dump(null));
        final DumpObject object = new DumpObject();
        object.setName(RandomUtil.nextString());
        object.setYear(RandomUtil.nextInt());
        object.setChild(new DumpObject());
        // For testing recursivity.
        object.getChild().setChild(object);
        Assert.assertNotNull(ObjectUtil.dump(object));
    }

}
