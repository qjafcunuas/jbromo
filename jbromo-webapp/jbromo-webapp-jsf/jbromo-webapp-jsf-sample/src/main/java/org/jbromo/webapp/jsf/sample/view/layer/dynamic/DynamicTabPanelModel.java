package org.jbromo.webapp.jsf.sample.view.layer.dynamic;

import javax.inject.Named;

import lombok.Getter;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.jbromo.common.IntegerUtil;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewModel;

/**
 * Define the dynamic tab panel model of the view.
 * 
 * @author qjafcunuas
 * 
 */
@Named
@ViewAccessScoped
public class DynamicTabPanelModel extends AbstractViewModel {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -2930800098788027207L;

    /**
     * Count the number of displayed ajax tab.
     */
    @Getter
    private final int[] ajaxTabCounter = new int[IntegerUtil.INT_10];

    /**
     * Count the number of displayed server tab.
     */
    @Getter
    private final int[] serverTabCounter = new int[IntegerUtil.INT_10];

}
