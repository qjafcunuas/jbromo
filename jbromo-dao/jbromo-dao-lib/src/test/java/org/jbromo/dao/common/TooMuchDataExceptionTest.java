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
package org.jbromo.dao.common;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.i18n.MessageKey;
import org.jbromo.dao.common.exception.TooMuchDataException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Define JUnit TooMuchDataException class.
 * @author qjafcunuas
 */
public class TooMuchDataExceptionTest {

    /**
     * Test constructor(int) method.
     */
    @Test
    public void constructorString() {
        final int max = IntegerUtil.INT_100;
        final TooMuchDataException exp = new TooMuchDataException(max);
        Assert.assertNotNull(exp.getLabel());
        Assert.assertEquals(MessageKey.TOO_MUCH_RESULT_MESSAGE, exp.getLabel().getKey());
        Assert.assertEquals(IntegerUtil.INT_1, exp.getLabel().getParameters().size());
        Assert.assertEquals(max, exp.getLabel().getParameters().get(0));
    }

}
