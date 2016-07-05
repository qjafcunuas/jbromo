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
package org.jbromo.webapp.jsf.sample.view.select.onemenu;

import java.util.List;
import java.util.Locale;

import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.jbromo.common.ListUtil;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewModel;
import org.jbromo.webapp.jsf.sample.view.select.common.SelectMenuOption;

import lombok.Getter;
import lombok.Setter;

/**
 * Define the SelectOneMenu model of the view.
 * @author qjafcunuas
 */
@Named
@ViewAccessScoped
public class SelectOneMenuModel extends AbstractViewModel {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -2930800098788027207L;

    /**
     * Define options list.
     */
    @Getter
    private final List<SelectMenuOption> options = ListUtil.toList();

    /**
     * The selected option.
     */
    @Getter
    @Setter
    private SelectMenuOption selected;

    /**
     * The ajax selected option.
     */
    @Getter
    @Setter
    private SelectMenuOption ajaxSelected;

    /**
     * The not selected option.
     */
    @Getter
    @Setter
    private SelectMenuOption notSelected;

    /**
     * The locale countries.
     */
    @Getter
    @Setter
    private List<Locale> countries;

    /**
     * The locale languages.
     */
    @Getter
    @Setter
    private List<Locale> languages;

    /**
     * The locale undistinct languages.
     */
    @Getter
    @Setter
    private List<Locale> undistinctLanguages;

    /**
     * The locale countries languages map.
     */
    @Getter
    @Setter
    private List<SelectItem> countriesLanguagesSelectItem;

    /**
     * The locale countries undistinct languages map.
     */
    @Getter
    @Setter
    private List<SelectItem> countriesUndistinctLanguagesSelectItem;

    /**
     * The selected country.
     */
    @Getter
    @Setter
    private Locale selectedCountry;

    /**
     * The selected language.
     */
    @Getter
    @Setter
    private Locale selectedLanguage;

    /**
     * The rows datatable.
     */
    @Getter
    private final List<SelectOneMenuRow> rows = ListUtil.toList();
}
