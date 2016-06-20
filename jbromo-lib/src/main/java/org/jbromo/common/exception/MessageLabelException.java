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
import org.jbromo.common.i18n.MessageKey;
import org.jbromo.common.i18n.MessageLabel;

import lombok.Getter;

/**
 * Exception with message label.
 * @author qjafcunuas
 */
public class MessageLabelException extends Exception {
    /**
     * serial Version UID.
     */
    private static final long serialVersionUID = 1223992049903172753L;

    /**
     * The i18n label.
     */
    @Getter
    private final IMessageLabel label;

    /**
     * Constructor with message field.
     * @param cause the exception
     */
    protected MessageLabelException(final Throwable cause) {
        this(MessageKey.DEFAULT_MESSAGE, cause);
    }

    /**
     * Constructor with message field.
     * @param message the message.
     */
    protected MessageLabelException(final String message) {
        this(new MessageLabel(MessageKey.DEFAULT_MESSAGE, message));
    }

    /**
     * Constructor with message field.
     * @param key the message key.
     */
    protected MessageLabelException(final IMessageKey key) {
        this(new MessageLabel(key), (Throwable) null);
    }

    /**
     * Constructor with message label.
     * @param label the message label.
     */
    protected MessageLabelException(final IMessageLabel label) {
        this(label, (Throwable) null);
    }

    /**
     * Constructor with message field.
     * @param key the message key.
     * @param cause the exception
     */
    protected MessageLabelException(final IMessageKey key, final Throwable cause) {
        this(new MessageLabel(key), cause);
    }

    /**
     * Constructor with message label.
     * @param label the message label.
     * @param cause the cause.
     */
    protected MessageLabelException(final IMessageLabel label, final Throwable cause) {
        super(cause);
        this.label = label;
        if (cause != null && !this.label.getParameters().contains(cause)) {
            label.getParameters().add(cause);
        }
    }

}
