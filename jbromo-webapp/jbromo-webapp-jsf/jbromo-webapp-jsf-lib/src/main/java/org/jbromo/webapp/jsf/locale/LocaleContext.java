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
package org.jbromo.webapp.jsf.locale;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.jbromo.common.ListUtil;
import org.jbromo.common.StringUtil;
import org.jbromo.common.locale.CountryLanguages;
import org.jbromo.common.locale.LocaleCountryUtil;
import org.jbromo.common.locale.LocaleLanguageUtil;
import org.jbromo.webapp.jsf.faces.converter.LocaleConverter;

/**
 * Define locale context.
 *
 * @author qjafcunuas
 *
 */
@ApplicationScoped
@Named
public class LocaleContext {

    /**
     * Define the empty flag.
     */
    private static final String FLAG_EMPTY = "empty.png";

    /**
     * Define supported locale.
     */
    private final Set<Locale> supportedLocales = new HashSet<Locale>();

    /**
     * Define supported locale countries.
     */
    private final Set<Locale> countries = new HashSet<Locale>();

    /**
     * Define supported locale undistinct languages.
     */
    private final Set<Locale> undistinctLanguages = new HashSet<Locale>();

    /**
     * Define supported countries languages.
     */
    private final Set<CountryLanguages> languagesByCountries = new HashSet<CountryLanguages>();

    /**
     * Define supported countries undistinct languages.
     */
    private final Set<CountryLanguages> undistinctLanguagesByCountries = new HashSet<CountryLanguages>();

    /**
     * Define supported countries languages, with language as key.
     */
    private final List<SelectItem> countriesLanguages = new ArrayList<SelectItem>();

    /**
     * Define supported countries undistinct languages, with language as key.
     */
    private final List<SelectItem> undistinctCountriesLanguages = new ArrayList<SelectItem>();

    /**
     * The faces context.
     */
    @Inject
    @Getter(AccessLevel.PRIVATE)
    private FacesContext facesContext;

    /**
     * The locale converter.
     */
    @Inject
    @Getter(AccessLevel.PRIVATE)
    private LocaleConverter localeConverter;

    /**
     * If not null, overload all distinct parameters. if true, application must
     * distinguished all languages (fr_FR distinct to fr_CA).
     */
    @Getter
    @Setter
    private boolean distinctLanguages = true;

    /**
     * PostConstruct method.
     */
    @PostConstruct
    protected void postConstruct() {
        loadLocales();
    }

    /**
     * Overloading distinct parameters.
     *
     * @param distinct
     *            the parameters.
     * @return the overloading value.
     */
    private boolean isDistinctLanguages(final Boolean distinct) {
        if (distinct != null) {
            return distinct.booleanValue();
        } else {
            return isDistinctLanguages();
        }
    }

    /**
     * Load locales information.
     *
     */
    private void loadLocales() {
        // Load locales.
        if (this.supportedLocales.isEmpty()) {
            // load supported locales.
            initSupportedLocales();
            // load supported countries.
            initSupportedCountries();
            // load undistinct languages.
            initSupportedUndistinctLanguages();
            // load countries languages.
            initSupportedCountriesLanguages();
        }
    }

    /**
     * Initialize supported locales.
     */
    private void initSupportedLocales() {
        final Iterator<Locale> iter = getFacesContext().getApplication()
                .getSupportedLocales();
        Locale one;
        while (iter.hasNext()) {
            one = iter.next();
            this.supportedLocales.add(one);
        }
    }

    /**
     * Initialize supported countries.
     */
    private void initSupportedCountries() {
        final Set<String> codes = new HashSet<String>();
        String code;
        for (final Locale locale : this.supportedLocales) {
            code = getCountryIsoCode(locale);
            if (code != null && !codes.contains(code)) {
                codes.add(code);
                this.countries.add(locale);
            }
        }
    }

