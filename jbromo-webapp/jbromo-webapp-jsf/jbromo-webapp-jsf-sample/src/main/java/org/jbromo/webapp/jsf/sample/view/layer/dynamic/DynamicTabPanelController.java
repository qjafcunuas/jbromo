package org.jbromo.webapp.jsf.sample.view.layer.dynamic;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.webapp.jsf.mvc.view.AbstractViewController;
import org.jbromo.webapp.jsf.view.RenderView;
import org.jbromo.webapp.jsf.view.RenderViewEvent;

/**
 * Define the dynamic tab panel controller of the view.
 * 
 * @author qjafcunuas
 * 
 */
@Named
@RequestScoped
public class DynamicTabPanelController extends
        AbstractViewController<DynamicTabPanelModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 3549835982536608004L;

    /**
     * The view's model.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private DynamicTabPanelModel model;

    @Override
    protected void loadData() {
        getModel().getAjaxTabCounter()[0] = 1;
        getModel().getServerTabCounter()[0] = 1;
    }

    /**
     * Call when view will be rendered.
     * 
     * @param event
     *            the event.
     */
    public void observeRenderView(
            @Observes @RenderView(controller = DynamicTabPanelController.class) final RenderViewEvent event) {
        onLoadPage();
    }

    /**
     * Call when a tab is displayed.
     * 
     * @param index
     *            the selected tab index.
     */
    public void onSelectAjaxTab(final Number index) {
        getModel().getAjaxTabCounter()[index.intValue()]++;
    }

    /**
     * Call when a tab is displayed.
     * 
     * @param index
     *            the selected tab index.
     */
    public void onSelectServerTab(final Number index) {
        getModel().getServerTabCounter()[index.intValue()]++;
    }

}
