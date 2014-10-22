package org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.webapp.jsf.mvc.view.AbstractViewController;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage.togglepanel.TogglePanelRenderEvent;
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
public class TogglePanelControllerView extends
        AbstractViewController<TogglePanelModelView> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 3549835982536608004L;

    /**
     * The view's model.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private TogglePanelModelView model;

    /**
     * Use for sending toggle load event.
     */
    @Inject
    @Getter
    private Event<TogglePanelRenderEvent> loadEvent;

    /**
     * Call when view will be rendered.
     * 
     * @param event
     *            the event.
     */
    public void observeRenderView(
            @Observes @RenderView(controller = TogglePanelControllerView.class) final RenderViewEvent event) {
        onLoadPage();
    }

    @Override
    protected void loadData() {
        this.loadEvent.fire(new TogglePanelRenderEvent());
    }

}
