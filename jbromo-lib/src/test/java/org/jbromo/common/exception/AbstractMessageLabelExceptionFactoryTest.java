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
package org.jbromo.common.exception;

import org.jbromo.common.i18n.IMessageKey;
import org.jbromo.common.invocation.ParameterizedTypeUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Define JUnit MessageLabelExceptionFactory class.
 * @param <E> the exception type.
 * @author qjafcunuas
 */
public abstract class AbstractMessageLabelExceptionFactoryTest<E extends MessageLabelException> {

    /**
     * Return the factory instance to test.
     * @return the factory instance to test.
     */
    protected abstract IMessageLabelExceptionFactory<E> getFactoryInstance();

    /**
     * The element class.
     */
    private final Class<E> elementClass = ParameterizedTypeUtil.getClass(this, 0);

    /**
     * Test getInstance method.
     */
    @Test
    public void getInstance() {
        Assert.assertNotNull(getFactoryInstance());
        Assert.assertEquals(getFactoryInstance(), getFactoryInstance());
    }

    /**
     * Test newInstance method.
     */
    @Test
    public void newInstance() {
        newInstance(AbstractMessageLabelExceptionTest.MESSAGE_KEY, this.elementClass);
    }

    /**
     * Test newInstance method.
     * @param key the key to used.
     * @param exceptionClass the exception class type.
     */
    public void newInstance(final IMessageKey key, final Class<? extends E> exceptionClass) {
        // One constructor.
        Assert.assertNotNull(getFactoryInstance().newInstance(key));
        Assert.assertEquals(getFactoryInstance().newInstance(key).getClass(), exceptionClass);

        // One constructor.
        Assert.assertNotNull(getFactoryInstance().newInstance(key, AbstractMessageLabelExceptionTest.EXCEPTION));
        Assert.assertEquals(getFactoryInstance().newInstance(key, AbstractMessageLabelExceptionTest.EXCEPTION).getClass(), exceptionClass);

        // One constructor.
        Assert.assertNotNull(getFactoryInstance().newInstance(AbstractMessageLabelExceptionTest.getMessageLabel(key)));
        Assert.assertEquals(getFactoryInstance().newInstance(AbstractMessageLabelExceptionTest.getMessageLabel(key)).getClass(), exceptionClass);

        // One constructor.
        Assert.assertNotNull(getFactoryInstance().newInstance(AbstractMessageLabelExceptionTest.getMessageLabel(key),
                                                              AbstractMessageLabelExceptionTest.EXCEPTION));
        Assert.assertEquals(getFactoryInstance()
                .newInstance(AbstractMessageLabelExceptionTest.getMessageLabel(key), AbstractMessageLabelExceptionTest.EXCEPTION).getClass(),
                            exceptionClass);
    }

    /**
     * Test getExceptionClass method.
     */
    @Test
    public void getExceptionClass() {
        Assert.assertNotNull(getFactoryInstance().getExceptionClass());
        Assert.assertEquals(getFactoryInstance().getExceptionClass(), this.elementClass);
    }

}
