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

import java.util.ArrayList;
import java.util.List;

import org.jbromo.common.ListUtil;
import org.jbromo.common.ObjectUtil;

/**
 * Define Button configuration.
 *
 * @author qjafcunuas
 *
 */
public class ButtonBarModel extends AbstractDisabledModel {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 856103023447306219L;

    /**
     * Define sorted buttons bar.
     */
    private final List<ButtonModel> buttons = new ArrayList<ButtonModel>();

    /**
     * Default constructor.
     *
     * @param rendered
     *            true if button is rendered.
     * @param disabled
     *            true if button is disabled.
     */
    public ButtonBarModel(final boolean rendered, final boolean disabled) {
        super();
        super.setRendered(rendered);
        super.setDisabled(disabled);
    }

    /**
     * Add a new button on the bar.
     *
     * @param rendered
     *            true if button is rendered.
     * @param disabled
     *            true if button is disabled.
     * @return button.
     */
    public ButtonModel addButton(final boolean rendered, final boolean disabled) {
        final ButtonModel button = new ButtonForBarModel(this, rendered,
                disabled);
        this.buttons.add(button);
        return button;
    }

    /**
     * Removes the first occurrence of the specified element from the bar.
     *
     * @param button
     *            the button to remove.
     * @return if bar contained the button.
     */
    public boolean removeButton(final ButtonModel button) {
        if (this.buttons.remove(button)) {
            ObjectUtil.cast(button, ButtonForBarModel.class).setBarModel(null);
        }
        return false;
    }

    /**
     * Return buttons.
     *
     * @return buttons.
     */
    public Iterable<ButtonModel> getButtons() {
        return ListUtil.toUnmodifiableList(this.buttons);
    }

}