package org.jbromo.webapp.jsf.sample.view.layer.table.columnheader;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.webapp.jsf.mvc.view.AbstractViewController;
import org.jbromo.webapp.jsf.view.RenderView;
import org.jbromo.webapp.jsf.view.RenderViewEvent;

/**
 * Define the table controller with Bromo sort/filter.
 *
 * @author qjafcunuas
 *
 */
@Named
@RequestScoped
public class ColumnHeaderDataTableController extends
        AbstractViewController<ColumnHeaderDataTableModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 8941579397966046670L;

    /**
     * The component's model.
     */
    @Getter(AccessLevel.PROTECTED)
    @Inject
    private ColumnHeaderDataTableModel model;

    @Override
    protected void loadData() {
        // Nothing to do
    }

    /**
     * Call when view will be rendered.
     *
     * @param event
     *            the event.
     */
    public void observeRenderView(
            @Observes @RenderView(controller = ColumnHeaderDataTableController.class) final RenderViewEvent event) {
        onLoadPage();
    }

}
