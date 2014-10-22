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
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.jbromo.webapp.jsf.mvc.view.AbstractViewModel;
import org.jbromo.webapp.jsf.view.IView;

/**
 * Define abstract wizard model implementation.
 *
 * @author qjafcunuas
 *
 */
@Slf4j
public abstract class AbstractWizardModel extends AbstractViewModel {
    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -4278494299625550378L;

    /**
     * The render events sent.
     */
    @Getter(AccessLevel.PRIVATE)
    private final List<AbstractWizardItemRenderEvent<? extends Serializable>> renderEvents = new ArrayList<AbstractWizardItemRenderEvent<?>>();

    /**
     * Add a render event.
     *
     * @param event
     *            the event to add.
     */
    protected void addRenderEvent(
            final AbstractWizardItemRenderEvent<? extends Serializable> event) {
        if (!event.equals(getLastRenderEvent())) {
            getRenderEvents().add(event);
        }
        logRenderEventsSize();
    }

    /**
     * Remove the last render event.
     */
    protected void removeLastRenderEvent() {
        getRenderEvents().remove(getRenderEvents().size() - 1);
        logRenderEventsSize();
    }

    /**
     * Log renderEvents size.
     */
    private void logRenderEventsSize() {
        log.debug("{} render events memorized", getRenderEvents().size());
    }

    /**
     * Return the last render event.
     *
     * @return the last render event.
     */
    protected AbstractWizardItemRenderEvent<? extends Serializable> getLastRenderEvent() {
        if (getRenderEvents().isEmpty()) {
            return null;
        } else {
            return getRenderEvents().get(getRenderEvents().size() - 1);
        }
    }

    /**
     * Return the current wizard item.
     *
     * @return the current wizard item.
     */
    public IView getCurrentWizardItem() {
        final AbstractWizardItemRenderEvent<? extends Serializable> renderEvent = getLastRenderEvent();
        if (renderEvent == null) {
            return null;
        } else {
            // Return the next item id, because it's the rendered item after the
            // event was sent.
            return renderEvent.getNextItem();
        }
    }

}
