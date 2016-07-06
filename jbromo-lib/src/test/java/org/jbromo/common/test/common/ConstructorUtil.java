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
package org.jbromo.common.test.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.jbromo.common.ClassUtil;
import org.jbromo.common.invocation.InvocationUtil;
import org.junit.Assert;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constructor util class.
 * @author qjafcunuas
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConstructorUtil {

    /**
     * Execute a private constructor.
     * @param <O> the object type.
     * @param objectClass the object class.
     */
    public static <O> void executePrivate(final Class<O> objectClass) {
        try {
            final Constructor<O> constructor = InvocationUtil.getConstructor(objectClass);
            if (Modifier.isPrivate(constructor.getModifiers())) {
                constructor.setAccessible(true);
                constructor.newInstance();
            } else {
                Assert.fail("Constructor is not private for class " + objectClass);
            }
        } catch (final InvocationTargetException e) {
            // Lombok UtilityClass annotation throw UnsupportedOperationException.
            if (!ClassUtil.isInstance(e.getTargetException(), UnsupportedOperationException.class)) {
                Assert.fail(e.getMessage());
            }
        } catch (final Exception e) {
            Assert.fail(e.getMessage());
        }
    }

}
