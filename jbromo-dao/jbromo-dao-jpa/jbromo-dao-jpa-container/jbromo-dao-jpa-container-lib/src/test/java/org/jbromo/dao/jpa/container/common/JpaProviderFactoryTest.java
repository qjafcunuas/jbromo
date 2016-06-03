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

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Define JpaProvuderFactory JUnit test.
 * @author qjafcunuas
 */
public class JpaProviderFactoryTest {

    /**
     * The file used by serviceLoader.
     */
    private static final File USED_PROVIDER = new File("target/test-classes/META-INF/services/org.jbromo.dao.jpa.container.common.IJpaProvider");

    /**
     * The file to used for oneProvider() test.
     */
    private static final File ONE_PROVIDER = new File("target/test-classes/META-INF/services/org.jbromo.dao.jpa.container.common.IJpaProvider1");

    /**
     * The file to used for twoProvider() test.
     */
    private static final File TWO_PROVIDER = new File("target/test-classes/META-INF/services/org.jbromo.dao.jpa.container.common.IJpaProvider2");

    /**
     * Initialize test.
     * @throws IOException exceptin.
     */
    @Before
    public void setUp() throws IOException {
        if (USED_PROVIDER.exists()) {
            USED_PROVIDER.delete();
        }
    }

    /**
     * Test without JPA provider.
     * @throws IOException exception.
     */
    @Test
    public void noProvider() throws IOException {
        JpaProviderFactory.getInstance().reload();
        Assert.assertNull(JpaProviderFactory.getInstance().getImplementation());
    }

    /**
     * Test with one JPA provider declared.
     * @throws IOException exception.
     */
    @Test
    public void oneProvider() throws IOException {
        FileUtils.copyFile(ONE_PROVIDER, USED_PROVIDER);
        JpaProviderFactory.getInstance().reload();
        Assert.assertNotNull(JpaProviderFactory.getInstance().getImplementation());
        Assert.assertEquals(JpaFirstProvider.class, JpaProviderFactory.getInstance().getImplementation().getClass());
    }

    /**
     * Test with two JPA provider declared.
     * @throws IOException exception.
     */
    @Test
    public void twoProvider() throws IOException {
        FileUtils.copyFile(TWO_PROVIDER, USED_PROVIDER);
        JpaProviderFactory.getInstance().reload();
        Assert.assertNotNull(JpaProviderFactory.getInstance().getImplementation());
        Assert.assertEquals(JpaFirstProvider.class, JpaProviderFactory.getInstance().getImplementation().getClass());
    }

}
