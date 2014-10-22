package org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.first;

import lombok.Getter;

import org.jbromo.webapp.jsf.component.wizard.AbstractWizardItemRenderEvent;
import org.jbromo.webapp.jsf.sample.view.View;

/**
 * Define event to fire for rendering first panel.
 * 
 * @author qjafcunuas
 * 
 */
@Getter
public class FirstWizardItemRenderEvent extends
        AbstractWizardItemRenderEvent<FirstWizardItemRenderData> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 903791455675769302L;

    /**
     * Default constructor.
     * 
     * @param data
     *            the data to send to the item controller.
     */
    public FirstWizardItemRenderEvent(final FirstWizardItemRenderData data) {
        super(View.WIZARD_FIRST_ITEM, null, data);
    }

}
