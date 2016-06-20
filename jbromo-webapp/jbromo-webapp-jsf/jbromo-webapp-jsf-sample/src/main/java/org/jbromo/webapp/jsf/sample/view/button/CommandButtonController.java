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
package org.jbromo.webapp.jsf.sample.view.button;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.jbromo.common.IntegerUtil;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewController;
import org.jbromo.webapp.jsf.view.RenderView;
import org.jbromo.webapp.jsf.view.RenderViewEvent;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * Define the ImageButton controller of the view.
 * @author qjafcunuas
 */
@Named
@RequestScoped
public class CommandButtonController extends AbstractViewController<CommandButtonModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 3549835982536608004L;

    /**
     * The view's model.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private CommandButtonModel model;

    @Override
    protected void loadData() {
        getModel().setClickCounter(IntegerUtil.INT_0);
        getModel().setAjaxCounter(IntegerUtil.INT_0);
    }

    /**
     * Call when view will be rendered.
     * @param event the event.
     */
    public void observeRenderView(@Observes @RenderView(controller = CommandButtonController.class) final RenderViewEvent event) {
        onLoadPage();
    }

    /**
     * Call when user click on button.
     * @return view's id.
     */
    public String onClick() {
        getModel().setClickCounter(1 + getModel().getClickCounter());
        return "";
    }

    /**
     * Call when user click on ajax button.
     * @return view's id.
     */
    public String onAjaxClick() {
        getModel().setAjaxCounter(1 + getModel().getAjaxCounter());
        return "";
    }

}
