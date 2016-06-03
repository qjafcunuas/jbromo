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

import org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer;
import org.jbromo.dao.jpa.container.common.JpaProviderFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import lombok.Getter;

/**
 * Define the JpaHibernateProvider JUnit.
 * @author qjafcunuas
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(JavassistLazyInitializer.class)
public class JpaHibernateProviderTest {

    /**
     * Define Hibernate JPA entity.
     * @author qjafcunuas
     */
    @Getter
    private class HibernateEntityHandler {
        JavassistLazyInitializer handler = null;

        /**
         * Default constructor.
         */
        public HibernateEntityHandler() {
            super();
            this.handler = PowerMockito.mock(JavassistLazyInitializer.class);
            PowerMockito.when(this.handler.getPersistentClass()).thenReturn(Integer.class);
        }
    }

    /**
     * Tests before running all others tests.
     */
    @BeforeClass
    public static void beforeClass() {
        Assert.assertNotNull(JpaProviderFactory.getInstance().getImplementation());
        Assert.assertEquals(JpaHibernateProvider.class, JpaProviderFactory.getInstance().getImplementation().getClass());
    }

    /**
     * Test getPersistentClass() method.
     */
    @Test
    public void getPersistentClass() {
        // Entity with handler.
        final HibernateEntityHandler entity = new HibernateEntityHandler();
        Assert.assertNull(JpaProviderFactory.getInstance().getImplementation().getPersistentClass(null));
        Assert.assertNotNull(JpaProviderFactory.getInstance().getImplementation().getPersistentClass(entity));
        Assert.assertEquals(Integer.class, JpaProviderFactory.getInstance().getImplementation().getPersistentClass(entity));
        // Entity without handler.
        Assert.assertEquals(Integer.class, JpaProviderFactory.getInstance().getImplementation().getPersistentClass(Integer.valueOf(0)));
    }

    /**
     * Test isCompositePrimaryKeyUpdatedDuringPersist() method.
     */
    @Test
    public void isCompositePrimaryKeyUpdatedDuringPersist() {
        Assert.assertTrue(JpaProviderFactory.getInstance().getImplementation().isCompositePrimaryKeyUpdatedDuringPersist());
    }

    /**
     * Test isFetchAliasable() method.
     */
    @Test
    public void isFetchAliasable() {
        Assert.assertTrue(JpaProviderFactory.getInstance().getImplementation().isFetchAliasable());
    }

    /**
     * Test isOneToManyAutoMerge() method.
     */
    @Test
    public void isOneToManyAutoMerge() {
        Assert.assertTrue(JpaProviderFactory.getInstance().getImplementation().isOneToManyAutoMerge());
    }
}
