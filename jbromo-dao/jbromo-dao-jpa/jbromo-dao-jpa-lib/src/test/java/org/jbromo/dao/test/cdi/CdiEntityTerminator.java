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
package org.jbromo.dao.test.cdi;

import java.io.Serializable;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.jbromo.dao.test.common.AbstractEntityTerminator;
import org.jbromo.dao.test.common.UserTransactionFacade;
import org.jbromo.model.jpa.IEntity;

/**
 * Define an object that can be used to terminate all created entities during
 * test.
 *
 * @author qjafcunuas
 *
 */
@ApplicationScoped
@Slf4j
public class CdiEntityTerminator extends AbstractEntityTerminator implements Serializable {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 6749326719119120269L;
    /**
     * The user transaction to used.
     */
    @Inject
    private UserTransactionFacade userTransaction;

    @Override
    protected <E extends IEntity<PK>, PK extends Serializable> void delete(
            final E entity) throws Exception {
        if (entity != null) {
            try {
                this.userTransaction.join();
                getEntityManager().joinTransaction();
                super.delete(entity);
            } finally {
                this.userTransaction.unjoin();
            }
        }
    }

    @Override
    @PreDestroy
    public void deleteAll() {
        log.info("Delete all persisted entities");
        super.deleteAll();
    }

}
