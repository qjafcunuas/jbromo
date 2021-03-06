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
package org.jbromo.dao.jpa;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.jbromo.common.CollectionUtil;
import org.jbromo.common.ListUtil;
import org.jbromo.common.MapUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.SetUtil;
import org.jbromo.common.StringUtil;
import org.jbromo.common.dto.IOrderBy;
import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.dao.common.exception.DataPersistException;
import org.jbromo.dao.common.exception.NullDataException;
import org.jbromo.dao.common.exception.NullPrimaryKeyException;
import org.jbromo.dao.common.exception.TooMuchDataException;
import org.jbromo.dao.common.exception.ValidationException;
import org.jbromo.dao.jpa.container.common.JpaProviderFactory;
import org.jbromo.dao.jpa.query.jpql.JpqlEntityQueryBuilder;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.util.EntityUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Defines the abstract DAO for entity. TODO JEE7 : remove TransactionAttribute (replace by javax.transaction.Transaction)
 * @author qjafcunuas
 * @param <E> the entity type
 * @param <P> the primary key type.
 */
@Slf4j
public abstract class AbstractEntityDao<E extends IEntity<P>, P extends Serializable> extends AbstractJpaDao<E> implements IEntityDao<E, P> {

    /**
     * Serial version number.
     */
    private static final long serialVersionUID = -1432967965544719525L;

    /**
     * validator to use in tests.
     */
    private static Validator validator;

    /**
     * Default constructor.
     */
    public AbstractEntityDao() {
        super();
        if (validator == null) {
            initValidator();
        }
    }

    /**
     * Initialize validator.
     */
    private static synchronized void initValidator() {
        if (validator == null) {
            validator = Validation.buildDefaultValidatorFactory().getValidator();
        }
    }

    /**
     * Execute query for a single result. If no query result, return null.
     * @param query the query to execute.
     * @return the entity.
     */
    protected E getSingleResult(final TypedQuery<E> query) {
        try {
            return query.getSingleResult();
        } catch (final javax.persistence.NoResultException e) {
            log.trace("No single result", e);
            return null;
        }
    }

    /**
     * Create an instance of Query for executing a JPQL query language.
     * @param query the JPQL query.
     * @param parameters the query parameters.
     * @return the new query instance.
     */
    protected TypedQuery<E> createTypedQuery(final String query, final Object... parameters) {
        final TypedQuery<E> tquery = getEntityManager().createQuery(query, getModelClass());
        int i = 1;
        for (final Object param : parameters) {
            tquery.setParameter(i++, param);
        }
        return tquery;
    }

    /**
     * Create an instance of Query for executing a named query (in the Java Persistence query language or in native SQL).
     * @param name the name of a query defined in metadata.
     * @return the new query instance.
     */
    protected TypedQuery<E> createTypedNamedQuery(final String name) {
        return getEntityManager().createNamedQuery(name, getModelClass());
    }

    /**
     * Validates entities.
     * @param entities entities to validate
     * @throws MessageLabelException on exception.
     */
    protected void validateEntities(final Collection<IEntity<Serializable>> entities) throws MessageLabelException {
        for (final IEntity<Serializable> entity : entities) {
            validateEntity(entity);
        }
    }

    /**
     * Validates entity.
     * @param entity entity to validate
     * @throws MessageLabelException on exception.
     */
    protected void validateEntity(final IEntity<Serializable> entity) throws MessageLabelException {
        final Set<ConstraintViolation<IEntity<Serializable>>> constraints = validator.validate(entity);

        if (!constraints.isEmpty()) {
            final StringBuilder message = new StringBuilder();
            for (final ConstraintViolation<IEntity<Serializable>> violation : constraints) {
                message.append("Error on field ");
                message.append(violation.getPropertyPath());
                message.append(" of entity ");
                message.append(violation.getRootBean().getClass().getName());
                message.append(" : ");
                message.append(violation.getMessage());
                message.append(". Field's value is ");
                final Object invalidValue = violation.getInvalidValue();
                if (invalidValue != null) {
                    message.append(invalidValue.toString());
                } else {
                    message.append(" null ");
                }
                message.append(". ");
            }
            log.warn(message.toString());
            throw new ValidationException(message.toString());
        }
        for (final Field field : EntityUtil.getPersistedFields(entity.getClass())) {
            if (EntityUtil.isOneToMany(field)) {
                try {
                    final Collection<IEntity<Serializable>> entities = InvocationUtil.getValue(entity, field);
                    validateEntities(entities);
                } catch (final InvocationException e) {
                    throw new DataPersistException(e);
                }
            }
        }
    }

