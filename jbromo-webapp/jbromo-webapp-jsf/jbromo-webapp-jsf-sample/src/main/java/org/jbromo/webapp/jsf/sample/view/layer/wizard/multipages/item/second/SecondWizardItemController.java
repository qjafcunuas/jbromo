package org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.second;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.webapp.jsf.component.wizard.AbstractWizardItemController;

/**
 * Define the wizard second item controller of the view.
 *
 * @author qjafcunuas
 *
 */
@Named
@RequestScoped
public class SecondWizardItemController extends
        AbstractWizardItemController<SecondWizardItemModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 3549835982536608004L;

    /**
     * The view's model.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private SecondWizardItemModel model;

    @Override
    protected void loadData() {
        // Nothing to do
    }

    /**
     * Call when item will be rendered.
     *
     * @param event
     *            the event.
     */
    public void observeRenderItem(
            @Observes final SecondWizardItemRenderEvent event) {
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
        return super.onNext(new SecondWizardItemNextEvent());
    }

    @Override
    public String onPrevious() {
        return super.onPrevious(new SecondWizardItemPreviousEvent());
    }

}
