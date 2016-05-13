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

import org.jbromo.common.ListUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.dto.IDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Define languages of a country.
 * @author qjafcunuas
 */
@EqualsAndHashCode(of = {"country"})
@ToString(of = {"country"})
public class CountryLanguages implements IDto {
    /**
     * serial versin UID.
     */
    private static final long serialVersionUID = 3838578581593064924L;

    /**
     * The country.
     */
    @Getter
    private final Locale country;

    /**
     * The languages of the country.
     */
    @Getter
    private final List<Locale> languages = new ArrayList<Locale>();

    /**
     * Default constructor. Languages list will be filtered according to the country.
     * @param country the country.
     * @param languages the languages list to filter.
     */
    public CountryLanguages(final Locale country, final List<Locale> languages) {
        super();
        this.country = country;
        if (ListUtil.isNotEmpty(languages)) {
            final String countryCode = LocaleCountryUtil.getIsoCode(country);
            for (final Locale language : languages) {
                if (ObjectUtil.equals(countryCode, LocaleCountryUtil.getIsoCode(language))) {
                    this.languages.add(language);
                }
            }
        }
    }

}
