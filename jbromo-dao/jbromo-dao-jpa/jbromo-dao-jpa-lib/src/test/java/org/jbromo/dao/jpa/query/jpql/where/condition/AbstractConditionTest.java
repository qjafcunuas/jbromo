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
package org.jbromo.dao.jpa.query.jpql.where.condition;

import java.util.List;

import org.jbromo.common.CollectionUtil;
import org.jbromo.common.ListUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.invocation.ParameterizedTypeUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit AbstractCondition class.
 * @author qjafcunuas
 * @param <C> the condition type.
 */
public abstract class AbstractConditionTest<C extends AbstractCondition<?>> {

    /**
     * The field name.
     */
    static final String FIELD_NAME = "name";

    /**
     * The condition class.
     */
    private final Class<C> conditionClass = ParameterizedTypeUtil.getClass(this, 0);

    /**
     * Return the operator.
     * @return the operator.
     */
    protected abstract String getOperator();

    /**
     * Return a new instance of the condition.
     * @return new instance.
     */
    protected C newInstance() {
        return ObjectUtil.newInstance(this.conditionClass, FIELD_NAME, new Object());
    }

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        Assert.assertNotNull(newInstance());
    }

    /**
     * Test build method.
     */
    @Test
    public void build() {
        final C condition = newInstance();
        if (CollectionUtil.isCollection(condition.getValue())) {
            build(FIELD_NAME + " " + getOperator() + " (?1 ) ");
        } else {
            build(FIELD_NAME + " " + getOperator() + " ?1 ");
        }
    }

    /**
     * Test build method.
     * @param query the waited query.
     */
    protected void build(final String query) {
        final C condition = newInstance();
        Assert.assertNotNull(condition);

        final StringBuilder builder = new StringBuilder("where ");
        final List<Object> parameters = ListUtil.toList();

        condition.build(builder, parameters);
        Assert.assertEquals(builder.toString(), "where " + query);
        if (condition.getValue() == null) {
            Assert.assertTrue(parameters.isEmpty());
        } else {
            Assert.assertEquals(parameters.get(parameters.size() - 1), condition.getValue());
        }
    }

}
