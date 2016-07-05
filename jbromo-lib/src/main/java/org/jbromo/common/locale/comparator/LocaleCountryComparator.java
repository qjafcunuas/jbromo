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

import org.jbromo.common.locale.LocaleCountryUtil;

/**
 * Define locale country comparator (FR, CA ...).
 * @author qjafcunuas
 */
public class LocaleCountryComparator extends AbstractLocaleComparator {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 3986670976365415136L;

    /**
     * Default constructor.
     * @param translate the locale to use for translating other locale.
     */
    LocaleCountryComparator(final Locale translate) {
        super(translate);
    }

    @Override
    protected String getLabel(final Locale locale) {
        return LocaleCountryUtil.translate(locale, getTranslate());
    }

}
