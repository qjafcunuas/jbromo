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
package org.jbromo.dao.jpa.query.jpql;

import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.dao.jpa.container.common.JpaProviderFactory;
import org.jbromo.dao.jpa.query.jpql.where.predicate.AndPredicate;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.sample.server.model.src.City;
import org.jbromo.sample.server.model.src.User;
import org.jbromo.sample.server.model.src.UserAddress;
import org.jbromo.sample.server.model.src.UserGroup;
import org.jbromo.sample.server.model.src.UserSurname;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * JUnit AbstractJpqlQueyBuilder class.
 * @author qjafcunuas
 */
@Slf4j
public class JpqlEntityQueyBuilderTest extends AbstractJpqlQueryBuilderTest<IEntity<?>> {

    /**
     * Return a new instance of an entity query builder.
     * @param <E> the entity type.
     * @param entityClass the entity class.
     * @return the builder.
     */
    private <E extends IEntity<?>> JpqlEntityQueryBuilder<E> newInstance(final Class<E> entityClass) {
        return new JpqlEntityQueryBuilder<E>(null, entityClass);
    }

    /**
     * Return a new instance of an entity query builder.
     * @param <E> the entity type.
     * @param entityClass the entity class.
     * @param eagerLoading the eager loading.
     * @return the builder.
     */
    private <E extends IEntity<?>> JpqlEntityQueryBuilder<E> newInstance(final Class<E> entityClass, final E eagerLoading) {
        try {
            return new JpqlEntityQueryBuilder<E>(null, entityClass, eagerLoading);
        } catch (final MessageLabelException e) {
            Assert.fail("Cannot instantiate builder for class " + entityClass);
            return null;
        }
    }

    /**
     * Before test.
     */
    @Before
    public void before() {
        final JpqlEntityQueryBuilder<User> queryBuilder = newInstance(User.class);
        Assert.assertNotNull(queryBuilder.getSelect());
        Assert.assertNotNull(queryBuilder.getFrom());
        Assert.assertNotNull(queryBuilder.getWhere());
        Assert.assertNotNull(queryBuilder.getOrderBy());
        Assert.assertNotNull(queryBuilder.getModelClass());
        Assert.assertEquals(queryBuilder.getModelClass(), User.class);
    }

