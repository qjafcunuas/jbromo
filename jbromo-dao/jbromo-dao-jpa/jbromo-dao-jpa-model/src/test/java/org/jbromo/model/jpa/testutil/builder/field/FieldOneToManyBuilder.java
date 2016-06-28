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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.OneToMany;

import org.jbromo.common.CollectionUtil;
import org.jbromo.common.IntegerUtil;
import org.jbromo.common.ListUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.common.invocation.ParameterizedTypeUtil;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.testutil.builder.AbstractEntityBuilder;
import org.jbromo.model.jpa.util.EntityUtil;
import org.junit.Assert;

/**
 * Define a string field builder.
 * @author qjafcunuas
 */
public class FieldOneToManyBuilder extends AbstractFieldBuilder<Collection<IEntity<Serializable>>> {

    /**
     * Default constructor.
     * @param fieldBuilderFactory the field builder factory to used.
     */
    FieldOneToManyBuilder(final FieldBuilderFactory fieldBuilderFactory) {
        super(fieldBuilderFactory);
    }

    @Override
    protected Class<OneToMany> getManagedClass() {
        return OneToMany.class;
    }

    /**
     * Return entity associated with the collection field.
     * @param field the field
     * @return the entity class.
     */
    private Class<IEntity<Serializable>> getEntityClass(final Field field) {
        Assert.assertTrue(CollectionUtil.isCollection(field.getType()));
        final List<Class<?>> classes = ParameterizedTypeUtil.getGenericType(field);
        Assert.assertNotNull(classes);
        Assert.assertTrue(classes.size() == 1);
        return EntityUtil.cast(classes.get(0));
    }

    @Override
    public List<ValidationValue<Collection<IEntity<Serializable>>>> getValidationErrorValues(final Field field) {
        final Class<IEntity<Serializable>> entityClass = getEntityClass(field);
        final List<ValidationValue<Collection<IEntity<Serializable>>>> list = ListUtil.toList();
        // build bad entities.
        final AbstractEntityBuilder<IEntity<Serializable>> entityBuilder = getFieldBuilderFactory().getEntityBuilderFactory().getBuilder(entityClass);
        final List<ValidationValue<IEntity<Serializable>>> badEntities = entityBuilder.getValidationErrorValues();

        List<IEntity<Serializable>> subList = null;
        for (final ValidationValue<IEntity<Serializable>> badEntity : badEntities) {
            // add a valid entity in the same list, after adding bad entities
            // (because valid entity must not erase bad entities into the set
            // entity field if same pk).
            subList = ListUtil.toList(badEntity.getValue());
            subList.add(entityBuilder.newEntity());
            list.add(new ValidationValue<Collection<IEntity<Serializable>>>(badEntity, subList));
        }
        return list;
    }

    @Override
    public List<ValidationValue<Collection<IEntity<Serializable>>>> getValidationSuccessValues(final Field field) {
        // TODO
        Assert.fail("Not implemented");
        return null;
    }

    @Override
    public void setFieldValue(final Object to, final Field field, final Object fieldValues) {
        setFieldValue(to, field, fieldValues, true);
    }

    @Override
    public void setFieldValue(final Object to, final Field field, final ValidationValue<?> fieldValue) {
        // fieldValue.getField : field UserAddress.User
        // field : field User.addresses, so declaringClass is User.
        final boolean injectParent = !fieldValue.isNullManyToOne() && !fieldValue.getField().getType().equals(field.getDeclaringClass());
        setFieldValue(to, field, fieldValue.getValue(), injectParent);
    }

    /**
     * Set field value into it owner.
     * @param to the owner object to set field value.
     * @param field the field of the owner to set.
     * @param fieldValues the value to set.
     * @param injectParent if true, the parent object is injected into the child object.
     */
    @SuppressWarnings("unchecked")
    private void setFieldValue(final Object to, final Field field, final Object fieldValues, final boolean injectParent) {
        try {
            Assert.assertTrue(CollectionUtil.isCollection(field.getType()));
            Assert.assertTrue(CollectionUtil.isCollection(fieldValues.getClass()));
            final Collection<Object> toValues = ObjectUtil.cast(InvocationUtil.getValue(to, field), Collection.class);
            Assert.assertNotNull("Null value return by getter on OneToMany field " + field, toValues);
            toValues.clear();
            final Collection<Object> fromValues = ObjectUtil.cast(fieldValues, Collection.class);
            final String mappedBy = EntityUtil.getOneToManyMappedBy(field);
            for (final Object fieldValue : fromValues) {
                if (injectParent) {
                    injectParent(to, fieldValue, mappedBy);
                }
                // final Object primaryKey = ((IEntity<?>) fieldValue)
                // .getPrimaryKey();
                //
                // for (final Field embeddedField : EntityUtil
                // .getPersistedFields(primaryKey.getClass())) {
                // if (embeddedField.getType().equals(to.getClass())) {
                // // Set owner to children.
                // injectValue(primaryKey, embeddedField, to);
                // }
                // }
                toValues.add(fieldValue);
            }

        } catch (final InvocationException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Inject parent object to a child object.
     * @param parent the parent object to inject.
     * @param to the object to set the parent.
     * @param toFieldName the field's name of the to object to set.
     * @throws InvocationException exception.
     */
    private void injectParent(final Object parent, final Object to, final String toFieldName) throws InvocationException {
        // Get field name of to class.
        final Field field = InvocationUtil.getField(to.getClass(), toFieldName);
        Assert.assertNotNull("Cannot find field " + toFieldName + " on object " + to, field);
        // inject parent into toFieldName.
        injectValue(to, field, parent);
        if (EntityUtil.isMapsId(field)) {
            // Must change embeddedId pk too.
            final String mapsId = EntityUtil.getMapsIdValue(field);
            // inject parent's pk into "to object"'s primary key as mapsId
            // field.
            injectParent(((IEntity<?>) parent).getPrimaryKey(), EntityUtil.getPrimaryKey((IEntity<?>) to), mapsId);
        }
    }

    @Override
    public Collection<IEntity<Serializable>> nextRandom(final boolean nullable, final Field field) {
        final Class<IEntity<Serializable>> entityClass = getEntityClass(field);
        final AbstractEntityBuilder<IEntity<Serializable>> entityBuilder = getFieldBuilderFactory().getEntityBuilderFactory().getBuilder(entityClass);

        final Collection<IEntity<Serializable>> entities = new ArrayList<IEntity<Serializable>>();
        int random = RandomUtil.nextInt(IntegerUtil.INT_10);
        if (!nullable) {
            // at least one element
            random++;
        }
        while (random-- > 0) {
            entities.add(entityBuilder.newEntity(nullable));
        }
        return entities;
    }

}
