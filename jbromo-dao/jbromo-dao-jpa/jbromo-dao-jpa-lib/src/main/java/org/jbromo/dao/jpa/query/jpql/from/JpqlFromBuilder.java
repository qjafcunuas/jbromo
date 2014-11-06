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

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.common.StringUtil;

/**
 * Build From clause.
 *
 * @author qjafcunuas
 *
 */
public class JpqlFromBuilder {
    /**
     * The From JPQL query.
     */
    @Getter(AccessLevel.PRIVATE)
    private final StringBuilder from = new StringBuilder();

    /**
     * Used for creating new alias.
     */
    private int aliasId = 0;

    /**
     * Default constructor.
     *
     * @param fromClass
     *            the class object to return.
     */
    public JpqlFromBuilder(final Class<?> fromClass) {
        super();
        getFrom().append("from ");
        getFrom().append(fromClass.getName());
        getFrom().append(StringUtil.SPACE);
        getFrom().append(getRootAlias());
        getFrom().append(StringUtil.SPACE);
    }

    /**
     * Return a new alias.
     *
     * @return the new alias.
     */
    private String newAlias() {
        return getRootAlias() + ++this.aliasId;
    }

    /**
     * Return the entity alias.
     *
     * @return the entity alias.
     */
    public final String getRootAlias() {
        return "o";
    }

    /**
     * Define a join element.
     *
     * @param join
     *            the join type.
     * @param parentAlias
     *            the alias to used.
     * @param field
     *            the field of the alias to used.
     * @param newAlias
     *            if true, return a new alias, else return parentAlias.field.
     * @return the alias.
     */
    private String join(final JoinOperator join, final String parentAlias,
            final String field, final boolean newAlias) {

        getFrom().append(join.getExpression());
        getFrom().append(parentAlias);
        getFrom().append(StringUtil.DOT);
        getFrom().append(field);
        getFrom().append(StringUtil.SPACE);
        if (newAlias) {
            final String childAlias = newAlias();
            getFrom().append(childAlias);
            getFrom().append(StringUtil.SPACE);
            return childAlias;
        } else {
            return parentAlias + StringUtil.DOT + field;
        }
    }

    /**
     * Define a join element.
     *
     * @param parentAlias
     *            the alias to used.
     * @param field
     *            the field of the alias to used.
     * @param newAlias
     *            if true, return a new alias, else return parentAlias.field.
     * @return the alias.
     */
    public String join(final String parentAlias, final String field,
            final boolean newAlias) {
        return join(JoinOperator.INNER_JOIN, parentAlias, field, newAlias);
    }

    /**
     * Define a join fetch element.
     *
     * @param parentAlias
     *            the alias to used.
     * @param field
     *            the field of the alias to used.
     * @param newAlias
     *            if true, return a new alias, else return parentAlias.field.
     * @return the alias.
     */
    public String joinFetch(final String parentAlias, final String field,
            final boolean newAlias) {
        return join(JoinOperator.INNER_JOIN_FETCH, parentAlias, field, newAlias);
    }

    /**
     * Define a left join element.
     *
     * @param parentAlias
     *            the alias to used.
     * @param field
     *            the field of the alias to used.
     * @param newAlias
     *            if true, return a new alias, else return parentAlias.field.
     * @return the alias.
     */
    public String leftJoin(final String parentAlias, final String field,
            final boolean newAlias) {
        return join(JoinOperator.LEFT_JOIN, parentAlias, field, newAlias);
    }

    /**
     * Define a left join fetch element.
     *
     * @param parentAlias
     *            the alias to used.
     * @param field
     *            the field of the alias to used.
     * @param newAlias
     *            if true, return a new alias, else return parentAlias.field.
     * @return the alias.
     */
    public String leftJoinFetch(final String parentAlias, final String field,
            final boolean newAlias) {
        return join(JoinOperator.LEFT_JOIN_FETCH, parentAlias, field, newAlias);
    }

    /**
     * Define a right join element.
     * 
     * @param parentAlias
     *            the alias to used.
     * @param field
     *            the field of the alias to used.
     * @param newAlias
     *            if true, return a new alias, else return parentAlias.field.
     * @return the alias.
     */
    public String rightJoin(final String parentAlias, final String field,
            final boolean newAlias) {
        return join(JoinOperator.RIGHT_JOIN, parentAlias, field, newAlias);
    }

    /**
     * Define a right join fetch  element.
     *
     * @param parentAlias
     *            the alias to used.
     * @param field
     *            the field of the alias to used.
     * @param newAlias
     *            if true, return a new alias, else return parentAlias.field.
     * @return the alias.
     */
    public String rightJoinFetch(final String parentAlias, final String field,
            final boolean newAlias) {
        return join(JoinOperator.RIGHT_JOIN_FETCH, parentAlias, field, newAlias);
    }

    /**
     * Build the JPQL select  element.
     *
     * @param builder
     *            the builder to add JPQL element.
     */
    public void build(final StringBuilder builder) {
        builder.append(toString());
    }

    @Override
    public String toString() {
        return getFrom().toString();
    }

}
