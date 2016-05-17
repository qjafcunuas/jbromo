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

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.ListUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.common.StringUtil;
import org.junit.Assert;

/**
 * JUnit LikeCondition class.
 * @author qjafcunuas
 */
public class LikeConditionTest extends AbstractConditionTest<LikeCondition> {

    @Override
    protected LikeCondition newInstance() {
        String value = RandomUtil.nextString(IntegerUtil.INT_10);
        value = value.replace(StringUtil.STAR, "1");
        return new LikeCondition(FIELD_NAME, value);
    }

    @Override
    protected String getOperator() {
        return "like";
    }

    @Override
    protected void build(final String query) {
        LikeCondition condition = newInstance();
        Assert.assertNotNull(condition);

        // Build without '*'.
        StringBuilder builder = new StringBuilder("where ");
        final List<Object> parameters = ListUtil.toList();

        condition.build(builder, parameters);
        Assert.assertEquals(builder.toString(), "where " + query);
        Assert.assertEquals(parameters.get(parameters.size() - 1), StringUtil.PERCENT + condition.getValue() + StringUtil.PERCENT);

        // Build with '*'.
        final String value = RandomUtil.nextString(IntegerUtil.INT_10);
        condition = new LikeCondition(FIELD_NAME, value + StringUtil.STAR);
        Assert.assertNotNull(condition);
        builder = new StringBuilder("where ");
        parameters.clear();

        condition.build(builder, parameters);
        Assert.assertEquals(builder.toString(), "where " + query);
        Assert.assertEquals(parameters.get(parameters.size() - 1), value + StringUtil.PERCENT);

    }

}
