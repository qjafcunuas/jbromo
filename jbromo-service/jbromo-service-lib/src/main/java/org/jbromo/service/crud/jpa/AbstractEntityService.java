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
package org.jbromo.service.crud.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.jbromo.common.cdi.annotation.Transactional;
import org.jbromo.common.cdi.annotation.Transactional.TxType;
import org.jbromo.common.dto.IOrderBy;
import org.jbromo.common.i18n.MessageKey;
import org.jbromo.dao.common.exception.DaoException;
import org.jbromo.dao.jpa.IEntityDao;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.service.crud.AbstractCrudService;
import org.jbromo.service.exception.ServiceException;
import org.jbromo.service.exception.ServiceExceptionFactory;

/**
 * The class for implementation of CRUD services.
 * @author qjafcunuas
 * @param <E> the entity class giving by Service layer.
 * @param
 *            <P>
 *            the entity class primary key.
 */
public abstract class AbstractEntityService<E extends IEntity<P>, P extends Serializable> extends AbstractCrudService<E, P>
    implements IEntityService<E, P> {

    /**
     * Serial version UID for class.
     */
    private static final long serialVersionUID = -4053211327121284550L;

    /**
     * The getter for entityDao.
     * @return the entityDao to get
     */
    protected abstract IEntityDao<E, P> getEntityDao();

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Transactional(TxType.REQUIRED)
    public E save(final E entity) throws ServiceException {
        if (entity.getPrimaryKey() == null) {
            return create(entity);
        } else {
            return update(entity);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Transactional(TxType.REQUIRED)
    public E create(final E entity) throws ServiceException {
        if (entity == null) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.ENTITY_TO_CREATE_IS_NULL);
        }

        try {
            return getEntityDao().create(entity);
        } catch (final DaoException e) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.DEFAULT_MESSAGE, e);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Transactional(TxType.SUPPORTS)
    public E read(final E entity) throws ServiceException {
        if (entity == null || entity.getPrimaryKey() == null) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.ENTITY_PK_MUST_BE_NOT_NULL);
        }
        try {
            return getEntityDao().findByPk(entity.getPrimaryKey());
        } catch (final DaoException e) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.DEFAULT_MESSAGE, e);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Transactional(TxType.SUPPORTS)
    public <C extends Collection<E>> C read(final C transientInstance) throws ServiceException {
        try {
            return getEntityDao().read(transientInstance);
        } catch (final DaoException e) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.DEFAULT_MESSAGE, e);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Transactional(TxType.REQUIRED)
    public E update(final E entity) throws ServiceException {
        if (entity == null) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.ENTITY_TO_UPDATE_IS_NULL);
        }

        try {
            return getEntityDao().update(entity);
        } catch (final DaoException e) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.DEFAULT_MESSAGE, e);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Transactional(TxType.REQUIRED)
    public boolean delete(final E entity) throws ServiceException {
        if (entity == null || entity.getPrimaryKey() == null) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.ENTITY_PK_MUST_BE_NOT_NULL);
        }
        try {
            return getEntityDao().delete(entity);
        } catch (final DaoException e) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.DEFAULT_MESSAGE, e);
        }
    }

    @Override
    public List<E> findAll(final E criteria, final E eagerLoading, final List<IOrderBy> orderBy) throws ServiceException {
        try {
            return getEntityDao().findAll(criteria, eagerLoading, orderBy);
        } catch (final DaoException e) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.DEFAULT_MESSAGE, e);
        }
    }

    @Override
    public List<E> findAll(final E criteria) throws ServiceException {
        try {
            return getEntityDao().findAll(criteria);
        } catch (final DaoException e) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.DEFAULT_MESSAGE, e);
        }
    }

    @Override
    public List<E> findAll() throws ServiceException {
        try {
            return getEntityDao().findAll();
        } catch (final DaoException e) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.DEFAULT_MESSAGE, e);
        }
    }

    @Override
    public E findByPk(final P primaryKey) throws ServiceException {
        try {
            return getEntityDao().findByPk(primaryKey);
        } catch (final DaoException e) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.DEFAULT_MESSAGE, e);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Transactional(TxType.REQUIRED)
    public <C extends Collection<E>> C create(final C transientInstance) throws ServiceException {
        try {
            return getEntityDao().create(transientInstance);
        } catch (final DaoException e) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.DEFAULT_MESSAGE, e);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Transactional(TxType.REQUIRED)
    public <C extends Collection<E>> C update(final C detachedInstance) throws ServiceException {
        try {
            return getEntityDao().update(detachedInstance);
        } catch (final DaoException e) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.DEFAULT_MESSAGE, e);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Transactional(TxType.REQUIRED)
    public void delete(final Collection<E> detachedInstance) throws ServiceException {
        try {
            getEntityDao().delete(detachedInstance);
        } catch (final DaoException e) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.DEFAULT_MESSAGE, e);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Transactional(TxType.REQUIRED)
    public <C extends Collection<E>> C save(final C detachedInstance) throws ServiceException {
        try {
            return getEntityDao().save(detachedInstance);
        } catch (final DaoException e) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.DEFAULT_MESSAGE, e);
        }
    }

    @Override
    public Collection<E> findAllByPk(final Collection<P> primaryKeys) throws ServiceException {
        try {
            return getEntityDao().findAllByPk(primaryKeys);
        } catch (final DaoException e) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.DEFAULT_MESSAGE, e);
        }
    }

    @Override
    public E findByPk(final P primaryKey, final E eagerLoading) throws ServiceException {
        try {
            return getEntityDao().findByPk(primaryKey, eagerLoading);
        } catch (final DaoException e) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.DEFAULT_MESSAGE, e);
        }
    }

    @Override
    public Collection<E> findAllByPk(final Collection<P> primaryKeys, final E eagerLoading) throws ServiceException {
        try {
            return getEntityDao().findAllByPk(primaryKeys, eagerLoading);
        } catch (final DaoException e) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.DEFAULT_MESSAGE, e);
        }
    }

    @Override
    public Long count() throws ServiceException {
        try {
            return getEntityDao().count();
        } catch (final DaoException e) {
            throw ServiceExceptionFactory.getInstance().newInstance(MessageKey.DEFAULT_MESSAGE, e);
        }
    }

}
