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

import java.util.Collection;

import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.dao.jpa.query.jpql.where.condition.ICondition;
import org.jbromo.model.jpa.IEntity;

/**
 * Define JPQL predicate.
 * @author qjafcunuas
 */
public interface IPredicate extends ICondition {
    /**
     * Add Equals condition (JPQL "=").
     * @param field the field.
     * @param value the condition's value.
     * @throws MessageLabelException exception.
     */
    void equals(final String field, final Object value) throws MessageLabelException;

    /**
     * Add Equals condition (JPQL "!=").
     * @param field the field.
     * @param value the condition's value.
     * @throws MessageLabelException exception.
     */
    void notEquals(final String field, final Object value) throws MessageLabelException;

    /**
     * Add Equals condition (JPQL "in").
     * @param <T> the values type.
     * @param field the field.
     * @param values the condition's values.
     * @throws MessageLabelException exception.
     */
    <T> void in(final String field, final Collection<T> values) throws MessageLabelException;

    /**
     * Add Equals condition (JPQL "not in").
     * @param <T> the values type.
     * @param field the field.
     * @param values the condition's values.
     * @throws MessageLabelException exception.
     */
    <T> void notIn(final String field, final Collection<T> values) throws MessageLabelException;

    /**
     * Add Like condition (JPQL "is like").
     * @param field the field.
     * @param value the condition's value.
     * @throws MessageLabelException exception.
     */
    void like(final String field, final String value) throws MessageLabelException;

    /**
     * Add is null condition (JPQL "is null").
     * @param field the field.
     * @throws MessageLabelException exception.
     */
    void isNull(final String field) throws MessageLabelException;

    /**
     * Add greater than condition (JPQL greater).
     * @param field the field.
     * @param value the condition's value.
     * @throws MessageLabelException exception.
     */
    void greaterThan(final String field, final Object value) throws MessageLabelException;

    /**
     * Add greater or equals condition (JPQL greater or equals).
     * @param field the field.
     * @param value the condition's value.
     * @throws MessageLabelException exception.
     */
    void greaterOrEquals(final String field, final Object value) throws MessageLabelException;

    /**
     * Add less than condition (JPQL inferior).
     * @param field the field.
     * @param value the condition's value.
     * @throws MessageLabelException exception.
     */
    void lessThan(final String field, final Object value) throws MessageLabelException;

    /**
     * Add less or equals then condition (JPQL inferior or equals).
     * @param field the field.
     * @param value the condition's value.
     * @throws MessageLabelException exception.
     */
    void lessOrEquals(final String field, final Object value) throws MessageLabelException;

    /**
     * Return a new AND predicate.
     * @return the predicate.
     * @throws MessageLabelException exception.
     */
    IPredicate and() throws MessageLabelException;

    /**
     * Return a new OR predicate.
     * @return the predicate.
     * @throws MessageLabelException exception.
     */
    IPredicate or() throws MessageLabelException;

    /**
     * Return a new and entity predicate.
     * @param <E> entity type.
     * @param entity the entity to add.
     * @return the predicate.
     * @throws MessageLabelException exception.
     */
    <E extends IEntity<?>> IPredicate and(final E entity) throws MessageLabelException;

    /**
     * Return a new or entity predicate.
     * @param <E> entity type.
     * @param entity the entity to add.
     * @return the predicate.
     * @throws MessageLabelException exception.
     */
    <E extends IEntity<?>> IPredicate or(final E entity) throws MessageLabelException;

    /**
     * Return a new NOT predicate.
     * @return the predicate.
     * @throws MessageLabelException exception.
     */
    IPredicate not() throws MessageLabelException;

}
