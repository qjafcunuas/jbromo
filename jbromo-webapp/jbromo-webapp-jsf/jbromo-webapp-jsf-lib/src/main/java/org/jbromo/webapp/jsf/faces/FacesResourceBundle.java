/*
 * Copyright (C) 2013-2014 The JBromo Authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jbromo.webapp.jsf.faces;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.config.IConfigKey;
import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.common.i18n.IMessageKey;
import org.jbromo.common.i18n.IMessageLabel;
import org.jbromo.webapp.jsf.locale.LocaleContext;

/**
 * Define the faces resource bundle.
 *
 * @author qjafcunuas
 *
 */
@SessionScoped
@Slf4j
public class FacesResourceBundle implements Serializable {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -8935636449092954608L;

    /**
     * The local context.
     */
    @Inject
    private LocaleContext localeContext;

    /**
     * The faces context.
     */
    @Inject
    private transient FacesContext facesContext;

    /**
     * The Application Message Ressource Bundle.
     */
    private transient ResourceBundle appMessageBundle;

    /**
     * The Bromo Message Ressource Bundle.
     */
    private transient ResourceBundle bromoMessageBundle;

    /**
     * The Bromo Config Ressource Bundle.
     */
    private transient ResourceBundle bromoConfigBundle;

    /**
     * Return the localized message according to a key.
     *
     * @param key
     *            the key.
     * @return the localized message.
     */
    public String getMessage(final IMessageKey key) {
        return getMessage(key.getKey());
    }

    /**
     * Return the localized message according to a key.
     *
     * @param key
     *            the key.
     * @return the localized message.
     */
    public String getMessage(final String key) {
        try {
            if (getAppMessageBundle().containsKey(key)) {
                return getAppMessageBundle().getString(key).replace("'", "''");
            } else if (getBromoMessageBundle().containsKey(key)) {
                return getBromoMessageBundle().getString(key)
                        .replace("'", "''");
            } else {
                return notFound(key);
            }
        } catch (final MissingResourceException e) {
            log.warn("Cannot localized key {}", key, e);
        } catch (final Exception e) {
            log.error("Problem when search the ressource bundle", e);
        }
        return notFound(key);
    }

    /**
     * Return the config according to a config key.
     *
     * @param config
     *            the config key.
     * @return the config.
     */
    public String getConfig(final IConfigKey config) {
        if (config == null) {
            return null;
        } else {
            return getConfig(config.getKey());
        }
    }

    /**
     * Return the config according to a key.
     *
     * @param key
     *            the key.
     * @return the config.
     */
    public String getConfig(final String key) {
        try {
            if (getBromoConfigBundle().containsKey(key)) {
                return getBromoConfigBundle().getString(key).replace("'", "''");
            } else {
                return notFound(key);
            }
        } catch (final MissingResourceException e) {
            log.warn("Cannot localized config key {}", key, e);
        } catch (final Exception e) {
            log.error("Problem when search the config ressource bundle", e);
        }
        return notFound(key);
    }

    /**
     * Return the localized message of an exception.
     *
     * @param exp
     *            the exception.
     * @return the localized message.
     */
    public String getMessage(final MessageLabelException exp) {
        return getMessage(exp.getLabel());
    }

    /**
     * Return the localized message according to a key.
     *
     * @param label
     *            the label.
     * @return the localized message.
     */
    public String getMessage(final IMessageLabel label) {
        if (label == null) {
            return null;
        }
        return getMessage(label.getKey(), label.getParameters());
    }

    /**
     * Return the localized message according to a key.
     *
     * @param key
     *            the key.
     * @param params
     *            the message parameters.
     * @return the localized message.
     */
    private String getMessage(final IMessageKey key, final Object... params) {
        try {
            final String text = getMessage(key);
            return MessageFormat.format(text, formatParams(params));
        } catch (final MissingResourceException e) {
            log.warn("Cannot localized key {} with params", key, e);
        } catch (final IllegalArgumentException e) {
            log.warn("Problem when format key {} with params", key, e);
        } catch (final Exception e) {
            log.error("Problem when search the ressource bundle with params", e);
        }
        return notFound(key);
    }

    /**
     * Return formated parameters.
     *
     * @param params
     *            the parameters to format.
     * @return the formated parameters.
     */
    private Object[] formatParams(final Object... params) {
        final Object[] formatted = new Object[params.length];
        for (int index = 0; index < params.length; index++) {
            formatted[index] = formatParam(params[index]);
        }
        return formatted;
    }

    /**
     * Return formated parameter.
     *
     * @param param
     *            the parameter to format.
     * @return the formated parameter.
     */
    private Object formatParam(final Object param) {
        if (param instanceof IMessageKey) {
            return getMessage((IMessageKey) param);
        } else if (param instanceof IMessageLabel) {
            return getMessage((IMessageLabel) param);
        } else if (param instanceof Collection<?>) {
            StringBuilder builder = null;
            for (final Object one : (Collection<?>) param) {
                if (builder == null) {
                    builder = new StringBuilder();
                } else {
                    builder.append(", ");
                }
                builder.append(formatParam(one));
            }
            return builder.toString();
        } else if (param instanceof Locale) {
            // FIXME
            // assume language and not country ... and distinguish it
            return this.localeContext.getLanguageLabel((Locale) param, true);
        }
        return param;
    }

    /**
     * Return the localized message according to a key.
     *
     * @param key
     *            the key.
     * @param params
     *            the message parameters.
     * @return the localized message.
     */
    private String getMessage(final IMessageKey key,
            final Collection<Object> params) {
        return getMessage(key, params.toArray());
    }

    /**
     * Return a not found key value.
     *
     * @param key
     *            the key.
     * @return the message.
     */
    private String notFound(final IMessageKey key) {
        return notFound(key.getKey());
    }

    /**
     * Return a not found key value.
     *
     * @param key
     *            the key.
     * @return the message.
     */
    private String notFound(final String key) {
        final StringBuffer buffer = new StringBuffer("??");
        buffer.append(key);
        buffer.append("??");
        return buffer.toString();
    }

    /**
     * Get the application message resource Bundle.
     *
     * @return the application message resource bundle.
     */
    private ResourceBundle getAppMessageBundle() {
        if (this.appMessageBundle == null) {
            this.appMessageBundle = this.facesContext.getApplication()
                    .getResourceBundle(this.facesContext, "message");
        }
        return this.appMessageBundle;
    }

    /**
     * Get the bromo message resource Bundle.
     *
     * @return the message resource bundle.
     */
    private ResourceBundle getBromoMessageBundle() {
        if (this.bromoMessageBundle == null) {
            this.bromoMessageBundle = ResourceBundle.getBundle("bromomessages",
                    this.localeContext.getLocale());
        }
        return this.bromoMessageBundle;
    }

    /**
     * Get the bromo config resource Bundle.
     *
     * @return the config resource bundle.
     */
    private ResourceBundle getBromoConfigBundle() {
        if (this.bromoConfigBundle == null) {
            this.bromoConfigBundle = ResourceBundle.getBundle("bromoconfig");
        }
        return this.bromoConfigBundle;
    }
}
