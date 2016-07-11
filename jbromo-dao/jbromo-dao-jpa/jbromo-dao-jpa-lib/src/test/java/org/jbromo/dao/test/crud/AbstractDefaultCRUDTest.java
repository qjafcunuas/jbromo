/*
 * Copyright (C) 2013-2014 The JBromo Authors. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.jbromo.dao.test.crud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jbromo.common.CollectionUtil;
import org.jbromo.common.crud.ICRUD;
import org.jbromo.common.invocation.ParameterizedTypeUtil;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.testutil.asserts.AbstractEntityAssert;
import org.jbromo.model.jpa.testutil.builder.AbstractEntityBuilder;
import org.jbromo.model.jpa.testutil.cache.EntityCache;
import org.jbromo.model.jpa.util.EntityUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * Abstract JPA DAO JUnit.
 * @param <E> the entity type.
 * @param <PK> the primary key type.
 * @param <C> the CRUD type.
 * @author qjafcunuas
 */
@Slf4j
public abstract class AbstractDefaultCRUDTest<E extends IEntity<PK>, PK extends Serializable, C extends ICRUD<E, PK, ?>> {

    /**
     * Used for added n elements in crud list.
     */
    private static final int CRUD_LIST_ELTS_NUMBER = 5;

    /**
     * The crud to test.
     * @return the crud to test.
     */
    protected abstract C getCrud();

    /**
     * Return the entity builder.
     * @return the entity builder.
     */
    protected abstract AbstractEntityBuilder<E> getEntityBuilder();

    /**
     * Return the entity assert.
     * @return the entity assert.
     */
    protected abstract AbstractEntityAssert<E> getEntityAssert();

    /**
     * Return the transaction object.
     * @return the transaction.
     */
    protected abstract ITransaction getTransaction();

    /**
     * Modify an entity so that it doesn't already exist for unique constraint into the persistent storage.
     * @param entity the entity to modify.
     */
    protected abstract void modifyForUniqueConstraint(final E entity);

    /**
     * Return the entity class.
     * @return the entity class.
     */
    protected Class<E> getEntityClass() {
        return ParameterizedTypeUtil.getClass(this, 0);
    }

    /**
     * Return the entity cache.
     * @return the entity cache.
     */
    EntityCache getEntityCache() {
        return EntityCache.getInstance();
    }

    /**
     * Run before each test.
     */
    @Before
    public final void beforeAbstractDefaultCRUDTest() {
        Assert.assertNotNull("CRUD shouldn't be null", getCrud());
        Assert.assertNotNull("Transaction shouldn't be null", getTransaction());
        Assert.assertNotNull("EntityBuilder shouldn't be null", getEntityBuilder());
        Assert.assertNotNull("EntityAssert shouldn't be null", getEntityAssert());
    }

    /**
     * Run after each test.
     */
    @After
    public final void afterAbstractDefaultCRUDTest() {
        try {
            getTransaction().close();
        } catch (final Exception e) {
            log.error("Cannot close transaction", e);
        }
    }

    /**
     * CRUD JUnit.
     */
    @Test
    public void crud() {
        // Create
        final E entity = create();
        try {
            // Read
            read(entity, true);
            findByPk(entity, true);

            // Update
            update(entity);
        } finally {
            // Delete
            delete(entity);
        }
    }

    /**
     * CRUD for null object JUnit.
     */
    @Test
    public void crudNull() {
        // Create
        try {
            getCrud().create(null);
            Assert.fail("Should thrown an exception!");
        } catch (final Exception e) {
            Assert.assertTrue(true);
        }
        // Read
        try {
            Assert.assertNull(getCrud().read(null));
            Assert.fail("Should thrown an exception!");
        } catch (final Exception e) {
            Assert.assertTrue(true);
        }
        // findByPk
        try {
            getCrud().findByPk(null);
            Assert.fail("Should thrown an exception!");
        } catch (final Exception e) {
            Assert.assertTrue(true);
        }
        // Update
        try {
            getCrud().update(null);
            Assert.fail("Should thrown an exception!");
        } catch (final Exception e) {
            Assert.assertTrue(true);
        }
        // Delete
        try {
            getCrud().delete(null);
            Assert.fail("Should thrown an exception!");
        } catch (final Exception e) {
            Assert.assertTrue(true);
        }
    }

