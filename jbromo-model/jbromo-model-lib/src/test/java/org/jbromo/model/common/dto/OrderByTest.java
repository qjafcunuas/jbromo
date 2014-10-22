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
package org.jbromo.model.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.invocation.InvocationException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for the AbstractOrderBy class.
 *
 * @author qjafcunuas
 *
 */
@Slf4j
public class OrderByTest {

    /**
     * Private class for test.
     *
     * @author qjafcunuas
     *
     */
    private class MyEntity implements Serializable {
        /**
         * serial version uid.
         */
        private static final long serialVersionUID = -7483137016662485160L;
        /**
         * The name.
         */
        @Getter
        private String name;
    }

    /**
     * Test constructor.
     */
    @Test
    public void test() {
        try {
            final AbstractOrderBy<OrderByTest.MyEntity> orderBy = new AbstractOrderBy<OrderByTest.MyEntity>(
                    "name") {
                /**
                 * serial version uid.
                 */
                private static final long serialVersionUID = -9054752217041506423L;

                @Override
                public SORT getSort() {
                    return null;
                }
            };
            Assert.assertEquals(orderBy.getOrder(), "name");
        } catch (final InvocationException e) {
            Assert.fail(e.getMessage());
        }
        try {
            new AbstractOrderBy<OrderByTest.MyEntity>("unvalid") {
                /**
                 * serial version uid.
                 */
                private static final long serialVersionUID = -9054752217041506424L;

                @Override
                public SORT getSort() {
                    return null;
                }
            };
            Assert.fail("Field unvalid should not be found!");
        } catch (final InvocationException e) {
            log.trace("Field unvalid haven't be found! It's normal!", e);
        }

    }
}
