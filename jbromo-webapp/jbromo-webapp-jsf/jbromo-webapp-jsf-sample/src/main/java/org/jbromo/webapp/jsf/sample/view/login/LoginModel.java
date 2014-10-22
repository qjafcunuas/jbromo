package org.jbromo.webapp.jsf.sample.view.login;

import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewModel;

/**
 * Define the login model.
 *
 * @author qjafcunuas
 *
 */
@Named
@ViewAccessScoped
public class LoginModel extends AbstractViewModel {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -6906687212977886744L;

    /**
     * The username.
     */
    @Getter
    @Setter
    private String username;

    /**
     * The password.
     */
    @Getter
    @Setter
    private String password;
}
