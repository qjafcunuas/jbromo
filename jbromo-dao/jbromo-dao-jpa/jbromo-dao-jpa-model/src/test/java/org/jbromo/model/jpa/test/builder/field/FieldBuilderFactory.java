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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.model.jpa.test.builder.AbstractEntityBuilderFactory;
import org.junit.Assert;

/**
 * Define field builder factory.
 *
 * @author qjafcunuas
 *
 */
public class FieldBuilderFactory {

    /**
     * The entity builder factory to used.
     */
    @Getter(AccessLevel.PACKAGE)
    private final AbstractEntityBuilderFactory entityBuilderFactory;

    /**
     * Map builder by field type.
     */
    private final Map<Class<?>, AbstractFieldBuilder<?>> mapper = new HashMap<Class<?>, AbstractFieldBuilder<?>>();

    /**
     * Default constructor.
     *
     * @param entityBuilderFactory
     *            the entity builder factory to used.
     */
    public FieldBuilderFactory(
            final AbstractEntityBuilderFactory entityBuilderFactory) {
        super();
        this.entityBuilderFactory = entityBuilderFactory;
        addBuilder(new FieldBigDecimalBuilder(this));
        addBuilder(new FieldBooleanBuilder(this));
        addBuilder(new FieldCalendarBuilder(this));
        addBuilder(new FieldEmbeddedBuilder(this));
        addBuilder(new FieldEmbeddedIdBuilder(this));
        addBuilder(new FieldEnumBuilder(this));
        addBuilder(new FieldIntegerBuilder(this));
        addBuilder(new FieldLongBuilder(this));
        addBuilder(new FieldManyToOneBuilder(this));
        addBuilder(new FieldManyToManyBuilder(this));
        addBuilder(new FieldOneToManyBuilder(this));
        addBuilder(new FieldOneToOneBuilder(this));
        addBuilder(new FieldShortBuilder(this));
        addBuilder(new FieldStringBuilder(this));
    }

    /**
     * Add a field builder.
     *
     * @param builder
     *            the builder to add.
     */
    private void addBuilder(final AbstractFieldBuilder<?> builder) {
        this.mapper.put(builder.getManagedClass(), builder);
    }

    /**
     * Return the field builder according to a field.
     *
     * @param <T>
     *            the field type.
     * @param field
     *            the field to get builder.
     * @return the builder.
     */
    @SuppressWarnings("unchecked")
    public <T> AbstractFieldBuilder<T> getBuilder(final Field field) {
        AbstractFieldBuilder<T> builder = null;
        final Class<?> managedClass = getManagedClass(field);
        builder = (AbstractFieldBuilder<T>) this.mapper.get(managedClass);
        if (builder == null) {
            Assert.assertNotNull("No field builder for field " + field,
                    builder);
        }
        return builder;
    }

    /**
     * Return the managed class according to a field.
     *
     * @param field
     *            the field.
     * @return the managed class.
     */
    private Class<?> getManagedClass(final Field field) {
        for (final Annotation annotation : field.getAnnotations()) {
            if (this.mapper.containsKey(annotation.annotationType())) {
                return annotation.annotationType();
            }
        }
        return field.getType();
    }

}
