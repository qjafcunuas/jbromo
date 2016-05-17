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

import org.jbromo.common.RandomUtil;
import org.jbromo.dao.common.exception.DaoException;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * JUnit AbstractMultiPredicate class.
 * @author qjafcunuas
 * @param
 *            <P>
 *            the predicate type.
 */
@Slf4j
public abstract class AbstractMultiPredicateTest<P extends AbstractMultiPredicate> extends AbstractPredicateTest<P> {

    @Override
    @Test
    public void empty() {
        final AbstractMultiPredicate predicate = newInstance();
        Assert.assertNotNull(predicate);
        Assert.assertNotNull(predicate.getChildren());
        Assert.assertTrue(predicate.getChildren().isEmpty());
        validate(predicate, "");
        // Add null condition.
        try {
            predicate.add(null);
        } catch (final DaoException e) {
            log.error(e.getMessage(), e);
            Assert.fail("Cannot add null predicate!");
        }
        validate(predicate, "");
        // Add 2 empties conditions.
        try {
            predicate.and();
            predicate.and();
        } catch (final DaoException e) {
            log.error(e.getMessage(), e);
            Assert.fail("Cannot add and predicate!");
        }
        validate(predicate, "");
    }

    @Override
    @Test
    public void multiCriteria() {
        final AbstractMultiPredicate predicate = newInstance();
        final int[] parameters = new int[] {RandomUtil.nextInt(), RandomUtil.nextInt(), RandomUtil.nextInt()};
        try {
            predicate.lessOrEquals(FIELD_NAME, parameters[0]);
            predicate.equals(FIELD_NAME, parameters[1]);
            predicate.greaterThan(FIELD_NAME, parameters[2]);
            validateParenthesis(predicate, "name <= ?1 " + getOperator() + " name = ?2 " + getOperator() + " name > ?3 ", parameters[0],
                                parameters[1], parameters[2]);
        } catch (final DaoException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    @Override
    @Test
    public void and() {
        final AbstractMultiPredicate predicate = newInstance();
        final int[] parameters = new int[] {RandomUtil.nextInt(), RandomUtil.nextInt(), RandomUtil.nextInt()};
        try {
            final AndPredicate and = predicate.and();
            validate(predicate, "");
            predicate.lessOrEquals(FIELD_NAME + "1", parameters[0]);
            super.validate(predicate, "name1 <= ?1 ", parameters[0]);

            // Add one element to and predicate.
            and.equals(FIELD_NAME + "2", parameters[1]);
            validateParenthesis(predicate, "name2 = ?1 " + getOperator() + " name1 <= ?2 ", parameters[1], parameters[0]);

            // Add another element to and predicate.
            and.greaterThan(FIELD_NAME + "3", parameters[2]);
            validateParenthesis(predicate, "name2 = ?1 and name3 > ?2 " + getOperator() + " name1 <= ?3 ", parameters[1], parameters[2],
                                parameters[0]);
        } catch (final DaoException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    @Override
    @Test
    public void or() {
        final AbstractMultiPredicate predicate = newInstance();
        final int[] parameters = new int[] {RandomUtil.nextInt(), RandomUtil.nextInt(), RandomUtil.nextInt()};
        try {
            final OrPredicate or = predicate.or();
            validate(predicate, "");
            predicate.lessOrEquals(FIELD_NAME + "1", parameters[0]);
            super.validate(predicate, "name1 <= ?1 ", parameters[0]);

            // Add one element to or predicate.
            or.equals(FIELD_NAME + "2", parameters[1]);
            validate(predicate, "name2 = ?1 " + getOperator() + " name1 <= ?2 ", parameters[1], parameters[0]);

            // Add another element to or predicate.
            or.greaterThan(FIELD_NAME + "3", parameters[2]);
            validate(predicate, "(name2 = ?1 or name3 > ?2 ) " + getOperator() + " name1 <= ?3 ", parameters[1], parameters[2], parameters[0]);
        } catch (final DaoException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    @Override
    @Test
    public void not() {
        final AbstractMultiPredicate predicate = newInstance();
        final int[] parameters = new int[] {RandomUtil.nextInt(), RandomUtil.nextInt(), RandomUtil.nextInt()};
        try {
            final NotPredicate not = predicate.not();
            validate(predicate, "");
            predicate.lessOrEquals(FIELD_NAME + "1", parameters[0]);
            super.validate(predicate, "name1 <= ?1 ", parameters[0]);

            // Add one element to not predicate.
            not.equals(FIELD_NAME + "2", parameters[1]);
            validateParenthesis(predicate, "not (name2 = ?1 ) " + getOperator() + " name1 <= ?2 ", parameters[1], parameters[0]);

        } catch (final DaoException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    @Override
    @Test
    public void isEmpty() {
        try {
            final AbstractPredicate predicate = newEntityInstance();
            Assert.assertTrue(predicate.isEmpty());
            final AbstractPredicate and = predicate.and();
            Assert.assertTrue(predicate.isEmpty());
            and.or();
            Assert.assertTrue(predicate.isEmpty());
            and.isNull("field");
            Assert.assertFalse(predicate.isEmpty());
        } catch (final DaoException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

}
