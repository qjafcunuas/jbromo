/*-
 * Copyright (C) 2013-2014 The JBromo Authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jbromo.webapp.jsf.sample.view.login;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.jbromo.webapp.jsf.mvc.view.AbstractViewController;
import org.jbromo.webapp.jsf.sample.i18n.AppMessageKey;
import org.jbromo.webapp.jsf.sample.security.SecurityRole;
import org.jbromo.webapp.jsf.sample.view.View;
import org.jbromo.webapp.jsf.security.LoginEvent;
import org.jbromo.webapp.jsf.security.SecurityContext;
import org.jbromo.webapp.jsf.view.IView;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Define the login controller.
 * @author qjafcunuas
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
     * @return the corresponding view's id.
     */
    public String onLogin() {
        final HttpServletRequest request = (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
        try {
            request.login(getModel().getUsername(), getModel().getPassword());
        } catch (final ServletException e) {
            log.debug("Bad authentication for login " + getModel().getUsername(), e);
            getFacesMessages().warn(AppMessageKey.VIEW_LOGIN_ERROR);
            return "";
        }
        IView viewId;
        if (getSecurityContext().isUserInSecurityRole(SecurityRole.ADMIN)) {
            viewId = View.ADMIN_ACCESS;
        } else if (getSecurityContext().isUserInSecurityRole(SecurityRole.GUEST)) {
            viewId = View.GUEST_ACCESS;
        } else {
            viewId = View.INDEX;
        }
        // Fire login event.
        this.event.fire(new LoginEvent(new SecurityUser(getModel().getUsername())));
        // Fire render view event.
        return fireEventRenderView(viewId).getId();
    }

}
