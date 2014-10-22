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
package org.jbromo.service.crud;

import java.io.Serializable;

import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.common.invocation.ParameterizedTypeUtil;

/**
 * The class for implementation of CRUD services.
 *
 * @author qjafcunuas
 * @param <M>
 *            the model type.
 * @param <PK>
 *            the primary key type.
 */
public abstract class AbstractCrudService<M extends Serializable, PK extends Serializable>
        implements ICRUDService<M, PK> {

    /**
     * Serial version UID for class.
     */
    private static final long serialVersionUID = -4053211327121284550L;

    /**
     * The model class.
     */
    private Class<M> modelClass;

    /**
     * Default constructor.
     */
    public AbstractCrudService() {
        super();
    }

    /**
     * Return the model class.
     *
     * @return the model class.
     */
    protected Class<M> getModelClass() {
        if (this.modelClass == null) {
            this.modelClass = ParameterizedTypeUtil.getGenericType(
                    InvocationUtil.getField(getClass(), "modelClass"), 0);
        }
        return this.modelClass;
    }

}
