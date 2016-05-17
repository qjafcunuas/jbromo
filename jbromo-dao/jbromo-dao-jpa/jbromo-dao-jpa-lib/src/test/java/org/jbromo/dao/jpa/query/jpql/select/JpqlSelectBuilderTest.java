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
package org.jbromo.dao.jpa.query.jpql.select;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit JpqlSelectBuilder class.
 * @author qjafcunuas
 */
public class JpqlSelectBuilderTest {

    /**
     * Validate select builder.
     * @param select the select builder to validate.
     * @param selectQuery the waited select query
     */
    private void validate(final JpqlSelectBuilder select, final String selectQuery) {
        Assert.assertEquals(select.toString(), selectQuery);
        final StringBuilder builder = new StringBuilder("test: ");
        select.build(builder);
        Assert.assertEquals(builder.toString(), "test: " + selectQuery);
    }

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        JpqlSelectBuilder select = new JpqlSelectBuilder("o1");
        validate(select, "select distinct o1 ");
        select = new JpqlSelectBuilder("o2", true);
        validate(select, "select distinct o2 ");
        select = new JpqlSelectBuilder("o3", false);
        validate(select, "select o3 ");
    }

}
