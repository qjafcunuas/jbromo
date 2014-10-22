package org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage.togglepanel.first;

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
public class FirstTogglePanelItemRenderData implements Serializable {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 7133806792968692798L;
    /**
     * True if next item is the third.
     */
    private final Boolean nextItemThird;
}
