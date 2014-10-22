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

import org.jbromo.common.exception.IMessageLabelExceptionFactory;
import org.jbromo.common.exception.MessageLabelExceptionFactoryFactory;
import org.jbromo.common.i18n.IMessageKey;
import org.jbromo.common.i18n.IMessageLabel;
import org.jbromo.common.i18n.MessageKey;

/**
 * Define the DaoException Factory implementation.
 *
 * @author qjafcunuas
 *
 */
public final class DaoExceptionFactory implements
        IMessageLabelExceptionFactory<DaoException> {

    /**
     * The singleton.
     */
    private static final DaoExceptionFactory INSTANCE = new DaoExceptionFactory();

    /**
     * Static initialization.
     */
    static {
        MessageLabelExceptionFactoryFactory.getInstance().addFactory(INSTANCE);
    }

    /**
     * Default constructor.
     */
    private DaoExceptionFactory() {
        super();
    }

    /**
     * Return the singleton instance.
     *
     * @return the instance.
     */
    public static DaoExceptionFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public DaoException newInstance(final IMessageKey key) {
        if (MessageKey.ENTITY_VALIDATION_ERROR.equals(key)) {
            return new DaoValidationException();
        } else {
            return new DaoException(key);
        }
    }

    @Override
    public DaoException newInstance(final IMessageKey key,
            final Throwable cause) {
        if (MessageKey.ENTITY_VALIDATION_ERROR.equals(key)) {
            return new DaoValidationException(cause);
        } else {
            return new DaoException(key, cause);
        }
    }

    @Override
    public DaoException newInstance(final IMessageLabel label) {
        if (MessageKey.ENTITY_VALIDATION_ERROR.equals(label.getKey())) {
            return new DaoValidationException(label.getParameters());
        } else {
            return new DaoException(label);
        }
    }

    @Override
    public DaoException newInstance(final IMessageLabel label,
            final Throwable cause) {
        if (MessageKey.ENTITY_VALIDATION_ERROR.equals(label.getKey())) {
            return new DaoValidationException(label.getParameters(), cause);
        } else {
            return new DaoException(label, cause);
        }
    }

    @Override
    public Class<DaoException> getExceptionClass() {
        return DaoException.class;
    }

}
