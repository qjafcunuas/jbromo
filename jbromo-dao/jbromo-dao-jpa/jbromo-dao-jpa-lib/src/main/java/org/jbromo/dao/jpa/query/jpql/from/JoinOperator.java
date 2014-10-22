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
package org.jbromo.dao.jpa.query.jpql.from;

import lombok.Getter;

/**
 * Define join operator.
 *
 * @author qjafcunuas
 *
 */
public enum JoinOperator {
    /**
     * Define LEFT JOIN.
     */
    LEFT_JOIN("left join ", null),
    /**
     * Define LEFT JOIN FETCH.
     */
    LEFT_JOIN_FETCH("left join fetch ", LEFT_JOIN),
    /**
     * Define INNER JOIN.
     */
    INNER_JOIN("inner join ", null),
    /**
     * Define INNER JOIN FETCH.
     */
    INNER_JOIN_FETCH("inner join fetch ", INNER_JOIN),
    /**
     * Define RIGHT JOIN.
     */
    RIGHT_JOIN("right join ", null),
    /**
     * Define RIGHT JION FETCH.
     */
    RIGHT_JOIN_FETCH("right join fetch ", RIGHT_JOIN);
    /**
     * Define expression for the operator.
     */
    @Getter
    private final String expression;

    /**
     * Return the corresponding join without fetching entities.
     */
    @Getter
    private final JoinOperator withoutFetch;

    /**
     * Default constructor.
     *
     * @param expression
     *            the expression.
     * @param withoutFetch
     *            set the similar join without fetch instruction.
     */
    private JoinOperator(final String expression,
            final JoinOperator withoutFetch) {
        this.expression = expression;
        if (withoutFetch == null) {
            this.withoutFetch = this;
        } else {
            this.withoutFetch = withoutFetch;
        }
    }

}
