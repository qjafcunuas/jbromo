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

import java.io.Serializable;
import java.util.List;

import org.jbromo.common.ListUtil;
import org.jbromo.common.i18n.IMessageKey;
import org.jbromo.common.i18n.IMessageLabel;
import org.jbromo.common.i18n.MessageSeverity;
import org.junit.Assert;
import org.junit.Test;

import lombok.Getter;

/**
 * Define JUnit MessageLabelException class.
 * @author qjafcunuas
 */
public abstract class AbstractMessageLabelExceptionTest {

    /**
     * A message key for test.
     */
    public static final IMessageKey MESSAGE_KEY = new IMessageKey() {

        /**
         * serialVersionUID.
         */
        private static final long serialVersionUID = -900612107907927705L;

        @Override
        public String getKey() {
            return "message.key";
        }

    };

    /**
     * A message label for test.
     */
    public static final IMessageLabel MESSAGE_LABEL = new IMessageLabel() {

        /**
         * serial version UID.
         */
        private static final long serialVersionUID = 2270061156849502965L;

        /**
         * The label parameters.
         */
        @Getter
        private final List<Serializable> parameters = ListUtil.toList();

        @Override
        public IMessageKey getKey() {
            return AbstractMessageLabelExceptionTest.MESSAGE_KEY;
        }

        @Override
        public MessageSeverity getSeverity() {
            return MessageSeverity.ERROR;
        }

    };

    /**
     * Return a new message label.
     * @param key the key to used.
     * @return the message label.
     */
    public static IMessageLabel getMessageLabel(final IMessageKey key) {
        return new IMessageLabel() {

            /**
             * serial version UID.
             */
            private static final long serialVersionUID = 2270061156849502965L;

            /**
             * The label parameters.
             */
            @Getter
            private final List<Serializable> parameters = ListUtil.toList();

            @Override
            public IMessageKey getKey() {
                return key;
            }

            @Override
            public MessageSeverity getSeverity() {
                return MessageSeverity.ERROR;
            }

        };
    }

    /**
     * An exception for test.
     */
    public static final Exception EXCEPTION = new Exception("The test exception");

    /**
     * Return the factory to used.
     * @return the factory.
     */
    protected abstract IMessageLabelExceptionFactory<?> getFactory();

    /**
     * Test getInstance method.
     */
    @Test
    public void getInstance() {
        Assert.assertNotNull(getFactory());
        Assert.assertEquals(getFactory(), getFactory());
    }

    /**
     * Test constructor with key parameter.
     */
    @Test
    public void constructorKey() {
        final MessageLabelException myExp = getFactory().newInstance(MESSAGE_KEY);
        Assert.assertNotNull(myExp.getLabel());
        Assert.assertNotNull(myExp.getLabel().getKey());
        Assert.assertEquals(myExp.getLabel().getKey(), MESSAGE_KEY);
    }

    /**
     * Test constructor with key, exception parameters.
     */
    @Test
    public void constructorKeyExp() {
        final MessageLabelException myExp = getFactory().newInstance(MESSAGE_KEY, EXCEPTION);
        Assert.assertNotNull(myExp.getLabel());
        Assert.assertNotNull(myExp.getLabel().getKey());
        Assert.assertEquals(myExp.getLabel().getKey(), MESSAGE_KEY);
        Assert.assertEquals(ListUtil.getLast(myExp.getLabel().getParameters()), EXCEPTION);
    }

    /**
     * Test constructor with label parameter.
     */
    @Test
    public void constructorLabel() {
        MESSAGE_LABEL.getParameters().clear();
        final MessageLabelException myExp = getFactory().newInstance(MESSAGE_LABEL);
        Assert.assertNotNull(myExp.getLabel());
        Assert.assertEquals(myExp.getLabel(), MESSAGE_LABEL);
    }

    /**
     * Test constructor with label, exception parameters.
     */
    @Test
    public void constructorLabelExp() {
        // null cause
        MESSAGE_LABEL.getParameters().clear();
        MessageLabelException myExp = getFactory().newInstance(MESSAGE_LABEL, null);
        Assert.assertNotNull(myExp.getLabel());
        Assert.assertEquals(myExp.getLabel(), MESSAGE_LABEL);
        Assert.assertNull(myExp.getCause());
        Assert.assertTrue(myExp.getLabel().getParameters().isEmpty());

        /**
         * Not null cause.
         */
        MESSAGE_LABEL.getParameters().clear();
        myExp = getFactory().newInstance(MESSAGE_LABEL, EXCEPTION);
        Assert.assertNotNull(myExp.getLabel());
        Assert.assertEquals(myExp.getLabel(), MESSAGE_LABEL);
        Assert.assertEquals(myExp.getCause(), EXCEPTION);
        Assert.assertEquals(ListUtil.getLast(myExp.getLabel().getParameters()), EXCEPTION);

        /**
         * Not null cause.
         */
        MESSAGE_LABEL.getParameters().clear();
        MESSAGE_LABEL.getParameters().add(EXCEPTION);
        try {
            myExp = getFactory().newInstance(MESSAGE_LABEL, EXCEPTION);
            Assert.assertNotNull(myExp.getLabel());
            Assert.assertEquals(myExp.getLabel(), MESSAGE_LABEL);
            Assert.assertEquals(myExp.getCause(), EXCEPTION);
            Assert.assertEquals(ListUtil.getLast(myExp.getLabel().getParameters()), EXCEPTION);
            Assert.assertEquals(myExp.getLabel().getParameters().indexOf(EXCEPTION), myExp.getLabel().getParameters().size() - 1);
        } finally {
            MESSAGE_LABEL.getParameters().clear();
        }

    }
}
