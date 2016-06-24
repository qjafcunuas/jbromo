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
package org.jbromo.model.jpa.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.jbromo.common.CollectionUtil;
import org.jbromo.common.ListUtil;
import org.jbromo.common.MapUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.SetUtil;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.common.invocation.InvocationUtil.AccessType;
import org.jbromo.dao.jpa.container.common.JpaProviderFactory;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.compositepk.ICompositePk;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for entities.
 * @author qjafcunuas
 */
@Slf4j
public final class EntityUtil {

    /**
     * Define persisted fields by entity class.
     */
    private static final Map<Class<?>, List<Field>> PERSISTED_FIELDS = MapUtil.toSynchronizedMap();

    /**
     * Define primary key field by entity class.
     */
    private static final Map<Class<?>, Field> PRIMARY_KEY_FIELDS = MapUtil.toSynchronizedMap();

    /**
     * Define maps id fields by entity class.
     */
    private static final Map<Class<?>, List<Field>> MAPS_ID_FIELDS = MapUtil.toSynchronizedMap();

    /**
     * This instance is only here for compilation error if method getPrimaryKey is changed, because this method is used for reflexion in this class.
     */
    @SuppressWarnings("unused")
    private static final IEntity<Integer> RUBBISH = new IEntity<Integer>() {
        /** serial version UID. */
        private static final long serialVersionUID = -7638780800744880877L;

        @Override
        public Integer getPrimaryKey() {
            return null;
        }

    };

    /**
     * Default constructor.
     */
    private EntityUtil() {
        super();
    }

    /**
     * Return primary keys of entities.
     * @param <E> the entity type.
     * @param
     *            <P>
     *            the primary key type.
     * @param entities the entities to get primary Keys.
     * @return the primary keys.
     */
    @SuppressWarnings("unchecked")
    public static <E extends IEntity<P>, P extends Serializable> Collection<P> getPrimaryKeys(final Collection<E> entities) {
        if (entities == null) {
            return null;
        }
        final Set<P> primaryKeys = SetUtil.toSet();
        for (final E entity : entities) {
            primaryKeys.add(entity.getPrimaryKey());
        }
        return CollectionUtil.toCollection(primaryKeys, entities.getClass());
    }

    /**
     * Map entities by primary key.
     * @param <E> the entity type.
     * @param
     *            <P>
     *            the primary key type.
     * @param entities the entities to map.
     * @return the mapped entities.
     */
    public static <E extends IEntity<P>, P extends Serializable> Map<P, E> mapByPk(final Collection<E> entities) {
        if (entities == null) {
            return null;
        }
        final Map<P, E> map = MapUtil.toMap();
        for (final E entity : entities) {
            map.put(entity.getPrimaryKey(), entity);
        }
        return map;
    }

    /**
     * Return true if object is an entity.
     * @param obj the object.
     * @return true/false.
     */
    public static boolean isEntity(final Object obj) {
        return IEntity.class.isInstance(obj);
    }

    /**
     * Find if a class extends IEntity.
     * @param clazz the class
     * @return true if implements IEntity, false otherwise
     */
    public static boolean isEntity(final Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        for (final Class<?> interf : clazz.getInterfaces()) {
            if (IEntity.class.equals(interf)) {
                return true;
            }
        }
        return isEntity(clazz.getSuperclass());
    }

    /**
     * This method return the entity primary key, even if the entity is on lazy loading.
     * @param <E> the entity type.
     * @param
     *            <P>
     *            the primary key type.
     * @param entity the entity to get primary key.
     * @return the entity primary key.
     */
    public static <E extends IEntity<P>, P extends Serializable> P getPrimaryKey(final E entity) {
        if (entity == null) {
            return null;
        }
        try {
            final Field fieldPk = EntityUtil.getPrimaryKeyField(entity.getClass());
            return InvocationUtil.getValue(entity, fieldPk);
        } catch (final Exception e) {
            log.error("Cannot read primary key", e);
            return null;
        }
    }

