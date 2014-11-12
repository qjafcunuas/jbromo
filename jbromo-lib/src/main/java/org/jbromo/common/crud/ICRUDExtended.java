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
package org.jbromo.common.crud;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.jbromo.common.dto.IOrderBy;
import org.jbromo.common.exception.MessageLabelException;

/**
 * Define a CRUD extended interface.
 *
 * @author qjafcunuas
 * @param <E>
 *            the serializable type.
 * @param <PK>
 *            the primary key type.
 * @param <EXP>
 *            the exception type.
 */
public interface ICRUDExtended<E extends Serializable, PK extends Serializable, EXP extends MessageLabelException>
        extends ICRUD<E, PK, EXP> {

    /**
     * Creates the objects.
     *
     * @param <C>
     *            the collection entity type.
     * @param transientInstance
     *            the objects to save.
     * @return the saved and persistent entities.
     * @throws EXP
     *             on exception.
     */
    <C extends Collection<E>> C create(final C transientInstance) throws EXP;

    /**
     * Reads the instance of the objects.
     *
     * @param <C>
     *            the collection entity type.
     * @param detachedInstance
     *            the objects which have to be readed.
     * @return the readed objects.
     * @throws EXP
     *             on exception.
     */
    <C extends Collection<E>> C read(final C detachedInstance) throws EXP;

    /**
     * Updates the instance of the objects.
     *
     * @param <C>
     *            the collection entity type.
     * @param detachedInstance
     *            the objects which have to be updated.
     * @return the updated objects.
     * @throws EXP
     *             on exception.
     */
    <C extends Collection<E>> C update(final C detachedInstance) throws EXP;

    /**
     * Deletes objects instance.
     *
     * @param detachedInstance
     *            the objects to delete.
     * @throws EXP
     *             on exception
     */
    void delete(final Collection<E> detachedInstance) throws EXP;

    /**
     * Save the instance of the objects.
     *
     * @param <C>
     *            the collection entity type.
     * @param detachedInstance
     *            the objects which have to be saved.
     * @return the saved objects.
     * @throws EXP
     *             on exception.
     */
    <C extends Collection<E>> C save(final C detachedInstance) throws EXP;

    /**
     * Returns the object corresponding with the identifier which is passed in
     * parameter.
     *
     * @param primaryKeys
     *            the identifier of the searched object.
     * @return the object associated with the identifier.
     * @throws EXP
     *             exception.
     */
    Collection<E> findAllByPk(final Collection<PK> primaryKeys) throws EXP;

    /**
     * Returns the object corresponding with the identifier which is passed in
     * parameter.
     *
     * @param primaryKey
     *            the identifier of {@link Serializable} type of the searched
     *            object
     * @param eagerLoading
     *            the eager loading.
     * @return the object associated with the identifier
     * @throws EXP
     *             exception.
     */
    E findByPk(final PK primaryKey, final E eagerLoading) throws EXP;

    /**
     * Returns the objects corresponding with the identifier which is passed in
     * parameter.
     *
     * @param primaryKeys
     *            the identifier of {@link Serializable} type of the searched
     *            object
     * @param eagerLoading
     *            the eager loading.
     * @return the object associated with the identifier
     * @throws EXP
     *             exception.
     */
    Collection<E> findAllByPk(final Collection<PK> primaryKeys,
            final E eagerLoading) throws EXP;

    /**
     * Return entities according to criteria.
     *
     * @param criteria
     *            the entity for searching entities.
     * @return entities.
     * @throws EXP
     *             the exception type handles by the service layer
     */
    List<E> findAll(final E criteria) throws EXP;

    /**
     * Return all entities.
     *
     * @param criteria
     *            the criteria.
     * @param eagerLoading
     *            the eager loading.
     * @param orderBy
     *            the order by clause.
     * @return entities.
     * @throws EXP
     *             exception.
     */
    List<E> findAll(final E criteria, final E eagerLoading,
            final List<IOrderBy<E>> orderBy) throws EXP;

}