    /**
     * Save JUnit.
     */
    @Test
    public void save() {
        final E entity = getEntityBuilder().newEntity();
        modifyForUniqueConstraint(entity);
        E saved = null;
        try {
            Assert.assertTrue("Entity to create must have null primary key!", EntityUtil.isNullPk(entity));
            // save new entity.
            getTransaction().join();
            saved = getCrud().save(entity);
            getTransaction().unjoin();
            Assert.assertTrue("Saved entity must have not null primary key!", EntityUtil.isNotNullPk(saved));

            // save existed entity.
            final PK pk = saved.getPrimaryKey();
            getEntityBuilder().modify(entity, true);
            getTransaction().join();
            saved = getCrud().save(entity);
            getTransaction().unjoin();
            Assert.assertTrue("Saved entity must have not null primary key!", EntityUtil.isNotNullPk(saved));
            getEntityAssert().assertEquals(pk, saved.getPrimaryKey());

        } catch (final AssertionError e) {
            throw e;
        } catch (final Exception e) {
            log.error("Error on save method", e);
            Assert.fail(e.getMessage());
        } finally {
            if (saved != null && EntityUtil.isNotNullPk(saved)) {
                try {
                    getCrud().delete(entity);
                } catch (final Exception e) {
                    log.error("Error when deleting entities", e);
                }
            }
        }

    }

    /**
     * JUnit test for Created method.
     * @return the created entity.
     */
    protected E create() {
        return create(true);
    }

    /**
     * JUnit test for Created method.
     * @param acceptNullField if true, entity fields can be null if it is authorized by the mapping. Else, all field are not null.
     * @return the created entity.
     */
    protected E create(final boolean acceptNullField) {
        final E entity = getEntityBuilder().newEntity(acceptNullField);
        try {
            // Modify unique constraints value if necessary.
            modifyForUniqueConstraint(entity);

            // create entity.
            getTransaction().join();
            final E created = getCrud().create(entity);
            getEntityAssert().assertNotNull(created);
            Assert.assertSame("Created entity should be the same object ", entity, created);
            getEntityAssert().assertEquals(entity, created);
            getTransaction().unjoin();

            // Read created entity.
            getTransaction().join();
            final E readed = getCrud().read(created);
            getEntityAssert().assertNotNull(readed);
            getEntityAssert().assertEquals(entity, readed);
            getTransaction().unjoin();

            Assert.assertFalse("Objects should not be the same!", readed == created);

            Assert.assertTrue("EntityCache doesn't contains created entity! Trouble with EntityDaoDecorator?",
                              EntityCache.getInstance().getEntities(getEntityClass()).contains(created));

            return created;
        } catch (final AssertionError e) {
            throw e;
        } catch (final Exception e) {
            log.error("Error on create method", e);
            Assert.fail("Error on create method: " + e);
        }
        return null;
    }

    /**
     * JUnit test for Read method.
     * @param entity the entity used for testing the Read operation
     * @param existed define if model should be existed or not.
     * @return the read entity instance
     */
    protected E read(final E entity, final boolean existed) {
        getEntityAssert().assertNotNull(entity);
        try {
            // Not null entity
            getTransaction().join();
            final E readed = getCrud().read(entity);
            if (existed) {
                Assert.assertNotNull(readed);
                getEntityAssert().assertEquals(entity, readed);
            } else {
                Assert.assertNull(readed);
            }

            return readed;
        } catch (final Exception e) {
            log.error("Cannot read entity", e);
            Assert.fail("Cannot read object " + entity);
        } finally {
            try {
                getTransaction().unjoin();
            } catch (final Exception e) {
                log.error("Cannot unjoin transaction", e);
            }
        }
        return null;
    }

    /**
     * JUnit test for findByPk method.
     * @param entity the entity used for testing the Read operation
     * @param existed define if model should be existed or not.
     * @return the read entity instance
     */
    protected E findByPk(final E entity, final boolean existed) {
        getEntityAssert().assertNotNull(entity);
        try {
            getTransaction().join();
            final E readed = getCrud().findByPk(entity.getPrimaryKey());
            if (existed) {
                getEntityAssert().assertNotNull(readed);
                Assert.assertNotNull(readed);
                getEntityAssert().assertEquals(entity, readed);
            } else {
                Assert.assertNull(readed);
            }
            getTransaction().unjoin();
            return readed;
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail("Cannot read object " + entity);
        }
        return null;
    }

