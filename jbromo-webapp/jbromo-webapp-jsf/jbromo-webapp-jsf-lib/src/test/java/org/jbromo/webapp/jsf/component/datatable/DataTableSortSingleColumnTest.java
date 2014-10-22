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
package org.jbromo.webapp.jsf.component.datatable;

import org.jbromo.common.dto.IOrderBy.SORT;
import org.junit.Assert;
import org.junit.Test;
import org.richfaces.model.SortMode;

/**
 * Define JUnit test for class DataTableSortSingleColumn.
 *
 * @author qjafcunuas
 *
 */
public class DataTableSortSingleColumnTest extends
        AbstractDataTableSortColumnTest<DataTableSortSingleColumn> {

    @Override
    protected DataTableSortSingleColumn newInstance() {
        return new DataTableSortSingleColumn();
    }

    /**
     * Test getSortMode.
     */
    @Test
    public void getSortMode() {
        final IDataTableSortColumn sort = newInstance();
        Assert.assertEquals(sort.getSortMode(), SortMode.single);
    }

    /**
     * Test getColumnRefs.
     */
    @Test
    public void getColumnRefs() {
        final IDataTableSortColumn sort = newInstance();
        // No sort.
        Assert.assertTrue(sort.getColumnRefs().isEmpty());

        // Set sort.
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertEquals(sort.getColumnRefs().size(), 1);
        Assert.assertTrue(sort.getColumnRefs().contains(SORT_BY_NAME));

        // Set another sort.
        sort.setOrderBy(SORT_BY_CODE);
        Assert.assertEquals(sort.getColumnRefs().size(), 1);
        Assert.assertTrue(sort.getColumnRefs().contains(SORT_BY_CODE));
    }

    @Override
    @Test
    public void getSort() {
        super.getSort();
        IDataTableSortColumn sort = newInstance();

        // Set different sorts.
        sort = newInstance();
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertEquals(sort.getSort(SORT_BY_NAME), SORT.ASCENDING);
        Assert.assertEquals(sort.getSort(SORT_BY_CODE), SORT.UNSORTED);
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertEquals(sort.getSort(SORT_BY_NAME), SORT.DESCENDING);
        Assert.assertEquals(sort.getSort(SORT_BY_CODE), SORT.UNSORTED);
        sort.setOrderBy(SORT_BY_CODE);
        Assert.assertEquals(sort.getSort(SORT_BY_NAME), SORT.UNSORTED);
        Assert.assertEquals(sort.getSort(SORT_BY_CODE), SORT.ASCENDING);
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertEquals(sort.getSort(SORT_BY_NAME), SORT.ASCENDING);
        Assert.assertEquals(sort.getSort(SORT_BY_CODE), SORT.UNSORTED);

    }
}
