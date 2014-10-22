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

import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.dao.common.exception.DaoException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Define JUnit OrPredicate class.
 *
 * @author qjafcunuas
 *
 */
@Slf4j
public class OrPredicateTest extends AbstractMultiPredicateTest<OrPredicate> {

    @Override
    protected String getOperator() {
        return "or";
    }

    @Override
    @Test
    public void or() {
        final AbstractMultiPredicate predicate = newInstance();
        final int[] parameters = new int[] { RandomUtil.nextInt(),
                RandomUtil.nextInt(), RandomUtil.nextInt() };
        try {
            final OrPredicate or = predicate.or();
            validate(predicate, "");
            Assert.assertEquals(or, predicate);
            predicate.lessOrEquals(FIELD_NAME + "1", parameters[0]);
            super.validate(predicate, "name1 <= ?1 ", parameters[0]);

            // Add one element to or predicate.
            or.equals(FIELD_NAME + "2", parameters[1]);
            validate(predicate, "(name1 <= ?1 or name2 = ?2 ) ", parameters[0],
                    parameters[1]);

            // Add another element to or predicate.
            or.greaterThan(FIELD_NAME + "3", parameters[2]);
            validate(predicate, "(name1 <= ?1 or name2 = ?2 or name3 > ?3 ) ",
                    parameters[0], parameters[1], parameters[2]);
        } catch (final DaoException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test distinguishes predicates.
     */
    @Test
    public void predicates() {
        final AbstractMultiPredicate predicate = newInstance();
        final int[] parameters = new int[] { RandomUtil.nextInt(),
                RandomUtil.nextInt(), RandomUtil.nextInt(),
                RandomUtil.nextInt() };
        try {
            final OrPredicate or = predicate.or();
            final AndPredicate and = predicate.and();
            validate(predicate, "");

            // Add one element to or predicate.
            or.lessOrEquals(FIELD_NAME + "1", parameters[0]);
            super.validate(predicate, "name1 <= ?1 ", parameters[0]);

            // Add one element to or predicate.
            or.equals(FIELD_NAME + "2", parameters[1]);
            super.validate(predicate, "(name1 <= ?1 or name2 = ?2 ) ",
                    parameters[0], parameters[1]);

            // Add one element to and predicate.
            and.notEquals(FIELD_NAME + "3", parameters[2]);
            validate(predicate, "(name3 != ?1 or name1 <= ?2 or name2 = ?3 ) ",
                    parameters[2], parameters[0], parameters[1]);

            // Add one element to and predicate.
            and.greaterThan(FIELD_NAME + "4", parameters[IntegerUtil.INT_3]);
            validate(
                    predicate,
                    "(name3 != ?1 and name4 > ?2 or name1 <= ?3 or name2 = ?4 ) ",
                    parameters[2], parameters[IntegerUtil.INT_3],
                    parameters[0], parameters[1]);

        } catch (final DaoException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }

    }

}
