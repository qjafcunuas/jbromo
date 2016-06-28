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
package org.jbromo.model.jpa.compositepk;

import java.lang.reflect.Field;
import java.util.List;

import org.jbromo.common.ObjectUtil;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.common.invocation.ParameterizedTypeUtil;
import org.jbromo.model.jpa.compositepk.AbstractCompositePk;
import org.jbromo.model.jpa.testutil.builder.AbstractEntityBuilderFactory;
import org.jbromo.model.jpa.testutil.builder.field.FieldBuilderFactory;
import org.jbromo.model.jpa.util.EntityUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Define default JUnit AbstractCompositePk class.
 * @author qjafcunuas
 * @param <PK> the primary key type.
 */
public abstract class AbstractCompositePkTest<PK extends AbstractCompositePk> {

    /**
     * Return the entity factory.
     * @return the entity factory.
     */
    protected abstract AbstractEntityBuilderFactory getEntityFactory();

    /**
     * Return the field builder factory.
     * @return the field builder factory.
     */
    protected FieldBuilderFactory getFieldBuilderFactory() {
        return getEntityFactory().getFieldBuilderFactory();
    }

    /**
     * Return the primary key class.
     * @return the primary key class.
     */
    protected Class<PK> getPrimaryKeyClass() {
        return ParameterizedTypeUtil.getClass(this, 0);
    }

    /**
     * Test empty constructor.
     */
    @Test
    public void constructorEmpty() {
        try {
            final PK pk = newInstance(true);
            for (final Field field : EntityUtil.getPersistedFields(pk.getClass())) {
                Assert.assertNull(InvocationUtil.getValue(pk, field));
            }
        } catch (final InvocationException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test full constructor.
     */
    @Test
    public void constructorFull() {
        final List<Field> fields = EntityUtil.getPersistedFields(getPrimaryKeyClass());
        try {
            final PK pk = newInstance(false);
            Assert.assertNotNull(pk);
            for (final Field field : fields) {
                Assert.assertNotNull(InvocationUtil.getValue(pk, field));
            }
        } catch (final InvocationException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Return a new instance of the primary key.
     * @param nullField if true, call the constructor with no argument; else call the constructor with all arguments.
     * @return the new instance.
     */
    private PK newInstance(final boolean nullField) {
        try {
            if (nullField) {
                return ObjectUtil.newInstance(getPrimaryKeyClass());
            }

            final List<Field> fields = EntityUtil.getPersistedFields(getPrimaryKeyClass());
            Object value;
            final PK pk = ObjectUtil.newInstance(getPrimaryKeyClass());
            for (final Field field : fields) {
                value = getFieldBuilderFactory().getBuilder(field).nextRandom(false, field);
                InvocationUtil.injectValue(pk, field, value);
            }
            return pk;
        } catch (final InvocationException e) {
            Assert.fail(e.getMessage());
        }
        return null;

    }

    /**
     * Test toString method.
     */
    @Test
    public void toStringTest() {
        PK pk = newInstance(true);
        Assert.assertNotNull(pk.toString());
        pk = newInstance(false);
        Assert.assertNotNull(pk.toString());
    }

    /**
     * Test equals method.
     */
    @Test
    public void equalsTest() {
        final PK onePk = newInstance(false);

        // Compare to null object.
        Assert.assertFalse(onePk.equals(null));
        // Compare with same object.
        Assert.assertTrue(onePk.equals(onePk));
        // Compare with another class.
        Assert.assertFalse(onePk.equals(1));

        // Compare with not all same fields.
        Assert.assertFalse(onePk.equals(newInstance(true)));
        Assert.assertFalse(onePk.equals(newInstance(false)));

        // Compare with all same fields.
        final PK clonePk = ObjectUtil.cast(onePk.clone(), getPrimaryKeyClass());
        Assert.assertNotSame(onePk, clonePk);
        Assert.assertEquals(onePk, clonePk);

    }

    /**
     * Test hashCode method.
     */
    @Test
    public void hashCodeTest() {
        final PK onePk = newInstance(false);

        // Compare for same object.
        Assert.assertTrue(onePk.hashCode() == onePk.hashCode());
        // Compare for distinct object with null fields.
        Assert.assertFalse(onePk.hashCode() == newInstance(true).hashCode());
        // Compare for distinct object with distinct not null fields.
        Assert.assertFalse(onePk.hashCode() == newInstance(false).hashCode());
    }

}
