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

import javax.ejb.Local;

import org.jbromo.dao.jpa.IEntityDao;
import org.jbromo.sample.server.model.src.User;

/**
 * The User DAO interface.
 *
 * @author qjafcunuas
 *
 */
@Local
public interface IUserDao extends IEntityDao<User, Long> {

    /**
     * Used for testing createTypedQuery method.
     *
     * @param pk
     *            a primary key.
     * @return the user.
     */
    User findTypedQuery(final Long pk);

    /**
     * Used for testing single named query method.
     *
     * @param login
     *            a login.
     * @return the user.
     */
    User findSingleNamedQuery(final String login);

    /**
     * Used for testing named query method.
     *
     * @return the user.
     */
    List<User> findAllNamedQuery();

}
