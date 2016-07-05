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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.jbromo.common.ClassUtil;
import org.jbromo.common.CollectionUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.SetUtil;
import org.jbromo.common.StringUtil;
import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.dao.jpa.query.jpql.JpqlEntityQueryBuilder;
import org.jbromo.dao.jpa.query.jpql.from.JpqlFromBuilder;
import org.jbromo.dao.jpa.query.jpql.where.JpqlWhereBuilder;
import org.jbromo.dao.jpa.query.jpql.where.condition.EqualsCondition;
import org.jbromo.dao.jpa.query.jpql.where.condition.GreaterOrEqualsCondition;
import org.jbromo.dao.jpa.query.jpql.where.condition.GreaterThanCondition;
import org.jbromo.dao.jpa.query.jpql.where.condition.ICondition;
import org.jbromo.dao.jpa.query.jpql.where.condition.InCondition;
import org.jbromo.dao.jpa.query.jpql.where.condition.IsNullCondition;
import org.jbromo.dao.jpa.query.jpql.where.condition.LessOrEqualsCondition;
import org.jbromo.dao.jpa.query.jpql.where.condition.LessThanCondition;
import org.jbromo.dao.jpa.query.jpql.where.condition.LikeCondition;
import org.jbromo.dao.jpa.query.jpql.where.condition.NotEqualsCondition;
import org.jbromo.dao.jpa.query.jpql.where.condition.NotInCondition;
import org.jbromo.model.common.util.INullObject;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.util.EntityUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Define a predicate (AND, OR, NOT).
 * @author qjafcunuas
 */
@Slf4j
public abstract class AbstractPredicate implements IPredicate {

    /**
     * The where builder.
     */
    private final JpqlWhereBuilder where;

    /**
     * Default constructor.
     * @param where the where clause.
     */
    protected AbstractPredicate(final JpqlWhereBuilder where) {
        super();
        if (where == null && ClassUtil.isInstance(this, JpqlWhereBuilder.class)) {
            this.where = ObjectUtil.cast(this, JpqlWhereBuilder.class);
        } else {
            this.where = where;
        }
    }

    /**
     * Return the from builder.
     * @return the builder.
     */
    protected JpqlFromBuilder getFrom() {
        return this.where.getQueryBuilder().getFrom();
    }

    /**
     * Return the entity manager.
     * @return the entity manager.
     */
    protected EntityManager getEntityManager() {
        return this.where.getQueryBuilder().getEntityManager();
    }

    /**
     * Return the query builder.
     * @return the query builder.
     */
    @SuppressWarnings("unchecked")
    private JpqlEntityQueryBuilder<IEntity<?>> getQueryBuilder() {
        return (JpqlEntityQueryBuilder<IEntity<?>>) this.where.getQueryBuilder();
    }

    /**
     * Return true if predicate needs to be build with parenthesis. (ex: operator not(...) ).
     * @return true/false.
     */
    public boolean isNeedParenthesis() {
        return true;
    }

    /**
     * Return the predicate's operator.
     * @return the operator.
     */
    protected abstract String getOperator();

    /**
     * Add a condition or a predicate to the current predicate.
     * @param child the child to add.
     * @throws MessageLabelException exception.
     */
    abstract void add(final ICondition child) throws MessageLabelException;

    @Override
    public void equals(final String field, final Object value) throws MessageLabelException {
        if (value != null) {
            add(new EqualsCondition(field, value));
        }
    }

    @Override
    public void notEquals(final String field, final Object value) throws MessageLabelException {
        if (value != null) {
            add(new NotEqualsCondition(field, value));
        }
    }

    @Override
    public <T> void in(final String field, final Collection<T> values) throws MessageLabelException {
        if (CollectionUtil.isEmpty(values)) {
            return;
        } else if (values.size() == 1) {
            equals(field, values.iterator().next());
        } else {
            add(new InCondition(field, values));
        }
    }

