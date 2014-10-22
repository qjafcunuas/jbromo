package org.jbromo.webapp.jsf.sample.view.button;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.common.IntegerUtil;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewController;
import org.jbromo.webapp.jsf.view.RenderView;
import org.jbromo.webapp.jsf.view.RenderViewEvent;

/**
 * Define the ImageButton controller of the view.
 * 
 * @author qjafcunuas
 * 
 */
@Named
@RequestScoped
public class CommandButtonController extends
        AbstractViewController<CommandButtonModel> {

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
     * 
     * @param event
     *            the event.
     */
    public void observeRenderView(
            @Observes @RenderView(controller = CommandButtonController.class) final RenderViewEvent event) {
        onLoadPage();
    }

    /**
     * Call when user click on button.
     * 
     * @return view's id.
     */
    public String onClick() {
        getModel().setClickCounter(1 + getModel().getClickCounter());
        return "";
    }

    /**
     * Call when user click on ajax button.
     * 
     * @return view's id.
     */
    public String onAjaxClick() {
        getModel().setAjaxCounter(1 + getModel().getAjaxCounter());
        return "";
    }

}
