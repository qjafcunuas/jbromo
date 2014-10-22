package org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage.togglepanel.third;

import java.io.Serializable;

import lombok.Getter;

import org.jbromo.webapp.jsf.component.wizard.AbstractWizardItemNextEvent;
import org.jbromo.webapp.jsf.component.wizard.AbstractWizardItemRenderEvent;
import org.jbromo.webapp.jsf.sample.view.View;

/**
 * Define event to fire for rendering third panel.
 * 
 * @author qjafcunuas
 * 
 */
@Getter
public class ThirdTogglePanelItemRenderEvent extends
        AbstractWizardItemRenderEvent<ThirdTogglePanelItemRenderData> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 903791455675769302L;

    /**
     * Default constructor.
     * 
     * @param nextEventSent
     *            The next event who has been sent for rendering this item.
     * @param data
     *            data to send to item controller.
     */
    public ThirdTogglePanelItemRenderEvent(
            final AbstractWizardItemNextEvent<? extends Serializable> nextEventSent,
            final ThirdTogglePanelItemRenderData data) {
        super(View.TOGGLE_PANEL_THIRD_ITEM, nextEventSent, data);
    }

}