    @Override
    public <T> void notIn(final String field, final Collection<T> values) throws MessageLabelException {
        if (CollectionUtil.isEmpty(values)) {
            return;
        } else if (values.size() == 1) {
            notEquals(field, values.iterator().next());
        } else {
            add(new NotInCondition(field, values));
        }
    }

    @Override
    public void like(final String field, final String value) throws MessageLabelException {
        if (value != null) {
            add(new LikeCondition(field, value));
        }
    }

    @Override
    public AndPredicate and() throws MessageLabelException {
        final AndPredicate and = new AndPredicate(this.where);
        add(and);
        return and;
    }

    @Override
    public OrPredicate or() throws MessageLabelException {
        final OrPredicate or = new OrPredicate(this.where);
        add(or);
        return or;
    }

    @Override
    public <E extends IEntity<?>> AndPredicate and(final E entity) throws MessageLabelException {
        final AndPredicate and = and();
        and.entity(entity);
        return and;
    }

    @Override
    public <E extends IEntity<?>> OrPredicate or(final E entity) throws MessageLabelException {
        final OrPredicate or = or();
        or.entity(entity);
        return or;
    }

    /**
     * Build query with an entity as criteria.
     * @param <E> the entity type.
     * @param criteria the entity criteria.
     * @throws MessageLabelException exception.
     */
    protected <E extends IEntity<?>> void entity(final E criteria) throws MessageLabelException {
        if (criteria == null) {
            return;
        }
        final List<Field> fields = EntityUtil.getPersistedFields(criteria.getClass());
        final Set<Object> lockRecursive = SetUtil.toSet();
        for (final Field field : fields) {
            entity(this, criteria, field, getFrom().getRootAlias(), lockRecursive);
        }
    }

    /**
     * Join element foe a specific criteria's field.
     * @param predicate the predicate.
     * @param criteria the criteria.
     * @param field the criteria's field.
     * @param parentAlias the parent field' alias.
     * @param lockRecursive prevent recursive call.
     * @throws MessageLabelException exception.
     */
    private void entity(final IPredicate predicate, final Object criteria, final Field field, final String parentAlias,
            final Set<Object> lockRecursive) throws MessageLabelException {
        try {
            if (lockRecursive.contains(criteria)) {
                return;
            }
            final Object value = InvocationUtil.getValue(criteria, field);
            if (value == null || lockRecursive.contains(value)) {
                return;
            }
            lockRecursive.add(criteria);
            entity(predicate, field, value, parentAlias, lockRecursive);
            lockRecursive.remove(criteria);

        } catch (final InvocationException e) {
            log.error("Cannot build where query!", e);
        }
    }

    /**
     * Join element foe a specific criteria's field.
     * @param predicate the predicate.
     * @param field the criteria's field.
     * @param value the criteria's field value.
     * @param parentAlias the parent field' alias.
     * @param lockRecursive prevent recursive call.
     * @throws InvocationException exception.
     * @throws MessageLabelException exception.
     */
    private void entity(final IPredicate predicate, final Field field, final Object value, final String parentAlias,
            final Set<Object> lockRecursive) throws InvocationException, MessageLabelException {
        if (EntityUtil.isEmbedded(field) || EntityUtil.isEmbeddedId(field)) {
            for (final Field embeddedField : EntityUtil.getPersistedFields(value.getClass())) {
                entity(predicate, value, embeddedField, parentAlias + StringUtil.DOT + field.getName(), lockRecursive);
            }
        } else if (EntityUtil.isManyToOne(field) || EntityUtil.isOneToOne(field)) {
            entityToOneField(predicate, field, value, parentAlias, lockRecursive);
        } else if (value instanceof String && ((String) value).contains(StringUtil.STAR)) {
            predicate.like(parentAlias + StringUtil.DOT + field.getName(), (String) value);
        } else if (CollectionUtil.isCollection(field.getType())) {
            final Collection<?> collection = (Collection<?>) value;
            entityCollectionField(predicate, field, collection, parentAlias, lockRecursive);
        } else {
            predicate.equals(parentAlias + StringUtil.DOT + field.getName(), value);
        }
    }

