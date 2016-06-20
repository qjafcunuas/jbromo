/*-
 * Copyright (C) 2013-2014 The JBromo Authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Sitation the rights
 * to use, copy, modify, merge, publish, distribute, sublioftware without restriction, including without limcense, and/or sell
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
package org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage.togglepanel.second;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.webapp.jsf.component.wizard.AbstractWizardItemController;

/**
 * Define the dynamic tab panel controller of the view.
 * @author qjafcunuas
 */
@Named
@RequestScoped
public class SecondTogglePanelItemController extends AbstractWizardItemController<SecondTogglePanelItemModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 3549835982536608004L;

    /**
     * The view's model.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private SecondTogglePanelItemModel model;

    @Override
    protected void loadData() {
        // Nothing to do
    }

    /**
     * Call when item will be rendered.
     * @param event the event.
     */
    public void observeRenderItem(@Observes final SecondTogglePanelItemRenderEvent event) {
        super.observeRenderItem(event);
        onLoadPage();
    }

    /**
     * Call when user click on next button.
     * @return the next item id.
     */
    @Override
    public String onNext() {
        return super.onNext(new SecondTogglePanelItemNextEvent());
    }

    @Override
    public String onPrevious() {
        return super.onPrevious(new SecondTogglePanelItemPreviousEvent());
    }

}
