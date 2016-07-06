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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jbromo.common.ObjectUtil;
import org.jbromo.common.StringUtil;
import org.jbromo.common.locale.comparator.LocaleComparatorFactory;

import lombok.experimental.UtilityClass;

/**
 * Define Locale utility for country.
 * @author qjafcunuas
 */
@UtilityClass
public final class LocaleCountryUtil {

    /**
     * Map Locale by country iso code.
     */
    private static final Map<String, Locale> ISO_MAPPER = Collections.synchronizedMap(new HashMap<String, Locale>());

    /**
     * Return the iso code of the locale.
     * @param locale the locale.
     * @return the iso code.
     */
    public static String getIsoCode(final Locale locale) {
        if (locale == null) {
            return null;
        }
        final String code = locale.getCountry();
        if (StringUtil.isEmpty(code)) {
            return null;
        } else {
            return code;
        }
    }

    /**
     * Return true if two locales have same country code.
     * @param one one locale.
     * @param two another locale.
     * @return true/false.
     */
    public static boolean same(final Locale one, final Locale two) {
        final String oneCode = getIsoCode(one);
        final String twoCode = getIsoCode(two);
        return ObjectUtil.notNullAndEquals(oneCode, twoCode);
    }

    /**
     * Return the translated locale label.
     * @param translate the locale to translate.
     * @param into the locale must be translated into this locale.
     * @return the translated locale label.
     */
    public static String translate(final Locale translate, final Locale into) {
        if (translate == null || into == null) {
            return null;
        }
        return translate.getDisplayCountry(into);
    }

    /**
     * Sort locale according to the label.
     * @param translate the locales to sort.
     * @param into locales must be translated into this locale for sorting order.
     */
    public static void sort(final List<Locale> translate, final Locale into) {
        Collections.sort(translate, LocaleComparatorFactory.getInstance().getCountryComparator(into));
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
        if (!ISO_MAPPER.containsKey(isoCode)) {
            // Build full code (fr_FR...), but it can be not existed (en_EN for
            // example).
            final String fullCode = isoCode.toLowerCase().concat("_").concat(isoCode);
            String code;
            for (final Locale locale : Locale.getAvailableLocales()) {
                // Test code like fr_FR.
                code = LocaleLanguageUtil.getFullCode(locale);
                if (fullCode.equals(code)) {
                    ISO_MAPPER.put(isoCode, locale);
                    break;
                }
                // Test code like FR.
                code = getIsoCode(locale);
                if (isoCode.equals(code)) {
                    ISO_MAPPER.put(isoCode, locale);
                }
            }
            if (!ISO_MAPPER.containsKey(isoCode)) {
                ISO_MAPPER.put(isoCode, null);
            }
        }
        return ISO_MAPPER.get(isoCode);
    }

}