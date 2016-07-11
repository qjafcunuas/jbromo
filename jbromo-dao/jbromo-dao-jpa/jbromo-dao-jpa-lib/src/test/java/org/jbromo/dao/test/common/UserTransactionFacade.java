/*
 * Copyright (C) 2013-2014 The JBromo Authors. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.jbromo.dao.test.common;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.jbromo.dao.test.crud.ITransaction;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Define User Transaction facade.
 * @author qjafcunuas
 */
@Slf4j
public class UserTransactionFacade implements ITransaction {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 6399575623131253096L;

    /**
     * The user transaction.
     */
    @Inject
    @Any
    @Getter(AccessLevel.PRIVATE)
    private UserTransaction transaction;

    /**
     * The entity manager.
     */
    @Inject
    @Getter(AccessLevel.PRIVATE)
    private EntityManager entityManager;

    /**
     * The join counter.
     */
    private static int count;

    @Override
    public void open() throws Exception {
        getEntityManager().clear();
        getTransaction().begin();
        increment();
    }

    @Override
    public void close() throws Exception {
        if (isOpened()) {
            try {
                if (!isMarkedRollback()) {
                    // if (getEntityManager().isJoinedToTransaction()) {
                    getEntityManager().flush();
                    // }
                }
                getEntityManager().clear();
            } catch (final Exception e) {
                log.error("Cannot flush/clear entity manager", e);
            } finally {
                if (isMarkedRollback()) {
                    getTransaction().rollback();
                } else {
                    getTransaction().commit();
                }
            }
        }
        cleancrement();
    }

    /**
     * Increment join counter.
     */
    private static synchronized void increment() {
        UserTransactionFacade.count++;
    }

    /**
     * Decrement join counter.
     */
    private static synchronized void decrement() {
        UserTransactionFacade.count--;
    }

    /**
     * Decrement join counter.
     */
    private static synchronized void cleancrement() {
        UserTransactionFacade.count = 0;
    }

    @Override
    public boolean isOpened() throws Exception {
        return getTransaction().getStatus() != Status.STATUS_NO_TRANSACTION;
    }

    @Override
    public boolean isMarkedRollback() throws Exception {
        return getTransaction().getStatus() == Status.STATUS_MARKED_ROLLBACK;
    }

    @Override
    public void join() throws Exception {
        if (!isOpened()) {
            open();
        } else {
            increment();
        }
    }

    @Override
    public void unjoin() throws Exception {
        decrement();
        if (UserTransactionFacade.count == 0) {
            close();
        }
    }
}
