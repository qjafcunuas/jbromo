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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.jbromo.common.ArrayUtil;
import org.jbromo.common.ListUtil;
import org.jbromo.common.StringUtil;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * Invocation tools.
 * @author qjafcunuas
 */
@Slf4j
@UtilityClass
public final class InvocationUtil {

    /**
     * Define access for getting value.
     * @author qjafcunuas
     */
    public enum AccessType {
        /** Define access by field value. */
        FIELD {
            @SuppressWarnings("unchecked")
            @Override
            <V> V getValue(final Object object, final Field field, final boolean accessible) throws InvocationException {
                Object value;
                try {
                    if (accessible) {
                        value = field.get(object);
                    } else {
                        boolean isAccessible;
                        isAccessible = field.isAccessible();
                        field.setAccessible(true);
                        value = field.get(object);
                        field.setAccessible(isAccessible);
                    }
                    return (V) value;
                } catch (final IllegalArgumentException e) {
                    final StringBuilder description = new StringBuilder();
                    description.append("Search : inappropriate argument in the field '").append(field.getName()).append("' call");
                    throw new InvocationException(description.toString(), e);
                } catch (final IllegalAccessException e) {
                    final StringBuilder description = new StringBuilder();
                    description.append("Search : illegal access of the field '").append(field.getName()).append("'");
                    throw new InvocationException(description.toString(), e);
                }
            }
        },

        /** Define access by getter method. */
        GETTER {
            @Override
            <V> V getValue(final Object object, final Field field, final boolean accessible) throws InvocationException {
                final Method method = InvocationUtil.getGetter(object, field);
                V value;
                if (accessible) {
                    value = InvocationUtil.invokeMethod(method, object);
                } else {
                    boolean isAccessible;
                    isAccessible = method.isAccessible();
                    method.setAccessible(true);
                    value = InvocationUtil.invokeMethod(method, object);
                    method.setAccessible(isAccessible);
                }
                return value;
            }
        };

        /**
         * Get value from a field.
         * @param object object to get field value.
         * @param field the field.
         * @param accessible if false, return value even if the value is not accessible (exist, but not public).
         * @param <V> the returned value type.
         * @return value of given field.
         * @throws InvocationException invocation exception when invoke method problem
         */
        abstract <V> V getValue(final Object object, final Field field, final boolean accessible) throws InvocationException;
    }

    /**
     * Null safe method invocation.
     * @param method method to invoke
     * @param obj object on which we will invoke the method
     * @param args the arguments used for the method call
     * @param <V> the returned value type.
     * @return value of given getter method
     * @throws InvocationException on exception
     */
    @SuppressWarnings("unchecked")
    public static <V> V invokeMethod(final Method method, final Object obj, final Object... args) throws InvocationException {
        V result = null;
        if (method != null) {
            try {
                result = (V) method.invoke(obj, args);
            } catch (final IllegalArgumentException e) {
                log.error("Illegal argument exception when invoking method", e);
                final StringBuilder description = new StringBuilder();
                description.append("Search : inappropriate argument in the method '").append(method.getName()).append("' call");
                throw new InvocationException(description.toString(), e);
            } catch (final IllegalAccessException e) {
                log.error("Illegal access exception when invoking method", e);
                final StringBuilder description = new StringBuilder();
                description.append("Search : illegal access of the method '").append(method.getName()).append("'");
                throw new InvocationException(description.toString(), e);
            } catch (final InvocationTargetException e) {
                log.error("Invocation target exception when invoking method", e);
                final StringBuilder description = new StringBuilder();
                description.append("Search : error during the method '").append(method.getName()).append("' invocation");
                throw new InvocationException(description.toString(), e);
            }
        }
        return result;
    }

