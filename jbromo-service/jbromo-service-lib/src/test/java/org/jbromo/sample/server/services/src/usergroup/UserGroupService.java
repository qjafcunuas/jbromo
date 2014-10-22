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
package org.jbromo.sample.server.services.src.usergroup;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.dao.common.Dao;
import org.jbromo.sample.server.model.src.UserGroup;
import org.jbromo.sample.server.services.dao.src.usergroup.IUserGroupDao;
import org.jbromo.service.Service;
import org.jbromo.service.crud.jpa.AbstractEntityService;

/**
 * Define the City service implementation.
 *
 * @author qjafcunuas
 *
 */
@Service
@Stateless
public class UserGroupService extends AbstractEntityService<UserGroup, Long>
        implements IUserGroupService {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 2712174922809512874L;

    /**
     * The entity dao to used.
     */
    @Inject
    @Dao
    @Getter(AccessLevel.PROTECTED)
    private IUserGroupDao entityDao;

}
