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
package org.jbromo.dao.test;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.dao.common.exception.ValidationException;
import org.jbromo.dao.jpa.IEntityDao;
import org.jbromo.dao.test.common.UserTransactionFacade;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.test.builder.field.ValidationValue;
import org.jbromo.model.jpa.test.crud.AbstractDefaultCRUDExtendedTest;
import org.junit.Assert;
import org.junit.Test;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract JPA DAO JUnit.
 * @param <E> the entity type.
 * @param <PK> the primary key type.
 * @param <DAO> the DAO type.
 * @author qjafcunuas
 */
@Slf4j
public abstract class AbstractDefaultEntityDaoTest<E extends IEntity<PK>, PK extends Serializable, DAO extends IEntityDao<E, PK>>
    extends AbstractDefaultCRUDExtendedTest<E, PK, DAO> {

    /**
     * The user transaction facade.
     */
    @Getter(AccessLevel.PROTECTED)
    @Inject
    private UserTransactionFacade transaction;

    /**
     * Try to save entities with bad value on persisted fields.
     * @throws InvocationException exception.
     */
    @Test
    public void badEntityValues() throws InvocationException {
        final List<ValidationValue<E>> entities = getEntityBuilder().getValidationErrorValues();
        E created = null;
        for (final ValidationValue<E> entity : entities) {
            try {
                // modifyForUniqueConstraint(entity.getValue());
                created = getCrud().create(entity.getValue());
                if (created != null) {
                    getCrud().delete(created);
                    Assert.fail("Entity with bad values has been persisted! " + entity.getLabel() + " on field " + entity.getField());
                }
            } catch (final ValidationException e) {
                log.debug("Bad value on entity field has been normally rejected for persistence");
            } catch (final MessageLabelException e) {
                log.error("Error when persisted bad entity", e);
                Assert.fail("Error when persisted bad entity " + getEntityClass().getName());
            }
        }
    }
}
