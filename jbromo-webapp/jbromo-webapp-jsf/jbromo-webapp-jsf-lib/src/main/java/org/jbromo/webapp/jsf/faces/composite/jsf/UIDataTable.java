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
package org.jbromo.webapp.jsf.faces.composite.jsf;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;

import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.IntegerUtil;
import org.jbromo.webapp.jsf.component.datatable.AbstractDataTableController;
import org.jbromo.webapp.jsf.component.datatable.AbstractDataTableModel;
import org.jbromo.webapp.jsf.faces.composite.util.UINamingContainerApp;

/**
 * Define UIDataScroller composite.
 *
 * @author qjafcunuas
 *
 */
@FacesComponent(value = "org.jbromo.webapp.jsf.faces.composite.jsf.UIDataTable")
@Slf4j
public class UIDataTable extends UINamingContainerApp {

    /**
     * Return the model.
     *
     * @return the model.
     */
    public AbstractDataTableModel<?> getModel() {
        return getAttribute("model");
    }

    /**
     * Return the controller.
     *
     * @return the controller.
     */
    public AbstractDataTableController<?, ?> getController() {
        return getAttribute("controller");
    }

    /**
     * Return the Richtable component.
     *
     * @return the richtable component.
     */
    protected org.richfaces.component.UIDataTable getRichTable() {
        return getParent(getCurrentComponent(getFacesContext()),
                org.richfaces.component.UIDataTable.class);
    }

    /**
     * Return row elements id to be renderer. It is used because richfaces @row
     * doesn't work ... Be careful to set an if of each child element.
     *
     * @return row elements id.
     */
    public String getRenderRowChildren() {
        final StringBuilder builder = new StringBuilder();
        // UIColumn
        for (final UIComponent column : getRichTable().getChildren()) {
            // column's child.
            for (final UIComponent child : column.getChildren()) {
                builder.append(child.getId());
                builder.append(" ");
            }
        }
        if (builder.indexOf("j_idt") >= 0) {
            for (int i = 0; i < IntegerUtil.INT_100; i++) {
                log.warn("Set id on all column's children! ({})", builder);
            }
        }
        return builder.toString();
    }

}
