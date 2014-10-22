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
package org.jbromo.webapp.jsf.faces.composite.jsf;

import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.event.AjaxBehaviorEvent;

import org.jbromo.webapp.jsf.faces.ELUtil;
import org.jbromo.webapp.jsf.faces.composite.util.UINamingContainerApp;
import org.richfaces.component.UIColumn;

/**
 * Define UIDataScroller composite.
 *
 * @author qjafcunuas
 *
 */
@FacesComponent(value = "org.jbromo.webapp.jsf.faces.composite.jsf.UIColumnHeader")
public class UIColumnHeader extends UINamingContainerApp {

    /**
     * SortBy string constant.
     */
    private static final String SORT_BY = "sortBy";

    /**
     * filterValue string constant.
     */
    private static final String FILTER_VALUE = "filterValue";

    /**
     * Return the value expression of columnHeader sortBy attribute.
     *
     * @return the value expression.
     */
    private String getHeaderSortBy() {
        return getAttribute(SORT_BY);
    }

    /**
     * Return the value expression of the columnHeader filterValue attribute.
     *
     * @return the value expression.
     */
    private ValueExpression getHeaderFilterValue() {
        return getValueExpression(FILTER_VALUE);
    }

    /**
     * Return the value expression of the rich column sortBy attribute.
     *
     * @return the value expression.
     */
    private ValueExpression getRichSortBy() {
        final UIColumn column = getUIColumn();
        if (column == null) {
            return null;
        }
        return column.getValueExpression(SORT_BY);
    }

    /**
     * Return the value expression of the rich column filterValue attribute.
     *
     * @return the value expression.
     */
    private ValueExpression getRichFilterValue() {
        final UIColumn column = getUIColumn();
        if (column == null) {
            return null;
        }
        return getUIColumn().getValueExpression(FILTER_VALUE);
    }

    /**
     * Return true if rich:column component parent has sortBy attribute.
     *
     * @return true/false
     */
    public boolean isRichSort() {
        return getRichSortBy() != null;
    }

    /**
     * Return true if rich:column component parent has sortBy attribute.
     *
     * @return true/false
     */
    public boolean isRichFilter() {
        return getRichFilterValue() != null;
    }

    /**
     * Return true if the header is sortable.
     *
     * @return true/false
     */
    public boolean isSortable() {
        return getSortBy() != null;
    }

    /**
     * Return true if the header is filterable.
     *
     * @return true/false
     */
    public boolean isFilterable() {
        return getFilterValue() != null;
    }

    /**
     * Return the sort attribute.
     *
     * @return the value.
     */
    private String getSortBy() {
        // Try richfaces sort.
        final ValueExpression ve = getRichSortBy();
        if (ve != null) {
            return ve.getExpressionString().replace("#", "").replace("{", "")
                    .replace("}", "").trim();
        }
        // Try with our sort
        return getHeaderSortBy();
    }

    /**
     * Create the value expression according to the filter value.
     *
     * @return the value expression.
     */
    private ValueExpression getFilterValue() {
        if (isRichFilter()) {
            return getRichFilterValue();
        } else {
            return getHeaderFilterValue();
        }
    }

    /**
     * Call when user wants to change order table.
     */
    public void sortListener() {
        getDataTable().getModel().getSortColumns().setOrderBy(getSortBy());
        if (isRichSort()) {
            getUIColumn().setValueExpression("sortOrder",
                    createRichSortOrderExpression());
        } else {
            getDataTable().getController().onSort();
        }
    }

    /**
     * Call when user wants to filter table.
     *
     * @param event
     *            the event.
     */
    public void filterListener(final AjaxBehaviorEvent event) {
        if (isRichFilter()) {
            // Nothing to do, richfaces do it.
            return;
        }
        getDataTable().getController().onFilter();
    }

    /**
     * Create the value expression according to the filter value.
     *
     * @return the value expression.
     */
    private ValueExpression createRichSortOrderExpression() {
        return ELUtil
                .createValueExpression("#{cc.attrs.model.sortColumns.getSortOrder('"
                        + getSortBy() + "')}");
    }

    /**
     * Get InputText value.
     *
     * @return the value.
     */
    public Object getInputTextValue() {
        final ValueExpression ve = getFilterValue();
        if (ve != null) {
            return ve.getValue(getFacesContext().getELContext());
        } else {
            return null;
        }
    }

    /**
     * Set the input text value.
     *
     * @param value
     *            the value to set.
     */
    public void setInputTextValue(final Object value) {
        final ValueExpression ve = getFilterValue();
        if (ve != null) {
            ve.setValue(getFacesContext().getELContext(), value);
        }
    }

    /**
     * Return the framework parent DataTable.
     *
     * @return table.
     */
    private UIDataTable getDataTable() {
        return getParent(UIDataTable.class);
    }

    /**
     * Return the UIColumn parent component.
     *
     * @return the UIColumn
     */
    private UIColumn getUIColumn() {
        return getParent(UIColumn.class);
    }

    /**
     * Return the arrow to display.
     *
     * @return the arrow to display.
     */
    public String getArrows() {
        final UIDataTable dataTable = getDataTable();
        if (dataTable.getModel().getSortColumns() != null) {
            return dataTable.getModel().getSortColumns().getArrows(getSortBy());
        }
        return null;
    }
}
