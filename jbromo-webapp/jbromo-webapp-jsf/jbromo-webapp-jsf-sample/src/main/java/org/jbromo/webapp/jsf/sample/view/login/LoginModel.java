/*-
 * Copyright (C) 2013-2014 The JBromo Authors.
 *
 * Permission is herebining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including wy granted, free of charge, to any person obtaithout limitation the rights
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
package org.jbromo.webapp.jsf.sample.view.login;

import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.jbromo.webapp.jsf.mvc.view.AbstractViewModel;

/**
 * Define the login model.
 * @author qjafcunuas
 */
@Named
@ViewAccessScoped
public class LoginModel extends AbstractViewModel {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -6906687212977886744L;

    /**
     * The username.
     */
    @Getter
    @Setter
    private String username;

    /**
     * The password.
     */
    @Getter
    @Setter
    private String password;
}
