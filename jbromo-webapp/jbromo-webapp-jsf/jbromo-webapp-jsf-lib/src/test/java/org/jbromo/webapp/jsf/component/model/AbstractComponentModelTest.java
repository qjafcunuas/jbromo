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
 * Define JUnit for AbstractComponentModel class.
 *
 * @author qjafcunuas
 * @param <M>
 *            the model type.
 *
 */
public abstract class AbstractComponentModelTest<M extends AbstractComponentModel> {

    /**
     * One style class.
     */
    private static final String STYLE_CLASS_FIRST = "firstClass";
    /**
     * Another style class.
     */
    private static final String STYLE_CLASS_SECOND = "secondClass";

    /**
     * Return a new instance of model.
     *
     * @return the model.
     */
    protected abstract M newInstance();

    /**
     * Test rendered method.
     */
    @Test
    public void rendered() {
        final AbstractComponentModel model = newInstance();
        model.setRendered(true);
        Assert.assertTrue(model.isRendered());
        model.setRendered(false);
        Assert.assertFalse(model.isRendered());
    }

    /**
     * Test styleClass methods.
     */
    @Test
    public void oneStyleClass() {
        final AbstractComponentModel model = newInstance();

        // no style class.
        Assert.assertNull(model.getStyleClass());

        // Add empty style class.
        model.addStyleClass(null);
        Assert.assertNull(model.getStyleClass());
        model.addStyleClass("");
        Assert.assertNull(model.getStyleClass());

        // Add style class.
        model.addStyleClass(STYLE_CLASS_FIRST);
        Assert.assertEquals(model.getStyleClass(), STYLE_CLASS_FIRST);

        // Remove another style class.
        model.removeStyleClass(STYLE_CLASS_SECOND);
        Assert.assertEquals(model.getStyleClass(), STYLE_CLASS_FIRST);

        // Add same style class.
        model.addStyleClass(STYLE_CLASS_FIRST);
        Assert.assertEquals(model.getStyleClass(), STYLE_CLASS_FIRST);

        // Add another style class.
        model.addStyleClass(STYLE_CLASS_SECOND);
        Assert.assertEquals(model.getStyleClass(), STYLE_CLASS_FIRST + " "
                + STYLE_CLASS_SECOND);

        // Add first style class.
        model.removeStyleClass(STYLE_CLASS_FIRST);
        Assert.assertEquals(model.getStyleClass(), STYLE_CLASS_SECOND);

        // Remove empty style class.
        model.removeStyleClass(null);
        Assert.assertEquals(model.getStyleClass(), STYLE_CLASS_SECOND);
        model.removeStyleClass("");
        Assert.assertEquals(model.getStyleClass(), STYLE_CLASS_SECOND);

        // Remove all style class.
        model.addStyleClass(STYLE_CLASS_FIRST);
        model.addStyleClass(STYLE_CLASS_SECOND);
        model.removeAllStyleClass();
        Assert.assertNull(model.getStyleClass());

    }
}
