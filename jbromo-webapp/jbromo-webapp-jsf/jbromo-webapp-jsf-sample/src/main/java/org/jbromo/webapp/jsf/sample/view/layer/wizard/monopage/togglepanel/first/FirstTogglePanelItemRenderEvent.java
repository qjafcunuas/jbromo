package org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage.togglepanel.first;

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
public class FirstTogglePanelItemRenderEvent extends
        AbstractWizardItemRenderEvent<FirstTogglePanelItemRenderData> {

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
    public FirstTogglePanelItemRenderEvent(
            final FirstTogglePanelItemRenderData data) {
        super(View.TOGGEL_PANEL_FIRST_ITEM, null, data);
    }

}
