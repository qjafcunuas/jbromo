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
package org.jbromo.webapp.jsf.test.mock.faces;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.application.ProjectStage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseId;
import javax.faces.render.RenderKit;

import org.jbromo.webapp.jsf.faces.FacesContextUtil;

/**
 * Define the mock of FacesContext.
 *
 * @author qjafcunuas
 *
 */
@Alternative
@RequestScoped
public class AlternativeFacesContext extends FacesContext {

    /**
     * Default constructor.
     */
    public AlternativeFacesContext() {
        super();
    }

    @PostConstruct
    private void postConstruct() {
        FacesContext.setCurrentInstance(MockFacesContext.mock());
    }

    @Override
    public Application getApplication() {
        return FacesContextUtil.getFacesContext().getApplication();
    }

    @Override
    public Map<Object, Object> getAttributes() {
        return FacesContextUtil.getFacesContext().getAttributes();
    }

    @Override
    public PartialViewContext getPartialViewContext() {
        return FacesContextUtil.getFacesContext().getPartialViewContext();
    }

    @Override
    public Iterator<String> getClientIdsWithMessages() {
        return FacesContextUtil.getFacesContext().getClientIdsWithMessages();
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return FacesContextUtil.getFacesContext().getExceptionHandler();
    }

    @Override
    public void setExceptionHandler(final ExceptionHandler exceptionHandler) {
        FacesContextUtil.getFacesContext()
                .setExceptionHandler(exceptionHandler);
    }

    @Override
    public Severity getMaximumSeverity() {
        return FacesContextUtil.getFacesContext().getMaximumSeverity();
    }

    @Override
    public Iterator<FacesMessage> getMessages() {
        return FacesContextUtil.getFacesContext().getMessages();
    }

    @Override
    public List<FacesMessage> getMessageList() {
        return FacesContextUtil.getFacesContext().getMessageList();
    }

    @Override
    public List<FacesMessage> getMessageList(final String clientId) {
        return FacesContextUtil.getFacesContext().getMessageList(clientId);
    }

    @Override
    public Iterator<FacesMessage> getMessages(final String clientId) {
        return FacesContextUtil.getFacesContext().getMessages(clientId);
    }

    @Override
    public RenderKit getRenderKit() {
        return FacesContextUtil.getFacesContext().getRenderKit();
    }

    @Override
    public boolean getRenderResponse() {
        return FacesContextUtil.getFacesContext().getRenderResponse();
    }

    @Override
    public boolean getResponseComplete() {
        return FacesContextUtil.getFacesContext().getResponseComplete();
    }

    @Override
    public boolean isValidationFailed() {
        return FacesContextUtil.getFacesContext().isValidationFailed();
    }

    @Override
    public ResponseStream getResponseStream() {
        return FacesContextUtil.getFacesContext().getResponseStream();
    }

    @Override
    public void setResponseStream(final ResponseStream responseStream) {
        FacesContextUtil.getFacesContext().setResponseStream(responseStream);
    }

    @Override
    public ResponseWriter getResponseWriter() {
        return FacesContextUtil.getFacesContext().getResponseWriter();
    }

    @Override
    public void setResponseWriter(final ResponseWriter responseWriter) {
        FacesContextUtil.getFacesContext().setResponseWriter(responseWriter);
    }

    @Override
    public UIViewRoot getViewRoot() {
        return FacesContextUtil.getFacesContext().getViewRoot();
    }

    @Override
    public void setViewRoot(final UIViewRoot root) {
        FacesContextUtil.getFacesContext().setViewRoot(root);
    }

    @Override
    public void addMessage(final String clientId, final FacesMessage message) {
        FacesContextUtil.getFacesContext().addMessage(clientId, message);
    }

    @Override
    public boolean isReleased() {
        return FacesContextUtil.getFacesContext().isReleased();
    }

    @Override
    public void release() {
        FacesContextUtil.getFacesContext().release();
    }

    @Override
    public void renderResponse() {
        FacesContextUtil.getFacesContext().renderResponse();
    }

    @Override
    public boolean isPostback() {
        return FacesContextUtil.getFacesContext().isPostback();
    }

    @Override
    public void responseComplete() {
        FacesContextUtil.getFacesContext().responseComplete();
    }

    @Override
    public void validationFailed() {
        FacesContextUtil.getFacesContext().validationFailed();
    }

    @Override
    public PhaseId getCurrentPhaseId() {
        return FacesContextUtil.getFacesContext().getCurrentPhaseId();
    }

    @Override
    public void setCurrentPhaseId(final PhaseId currentPhaseId) {
        FacesContextUtil.getFacesContext().setCurrentPhaseId(currentPhaseId);
    }

    @Override
    public void setProcessingEvents(final boolean processingEvents) {
        FacesContextUtil.getFacesContext()
                .setProcessingEvents(processingEvents);
    }

    @Override
    public boolean isProcessingEvents() {
        return FacesContextUtil.getFacesContext().isProcessingEvents();
    }

    @Override
    public boolean isProjectStage(final ProjectStage stage) {
        return FacesContextUtil.getFacesContext().isProjectStage(stage);
    }

    @Override
    public ExternalContext getExternalContext() {
        return FacesContextUtil.getFacesContext().getExternalContext();
    }

    @Override
    public ELContext getELContext() {
        return FacesContextUtil.getFacesContext().getELContext();
    }

}
