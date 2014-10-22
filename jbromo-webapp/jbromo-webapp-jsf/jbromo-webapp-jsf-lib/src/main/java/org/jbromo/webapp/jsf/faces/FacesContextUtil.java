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
package org.jbromo.webapp.jsf.faces;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FacesContext Utility class.
 *
 * @author qjafcunuas
 *
 */
public final class FacesContextUtil {
    /**
     * Default constructor.
     */
    private FacesContextUtil() {
        super();
    }

    /**
     * Return the current faces context.
     *
     * @return the faces context.
     */
    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Return the HTTP servlet response.
     *
     * @return the response.
     */
    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) getFacesContext().getExternalContext()
                .getResponse();
    }

    /**
     * Return the HTTP servlet request.
     *
     * @return the request.
     */
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) getFacesContext().getExternalContext()
                .getRequest();
    }

    /**
     * Return the EL context.
     *
     * @return the EL context.
     */
    public static ELContext getELContext() {
        return getFacesContext().getELContext();
    }

}
