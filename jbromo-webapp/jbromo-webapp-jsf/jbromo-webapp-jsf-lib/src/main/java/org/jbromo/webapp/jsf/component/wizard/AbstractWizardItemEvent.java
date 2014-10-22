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
package org.jbromo.webapp.jsf.component.wizard;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.jbromo.webapp.jsf.view.IView;

/**
 * Define default event to send to a wizard item.
 *
 * @param <T>
 *            the type of the data to send to wizard controller.
 * @author qjafcunuas
 *
 */
public abstract class AbstractWizardItemEvent<T extends Serializable>
        implements Serializable {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 6921272160042594972L;

    /**
     * The data to send to the controller.
     */
    @Getter
    private final T data;

    /**
     * Define the wizard where the item is used.
     */
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PACKAGE)
    private IView wizard;

    /**
     * Default constructor.
     *
     * @param data
     *            the data to send.
     */
    public AbstractWizardItemEvent(final T data) {
        super();
        this.data = data;
    }

}
