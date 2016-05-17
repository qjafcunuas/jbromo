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
package org.jbromo.common.test.cdi.deltaspike;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.jbromo.common.test.cdi.ICdiContainer;

/**
 * Define the DeltaSpike CDI container.
 * @author qjafcunuas
 */
public class DeltaSpikeCdiContainer implements ICdiContainer {

    /**
     * The DeltaSpike CDI container.
     */
    private CdiContainer container;

    @Override
    public void start() {
        this.container = CdiContainerLoader.getCdiContainer();
        this.container.boot();
    }

    @Override
    public void postStart() {
        final ContextControl contextControl = this.container.getContextControl();
        contextControl.startContext(ApplicationScoped.class);
        contextControl.startContext(SessionScoped.class);
        contextControl.startContext(RequestScoped.class);
    }

    @Override
    public void preStop() {
        final ContextControl contextControl = this.container.getContextControl();
        contextControl.stopContext(RequestScoped.class);
        contextControl.stopContext(SessionScoped.class);
        contextControl.stopContext(ApplicationScoped.class);
    }

    @Override
    public void stop() {
        if (this.container != null) {
            this.container.shutdown();
        }
    }

    @Override
    public <T> T select(final Class<T> theClass) {
        return BeanProvider.getContextualReference(theClass);
    }

}
