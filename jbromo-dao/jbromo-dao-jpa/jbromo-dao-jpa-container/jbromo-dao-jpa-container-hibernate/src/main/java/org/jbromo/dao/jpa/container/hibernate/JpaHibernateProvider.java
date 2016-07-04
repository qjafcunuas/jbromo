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
package org.jbromo.dao.jpa.container.hibernate;

import java.lang.reflect.Field;

import org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.common.invocation.InvocationUtil.AccessType;
import org.jbromo.dao.jpa.container.common.IJpaProvider;

import lombok.extern.slf4j.Slf4j;

/**
 * Define specific utility for Hibernate JPA provider.
 * @author qjafcunuas
 */
@Slf4j
public class JpaHibernateProvider implements IJpaProvider {

    @Override
    public Class<?> getPersistentClass(final Object object) {
        if (object == null) {
            return null;
        }
        try {
            final Field field = InvocationUtil.getField(object.getClass(), "handler");
            if (field != null) {
                final JavassistLazyInitializer handler = InvocationUtil.getValue(object, field, AccessType.FIELD, false);
                return handler.getPersistentClass();
            }
        } catch (final InvocationException e) {
            log.warn("Cannot run invocation for {}", object, e);
        }
        return object.getClass();
    }

    @Override
    public boolean isCompositePrimaryKeyUpdatedDuringPersist() {
        return true;
    }

    @Override
    public boolean isFetchAliasable() {
        return true;
    }

    @Override
    public boolean isOneToManyAutoMerge() {
        return true;
    }

}
