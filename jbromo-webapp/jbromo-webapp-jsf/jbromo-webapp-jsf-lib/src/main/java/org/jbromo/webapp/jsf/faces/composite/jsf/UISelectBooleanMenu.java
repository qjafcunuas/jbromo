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
package org.jbromo.webapp.jsf.faces.composite.jsf;

import java.io.IOException;
import java.util.List;

import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;

import org.jbromo.common.ListUtil;
import org.jbromo.common.StringUtil;
import org.jbromo.common.i18n.IMessageKey;
import org.jbromo.common.i18n.MessageKey;
import org.jbromo.webapp.jsf.cdi.CDIFacesUtil;
import org.jbromo.webapp.jsf.faces.FacesResourceBundle;
import org.jbromo.webapp.jsf.faces.composite.util.AbstractUIOutputSelectOne;

/**
 * Define UISelectYesNoMenu composite.
 *
 * @author qjafcunuas
 *
 */
@FacesComponent(value = "org.jbromo.webapp.jsf.faces.composite.jsf.UISelectBooleanMenu")
public class UISelectBooleanMenu extends AbstractUIOutputSelectOne {

    @Override
    protected List<?> getValues() {
        return ListUtil.toList(Boolean.TRUE, Boolean.FALSE);
    }

    @Override
    protected void encodeOutputText(final FacesContext context)
            throws IOException {
        if (isReadonly()) {
            final Boolean value = (Boolean) getValue();
            if (value == null) {
                getOutputText().setValue(getNoReadonlyLabel());
            } else {
                getOutputText().setValue(getLabel(value));
            }
        }
    }

    /**
     * Return the typeLabel attributes.
     *
     * @return the value.
     */
    protected String getTypeLabel() {
        return getAttribute("typeLabel");
    }

    /**
     * Return the trueLabel attributes.
     *
     * @return the value.
     */
    protected String getTrueLabel() {
        return getAttribute("trueLabel");
    }

    /**
     * Return the falseLabel attributes.
     *
     * @return the value.
     */
    protected String getFalseLabel() {
        return getAttribute("falseLabel");
    }

    /**
     * Return the i18n message according to the value.
     *
     * @param value
     *            the value.
     * @return the i18n message.
     */
    public String getLabel(final Boolean value) {
        String label;
        if (Boolean.TRUE.equals(value)) {
            label = getTrueLabel();
            if (!StringUtil.isEmpty(label)) {
                return label;
            } else {
                return getLabel(value, getTypeLabel());
            }
        } else {
            label = getFalseLabel();
            if (!StringUtil.isEmpty(label)) {
                return label;
            } else {
                return getLabel(value, getTypeLabel());
            }
        }
    }

    /**
     * Return the label value according to the type.
     *
     * @param value
     *            the value.
     * @param typeLabel
     *            the type label.
     * @return the label.
     */
    private String getLabel(final Boolean value, final String typeLabel) {
        final FacesResourceBundle bundle = CDIFacesUtil
                .getFacesResourceBundle();
        IMessageKey key = null;
        if (typeLabel == null) {
            // Yes/No by default.
            key = value ? MessageKey.YES : MessageKey.NO;
        } else if ("yesno".equals(typeLabel)) {
            key = value ? MessageKey.YES : MessageKey.NO;
        } else if ("truefalse".equals(typeLabel)) {
            key = value ? MessageKey.TRUE : MessageKey.FALSE;
        }
        return bundle.getMessage(key);
    }

}
