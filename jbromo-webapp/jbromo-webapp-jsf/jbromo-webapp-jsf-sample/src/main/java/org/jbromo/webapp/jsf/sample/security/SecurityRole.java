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
package org.jbromo.webapp.jsf.sample.security;

import org.jbromo.common.i18n.IMessageKey;
import org.jbromo.webapp.jsf.sample.i18n.AppMessageKey;
import org.jbromo.webapp.jsf.security.ISecurityRole;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Define security role.
 * @author qjafcunuas
 */
@AllArgsConstructor
public enum SecurityRole implements ISecurityRole {
    /** Admin role. */
    ADMIN(AppMessageKey.ROLE_ADMIN), /** Guest role. */
    GUEST(AppMessageKey.ROLE_GUEST);

    /**
     * The i18n key.
     */
    @Getter
    private final IMessageKey key;

    @Override
    public String getName() {
        return name();
    }

}
