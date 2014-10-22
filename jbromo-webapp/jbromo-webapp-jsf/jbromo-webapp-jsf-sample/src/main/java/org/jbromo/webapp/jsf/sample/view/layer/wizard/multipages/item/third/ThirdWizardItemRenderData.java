package org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.third;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data sent by render event.
 * 
 * @author qjafcunuas
 * 
 */
@AllArgsConstructor
@Getter
public class ThirdWizardItemRenderData implements Serializable {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 7133806792968692798L;
    /**
     * True if previous item is the first.
     */
    private final Boolean previousItemFirst;
}
