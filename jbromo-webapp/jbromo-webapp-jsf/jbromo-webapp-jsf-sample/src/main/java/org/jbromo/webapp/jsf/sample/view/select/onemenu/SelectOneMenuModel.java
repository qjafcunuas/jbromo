package org.jbromo.webapp.jsf.sample.view.select.onemenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.model.SelectItem;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewModel;
import org.jbromo.webapp.jsf.sample.view.select.common.SelectMenuOption;

/**
 * Define the SelectOneMenu model of the view.
 *
 * @author qjafcunuas
 *
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
    private final List<SelectMenuOption> options = new ArrayList<SelectMenuOption>();

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
    private final List<SelectOneMenuRow> rows = new ArrayList<SelectOneMenuRow>();
}
