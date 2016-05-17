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
package org.jbromo.common.i18n;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jbromo.common.ArrayUtil;
import org.jbromo.common.ListUtil;
import org.jbromo.common.exception.AbstractMessageLabelExceptionTest;
import org.junit.Assert;
import org.junit.Test;

import lombok.Getter;

/**
 * Define JUnit MessageLabel class.
 * @author qjafcunuas
 */
public class MessageLabelTest {

    /**
     * A message key for test.
     */
    public static final IMessageKey MESSAGE_KEY = new IMessageKey() {

        /**
         * serialVersionUID.
         */
        private static final long serialVersionUID = 5202980546725541235L;

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
        private final List<Serializable> parameters = new ArrayList<Serializable>();

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
     * An exception for test.
     */
    public static final Exception EXCEPTION = new Exception("The test exception");

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        MessageLabel ml;
        final List<Serializable> parameters = ListUtil.toList((Serializable) 1, 2);

        // No parameters.
        ml = new MessageLabel();
        Assert.assertNotNull(ml);
        Assert.assertNull(ml.getKey());
        Assert.assertNotNull(ml.getParameters());
        Assert.assertTrue(ml.getParameters().isEmpty());
        Assert.assertEquals(ml.getSeverity(), MessageSeverity.ERROR);

        // Key parameter
        ml = new MessageLabel(MESSAGE_KEY);
        Assert.assertNotNull(ml);
        Assert.assertEquals(ml.getKey(), MESSAGE_KEY);
        Assert.assertNotNull(ml.getParameters());
        Assert.assertTrue(ml.getParameters().isEmpty());
        Assert.assertEquals(ml.getSeverity(), MessageSeverity.ERROR);

        // Key and parameters collection
        ml = new MessageLabel(MESSAGE_KEY, parameters);
        Assert.assertNotNull(ml);
        Assert.assertEquals(ml.getKey(), MESSAGE_KEY);
        Assert.assertNotNull(ml.getParameters());
        Assert.assertFalse(ml.getParameters().isEmpty());
        Assert.assertTrue(ListUtil.containsAll(ml.getParameters(), parameters, true));
        Assert.assertEquals(ml.getParameters().get(0), 1);
        Assert.assertEquals(ml.getParameters().get(1), 2);
        Assert.assertEquals(ml.getSeverity(), MessageSeverity.ERROR);

        // Key and severity
        ml = new MessageLabel(MESSAGE_KEY, MessageSeverity.INFO);
        Assert.assertNotNull(ml);
        Assert.assertEquals(ml.getKey(), MESSAGE_KEY);
        Assert.assertNotNull(ml.getParameters());
        Assert.assertTrue(ml.getParameters().isEmpty());
        Assert.assertEquals(ml.getSeverity(), MessageSeverity.INFO);

        // Key and parameters array
        ml = new MessageLabel(MESSAGE_KEY, ArrayUtil.toArray(parameters, Serializable.class));
        Assert.assertNotNull(ml);
        Assert.assertEquals(ml.getKey(), MESSAGE_KEY);
        Assert.assertNotNull(ml.getParameters());
        Assert.assertFalse(ml.getParameters().isEmpty());
        Assert.assertTrue(ListUtil.containsAll(ml.getParameters(), parameters, true));
        Assert.assertEquals(ml.getParameters().get(0), 1);
        Assert.assertEquals(ml.getParameters().get(1), 2);
        Assert.assertEquals(ml.getSeverity(), MessageSeverity.ERROR);

        // Key, severity and parameters collection
        ml = new MessageLabel(MESSAGE_KEY, MessageSeverity.INFO, parameters);
        Assert.assertNotNull(ml);
        Assert.assertEquals(ml.getKey(), MESSAGE_KEY);
        Assert.assertNotNull(ml.getParameters());
        Assert.assertFalse(ml.getParameters().isEmpty());
        Assert.assertTrue(ListUtil.containsAll(ml.getParameters(), parameters, true));
        Assert.assertEquals(ml.getParameters().get(0), 1);
        Assert.assertEquals(ml.getParameters().get(1), 2);
        Assert.assertEquals(ml.getSeverity(), MessageSeverity.INFO);

        // Key, severity and parameters array
        ml = new MessageLabel(MESSAGE_KEY, MessageSeverity.INFO, ArrayUtil.toArray(parameters, Serializable.class));
        Assert.assertNotNull(ml);
        Assert.assertEquals(ml.getKey(), MESSAGE_KEY);
        Assert.assertNotNull(ml.getParameters());
        Assert.assertFalse(ml.getParameters().isEmpty());
        Assert.assertTrue(ListUtil.containsAll(ml.getParameters(), parameters, true));
        Assert.assertEquals(ml.getParameters().get(0), 1);
        Assert.assertEquals(ml.getParameters().get(1), 2);
        Assert.assertEquals(ml.getSeverity(), MessageSeverity.INFO);

    }
}
