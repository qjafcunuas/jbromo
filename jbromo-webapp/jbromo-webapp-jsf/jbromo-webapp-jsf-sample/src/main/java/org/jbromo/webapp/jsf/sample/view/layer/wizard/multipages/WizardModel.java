package org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.jbromo.webapp.jsf.component.wizard.AbstractWizardModel;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.first.FirstWizardItemNextData;

/**
 * Define the wizard model of the view.
 * 
 * @author qjafcunuas
 * 
 */
@Named
@ConversationScoped
public class WizardModel extends AbstractWizardModel {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -2930800098788027207L;

    /**
     * The data from the first item.
     */
    @Getter
    @Setter
    private FirstWizardItemNextData firstData;

}
