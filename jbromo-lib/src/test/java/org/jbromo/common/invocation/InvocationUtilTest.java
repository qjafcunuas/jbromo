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
package org.jbromo.common.invocation;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.ListUtil;
import org.jbromo.common.ThrowableUtil;
import org.jbromo.common.test.common.ConstructorUtil;
import org.jbromo.common.test.common.EnumTestUtil;
import org.junit.Assert;
import org.junit.Test;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Test for the ParameterizedType util class.
 * @author qjafcunuas
 */
@Slf4j
public class InvocationUtilTest {

    /**
     * Define an object for test.
     * @author qjafcunuas
     */
    private class MyObject {
        /**
         * An object.
         */
        @Getter
        @Setter
        private Serializable object;

        /**
         * A boolean. No setter for testing InvocationUtil.injectValue method.
         */
        @Getter
        @Setter(AccessLevel.NONE)
        private boolean bool;

        /**
         * A boolean. No setter/getter .
         */
        @Getter(AccessLevel.NONE)
        @Setter(AccessLevel.NONE)
        private boolean noGetterSetter;

        /**
         * A boolean. No setter/getter .
         */
        @Getter(AccessLevel.NONE)
        @Setter(AccessLevel.NONE)
        public boolean publicField;

        /**
         * Executing this method throw an exception.
         */
        void error() {
            final int i = 1 / 0;
        }
    }

    /**
     * Define an object for GetFields test.
     * @author qjafcunuas
     */
    private class MyExtendedObject extends MyObject implements Cloneable {
        /**
         * An object.
         */
        @Getter
        @Setter
        private Serializable extended;
    }

    /**
     * Define persistable fields.
     * @author qjafcunuas
     */
    @Getter
    private static class PersitableObject {
        /** A private field. */
        private Integer field;

        /** A private final field. */
        private final Integer fieldFinal = 0;

        /** A private static field. */
        private static Integer fieldStatic = 0;

        /** A private transient field. */
        private transient Integer fieldTransient = 0;

        /** A private static final field. */
        private static final Integer fieldStaticFinal = 0;

        /** A private static transient field. */
        private static transient Integer fieldStaticTransient = 0;

        /** A private final transient field. */
        private final transient Integer fieldFinalTransient = 0;

        /** A private static final transient field. */
        private static final transient Integer fieldStaticFinalTransient = 0;

        /** A public field. */
        public final Integer publicField = 0;
    }

    /**
     * Inner class for testing getConstructor method.
     * @author qjafcunuas
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    private static class ConstructorObject {
        /** A private field. */
        private Number firstField;

