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
package org.jbromo.dao.jpa.query.jpql.orderby;

import org.jbromo.common.StringUtil;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * Build order by clause.
 * @author qjafcunuas
 */
public class JpqlOrderByBuilder {
    /**
     * The OrderBy JPQL query.
     */
    @Getter(AccessLevel.NONE)
    private final StringBuilder orderBy = new StringBuilder();

    /**
     * Build the JPQL orderBy element.
     * @param builder the builder to add JPQL element.
     */
    public void build(final StringBuilder builder) {
        if (this.orderBy.length() > 0) {
            builder.append("order by ");
            builder.append(this.orderBy.toString());
            builder.append(StringUtil.SPACE);
        }
    }

    /**
     * Add an order ASC by clause.
     * @param clause the clause to add.
     */
    public void asc(final String clause) {
        order(clause, "ASC");
    }

    /**
     * Add an order by DESC clause.
     * @param clause the clause to add.
     */
    public void desc(final String clause) {
        order(clause, "DESC");
    }

    /**
     * Add an order by clause.
     * @param clause the clause to add.
     * @param order the order value (ASC or DESC).
     */
    private void order(final String clause, final String order) {
        if (this.orderBy.length() != 0) {
            this.orderBy.append(StringUtil.COMMA);
        }
        this.orderBy.append(clause);
        this.orderBy.append(StringUtil.SPACE);
        this.orderBy.append(order);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        build(builder);
        return builder.toString();
    }

}
