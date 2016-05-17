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

import org.jbromo.common.ClassUtil;
import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.common.exception.TestException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Define JUnit CatchExceptionInterceptor Test.
 * @author qjafcunuas
 */
public abstract class AbstractCatchExceptionInterceptorTestImpl {

    /**
     * An intercepted bean.
     */
    @Inject
    private CatchExceptionInterceptorMessageLabelExceptionBean messageLabelBean;

    /**
     * An intercepted bean.
     */
    @Inject
    private CatchExceptionInterceptorTestExceptionBean testBean;

    /**
     * Test before executing others.
     */
    @Before
    public void beforeTest() {
        Assert.assertNotNull(this.messageLabelBean);
        Assert.assertNotNull(this.testBean);
    }

    /**
     * Test called method throwing an exception.
     */
    @Test
    public void throwException() {
        // MessageLabelException Bean
        try {
            this.messageLabelBean.throwException();
            Assert.fail("Method should thrown an exception!");
        } catch (final Exception e) {
            Assert.assertTrue(ClassUtil.isInstance(e, MessageLabelException.class));
            Assert.assertNotNull(e.getCause());
            Assert.assertEquals(e.getCause(), CatchExceptionInterceptorMessageLabelExceptionBean.EXCEPTION);
        }
        // TestException Bean
        try {
            this.testBean.throwException();
            Assert.fail("Method should thrown an exception!");
        } catch (final Exception e) {
            Assert.assertTrue(ClassUtil.isInstance(e, TestException.class));
            Assert.assertNotNull(e.getCause());
            Assert.assertEquals(e.getCause(), CatchExceptionInterceptorTestExceptionBean.EXCEPTION);
        }
    }

    /**
     * Test called method throwing a MessageLabelException.
     */
    @Test
    public void throwMessageLabelException() {
        // MessageLabelException Bean
        try {
            this.messageLabelBean.throwMessageLabelException();
            Assert.fail("Method should thrown an exception!");
        } catch (final Exception e) {
            Assert.assertTrue(ClassUtil.isInstance(e, MessageLabelException.class));
            Assert.assertEquals(e, CatchExceptionInterceptorMessageLabelExceptionBean.MESSAGE_LABEL_EXCEPTION);
        }

        // TestException Bean
        try {
            this.testBean.throwMessageLabelException();
            Assert.fail("Method should thrown an exception!");
        } catch (final Exception e) {
            Assert.assertTrue(ClassUtil.isInstance(e, TestException.class));
            Assert.assertNotNull(e.getCause());
            Assert.assertEquals(e.getCause(), CatchExceptionInterceptorTestExceptionBean.MESSAGE_LABEL_EXCEPTION);
        }
    }

    /**
     * Test called method throwing a TestException.
     */
    @Test
    public void throwTestException() {
        try {
            // TestException Bean
            this.testBean.throwTestException();
            Assert.fail("Method should thrown an exception!");
        } catch (final Exception e) {
            Assert.assertTrue(ClassUtil.isInstance(e, TestException.class));
            Assert.assertEquals(e, CatchExceptionInterceptorTestExceptionBean.TEST_EXCEPTION);
        }
    }

    /**
     * Test called method not throwing exception.
     */
    @Test
    public void noException() {
        // MessageLabelException Bean
        try {
            this.messageLabelBean.noException();
        } catch (final Exception e) {
            Assert.fail("Method shouldn't thrown an exception!");
        }
        // TestException Bean
        try {
            this.testBean.noException();
        } catch (final Exception e) {
            Assert.fail("Method shouldn't thrown an exception!");
        }
    }

}
