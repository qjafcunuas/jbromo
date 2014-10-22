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

/**
 * Define a JPQL condition, like "o.firstname = 'titi'", "o.field is null" ...
 *
 * @author qjafcunuas
 *
 */
public interface ICondition {

    /**
     * Build the JPQL condition.
     *
     * @param builder
     *            the builder to set JPQL condition.
     * @param parameters
     *            the parameters to set condition's value.
     */
    void build(final StringBuilder builder, final List<Object> parameters);

    /**
     * Return true if this condition is empty, so that it is not build into the
     * JPQL query. For example, for a 'and' condition without children, return
     * true.
     *
     * @return true/false.
     */
    boolean isEmpty();

}