    /**
     * Simple query test.
     */
    @Test
    public void simpleQuery() {
        final JpqlEntityQueryBuilder<UserGroup> queryBuilder = newInstance(UserGroup.class);
        try {
            queryBuilder.getWhere().equals("name", "toto");
            queryBuilder.getOrderBy().asc("name");
            validate(queryBuilder, "select distinct o from " + UserGroup.class.getName() + " o where name = ?1 order by name ASC ");
        } catch (final MessageLabelException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Join query test.
     */
    @Test
    public void joinQuery() {
        final JpqlEntityQueryBuilder<User> queryBuilder = newInstance(User.class);
        try {
            queryBuilder.getOrderBy().asc("firstname");
            queryBuilder.getOrderBy().asc("lastname");
            queryBuilder.getFrom().join(queryBuilder.getFrom().getRootAlias(), "manyToOneGroup", false);

            final AndPredicate and = queryBuilder.getWhere().and();
            and.equals("firstname", "titi");
            and.notEquals("name", "tutu");
            validate(queryBuilder, "select distinct o from " + User.class.getName() + " o " + "inner join o.manyToOneGroup "
                                   + "where firstname = ?1 and name != ?2 order by firstname ASC,lastname ASC ");
        } catch (final MessageLabelException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Validate queryBuilder according to a waited query.
     * @param queryBuilder the query builder.
     * @param query the waited query.
     */
    private void validate(final JpqlEntityQueryBuilder<?> queryBuilder, final String query) {
        Assert.assertNotNull(queryBuilder.toString());
        Assert.assertEquals(queryBuilder.toString(), query);
    }

    /**
     * Test entity query.
     */
    @Test
    public void entityQuery() {
        // Null eager loading.
        try {
            new JpqlEntityQueryBuilder<User>(null, User.class, null);
            Assert.fail("Should throw an exception because null eager loading is not authorized.");
        } catch (final MessageLabelException e) {
            Assert.assertTrue(true);
        }

        // Empty eager loading.
        User user = new User();
        JpqlEntityQueryBuilder<User> queryBuilder = newInstance(User.class, user);
        Assert.assertNotNull(queryBuilder.getAlias(user));
        Assert.assertEquals(queryBuilder.getAlias(user), queryBuilder.getFrom().getRootAlias());
        Assert.assertNotNull(queryBuilder.getEager("o"));
        Assert.assertEquals(queryBuilder.getEager("o"), user);
        validate(queryBuilder, "select distinct o from " + User.class.getName() + " o ");

        // eager loading + criteria
        try {
            user = new User();
            user.setLogin("qjafcunuas");
            queryBuilder = newInstance(User.class, user);
            queryBuilder.getWhere().and(user);
            Assert.assertNotNull(queryBuilder.getAlias(user));
            Assert.assertEquals(queryBuilder.getAlias(user), queryBuilder.getFrom().getRootAlias());
            Assert.assertNotNull(queryBuilder.getEager("o"));
            Assert.assertEquals(queryBuilder.getEager("o"), user);
            Assert.assertEquals(queryBuilder.toString(), "select distinct o from " + User.class.getName() + " o where o.login = ?1 ");
        } catch (final MessageLabelException e) {
            log.error("Cannot build query", e);
            Assert.fail("Cannot build query");
        }

    }

    /**
     * Test entity query.
     */
    @Test
    public void entityQueryManyToOne() {

        // Only eager loading.
        User user = new User();
        UserGroup group = new UserGroup();
        user.setManyToOneGroup(group);
        JpqlEntityQueryBuilder<User> queryBuilder = newInstance(User.class, user);
        Assert.assertNotNull(queryBuilder.getAlias(user));
        Assert.assertEquals(queryBuilder.getAlias(user), queryBuilder.getFrom().getRootAlias());
        Assert.assertNotNull(queryBuilder.getEager("o"));
        Assert.assertEquals(queryBuilder.getEager("o"), user);
        Assert.assertNotNull(queryBuilder.getAlias(group));
        Assert.assertEquals(queryBuilder.getAlias(group), "o.manyToOneGroup");
        Assert.assertNotNull(queryBuilder.getEager("o.manyToOneGroup"));
        Assert.assertEquals(queryBuilder.getEager("o.manyToOneGroup"), group);
        Assert.assertEquals(queryBuilder.toString(), "select distinct o from " + User.class.getName() + " o left join fetch o.manyToOneGroup ");

        // Only criteria
        try {
            user = new User();
            user.setLogin("qjafcunuas");
            group = new UserGroup();
            group.setName("groupName");
            user.setManyToOneGroup(group);
            queryBuilder = newInstance(User.class);
            queryBuilder.getWhere().and(user);
            Assert.assertNull(queryBuilder.getAlias(user));
            Assert.assertNull(queryBuilder.getAlias(group));
            Assert.assertNull(queryBuilder.getEager("o"));
            Assert.assertNull(queryBuilder.getEager("o1"));
            Assert.assertEquals(queryBuilder.toString(),
                                "select distinct o from " + User.class.getName() + " o where o.login = ?1 and o.manyToOneGroup.name = ?2 ");
        } catch (final MessageLabelException e) {
            log.error("Cannot build query", e);
            Assert.fail("Cannot build query");
        }

        // eager loading + criteria
        try {
            user = new User();
            user.setLogin("qjafcunuas");
            group = new UserGroup();
            group.setName("groupName");
            user.setManyToOneGroup(group);
            queryBuilder = newInstance(User.class, user);
            queryBuilder.getWhere().and(user);
            Assert.assertNotNull(queryBuilder.getAlias(user));
            Assert.assertEquals(queryBuilder.getAlias(user), queryBuilder.getFrom().getRootAlias());
            Assert.assertNotNull(queryBuilder.getEager("o"));
            Assert.assertEquals(queryBuilder.getEager("o"), user);
            Assert.assertNotNull(queryBuilder.getAlias(group));
            Assert.assertEquals(queryBuilder.getAlias(group), "o.manyToOneGroup");
            Assert.assertNotNull(queryBuilder.getEager("o.manyToOneGroup"));
            Assert.assertEquals(queryBuilder.getEager("o.manyToOneGroup"), group);
            Assert.assertEquals(queryBuilder.toString(), "select distinct o from " + User.class.getName()
                                                         + " o left join fetch o.manyToOneGroup where o.login = ?1 and o.manyToOneGroup.name = ?2 ");
        } catch (final MessageLabelException e) {
            log.error("Cannot build query", e);
            Assert.fail("Cannot build query");
        }

    }

    /**
     * Test entity query.
     */
    @Test
    public void entityQueryOneToOne() {

        // Only eager loading.
        User user = new User();
        UserGroup group = new UserGroup();
        user.setOneToOneGroup(group);
        JpqlEntityQueryBuilder<User> queryBuilder = newInstance(User.class, user);
        Assert.assertNotNull(queryBuilder.getAlias(user));
        Assert.assertEquals(queryBuilder.getAlias(user), queryBuilder.getFrom().getRootAlias());
        Assert.assertNotNull(queryBuilder.getEager("o"));
        Assert.assertEquals(queryBuilder.getEager("o"), user);
        Assert.assertNotNull(queryBuilder.getAlias(group));
        Assert.assertEquals(queryBuilder.getAlias(group), "o.oneToOneGroup");
        Assert.assertNotNull(queryBuilder.getEager("o.oneToOneGroup"));
        Assert.assertEquals(queryBuilder.getEager("o.oneToOneGroup"), group);
        Assert.assertEquals(queryBuilder.toString(), "select distinct o from " + User.class.getName() + " o left join fetch o.oneToOneGroup ");

        // Only criteria
        try {
            user = new User();
            user.setLogin("qjafcunuas");
            group = new UserGroup();
            group.setName("groupName");
            user.setOneToOneGroup(group);
            queryBuilder = newInstance(User.class);
            queryBuilder.getWhere().and(user);
            Assert.assertNull(queryBuilder.getAlias(user));
            Assert.assertNull(queryBuilder.getAlias(group));
            Assert.assertNull(queryBuilder.getEager("o"));
            Assert.assertNull(queryBuilder.getEager("o.group"));
            Assert.assertEquals(queryBuilder.toString(),
                                "select distinct o from " + User.class.getName() + " o where o.login = ?1 and o.oneToOneGroup.name = ?2 ");
        } catch (final MessageLabelException e) {
            log.error("Cannot build query", e);
            Assert.fail("Cannot build query");
        }

        // eager loading + criteria
        try {
            user = new User();
            user.setLogin("qjafcunuas");
            group = new UserGroup();
            group.setName("groupName");
            user.setOneToOneGroup(group);
            queryBuilder = newInstance(User.class, user);
            queryBuilder.getWhere().and(user);
            Assert.assertNotNull(queryBuilder.getAlias(user));
            Assert.assertEquals(queryBuilder.getAlias(user), queryBuilder.getFrom().getRootAlias());
            Assert.assertNotNull(queryBuilder.getEager("o"));
            Assert.assertEquals(queryBuilder.getEager("o"), user);
            Assert.assertNotNull(queryBuilder.getAlias(group));
            Assert.assertEquals(queryBuilder.getAlias(group), "o.oneToOneGroup");
            Assert.assertNotNull(queryBuilder.getEager("o.oneToOneGroup"));
            Assert.assertEquals(queryBuilder.getEager("o.oneToOneGroup"), group);
            Assert.assertEquals(queryBuilder.toString(), "select distinct o from " + User.class.getName()
                                                         + " o left join fetch o.oneToOneGroup where o.login = ?1 and o.oneToOneGroup.name = ?2 ");
        } catch (final MessageLabelException e) {
            log.error("Cannot build query", e);
            Assert.fail("Cannot build query");
        }

    }

    /**
     * Test entity query.
     */
    @Test
    public void entityQueryManyToMany() {

        // Only eager loading.
        User user = new User();
        UserGroup group = new UserGroup();
        user.getManyToManyGroups().add(group);
        JpqlEntityQueryBuilder<User> queryBuilder = newInstance(User.class, user);
        Assert.assertNotNull(queryBuilder.getAlias(user));
        Assert.assertEquals(queryBuilder.getAlias(user), queryBuilder.getFrom().getRootAlias());
        Assert.assertNotNull(queryBuilder.getEager("o"));
        Assert.assertEquals(queryBuilder.getEager("o"), user);
        Assert.assertNotNull(queryBuilder.getAlias(group));
        if (JpaProviderFactory.getInstance().getImplementation().isFetchAliasable()) {
            Assert.assertEquals(queryBuilder.getAlias(group), "o1");
            Assert.assertNotNull(queryBuilder.getEager("o1"));
            Assert.assertEquals(queryBuilder.getEager("o1"), group);
            Assert.assertEquals(queryBuilder.toString(),
                                "select distinct o from " + User.class.getName() + " o left join fetch o.manyToManyGroups o1 ");
        } else {
            Assert.assertEquals(queryBuilder.toString(), "select distinct o from " + User.class.getName() + " o left join fetch o.manyToManyGroups ");

        }

        // Only criteria
        try {
            user = new User();
            user.setLogin("qjafcunuas");
            group = new UserGroup();
            group.setName("groupName");
            user.getManyToManyGroups().add(group);
            queryBuilder = newInstance(User.class);
            queryBuilder.getWhere().and(user);
            Assert.assertNull(queryBuilder.getAlias(user));
            Assert.assertNull(queryBuilder.getAlias(group));
            Assert.assertNull(queryBuilder.getEager("o"));
            Assert.assertNull(queryBuilder.getEager("o1"));
            Assert.assertEquals(queryBuilder.toString(), "select distinct o from " + User.class.getName()
                                                         + " o inner join o.manyToManyGroups o1 where o.login = ?1 and o1.name = ?2 ");
        } catch (final MessageLabelException e) {
            log.error("Cannot build query", e);
            Assert.fail("Cannot build query");
        }

        // eager loading + criteria
        try {
            user = new User();
            user.setLogin("qjafcunuas");
            group = new UserGroup();
            group.setName("groupName");
            user.getManyToManyGroups().add(group);
            queryBuilder = newInstance(User.class, user);
            queryBuilder.getWhere().and(user);
            Assert.assertNotNull(queryBuilder.getAlias(user));
            Assert.assertEquals(queryBuilder.getAlias(user), queryBuilder.getFrom().getRootAlias());
            Assert.assertNotNull(queryBuilder.getEager("o"));
            Assert.assertEquals(queryBuilder.getEager("o"), user);
            Assert.assertNotNull(queryBuilder.getAlias(group));
            if (JpaProviderFactory.getInstance().getImplementation().isFetchAliasable()) {
                Assert.assertEquals(queryBuilder.getAlias(group), "o1");
                Assert.assertNotNull(queryBuilder.getEager("o1"));
                Assert.assertEquals(queryBuilder.getEager("o1"), group);
                Assert.assertEquals(queryBuilder.toString(), "select distinct o from " + User.class
                        .getName() + " o left join fetch o.manyToManyGroups o1 inner join o.manyToManyGroups o2 where o.login = ?1 and o2.name = ?2 ");
            } else {
                Assert.assertEquals(queryBuilder
                        .toString(), "select distinct o from " + User.class.getName()
                                     + " o left join fetch o.manyToManyGroups inner join o.manyToManyGroups o1 where o.login = ?1 and o1.name = ?2 ");
            }
        } catch (final MessageLabelException e) {
            log.error("Cannot build query", e);
            Assert.fail("Cannot build query");
        }

    }

    /**
     * Test entity query.
     */
    @Test
    public void entityQueryOneToMany() {

        // Only eager loading.
        User user = new User();
        UserAddress address = new UserAddress(user);
        City city = new City();
        address.setCity(city);
        JpqlEntityQueryBuilder<User> queryBuilder = newInstance(User.class, user);
        Assert.assertNotNull(queryBuilder.getAlias(user));
        Assert.assertEquals(queryBuilder.getAlias(user), queryBuilder.getFrom().getRootAlias());
        Assert.assertNotNull(queryBuilder.getEager("o"));
        Assert.assertEquals(queryBuilder.getEager("o"), user);
        Assert.assertNotNull(queryBuilder.getAlias(address));
        if (JpaProviderFactory.getInstance().getImplementation().isFetchAliasable()) {
            Assert.assertEquals(queryBuilder.getAlias(address), "o1");
            Assert.assertNotNull(queryBuilder.getEager("o1"));
            Assert.assertEquals(queryBuilder.getEager("o1"), address);
            Assert.assertNotNull(queryBuilder.getAlias(city));
            Assert.assertEquals(queryBuilder.getAlias(city), "o1.city");
            Assert.assertNotNull(queryBuilder.getEager("o1.city"));
            Assert.assertEquals(queryBuilder.getEager("o1.city"), city);
            Assert.assertEquals(queryBuilder.toString(),
                                "select distinct o from " + User.class.getName() + " o left join fetch o.addresses o1 left join fetch o1.city ");
        } else {
            Assert.assertEquals(queryBuilder.toString(), "select distinct o from " + User.class.getName() + " o left join fetch o.addresses ");
        }

        // Only criteria
        try {
            user = new User();
            user.setLogin("qjafcunuas");
            address = new UserAddress(user);
            address.setStreet("street name");
            city = new City();
            address.setCity(city);
            city.setName("city name");
            queryBuilder = newInstance(User.class);
            queryBuilder.getWhere().and(user);
            Assert.assertNull(queryBuilder.getAlias(user));
            Assert.assertNull(queryBuilder.getAlias(address));
            Assert.assertNull(queryBuilder.getAlias(city));
            Assert.assertNull(queryBuilder.getEager("o"));
            Assert.assertNull(queryBuilder.getEager("o1"));
            Assert.assertNull(queryBuilder.getEager("o2"));
            Assert.assertEquals(queryBuilder
                    .toString(), "select distinct o from " + User.class.getName()
                                 + " o inner join o.addresses o1 where o.login = ?1 and o1.street = ?2 and o1.city.name = ?3 ");
        } catch (final MessageLabelException e) {
            log.error("Cannot build query", e);
            Assert.fail("Cannot build query");
        }

        // eager loading + criteria
        try {
            user = new User();
            user.setLogin("qjafcunuas");
            address = new UserAddress(user);
            address.setStreet("street name");
            city = new City();
            address.setCity(city);
            city.setName("city name");
            queryBuilder = newInstance(User.class, user);
            queryBuilder.getWhere().and(user);
            Assert.assertNotNull(queryBuilder.getAlias(user));
            Assert.assertEquals(queryBuilder.getAlias(user), queryBuilder.getFrom().getRootAlias());
            Assert.assertNotNull(queryBuilder.getEager("o"));
            Assert.assertEquals(queryBuilder.getEager("o"), user);
            if (JpaProviderFactory.getInstance().getImplementation().isFetchAliasable()) {
                Assert.assertNotNull(queryBuilder.getAlias(address));
                Assert.assertEquals(queryBuilder.getAlias(address), "o1");
                Assert.assertNotNull(queryBuilder.getEager("o1"));
                Assert.assertEquals(queryBuilder.getEager("o1"), address);
                Assert.assertNotNull(queryBuilder.getAlias(city));
                Assert.assertEquals(queryBuilder.getAlias(city), "o1.city");
                Assert.assertNotNull(queryBuilder.getEager("o1.city"));
                Assert.assertEquals(queryBuilder.getEager("o1.city"), city);
                Assert.assertEquals(queryBuilder.toString(), "select distinct o from " + User.class
                        .getName() + " o left join fetch o.addresses o1 left join fetch o1.city inner join o.addresses o2 where o.login = ?1 and o2.street = ?2 and o2.city.name = ?3 ");
            } else {
                Assert.assertEquals(queryBuilder.toString(), "select distinct o from " + User.class
                        .getName() + " o left join fetch o.addresses inner join o.addresses o1 where o.login = ?1 and o1.street = ?2 and o1.city.name = ?3 ");
            }
        } catch (final MessageLabelException e) {
            log.error("Cannot build query", e);
            Assert.fail("Cannot build query");
        }

    }

    /**
     * Test entity query.
     */
    @Test
    public void entityQueryOneToManyCompositePk() {

        // Only eager loading.
        User user = new User();
        UserSurname surname = new UserSurname(user, null);
        JpqlEntityQueryBuilder<User> queryBuilder = newInstance(User.class, user);
        Assert.assertNotNull(queryBuilder.getAlias(user));
        Assert.assertEquals(queryBuilder.getAlias(user), queryBuilder.getFrom().getRootAlias());
        Assert.assertNotNull(queryBuilder.getEager("o"));
        Assert.assertEquals(queryBuilder.getEager("o"), user);
        Assert.assertNotNull(queryBuilder.getAlias(surname));
        if (JpaProviderFactory.getInstance().getImplementation().isFetchAliasable()) {
            Assert.assertEquals(queryBuilder.getAlias(surname), "o1");
            Assert.assertNotNull(queryBuilder.getEager("o1"));
            Assert.assertEquals(queryBuilder.getEager("o1"), surname);
            Assert.assertEquals(queryBuilder.toString(), "select distinct o from " + User.class.getName() + " o left join fetch o.surnames o1 ");
        } else {
            Assert.assertEquals(queryBuilder.toString(), "select distinct o from " + User.class.getName() + " o left join fetch o.surnames ");
        }

        // Only criteria
        try {
            user = new User();
            user.setLogin("qjafcunuas");
            surname = new UserSurname(user, null);
            surname.setSurname("the surname");
            queryBuilder = newInstance(User.class);
            queryBuilder.getWhere().and(user);
            Assert.assertNull(queryBuilder.getAlias(user));
            Assert.assertNull(queryBuilder.getAlias(surname));
            Assert.assertNull(queryBuilder.getEager("o"));
            Assert.assertNull(queryBuilder.getEager("o1"));
            Assert.assertEquals(queryBuilder.toString(), "select distinct o from " + User.class.getName()
                                                         + " o inner join o.surnames o1 where o.login = ?1 and o1.surname = ?2 ");
        } catch (final MessageLabelException e) {
            log.error("Cannot build query", e);
            Assert.fail("Cannot build query");
        }

        // eager loading + criteria
        try {
            user = new User();
            user.setLogin("qjafcunuas");
            surname = new UserSurname(user, null);
            surname.setSurname("the surname");
            queryBuilder = newInstance(User.class, user);
            queryBuilder.getWhere().and(user);
            Assert.assertNotNull(queryBuilder.getAlias(user));
            Assert.assertEquals(queryBuilder.getAlias(user), queryBuilder.getFrom().getRootAlias());
            Assert.assertNotNull(queryBuilder.getEager("o"));
            Assert.assertEquals(queryBuilder.getEager("o"), user);
            Assert.assertNotNull(queryBuilder.getAlias(surname));
            if (JpaProviderFactory.getInstance().getImplementation().isFetchAliasable()) {
                Assert.assertEquals(queryBuilder.getAlias(surname), "o1");
                Assert.assertNotNull(queryBuilder.getEager("o1"));
                Assert.assertEquals(queryBuilder.getEager("o1"), surname);
                Assert.assertEquals(queryBuilder
                        .toString(), "select distinct o from " + User.class.getName()
                                     + " o left join fetch o.surnames o1 inner join o.surnames o2 where o.login = ?1 and o2.surname = ?2 ");
            } else {
                Assert.assertEquals(queryBuilder
                        .toString(), "select distinct o from " + User.class.getName()
                                     + " o left join fetch o.surnames inner join o.surnames o1 where o.login = ?1 and o1.surname = ?2 ");
            }
        } catch (final MessageLabelException e) {
            log.error("Cannot build query", e);
            Assert.fail("Cannot build query");
        }

    }

}
