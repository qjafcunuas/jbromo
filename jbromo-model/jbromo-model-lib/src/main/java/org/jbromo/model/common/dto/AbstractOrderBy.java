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
package org.jbromo.model.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.dto.IOrderBy;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.common.invocation.ParameterizedTypeUtil;

/**
 * Default order by implementation.
 *
 * @author qjafcunuas
 *
 * @param <M>
 */
@Slf4j
public abstract class AbstractOrderBy<M extends Serializable> implements
        IOrderBy<M> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 3999604122369087634L;

    /**
     * The order.
     */
    @Getter
    private final String order;

    /**
     * The model class.
     */
    private final Class<M> modelClass;

    /**
     * Default constructor.
     *
     * @param order
     *            the order clause.
     * @throws InvocationException
     *             exception.
     */
    public AbstractOrderBy(final String order) throws InvocationException {
        super();
        this.order = order;
        this.modelClass = ParameterizedTypeUtil.getClass(this, 0);
        if (InvocationUtil.getField(getModelClass(), order) == null) {
            log.error("Field name {} doesn't exit into model {}", order,
                    getModelClass());
            throw new InvocationException("Field name " + order
                    + " doesn't exit into model " + getModelClass());
        }
    }

    /**
     * Return the model class.
     *
     * @return the model class.
     */
    protected final Class<M> getModelClass() {
        return this.modelClass;
    }

}
