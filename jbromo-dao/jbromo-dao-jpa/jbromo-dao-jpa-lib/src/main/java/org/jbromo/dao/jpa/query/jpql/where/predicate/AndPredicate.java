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

import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.dao.jpa.query.jpql.where.JpqlWhereBuilder;

/**
 * Define and predicate.
 * @author qjafcunuas
 */
public class AndPredicate extends AbstractMultiPredicate {

    /**
     * Default constructor.
     * @param where the where builder.
     */
    public AndPredicate(final JpqlWhereBuilder where) {
        super(where);
    }

    @Override
    public boolean isNeedParenthesis() {
        return false;
    }

    @Override
    protected String getOperator() {
        return "and";
    }

    @Override
    public AndPredicate and() throws MessageLabelException {
        return this;
    }
}
