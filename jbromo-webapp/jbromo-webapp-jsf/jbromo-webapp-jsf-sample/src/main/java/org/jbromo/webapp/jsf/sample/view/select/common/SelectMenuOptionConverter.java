package org.jbromo.webapp.jsf.sample.view.select.common;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import org.jbromo.webapp.jsf.cdi.CDIFacesUtil;
import org.jbromo.webapp.jsf.faces.converter.IConverter;
import org.jbromo.webapp.jsf.sample.view.select.onemenu.SelectOneMenuModel;

/**
 * Define the selectOneMenuOption converter.
 *
 * @author qjafcunuas
 *
 */
@FacesConverter(forClass = SelectMenuOption.class)
// @Advanced
public class SelectMenuOptionConverter implements IConverter<SelectMenuOption> {

    /**
     * Return the view's model.
     *
     * @return the view's model.
     */
    private SelectOneMenuModel getModel() {
        return CDIFacesUtil.getCDIObject(SelectOneMenuModel.class);
    }

    @Override
    public SelectMenuOption getAsObject(final FacesContext context,
            final UIComponent component, final String value) {
        if (value == null) {
            return null;
        } else {
            final SelectMenuOption option = new SelectMenuOption(
                    Integer.valueOf(value), null, null);
            final List<SelectMenuOption> options = getModel().getOptions();
            final int pos = options.indexOf(option);
            if (pos >= 0) {
                return options.get(pos);
            } else {
                return null;
            }
        }
    }

    @Override
    public String getAsString(final FacesContext context,
            final UIComponent component, final Object value) {
        if (value == null) {
            return null;
        }
        return ((SelectMenuOption) value).getKey().toString();
    }

}
