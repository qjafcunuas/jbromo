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
package org.jbromo.common;

import java.util.Calendar;
import java.util.Date;

import lombok.Getter;

import org.jbromo.common.test.common.ConstructorUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for the Calendar Util class.
 *
 * @author qjafcunuas
 *
 */
public class CalendarUtilTest {

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        ConstructorUtil.executePrivate(CalendarUtil.class);
        ConstructorUtil.executePrivate(CalendarUtil.SECOND.class);
        ConstructorUtil.executePrivate(CalendarUtil.MINUTE.class);
        ConstructorUtil.executePrivate(CalendarUtil.HOUR.class);
        ConstructorUtil.executePrivate(CalendarUtil.DAY.class);
    }

    /**
     * Extended date.
     *
     * @author qjafcunuas
     *
     */
    @Getter
    private class ExtendedDate {
        /** The year. */
        private final int year;
        /** The month. */
        private final int month;
        /** The day. */
        private final int day;
        /** The hour. */
        private final int hour;
        /** The minute. */
        private final int minute;
        /** The second. */
        private final int second;
        /** The millisecond. */
        private final int millisecond;

        /**
         * Default constructor.
         *
         * @param cal
         *            the calendar.
         */
        public ExtendedDate(final Calendar cal) {
            this.year = cal.get(Calendar.YEAR);
            this.month = cal.get(Calendar.MONTH);
            this.day = cal.get(Calendar.DAY_OF_MONTH);
            this.hour = cal.get(Calendar.HOUR_OF_DAY);
            this.minute = cal.get(Calendar.MINUTE);
            this.second = cal.get(Calendar.SECOND);
            this.millisecond = cal.get(Calendar.MILLISECOND);
        }

    }

    /**
     * Test value.
     *
     * @param cal
     *            the calendar value to test.
     * @param year
     *            the year of the calendar must be equals to this value.
     * @param month
     *            the month of the calendar must be equals to this value.
     * @param day
     *            the day of the calendar must be equals to this value.
     * @param hour
     *            the hour of the calendar must be equals to this value.
     * @param minute
     *            the minute of the calendar must be equals to this value.
     * @param second
     *            the second of the calendar must be equals to this value.
     * @param millisecond
     *            the millisecond of the calendar must be equals to this value.
     */
    private void assertEquals(final Calendar cal, final int year,
            final int month, final int day, final int hour, final int minute,
            final int second, final int millisecond) {
        Assert.assertTrue(cal.get(Calendar.YEAR) == year);
        Assert.assertTrue(cal.get(Calendar.MONTH) == month);
        Assert.assertTrue(cal.get(Calendar.DAY_OF_MONTH) == day);
        Assert.assertTrue(cal.get(Calendar.HOUR_OF_DAY) == hour);
        Assert.assertTrue(cal.get(Calendar.MINUTE) == minute);
        Assert.assertTrue(cal.get(Calendar.SECOND) == second);
        Assert.assertTrue(cal.get(Calendar.MILLISECOND) == millisecond);
    }

    /**
     * Test isCalendar method.
     */
    @Test
    public void isCalendar() {
        final Object cal = Calendar.getInstance();
        Assert.assertTrue(CalendarUtil.isCalendar(cal.getClass()));
        final Object extendCal = new Calendar() {

            /**
             * serial version UID.
             */
            private static final long serialVersionUID = 2003461637276833640L;

            @Override
            public void add(final int arg0, final int arg1) {
            }

            @Override
            protected void computeFields() {
            }

            @Override
            protected void computeTime() {
            }

            @Override
            public int getGreatestMinimum(final int arg0) {
                return 0;
            }

            @Override
            public int getLeastMaximum(final int arg0) {
                return 0;
            }

            @Override
            public int getMaximum(final int arg0) {
                return 0;
            }

            @Override
            public int getMinimum(final int arg0) {
                return 0;
            }

            @Override
            public void roll(final int arg0, final boolean arg1) {
            }
        };
        Assert.assertTrue(CalendarUtil.isCalendar(extendCal.getClass()));
        Assert.assertFalse(CalendarUtil.isCalendar(Date.class));
        Assert.assertFalse(CalendarUtil.isCalendar(Object.class));
    }

    /**
     * Test setZeroHour method.
     */
    @Test
    public void setZeroHour() {
        final Calendar cal = Calendar.getInstance();
        final ExtendedDate date = new ExtendedDate(cal);
        CalendarUtil.setZeroHour(cal);
        assertEquals(cal, date.year, date.month, date.day, 0, 0, 0, 0);
    }

    /**
     * Test setZeroMinute method.
     */
    @Test
    public void setZeroMinute() {
        final Calendar cal = Calendar.getInstance();
        final ExtendedDate date = new ExtendedDate(cal);
        CalendarUtil.setZeroMinute(cal);
        assertEquals(cal, date.year, date.month, date.day, date.hour, 0, 0, 0);
    }

    /**
     * Test setZeroSecond method.
     */
    @Test
    public void setZeroSecond() {
        final Calendar cal = Calendar.getInstance();
        final ExtendedDate date = new ExtendedDate(cal);
        CalendarUtil.setZeroSecond(cal);
        assertEquals(cal, date.year, date.month, date.day, date.hour,
                date.minute, 0, 0);
    }

    /**
     * Test setZeroMillisecond method.
     */
    @Test
    public void setZeroMillisecond() {
        final Calendar cal = Calendar.getInstance();
        final ExtendedDate date = new ExtendedDate(cal);
        CalendarUtil.setZeroMillisecond(cal);
        assertEquals(cal, date.year, date.month, date.day, date.hour,
                date.minute, date.second, 0);
    }

    /**
     * Test field DAY value.
     */
    @Test
    public void fieldDay() {
        Assert.assertEquals(
                CalendarUtil.DAY.IN_MILLISECOND,
                Integer.valueOf(IntegerUtil.INT_24 * IntegerUtil.INT_60
                        * IntegerUtil.INT_60 * IntegerUtil.INT_1000));
        Assert.assertEquals(
                CalendarUtil.DAY.IN_SECOND,
                Integer.valueOf(IntegerUtil.INT_24 * IntegerUtil.INT_60
                        * IntegerUtil.INT_60));
        Assert.assertEquals(CalendarUtil.DAY.IN_MINUTE,
                Integer.valueOf(IntegerUtil.INT_24 * IntegerUtil.INT_60));
        Assert.assertEquals(CalendarUtil.DAY.IN_HOUR,
                Integer.valueOf(IntegerUtil.INT_24));
    }

    /**
     * Test field HOUR value.
     */
    @Test
    public void fieldHour() {
        Assert.assertEquals(
                CalendarUtil.HOUR.IN_MILLISECOND,
                Integer.valueOf(IntegerUtil.INT_60 * IntegerUtil.INT_60
                        * IntegerUtil.INT_1000));
        Assert.assertEquals(CalendarUtil.HOUR.IN_SECOND,
                Integer.valueOf(IntegerUtil.INT_60 * IntegerUtil.INT_60));
        Assert.assertEquals(CalendarUtil.HOUR.IN_MINUTE,
                Integer.valueOf(IntegerUtil.INT_60));
    }

    /**
     * Test field MINUTE value.
     */
    @Test
    public void fieldMinute() {
        Assert.assertEquals(CalendarUtil.MINUTE.IN_MILLISECOND,
                Integer.valueOf(IntegerUtil.INT_60 * IntegerUtil.INT_1000));
        Assert.assertEquals(CalendarUtil.MINUTE.IN_SECOND,
                Integer.valueOf(IntegerUtil.INT_60));
    }

    /**
     * Test field SECOND value.
     */
    @Test
    public void fieldSecond() {
        Assert.assertEquals(CalendarUtil.SECOND.IN_MILLISECOND,
                Integer.valueOf(IntegerUtil.INT_1000));
    }

    /**
     * Test toString(cal,format) method.
     */
    @Test
    public void toStringFormat() {
        // Set calendar to 2014-02-01 03:04:05
        final Calendar cal = Calendar.getInstance();
        cal.set(IntegerUtil.INT_2000, IntegerUtil.INT_1, IntegerUtil.INT_1,
                IntegerUtil.INT_3, IntegerUtil.INT_4, IntegerUtil.INT_5);

        Assert.assertEquals(
                CalendarUtil.toString(cal, CalendarUtil.FORMAT.YYYYMMDD),
                "20000201");

        Assert.assertEquals(
                CalendarUtil.toString(cal, CalendarUtil.FORMAT.HHMMSS),
                "030405");

        Assert.assertEquals(
                CalendarUtil.toString(cal, CalendarUtil.FORMAT.YYYYMMDDHHMMSS),
                "20000201030405");

    }
}