    /**
     * JUnit test for Updated method.
     * @param entity the entity used for testing the Update operation.
     * @return the updated entity.
     */
    protected E update(final E entity) {

        // Update model.
        E updated = null;
        getEntityBuilder().modify(entity, true);
        modifyForUniqueConstraint(entity);

        try {
            // Read updated entity.
            final E readedBefore = getCrud().findByPk(entity.getPrimaryKey());
            getEntityAssert().assertNotNull(readedBefore);
            Assert.assertFalse("Object should not be the same", entity == readedBefore);

            getTransaction().join();
            updated = getCrud().update(entity);
            getEntityAssert().assertNotNull(updated);
            Assert.assertNotSame("Updated entity should not be the same object ", entity, updated);
            getEntityAssert().assertEquals(entity, updated);
            getTransaction().unjoin();
            Assert.assertFalse("Object should not be the same", updated == readedBefore);

            // Read updated entity.
            getTransaction().join();
            final E readed = getCrud().findByPk(updated.getPrimaryKey());
            getEntityAssert().assertNotNull(readed);
            getEntityAssert().assertEquals(entity, readed);
            getTransaction().unjoin();

            Assert.assertFalse("Object should not be the same", readed == readedBefore);

            return updated;
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        return null;

    }

    /**
     * JUnit test for the Delete method.
     * @param entity the entity used for testing the Delete operation
     */
    protected void delete(final E entity) {
        // Delete entity
        try {
            getCrud().delete(entity);
        } catch (final Exception e) {
            log.error("Cannot delete entity", e);
            Assert.fail(e.getMessage());
        }

        // Read deleted model
        read(entity, false);
    }

    /**
     * This method is used for caching entities.
     * @param max the max element to create into the cache.
     */
    public void cache(final int max) {
        final int count = getEntityCache().getEntities(getEntityClass()).size();
        if (max > count) {
            create(max - count);
        }
    }

    /**
     * JUnit test for Read method.
     * @param count the number of entities to create.
     * @return the created entities.
     */
    public Collection<E> create(final int count) {
        final List<E> entities = new ArrayList<>();
        E entity;
        E created;
        try {
            for (int i = 0; i < count; i++) {
                // New entity.
                entity = getEntityBuilder().newEntity();
                modifyForUniqueConstraint(entity);

                // create entity.
                getTransaction().join();
                created = getCrud().create(entity);
                getEntityAssert().assertNotNull(created);
                getEntityAssert().assertEquals(entities, created);
                getTransaction().unjoin();
                entities.add(entity);
            }
            return entities;
        } catch (final Exception e) {
            log.error("Cannot create entities", e);
            Assert.fail(e.getMessage());
            return null;
        }
    }

    /**
     * JUnit test for findAll method.
     */
    @Test
    public void findAll() {
        Collection<E> created = null;
        try {
            // Add new elements.
            created = create(CRUD_LIST_ELTS_NUMBER);
            // FindAll
            final List<E> entities = getCrud().findAll();
            Assert.assertTrue(entities.containsAll(created));
        } catch (final Exception e) {
            log.error("Cannot find all entities", e);
            Assert.fail(e.getMessage());
        } finally {
            delete(created);
        }
    }

    /**
     * JUnit test for count method.
     */
    @Test
    public void count() {
        Collection<E> created = null;
        Long before = 0L;
        try {
            // Count existed entity.
            before = getCrud().count();
            // Add new elements.
            created = create(CRUD_LIST_ELTS_NUMBER);
            // Count existed entity.
            final Long after = getCrud().count();
            Assert.assertTrue("Not contains created elements", after == before + created.size());
        } catch (final Exception e) {
            log.error("Cannot count all entities", e);
            Assert.fail(e.getMessage());
        } finally {
            // Delete created entities.
            delete(created);
        }
        try {
            Assert.assertTrue("Not contains created elements", before == getCrud().count());
        } catch (final Exception e) {
            log.error("Cannot count all entities", e);
            Assert.fail(e.getMessage());
        }

    }

    /**
     * Delete entities from persistent storage.
     * @param entities the entities to delete.
     */
    private void delete(final Collection<E> entities) {
        if (CollectionUtil.isNotEmpty(entities)) {
            for (final E entity : entities) {
                delete(entity);
            }
        }
    }

}
