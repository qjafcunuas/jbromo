/*-
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
package org.jbromo.common.i18n;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Define an i18n label.
 * @author qjafcunuas
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageLabel implements IMessageLabel {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 8990000917395720674L;

    /**
     * The i18n key.
     */
    @Setter
    private IMessageKey key;

    /**
     * The severity.
     */
    @Setter
    private MessageSeverity severity = MessageSeverity.ERROR;

    /**
     * The parameters.
     */
    private final List<Serializable> parameters = new ArrayList<>();

    /**
     * Default constructor.
     * @param key the key to set.
     */
    public MessageLabel(final IMessageKey key) {
        super();
        setKey(key);
    }

    /**
     * Default constructor.
     * @param key the key to set.
     * @param parameters the array parameters.
     */
    public MessageLabel(final IMessageKey key, final Serializable... parameters) {
        this(key);
        for (final Serializable parameter : parameters) {
            getParameters().add(parameter);
        }
    }

    /**
     * Default constructor.
     * @param key the key to set.
     * @param parameters the collection parameters.
     */
    public MessageLabel(final IMessageKey key, final Collection<Serializable> parameters) {
        this(key);
        getParameters().addAll(parameters);
    }

    /**
     * Default constructor.
     * @param key the key to set.
     * @param severity the severity.
     * @param parameters the parameters.
     */
    public MessageLabel(final IMessageKey key, final MessageSeverity severity, final Serializable... parameters) {
        this(key, severity);
        for (final Serializable parameter : parameters) {
            getParameters().add(parameter);
        }
    }

    /**
     * Default constructor.
     * @param key the key to set.
     * @param severity the severity.
     * @param parameters the parameters.
     */
    public MessageLabel(final IMessageKey key, final MessageSeverity severity, final Collection<Serializable> parameters) {
        this(key, severity);
        getParameters().addAll(parameters);
    }

}
