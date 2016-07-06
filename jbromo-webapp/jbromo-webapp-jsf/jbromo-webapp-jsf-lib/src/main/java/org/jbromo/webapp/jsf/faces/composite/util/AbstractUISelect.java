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
package org.jbromo.webapp.jsf.faces.composite.util;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;

import org.jbromo.common.ListUtil;
import org.jbromo.common.ObjectUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Define Abstract UISelect composite.
 * @author qjafcunuas
 * @version 1.0.0
 */
@Slf4j
public abstract class AbstractUISelect extends UINamingContainerApp {

    /**
     * For binding the selectItems.
     */
    @Getter
    @Setter
    private UISelectItems selectItems;

    @Override
    public void encodeBegin(final FacesContext context) throws IOException {
        encodeSelectItems(context);
        super.encodeBegin(context);
    }

    /**
     * Return the values attributes.
     * @return the value.
     */
    protected <O> List<O> getValues() {
        final List<O> values = getAttribute("values");
        if (ListUtil.isNotEmpty(values)) {
            return sort(values);
        }
        return values;
    }

    /**
     * Return the sortBy attributes.
     * @return the value.
     */
    protected ValueExpression getSortBy() {
        return getValueExpression("sortBy");
    }

    /**
     * Return the itemLabel attributes.
     * @return the value.
     */
    protected ValueExpression getItemLabel() {
        return getValueExpression("itemLabel");
    }

    /**
     * Return the itemDescription attributes.
     * @return the value.
     */
    protected ValueExpression getItemDescription() {
        return getValueExpression("itemDescription");
    }

    /**
     * Sort values according to the displaying label.
     * @param values the values to sort.
     * @return the sorted list.
     */
    @SuppressWarnings("unchecked")
    private <O> List<O> sort(final List<O> values) {
        if (ListUtil.hasElements(values)) {
            final ValueExpression ve = getSortBy();
            if (ve != null) {
                final Comparator<O> comparator = new Comparator<O>() {
                    @Override
                    public int compare(final O o1, final O o2) {
                        final ELContext elContext = getFacesContext().getELContext();
                        // sort1 value.
                        elContext.getELResolver().setValue(elContext, null, "item", o1);
                        final Object sort1 = ve.getValue(elContext);
                        // sort2 value.
                        elContext.getELResolver().setValue(elContext, null, "item", o2);
                        final Object sort2 = ve.getValue(elContext);
                        // Compare value.
                        return ObjectUtil.compare(sort1, sort2);
                    }
                };
                try {
                    final List<O> sorted = values.getClass().newInstance();
                    sorted.addAll(values);
                    Collections.sort(sorted, comparator);
                    return sorted;
                } catch (final InstantiationException e) {
                    log.error("Cannot instantiate objects list", e);
                } catch (final IllegalAccessException e) {
                    log.error("Illegal access objects list", e);
                }
            }
        }
        return values;
    }

    /**
     * Render the beginning of the selectItems to the response contained in the specified FacesContext.
     * @param context the context.
     * @throws IOException exception.
     */
    protected void encodeSelectItems(final FacesContext context) throws IOException {
        getSelectItems().setValue(getValues());
    }

}
