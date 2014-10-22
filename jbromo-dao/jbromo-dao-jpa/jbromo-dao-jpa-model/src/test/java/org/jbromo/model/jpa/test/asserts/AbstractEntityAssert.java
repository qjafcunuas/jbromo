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
package org.jbromo.model.jpa.test.asserts;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.CalendarUtil;
import org.jbromo.common.CollectionUtil;
import org.jbromo.common.ListUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.SetUtil;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.common.invocation.ParameterizedTypeUtil;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.util.EntityUtil;
import org.junit.Assert;

/**
 * Interface that defines service model specifications.
 *
 * @author qjafcunuas
 * @param <E>
 *            the entity type
 */
@Slf4j
public abstract class AbstractEntityAssert<E extends IEntity<?>> {

    /**
     * Return the entity class.
     *
     * @return the entity class.
     */
    protected Class<E> getEntityClass() {
        return ParameterizedTypeUtil.getClass(this, 0);
    }

    /**
     * Default constructor.
     */
    protected AbstractEntityAssert() {
        super();
    }

    /**
     * Tests if the instance <code>one</code> is equal to the instance
     * <code>two</code>, according to all fields.
     *
     * @param <O>
     *            the object type.
     * @param one
     *            the first model instance to compare
     * @param two
     *            the second model instance to compare
     * @param tested
     *            contains object already tested, to that process don't call
     *            recursively infinity.
     */
    private <O> void assertCollectionEquals(final Collection<O> one,
            final Collection<O> two, final Set<Object> tested) {
        Assert.assertTrue(CollectionUtil.containsAll(one, two, true));
        for (final O oneO : one) {
            for (final O twoO : two) {
                if (oneO.equals(twoO)) {
                    assertEquals(oneO, twoO, tested);
                    break;
                }
            }
        }
    }

    /**
     * Tests if the instance <code>one</code> are equal to the instance
     * <code>two</code>, according to all fields.
     *
     * @param <O>
     *            the object type.
     * @param one
     *            the first model instance to compare
     * @param two
     *            the second model instance to compare
     */
    public final <O> void assertEquals(final O one, final O two) {
        assertEquals(one, two, SetUtil.toSet());
    }

    /**
     * Tests if the instance <code>one</code> are equal to the instance
     * <code>two</code>, according to all fields.
     *
     * @param <O>
     *            the object type.
     * @param one
     *            the first model instance to compare
     * @param two
     *            the second model instance to compare
     * @param tested
     *            contains object already tested, to that process don't call
     *            recursively infinity.
     */
    @SuppressWarnings("unchecked")
    private <O> void assertEquals(final O one, final O two,
            final Set<Object> tested) {
        if (one == null && two == null) {
            return;
        }
        if (one == null || two == null) {
            Assert.fail("One object is null and otherone is not null.");
        }
        if (one == two) {
            return;
        }
        if (tested.contains(one)) {
            return;
        }

        if (CollectionUtil.isCollection(one.getClass())
                && CollectionUtil.isCollection(two.getClass())) {
            final Collection<Object> oneCol = ObjectUtil.cast(one,
                    Collection.class);
            final Collection<Object> twoCol = ObjectUtil.cast(two,
                    Collection.class);
            assertCollectionEquals(oneCol, twoCol, tested);
            return;
        }

        if (EntityUtil.isEntity(one) && EntityUtil.isEntity(two)) {
            Assert.assertTrue("Entities must be equals", one.equals(two));
            Assert.assertTrue("Entities must have same hashCode",
                    one.hashCode() == two.hashCode());
            Assert.assertTrue("Entities must have same ToString result", one
                    .toString().equals(two.toString()));
            tested.add(one);
            tested.add(two);
        }

        for (final Field f : EntityUtil.getPersistedFields(one)) {
            final Class<?> type = f.getType();
            Object valueOne;
            try {
                valueOne = InvocationUtil.getValue(one, f);
            } catch (final InvocationException e) {
                log.error("Cannot get value {} from {}", f, one);
                Assert.fail(e.getMessage());
                return;
            }
            Object valueTwo;
            try {
                valueTwo = InvocationUtil.getValue(two, f);
            } catch (final InvocationException e) {
                log.error("Cannot get value {} from {}", f, two);
                Assert.fail(e.getMessage());
                return;
            }
            if (EntityUtil.isEntity(type)) {
                assertEquals(valueOne, valueTwo, tested);
            } else if (EntityUtil.isOneToMany(f)) {
                final Collection<IEntity<Serializable>> oneCol = ObjectUtil
                        .cast(valueOne, Collection.class);
                final Collection<IEntity<Serializable>> twoCol = ObjectUtil
                        .cast(valueTwo, Collection.class);
                assertOneToManyEquals(oneCol, twoCol, tested);
            } else if (CollectionUtil.isCollection(type)) {
                assertEquals(valueOne, valueTwo, tested);
            } else if (CalendarUtil.isCalendar(type)) {
                Assert.assertEquals(valueOne, valueTwo);
            } else if (EntityUtil.isEmbedded(f)) {
                assertEmbeddedEquals(valueOne, valueTwo, tested);
            } else {
                Assert.assertEquals("Problem with " + f.getName() + " one : "
                        + valueOne + " two : " + valueTwo, valueOne, valueTwo);
            }

        }
    }

