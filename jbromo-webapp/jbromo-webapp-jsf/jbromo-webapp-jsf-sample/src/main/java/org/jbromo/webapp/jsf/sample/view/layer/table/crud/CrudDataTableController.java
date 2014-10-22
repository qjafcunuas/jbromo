package org.jbromo.webapp.jsf.sample.view.layer.table.crud;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.service.Service;
import org.jbromo.service.exception.ServiceException;
import org.jbromo.webapp.jsf.component.datatable.crud.AbstractDataTableCRUDController;
import org.jbromo.webapp.jsf.sample.view.layer.service.DataRow;
import org.jbromo.webapp.jsf.sample.view.layer.service.IDataRowService;
import org.jbromo.webapp.jsf.view.RenderView;
import org.jbromo.webapp.jsf.view.RenderViewEvent;

/**
 * Define the CRUD table controller.
 *
 * @author qjafcunuas
 *
 */
@Named
@RequestScoped
@Slf4j
public class CrudDataTableController extends
        AbstractDataTableCRUDController<DataRow, CrudDataTableModel> {

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
    protected List<DataRow> findAll() throws ServiceException {
        return getService().findAll(getModel().getCriteria(), null,
                getModel().getOrderBy());
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
     *
     * @param event
     *            the event.
     */
    public void observeRenderView(
            @Observes @RenderView(controller = CrudDataTableController.class) final RenderViewEvent event) {
        onLoadPage();
    }

    /**
     * Initialize data.
     *
     * @throws ServiceException
     *             exception.
     */
    private void init() throws ServiceException {
        if (getService().findAll().isEmpty()) {
            DataRow row;
            final int max = RandomUtil.nextInt(IntegerUtil.INT_24,
                    IntegerUtil.INT_256);
            for (int i = 0; i < max; i++) {
                row = new DataRow();
                row.setName(RandomUtil.nextString(DataRow.NAME_SIZE_MAX));
                row.setDescription(RandomUtil
                        .nextString(DataRow.DESCRIPTION_SIZE_MAX));
                row = getService().create(row);
            }
        }

    }

}
