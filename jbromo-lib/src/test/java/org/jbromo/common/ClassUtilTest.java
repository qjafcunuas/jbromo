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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.jbromo.common.test.common.ConstructorUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Define JUnit for ClassUtil class.
 * @author qjafcunuas
 */
public class ClassUtilTest {

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        ConstructorUtil.executePrivate(ClassUtil.class);
    }

    /**
     * Test getClass(Object) method.
     */
    @Test
    public void getClassTest() {
        Assert.assertNull(ClassUtil.getClass((Object) null));
        Assert.assertEquals(ClassUtil.getClass(new ArrayList<Object>()), ArrayList.class);
        Assert.assertEquals(ClassUtil.getClass(new HashMap<Object, Object>()), HashMap.class);
        Assert.assertEquals(ClassUtil.getClass(new Object()), Object.class);
        Assert.assertFalse(ClassUtil.getClass(Integer.SIZE).equals(Object.class));
    }

    /**
     * Test getClass(Object[]) method.
     */
    @Test
    public void getClassesTest() {
        Assert.assertNull(ClassUtil.getClass((Object[]) null));
        final Object[] objects = new Object[] {ListUtil.toList(), BigDecimal.ZERO};
        Assert.assertTrue(ClassUtil.getClass(objects).length == 2);
        Assert.assertEquals(ClassUtil.getClass(objects)[0], ArrayList.class);
        Assert.assertEquals(ClassUtil.getClass(objects)[1], BigDecimal.class);
    }

    /**
     * Test isInstance method.
     */
    @Test
    public void isInstance() {
        Assert.assertFalse(ClassUtil.isInstance(null, null));
        Assert.assertFalse(ClassUtil.isInstance(new Object(), null));
        Assert.assertFalse(ClassUtil.isInstance(null, Object.class));

        Assert.assertTrue(ClassUtil.isInstance(new ArrayList<Object>(), Object.class));
        Assert.assertTrue(ClassUtil.isInstance(new ArrayList<Object>(), ArrayList.class));
        Assert.assertTrue(ClassUtil.isInstance(new ArrayList<Object>(), List.class));
        Assert.assertTrue(ClassUtil.isInstance(new ArrayList<Object>(), Iterable.class));

        Assert.assertFalse(ClassUtil.isInstance(new ArrayList<Object>(), HashMap.class));
    }

    /**
     * Test isAssignableFrom method.
     */
    @Test
    public void isAssignableFrom() {
        Assert.assertFalse(ClassUtil.isAssignableFrom(null, null));
        Assert.assertFalse(ClassUtil.isAssignableFrom(List.class, null));
        Assert.assertFalse(ClassUtil.isAssignableFrom(null, List.class));

        Assert.assertTrue(ClassUtil.isAssignableFrom(ArrayList.class, Object.class));
        Assert.assertTrue(ClassUtil.isAssignableFrom(ArrayList.class, ArrayList.class));
        Assert.assertTrue(ClassUtil.isAssignableFrom(ArrayList.class, List.class));
        Assert.assertTrue(ClassUtil.isAssignableFrom(ArrayList.class, Iterable.class));

        Assert.assertFalse(ClassUtil.isAssignableFrom(ArrayList.class, HashMap.class));
    }

    /**
     * Test isPrimitive method.
     */
    @Test
    public void isPrimitive() {
        Assert.assertFalse(ClassUtil.isPrimitive(null));
        Assert.assertTrue(ClassUtil.isPrimitive(Boolean.class));
        Assert.assertTrue(ClassUtil.isPrimitive(boolean.class));
        Assert.assertTrue(ClassUtil.isPrimitive(Short.class));
        Assert.assertTrue(ClassUtil.isPrimitive(short.class));
        Assert.assertTrue(ClassUtil.isPrimitive(Integer.class));
        Assert.assertTrue(ClassUtil.isPrimitive(int.class));
        Assert.assertTrue(ClassUtil.isPrimitive(Long.class));
        Assert.assertTrue(ClassUtil.isPrimitive(long.class));
        Assert.assertFalse(ClassUtil.isPrimitive(Enum.class));
        Assert.assertFalse(ClassUtil.isPrimitive(BigDecimal.class));
        Assert.assertFalse(ClassUtil.isPrimitive(BigInteger.class));
        Assert.assertTrue(ClassUtil.isPrimitive(Double.class));
        Assert.assertTrue(ClassUtil.isPrimitive(double.class));
        Assert.assertTrue(ClassUtil.isPrimitive(Float.class));
        Assert.assertTrue(ClassUtil.isPrimitive(float.class));
        Assert.assertFalse(ClassUtil.isPrimitive(Calendar.class));
        Assert.assertFalse(ClassUtil.isPrimitive(String.class));
        Assert.assertTrue(ClassUtil.isPrimitive(Byte.class));
        Assert.assertTrue(ClassUtil.isPrimitive(byte.class));
    }

    /**
     * Test isBoolean method.
     */
    @Test
    public void isBoolean() {
        Assert.assertFalse(ClassUtil.isBoolean(null));
        Assert.assertFalse(ClassUtil.isBoolean(Integer.class));
        Assert.assertFalse(ClassUtil.isBoolean(Object.class));
        Assert.assertTrue(ClassUtil.isBoolean(boolean.class));
        Assert.assertTrue(ClassUtil.isBoolean(Boolean.class));
    }

    /**
     * Test isDouble method.
     */
    @Test
    public void isDouble() {
        Assert.assertFalse(ClassUtil.isDouble(null));
        Assert.assertFalse(ClassUtil.isDouble(Integer.class));
        Assert.assertFalse(ClassUtil.isDouble(Object.class));
        Assert.assertTrue(ClassUtil.isDouble(double.class));
        Assert.assertTrue(ClassUtil.isDouble(Double.class));
    }

    /**
     * Test isFloat method.
     */
    @Test
    public void isFloat() {
        Assert.assertFalse(ClassUtil.isFloat(null));
        Assert.assertFalse(ClassUtil.isFloat(Integer.class));
        Assert.assertFalse(ClassUtil.isFloat(Object.class));
        Assert.assertTrue(ClassUtil.isFloat(float.class));
        Assert.assertTrue(ClassUtil.isFloat(Float.class));
    }

    /**
     * Test isInt method.
     */
    @Test
    public void isInt() {
        Assert.assertFalse(ClassUtil.isInt(null));
        Assert.assertFalse(ClassUtil.isInt(Double.class));
        Assert.assertFalse(ClassUtil.isInt(Object.class));
        Assert.assertTrue(ClassUtil.isInt(int.class));
        Assert.assertTrue(ClassUtil.isInt(Integer.class));
    }

    /**
     * Test isLong method.
     */
    @Test
    public void isLong() {
        Assert.assertFalse(ClassUtil.isLong(null));
        Assert.assertFalse(ClassUtil.isLong(Double.class));
        Assert.assertFalse(ClassUtil.isLong(Object.class));
        Assert.assertTrue(ClassUtil.isLong(long.class));
        Assert.assertTrue(ClassUtil.isLong(Long.class));
    }

    /**
     * Test isShort method.
     */
    @Test
    public void isShort() {
        Assert.assertFalse(ClassUtil.isShort(null));
        Assert.assertFalse(ClassUtil.isShort(Double.class));
        Assert.assertFalse(ClassUtil.isShort(Object.class));
        Assert.assertTrue(ClassUtil.isShort(short.class));
        Assert.assertTrue(ClassUtil.isShort(Short.class));
    }

}
