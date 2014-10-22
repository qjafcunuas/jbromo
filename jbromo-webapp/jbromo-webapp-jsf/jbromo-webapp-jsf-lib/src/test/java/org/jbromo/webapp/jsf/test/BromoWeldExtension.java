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
package org.jbromo.webapp.jsf.test;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;

import org.jboss.weld.context.AbstractBoundContext;
import org.jboss.weld.context.bound.MutableBoundRequest;
import org.jboss.weld.manager.BeanManagerImpl;

public class BromoWeldExtension implements Extension {

    public static final Map<String, Object> requestMap = new HashMap<String, Object>();

    public void afterDeployment(
            @Observes final AfterDeploymentValidation event,
            final BeanManager beanManager) {

        final Map<String, Object> sessionMap = new HashMap<String, Object>();
        activateContext(beanManager, SessionScoped.class, sessionMap);


        activateContext(beanManager, RequestScoped.class, BromoWeldExtension.requestMap);

        activateContext(beanManager, ConversationScoped.class,
                new MutableBoundRequest(BromoWeldExtension.requestMap, sessionMap));

        // setContextActive(beanManager, SessionScoped.class);
        // setContextActive(beanManager, RequestScoped.class);
    }

    private void setContextActive(final BeanManager beanManager,
            final Class<? extends Annotation> cls) {
        // final Context context = beanManager
        // .getContext(cls);
        // context.setBeanStore(new HashMapBeanStore());
        // context.setActive(true);
    }

    /**
     * Activates a context for a given manager.
     *
     * @param beanManager
     *            in which the context is activated
     * @param cls
     *            the class that represents the scope
     * @param storage
     *            in which to put the scoped values
     * @param <S>
     *            the type of the storage
     */
    private <S> void activateContext(final BeanManager beanManager,
            final Class<? extends Annotation> cls, final S storage) {
        final BeanManagerImpl beanManagerImpl = (BeanManagerImpl) beanManager;
        @SuppressWarnings("unchecked")
        final AbstractBoundContext<S> context = (AbstractBoundContext<S>) beanManagerImpl
                .getContext(cls);

        context.associate(storage);
        context.activate();
    }

}
