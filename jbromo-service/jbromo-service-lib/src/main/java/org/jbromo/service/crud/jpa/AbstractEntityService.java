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
import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.dao.jpa.IEntityDao;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.service.crud.AbstractCrudService;

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
    public E save(final E entity) throws MessageLabelException {
        if (entity.getPrimaryKey() == null) {
            return create(entity);
        } else {
            return update(entity);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Transactional(TxType.REQUIRED)
    public E create(final E entity) throws MessageLabelException {
        return getEntityDao().create(entity);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Transactional(TxType.SUPPORTS)
    public E read(final E entity) throws MessageLabelException {
        return getEntityDao().findByPk(entity.getPrimaryKey());
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Transactional(TxType.SUPPORTS)
    public <C extends Collection<E>> C read(final C transientInstance) throws MessageLabelException {
        return getEntityDao().read(transientInstance);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Transactional(TxType.REQUIRED)
    public E update(final E entity) throws MessageLabelException {
        return getEntityDao().update(entity);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Transactional(TxType.REQUIRED)
    public boolean delete(final E entity) throws MessageLabelException {
        return getEntityDao().delete(entity);
    }

    @Override
    public List<E> findAll(final E criteria, final E eagerLoading, final List<IOrderBy> orderBy) throws MessageLabelException {
        return getEntityDao().findAll(criteria, eagerLoading, orderBy);
    }

    @Override
    public List<E> findAll(final E criteria) throws MessageLabelException {
        return getEntityDao().findAll(criteria);
    }

    @Override
    public List<E> findAll() throws MessageLabelException {
        return getEntityDao().findAll();
    }

    @Override
    public E findByPk(final P primaryKey) throws MessageLabelException {
        return getEntityDao().findByPk(primaryKey);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Transactional(TxType.REQUIRED)
    public <C extends Collection<E>> C create(final C transientInstance) throws MessageLabelException {
        return getEntityDao().create(transientInstance);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Transactional(TxType.REQUIRED)
    public <C extends Collection<E>> C update(final C detachedInstance) throws MessageLabelException {
        return getEntityDao().update(detachedInstance);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Transactional(TxType.REQUIRED)
    public void delete(final Collection<E> detachedInstance) throws MessageLabelException {
        getEntityDao().delete(detachedInstance);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Transactional(TxType.REQUIRED)
    public <C extends Collection<E>> C save(final C detachedInstance) throws MessageLabelException {
        return getEntityDao().save(detachedInstance);
    }

    @Override
    public Collection<E> findAllByPk(final Collection<P> primaryKeys) throws MessageLabelException {
        return getEntityDao().findAllByPk(primaryKeys);
    }

    @Override
    public E findByPk(final P primaryKey, final E eagerLoading) throws MessageLabelException {
        return getEntityDao().findByPk(primaryKey, eagerLoading);
    }

    @Override
    public Collection<E> findAllByPk(final Collection<P> primaryKeys, final E eagerLoading) throws MessageLabelException {
        return getEntityDao().findAllByPk(primaryKeys, eagerLoading);
    }

    @Override
    public Long count() throws MessageLabelException {
        return getEntityDao().count();
    }

}
