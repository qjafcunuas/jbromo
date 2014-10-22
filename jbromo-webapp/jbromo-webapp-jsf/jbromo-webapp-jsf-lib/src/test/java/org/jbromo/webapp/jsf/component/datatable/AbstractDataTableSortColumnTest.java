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
import org.richfaces.component.SortOrder;

/**
 * Define JUnit for AbstractDataTableSortColumn class.
 *
 * @author qjafcunuas
 * @param <M>
 *            the model type.
 *
 */
public abstract class AbstractDataTableSortColumnTest<M extends AbstractDataTableSortColumn> {

    /**
     * One sort.
     */
    protected static final String SORT_BY_NAME = "name";
    /**
     * Another sort.
     */
    protected static final String SORT_BY_CODE = "code";

    /**
     * Return a new instance of model.
     *
     * @return the model.
     */
    protected abstract M newInstance();

    /**
     * Test getSortOrder method.
     */
    @Test
    public void getSortOrder() {
        M sort = newInstance();
        // No sort.
        Assert.assertEquals(sort.getSortOrder(SORT_BY_NAME), SortOrder.unsorted);
        Assert.assertEquals(sort.getSortOrder(SORT_BY_CODE), SortOrder.unsorted);

        // Set one sort.
        sort = newInstance();
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertEquals(sort.getSortOrder(SORT_BY_NAME),
                SortOrder.ascending);
        Assert.assertEquals(sort.getSortOrder(SORT_BY_CODE), SortOrder.unsorted);

        // Set another sort.
        sort = newInstance();
        sort.setOrderBy(SORT_BY_CODE);
        Assert.assertEquals(sort.getSortOrder(SORT_BY_NAME), SortOrder.unsorted);
        Assert.assertEquals(sort.getSortOrder(SORT_BY_CODE),
                SortOrder.ascending);

        // Set same sorts.
        sort = newInstance();
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertEquals(sort.getSortOrder(SORT_BY_NAME),
                SortOrder.ascending);
        Assert.assertEquals(sort.getSortOrder(SORT_BY_CODE), SortOrder.unsorted);
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertEquals(sort.getSortOrder(SORT_BY_NAME),
                SortOrder.descending);
        Assert.assertEquals(sort.getSortOrder(SORT_BY_CODE), SortOrder.unsorted);
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertEquals(sort.getSortOrder(SORT_BY_NAME), SortOrder.unsorted);
        Assert.assertEquals(sort.getSortOrder(SORT_BY_CODE), SortOrder.unsorted);
    }

    /**
     * Test getSort.
     */
    @Test
    public void getSort() {
        IDataTableSortColumn sort = newInstance();
        // No sort.
        Assert.assertEquals(sort.getSort(SORT_BY_NAME), SORT.UNSORTED);
        Assert.assertEquals(sort.getSort(SORT_BY_CODE), SORT.UNSORTED);

        // Set sort.
        sort = newInstance();
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertEquals(sort.getSort(SORT_BY_NAME), SORT.ASCENDING);
        Assert.assertEquals(sort.getSort(SORT_BY_CODE), SORT.UNSORTED);

        // Set another sort.
        sort = newInstance();
        sort.setOrderBy(SORT_BY_CODE);
        Assert.assertEquals(sort.getSort(SORT_BY_NAME), SORT.UNSORTED);
        Assert.assertEquals(sort.getSort(SORT_BY_CODE), SORT.ASCENDING);

        // Set same sorts.
        sort = newInstance();
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertEquals(sort.getSort(SORT_BY_NAME), SORT.ASCENDING);
        Assert.assertEquals(sort.getSort(SORT_BY_CODE), SORT.UNSORTED);
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertEquals(sort.getSort(SORT_BY_NAME), SORT.DESCENDING);
        Assert.assertEquals(sort.getSort(SORT_BY_CODE), SORT.UNSORTED);
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertEquals(sort.getSort(SORT_BY_NAME), SORT.UNSORTED);
        Assert.assertEquals(sort.getSort(SORT_BY_CODE), SORT.UNSORTED);
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertEquals(sort.getSort(SORT_BY_NAME), SORT.ASCENDING);
        Assert.assertEquals(sort.getSort(SORT_BY_CODE), SORT.UNSORTED);
    }

    /**
     * Test isAscending method.
     */
    @Test
    public void isAscending() {
        M sort = newInstance();
        // No sort.
        Assert.assertFalse(sort.isAscending(SORT_BY_NAME));
        Assert.assertFalse(sort.isAscending(SORT_BY_CODE));

        // Set one sort.
        sort = newInstance();
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertTrue(sort.isAscending(SORT_BY_NAME));
        Assert.assertFalse(sort.isAscending(SORT_BY_CODE));

        // Set another sort.
        sort = newInstance();
        sort.setOrderBy(SORT_BY_CODE);
        Assert.assertFalse(sort.isAscending(SORT_BY_NAME));
        Assert.assertTrue(sort.isAscending(SORT_BY_CODE));

        // Set same sorts.
        sort = newInstance();
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertTrue(sort.isAscending(SORT_BY_NAME));
        Assert.assertFalse(sort.isAscending(SORT_BY_CODE));
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertFalse(sort.isAscending(SORT_BY_NAME));
        Assert.assertFalse(sort.isAscending(SORT_BY_CODE));
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertFalse(sort.isAscending(SORT_BY_NAME));
        Assert.assertFalse(sort.isAscending(SORT_BY_CODE));
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertTrue(sort.isAscending(SORT_BY_NAME));
        Assert.assertFalse(sort.isAscending(SORT_BY_CODE));
    }

    /**
     * Test isDescending method.
     */
    @Test
    public void isDescending() {
        M sort = newInstance();
        // No sort.
        Assert.assertFalse(sort.isDescending(SORT_BY_NAME));
        Assert.assertFalse(sort.isDescending(SORT_BY_CODE));

        // Set one sort.
        sort = newInstance();
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertFalse(sort.isDescending(SORT_BY_NAME));
        Assert.assertFalse(sort.isDescending(SORT_BY_CODE));

        // Set another sort.
        sort = newInstance();
        sort.setOrderBy(SORT_BY_CODE);
        Assert.assertFalse(sort.isDescending(SORT_BY_NAME));
        Assert.assertFalse(sort.isDescending(SORT_BY_CODE));

        // Set same sorts.
        sort = newInstance();
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertFalse(sort.isDescending(SORT_BY_NAME));
        Assert.assertFalse(sort.isDescending(SORT_BY_CODE));
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertTrue(sort.isDescending(SORT_BY_NAME));
        Assert.assertFalse(sort.isDescending(SORT_BY_CODE));
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertFalse(sort.isDescending(SORT_BY_NAME));
        Assert.assertFalse(sort.isDescending(SORT_BY_CODE));
        sort.setOrderBy(SORT_BY_NAME);
        Assert.assertFalse(sort.isDescending(SORT_BY_NAME));
        Assert.assertFalse(sort.isDescending(SORT_BY_CODE));
    }

}