    /**
     * Retrieves {@link Method} for given class, and method name. Null safe.
     * @param modelClass entity class
     * @param methodName method name
     * @param parameterTypes the list of parameters
     * @return method.
     * @throws InvocationException exception.
     */
    public static Method getMethod(final Class<?> modelClass, final String methodName, final Class<?>... parameterTypes) throws InvocationException {
        if (modelClass == null || StringUtil.isEmpty(methodName)) {
            return null;
        }
        Class<?> superClass = modelClass;
        while (superClass != null) {
            for (final Method method : superClass.getDeclaredMethods()) {
                if (method.getName().equals(methodName) && Arrays.equals(parameterTypes, method.getParameterTypes())) {
                    return method;
                }
            }
            superClass = superClass.getSuperclass();
        }

        final StringBuilder description = new StringBuilder();
        description.append("methodName named ").append(methodName);
        description.append(" for class ").append(modelClass.getName());
        description.append(" does not exist!");
        log.error(description.toString());
        throw new InvocationException(description.toString());
    }

    /**
     * Retrieves {@link Method} for given class, and method name. Null safe.
     * @param <T> the object type.
     * @param objectClass object class
     * @param parameterTypes the list of parameters
     * @return method.
     * @throws InvocationException exception.
     */
    public static <T> Constructor<T> getConstructor(final Class<T> objectClass, final Class<?>... parameterTypes) throws InvocationException {
        final Constructor<T> constructor = getConstructorRecursively(objectClass, parameterTypes);
        if (constructor == null) {
            final StringBuilder description = new StringBuilder();
            description.append("getMethod : constructor for class ");
            description.append(objectClass.getName());
            description.append(" is not accessible or doesn't exist!");
            log.trace(description.toString());
            throw new InvocationException(description.toString());
        } else {
            return constructor;
        }
    }

    /**
     * Retrieves {@link Method} for given class, and method name. Null safe.
     * @param <T> the object type.
     * @param objectClass object class
     * @param parameterTypes the list of parameters
     * @return method.
     * @throws InvocationException exception.
     */
    private static <T> Constructor<T> getConstructorRecursively(final Class<T> objectClass,
            final Class<?>... parameterTypes) throws InvocationException {
        try {
            return objectClass.getDeclaredConstructor(parameterTypes);
        } catch (final NoSuchMethodException e) {
            log.trace("Constructor with parameters {} not found on {}", new Object[] {parameterTypes, objectClass, e});
            Constructor<T> constructor = null;
            if (!ArrayUtil.isEmpty(parameterTypes)) {
                for (int index = 0; index < parameterTypes.length && constructor == null; index++) {
                    Class<?> superclass = parameterTypes[index].getSuperclass();
                    final Class<?> indexClass = parameterTypes[index];
                    while (superclass != null && constructor == null) {
                        parameterTypes[index] = superclass;
                        constructor = getConstructorRecursively(objectClass, parameterTypes);
                        superclass = superclass.getSuperclass();
                    }
                    parameterTypes[index] = indexClass;
                }
            }
            return constructor;
        }
    }

    /**
     * Retrieves {@link Method} for given class, and method name. Null safe.
     * @param modelClass entity class
     * @param methodName method name
     * @param parameterTypes the list of parameters
     * @return method.
     */
    public static boolean hasMethod(final Class<?> modelClass, final String methodName, final Class<?>... parameterTypes) {
        if (modelClass != null && StringUtil.isNotEmpty(methodName)) {
            try {
                modelClass.getMethod(methodName, parameterTypes);
                return true;
            } catch (final SecurityException | NoSuchMethodException e) {
                log.trace(e.getMessage(), e);
                return false;
            }
        }
        return false;
    }

    /**
     * Set value to a field.
     * @param object object to set value.
     * @param field the field
     * @param newValue the new value
     * @throws InvocationException invocation exception when invoke method problem.
     */
    public static void setValue(final Object object, final Field field, final Object newValue) throws InvocationException {
        final Method m = getSetter(object, field);
        InvocationUtil.invokeMethod(m, object, newValue);
    }

    /**
     * Return the setter method.
     * @param object the object to get setter method.
     * @param field the object's field.
     * @return the setter method.
     * @throws InvocationException exception.
     */
    public static Method getSetter(final Object object, final Field field) throws InvocationException {
        return InvocationUtil.getMethod(object.getClass(), getSetterName(field), field.getType());
    }

