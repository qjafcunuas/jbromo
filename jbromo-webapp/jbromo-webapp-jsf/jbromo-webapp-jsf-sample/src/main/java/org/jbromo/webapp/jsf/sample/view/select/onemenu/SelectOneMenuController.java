package org.jbromo.webapp.jsf.sample.view.select.onemenu;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.webapp.jsf.locale.LocaleContext;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewController;
import org.jbromo.webapp.jsf.sample.view.select.common.SelectMenuOptionUtil;
import org.jbromo.webapp.jsf.view.RenderView;
import org.jbromo.webapp.jsf.view.RenderViewEvent;

/**
 * Define the SelectOneMenu controller of the view.
 * 
 * @author qjafcunuas
 * 
 */
@Named
@RequestScoped
public class SelectOneMenuController extends
        AbstractViewController<SelectOneMenuModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 3549835982536608004L;

    /**
     * The view's model.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private SelectOneMenuModel model;

    /**
     * The locale context.
     */
    @Inject
    @Getter(AccessLevel.PRIVATE)
    private LocaleContext localeContext;

    @Override
    protected void loadData() {
        // Initialize options.
        initOptions();
        // Set random key. It should be sorted by selectOneMenu component.
        getModel().setSelected(
                RandomUtil.nextCollection(getModel().getOptions()));
        getModel().setNotSelected(null);
        // Initialize rows.
        initRows();
        // Initialize countries and languages.
        initLocale();
    }

    /**
     * Initialize options elements.
     */
    private void initOptions() {
        getModel().getOptions().clear();
        getModel().getOptions().addAll(
                SelectMenuOptionUtil.build(IntegerUtil.INT_10));
    }

    /**
     * Initialize rows elements.
     */
    private void initRows() {
        SelectOneMenuRow row;
        getModel().getRows().clear();
        for (int value = IntegerUtil.INT_0; value < IntegerUtil.INT_2; value++) {
            for (int readonly = IntegerUtil.INT_0; readonly < IntegerUtil.INT_2; readonly++) {
                for (int noSelectionLabel = IntegerUtil.INT_0; noSelectionLabel < IntegerUtil.INT_2; noSelectionLabel++) {
                    for (int noReadonlyLabel = IntegerUtil.INT_0; noReadonlyLabel < IntegerUtil.INT_2; noReadonlyLabel++) {
                        for (int required = IntegerUtil.INT_0; required < IntegerUtil.INT_2; required++) {
                            row = new SelectOneMenuRow();
                            if (value != 0) {
                                row.setValue(RandomUtil
                                        .nextCollection(getModel().getOptions()));
                            }
                            row.setReadonly(readonly != 0);
                            row.setNoSelectionLabel(noSelectionLabel != 0);
                            row.setNoReadonlyLabel(noReadonlyLabel != 0);
                            row.setRequired(required != 0);
                            getModel().getRows().add(row);
                        }
                    }
                }
            }
        }
    }

    /**
     * Initialize locales elements.
     */
    private void initLocale() {
        getModel().setCountries(getLocaleContext().getCountries());
        getModel().setLanguages(getLocaleContext().getLanguages(true));
        getModel().setCountriesLanguagesSelectItem(
                getLocaleContext().getCountriesLanguagesSelectItem(true));
        getModel().setUndistinctLanguages(
                getLocaleContext().getLanguages(false));
        getModel().setCountriesUndistinctLanguagesSelectItem(
                getLocaleContext().getCountriesLanguagesSelectItem(false));
        getModel().setSelectedCountry(
                RandomUtil.nextCollection(true, getModel().getCountries()));
    }

    /**
     * Call when view will be rendered.
     * 
     * @param event
     *            the event.
     */
    public void observeRenderView(
            @Observes @RenderView(controller = SelectOneMenuController.class) final RenderViewEvent event) {
        onLoadPage();
    }

}
