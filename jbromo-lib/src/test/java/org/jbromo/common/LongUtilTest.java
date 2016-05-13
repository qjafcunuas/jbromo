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
package org.jbromo.common;

import java.lang.reflect.Field;
import java.util.List;

import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.common.test.common.ConstructorUtil;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * JUnit IntegerUtil class.
 * @author qjafcunuas
 */
@Slf4j
public class LongUtilTest {

    /**
     * Define LONG prefix.
     */
    private static final String LONG_PREFIX = "LONG_";

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        ConstructorUtil.executePrivate(LongUtil.class);
    }

    /**
     * Test INT_xxx value.
     */
    @Test
    public void constant() {
        Long value;
        int pos;
        final List<Field> fields = InvocationUtil.getFields(IntegerUtil.class);
        for (final Field field : fields) {
            pos = field.getName().indexOf(LONG_PREFIX);
            if (pos >= 0) {
                value = Long.valueOf(field.getName().substring(pos + LONG_PREFIX.length()));
                try {
                    Assert.assertEquals(Long.valueOf(field.getLong(null)), value);
                } catch (final Exception e) {
                    log.error("Cannot read value " + field.getName(), e);
                    Assert.fail("Cannot read value for field " + field.getName());
                }
            }
        }
    }
}
