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
 * Define event for rendering a wizard item.
 *
 * @param <T>
 *            the type of the data to send to item wizard controller.
 * @author qjafcunuas
 *
 */
public abstract class AbstractWizardItemRenderEvent<T extends Serializable>
        extends AbstractWizardItemEvent<T> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 6921272160042594972L;

    /**
     * The currently rendered item before sending this render event.
     */
    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private IView currentItem;

    /**
     * The rendered item after sending this render event.
     */
    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PRIVATE)
    private IView nextItem;

    /**
     * Default constructor.
     *
     * @param nextItem
     *            the item who will be rendered.
     * @param nextEventSent
     *            The next event who has been sent before rendering this next
     *            item.
     */
    protected AbstractWizardItemRenderEvent(
            final IView nextItem,
            final AbstractWizardItemNextEvent<? extends Serializable> nextEventSent) {
        this(nextItem, nextEventSent, (T) null);
    }

    /**
     * Default constructor.
     *
     * @param nextItem
     *            the item who will be rendered.
     * @param nextEventSent
     *            The next event who has been sent before rendering this next
     *            item.
     * @param data
     *            the data to send to wizard controller.
     */
    protected AbstractWizardItemRenderEvent(
            final IView nextItem,
            final AbstractWizardItemNextEvent<? extends Serializable> nextEventSent,
            final T data) {
        super(data);
        this.setNextItem(nextItem);
        if (nextEventSent != null) {
            nextEventSent.setNextItem(nextItem);
        }
    }

}
