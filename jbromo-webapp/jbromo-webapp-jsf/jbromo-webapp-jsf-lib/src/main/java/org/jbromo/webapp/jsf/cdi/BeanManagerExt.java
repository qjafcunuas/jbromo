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
package org.jbromo.webapp.jsf.cdi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.InterceptionType;
import javax.enterprise.inject.spi.Interceptor;
import javax.enterprise.inject.spi.ObserverMethod;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import org.jbromo.webapp.jsf.component.wizard.AbstractWizardItemEvent;
import org.jbromo.webapp.jsf.view.RenderViewEvent;

/**
 * Encapsulate BeanManager.
 *
 * @author qjafcunuas
 *
 */
@AllArgsConstructor
public class BeanManagerExt implements BeanManager {

    /**
     * The current BeanManager.
     */
    @Getter(AccessLevel.NONE)
    private final BeanManager beanManager;

    @Override
    public <T> AnnotatedType<T> createAnnotatedType(final Class<T> arg0) {
        return this.beanManager.createAnnotatedType(arg0);
    }

    @Override
    public <T> CreationalContext<T> createCreationalContext(
            final Contextual<T> arg0) {
        return this.beanManager.createCreationalContext(arg0);
    }

    @Override
    public <T> InjectionTarget<T> createInjectionTarget(
            final AnnotatedType<T> arg0) {
        return this.beanManager.createInjectionTarget(arg0);
    }

    @Override
    public void fireEvent(final Object event, final Annotation... annotations) {
        if (RenderViewEvent.class.isInstance(event)) {
            fireEvent((RenderViewEvent) event);
        } else if (AbstractWizardItemEvent.class.isInstance(event)) {
            fireEvent((AbstractWizardItemEvent<?>) event);
        } else {
            this.beanManager.fireEvent(event, annotations);
        }
    }

    /**
     * Fire renderView event.
     *
     * @param event
     *            the event to fire.
     */
    public void fireEvent(final RenderViewEvent event) {
        this.beanManager.fireEvent(event, event.getView().getRenderViewAnnotation());
    }

    /**
     * Fire wizard item event.
     *
     * @param event
     *            the event to fire.
     */
    public void fireEvent(final AbstractWizardItemEvent<?> event) {
        this.beanManager.fireEvent(event, event.getWizard()
                .getRenderViewAnnotation());
    }

    @Override
    public Set<Bean<?>> getBeans(final String arg0) {
        return this.beanManager.getBeans(arg0);
    }

    @Override
    public Set<Bean<?>> getBeans(final Type arg0, final Annotation... arg1) {
        return this.beanManager.getBeans(arg0, arg1);
    }

    @Override
    public Context getContext(final Class<? extends Annotation> arg0) {
        return this.beanManager.getContext(arg0);
    }

    @Override
    public ELResolver getELResolver() {
        return this.beanManager.getELResolver();
    }

    @Override
    public Object getInjectableReference(final InjectionPoint arg0,
            final CreationalContext<?> arg1) {
        return this.beanManager.getInjectableReference(arg0, arg1);
    }

    @Override
    public Set<Annotation> getInterceptorBindingDefinition(
            final Class<? extends Annotation> arg0) {
        return this.beanManager.getInterceptorBindingDefinition(arg0);
    }

    @Override
    public Bean<?> getPassivationCapableBean(final String arg0) {
        return this.beanManager.getPassivationCapableBean(arg0);
    }

    @Override
    public Object getReference(final Bean<?> arg0, final Type arg1,
            final CreationalContext<?> arg2) {
        return this.beanManager.getReference(arg0, arg1, arg2);
    }

    @Override
    public Set<Annotation> getStereotypeDefinition(
            final Class<? extends Annotation> arg0) {
        return this.beanManager.getStereotypeDefinition(arg0);
    }

    @Override
    public boolean isInterceptorBinding(final Class<? extends Annotation> arg0) {
        return this.beanManager.isInterceptorBinding(arg0);
    }

    @Override
    public boolean isNormalScope(final Class<? extends Annotation> arg0) {
        return this.beanManager.isNormalScope(arg0);
    }

    @Override
    public boolean isPassivatingScope(final Class<? extends Annotation> arg0) {
        return this.beanManager.isPassivatingScope(arg0);
    }

    @Override
    public boolean isQualifier(final Class<? extends Annotation> arg0) {
        return this.beanManager.isQualifier(arg0);
    }

    @Override
    public boolean isScope(final Class<? extends Annotation> arg0) {
        return this.beanManager.isScope(arg0);
    }

    @Override
    public boolean isStereotype(final Class<? extends Annotation> arg0) {
        return this.beanManager.isStereotype(arg0);
    }

    @Override
    public <X> Bean<? extends X> resolve(final Set<Bean<? extends X>> arg0) {
        return this.beanManager.resolve(arg0);
    }

    @Override
    public List<Decorator<?>> resolveDecorators(final Set<Type> arg0,
            final Annotation... arg1) {
        return this.beanManager.resolveDecorators(arg0, arg1);
    }

    @Override
    public List<Interceptor<?>> resolveInterceptors(
            final InterceptionType arg0, final Annotation... arg1) {
        return this.beanManager.resolveInterceptors(arg0, arg1);
    }

    @Override
    public <T> Set<ObserverMethod<? super T>> resolveObserverMethods(
            final T arg0, final Annotation... arg1) {
        return this.beanManager.resolveObserverMethods(arg0, arg1);
    }

    @Override
    public void validate(final InjectionPoint arg0) {
        this.beanManager.validate(arg0);
    }

    @Override
    public ExpressionFactory wrapExpressionFactory(final ExpressionFactory arg0) {
        return this.beanManager.wrapExpressionFactory(arg0);
    }

}
