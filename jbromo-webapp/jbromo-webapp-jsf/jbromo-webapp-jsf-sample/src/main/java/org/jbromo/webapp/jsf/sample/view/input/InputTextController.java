package org.jbromo.webapp.jsf.sample.view.input;

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
 * Define the InputText controller of the view.
 * 
 * @author qjafcunuas
 * 
 */
@Named
@RequestScoped
public class InputTextController extends AbstractViewController<InputTextModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 3549835982536608004L;

    /**
     * The view's model.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private InputTextModel model;

    @Override
    protected void loadData() {
        getModel().setInoutput("inoutput text");
    }

    /**
     * Call when view will be rendered.
     * 
     * @param event
     *            the event.
     */
    public void observeRenderView(
            @Observes @RenderView(controller = InputTextController.class) final RenderViewEvent event) {
        onLoadPage();
    }

}
