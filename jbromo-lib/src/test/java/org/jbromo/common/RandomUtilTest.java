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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.jbromo.common.test.common.ConstructorUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for the Random Util class.
 * @author qjafcunuas
 */
public class RandomUtilTest {

    /**
     * Define enum for test.
     * @author qjafcunuas
     */
    private enum MyEnum {
        /**
         * One value.
         */
        ONE, /**
              * Another value.
              */
        TWO, /**
              * Another value.
              */
        THREE;
    }

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        ConstructorUtil.executePrivate(RandomUtil.class);
    }

    /**
     * Test the isNull method.
     */
    @Test
    public void isNull() {
        try {
            for (int i = 0; i < IntegerUtil.INT_100; i++) {
                RandomUtil.isNull();
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the nextBigDecimal method.
     */
    @Test
    public void nextBigDecimal() {
        try {
            final int fraction = IntegerUtil.INT_3;
            // nextBigDecimal(nullable, integer, fraction)
            for (int i = 1; i < IntegerUtil.INT_100; i++) {
                RandomUtil.nextBigDecimal(true, i, fraction);
                final BigDecimal value = RandomUtil.nextBigDecimal(false, i, fraction);
                Assert.assertNotNull(value);
                Assert.assertTrue("Error on value " + value + " - i=" + i, value.compareTo(BigDecimal.valueOf(Math.pow(IntegerUtil.INT_10, i))) < 0);
                Assert.assertTrue("Error on scale value " + value.scale(), value.scale() == fraction);
            }

            // nextBigDecimal(nullable, integer, fraction, min)
            for (int i = 1; i < IntegerUtil.INT_100; i++) {
                // min = 10*(i-1)
                final BigDecimal min = BigDecimal.valueOf(Math.pow(IntegerUtil.INT_10, i - 1));
                RandomUtil.nextBigDecimal(true, i, fraction, min);
                final BigDecimal value = RandomUtil.nextBigDecimal(false, i, fraction, min);
                Assert.assertNotNull(value);
                Assert.assertTrue("Error on value " + value + " - i=" + i + " - min=" + min,
                                  value.compareTo(BigDecimal.valueOf(Math.pow(IntegerUtil.INT_10, i))) < 0);
                Assert.assertTrue("Error on scale value " + value.scale(), value.scale() == fraction);
                Assert.assertTrue("Error on value " + value + "not greater than " + min, value.compareTo(min) > 0);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the isNull method.
     */
    @Test
    public void nextBoolean() {
        try {
            Boolean value;
            for (int i = 0; i < IntegerUtil.INT_1000; i++) {
                value = RandomUtil.nextBoolean();
                Assert.assertNotNull(value);
                value = RandomUtil.nextBoolean(false);
                Assert.assertNotNull(value);
                value = RandomUtil.nextBoolean(true);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the nextChar method.
     */
    @Test
    public void nextChar() {
        try {
            for (int i = 0; i < IntegerUtil.INT_100; i++) {
                RandomUtil.nextChar();
                final char c = RandomUtil.nextCharLatin();
                Assert.assertTrue(c == ' ' || '0' <= c && c <= '9' || 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z');
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the nextDate method.
     */
    @Test
    public void nextCalendar() {
        try {
            Calendar value;
            for (int i = 0; i < IntegerUtil.INT_100; i++) {
                value = RandomUtil.nextCalendar();
                Assert.assertNotNull(value);
                value = RandomUtil.nextCalendar(false);
                Assert.assertNotNull(value);
                value = RandomUtil.nextCalendar(true);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the nextDouble method.
     */
    @Test
    public void nextDouble() {
        try {
            Double value;
            for (int i = 0; i < IntegerUtil.INT_100; i++) {
                value = RandomUtil.nextDouble();
                Assert.assertNotNull(value);
                value = RandomUtil.nextDouble(false);
                Assert.assertNotNull(value);
                value = RandomUtil.nextDouble(true);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the nextEmail method.
     */
    @Test
    public void nextEmail() {
        try {
            String value;
            for (int i = 0; i < IntegerUtil.INT_100; i++) {
                value = RandomUtil.nextEmail(false, RandomUtil.EMAIL_SIZE_MIN);
                Assert.assertNotNull(value);
                Assert.assertTrue(value.length() == RandomUtil.EMAIL_SIZE_MIN);
                value = RandomUtil.nextEmail(false, RandomUtil.EMAIL_SIZE_MIN - 1);
                Assert.assertNotNull(value);
                Assert.assertTrue(value.length() == RandomUtil.EMAIL_SIZE_MIN);
                value = RandomUtil.nextEmail(false, IntegerUtil.INT_10);
                Assert.assertNotNull(value);
                Assert.assertTrue(value.length() == IntegerUtil.INT_10);
                Assert.assertTrue(value.contains("@"));
                Assert.assertTrue(value.contains("."));
                Assert.assertFalse(value.contains(" "));
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the nextEnum method.
     */
    @Test
    public void nextEnum() {
        try {
            MyEnum value;
            for (int i = 0; i < IntegerUtil.INT_100; i++) {
                value = RandomUtil.nextEnum(MyEnum.values());
                Assert.assertNotNull(value);
                value = RandomUtil.nextEnum(false, MyEnum.values());
                Assert.assertNotNull(value);
                value = RandomUtil.nextEnum(true, MyEnum.values());
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the nextCollection method.
     */
    @Test
    public void nextCollection() {
        try {
            Assert.assertNull(RandomUtil.nextCollection(null));
            final List<Object> list = new ArrayList<Object>();
            Assert.assertNull(RandomUtil.nextCollection(list));
            for (int i = 0; i < IntegerUtil.INT_10; i++) {
                list.add(new Object());
            }
            Object value;
            for (int i = 0; i < IntegerUtil.INT_100; i++) {
                value = RandomUtil.nextCollection(list);
                Assert.assertNotNull(value);
                Assert.assertTrue(list.contains(value));
                value = RandomUtil.nextCollection(false, list);
                Assert.assertNotNull(value);
                value = RandomUtil.nextCollection(true, list);
                Assert.assertTrue(value == null || list.contains(value));
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the nextCollection method.
     */
    @Test
    public void nextSubCollection() {
        try {
            Assert.assertNull(RandomUtil.nextSubCollection(null));
            final List<Object> list = new ArrayList<Object>();
            Assert.assertEquals(0, RandomUtil.nextSubCollection(list).size());
            Collection<Object> random = null;
            for (int i = 0; i < IntegerUtil.INT_10; i++) {
                list.add(new Object());
            }
            for (int i = 0; i < IntegerUtil.INT_100; i++) {
                random = RandomUtil.nextSubCollection(list);
                Assert.assertNotNull(random);
                Assert.assertTrue(list.containsAll(random));
                Assert.assertTrue(random.size() <= list.size());
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the nextFloat method.
     */
    @Test
    public void nextFloat() {
        try {
            Float value;
            for (int i = 0; i < IntegerUtil.INT_100; i++) {
                value = RandomUtil.nextFloat();
                Assert.assertNotNull(value);
                value = RandomUtil.nextFloat(false);
                Assert.assertNotNull(value);
                value = RandomUtil.nextFloat(true);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the nextFullBigDecimal method.
     */
    @Test
    public void nextFullBigDecimal() {
        try {
            final int fraction = IntegerUtil.INT_3;
            // nextFullBigDecimal(integer, fraction)
            for (int i = 1; i < IntegerUtil.INT_100; i++) {
                final BigDecimal value = RandomUtil.nextFullBigDecimal(i, fraction);
                Assert.assertNotNull(value);
                // value must be less than 10*i
                Assert.assertTrue("Error on value " + value + " - i=" + i, value.compareTo(BigDecimal.valueOf(Math.pow(IntegerUtil.INT_10, i))) < 0);
                // value must be greater than 10*(i-1)
                Assert.assertTrue("Error on value " + value + " - i=" + i,
                                  value.compareTo(BigDecimal.valueOf(Math.pow(IntegerUtil.INT_10, i - 1))) >= 0);
                Assert.assertTrue("Error on scale value " + value.scale(), value.scale() == fraction);
            }

            // nextFullBigDecimal(integer, fraction, min)
            for (int i = 1; i < IntegerUtil.INT_100; i++) {
                final BigDecimal min = RandomUtil.nextFullBigDecimal(i, fraction);
                Assert.assertNotNull(min);
                final BigDecimal value = RandomUtil.nextFullBigDecimal(i, fraction, min);
                Assert.assertNotNull(value);
                // value must be less than 10*i
                Assert.assertTrue("Error on value " + value + " - i=" + i + " - min=" + min,
                                  value.compareTo(BigDecimal.valueOf(Math.pow(IntegerUtil.INT_10, i))) < 0);
                // value must be greater than 10*(i-1)
                Assert.assertTrue("Error on value " + value + " - i=" + i + " - min=" + min,
                                  value.compareTo(BigDecimal.valueOf(Math.pow(IntegerUtil.INT_10, i - 1))) >= 0);
                Assert.assertTrue("Error on scale value " + value.scale(), value.scale() == fraction);
                Assert.assertTrue("Error on value " + value + "not greater than " + min, value.compareTo(min) >= 0);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the nextInt method.
     */
    @Test
    public void nextInt() {
        // min <=0
        Assert.assertEquals(RandomUtil.nextInt(0), 0);
        Assert.assertEquals(RandomUtil.nextInt(-1), 0);
        // min, max <=0
        Assert.assertEquals(RandomUtil.nextInt(IntegerUtil.INT_10, 0), 0);
        Assert.assertEquals(RandomUtil.nextInt(IntegerUtil.INT_10, -1), 0);
        // min <= 0, max
        Assert.assertTrue(RandomUtil.nextInt(0, IntegerUtil.INT_10) >= 0);
        Assert.assertTrue(RandomUtil.nextInt(-1, IntegerUtil.INT_10) >= -1);
        // min == max
        Assert.assertEquals(RandomUtil.nextInt(IntegerUtil.INT_10, IntegerUtil.INT_10), IntegerUtil.INT_10);
        Assert.assertEquals(RandomUtil.nextInt(IntegerUtil.INT_10, IntegerUtil.INT_10), IntegerUtil.INT_10);
        try {
            Integer value;
            for (int i = 0; i < IntegerUtil.INT_100; i++) {
                value = RandomUtil.nextInt();
                Assert.assertNotNull(value);
                value = RandomUtil.nextInt(false);
                Assert.assertNotNull(value);
                value = RandomUtil.nextInt(true);
                final int max = Math.abs(RandomUtil.nextInt());
                final int min = Math.abs(RandomUtil.nextInt(max));
                Assert.assertNotNull(min);
                Assert.assertNotNull(max);
                // value < max
                value = RandomUtil.nextInt(max);
                Assert.assertTrue("value " + value + " should be less than " + max, value <= max);
                // value null ?, value < max
                value = RandomUtil.nextInt(true, max);
                Assert.assertTrue("value " + value + " should be less than " + max, value == null || value <= max);
                // value not null, value < max
                value = RandomUtil.nextInt(false, max);
                Assert.assertTrue("value " + value + " should be less than " + max, value != null && value <= max);
                // min < value < max
                value = RandomUtil.nextInt(min, max);
                Assert.assertTrue("value " + value + " should be less than " + max, value != null && value <= max);
                Assert.assertTrue("value " + value + " should be greater than " + max, value != null && min <= value);
                // value not null, min < value < max
                value = RandomUtil.nextInt(false, min, max);
                Assert.assertTrue("value " + value + " should be less than " + max, value != null && value <= max);
                Assert.assertTrue("value " + value + " should be greater than " + max, value != null && min <= value);
                // value null ?, min < value < max
                value = RandomUtil.nextInt(false, min, max);
                Assert.assertTrue("value " + value + " should be less than " + max, value == null || value <= max);
                Assert.assertTrue("value " + value + " should be greater than " + max, value == null || min <= value);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the nextLong method.
     */
    @Test
    public void nextLong() {
        try {
            for (int i = 0; i < IntegerUtil.INT_100; i++) {
                Assert.assertNotNull(RandomUtil.nextLong());
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the nextLong(boolean) method.
     */
    @Test
    public void nextLongBoolean() {

        try {
            for (int i = 0; i < IntegerUtil.INT_100; i++) {
                Assert.assertNotNull(RandomUtil.nextLong(false));
                RandomUtil.nextLong(true);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the nextLong(boolean,long) method.
     */
    @Test
    public void nextLongBooleanlong() {

        try {
            Long value;
            for (int i = 0; i < IntegerUtil.INT_100; i++) {
                final long max = Math.abs(RandomUtil.nextLong());
                Assert.assertNotNull(max);
                // value nullable, value < max
                value = RandomUtil.nextLong(true, max);
                Assert.assertTrue("value " + value + " should be less than " + max, value == null || value <= max);
                // value not null, value < max
                value = RandomUtil.nextLong(false, max);
                Assert.assertNotNull(value);
                Assert.assertTrue("value " + value + " should be less than " + max, value <= max);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the nextLong(boolean,long,long) method.
     */
    @Test
    public void nextLongBooleanlonglong() {

        try {
            Long value;
            for (int i = 0; i < IntegerUtil.INT_100; i++) {
                final long max = Math.abs(RandomUtil.nextLong());
                final long min = Math.abs(RandomUtil.nextLong(max));
                Assert.assertNotNull(max);
                Assert.assertNotNull(min);
                // value not null, min < value < max
                value = RandomUtil.nextLong(false, min, max);
                Assert.assertNotNull(value);
                Assert.assertTrue("value " + value + " should be less than " + max, value <= max);
                Assert.assertTrue("value " + value + " should be greater than " + max, min <= value);
                // value nullable, min < value < max
                value = RandomUtil.nextLong(true, min, max);
                Assert.assertTrue("value " + value + " should be less than " + max, value == null || value <= max);
                Assert.assertTrue("value " + value + " should be greater than " + max, value == null || min <= value);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the nextLong(boolean,Long,Long) method.
     */
    @Test
    public void nextLongBooleanLongLong() {

        try {
            Long value;
            for (int i = 0; i < IntegerUtil.INT_100; i++) {
                final Long max = Math.abs(RandomUtil.nextLong());
                final Long min = Math.abs(RandomUtil.nextLong(max));
                Assert.assertNotNull(max);
                Assert.assertNotNull(min);
                // value not null, min < value < max
                value = RandomUtil.nextLong(false, min, max);
                Assert.assertNotNull(value);
                Assert.assertTrue("value " + value + " should be less than " + max, value <= max);
                Assert.assertTrue("value " + value + " should be greater than " + max, min <= value);
                // value null ?, min < value < max
                value = RandomUtil.nextLong(true, min, max);
                Assert.assertTrue("value " + value + " should be less than " + max, value == null || value <= max);
                Assert.assertTrue("value " + value + " should be greater than " + min, value == null || min <= value);
                // Null min/max
                Assert.assertNotNull(RandomUtil.nextLong(false, null, null));
                Assert.assertNotNull(RandomUtil.nextLong(false, min, null));
                Assert.assertNotNull(RandomUtil.nextLong(false, null, max));
                RandomUtil.nextLong(true, null, null);
                value = RandomUtil.nextLong(true, min, null);
                Assert.assertTrue("value " + value + " should be greater than " + min, value == null || min <= value);
                value = RandomUtil.nextLong(true, null, max);
                Assert.assertTrue("value " + value + " should be less than " + max, value == null || value <= max);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the nextLong(Long,Long) method.
     */
    @Test
    public void nextLongLongLong() {

        try {
            Long value;
            for (int i = 0; i < IntegerUtil.INT_100; i++) {
                final Long max = Math.abs(RandomUtil.nextLong());
                final Long min = Math.abs(RandomUtil.nextLong(max));
                Assert.assertNotNull(max);
                Assert.assertNotNull(min);
                // value not null, min < value < max
                value = RandomUtil.nextLong(min, max);
                Assert.assertNotNull(value);
                Assert.assertTrue("value " + value + " should be less than " + max, value <= max);
                Assert.assertTrue("value " + value + " should be greater than " + max, min <= value);
                // Null min/max
                Assert.assertNotNull(RandomUtil.nextLong(null, null));
                Assert.assertNotNull(RandomUtil.nextLong(min, null));
                Assert.assertNotNull(RandomUtil.nextLong(null, max));
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the nextShort method.
     */
    @Test
    public void nextShort() {
        // max <=0
        Assert.assertEquals(RandomUtil.nextShort((short) 0), (short) 0);
        Assert.assertEquals(RandomUtil.nextShort((short) -1), (short) 0);

        try {
            Short value;
            for (int i = 0; i < IntegerUtil.INT_100; i++) {
                value = RandomUtil.nextShort();
                Assert.assertNotNull(value);
                value = RandomUtil.nextShort(false);
                Assert.assertNotNull(value);
                value = RandomUtil.nextShort(true);
                final short max = (short) Math.abs(RandomUtil.nextShort());
                final short min = (short) Math.abs(RandomUtil.nextShort(max));
                Assert.assertNotNull(max);
                Assert.assertNotNull(min);
                // value < max
                value = RandomUtil.nextShort(max);
                Assert.assertNotNull(value);
                Assert.assertTrue("value " + value + " should be less than " + max, value <= max);
                // value null ?, value < max
                value = RandomUtil.nextShort(true, max);
                Assert.assertTrue("value " + value + " should be less than " + max, value == null || value <= max);
                // value not null, value < max
                value = RandomUtil.nextShort(false, max);
                Assert.assertNotNull(value);
                Assert.assertTrue("value " + value + " should be less than " + max, value <= max);
                // min < value < max
                value = RandomUtil.nextShort(min, max);
                Assert.assertNotNull(value);
                Assert.assertTrue("value " + value + " should be less than " + max, value <= max);
                Assert.assertTrue("value " + value + " should be greater than " + max, min <= value);
                // value not null, min < value < max
                value = RandomUtil.nextShort(false, min, max);
                Assert.assertNotNull(value);
                Assert.assertTrue("value " + value + " should be less than " + max, value <= max);
                Assert.assertTrue("value " + value + " should be greater than " + max, min <= value);
                // value null ?, min < value < max
                value = RandomUtil.nextShort(true, min, max);
                Assert.assertTrue("value " + value + " should be less than " + max, value == null || value <= max);
                Assert.assertTrue("value " + value + " should be greater than " + max, value == null || min <= value);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the nextString method.
     */
    @Test
    public void nextString() {
        try {
            String value;
            for (int i = 1; i < IntegerUtil.INT_100; i++) {
                value = RandomUtil.nextString();
                Assert.assertNotNull(value);
                value = RandomUtil.nextString(false);
                Assert.assertNotNull(value);
                value = RandomUtil.nextString(true);
                // size
                value = RandomUtil.nextString(i);
                Assert.assertNotNull(value);
                Assert.assertTrue("1. Bad length value " + i + " vs " + value.length(), value.length() == i);
                // size & not null
                value = RandomUtil.nextString(false, i);
                Assert.assertNotNull(value);
                Assert.assertTrue("2. Bad length value " + i + " vs " + value.length(), value.length() == i);
                // size & can be null
                value = RandomUtil.nextString(true, i);
                Assert.assertTrue("3. Bad length value " + i + " vs " + (value == null ? "null" : value.length()),
                                  value == null || value.length() == i);
                // min max size
                value = RandomUtil.nextString(i, i + IntegerUtil.INT_10);
                Assert.assertNotNull(value);
                Assert.assertTrue("4. Bad length value " + i + " vs " + value.length(),
                                  i <= value.length() && value.length() <= i + IntegerUtil.INT_10);
                // min max size & not null
                value = RandomUtil.nextString(false, i, i + IntegerUtil.INT_10);
                Assert.assertNotNull(value);
                Assert.assertTrue("5. Bad length value " + i + " vs " + value.length(),
                                  i <= value.length() && value.length() <= i + IntegerUtil.INT_10);
                // min max size & can be null
                value = RandomUtil.nextString(true, i, i + IntegerUtil.INT_10);
                Assert.assertTrue("6. Bad length value " + i + " vs " + (value == null ? "null" : value.length()),
                                  value == null || i <= value.length() && value.length() <= i + IntegerUtil.INT_10);

            }
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test nextAnother method.
     */
    @Test
    public void nextAnother() {
        Assert.assertNull(RandomUtil.nextAnother(null));
        nextAnother(Boolean.TRUE);
        nextAnother(Boolean.FALSE);
        nextAnother(LongUtil.LONG_10);
        nextAnother(IntegerUtil.INT_10);
        nextAnother((short) 1);
        nextAnother(Double.valueOf("1.0"));
        nextAnother(Float.valueOf("1.0"));
        nextAnother(String.valueOf("abc"));
        nextAnother(BigDecimal.TEN);
    }

    /**
     * Valid nextAntoher method for an object.
     * @param obj the object to test.
     */
    private void nextAnother(final Object obj) {
        final Object result = RandomUtil.nextAnother(obj);
        Assert.assertNotNull(result);
        Assert.assertFalse(result.equals(obj));
    }

    /**
     * Test random method.
     */
    @Test
    public void random() {
        // Null collection.
        Assert.assertNull(RandomUtil.nextSubCollection(false, (List<Integer>) null));
        Assert.assertNull(RandomUtil.nextSubCollection(true, (List<Integer>) null));

        // Empty collection.
        final List<Integer> from = ListUtil.toList();
        List<Integer> result = RandomUtil.nextSubCollection(false, from);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
        result = RandomUtil.nextSubCollection(true, from);
        Assert.assertTrue(result.isEmpty());

        // Not empty collection.
        for (int i = 1; i < IntegerUtil.INT_100; i++) {
            from.add(i);
        }
        result = RandomUtil.nextSubCollection(false, from);
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        Assert.assertTrue(result.size() <= from.size());
        result = RandomUtil.nextSubCollection(false, from);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.size() <= from.size());

    }
}
