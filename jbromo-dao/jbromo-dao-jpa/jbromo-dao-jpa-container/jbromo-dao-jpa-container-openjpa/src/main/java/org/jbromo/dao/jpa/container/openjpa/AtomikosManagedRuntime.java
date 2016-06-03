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
package org.jbromo.dao.jpa.container.openjpa;

import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;

import org.apache.openjpa.ee.ManagedRuntime;

import com.atomikos.icatch.jta.UserTransactionManager;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Define the atomikos managed runtime for OpenJPA provider.
 * @author qjafcunuas
 */
@Slf4j
public class AtomikosManagedRuntime implements ManagedRuntime {

    /**
     * The user transaction manager.
     */
    @Getter
    private final UserTransactionManager transactionManager;

    /**
     * The rollback cause.
     */
    @Getter
    private Throwable rollbackCause;

    /**
     * Default constructor.
     */
    public AtomikosManagedRuntime() {
        super();
        this.transactionManager = new UserTransactionManager();
    }

    @Override
    public void doNonTransactionalWork(final Runnable runnable) throws NotSupportedException {
        try {
            final Transaction transaction = getTransactionManager().suspend();
            try {
                runnable.run();
            } finally {
                getTransactionManager().resume(transaction);
            }
        } catch (final InvalidTransactionException | SystemException e) {
            log.error("Cannot suspend transaction", e);
            throw new NotSupportedException(e.getMessage());
        }

    }

    @Override
    public Object getTransactionKey() throws Exception, SystemException {
        return getTransactionManager().getTransaction();
    }

    @Override
    public synchronized void setRollbackOnly(final Throwable cause) throws Exception {
        getTransactionManager().setRollbackOnly();
        this.rollbackCause = cause;
    }

}
