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
package org.jbromo.dao.test.arquillian;

import java.io.Serializable;

import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.jbromo.dao.test.common.AbstractEntityTerminator;
import org.jbromo.model.jpa.IEntity;

/**
 * Define an object that can be used to terminate all created entities during
 * test.
 *
 * @author qjafcunuas
 *
 */
@Startup
@Singleton
public class ArquillianEntityTerminator extends AbstractEntityTerminator {

    /**
     * The user transaction to used.
     */
    @Inject
    private UserTransaction userTransaction;

    @Override
    protected <E extends IEntity<PK>, PK extends Serializable> void delete(
            final E entity) throws Exception {
        if (entity != null) {
            this.userTransaction.begin();
            super.delete(entity);
            this.userTransaction.commit();
        }
    }

    @Override
    @PreDestroy
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void deleteAll() {
        super.deleteAll();
    }

}
