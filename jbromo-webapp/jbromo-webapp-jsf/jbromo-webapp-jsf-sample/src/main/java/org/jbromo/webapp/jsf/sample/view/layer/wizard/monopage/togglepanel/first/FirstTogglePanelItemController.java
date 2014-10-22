package org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage.togglepanel.first;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.webapp.jsf.component.wizard.AbstractWizardItemController;

/**
 * Define the dynamic tab panel controller of the view.
 *
 * @author qjafcunuas
 *
 */
@Named
@RequestScoped
public class FirstTogglePanelItemController extends
        AbstractWizardItemController<FirstTogglePanelItemModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 3549835982536608004L;

    /**
     * The view's model.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private FirstTogglePanelItemModel model;

    @Override
    protected void loadData() {
        // Nothing to do
    }

    /**
     * Call when item will be rendered.
     *
     * @param <T>
     *            the element type.
     * @param event
     *            the event.
     */
    public <T extends Serializable> void observeRenderItem(
            @Observes final FirstTogglePanelItemRenderEvent event) {
        super.observeRenderItem(event);
        onLoadPage();
    }

    /**
     * Call when user click on next button.
     *
     * @return the next item id.
     */
    @Override
    public String onNext() {
        return super.onNext(new FirstTogglePanelItemNextEvent(getNextData()));
    }

    /**
     * Build the data to send for next event.
     *
     * @return the data.
     */
    private FirstTogglePanelItemNextData getNextData() {
        return new FirstTogglePanelItemNextData(getModel().isNextItemThird());
    }

}
