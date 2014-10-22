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
package org.jbromo.webapp.jsf.test.mock.faces;

import javax.el.ELContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.mockito.Mockito;

/**
 * Define the mock of FacesContext.
 *
 * @author qjafcunuas
 *
 */
public final class MockFacesContext {

    /**
     * Default constructor.
     */
    private MockFacesContext() {
        super();
    }

    /**
     * Mock the external context.
     *
     * @return the context.
     */
    public static FacesContext mock() {
        // Faces Context.
        final FacesContext context = Mockito.mock(FacesContext.class);

        // External context.
        final ExternalContext externalContext = MockExternalContext.mock();
        Mockito.when(context.getExternalContext()).thenReturn(externalContext);

        // EL context.
        final ELContext elContext = MockELContext.mock();
        Mockito.when(context.getELContext()).thenReturn(elContext);

        return context;
    }
}
