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
package org.jbromo.webapp.jsf.component.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.jbromo.common.ListUtil;
import org.jbromo.common.StringUtil;

/**
 * Define default component model.
 *
 * @author qjafcunuas
 *
 */
public class AbstractComponentModel implements Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 5168179941075473312L;

    /**
     * Define if component should be rendered or not.
     */
    @Getter
    @Setter
    private boolean rendered = true;

    /**
     * The style class.
     */
    private String styleClass = null;

    /**
     * The style classes.
     */
    private final List<String> styleClasses = new ArrayList<String>();

    /**
     * Add a styleClass.
     *
     * @param styleClass
     *            the style class to add.
     */
    public void addStyleClass(final String styleClass) {
        if (!StringUtil.isEmpty(styleClass)
                && !styleClass.equals(ListUtil.getLast(this.styleClasses))) {
            this.styleClasses.add(styleClass);
            this.styleClass = null;
        }
    }

    /**
     * Remove a style class.
     *
     * @param styleClass
     *            the style to remove.
     */
    public void removeStyleClass(final String styleClass) {
        if (styleClass != null && this.styleClasses.remove(styleClass)) {
            this.styleClass = null;
        }
    }

    /**
     * Remove all style class.
     *
     */
    public void removeAllStyleClass() {
        this.styleClasses.clear();
        this.styleClass = null;
    }

    /**
     * Return the style class.
     *
     * @return the style class.
     */
    public String getStyleClass() {
        if (this.styleClass != null) {
            return this.styleClass;
        }
        if (this.styleClasses.isEmpty()) {
            return null;
        } else if (this.styleClasses.size() == 1) {
            this.styleClass = this.styleClasses.iterator().next();
        } else {
            final StringBuilder builder = new StringBuilder();
            for (final String one : this.styleClasses) {
                builder.append(one);
                builder.append(StringUtil.SPACE);
            }
            this.styleClass = builder.substring(0, builder.length() - 1);
        }
        return this.styleClass;
    }
}