    /**
     * Tests if the instance <code>one</code> is equal to the instance
     * <code>two</code>, according to all fields. Object are declared null if is
     * null or if all persisted field are null.
     *
     * @param <O>
     *            the object type.
     * @param one
     *            the first model instance to compare
     * @param two
     *            the second model instance to compare
     * @param tested
     *            contains object already tested, to that process don't call
     *            recursively infinity.
     */
    private <O> void assertEmbeddedEquals(final O one, final O two,
            final Set<Object> tested) {
        if (one == null && two == null) {
            return;
        }
        if (one != null && two != null) {
            assertEquals(one, two, tested);
            return;
        }
        Assert.assertTrue(isEmbeddedNull(one) == isEmbeddedNull(two));
    }

    /**
     * Tests if the instance <code>one</code> is equal to the instance
     * <code>two</code>, according to all fields. Object are declared null if is
     * null or if all persisted field are null.
     *
     * @param <O>
     *            the object type.
     * @param one
     *            the first model instance to compare
     * @param two
     *            the second model instance to compare
     * @param tested
     *            contains object already tested, to that process don't call
     *            recursively infinity.
     */
    @SuppressWarnings("unchecked")
    private <O extends IEntity<Serializable>> void assertOneToManyEquals(
            final Collection<O> one, final Collection<O> two,
            final Set<Object> tested) {
        if (CollectionUtil.isEmpty(one) && CollectionUtil.isEmpty(two)) {
            return;
        }
        final Class<O> objectClass = (Class<O>) one.iterator().next()
                .getClass();
        final Field pk = EntityUtil.getPrimaryKeyField(objectClass);
        if (EntityUtil.isEmbeddedId(pk)) {
            assertEquals(one, two, tested);
        } else {
            Assert.assertEquals(one.size(), two.size());
            // entity with @Id.
            // FIXME how to compare object ?
            // When updating with new object in OneToMany (so with no pk), the
            // object to update have no pk, but the updated object hone one ...
            // Ex from test : User has OneToMany addresses. If we add a new
            // address (with no pk) to a user, the user object contains an
            // address with no pk.
            // After updated the user in the persistent storage, the updates
            // user contains addresses witch all have a pk...
            Collection<?> pks = EntityUtil.getPrimaryKeys(ListUtil.toList(one));
            if (pks.contains(null)) {
                return;
            }
            pks = EntityUtil.getPrimaryKeys(ListUtil.toList(two));
            if (pks.contains(null)) {
                return;
            }
            // There is no null pk, so we can compare collection.
            assertCollectionEquals(one, two, tested);
        }
    }

    /**
     * Return true if value is null or if all fields are null.
     *
     * @param value
     *            the value to test.
     * @return true/false.
     */
    private boolean isEmbeddedNull(final Object value) {
        if (value == null) {
            return true;
        } else {
            Object fieldValue = null;
            // All field value must be null.
            for (final Field field : EntityUtil.getPersistedFields(value
                    .getClass())) {
                try {
                    fieldValue = InvocationUtil.getValue(value, field);
                    if (EntityUtil.isEmbedded(field)
                            && !isEmbeddedNull(fieldValue)) {
                        return false;
                    } else if (fieldValue != null) {
                        return false;
                    }
                } catch (final InvocationException e) {
                    Assert.fail(e.getMessage());
                }
            }
            return true;
        }
    }

    /**
     * Entity and Primary key should not be null.
     *
     * @param entity
     *            the entity to test.
     */
    public void assertNotNull(final E entity) {
        Assert.assertNotNull("Entity should not be null", entity);
        Assert.assertNotNull("Primary key should not be null for entity "
                + entity, entity.getPrimaryKey());
    }

    /**
     * Entities and Primary key should not be null.
     *
     * @param entities
     *            the entities to test.
     */
    public void assertNotNull(final Collection<E> entities) {
        Assert.assertNotNull("Entities collection should not be null", entities);
        for (final E entity : entities) {
            assertNotNull(entity);
        }
    }

}
