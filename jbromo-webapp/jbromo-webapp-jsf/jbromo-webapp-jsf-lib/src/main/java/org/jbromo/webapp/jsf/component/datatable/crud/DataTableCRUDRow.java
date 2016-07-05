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

import org.jbromo.webapp.jsf.component.datatable.DataTableActionBarCell;
import org.jbromo.webapp.jsf.component.datatable.DataTableRow;

import lombok.Getter;

/**
 * Define a row for a jbromo datatable.
 * @author qjafcunuas
 * @param <E> the element row type.
 */
public class DataTableCRUDRow<E extends Serializable> extends DataTableRow<E> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -5238556148720831711L;

    /**
     * Define the buttons bar.
     */
    @Getter
    private final DataTableActionBarCell buttons = new DataTableActionBarCell();

    /**
     * The readonly value.
     */
    @Getter
    private boolean readonly = true;

    /**
     * True if row contains a non persisted element.
     */
    @Getter
    private boolean newRow = false;

    /**
     * True if row has been deleted.
     */
    @Getter
    private boolean deleted = false;

    /**
     * Default constructor.
     */
    public DataTableCRUDRow() {
        super();
        updateRendered();
    }

    /**
     * Set row as readonly.
     * @param readonly the value to set.
     */
    public void setReadonly(final boolean readonly) {
        this.readonly = readonly;
        updateRendered();
    }

    /**
     * Set row as new row.
     * @param newRow the value to set.
     */
    public void setNewRow(final boolean newRow) {
        this.newRow = newRow;
        updateRendered();
    }

    /**
     * Set row as deleted.
     * @param deleted the value to set.
     */
    public void setDeleted(final boolean deleted) {
        this.deleted = deleted;
        updateRendered();
    }

    /**
     * Update rendered buttons flag according to dependent values.
     */
    private void updateRendered() {
        getButtons().getEditButton().setRendered(!this.deleted && this.readonly);
        getButtons().getDeleteButton().setRendered(!this.deleted && (this.readonly || this.newRow));
        getButtons().getSaveButton().setRendered(!this.deleted && !this.readonly);
        getButtons().getCancelButton().setRendered(!this.deleted && !this.readonly && !this.newRow);
    }

}
