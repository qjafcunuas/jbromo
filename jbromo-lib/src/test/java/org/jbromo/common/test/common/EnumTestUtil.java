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

import java.lang.reflect.Method;

import org.jbromo.common.EnumUtil;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.junit.Assert;
import org.junit.Ignore;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Enum test util class.
 * @author qjafcunuas
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Ignore
public final class EnumTestUtil {

    /**
     * Tests valueOf method.
     * @param <E> the enum type.
     * @param enumClass the enum class.
     */
    public static <E extends Enum<E>> void valueOf(final Class<E> enumClass) {
        E value;
        try {
            final Method method = InvocationUtil.getMethod(enumClass, "valueOf", String.class);
            for (final E one : EnumUtil.getValues(enumClass)) {

                value = InvocationUtil.invokeMethod(method, one, one.name());
                Assert.assertEquals(one, value);
            }
        } catch (final InvocationException e) {
            Assert.fail(e.getMessage());
        }
    }

}
