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
package org.jbromo.dao.test.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.common.invocation.ParameterizedTypeUtil;
import org.jbromo.dao.jpa.IEntityDao;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.testutil.cache.EntityCache;
import org.jbromo.model.jpa.util.EntityUtil;

/**
 * Decorate DAO for getting create/update/delete entities, so that those
 * entities can be deleted at the end of the test.
 *
 * @author qjafcunuas
 *
 */
@Decorator
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class EntityDaoDecoratorWeld implements IEntityDao {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 7108382367395141322L;

    /**
     * Return the entity cache.
     *
     * @return the entity cache.
     */
    EntityCache getEntityCache() {
        return EntityCache.getInstance();
    }

    /**
     * The entity dao to used.
     */
    @Inject
    @Any
    @Delegate
    private IEntityDao entityDao;

    /**
     * Return the entity dao to used.
     *
     * @return the dao.
     */
    private IEntityDao getEntityDao() {
        return this.entityDao;
    }

    @Override
    public Serializable create(final Serializable transientInstance)
            throws MessageLabelException {
        final Serializable entity = getEntityDao().create(transientInstance);
        getEntityCache().add((IEntity<Serializable>) entity);
        return entity;
    }

    @Override
    public Collection create(final Collection transientInstance)
            throws MessageLabelException {
        final Collection col = getEntityDao().create(transientInstance);
        getEntityCache().add(col);
        return col;
    }

    @Override
    public Serializable update(final Serializable detachedInstance)
            throws MessageLabelException {
        final Serializable entity = getEntityDao().update(detachedInstance);
        getEntityCache().update((IEntity<Serializable>) entity);
        return entity;
    }

    @Override
    public Collection update(final Collection detachedInstance)
            throws MessageLabelException {
        final Collection col = getEntityDao().update(detachedInstance);
        getEntityCache().update(col);
        return col;
    }

    @Override
    public boolean delete(final Serializable detachedInstance)
            throws MessageLabelException {
        final boolean value = getEntityDao().delete(detachedInstance);
        getEntityCache().remove((IEntity<Serializable>) detachedInstance);
        return value;
    }

    @Override
    public void delete(final Collection detachedInstance)
            throws MessageLabelException {
        getEntityDao().delete(detachedInstance);
        getEntityCache().remove(detachedInstance);
    }

    @Override
    public Serializable save(final Serializable detachedInstance)
            throws MessageLabelException {
        final boolean nullPk = EntityUtil
                .isNullPk((IEntity<Serializable>) detachedInstance);
        final Serializable entity = getEntityDao().save(detachedInstance);
        if (nullPk) {
            getEntityCache().add((IEntity<Serializable>) entity);
        } else {
            getEntityCache().update((IEntity<Serializable>) entity);
        }
        return entity;
    }

    @Override
    public Collection save(final Collection detachedInstance)
            throws MessageLabelException {
        final Collection saved = getEntityDao().create(detachedInstance);
        final Class<IEntity<Serializable>> entityClass = ParameterizedTypeUtil
                .getClass(getEntityDao(), 0);
        final List<IEntity<Serializable>> collected = getEntityCache()
                .getEntities(entityClass);
        for (final Object entity : saved) {
            if (!collected.contains(entity)) {
                getEntityCache().add((IEntity<Serializable>) entity);
            } else {
                getEntityCache().update((IEntity<Serializable>) entity);
            }
        }
        return saved;
    }

}
