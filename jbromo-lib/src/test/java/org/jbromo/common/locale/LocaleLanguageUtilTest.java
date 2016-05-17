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

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.test.common.ConstructorUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for the Locale Language Util class.
 * @author qjafcunuas
 */
public class LocaleLanguageUtilTest {

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        ConstructorUtil.executePrivate(LocaleLanguageUtil.class);
    }

    /**
     * Test the getIsoCode method.
     */
    @Test
    public void getIsoCode() {
        // Null
        Assert.assertNull(LocaleLanguageUtil.getIsoCode(null));
        Assert.assertNull(LocaleLanguageUtil.getIsoCode(new Locale("")));
        // Values
        Assert.assertEquals(LocaleLanguageUtil.getIsoCode(Locale.CANADA_FRENCH), "fr");
        Assert.assertEquals(LocaleLanguageUtil.getIsoCode(Locale.FRENCH), "fr");
    }

    /**
     * Test the getIsoCode method.
     */
    @Test
    public void getFullCode() {
        // Null
        Assert.assertNull(LocaleLanguageUtil.getFullCode(null));
        Assert.assertNull(LocaleLanguageUtil.getFullCode(new Locale("")));
        // Values
        Assert.assertEquals(LocaleLanguageUtil.getFullCode(Locale.CANADA_FRENCH), "fr_CA");
        Assert.assertEquals(LocaleLanguageUtil.getFullCode(Locale.FRENCH), "fr");
    }

    /**
     * Test the translate method.
     */
    @Test
    public void translate() {
        // Null
        Assert.assertNull(LocaleLanguageUtil.translate(null, null, false));
        Assert.assertNull(LocaleLanguageUtil.translate(null, null, true));
        Assert.assertNull(LocaleLanguageUtil.translate(null, Locale.FRENCH, false));
        Assert.assertNull(LocaleLanguageUtil.translate(null, Locale.FRENCH, true));
        Assert.assertNull(LocaleLanguageUtil.translate(Locale.FRENCH, null, false));
        Assert.assertNull(LocaleLanguageUtil.translate(Locale.FRENCH, null, true));
        // Values
        Assert.assertEquals(LocaleLanguageUtil.translate(Locale.CANADA_FRENCH, Locale.FRENCH, false), "français");
        Assert.assertEquals(LocaleLanguageUtil.translate(Locale.CANADA_FRENCH, Locale.FRENCH, true), "français (Canada)");
    }

    /**
     * Return a locale list.
     * @return the list.
     */
    private List<Locale> getLocales() {
        final List<Locale> locales = new ArrayList<Locale>();
        locales.add(Locale.GERMAN);
        locales.add(Locale.FRENCH);
        locales.add(Locale.CANADA_FRENCH);
        locales.add(Locale.ENGLISH);
        return locales;
    }

    /**
     * Test the sort method.
     */
    @Test
    public void sort() {
        // Distinct french translation.
        List<Locale> locales = getLocales();
        LocaleLanguageUtil.sort(locales, Locale.FRENCH, true);
        Assert.assertTrue(locales.indexOf(Locale.GERMAN) == IntegerUtil.INT_0);
        Assert.assertTrue(locales.indexOf(Locale.FRENCH) == IntegerUtil.INT_2);
        Assert.assertTrue(locales.indexOf(Locale.CANADA_FRENCH) == IntegerUtil.INT_3);
        Assert.assertTrue(locales.indexOf(Locale.ENGLISH) == IntegerUtil.INT_1);

        // French translation.
        locales = getLocales();
        LocaleLanguageUtil.sort(locales, Locale.FRENCH, false);
        Assert.assertTrue(locales.indexOf(Locale.GERMAN) == IntegerUtil.INT_0);
        Assert.assertTrue(locales.indexOf(Locale.FRENCH) >= IntegerUtil.INT_2);
        Assert.assertTrue(locales.indexOf(Locale.CANADA_FRENCH) >= IntegerUtil.INT_2);
        Assert.assertTrue(locales.indexOf(Locale.ENGLISH) == IntegerUtil.INT_1);

        // Distinct english translation.
        locales = getLocales();
        LocaleLanguageUtil.sort(locales, Locale.ENGLISH, true);
        Assert.assertTrue(locales.indexOf(Locale.GERMAN) == IntegerUtil.INT_3);
        Assert.assertTrue(locales.indexOf(Locale.FRENCH) == IntegerUtil.INT_1);
        Assert.assertTrue(locales.indexOf(Locale.CANADA_FRENCH) == IntegerUtil.INT_2);
        Assert.assertTrue(locales.indexOf(Locale.ENGLISH) == IntegerUtil.INT_0);

        // English translation.
        locales = getLocales();
        LocaleLanguageUtil.sort(locales, Locale.ENGLISH, false);
        Assert.assertTrue(locales.indexOf(Locale.GERMAN) == IntegerUtil.INT_3);
        Assert.assertTrue(locales.indexOf(Locale.FRENCH) == IntegerUtil.INT_1 || locales.indexOf(Locale.FRENCH) == IntegerUtil.INT_2);
        Assert.assertTrue(locales.indexOf(Locale.CANADA_FRENCH) == IntegerUtil.INT_1 || locales.indexOf(Locale.CANADA_FRENCH) == IntegerUtil.INT_2);
        Assert.assertTrue(locales.indexOf(Locale.ENGLISH) == IntegerUtil.INT_0);

    }

    /**
     * Test the toLocale method.
     */
    @Test
    public void toLocale() {
        // Null code.
        Assert.assertNull(LocaleLanguageUtil.toLocale(null));
        // Empty code.
        Assert.assertNull(LocaleLanguageUtil.toLocale(""));

        // Not empty code.
        Assert.assertEquals(LocaleLanguageUtil.toLocale("fr"), Locale.FRENCH);
        Assert.assertEquals(LocaleLanguageUtil.toLocale("fr_CA"), Locale.CANADA_FRENCH);
    }

    /**
     * Test the toLocale(lang,country) method.
     */
    @Test
    public void toLocaleLangCountry() {
        // null/empty, null/empty.
        Assert.assertNull(LocaleLanguageUtil.toLocale(null, null));
        Assert.assertNull(LocaleLanguageUtil.toLocale("", null));
        Assert.assertNull(LocaleLanguageUtil.toLocale(null, ""));
        Assert.assertNull(LocaleLanguageUtil.toLocale("", ""));
        // null/empty, not null
        Assert.assertNull(LocaleLanguageUtil.toLocale(null, "FR"));
        Assert.assertNull(LocaleLanguageUtil.toLocale("", "FR"));
        // not empty, null/empty
        Assert.assertEquals(LocaleLanguageUtil.toLocale("fr", null), new Locale("fr"));
        Assert.assertEquals(LocaleLanguageUtil.toLocale("fr", ""), new Locale("fr"));

        // Not empty code.
        Assert.assertEquals(LocaleLanguageUtil.toLocale("fr"), Locale.FRENCH);
        Assert.assertEquals(LocaleLanguageUtil.toLocale("fr", "CA"), Locale.CANADA_FRENCH);
    }

}