    @Override
    @Transactional(value = TxType.SUPPORTS)
    public E read(final E transientInstance) throws MessageLabelException {
        log.trace("Read entity {}", transientInstance);
        if (transientInstance == null || transientInstance.getPrimaryKey() == null) {
            throw new NullPrimaryKeyException();
        }
        return findByPk(transientInstance.getPrimaryKey());
    }

    @Override
    @Transactional(TxType.SUPPORTS)
    public E findByPk(final P pk) throws MessageLabelException {
        log.trace("Get entity {} for pk {}", getModelClass().getName(), pk);
        if (pk == null) {
            throw new NullPrimaryKeyException();
        }
        final E entity = getEntityManager().find(getModelClass(), pk);
        if (entity == null) {
            log.trace("Object {} is not found for pk {}", getModelClass().getName(), pk);
        } else {
            log.trace("Object {} is found for pk {}", getModelClass().getName(), pk);
        }
        return entity;
    }

    /**
     * Return a query builder.
     * @param eagerLoading the eager loading object.
     * @return the query builder.
     * @throws MessageLabelException exception.
     */
    protected JpqlEntityQueryBuilder<E> getQueryBuilder(final E eagerLoading) throws MessageLabelException {
        if (eagerLoading == null) {
            return new JpqlEntityQueryBuilder<>(getEntityManager(), getModelClass());
        } else {
            return new JpqlEntityQueryBuilder<>(getEntityManager(), getModelClass(), eagerLoading);
        }

    }

