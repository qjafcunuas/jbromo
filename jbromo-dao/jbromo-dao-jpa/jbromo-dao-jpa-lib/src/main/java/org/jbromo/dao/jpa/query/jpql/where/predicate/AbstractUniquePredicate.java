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
package org.jbromo.dao.jpa.query.jpql.where.predicate;

import java.util.List;

import org.jbromo.common.StringUtil;
import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.dao.common.exception.DataFindException;
import org.jbromo.dao.jpa.query.jpql.where.JpqlWhereBuilder;
import org.jbromo.dao.jpa.query.jpql.where.condition.ICondition;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * Define unique predicate. A unique predicate has only one child (for example the not operator).
 * @author qjafcunuas
 */
public abstract class AbstractUniquePredicate extends AbstractPredicate {

    /**
     * The child condition.
     */
    @Getter(AccessLevel.PROTECTED)
    private ICondition child;

    /**
     * Default constructor.
     * @param where the where builder.
     */
    public AbstractUniquePredicate(final JpqlWhereBuilder where) {
        super(where);
    }

    @Override
    protected abstract String getOperator();

    @Override
    void add(final ICondition condition) throws MessageLabelException {
        if (this.child != null) {
            throw new DataFindException("Cannot add more than one element on a UniquePredicate");
        }
        this.child = condition;
    }

    @Override
    public void build(final StringBuilder builder, final List<Object> parameters) {
        if (isEmpty()) {
            return;
        }
        final int length = builder.length();
        builder.append(getOperator());
        builder.append(StringUtil.SPACE);
        builder.append(StringUtil.PARENTHESIS_OPEN);
        final int all = builder.length();
        getChild().build(builder, parameters);
        if (all == builder.length()) {
            // Nothing has been added.
            builder.delete(length, all);
            return;
        }
        builder.append(StringUtil.PARENTHESIS_CLOSE);
        builder.append(StringUtil.SPACE);
    }

    @Override
    public boolean isEmpty() {
        return getChild() == null || getChild().isEmpty();
    }

}
