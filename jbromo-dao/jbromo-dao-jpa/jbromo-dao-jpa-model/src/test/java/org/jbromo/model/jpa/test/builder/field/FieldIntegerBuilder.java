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
package org.jbromo.model.jpa.test.builder.field;

import java.lang.reflect.Field;

import org.jbromo.common.RandomUtil;
import org.jbromo.common.invocation.AnnotationUtil;
import org.jbromo.model.jpa.util.EntityUtil;

/**
 * Define a string field builder.
 *
 * @author qjafcunuas
 *
 */
public class FieldIntegerBuilder extends AbstractFieldNumberBuilder<Integer> {

    /**
     * Default constructor.
     *
     * @param fieldBuilderFactory
     *            the field builder factory to used.
     */
    FieldIntegerBuilder(final FieldBuilderFactory fieldBuilderFactory) {
        super(fieldBuilderFactory);
    }

    @Override
    protected Integer max(final Number value, final boolean valid) {
        if (valid) {
            return value.intValue();
        } else {
            return value.intValue() + 1;
        }
    }

    @Override
    protected Integer min(final Number value, final boolean valid) {
        if (valid) {
            return value.intValue();
        } else {
            return value.intValue() - 1;
        }
    }

    @Override
    public Integer nextRandom(final boolean nullable, final Field field) {
        final Long min = AnnotationUtil.getMin(field);
        final Long max = AnnotationUtil.getMin(field);
        final boolean returnNull = nullable
                && EntityUtil.isNullable(field);
        if (min == null && max == null) {
            return RandomUtil.nextInt(returnNull);
        } else if (min == null) {
            return RandomUtil.nextInt(returnNull, max.intValue());
        } else if (max == null) {
            return RandomUtil.nextInt(returnNull, min.intValue(),
                    Short.MAX_VALUE);
        } else {
            return RandomUtil.nextInt(returnNull, min.intValue(),
                    max.intValue());
        }
    }

}
