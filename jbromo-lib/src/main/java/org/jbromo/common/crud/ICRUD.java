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
package org.jbromo.common.crud;

import java.io.Serializable;
import java.util.List;

import org.jbromo.common.exception.MessageLabelException;

/**
 * Define a CRUD interface.
 * @author qjafcunuas
 * @param <E> the serializable type.
 * @param
 *            <P>
 *            the primary key type.
 * @param <X> the exception type.
 */
public interface ICRUD<E extends Serializable, P extends Serializable, X extends MessageLabelException> extends Serializable {
    /**
     * Creates the object.
     * @param transientInstance the object to save.
     * @return the saved and persistent entity.
     * @throws X on exception.
     */
    E create(final E transientInstance) throws X;

    /**
     * The reader for service layer.
     * @param transientInstance the object to read.
     * @return the readed object.
     * @throws X the exception type handles by the service layer
     */
    E read(final E transientInstance) throws X;

    /**
     * Updates the instance of the object.
     * @param detachedInstance the object which have to be updated.
     * @return the updated object.
     * @throws X on exception.
     */
    E update(final E detachedInstance) throws X;

    /**
     * Deletes an object instance.
     * @param detachedInstance the object to delete.
     * @return true if entity existed before.
     * @throws X on exception.
     */
    boolean delete(final E detachedInstance) throws X;

    /**
     * Save the instance of the object. if instance has null primary key, creates it, else updates it.
     * @param detachedInstance the object which have to be saved.
     * @return the saved object.
     * @throws X on exception.
     */
    E save(final E detachedInstance) throws X;

    /**
     * Returns the object corresponding with the identifier which is passed in parameter.
     * @param primaryKey the identifier of the searched object.
     * @return the object associated with the identifier.
     * @throws X exception.
     */
    E findByPk(final P primaryKey) throws X;

    /**
     * Returns all persisted objects.
     * @return the list of all objects
     * @throws X exception.
     */
    List<E> findAll() throws X;

    /**
     * Returns the number of persisted instances for this entity.
     * @return the number of objects
     * @throws X exception.
     */
    Long count() throws X;

}