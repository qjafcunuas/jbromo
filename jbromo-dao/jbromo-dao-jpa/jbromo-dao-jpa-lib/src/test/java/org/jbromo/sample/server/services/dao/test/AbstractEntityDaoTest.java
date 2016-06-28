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
package org.jbromo.sample.server.services.dao.test;

import java.io.Serializable;

import org.jbromo.dao.jpa.IEntityDao;
import org.jbromo.dao.test.AbstractDefaultEntityDaoTest;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.testutil.asserts.AbstractEntityAssert;
import org.jbromo.model.jpa.testutil.builder.AbstractEntityBuilder;
import org.jbromo.sample.server.model.test.asserts.EntityAssertFactory;
import org.jbromo.sample.server.model.test.builder.EntityBuilderFactory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstract JPA DAO JUnit.
 * @param <E> the entity type.
 * @param <PK> the primary key type.
 * @param <DAO> the DAO type.
 * @author qjafcunuas
 */
public abstract class AbstractEntityDaoTest<E extends IEntity<PK>, PK extends Serializable, DAO extends IEntityDao<E, PK>>
    extends AbstractDefaultEntityDaoTest<E, PK, DAO> {

    /**
     * The entity builder.
     */
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private AbstractEntityBuilder<E> entityBuilder = EntityBuilderFactory.getInstance().getBuilder(getEntityClass());

    /**
     * The entity assert.
     */
    @Getter(AccessLevel.PROTECTED)
    private final AbstractEntityAssert<E> entityAssert = EntityAssertFactory.getInstance().getAssert(getEntityClass());

}
