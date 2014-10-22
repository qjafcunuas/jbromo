/*
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
package org.jbromo.dao.common.exception;

import java.util.List;

import org.jbromo.common.i18n.MessageKey;
import org.jbromo.common.i18n.MessageLabel;

/**
 * Exception for DAO actions.
 *
 * @author qjafcunuas
 */
public class DaoValidationException extends DaoException {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 1223992049903172753L;

    /**
     * Default constructor.
     */
    DaoValidationException() {
        super(MessageKey.ENTITY_VALIDATION_ERROR);
    }

    /**
     * Constructor with message field.
     *
     * @param cause
     *            the exception
     */
    DaoValidationException(final Throwable cause) {
        super(MessageKey.ENTITY_VALIDATION_ERROR, cause);
    }

    /**
     * Constructor with message field.
     *
     * @param parameters
     *            the message parameters
     */
    DaoValidationException(final List<Object> parameters) {
        super(new MessageLabel(MessageKey.ENTITY_VALIDATION_ERROR, parameters));
    }

    /**
     * Constructor using fields.
     *
     * @param parameters
     *            the message parameters
     * @param cause
     *            the exception
     */
    DaoValidationException(final List<Object> parameters,
            final Throwable cause) {
        super(new MessageLabel(MessageKey.ENTITY_VALIDATION_ERROR, parameters),
                cause);
    }

}
