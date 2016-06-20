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

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.jbromo.common.IntegerUtil;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Define a row data.
 * @author qjafcunuas
 */
@EqualsAndHashCode(of = {"primaryKey"})
public class DataRow implements Serializable {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -4555118327274556353L;

    /**
     * Define the name size max.
     */
    public static final int NAME_SIZE_MAX = IntegerUtil.INT_50;

    /**
     * Define the description size max.
     */
    public static final int DESCRIPTION_SIZE_MAX = IntegerUtil.INT_50;

    /**
     * The primary key.
     */
    @Getter
    @Setter
    private Integer primaryKey;

    /**
     * The name.
     */
    @Getter
    @Setter
    @Size(max = NAME_SIZE_MAX)
    private String name;

    /**
     * The description.
     */
    @Getter
    @Setter
    @Size(max = DESCRIPTION_SIZE_MAX)
    private String description;

}
