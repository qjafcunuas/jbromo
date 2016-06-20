/*-
 * Copyright (C) 2013-2014 The JBromo Authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to ding without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit personsdeal
 * in the Software without restriction, inclu to whom the Software is
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
package org.jbromo.webapp.jsf.sample.view.layer.table.columnheader;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.webapp.jsf.mvc.view.AbstractViewController;
import org.jbromo.webapp.jsf.view.RenderView;
import org.jbromo.webapp.jsf.view.RenderViewEvent;

/**
 * Define the table controller with Bromo sort/filter.
 * @author qjafcunuas
 */
@Named
@RequestScoped
public class ColumnHeaderDataTableController extends AbstractViewController<ColumnHeaderDataTableModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 8941579397966046670L;

    /**
     * The component's model.
     */
    @Getter(AccessLevel.PROTECTED)
    @Inject
    private ColumnHeaderDataTableModel model;

    @Override
    protected void loadData() {
        // Nothing to do
    }

    /**
     * Call when view will be rendered.
     * @param event the event.
     */
    public void observeRenderView(@Observes @RenderView(controller = ColumnHeaderDataTableController.class) final RenderViewEvent event) {
        onLoadPage();
    }

}
