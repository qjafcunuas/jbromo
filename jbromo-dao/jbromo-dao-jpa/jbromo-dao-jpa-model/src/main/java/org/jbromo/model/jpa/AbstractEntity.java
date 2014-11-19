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
package org.jbromo.model.jpa;

import java.io.Serializable;

import org.jbromo.common.ObjectUtil;
import org.jbromo.model.jpa.util.EntityUtil;

/**
 * Abstract class designed for persistent objects.
 *
 * @author qjafcunuas
 * @param <P>
 *            Primary key
 */
public abstract class AbstractEntity<P extends Serializable> implements
        IEntity<P> {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 570007284571594990L;

    @Override
    public int hashCode() {
        final P pk = EntityUtil.readPrimaryKey(this);
        if (pk == null) {
            return super.hashCode();
        } else {
            return pk.hashCode();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (ObjectUtil.same(this, obj)) {
            return true;
        }
        if (!EntityUtil.isEntity(obj)) {
            return false;
        }
        final IEntity<P> entity = ObjectUtil.cast(obj, IEntity.class);
        final Class<?> thisClass = EntityUtil.getClass(this);
        final Class<?> objClass = EntityUtil.getClass(entity);
        if (!thisClass.equals(objClass)) {
            return false;
        }
        final P objPk = EntityUtil.readPrimaryKey(entity);
        final P thisPk = EntityUtil.readPrimaryKey(this);
        return ObjectUtil.notNullAndEquals(objPk, thisPk);
    }

    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder();
        str.append(getClass().getSimpleName());
        str.append("[pk=");
        str.append(EntityUtil.readPrimaryKey(this));
        str.append("]");
        return str.toString();
    }

}