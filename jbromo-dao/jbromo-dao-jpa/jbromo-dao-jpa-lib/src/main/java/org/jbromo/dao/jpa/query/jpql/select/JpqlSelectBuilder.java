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
package org.jbromo.dao.jpa.query.jpql.select;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.common.StringUtil;

/**
 * Build Select clause.
 *
 * @author qjafcunuas
 *
 */
public class JpqlSelectBuilder {
    /**
     * Define the alias to used in select clause.
     */
    @Getter(AccessLevel.NONE)
    private final String alias;

    /**
     * Define if distinct directive have to be used.
     */
    @Getter(AccessLevel.NONE)
    private final boolean distinct;

    /**
     * Default constructor.
     *
     * @param alias
     *            the alias to use.
     */
    public JpqlSelectBuilder(final String alias) {
        this(alias, true);
    }

    /**
     * Default constructor.
     *
     * @param alias
     *            the alias to use.
     * @param distinct
     *            if true, return only distinct element.
     */
    public JpqlSelectBuilder(final String alias, final boolean distinct) {
        super();
        this.alias = alias;
        this.distinct = distinct;
    }

    /**
     * Build the JPQL select element.
     *
     * @param builder
     *            the builder to add JPQL element.
     */
    public void build(final StringBuilder builder) {
        builder.append("select ");
        if (this.distinct) {
            builder.append("distinct ");
        }
        builder.append(this.alias);
        builder.append(StringUtil.SPACE);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        build(builder);
        return builder.toString();
    }

}
