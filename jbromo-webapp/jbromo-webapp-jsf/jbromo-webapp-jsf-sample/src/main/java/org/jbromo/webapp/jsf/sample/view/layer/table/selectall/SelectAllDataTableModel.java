package org.jbromo.webapp.jsf.sample.view.layer.table.selectall;

import javax.inject.Named;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.jbromo.common.dto.IOrderBy;
import org.jbromo.common.dto.IOrderBy.SORT;
import org.jbromo.webapp.jsf.component.datatable.AbstractDataTableModel;
import org.jbromo.webapp.jsf.component.datatable.DataTableSortMultiColumn;
import org.jbromo.webapp.jsf.sample.view.layer.service.DataRow;
import org.jbromo.webapp.jsf.sample.view.layer.service.DataRowOrderBy;

/**
 * Define the simple table model.
 * 
 * @author qjafcunuas
 * 
 */
@Named
@ViewAccessScoped
public class SelectAllDataTableModel extends AbstractDataTableModel<DataRow> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -7947750742835660437L;

    /**
     * Default constructor.
     */
    public SelectAllDataTableModel() {
        super();
        setSortColumns(new DataTableSortMultiColumn());
    }

    @Override
    protected IOrderBy<DataRow> getOrderBy(final String columnRef,
            final SORT sort) {
        return DataRowOrderBy.getOrderBy(columnRef, sort);
    }

}
