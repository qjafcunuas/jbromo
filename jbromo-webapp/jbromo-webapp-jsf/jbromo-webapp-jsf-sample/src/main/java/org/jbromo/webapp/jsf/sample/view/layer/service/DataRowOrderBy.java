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

import org.jbromo.common.dto.IOrderBy;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Define DataRow order clause.
 * @author qjafcunuas
 */
@AllArgsConstructor
@Getter
public enum DataRowOrderBy implements IOrderBy {
    /**
     * Order by primary key.
     */
    PRIMARY_KEY_ASC("primaryKey", IOrderBy.SORT.ASCENDING),

    /**
     * Order by primary key.
     */
    PRIMARY_KEY_DESC(PRIMARY_KEY_ASC.getOrder(), IOrderBy.SORT.DESCENDING),

    /**
     * Order by name.
     */
    NAME_ASC("name", IOrderBy.SORT.ASCENDING),

    /**
     * Order by name.
     */
    NAME_DESC(NAME_ASC.getOrder(), IOrderBy.SORT.DESCENDING),

    /**
     * Order by description.
     */
    DESCRIPTION_ASC("description", IOrderBy.SORT.ASCENDING),

    /**
     * Order by description.
     */
    DESCRIPTION_DESC(DESCRIPTION_ASC.getOrder(), IOrderBy.SORT.DESCENDING);

    /**
     * The order clause.
     */
    private String order;

    /**
     * The order clause.
     */
    private IOrderBy.SORT sort;

    /**
     * Return the order by.
     * @param columnRef the columnRef.
     * @param sort the sort.
     * @return the order by.
     */
    public static DataRowOrderBy getOrderBy(final String columnRef, final IOrderBy.SORT sort) {
        for (final DataRowOrderBy one : values()) {
            if (one.getOrder().equals(columnRef) && one.getSort().equals(sort)) {
                return one;
            }
        }
        return null;
    }
}
