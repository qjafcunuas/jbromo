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
package org.jbromo.model.jpa.compositepk;

import java.lang.reflect.Field;

import javax.persistence.Transient;

import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.ObjectUtil;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.common.invocation.InvocationUtil.AccessType;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.util.EntityUtil;

/**
 * Default ICompositePk implementation.
 *
 * @author qjafcunuas
 *
 */
@Slf4j
public class AbstractCompositePk implements ICompositePk, Cloneable {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -782786660431050351L;

    /**
     * Default prime value.
     */
    private static final int PRIME = 31;

    /**
     * The entity of the primary key.
     */
    @Transient
    private final IEntity<? extends AbstractCompositePk> entity;

    /**
     * Default constructor.
     */
    protected AbstractCompositePk() {
        this(null);
    }

    /**
     * Default constructor.
     *
     * @param entity
     *            the entity of the primary key.
     */
    protected AbstractCompositePk(
            final IEntity<? extends AbstractCompositePk> entity) {
        super();
        this.entity = entity;
    }

    @Override
    public int hashCode() {
        int result = 1;
        Object value;
        for (final Field field : EntityUtil.getPersistedFields(getClass())) {
            value = getValue(field);
            if (value == null) {
                return super.hashCode();
            }
            result = PRIME * result + value.hashCode();
        }
        return result;
    }

    /**
     * Return the field value. If it is null, return the primary key of the
     * corresponding mapsId object
     *
     * @param field
     *            the field to get value.
     * @return the value.
     */
    private Object getValue(final Field field) {
        Object value;
        try {
            value = InvocationUtil.getValue(this, field, AccessType.FIELD,
                    false);
        } catch (final InvocationException e) {
            log.error("Cannot read value for field " + field, e);
            return null;
        }
        if (value == null) {
            // Get field value of the entity.
            value = EntityUtil
                    .getMapsIdFieldValue(this.entity, field.getName());
            if (value != null) {
                final Object pk = EntityUtil.readPrimaryKey((IEntity<?>) value);
                if (pk != null) {
                    value = pk;
                } else {
                    value = value.hashCode();
                }
            }
        }
        return value;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Object thisValue;
        Object objValue;
        for (final Field field : EntityUtil.getPersistedFields(getClass())) {
            thisValue = getValue(field);
            objValue = ((AbstractCompositePk) obj).getValue(field);
            if (!ObjectUtil.notNullAndEquals(thisValue, objValue)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public AbstractCompositePk clone() {
        AbstractCompositePk o = null;
        try {
            o = ObjectUtil.cast(super.clone(), AbstractCompositePk.class);
        } catch (final CloneNotSupportedException e) {
            // Should not happened.
            log.error(e.getMessage(), e);
        }
        return o;
    }
}
