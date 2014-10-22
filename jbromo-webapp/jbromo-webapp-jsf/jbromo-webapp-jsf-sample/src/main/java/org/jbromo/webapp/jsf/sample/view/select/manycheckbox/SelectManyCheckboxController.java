package org.jbromo.webapp.jsf.sample.view.select.manycheckbox;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewController;
import org.jbromo.webapp.jsf.sample.view.select.common.SelectMenuOptionUtil;
import org.jbromo.webapp.jsf.view.RenderView;
import org.jbromo.webapp.jsf.view.RenderViewEvent;

/**
 * Define the SelectManyCheckbox controller of the view.
 *
 * @author qjafcunuas
 *
 */
@Named
@RequestScoped
public class SelectManyCheckboxController extends
        AbstractViewController<SelectManyCheckboxModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 3549835982536608004L;

    /**
     * The view's model.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private SelectManyCheckboxModel model;

    @Override
    protected void loadData() {
        // Initialize options.
        initOptions();
        // Set random key. It should be sorted by selectOneMenu component.
        getModel().getSelected().addAll(
                RandomUtil.nextSubCollection(getModel().getOptions()));
    }

    /**
     * Initialize options elements.
     */
    private void initOptions() {
        getModel().getOptions().clear();
        getModel().getOptions().addAll(
                SelectMenuOptionUtil.build(IntegerUtil.INT_5));
    }

    /**
     * Call when view will be rendered.
     *
     * @param event
     *            the event.
     */
    public void observeRenderView(
            @Observes @RenderView(controller = SelectManyCheckboxController.class) final RenderViewEvent event) {
        onLoadPage();
    }

}
