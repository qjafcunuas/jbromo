package org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.first;

import lombok.Getter;

import org.jbromo.webapp.jsf.component.wizard.AbstractWizardItemNextEvent;

/**
 * Define event to fired when user clicks on next button of the first panel.
 * 
 * @author qjafcunuas
 * 
 */
@Getter
public class FirstWizardItemNextEvent extends
        AbstractWizardItemNextEvent<FirstWizardItemNextData> {

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
    public FirstWizardItemNextEvent(final FirstWizardItemNextData data) {
        super(data);
    }

}
