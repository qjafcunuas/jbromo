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
import org.jbromo.common.exception.MessageLabelException;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * JUnit AbstractUniquePredicate class.
 * @author qjafcunuas
 * @param
 *            <P>
 *            the predicate type.
 */
@Slf4j
public abstract class AbstractUniquePredicateTest<P extends AbstractUniquePredicate> extends AbstractPredicateTest<P> {

    @Override
    @Test
    public void empty() {
        final P predicate = newInstance();
        Assert.assertNotNull(predicate);
        Assert.assertNull(predicate.getChild());
        validate(predicate, "");
    }

    @Override
    protected void validate(final AbstractPredicate predicate, final String query, final Object... parameters) {
        if (query.isEmpty()) {
            super.validate(predicate, query, parameters);
        } else if (predicate.isNeedParenthesis()) {
            super.validate(predicate, getOperator() + " (" + query + ") ", parameters);
        } else {
            super.validate(predicate, getOperator() + " " + query, parameters);
        }
    }

    @Override
    @Test
    public void multiCriteria() {
        final P predicate = newInstance();
        final int[] parameters = new int[] {RandomUtil.nextInt(), RandomUtil.nextInt()};
        try {
            predicate.lessOrEquals(FIELD_NAME, parameters[0]);
        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
        try {
            predicate.greaterThan(FIELD_NAME, parameters[1]);
            Assert.fail("Cannot set multi-criteria on unique predicate.");
        } catch (final MessageLabelException e) {
            Assert.assertTrue(true);
        }

    }

    @Override
    @Test
    public void and() {
        final P predicate = newInstance();
        final int[] parameters = new int[] {RandomUtil.nextInt(), RandomUtil.nextInt()};
        try {
            final AndPredicate and = predicate.and();
            validate(predicate, "");
            and.lessOrEquals(FIELD_NAME + "1", parameters[0]);
            validate(predicate, "name1 <= ?1 ", parameters[0]);

            // Add one element to and predicate.
            and.equals(FIELD_NAME + "2", parameters[1]);
            validate(predicate, "name1 <= ?1 and name2 = ?2 ", parameters[0], parameters[1]);

        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    @Override
    @Test
    public void or() {
        final P predicate = newInstance();
        final int[] parameters = new int[] {RandomUtil.nextInt(), RandomUtil.nextInt()};
        try {
            final OrPredicate or = predicate.or();
            validate(predicate, "");
            or.lessOrEquals(FIELD_NAME + "1", parameters[0]);
            validate(predicate, "name1 <= ?1 ", parameters[0]);

            // Add one element to or predicate.
            or.equals(FIELD_NAME + "2", parameters[1]);
            validate(predicate, "(name1 <= ?1 or name2 = ?2 ) ", parameters[0], parameters[1]);
        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    @Override
    @Test
    public void not() {
        final P predicate = newInstance();
        final int[] parameters = new int[] {RandomUtil.nextInt()};
        try {
            final NotPredicate not = predicate.not();
            validate(predicate, "");
            not.lessOrEquals(FIELD_NAME + "1", parameters[0]);
            validate(predicate, "not (name1 <= ?1 ) ", parameters[0]);

        } catch (final MessageLabelException e) {
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
        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

}
