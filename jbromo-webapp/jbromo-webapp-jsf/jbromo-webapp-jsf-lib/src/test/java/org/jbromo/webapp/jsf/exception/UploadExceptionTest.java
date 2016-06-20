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
package org.jbromo.webapp.jsf.exception;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.i18n.MessageKey;
import org.junit.Assert;
import org.junit.Test;

/**
 * Define JUnit UploadException class.
 * @author qjafcunuas
 */
public class UploadExceptionTest {

    /**
     * Test constructor() method.
     */
    @Test
    public void constructorString() {
        final UploadException exp = new UploadException();
        Assert.assertNotNull(exp.getLabel());
        Assert.assertEquals(MessageKey.ERROR_DOWNLOAD_FILE, exp.getLabel().getKey());
        Assert.assertEquals(IntegerUtil.INT_0, exp.getLabel().getParameters().size());
    }

}
