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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import org.jbromo.common.StringUtil;

/**
 * Define abstract condition implementation.
 *
 * @param <O>
 *            the value type
 * @author qjafcunuas
 *
 */
@AllArgsConstructor
public abstract class AbstractCondition<O> implements ICondition {

    /**
     * The field to used.
     */
    @Getter(AccessLevel.PACKAGE)
    private final String field;

    /**
     * The value to used.
     */
    @Getter(AccessLevel.PACKAGE)
    private final O value;

    /**
     * Return the condition operator.
     *
     * @return the operator.
     */
    protected abstract String getOperator();

    @Override
    public void build(final StringBuilder builder, final List<Object> parameters) {
        builder.append(getField());
        builder.append(StringUtil.SPACE);
        builder.append(getOperator());
        builder.append(StringUtil.SPACE);
        addParameter(this.value, builder, parameters);
    }

    /**
     * Add a query parameter.
     *
     * @param value
     *            the value of the parameter.
     * @param builder
     *            the JPQL builder.
     * @param parameters
     *            the parameters.
     *
     */
    void addParameter(final Object value, final StringBuilder builder,
            final List<Object> parameters) {
        builder.append(StringUtil.QUESTION_MARK);
        // Parameter start to 1 : ?1 ?2 ?3 ...
        builder.append(parameters.size() + 1);
        builder.append(StringUtil.SPACE);
        parameters.add(value);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

}
