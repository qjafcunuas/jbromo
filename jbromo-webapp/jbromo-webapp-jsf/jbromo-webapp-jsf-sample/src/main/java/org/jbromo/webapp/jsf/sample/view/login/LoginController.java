package org.jbromo.webapp.jsf.sample.view.login;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.jbromo.webapp.jsf.mvc.view.AbstractViewController;
import org.jbromo.webapp.jsf.sample.i18n.AppMessageKey;
import org.jbromo.webapp.jsf.sample.security.SecurityRole;
import org.jbromo.webapp.jsf.sample.view.View;
import org.jbromo.webapp.jsf.security.LoginEvent;
import org.jbromo.webapp.jsf.security.SecurityContext;
import org.jbromo.webapp.jsf.view.IView;

/**
 * Define the login controller.
 *
 * @author qjafcunuas
 *
 */
@Named
@RequestScoped
@Slf4j
public class LoginController extends AbstractViewController<LoginModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -8922313316022819991L;
    /**
     * the model of the view.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private LoginModel model;

    /**
     * The security context.
     */
    @Inject
    @Getter(AccessLevel.PRIVATE)
    private SecurityContext securityContext;

    /**
     * For firing login event.
     */
    @Inject
    private Event<LoginEvent> event;

    @Override
    protected void loadData() {
        // nothing to do.
    }

    /**
     * Called when user select login menu.
     *
     * @return the corresponding view's id.
     */
    public String onLogin() {
        final HttpServletRequest request = (HttpServletRequest) getFacesContext()
                .getExternalContext().getRequest();
        try {
            request.login(getModel().getUsername(), getModel().getPassword());
        } catch (final ServletException e) {
            log.debug("Bad authentication for login "
                    + getModel().getUsername(), e);
            getFacesMessages().warn(AppMessageKey.VIEW_LOGIN_ERROR);
            return "";
        }
        IView viewId;
        if (getSecurityContext().isUserInSecurityRole(SecurityRole.ADMIN)) {
            viewId = View.ADMIN_ACCESS;
        } else if (getSecurityContext()
                .isUserInSecurityRole(SecurityRole.GUEST)) {
            viewId = View.GUEST_ACCESS;
        } else {
            viewId = View.INDEX;
        }
        // Fire login event.
        event.fire(new LoginEvent(new SecurityUser(getModel()
                .getUsername())));
        // Fire render view event.
        return fireEventRenderView(viewId).getId();
    }

}
