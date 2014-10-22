package org.jbromo.webapp.jsf.sample.view.layer.service;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.jbromo.common.i18n.MessageKey;
import org.jbromo.service.Service;
import org.jbromo.service.crud.memory.AbstractMemoryService;
import org.jbromo.service.exception.ServiceException;
import org.jbromo.service.exception.ServiceExceptionFactory;

/**
 * Define the DataRow service implementation.
 *
 * @author qjafcunuas
 *
 */
@Service
@Singleton
public class DataRowService extends AbstractMemoryService<DataRow> implements
        IDataRowService {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -4246453388518207122L;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DataRow create(final DataRow model) throws ServiceException {
        if (model.getPrimaryKey() != null) {
            throw ServiceExceptionFactory.getInstance().newInstance(
                    MessageKey.ENTITY_TO_CREATE_IS_NULL);
        }
        final DataRow one = new DataRow();
        int pk = getMap().size() + 1;
        one.setPrimaryKey(pk);
        while (getMap().containsValue(one)) {
            pk++;
            one.setPrimaryKey(pk);
        }
        // Set primary key.
        model.setPrimaryKey(pk);
        return super.create(model);
    }

}
