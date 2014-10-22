package org.jbromo.webapp.jsf.sample.view.select.onemenu;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.jbromo.webapp.jsf.sample.view.select.common.SelectMenuOption;

/**
 * Define a row element.
 * 
 * @author qjafcunuas
 * 
 */
@Getter
@Setter
public class SelectOneMenuRow implements Serializable {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 1991187992246869187L;

    /**
     * Define selectOneMenu readonly attribute.
     */
    private Boolean readonly;

    /**
     * Define selectOneMenu noReadonlyLabel is not null.
     */
    private Boolean noReadonlyLabel;

    /**
     * Define selectOneMenu noSelectionLabel is not null.
     */
    private Boolean noSelectionLabel;

    /**
     * Define selectOneMenu value.
     */
    private SelectMenuOption value;

    /**
     * Define selectOneMenu required is not null.
     */
    private Boolean required;
}
