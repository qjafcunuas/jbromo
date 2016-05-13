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

import java.util.Collection;

import org.jbromo.common.test.common.ConstructorUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for the Collection Util class.
 * @author qjafcunuas
 */
public class StringUtilTest {

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        ConstructorUtil.executePrivate(StringUtil.class);
    }

    /**
     * Test constant.
     */
    public void constant() {
        Assert.assertEquals(StringUtil.DOT, ".");
        Assert.assertEquals(StringUtil.EMPTY, "");
        Assert.assertEquals(StringUtil.EQUALS, "=");
        Assert.assertEquals(StringUtil.FALSE, "0");
        Assert.assertEquals(StringUtil.INFERIOR, "<");
        Assert.assertEquals(StringUtil.INFERIOR_OR_EQUALS, "<=");
        Assert.assertEquals(StringUtil.PARENTHESIS_CLOSE, ")");
        Assert.assertEquals(StringUtil.PARENTHESIS_OPEN, "(");
        Assert.assertEquals(StringUtil.PERCENT, "%");
        Assert.assertEquals(StringUtil.POINT, ",");
        Assert.assertEquals(StringUtil.PASSWORD_SIZE, IntegerUtil.INT_40);
        Assert.assertEquals(StringUtil.QUESTION_MARK, "?");
        Assert.assertEquals(StringUtil.SLASH, "/");
        Assert.assertEquals(StringUtil.SPACE, " ");
        Assert.assertEquals(StringUtil.STAR, "*");
        Assert.assertEquals(StringUtil.SUPERIOR, ">");
        Assert.assertEquals(StringUtil.SUPERIOR_OR_EQUALS, ">=");
        Assert.assertEquals(StringUtil.TRUE, "1");
    }

    /**
     * Test the capitalize method.
     */
    @Test
    public void capitalize() {
        Assert.assertTrue("Toto".equals(StringUtil.capitalize("toto")));
        Assert.assertFalse("toto".equals(StringUtil.capitalize("toto")));
    }

    /**
     * Test the isEmpty method.
     */
    @Test
    public void isEmpty() {
        String value = null;
        Assert.assertTrue(StringUtil.isEmpty(value));
        value = "";
        Assert.assertTrue(StringUtil.isEmpty(value));
        value = "toto";
        Assert.assertFalse(StringUtil.isEmpty(value));
    }

    /**
     * Test the isNotEmpty method.
     */
    @Test
    public void isNotEmpty() {
        String value = null;
        Assert.assertFalse(StringUtil.isNotEmpty(value));
        value = "";
        Assert.assertFalse(StringUtil.isNotEmpty(value));
        value = "toto";
        Assert.assertTrue(StringUtil.isNotEmpty(value));
    }

    /**
     * Test the concat method.
     */
    @Test
    public void concat() {
        Assert.assertNull(StringUtil.concat());
        Assert.assertNull(StringUtil.concat((String[]) null));
        Assert.assertNull(StringUtil.concat(null, null));
        Assert.assertTrue("aa".equals(StringUtil.concat("aa")));
        Assert.assertTrue("bb".equals(StringUtil.concat("bb", null)));
        Assert.assertTrue("cc".equals(StringUtil.concat(null, "cc")));
        Assert.assertTrue("ddee".equals(StringUtil.concat("dd", "ee")));
    }

    /**
     * Test the toString method for array.
     */
    @Test
    public void toStringArray() {
        final String[] strings = new String[] {"a", "b", "c"};
        Assert.assertTrue(StringUtil.toString(strings, ";").equals("a;b;c"));

        final Integer[] integers = new Integer[] {1, 2, 3};
        Assert.assertTrue(StringUtil.toString(integers, "AA").equals("1AA2AA3"));
    }

    /**
     * Test the toString method for collection.
     */
    @Test
    public void toStringCollection() {
        final Collection<String> strings = ListUtil.toList("a", "b", "c");
        Assert.assertTrue(StringUtil.toString(strings, ";").equals("a;b;c"));

        final Collection<Integer> integers = ListUtil.toList(1, 2, 3);
        Assert.assertTrue(StringUtil.toString(integers, "AA").equals("1AA2AA3"));
    }

    /**
     * Test the capitalize with separator method for collection.
     */
    @Test
    public void capitalizeSeparator() {
        Assert.assertNull(StringUtil.capitalize(null, ' '));
        Assert.assertEquals(StringUtil.EMPTY, StringUtil.capitalize(StringUtil.EMPTY, ' '));
        Assert.assertEquals(StringUtil.SPACE, StringUtil.capitalize(StringUtil.SPACE, ' '));
        Assert.assertEquals("David Vincent", StringUtil.capitalize("david vincent", ' '));
        Assert.assertEquals("Jean-david Vincent", StringUtil.capitalize("jean-david vincent", ' '));
        Assert.assertEquals("Jean-David vincent", StringUtil.capitalize("jean-david vincent", '-'));
        Assert.assertEquals("Jean-David Vincent", StringUtil.capitalize("jean-david vincent", ' ', '-'));
    }

    /**
     * Test encrypt method.
     */
    @Test
    public void encrypt() {
        Assert.assertNull(StringUtil.encrypt(null));
        Assert.assertEquals(StringUtil.encrypt("password"), "5BAA61E4C9B93F3F0682250B6CF8331B7EE68FD8");
    }

}
