/*
 * Copyright (C) 2013-2014 The JBromo Authors.
import java.lang.reflect.Field;

import org.jbromo.common.RandomUtil;
import org.jbromo.common.invocation.AnnotationUtil;
import org.jbromo.model.jpa.util.EntityUtil;
 the Software without restriction, including without limitation the rights
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
public class FieldShortBuilder extends AbstractFieldNumberBuilder<Short> {

    /**
     * Default constructor.
     *
     * @param fieldBuilderFactory
     *            the field builder factory to used.
     */
    FieldShortBuilder(final FieldBuilderFactory fieldBuilderFactory) {
        super(fieldBuilderFactory);
    }

    @Override
    protected Short max(final Number value, final boolean valid) {
        if (valid) {
            return value.shortValue();
        } else {
            return (short) (value.shortValue() + 1);
        }
    }

    @Override
    protected Short min(final Number value, final boolean valid) {
        if (valid) {
            return value.shortValue();
        } else {
            return (short) (value.shortValue() - 1);
        }
    }

    @Override
    public Short nextRandom(final boolean nullable, final Field field) {
        final Long min = AnnotationUtil.getMin(field);
        final Long max = AnnotationUtil.getMin(field);
        final boolean returnNull = nullable
                && EntityUtil.isNullable(field);
        if (min == null && max == null) {
            return RandomUtil.nextShort(returnNull);
        } else if (min == null) {
            return RandomUtil.nextShort(returnNull, max.shortValue());
        } else if (max == null) {
            return RandomUtil.nextShort(returnNull, min.shortValue(),
                    Short.MAX_VALUE);
        } else {
            return RandomUtil.nextShort(returnNull, min.shortValue(),
                    max.shortValue());
        }
    }

}
