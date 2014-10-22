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
package org.jbromo.webapp.jsf.faces.mojarra;

import java.beans.FeatureDescriptor;
import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.ValueExpression;
import javax.faces.el.CompositeComponentExpressionHolder;

import org.jbromo.common.ClassUtil;
import org.jbromo.common.ObjectUtil;

/**
 * On Mojarra 2.1.19, FacesConverter with forClass attribute doesn't work on
 * composite component.
 *
 * This EL Resolver will come into play when we detect a <code>base</code> of
 * type <code>CompositeComponentExpressionHolder</code> and it will resolve the
 * value type properly.
 *
 * cf. http://java.net/jira/browse/JAVASERVERFACES-2568
 *
 * Remove this class once the bug is fixed.
 *
 * @author Val Blant
 */
public class CompositeComponentExpressionHolderELResolver extends ELResolver {
    @Override
    public Object getValue(final ELContext elContext, final Object base,
            final Object property) {
        return null;
    }

    @Override
    public Class<?> getType(final ELContext elContext, final Object base,
            final Object property) {
        Class<?> clazz = null;

        if (ClassUtil
                .isInstance(base, CompositeComponentExpressionHolder.class)) {
            final CompositeComponentExpressionHolder eHolder = ObjectUtil.cast(
                    base, CompositeComponentExpressionHolder.class);
            final ValueExpression ve = eHolder.getExpression(property
                    .toString());
            if (ve != null) {
                clazz = ve.getType(elContext);

                if (clazz != null) {
                    elContext.setPropertyResolved(true);
                }
            }
        }

        return clazz;
    }

    @Override
    public void setValue(final ELContext elContext, final Object base,
            final Object property, final Object value) {
    }

    @Override
    public Class<?> getCommonPropertyType(final ELContext elcontext,
            final Object obj) {
        return Object.class;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(
            final ELContext elcontext, final Object obj) {
        return null;
    }

    @Override
    public boolean isReadOnly(final ELContext elcontext, final Object obj,
            final Object obj1) {
        return true;
    }

}