    /**
     * Initialize supported undistinct languages.
     */
    private void initSupportedUndistinctLanguages() {
        final Set<String> codes = new HashSet<String>();
        String isoCode;
        String fullCode;
        for (final Locale locale : this.supportedLocales) {
            // Get iso code (fr, en ...)
            isoCode = LocaleLanguageUtil.getIsoCode(locale);
            if (!codes.contains(isoCode)) {
                // Get full code (fr, fr_FR, fr_CA, en_UK ...)
                fullCode = LocaleLanguageUtil.getFullCode(locale);
                if (fullCode.equals(isoCode)) {
                    codes.add(isoCode);
                    this.undistinctLanguages.add(locale);
                } else {
                    final Locale tmp = LocaleLanguageUtil.toLocale(isoCode);
                    if (tmp != null) {
                        codes.add(isoCode);
                        this.undistinctLanguages.add(tmp);
                    }
                }
            }
        }
    }

    /**
     * Convert a Locale to a string, using locale converter.
     *
     * @param locale
     *            the locale.
     * @return the string.
     */
    private String convert(final Locale locale) {
        return getLocaleConverter().getAsString(null, null, locale);
    }

    /**
     * Initialize supported countries languages.
     */
    private void initSupportedCountriesLanguages() {
        CountryLanguages value;

        // Distinct.
        final List<Locale> allLanguages = getLanguages(true);
        SelectItem item;
        for (final Locale country : getCountries()) {
            value = new CountryLanguages(country, allLanguages);
            this.languagesByCountries.add(value);
            for (final Locale lang : value.getLanguages()) {
                item = new SelectItem(convert(country), convert(lang));
                this.countriesLanguages.add(item);
            }
        }
        // Undistinct
        Locale one;
        for (final Locale country : getCountries()) {
            value = new CountryLanguages(country, allLanguages);
            for (final Locale lang : ListUtil.toList(value.getLanguages())) {
                one = LocaleLanguageUtil.toLocale(LocaleLanguageUtil
                        .getIsoCode(lang));
                if (!value.getLanguages().contains(one)) {
                    value.getLanguages().add(one);
                }
                value.getLanguages().remove(lang);
            }
            this.undistinctLanguagesByCountries.add(value);
            for (final Locale lang : value.getLanguages()) {
                item = new SelectItem(convert(country), convert(lang));
                this.undistinctCountriesLanguages.add(item);
            }
        }

    }

    /**
     * Return the current locale.
     *
     * @return the locale.
     */
    public Locale getLocale() {
        return getFacesContext().getExternalContext().getRequestLocale();
    }

    /**
     * Return countries list, sorted by name.
     *
     * @return countries list.
     */
    public List<Locale> getCountries() {
        final List<Locale> locales = new ArrayList<Locale>();
        locales.addAll(this.countries);
        LocaleCountryUtil.sort(locales, getLocale());
        return locales;
    }

    /**
     * Return languages list, sorted by name.
     *
     * @return languages list.
     */
    public List<Locale> getLanguages() {
        return getLanguages(null);
    }

    /**
     * Return languages list, sorted by name.
     *
     * @param distinct
     *            if not null, overload distinctLanguages member. if true,
     *            distinguish all languages (fr_FR distinct to fr_CA).
     * @return languages list.
     */
    public List<Locale> getLanguages(final Boolean distinct) {
        List<Locale> locales;
        final boolean distinctLang = isDistinctLanguages(distinct);
        if (distinctLang) {
            locales = ListUtil.toList(this.supportedLocales);
        } else {
            locales = ListUtil.toList(this.undistinctLanguages);
        }
        LocaleLanguageUtil.sort(locales, getLocale(), distinctLang);
        return locales;
    }

    /**
     * Return Countries list, with associated languages.
     *
     * @return countries list.
     */
    public List<CountryLanguages> getLanguagesByCountries() {
        return getLanguagesByCountries(null);
    }

    /**
     * Return Countries list, with associated languages.
     *
     * @param distinct
     *            if not null, overload distinctLanguages member. if true,
     *            distinguish all languages (fr_FR distinct to fr_CA).
     * @return countries list.
     */
    public List<CountryLanguages> getLanguagesByCountries(final Boolean distinct) {
        final boolean distinctLang = isDistinctLanguages(distinct);
        if (distinctLang) {
            return ListUtil.toList(this.languagesByCountries);
        } else {
            return ListUtil
                    .toList(this.undistinctLanguagesByCountries);
        }
    }

