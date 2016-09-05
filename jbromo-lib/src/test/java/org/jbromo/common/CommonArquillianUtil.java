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
package org.jbromo.common;

import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.beans10.BeansDescriptor;
import org.jbromo.common.test.arquillian.ArquillianUtil;

/**
 * Default implementation for bromo-common arquillian test.
 * @author qjafcunuas
 */
public final class CommonArquillianUtil {

    /**
     * Default constructor.
     */
    private CommonArquillianUtil() {
        super();
    }

    /**
     * Build a jar archive to deploy for arquillian test.
     * @param classToTest the class to test.
     * @param beans the beans descriptor.
     * @return the archive to deploy.
     */
    public static JavaArchive createJar(final Class<?> classToTest, final BeansDescriptor beans) {
        return ArquillianUtil.createJar(classToTest, beans, CommonArquillianUtil.class.getPackage());
    }

    /**
     * Build a war archive to deploy for arquillian test.
     * @param classToTest the class to test.
     * @param beans the beans descriptor.
     * @return the archive to deploy.
     */
    public static WebArchive createWar(final Class<?> classToTest, final BeansDescriptor beans) {
        return ArquillianUtil.createWar(classToTest, beans, CommonArquillianUtil.class.getPackage());
    }

}