        /** A private field. */
        private Number secondField;

    }

    /**
     * Inner class for testing getConstructor method.
     * @author qjafcunuas
     */
    @AllArgsConstructor
    @Getter
    @Setter
    private static class ConstructorObjectWithoutNoArgsConstructor {
        /** A private field. */
        private Number firstField;

        /** A private field. */
        private Number secondField;

    }

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        ConstructorUtil.executePrivate(InvocationUtil.class);
    }

    /**
     * Test valueOf method.
     */
    @Test
    public void valueOf() {
        EnumTestUtil.valueOf(InvocationUtil.AccessType.class);
    }

    /**
     * Test the getFields method.
     */
    @Test
    public void getFields() {
        // Null
        List<Field> fields = InvocationUtil.getFields(null);
        Assert.assertNotNull(fields);
        Assert.assertTrue(fields.size() == 0);
        // MyObject
        fields = InvocationUtil.getFields(MyObject.class);
        cleanJacoco(fields);
        Assert.assertTrue(fields.size() == IntegerUtil.INT_4);
        Assert.assertTrue(fields.get(IntegerUtil.INT_0).getName().equals("object"));
        Assert.assertTrue(fields.get(IntegerUtil.INT_1).getName().equals("bool"));
        Assert.assertTrue(fields.get(IntegerUtil.INT_2).getName().equals("noGetterSetter"));
        Assert.assertTrue(fields.get(IntegerUtil.INT_3).getName().equals("publicField"));

        // MyExtendedObject
        fields = InvocationUtil.getFields(MyExtendedObject.class);
        cleanJacoco(fields);
        Assert.assertTrue(fields.size() == IntegerUtil.INT_5);
        Assert.assertTrue(fields.get(IntegerUtil.INT_0).getName().equals("extended"));
        Assert.assertTrue(fields.get(IntegerUtil.INT_1).getName().equals("object"));
        Assert.assertTrue(fields.get(IntegerUtil.INT_2).getName().equals("bool"));
        Assert.assertTrue(fields.get(IntegerUtil.INT_3).getName().equals("noGetterSetter"));
        Assert.assertTrue(fields.get(IntegerUtil.INT_4).getName().equals("publicField"));
    }

    /**
     * Remove Jacoco field.
     * @param fields the fields.
     */
    private void cleanJacoco(final List<Field> fields) {
        final List<Field> clone = ListUtil.toList(fields);
        for (final Field field : clone) {
            if (field.getName().contains("$jacocoData")) {
                fields.remove(field);
            }
        }
    }

    /**
     * Test the getMethod method.
     */
    @Test
    public void getMethod() {
        // null, null
        try {
            Assert.assertNull(InvocationUtil.getMethod(null, null));
        } catch (final InvocationException e1) {
            Assert.fail("Invocation shouldn't thrown exception");
        }
        // null, empty
        try {
            Assert.assertNull(InvocationUtil.getMethod(null, ""));
        } catch (final InvocationException e1) {
            Assert.fail("Invocation shouldn't thrown exception");
        }
        // null, not empty
        try {
            Assert.assertNull(InvocationUtil.getMethod(null, "toto"));
        } catch (final InvocationException e1) {
            Assert.fail("Invocation shouldn't thrown exception");
        }
        // not null, null
        try {
            Assert.assertNull(InvocationUtil.getMethod(MyObject.class, null));
        } catch (final InvocationException e1) {
            Assert.fail("Invocation shouldn't thrown exception");
        }
        // not null, empty
        try {
            Assert.assertNull(InvocationUtil.getMethod(MyObject.class, ""));
        } catch (final InvocationException e1) {
            Assert.fail("Invocation shouldn't thrown exception");
        }
        // MyObject
        try {
            Assert.assertNotNull(InvocationUtil.getMethod(MyObject.class, "getObject"));
        } catch (final InvocationException e1) {
            Assert.fail("Method getObject should exist!");
        }
        try {
            InvocationUtil.getMethod(MyObject.class, "toto");
            Assert.fail("Method toto should not exist!");
        } catch (final InvocationException e) {
            // nothing to do.
            Assert.assertTrue(true);
        }
        // MyObject
        try {
            Assert.assertNull(InvocationUtil.getMethod(MyObject.class, "getObject", Integer.class));
            Assert.fail("Method getObject(Integer) should not exist!");
        } catch (final InvocationException e1) {
            // nothing to do.
            Assert.assertTrue(true);
        }

        // MyExtendedObject
        try {
            Assert.assertNotNull(InvocationUtil.getMethod(MyExtendedObject.class, "getObject"));
        } catch (final InvocationException e) {
            Assert.fail("Method getObject should exist!");
        }
        try {
            Assert.assertNotNull(InvocationUtil.getMethod(MyExtendedObject.class, "getExtended"));
        } catch (final InvocationException e) {
            Assert.fail("Method getExtended should exist!");
        }
        try {
            InvocationUtil.getMethod(MyExtendedObject.class, "toto");
            Assert.fail("Method toto should not exist!");
        } catch (final InvocationException e) {
            // nothing to do.
            Assert.assertTrue(true);
        }
    }

    /**
     * Test the getMethod method.
     */
    @Test
    public void hasMethod() {
        // null, null
        Assert.assertFalse(InvocationUtil.hasMethod(null, null));
        // null, empty
        Assert.assertFalse(InvocationUtil.hasMethod(null, ""));
        // null, not empty
        Assert.assertFalse(InvocationUtil.hasMethod(null, "toto"));
        // not null, null
        Assert.assertFalse(InvocationUtil.hasMethod(MyObject.class, null));
        // not null, empty
        Assert.assertFalse(InvocationUtil.hasMethod(MyObject.class, ""));

        // MyObject
        Assert.assertTrue(InvocationUtil.hasMethod(MyObject.class, "getObject"));
        Assert.assertFalse(InvocationUtil.hasMethod(MyObject.class, "toto"));

        // MyExtendedObject
        Assert.assertTrue(InvocationUtil.hasMethod(MyExtendedObject.class, "getObject"));
        Assert.assertTrue(InvocationUtil.hasMethod(MyExtendedObject.class, "getExtended"));
        Assert.assertFalse(InvocationUtil.hasMethod(MyExtendedObject.class, "toto"));
    }

    /**
     * Test the getField method.
     */
    @Test
    public void getField() {
        // null, null
        Assert.assertNull(InvocationUtil.getField(null, null));
        // null, empty
        Assert.assertNull(InvocationUtil.getField(null, ""));
        // null, not empty
        Assert.assertNull(InvocationUtil.getField(null, "toto"));
        // not null, null
        Assert.assertNull(InvocationUtil.getField(MyObject.class, null));
        // not null, empty
        Assert.assertNull(InvocationUtil.getField(MyObject.class, ""));

        // MyObject
        Assert.assertNotNull(InvocationUtil.getField(MyObject.class, "object"));
        Assert.assertNull(InvocationUtil.getField(MyObject.class, "extended"));
        Assert.assertNull(InvocationUtil.getField(MyObject.class, "toto"));

        // MyExtendedObject
        Assert.assertNotNull(InvocationUtil.getField(MyExtendedObject.class, "object"));
        Assert.assertNotNull(InvocationUtil.getField(MyExtendedObject.class, "extended"));
        Assert.assertNull(InvocationUtil.getField(MyExtendedObject.class, "toto"));
    }

    /**
     * Test the getValue method.
     */
    @Test
    public void getValue() {
        // MyObject
        final MyObject obj = new MyObject();
        obj.setObject(IntegerUtil.INT_0);
        Field field = InvocationUtil.getField(obj.getClass(), "object");
        Assert.assertNotNull(field);

        // null, null
        try {
            Assert.assertNull(InvocationUtil.getValue(null, (Field) null));
            Assert.assertNull(InvocationUtil.getValue(null, (String) null));
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }
        // null, not null
        try {
            Assert.assertNull(InvocationUtil.getValue(null, field));
            Assert.assertNull(InvocationUtil.getValue(null, field.getName()));
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }
        // not null, null
        try {
            Assert.assertNull(InvocationUtil.getValue(obj, (Field) null));
            Assert.assertNull(InvocationUtil.getValue(obj, (String) null));
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }
        // not null, not null
        try {
            Assert.assertNotNull(InvocationUtil.getValue(obj, field));
            Assert.assertNotNull(InvocationUtil.getValue(obj, field.getName()));
            Assert.assertEquals(InvocationUtil.getValue(obj, field), obj.getObject());
            Assert.assertEquals(InvocationUtil.getValue(obj, field.getName()), obj.getObject());
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }
        // boolean
        field = InvocationUtil.getField(obj.getClass(), "bool");
        Assert.assertNotNull(field);
        try {
            Assert.assertNotNull(InvocationUtil.getValue(obj, field));
            Assert.assertNotNull(InvocationUtil.getValue(obj, field.getName()));
            Assert.assertEquals(InvocationUtil.getValue(obj, field), obj.isBool());
            Assert.assertEquals(InvocationUtil.getValue(obj, field.getName()), obj.isBool());
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }

        // MyExtendedObjt
        final MyExtendedObject extObj = new MyExtendedObject();
        extObj.setObject(IntegerUtil.INT_0);
        extObj.setExtended(IntegerUtil.INT_1);
        try {
            field = InvocationUtil.getField(extObj.getClass(), "object");
            Assert.assertNotNull(field);
            Assert.assertNotNull(InvocationUtil.getValue(extObj, field));
            Assert.assertNotNull(InvocationUtil.getValue(extObj, field.getName()));
            Assert.assertEquals(InvocationUtil.getValue(extObj, field), extObj.getObject());
            Assert.assertEquals(InvocationUtil.getValue(extObj, field.getName()), extObj.getObject());

            field = InvocationUtil.getField(extObj.getClass(), "extended");
            Assert.assertNotNull(field);
            Assert.assertNotNull(InvocationUtil.getValue(extObj, field));
            Assert.assertNotNull(InvocationUtil.getValue(extObj, field.getName()));
            Assert.assertEquals(InvocationUtil.getValue(extObj, field), extObj.getExtended());
            Assert.assertEquals(InvocationUtil.getValue(extObj, field.getName()), extObj.getExtended());
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }
    }

    /**
     * Test the getValue(object, field, access, accessible) method.
     */
    @Test
    public void getValueOFAA() {
        // MyObject
        final MyObject obj = new MyObject();
        obj.setObject(IntegerUtil.INT_0);
        Field field = InvocationUtil.getField(obj.getClass(), "object");
        Assert.assertNotNull(field);

        // null, null, null
        try {
            Assert.assertNull(InvocationUtil.getValue(null, (Field) null, (InvocationUtil.AccessType) null, false));
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }
        // null, null, not null
        try {
            Assert.assertNull(InvocationUtil.getValue(null, (Field) null, InvocationUtil.AccessType.GETTER, false));
            Assert.assertNull(InvocationUtil.getValue(null, field));
            Assert.assertNull(InvocationUtil.getValue(null, field.getName()));
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }
        // null, not null, null
        try {
            Assert.assertNull(InvocationUtil.getValue(null, field, (InvocationUtil.AccessType) null, false));
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }
        // null, not null, not null
        try {
            Assert.assertNull(InvocationUtil.getValue(null, field, InvocationUtil.AccessType.GETTER, false));
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }
        // not null, null, null
        try {
            Assert.assertNull(InvocationUtil.getValue(obj, (Field) null, (InvocationUtil.AccessType) null, false));
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }
        // not null, null, not null
        try {
            Assert.assertNull(InvocationUtil.getValue(obj, (Field) null, InvocationUtil.AccessType.GETTER, false));
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }
        // not null, not null, null
        try {
            Assert.assertNull(InvocationUtil.getValue(obj, field, (InvocationUtil.AccessType) null, false));
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }
        // not null, not null, not null
        try {
            Assert.assertNotNull(InvocationUtil.getValue(obj, field, InvocationUtil.AccessType.GETTER, false));
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }
        // not null, not null, not null
        try {
            Assert.assertNotNull(InvocationUtil.getValue(obj, field, InvocationUtil.AccessType.FIELD, false));
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }
        // private field
        try {
            Assert.assertNotNull(InvocationUtil.getValue(obj, field, InvocationUtil.AccessType.FIELD, true));
            Assert.fail("Field is not accessible!");
        } catch (final InvocationException e) {
            Assert.assertTrue(true);
        }
        // public field.
        try {
            field = InvocationUtil.getField(obj.getClass(), "publicField");
            Assert.assertNotNull(InvocationUtil.getValue(obj, field, InvocationUtil.AccessType.FIELD, true));
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }

    }

    /**
     * Test the setValue method.
     */
    @Test
    public void setValue() {
        // MyObject
        final MyObject obj = new MyObject();
        try {
            final Field field = InvocationUtil.getField(obj.getClass(), "object");
            Assert.assertNotNull(field);
            InvocationUtil.setValue(obj, field, IntegerUtil.INT_10);
            Assert.assertEquals(IntegerUtil.INT_10, obj.getObject());
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }

    }

    /**
     * Test the hasSetter method.
     */
    @Test
    public void hasSetter() {
        try {
            Field field = InvocationUtil.getField(MyObject.class, "object");
            Assert.assertNotNull(field);
            Assert.assertTrue(InvocationUtil.hasSetter(field));
            field = InvocationUtil.getField(MyObject.class, "bool");
            Assert.assertNotNull(field);
            Assert.assertFalse(InvocationUtil.hasSetter(field));
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }

    }

    /**
     * Test the hasGetter method.
     */
    @Test
    public void hasGetter() {
        try {
            Field field = InvocationUtil.getField(MyObject.class, "object");
            Assert.assertNotNull(field);
            Assert.assertTrue(InvocationUtil.hasGetter(field));
            field = InvocationUtil.getField(MyObject.class, "noGetterSetter");
            Assert.assertNotNull(field);
            Assert.assertFalse(InvocationUtil.hasGetter(field));
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }

    }

    /**
     * Test the invokeMethod method.
     */
    @Test
    public void invokeMethod() {
        // Null method
        try {
            Assert.assertNull(InvocationUtil.invokeMethod(null, new Object(), IntegerUtil.INT_10));
        } catch (final InvocationException e1) {
            Assert.fail("Method setObject should exist!");
        }
        // Create MyObject
        Method methodSetObject = null;
        Method methodError = null;
        final MyObject obj = new MyObject();
        try {
            methodSetObject = InvocationUtil.getMethod(MyObject.class, "setObject", Serializable.class);
            Assert.assertNotNull(methodSetObject);
            methodError = InvocationUtil.getMethod(MyObject.class, "error");
            Assert.assertNotNull(methodError);
        } catch (final InvocationException e1) {
            Assert.fail("Method setObject should exist!");
        }
        // Valid method invocation
        try {
            InvocationUtil.invokeMethod(methodSetObject, obj, IntegerUtil.INT_10);
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }
        Assert.assertEquals(obj.getObject(), IntegerUtil.INT_10);
        // Invalid argument method invocation
        try {
            InvocationUtil.invokeMethod(methodSetObject, obj, new Object());
            Assert.fail("Invoking bad parameter on method should thrown an exception.");
        } catch (final InvocationException e) {
            Assert.assertTrue(true);
        }
        // Exception throwing during method invocation
        try {
            InvocationUtil.invokeMethod(methodError, obj);
            Assert.fail("Executing this method should thrown an exception.");
        } catch (final InvocationException e) {
            Assert.assertTrue(true);
        }
    }

    /**
     * Test the isFromInterfaceType(Class) method.
     */
    @Test
    public void isFromInterfaceTypeClass() {
        // null, null
        Assert.assertFalse(InvocationUtil.isFromInterfaceType((Class<?>) null, (Class<?>) null));
        // null, not null
        Assert.assertFalse(InvocationUtil.isFromInterfaceType((Class<?>) null, MyObject.class));
        // not null, null
        Assert.assertFalse(InvocationUtil.isFromInterfaceType(MyObject.class, (Class<?>) null));

        // others
        Assert.assertFalse(InvocationUtil.isFromInterfaceType(MyObject.class, MyObject.class));
        Assert.assertFalse(InvocationUtil.isFromInterfaceType(MyExtendedObject.class, MyObject.class));
        Assert.assertFalse(InvocationUtil.isFromInterfaceType(MyObject.class, Cloneable.class));
        Assert.assertTrue(InvocationUtil.isFromInterfaceType(MyExtendedObject.class, Cloneable.class));
    }

    /**
     * Test the isFromInterfaceType(Field) method.
     */
    @Test
    public void isFromInterfaceTypeField() {
        final Field field = InvocationUtil.getField(MyObject.class, "object");

        // null, null
        Assert.assertFalse(InvocationUtil.isFromInterfaceType((Field) null, (Class<?>) null));
        // null, not null
        Assert.assertFalse(InvocationUtil.isFromInterfaceType((Field) null, MyObject.class));
        // not null, null
        Assert.assertFalse(InvocationUtil.isFromInterfaceType(field, (Class<?>) null));

        Assert.assertFalse(InvocationUtil.isFromInterfaceType(field, MyObject.class));
        Assert.assertFalse(InvocationUtil.isFromInterfaceType(field, Serializable.class));
    }

    /**
     * Test the isPersistable method.
     */
    @Test
    public void isPersistable() {
        Assert.assertTrue(InvocationUtil.isPersistable(InvocationUtil.getField(PersitableObject.class, "field")));
        Assert.assertFalse(InvocationUtil.isPersistable(InvocationUtil.getField(PersitableObject.class, "fieldFinal")));
        Assert.assertFalse(InvocationUtil.isPersistable(InvocationUtil.getField(PersitableObject.class, "fieldStatic")));
        Assert.assertFalse(InvocationUtil.isPersistable(InvocationUtil.getField(PersitableObject.class, "fieldTransient")));
        Assert.assertFalse(InvocationUtil.isPersistable(InvocationUtil.getField(PersitableObject.class, "fieldStaticFinal")));
        Assert.assertFalse(InvocationUtil.isPersistable(InvocationUtil.getField(PersitableObject.class, "fieldStaticTransient")));
        Assert.assertFalse(InvocationUtil.isPersistable(InvocationUtil.getField(PersitableObject.class, "fieldFinalTransient")));
        Assert.assertFalse(InvocationUtil.isPersistable(InvocationUtil.getField(PersitableObject.class, "fieldStaticFinalTransient")));
        Assert.assertFalse(InvocationUtil.isPersistable(InvocationUtil.getField(PersitableObject.class, "publicField")));
    }

    /**
     * Test injectValue method.
     */
    @Test
    public void injectValue() {
        final MyObject obj = new MyObject();
        obj.setObject(0);

        // Object with existing setter method.
        Field field = InvocationUtil.getField(obj.getClass(), "object");
        Assert.assertNotNull(field);
        try {
            InvocationUtil.injectValue(obj, field, IntegerUtil.INT_10);
            Assert.assertEquals(obj.getObject(), IntegerUtil.INT_10);
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }

        // Object with no setter method.
        field = InvocationUtil.getField(obj.getClass(), "bool");
        Assert.assertNotNull(field);
        try {
            Assert.assertFalse(obj.isBool());
            InvocationUtil.injectValue(obj, field, true);
            Assert.assertTrue(obj.isBool());
        } catch (final InvocationException e) {
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }

        // Object with unexisted field.
        field = InvocationUtil.getField(ConstructorObject.class, "firstField");
        Assert.assertNotNull(field);
        try {
            InvocationUtil.injectValue(obj, field, true);
            Assert.fail("should failed!");
        } catch (final InvocationException e) {
            Assert.assertTrue(true);
        }

    }

    /**
     * Test getConstructor method.
     */
    @Test
    public void getConstructor() {
        // Empty constructor.
        try {
            Assert.assertNotNull(InvocationUtil.getConstructor(ConstructorObject.class));
        } catch (final InvocationException e) {
            log.error("Cannot get empty constructor", e);
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }
        // Field constructor.
        try {
            Assert.assertNotNull(InvocationUtil.getConstructor(ConstructorObject.class, Number.class, Number.class));
        } catch (final InvocationException e) {
            log.error("Cannot get constructor", e);
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }
        try {
            Assert.assertNotNull(InvocationUtil.getConstructor(ConstructorObject.class, Integer.class, Number.class));
        } catch (final InvocationException e) {
            log.error("Cannot get constructor", e);
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }
        try {
            Assert.assertNotNull(InvocationUtil.getConstructor(ConstructorObject.class, Number.class, Integer.class));
        } catch (final InvocationException e) {
            log.error("Cannot get constructor", e);
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }
        try {
            Assert.assertNotNull(InvocationUtil.getConstructor(ConstructorObject.class, Integer.class, Integer.class));
        } catch (final InvocationException e) {
            log.error("Cannot get constructor", e);
            Assert.fail(ThrowableUtil.getStackTrace(e));
        }
        try {
            // Unexisted constructor.
            Assert.assertNull(InvocationUtil.getConstructor(ConstructorObject.class, Integer.class, Integer.class, Integer.class));
        } catch (final InvocationException e) {
            Assert.assertTrue(true);
        }
        try {
            // Unexisted constructor without parameters.
            Assert.assertNull(InvocationUtil.getConstructor(ConstructorObjectWithoutNoArgsConstructor.class));
        } catch (final InvocationException e) {
            Assert.assertTrue(true);
        }

    }

}