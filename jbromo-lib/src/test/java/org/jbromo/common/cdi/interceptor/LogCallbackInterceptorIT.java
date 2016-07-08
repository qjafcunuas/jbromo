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
package org.jbromo.common.cdi.interceptor;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.beans10.BeansDescriptor;
import org.jbromo.common.CommonArquillianUtil;
import org.junit.runner.RunWith;

/**
 * Define JUnit LogCallbackInterceptor Integration Test.
 * @author qjafcunuas
 */
@RunWith(Arquillian.class)
public class LogCallbackInterceptorIT extends AbstractLogCallbackInterceptorTestImpl {

    /**
     * Built an archive to deploy.
     * @return the archive to deploy.
     */
    @Deployment
    public static JavaArchive createTestArchive() {
        final BeansDescriptor beans = Descriptors.create(BeansDescriptor.class).getOrCreateInterceptors()
                .clazz(LogCallbackInterceptor.class.getName()).up();

        final JavaArchive arch = CommonArquillianUtil.createTestArchive(LogCallbackInterceptorIT.class, beans);
        arch.deleteClass(TransactionalInterceptor.class);

        return arch;
    }

}
