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
package org.jbromo.common.locale;

import java.util.List;
import java.util.Locale;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.ListUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Define JUnit CountryLanguages class.
 * @author qjafcunuas
 */
public class CountryLanguagesTest {

    /**
     * Return a new instance for Canada.
     * @return the new instance.
     */
    private CountryLanguages newCanada() {
        // Chinese must be removed by constructor.
        final List<Locale> languages = ListUtil.toList(Locale.CANADA_FRENCH, Locale.CANADA, Locale.CHINESE);
        return new CountryLanguages(Locale.CANADA, languages);
    }

    /**
     * Return a new instance for France.
     * @return the new instance.
     */
    private CountryLanguages newFrance() {
        // Chinese must be removed by constructor.
        final List<Locale> languages = ListUtil.toList(Locale.FRENCH, Locale.CHINESE);
        return new CountryLanguages(Locale.FRANCE, languages);
    }

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        // null values.
        CountryLanguages cl = new CountryLanguages(null, null);
        Assert.assertNull(cl.getCountry());
        Assert.assertTrue(cl.getLanguages().isEmpty());

        cl = newCanada();
        Assert.assertNotNull(cl);
        Assert.assertEquals(cl.getCountry(), Locale.CANADA);
        Assert.assertNotNull(cl.getLanguages());
        Assert.assertEquals(cl.getLanguages().size(), 2);
        Assert.assertTrue(cl.getLanguages().contains(Locale.CANADA));
        Assert.assertTrue(cl.getLanguages().contains(Locale.CANADA_FRENCH));
    }

    /**
     * Test toString method.
     */
    @Test
    public void toStringMethod() {
        CountryLanguages cl = newCanada();
        Assert.assertEquals(cl.toString(), "CountryLanguages(country=en_CA)");
        cl = new CountryLanguages(null, null);
        Assert.assertEquals(cl.toString(), "CountryLanguages(country=null)");
    }

    /**
     * Test equals method.
     */
    @Test
    public void equalsMethod() {
        final CountryLanguages canada1 = newCanada();
        // null
        Assert.assertTrue(new CountryLanguages(null, null).equals(new CountryLanguages(null, null)));
        Assert.assertFalse(new CountryLanguages(Locale.CANADA, null).equals(new CountryLanguages(null, null)));
        Assert.assertFalse(new CountryLanguages(null, null).equals(new CountryLanguages(Locale.CANADA, null)));
        Assert.assertTrue(new CountryLanguages(Locale.CANADA, null).equals(new CountryLanguages(Locale.CANADA, null)));

        // Same object.
        Assert.assertEquals(canada1, canada1);

        // Not same class.
        Assert.assertNotEquals(canada1, IntegerUtil.INT_0);

        // Same countries.
        final CountryLanguages canada2 = new CountryLanguages(Locale.CANADA, null);
        Assert.assertEquals(canada1, canada2);

        // Distinct countries.
        final CountryLanguages france = newFrance();
        Assert.assertFalse(canada1.equals(france));
    }

    /**
     * Test hashCode method.
     */
    @Test
    public void hashCodeMethod() {
        // null
        Assert.assertTrue(new CountryLanguages(null, null).hashCode() == new CountryLanguages(null, null).hashCode());
        Assert.assertFalse(new CountryLanguages(Locale.CANADA, null).hashCode() == new CountryLanguages(null, null).hashCode());
        Assert.assertFalse(new CountryLanguages(null, null).hashCode() == new CountryLanguages(Locale.CANADA, null).hashCode());

        final CountryLanguages canada1 = newCanada();
        final CountryLanguages canada2 = new CountryLanguages(Locale.CANADA, null);
        final CountryLanguages france = newFrance();
        Assert.assertEquals(canada1.hashCode(), canada1.hashCode());
        Assert.assertEquals(canada1.hashCode(), canada2.hashCode());
        Assert.assertFalse(canada1.hashCode() == france.hashCode());
    }

}
