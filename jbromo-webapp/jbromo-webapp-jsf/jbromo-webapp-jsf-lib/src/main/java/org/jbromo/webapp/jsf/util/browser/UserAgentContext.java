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
package org.jbromo.webapp.jsf.util.browser;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.webapp.common.browser.UserAgentUtil;

/**
 * Define user-agent inspector.
 *
 * @author qjafcunuas
 *
 */
@Named
@ApplicationScoped
public class UserAgentContext {

    /**
     * The faces context.
     */
    @Inject
    @Getter(AccessLevel.PRIVATE)
    private FacesContext facesContext;

    /**
     * Return the user-agent from the request.
     *
     * @return the user-agent.
     */
    private String getUserAgent() {
        return ((HttpServletRequest) getFacesContext().getExternalContext()
                .getRequest()).getHeader("user-agent");
    }

    /**
     * Return the browser css label.
     *
     * @return the css label.
     */
    public String getBrowserLabel() {
        return UserAgentUtil.getBrowserLabel(getUserAgent());
    }
}
