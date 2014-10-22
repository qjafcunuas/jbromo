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

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.common.i18n.IMessageKey;
import org.jbromo.common.i18n.IMessageLabel;

/**
 * Define the label translation object for EL.
 *
 * @author qjafcunuas
 *
 */
@ApplicationScoped
@Named("i18n")
public class ELMessage {

    /**
     * The bundle.
     */
    @Inject
    @Getter(AccessLevel.PRIVATE)
    private FacesResourceBundle bundle;

    /**
     * Build label message.
     *
     * @param label
     *            the label.
     * @return the translated label.
     */
    public String formatLabel(final IMessageLabel label) {
        return getBundle().getMessage(label);
    }

    /**
     * Build a message.
     *
     * @param key
     *            the i18n key.
     * @return the translated message.
     */
    public String formatString(final String key) {
        return getBundle().getMessage(key);
    }

    /**
     * Build a key message.
     *
     * @param key
     *            the key.
     * @return the translated message.
     */
    public String formatKey(final IMessageKey key) {
        return formatString(key.getKey());
    }
}
