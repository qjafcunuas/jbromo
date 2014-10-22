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
package org.jbromo.dao.jpa.query.jpql;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.CollectionUtil;
import org.jbromo.common.StringUtil;
import org.jbromo.common.i18n.MessageKey;
import org.jbromo.common.i18n.MessageLabel;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.dao.common.exception.DaoException;
import org.jbromo.dao.common.exception.DaoExceptionFactory;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.util.EntityUtil;

/**
 * JPQL Query builder.
 *
 * @author qjafcunuas
 *
 * @param <E>
 *            the entity type.
 */
@Slf4j
public class JpqlEntityQueryBuilder<E extends IEntity<?>> extends
        AbstractJpqlQueryBuilder<E> {

    /**
     * The eager loading object.
     */
    @Getter(AccessLevel.PRIVATE)
    private final E eagerLoading;

    /**
     * Map alias with object.
     */
    @Getter(AccessLevel.PRIVATE)
    private final Map<Object, String> aliasMapper = new HashMap<Object, String>();

    /**
     * Map object with alias.
     */
    @Getter(AccessLevel.PRIVATE)
    private final Map<String, Object> eagerMapper = new HashMap<String, Object>();

    /**
     * Default constructor.
     *
     * @param entityManager
     *            the entityManager to used.
     * @param entityClass
     *            the entityClass to used.
     * @param eagerLoading
     *            the eager loading object.
     * @throws DaoException
     *             exception.
     */
    public JpqlEntityQueryBuilder(final EntityManager entityManager,
            final Class<E> entityClass, final E eagerLoading)
            throws DaoException {
        super(entityManager, entityClass);
        this.eagerLoading = eagerLoading;
        loadEagerLoading();
    }

    /**
     * Default constructor.
     *
     * @param entityManager
     *            the entityManager to used.
     * @param entityClass
     *            the entityClass to used.
     * @throws DaoException
     *             exception.
     */
    public JpqlEntityQueryBuilder(final EntityManager entityManager,
            final Class<E> entityClass) throws DaoException {
        super(entityManager, entityClass);
        this.eagerLoading = null;
    }

    /**
     * Return an alias associated with an object.
     *
     * @param object
     *            the object.
     * @return the alias.
     */
    public String getAlias(final Object object) {
        return getAliasMapper().get(object);
    }

    /**
     * Return an alias associated with an object.
     *
     * @param alias
     *            the alias.
     * @return the eager loading object.
     */
    public Object getEager(final String alias) {
        return getEagerMapper().get(alias);
    }

    /**
     * Set an alias for an object.
     *
     * @param object
     *            the object.
     * @param alias
     *            the alias to set.
     */
    private void setAlias(final Object object, final String alias) {
        getAliasMapper().put(object, alias);
        getEagerMapper().put(alias, object);
    }

    /**
     * Load the eager loading object.
     *
     * @throws DaoException
     *             exception.
     */
    private void loadEagerLoading() throws DaoException {
        try {
            if (getEagerLoading() == null) {
                throw DaoExceptionFactory.getInstance().newInstance(
                        new MessageLabel(MessageKey.DEFAULT_MESSAGE,
                                "Eager loading object cannot be null"));
            }
            setAlias(getEagerLoading(), getFrom().getRootAlias());

            for (final Field field : EntityUtil
                    .getPersistedFields(getModelClass())) {
                loadEagerLoading(getFrom().getRootAlias(), field,
                        getEagerLoading());
            }
        } catch (final Exception e) {
            log.error("Cannot instantiate object " + this.eagerLoading, e);
            throw DaoExceptionFactory.getInstance().newInstance(
                    MessageKey.DEFAULT_MESSAGE, e);
        }
    }

    /**
     * Fetch the eager loading object.
     *
     * @param parentAlias
     *            the alias of the parent.
     * @param field
     *            the field of the parent.
     * @param parent
     *            the parent value.
     * @throws InvocationException
     *             exception.
     */
    private void loadEagerLoading(final String parentAlias, final Field field,
            final Object parent) throws InvocationException {
        String alias;
        if (EntityUtil.isEntity(field.getType())) {
            final Object value = InvocationUtil.getValue(parent, field);
            if (value == null) {
                return;
            }
            alias = getAlias(value);
            if (alias == null) {
                // Entity is already define in another relation ship.
                // For example, in a composite pk.
                // Entity a = new Entity()
                // a.getChildren().add(new ChildEntity(a,new Entity2());
                // Entity a exist twice, so we don't want recursively call.
                alias = getFrom().leftJoinFetch(parentAlias, field.getName(),
                        false);
                setAlias(value, alias);
                for (final Field childfield : EntityUtil
                        .getPersistedFields(value.getClass())) {
                    loadEagerLoading(alias, childfield, value);
                }
            }
        } else if (EntityUtil.isEmbedded(field)
                || EntityUtil.isEmbeddedId(field)) {
            final Object value = InvocationUtil.getValue(parent, field);
            if (value == null) {
                return;
            }
            alias = parentAlias + StringUtil.DOT + field.getName();
            for (final Field embeddedField : EntityUtil
                    .getPersistedFields(field)) {
                loadEagerLoading(alias, embeddedField, value);
            }
        } else if (EntityUtil.isOneToMany(field)
                || EntityUtil.isManyToMany(field)) {
            final Object value = InvocationUtil.getValue(parent, field);
            if (value == null) {
                return;
            }
            final Collection<?> col = (Collection<?>) value;
            if (!CollectionUtil.isEmpty(col)) {
                final Object child = col.iterator().next();
                alias = getFrom().leftJoinFetch(parentAlias, field.getName(),
                        true);
                setAlias(child, alias);
                for (final Field childfield : EntityUtil
                        .getPersistedFields(child.getClass())) {
                    loadEagerLoading(alias, childfield, child);
                }
            }
        }
    }

}
