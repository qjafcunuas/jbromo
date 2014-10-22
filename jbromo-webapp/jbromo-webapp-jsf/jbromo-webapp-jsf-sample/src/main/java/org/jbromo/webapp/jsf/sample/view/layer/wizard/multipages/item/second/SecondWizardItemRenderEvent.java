package org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.second;

import java.io.Serializable;

import lombok.Getter;

import org.jbromo.webapp.jsf.component.wizard.AbstractWizardItemNextEvent;
import org.jbromo.webapp.jsf.component.wizard.AbstractWizardItemRenderEvent;
import org.jbromo.webapp.jsf.sample.view.View;

/**
 * Define event to fire for rendering second panel.
 * 
 * @author qjafcunuas
 * 
 */
@Getter
public class SecondWizardItemRenderEvent extends
        AbstractWizardItemRenderEvent<Serializable> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 903791455675769302L;

    /**
     * Default constructor.
     * 
     * @param nextEventSent
     *            The next event who has been sent for rendering this item.
     * 
     */
    public SecondWizardItemRenderEvent(
            final AbstractWizardItemNextEvent<? extends Serializable> nextEventSent) {
        super(View.WIZARD_SECOND_ITEM, nextEventSent);
    }

}
