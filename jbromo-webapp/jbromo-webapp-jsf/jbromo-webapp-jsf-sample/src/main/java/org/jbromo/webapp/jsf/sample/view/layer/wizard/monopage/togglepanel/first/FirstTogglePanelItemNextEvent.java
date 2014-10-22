package org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage.togglepanel.first;

import lombok.Getter;

import org.jbromo.webapp.jsf.component.wizard.AbstractWizardItemNextEvent;

/**
 * Define event to fired when user clicks on next button of the first panel.
 * 
 * @author qjafcunuas
 * 
 */
@Getter
public class FirstTogglePanelItemNextEvent extends
        AbstractWizardItemNextEvent<FirstTogglePanelItemNextData> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 903791455675769302L;

    /**
     * Default constructor.
     * 
     * @param data
     *            the data to send.
     */
    public FirstTogglePanelItemNextEvent(final FirstTogglePanelItemNextData data) {
        super(data);
    }

}