    /**
     * Return the getter method.
     * @param object the object to get getter method.
     * @param field the object's field.
     * @return the getter method.
     * @throws InvocationException exception.
     */
    public static Method getGetter(final Object object, final Field field) throws InvocationException {
        return InvocationUtil.getMethod(object.getClass(), getGetterName(field));
    }

    /**
     * Return true if setter method exists.
     * @param field the field.
     * @return true/false
     * @throws InvocationException exception.
     */
    public static boolean hasSetter(final Field field) throws InvocationException {
        return hasMethod(field.getDeclaringClass(), getSetterName(field), field.getType());
    }

    /**
     * Return true if getter method exists.
     * @param field the field.
     * @return true/false
     * @throws InvocationException exception.
     */
    public static boolean hasGetter(final Field field) throws InvocationException {
        return hasMethod(field.getDeclaringClass(), getGetterName(field));
    }

    /**
     * Return the setter method name of a field.
     * @param field the field.
     * @return the setter method name.
     */
    private static String getSetterName(final Field field) {
        return "set" + StringUtil.capitalize(field.getName());
    }

    /**
     * Return the getter method name of a field.
     * @param field the field.
     * @return the getter method name.
     */
    private static String getGetterName(final Field field) {
        if (field.getType().equals(boolean.class)) {
            return "is" + StringUtil.capitalize(field.getName());
        } else {
            return "get" + StringUtil.capitalize(field.getName());
        }
    }

    /**
     * Set value to a field with setter method. If setter method doesn't exist, set value directly to the member.
     * @param object object to set value.
     * @param field the field
     * @param newValue the new value
     * @throws InvocationException invocation exception when invoke method problem.
     */
    public static void injectValue(final Object object, final Field field, final Object newValue) throws InvocationException {
        // Try to used setter method if exist.
        final String setter = getSetterName(field);
        if (hasMethod(object.getClass(), setter, field.getType())) {
            final Method m = InvocationUtil.getMethod(object.getClass(), setter, field.getType());
            InvocationUtil.invokeMethod(m, object, newValue);
        } else {
            // Setter method doesn't exist.
            // Set value to member directly.
            field.setAccessible(true);
            try {
                field.set(object, newValue);
            } catch (final Exception e) {
                log.error(" Cannot set value on class " + object.getClass(), e);
                throw new InvocationException("Cannot set value on class " + object.getClass(), e);
            }
        }

    }

    /**
     * Get value from a field.
     * @param object object to get value.
     * @param field the field
     * @param <V> the returned value type.
     * @return value of given getter method
     * @throws InvocationException invocation exception when invoke method problem
     */
    public static <V> V getValue(final Object object, final Field field) throws InvocationException {
        return getValue(object, field, AccessType.GETTER, true);
    }

    /**
     * Get value from a field.
     * @param object object to get field value.
     * @param name the field name.
     * @param <V> the returned value type.
     * @return value of given getter method
     * @throws InvocationException invocation exception when invoke method problem
     */
    public static <V> V getValue(final Object object, final String name) throws InvocationException {
        if (object == null || StringUtil.isEmpty(name)) {
            return null;
        }
        final Field field = InvocationUtil.getField(object.getClass(), name);
        return getValue(object, field);
    }

    /**
     * Get value from a field.
     * @param object object to get field value.
     * @param field the field.
     * @param accessType define how to read field (by property (getter) of field)
     * @param accessible if false, return value even if the value is not accessible (exist, but not public).
     * @param <V> the returned value type.
     * @return value of given getter method
     * @throws InvocationException invocation exception when invoke method problem
     */
    public static <V> V getValue(final Object object, final Field field, final AccessType accessType,
            final boolean accessible) throws InvocationException {

        if (object == null || field == null || accessType == null) {
            return null;
        }
        return accessType.getValue(object, field, accessible);
    }

