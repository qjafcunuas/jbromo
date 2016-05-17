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
import org.jbromo.common.i18n.IMessageLabel;

/**
 * Exception for Test.
 * @author qjafcunuas
 */
public class TestException extends MessageLabelException {
    /**
     * serial version UID..
     */
    private static final long serialVersionUID = 1223992049903172753L;

    /**
     * Constructor with message field.
     * @param key the message key.
     */
    TestException(final IMessageKey key) {
        super(key);
    }

    /**
     * Constructor with message label.
     * @param label the message label.
     */
    TestException(final IMessageLabel label) {
        super(label);
    }

    /**
     * Constructor with message field.
     * @param key the message key.
     * @param cause the exception
     */
    TestException(final IMessageKey key, final Throwable cause) {
        super(key, cause);
    }

    /**
     * Constructor with message label.
     * @param label the message label.
     * @param cause the cause.
     */
    TestException(final IMessageLabel label, final Throwable cause) {
        super(label, cause);
    }

}
