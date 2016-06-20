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
package org.jbromo.webapp.jsf.sample.view.layer.table.crud;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.service.Service;
import org.jbromo.webapp.jsf.component.datatable.crud.AbstractDataTableCRUDController;
import org.jbromo.webapp.jsf.sample.view.layer.service.DataRow;
import org.jbromo.webapp.jsf.sample.view.layer.service.IDataRowService;
import org.jbromo.webapp.jsf.view.RenderView;
import org.jbromo.webapp.jsf.view.RenderViewEvent;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Define the CRUD table controller.
 * @author qjafcunuas
 */
@Named
@RequestScoped
@Slf4j
public class CrudDataTableController extends AbstractDataTableCRUDController<DataRow, CrudDataTableModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 8941579397966046670L;

    /**
     * The component's model.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private CrudDataTableModel model;

    /**
     * The DataRow service.
     */
    @Inject
    @Service
    @Getter(AccessLevel.PROTECTED)
    private IDataRowService service;

    @Override
    protected List<DataRow> findAll() throws MessageLabelException {
        return getService().findAll(getModel().getCriteria(), null, getModel().getOrderBy());
    }

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
     * Call when view will be rendered.
     * @param event the event.
     */
    public void observeRenderView(@Observes @RenderView(controller = CrudDataTableController.class) final RenderViewEvent event) {
        onLoadPage();
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
