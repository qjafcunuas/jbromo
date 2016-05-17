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
package org.jbromo.webapp.jsf.sample.view.layer.table.columnheader;

import javax.inject.Named;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.jbromo.common.dto.IOrderBy;
import org.jbromo.common.dto.IOrderBy.SORT;
import org.jbromo.webapp.jsf.component.datatable.AbstractDataTableModel;
import org.jbromo.webapp.jsf.component.datatable.DataTableSortMultiColumn;
import org.jbromo.webapp.jsf.sample.view.layer.service.DataRow;
import org.jbromo.webapp.jsf.sample.view.layer.service.DataRowOrderBy;

/**
 * Define the table model with Bromo sort/filter.
 * @author qjafcunuas
 */
@Named
@ViewAccessScoped
public class BromoHeaderDataTableModel extends AbstractDataTableModel<DataRow> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -7947750742835660437L;

    /**
     * Default constructor.
     */
    public BromoHeaderDataTableModel() {
        super();
        setSortColumns(new DataTableSortMultiColumn());
    }

    @Override
    protected IOrderBy getOrderBy(final String columnRef, final SORT sort) {
        return DataRowOrderBy.getOrderBy(columnRef, sort);
    }

}
