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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.jbromo.common.test.common.ConstructorUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for the Locale Language Util class.
 * @author qjafcunuas
 */
public class LocaleCountryUtilTest {

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        ConstructorUtil.executePrivate(LocaleCountryUtil.class);
    }

    /**
     * Test the getIsoCode method.
     */
    @Test
    public void getIsoCode() {
        // Null
        Assert.assertNull(LocaleCountryUtil.getIsoCode(null));
        // Values
        Assert.assertEquals(LocaleCountryUtil.getIsoCode(Locale.CANADA), "CA");
        Assert.assertEquals(LocaleCountryUtil.getIsoCode(Locale.FRANCE), "FR");
    }

    /**
     * Test the same method.
     */
    @Test
    public void same() {
        // Null
        Assert.assertFalse(LocaleCountryUtil.same(null, null));
        Assert.assertFalse(LocaleCountryUtil.same(Locale.FRANCE, null));
        Assert.assertFalse(LocaleCountryUtil.same(null, Locale.FRANCE));
        // Values
        Assert.assertTrue(LocaleCountryUtil.same(Locale.CANADA, Locale.CANADA_FRENCH));
        // FRANCE=fr_FR, FRENCH=fr => false
        Assert.assertFalse(LocaleCountryUtil.same(Locale.FRANCE, Locale.FRENCH));
        Assert.assertFalse(LocaleCountryUtil.same(Locale.FRANCE, Locale.CANADA));
    }

    /**
     * Test the translate method.
     */
    @Test
    public void translate() {
        // Null
        Assert.assertNull(LocaleCountryUtil.translate(null, null));
        Assert.assertNull(LocaleCountryUtil.translate(null, Locale.FRANCE));
        Assert.assertNull(LocaleCountryUtil.translate(Locale.FRANCE, null));
        // Values
        Assert.assertEquals(LocaleCountryUtil.translate(Locale.FRANCE, Locale.FRENCH), "France");
        Assert.assertEquals(LocaleCountryUtil.translate(Locale.CANADA, Locale.FRENCH), "Canada");
        Assert.assertEquals(LocaleCountryUtil.translate(Locale.ITALY, Locale.ENGLISH), "Italy");
    }

    /**
     * Return a locale list.
     * @return the list.
     */
    private List<Locale> getLocales() {
        final List<Locale> locales = new ArrayList<Locale>();
        locales.add(Locale.FRANCE);
        locales.add(Locale.CANADA);
        locales.add(Locale.GERMANY);
        return locales;
    }

    /**
     * Test the sort method.
     */
    @Test
    public void sort() {
        // French translation.
        List<Locale> locales = getLocales();
        LocaleCountryUtil.sort(locales, Locale.FRENCH);
        Assert.assertTrue(locales.indexOf(Locale.GERMANY) == 0);
        Assert.assertTrue(locales.indexOf(Locale.FRANCE) == 2);
        Assert.assertTrue(locales.indexOf(Locale.CANADA) == 1);

        // English translation.
        locales = getLocales();
        LocaleCountryUtil.sort(locales, Locale.ENGLISH);
        Assert.assertTrue(locales.indexOf(Locale.GERMANY) == 2);
        Assert.assertTrue(locales.indexOf(Locale.FRANCE) == 1);
        Assert.assertTrue(locales.indexOf(Locale.CANADA) == 0);
    }

    /**
     * Test the toLocale method.
     */
    @Test
    public void toLocale() {
        Assert.assertTrue(LocaleCountryUtil.same(LocaleCountryUtil.toLocale("FR"), Locale.FRANCE));
        Assert.assertTrue(LocaleCountryUtil.same(LocaleCountryUtil.toLocale("FR"), Locale.FRANCE));
        Assert.assertTrue(LocaleCountryUtil.same(LocaleCountryUtil.toLocale("CA"), Locale.CANADA));
        Assert.assertNull(LocaleCountryUtil.toLocale(null));
        Assert.assertNull(LocaleCountryUtil.toLocale(""));
        // twice for testing hashtable field
        Assert.assertNotNull(LocaleCountryUtil.toLocale("FR"));
        Assert.assertNotNull(LocaleCountryUtil.toLocale("FR"));
        // twice for testing hashtable field
        Assert.assertNull(LocaleCountryUtil.toLocale("ZZZ"));
        Assert.assertNull(LocaleCountryUtil.toLocale("ZZZ"));

    }
}
