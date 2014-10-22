package org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.third;

import java.io.Serializable;

import lombok.Getter;

import org.jbromo.webapp.jsf.component.wizard.AbstractWizardItemNextEvent;

/**
 * Define event to fired when user clicks on next button of the third panel.
 * 
 * @author qjafcunuas
 * 
 */
@Getter
public class ThirdWizardItemNextEvent extends
        AbstractWizardItemNextEvent<Serializable> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 903791455675769302L;

    /**
     * Default constructor.
     */
    public ThirdWizardItemNextEvent() {
        super(null);
    }

}
