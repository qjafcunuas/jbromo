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
package org.jbromo.webapp.jsf.mvc.util;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Define default controller implementation.
 *
 * @author qjafcunuas
 *
 * @param <M>
 *            the model type.
 */
@Slf4j
public abstract class AbstractOnLoadController<M extends AbstractOnLoadModel>
        extends AbstractFacesController<M> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -3486564511771064001L;

    /**
     * The faces context.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private FacesContext facesContext;

    /**
     * Method used for initialize the model once, according to it context
     * (session, request...).
     */
    protected abstract void loadData();

    /**
     * Method used for loading page.
     */
    public void onLoadPage() {
        if (!getModel().isPageLoaded()) {
            log.debug("Loading data for {} controller", getClass().getName());
            loadData();
            getModel().setPageLoaded(true);
            log.trace("Data for {} controller loaded", getClass().getName());
        }
    }

}
