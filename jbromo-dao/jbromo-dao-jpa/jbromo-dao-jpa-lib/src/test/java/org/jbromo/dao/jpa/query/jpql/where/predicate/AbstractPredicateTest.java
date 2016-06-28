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

import java.util.Iterator;
import java.util.List;

import org.jbromo.common.CollectionUtil;
import org.jbromo.common.IntegerUtil;
import org.jbromo.common.ListUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.common.StringUtil;
import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.common.invocation.ParameterizedTypeUtil;
import org.jbromo.dao.jpa.query.jpql.AbstractJpqlQueryBuilder;
import org.jbromo.dao.jpa.query.jpql.JpqlEntityQueryBuilder;
import org.jbromo.dao.jpa.query.jpql.where.JpqlWhereBuilder;
import org.jbromo.sample.server.model.src.City;
import org.jbromo.sample.server.model.src.User;
import org.jbromo.sample.server.model.src.UserAddress;
import org.jbromo.sample.server.model.src.UserGroup;
import org.jbromo.sample.server.model.src.UserSurname;
import org.jbromo.sample.server.model.test.builder.EntityBuilderFactory;
import org.junit.Assert;
import org.junit.Test;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Define JUnit AbstractPredicate class.
 * @author qjafcunuas
 * @param
 *            <P>
 *            the predicate type.
 */
@Slf4j
public abstract class AbstractPredicateTest<P extends AbstractPredicate> {

    /**
     * Define a field name.
     */
    static final String FIELD_NAME = "name";

    /**
     * The predicate class.
     */
    @Getter(AccessLevel.PROTECTED)
    private final Class<P> predicateClass = ParameterizedTypeUtil.getClass(this, 0);

    /**
     * Return the expression's operator.
     * @return the operator.
     */
    protected abstract String getOperator();

    /**
     * Return a new instance of the predicate to test.
     * @return the new instance.
     */
    protected P newInstance() {
        return ObjectUtil.newInstance(getPredicateClass(), new JpqlWhereBuilder(new AbstractJpqlQueryBuilder<User>(null, User.class)));
    }

    /**
     * Return a new instance of the predicate to test.
     * @return the new instance.
     */
    protected P newEntityInstance() {
        return ObjectUtil.newInstance(getPredicateClass(), new JpqlWhereBuilder(new JpqlEntityQueryBuilder<User>(null, User.class)));
    }

    /**
     * Validate predicate to waited query.
     * @param predicate the predicate.
     * @param query the waited query.
     * @param parameters the query parameters.
     */
    protected void validate(final AbstractPredicate predicate, final String query, final Object... parameters) {
        // Build
        final List<Object> list = ListUtil.toList();
        final StringBuilder builder = new StringBuilder("predicate: ");
        predicate.build(builder, list);
        // Verify query.
        Assert.assertEquals(builder.toString(), "predicate: " + query);
        Assert.assertEquals(predicate.toString(), query);
        // Verify parameters.
        Assert.assertTrue(CollectionUtil.identical(list, ListUtil.toList(parameters)));
    }

    /**
     * Validate predicate to waited query.
     * @param predicate the predicate.
     * @param query the waited query, wich method add parenthesis around.
     * @param parameters the query parameters.
     */
    protected void validateParenthesis(final AbstractPredicate predicate, final String query, final Object... parameters) {
        // Build
        final List<Object> list = ListUtil.toList();
        final StringBuilder builder = new StringBuilder("predicate: ");
        predicate.build(builder, list);
        final String queryp = predicate.isNeedParenthesis() && !query.isEmpty() ? "(" + query + ") " : query;
        // Verify query.
        Assert.assertEquals(builder.toString(), "predicate: " + queryp);
        Assert.assertEquals(predicate.toString(), queryp);
        // Verify parameters.
        Assert.assertTrue(CollectionUtil.identical(list, ListUtil.toList(parameters)));
    }

    /**
     * Define empty predicate test.
     */
    public abstract void empty();

