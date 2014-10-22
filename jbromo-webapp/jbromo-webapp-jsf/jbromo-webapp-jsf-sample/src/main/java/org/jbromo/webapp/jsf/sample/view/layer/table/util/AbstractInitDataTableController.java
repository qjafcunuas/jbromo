package org.jbromo.webapp.jsf.sample.view.layer.table.util;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.service.Service;
import org.jbromo.service.exception.ServiceException;
import org.jbromo.webapp.jsf.component.datatable.AbstractDataTableController;
import org.jbromo.webapp.jsf.component.datatable.AbstractDataTableModel;
import org.jbromo.webapp.jsf.sample.view.layer.service.DataRow;
import org.jbromo.webapp.jsf.sample.view.layer.service.IDataRowService;

/**
 * Define default implementation for DataTable controller.
 *
 * @author qjafcunuas
 *
 * @param <RE>
 *            the row element type.
 * @param <M>
 *            the view model type.
 */
@Slf4j
public abstract class AbstractInitDataTableController<RE extends Serializable, M extends AbstractDataTableModel<RE>>
        extends AbstractDataTableController<RE, M> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -7853950683816268517L;
    /**
     * The DataRow service.
     */
    @Getter(AccessLevel.PROTECTED)
    @Inject
    @Service
    private IDataRowService service;

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
