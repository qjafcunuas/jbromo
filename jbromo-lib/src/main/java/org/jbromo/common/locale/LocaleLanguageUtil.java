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

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.jbromo.common.StringUtil;
import org.jbromo.common.locale.comparator.LocaleComparatorFactory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Define Locale utility for language.
 * @author qjafcunuas
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LocaleLanguageUtil {

    /**
     * Return the iso code of the locale (fr, en...).
     * @param locale the locale.
     * @return the iso code.
     */
    public static String getIsoCode(final Locale locale) {
        if (locale == null) {
            return null;
        }
        final String code = locale.getLanguage();
        if (StringUtil.isEmpty(code)) {
            return null;
        } else {
            return code;
        }
    }

    /**
     * Return the full code of the locale.
     * @param locale the locale.
     * @return the full code.
     */
    public static String getFullCode(final Locale locale) {
        if (locale == null) {
            return null;
        }
        final String code = locale.toString();
        if (StringUtil.isEmpty(code)) {
            return null;
        } else {
            return code;
        }
    }

    /**
     * Return the translated locale label.
     * @param translate the locale to translate.
     * @param into the locale must be translated into this locale.
     * @param distinct if true, return translated for all languages (fr_FR, fr_CA...), else return only for simple languages (fr, en...).
     * @return the translated locale label.
     */
    public static String translate(final Locale translate, final Locale into, final boolean distinct) {
        if (translate == null || into == null) {
            return null;
        }
        if (distinct) {
            return translate.getDisplayName(into);
        } else {
            return translate.getDisplayLanguage(into);
        }
    }

    /**
     * Sort locale according to the label.
     * @param translate the locales to sort.
     * @param into locales must be translated into this locale for sorting order.
     * @param distinct if true, return translated for all languages (fr_FR, fr_CA...), else return only for simple languages (fr, en...).
     */
    public static void sort(final List<Locale> translate, final Locale into, final boolean distinct) {
        Collections.sort(translate, LocaleComparatorFactory.getInstance().getLanguageComparator(into, distinct));
    }

    /**
     * Return the locale according to an iso code.
     * @param isoCode the iso code.
     * @return the locale.
     */
    public static Locale toLocale(final String isoCode) {
        if (StringUtil.isEmpty(isoCode)) {
            return null;
        }
        if (isoCode.contains("_")) {
            final String[] split = isoCode.split("_");
            return toLocale(split[0], split[1]);
        } else {
            return new Locale(isoCode);
        }
    }

    /**
     * Return the locale according to an iso code.
     * @param isoLang the iso lang code.
     * @param isoCountry the iso country code.
     * @return the locale.
     */
    public static Locale toLocale(final String isoLang, final String isoCountry) {
        if (StringUtil.isEmpty(isoLang)) {
            return null;
        }
        if (StringUtil.isEmpty(isoCountry)) {
            return new Locale(isoLang);
        } else {
            return new Locale(isoLang, isoCountry);
        }
    }
}
