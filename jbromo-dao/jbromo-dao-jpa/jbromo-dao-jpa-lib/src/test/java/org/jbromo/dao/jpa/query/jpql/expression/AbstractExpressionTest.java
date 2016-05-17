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
package org.jbromo.dao.jpa.query.jpql.expression;

import org.jbromo.common.ObjectUtil;
import org.jbromo.common.StringUtil;
import org.jbromo.common.invocation.ParameterizedTypeUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Define JUnit AbstractExpression class.
 * @param <E> the expression to test.
 * @author qjafcunuas
 */
public abstract class AbstractExpressionTest<E extends AbstractExpression> {

    /**
     * Define the field name to used.
     */
    private static final String FIELD_NAME = "name";

    /**
     * The expression class.
     */
    private final Class<E> expressionClass = ParameterizedTypeUtil.getClass(this, 0);

    /**
     * Return the expression's operator.
     * @return the operator.
     */
    protected abstract String getOperator();

    /**
     * Return a new instance of the expression.
     * @return new instance.
     */
    protected E newInstance() {
        return ObjectUtil.newInstance(this.expressionClass, FIELD_NAME);
    }

    /**
     * Test constructor(String) method.
     */
    @Test
    public void constructor() {
        final E expression = newInstance();
        Assert.assertNotNull(expression);
    }

    /**
     * Test getField method.
     */
    @Test
    public void getField() {
        final E expression = newInstance();
        Assert.assertNotNull(expression);
        Assert.assertEquals(expression.getField(), FIELD_NAME);
    }

    /**
     * Test getOperator method.
     */
    @Test
    public void getOperatorTest() {
        Assert.assertEquals(newInstance().getOperator(), getOperator());
    }

    /**
     * Test build method.
     */
    @Test
    public void build() {
        final E expression = newInstance();
        final StringBuilder builder = new StringBuilder("Test expression ");
        expression.build(builder);
        Assert.assertEquals(builder.toString(),
                            "Test expression " + getOperator() + StringUtil.PARENTHESIS_OPEN + FIELD_NAME + StringUtil.PARENTHESIS_CLOSE);
    }
}
