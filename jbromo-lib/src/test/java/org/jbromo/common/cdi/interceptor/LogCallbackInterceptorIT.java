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

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
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
     * The file to configure logback as debug for interceptor.
     */
    private static final String LOG_DEBUG = "log4j_LogCallbackInterceptor_debug.xml";

    /**
     * The file to configure logback as debug for interceptor.
     */
    private static final String LOG_TRACE = "log4j_LogCallbackInterceptor_trace.xml";

    /**
     * The file to configure logback as debug for interceptor.
     */
    private static final String LOG_DEFAULT = "log4j.xml";

    /**
     * Built an archive to deploy.
     * @return the archive to deploy.
     */
    @Deployment
    public static Archive<?> createTestArchive() {
        final BeansDescriptor beans = Descriptors.create(BeansDescriptor.class).getOrCreateInterceptors()
                .clazz(LogCallbackInterceptor.class.getName()).up();
        final WebArchive war = CommonArquillianUtil.createWar(LogCallbackInterceptorIT.class, beans);
        war.addAsResource(LOG_DEFAULT);
        war.addAsResource(LOG_DEBUG);
        war.addAsResource(LOG_TRACE);
        war.deleteClass(LogCallbackInterceptorTest.class);
        return war;
    }

    @Override
    protected void loadLogConfig(final Level level) {
        URL url;
        switch (level) {
            case DEBUG:
                url = this.getClass().getClassLoader().getResource(LOG_DEBUG);
                break;
            case TRACE:
                url = this.getClass().getClassLoader().getResource(LOG_TRACE);
                break;
            default:
                url = this.getClass().getClassLoader().getResource(LOG_DEFAULT);
                break;
        }
        org.apache.log4j.xml.DOMConfigurator.configure(url);

    }

}
