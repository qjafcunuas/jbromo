/*
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
package org.jbromo.webapp.jsf.security;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * Define the security context.
 *
 * @author qjafcunuas
 *
 */
@SessionScoped
@Named
public class SecurityContext implements Serializable {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 6911502210667921267L;

    /**
     * The faces context.
     */
    @Inject
    @Getter(AccessLevel.PRIVATE)
    private transient FacesContext facesContext;

    /**
     * The current user.
     */
    private ISecurityUser user;

    /**
     * Return true if user is associated with a security role.
     *
     * @param role
     *            the security role.
     * @return true/false.
     */
    public boolean isUserInSecurityRole(final ISecurityRole role) {
        return getFacesContext().getExternalContext().isUserInRole(
                role.getName());
    }

    /**
     * Return true if user is associated with a security role.
     *
     * @param role
     *            the security role.
     * @return true/false.
     */
    public boolean isUserInRole(final String role) {
        return getFacesContext().getExternalContext().isUserInRole(role);
    }

    /**
     * Return true if user is authenticated.
     *
     * @return true/false.
     */
    public boolean isAuthenticated() {
        return getFacesContext().getExternalContext().getUserPrincipal() != null;
    }

    /**
     * Observer when new user is connected successfully.
     *
     * @param event
     *            the event.
     */
    public void observeRenderView(@Observes final LoginEvent event) {
        this.user = event.getUser();
    }

    /**
     * Observer when user is disconnected.
     *
     * @param event
     *            the event.
     */
    public void observeRenderView(@Observes final LogoutEvent event) {
        this.user = null;
    }

    /**
     * Return the current user.
     *
     * @return user.
     */
    public ISecurityUser getUser() {
        if (this.user == null || !isAuthenticated()) {
            return null;
        } else {
            return this.user;
        }
    }

}
