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
package org.jbromo.model.jpa.testutil.builder.field;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.OneToOne;

import org.jbromo.common.CollectionUtil;
import org.jbromo.common.ListUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.testutil.cache.EntityCache;
import org.jbromo.model.jpa.util.EntityUtil;
import org.junit.Assert;

/**
 * Define a OneToOne field builder.
 * @author qjafcunuas
 */
public class FieldOneToOneBuilder extends AbstractFieldBuilder<IEntity<Serializable>> {

    /**
     * Default constructor.
     * @param fieldBuilderFactory the field builder factory to used.
     */
    FieldOneToOneBuilder(final FieldBuilderFactory fieldBuilderFactory) {
        super(fieldBuilderFactory);
    }

    @Override
    protected Class<OneToOne> getManagedClass() {
        return OneToOne.class;
    }

    @Override
    public List<ValidationValue<IEntity<Serializable>>> getValidationErrorValues(final Field field) {
        // No error values on ManyToMany, because entities fields are not saved
        // with their owner, except for null values.
        final List<ValidationValue<IEntity<Serializable>>> values = ListUtil.toList();
        addNull(field, values, false);
        return values;
    }

    @Override
    public List<ValidationValue<IEntity<Serializable>>> getValidationSuccessValues(final Field field) {
        final List<ValidationValue<IEntity<Serializable>>> values = ListUtil.toList();
        addNull(field, values, true);
        values.add(new ValidationValue<IEntity<Serializable>>(field, "no", nextRandom(false, field)));
        return values;
    }

    @Override
    public IEntity<Serializable> nextRandom(final boolean nullable, final Field field) {
        final boolean returnNull = nullable && EntityUtil.isNullable(field);
        if (RandomUtil.isNull(returnNull)) {
            return null;
        }
        final List<IEntity<Serializable>> entities = getEntities(field);
        if (CollectionUtil.isEmpty(entities)) {
            return null;
        } else {
            final int random = RandomUtil.nextInt(entities.size());
            return entities.get(random);
        }
    }

    /**
     * Return cached entities for a field.
     * @param <E> the entity type.
     * @param <PK> the primary key type.
     * @param field the field to get cached entities.
     * @return entities.
     */
    @SuppressWarnings("unchecked")
    private <E extends IEntity<PK>, PK extends Serializable> List<IEntity<Serializable>> getEntities(final Field field) {
        final Class<IEntity<Serializable>> entityClass = (Class<IEntity<Serializable>>) field.getType();
        return EntityCache.getInstance().getEntities(entityClass);
    }

    @Override
    public void setFieldValue(final Object to, final Field field, final Object fieldValue) {
        Method method = null;
        try {
            method = InvocationUtil.getSetter(to, field);
        } catch (final InvocationException e) {
            Assert.fail(e.getMessage());
        }
        if (method == null || !method.isAccessible()) {
            // For embeddedId, ManyToOne fields have no setter.
            injectValue(to, field, fieldValue);
        } else {
            super.setFieldValue(to, field, fieldValue);
        }
    }

}
