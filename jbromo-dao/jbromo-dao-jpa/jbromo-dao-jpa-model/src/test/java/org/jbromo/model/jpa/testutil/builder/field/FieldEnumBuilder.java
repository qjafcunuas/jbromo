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

import org.jbromo.common.EnumUtil;
import org.jbromo.common.ListUtil;
import org.jbromo.common.RandomUtil;
import org.jbromo.model.jpa.util.EntityUtil;

/**
 * Define a string field builder.
 * @author qjafcunuas
 */
@SuppressWarnings("rawtypes")
public class FieldEnumBuilder extends AbstractFieldBuilder<Enum> {

    /**
     * Default constructor.
     * @param fieldBuilderFactory the field builder factory to used.
     */
    FieldEnumBuilder(final FieldBuilderFactory fieldBuilderFactory) {
        super(fieldBuilderFactory);
    }

    @Override
    public List<ValidationValue<Enum>> getValidationErrorValues(final Field field) {
        final List<ValidationValue<Enum>> values = ListUtil.toList();
        addNull(field, values, false);
        return values;

    }

    @Override
    public List<ValidationValue<Enum>> getValidationSuccessValues(final Field field) {
        final List<ValidationValue<Enum>> values = ListUtil.toList();
        final Enum<?>[] enums = EnumUtil.getConstants(field.getType());
        if (enums.length > 0) {
            values.add(new ValidationValue<Enum>(field, "no", enums[0]));
        }
        addNull(field, values, true);
        return values;
    }

    /**
     * Return all possible enum values.
     * @param field the field.
     * @return the values.
     */
    private Enum[] getValues(final Field field) {
        return EnumUtil.getConstants(field.getType());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Enum<?> nextRandom(final boolean nullable, final Field field) {
        final boolean returnNull = nullable && EntityUtil.isNullable(field);
        return RandomUtil.nextEnum(returnNull, getValues(field));
    }
}
