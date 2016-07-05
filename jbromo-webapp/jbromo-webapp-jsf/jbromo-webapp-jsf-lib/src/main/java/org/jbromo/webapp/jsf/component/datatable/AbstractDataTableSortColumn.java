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

import org.jbromo.common.CollectionUtil;
import org.jbromo.common.dto.IOrderBy.SORT;
import org.jbromo.common.i18n.MessageKey;
import org.jbromo.webapp.jsf.cdi.CDIFacesUtil;
import org.richfaces.component.SortOrder;

/**
 * Define list order.
 * @author qjafcunuas
 */
public abstract class AbstractDataTableSortColumn implements IDataTableSortColumn {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 7251288301625445550L;

    @Override
    public SortOrder getSortOrder(final String columnName) {
        final SORT order = getSort(columnName);
        if (order != null) {
            switch (order) {
                case ASCENDING:
                    return SortOrder.ascending;
                case DESCENDING:
                    return SortOrder.descending;
                default:
                    return SortOrder.unsorted;
            }
        }
        return SortOrder.unsorted;
    }

    @Override
    public boolean isAscending(final String columnRef) {
        return SORT.ASCENDING.equals(getSort(columnRef));
    }

    @Override
    public boolean isDescending(final String columnRef) {
        return SORT.DESCENDING.equals(getSort(columnRef));
    }

    @Override
    public String getArrows(final String columnRef) {
        final SORT sort = getSort(columnRef);
        final String arrow = getArrow(sort);
        final int indexOf = CollectionUtil.indexOf(getColumnRefs(), columnRef) + 1;
        final StringBuilder arrows = new StringBuilder();
        for (int i = 0; i < indexOf; i++) {
            arrows.append(arrow);
        }
        return arrows.toString();
    }

    /**
     * Return the arrow according to a sort value.
     * @param sort the sort value.
     * @return the arrow.
     */
    protected String getArrow(final SORT sort) {
        if (sort == null) {
            return "";
        } else {
            switch (sort) {
                case ASCENDING:
                    return CDIFacesUtil.getELMessage().formatKey(MessageKey.ARROW_UP);
                case DESCENDING:
                    return CDIFacesUtil.getELMessage().formatKey(MessageKey.ARROW_DOWN);
                case UNSORTED:
                    return CDIFacesUtil.getELMessage().formatKey(MessageKey.ARROW_UP_AND_DOWN);
                default:
                    return "";
            }
        }
    }

    /**
     * Set next order.
     * @param prev the previous order.
     * @return the next order.
     */
    protected SORT nextOrder(final SORT prev) {
        if (prev == null) {
            return SORT.ASCENDING;
        } else {
            switch (prev) {
                case ASCENDING:
                    return SORT.DESCENDING;
                case DESCENDING:
                    return SORT.UNSORTED;
                default:
                    return SORT.ASCENDING;
            }
        }
    }

}
