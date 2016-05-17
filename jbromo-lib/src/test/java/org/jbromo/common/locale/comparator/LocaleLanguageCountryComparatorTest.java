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
package org.jbromo.common.locale.comparator;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

/**
 * Define the JUnit test class.
 * @author qjafcunuas
 */
public class LocaleLanguageCountryComparatorTest {

    /**
     * Return the new comparator.
     * @param locale the locale comparator for translation.
     * @return the comparator.
     */
    private ILocaleComparator newComparator(final Locale locale) {
        return LocaleComparatorFactory.getInstance().getLanguageComparator(locale, true);
    }

    /**
     * Compare null value.
     */
    @Test
    public void compareNull() {
        final ILocaleComparator comparator = newComparator(Locale.FRENCH);
        Assert.assertTrue("Bad comparaison", comparator.compare(null, null) == 0);
        Assert.assertTrue("Bad comparaison", comparator.compare(null, Locale.FRENCH) < 0);
        Assert.assertTrue("Bad comparaison", comparator.compare(Locale.FRENCH, null) > 0);
    }

    /**
     * Compare in english.
     */
    @Test
    public void english() {
        final ILocaleComparator comparator = newComparator(Locale.ENGLISH);
        // French < German
        Assert.assertTrue("Bad comparaison", comparator.compare(Locale.GERMAN, Locale.FRENCH) > 0);
    }

    /**
     * Compare in french.
     */
    @Test
    public void french() {
        final ILocaleComparator comparator = newComparator(Locale.FRENCH);
        // Allemand < Français
        Assert.assertTrue("Bad comparaison", comparator.compare(Locale.GERMAN, Locale.FRENCH) < 0);
    }

}
