/*
 * Copyright (C) 2013-2014 The JBromo Authors.
 *import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.ThrowableUtil;
import org.jbromo.common.dto.IOrderBy;
import org.jbromo.common.dto.IOrderBy.SORT;
import org.jbromo.common.invocation.ParameterizedTypeUtil;
import org.jbromo.webapp.jsf.component.model.CheckboxModel;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewModel;
 notice shall be included in
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.ThrowableUtil;
import org.jbromo.common.dto.IOrderBy;
import org.jbromo.common.dto.IOrderBy.SORT;
import org.jbromo.common.invocation.ParameterizedTypeUtil;
import org.jbromo.webapp.jsf.component.model.CheckboxModel;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewModel;

/**
 * Define a dataTable model for a bromo datatable.
 * 
 * @author qjafcunuas
 * 
 * @param <E>
 *            the row element type.
 */
@Slf4j
public abstract class AbstractDataTableModel<E extends Serializable> extends
        AbstractViewModel {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 5715398404985210199L;

    /**
     * Define criteria filter.
     */
    @Setter
    private E criteria;

    /**
     * Define the clicked row.
     */
    @Getter
    @Setter
    private DataTableRow<E> rowClicked;

    /**
     * The order by table state.
     */
    @Getter
    @Setter
    private IDataTableSortColumn sortColumns = new DataTableSortSingleColumn();

    /**
     * Define the row element class.
     */
    private Class<E> elementClass;

    /**
     * The rows.
     */
    @Getter
    private final List<DataTableRow<E>> rows = new ArrayList<DataTableRow<E>>();

    /**
     * Define deleteAll checkbox model.
     */
    @Getter
    private final CheckboxModel selectAllBox = new CheckboxModel(true, false);

    /**
     * Return the row element class.
     *
     * @return the row element class.
     */
    protected Class<E> getElementClass() {
        if (this.elementClass == null) {
            this.elementClass = ParameterizedTypeUtil.getClass(this,
                    IntegerUtil.INT_0);
        }
        return this.elementClass;
    }

    /**
     * Return the criteria.
     *
     * @return the criteria.
     */
    public E getCriteria() {
        if (this.criteria == null) {
            this.criteria = newCriteriaInstance();
        }
        return this.criteria;
    }

    /**
     * Return the row class.
     *
     * @return the row class.
     */
    @SuppressWarnings("unchecked")
    protected Class<DataTableRow<E>> getRowClass() {
        return (Class<DataTableRow<E>>) (Class<?>) DataTableRow.class;
    }

    /**
     * Create a new instance of a row.
     *
     * @return the new instance.
     */
    protected DataTableRow<E> newRowInstance() {
        try {
            return getRowClass().newInstance();
        } catch (final Exception e) {
            log.error("Cannot create new instance of {} {} {}", getRowClass()
                    .getName(), ThrowableUtil.getStackTrace(e));
            return null;
        }
    }

    /**
     * Create a new instance of an element.
     *
     * @return the new instance.
     */
    protected E newElementInstance() {
        try {
            return getElementClass().newInstance();
        } catch (final Exception e) {
            log.error("Cannot create new instance of "
                    + getElementClass().getName(), e);
            return null;
        }
    }

    /**
     * Create a new instance of a criteria.
     *
     * @return the new instance.
     */
    protected E newCriteriaInstance() {
        return newElementInstance();
    }

    /**
     * Create a new instance of a row.
     *
     * @param element
     *            the row's element.
     * @return the new instance.
     */
    protected DataTableRow<E> createRow(final E element) {
        final DataTableRow<E> row = newRowInstance();
        if (row != null) {
            row.getSelectBox().setRendered(getSelectAllBox().isRendered());
            row.setElement(element);
        }
        return row;
    }

    /**
     * Build an order by clause.
     *
     * @param columnRef
     *            the column reference.
     * @param sort
     *            the sort.
     * @return the order by clause.
     */
    protected abstract IOrderBy<E> getOrderBy(final String columnRef,
            final SORT sort);

    /**
     * Return the orders by clause.
     *
     * @return the orders by clause.
     */
    public List<IOrderBy<E>> getOrderBy() {
        final List<IOrderBy<E>> list = new ArrayList<IOrderBy<E>>();
        if (getSortColumns() == null) {
            return list;
        }
        IOrderBy<E> orderBy;
        for (final String columnRef : getSortColumns().getColumnRefs()) {
            orderBy = getOrderBy(columnRef, getSortColumns().getSort(columnRef));
            if (orderBy != null) {
                list.add(getOrderBy(columnRef,
                        getSortColumns().getSort(columnRef)));
            }
        }
        return list;
    }
}
