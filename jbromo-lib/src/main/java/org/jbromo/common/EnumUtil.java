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
package org.jbromo.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Define Enum utility.
 * @author qjafcunuas
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EnumUtil {

    /**
     * Return a list of enum value possible.
     * @param <E> the enum type.
     * @param type the enum type
     * @return the list enum
     */
    public static <E extends Enum<E>> E[] getValues(final Class<E> type) {
        if (type == null) {
            return null;
        }
        return type.getEnumConstants();
    }

    /**
     * Return a list of enum value possible.
     * @param type the enum type
     * @return the list enum
     */
    public static <E extends Enum<E>> Enum<E>[] getConstants(final Class<E> type) {
        if (type == null) {
            return null;
        }
        return type.getEnumConstants();
    }

}
