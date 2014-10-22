package org.jbromo.webapp.jsf.sample.view.layer.table.columnheader;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class BromoHeaderDataTableController extends
        AbstractInitDataTableController<DataRow, BromoHeaderDataTableModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 8941579397966046670L;

    /**
     * The component's model.
     */
    @Getter(AccessLevel.PROTECTED)
    @Inject
    private BromoHeaderDataTableModel model;

    @Override
    protected List<DataRow> findAll() {
        try {
            return getService().findAll(getModel().getCriteria(), null,
                    getModel().getOrderBy());
        } catch (final ServiceException e) {
            log.trace(e.getMessage(), e);
            getFacesMessages().message(e);
            return new ArrayList<DataRow>();
        }
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
            @Observes @RenderView(controller = ColumnHeaderDataTableController.class) final RenderViewEvent event) {
        onLoadPage();
    }

}
