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
package org.jbromo.common.cdi.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.jbromo.common.cdi.annotation.LogCallback;

import lombok.extern.slf4j.Slf4j;

/**
 * Define TraceCallback interceptor implementation.
 * @author qjafcunuas
 */
@LogCallback
@Interceptor
@Slf4j
public class LogCallbackInterceptor {
    /**
     * Intercept all method called for logging.
     * @param ctx the context.
     * @return the return object.
     * @throws Exception exception.
     */
    @AroundInvoke
    public Object intercept(final InvocationContext ctx) throws Exception {
        final String klass = ctx.getTarget().getClass().getName();
        final String method = ctx.getMethod().getName();
        final long time = System.currentTimeMillis();
        try {
            log.debug("Calling {}.{}", klass, method);
            return ctx.proceed();
        } finally {
            if (log.isTraceEnabled()) {
                final Object[] args = new Object[] {klass, method, ctx.getParameters(), System.currentTimeMillis() - time};
                log.trace("Processing time for {}.{}{} is {}ms", args);
            }
        }
    }

}
