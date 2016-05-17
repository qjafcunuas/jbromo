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
package org.jbromo.common.invocation;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;

/**
 * Test for the ParameterizedType util class.
 * @author qjafcunuas
 */
public class ParameterizedTypeUtilTest {

    /**
     * Define a class.
     */
    private abstract class AbstractObject<O> {
        /**
         * Define a string collections.
         */
        @Getter
        @Setter
        private Collection<String> strings;
    }

    /**
     * Define a class.
     */
    private abstract class AbstractObjectObject<O, P> extends AbstractObject<O> {
    }

    /**
     * Define an object.
     */
    private class MyObject extends AbstractObject<Integer> {
    };

    /**
     * Define an object.
     */
    private class MyObjectObject extends AbstractObjectObject<Integer, String> {
    }

    /**
     * Test the getClass method.
     */
    @Test
    public void testGetClass() {
        AbstractObject<?> obj = new MyObject();
        Assert.assertTrue(Integer.class.equals(ParameterizedTypeUtil.getClass(obj, AbstractObject.class, 0)));

        obj = new MyObjectObject();
        Assert.assertTrue(Integer.class.equals(ParameterizedTypeUtil.getClass(obj, AbstractObjectObject.class, 0)));
        Assert.assertTrue(String.class.equals(ParameterizedTypeUtil.getClass(obj, AbstractObjectObject.class, 1)));
    }

    /**
     * Test the getGenericType method.
     */
    @Test
    public void testGetGenericType() {
        final Field field = InvocationUtil.getField(MyObject.class, "strings");
        Assert.assertNotNull(field);
        final List<Class<?>> list = ParameterizedTypeUtil.getGenericType(field);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() == 1);
        Assert.assertTrue(list.contains(String.class));
        Assert.assertTrue(ParameterizedTypeUtil.getGenericType(field, 0).equals(String.class));

    }

}
