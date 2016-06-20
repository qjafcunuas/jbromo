/*-
 * Copyright (C) 2013-2014 The JBromo Authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction,  copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit personsincluding without limitation the rights
 * to use, to whom the Software is
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
package org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage.togglepanel.third;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.common.BooleanUtil;
import org.jbromo.webapp.jsf.component.wizard.AbstractWizardItemController;

/**
 * Define the dynamic tab panel controller of the view.
 * @author qjafcunuas
 */
@Named
@RequestScoped
public class ThirdTogglePanelItemController extends AbstractWizardItemController<ThirdTogglePanelItemModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 3549835982536608004L;

    /**
     * The view's model.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private ThirdTogglePanelItemModel model;

    @Override
    protected void loadData() {
        // Nothing to do
    }

    /**
     * Call when item will be rendered.
     * @param <T> the element type.
     * @param event the event.
     */
    public <T extends Serializable> void observeRenderItem(@Observes final ThirdTogglePanelItemRenderEvent event) {
        super.observeRenderItem(event);
        onLoadPage();
        getModel().setPreviousItemFirst(BooleanUtil.isTrue(event.getData().getPreviousItemFirst()));
    }

    @Override
    public String onNext() {
        return super.onNext(new ThirdTogglePanelItemNextEvent());
    }

}
