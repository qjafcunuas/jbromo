package org.jbromo.webapp.jsf.sample.view.layer.table.selectall;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.service.exception.ServiceException;
import org.jbromo.webapp.jsf.sample.view.layer.service.DataRow;
import org.jbromo.webapp.jsf.sample.view.layer.table.util.AbstractInitDataTableController;
import org.jbromo.webapp.jsf.view.RenderView;
import org.jbromo.webapp.jsf.view.RenderViewEvent;

/**
 * Define the table controller with Bromo sort/filter.
 *
 * @author qjafcunuas
 *
 */
@Named
@RequestScoped
public class SelectAllDataTableController extends
        AbstractInitDataTableController<DataRow, SelectAllDataTableModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 8941579397966046670L;

    /**
     * The component's model.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private SelectAllDataTableModel model;

    @Override
    protected List<DataRow> findAll() throws ServiceException {
        return getService().findAll(getModel().getCriteria(), null,
                getModel().getOrderBy());
    }

    @Override
    public String onRowClick() {
        // Nothing to do.
        return null;
    }

    /**
     * Call when view will be rendered.
     *
     * @param event
     *            the event.
     */
    public void observeRenderView(
            @Observes @RenderView(controller = SelectAllDataTableController.class) final RenderViewEvent event) {
        onLoadPage();
    }

}
