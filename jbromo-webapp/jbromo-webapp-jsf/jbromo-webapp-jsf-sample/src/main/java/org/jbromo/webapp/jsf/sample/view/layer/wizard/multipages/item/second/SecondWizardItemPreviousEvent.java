package org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.second;

import java.io.Serializable;

import lombok.Getter;

import org.jbromo.webapp.jsf.component.wizard.AbstractWizardItemPreviousEvent;

/**
 * Define event to fired when user clicks on next button of the second panel.
 * 
 * @author qjafcunuas
 * 
 */
@Getter
public class SecondWizardItemPreviousEvent extends
        AbstractWizardItemPreviousEvent<Serializable> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 903791455675769302L;

    /**
     * Default constructor.
     */
    public SecondWizardItemPreviousEvent() {
        this(null);
    }

    /**
     * Default constructor.
     * 
     * @param element
     *            the element value.
     */
    protected SecondWizardItemPreviousEvent(final Serializable element) {
        super(element);
    }

}
