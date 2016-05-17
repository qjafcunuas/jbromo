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
package org.jbromo.dao.jpa.query.jpql.from;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit JoinOperator class.
 * @author qjafcunuas
 */
public class JoinOperatorTest {

    /**
     * Test enum.
     */
    @Test
    public void test() {
        test(JoinOperator.INNER_JOIN, "inner join ", JoinOperator.INNER_JOIN);
        test(JoinOperator.INNER_JOIN_FETCH, "inner join fetch ", JoinOperator.INNER_JOIN);
        test(JoinOperator.LEFT_JOIN, "left join ", JoinOperator.LEFT_JOIN);
        test(JoinOperator.LEFT_JOIN_FETCH, "left join fetch ", JoinOperator.LEFT_JOIN);
        test(JoinOperator.RIGHT_JOIN, "right join ", JoinOperator.RIGHT_JOIN);
        test(JoinOperator.RIGHT_JOIN_FETCH, "right join fetch ", JoinOperator.RIGHT_JOIN);
    }

    /**
     * Test an operator.
     * @param join the operator to test.
     * @param expression the operator expression value.
     * @param withoutFetch the without fetch operator value.
     */
    private void test(final JoinOperator join, final String expression, final JoinOperator withoutFetch) {
        Assert.assertNotNull(join.getExpression());
        Assert.assertEquals(join.getExpression(), expression);
        Assert.assertNotNull(join.getWithoutFetch());
        Assert.assertEquals(join.getWithoutFetch(), withoutFetch);
    }
}
