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
package org.jbromo.common.exception;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * Define the MessageLabelException Factory implementation.
 *
 * @author qjafcunuas
 *
 */
@Slf4j
public final class MessageLabelExceptionFactoryFactory {

    /**
     * The singleton instances.
     */
    private static final Map<Class<IMessageLabelExceptionFactory<MessageLabelException>>, IMessageLabelExceptionFactory<MessageLabelException>> INSTANCES = new HashMap<Class<IMessageLabelExceptionFactory<MessageLabelException>>, IMessageLabelExceptionFactory<MessageLabelException>>();

    /**
     * The singleton instance.
     */
    private static final MessageLabelExceptionFactoryFactory INSTANCE = new MessageLabelExceptionFactoryFactory();

    /**
     * Default constructor.
     */
    private MessageLabelExceptionFactoryFactory() {
        super();
    }

    /**
     * Return the singleton instance.
     *
     * @return the instance.
     */
    public static MessageLabelExceptionFactoryFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Return the singleton instance.
     *
     * @param classFactory
     *            the class to get the factory.
     * @return the instance.
     */
    public IMessageLabelExceptionFactory<MessageLabelException> getInstance(
            final Class<? extends IMessageLabelExceptionFactory<?>> classFactory) {
        IMessageLabelExceptionFactory<MessageLabelException> factory = INSTANCES
                .get(classFactory);
        if (factory == null) {
            try {
                // Force class loading.
                Class.forName(classFactory.getCanonicalName());
            } catch (final ClassNotFoundException e) {
                log.error("Cannot found class", e);
            }
            factory = INSTANCES.get(classFactory);
        }
        return factory;
    }

    /**
     * Add a factory.
     *
     * @param factory
     *            the factory to add.
     */
    @SuppressWarnings("unchecked")
    public void addFactory(
            final IMessageLabelExceptionFactory<? extends MessageLabelException> factory) {
        INSTANCES
                .put((Class<IMessageLabelExceptionFactory<MessageLabelException>>) factory
                        .getClass(),
                        (IMessageLabelExceptionFactory<MessageLabelException>) factory);
    }
}
