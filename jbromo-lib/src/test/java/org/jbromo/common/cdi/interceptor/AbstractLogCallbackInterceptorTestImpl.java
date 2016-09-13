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

import javax.inject.Inject;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.test.logger.MockLogger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Define JUnit LogCallbackInterceptor Test.
 * @author qjafcunuas
 */
public abstract class AbstractLogCallbackInterceptorTestImpl {

    /**
     * An intercepted bean.
     */
    @Inject
    private LogCallbackInterceptorBean bean;

    /**
     * The mocked logger.
     */
    private MockLogger mockLogger;

    /**
     * Define logs level used for tests.
     * @author qjafcunuas
     *
     */
    public enum Level {
        /** Define the default level. */
        DEFAULT,
        /** Define the info level. */
        INFO,
        /** Define the debug level. */
        DEBUG,
        /** Define the trace level. */
        TRACE;
    }

    /**
     * Run before executing each test.
     */
    @Before
    public void beforeTest() {
        Assert.assertNotNull(this.bean);
        this.mockLogger = MockLogger.mock(LogCallbackInterceptor.class);
        this.mockLogger.getLogged().clear();
    }

    /**
     * Run after each test.
     */
    @After
    public void afterTest() {
        MockLogger.unmock(LogCallbackInterceptor.class);
        loadLogConfig(Level.DEFAULT);
    }

    /**
     * Load log configuration.
     * @param level the level configuration.
     */
    protected abstract void loadLogConfig(Level level);

    /**
     * Run the test.
     */
    @Test
    public void info() {
        loadLogConfig(Level.INFO);
        this.bean.run();
        Assert.assertTrue(this.mockLogger.getLogged().isEmpty());
    }

    /**
     * Run the test.
     */
    @Test
    public void debug() {
        loadLogConfig(Level.DEBUG);
        this.bean.run();
        Assert.assertEquals(IntegerUtil.INT_1, this.mockLogger.getLogged().size());
    }

    /**
     * Run the test.
     */
    @Test
    public void trace() {
        loadLogConfig(Level.TRACE);
        this.bean.run();
        Assert.assertEquals(IntegerUtil.INT_2, this.mockLogger.getLogged().size());
    }

}
