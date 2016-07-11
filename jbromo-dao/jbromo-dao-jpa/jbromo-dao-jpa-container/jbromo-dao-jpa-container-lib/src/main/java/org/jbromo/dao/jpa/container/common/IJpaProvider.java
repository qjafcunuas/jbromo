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
package org.jbromo.dao.jpa.container.common;

import javax.transaction.UserTransaction;

/**
 * Define specific utility for JPA provider.
 * @author qjafcunuas
 */
public interface IJpaProvider {

    /**
     * Return the user transaction in a JSE environment.
     * @return the user transaction.
     */
    UserTransaction getUserTransactionJSE();

    /**
     * Return the persisted class of an object (not the proxy class).
     * @param object the object.
     * @return the persisted class.
     */
    Class<?> getPersistentClass(final Object object);

    /**
     * Return true if the provider set the parent's primary key into the composite primary key when persisting parent entity.
     * @return true/false.
     */
    boolean isCompositePrimaryKeyUpdatedDuringPersist();

    /**
     * Return true if the provider authorize to set an alias on a fetch join.
     * @return true/false.
     */
    boolean isFetchAliasable();

    /**
     * Return true if the provider merges automatically oneToMany relationship.
     * @return true/false.
     */
    boolean isOneToManyAutoMerge();

}
