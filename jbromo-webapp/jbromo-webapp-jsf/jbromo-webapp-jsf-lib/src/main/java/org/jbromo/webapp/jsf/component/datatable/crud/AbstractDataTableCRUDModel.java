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
package org.jbromo.webapp.jsf.component.datatable.crud;

import java.io.Serializable;

import org.jbromo.webapp.jsf.component.datatable.AbstractDataTableModel;
import org.jbromo.webapp.jsf.component.datatable.DataTableRow;

/**
 * Define a dataTable model for a bromo datatable.
 * @author qjafcunuas
 * @param <R> the row element type.
 */
public abstract class AbstractDataTableCRUDModel<R extends Serializable> extends AbstractDataTableModel<R> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 5715398404985210109L;

    @Override
    @SuppressWarnings("unchecked")
    protected Class<DataTableRow<R>> getRowClass() {
        return (Class<DataTableRow<R>>) (Class<?>) DataTableCRUDRow.class;
    }

    @Override
    public DataTableCRUDRow<R> getRowClicked() {
        return (DataTableCRUDRow<R>) super.getRowClicked();
    }

    @Override
    protected DataTableCRUDRow<R> newRowInstance() {
        return (DataTableCRUDRow<R>) super.newRowInstance();
    }

    @Override
    protected R newElementInstance() {
        return super.newElementInstance();
    }

    @Override
    protected DataTableCRUDRow<R> createRow(final R element) {
        return (DataTableCRUDRow<R>) super.createRow(element);
    }

}
