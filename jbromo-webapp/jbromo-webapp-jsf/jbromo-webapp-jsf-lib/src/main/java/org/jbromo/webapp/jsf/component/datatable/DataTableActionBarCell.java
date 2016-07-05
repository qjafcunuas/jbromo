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
package org.jbromo.webapp.jsf.component.datatable;

import org.jbromo.webapp.jsf.component.model.ButtonBarModel;
import org.jbromo.webapp.jsf.component.model.ButtonModel;

import lombok.Getter;

/**
 * Define action bar for datatable row.
 * @author qjafcunuas
 */
public class DataTableActionBarCell extends ButtonBarModel {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -2263069604384424284L;

    /**
     * Button for cancel action row.
     */
    @Getter
    private final ButtonModel cancelButton = createButton(false, false, "#{cc.attrs.controller.onCancel}");

    /**
     * Button for save action row.
     */
    @Getter
    private final ButtonModel saveButton = createButton(false, false, "#{cc.attrs.controller.onSave}");

    /**
     * Button for edit action row.
     */
    @Getter
    private final ButtonModel editButton = createButton(true, false, "#{cc.attrs.controller.onEdit}");

    /**
     * Button for delete action row.
     */
    @Getter
    private final ButtonModel deleteButton = createButton(true, false, "#{cc.attrs.controller.onDelete}");

    /**
     * Default constructor.
     */
    public DataTableActionBarCell() {
        super(true, false);
    }

    /**
     * Create a button.
     * @param rendered true if button is rendered.
     * @param disabled true if button is disabled.
     * @param action the button's action.
     * @return the new button.
     */
    private ButtonModel createButton(final boolean rendered, final boolean disabled, final String action) {
        final ButtonModel button = addButton(rendered, disabled);
        button.setAction(action);
        return button;
    }

}
