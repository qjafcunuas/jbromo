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
package org.jbromo.model.jpa.testutil.builder.field;

import java.lang.reflect.Field;
import java.util.List;

import org.jbromo.common.ListUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.model.jpa.util.EntityUtil;

/**
 * Define a string field builder.
 * @author qjafcunuas
 */
public class FieldBooleanBuilder extends AbstractFieldBuilder<Boolean> {

    /**
     * Default constructor.
     * @param fieldBuilderFactory the field builder factory to used.
     */
    FieldBooleanBuilder(final FieldBuilderFactory fieldBuilderFactory) {
        super(fieldBuilderFactory);
    }

    @Override
    public List<ValidationValue<Boolean>> getValidationErrorValues(final Field field) {
        final List<ValidationValue<Boolean>> values = ListUtil.toList();
        addNull(field, values, false);
        return values;
    }

    @Override
    public List<ValidationValue<Boolean>> getValidationSuccessValues(final Field field) {
        final List<ValidationValue<Boolean>> values = ListUtil.toList();
        addNull(field, values, true);
        values.add(new ValidationValue<Boolean>(field, "no", RandomUtil.nextBoolean()));
        return values;
    }

    @Override
    public Boolean nextRandom(final boolean nullable, final Field field) {
        final boolean returnNull = nullable && EntityUtil.isNullable(field);
        return RandomUtil.nextBoolean(returnNull);
    }
}
