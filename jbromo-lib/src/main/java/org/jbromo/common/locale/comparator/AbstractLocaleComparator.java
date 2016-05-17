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

import lombok.AccessLevel;
import lombok.Getter;

/**
 * Define locale comparator.
 * @author qjafcunuas
 */
public abstract class AbstractLocaleComparator implements ILocaleComparator {

    /**
     * The locale for translated displayed locale.
     */
    @Getter(AccessLevel.PROTECTED)
    private final Locale translate;

    /**
     * Default constructor.
     * @param translate the locale to use for translating other locale.
     */
    protected AbstractLocaleComparator(final Locale translate) {
        super();
        this.translate = translate;
    }

    @Override
    public int compare(final Locale o1, final Locale o2) {
        if (o1 == null && o2 == null) {
            return 0;
        } else if (o1 == null) {
            return -1;
        } else if (o2 == null) {
            return 1;
        }

        final String d1 = getLabel(o1);
        final String d2 = getLabel(o2);
        return d1.compareToIgnoreCase(d2);
    }

    /**
     * Return the label of the locale.
     * @param locale the locale.
     * @return the label.
     */
    protected abstract String getLabel(final Locale locale);

}
