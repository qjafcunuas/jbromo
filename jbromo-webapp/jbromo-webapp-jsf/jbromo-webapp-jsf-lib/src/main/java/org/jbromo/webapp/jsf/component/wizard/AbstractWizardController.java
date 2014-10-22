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
package org.jbromo.webapp.jsf.component.wizard;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.jbromo.webapp.jsf.mvc.view.AbstractViewController;
import org.jbromo.webapp.jsf.view.IView;
import org.jbromo.webapp.jsf.view.RenderViewEvent;

/**
 * Define abstract wizard controller implementation.
 *
 * @author qjafcunuas
 * @param <M>
 *            the model type.
 */
@Slf4j
public abstract class AbstractWizardController<M extends AbstractWizardModel>
        extends AbstractViewController<M> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -2283695919825253165L;

    /**
     * True if render event has already been sent.
     */
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private boolean renderEventSent;

    /**
     * Return the wizard identification.
     *
     * @return the wizard.
     */
    protected abstract IView getWizard();

    @SuppressWarnings("unchecked")
    @Override
    protected <E extends Serializable> void fireEvent(final E event) {
        if (AbstractWizardItemEvent.class.isInstance(event)) {
            final AbstractWizardItemEvent<Serializable> itemEvent = (AbstractWizardItemEvent<Serializable>) event;
            fireEvent(itemEvent);
        } else if (RenderViewEvent.class.isInstance(event)) {
            final RenderViewEvent renderEvent = (RenderViewEvent) event;
            super.fireEvent(renderEvent);
        } else {
            super.fireEvent(event);
        }
    }

    /**
     * Render a view from a wizard.
     *
     * @param nextEventSent
     *            the next item sent.
     * @param view
     *            the view to render.
     */
    protected void fireEventRenderView(
            final AbstractWizardItemNextEvent<? extends Serializable> nextEventSent,
            final IView view) {
        nextEventSent.setNextItem(super.fireEventRenderView(view));
    }

    @Override
    protected IView fireEventRenderView(final IView view) {
        // Don't used this method.
        log.error("Don't use this method!");
        return null;
    }

    /**
     * Fire an item event.
     *
     * @param event
     *            the event to fire.
     */
    protected void fireEvent(final AbstractWizardItemEvent<Serializable> event) {
        // Set wizard identification in the event.
        event.setWizard(getWizard());
        if (AbstractWizardItemRenderEvent.class.isInstance(event)) {
            final AbstractWizardItemRenderEvent<Serializable> renderEvent = (AbstractWizardItemRenderEvent<Serializable>) event;
            // Update current rendered item in event before sending event for
            // rendering next item.
            renderEvent.setCurrentItem(getModel().getCurrentWizardItem());
        }
        super.fireEvent(event);
        if (AbstractWizardItemRenderEvent.class.isInstance(event)) {
            final AbstractWizardItemRenderEvent<Serializable> renderEvent = (AbstractWizardItemRenderEvent<Serializable>) event;
            // Set item as current item in wizard model.
            getModel().addRenderEvent(renderEvent);
            setRenderEventSent(true);
        }
    }

    /**
     * Call when view will be rendered for conversational scoped.
     *
     * @param event
     *            the event.
     */
    protected void observeRenderViewImpl(final RenderViewEvent event) {
        onLoadPage();
        // View id is dynamic for wizard.
        event.setView(new IView() {

            @Override
            public Annotation getRenderViewAnnotation() {
                return getModel().getCurrentWizardItem()
                        .getRenderViewAnnotation();
            }

            @Override
            public String getId() {
                return getModel().getCurrentWizardItem().getId();
            }
        });
    }

    /**
     * Method used for forcing superclass to implement it with "@Observes" and
     * "@ForWizard(controller=)" annotation. The implementation must call
     * observeItemPreviousEventImpl method.
     *
     * @param event
     *            the previous event.
     */
    protected abstract void observeItemPreviousEvent(
            final AbstractWizardItemPreviousEvent<? extends Serializable> event);

    /**
     * Observes item previous event.
     *
     * @param event
     *            the previous event.
     */
    protected void observeItemPreviousEventImpl(
            final AbstractWizardItemPreviousEvent<? extends Serializable> event) {
        if (WizardItemDefaultPreviousEvent.class.isInstance(event)) {
            if (!isRenderEventSent()) {
                // Remove the render event that display the current wizard.
                getModel().removeLastRenderEvent();
                // Remove the render event that display the previous wizard.
                final AbstractWizardItemRenderEvent<? extends Serializable> last = getModel()
                        .getLastRenderEvent();
                getModel().removeLastRenderEvent();
                // Fire the render event that displayed the previous wizard.
                fireEvent(last);
            }
        } else {
            AbstractWizardItemRenderEvent<? extends Serializable> last = null;
            if (isRenderEventSent()) {
                last = getModel().getLastRenderEvent();
                getModel().removeLastRenderEvent();
            }
            // Remove the render event that display the current wizard.
            getModel().removeLastRenderEvent();
            // Remove the render event that display the previous wizard.
            getModel().removeLastRenderEvent();
            if (isRenderEventSent()) {
                getModel().addRenderEvent(last);
            }
        }

    }

}
