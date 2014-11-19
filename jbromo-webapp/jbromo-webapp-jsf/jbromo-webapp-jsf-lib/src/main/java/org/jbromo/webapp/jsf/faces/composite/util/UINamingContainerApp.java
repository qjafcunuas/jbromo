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
package org.jbromo.webapp.jsf.faces.composite.util;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;

/**
 * Define default framework UINamingContainer.
 *
 * @author qjafcunuas
 * @version 1.0.0
 *
 */
public class UINamingContainerApp extends UINamingContainer {

    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    /**
     * Return the component's parent assignable to a class.
     * 
     * @param <U>
     *            the UI type.
     * @param parentClass
     *            the UI parent class to get component.
     * @return the parent component.
     */
    protected <U extends UIComponent> U getParent(final Class<U> parentClass) {
        return getParent(this, parentClass);
    }

    /**
     * Return the component's parent assignable to a class.
     * 
     * @param <U>
     *            the UI type.
     * @param component
     *            the component to get parent.
     * @param parentClass
     *            the UI parent class to get component.
     * @return the parent component.
     */
    @SuppressWarnings("unchecked")
    protected <U extends UIComponent> U getParent(final UIComponent component,
            final Class<U> parentClass) {
        UIComponent parent = component.getParent();
        while (parent != null) {
            if (parentClass.isInstance(parent)) {
                return (U) parent;
            }
            parent = parent.getParent();
        }
        return null;
    }

    /**
     * Return the attribute key value.
     *
     * @param <O>
     *            the object type to return.
     * @param key
     *            the attribute key to get value.
     * @return the value.
     */
    @SuppressWarnings("unchecked")
    protected <O> O getAttribute(final String key) {
        final ValueExpression ve = getValueExpression(key);
        if (ve != null) {
            return (O) ve.getValue(getFacesContext().getELContext());
        } else if (getAttributes().containsKey(key)) {
            return (O) getAttributes().get(key);
        } else {
            return null;
        }
    }
}
