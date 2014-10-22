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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jbromo.common.dto.IOrderBy.SORT;
import org.richfaces.model.SortMode;

/**
 * Define multiple list order.
 *
 * @author qjafcunuas
 *
 */
public class DataTableSortMultiColumn extends AbstractDataTableSortColumn {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 7251288301625445550L;

    /**
     * The current sorted column ids.
     */
    private final Map<String, SORT> columnIds = new LinkedHashMap<String, SORT>();

    @Override
    public void setOrderBy(final String columnId) {
        if (!this.columnIds.containsKey(columnId)) {
            this.columnIds.put(columnId, SORT.ASCENDING);
        } else {
            final SORT next = nextOrder(this.columnIds.get(columnId));
            if (isLast(columnId) && SORT.UNSORTED.equals(next)) {
                this.columnIds.remove(columnId);
            } else {
                this.columnIds.put(columnId, next);
            }
        }
    }

    /**
     * Return true if the column id is at the end of the map.
     *
     * @param columnId
     *            the column id.
     * @return true/false.
     */
    private boolean isLast(final String columnId) {
        final Iterator<String> iter = this.columnIds.keySet().iterator();
        String last = null;
        while (iter.hasNext()) {
            last = iter.next();
        }
        return columnId.equals(last);
    }

    @Override
    public SORT getSort(final String columnId) {
        if (this.columnIds.containsKey(columnId)) {
            return this.columnIds.get(columnId);
        } else {
            return SORT.UNSORTED;
        }
    }

    @Override
    public Collection<String> getColumnRefs() {
        return this.columnIds.keySet();
    }

    @Override
    public SortMode getSortMode() {
        return SortMode.multi;
    }
}