    @Override
    @Transactional(TxType.SUPPORTS)
    public E findByPk(final P pk, final E eagerLoading) throws MessageLabelException {
        if (eagerLoading == null) {
            return findByPk(pk);
        }
        if (pk == null) {
            throw new NullPrimaryKeyException();
        }
        log.trace("Get entity {} for pk {}", getModelClass().getName(), pk);
        final JpqlEntityQueryBuilder<E> queryBuilder = getQueryBuilder(eagerLoading);
        queryBuilder.getWhere()
                .equals(queryBuilder.getAlias(eagerLoading) + StringUtil.DOT + EntityUtil.getPrimaryKeyField(getModelClass()).getName(), pk);
        if (queryBuilder.getSingleResult() == null) {
            log.trace("Object {} is not found for pk {}", getModelClass().getName(), pk);
            return null;
        } else {
            log.trace("Object {} is found for pk {}", getModelClass().getName(), pk);
            return getEntityManager().find(getModelClass(), pk);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(TxType.SUPPORTS)
    public Collection<E> findAllByPk(final Collection<P> primaryKeys, final E eagerLoading) throws MessageLabelException {
        if (primaryKeys == null) {
            return null;
        }
        E eager = eagerLoading;
        if (eagerLoading == null) {
            eager = ObjectUtil.newInstance(getModelClass());
        }
        if (CollectionUtil.isEmpty(primaryKeys)) {
            return ObjectUtil.newInstance(primaryKeys.getClass());
        }
        if (primaryKeys.size() >= MAX_ELEMENTS) {
            throw new TooMuchDataException(MAX_ELEMENTS);
        }

        log.trace("Get entities {} for {} pk with eager loading ", getModelClass().getName(), primaryKeys.size());
        final JpqlEntityQueryBuilder<E> queryBuilder = getQueryBuilder(eager);
        queryBuilder.getWhere().in(queryBuilder.getAlias(eager) + StringUtil.DOT + EntityUtil.getPrimaryKeyField(getModelClass()).getName(),
                                   primaryKeys);
        final List<E> entities = queryBuilder.getResultList();
        log.trace("Found {} entities {}", entities.size(), getModelClass().getName());
        if (primaryKeys.getClass().equals(entities.getClass())) {
            return entities;
        } else {
            return CollectionUtil.toCollection(entities, primaryKeys.getClass());
        }
    }

    @Override
    @Transactional(TxType.SUPPORTS)
    public Collection<E> findAllByPk(final Collection<P> primaryKeys) throws MessageLabelException {
        return findAllByPk(primaryKeys, (E) null);
    }

    @Override
    @Transactional(TxType.SUPPORTS)
    public List<E> findAll() throws MessageLabelException {
        log.trace("Find all entities {}", getModelClass().getName());
        final JpqlEntityQueryBuilder<E> queryBuilder = getQueryBuilder(null);
        final List<E> entities = queryBuilder.getResultList();
        log.trace("Found {} elements for entities {}", entities.size(), getModelClass().getName());
        return entities;
    }

    @Override
    @Transactional(TxType.SUPPORTS)
    public List<E> findAll(final E criteria) throws MessageLabelException {
        return findAll(criteria, null, null);
    }

    /**
     * Set order by into query builder.
     * @param queryBuilder the builder.
     * @param orderBy the orderby.
     */
    private void orderBy(final JpqlEntityQueryBuilder<E> queryBuilder, final List<IOrderBy> orderBy) {
        if (ListUtil.isNotEmpty(orderBy)) {
            for (final IOrderBy one : orderBy) {
                if (one.getSort() != null && StringUtil.isNotEmpty(one.getOrder())) {
                    switch (one.getSort()) {
                        case ASCENDING:
                            queryBuilder.getOrderBy().asc(one.getOrder());
                            break;
                        case DESCENDING:
                            queryBuilder.getOrderBy().desc(one.getOrder());
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    @Override
    @Transactional(TxType.SUPPORTS)
    public List<E> findAll(final E criteria, final E eagerLoading, final List<IOrderBy> orderBy) throws MessageLabelException {
        log.trace("Find all entities {}", getModelClass().getName());
        E eager = eagerLoading;
        if (eager == null) {
            eager = ObjectUtil.newInstance(getModelClass());
        }
        final JpqlEntityQueryBuilder<E> queryBuilder = getQueryBuilder(eager);
        queryBuilder.getWhere().and(criteria);
        orderBy(queryBuilder, orderBy);
        final List<E> list = queryBuilder.getResultList();
        log.trace("Found {} elements for entities {}", list.size(), getModelClass().getName());
        return list;
    }

    /**
     * Returns all persisted objects of type, according to a named query.
     * @param namedQuery the named query.
     * @param parameters the parameters'query.
     * @return the list of all objects.
     */
    protected List<E> executeNamedQuery(final String namedQuery, final Object... parameters) {
        log.trace("Find all entities {} for named query {}", getModelClass().getName(), namedQuery);
        final TypedQuery<E> query = createTypedNamedQuery(namedQuery);
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] instanceof Object[]) {
                query.setParameter(i + 1, Arrays.asList((Object[]) parameters[i]));
            } else {
                query.setParameter(i + 1, parameters[i]);
            }
        }
        final List<E> list = query.getResultList();
        if (log.isTraceEnabled()) {
            final Object[] args = new Object[] {list.size(), getModelClass().getName(), namedQuery};
            log.trace("Found {} elements for entities {} and named query {}", args);
        }
        return list;
    }

    /**
     * Returns all persisted objects of type, according to a named query.
     * @param namedQuery the named query.
     * @param parameters the parameters'query.
     * @return the list of all objects.
     */
    protected E executeSingleNamedQuery(final String namedQuery, final Object... parameters) {
        log.trace("Find entity {} for named query {}", getModelClass().getName(), namedQuery);
        final TypedQuery<E> query = createTypedNamedQuery(namedQuery);
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i + 1, parameters[i]);
        }
        return getSingleResult(query);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(TxType.REQUIRED)
    public E create(final E transientInstance) throws MessageLabelException {
        log.trace("Create a new entity {}", getModelClass().getName());
        if (transientInstance == null) {
            log.warn("Try to create a null object !");
            throw new NullDataException();
        }
        validateEntity((IEntity<Serializable>) transientInstance);
        mapsId(transientInstance);
        reSet(transientInstance);
        getEntityManager().persist(transientInstance);
        // rebuild Set fields because they are hashed with null entity pk.
        reSet(transientInstance);
        log.trace("New entity {} created", transientInstance);
        return transientInstance;
    }

    /**
     * Load entities with MapsId annotation on relationship.
     * @param detach the parent's entity.
     */
    private void mapsId(final IEntity<?> detach) {
        final Collection<IEntity<?>> already = SetUtil.toSet();
        mapsId(detach, already);
    }

    /**
     * Load entities with MapsId annotation on relationship, except those already loaded.
     * @param detach the parent's entity.
     * @param already already tested entities.
     */
    @SuppressWarnings("unchecked")
    private void mapsId(final IEntity<?> detach, final Collection<IEntity<?>> already) {
        // no infinite loop.
        if (already.contains(detach)) {
            return;
        }
        already.add(detach);
        Object value;
        Object persistedValue;
        try {
            for (final Field field : EntityUtil.getPersistedFields(detach.getClass())) {
                value = InvocationUtil.getValue(detach, field);
                if (value == null) {
                    continue;
                }
                if (EntityUtil.isMapsId(field)) {
                    if (EntityUtil.isNotNullPk((IEntity<?>) value)) {
                        persistedValue = getEntityManager().find(value.getClass(), EntityUtil.getPrimaryKey((IEntity<?>) value));
                        InvocationUtil.injectValue(detach, field, persistedValue);
                    }
                } else if (EntityUtil.isEntity(field.getType())) {
                    mapsId((IEntity<?>) value, already);
                } else if (EntityUtil.isOneToMany(field)) {
                    for (final IEntity<?> one : (Collection<IEntity<?>>) value) {
                        mapsId(one, already);
                    }
                }
            }
        } catch (final InvocationException e) {
            log.warn(e.getMessage(), e);
        }
    }

    /**
     * Reset object fields.
     * @param entity the entity.
     * @throws MessageLabelException exception.
     */
    private void reSet(final E entity) throws MessageLabelException {
        for (final Field field : EntityUtil.getPersistedFields(entity.getClass())) {
            if (SetUtil.isSet(field.getType())) {
                try {
                    final Set<?> set = (Set<?>) InvocationUtil.getValue(entity, field);
                    set.size();
                    SetUtil.reSet(set);
                } catch (final InvocationException e) {
                    throw new DataPersistException("Cannot reSet field " + field, e);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(TxType.REQUIRED)
    public E update(final E detachedInstance) throws MessageLabelException {
        log.trace("Update one entity {}", getModelClass().getName());
        if (detachedInstance == null) {
            log.warn("Try to update a null object !");
            throw new NullDataException();
        }
        validateEntity((IEntity<Serializable>) detachedInstance);
        mapsId(detachedInstance);
        reSet(detachedInstance);

        // Clear xxxToMany relationship before merging entity, and add their
        // after merged.
        final Map<Field, Collection<IEntity<?>>> map = oneToMany(detachedInstance);
        // Merge the entity.
        final E entity = getEntityManager().merge(detachedInstance);
        // Recollection oneToMany relationship in the entity.
        oneToMany(entity, map);
        oneToMany(detachedInstance, map);

        // Flush and reset Set object.
        getEntityManager().flush();
        reSet(detachedInstance);
        reSet(entity);

        log.trace("Entity {} updated", entity);
        return entity;
    }

    /**
     * Some JPA provider doesn't merge oneToMany relationship automatically. So the process will start to merge entity with empty oneToMany
     * relationship, and then add the previous oneToMany relationship to the merged entity. This method add previous relationship clear to the entity.
     * @param entity the entity to add oneToMany relationship.
     * @param map the map of previous oneToMany relationship to add.
     */
    private void oneToMany(final E entity, final Map<Field, Collection<IEntity<?>>> map) {
        if (JpaProviderFactory.getInstance().getImplementation().isOneToManyAutoMerge()) {
            return;
        }
        getEntityManager().flush();
        Collection<IEntity<?>> col;
        try {
            String mappedBy;
            for (final Entry<Field, Collection<IEntity<?>>> entry : map.entrySet()) {
                col = InvocationUtil.getValue(entity, entry.getKey());
                col.clear();
                // inject parent into collection elements.
                for (final IEntity<?> child : entry.getValue()) {
                    mappedBy = EntityUtil.getOneToManyMappedBy(entry.getKey());
                    InvocationUtil.injectValue(child, InvocationUtil.getField(EntityUtil.getPersistentClass(child), mappedBy), entity);
                }
                col.addAll(entry.getValue());
            }
        } catch (final InvocationException e) {
            // Should not happended.
            log.warn(e.getMessage(), e);
        }
        getEntityManager().flush();
    }

    /**
     * Some JPA provider doesn't merge oneToMany relationship automatically. So the process will start to merge entity with empty oneToMany
     * relationship, and then add the previous oneToMany relationship to the merged entity. This method clear oneToMany relationship.
     * @param entity the entity to clear oneToMany relationship.
     * @return the entity with empty oneToMany relationship.
     */
    private Map<Field, Collection<IEntity<?>>> oneToMany(final E entity) {
        final Map<Field, Collection<IEntity<?>>> map = MapUtil.toMap();
        if (JpaProviderFactory.getInstance().getImplementation().isOneToManyAutoMerge()) {
            return map;
        }
        Collection<IEntity<?>> col;
        try {
            for (final Field field : EntityUtil.getPersistedFields(entity)) {
                if (EntityUtil.isOneToMany(field)) {
                    col = InvocationUtil.getValue(entity, field);
                    map.put(field, ListUtil.toList(col));
                    col.clear();
                }
            }
        } catch (final InvocationException e) {
            // Should not happended.
            log.warn(e.getMessage(), e);
        }
        return map;
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public boolean delete(final E detachedInstance) throws MessageLabelException {
        log.trace("Delete one entity {}", getModelClass().getName());
        if (detachedInstance == null) {
            log.warn("Try to delete a null object !");
            throw new NullDataException();
        }
        final E persistedInstance = findByPk(detachedInstance.getPrimaryKey());
        if (persistedInstance == null) {
            log.trace("Entity {} {} doesn't exist for deletion", getModelClass().getName(), detachedInstance.getPrimaryKey());
            return false;
        }
        getEntityManager().remove(persistedInstance);
        log.trace("Entity {} {} deleted", getModelClass().getName(), persistedInstance.getPrimaryKey());
        return true;
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public void delete(final Collection<E> detachedInstance) throws MessageLabelException {
        if (CollectionUtil.isEmpty(detachedInstance)) {
            log.warn("Try to delete a null or empty objects !");
            throw new NullDataException();
        }
        for (final E entity : detachedInstance) {
            delete(entity);
        }
    }

    @Override
    @Transactional(TxType.SUPPORTS)
    public Long count() throws MessageLabelException {
        log.trace("Count entities {}", getModelClass().getName());
        final Long count = (Long) getEntityManager().createQuery("SELECT COUNT(o) FROM " + getModelClass().getName() + " o").getSingleResult();
        log.trace("Count {} entities {}", count, getModelClass().getName());
        return count;
    }

    /**
     * Create a new instance of an object.
     * @param <C> the collection type.
     * @param transientInstance the object to get a new instance.
     * @return the new instance.
     */
    @SuppressWarnings("unchecked")
    private <C extends Collection<E>> C newInstance(final C transientInstance) {
        return (C) ObjectUtil.newInstance(transientInstance.getClass());
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public <C extends Collection<E>> C create(final C transientInstance) throws MessageLabelException {
        final C created = newInstance(transientInstance);
        for (final E entity : transientInstance) {
            created.add(create(entity));
        }
        return created;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(TxType.SUPPORTS)
    public <C extends Collection<E>> C read(final C detachedInstance) throws MessageLabelException {
        final Collection<P> primaryKeys = EntityUtil.getPrimaryKeys(detachedInstance);
        return (C) findAllByPk(primaryKeys);
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public <C extends Collection<E>> C update(final C detachedInstance) throws MessageLabelException {
        final C updated = newInstance(detachedInstance);
        for (final E entity : detachedInstance) {
            updated.add(update(entity));
        }
        return updated;
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public E save(final E detachedInstance) throws MessageLabelException {
        if (EntityUtil.isNullPk(detachedInstance)) {
            return create(detachedInstance);
        } else {
            return update(detachedInstance);
        }
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public <C extends Collection<E>> C save(final C transientInstance) throws MessageLabelException {
        final C saved = newInstance(transientInstance);
        for (final E one : transientInstance) {
            saved.add(save(one));
        }
        return saved;
    }

    /**
     * Return true if field on entity is loaded.
     * @param <E1> the entity type.
     * @param entity the entity.
     * @param eagerLoading field to verify into entity that are loading or not.
     * @return true/false.
     */
    protected <E1 extends IEntity<?>> boolean isLoaded(final E1 entity, final E1 eagerLoading) {
        return false;
        // TODO
        // final String fieldname = EagerLoadingUtil.getName(field);
        // return
        // getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(entity,
        // fieldname);
    }

    /**
     * Return true if an entity is loaded.
     * @param <E1> the entity type.
     * @param entity the entity.
     * @return true/false.
     */
    protected <E1 extends IEntity<?>> boolean isLoaded(final E1 entity) {
        return getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(entity);
    }

}