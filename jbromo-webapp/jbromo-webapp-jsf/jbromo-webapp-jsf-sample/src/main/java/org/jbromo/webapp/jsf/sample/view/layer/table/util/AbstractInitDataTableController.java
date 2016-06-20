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
package org.jbromo.webapp.jsf.sample.view.layer.table.util;

import java.io.Serializable;

import javax.inject.Inject;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.service.Service;
import org.jbromo.webapp.jsf.component.datatable.AbstractDataTableController;
import org.jbromo.webapp.jsf.component.datatable.AbstractDataTableModel;
import org.jbromo.webapp.jsf.sample.view.layer.service.DataRow;
import org.jbromo.webapp.jsf.sample.view.layer.service.IDataRowService;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Define default implementation for DataTable controller.
 * @author qjafcunuas
 * @param <E> the row element type.
 * @param <M> the view model type.
 */
@Slf4j
public abstract class AbstractInitDataTableController<E extends Serializable, M extends AbstractDataTableModel<E>>
    extends AbstractDataTableController<E, M> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -7853950683816268517L;

    /**
     * The DataRow service.
     */
    @Getter(AccessLevel.PROTECTED)
    @Inject
    @Service
    private IDataRowService service;

    @Override
    protected void loadData() {
        try {
            init();
            loadElements();
        } catch (final MessageLabelException e) {
            log.trace(e.getMessage(), e);
            getFacesMessages().message(e);
        }
    }

    /**
     * Initialize data.
     * @throws MessageLabelException exception.
     */
    private void init() throws MessageLabelException {
        if (getService().findAll().isEmpty()) {
            DataRow row;
            final int max = RandomUtil.nextInt(IntegerUtil.INT_24, IntegerUtil.INT_256);
            for (int i = 0; i < max; i++) {
                row = new DataRow();
                row.setName(RandomUtil.nextString(DataRow.NAME_SIZE_MAX));
                row.setDescription(RandomUtil.nextString(DataRow.DESCRIPTION_SIZE_MAX));
                row = getService().create(row);
            }
        }

    }
}
