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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.UtilityClass;

/**
 * Define Calendar utility.
 * @author qjafcunuas
 */
@UtilityClass
public final class CalendarUtil {

    /**
     * Define constant for second.
     * @author qjafcunuas
     */
    public static final class SECOND {
        /**
         * In milisecond.
         */
        public static final Integer IN_MILLISECOND = IntegerUtil.INT_1000;

        /**
         * Default constructor.
         */
        private SECOND() {
            super();
        }

    }

    /**
     * Define constant for minute.
     * @author qjafcunuas
     */
    public static final class MINUTE {

        /**
         * In second.
         */
        public static final Integer IN_SECOND = IntegerUtil.INT_60;

        /**
         * In milisecond.
         */
        public static final Integer IN_MILLISECOND = MINUTE.IN_SECOND * SECOND.IN_MILLISECOND;

        /**
         * Default constructor.
         */
        private MINUTE() {
            super();
        }
    }

    /**
     * Define constant for hour.
     * @author qjafcunuas
     */
    public static final class HOUR {

        /**
         * In minute.
         */
        public static final Integer IN_MINUTE = IntegerUtil.INT_60;

        /**
         * In second.
         */
        public static final Integer IN_SECOND = HOUR.IN_MINUTE * IntegerUtil.INT_60;

        /**
         * In milisecond.
         */
        public static final Integer IN_MILLISECOND = HOUR.IN_SECOND * SECOND.IN_MILLISECOND;

        /**
         * Default constructor.
         */
        private HOUR() {
            super();
        }
    }

    /**
     * Define constant for day.
     * @author qjafcunuas
     */
    public static final class DAY {

        /**
         * In hour.
         */
        public static final Integer IN_HOUR = IntegerUtil.INT_24;

        /**
         * In minute.
         */
        public static final Integer IN_MINUTE = DAY.IN_HOUR * IntegerUtil.INT_60;

        /**
         * In second.
         */
        public static final Integer IN_SECOND = DAY.IN_MINUTE * IntegerUtil.INT_60;

        /**
         * In milisecond.
         */
        public static final Integer IN_MILLISECOND = DAY.IN_SECOND * SECOND.IN_MILLISECOND;

        /**
         * Default constructor.
         */
        private DAY() {
            super();
        }
    }

    /**
     * The string format.
     * @author qjafcunuas
     */
    public enum FORMAT {
        /** Ex: 20130701. */
        YYYYMMDD("yyyyMMdd"),

        /** Ex: 120500. */
        HHMMSS("hhmmss"),

        /** Ex: 20130701120000. */
        YYYYMMDDHHMMSS("yyyyMMddhhmmss");

        /**
         * The data formatter.
         */
        @Getter(AccessLevel.PRIVATE)
        private final DateFormat formatter;

        /**
         * Default constructor.
         * @param pattern the pattern to use.
         */
        private FORMAT(final String pattern) {
            this.formatter = new SimpleDateFormat(pattern);
        }

        /**
         * Format a calendar to a string, for the corresponfing pattern.
         * @param cal the calendar to format.
         * @return the string result.
         */
        private String toString(final Calendar cal) {
            return getFormatter().format(cal.getTime());
        }

    }

    /**
     * Return true if a class extends Calendar.
     * @param clazz the class
     * @return true if extends Calendar, false otherwise
     */
    public static boolean isCalendar(final Class<?> clazz) {
        return Calendar.class.isAssignableFrom(clazz);
    }

    /**
     * Set time to 00:00:00.000 hour.
     * @param cal the calendar to set.
     */
    public static void setZeroHour(final Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        setZeroMinute(cal);
    }

    /**
     * Set time to 00:00.000 minute.
     * @param cal the calendar to set.
     */
    public static void setZeroMinute(final Calendar cal) {
        cal.set(Calendar.MINUTE, 0);
        setZeroSecond(cal);
    }

    /**
     * Set time to 00.000 second.
     * @param cal the calendar to set.
     */
    public static void setZeroSecond(final Calendar cal) {
        cal.set(Calendar.SECOND, 0);
        setZeroMillisecond(cal);
    }

    /**
     * Set time to 0 millisecond.
     * @param cal the calendar to set.
     */
    public static void setZeroMillisecond(final Calendar cal) {
        cal.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Return the string date.
     * @param cal the calendar.
     * @param format the format to use.
     * @return the string/
     */
    public static String toString(final Calendar cal, final FORMAT format) {
        return format.toString(cal);
    }

}