    /**
     * Retrieves all declared fields for given class, including inherited ones.
     * @param objectClass the instance
     * @return the field list.
     */
    public static List<Field> getFields(final Class<?> objectClass) {
        if (objectClass == null) {
            return ListUtil.toList();
        }
        final List<Field> fields = ListUtil.toList(objectClass.getDeclaredFields());
        Class<?> superclass = objectClass.getSuperclass();
        while (superclass != null) {
            fields.addAll(ListUtil.toList(superclass.getDeclaredFields()));
            superclass = superclass.getSuperclass();
        }
        Field field;
        for (final Iterator<Field> iter = fields.iterator(); iter.hasNext();) {
            field = iter.next();
            if (field.getName().contains("this$0")) {
                iter.remove();
            }
        }
        return fields;
    }

    /**
     * Retrieves declared field for given class, including inherited ones.
     * @param objectClass the instance
     * @param name the name of the field to get.
     * @return the field.
     */
    public static Field getField(final Class<?> objectClass, final String name) {
        if (objectClass == null || StringUtil.isEmpty(name)) {
            return null;
        }
        final List<Field> fields = getFields(objectClass);
        for (final Field field : fields) {
            if (field.getName().equals(name)) {
                return field;
            }
        }
        return null;
    }

    /**
     * Retrieves declared field for given class, including inherited ones.
     * @param objectClass the instance
     * @param fieldClass the first field class of the field to get.
     * @return the field.
     */
    public static Field getField(final Class<?> objectClass, final Class<?> fieldClass) {
        if (objectClass == null || fieldClass == null) {
            return null;
        }
        final List<Field> fields = getFields(objectClass);
        for (final Field field : fields) {
            if (field.getType().equals(fieldClass)) {
                return field;
            }
        }
        return null;
    }

    /**
     * Checks if current field type implements Collection.
     * @param field current field
     * @param clazz Class to test
     * @return true if current field is a collection
     */
    @SuppressWarnings("rawtypes")
    public static boolean isFromInterfaceType(final Field field, final Class clazz) {
        if (field != null && clazz != null) {
            final List<Class> fullInheritance = ListUtil.toList();
            Class classType = field.getType();
            while (classType != null) {
                fullInheritance.add(classType.getClass());
                fullInheritance.addAll(getInterfaceInheritance(classType));
                classType = classType.getSuperclass();
            }
            return fullInheritance.contains(clazz);
        }
        return false;
    }

    /**
     * Checks if current class type implements the other clazz.
     * @param currentClazz current class
     * @param clazz Class to test
     * @return true if current field is a collection
     */
    @SuppressWarnings("rawtypes")
    public static boolean isFromInterfaceType(final Class currentClazz, final Class clazz) {
        if (currentClazz != null && clazz != null) {
            final List<Class> fullInheritance = ListUtil.toList();
            Class classType = currentClazz;
            while (classType != null) {
                fullInheritance.add(classType.getClass());
                fullInheritance.addAll(getInterfaceInheritance(classType));
                classType = classType.getSuperclass();
            }
            return fullInheritance.contains(clazz);
        }
        return false;
    }

    /**
     * Returns interface inheritance.
     * @param clazz current class
     * @return list of all inherited interfaces
     */
    @SuppressWarnings("rawtypes")
    private static List<Class> getInterfaceInheritance(final Class clazz) {
        final List<Class> inheritance = ListUtil.toList();
        for (final Class clInt : clazz.getInterfaces()) {
            inheritance.add(clInt);
            inheritance.addAll(getInterfaceInheritance(clInt));
        }
        return inheritance;
    }

    /**
     * Find if a field is persistable or not (private and not static, final, transient).
     * @param field the field
     * @return true if this is a persisted field.
     */
    public static boolean isPersistable(final Field field) {
        final int mod = field.getModifiers();
        return Modifier.isPrivate(mod) && !(Modifier.isStatic(mod) || Modifier.isFinal(mod) || Modifier.isTransient(mod));
    }

}