    /**
     * Join element for a collection criteria's field.
     * @param predicate the predicate.
     * @param field the criteria's field.
     * @param value the criteria's field value.
     * @param parentAlias the parent field' alias.
     * @param lockRecursive prevent recursive call.
     * @throws InvocationException exception.
     * @throws MessageLabelException exception.
     */
    private void entityCollectionField(final IPredicate predicate, final Field field, final Collection<?> value, final String parentAlias,
            final Set<Object> lockRecursive) throws InvocationException, MessageLabelException {
        final Collection<?> collection = value;
        if (CollectionUtil.isEmpty(collection)) {
            return;
        }
        String collectionAlias;
        if (CollectionUtil.contains(collection, INullObject.class)) {
            collectionAlias = getFrom().leftJoin(parentAlias, field.getName(), true);
        } else {
            collectionAlias = getFrom().join(parentAlias, field.getName(), true);
        }
        // build something like: (collection.entity1.field1 = ? and/or collection.entity1.field2 = ?) or
        // (collection.entity2.field1 = ? and/or collection.entity2.field2 = ?)
        final IPredicate or = predicate.or();
        for (final Object o : collection) {
            if (INullObject.class.isInstance(o)) {
                final Field pkField = EntityUtil.getPrimaryKeyField(o.getClass());
                or.isNull(collectionAlias + StringUtil.DOT + pkField.getName());
            } else {
                IPredicate next;
                if (ClassUtil.isInstance(predicate, OrPredicate.class)) {
                    next = or;
                } else {
                    next = or.and();
                }
                for (final Field collectionField : EntityUtil.getPersistedFields(o.getClass())) {
                    entity(next, o, collectionField, collectionAlias, lockRecursive);
                }
            }
        }
    }

    /**
     * Join element foe a OneToOne or ManyToOne criteria's field.
     * @param predicate the predicate.
     * @param field the criteria's field.
     * @param value the criteria's field value.
     * @param parentAlias the parent field' alias.
     * @param lockRecursive prevent recursive call.
     * @throws InvocationException exception.
     * @throws MessageLabelException exception.
     */
    private void entityToOneField(final IPredicate predicate, final Field field, final Object value, final String parentAlias,
            final Set<Object> lockRecursive) throws InvocationException, MessageLabelException {
        // Parent eager loading.
        Object eagerLoading = getQueryBuilder().getEager(parentAlias);
        if (eagerLoading != null) {
            // field eager loading.
            eagerLoading = InvocationUtil.getValue(eagerLoading, field);
        }
        String alias;
        if (eagerLoading != null) {
            alias = getQueryBuilder().getAlias(eagerLoading);
        } else {
            alias = parentAlias + StringUtil.DOT + field.getName();
        }
        for (final Field embeddedField : EntityUtil.getPersistedFields(value.getClass())) {
            entity(predicate, value, embeddedField, alias, lockRecursive);
        }
    }

    @Override
    public NotPredicate not() throws MessageLabelException {
        final NotPredicate not = new NotPredicate(this.where);
        add(not);
        return not;
    }

    @Override
    public void isNull(final String field) throws MessageLabelException {
        add(new IsNullCondition(field));
    }

    @Override
    public void greaterThan(final String field, final Object value) throws MessageLabelException {
        if (value != null) {
            add(new GreaterThanCondition(field, value));
        }
    }

    @Override
    public void greaterOrEquals(final String field, final Object value) throws MessageLabelException {
        if (value != null) {
            add(new GreaterOrEqualsCondition(field, value));
        }
    }

    @Override
    public void lessThan(final String field, final Object value) throws MessageLabelException {
        if (value != null) {
            add(new LessThanCondition(field, value));
        }
    }

    @Override
    public void lessOrEquals(final String field, final Object value) throws MessageLabelException {
        if (value != null) {
            add(new LessOrEqualsCondition(field, value));
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        final List<Object> parameters = new ArrayList<>();
        build(builder, parameters);
        return builder.toString();
    }

}
