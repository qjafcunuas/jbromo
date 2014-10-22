package org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage.togglepanel.second;

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
public class SecondTogglePanelItemRenderEvent extends
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
    public SecondTogglePanelItemRenderEvent(
            final AbstractWizardItemNextEvent<? extends Serializable> nextEventSent) {
        super(View.TOGGEL_PANEL_SECOND_ITEM, nextEventSent);
    }

}
