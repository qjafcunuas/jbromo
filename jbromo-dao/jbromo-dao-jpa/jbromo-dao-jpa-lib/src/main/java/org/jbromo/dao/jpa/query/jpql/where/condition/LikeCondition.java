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
package org.jbromo.dao.jpa.query.jpql.where.condition;

import java.util.List;

import org.jbromo.common.StringUtil;

/**
 * Define like condition.
 *
 * @author qjafcunuas
 *
 */
public class LikeCondition extends AbstractCondition<String> {

    /**
     * Default constructor.
     *
     * @param field
     *            the field.
     * @param value
     *            the values.
     */
    public LikeCondition(final String field, final String value) {
        super(field, value);
    }

    @Override
    protected String getOperator() {
        return "like";
    }

    @Override
    public void build(final StringBuilder builder, final List<Object> parameters) {
        builder.append(getField());
        builder.append(StringUtil.SPACE);
        builder.append(getOperator());
        builder.append(StringUtil.SPACE);
        if (getValue().contains(StringUtil.STAR)) {
            addParameter(getValue()
                    .replace(StringUtil.STAR, StringUtil.PERCENT), builder,
                    parameters);
        } else {
            addParameter(StringUtil.PERCENT + getValue() + StringUtil.PERCENT,
                    builder, parameters);
        }
    }

}
