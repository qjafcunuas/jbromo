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
package org.jbromo.common.cdi.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.ClassUtil;
import org.jbromo.common.cdi.annotation.CatchException;
import org.jbromo.common.exception.IMessageLabelExceptionFactory;
import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.common.exception.MessageLabelExceptionFactoryFactory;
import org.jbromo.common.i18n.MessageKey;
import org.jbromo.common.invocation.AnnotationUtil;

/**
 * Define CatchException interceptor implementation.
 *
 * @author qjafcunuas
 *
 */
@CatchException(value = IMessageLabelExceptionFactory.class)
@Interceptor
@Slf4j
public class CatchExceptionInterceptor {

    /**
     * Intercept all method called for logging.
     *
     * @param ctx
     *            the context.
     * @return the return object.
     * @throws MessageLabelException
     *             exception.
     */
    @AroundInvoke
    public Object intercept(final InvocationContext ctx)
            throws MessageLabelException {
        try {
            return ctx.proceed();
        } catch (final MessageLabelException e) {
            final Class<IMessageLabelExceptionFactory<MessageLabelException>> factoryClass = getExceptionClass(ctx);
            throw getException(factoryClass, e);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            final Class<IMessageLabelExceptionFactory<MessageLabelException>> factoryClass = getExceptionClass(ctx);
            throw getException(factoryClass, e);
        }
    }

    /**
     * Return the exception class to throw.
     *
     * @param ctx
     *            the invocation context.
     * @return the exception class to throw.
     */
    @SuppressWarnings("unchecked")
    private Class<IMessageLabelExceptionFactory<MessageLabelException>> getExceptionClass(
            final InvocationContext ctx) {
        final CatchException annotation = AnnotationUtil.getAnnotation(ctx,
                CatchException.class);
        if (annotation == null) {
            log.warn("Cannot find CatchException annotation");
            return null;
        }
        return (Class<IMessageLabelExceptionFactory<MessageLabelException>>) annotation
                .value();
    }

    /**
     * Throw a new exception.
     *
     * @param factoryClass
     *            the factory exception class.
     * @param exp
     *            the current exception.
     * @return the new EJB exception.
     */
    private MessageLabelException getException(
            final Class<IMessageLabelExceptionFactory<MessageLabelException>> factoryClass,
            final MessageLabelException exp) {
        final IMessageLabelExceptionFactory<MessageLabelException> factory = MessageLabelExceptionFactoryFactory
                .getInstance().getInstance(factoryClass);
        if (ClassUtil.isInstance(exp, factory.getExceptionClass())) {
            return exp;
        } else {
            return factory.newInstance(exp.getLabel(), exp);
        }
    }

    /**
     * Throw a new exception.
     *
     * @param factoryClass
     *            the factory exception class.
     * @param exp
     *            the current exception.
     * @return the new EJB exception.
     */
    private MessageLabelException getException(
            final Class<IMessageLabelExceptionFactory<MessageLabelException>> factoryClass,
            final Exception exp) {
        final IMessageLabelExceptionFactory<MessageLabelException> factory = MessageLabelExceptionFactoryFactory
                .getInstance().getInstance(factoryClass);
        return factory.newInstance(MessageKey.DEFAULT_MESSAGE, exp);
    }

}
