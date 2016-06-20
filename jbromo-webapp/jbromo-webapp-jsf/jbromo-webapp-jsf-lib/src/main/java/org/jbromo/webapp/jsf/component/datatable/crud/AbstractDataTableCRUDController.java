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
import java.util.List;

import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.service.crud.ICRUDService;
import org.jbromo.webapp.jsf.component.datatable.AbstractDataTableController;
import org.jbromo.webapp.jsf.component.datatable.DataTableRow;

import lombok.extern.slf4j.Slf4j;

/**
 * Define a dataTable model for a bromo datatable.
 * @author qjafcunuas
 * @param <R> the row element type.
 * @param <M> the view model type.
 */
@Slf4j
public abstract class AbstractDataTableCRUDController<R extends Serializable, M extends AbstractDataTableCRUDModel<R>>
    extends AbstractDataTableController<R, M> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 1540787188962648088L;

    /**
     * The CRUD service.
     * @return the CRUD service.
     */
    protected abstract ICRUDService<R, ?> getService();

    /**
     * Create the clicked element by calling the service.
     * @param element the element to save.
     * @return element.
     * @throws MessageLabelException exception.
     */
    protected R create(final R element) throws MessageLabelException {
        return getService().create(element);
    }

    /**
     * Read clicked element from the service.
     * @param element the element to read.
     * @return element.
     * @throws MessageLabelException exception.
     */
    protected R read(final R element) throws MessageLabelException {
        return getService().read(element);
    }

    /**
     * Update the clicked element by calling the service.
     * @param element the element to save.
     * @return element.
     * @throws MessageLabelException exception.
     */
    protected R update(final R element) throws MessageLabelException {
        return getService().update(element);
    }

    /**
     * Save an element by calling the service.
     * @param element the element to save.
     * @return element.
     * @throws MessageLabelException exception.
     */
    protected R save(final R element) throws MessageLabelException {
        return getService().save(element);
    }

    /**
     * Delete the clicked elements by calling the service.
     * @param element the element to delete.
     * @throws MessageLabelException exception.
     */
    protected void delete(final R element) throws MessageLabelException {
        getService().delete(element);
    }

    @Override
    protected List<R> findAll() throws MessageLabelException {
        return getService().findAll();
    }

    /**
     * Load clicked element into the model.
     * @throws MessageLabelException exception.
     */
    protected void loadElement() throws MessageLabelException {
        final R element = read(getModel().getRowClicked().getElement());
        loadElement(element);
    }

    /**
     * Load an element into the model.
     * @param element the element to load.
     */
    protected void loadElement(final R element) {
        boolean found = false;
        for (final DataTableRow<R> row : getModel().getRows()) {
            if (row.getElement().equals(element)) {
                // updated element.
                row.setElement(element);
                found = true;
            }
        }
        if (!found) {
            // new created element.
            getModel().getRows().add(getModel().createRow(element));
        }
    }

    /**
     * Call when user wants to create an element.
     * @return next view id.
     */
    public String onNew() {
        final DataTableCRUDRow<R> row = getModel().createRow(getModel().newElementInstance());
        getModel().getRows().add(0, row);
        getModel().setRowClicked(row);
        row.setNewRow(true);
        row.setReadonly(false);
        return "";
    }

    /**
     * Call when user wants to edit clicked elements.
     * @param row the row to edit.
     * @return next view id.
     */
    public String onEdit(final DataTableCRUDRow<R> row) {
        try {
            getModel().setRowClicked(row);
            loadElement();
            getModel().getRowClicked().setReadonly(false);
        } catch (final MessageLabelException e) {
            log.trace(e.getMessage(), e);
            getFacesMessages().message(e);
        }
        return "";
    }

    /**
     * Call when user wants to delete clicked element.
     * @param row the row to edit.
     * @return next view id.
     */
    public String onDelete(final DataTableCRUDRow<R> row) {
        try {
            getModel().setRowClicked(row);
            if (!row.isNewRow()) {
                delete(row.getElement());
            } else {
                row.setReadonly(true);
            }
            row.setDeleted(true);
        } catch (final MessageLabelException e) {
            log.trace(e.getMessage(), e);
            getFacesMessages().message(e);
        }
        return "";
    }

    /**
     * Call when user wants to cancel current clicked changed.
     * @param row the row to edit.
     * @return next view id.
     */
    public String onCancel(final DataTableCRUDRow<R> row) {
        try {
            getModel().setRowClicked(row);
            loadElement();
            getModel().getRowClicked().setReadonly(true);
        } catch (final MessageLabelException e) {
            log.trace(e.getMessage(), e);
            getFacesMessages().message(e);
        }
        return "";
    }

    /**
     * Call when user wants to save clicked element.
     * @param row the row to edit.
     * @return next view id.
     */
    public String onSave(final DataTableCRUDRow<R> row) {
        try {
            getModel().setRowClicked(row);
            final R saved = save(getModel().getRowClicked().getElement());
            getModel().getRowClicked().setElement(saved);
            loadElement();
            getModel().getRowClicked().setReadonly(true);
        } catch (final MessageLabelException e) {
            log.trace(e.getMessage(), e);
            getFacesMessages().message(e);
        }
        return "";
    }

    @Override
    public String onRowClick() {
        // Not used.
        return "";
    }

}
