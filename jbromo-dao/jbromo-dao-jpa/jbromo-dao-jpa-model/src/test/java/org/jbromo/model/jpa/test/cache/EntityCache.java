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
package org.jbromo.model.jpa.test.cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.common.CollectionUtil;
import org.jbromo.model.common.test.cache.CacheData;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.util.EntityUtil;

/**
 * Define Entity collector, during JUnit test.
 *
 * @author qjafcunuas
 *
 */
public final class EntityCache {

    /**
     * The singleton instance.
     */
    private static final EntityCache INSTANCE = new EntityCache();

    /**
     * The data cache.
     */
    @Getter(AccessLevel.PRIVATE)
    private final CacheData cache = new CacheData() {

        @Override
        @SuppressWarnings("unchecked")
        protected Class<?> getClass(final Class<?> theClass) {
            final Class<IEntity<?>> entityClass = (Class<IEntity<?>>) theClass;
            return EntityUtil.getClass(entityClass);
        }

    };

    /**
     * Default constructor.
     */
    private EntityCache() {
        super();
    }

    /**
     * Return the singleton instance.
     *
     * @return the instance.
     */
    public static EntityCache getInstance() {
        return INSTANCE;
    }

    /**
     * Add an entity into the collector.
     *
     * @param <E>
     *            the entity type.
     * @param <PK>
     *            the primary key type.
     * @param entity
     *            the entity to add.
     */
    public <E extends IEntity<PK>, PK extends Serializable> void add(
            final E entity) {
        getCache().add(entity);
    }

    /**
     * Add or updates an object in the cache. If the object is null, return
     * null. If the object exists in the cache, update it and return false. If
     * the object doesn't exist in the cache, add it and return true.
     *
     * @param <E>
     *            the object type.
     * @param object
     *            the object to add.
     * @return true/false.
     */
    public <E> Boolean addOrUpdate(final E object) {
        return getCache().addOrUpdate(object);
    }

    /**
     * Return entities from the current space cache.
     *
     * @param <E>
     *            the entity type.
     * @param <PK>
     *            the primary key type.
     * @param entityClass
     *            the entity class to return.
     * @return entities.
     */
    public <E extends IEntity<PK>, PK extends Serializable> List<E> getEntities(
            final Class<E> entityClass) {
        return getCache().getObjects(entityClass);
    }

    /**
     * Add entities into the collector.
     *
     * @param <E>
     *            the entity type.
     * @param <PK>
     *            the primary key type.
     * @param entities
     *            the entity to add.
     */
    public <E extends IEntity<PK>, PK extends Serializable> void add(
            final Collection<E> entities) {
        if (CollectionUtil.isEmpty(entities)) {
            return;
        }
        getCache().add(entities);
    }

    /**
     * Update an entity into the collector.
     *
     * @param <E>
     *            the entity type.
     * @param <PK>
     *            the primary key type.
     * @param entity
     *            the entity to update.
     */
    public <E extends IEntity<PK>, PK extends Serializable> void update(
            final E entity) {
        getCache().update(entity);
    }

    /**
     * Update entities into the collector.
     *
     * @param <E>
     *            the entity type.
     * @param <PK>
     *            the primary key type.
     * @param entities
     *            the entities to update.
     */
    public <E extends IEntity<PK>, PK extends Serializable> void update(
            final Collection<E> entities) {
        getCache().update(entities);
    }

    /**
     * Remove an entity into the collector.
     *
     * @param <E>
     *            the entity type.
     * @param <PK>
     *            the primary key type.
     * @param entity
     *            the entity to remove.
     */
    public <E extends IEntity<PK>, PK extends Serializable> void remove(
            final E entity) {
        getCache().remove(entity);
    }

    /**
     * Remove entities into the collector.
     *
     * @param <E>
     *            the entity type.
     * @param <PK>
     *            the primary key type.
     * @param entities
     *            the entities to remove.
     */
    public <E extends IEntity<PK>, PK extends Serializable> void remove(
            final Collection<E> entities) {
        getCache().remove(entities);
    }

    /**
     * Return all created entities, sorted by creation date.
     *
     * @return entities.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<IEntity<?>> getSorted() {
        return (List) getCache().getSorted();
    }

    /**
     * Clear cache.
     */
    public void clear() {
        getCache().clear();
    }

}
