package org.jbromo.webapp.jsf.sample.view.select.booleanmenu;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.webapp.jsf.locale.LocaleContext;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewController;
import org.jbromo.webapp.jsf.view.RenderView;
import org.jbromo.webapp.jsf.view.RenderViewEvent;

/**
 * Define the SelectBooleanMenu controller of the view.
 *
 * @author qjafcunuas
 *
 */
@Named
@RequestScoped
public class SelectBooleanMenuController extends
        AbstractViewController<SelectBooleanMenuModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 3549835982536608004L;

    /**
     * The view's model.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private SelectBooleanMenuModel model;

    /**
     * The locale context.
     */
    @Inject
    @Getter(AccessLevel.NONE)
    private LocaleContext localeContext;

    @Override
    protected void loadData() {
        // Nothing to do.
    }

    /**
     * Call when view will be rendered.
     *
     * @param event
     *            the event.
     */
    public void observeRenderView(
            @Observes @RenderView(controller = SelectBooleanMenuController.class) final RenderViewEvent event) {
        onLoadPage();
    }

}
