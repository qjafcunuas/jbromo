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
package org.jbromo.model.jpa.testutil.crud;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.jbromo.common.CollectionUtil;
import org.jbromo.common.IntegerUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.common.SetUtil;
import org.jbromo.common.StringUtil;
import org.jbromo.common.crud.ICRUDExtended;
import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.common.invocation.ParameterizedTypeUtil;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.testutil.builder.field.AbstractFieldBuilder;
import org.jbromo.model.jpa.util.EntityUtil;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * Abstract JPA DAO JUnit. FIXME move to jbromo-dao-jpa-lib ?
 * @param <E> the entity type.
 * @param <PK> the primary key type.
 * @param <C> the CRUD type.
 * @author qjafcunuas
 */
@Slf4j
public abstract class AbstractDefaultCRUDExtendedTest<E extends IEntity<PK>, PK extends Serializable, C extends ICRUDExtended<E, PK, ?>>
    extends AbstractDefaultCRUDTest<E, PK, C> {

    /**
     * Used for added n elements in crud list.
     */
    private static final int CRUD_LIST_ELTS_NUMBER = 5;

    @Override
    protected void modifyForUniqueConstraint(final E entity) {
        modifyForUniqueConstraint(entity, null);
    }

    /**
     * Modify an entity so that it doesn't already exist for unique constraint into the persistent storage and into an entities list.
     * @param entity the entity to modify.
     * @param entities the entities list.
     */
    protected void modifyForUniqueConstraint(final E entity, final Collection<E> entities) {
        try {
            for (final Field field : EntityUtil.getPersistedFields(entity)) {
                if (EntityUtil.isUnique(field)) {
                    modifyForUniqueConstraint(entity, field, entities, IntegerUtil.INT_0);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail("Cannot verify unicity for entity " + entity.getClass());
        }
    }

    /**
     * Return a distinct random value.
     * @param <O> the object type.
     * @param field the field to get new value.
     * @param distinct the return value must not be equals to this parameter.
     * @return another value.
     */
    private <O> O nextRandom(final Field field, final O distinct) {
        final AbstractFieldBuilder<O> builder = getEntityBuilder().getFactory().getFieldBuilderFactory().getBuilder(field);
        return builder.nextRandom(field, distinct);
    }

    /**
     * Modify an entity so that it doesn't already exist for unique constraint into the persistent storage and into an entities list.
     * @param entity the entity to modify.
     * @param field the field to modify.
     * @param entities the entities list.
     * @param counter stop process if cannot find unicity.
     * @throws Exception exception.
     */
    @SuppressWarnings("unchecked")
    protected void modifyForUniqueConstraint(final E entity, final Field field, final Collection<E> entities, final int counter) throws Exception {
        Object value;
        if (counter == IntegerUtil.INT_100) {
            Assert.fail("Cannot modify entity for unique constraint on field " + field);
        }
        final E criteria = (E) entity.getClass().newInstance();
        // Read entity field value.
        value = InvocationUtil.getValue(entity, field);
        if (value != null) {
            // Unicity in persistent storage.
            final AbstractFieldBuilder<?> fieldBuilder = getEntityBuilder().getFactory().getFieldBuilderFactory().getBuilder(field);
            fieldBuilder.setFieldValue(criteria, field, value);
            // InvocationUtil.setValue(criteria, field, value);
            final List<E> found = getCrud().findAll(criteria);
            if (!found.isEmpty() && !found.contains(entity)) {
                // Entity for unique constraint already exists in persistent
                // storage.
                // Set a new value.
                fieldBuilder.setFieldValue(entity, field, nextRandom(field, value));
                // InvocationUtil
                // .setValue(entity, field, nextRandom(field, value));
                modifyForUniqueConstraint(entity, field, entities, 1 + counter);
                final Object newValue = InvocationUtil.getValue(entity, field);
                Assert.assertFalse("Cannot set another value for unique constraint on field " + field, ObjectUtil.equals(value, newValue));
            }
            // Unicity for entities parameter.
            if (CollectionUtil.isNotEmpty(entities)) {
                for (final E one : entities) {
                    if (!ObjectUtil.equals(one, entity) && value.equals(InvocationUtil.getValue(one, field))) {
                        // Entity for unique constraint already exists in
                        // entities list.
                        // Set a new value.
                        fieldBuilder.setFieldValue(entity, field, nextRandom(field, nextRandom(field, value)));
                        // InvocationUtil.setValue(entity, field,
                        // nextRandom(field, value));
                        modifyForUniqueConstraint(entity, field, entities, 1 + counter);
                        final Object newValue = InvocationUtil.getValue(entity, field);
                        Assert.assertFalse("Cannot set another value for unique constraint on field " + field, ObjectUtil.equals(value, newValue));
                        break;
                    }
                }
            }
        }
    }

    /**
     * JUnit test for CRUD method.
     */
    @Test
    public void crudList() {
        // Create
        final Collection<E> entities = create(CRUD_LIST_ELTS_NUMBER);

        try {
            // Read
            read(entities);

            // Update
            update(entities);

        } finally {
            // Delete
            delete(entities);
        }
    }

    @Override
    public Collection<E> create(final int count) {
        final List<E> entities = new ArrayList<E>();
        E entity;
        for (int i = 0; i < count; i++) {
            entity = getEntityBuilder().newEntity();
            modifyForUniqueConstraint(entity, entities);
            entities.add(entity);
        }

        try {
            // create entity.
            getTransaction().join();
            final Collection<E> created = getCrud().create(entities);
            getEntityAssert().assertNotNull(created);
            getEntityAssert().assertEquals(entities, created);
            getTransaction().unjoin();

            // Read created entity.
            final Collection<PK> primaryKeys = EntityUtil.getPrimaryKeys(created);
            Assert.assertFalse(CollectionUtil.isEmpty(primaryKeys));
            Assert.assertTrue(count == primaryKeys.size());
            getTransaction().join();
            final Collection<E> readed = getCrud().findAllByPk(primaryKeys);
            getEntityAssert().assertNotNull(readed);
            getEntityAssert().assertEquals(entities, readed);
            getTransaction().unjoin();

            return created;
        } catch (final Exception e) {
            log.error("Cannot created entities", e);
            Assert.fail("Cannot created entities " + e);
        } finally {
            try {
                getTransaction().close();
            } catch (final Exception e) {
                log.error("Cannot close transaction", e);
            }
        }
        return null;
    }

    /**
     * JUnit test for Read method.
     * @param entities the entities used for testing the Read operation
     * @return the read entities instance
     */
    protected Collection<E> read(final Collection<E> entities) {
        getEntityAssert().assertNotNull(entities);
        try {
            getTransaction().join();
            final Collection<E> readed = getCrud().read(entities);
            getEntityAssert().assertNotNull(readed);
            getEntityAssert().assertEquals(entities, readed);

            return readed;
        } catch (final Exception e) {
            log.error("Cannot read entities", e);
            Assert.fail("Cannot read object " + entities);
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
     * JUnit test for Read method.
     * @param entities the entities used for testing the Read operation
     * @return the read entities instance
     */
    protected Collection<E> findAllByPk(final Collection<E> entities) {
        getEntityAssert().assertNotNull(entities);
        try {
            Assert.assertNull(getCrud().findAllByPk(null));
            getTransaction().join();
            final Collection<PK> primaryKeys = EntityUtil.getPrimaryKeys(entities);
            final Collection<E> readed = getCrud().findAllByPk(primaryKeys);
            getEntityAssert().assertNotNull(readed);
            getEntityAssert().assertEquals(entities, readed);
            // with earger loading.
            getCrud().findAllByPk(primaryKeys, ObjectUtil.newInstance(getEntityClass()));
            // empty pks
            primaryKeys.clear();
            getCrud().findAllByPk(primaryKeys, ObjectUtil.newInstance(getEntityClass()));

            return readed;
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail("Cannot read object " + entities);
        } finally {
            try {
                getTransaction().close();
            } catch (final Exception e) {
                log.error("Cannot close transaction", e);
            }
        }
        return null;
    }

    /**
     * JUnit test for Readed method.
     * @param entities the entities used for testing the Read operation.
     * @return the updated entities.
     */
    protected Collection<E> update(final Collection<E> entities) {
        getEntityAssert().assertNotNull(entities);
        for (final E entity : entities) {
            getEntityBuilder().modify(entity, true);
            modifyForUniqueConstraint(entity, entities);
        }

        try {
            getTransaction().join();
            final Collection<E> updated = getCrud().update(entities);
            getEntityAssert().assertNotNull(updated);
            getEntityAssert().assertEquals(entities, updated);
            getTransaction().unjoin();

            // Read updated entity.
            final Collection<PK> primaryKeys = EntityUtil.getPrimaryKeys(entities);
            getTransaction().join();
            final Collection<E> readed = getCrud().findAllByPk(primaryKeys);
            getEntityAssert().assertNotNull(readed);
            getEntityAssert().assertEquals(entities, readed);
            getTransaction().unjoin();

            return updated;
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } finally {
            try {
                getTransaction().close();
            } catch (final Exception e) {
                log.error("Cannot close transaction", e);
            }
        }
        return null;

    }

    /**
     * JUnit test for the Delete method.
     * @param entities the entities used for testing the Delete operation
     */
    protected void delete(final Collection<E> entities) {
        try {
            getCrud().delete((Collection<E>) null);
            Assert.fail("Should thrown an exception!");
        } catch (final Exception e) {
            Assert.assertTrue(true);
        }
        try {
            getCrud().delete(new ArrayList<E>());
            Assert.fail("Should thrown an exception!");
        } catch (final Exception e) {
            Assert.assertTrue(true);
        }
        getEntityAssert().assertNotNull(entities);
        // Delete entity
        try {
            getCrud().delete(entities);
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

        // Read deleted model
        final Collection<PK> primaryKeys = EntityUtil.getPrimaryKeys(entities);
        try {
            final Collection<E> readed = getCrud().findAllByPk(primaryKeys);
            Assert.assertTrue("List should be empty", readed.isEmpty());
        } catch (final MessageLabelException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * JUnit test for findAll method with entity as criteria.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void findAllCriteria() {
        final E created = create(false);
        E criteria = null;
        final Set<Object> tested = ObjectUtil.cast(SetUtil.toSet(created), Set.class);
        try {
            for (final Field field : EntityUtil.getPersistedFields(created)) {
                criteria = (E) created.getClass().newInstance();
                findAllCriteria(created, criteria, created, criteria, field, true, tested);
            }
        } catch (final InstantiationException e) {
            log.error("Cannot instanciate object ", e);
            e.printStackTrace();
            Assert.fail("Cannot instanciate object " + created.getClass());
        } catch (final IllegalAccessException e) {
            log.error("Cannot access object ", e);
            Assert.fail("Cannot access object " + created.getClass());
        } catch (final Exception e) {
            log.error("Cannot build query ", e);
            Assert.fail(e.getMessage());
        } finally {
            try {
                getCrud().delete(created);
            } catch (final MessageLabelException e) {
                log.error("Cannot delete entities", e);
            }
        }
    }

    /**
     * JUnit test for findAll method with entity as criteria.
     * @param <M> the entity/member object type.
     * @param entity the persisted entity.
     * @param criteria the criteria associated with the persisted entity.
     * @param memberEntity the entity or a member of the entity.
     * @param memberCriteria the criteria associated with the member of the entity.
     * @param memberEntityField the field of the member entity to test.
     * @param testAnotherValue if true, test field with another value than the entity field.
     * @param tested contains already tested object (for rejecting recursively call).
     * @throws Exception exception.
     */
    private <M> void findAllCriteria(final E entity, final E criteria, final M memberEntity, final M memberCriteria, final Field memberEntityField,
            final boolean testAnotherValue, final Set<Object> tested) throws Exception {

        if (EntityUtil.isEmbedded(memberEntityField) || EntityUtil.isEmbeddedId(memberEntityField)) {
            findAllEmbeddedCriteria(entity, criteria, memberEntity, memberCriteria, memberEntityField, testAnotherValue, tested);
        } else if (CollectionUtil.isCollection(memberEntityField.getType())) {
            findAllCollectionCriteria(entity, criteria, memberEntity, memberCriteria, memberEntityField, testAnotherValue, tested);
        } else {
            findAllFieldCriteria(entity, criteria, memberEntity, memberCriteria, memberEntityField, testAnotherValue, tested);
        }
    }

    /**
     * Return field value of an entity's member. If member field value is a collection, return the first element of the collection.
     * @param memberEntity the member to get field value.
     * @param memberEntityField the member field to get value.
     * @return the value.
     * @throws InvocationException exception.
     */
    private Object getEntityFieldValue(final Object memberEntity, final Field memberEntityField) throws InvocationException {
        if (CollectionUtil.isCollection(memberEntity)) {
            Collection<?> memberEntityFieldCollectionValue = null;
            // Load field value for first element in the collection.
            memberEntityFieldCollectionValue = ObjectUtil.cast(memberEntity, Collection.class);
            if (!CollectionUtil.isEmpty(memberEntityFieldCollectionValue)) {
                return InvocationUtil.getValue(memberEntityFieldCollectionValue.iterator().next(), memberEntityField);
            }
        } else {
            return InvocationUtil.getValue(memberEntity, memberEntityField);
        }
        return null;

    }

    /**
     * Return field value of an entity's member. If member field value is a collection, return all elements of the collection.
     * @param memberEntity the member to get field value.
     * @param memberEntityField the member field to get value.
     * @return the value.
     * @throws InvocationException exception.
     */
    private Object getEntityFieldValues(final Object memberEntity, final Field memberEntityField) throws InvocationException {
        if (CollectionUtil.isCollection(memberEntity)) {
            final Collection<Object> objects = SetUtil.toSet();
            final Collection<?> memberEntityCollection = ObjectUtil.cast(memberEntity, Collection.class);
            for (final Object object : memberEntityCollection) {
                objects.add(InvocationUtil.getValue(object, memberEntityField));
            }
            return objects;
        } else {
            return InvocationUtil.getValue(memberEntity, memberEntityField);
        }
    }

    /**
     * JUnit test for findAll method with entity as criteria.
     * @param <M> the entity/member object type.
     * @param entity the persisted entity.
     * @param criteria the criteria associated with the persisted entity.
     * @param memberEntity the entity or a member of the entity.
     * @param memberCriteria the criteria associated with the member of the entity.
     * @param memberEntityField the field of the member entity to test.
     * @param testAnotherValue if true, test field with another value than the entity field.
     * @param tested contains already tested object (for rejecting recursively call).
     * @throws Exception exception.
     */
    private <M> void findAllFieldCriteria(final E entity, final E criteria, final M memberEntity, final M memberCriteria,
            final Field memberEntityField, final boolean testAnotherValue, final Set<Object> tested) throws Exception {

        // Read field value.
        final Object memberEntityFieldValue = getEntityFieldValue(memberEntity, memberEntityField);

        Assert.assertNotNull("Cannot search entity for null value on field " + memberEntityField, memberEntityFieldValue);

        if (EntityUtil.isManyToOne(memberEntityField) || EntityUtil.isOneToOne(memberEntityField)) {
            // For no recursively call.
            if (tested.contains(memberEntityFieldValue)) {
                return;
            }
            tested.add(memberEntityFieldValue);
        }

        List<E> entities;
        // Set value into the criteria.
        getEntityBuilder().getFactory().getFieldBuilderFactory().getBuilder(memberEntityField).setFieldValue(memberCriteria, memberEntityField,
                                                                                                             memberEntityFieldValue);

        // no eager loading.
        entities = getCrud().findAll(criteria);
        Assert.assertTrue("Entities should contained the created entity : test field " + memberEntityField, entities.contains(entity));

        // with eager loading
        entities = getCrud().findAll(criteria, criteria, null);
        Assert.assertTrue("Entities should contained the created entity", entities.contains(entity));
        verifyEagerLoading(entities, criteria);

        // by pk
        Assert.assertEquals(entity, getCrud().findByPk(entity.getPrimaryKey(), (E) null));
        Assert.assertEquals(entity, getCrud().findByPk(entity.getPrimaryKey(), criteria));

        // For String, test like operator
        if (memberEntityFieldValue instanceof String) {
            final String stringValue = (String) memberEntityFieldValue;
            if (!StringUtil.isEmpty(stringValue) && stringValue.length() > 1) {
                final String str = StringUtil.STAR.concat(stringValue.substring(1));
                final AbstractFieldBuilder<?> fieldBuilder = getEntityBuilder().getFactory().getFieldBuilderFactory().getBuilder(memberEntityField);
                fieldBuilder.setFieldValue(memberCriteria, memberEntityField, str);
                entities = getCrud().findAll(criteria);
                Assert.assertTrue("Entities should contained the created entity", entities.contains(entity));
            }
        }
        // Set another value into the criteria.
        if (testAnotherValue) {
            Object anotherValue = null;
            if (EntityUtil.isGeneratedValue(memberEntityField)) {
                anotherValue = -(Long) memberEntityFieldValue;
            } else {
                anotherValue = getEntityBuilder().nextRandom(memberEntityField, memberEntityFieldValue);
            }
            if (anotherValue != null) {
                final AbstractFieldBuilder<?> fieldBuilder = getEntityBuilder().getFactory().getFieldBuilderFactory().getBuilder(memberEntityField);
                fieldBuilder.setFieldValue(memberCriteria, memberEntityField, anotherValue);
                entities = getCrud().findAll(criteria);
                Assert.assertFalse("Entities shouldn't contained the created entity : " + memberEntityField + " for value " + anotherValue,
                                   entities.contains(entity));
            }
        }
    }

    /**
     * Verify that eager loading is valid on entities.
     * @param entities the entities to check.
     * @param eagerLoading the eager loading strategy.
     * @throws InvocationException exception.
     */
    private void verifyEagerLoading(final Collection<E> entities, final E eagerLoading) throws InvocationException {
        // Loop on entities.
        for (final E entity : entities) {
            verifyEagerLoading(entity, eagerLoading);
        }
    }

    /**
     * Verify that eager loading is valid on entity.
     * @param entity the entity to check.
     * @param eagerLoading the eager loading strategy.
     * @throws InvocationException exception.
     */
    private void verifyEagerLoading(final Object entity, final Object eagerLoading) throws InvocationException {

        // Loop on criteria fields.
        for (final Field field : EntityUtil.getPersistedFields(eagerLoading)) {
            verifyEagerLoading(entity, eagerLoading, field);
        }
    }

    /**
     * Verify that eager loading is valid on a field's entity.
     * @param entity the entity to check.
     * @param eagerLoading the eager loading strategy.
     * @throws InvocationException exception.
     */
    private void verifyEagerLoading(final Object entity, final Object eagerLoading, final Field field) throws InvocationException {

        final Object eagerValue = InvocationUtil.getValue(eagerLoading, field);
        if (eagerValue == null) {
            // No eager loading for this field.
            return;
        }
        final Object entityValue = InvocationUtil.getValue(entity, field);
        if (EntityUtil.isManyToOne(field) || EntityUtil.isOneToOne(field)) {
            if (entityValue != null) {
                verifyEagerLoading(entityValue, eagerValue);
            }
        } else if (EntityUtil.isManyToMany(field) || EntityUtil.isOneToMany(field)) {
            final Collection<?> eagerCol = (Collection<?>) eagerValue;
            if (!eagerCol.isEmpty()) {
                final Object eager = eagerCol.iterator().next();
                for (final Object o : (Collection<?>) entityValue) {
                    verifyEagerLoading(o, eager);
                }
            }
        } else if (EntityUtil.isEmbedded(field) || EntityUtil.isEmbeddedId(field)) {
            verifyEagerLoading(entityValue, eagerValue);
        }
    }

    /**
     * JUnit test for findAll method with entity as criteria.
     * @param <M> the entity/member object type.
     * @param entity the persisted entity.
     * @param criteria the criteria associated with the persisted entity.
     * @param memberEntity the entity or a member of the entity.
     * @param memberCriteria the criteria associated with the member of the entity.
     * @param memberEntityField the field of the member entity to test.
     * @param testAnotherValue if true, test field with another value than the entity field.
     * @param tested contains already tested object (for rejecting recursively call).
     * @throws Exception exception.
     */
    @SuppressWarnings("unchecked")
    private <M> void findAllCollectionCriteria(final E entity, final E criteria, final M memberEntity, final M memberCriteria,
            final Field memberEntityField, final boolean testAnotherValue, final Set<Object> tested) throws Exception {

        // Read field value.
        final Object memberEntityFieldValue = InvocationUtil.getValue(memberEntity, memberEntityField);

        final Collection<?> collection = ObjectUtil.cast(memberEntityFieldValue, Collection.class);
        if (!CollectionUtil.isEmpty(collection)) {
            // Field is a collection.
            final List<Class<?>> classes = ParameterizedTypeUtil.getGenericType(memberEntityField);
            Assert.assertNotNull(classes);
            Assert.assertTrue(classes.size() == 1);
            final Class<?> entityClass = classes.get(0);
            // final Object first = iter.next();
            Object entityCriteria;
            // For each fields of the entity collection.
            for (final Field entityField : EntityUtil.getPersistedFields(entityClass)) {

                entityCriteria = entityClass.newInstance();
                final Collection<Object> fieldCollection = ObjectUtil.cast(InvocationUtil.getValue(memberCriteria, memberEntityField),
                                                                           Collection.class);
                fieldCollection.clear();

                fieldCollection.add(entityCriteria);
                // Cannot test another value because for a collection, it can
                // contains more than one value.
                findAllCriteria(entity, criteria, collection, entityCriteria, entityField, false, tested);
            }
        }
    }

    /**
     * JUnit test for findAll method with entity as criteria.
     * @param <M> the entity/member object type.
     * @param entity the persisted entity.
     * @param criteria the criteria associated with the persisted entity.
     * @param memberEntity the entity or a member of the entity.
     * @param memberCriteria the criteria associated with the member of the entity.
     * @param memberEntityField the field of the member entity to test.
     * @param testAnotherValue if true, test field with another value than the entity field.
     * @param tested contains already tested object (for rejecting recursively call).
     * @throws Exception exception.
     */
    private <M> void findAllEmbeddedCriteria(final E entity, final E criteria, final M memberEntity, final M memberCriteria,
            final Field memberEntityField, final boolean testAnotherValue, final Set<Object> tested) throws Exception {

        Object memberCriteriaFieldValue;
        // For all fields of the embedded member.
        for (final Field embeddedField : EntityUtil.getPersistedFields(memberEntityField.getType())) {
            // Clean memberCriteriaFieldValue into memberCriteria with new
            // instance.
            memberCriteriaFieldValue = memberEntityField.getType().newInstance();
            getEntityBuilder().getFactory().getFieldBuilderFactory().getBuilder(memberEntityField).setFieldValue(memberCriteria, memberEntityField,
                                                                                                                 memberCriteriaFieldValue);
            // Find all for this embedded field.
            memberCriteriaFieldValue = InvocationUtil.getValue(memberCriteria, memberEntityField);
            findAllCriteria(entity, criteria, getEntityFieldValues(memberEntity, memberEntityField), memberCriteriaFieldValue, embeddedField,
                            EntityUtil.isEmbedded(memberEntityField), tested);
        }

    }

    /**
     * JUnit test for no single result
     */
    @Test
    public void noSingleResult() {
        // Create
        final E entity = create();
        try {
            Assert.assertNotNull(getCrud().findByPk(entity.getPrimaryKey()));

            // Search another pk that doesn't exit in persistent storage.
            int count = 0;
            PK anotherPk = RandomUtil.nextAnother(entity.getPrimaryKey());
            do {
                anotherPk = RandomUtil.nextAnother(entity.getPrimaryKey());
            } while (getCrud().findByPk(anotherPk) != null && count++ < IntegerUtil.INT_100);

            // Test getSingleResult().
            Assert.assertNull(getCrud().findByPk(anotherPk, getEntityBuilder().newEntity()));
        } catch (final MessageLabelException e) {
            log.error("Cannot execute query ", e);
            Assert.fail(e.getMessage());
        } finally {
            // Delete
            delete(entity);
        }
    }

}
