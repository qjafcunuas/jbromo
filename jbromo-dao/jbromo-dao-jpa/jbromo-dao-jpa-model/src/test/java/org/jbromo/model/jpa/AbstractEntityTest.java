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
package org.jbromo.model.jpa;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;

import org.jbromo.common.CollectionUtil;
import org.jbromo.common.IntegerUtil;
import org.jbromo.common.ListUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.StringUtil;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.common.invocation.InvocationUtil.AccessType;
import org.jbromo.common.invocation.ParameterizedTypeUtil;
import org.jbromo.model.jpa.testutil.builder.AbstractEntityBuilder;
import org.jbromo.model.jpa.testutil.builder.AbstractEntityBuilderFactory;
import org.jbromo.model.jpa.testutil.builder.field.AbstractFieldBuilder;
import org.jbromo.model.jpa.testutil.cache.EntityCache;
import org.jbromo.model.jpa.testutil.validation.EntityValidationUtil;
import org.jbromo.model.jpa.util.EntityUtil;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * Define JUnit AbstractEntity test.
 * @author qjafcunuas
 * @param <E> the entity type.
 * @param <PK> the primary ket type.
 */
@Slf4j
public abstract class AbstractEntityTest<E extends AbstractEntity<PK>, PK extends Serializable> {

    /**
     * Return the entity factory.
     * @return the entity factory.
     */
    protected abstract AbstractEntityBuilderFactory getEntityFactory();

    /**
     * Return the entity builder.
     * @return the entity builder.
     */
    protected AbstractEntityBuilder<E> getEntityBuilder() {
        return getEntityFactory().getBuilder(getEntityClass());
    }

    /**
     * Return the entity class.
     * @return the entity class.
     */
    protected Class<E> getEntityClass() {
        return ParameterizedTypeUtil.getClass(this, 0);
    }

    /**
     * Call this method so that cache will be contain x elements of the entity class.
     * @param count the size of the cache after calling this method.
     */
    public void initialize(final int count) {
        E entity;
        while (EntityCache.getInstance().getEntities(getEntityClass()).size() < count) {
            entity = getEntityBuilder().newEntity();
            getEntityBuilder().injectPrimaryKey(entity);
            EntityCache.getInstance().addOrUpdate(entity);
        }
    }

    /**
     * Validate entity annotations.
     */
    @Test
    public void validate() {
        EntityValidationUtil.checkClass(getEntityClass());
    }

    /**
     * Test equals method.
     */
    @Test
    public void equalsTest() {
        // Build 2 entities, with null pk.
        final E firstEntity = getEntityBuilder().newEntity();
        final E secondEntity = getEntityBuilder().newEntity();

        // Verify entities are not null and pk are null.
        Assert.assertNotNull(firstEntity);
        Assert.assertNotNull(secondEntity);
        if (!EntityUtil.isEmbeddedId(EntityUtil.getPrimaryKeyField(getEntityClass()))) {
            Assert.assertTrue(EntityUtil.isNullPk(firstEntity));
            Assert.assertTrue(EntityUtil.isNullPk(secondEntity));
        }

        // Entity is not equals to an non-entity object.
        Assert.assertFalse(firstEntity.equals(new Object()));

        // Entity is not equals to another entity object.
        Assert.assertFalse(firstEntity.equals(new IEntity<Long>() {

            /**
             * serial version UID.
             */
            private static final long serialVersionUID = 3020928575234339296L;

            @Override
            public Long getPrimaryKey() {
                return null;
            }

        }));

        // Entities are not equals to null object.
        Assert.assertFalse(firstEntity.equals(null));
        Assert.assertFalse(secondEntity.equals(null));

        // Entities are equals to themselfes.
        Assert.assertTrue(firstEntity.equals(firstEntity));
        Assert.assertTrue(secondEntity.equals(secondEntity));

        if (!EntityUtil.isEmbeddedId(EntityUtil.getPrimaryKeyField(getEntityClass()))) {
            // Entities with null pk are not equals.
            Assert.assertFalse(firstEntity.equals(secondEntity));
            Assert.assertFalse(secondEntity.equals(firstEntity));

            // Set a pk on first entity.
            getEntityBuilder().injectPrimaryKey(firstEntity);

            // Entity with null pk is not equals to an entity with a not
            // null
            // pk.
            Assert.assertFalse(firstEntity.equals(secondEntity));
            Assert.assertFalse(secondEntity.equals(firstEntity));
        }

        // Set a distinct pk on second entity.
        getEntityBuilder().injectPrimaryKey(secondEntity, EntityUtil.getPrimaryKey(firstEntity));

        // Entities with distinct not null pk are not equals.
        Assert.assertFalse(firstEntity.equals(secondEntity));
        Assert.assertFalse(secondEntity.equals(firstEntity));

        // Set same pk on entities.
        getEntityBuilder().injectPrimaryKey(firstEntity, secondEntity);
        Assert.assertTrue(firstEntity.equals(secondEntity));
        Assert.assertTrue(secondEntity.equals(firstEntity));

    }

