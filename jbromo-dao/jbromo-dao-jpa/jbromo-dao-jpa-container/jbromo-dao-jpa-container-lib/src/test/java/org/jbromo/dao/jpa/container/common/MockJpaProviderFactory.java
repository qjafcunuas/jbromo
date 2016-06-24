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
package org.jbromo.dao.jpa.container.common;

import java.lang.reflect.Field;

import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.junit.Assert;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility clas to mock the JpaProviderFactory instance.
 * @author qjafcunuas
 */
@Slf4j
public class MockJpaProviderFactory {

    /**
     * Default provider that return false.
     */
    public static final IJpaProvider PROVIDER_FALSE = new IJpaProvider() {

        @Override
        public boolean isOneToManyAutoMerge() {
            return false;
        }

        @Override
        public boolean isFetchAliasable() {
            return false;
        }

        @Override
        public boolean isCompositePrimaryKeyUpdatedDuringPersist() {
            return false;
        }

        @Override
        public Class<?> getPersistentClass(final Object object) {
            return object == null ? null : object.getClass();
        }
    };

    /**
     * Default provider that return true.
     */
    public static final IJpaProvider PROVIDER_TRUE = new IJpaProvider() {

        @Override
        public boolean isOneToManyAutoMerge() {
            return true;
        }

        @Override
        public boolean isFetchAliasable() {
            return true;
        }

        @Override
        public boolean isCompositePrimaryKeyUpdatedDuringPersist() {
            return true;
        }

        @Override
        public Class<?> getPersistentClass(final Object object) {
            return object == null ? null : object.getClass();
        }
    };

    /**
     * Mock the JPA provider into the factory.
     * @param provider the mocked provider.
     */
    public static void mock(final IJpaProvider provider) {
        final JpaProviderFactory instance = JpaProviderFactory.getInstance();
        try {
            final Field field = InvocationUtil.getField(JpaProviderFactory.class, IJpaProvider.class);
            InvocationUtil.injectValue(instance, field, provider);
            if (provider == null) {
                Assert.assertNull(JpaProviderFactory.getInstance().getImplementation());
            } else {
                Assert.assertNotNull(JpaProviderFactory.getInstance().getImplementation());
                Assert.assertEquals(provider.isCompositePrimaryKeyUpdatedDuringPersist(),
                                    JpaProviderFactory.getInstance().getImplementation().isCompositePrimaryKeyUpdatedDuringPersist());
                Assert.assertEquals(provider.isFetchAliasable(), JpaProviderFactory.getInstance().getImplementation().isFetchAliasable());
                Assert.assertEquals(provider.isOneToManyAutoMerge(), JpaProviderFactory.getInstance().getImplementation().isOneToManyAutoMerge());
            }
        } catch (final InvocationException e) {
            log.error("Cannot mock provider", e);
        }
    }

    /**
     * Unmock the JPA provider factory.
     */
    public static void unmock() {
        JpaProviderFactory.getInstance().reload();
    }

}
