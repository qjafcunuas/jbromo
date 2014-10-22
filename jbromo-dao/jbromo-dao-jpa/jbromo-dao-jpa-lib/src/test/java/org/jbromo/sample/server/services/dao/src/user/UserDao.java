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
package org.jbromo.sample.server.services.dao.src.user;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.dao.common.Dao;
import org.jbromo.dao.jpa.AbstractEntityDao;
import org.jbromo.sample.server.model.src.User;

/**
 * The user DAO implementation.
 *
 * @author qjafcunuas
 *
 */
@Dao
@Stateless
public class UserDao extends AbstractEntityDao<User, Long> implements
        IUserDao {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -1060683774516455634L;

    /**
     * The entity manager to used.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private EntityManager entityManager;

    @Override
    public User findTypedQuery(final Long pk) {
        final TypedQuery<User> query = createTypedQuery("select o from User o where o.primaryKey = ?1");
        query.setParameter(1, pk);
        return getSingleResult(query);
    }

    @Override
    public User findSingleNamedQuery(final String login) {
        return executeSingleNamedQuery(User.NAMED_QUERY_FIND_BY_LOGIN, login);
    }

    @Override
    public List<User> findAllNamedQuery() {
        return executeNamedQuery(User.NAMED_QUERY_FIND_ALL);
    }

}
