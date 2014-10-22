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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.jbromo.webapp.jsf.mvc.view.AbstractViewController;
import org.jbromo.webapp.jsf.view.IView;

/**
 * Define abstract wizard item controller implementation.
 *
 * @author qjafcunuas
 * @param <M>
 *            the model type.
 */
@Slf4j
public abstract class AbstractWizardItemController<M extends AbstractWizardItemModel>
        extends AbstractViewController<M> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -2283695919825253165L;

    /**
     * Define if an event has been sent when previous button has been clicked.
     * Not set in model because only used by this controller during event phase.
     */
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private boolean defaultPreviousEventSent;

    /**
     * Call when user clicks on next button.
     *
     * @return the next item id.
     */
    public abstract String onNext();

    /**
     * Used for sending next event and go to next item.
     *
     * @param nextEvent
     *            the next event to send.
     * @return the next item id.
     */
    protected String onNext(
            final AbstractWizardItemNextEvent<? extends Serializable> nextEvent) {
        fireEvent(nextEvent);
        if (nextEvent.getNextItem() == null) {
            log.debug("Next : stay on same wizard");
            return getViewId(getModel().getCurrentItem());
        } else {
            log.debug("Next : go to {} wizard", nextEvent.getNextItem().getId());
            return getViewId(nextEvent.getNextItem());
        }
    }

    /**
     * Return the view id to display. For mono-page wizard, return the wizard
     * view id. For multi-pages, return the wiew id of the wizard item.
     *
     * @param itemId
     *            the item to display.
     * @return the view id.
     */
    private String getViewId(final IView itemId) {
        if (getModel().getWizard().getId() != null) {
            return getModel().getWizard().getId();
        } else {
            return itemId.getId();
        }
    }

    /**
     * Call when user clicks on previous button.
     *
     * @return the previous item id.
     */
    public String onPrevious() {
        return onPrevious(null);
    }

    /**
     * Call when user clicks on previous button.
     *
     * @param previousEvent
     *            the previous event to send.
     * @return the previous item id.
     */
    protected String onPrevious(
            final AbstractWizardItemPreviousEvent<? extends Serializable> previousEvent) {
        log.debug("Prev : return to {} wizard", getModel().getPreviousItem()
                .getId());
        if (previousEvent != null) {
            fireEvent(previousEvent);
        }
        if (!isDefaultPreviousEventSent()) {
            // Call wizard controller so that it can update its data.
            final WizardItemDefaultPreviousEvent defaultEvent = new WizardItemDefaultPreviousEvent();
            fireEvent(defaultEvent);
        }
        return getViewId(getModel().getPreviousItem());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <E extends Serializable> void fireEvent(final E event) {
        if (AbstractWizardItemEvent.class.isInstance(event)) {
            fireEvent((AbstractWizardItemEvent<Serializable>) event);
        } else {
            getBeanManager().fireEvent(event);
        }
    }

    /**
     * Fire an item event.
     *
     * @param event
     *            the event to fire.
     */
    protected void fireEvent(final AbstractWizardItemEvent<Serializable> event) {
        // Set wizard identification in the event.
        event.setWizard(getModel().getWizard());
        // Fire event.
        getBeanManager().fireEvent(event);
        if (WizardItemDefaultPreviousEvent.class.isInstance(event)) {
            setDefaultPreviousEventSent(true);
        } else if (AbstractWizardItemPreviousEvent.class.isInstance(event)) {
            // Event is a specific previous event, so send default previous item
            // event and says to the wizard controller not sending the last
            // render event.
            final WizardItemDefaultPreviousEvent previousEvent = new WizardItemDefaultPreviousEvent();
            previousEvent.setWizard(getModel().getWizard());
            getBeanManager().fireEvent(previousEvent);
            setDefaultPreviousEventSent(true);
        }

    }

    /**
     * Must be call when item will be rendered.
     *
     * @param <T>
     *            the element type.
     * @param event
     *            the event.
     */
    protected <T extends Serializable> void observeRenderItem(
            final AbstractWizardItemRenderEvent<T> event) {
        getModel().setWizard(event.getWizard());
        getModel().setPreviousItem(event.getCurrentItem());
    }

}
