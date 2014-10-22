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
package org.jbromo.webapp.jsf.cdi;

import javax.el.ELContext;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.myfaces.extensions.cdi.core.api.provider.BeanManagerProvider;
import org.jbromo.webapp.jsf.faces.ELMessage;
import org.jbromo.webapp.jsf.faces.FacesMessages;
import org.jbromo.webapp.jsf.faces.FacesResourceBundle;

/**
 * CDI utility.
 *
 * @author qjafcunuas
 *
 */
public final class CDIFacesUtil {

    /**
     * Default constructor.
     */
    private CDIFacesUtil() {
        super();
    }

    /**
     * Return the faces context.
     *
     * @return the faces context.
     */
    public static FacesContext getFacesContext() {
        return getCDIObject(FacesContext.class);
    }

    /**
     * Return a CDI object.
     *
     * @param <C>
     *            the class object type to return.
     * @param type
     *            the class object to return.
     * @return the CDI object.
     */
    public static <C> C getCDIObject(final Class<C> type) {
        return BeanManagerProvider.getInstance().getContextualReference(type);
        // For DeltaSpike : BeanProvider.getContextualReference(type, true);
    }

    /**
     * Return the EL context.
     *
     * @return the EL context.
     */
    public static ELContext getELContext() {
        return getFacesContext().getELContext();
    }

    /**
     * Return the EL message.
     *
     * @return the EL message.
     */
    public static ELMessage getELMessage() {
        return getCDIObject(ELMessage.class);
    }

    /**
     * Return the Faces messages.
     *
     * @return the Faces messages.
     */
    public static FacesMessages getFacesMessages() {
        return getCDIObject(FacesMessages.class);
    }

    /**
     * Return the Faces resource bundle.
     *
     * @return the Faces resource bundle.
     */
    public static FacesResourceBundle getFacesResourceBundle() {
        return getCDIObject(FacesResourceBundle.class);
    }

    /**
     * Return the BeanManager.
     *
     * @return the BeanManager.
     */
    public static BeanManager getBeanManager() {
        return getCDIObject(BeanManager.class);
    }

    /**
     * Return the BeanManager.
     *
     * @return the BeanManager.
     */
    public static BeanManager getBeanManagerExt() {
        return new BeanManagerExt(getCDIObject(BeanManager.class));
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

}
