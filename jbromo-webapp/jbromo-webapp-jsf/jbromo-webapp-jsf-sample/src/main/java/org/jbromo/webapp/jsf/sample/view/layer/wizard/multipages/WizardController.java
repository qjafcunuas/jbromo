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
package org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.jbromo.common.BooleanUtil;
import org.jbromo.webapp.jsf.component.wizard.AbstractWizardController;
import org.jbromo.webapp.jsf.component.wizard.AbstractWizardItemPreviousEvent;
import org.jbromo.webapp.jsf.sample.view.View;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.first.FirstWizardItemNextEvent;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.first.FirstWizardItemRenderData;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.first.FirstWizardItemRenderEvent;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.second.SecondWizardItemNextEvent;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.second.SecondWizardItemPreviousEvent;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.second.SecondWizardItemRenderEvent;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.third.ThirdWizardItemNextEvent;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.third.ThirdWizardItemRenderData;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.third.ThirdWizardItemRenderEvent;
import org.jbromo.webapp.jsf.view.IView;
import org.jbromo.webapp.jsf.view.RenderView;
import org.jbromo.webapp.jsf.view.RenderViewEvent;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * Define the dynamic tab panel controller of the view.
 * @author qjafcunuas
 */
@Named
@RequestScoped
public class WizardController extends AbstractWizardController<WizardModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 3549835982536608004L;

    /**
     * The conversation.
     */
    @Inject
    @Getter(AccessLevel.PRIVATE)
    private Conversation conversation;

    /**
     * The view's model.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private WizardModel model;

    @Override
    protected IView getWizard() {
        return View.WIZARD;
    }

    @Override
    protected void loadData() {
        if (!getConversation().isTransient()) {
            getConversation().end();
        }
        getConversation().begin();
        // Load first panel item.
        fireEvent(new FirstWizardItemRenderEvent(new FirstWizardItemRenderData(false)));
        getModel().setPageLoaded(true);
    }

    /**
     * Call when view will be rendered.
     * @param event the event.
     */
    public void observeRenderView(@Observes @RenderView(controller = WizardController.class) final RenderViewEvent event) {
        super.observeRenderViewImpl(event);
    }

    /**
     * Call when user click on next button of first panel item.
     * @param nextEvent the event.
     */
    public void observeFirstItemNext(@Observes @RenderView(controller = WizardController.class) final FirstWizardItemNextEvent nextEvent) {
        getModel().setFirstData(nextEvent.getData());
        if (BooleanUtil.isTrue(nextEvent.getData().getNextItemThird())) {
            fireEvent(new ThirdWizardItemRenderEvent(nextEvent, new ThirdWizardItemRenderData(true)));
        } else {
            fireEvent(new SecondWizardItemRenderEvent(nextEvent));
        }
    }

    /**
     * Call when user click on next button of first panel item.
     * @param nextEvent the event.
     */
    public void observeSecondItemNext(@Observes @RenderView(controller = WizardController.class) final SecondWizardItemNextEvent nextEvent) {
        fireEvent(new ThirdWizardItemRenderEvent(nextEvent, new ThirdWizardItemRenderData(false)));
    }

    /**
     * Call when user click on next button of first panel item.
     * @param event the event.
     */
    public void observeSecondItemPrevious(@Observes @RenderView(controller = WizardController.class) final SecondWizardItemPreviousEvent event) {
        fireEvent(new FirstWizardItemRenderEvent(new FirstWizardItemRenderData(getModel().getFirstData().getNextItemThird())));
    }

    @Override
    protected void observeItemPreviousEvent(
            @Observes @RenderView(controller = WizardController.class) final AbstractWizardItemPreviousEvent<? extends Serializable> event) {
        observeItemPreviousEventImpl(event);
    }

    /**
     * Call when user click on next button of first panel item.
     * @param nextEvent the event.
     */
    public void observeThirdItemNext(@Observes @RenderView(controller = WizardController.class) final ThirdWizardItemNextEvent nextEvent) {
        fireEventRenderView(nextEvent, View.TOGGLE_PANEL);
    }

}
