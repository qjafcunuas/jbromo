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

import javax.faces.component.FacesComponent;

import lombok.Getter;
import lombok.Setter;

import org.jbromo.webapp.jsf.faces.composite.util.UINamingContainerApp;

/**
 * Define UIDataScroller composite.
 *
 * @author qjafcunuas
 *
 */
@FacesComponent(value = "org.jbromo.webapp.jsf.faces.composite.jsf.UIDataScroller")
public class UIDataScroller extends UINamingContainerApp {

    /**
     * For binding the tab panel.
     */
    @Getter
    @Setter
    private org.richfaces.component.UIDataScroller dataScroller;

    /**
     * Return the rows number.
     *
     * @return the rows number.
     */
    public Integer getRows() {
        return getDataScroller().getRowCount();
    }

    /**
     * Return the pages number.
     *
     * @return the pages number.
     */
    public Integer getPages() {
        return getDataScroller().getPageCount();
    }

}
