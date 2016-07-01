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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Define Boolean Utility.
 * 
 * @author qjafcunuas
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BooleanUtil {

	/**
	 * All possible values list.
	 */
	public static final List<Boolean> ALL = Collections.unmodifiableList(ListUtil.toList(Boolean.FALSE, Boolean.TRUE));

	/**
	 * Empty list.
	 */
	public static final List<Boolean> EMPTY = Collections.unmodifiableList(new ArrayList<Boolean>());

	/**
	 * Return true if Boolean is not null and true.
	 * 
	 * @param value
	 *            the Boolean value
	 * @return true/false.
	 */
	public static boolean isTrue(final Boolean value) {
		return Boolean.TRUE.equals(value);
	}

	/**
	 * Return true if Boolean is not null and false.
	 * 
	 * @param value
	 *            the Boolean value
	 * @return true/false.
	 */
	public static boolean isFalse(final Boolean value) {
		return Boolean.FALSE.equals(value);
	}

	/**
	 * Return true if Boolean is null.
	 * 
	 * @param value
	 *            the Boolean value
	 * @return true/false.
	 */
	public static boolean isNull(final Boolean value) {
		return value == null;
	}

	/**
	 * Return true if Boolean is null or true.
	 * 
	 * @param value
	 *            the Boolean value
	 * @return true/false.
	 */
	public static boolean isNullOrTrue(final Boolean value) {
		return isNull(value) || value.booleanValue();
	}

	/**
	 * Return true if Boolean is null or false.
	 * 
	 * @param value
	 *            the Boolean value
	 * @return true/false.
	 */
	public static boolean isNullOrFalse(final Boolean value) {
		return isNull(value) || !value.booleanValue();
	}

}
