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
package org.jbromo.model.jpa.test.builder.field;

import java.lang.reflect.Field;

import lombok.Getter;
import lombok.Setter;

/**
 * Define a field value for validation.
 *
 * @author qjafcunuas
 *
 * @param <O>
 *            the object type.
 */
@Getter
public class ValidationValue<O> {
    /**
     * The field of the value.
     */
    private final Field field;
    /**
     * An explanation label.
     */
    private final String label;
    /**
     * The value for validation.
     */
    private final O value;

    /**
     * Default constructor.
     *
     * @param field
     *            the field to set.
     * @param label
     *            the label to set.
     * @param value
     *            the value to set.
     */
    public ValidationValue(final Field field, final String label, final O value) {
        super();
        this.field = field;
        this.label = label;
        this.value = value;
    }

    /**
     * Default constructor.
     *
     * @param validationValue
     *            the validationValue to get all fields, except value
     * @param value
     *            the value to set.
     */
    public ValidationValue(final ValidationValue<?> validationValue,
            final O value) {
        super();
        this.field = validationValue.getField();
        this.label = validationValue.getLabel();
        this.nullManyToOne = validationValue.isNullManyToOne();
        this.value = value;
    }

    /**
     * Define if this object is for a ManyToOne with null value.
     */
    @Setter
    private boolean nullManyToOne = false;

}
