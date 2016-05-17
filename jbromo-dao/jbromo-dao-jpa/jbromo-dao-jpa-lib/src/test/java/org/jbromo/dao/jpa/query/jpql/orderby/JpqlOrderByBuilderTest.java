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

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit JpqlOrderByBuilder class.
 * @author qjafcunuas
 */
public class JpqlOrderByBuilderTest {

    /**
     * Test no order by clause.
     */
    @Test
    public void empty() {
        final JpqlOrderByBuilder orderBy = new JpqlOrderByBuilder();
        validate(orderBy, "");
    }

    /**
     * Test asc method.
     */
    @Test
    public void asc() {
        final JpqlOrderByBuilder orderBy = new JpqlOrderByBuilder();
        orderBy.asc("name");
        validate(orderBy, "order by name ASC ");
        orderBy.asc("age");
        validate(orderBy, "order by name ASC,age ASC ");
    }

    /**
     * Test asc method.
     */
    @Test
    public void desc() {
        final JpqlOrderByBuilder orderBy = new JpqlOrderByBuilder();
        orderBy.desc("name");
        validate(orderBy, "order by name DESC ");
        orderBy.desc("age");
        validate(orderBy, "order by name DESC,age DESC ");
    }

    /**
     * Validate orderBy builder.
     * @param orderBy the orderBy builder to validate.
     * @param orderByQuery the waited from query
     */
    private void validate(final JpqlOrderByBuilder orderBy, final String orderByQuery) {
        Assert.assertEquals(orderBy.toString(), orderByQuery);
        final StringBuilder builder = new StringBuilder("test: ");
        orderBy.build(builder);
        Assert.assertEquals(builder.toString(), "test: " + orderByQuery);
    }

}
