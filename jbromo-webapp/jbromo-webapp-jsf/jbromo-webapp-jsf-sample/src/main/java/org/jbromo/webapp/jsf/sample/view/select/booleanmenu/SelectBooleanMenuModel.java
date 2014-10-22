package org.jbromo.webapp.jsf.sample.view.select.booleanmenu;

import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewModel;

/**
 * Define the SelectBooleanMenu model of the view.
 *
 * @author qjafcunuas
 *
 */
@Named
@ViewAccessScoped
public class SelectBooleanMenuModel extends AbstractViewModel {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -2930800098788027207L;

    /**
     * a selected value.
     */
    @Getter
    @Setter
    private Boolean selectedValue;

}
