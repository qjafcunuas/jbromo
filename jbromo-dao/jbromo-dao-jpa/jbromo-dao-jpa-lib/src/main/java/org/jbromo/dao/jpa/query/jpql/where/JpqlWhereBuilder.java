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
package org.jbromo.dao.jpa.query.jpql.where;

import java.io.Serializable;
import java.util.List;

import org.jbromo.common.StringUtil;
import org.jbromo.dao.jpa.query.jpql.AbstractJpqlQueryBuilder;
import org.jbromo.dao.jpa.query.jpql.where.predicate.AbstractUniquePredicate;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * Define the JPQL where builder. The builder is based on : predicate, condition and expression.
 * <ul>
 * <li>And, Or, Not operators are predicates.</li>
 * <li>"=", "like is" are conditions.</li>
 * <li>"avg(field)", "sum(field)" are expressions.</li>
 * </ul>
 * @author qjafcunuas
 */
public final class JpqlWhereBuilder extends AbstractUniquePredicate {
    /**
     * The from builder.
     */
    @Getter(AccessLevel.PUBLIC)
    private final AbstractJpqlQueryBuilder<? extends Serializable> queryBuilder;

    /**
     * Default constructor.
     * @param abstractJpqlQueryBuilder the query builder.
     */
    public JpqlWhereBuilder(final AbstractJpqlQueryBuilder<? extends Serializable> abstractJpqlQueryBuilder) {
        super(null);
        this.queryBuilder = abstractJpqlQueryBuilder;
    }

    @Override
    public boolean isNeedParenthesis() {
        return false;
    }

    @Override
    protected String getOperator() {
        return "where";
    }

    @Override
    public void build(final StringBuilder builder, final List<Object> parameters) {
        if (isEmpty()) {
            return;
        }
        builder.append(getOperator());
        builder.append(StringUtil.SPACE);
        getChild().build(builder, parameters);
    }

}