    /**
     * This method return the entity primary key, even if the entity is on lazy loading. According to the JPA provider, the return value is read with
     * the getter method or directly from the field.
     * @param <E> the entity type.
     * @param
     *            <P>
     *            the primary key type.
     * @param entity the entity to get primary key.
     * @return the entity primary key.
     */
    public static <E extends IEntity<P>, P extends Serializable> P readPrimaryKey(final E entity) {
        if (JpaProviderFactory.getInstance().getImplementation() == null
            || JpaProviderFactory.getInstance().getImplementation().isCompositePrimaryKeyUpdatedDuringPersist()) {
            return getPrimaryKey(entity);
        } else {
            try {
                return InvocationUtil.getValue(entity, EntityUtil.getPrimaryKeyField(getPersistentClass(entity)), AccessType.FIELD, false);
            } catch (final InvocationException e) {
                // Should not happened.
                log.error("Cannot read pk for class {}", entity.getClass(), e);
                return null;
            }
        }
    }

    /**
     * Return true if entity is a lazy loading entity, else return false.
     * @param <E> the entity type.
     * @param entity the entity to test.
     * @return true/false.
     */
    public static <E extends IEntity<?>> boolean isLazy(final E entity) {
        return !isEager(entity);
    }

    /**
     * Return true if entity is a eager loading entity, else return false.
     * @param <E> the entity type.
     * @param entity the entity to test.
     * @return true/false.
     */
    public static <E extends IEntity<?>> boolean isEager(final E entity) {
        return getClass(entity).equals(entity.getClass());
    }

    /**
     * Return the class of an entity.
     * @param <E> the entity type.
     * @param entity the entity to get class.
     * @return the entity class.
     */
    @SuppressWarnings("unchecked")
    public static <E extends IEntity<?>> Class<E> getClass(final E entity) {
        if (entity == null) {
            return null;
        }
        return (Class<E>) getClass(entity.getClass());
    }

    /**
     * Return the class of an entity.
     * @param <E> the entity type.
     * @param entityClass the entity class to get class.
     * @return the entity class.
     */
    @SuppressWarnings("unchecked")
    public static <E extends IEntity<?>> Class<E> getClass(final Class<E> entityClass) {
        if (entityClass == null) {
            return null;
        }
        String className = entityClass.getName();
        final int index = className.indexOf("_$$_");
        if (index > 0) {
            className = className.substring(0, index);
            try {
                return (Class<E>) Class.forName(className);
            } catch (final ClassNotFoundException e) {
                log.error("Cannot get class of " + entityClass, e);
                return null;
            }
        } else {
            return entityClass;
        }
    }

