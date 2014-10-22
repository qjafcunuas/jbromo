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

import java.io.IOException;
import java.util.List;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.jbromo.common.BooleanUtil;
import org.jbromo.common.ListUtil;
import org.jbromo.common.ObjectUtil;

/**
 * Define Abstract UISelect composite with outputText for readonly.
 * 
 * @author qjafcunuas
 * @version 1.0.0
 * 
 */
public abstract class AbstractUIOutputSelectOne extends AbstractUISelect {

    /**
     * For binding the selectOne.
     */
    @Getter
    @Setter
    private UISelectOne selectOne;

    /**
     * For binding the outputText.
     */
    @Getter
    @Setter
    private HtmlOutputText outputText;

    /**
     * For binding the selectItems.
     */
    @Getter
    @Setter
    private UISelectItems selectItems;

    @Override
    public void encodeBegin(final FacesContext context) throws IOException {
        encodeOutputText(context);
        encodeSelectOne(context);
        super.encodeBegin(context);
    }

    /**
     * Return the noSelectionLabel attributes.
     *
     * @return the value.
     */
    protected String getNoSelectionLabel() {
        return getAttribute("noSelectionLabel");
    }

    /**
     * Return the noReadonlyLabel attributes.
     *
     * @return the value.
     */
    protected String getNoReadonlyLabel() {
        return getAttribute("noReadonlyLabel");
    }

    /**
     * Return the readonly attribute.
     *
     * @return the value.
     */
    protected boolean isReadonly() {
        final Boolean value = getAttribute("readonly");
        return BooleanUtil.isTrue(value);
    }

    /**
     * Return the value attribute.
     *
     * @return the value.
     */
    protected Object getValue() {
        return getAttribute("value");
    }

    /**
     * Render the beginning of the outputText to the response contained in the
     * specified FacesContext.
     *
     * @param context
     *            the context.
     * @throws IOException
     *             exception.
     */
    protected void encodeOutputText(final FacesContext context)
            throws IOException {
        if (isReadonly()) {
            if (getValue() == null) {
                getOutputText().setValue(getNoReadonlyLabel());
            } else {
                final ELContext elContext = context.getELContext();
                elContext.getELResolver().setValue(elContext, null, "item",
                        getValue());
                // itemLabel as value.
                ValueExpression ve = getItemLabel();
                if (ve != null) {
                    getOutputText().setValue(ve.getValue(elContext));
                }

                // itemDescription as title.
                ve = getItemDescription();
                if (ve != null) {
                    getOutputText().setTitle(ve.getValue(elContext).toString());
                }
            }
        }
    }

    /**
     * Render the beginning of the command button to the response contained in
     * the specified FacesContext.
     *
     * @param context
     *            the context.
     * @throws IOException
     *             exception.
     */
    protected void encodeSelectOne(final FacesContext context)
            throws IOException {
        getSelectOne().setRendered(!isReadonly());
        if (!isReadonly()) {
            // Remove no selection label.
            if (!getSelectOne().getChildren().isEmpty()
                    && getSelectOne().getChildren().get(0) instanceof UISelectItem) {
                getSelectOne().getChildren().remove(0);
            }
            // Add no selection label.
            if (getNoSelectionLabel() != null) {
                final UISelectItem selectItem = new UISelectItem();
                selectItem.setNoSelectionOption(true);
                selectItem.setItemLabel(getNoSelectionLabel());
                getSelectOne().getChildren().add(0, selectItem);
            }
            // If empty values, hide selectOneMenu.
            if (ListUtil.isList(getSelectItems().getValue())) {
                final List<?> list = ObjectUtil.cast(getSelectItems()
                        .getValue(), List.class);
                getSelectOne().setRendered(!list.isEmpty());
            } else {
                getSelectOne().setRendered(true);
            }
        }
    }

}
