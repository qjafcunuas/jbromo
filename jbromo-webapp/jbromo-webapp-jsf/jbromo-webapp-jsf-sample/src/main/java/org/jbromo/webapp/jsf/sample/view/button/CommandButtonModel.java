package org.jbromo.webapp.jsf.sample.view.button;

import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewModel;

/**
 * Define ImageButton model's view.
 * 
 * @author qjafcunuas
 * 
 */
@Named
@ViewAccessScoped
public class CommandButtonModel extends AbstractViewModel {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -2930800098788027407L;

    /**
     * Count clicks event.
     */
    @Getter
    @Setter
    private Integer clickCounter;

    /**
     * Count clicks event.
     */
    @Getter
    @Setter
    private Integer ajaxCounter;

}
