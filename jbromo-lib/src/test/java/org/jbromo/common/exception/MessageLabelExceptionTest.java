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

import org.jbromo.common.i18n.MessageKey;
import org.jbromo.common.i18n.MessageLabel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Define JUnit MessageLabelException class.
 * @author qjafcunuas
 */
public class MessageLabelExceptionTest {

    /**
     * Test constructor(String).
     */
    @Test
    public void construtorString() {
    	final String message = "My message";
        final TestException exp = new TestException(message);
        Assert.assertNotNull(exp.getLabel());
        Assert.assertEquals(MessageKey.DEFAULT_MESSAGE, exp.getLabel().getKey());
        Assert.assertEquals(1, exp.getLabel().getParameters().size());
        Assert.assertEquals(message, exp.getLabel().getParameters().get(0));
    }

    /**
     * Test constructor(Throwable).
     */
    @Test
    public void construtorThrowable() {
        final Throwable t = new Throwable("my exception.");
        final TestException exp = new TestException(t);
        Assert.assertNotNull(exp.getLabel());
        Assert.assertEquals(MessageKey.DEFAULT_MESSAGE, exp.getLabel().getKey());
        Assert.assertEquals(1, exp.getLabel().getParameters().size());
        Assert.assertEquals(t, exp.getLabel().getParameters().get(0));
    }

    /**
     * Test constructor(String,Throwable).
     */
    @Test
    public void construtorStringThrowable() {
    	final String message = "My message";
        final Throwable t = new Throwable("my exception.");
        final TestException exp = new TestException(message, t);
        Assert.assertNotNull(exp.getLabel());
        Assert.assertEquals(MessageKey.DEFAULT_MESSAGE, exp.getLabel().getKey());
        Assert.assertEquals(2, exp.getLabel().getParameters().size());
        Assert.assertEquals(message, exp.getLabel().getParameters().get(0));
        Assert.assertEquals(t, exp.getLabel().getParameters().get(1));
    }

    /**
     * Test constructor(key).
     */
    @Test
    public void construtorKey() {
        final TestException exp = new TestException(MessageKey.DEFAULT_MESSAGE);
        Assert.assertNotNull(exp.getLabel());
        Assert.assertEquals(MessageKey.DEFAULT_MESSAGE, exp.getLabel().getKey());
    }

    /**
     * Test constructor(label).
     */
    @Test
    public void construtorLabel() {
        final MessageLabel label = new MessageLabel(MessageKey.DEFAULT_MESSAGE);
        final TestException exp = new TestException(label);
        Assert.assertNotNull(exp.getLabel());
        Assert.assertEquals(label, exp.getLabel());
    }

    /**
     * Test constructor(key, throwable).
     */
    @Test
    public void construtorKeyThrowable() {
        final Throwable t = new Throwable("my message");
        final TestException exp = new TestException(MessageKey.DEFAULT_MESSAGE, t);
        Assert.assertNotNull(exp.getLabel());
        Assert.assertEquals(MessageKey.DEFAULT_MESSAGE, exp.getLabel().getKey());
        Assert.assertEquals(t, exp.getCause());
    }

    /**
     * Test constructor(label, throwable).
     */
    @Test
    public void construtorLabelThrowable() {
        MessageLabel label = new MessageLabel(MessageKey.DEFAULT_MESSAGE);

        // Null throwable
        TestException exp = new TestException(label, (Throwable) null);
        Assert.assertNotNull(exp.getLabel());
        Assert.assertEquals(label, exp.getLabel());
        Assert.assertTrue(exp.getLabel().getParameters().isEmpty());
        Assert.assertNull(exp.getCause());

        // No throwable parameter in label.
        final Throwable t = new Throwable("The message");
        exp = new TestException(label, t);
        Assert.assertNotNull(exp.getLabel());
        Assert.assertEquals(label, exp.getLabel());
        Assert.assertEquals(1, exp.getLabel().getParameters().size());
        Assert.assertEquals(t, exp.getLabel().getParameters().get(0));
        Assert.assertEquals(t, exp.getCause());

        // Throwable parameter in label.
        label = new MessageLabel(MessageKey.DEFAULT_MESSAGE, t);
        exp = new TestException(label, t);
        Assert.assertNotNull(exp.getLabel());
        Assert.assertEquals(label, exp.getLabel());
        Assert.assertEquals(1, exp.getLabel().getParameters().size());
        Assert.assertEquals(t, exp.getLabel().getParameters().get(0));
        Assert.assertEquals(t, exp.getCause());
    }

}
