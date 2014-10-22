package org.jbromo.webapp.jsf.sample.view.select.common;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Define an option of the selectOneMenu.
 * 
 * @author qjafcunuas
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = { "key" })
public class SelectMenuOption implements Serializable,
        Comparable<SelectMenuOption> {
    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -5620664467135380325L;
    /**
     * The object key.
     */
    private final Integer key;
    /**
     * The label to display.
     */
    private final String label;
    /**
     * The description to display.
     */
    private final String description;

    @Override
    public int compareTo(final SelectMenuOption option) {
        return getLabel().compareTo(option.getLabel());
    }

}
