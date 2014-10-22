package org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage.togglepanel.first;

import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.jbromo.webapp.jsf.component.wizard.AbstractWizardItemModel;

/**
 * Define the dynamic tab panel model of the view.
 * 
 * @author qjafcunuas
 * 
 */
@Named
@ViewAccessScoped
public class FirstTogglePanelItemModel extends AbstractWizardItemModel {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -2930800098788027207L;

    /**
     * if true, next item panel must be the third, else the second.
     */
    @Getter
    @Setter
    private boolean nextItemThird;

}
