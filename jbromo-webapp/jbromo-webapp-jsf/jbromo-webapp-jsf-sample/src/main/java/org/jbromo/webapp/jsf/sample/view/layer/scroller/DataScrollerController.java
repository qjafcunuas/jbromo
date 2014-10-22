package org.jbromo.webapp.jsf.sample.view.layer.scroller;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewController;
import org.jbromo.webapp.jsf.sample.view.layer.service.DataRow;
import org.jbromo.webapp.jsf.view.RenderView;
import org.jbromo.webapp.jsf.view.RenderViewEvent;

/**
 * Define the DataScroller controller of the view.
 * 
 * @author qjafcunuas
 * 
 */
@Named
@RequestScoped
public class DataScrollerController extends
        AbstractViewController<DataScrollerModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 3549835982536608004L;

    /**
     * The view's model.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private DataScrollerModel model;

    @Override
    protected void loadData() {
        DataRow row;
        for (int i = 0; i < RandomUtil.nextInt(IntegerUtil.INT_24,
                IntegerUtil.INT_256); i++) {
            row = new DataRow();
            row.setName(RandomUtil.nextString(IntegerUtil.INT_50));
            row.setDescription(RandomUtil.nextString(IntegerUtil.INT_256));
            getModel().getRows().add(row);
        }
    }

    /**
     * Call when view will be rendered.
     * 
     * @param event
     *            the event.
     */
    public void observeRenderView(
            @Observes @RenderView(controller = DataScrollerController.class) final RenderViewEvent event) {
        onLoadPage();
    }

}