    /**
     * Test equals method.
     */
    @Test
    public void equalsTest() {
        final P predicate = newInstance();
        final int parameter = RandomUtil.nextInt();
        try {
            predicate.equals(FIELD_NAME, null);
            validate(predicate, "");
            predicate.equals(FIELD_NAME, parameter);
            validate(predicate, "name = ?1 ", parameter);
        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test notEquals method.
     */
    @Test
    public void notEquals() {
        final P predicate = newInstance();
        final int parameter = RandomUtil.nextInt();
        try {
            predicate.notEquals(FIELD_NAME, null);
            validate(predicate, "");
            predicate.notEquals(FIELD_NAME, parameter);
            validate(predicate, "name != ?1 ", parameter);
        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test in method.
     */
    @Test
    public void in() {
        // Null parameter
        P predicate = newInstance();
        List<Integer> parameters;
        try {
            predicate.in(FIELD_NAME, null);
            validate(predicate, "");
        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
        // One parameter
        predicate = newInstance();
        parameters = ListUtil.toList(RandomUtil.nextInt());
        try {
            predicate.in(FIELD_NAME, parameters);
            validate(predicate, "name = ?1 ", parameters.get(0));
        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
        // Two parameters
        predicate = newInstance();
        parameters = ListUtil.toList(RandomUtil.nextInt(), RandomUtil.nextInt());
        try {
            predicate.in(FIELD_NAME, parameters);
            validate(predicate, "name in (?1 ) ", parameters);
        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test notIn method.
     */
    @Test
    public void notIn() {
        // Null parameter
        P predicate = newInstance();
        List<Integer> parameters;
        try {
            predicate.notIn(FIELD_NAME, null);
            validate(predicate, "");
        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
        // One parameter
        predicate = newInstance();
        parameters = ListUtil.toList(RandomUtil.nextInt());
        try {
            predicate.notIn(FIELD_NAME, parameters);
            validate(predicate, "name != ?1 ", parameters.get(0));
        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
        // Two parameters
        predicate = newInstance();
        parameters = ListUtil.toList(RandomUtil.nextInt(), RandomUtil.nextInt());
        try {
            predicate.notIn(FIELD_NAME, parameters);
            validate(predicate, "name not in (?1 ) ", parameters);
        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test like method.
     */
    @Test
    public void like() {
        final P predicate = newInstance();
        final String parameter = RandomUtil.nextString().replace(StringUtil.STAR, "1");
        try {
            predicate.like(FIELD_NAME, null);
            validate(predicate, "");
            predicate.like(FIELD_NAME, StringUtil.STAR + parameter + StringUtil.STAR);
            validate(predicate, "name like ?1 ", StringUtil.PERCENT + parameter + StringUtil.PERCENT);
        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test isNull method.
     */
    @Test
    public void isNull() {
        final P predicate = newInstance();
        try {
            predicate.isNull(FIELD_NAME);
            validate(predicate, "name is null ");
        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test greaterThan method.
     */
    @Test
    public void greaterThan() {
        final P predicate = newInstance();
        final int parameter = RandomUtil.nextInt();
        try {
            predicate.greaterThan(FIELD_NAME, null);
            validate(predicate, "");
            predicate.greaterThan(FIELD_NAME, parameter);
            validate(predicate, "name > ?1 ", parameter);
        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test greaterOrEquals method.
     */
    @Test
    public void greaterOrEquals() {
        final P predicate = newInstance();
        final int parameter = RandomUtil.nextInt();
        try {
            predicate.greaterOrEquals(FIELD_NAME, null);
            validate(predicate, "");
            predicate.greaterOrEquals(FIELD_NAME, parameter);
            validate(predicate, "name >= ?1 ", parameter);
        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test lessThan method.
     */
    @Test
    public void lessThan() {
        final P predicate = newInstance();
        final int parameter = RandomUtil.nextInt();
        try {
            predicate.lessThan(FIELD_NAME, null);
            validate(predicate, "");
            predicate.lessThan(FIELD_NAME, parameter);
            validate(predicate, "name < ?1 ", parameter);
        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test lessOrEquals method.
     */
    @Test
    public void lessOrEquals() {
        final P predicate = newInstance();
        final int parameter = RandomUtil.nextInt();
        try {
            predicate.lessOrEquals(FIELD_NAME, null);
            validate(predicate, "");
            predicate.lessOrEquals(FIELD_NAME, parameter);
            validate(predicate, "name <= ?1 ", parameter);
        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test multi-criteria on same operator..
     */
    public abstract void multiCriteria();

    /**
     * Test and method.
     */
    public abstract void and();

    /**
     * Test or method.
     */
    public abstract void or();

    /**
     * Test or method.
     */
    public abstract void not();

    /**
     * Test and(entity) method for simple entity.
     */
    @Test
    public void andEntity() {
        try {
            User user;
            // Null entity.
            AbstractPredicate predicate = newInstance();
            predicate.and(null);
            validate(predicate, "");

            // Empty entity.
            predicate = newInstance();
            predicate.and(new User());
            validate(predicate, "");

            // Entity with like operator
            predicate = newInstance();
            user = new User();
            user.setFirstname("*Bruno");
            predicate.and(user);
            validate(predicate, "o.firstname like ?1 ", "%Bruno");

            // UserGroup
            final UserGroup group = EntityBuilderFactory.getInstance().getBuilder(UserGroup.class).newEntity(false);
            predicate = newInstance();
            predicate.and(group);
            validate(predicate, "o.name = ?1 ", group.getName());

            // User
            user = EntityBuilderFactory.getInstance().getBuilder(User.class).newEntity(false);
            user.getAddresses().clear();
            user.getSurnames().clear();
            predicate = newInstance();
            predicate.and(user);
            validate(predicate,
                     "o.login = ?1 and o.encryptedPassword = ?2 and o.firstname = ?3 and o.lastname = ?4 and o.active = ?5 and o.calendar.creationDate = ?6 and o.calendar.updateDate = ?7 ",
                     user.getLogin(), user.getEncryptedPassword(), user.getFirstname(), user.getLastname(), user.getActive(),
                     user.getCalendar().getCreationDate(), user.getCalendar().getUpdateDate());
            Assert.assertEquals(predicate.getFrom().toString(), "from " + User.class.getName() + " o ");

        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test and(entity) method for ManyToOne.
     */
    @Test
    public void andEntityManyToOne() {
        try {

            // User + manyToOne
            final UserGroup group = EntityBuilderFactory.getInstance().getBuilder(UserGroup.class).newEntity(false);
            final User user = EntityBuilderFactory.getInstance().getBuilder(User.class).newEntity(false);
            user.getAddresses().clear();
            user.getSurnames().clear();
            user.setManyToOneGroup(group);
            final AbstractPredicate predicate = newEntityInstance();
            predicate.and(user);
            validate(predicate,
                     "o.login = ?1 and o.encryptedPassword = ?2 and o.firstname = ?3 and o.lastname = ?4 and o.active = ?5 and o.calendar.creationDate = ?6 and o.calendar.updateDate = ?7 and o.manyToOneGroup.name = ?8 ",
                     user.getLogin(), user.getEncryptedPassword(), user.getFirstname(), user.getLastname(), user.getActive(),
                     user.getCalendar().getCreationDate(), user.getCalendar().getUpdateDate(), user.getManyToOneGroup().getName());
            Assert.assertEquals(predicate.getFrom().toString(), "from " + User.class.getName() + " o ");

        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }

    }

    /**
     * Test and(entity) method for ManyToMany.
     */
    @Test
    public void andEntityManyToMany() {

        try {
            Object[] parameters;
            // create user and groups.
            final UserGroup group = EntityBuilderFactory.getInstance().getBuilder(UserGroup.class).newEntity(false);
            final UserGroup anotherGroup = EntityBuilderFactory.getInstance().getBuilder(UserGroup.class).newEntity(false);
            final User user = EntityBuilderFactory.getInstance().getBuilder(User.class).newEntity(false);
            // Set groups to user.
            user.getAddresses().clear();
            user.getSurnames().clear();
            user.getManyToManyGroups().add(group);
            user.getManyToManyGroups().add(anotherGroup);

            // test predicate.
            final AbstractPredicate predicate = newInstance();
            predicate.and(user);
            // Order parameter according to group position.
            if (ObjectUtil.same(user.getManyToManyGroups().iterator().next(), group)) {
                parameters = new Object[] {user.getLogin(), user.getEncryptedPassword(), user.getFirstname(), user.getLastname(), user.getActive(),

                        user.getCalendar().getCreationDate(), user.getCalendar().getUpdateDate(), group.getName(), anotherGroup.getName()};
            } else {
                parameters = new Object[] {user.getLogin(), user.getEncryptedPassword(), user.getFirstname(), user.getLastname(), user.getActive(),
                                           user.getCalendar().getCreationDate(), user.getCalendar().getUpdateDate(), anotherGroup.getName(),
                                           group.getName()};
            }
            validate(predicate,
                     "o.login = ?1 and o.encryptedPassword = ?2 and o.firstname = ?3 and o.lastname = ?4 and o.active = ?5 and o.calendar.creationDate = ?6 and o.calendar.updateDate = ?7 and (o1.name = ?8 or o1.name = ?9 ) ",
                     parameters);
            Assert.assertEquals(predicate.getFrom().toString(), "from " + User.class.getName() + " o inner join o.manyToManyGroups o1 ");

        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }

    }

    /**
     * Test and(entity) method for OneToOne.
     */
    @Test
    public void andEntityOneToOne() {

        try {
            // User + oneToOne
            final UserGroup group = EntityBuilderFactory.getInstance().getBuilder(UserGroup.class).newEntity(false);
            final User user = EntityBuilderFactory.getInstance().getBuilder(User.class).newEntity(false);
            user.getSurnames().clear();
            user.getAddresses().clear();
            user.setOneToOneGroup(group);
            final AbstractPredicate predicate = newEntityInstance();
            predicate.and(user);
            validate(predicate,
                     "o.login = ?1 and o.encryptedPassword = ?2 and o.firstname = ?3 and o.lastname = ?4 and o.active = ?5 and o.calendar.creationDate = ?6 and o.calendar.updateDate = ?7 and o.oneToOneGroup.name = ?8 ",
                     user.getLogin(), user.getEncryptedPassword(), user.getFirstname(), user.getLastname(), user.getActive(),
                     user.getCalendar().getCreationDate(), user.getCalendar().getUpdateDate(), user.getOneToOneGroup().getName());
            Assert.assertEquals(predicate.getFrom().toString(), "from " + User.class.getName() + " o ");

        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }

    }

    /**
     * Build a user with n addresses.
     * @param count the addresses number.
     * @return the user.
     */
    private User buildAddresses(final int count) {
        User user = null;
        while (user == null || user.getAddresses().size() < count) {
            user = EntityBuilderFactory.getInstance().getBuilder(User.class).newEntity(false);
        }
        while (user.getAddresses().size() != count) {
            user.getAddresses().remove(user.getAddresses().iterator().next());
        }
        for (final UserAddress address : user.getAddresses()) {
            address.setCity(EntityBuilderFactory.getInstance().getBuilder(City.class).newEntity(false));
            Assert.assertSame(user, address.getUser());
        }
        user.getSurnames().clear();
        return user;
    }

    /**
     * Build a user with n surnames.
     * @param count the surnames number.
     * @return the user.
     */
    private User buildSurnames(final int count) {
        User user = null;
        while (user == null || user.getSurnames().size() < count) {
            user = EntityBuilderFactory.getInstance().getBuilder(User.class).newEntity(false);
        }
        while (user.getSurnames().size() != count) {
            user.getSurnames().remove(user.getSurnames().iterator().next());
        }
        for (final UserSurname surname : user.getSurnames()) {
            surname.setSurname(RandomUtil.nextString(IntegerUtil.INT_10));
            Assert.assertSame(user, surname.getUser());
        }
        user.getAddresses().clear();
        return user;
    }

    /**
     * Test and(entity) method for OneToMany.
     */
    @Test
    public void andEntityOneToMany() {

        try {
            Object[] parameters;

            // Build a user with 2 addresses.
            User user = buildAddresses(2);
            AbstractPredicate predicate = newEntityInstance();
            predicate.and(user);
            // Build waited parameters
            final Iterator<UserAddress> iter = user.getAddresses().iterator();
            UserAddress address = iter.next();
            parameters = new Object[] {user.getLogin(), user.getEncryptedPassword(), user.getFirstname(), user.getLastname(), user.getActive(),
                                       user.getCalendar().getCreationDate(), user.getCalendar().getUpdateDate(), address.getStreet(),
                                       address.getCity().getName(), address.getCity().getPostcode(), null, null, null};
            address = iter.next();
            parameters[IntegerUtil.INT_10] = address.getStreet();
            parameters[IntegerUtil.INT_11] = address.getCity().getName();
            parameters[IntegerUtil.INT_12] = address.getCity().getPostcode();
            validate(predicate,
                     "o.login = ?1 and o.encryptedPassword = ?2 and o.firstname = ?3 and o.lastname = ?4 and o.active = ?5 and o.calendar.creationDate = ?6 and o.calendar.updateDate = ?7 and (o1.street = ?8 and o1.city.name = ?9 and o1.city.postcode = ?10 or o1.street = ?11 and o1.city.name = ?12 and o1.city.postcode = ?13 ) ",
                     parameters);
            Assert.assertEquals(predicate.getFrom().toString(), "from " + User.class.getName() + " o inner join o.addresses o1 ");

            // Build a user with only city as not null.
            user = new User();
            user.getAddresses().add(new UserAddress());
            user.getAddresses().iterator().next().setCity(new City());
            user.getAddresses().iterator().next().getCity().setName("Cambrai");
            predicate = newEntityInstance();
            predicate.and(user);
            validate(predicate, "o1.city.name = ?1 ", user.getAddresses().iterator().next().getCity().getName());
            Assert.assertEquals(predicate.getFrom().toString(), "from " + User.class.getName() + " o inner join o.addresses o1 ");

            // Build a user with 2 surnames.
            user = buildSurnames(2);
            predicate = newEntityInstance();
            predicate.and(user);
            // Build waited parameters
            final Iterator<UserSurname> iterS = user.getSurnames().iterator();
            final UserSurname surname = iterS.next();
            parameters = new Object[] {user.getLogin(), user.getEncryptedPassword(), user.getFirstname(), user.getLastname(), user.getActive(),
                                       user.getCalendar().getCreationDate(), user.getCalendar().getUpdateDate(), surname.getSurname(),
                                       iterS.next().getSurname()};
            validate(predicate,
                     "o.login = ?1 and o.encryptedPassword = ?2 and o.firstname = ?3 and o.lastname = ?4 and o.active = ?5 and o.calendar.creationDate = ?6 and o.calendar.updateDate = ?7 and (o1.surname = ?8 or o1.surname = ?9 ) ",
                     parameters);
            Assert.assertEquals(predicate.getFrom().toString(), "from " + User.class.getName() + " o inner join o.surnames o1 ");

        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }

    }

    /**
     * Test or(entity) method for simple entity.
     */
    @Test
    public void orEntity() {
        try {
            User user;
            // Null entity.
            AbstractPredicate predicate = newInstance();
            predicate.or(null);
            validate(predicate, "");

            // Empty entity.
            predicate = newInstance();
            predicate.or(new User());
            validate(predicate, "");

            // Entity with like operator
            predicate = newInstance();
            user = new User();
            user.setFirstname("*Bruno");
            predicate.or(user);
            validate(predicate, "o.firstname like ?1 ", "%Bruno");

            // UserGroup
            final UserGroup group = EntityBuilderFactory.getInstance().getBuilder(UserGroup.class).newEntity(false);
            predicate = newInstance();
            predicate.or(group);
            validate(predicate, "o.name = ?1 ", group.getName());

            // User
            user = EntityBuilderFactory.getInstance().getBuilder(User.class).newEntity(false);
            user.getAddresses().clear();
            user.getSurnames().clear();
            predicate = newInstance();
            predicate.or(user);
            validate(predicate,
                     "(o.login = ?1 or o.encryptedPassword = ?2 or o.firstname = ?3 or o.lastname = ?4 or o.active = ?5 or o.calendar.creationDate = ?6 or o.calendar.updateDate = ?7 ) ",
                     user.getLogin(), user.getEncryptedPassword(), user.getFirstname(), user.getLastname(), user.getActive(),
                     user.getCalendar().getCreationDate(), user.getCalendar().getUpdateDate());
            Assert.assertEquals(predicate.getFrom().toString(), "from " + User.class.getName() + " o ");

        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test or(entity) method for ManyToOne.
     */
    @Test
    public void orEntityManyToOne() {
        try {

            // User + manyToOne
            final UserGroup group = EntityBuilderFactory.getInstance().getBuilder(UserGroup.class).newEntity(false);
            final User user = EntityBuilderFactory.getInstance().getBuilder(User.class).newEntity(false);
            user.getAddresses().clear();
            user.getSurnames().clear();
            user.setManyToOneGroup(group);
            final AbstractPredicate predicate = newEntityInstance();
            predicate.or(user);
            validate(predicate,
                     "(o.login = ?1 or o.encryptedPassword = ?2 or o.firstname = ?3 or o.lastname = ?4 or o.active = ?5 or o.calendar.creationDate = ?6 or o.calendar.updateDate = ?7 or o.manyToOneGroup.name = ?8 ) ",
                     user.getLogin(), user.getEncryptedPassword(), user.getFirstname(), user.getLastname(), user.getActive(),
                     user.getCalendar().getCreationDate(), user.getCalendar().getUpdateDate(), user.getManyToOneGroup().getName());
            Assert.assertEquals(predicate.getFrom().toString(), "from " + User.class.getName() + " o ");

        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }

    }

    /**
     * Test or(entity) method for ManyToMany.
     */
    @Test
    public void orEntityManyToMany() {

        try {
            Object[] parameters;
            // create user and groups.
            final UserGroup group = EntityBuilderFactory.getInstance().getBuilder(UserGroup.class).newEntity(false);
            final UserGroup anotherGroup = EntityBuilderFactory.getInstance().getBuilder(UserGroup.class).newEntity(false);
            final User user = EntityBuilderFactory.getInstance().getBuilder(User.class).newEntity(false);
            // Set groups to user.
            user.getAddresses().clear();
            user.getSurnames().clear();
            user.getManyToManyGroups().add(group);
            user.getManyToManyGroups().add(anotherGroup);

            // test predicate.
            final AbstractPredicate predicate = newInstance();
            predicate.or(user);
            // Order parameter according to group position.
            if (ObjectUtil.same(user.getManyToManyGroups().iterator().next(), group)) {
                parameters = new Object[] {user.getLogin(), user.getEncryptedPassword(), user.getFirstname(), user.getLastname(), user.getActive(),

                        user.getCalendar().getCreationDate(), user.getCalendar().getUpdateDate(), group.getName(), anotherGroup.getName()};
            } else {
                parameters = new Object[] {user.getLogin(), user.getEncryptedPassword(), user.getFirstname(), user.getLastname(), user.getActive(),
                                           user.getCalendar().getCreationDate(), user.getCalendar().getUpdateDate(), anotherGroup.getName(),
                                           group.getName()};
            }
            validate(predicate,
                     "(o.login = ?1 or o.encryptedPassword = ?2 or o.firstname = ?3 or o.lastname = ?4 or o.active = ?5 or o.calendar.creationDate = ?6 or o.calendar.updateDate = ?7 or o1.name = ?8 or o1.name = ?9 ) ",
                     parameters);
            Assert.assertEquals(predicate.getFrom().toString(), "from " + User.class.getName() + " o inner join o.manyToManyGroups o1 ");

        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }

    }

    /**
     * Test or(entity) method for OneToOne.
     */
    @Test
    public void orEntityOneToOne() {

        try {
            // User + oneToOne
            final UserGroup group = EntityBuilderFactory.getInstance().getBuilder(UserGroup.class).newEntity(false);
            final User user = EntityBuilderFactory.getInstance().getBuilder(User.class).newEntity(false);
            user.getAddresses().clear();
            user.getSurnames().clear();
            user.setOneToOneGroup(group);
            final AbstractPredicate predicate = newEntityInstance();
            predicate.or(user);
            validate(predicate,
                     "(o.login = ?1 or o.encryptedPassword = ?2 or o.firstname = ?3 or o.lastname = ?4 or o.active = ?5 or o.calendar.creationDate = ?6 or o.calendar.updateDate = ?7 or o.oneToOneGroup.name = ?8 ) ",
                     user.getLogin(), user.getEncryptedPassword(), user.getFirstname(), user.getLastname(), user.getActive(),
                     user.getCalendar().getCreationDate(), user.getCalendar().getUpdateDate(), user.getOneToOneGroup().getName());
            Assert.assertEquals(predicate.getFrom().toString(), "from " + User.class.getName() + " o ");

        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }

    }

    /**
     * Test or(entity) method for OneToMany.
     */
    @Test
    public void orEntityOneToMany() {

        try {
            Object[] parameters;

            // Build a user with 2 addresses.
            User user = buildAddresses(2);

            AbstractPredicate predicate = newEntityInstance();
            predicate.or(user);

            // Build waited parameters
            final Iterator<UserAddress> iter = user.getAddresses().iterator();
            UserAddress address = iter.next();
            parameters = new Object[] {user.getLogin(), user.getEncryptedPassword(), user.getFirstname(), user.getLastname(), user.getActive(),
                                       user.getCalendar().getCreationDate(), user.getCalendar().getUpdateDate(), address.getStreet(),
                                       address.getCity().getName(), address.getCity().getPostcode(), null, null, null};
            address = iter.next();
            parameters[IntegerUtil.INT_10] = address.getStreet();
            parameters[IntegerUtil.INT_11] = address.getCity().getName();
            parameters[IntegerUtil.INT_12] = address.getCity().getPostcode();

            validate(predicate,
                     "(o.login = ?1 or o.encryptedPassword = ?2 or o.firstname = ?3 or o.lastname = ?4 or o.active = ?5 or o.calendar.creationDate = ?6 or o.calendar.updateDate = ?7 or o1.street = ?8 or o1.city.name = ?9 or o1.city.postcode = ?10 or o1.street = ?11 or o1.city.name = ?12 or o1.city.postcode = ?13 ) ",
                     parameters);
            Assert.assertEquals(predicate.getFrom().toString(), "from " + User.class.getName() + " o inner join o.addresses o1 ");

            // Build a user with only city as not null.
            user = new User();
            user.getAddresses().add(new UserAddress());
            user.getAddresses().iterator().next().setCity(new City());
            user.getAddresses().iterator().next().getCity().setName("Cambrai");
            predicate = newEntityInstance();
            predicate.or(user);
            validate(predicate, "o1.city.name = ?1 ", user.getAddresses().iterator().next().getCity().getName());
            Assert.assertEquals(predicate.getFrom().toString(), "from " + User.class.getName() + " o inner join o.addresses o1 ");

            // Build a user with 2 surnames.
            user = buildSurnames(2);
            predicate = newEntityInstance();
            predicate.or(user);
            // Build waited parameters
            final Iterator<UserSurname> iterS = user.getSurnames().iterator();
            final UserSurname surname = iterS.next();
            parameters = new Object[] {user.getLogin(), user.getEncryptedPassword(), user.getFirstname(), user.getLastname(), user.getActive(),
                                       user.getCalendar().getCreationDate(), user.getCalendar().getUpdateDate(), surname.getSurname(),
                                       iterS.next().getSurname()};
            validate(predicate,
                     "(o.login = ?1 or o.encryptedPassword = ?2 or o.firstname = ?3 or o.lastname = ?4 or o.active = ?5 or o.calendar.creationDate = ?6 or o.calendar.updateDate = ?7 or o1.surname = ?8 or o1.surname = ?9 ) ",
                     parameters);
            Assert.assertEquals(predicate.getFrom().toString(), "from " + User.class.getName() + " o inner join o.surnames o1 ");

        } catch (final MessageLabelException e) {
            log.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the isEmpty method.
     */
    public abstract void isEmpty();

}
