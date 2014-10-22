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
package org.jbromo.dao.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.invocation.ParameterizedTypeUtil;
import org.jbromo.dao.common.IDao;

/**
 * Defines the abstract DAO for entity.
 *
 * @author qjafcunuas
 * @param <M>
 *            the model type
 */
@Slf4j
public abstract class AbstractJpaDao<M extends Serializable> implements IDao {

    /**
     * Serial version number.
     */
    private static final long serialVersionUID = -1542961254544719525L;

    /**
     * Define max results elements return by a find query.
     */
    protected static final int MAX_ELEMENTS = IntegerUtil.INT_1000;

    /**
     * The model class used by this DAO.
     */
    @Setter(AccessLevel.PRIVATE)
    private Class<M> modelClass;

    /**
     * Entity manager getter.
     *
     * @return the EntityManager
     */
    protected abstract EntityManager getEntityManager();

    /**
     * Execute query for a single result. If no query result, return null.
     *
     * @param query
     *            the query to execute.
     * @return the model.
     */
    @SuppressWarnings("unchecked")
    protected M getSingleResult(final Query query) {
        try {
            return (M) query.getSingleResult();
        } catch (final javax.persistence.NoResultException e) {
            log.trace("No single result", e);
            return null;
        }
    }

    /**
     * Create an instance of Query for executing a JPQL query language.
     *
     * @param query
     *            the JPQL query.
     * @return the new query instance.
     */
    protected Query createQuery(final String query) {
        return getEntityManager().createQuery(query);
    }

    /**
     * Create an instance of Query for executing a named query (in the Java
     * Persistence query language or in native SQL).
     *
     * @param name
     *            the name of a query defined in metadata.
     * @return the new query instance.
     */
    protected Query createNamedQuery(final String name) {
        return getEntityManager().createNamedQuery(name);
    }

    /**
     * Synchronizes the persistence context with the database.
     */
    protected void flush() {
        getEntityManager().flush();
    }

    /**
     * Deletes the persistence context.<br/>
     * All unsaved modifications will be lost !
     */
    protected void clear() {
        getEntityManager().clear();
    }

    /**
     * Return the model class.
     *
     * @return the model class.
     */
    protected Class<M> getModelClass() {
        if (this.modelClass == null) {
            final Class<M> theClass = loadModelClass();
            setModelClass(theClass);
        }
        return this.modelClass;
    }

    /**
     * Load the model class from generic value.
     *
     * @return the model class.
     */
    protected Class<M> loadModelClass() {
        return ParameterizedTypeUtil.getClass(this, IntegerUtil.INT_0);
    }

}