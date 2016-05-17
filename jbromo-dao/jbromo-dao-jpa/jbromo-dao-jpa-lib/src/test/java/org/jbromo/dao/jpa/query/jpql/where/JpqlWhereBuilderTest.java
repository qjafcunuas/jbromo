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
package org.jbromo.dao.jpa.query.jpql.where;

import org.jbromo.dao.common.exception.DaoException;
import org.jbromo.dao.jpa.query.jpql.AbstractJpqlQueryBuilder;
import org.jbromo.dao.jpa.query.jpql.JpqlEntityQueryBuilder;
import org.jbromo.dao.jpa.query.jpql.where.predicate.AbstractUniquePredicateTest;
import org.jbromo.sample.server.model.src.User;
import org.junit.Assert;

import lombok.extern.slf4j.Slf4j;

/**
 * JUnit JpqlWhereBuilder class.
 * @author qjafcunuas
 */
@Slf4j
public class JpqlWhereBuilderTest extends AbstractUniquePredicateTest<JpqlWhereBuilder> {

    @Override
    protected JpqlWhereBuilder newInstance() {
        return new JpqlWhereBuilder(new AbstractJpqlQueryBuilder<User>(null, User.class));
    }

    @Override
    protected JpqlWhereBuilder newEntityInstance() {
        try {
            return new JpqlWhereBuilder(new JpqlEntityQueryBuilder<User>(null, User.class));
        } catch (final DaoException e) {
            log.error("Cannot instanciate class", e);
            Assert.fail(e.getMessage());
            return null;
        }
    }

    @Override
    protected String getOperator() {
        return "where";
    }

}
