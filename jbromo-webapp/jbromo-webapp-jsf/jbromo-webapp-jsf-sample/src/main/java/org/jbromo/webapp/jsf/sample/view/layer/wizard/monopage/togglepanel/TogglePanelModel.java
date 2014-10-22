package org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage.togglepanel;

import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.jbromo.webapp.jsf.component.wizard.AbstractWizardModel;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage.togglepanel.first.FirstTogglePanelItemNextData;

/**
 * Define the toggle panel model of the view.
 * 
 * @author qjafcunuas
 * 
 */
@Named
@ViewAccessScoped
public class TogglePanelModel extends AbstractWizardModel {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -2930800098788027207L;

    /**
     * The data from the first item.
     */
    @Getter
    @Setter
    private FirstTogglePanelItemNextData firstData;

}
