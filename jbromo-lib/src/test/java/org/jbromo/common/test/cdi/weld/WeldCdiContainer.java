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
package org.jbromo.common.test.cdi.weld;

import org.jbromo.common.test.cdi.ICdiContainer;

/**
 * Define the Weld container access.
 * @author qjafcunuas
 */
public class WeldCdiContainer implements ICdiContainer {

    @Override
    public void start() {
        // TODO Auto-generated method stub

    }

    @Override
    public void postStart() {
        // TODO Auto-generated method stub

    }

    @Override
    public void preStop() {
        // TODO Auto-generated method stub

    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }

    @Override
    public <T> T select(final Class<T> theClass) {
        // TODO Auto-generated method stub
        return null;
    }

    // /**
    // * The weld object.
    // */
    // private Weld weld;
    // /**
    // * The weld container.
    // */
    // private WeldContainer container;
    //
    // @Override
    // public void start() {
    // this.weld = new Weld();
    // this.container = this.weld.initialize();
    // this.container.getBeanManager().fireEvent(new CdiStartedEvent());
    // }
    //
    // @Override
    // public void postStart() {
    // }
    //
    // @Override
    // public void preStop() {
    // }
    //
    // @Override
    // public void stop() {
    // try {
    // this.container.getBeanManager().fireEvent(new CdiShutdownEvent());
    // this.weld.shutdown();
    // } catch (final Exception e) {
    // log.error("Cannot stop Weld Container", e);
    // }
    // }
    //
    // @Override
    // public <T> T select(final Class<T> theClass) {
    // return this.container.instance().select(theClass).get();
    // }

}
