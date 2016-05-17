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
package org.jbromo.common.invocation;

import org.junit.Assert;
import org.junit.Test;

/**
 * Define JUnit InvocationException class.
 * @author qjafcunuas
 */
public class InvocationExceptionTest {

    /**
     * Define a message exception.
     */
    private static final String MESSAGE = "The message";

    /**
     * Define an exception.
     */
    private static final Exception EXCEPTION = new Exception("The exception");

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        // message.
        InvocationException ie = new InvocationException(MESSAGE);
        Assert.assertNotNull(ie);
        Assert.assertNotNull(ie.getMessage());
        Assert.assertEquals(ie.getMessage(), MESSAGE);

        // exception.
        ie = new InvocationException(EXCEPTION);
        Assert.assertNotNull(ie);
        Assert.assertNotNull(ie.getMessage());
        Assert.assertNotNull(ie.getCause());
        Assert.assertEquals(ie.getCause(), EXCEPTION);

        // exception.
        ie = new InvocationException(MESSAGE, EXCEPTION);
        Assert.assertNotNull(ie);
        Assert.assertNotNull(ie.getMessage());
        Assert.assertEquals(ie.getMessage(), MESSAGE);
        Assert.assertNotNull(ie.getCause());
        Assert.assertEquals(ie.getCause(), EXCEPTION);
    }

}
