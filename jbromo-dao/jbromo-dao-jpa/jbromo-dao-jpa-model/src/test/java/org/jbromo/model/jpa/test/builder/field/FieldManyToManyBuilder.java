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
package org.jbromo.model.jpa.test.builder.field;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.ManyToMany;

import org.jbromo.common.CollectionUtil;
import org.jbromo.common.ListUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.common.invocation.ParameterizedTypeUtil;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.test.cache.EntityCache;
import org.jbromo.model.jpa.util.EntityUtil;
import org.junit.Assert;

/**
 * Define a string field builder.
 *
 * @author qjafcunuas
 *
 */
public class FieldManyToManyBuilder extends
        AbstractFieldBuilder<Collection<IEntity<Serializable>>> {

    /**
     * Default constructor.
     *
     * @param fieldBuilderFactory
     *            the field builder factory to used.
     */
    FieldManyToManyBuilder(final FieldBuilderFactory fieldBuilderFactory) {
        super(fieldBuilderFactory);
    }

    @Override
    protected Class<ManyToMany> getManagedClass() {
        return ManyToMany.class;
    }

    @Override
    public List<ValidationValue<Collection<IEntity<Serializable>>>> getValidationErrorValues(
            final Field field) {
        Assert.assertTrue(CollectionUtil.isCollection(field.getType()));
        // No success values on ManyToOne, because entities fields are not saved
        // with their owner.
        return new ArrayList<ValidationValue<Collection<IEntity<Serializable>>>>();
    }

    /**
     * Return entity associated with the collection field.
     *
     * @param field
     *            the field
     * @return the entity class.
     */
    private Class<IEntity<Serializable>> getEntityClass(final Field field) {
        Assert.assertTrue(CollectionUtil.isCollection(field.getType()));
        final List<Class<?>> classes = ParameterizedTypeUtil
                .getGenericType(field);
        Assert.assertNotNull(classes);
        Assert.assertTrue(classes.size() == 1);
        return EntityUtil.cast(classes.get(0));
    }

    @Override
    public List<ValidationValue<Collection<IEntity<Serializable>>>> getValidationSuccessValues(
            final Field field) {
        final List<ValidationValue<Collection<IEntity<Serializable>>>> list = ListUtil.toList();
        final Collection<IEntity<Serializable>> entities = nextEntities(false,
                getEntityClass(field));
        list.add(new ValidationValue<Collection<IEntity<Serializable>>>(field,
                "no", entities));
        return list;
    }

    @Override
    public Collection<IEntity<Serializable>> nextRandom(final boolean nullable,
            final Field field) {
        return nextEntities(nullable, getEntityClass(field));
    }

    /**
     * Return a random entities list.
     *
     * @param <E>
     *            the entity type.
     * @param <PK>
     *            the primary key type.
     * @param empty
     *            if true, the return list can be empty.
     * @param entityClass
     *            the entity class.
     * @return the entities.
     */
    private <E extends IEntity<PK>, PK extends Serializable> List<E> nextEntities(
            final boolean empty, final Class<E> entityClass) {
        if (empty && RandomUtil.isNull()) {
            return new ArrayList<E>();
        }
        final List<E> entities = EntityCache.getInstance().getEntities(
                entityClass);
        return RandomUtil.nextSubCollection(false, entities);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setFieldValue(final Object to, final Field field,
            final Object fieldValue) {
        try {
            Assert.assertTrue(CollectionUtil.isCollection(field.getType()));
            final Collection<Object> toValues = (Collection<Object>) InvocationUtil
                    .getValue(to, field);
            Assert.assertNotNull(
                    "Null value return by getter on ManyToMany field " + field,
                    toValues);
            toValues.clear();
            if (CollectionUtil.isCollection(fieldValue.getClass())) {
                toValues.addAll((Collection<Object>) fieldValue);
            } else {
                toValues.add(fieldValue);
            }
        } catch (final InvocationException e) {
            Assert.fail(e.getMessage());
        }
    }

}