    /**
     * Return Countries languages map.
     *
     * @return countries languages map.
     */
    public List<SelectItem> getCountriesLanguagesSelectItem() {
        return getCountriesLanguagesSelectItem(null);
    }

    /**
     * Return Countries languages map.
     *
     * @param distinct
     *            if not null, overload distinctLanguages member. if true,
     *            distinguish all languages (fr_FR distinct to fr_CA).
     * @return Countries languages list.
     */
    public List<SelectItem> getCountriesLanguagesSelectItem(
            final Boolean distinct) {
        List<SelectItem> values;
        final boolean distinctLang = isDistinctLanguages(distinct);
        if (distinctLang) {
            values = ListUtil.toList(this.countriesLanguages);
        } else {
            values = ListUtil.toList(this.undistinctCountriesLanguages);
        }
        return values;
    }

    /**
     * Return the country iso code of the current locale.
     *
     * @return the iso code.
     */
    public String getCountryIsoCode() {
        return getCountryIsoCode(getLocale());
    }

    /**
     * Return the country iso code of a locale.
     *
     * @param locale
     *            the locale to get country iso code.
     * @return the iso code.
     */
    public String getCountryIsoCode(final Locale locale) {
        return LocaleCountryUtil.getIsoCode(locale);
    }

    /**
     * Return the language iso code of the current locale (2 chars).
     *
     * @return the iso code.
     */
    public String getLanguageIsoCode() {
        return getLanguageIsoCode(getLocale());
    }

    /**
     * Return the language iso code of a locale (2 chars).
     *
     * @param locale
     *            the locale to get language iso code.
     * @return the iso code.
     */
    public String getLanguageIsoCode(final Locale locale) {
        return LocaleLanguageUtil.getIsoCode(locale);
    }

    /**
     * Return the country flag image file name of the current locale.
     *
     * @return the file name.
     */
    public String getCountryFlag() {
        return getCountryFlag(getLocale());
    }

    /**
     * Return the country flag image file name .
     *
     * @param locale
     *            the locale to get file name.
     * @return the file name.
     */
    public String getCountryFlag(final Locale locale) {
        final String code = getCountryIsoCode(locale);
        if (StringUtil.isEmpty(code)) {
            return FLAG_EMPTY;
        }
        return code.toLowerCase().concat(".png");
    }

    /**
     * Return the country label of the current locale, translated into the
     * current locale language.
     *
     * @return the country label.
     */
    public String getCountryLabel() {
        return getCountryLabel(getLocale());
    }

    /**
     * Return the country label of a locale, translated into the current locale
     * language.
     *
     * @param locale
     *            the locale to get label.
     * @return the country label.
     */
    public String getCountryLabel(final Locale locale) {
        return LocaleCountryUtil.translate(locale, getLocale());
    }

    /**
     * Return the language label of the current locale, translated into the
     * current locale language.
     *
     * @return the language label.
     */
    public String getLanguageLabel() {
        return getLanguageLabel(getLocale(), null);
    }

    /**
     * Return the language label of a locale, translated into the current locale
     * language.
     *
     * @param locale
     *            the locale to get label.
     * @return the language label.
     */
    public String getLanguageLabel(final Locale locale) {
        return getLanguageLabel(locale, null);
    }

    /**
     * Return the language label of a locale, translated into the current locale
     * language.
     *
     * @param locale
     *            the locale to get label.
     * @param distinct
     *            if not null, overload distinctLanguages member. if true,
     *            distinguish all languages (fr_FR distinct to fr_CA).
     * @return the language label.
     */
    public String getLanguageLabel(final Locale locale, final Boolean distinct) {
        final boolean distinctLang = isDistinctLanguages(distinct);
        return LocaleLanguageUtil.translate(locale, getLocale(), distinctLang);
    }
}