    /**
     * Return true if entity or entity's primary key is null.
     * @param <E> the entity type.
     * @param
     *            <P>
     *            the primary key type.
     * @param entity the entity.
     * @return true/false.
     */
    public static <E extends IEntity<P>, P extends Serializable> boolean isNullPk(final E entity) {
        if (entity == null) {
            return true;
        }
        final Field fieldPk = getPrimaryKeyField(entity.getClass());
        final P pk = getPrimaryKey(entity);
        if (pk == null) {
            return true;
        }
        if (!isEmbeddedId(fieldPk)) {
            return false;
        }
        try {
            // One field is not null ?
            Object value;
            IEntity<?> cast;
            for (final Field field : getPersistedFields(fieldPk.getType())) {
                value = InvocationUtil.getValue(pk, field);
                if (value != null) {
                    if (EntityUtil.isManyToOne(field)) {
                        cast = ObjectUtil.cast(value, IEntity.class);
                        if (isNotNullPk(cast)) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
        } catch (final InvocationException e) {
            log.trace(e.getMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * Return true if entity and it's primary key are not null.
     * @param <E> the entity type.
     * @param
     *            <P>
     *            the primary key type.
     * @param entity the entity.
     * @return true/false.
     */
    public static <E extends IEntity<P>, P extends Serializable> boolean isNotNullPk(final E entity) {
        return !isNullPk(entity);
    }

    /**
     * Cast a class to an entity class.
     * @param
     *            <P>
     *            the primary key type.
     * @param objectClass the object class to cast.
     * @return the entity class.
     */
    @SuppressWarnings("unchecked")
    public static <P extends Serializable> Class<IEntity<P>> cast(final Class<?> objectClass) {
        return (Class<IEntity<P>>) objectClass;
    }

    /**
     * Get all the persisted fields.
     * @param objectClass the object class.
     * @return the field list.
     */
    public static List<Field> getPersistedFields(final Class<?> objectClass) {
        if (PERSISTED_FIELDS.containsKey(objectClass)) {
            return PERSISTED_FIELDS.get(objectClass);
        } else {
            List<Field> fields = InvocationUtil.getFields(objectClass);
            // Filter the list for persisted elements.
            final List<Field> clone = ListUtil.toList(fields);
            for (final Field field : clone) {
                if (!isPersisted(field)) {
                    fields.remove(field);
                }
            }
            // Set list unmodified.
            fields = ListUtil.toUnmodifiableList(fields);
            PERSISTED_FIELDS.put(objectClass, fields);
            return fields;
        }
    }

    /**
     * Get all the persisted fields.
     * @param object the object.
     * @return the field list.
     */
    public static List<Field> getPersistedFields(final Object object) {
        return getPersistedFields(getPersistentClass(object));
    }

    /**
     * Return the persisted clas of an object. For hibernate proxy, return the entity class, not the proxy class.
     * @param object the object.
     * @return the persisted class.
     */
    public static Class<?> getPersistentClass(final Object object) {
        if (object == null) {
            return null;
        } else if (JpaProviderFactory.getInstance().getImplementation() == null) {
            return object.getClass();
        } else {
            return JpaProviderFactory.getInstance().getImplementation().getPersistentClass(object);
        }
    }

    /**
     * Get all the maps id fields.
     * @param objectClass the object class.
     * @return the field list.
     */
    public static List<Field> getMapsIdFields(final Class<?> objectClass) {
        if (MAPS_ID_FIELDS.containsKey(objectClass)) {
            return MAPS_ID_FIELDS.get(objectClass);
        } else {
            final List<Field> fields = getPersistedFields(objectClass);
            // Filter the list for mapsId elements.
            List<Field> clone = ListUtil.toList(fields);
            for (final Field field : fields) {
                if (!isMapsId(field)) {
                    clone.remove(field);
                }
            }
            // Set list unmodified.
            clone = ListUtil.toUnmodifiableList(clone);
            MAPS_ID_FIELDS.put(objectClass, clone);
            return clone;
        }
    }

    /**
     * Return the primary key field of the entity.
     * @param objectClass the entity class.
     * @return the primary key field.
     */
    public static Field getPrimaryKeyField(final Class<?> objectClass) {
        if (PRIMARY_KEY_FIELDS.containsKey(objectClass)) {
            return PRIMARY_KEY_FIELDS.get(objectClass);
        } else {
            final List<Field> fields = InvocationUtil.getFields(objectClass);
            for (final Field field : fields) {
                if (isPrimaryKey(field)) {
                    PRIMARY_KEY_FIELDS.put(objectClass, field);
                    return field;
                }
            }
            return null;
        }
    }

    /**
     * Get all the persisted fields.
     * @param field the parent field.
     * @return the field list.
     */
    public static List<Field> getPersistedFields(final Field field) {
        return getPersistedFields(field.getType());
    }

    /**
     * Find if a field is persisted (private and not static, final, transient).
     * @param field the field
     * @return true if this is a persisted field.
     */
    public static boolean isPersisted(final Field field) {
        if (isPrimaryKey(field)) {
            return true;
        }
        if (isTransient(field)) {
            return false;
        }
        if (isEmbedded(field)) {
            return true;
        }
        return InvocationUtil.isPersistable(field);
    }

    /**
     * Return true if Embedded annotation is define on a field.
     * @param field the field
     * @return true/false.
     */
    public static boolean isEmbedded(final Field field) {
        return field.getAnnotation(Embedded.class) != null;
    }

    /**
     * Return true if Transient annotation is define on a field.
     * @param field the field
     * @return true/false.
     */
    public static boolean isTransient(final Field field) {
        return field.getAnnotation(Transient.class) != null;
    }

    /**
     * Return true if Id annotation is define on a field.
     * @param field the field
     * @return true/false.
     */
    public static boolean isId(final Field field) {
        return field.getAnnotation(Id.class) != null;
    }

    /**
     * Return true if MapsId annotation is define on a field.
     * @param field the field
     * @return true/false.
     */
    public static boolean isMapsId(final Field field) {
        return field.getAnnotation(MapsId.class) != null;
    }

    /**
     * Return mapsId field values.
     * @param entity the entity to get field values.
     * @return the map with mapsId value as key, and composite entity as value.
     */
    public static Map<String, IEntity<? extends ICompositePk>> getMapsId(final IEntity<? extends Serializable> entity) {
        if (entity == null) {
            return null;
        }
        final Map<String, IEntity<? extends ICompositePk>> map = MapUtil.toMap();
        String name;
        IEntity<? extends ICompositePk> composite;
        for (final Field field : getMapsIdFields(entity.getClass())) {
            try {
                name = getMapsIdValue(field);
                composite = InvocationUtil.getValue(entity, field);
                map.put(name, composite);
            } catch (final InvocationException e) {
                log.error("Cannot get value", e);
                return null;
            }
        }
        return map;
    }

    /**
     * Get field name from MapsId annotation.
     * @param field the field
     * @return the field name.
     */
    public static String getMapsIdValue(final Field field) {
        final MapsId m = field.getAnnotation(MapsId.class);
        return m == null ? null : m.value();
    }

    /**
     * Get field value from an object according to the value of the MapsId annotation.
     * @param <E> the entity type.
     * @param
     *            <P>
     *            the primary key type.
     * @param entity the entity to get mapsId field.
     * @param value the mapsId value to get field.
     * @return the field value.
     */
    public static <E extends IEntity<P>, P extends ICompositePk> Object getMapsIdFieldValue(final E entity, final String value) {
        if (entity == null || value == null) {
            return null;
        }
        // For each field with MapsId annotation
        for (final Field field : getMapsIdFields(entity.getClass())) {
            // If the field is the one we searched
            if (value.equals(getMapsIdValue(field))) {
                try {
                    return InvocationUtil.getValue(entity, field);
                } catch (final InvocationException e) {
                    log.error("Cannot get value", e);
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * Return true if EmbeddedId annotation is define on a field.
     * @param field the field
     * @return true/false.
     */
    public static boolean isEmbeddedId(final Field field) {
        return field.getAnnotation(EmbeddedId.class) != null;
    }

    /**
     * Return true if field has a primary key annotation.
     * @param field the field
     * @return true/false.
     */
    public static boolean isPrimaryKey(final Field field) {
        return isId(field) || isEmbeddedId(field);
    }

    /**
     * Return true if GeneratedValue annotation is define on a field.
     * @param field the field
     * @return true/false.
     */
    public static boolean isGeneratedValue(final Field field) {
        return field.getAnnotation(GeneratedValue.class) != null;
    }

    /**
     * Return true if field is nullable, else false. A field is not nullable if there is a Column or JoinColumn annotation with nullable set to false,
     * or ManyToOne annotation with optional set to false.
     * @param field the field.
     * @return true/false.
     */
    public static boolean isNullable(final Field field) {
        final Column column = field.getAnnotation(Column.class);
        if (column != null) {
            return column.nullable();
        }
        final JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
        if (joinColumn != null) {
            return joinColumn.nullable();
        }
        final ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
        if (manyToOne != null) {
            return manyToOne.optional();
        }
        return true;
    }

    /**
     * Get unique value in Column or JoinColumn annotation.
     * @param field the field
     * @return true if unique, false if not Column annotation or not unique.
     */
    public static boolean isUnique(final Field field) {
        final Column column = field.getAnnotation(Column.class);
        if (column != null) {
            return column.unique();
        }
        final JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
        if (joinColumn != null) {
            return joinColumn.unique();
        }
        return false;
    }

    /**
     * Find if a OneToOne or OneToMany annotation exits and if it's the case, verify if the fetch is LAZY.
     * @param field the field
     * @return true if eager, false otherwise
     */
    public static boolean isLazy(final Field field) {
        final OneToOne oneOne = field.getAnnotation(OneToOne.class);
        if (oneOne != null) {
            // By Default Eager
            return oneOne.fetch().equals(FetchType.LAZY);
        }
        final ManyToOne manyOne = field.getAnnotation(ManyToOne.class);
        if (manyOne != null) {
            // By Default Eager
            return manyOne.fetch().equals(FetchType.LAZY);
        }
        final OneToMany oneMany = field.getAnnotation(OneToMany.class);
        if (oneMany != null) {
            // By Default Lazy
            return oneMany.fetch().equals(FetchType.LAZY);
        }
        final ManyToMany manyMany = field.getAnnotation(ManyToMany.class);
        if (manyMany != null) {
            // By Default Lazy
            return manyMany.fetch().equals(FetchType.LAZY);
        }
        // Other case, no problem
        return true;
    }

    /**
     * Return the length from @Column.
     * @param field the field
     * @return null if no column annotation, the length otherwise
     */
    public static Integer getColumnLength(final Field field) {
        final Column column = field.getAnnotation(Column.class);
        return column == null ? null : column.length();
    }

    /**
     * Return the scale from @Column.
     * @param field the field
     * @return null if no column annotation, the scale otherwise
     */
    public static Integer getColumnScale(final Field field) {
        final Column column = field.getAnnotation(Column.class);
        return column == null ? null : column.scale();
    }

    /**
     * Return the precision from @Column.
     * @param field the field
     * @return null if no column annotation, the precision otherwise
     */
    public static Integer getColumnPrecision(final Field field) {
        final Column column = field.getAnnotation(Column.class);
        return column == null ? null : column.precision();
    }

    /**
     * Return true if class is annotated as Embeddable.
     * @param objectClass the object class.
     * @return true/false.
     */
    public static boolean isEmbeddable(final Class<?> objectClass) {
        return objectClass.isAnnotationPresent(Embeddable.class);
    }

    /**
     * Return true if ManyToMany annotation is defined on a field.
     * @param field the field
     * @return true/false.
     */
    public static boolean isManyToMany(final Field field) {
        return field.getAnnotation(ManyToMany.class) != null;
    }

    /**
     * Return true if OneToOne annotation is defined on a field.
     * @param field the field
     * @return true/false.
     */
    public static boolean isOneToOne(final Field field) {
        return field.getAnnotation(OneToOne.class) != null;
    }

    /**
     * Return true if OneToMany annotation is defined on a field.
     * @param field the field
     * @return true/false.
     */
    public static boolean isOneToMany(final Field field) {
        return field.getAnnotation(OneToMany.class) != null;
    }

    /**
     * Return the mappedBy attribute of the OneToMany annotation.
     * @param field the field
     * @return the attribute.
     */
    public static String getOneToManyMappedBy(final Field field) {
        final OneToMany m = field.getAnnotation(OneToMany.class);
        return m == null ? null : m.mappedBy();
    }

    /**
     * Return true if ManyToOne annotation is defined on a field.
     * @param field the field
     * @return true/false.
     */
    public static boolean isManyToOne(final Field field) {
        return field.getAnnotation(ManyToOne.class) != null;
    }

}
