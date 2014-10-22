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

import java.lang.reflect.Constructor;
import java.util.Map;

import lombok.Getter;

import org.jbromo.common.MapUtil;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.test.builder.field.FieldBuilderFactory;
import org.junit.Assert;

/**
 * Define entity builder factory.
 *
 * @author qjafcunuas
 *
 */
public abstract class AbstractEntityBuilderFactory {

    /**
     * Map builders class for entity.
     */
    private final Map<Class<?>, Class<?>> builders = MapUtil.toSynchronizedMap(null);

    /**
     * The field builder factory.
     */
    @Getter
    private final FieldBuilderFactory fieldBuilderFactory;

    /**
     * Default constructor.
     */
    protected AbstractEntityBuilderFactory() {
        super();
        this.fieldBuilderFactory = new FieldBuilderFactory(this);
    }

    /**
     * Set specific builder.
     *
     * @param entityClass
     *            the entity class to set builder.
     * @param builderClass
     *            the builder class.
     */
    protected void setBuilderClass(
            final Class<? extends IEntity<?>> entityClass,
            final Class<? extends AbstractEntityBuilder<?>> builderClass) {
        this.builders.put(entityClass, builderClass);
    }

    /**
     * Return a specific builder.
     *
     * @param <E>
     *            the entity type.
     * @param entityClass
     *            the entity class to get builder.
     * @return the builder class;
     */
    @SuppressWarnings("unchecked")
    protected <E extends IEntity<?>> Class<AbstractEntityBuilder<E>> getBuilderClass(
            final Class<? extends IEntity<?>> entityClass) {
        return (Class<AbstractEntityBuilder<E>>) this.builders.get(entityClass);
    }

    /**
     * Return an entity builder.
     *
     * @param <E>
     *            the entity type.
     * @param entityClass
     *            the entityClass.
     * @return the entity builder.
     */
    public <E extends IEntity<?>> AbstractEntityBuilder<E> getBuilder(
            final Class<E> entityClass) {
        if (this.builders.containsKey(entityClass)) {
            final Class<AbstractEntityBuilder<E>> builderClass = getBuilderClass(entityClass);
            return newInstance(builderClass);
        } else {
            return new AbstractEntityBuilder<E>(this) {
                @Override
                protected Class<E> getEntityClass() {
                    return entityClass;
                }
            };
        }
    }

    /**
     * Return new instance of a builder class.
     *
     * @param <E>
     *            the entity type.
     * @param builderClass
     *            the builder class.
     * @return the new instance.
     */
    private <E extends IEntity<?>> AbstractEntityBuilder<E> newInstance(
            final Class<AbstractEntityBuilder<E>> builderClass) {
        try {
            final Constructor<AbstractEntityBuilder<E>> constructor = InvocationUtil
                    .getConstructor(builderClass, getClass());
            constructor.setAccessible(true);
            return constructor.newInstance(this);
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail("Cannot invocate constructor with parameters.");
            return null;
        }
    }

}
