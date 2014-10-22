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
package org.jbromo.service.exception;

import org.jbromo.common.exception.IMessageLabelExceptionFactory;
import org.jbromo.common.exception.MessageLabelExceptionFactoryFactory;
import org.jbromo.common.i18n.IMessageKey;
import org.jbromo.common.i18n.IMessageLabel;

/**
 * Define the ServiceException Factory implementation.
 *
 * @author qjafcunuas
 *
 */
public final class ServiceExceptionFactory implements
        IMessageLabelExceptionFactory<ServiceException> {

    /**
     * The singleton.
     */
    private static final ServiceExceptionFactory INSTANCE = new ServiceExceptionFactory();

    /**
     * Static initialization.
     */
    static {
        MessageLabelExceptionFactoryFactory.getInstance().addFactory(INSTANCE);
    }

    /**
     * Default constructor.
     */
    private ServiceExceptionFactory() {
        super();
    }

    /**
     * Return the singleton instance.
     *
     * @return the instance.
     */
    public static ServiceExceptionFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public ServiceException newInstance(final IMessageKey key) {
        return new ServiceException(key);
    }

    @Override
    public ServiceException newInstance(final IMessageKey key,
            final Throwable cause) {
        return new ServiceException(key, cause);
    }

    @Override
    public ServiceException newInstance(final IMessageLabel label) {
        return new ServiceException(label);
    }

    @Override
    public ServiceException newInstance(final IMessageLabel label,
            final Throwable cause) {
        return new ServiceException(label, cause);
    }

    @Override
    public Class<ServiceException> getExceptionClass() {
        return ServiceException.class;
    }

}
