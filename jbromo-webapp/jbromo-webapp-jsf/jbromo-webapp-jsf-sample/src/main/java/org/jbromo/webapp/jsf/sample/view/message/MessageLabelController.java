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
 * 
 * @author qjafcunuas
 * 
 */
@Named
@RequestScoped
public class MessageLabelController extends
        AbstractViewController<MessageLabelModel> {

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
     * 
     * @param severity
     *            the severity.
     * @return the message label.
     */
    private IMessageLabel buildLabel(final MessageSeverity severity) {
        return new MessageLabel(AppMessageKey.VIEW_MESSAGE_LABEL_TEXT,
                severity, severity);
    }

    /**
     * Call when view will be rendered.
     * 
     * @param event
     *            the event.
     */
    public void observeRenderView(
            @Observes @RenderView(controller = MessageLabelController.class) final RenderViewEvent event) {
        onLoadPage();
    }

}
