package org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage.togglepanel.third;

import java.io.Serializable;

import lombok.Getter;

import org.jbromo.webapp.jsf.component.wizard.AbstractWizardItemNextEvent;

/**
 * Define event to fired when user clicks on next button of the second panel.
 * 
 * @author qjafcunuas
 * 
 */
@Getter
public class ThirdTogglePanelItemNextEvent extends
        AbstractWizardItemNextEvent<Serializable> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 903791455675769302L;

    /**
     * Default constructor.
     */
    public ThirdTogglePanelItemNextEvent() {
        super(null);
    }

}
