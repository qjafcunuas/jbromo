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
package org.jbromo.dao.jpa.query.jpql.where.predicate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.common.CollectionUtil;
import org.jbromo.common.ListUtil;
import org.jbromo.common.StringUtil;
import org.jbromo.dao.common.exception.DaoException;
import org.jbromo.dao.jpa.query.jpql.where.JpqlWhereBuilder;
import org.jbromo.dao.jpa.query.jpql.where.condition.ICondition;

/**
 * Define a predicate (AND, OR, NOT).
 *
 * @author qjafcunuas
 *
 */
public abstract class AbstractMultiPredicate extends AbstractPredicate {

    /**
     * The children predicates and conditions.
     */
    @Getter(AccessLevel.PROTECTED)
    private final List<ICondition> children = new ArrayList<ICondition>();

    /**
     * Default constructor.
     *
     * @param where
     *            the where builder.
     */
    public AbstractMultiPredicate(final JpqlWhereBuilder where) {
        super(where);
    }

    @Override
    protected abstract String getOperator();

    @Override
    void add(final ICondition child) throws DaoException {
        if (child != null) {
            getChildren().add(child);
        }
    }

    @Override
    public void build(final StringBuilder builder, final List<Object> parameters) {
        if (CollectionUtil.isEmpty(getChildren())) {
            return;
        }
        final List<ICondition> cloned = ListUtil.toList(getChildren());
        for (final ICondition condition : getChildren()) {
            // If condition is empty, it must not be generated.
            if (condition.isEmpty()) {
                cloned.remove(condition);
            }
        }
        if (cloned.isEmpty()) {
            return;
        } else if (cloned.size() == 1) {
            cloned.get(0).build(builder, parameters);
        } else {
            if (isNeedParenthesis()) {
                builder.append(StringUtil.PARENTHESIS_OPEN);
            }
            final Iterator<ICondition> iter = cloned.iterator();
            iter.next().build(builder, parameters);
            while (iter.hasNext()) {
                builder.append(getOperator());
                builder.append(StringUtil.SPACE);
                iter.next().build(builder, parameters);
            }
            if (isNeedParenthesis()) {
                builder.append(StringUtil.PARENTHESIS_CLOSE);
                builder.append(StringUtil.SPACE);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        for (final ICondition condition : getChildren()) {
            if (!condition.isEmpty()) {
                return false;
            }
        }
        return true;
    }

}
