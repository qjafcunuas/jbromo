package org.jbromo.webapp.jsf.sample.view.language;

import java.util.List;
import java.util.Locale;

import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewModel;

/**
 * Define the language model of the view.
 * 
 * @author qjafcunuas
 * 
 */
@Named
@ViewAccessScoped
public class LanguageModel extends AbstractViewModel {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -2930800098788027207L;

    /**
     * Define languages list.
     */
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private List<Locale> languages;

    /**
     * Define languages list.
     */
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private List<Locale> allLanguages;

}
