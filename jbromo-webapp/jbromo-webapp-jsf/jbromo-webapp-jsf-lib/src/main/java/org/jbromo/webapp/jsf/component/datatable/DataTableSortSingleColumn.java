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

import java.util.ArrayList;
import java.util.Collection;

import org.jbromo.common.StringUtil;
import org.jbromo.common.dto.IOrderBy.SORT;
import org.jbromo.common.i18n.MessageKey;
import org.jbromo.webapp.jsf.cdi.CDIFacesUtil;
import org.richfaces.model.SortMode;

/**
 * Define list order.
 * @author qjafcunuas
 */
public class DataTableSortSingleColumn extends AbstractDataTableSortColumn {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 7251288301625445550L;

    /**
     * The current sorted column name.
     */
    private String columnName;

    /**
     * The current sort order of the column.
     */
    private SORT order = null;

    @Override
    public void setOrderBy(final String columnName) {
        if (this.columnName != null && this.columnName.equals(columnName)) {
            nextOrder();
        } else {
            this.columnName = columnName;
            this.order = SORT.ASCENDING;
        }
    }

    @Override
    public SORT getSort(final String columnName) {
        if (isSorted(columnName)) {
            return this.order;
        } else {
            return SORT.UNSORTED;
        }
    }

    /**
     * Return true if column is ordered.
     * @param columnName the column name.
     * @return true/false.
     */
    private boolean isSorted(final String columnName) {
        return this.columnName != null && this.columnName.equals(columnName);
    }

    /**
     * Set next order.
     */
    private void nextOrder() {
        this.order = nextOrder(this.order);
    }

    @Override
    public Collection<String> getColumnRefs() {
        final Collection<String> collection = new ArrayList<>(1);
        if (StringUtil.isNotEmpty(this.columnName)) {
            collection.add(this.columnName);
        }
        return collection;
    }

    @Override
    public SortMode getSortMode() {
        return SortMode.single;
    }

    @Override
    protected String getArrow(final SORT sort) {
        if (sort == null) {
            return "";
        } else {
            switch (sort) {
                case ASCENDING:
                    return CDIFacesUtil.getELMessage().formatKey(MessageKey.ARROW_UP);
                case DESCENDING:
                    return CDIFacesUtil.getELMessage().formatKey(MessageKey.ARROW_DOWN);
                default:
                    return "";
            }
        }
    }
}
