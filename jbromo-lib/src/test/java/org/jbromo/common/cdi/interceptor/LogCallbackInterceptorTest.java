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

import java.io.InputStream;

import org.jbromo.common.test.cdi.CdiRunner;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * Define JUnit LogCallbackInterceptor Test.
 * @author qjafcunuas
 */
@RunWith(CdiRunner.class)
public class LogCallbackInterceptorTest extends AbstractLogCallbackInterceptorTestImpl {

    /**
     * The file to configure logback as debug for interceptor.
     */
    private static final String LOG_DEBUG = "logback_LogCallbackInterceptor_debug.xml";

    /**
     * The file to configure logback as trace for interceptor.
     */
    private static final String LOG_TRACE = "logback_LogCallbackInterceptor_trace.xml";

    /**
     * The file to configure logback as info for interceptor.
     */
    private static final String LOG_INFO = "logback_LogCallbackInterceptor_info.xml";

    /**
     * The default file to configure logback.
     */
    private static final String LOG_DEFAULT = "logback.xml";

    @Override
    protected void loadLogConfig(final Level level) {
        final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.reset();
        final JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(loggerContext);
        InputStream in;
        try {
            switch (level) {
                case INFO:
                    in = this.getClass().getClassLoader().getResourceAsStream(LOG_INFO);
                    break;
                case DEBUG:
                    in = this.getClass().getClassLoader().getResourceAsStream(LOG_DEBUG);
                    break;
                case TRACE:
                    in = this.getClass().getClassLoader().getResourceAsStream(LOG_TRACE);
                    break;
                default:
                    in = this.getClass().getClassLoader().getResourceAsStream(LOG_DEFAULT);
                    break;
            }
            configurator.doConfigure(in);
        } catch (final JoranException e) {
            Assert.fail(e.getMessage());
        }
    }

}
