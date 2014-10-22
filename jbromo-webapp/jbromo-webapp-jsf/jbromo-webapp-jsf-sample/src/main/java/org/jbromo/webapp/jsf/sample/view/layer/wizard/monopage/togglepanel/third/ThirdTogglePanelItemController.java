package org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage.togglepanel.third;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.common.BooleanUtil;
import org.jbromo.webapp.jsf.component.wizard.AbstractWizardItemController;

/**
 * Define the dynamic tab panel controller of the view.
 *
 * @author qjafcunuas
 *
 */
@Named
@RequestScoped
public class ThirdTogglePanelItemController extends
        AbstractWizardItemController<ThirdTogglePanelItemModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 3549835982536608004L;

    /**
     * The view's model.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private ThirdTogglePanelItemModel model;

    @Override
    protected void loadData() {
        // Nothing to do
    }

    /**
     * Call when item will be rendered.
     *
     * @param <T>
     *            the element type.
     * @param event
     *            the event.
     */
    public <T extends Serializable> void observeRenderItem(
            @Observes final ThirdTogglePanelItemRenderEvent event) {
        super.observeRenderItem(event);
        onLoadPage();
        getModel().setPreviousItemFirst(
                BooleanUtil.isTrue(event.getData().getPreviousItemFirst()));
    }

    @Override
    public String onNext() {
        return super.onNext(new ThirdTogglePanelItemNextEvent());
    }

}
