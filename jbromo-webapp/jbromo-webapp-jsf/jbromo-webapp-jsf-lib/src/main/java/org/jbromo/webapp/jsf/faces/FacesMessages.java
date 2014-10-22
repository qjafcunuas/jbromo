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
package org.jbromo.webapp.jsf.faces;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.common.i18n.IMessageKey;
import org.jbromo.common.i18n.IMessageLabel;
import org.jbromo.common.i18n.MessageSeverity;

/**
 * Define default controller implementation.
 *
 * @author qjafcunuas
 *
 */
@ApplicationScoped
public class FacesMessages implements Serializable {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -3486564511771064001L;

    /**
     * The faces context.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private FacesContext facesContext;

    /**
     * The faces message bundle.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private FacesResourceBundle messageBundle;

    /**
     * Build a faces message.
     *
     * @param uiMessage
     *            the ui message on which displaying message.
     * @param severity
     *            the message severity.
     * @param message
     *            the message to display.
     */
    private void message(final UIMessage uiMessage,
            final FacesMessage.Severity severity, final String message) {
        final FacesMessage msg = new FacesMessage(severity, message, null);
        if (uiMessage == null) {
            getFacesContext().addMessage((String) null, msg);
        } else {
            getFacesContext().addMessage(uiMessage.getClientId(), msg);
        }
    }

    /**
     * Build a faces message.
     *
     * @param label
     *            the label to display.
     */
    public void message(final IMessageLabel label) {
        message((UIMessage) null, label);
    }

    /**
     * Return true if severity is Fatal.
     *
     * @param severity
     *            the severity.
     * @return true/false.
     */
    private boolean isSeverityFatal(final FacesMessage.Severity severity) {
        return FacesMessage.SEVERITY_FATAL.equals(severity);
    }

    /**
     * Return true if severity is Error.
     *
     * @param severity
     *            the severity.
     * @return true/false.
     */
    private boolean isSeverityError(final FacesMessage.Severity severity) {
        return FacesMessage.SEVERITY_ERROR.equals(severity);
    }

    /**
     * Build a faces message.
     *
     * @param label
     *            the label to display.
     * @param uiMessage
     *            the ui message on which displaying message.
     */
    public void message(final UIMessage uiMessage, final IMessageLabel label) {
        final String msg = this.messageBundle.getMessage(label);
        final FacesMessage.Severity severity = getFacesSeverity(label
                .getSeverity());
        if (!getFacesContext().isValidationFailed()
                && (isSeverityError(severity) || isSeverityFatal(severity))) {
            getFacesContext().validationFailed();
        }
        message(uiMessage, severity, msg);
    }

    /**
     * Build faces messages.
     *
     * @param labels
     *            the labels to display.
     */
    public void message(final Collection<IMessageLabel> labels) {
        message((UIMessage) null, labels);
    }

    /**
     * Build a faces message.
     *
     * @param labels
     *            the labels to display.
     * @param uiMessage
     *            the ui message on which displaying message.
     */
    public void message(final UIMessage uiMessage,
            final Collection<IMessageLabel> labels) {
        for (final IMessageLabel label : labels) {
            message(uiMessage, label);
        }
    }

    /**
     * Build a faces message.
     *
     * @param exception
     *            the exception to display.
     */
    public void message(final MessageLabelException exception) {
        message((UIMessage) null, exception);
    }

    /**
     * Build a faces message.
     *
     * @param exception
     *            the exception to display.
     * @param uiMessage
     *            the ui message on which displaying message.
     */
    public void message(final UIMessage uiMessage,
            final MessageLabelException exception) {
        final String msg = this.messageBundle.getMessage(exception);
        final FacesMessage.Severity severity = getFacesSeverity(exception
                .getLabel().getSeverity());
        message(uiMessage, severity, msg);
    }

    /**
     * Return faces severity.
     *
     * @param severity
     *            the i18n severity.
     * @return the faces severity.
     */
    private Severity getFacesSeverity(final MessageSeverity severity) {
        if (severity == null) {
            return FacesMessage.SEVERITY_ERROR;
        }
        switch (severity) {
        case INFO:
            return FacesMessage.SEVERITY_INFO;
        case WARNING:
            return FacesMessage.SEVERITY_WARN;
        default:
            return FacesMessage.SEVERITY_ERROR;
        }
    }

    /**
     * Build an info faces message.
     *
     * @param uiMessage
     *            the ui message on which displaying message.
     * @param message
     *            the message to display.
     */
    private void info(final UIMessage uiMessage, final String message) {
        message(uiMessage, FacesMessage.SEVERITY_INFO, message);
    }

    /**
     * Build a warn faces message.
     *
     * @param uiMessage
     *            the ui message on which displaying message.
     * @param message
     *            the message to display.
     */
    private void warn(final UIMessage uiMessage, final String message) {
        message(uiMessage, FacesMessage.SEVERITY_WARN, message);
    }

    /**
     * Build an error faces message.
     *
     * @param uiMessage
     *            the ui message on which displaying message.
     * @param message
     *            the message to display.
     */
    private void error(final UIMessage uiMessage, final String message) {
        message(uiMessage, FacesMessage.SEVERITY_ERROR, message);
    }

    /**
     * Build an info faces message.
     *
     * @param message
     *            the message to display.
     */
    public void info(final IMessageKey message) {
        info((UIMessage) null, message);
    }

    /**
     * Build an warn faces message.
     *
     * @param message
     *            the message to display.
     */
    public void warn(final IMessageKey message) {
        warn((UIMessage) null, message);
    }

    /**
     * Build an error faces message.
     *
     * @param message
     *            the message to display.
     */
    public void error(final IMessageKey message) {
        error((UIMessage) null, message);
    }

    /**
     * Build an info faces message.
     *
     * @param uiMessage
     *            the ui message on which displaying message.
     * @param message
     *            the message to display.
     */
    protected void info(final UIMessage uiMessage, final IMessageKey message) {
        info(uiMessage, this.messageBundle.getMessage(message));
    }

    /**
     * Build an warn faces message.
     *
     * @param uiMessage
     *            the ui message on which displaying message.
     * @param message
     *            the message to display.
     */
    protected void warn(final UIMessage uiMessage, final IMessageKey message) {
        warn(uiMessage, this.messageBundle.getMessage(message));
    }

    /**
     * Build an error faces message.
     *
     * @param uiMessage
     *            the ui message on which displaying message.
     * @param message
     *            the message to display.
     */
    protected void error(final UIMessage uiMessage, final IMessageKey message) {
        error(uiMessage, this.messageBundle.getMessage(message));
    }

}
