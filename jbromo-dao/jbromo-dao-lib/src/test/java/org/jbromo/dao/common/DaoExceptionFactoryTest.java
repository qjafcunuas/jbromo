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
package org.jbromo.dao.common;

import org.jbromo.common.exception.AbstractMessageLabelExceptionFactoryTest;
import org.jbromo.common.i18n.MessageKey;
import org.jbromo.dao.common.exception.DaoException;
import org.jbromo.dao.common.exception.DaoExceptionFactory;
import org.jbromo.dao.common.exception.DaoValidationException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Define JUnit DaoExceptionFactory class.
 *
 * @author qjafcunuas
 *
 */
public class DaoExceptionFactoryTest extends
        AbstractMessageLabelExceptionFactoryTest<DaoException> {

    @Override
    protected DaoExceptionFactory getFactoryInstance() {
        return DaoExceptionFactory.getInstance();
    }

    /**
     * Test newInstance method.
     */
    @Test
    public void newInstanceValidation() {
        super.newInstance(MessageKey.ENTITY_VALIDATION_ERROR,
                DaoValidationException.class);

        try {
            throw getFactoryInstance().newInstance(
                    MessageKey.ENTITY_VALIDATION_ERROR);
        } catch (final DaoValidationException e) {
            Assert.assertTrue(true);
        } catch (final DaoException e) {
            Assert.fail("newInstance should return a DaoValidationException class");
        }

    }
}