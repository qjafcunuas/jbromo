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
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.ThrowableUtil;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.test.cache.EntityCache;

/**
 * Define an object that can be used to terminate all created entities during
 * test.
 *
 * @author qjafcunuas
 *
 */
@Slf4j
public abstract class AbstractEntityTerminator {

    /**
     * The entity manager to used.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private EntityManager entityManager;

    /**
     * Return the entity cache.
     *
     * @return the entity cache.
     */
    protected EntityCache getEntityCache() {
        return EntityCache.getInstance();
    }

    /**
     * Delete an entity.
     *
     * @param <E>
     *            the entity type.
     * @param <PK>
     *            the entity primary key type.
     * @param entity
     *            the entity to delete.
     * @throws Exception
     *             exception.
     */
    @SuppressWarnings("unchecked")
    protected <E extends IEntity<PK>, PK extends Serializable> void delete(
            final E entity) throws Exception {
        if (entity != null) {
            final E ref = (E) this.entityManager.getReference(
                    entity.getClass(), entity.getPrimaryKey());
            this.entityManager.remove(ref);
            this.entityManager.flush();
            this.entityManager.clear();
            getEntityCache().remove(entity);
        }
    }

    /**
     * Delete entities from persistent storage that already present into the
     * collector.
     *
     */
    public void deleteAll() {
        try {
            int count = IntegerUtil.INT_10;
            List<IEntity<?>> entities = getEntityCache().getSorted();
            log.info("Cleaning  cache ({} entities)", entities.size());
            Collections.reverse(entities);
            while (!entities.isEmpty() && count-- > IntegerUtil.INT_0) {
                for (final IEntity<?> entity : entities) {
                    try {
                        delete(entity);
                    } catch (final Exception e) {
                        if (count == IntegerUtil.INT_2) {
                            log.warn(
                                    "Cannot delete entity {} from persistent storage; try once later.",
                                    entity);
                        } else if (count == IntegerUtil.INT_1) {
                            log.warn(
                                    "Cannot delete entity {} from persistent storage. \n{}",
                                    entity, ThrowableUtil.getStackTrace(e));
                        }
                    }
                }
                entities = getEntityCache().getSorted();
                Collections.reverse(entities);
            }
        } finally {
            getEntityCache().clear();
        }
    }

}
