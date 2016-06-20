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
package org.jbromo.dao.jpa.query.jpql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jbromo.common.ListUtil;
import org.jbromo.dao.jpa.query.AbstractQueryBuilder;
import org.jbromo.dao.jpa.query.jpql.from.JpqlFromBuilder;
import org.jbromo.dao.jpa.query.jpql.orderby.JpqlOrderByBuilder;
import org.jbromo.dao.jpa.query.jpql.select.JpqlSelectBuilder;
import org.jbromo.dao.jpa.query.jpql.where.JpqlWhereBuilder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * JPQL Query builder.
 * @author qjafcunuas
 * @param <M> the model type.
 */
@Slf4j
public class AbstractJpqlQueryBuilder<M extends Serializable> extends AbstractQueryBuilder<M> {

    /**
     * The From JPQL query.
     */
    @Getter(AccessLevel.PUBLIC)
    private final JpqlFromBuilder from;

    /**
     * The Select JPQL query.
     */
    @Getter(AccessLevel.PROTECTED)
    private final JpqlSelectBuilder select;

    /**
     * The Where JPQL query.
     */
    @Getter(AccessLevel.PUBLIC)
    private final JpqlWhereBuilder where;

    /**
     * The OrderBy JPQL query.
     */
    @Getter(AccessLevel.PUBLIC)
    private final JpqlOrderByBuilder orderBy;

    /**
     * Default constructor.
     * @param entityManager the entityManager to used.
     * @param modelClass the modelClass to used.
     */
    public AbstractJpqlQueryBuilder(final EntityManager entityManager, final Class<M> modelClass) {
        super(entityManager, modelClass);
        this.from = new JpqlFromBuilder(getModelClass());
        this.select = new JpqlSelectBuilder(this.from.getRootAlias(), true);
        this.where = new JpqlWhereBuilder(this);
        this.orderBy = new JpqlOrderByBuilder();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        getSelect().build(builder);
        getFrom().build(builder);
        getWhere().build(builder, ListUtil.toList());
        getOrderBy().build(builder);
        return builder.toString();
    }

    /**
     * Return query result list.
     * @return the result list.
     */
    public List<M> getResultList() {
        final TypedQuery<M> query = getQuery();
        return query.getResultList();
    }

    /**
     * Return the JPQL query.
     * @return the query.
     */
    private TypedQuery<M> getQuery() {
        // Build JPQL query.
        final StringBuilder builder = new StringBuilder();
        final List<Object> parameters = new ArrayList<Object>();
        getSelect().build(builder);
        getFrom().build(builder);
        getWhere().build(builder, parameters);
        getOrderBy().build(builder);

        // / Build JPA query.
        final TypedQuery<M> query = getEntityManager().createQuery(builder.toString(), getModelClass());
        bindParameters(query, parameters);
        return query;
    }

    /**
     * Execute query for a single result. If no query result, return null.
     * @return the single result.
     */
    public M getSingleResult() {
        try {
            final TypedQuery<M> query = getQuery();
            return query.getSingleResult();
        } catch (final NoResultException e) {
            log.trace("No single result");
            return null;
        }
    }

    /**
     * Binds parameters to query.
     * @param query the query string.
     * @param parameters parameters
     */
    private void bindParameters(final Query query, final List<Object> parameters) {
        for (int index = 0; index < parameters.size(); index++) {
            // Parameters start to 1 : ?1 ?2 ?3 ...
            query.setParameter(index + 1, parameters.get(index));
        }
    }

}