    /**
     * Test hashCode method.
     */
    @Test
    public void hashCodeTest() {
        // Build 2 entities, with null pk (except entity with embeddedId that
        // have no null pk).
        final E firstEntity = getEntityBuilder().newEntity();
        E secondEntity = getEntityBuilder().newEntity();

        final boolean embeddedId = EntityUtil.isEmbeddedId(EntityUtil.getPrimaryKeyField(getEntityClass()));
        if (embeddedId) {
            // For entity with embeddedId, find distinguish primary key.
            final int counter = IntegerUtil.INT_100;
            while (firstEntity.equals(secondEntity) && counter > 0) {
                secondEntity = getEntityBuilder().newEntity();
            }
        }

        // Entities haven't the same hashCode.
        Assert.assertFalse(firstEntity.hashCode() == secondEntity.hashCode());

        // Entities have same hashCode for themself.
        Assert.assertTrue(firstEntity.hashCode() == firstEntity.hashCode());
        Assert.assertTrue(secondEntity.hashCode() == secondEntity.hashCode());
    }

    /**
     * Test hashCode/e method for embeddedId.
     */
    @Test
    public void embeddedId() {
        final boolean embeddedId = EntityUtil.isEmbeddedId(EntityUtil.getPrimaryKeyField(getEntityClass()));
        if (!embeddedId) {
            return;
        }

        try {
            // Test with null pk.
            E firstEntity = getEntityBuilder().newEntity();
            E secondEntity = getEntityBuilder().newEntity();
            List<E> entities = ListUtil.toList(firstEntity, secondEntity);
            Field fieldOfPk;
            IEntity<?> value;

            for (final Field field : EntityUtil.getMapsIdFields(getEntityClass())) {
                for (final E entity : entities) {
                    // Set null object into the entity.
                    InvocationUtil.injectValue(entity, field, null);
                    Assert.assertNull(InvocationUtil.getValue(entity, field));
                    // set null value into the entity primary key.
                    fieldOfPk = InvocationUtil.getField(entity.getPrimaryKey().getClass(), EntityUtil.getMapsIdValue(field));
                    InvocationUtil.injectValue(entity.getPrimaryKey(), fieldOfPk, null);
                    Assert.assertNull(InvocationUtil.getValue(entity.getPrimaryKey(), fieldOfPk));
                }
            }
            Assert.assertFalse(firstEntity == secondEntity);
            Assert.assertFalse(firstEntity.getPrimaryKey() == secondEntity.getPrimaryKey());
            Assert.assertFalse(firstEntity.hashCode() == secondEntity.hashCode());
            Assert.assertFalse(firstEntity.equals(secondEntity));

            // Test with same pk values, but distinct mapsId object.
            firstEntity = getEntityBuilder().newEntity();
            secondEntity = getEntityBuilder().newEntity();
            entities = ListUtil.toList(firstEntity, secondEntity);
            for (final Field field : EntityUtil.getMapsIdFields(getEntityClass())) {
                // read value from first entity.
                value = InvocationUtil.getValue(firstEntity, field);
                // find a distinct value.
                value = getEntityBuilder().getFactory().getBuilder(value.getClass()).nextRandom(field, value);
                // inject the distinct value in the second entity.
                InvocationUtil.injectValue(secondEntity, field, value);
            }
            getEntityBuilder().injectPrimaryKey(firstEntity, secondEntity);
            Assert.assertFalse(firstEntity == secondEntity);
            Assert.assertFalse(firstEntity.getPrimaryKey() == secondEntity.getPrimaryKey());
            Assert.assertTrue(firstEntity.hashCode() == secondEntity.hashCode());
            Assert.assertTrue(firstEntity.equals(secondEntity));

            // Test with null pk values, and same mapsId object with not null
            // primary key.
            firstEntity = getEntityBuilder().newEntity();
            secondEntity = getEntityBuilder().newEntity();
            entities = ListUtil.toList(firstEntity, secondEntity);
            for (final Field field : EntityUtil.getMapsIdFields(getEntityClass())) {
                // read value from first entity.
                value = InvocationUtil.getValue(firstEntity, field);
                // inject the value in the second entity.
                InvocationUtil.injectValue(secondEntity, field, value);
                // set null value into the entity primary key.
                for (final E entity : entities) {
                    fieldOfPk = InvocationUtil.getField(entity.getPrimaryKey().getClass(), EntityUtil.getMapsIdValue(field));
                    InvocationUtil.injectValue(entity.getPrimaryKey(), fieldOfPk, null);
                    Assert.assertNull(InvocationUtil.getValue(entity.getPrimaryKey(), fieldOfPk, AccessType.FIELD, false));
                }
            }
            Assert.assertFalse(firstEntity == secondEntity);
            Assert.assertFalse(firstEntity.getPrimaryKey() == secondEntity.getPrimaryKey());
            Assert.assertTrue(firstEntity.hashCode() == secondEntity.hashCode());
            Assert.assertTrue(firstEntity.equals(secondEntity));

            // Test with null pk values, and same mapsId object with null
            // primary key.
            firstEntity = getEntityBuilder().newEntity();
            secondEntity = getEntityBuilder().newEntity();
            entities = ListUtil.toList(firstEntity, secondEntity);
            for (final Field field : EntityUtil.getMapsIdFields(getEntityClass())) {
                // Build a new mapsId object with null pk.
                value = getEntityBuilder().getFactory().getBuilder((Class<IEntity<Serializable>>) field.getType()).newEntity(true);
                Assert.assertNull(value.getPrimaryKey());
                // inject the value in the entities.
                InvocationUtil.injectValue(firstEntity, field, value);
                InvocationUtil.injectValue(secondEntity, field, value);
                // set null value into the entity primary key.
                for (final E entity : entities) {
                    fieldOfPk = InvocationUtil.getField(entity.getPrimaryKey().getClass(), EntityUtil.getMapsIdValue(field));
                    InvocationUtil.injectValue(entity.getPrimaryKey(), fieldOfPk, null);
                    Assert.assertNull(InvocationUtil.getValue(entity.getPrimaryKey(), fieldOfPk, AccessType.FIELD, false));
                }
            }
            Assert.assertFalse(firstEntity == secondEntity);
            Assert.assertFalse(firstEntity.getPrimaryKey() == secondEntity.getPrimaryKey());
            Assert.assertTrue(firstEntity.hashCode() == secondEntity.hashCode());
            Assert.assertTrue(firstEntity.equals(secondEntity));

        } catch (final InvocationException e) {
            log.error("Invocation error");
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test toString method.
     */
    @Test
    public void toStringTest() {
        final E entity = getEntityBuilder().newEntity();
        getEntityBuilder().injectPrimaryKey(entity);
        Assert.assertFalse(StringUtil.isEmpty(entity.toString()));
    }

    /**
     * Test Getter/Setter method.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetterSetter() {
        // Build an entity.
        final E entity = getEntityBuilder().newEntity();
        final List<Field> fields = InvocationUtil.getFields(getEntityClass());
        Object newValue = null;
        AbstractFieldBuilder<?> fieldBuilder;
        int mod;
        for (final Field field : fields) {
            mod = field.getModifiers();
            if (!Modifier.isStatic(mod)) {
                fieldBuilder = getEntityFactory().getFieldBuilderFactory().getBuilder(field);
                newValue = fieldBuilder.nextRandom(true, field);
                fieldBuilder.setFieldValue(entity, field, newValue);
                try {
                    if (CollectionUtil.isCollection(newValue)) {
                        final Collection<Object> colGetter = ObjectUtil.cast(InvocationUtil.getValue(entity, field), Collection.class);
                        final Collection<Object> colNewValue = ObjectUtil.cast(newValue, Collection.class);
                        CollectionUtil.containsAll(colGetter, colNewValue, true);
                    } else {
                        Assert.assertTrue(field.toString(), ObjectUtil.equals(newValue, InvocationUtil.getValue(entity, field)));
                    }
                } catch (final InvocationException e) {
                    Assert.fail("Cannot get value from field " + field);
                }
            }
        }
    }
}
