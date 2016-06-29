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
package org.jbromo.service.test;

import java.io.Serializable;

import javax.inject.Inject;

import org.jbromo.dao.test.common.UserTransactionFacade;
import org.jbromo.dao.test.crud.AbstractDefaultCRUDExtendedTest;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.service.crud.jpa.IEntityService;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * Define the default service test implementation.
 * @author qjafcunuas
 * @param <E> the entity type.
 * @param <PK> the primary key type.
 */
public abstract class AbstractDefaultEntityServiceTest<E extends IEntity<PK>, PK extends Serializable>
    extends AbstractDefaultCRUDExtendedTest<E, PK, IEntityService<E, PK>> {

    /**
     * The user transaction facade.
     */
    @Getter(AccessLevel.PROTECTED)
    @Inject
    private UserTransactionFacade transaction;

}
