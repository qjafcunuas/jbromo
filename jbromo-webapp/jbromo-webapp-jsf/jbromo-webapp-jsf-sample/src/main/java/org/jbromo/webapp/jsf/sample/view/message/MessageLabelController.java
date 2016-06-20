/*-
 * Copyright (C) 2013-2014 The JBromo Authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (theestriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all co "Software"), to deal
 * in the Software without rpies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jbromo.webapp.jsf.sample.view.message;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.common.i18n.IMessageLabel;
import org.jbromo.common.i18n.MessageLabel;
import org.jbromo.common.i18n.MessageSeverity;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewController;
import org.jbromo.webapp.jsf.sample.i18n.AppMessageKey;
import org.jbromo.webapp.jsf.view.RenderView;
import org.jbromo.webapp.jsf.view.RenderViewEvent;

/**
 * Define the SelectOneMenu controller of the view.
 * @author qjafcunuas
 */
@Named
@RequestScoped
public class MessageLabelController extends AbstractViewController<MessageLabelModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 3549835982536608004L;

    /**
     * The view's model.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private MessageLabelModel model;

    @Override
    protected void loadData() {
        // Initialize labels.
        getModel().setError(buildLabel(MessageSeverity.ERROR));
        getModel().setFatal(buildLabel(MessageSeverity.FATAL));
        getModel().setInfo(buildLabel(MessageSeverity.INFO));
        getModel().setOk(buildLabel(MessageSeverity.OK));
        getModel().setWaiting(buildLabel(MessageSeverity.WAITING));
        getModel().setWarning(buildLabel(MessageSeverity.WARNING));

        getModel().getMessages().add(getModel().getFatal());
        getModel().getMessages().add(getModel().getError());
        getModel().getMessages().add(getModel().getWarning());
        getModel().getMessages().add(getModel().getWaiting());
        getModel().getMessages().add(getModel().getInfo());
        getModel().getMessages().add(getModel().getOk());
    }

    /**
     * Build a message label for a severity.
     * @param severity the severity.
     * @return the message label.
     */
    private IMessageLabel buildLabel(final MessageSeverity severity) {
        return new MessageLabel(AppMessageKey.VIEW_MESSAGE_LABEL_TEXT, severity, severity);
    }

    /**
     * Call when view will be rendered.
     * @param event the event.
     */
    public void observeRenderView(@Observes @RenderView(controller = MessageLabelController.class) final RenderViewEvent event) {
        onLoadPage();
    }

}
