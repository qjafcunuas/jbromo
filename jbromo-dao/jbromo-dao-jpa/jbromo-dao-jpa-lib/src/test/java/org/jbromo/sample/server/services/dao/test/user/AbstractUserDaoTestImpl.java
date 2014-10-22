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
package org.jbromo.sample.server.services.dao.test.user;

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.common.IntegerUtil;
import org.jbromo.dao.common.Dao;
import org.jbromo.sample.server.model.src.User;
import org.jbromo.sample.server.services.dao.src.user.IUserDao;
import org.jbromo.sample.server.services.dao.test.AbstractEntityDaoTest;
import org.jbromo.sample.server.services.dao.test.city.CityDaoTest;
import org.jbromo.sample.server.services.dao.test.usergroup.UserGroupDaoTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * User DAO test.
 *
 * @author qjafcunuas
 *
 */
public abstract class AbstractUserDaoTestImpl extends
        AbstractEntityDaoTest<User, Long, IUserDao> {

    /**
     * The dao to test.
     */
    @Inject
    @Dao
    @Getter(AccessLevel.PROTECTED)
    private IUserDao crud;

    /**
     * The usergroup tester.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private UserGroupDaoTest groupTest;

    /**
     * The user tester.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private CityDaoTest cityTest;

    /**
     * Initializes tests.
     */
    @Before
    public void beforeAbstractCityDaoTestImpl() {
        Assert.assertNotNull(getGroupTest());
        Assert.assertNotNull(getCityTest());

        getGroupTest().cache(IntegerUtil.INT_10);
        getCityTest().cache(IntegerUtil.INT_10);

        cache(IntegerUtil.INT_10);
    }

    /**
     * Test findTypedQuery method.
     */
    @Test
    public void findTypedQuery() {
        final User user = create();
        try {
            Assert.assertEquals(getCrud().findTypedQuery(user.getPrimaryKey()),
                    user);
            Assert.assertNull(getCrud().findTypedQuery(-1L));
        } finally {
            delete(user);
        }
    }

    /**
     * Test findTypedQuery method.
     */
    @Test
    public void findSingleNamedQuery() {
        final User user = create();
        try {
            Assert.assertEquals(
                    getCrud().findSingleNamedQuery(user.getLogin()), user);
            Assert.assertNull(getCrud().findTypedQuery(-1L));
        } finally {
            delete(user);
        }
    }

    /**
     * Test findAllNamedQuery method.
     */
    @Test
    public void findAllNamedQuery() {
        final User user = create();
        try {
            Assert.assertTrue(getCrud().findAllNamedQuery().contains(user));
        } finally {
            delete(user);
        }
    }

}
