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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Random utility.
 * @author qjafcunuas
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RandomUtil {

    /**
     * Define email size min.
     */
    public static final Integer EMAIL_SIZE_MIN = 7;

    /** Randomizer. */
    private static final Random RANDOMIZER = new Random();

    /**
     * Define possible char.
     */
    private static final StringBuilder SYMBOLS = new StringBuilder();

    /**
     * Define possible char.
     */
    private static final StringBuilder SYMBOLS_LATIN = new StringBuilder();

    /** List of arabe digit. */
    private static final char[] ARAB = {'\u0635', '\u0628', '\u0627', '\u062D', '\u0020', '\u0627', '\u0644', '\u062E', '\u064A', '\u0631'};

    /** List of chinese digit. */
    private static final char[] CHINESE = {'\u4F60', '\u597D', '\u5E03', '\u9B6F', '\u8AFE', '\u6211', '\u559C', '\u6B61', '\u72D7'};

    /** List of russe digit. */
    private static final char[] RUSSE = {'\u041F', '\u0440', '\u0438', '\u0432', '\u0435', '\u0442', '\u0020', '\u0424', '\u043B', '\u043E', '\u0440',
                                         '\u0430', '\u043D'};

    /** List of thai digit. */
    private static final char[] THAI = {'\u0E2A', '\u0E27', '\u0E31', '\u0E2A', '\u0E14', '\u0E35', '\u0E0A', '\u0E35', '\u0E27', '\u0E27', '\u0E34',
                                        '\u0E17', '\u0E22', '\u0E32'};

    static {
        for (final char element : ARAB) {
            SYMBOLS.append(element);
        }
        for (final char element : CHINESE) {
            SYMBOLS.append(element);
        }
        for (final char element : RUSSE) {
            SYMBOLS.append(element);
        }
        for (final char element : THAI) {
            SYMBOLS.append(element);
        }
        for (char idx = '0'; idx <= '9'; idx++) {
            SYMBOLS.append(idx);
            SYMBOLS_LATIN.append(idx);
        }
        for (char idx = 'a'; idx <= 'z'; idx++) {
            SYMBOLS.append(idx);
            SYMBOLS_LATIN.append(idx);
        }
        for (char idx = 'A'; idx <= 'Z'; idx++) {
            SYMBOLS.append(idx);
            SYMBOLS_LATIN.append(idx);
        }
        SYMBOLS.append("                      ");
    }

    /**
     * Returns next long random value.
     * @return next long random value
     */
    public static long nextLong() {
        return RANDOMIZER.nextLong();
    }

    /**
     * Returns next long random value. Returned value can be null.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @return next long random value
     */
    public static Long nextLong(final boolean nullable) {
        if (isNull(nullable)) {
            return null;
        }
        return nextLong();
    }

    /**
     * Returns a next long random value, uniformly distributed value between 0 (inclusive) and the specified value (exclusive), drawn from this random
     * number generator's sequence.
     * @param max max return value.
     * @return next long random value
     */
    public static long nextLong(final long max) {
        return (long) (nextDouble() * max);
    }

    /**
     * Returns a next long random value, uniformly distributed value between 0 (inclusive) and the specified value (exclusive), drawn from this random
     * number generator's sequence. Retruned value can be null.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @param max max return value.
     * @return next long random value
     */
    public static Long nextLong(final boolean nullable, final long max) {
        if (isNull(nullable)) {
            return null;
        }
        return nextLong(max);
    }

    /**
     * Returns a next long random value, uniformly distributed value between specified min value (inclusive) and the specified max value (exclusive),
     * drawn from this random number generator's sequence.
     * @param min min return value.
     * @param max max return value.
     * @return next long random value
     */
    public static long nextLong(final long min, final long max) {
        return min + nextLong(max - min);
    }

    /**
     * Returns a next long random value, uniformly distributed value between specified min value (inclusive) and the specified max value (exclusive),
     * drawn from this random number generator's sequence.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @param min min return value.
     * @param max max return value.
     * @return next long random value
     */
    public static Long nextLong(final boolean nullable, final long min, final long max) {
        if (isNull(nullable)) {
            return null;
        }
        return nextLong(min, max);
    }

    /**
     * Returns a next long random value, uniformly distributed value between specified min value (inclusive) and the specified max value (exclusive),
     * drawn from this random number generator's sequence.
     * @param min min return value.
     * @param max max return value.
     * @return next long random value
     */
    public static Long nextLong(final Long min, final Long max) {
        if (min == null && max == null) {
            return nextLong();
        } else if (max == null) {
            return nextLong(min.longValue(), Long.MAX_VALUE);
        } else if (min == null) {
            return nextLong(max.longValue());
        } else {
            return nextLong(min.longValue(), max.longValue());
        }
    }

    /**
     * Returns a next long random value, uniformly distributed value between specified min value (inclusive) and the specified max value (exclusive),
     * drawn from this random number generator's sequence.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @param min min return value.
     * @param max max return value.
     * @return next long random value
     */
    public static Long nextLong(final boolean nullable, final Long min, final Long max) {
        if (isNull(nullable)) {
            return null;
        }
        return nextLong(min, max);
    }

    /**
     * Returns next Double random value.
     * @return next Double random value
     */
    public static double nextDouble() {
        return RANDOMIZER.nextDouble();
    }

    /**
     * Returns next Double random value.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @return next Double random value
     */
    public static Double nextDouble(final boolean nullable) {
        if (isNull(nullable)) {
            return null;
        }
        return nextDouble();
    }

    /**
     * Returns next int random value.
     * @return next int random value
     */
    public static int nextInt() {
        return RANDOMIZER.nextInt();
    }

    /**
     * Returns next Integer random value. Returned value can be null.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @return next Integer random value
     */
    public static Integer nextInt(final boolean nullable) {
        if (isNull(nullable)) {
            return null;
        }
        return nextInt();
    }

    /**
     * Returns next int random value.
     * @param max the max value.
     * @return next int random value
     */
    public static int nextInt(final int max) {
        if (max <= 0) {
            return 0;
        }
        return RANDOMIZER.nextInt(max);
    }

    /**
     * Returns next int random value. Returned value can be null.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @param max the max value.
     * @return next int random value
     */
    public static Integer nextInt(final boolean nullable, final int max) {
        if (isNull(nullable)) {
            return null;
        }
        return nextInt(max);
    }

    /**
     * Returns next int random value.
     * @param min the min value.
     * @param max the max value.
     * @return next int random value
     */
    public static int nextInt(final int min, final int max) {
        if (max <= 0) {
            return 0;
        }
        if (max == min) {
            return min;
        }
        return min + RANDOMIZER.nextInt(max - min);
    }

    /**
     * Returns next int random value. Returned value can be null.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @param min the min value.
     * @param max the max value.
     * @return next int random value
     */
    public static Integer nextInt(final boolean nullable, final int min, final int max) {
        if (isNull(nullable)) {
            return null;
        }
        return nextInt(min, max);
    }

    /**
     * Return true if next value can be null.
     * @param nullable if false, return false.
     * @return true/false.
     */
    public static boolean isNull(final boolean nullable) {
        if (nullable) {
            final short val = nextShort((short) IntegerUtil.INT_10);
            return val == 0;
        } else {
            return false;
        }
    }

    /**
     * Returns next short random value.
     * @return next short random value
     */
    public static short nextShort() {
        return (short) nextInt(Short.MAX_VALUE);
    }

    /**
     * Returns next short random value.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @return next short random value
     */
    public static Short nextShort(final boolean nullable) {
        if (isNull(nullable)) {
            return null;
        }
        return nextShort();
    }

    /**
     * Returns next short random value, between 0 (inclusive) and the specified value (exclusive).
     * @param max the max value.
     * @return next short random value
     */
    public static short nextShort(final short max) {
        if (max <= 0) {
            return 0;
        }
        return (short) nextInt(max);
    }

    /**
     * Returns next short random value, between 0 (inclusive) and the specified value (exclusive).
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @param max the max value.
     * @return next short random value
     */
    public static Short nextShort(final boolean nullable, final short max) {
        if (isNull(nullable)) {
            return null;
        }
        return nextShort(max);
    }

    /**
     * Returns a next short random value, uniformly distributed value between specified min value (inclusive) and the specified max value (exclusive),
     * drawn from this random number generator's sequence.
     * @param min min return value.
     * @param max max return value.
     * @return next short random value
     */
    public static short nextShort(final short min, final short max) {
        return (short) (min + nextShort((short) (max - min)));
    }

    /**
     * Returns a next Short random value, uniformly distributed value between specified min value (inclusive) and the specified max value (exclusive),
     * drawn from this random number generator's sequence.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @param min min return value.
     * @param max max return value.
     * @return next Short random value
     */
    public static Short nextShort(final boolean nullable, final short min, final short max) {
        if (isNull(nullable)) {
            return null;
        }
        return nextShort(min, max);
    }

    /**
     * Returns next bigDecimal random value.
     * @param integer integer of bigDecimal
     * @param fraction fraction of bigDecimal
     * @return next bigDecimal random value
     */
    public static BigDecimal nextFullBigDecimal(final int integer, final int fraction) {
        return new BigDecimal(nextFullBigInteger(integer + fraction), fraction);
    }

    /**
     * Returns next bigDecimal random value.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @param integer integer of bigDecimal
     * @param fraction fraction of bigDecimal
     * @return next bigDecimal random value
     */
    public static BigDecimal nextBigDecimal(final boolean nullable, final int integer, final int fraction) {
        if (isNull(nullable)) {
            return null;
        }
        // 0 to 9 integer value.
        BigInteger n = BigInteger.valueOf(nextInt(IntegerUtil.INT_0, IntegerUtil.INT_10));
        for (int i = 1; i < integer + fraction; i++) {
            n = n.multiply(BigInteger.TEN).add(BigInteger.valueOf(nextInt(IntegerUtil.INT_0, IntegerUtil.INT_10)));
        }
        return new BigDecimal(n, fraction);
    }

    /**
     * Returns next bigDecimal random value.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @param integer integer of bigDecimal
     * @param fraction fraction of bigDecimal
     * @param min min value of bigDecimal
     * @return next bigDecimal random value
     */
    public static BigDecimal nextBigDecimal(final boolean nullable, final int integer, final int fraction, final BigDecimal min) {
        if (isNull(nullable)) {
            return null;
        }
        BigDecimal value = nextBigDecimal(false, integer, fraction);
        while (value.compareTo(min) < 0) {
            value = value.multiply(BigDecimal.TEN);
        }
        return value;
    }

    /**
     * Returns next bigDecimal random value.
     * @param integer integer of bigDecimal
     * @param fraction fraction of bigDecimal
     * @param min min value of bigDecimal
     * @param max max value of bigDecimal bigDecimal random value
     * @return random value.
     */
    public static BigDecimal nextBigDecimal(final int integer, final int fraction, final BigDecimal min, final BigDecimal max) {
        BigDecimal value = nextBigDecimal(false, integer, fraction);
        final int scale = value.scale();
        while (min != null && value.compareTo(min) < 0) {
            value = value.multiply(BigDecimal.TEN);
        }
        while (max != null && value.compareTo(max) > 0) {
            value = value.divide(BigDecimal.TEN);
        }
        while (min != null && value.compareTo(min) < 0) {
            value = value.multiply(BigDecimal.TEN);
        }
        return value.setScale(scale, RoundingMode.HALF_DOWN);
    }

    /**
     * Returns next bigDecimal random value.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @param integer integer of bigDecimal
     * @param fraction fraction of bigDecimal
     * @param min min value of bigDecimal
     * @param max max value of bigDecimal bigDecimal random value
     * @return random value.
     */
    public static BigDecimal nextBigDecimal(final boolean nullable, final int integer, final int fraction, final BigDecimal min,
            final BigDecimal max) {
        if (isNull(nullable)) {
            return null;
        }
        return nextBigDecimal(integer, fraction, min, max);
    }

    /**
     * Returns next bigDecimal random value with a min value.
     * @param integer integer of bigDecimal
     * @param fraction fraction of bigDecimal
     * @param min with a min value
     * @return next bigDecimal random value
     */
    public static BigDecimal nextFullBigDecimal(final int integer, final int fraction, final BigDecimal min) {
        final BigDecimal next = nextFullBigDecimal(integer, fraction);
        if (min == null) {
            return next;
        } else if (next.compareTo(min) >= 0) {
            return next;
        } else {
            return min.add(nextBigDecimal(false, integer - 1, fraction));
        }
    }

    /**
     * Returns next BigInteger random value.
     * @param integer integer of BigInteger
     * @return next BigInteger random value
     */
    public static BigInteger nextFullBigInteger(final int integer) {
        // Don't want 0 for first left number (1 to 9).
        BigInteger n = BigInteger.valueOf(nextInt(IntegerUtil.INT_1, IntegerUtil.INT_9));
        for (int i = 1; i < integer; i++) {
            n = n.multiply(BigInteger.TEN).add(BigInteger.valueOf(nextInt(IntegerUtil.INT_1, IntegerUtil.INT_9)));
        }
        return n;
    }

    /**
     * Returns next boolean random value.
     * @return next boolean random value
     */
    public static boolean nextBoolean() {
        return RANDOMIZER.nextBoolean();
    }

    /**
     * Returns next not null Boolean random value.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @return next not null Boolean random value
     */
    public static Boolean nextBoolean(final boolean nullable) {
        final short rand = nextShort(nullable ? (short) IntegerUtil.INT_3 : (short) IntegerUtil.INT_2);

        Boolean random = null;
        switch (rand) {
            case 0:
                random = Boolean.TRUE;
                break;
            case 1:
                random = Boolean.FALSE;
                break;
            default:
                break;
        }
        return random;
    }

    /**
     * Returns next float random value.
     * @return next float random value
     */
    public static float nextFloat() {
        return Float.valueOf(RANDOMIZER.nextFloat());
    }

    /**
     * Returns next float random value.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @return next float random value
     */
    public static Float nextFloat(final boolean nullable) {
        if (isNull(nullable)) {
            return null;
        }
        return nextFloat();
    }

    /**
     * Returns a new random string (16 chars).
     * @return random string
     */
    public static String nextString() {
        return Long.toHexString(Double.doubleToLongBits(RANDOMIZER.nextDouble()));
    }

    /**
     * Returns a new random string (16 chars).
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @return random string
     */
    public static String nextString(final boolean nullable) {
        if (isNull(nullable)) {
            return null;
        }
        return nextString();
    }

    /**
     * Returns a random string with given size.
     * @param size size of string
     * @return random string.
     */
    public static String nextString(final int size) {
        return nextString(size, SYMBOLS);
    }

    /**
     * Returns a random string with given size.
     * @param min the minimum size of string
     * @param max the maximum size of string
     * @return random string.
     */
    public static String nextString(final int min, final int max) {
        final int size = nextInt(min, max);
        return nextString(size, SYMBOLS);
    }

    /**
     * Returns a random string with given size.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @param size size of string
     * @return random string.
     */
    public static String nextString(final boolean nullable, final int size) {
        if (isNull(nullable)) {
            return null;
        }
        return nextString(size);
    }

    /**
     * Returns a random string with given min/max size.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @param min the minimum size of string
     * @param max the maximum size of string
     * @return random string.
     */
    public static String nextString(final boolean nullable, final int min, final int max) {
        if (isNull(nullable)) {
            return null;
        }
        return nextString(min, max);
    }

    /**
     * Returns a random string with given min/max size.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @param min the minimum size of string (can be null).
     * @param max the maximum size of string (can be null).
     * @return random string.
     */
    public static String nextString(final boolean nullable, final Integer min, final Integer max) {
        if (isNull(nullable)) {
            return null;
        }
        return nextString(min, max);
    }

    /**
     * Returns a random string with given min/max size.
     * @param min the minimum size of string (can be null).
     * @param max the maximum size of string (can be null).
     * @return random string.
     */
    public static String nextString(final Integer min, final Integer max) {
        if (min == null && max == null) {
            return nextString();
        } else if (max == null) {
            return nextString(min.intValue(), min.intValue() + IntegerUtil.INT_32);
        } else if (min == null) {
            return nextString(max.intValue());
        } else {
            return nextString(min.intValue(), max.intValue());
        }
    }

    /**
     * Returns a random string with given size.
     * @param size size of string
     * @param symbolsList the symbol list.
     * @return random string.
     */
    private static String nextString(final int size, final StringBuilder symbolsList) {
        if (size <= 0) {
            return StringUtil.EMPTY;
        }
        final StringBuilder buffer = new StringBuilder();
        // Starting with char != ' '.
        char a;
        do {
            a = nextChar(symbolsList);
        } while (a == ' ');
        buffer.append(a);
        for (int idx = 2; idx < size; ++idx) {
            buffer.append(nextChar(symbolsList));
        }
        // Ending with char != ' '.
        if (buffer.length() < size) {
            do {
                a = nextChar(symbolsList);
            } while (a == ' ');
            buffer.append(a);
        }
        return buffer.toString();
    }

    /**
     * returns a random calendar.
     * @return random calendar.
     */
    public static Calendar nextCalendar() {
        final BigInteger bint = nextFullBigInteger(13);
        final Date date = new Date();
        date.setTime(bint.longValue());
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * returns a random calendar.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @return random calendar.
     */
    public static Calendar nextCalendar(final boolean nullable) {
        if (isNull(nullable)) {
            return null;
        }
        return nextCalendar();
    }

    /**
     * returns a random email with given size.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @param size size of string.
     * @return random email.
     */
    public static String nextEmail(final boolean nullable, final int size) {
        if (isNull(nullable)) {
            return null;
        }
        final StringBuilder buffer = new StringBuilder();
        if (size < EMAIL_SIZE_MIN) {
            // ie < a@b.fr
            buffer.append("ab@c.fr");
        } else {
            final int finalPart = 3;
            final int nbfirstPart = (size - finalPart - 1) / 2;
            final int nbSecondPart = size - finalPart - nbfirstPart - 1;
            for (int idx = 0; idx < nbfirstPart; ++idx) {
                buffer.append(nextCharLatin());
            }
            buffer.append("@");
            for (int idx = 0; idx < nbSecondPart; ++idx) {
                buffer.append(nextCharLatin());
            }
            buffer.append(".");
            for (int idx = 0; idx < finalPart - 1; ++idx) {
                buffer.append(nextCharLatin());
            }
        }
        return buffer.toString();
    }

    /**
     * Return next char.
     * @return the char.
     */
    public static char nextChar() {
        return nextChar(SYMBOLS);
    }

    /**
     * Return next latin char.
     * @return the char
     */
    public static char nextCharLatin() {
        return nextChar(SYMBOLS_LATIN);
    }

    /**
     * Return next latin char.
     * @param symbols the possible char to return.
     * @return the char.
     */
    private static char nextChar(final StringBuilder symbols) {
        return symbols.charAt(RANDOMIZER.nextInt(symbols.length()));
    }

    /**
     * Return a randoming enum value.
     * @param <T> the enum type.
     * @param values the values
     * @return the value
     */
    public static <T extends Enum<?>> T nextEnum(final T[] values) {
        return values[RandomUtil.nextInt(values.length)];
    }

    /**
     * Return a randoming enum value.
     * @param <T> the enum type.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @param values the values
     * @return the value
     */
    public static <T extends Enum<T>> T nextEnum(final boolean nullable, final T[] values) {
        if (isNull(nullable)) {
            return null;
        }
        return nextEnum(values);
    }

    /**
     * Return a randoming collection value.
     * @param <T> the collection type element.
     * @param values the values
     * @return the value
     */
    public static <T> T nextCollection(final Collection<T> values) {
        if (CollectionUtil.isEmpty(values)) {
            return null;
        }
        int random = nextInt(0, values.size());
        final Iterator<T> iter = values.iterator();
        while (random > 0) {
            iter.next();
            random--;
        }
        return iter.next();
    }

    /**
     * Return a randoming collection value.
     * @param <C> the collection type.
     * @param <T> the element type of the collection.
     * @param values the values
     * @return the value
     */
    @SuppressWarnings("unchecked")
    public static <C extends Collection<T>, T> C nextSubCollection(final C values) {
        if (values == null) {
            return null;
        }
        C col;
        col = (C) ObjectUtil.newInstance(values.getClass());
        col.addAll(values);
        final int size = nextInt(values.size());
        while (col.size() > size) {
            col.remove(nextCollection(col));
        }
        return col;
    }

    /**
     * Return a randoming collection value.
     * @param <T> the collection type element.
     * @param nullable if true, returned value can be null; else it cannot be null.
     * @param values the values
     * @return the value
     */
    public static <T> T nextCollection(final boolean nullable, final Collection<T> values) {
        if (isNull(nullable)) {
            return null;
        }
        return nextCollection(values);
    }

    /**
     * Return another random value.
     * @param <O> the object type.
     * @param value the value.
     * @return the next value.
     */
    @SuppressWarnings("unchecked")
    public static <O> O nextAnother(final O value) {
        if (value == null) {
            return null;
        }
        Object nextValue = null;
        final Class<?> type = value.getClass();
        if (ClassUtil.isBoolean(type)) {
            nextValue = !((Boolean) value);
        } else if (ClassUtil.isLong(type)) {
            nextValue = nextLong();
        } else if (ClassUtil.isInt(type)) {
            nextValue = nextInt();
        } else if (ClassUtil.isShort(type)) {
            nextValue = nextShort();
        } else if (ClassUtil.isDouble(type)) {
            nextValue = nextDouble();
        } else if (ClassUtil.isFloat(type)) {
            nextValue = nextFloat();
        } else if (type.equals(String.class)) {
            final String str = (String) value;
            if (str.isEmpty()) {
                nextValue = nextString(IntegerUtil.INT_1);
            } else {
                nextValue = nextString(str.length());
            }
        } else if (BigDecimal.class.isAssignableFrom(type)) {
            final BigDecimal big = (BigDecimal) value;
            nextValue = nextBigDecimal(false, big.precision(), big.scale());
        }

        if (value.equals(nextValue)) {
            nextValue = nextAnother(value);
        }
        return (O) nextValue;
    }

    /**
     * Return a random collection.
     * @param <C> the collection type.
     * @param <T> the object type.
     * @param empty if true, returned collection can be empty.
     * @param from the elements to add to the returned collection.
     * @return the random collection.
     */
    public static <T, C extends Collection<T>> C nextSubCollection(final boolean empty, final C from) {
        if (from == null) {
            return null;
        }
        final C to = nextSubCollection(from);
        if (!empty && to.isEmpty() && !from.isEmpty()) {
            int random = nextInt(from.size());
            final Iterator<T> iter = from.iterator();
            while (random > 0) {
                iter.next();
                random--;
            }
            to.add(iter.next());
        }
        return to;
    }

}
