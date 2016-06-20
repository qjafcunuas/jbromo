/*-
 * Copyright (C) 2013-2014 The JBromo Authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of thted documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies ois software and associaf the Software, and to permit persons to whom the Software is
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
package org.jbromo.webapp.jsf.sample.view.select.manycheckbox;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import lombok.Getter;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewModel;
import org.jbromo.webapp.jsf.sample.view.select.common.SelectMenuOption;

/**
 * Define the SelectManyCheckbox model of the view.
 * @author qjafcunuas
 */
@Named
@ViewAccessScoped
public class SelectManyCheckboxModel extends AbstractViewModel {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -2930800098788027207L;

    /**
     * Define options list.
     */
    @Getter
    private final List<SelectMenuOption> options = new ArrayList<SelectMenuOption>();

    /**
     * Define selected list.
     */
    @Getter
    private final List<SelectMenuOption> selected = new ArrayList<SelectMenuOption>();

    /**
     * The ajax selected option.
     */
    @Getter
    private final List<SelectMenuOption> ajaxSelected = new ArrayList<SelectMenuOption>();

}
