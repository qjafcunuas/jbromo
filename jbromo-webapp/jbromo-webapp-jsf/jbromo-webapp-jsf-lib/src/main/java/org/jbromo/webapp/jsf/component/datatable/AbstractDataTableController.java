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
package org.jbromo.webapp.jsf.component.datatable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewController;

/**
 * Define a dataTable model for a bromo datatable.
 * 
 * @author qjafcunuas
 * 
 * @param <R>
 *            the row element type.
 * @param <M>
 *            the view model type.
 */
@Slf4j
public abstract class AbstractDataTableController<R extends Serializable, M extends AbstractDataTableModel<R>>
        extends AbstractViewController<M> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 1540787188962648087L;

    /**
     * Find all elements from the service.
     *
     * @return elements.
     * @throws MessageLabelException
     *             exception.
     */
    protected abstract List<R> findAll() throws MessageLabelException;

    /**
     * Load elements into the model. The model's rows is cleared before added
     * elements.
     *
     * @throws MessageLabelException
     *             exception.
     */
    public void loadElements() throws MessageLabelException {
        getModel().getRows().clear();
        final List<R> elements = findAll();
        loadElements(elements);
    }

    /**
     * Call when user wants to filter elements.
     */
    public void onFilter() {
        try {
            loadElements();
        } catch (final MessageLabelException e) {
            log.trace(e.getMessage(), e);
            getFacesMessages().message(e);
        }
    }

    /**
     * Call when user wants to sort elements.
     */
    public void onSort() {
        try {
            loadElements();
        } catch (final MessageLabelException e) {
            log.trace(e.getMessage(), e);
            getFacesMessages().message(e);
        }
    }

    /**
     * Call when user clicks on a row.
     *
     * @return the next view id.
     */
    public abstract String onRowClick();

    /**
     * Load elements into the model. The model's rows is not cleared before
     * added elements.
     *
     * @param elements
     *            the elements to add.
     */
    protected void loadElements(final Collection<R> elements) {
        DataTableRow<R> row;
        for (final R element : elements) {
            row = getModel().createRow(element);
            getModel().getRows().add(row);
        }
    }

    /**
     * Remove a row.
     *
     * @param row
     *            the row to remove.
     */
    protected void removeRow(final DataTableRow<R> row) {
        getModel().getRows().remove(row);
    }

    /**
     * Remove an element.
     *
     * @param element
     *            the element to remove.
     */
    protected void removeElement(final R element) {
        DataTableRow<R> toRemove = null;
        for (final DataTableRow<R> row : getModel().getRows()) {
            if (row.getElement().equals(element)) {
                toRemove = row;
            }
        }
        if (toRemove != null) {
            removeRow(toRemove);
        }
    }

}
