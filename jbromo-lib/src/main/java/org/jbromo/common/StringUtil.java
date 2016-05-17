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

import java.security.MessageDigest;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Define String Utility.
 * @author qjafcunuas
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtil {

    /**
     * Empty String.
     */
    public static final String EMPTY = "";

    /**
     * Space.
     */
    public static final String SPACE = " ";

    /**
     * Dot.
     */
    public static final String DOT = ".";

    /**
     * The ? char.
     */
    public static final String QUESTION_MARK = "?";

    /**
     * The % char.
     */
    public static final String PERCENT = "%";

    /**
     * Star.
     */
    public static final String STAR = "*";

    /**
     * Slash.
     */
    public static final String SLASH = "/";

    /**
     * Define true value.
     */
    public static final String TRUE = "1";

    /**
     * Define false value.
     */
    public static final String FALSE = "0";

    /**
     * (.
     */
    public static final String PARENTHESIS_OPEN = "(";

    /**
     * ).
     */
    public static final String PARENTHESIS_CLOSE = ")";

    /**
     * Equals.
     */
    public static final String EQUALS = "=";

    /**
     * Superior.
     */
    public static final String SUPERIOR = ">";

    /**
     * Superior or equals.
     */
    public static final String SUPERIOR_OR_EQUALS = ">=";

    /**
     * Inferior.
     */
    public static final String INFERIOR = "<";

    /**
     * Inferior or equals.
     */
    public static final String INFERIOR_OR_EQUALS = "<=";

    /**
     * Comma.
     */
    public static final String COMMA = ",";

    /**
     * Define the max size for persisted crypted password.
     */
    public static final int PASSWORD_SIZE = IntegerUtil.INT_40;

    /**
     * Return same string with first char in uppercase.
     * @param value the value.
     * @return the capitalizing value.
     */
    public static String capitalize(final String value) {
        return StringUtils.capitalize(value);
    }

    /**
     * Return same string with all words in uppercase.
     * @param value the value.
     * @param separator the separator to used for splitting string and capitalizing all words.
     * @return the capitalizing value.
     */
    public static String capitalize(final String value, final char... separator) {
        return WordUtils.capitalize(value, separator);
    }

    /**
     * Return true if string is null or empty.
     * @param value the value to check.
     * @return true/false.
     */
    public static boolean isEmpty(final String value) {
        return value == null || value.length() == 0;
    }

    /**
     * Return true if string is not null and not empty.
     * @param value the value to check.
     * @return true/false.
     */
    public static boolean isNotEmpty(final String value) {
        return !isEmpty(value);
    }

    /**
     * Performs a SHA encryption process on the incoming string parameter.
     * @param value the value to encrypt.
     * @return SHA-encrypted string if successful, or null if there are problems.
     */
    public static String encrypt(final String value) {
        if (value == null) {
            return null;
        }
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.reset();
            md.update(value.getBytes("UTF-8"));
            return toHexString(md.digest());
        } catch (final Exception e) {
            log.error("Cannot encrypt value", e);
            return null;
        }
    }

    /**
     * Convert bytes to string.
     * @param bytes the bytes to convert.
     * @return the string.
     */
    private static String toHexString(final byte[] bytes) {
        final StringBuilder sb = new StringBuilder(bytes.length * 2);
        final int a = 0xff;
        for (int i = 0; i < bytes.length; i++) {
            final int v = bytes[i] & a;
            if (v < IntegerUtil.INT_16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * Concatenate strings. Null value are not concatenated.
     * @param values the string list to concatenate.
     * @return concatenated string
     */
    public static String concat(final String... values) {
        if (values == null || values.length == 0) {
            return null;
        }
        final StringBuilder strBlr = new StringBuilder();
        for (final String one : values) {
            if (one != null) {
                strBlr.append(one);
            }
        }
        if (strBlr.length() == 0) {
            return null;
        } else {
            return strBlr.toString();
        }
    }

    /**
     * Joins the elements of the provided collection into a single String containing the provided list of elements.
     * @param collection the collection.
     * @param separator the separator.
     * @return the string.
     */
    public static String toString(final Collection<?> collection, final String separator) {
        return StringUtils.join(collection, separator);
    }

    /**
     * Joins the elements of the provided array into a single String containing the provided list of elements.
     * @param array the array.
     * @param separator the separator.
     * @return the string.
     */
    public static String toString(final Object[] array, final String separator) {
        return StringUtils.join(array, separator);
    }

}
