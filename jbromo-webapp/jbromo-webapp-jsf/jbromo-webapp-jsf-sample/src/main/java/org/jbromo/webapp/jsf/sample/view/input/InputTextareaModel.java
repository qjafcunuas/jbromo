package org.jbromo.webapp.jsf.sample.view.input;

import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.jbromo.common.IntegerUtil;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewModel;

/**
 * Define inputText model's view.
 *
 * @author qjafcunuas
 *
 */
@Named
@ViewAccessScoped
public class InputTextareaModel extends AbstractViewModel {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -6286559128839207749L;

    /**
     * Input value.
     */
    @Getter
    @Setter
    @Size(max = IntegerUtil.INT_10)
    @NotNull
    private String input;

    /**
     * Inoutput value.
     */
    @Getter
    @Setter
    @Size(max = IntegerUtil.INT_20)
    @NotNull
    private String inoutput;
}
