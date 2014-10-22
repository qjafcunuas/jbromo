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
package org.jbromo.webapp.jsf.component.datatable.crud;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.ToString;

import org.junit.Assert;
import org.junit.Test;

/**
 * Define JUnit DataTableCRUDRow class.
 *
 * @author qjafcunuas
 *
 */
public class DataTableCRUDRowTest {

    /**
     * The result junit tests.
     *
     * @author qjafcunuas
     *
     */
    @AllArgsConstructor
    @ToString
    private class Result {
        /** The input readonly value. */
        private final boolean readonly;
        /** The input newRow value. */
        private final boolean newRow;
        /** The input deleted value. */
        private final boolean deleted;
        /** The output edit rendered value. */
        private final boolean editRendered;
        /** The output delete rendered value. */
        private final boolean deleteRendered;
        /** The output cancel rendered value. */
        private final boolean cancelRendered;
        /** The output save rendered value. */
        private final boolean saveRendered;
    }

    /**
     * Define output results for all possible input values.
     */
    private final Result[] results = new Result[] {
            new Result(false, false, false, false, false, true, true),
            new Result(false, false, true, false, false, false, false),
            new Result(false, true, false, false, true, false, true),
            new Result(false, true, true, false, false, false, false),
            new Result(true, false, false, true, true, false, false),
            new Result(true, false, true, false, false, false, false),
            new Result(true, true, false, true, true, false, false),
            new Result(true, true, true, false, false, false, false) };

    /**
     * Test class.
     */
    @Test
    public void test() {
        DataTableCRUDRow<Serializable> row;
        for (final Result result : this.results) {
            // Test all combinaison of values set.
            row = new DataTableCRUDRow<Serializable>();
            row.setReadonly(result.readonly);
            row.setNewRow(result.newRow);
            row.setDeleted(result.deleted);
            checkAssert(row, result);

            row = new DataTableCRUDRow<Serializable>();
            row.setReadonly(result.readonly);
            row.setDeleted(result.deleted);
            row.setNewRow(result.newRow);
            checkAssert(row, result);

            row = new DataTableCRUDRow<Serializable>();
            row.setNewRow(result.newRow);
            row.setReadonly(result.readonly);
            row.setDeleted(result.deleted);
            checkAssert(row, result);

            row = new DataTableCRUDRow<Serializable>();
            row.setNewRow(result.newRow);
            row.setDeleted(result.deleted);
            row.setReadonly(result.readonly);
            checkAssert(row, result);

            row = new DataTableCRUDRow<Serializable>();
            row.setDeleted(result.deleted);
            row.setReadonly(result.readonly);
            row.setNewRow(result.newRow);
            checkAssert(row, result);

            row = new DataTableCRUDRow<Serializable>();
            row.setDeleted(result.deleted);
            row.setNewRow(result.newRow);
            row.setReadonly(result.readonly);
            checkAssert(row, result);

        }
    }

    /**
     * Check row according to result.
     *
     * @param row
     *            the row.
     * @param result
     *            the result.
     */
    private void checkAssert(final DataTableCRUDRow<Serializable> row,
            final Result result) {
        Assert.assertEquals(result.toString(), row.getButtons().getEditButton()
                .isRendered(), result.editRendered);
        Assert.assertEquals(result.toString(), row.getButtons()
                .getDeleteButton().isRendered(), result.deleteRendered);
        Assert.assertEquals(result.toString(), row.getButtons()
                .getCancelButton().isRendered(), result.cancelRendered);
        Assert.assertEquals(result.toString(), row.getButtons().getSaveButton()
                .isRendered(), result.saveRendered);
    }

}
