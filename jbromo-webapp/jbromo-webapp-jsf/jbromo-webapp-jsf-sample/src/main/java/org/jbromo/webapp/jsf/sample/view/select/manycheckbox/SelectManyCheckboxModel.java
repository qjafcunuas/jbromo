package org.jbromo.webapp.jsf.sample.view.select.manycheckbox;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import lombok.Getter;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewModel;
import org.jbromo.webapp.jsf.sample.view.select.common.SelectMenuOption;

/**
 * Define the SelectManyCheckbox model of the view.
 *
 * @author qjafcunuas
 *
 */
@Named
@ViewAccessScoped
public class SelectManyCheckboxModel extends AbstractViewModel {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -2930800098788027207L;

    /**
     * Define options list.
     */
    @Getter
    private final List<SelectMenuOption> options = new ArrayList<SelectMenuOption>();

    /**
     * Define selected list.
     */
    @Getter
    private final List<SelectMenuOption> selected = new ArrayList<SelectMenuOption>();

    /**
     * The ajax selected option.
     */
    @Getter
    private final List<SelectMenuOption> ajaxSelected = new ArrayList<SelectMenuOption>();

}
