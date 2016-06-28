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
package org.jbromo.model.jpa.testutil.builder;

import java.util.Map;

import org.jbromo.common.MapUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.invocation.AnnotationUtil;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.testutil.builder.field.FieldBuilderFactory;

import lombok.Getter;

/**
 * Define entity builder factory.
 * @author qjafcunuas
 */
public abstract class AbstractEntityBuilderFactory {

    /**
     * The field builder factory.
     */
    @Getter
    private final FieldBuilderFactory fieldBuilderFactory;

    /**
     * The builders.
     */
    @SuppressWarnings("rawtypes")
    private static final Map<Class, AbstractEntityBuilder> builders = MapUtil.toSynchronizedMap();

    /**
     * Default constructor.
     */
    protected AbstractEntityBuilderFactory() {
        super();
        this.fieldBuilderFactory = new FieldBuilderFactory(this);
    }

    /**
     * Return a specific builder.
     * @param <E> the entity type.
     * @param entityClass the entity class to get builder.
     * @return the builder class;
     */
    @SuppressWarnings("unchecked")
    protected <E extends IEntity<?>> Class<AbstractEntityBuilder<E>> getBuilderClass(final Class<? extends IEntity<?>> entityClass) {
        final EntityBuilder anno = AnnotationUtil.getAnnotation(getClass(), EntityBuilder.class);
        if (anno != null && anno.value() != null) {
            for (final Class<? extends AbstractEntityBuilder<?>> one : anno.value()) {
                final AbstractEntityBuilder<?> builder = ObjectUtil.newInstance(one);
                if (builder != null && builder.getEntityClass().equals(entityClass)) {
                    return (Class<AbstractEntityBuilder<E>>) one;
                }
            }
        }
        return null;
    }

    /**
     * Return an entity builder.
     * @param <E> the entity type.
     * @param entityClass the entityClass.
     * @return the entity builder.
     */
    @SuppressWarnings("unchecked")
    public <E extends IEntity<?>> AbstractEntityBuilder<E> getBuilder(final Class<E> entityClass) {
        if (builders.containsKey(entityClass)) {
            return builders.get(entityClass);
        }
        final Class<AbstractEntityBuilder<E>> builderClass = getBuilderClass(entityClass);
        AbstractEntityBuilder<E> builder;
        if (builderClass != null) {
            builder = ObjectUtil.newInstance(builderClass);
        } else {
            builder = new AbstractEntityBuilder<E>() {
                @Override
                protected Class<E> getEntityClass() {
                    return entityClass;
                }
            };
        }
        builder.setFactory(this);
        builders.put(entityClass, builder);
        return builder;
    }

}
