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

import java.util.Iterator;

import org.jbromo.common.dto.IOrderBy.SORT;
import org.junit.Assert;
import org.junit.Test;
import org.richfaces.model.SortMode;

/**
 * Define JUnit test for class DataTableSortMultiColumn.
 *
 * @author qjafcunuas
 *
 */
public class DataTableSortMultiColumnTest extends
        AbstractDataTableSortColumnTest<DataTableSortMultiColumn> {

    @Override
    protected DataTableSortMultiColumn newInstance() {
        return new DataTableSortMultiColumn();
    }

    /**
     * Test getSortMode.
     */
    @Test
    public void getSortMode() {
        final IDataTableSortColumn sort = newInstance();
        Assert.assertEquals(sort.getSortMode(), SortMode.multi);
    }

    /**
     * Test getColumnRefs.
     */
    @Test
    public void getColumnRefs() {
        IDataTableSortColumn sort = new DataTableSortMultiColumn();
        Iterator<String> iterator;

        // No sort.
        Assert.assertTrue(sort.getColumnRefs().isEmpty());

        // Set one sort.
        sort = new DataTableSortMultiColumn();
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertEquals(sort.getColumnRefs().size(), 1);
        Assert.assertTrue(sort.getColumnRefs().contains(SORT_BY_NAME));

        // Set two sorts.
        sort = new DataTableSortMultiColumn();
        sort.setOrderBy(SORT_BY_NAME);
        sort.setOrderBy(SORT_BY_CODE);
        iterator = sort.getColumnRefs().iterator();
        Assert.assertEquals(sort.getColumnRefs().size(), 2);
        Assert.assertEquals(iterator.next(), SORT_BY_NAME);
        Assert.assertEquals(iterator.next(), SORT_BY_CODE);

        // Set same first sort twice.
        sort = new DataTableSortMultiColumn();
        sort.setOrderBy(SORT_BY_NAME);
        sort.setOrderBy(SORT_BY_CODE);
        sort.setOrderBy(SORT_BY_NAME);
        iterator = sort.getColumnRefs().iterator();
        Assert.assertEquals(sort.getColumnRefs().size(), 2);
        Assert.assertEquals(iterator.next(), SORT_BY_NAME);
        Assert.assertEquals(iterator.next(), SORT_BY_CODE);

        // Set same first sort three time.
        sort = new DataTableSortMultiColumn();
        sort.setOrderBy(SORT_BY_NAME);
        sort.setOrderBy(SORT_BY_CODE);
        sort.setOrderBy(SORT_BY_NAME);
        sort.setOrderBy(SORT_BY_NAME);
        iterator = sort.getColumnRefs().iterator();
        Assert.assertEquals(sort.getColumnRefs().size(), 2);
        Assert.assertEquals(iterator.next(), SORT_BY_NAME);
        Assert.assertEquals(iterator.next(), SORT_BY_CODE);

        // Set same second sort twice.
        sort = new DataTableSortMultiColumn();
        sort.setOrderBy(SORT_BY_NAME);
        sort.setOrderBy(SORT_BY_CODE);
        sort.setOrderBy(SORT_BY_CODE);
        iterator = sort.getColumnRefs().iterator();
        Assert.assertEquals(sort.getColumnRefs().size(), 2);
        Assert.assertEquals(iterator.next(), SORT_BY_NAME);
        Assert.assertEquals(iterator.next(), SORT_BY_CODE);

        // Set same second sort three time.
        sort = new DataTableSortMultiColumn();
        sort.setOrderBy(SORT_BY_NAME);
        sort.setOrderBy(SORT_BY_CODE);
        sort.setOrderBy(SORT_BY_CODE);
        sort.setOrderBy(SORT_BY_CODE);
        iterator = sort.getColumnRefs().iterator();
        Assert.assertEquals(sort.getColumnRefs().size(), 1);
        Assert.assertEquals(iterator.next(), SORT_BY_NAME);

    }

    /**
     * Test getSort.
     */
    @Override
    @Test
    public void getSort() {
        super.getSort();
        IDataTableSortColumn sort = new DataTableSortMultiColumn();

        // Set two sort.
        sort = new DataTableSortMultiColumn();
        sort.setOrderBy(SORT_BY_NAME);
        sort.setOrderBy(SORT_BY_CODE);
        Assert.assertEquals(sort.getSort(SORT_BY_NAME), SORT.ASCENDING);
        Assert.assertEquals(sort.getSort(SORT_BY_CODE), SORT.ASCENDING);

        // Set same first sort twice.
        sort = new DataTableSortMultiColumn();
        sort.setOrderBy(SORT_BY_NAME);
        sort.setOrderBy(SORT_BY_NAME);
        sort.setOrderBy(SORT_BY_CODE);
        Assert.assertEquals(sort.getSort(SORT_BY_NAME), SORT.DESCENDING);
        Assert.assertEquals(sort.getSort(SORT_BY_CODE), SORT.ASCENDING);

        sort = new DataTableSortMultiColumn();
        sort.setOrderBy(SORT_BY_NAME);
        sort.setOrderBy(SORT_BY_CODE);
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertEquals(sort.getSort(SORT_BY_NAME), SORT.DESCENDING);
        Assert.assertEquals(sort.getSort(SORT_BY_CODE), SORT.ASCENDING);

        // Set same first sort three time.
        sort = new DataTableSortMultiColumn();
        sort.setOrderBy(SORT_BY_NAME);
        sort.setOrderBy(SORT_BY_NAME);
        sort.setOrderBy(SORT_BY_NAME);
        sort.setOrderBy(SORT_BY_CODE);
        Assert.assertEquals(sort.getSort(SORT_BY_NAME), SORT.UNSORTED);
        Assert.assertEquals(sort.getSort(SORT_BY_CODE), SORT.ASCENDING);

        sort = new DataTableSortMultiColumn();
        sort.setOrderBy(SORT_BY_NAME);
        sort.setOrderBy(SORT_BY_NAME);
        sort.setOrderBy(SORT_BY_CODE);
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertEquals(sort.getSort(SORT_BY_NAME), SORT.UNSORTED);
        Assert.assertEquals(sort.getSort(SORT_BY_CODE), SORT.ASCENDING);

        sort = new DataTableSortMultiColumn();
        sort.setOrderBy(SORT_BY_NAME);
        sort.setOrderBy(SORT_BY_CODE);
        sort.setOrderBy(SORT_BY_NAME);
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertEquals(sort.getSort(SORT_BY_NAME), SORT.UNSORTED);
        Assert.assertEquals(sort.getSort(SORT_BY_CODE), SORT.ASCENDING);

        // Set same second sort twice.
        sort = new DataTableSortMultiColumn();
        sort.setOrderBy(SORT_BY_NAME);
        sort.setOrderBy(SORT_BY_CODE);
        sort.setOrderBy(SORT_BY_CODE);
        Assert.assertEquals(sort.getSort(SORT_BY_NAME), SORT.ASCENDING);
        Assert.assertEquals(sort.getSort(SORT_BY_CODE), SORT.DESCENDING);

        // Set same second sort three time.
        sort = new DataTableSortMultiColumn();
        sort.setOrderBy(SORT_BY_NAME);
        sort.setOrderBy(SORT_BY_CODE);
        sort.setOrderBy(SORT_BY_CODE);
        sort.setOrderBy(SORT_BY_CODE);
        Assert.assertEquals(sort.getSort(SORT_BY_NAME), SORT.ASCENDING);
        Assert.assertEquals(sort.getSort(SORT_BY_CODE), SORT.UNSORTED);
    }

}
