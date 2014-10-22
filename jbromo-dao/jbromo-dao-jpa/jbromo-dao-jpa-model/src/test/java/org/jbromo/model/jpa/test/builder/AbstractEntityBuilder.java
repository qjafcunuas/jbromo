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
package org.jbromo.model.jpa.test.builder;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.ListUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.common.invocation.ParameterizedTypeUtil;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.compositepk.ICompositePk;
import org.jbromo.model.jpa.test.builder.field.AbstractFieldBuilder;
import org.jbromo.model.jpa.test.builder.field.ValidationValue;
import org.jbromo.model.jpa.util.EntityUtil;
import org.junit.Assert;

/**
 * Define entity builder.
 *
 * @author qjafcunuas
 * @param <E>
 *            the entity type
 */
@Slf4j
public abstract class AbstractEntityBuilder<E extends IEntity<?>> {

    /**
     * The factory to used.
     */
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.NONE)
    private final AbstractEntityBuilderFactory factory;

    /**
     * Return the entity class.
     *
     * @return the entity class.
     */
    protected Class<E> getEntityClass() {
        return ParameterizedTypeUtil.getClass(this, 0);
    }

    /**
     * Default constructor.
     *
     * @param factory
     *            the factory to used for building others entities.
     */
    protected AbstractEntityBuilder(final AbstractEntityBuilderFactory factory) {
        super();
        this.factory = factory;
    }

    /**
     * Return a random value.
     *
     * @param <T>
     *            the value type.
     * @param field
     *            the field to get random value.
     * @param distinct
     *            the returned value is not equals to this distinct object.
     * @return the random value.
     */
    @SuppressWarnings("unchecked")
    public <T> T nextRandom(final Field field, final T distinct) {
        return (T) getFactory().getFieldBuilderFactory().getBuilder(field)
                .nextRandom(field, distinct);
    }

    /**
     * Return a random value.
     *
     * @param <T>
     *            the value type.
     * @param field
     *            the field to get random value.
     * @param distinct
     *            the returned value is not equals to this distinct object.
     * @return the random value.
     */
    @SuppressWarnings("unchecked")
    public <T> T nextRandom(final Field field, final Collection<T> distinct) {
        return (T) getFactory().getFieldBuilderFactory().getBuilder(field)
                .nextRandom(field, distinct);
    }

    /**
     * Creates a new non-persistent instance of the model.
     *
     * @return a new instance of model.
     */
    public E newEntity() {
        return newEntity(true);
    }

    /**
     * Return an entity builder.
     *
     * @param <M>
     *            the entity type to get.
     * @param <MPK>
     *            the primary key type.
     * @param acceptNullField
     *            if false, all field of the entity is not null.
     * @param entityClass
     *            the entity class.
     * @return an entity builder.
     */
    public <M extends IEntity<MPK>, MPK extends Serializable> M newEntity(
            final boolean acceptNullField, final Class<M> entityClass) {
        return getFactory().getBuilder(entityClass).newEntity(acceptNullField);
    }

    /**
     * Creates a new non-persistent instance of the model.
     *
     * @param acceptNullField
     *            if true, entity fields can be null if it is authorized by the
     *            mapping.
     * @return a new instance of model.
     */
    public E newEntity(final boolean acceptNullField) {
        try {
            final E model = getEntityClass().newInstance();
            modify(model, acceptNullField);
            return model;
        } catch (final Exception e) {
            log.error("Cannot build a new Model {} {}", getEntityClass(), e);
            Assert.fail(e.getMessage());
            return null;
        }
    }

    /**
     * Modifies the instance of the model.
     *
     * @param <O>
     *            the model type to modify.
     * @param model
     *            the model instance to modify
     * @param acceptNullField
     *            if true, entity fields can be null if it is authorized by the
     *            mapping.
     */
    public <O> void modify(final O model, final boolean acceptNullField) {
        try {
            final Class<?> modelClass = model.getClass();
            AbstractFieldBuilder<Object> fieldBuilder = null;
            Object fieldValue = null;
            Field embeddedId = null;
            for (final Field field : EntityUtil.getPersistedFields(modelClass)) {
                if (EntityUtil.isEmbeddedId(field)) {
                    embeddedId = field;
                } else if (!EntityUtil.isId(field)) {
                    fieldBuilder = getFactory().getFieldBuilderFactory()
                            .getBuilder(field);
                    fieldValue = fieldBuilder
                            .nextRandom(acceptNullField, field);
                    fieldBuilder.setFieldValue(model, field, fieldValue);
                }
            }
            if (embeddedId != null) {
                String mapsId;
                IEntity<?> mapsIdValue;
                final Field fieldPk = EntityUtil.getPrimaryKeyField(modelClass);
                final Object valuePk = EntityUtil
                        .getPrimaryKey((IEntity<?>) model);
                for (final Field one : EntityUtil
                        .getPersistedFields(modelClass)) {
                    mapsId = EntityUtil.getMapsIdValue(one);
                    if (mapsId != null) {
                        // field has MapsId annotation.
                        mapsIdValue = (IEntity<?>) one.get(model);
                        // Get field on primary key for with name is mapsId.
                        final Field f = InvocationUtil.getField(
                                fieldPk.getType(), mapsId);
                        if (mapsIdValue != null) {
                            // field entity value is not null.
                            // Inject entity's primary key into composit pk.
                            InvocationUtil.injectValue(valuePk, f,
                                    EntityUtil.getPrimaryKey(mapsIdValue));
                        } else {
                            InvocationUtil.injectValue(valuePk, f, null);

                        }
                    }
                }
            }
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Modify a model for a EmbeddedId field.
     *
     * @param <O>
     *            the model type.
     * @param model
     *            the model.
     * @param field
     *            the model's field.
     * @throws InvocationException
     *             exception.
     */
    protected <O> void modifyEmbeddedId(final O model, final Field field)
            throws InvocationException {
        final ICompositePk compositePk = (ICompositePk) InvocationUtil
                .getValue(model, field);
        modify(compositePk, false);
    }

    /**
     * Modify a model for a ManyToMany relation ship.
     *
     * @param <O>
     *            the model type.
     * @param model
     *            the model.
     * @param field
     *            the model's field.
     * @throws InvocationException
     *             exception.
     */
    protected <O> void modifyCollection(final O model, final Field field)
            throws InvocationException {
        final Collection<Object> entities = InvocationUtil.getValue(model,
                field);
        Assert.assertNotNull(entities);
        entities.clear();
        entities.addAll(getFactory().getFieldBuilderFactory().getBuilder(field)
                .getValidationSuccessValues(field));
    }

    /**
     * Modify a model for a OneToMany relation ship.
     *
     * @param <O>
     *            the model type.
     * @param model
     *            the model which have a collection of entities with
     *            compositePk.
     * @param field
     *            the model's field (Collection of entities with compositePk).
     * @param acceptNullField
     *            if true, entity fields can be null if it is authorized by the
     *            mapping.
     * @throws InvocationException
     *             exception.
     */
    @SuppressWarnings("unchecked")
    protected <O> void modifyOneToMany(final O model, final Field field,
            final boolean acceptNullField) throws InvocationException {
        final List<Class<?>> classes = ParameterizedTypeUtil
                .getGenericType(field);
        Assert.assertNotNull(classes);
        Assert.assertTrue(classes.size() == 1);
        final Class<IEntity<ICompositePk>> entityClass = ObjectUtil.cast(
                classes.get(0), Class.class);

        final Collection<Object> entities = ObjectUtil.cast(
                InvocationUtil.getValue(model, field), Collection.class);
        Assert.assertNotNull(entities);
        entities.clear();

        final int random = RandomUtil.nextInt(IntegerUtil.INT_10);
        for (int i = 0; i < random; i++) {
            final IEntity<ICompositePk> child = newEntity(acceptNullField,
                    entityClass);
            for (final Field compositePkField : EntityUtil
                    .getPersistedFields(child.getPrimaryKey().getClass())) {
                if (compositePkField.getType().equals(model.getClass())) {
                    InvocationUtil.setValue(child.getPrimaryKey(),
                            compositePkField, model);
                }
            }
            if (!entities.contains(child)) {
                entities.add(child);
            }
        }
    }

    /**
     * Return error on validation values for persisting entity.
     *
     * @return the error values.
     */
    public List<ValidationValue<E>> getValidationErrorValues() {
        final List<ValidationValue<E>> entities = ListUtil.toList();
        E entity;
        AbstractFieldBuilder<Object> fieldBuilder;
        for (final Field field : EntityUtil
                .getPersistedFields(getEntityClass())) {
            fieldBuilder = getFactory().getFieldBuilderFactory().getBuilder(
                    field);
            for (final ValidationValue<Object> fieldValue : fieldBuilder
                    .getValidationErrorValues(field)) {
                entity = newEntity();
                fieldBuilder.setFieldValue(entity, field, fieldValue);
                entities.add(new ValidationValue<E>(fieldValue, entity));
            }
        }
        return entities;

    }

    /**
     * Set a random primary key on a entity.
     *
     * @param entity
     *            the entity to set a primary key.
     */
    public void injectPrimaryKey(final E entity) {
        injectPrimaryKey(entity, (Serializable) null);
    }

    /**
     * Set a random primary key on a entity.
     *
     * @param entity
     *            the entity to set pk.
     * @param distinct
     *            if not null, the random pk must not be equals to this value.
     */
    public void injectPrimaryKey(final E entity, final Serializable distinct) {
        if (entity == null) {
            return;
        }
        final Field fieldPk = EntityUtil.getPrimaryKeyField(entity.getClass());
        final AbstractFieldBuilder<Serializable> fieldBuilder = getFactory()
                .getFieldBuilderFactory().getBuilder(fieldPk);
        Serializable pk;
        if (distinct == null) {
            pk = fieldBuilder.nextRandom(false, fieldPk);
            Assert.assertNotNull(pk);
        } else {
            pk = fieldBuilder.nextRandom(fieldPk, distinct);
            Assert.assertNotNull(pk);
            Assert.assertFalse(distinct.equals(pk));
        }
        fieldBuilder.setFieldValue(entity, fieldPk, pk);
        Assert.assertEquals(entity.getPrimaryKey(), pk);
    }

    /**
     * Set primary key on a entity from another.
     *
     * @param from
     *            the entity to read primary key.
     * @param to
     *            the entity to set primary key.
     */
    public void injectPrimaryKey(final E from, final E to) {
        final Field fieldPk = EntityUtil.getPrimaryKeyField(getEntityClass());
        final AbstractFieldBuilder<Serializable> fieldBuilder = getFactory()
                .getFieldBuilderFactory().getBuilder(fieldPk);
        final IEntity<?> cast = ObjectUtil.cast(from, IEntity.class);
        final Serializable pk = EntityUtil.getPrimaryKey(cast);
        fieldBuilder.setFieldValue(to, fieldPk, pk);
    }

}
