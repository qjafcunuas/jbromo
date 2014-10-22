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
package org.jbromo.webapp.jsf.component.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Define JUnit for ButtonBarModel class.
 *
 * @author qjafcunuas
 *
 */
public class ButtonBarModelTest extends
        AbstractDisabledModelTest<ButtonBarModel> {

    @Override
    protected ButtonBarModel newInstance() {
        final ButtonBarModel model = new ButtonBarModel(false, false);
        model.addButton(true, true);
        return model;
    }

    @Override
    @Test
    public void disabled() {
        boolean disabled = false;
        super.disabled();
        // ButtonBar is enabled.
        ButtonBarModel model = new ButtonBarModel(false, false);
        // First button is set as enabled.
        // Second button is set as disabled.
        model.addButton(true, false);
        model.addButton(true, true);

        // First button must be enabled.
        // Second button must be disabled.
        disabled = false;
        for (final ButtonModel button : model.getButtons()) {
            Assert.assertEquals(button.isDisabled(), disabled);
            disabled = !disabled;
        }

        // New buttonBar is disabled.
        model = new ButtonBarModel(false, true);
        // First button is set as enabled.
        // Second button is set as disabled.
        model.addButton(true, false);
        model.addButton(true, true);

        // all buttons must be disabled because buttons bar is disabled.
        for (final ButtonModel button : model.getButtons()) {
            Assert.assertTrue(button.isDisabled());
        }

        // Enabled buttons bar.
        model.setDisabled(false);
        // First button must be enabled.
        // Second button must be disabled.
        disabled = false;
        for (final ButtonModel button : model.getButtons()) {
            Assert.assertEquals(button.isDisabled(), disabled);
            disabled = !disabled;
        }

        // Disabled buttons bar.
        model.setDisabled(true);
        // Buttons must be disabled.
        for (final ButtonModel button : model.getButtons()) {
            Assert.assertTrue(button.isDisabled());
        }

    }

    @Override
    @Test
    public void rendered() {
        boolean rendered = false;
        super.rendered();
        // ButtonBar is rendered.
        ButtonBarModel model = new ButtonBarModel(true, false);
        // First button is set as rendered.
        // Second button is set as not rendered.
        model.addButton(true, false);
        model.addButton(false, false);

        // First button must be rendered.
        // Second button must not be rendered.
        rendered = true;
        for (final ButtonModel button : model.getButtons()) {
            Assert.assertEquals(button.isRendered(), rendered);
            rendered = !rendered;
        }

        // New buttonBar is not rendered.
        model = new ButtonBarModel(false, false);
        // First button is set as rendered.
        // Second button is set as not rendered.
        model.addButton(true, false);
        model.addButton(false, false);

        // all buttons must not be rendered because buttons bar is not rendered.
        for (final ButtonModel button : model.getButtons()) {
            Assert.assertFalse(button.isRendered());
        }

        // Rendered buttons bar.
        model.setRendered(true);
        // First button must be rendered.
        // Second button must ber not rendered.
        rendered = true;
        for (final ButtonModel button : model.getButtons()) {
            Assert.assertEquals(button.isRendered(), rendered);
            rendered = !rendered;
        }

        // Hide buttons bar.
        model.setRendered(false);
        // Buttons must be not rendered.
        for (final ButtonModel button : model.getButtons()) {
            Assert.assertFalse(button.isRendered());
        }

    }
}
