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
package org.jbromo.common.test.cdi.openwebbeans;

import org.jbromo.common.test.cdi.ICdiContainer;

/**
 * Define the OpenWebBeans container access.
 * @author qjafcunuas
 */
public class OpenWebBeansCdiContainer implements ICdiContainer {

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
    // * The OpenWebBeans CDI container.
    // */
    // private ContainerLifecycle lifecycle = null;
    //
    // @Override
    // public void start() {
    // /*
    // * https://github.com/jharting/deltaspike/blob/master/deltaspike/cdictrl/
    // * impl-owb/src/main/java/org/apache/deltaspike/cdise/owb/
    // * OpenWebBeansContainerControl.java
    // */
    // this.lifecycle = WebBeansContext.currentInstance().getService(
    // ContainerLifecycle.class);
    // this.lifecycle.startApplication(null);
    // }
    //
    // @Override
    // public void stop() {
    // this.lifecycle.stopApplication(null);
    // }
    //
    // @Override
    // public Object select(final Class<?> theClass) {
    // //
    // http://programcreek.com/java-api-examples/index.php?api=org.apache.webbeans.spi.ContainerLifecycle
    //
    // // System.setProperty(JUNIT_TEST, "true");
    // final BeanManager beanManager = this.lifecycle.getBeanManager();
    // final Set<Bean<?>> testBeans = new HashSet<Bean<?>>(
    // beanManager.getBeans(theClass));
    // // filter out subclasses
    // for (final Iterator<Bean<?>> i = testBeans.iterator(); i.hasNext();) {
    // if (!i.next().getBeanClass().equals(theClass)) {
    // i.remove();
    // }
    // }
    // if (testBeans.isEmpty()) {
    // return null;
    // }
    // final Bean<?> testBean = beanManager.resolve(testBeans);
    // final CreationalContext<?> creationalContext = beanManager
    // .createCreationalContext(testBean);
    // return beanManager.getReference(testBean, theClass, creationalContext);
    // }
    //
    // @Override
    // public boolean isAvailable() {
    // final Class<WebBeansContext> class1 = WebBeansContext.class;
    // return true;
    // }

}
