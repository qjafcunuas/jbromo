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
package org.jbromo.webapp.jsf.sample.view.layer.service;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.dao.common.exception.NullPrimaryKeyException;
import org.jbromo.service.Service;
import org.jbromo.service.crud.memory.AbstractMemoryService;

/**
 * Define the DataRow service implementation.
 * @author qjafcunuas
 */
@Service
@Singleton
public class DataRowService extends AbstractMemoryService<DataRow>implements IDataRowService {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -4246453388518207122L;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DataRow create(final DataRow model) throws MessageLabelException {
        if (model.getPrimaryKey() != null) {
            throw new NullPrimaryKeyException();
        }
        final DataRow one = new DataRow();
        int pk = getMap().size() + 1;
        one.setPrimaryKey(pk);
        while (getMap().containsValue(one)) {
            pk++;
            one.setPrimaryKey(pk);
        }
        // Set primary key.
        model.setPrimaryKey(pk);
        return super.create(model);
    }

}
