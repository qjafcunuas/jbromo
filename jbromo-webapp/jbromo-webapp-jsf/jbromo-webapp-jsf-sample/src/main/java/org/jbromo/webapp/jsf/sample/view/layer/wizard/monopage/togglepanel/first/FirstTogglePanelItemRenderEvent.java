/*-
 * Copyright (C) 2013-2014 The JBromo Authors.
 *
 * Permission is hereby granteny person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Softw

d, free of charge, toare without restriction, including without limitation the rights
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
package org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage.togglepanel.first;

import lombok.Getter;

import org.jbromo.webapp.jsf.component.wizard.AbstractWizardItemRenderEvent;
import org.jbromo.webapp.jsf.sample.view.View;

/**
 * Define event to fire for rendering first panel.
 * @author qjafcunuas
 */
@Getter
public class FirstTogglePanelItemRenderEvent extends AbstractWizardItemRenderEvent<FirstTogglePanelItemRenderData> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 903791455675769302L;

    /**
     * Default constructor.
     * @param data the data to send to the item controller.
     */
    public FirstTogglePanelItemRenderEvent(final FirstTogglePanelItemRenderData data) {
        super(View.TOGGEL_PANEL_FIRST_ITEM, null, data);
    }

}
