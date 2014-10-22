package org.jbromo.webapp.jsf.sample.view.message;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.jbromo.common.i18n.IMessageLabel;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewModel;

/**
 * Define the MessageLabel model of the view.
 *
 * @author qjafcunuas
 *
 */
@Named
@ViewAccessScoped
public class MessageLabelModel extends AbstractViewModel {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -2930800098788027407L;

    /**
     * Fatal message.
     */
    @Getter
    @Setter
    private IMessageLabel fatal;
    /**
     * Error message.
     */
    @Getter
    @Setter
    private IMessageLabel error;
    /**
     * Warning message.
     */
    @Getter
    @Setter
    private IMessageLabel warning;
    /**
     * Waiting message.
     */
    @Getter
    @Setter
    private IMessageLabel waiting;
    /**
     * Info message.
     */
    @Getter
    @Setter
    private IMessageLabel info;
    /**
     * Ok message.
     */
    @Getter
    @Setter
    private IMessageLabel ok;

    /**
     * Define messages.
     */
    @Getter
    private final List<IMessageLabel> messages = new ArrayList<IMessageLabel>();

}
