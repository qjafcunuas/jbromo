package org.jbromo.webapp.jsf.sample.view.layer.scroller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import lombok.Getter;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewModel;
import org.jbromo.webapp.jsf.sample.view.layer.service.DataRow;

/**
 * Define the dataScroller model of the view.
 * 
 * @author qjafcunuas
 * 
 */
@Named
@ViewAccessScoped
public class DataScrollerModel extends AbstractViewModel {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -2930800098788027207L;

    /**
     * The rows.
     */
    @Getter
    private final List<DataRow> rows = new ArrayList<DataRow>();

}
