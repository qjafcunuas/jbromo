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
package org.jbromo.webapp.jsf.faces.converter;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

/**
 * Define JUnit LocaleConverter class.
 *
 * @author qjafcunuas
 *
 */
public class LocaleConverterTest {

    /**
     * Test getAsObject method.
     */
    @Test
    public void getAsObject() {
        final LocaleConverter converter = new LocaleConverter();
        Assert.assertNull(converter.getAsObject(null, null, null));
        Assert.assertNull(converter.getAsObject(null, null, ""));
        Assert.assertEquals(Locale.FRANCE,
                converter.getAsObject(null, null, "fr_FR"));
        Assert.assertEquals(Locale.FRENCH,
                converter.getAsObject(null, null, "fr"));
    }

    /**
     * Test getAsString method.
     */
    @Test
    public void getAsString() {
        final LocaleConverter converter = new LocaleConverter();
        Assert.assertNull(converter.getAsString(null, null, null));
        Assert.assertEquals("fr_FR",
                converter.getAsString(null, null, Locale.FRANCE));
        Assert.assertEquals("fr",
                converter.getAsString(null, null, Locale.FRENCH));
    }
}
