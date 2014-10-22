package org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.first;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.webapp.jsf.component.wizard.AbstractWizardItemController;

/**
 * Define the wizard first item controller of the view.
 *
 * @author qjafcunuas
 *
 */
@Named
@RequestScoped
public class FirstWizardItemController extends
        AbstractWizardItemController<FirstWizardItemModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 3549835982536608005L;

    /**
     * The view's model.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private FirstWizardItemModel model;

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
            @Observes final FirstWizardItemRenderEvent event) {
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
        return super.onNext(new FirstWizardItemNextEvent(getNextData()));
    }

    /**
     * Build the data to send for next event.
     *
     * @return the data.
     */
    private FirstWizardItemNextData getNextData() {
        return new FirstWizardItemNextData(getModel().isNextItemThird());
    }

}
