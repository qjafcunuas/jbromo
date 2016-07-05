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
package org.jbromo.webapp.jsf.component.datatable;

import java.io.Serializable;

import org.jbromo.webapp.jsf.component.model.CheckboxModel;

import lombok.Getter;
import lombok.Setter;

/**
 * Define a row for a bromo datatable.
 * @author qjafcunuas
 * @param <E> the element row type.
 */
public class DataTableRow<E extends Serializable> implements Serializable {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -5238556148720831711L;

    /**
     * The row element.
     */
    @Getter
    @Setter
    private E element;

    /**
     * Define select checkbox model.
     */
    @Getter
    private final CheckboxModel selectBox = new CheckboxModel(true, false);

}
