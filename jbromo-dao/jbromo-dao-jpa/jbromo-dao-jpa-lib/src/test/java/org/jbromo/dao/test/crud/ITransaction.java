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
package org.jbromo.dao.test.crud;

import java.io.Serializable;

/**
 * Define transaction for CRUD test.
 * @author qjafcunuas
 */
public interface ITransaction extends Serializable {
    /**
     * Open a transaction.
     * @throws Exception exception.
     */
    void open() throws Exception;

    /**
     * Close the current transaction.
     * @throws Exception exception.
     */
    void close() throws Exception;

    /**
     * Return true if transaction is opened.
     * @return true/false.
     * @throws Exception exception.
     */
    boolean isOpened() throws Exception;

    /**
     * Return true if transaction is marked as rollabck.
     * @return true/false.
     * @throws Exception exception.
     */
    boolean isMarkedRollback() throws Exception;

    /**
     * Join current transaction.
     * @throws Exception exception.
     */
    void join() throws Exception;

    /**
     * Unjoin current transaction.
     * @throws Exception exception.
     */
    void unjoin() throws Exception;
}
