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
package org.jbromo.webapp.jsf.component.menu;

import javax.enterprise.event.Observes;

import org.jbromo.webapp.jsf.mvc.component.AbstractComponentController;
import org.jbromo.webapp.jsf.view.RenderViewEvent;

/**
 * Define abstract menu controller implementation.
 *
 * @author qjafcunuas
 * @param <M>
 *            the model type.
 */
public abstract class AbstractMenuController<M extends AbstractMenuModel>
        extends AbstractComponentController<M> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -2283695919825253165L;

    /**
     * Observer when new view will be rendered.
     *
     * @param event
     *            the event.
     */
    public void observeRenderView(@Observes final RenderViewEvent event) {
        getModel().setCurrentView(event.getView());
    }

}
