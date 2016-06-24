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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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
import javax.validation.constraints.NotNull;

import org.jbromo.common.CollectionUtil;
import org.jbromo.common.IntegerUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.SetUtil;
import org.jbromo.common.StringUtil;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.common.test.common.ConstructorUtil;
import org.jbromo.dao.jpa.container.common.MockJpaProviderFactory;
import org.jbromo.model.jpa.AbstractEntity;
import org.jbromo.model.jpa.AbstractEntityId;
import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.compositepk.AbstractCompositePk;
import org.jbromo.model.jpa.compositepk.ICompositePk;
import org.junit.Assert;
import org.junit.Test;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Test for the object util class.
 * @author qjafcunuas
 */
public class EntityUtilTest {

    /**
     * Define an entity with String primary key.
     */
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public class StringEntity extends AbstractEntity<String> {

        /**
         * serial version UID.
         */
        private static final long serialVersionUID = -4450907378336449924L;

        /**
         * The primary key.
         */
        @Id
        private String primaryKey;
    };

    /**
     * Define an entity with Integer primary key.
     */
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public class IntegerEntity extends AbstractEntity<Integer> {

        /**
         * serial version UID.
         */
        private static final long serialVersionUID = -4450907378336449924L;

        /**
         * The primary key.
         */
        @Id
        private Integer primaryKey;
    };

    /**
     * Define an Hibernate entity with Integer primary key.
     */
    public class IntegerEntity_$$_javassist extends IntegerEntity {

        /**
         * serial version UID.
         */
        private static final long serialVersionUID = -4450907378336449924L;

    };

    /**
     * Define an Hibernate entity with no existing class IntegerEntity2.
     */
    public class IntegerEntity2_$$_javassist extends IntegerEntity {

        /**
         * serial version UID.
         */
        private static final long serialVersionUID = -4450907378336449924L;

    };

    /**
     * Define an entity composite primary key.
     */
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public class CompositeEntityPk extends AbstractCompositePk {

        /**
         * serial version UID.
         */
        private static final long serialVersionUID = -4450907378336449932L;

        /**
         * The user.
         */
        @Column(name = "USER_ID", nullable = false)
        private Integer userId;

        /**
         * The city.
         */
        @Column(name = "CITY_ID", nullable = false)
        private Integer cityId;

        /**
         * Default constructor.
         * @param entity the entity of the primary key.
         */
        public CompositeEntityPk(final CompositeEntity entity) {
            super(entity);
            if (entity != null) {
                if (entity.getUser() != null) {
                    this.userId = entity.getUser().getPrimaryKey();
                }
                if (entity.getCity() != null) {
                    this.cityId = entity.getCity().getPrimaryKey();
                }
            }
        }
    };

    /**
     * Define an entity composite primary key.
     */
    @Getter
    @NoArgsConstructor
    public static class CompositeEntity extends AbstractEntity<CompositeEntityPk> {

        /**
         * serial version UID.
         */
        private static final long serialVersionUID = -4450907378336449947L;

        /**
         * The primary key.
         */
        @EmbeddedId
        private CompositeEntityPk primaryKey = new EntityUtilTest().new CompositeEntityPk(this);

        /**
         * The city.
         */
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @MapsId("userId")
        @Getter
        @Setter(AccessLevel.NONE)
        @NotNull
        private IntegerEntity user;

        /**
         * Another pk, not functionnaly sense, only for having composite pk example.
         */
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @MapsId("cityId")
        @Getter
        @Setter(AccessLevel.NONE)
        @NotNull
        private IntegerEntity city;

        /**
         * The primary key field.
         */
        static final Field FIELD_PRIMARY_KEY = InvocationUtil.getField(CompositeEntity.class, "primaryKey");

        /**
         * The user field.
         */
        static final Field FIELD_USER = InvocationUtil.getField(CompositeEntity.class, "user");

        /**
         * The city field.
         */
        static final Field FIELD_CITY = InvocationUtil.getField(CompositeEntity.class, "city");

        /**
         * Default constructor.
         * @param user the user.
         * @param city the city.
         */
        public CompositeEntity(final IntegerEntity user, final IntegerEntity city) {
            super();
            this.city = city;
            this.user = user;
            this.primaryKey = new EntityUtilTest().new CompositeEntityPk(this);
        }
    };

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        ConstructorUtil.executePrivate(EntityUtil.class);
    }

    /**
     * Test RUBBISH object.
     */
    @Test
    public void rubbish() {
        try {
            final Field field = InvocationUtil.getField(EntityUtil.class, "RUBBISH");
            if (Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true);
                Assert.assertNotNull(field.get(null));
                final IEntity<?> rubbish = ObjectUtil.cast(field.get(null), IEntity.class);
                Assert.assertNull(rubbish.getPrimaryKey());
            } else {
                Assert.fail("Object RUBBISH should be private!");
            }
        } catch (final Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the getPrimaryKeys method.
     */
    @Test
    public void getPrimaryKeys() {
        Assert.assertNull(EntityUtil.getPrimaryKeys(null));

        final List<IntegerEntity> entities = new ArrayList<IntegerEntity>();
        final List<Integer> primaryKeys = new ArrayList<Integer>();

        IntegerEntity entity;
        for (int i = IntegerUtil.INT_1; i < IntegerUtil.INT_100; i++) {
            entity = new IntegerEntity(i);
            entities.add(entity);
            primaryKeys.add(entity.getPrimaryKey());
        }

        Collection<Integer> pks = EntityUtil.getPrimaryKeys(entities);
        Assert.assertTrue(CollectionUtil.containsAll(primaryKeys, pks, false));

        // Null pk.
        entity = new IntegerEntity();
        entities.add(entity);
        pks = EntityUtil.getPrimaryKeys(entities);
        primaryKeys.add(null);
        Assert.assertTrue(CollectionUtil.containsAll(primaryKeys, pks, false));
    }

    /**
     * Test the mapByPk method.
     */
    @Test
    public void mapByPk() {
        Assert.assertNull(EntityUtil.mapByPk(null));

        final List<IntegerEntity> entities = new ArrayList<IntegerEntity>();
        final List<Integer> primaryKeys = new ArrayList<Integer>();

        IntegerEntity entity;
        for (int i = IntegerUtil.INT_1; i < IntegerUtil.INT_100; i++) {
            entity = new IntegerEntity(i);
            entities.add(entity);
            primaryKeys.add(entity.getPrimaryKey());
        }

        final Map<Integer, IntegerEntity> map = EntityUtil.mapByPk(entities);
        Assert.assertTrue(CollectionUtil.containsAll(primaryKeys, map.keySet(), false));
        Assert.assertTrue(CollectionUtil.containsAll(entities, map.values(), false));
    }

    /**
     * Test the isEntity(object method.
     */
    @Test
    public void isEntityObject() {
        Assert.assertTrue(EntityUtil.isEntity(new IntegerEntity()));
        Assert.assertTrue(EntityUtil.isEntity(new IntegerEntity_$$_javassist()));
        Assert.assertFalse(EntityUtil.isEntity(new Object()));
    }

    /**
     * Test the isEntity(class) method.
     */
    @Test
    public void isEntityClass() {
        Assert.assertTrue(EntityUtil.isEntity(AbstractEntity.class));
        Assert.assertTrue(EntityUtil.isEntity(AbstractEntityId.class));
        Assert.assertTrue(EntityUtil.isEntity(IntegerEntity.class));
        Assert.assertTrue(EntityUtil.isEntity(IntegerEntity_$$_javassist.class));
        Assert.assertFalse(EntityUtil.isEntity(Object.class));
        Assert.assertFalse(EntityUtil.isEntity(BigDecimal.class));
    }

    /**
     * Test the getPrimaryKey method.
     */
    @Test
    public void getPrimaryKeyEntity() {
        final IntegerEntity entity = new IntegerEntity(IntegerUtil.INT_1);
        Assert.assertNull(EntityUtil.getPrimaryKey(null));
        Assert.assertEquals(Integer.valueOf(IntegerUtil.INT_1), EntityUtil.getPrimaryKey(entity));
    }

    /**
     * Test the isEager method.
     */
    @Test
    public void isEager() {
        final IntegerEntity eager = new IntegerEntity();
        final IntegerEntity lazy = new IntegerEntity_$$_javassist();
        Assert.assertTrue(EntityUtil.isEager(eager));
        Assert.assertFalse(EntityUtil.isEager(lazy));
    }

    /**
     * Test the isLazy method.
     */
    @Test
    public void isLazy() {
        final IntegerEntity eager = new IntegerEntity();
        final IntegerEntity lazy = new IntegerEntity_$$_javassist();
        Assert.assertFalse(EntityUtil.isLazy(eager));
        Assert.assertTrue(EntityUtil.isLazy(lazy));
    }

    /**
     * Test the reSet method.
     */
    @Test
    public void reSet() {
        final Set<IntegerEntity> entities = new HashSet<IntegerEntity>();
        final Set<IntegerEntity> clone = new HashSet<IntegerEntity>();
        IntegerEntity entity;
        for (int i = IntegerUtil.INT_1; i < IntegerUtil.INT_100; i++) {
            entity = new IntegerEntity();
            entities.add(entity);
            entity.setPrimaryKey(i);
            entity = new IntegerEntity(i);
            clone.add(entity);
        }
        Assert.assertFalse(SetUtil.containsAll(entities, clone));
        SetUtil.reSet(entities);
        Assert.assertTrue(SetUtil.containsAll(entities, clone));
    }

    /**
     * Test the getClass method.
     */
    @Test
    public void getClassTest() {
        Assert.assertNull(EntityUtil.getClass((IEntity<?>) null));
        Assert.assertNull(EntityUtil.getClass((Class<IEntity<?>>) null));

        Assert.assertEquals(StringEntity.class, EntityUtil.getClass(new StringEntity()));
        Assert.assertNotEquals(IntegerEntity.class, EntityUtil.getClass(new StringEntity()));
        Assert.assertNotEquals(StringEntity.class, EntityUtil.getClass(new IntegerEntity_$$_javassist()));
        Assert.assertEquals(IntegerEntity.class, EntityUtil.getClass(new IntegerEntity_$$_javassist()));
        Assert.assertNull(EntityUtil.getClass(new IntegerEntity2_$$_javassist()));
    }

    /**
     * Test the isNullPk method.
     */
    @Test
    public void isNullPk() {
        Assert.assertTrue(EntityUtil.isNullPk((IEntity<?>) null));
        Assert.assertTrue(EntityUtil.isNullPk(new StringEntity()));
        Assert.assertFalse(EntityUtil.isNullPk(new StringEntity(StringUtil.SLASH)));

        Assert.assertTrue(EntityUtil.isNullPk(new CompositeEntity()));
        Assert.assertTrue(EntityUtil.isNullPk(new CompositeEntity(new IntegerEntity(), new IntegerEntity())));
        final IntegerEntity user = new IntegerEntity();
        user.setPrimaryKey(IntegerUtil.INT_1);
        final IntegerEntity city = new IntegerEntity();
        city.setPrimaryKey(IntegerUtil.INT_2);
        Assert.assertFalse(EntityUtil.isNullPk(new CompositeEntity(user, new IntegerEntity())));
        Assert.assertFalse(EntityUtil.isNullPk(new CompositeEntity(new IntegerEntity(), city)));
        Assert.assertFalse(EntityUtil.isNullPk(new CompositeEntity(user, city)));
    }

    /**
     * Test the isNullPk method.
     */
    @Test
    public void isNotNullPk() {
        Assert.assertFalse(EntityUtil.isNotNullPk((IEntity<?>) null));
        Assert.assertFalse(EntityUtil.isNotNullPk(new StringEntity()));
        Assert.assertTrue(EntityUtil.isNotNullPk(new StringEntity(StringUtil.SLASH)));

        Assert.assertFalse(EntityUtil.isNotNullPk(new CompositeEntity()));
        Assert.assertFalse(EntityUtil.isNotNullPk(new CompositeEntity(new IntegerEntity(), new IntegerEntity())));
        final IntegerEntity user = new IntegerEntity();
        user.setPrimaryKey(IntegerUtil.INT_1);
        final IntegerEntity city = new IntegerEntity();
        city.setPrimaryKey(IntegerUtil.INT_2);
        Assert.assertTrue(EntityUtil.isNotNullPk(new CompositeEntity(user, new IntegerEntity())));
        Assert.assertTrue(EntityUtil.isNotNullPk(new CompositeEntity(new IntegerEntity(), city)));
        Assert.assertTrue(EntityUtil.isNotNullPk(new CompositeEntity(user, city)));
    }

    /**
     * Test the cast method.
     */
    @Test
    public void cast() {
        final Class<?> objectClass = IntegerEntity.class;
        Assert.assertTrue(EntityUtil.cast(objectClass).equals(IntegerEntity.class));
    }

    /**
     * Define an object for testing persisted fields.
     * @author qjafcunuas
     */
    @Getter
    @Setter
    @SuppressWarnings("unused")
    public static class MyObject implements IEntity<ICompositePk> {
        /**
         * serial version uid.
         */
        private static final long serialVersionUID = -6748447188366659854L;

        /**
         * A static object.
         */
        private static Object theStatic;

        /**
         * A static final object.
         */
        private static final Object THE_STATIC_FINAL = new Object();

        /**
         * A persisted object.
         */
        @Id
        @GeneratedValue
        @Getter
        private ICompositePk primaryKey;

        /**
         * A transient object.
         */
        private transient Object theTransient;

        /**
         * A transient annotated object.
         */
        @Transient
        private Object theTransientAnno;

        /**
         * An embedded object.
         */
        @Embedded
        private final Object theEmbeddedAnno = new Object();

        /**
         * An embeddedId object.
         */
        @EmbeddedId
        private Object theEmbeddedIdAnno;

        /**
         * A MapsId object.
         */
        @MapsId("theId")
        private IEntity<? extends AbstractCompositePk> theMapsId;

        /**
         * The Embedded annotated field.
         */
        static final Field FIELD_EMBEDDED_ANNO = InvocationUtil.getField(MyObject.class, "theEmbeddedAnno");

        /**
         * The EmbeddedId annotated field.
         */
        static final Field FIELD_EMBEDDED_ID_ANNO = InvocationUtil.getField(MyObject.class, "theEmbeddedIdAnno");

        /**
         * The Id annotated field.
         */
        static final Field FIELD_ID_ANNO = InvocationUtil.getField(MyObject.class, "primaryKey");

        /**
         * The Transient annotated field.
         */
        static final Field FIELD_TRANSIENT_ANNO = InvocationUtil.getField(MyObject.class, "theTransientAnno");

        /**
         * The transient field.
         */
        static final Field FIELD_TRANSIENT = InvocationUtil.getField(MyObject.class, "theTransient");

        /**
         * The MapsId field.
         */
        static final Field FIELD_MAPS_ID_ANNO = InvocationUtil.getField(MyObject.class, "theMapsId");

    }

    /**
     * Define an object for testing persisted fields.
     * @author qjafcunuas
     */
    @Getter
    @Setter
    @Embeddable
    public static class MyUniqueObject {
        /**
         * An object without annotation.
         */
        private Object nothing;

        /**
         * A Column object.
         */
        @Column
        private Object theColumn;

        /**
         * A Column(unique=true) object.
         */
        @Column(unique = true)
        private Object theColumnUnique;

        /**
         * A Column(unique=false) object.
         */
        @Column(unique = false)
        private Object theColumnNotUnique;

        /**
         * A JoinColumn object.
         */
        @JoinColumn
        private Object theJoinColumn;

        /**
         * A JoinColumn(unique=true) object.
         */
        @JoinColumn(unique = true)
        private Object theJoinColumnUnique;

        /**
         * A JoinColumn(unique=false) object.
         */
        @JoinColumn(unique = false)
        private Object theJoinColumnNotUnique;

        /**
         * The Column annotated field.
         */
        static final Field FIELD_COLUMN_ANNO = InvocationUtil.getField(MyUniqueObject.class, "theColumn");

        /**
         * The Column(unique=true) annotated field.
         */
        static final Field FIELD_COLUMN_UNIQUE_ANNO = InvocationUtil.getField(MyUniqueObject.class, "theColumnUnique");

        /**
         * The Column(unique=false) annotated field.
         */
        static final Field FIELD_COLUMN_NOT_UNIQUE_ANNO = InvocationUtil.getField(MyUniqueObject.class, "theColumnNotUnique");

        /**
         * The JoinColumn annotated field.
         */
        static final Field FIELD_JOIN_COLUMN_ANNO = InvocationUtil.getField(MyUniqueObject.class, "theJoinColumn");

        /**
         * The JoinColumn(unique=true) annotated field.
         */
        static final Field FIELD_JOIN_COLUMN_UNIQUE_ANNO = InvocationUtil.getField(MyUniqueObject.class, "theJoinColumnUnique");

        /**
         * The JoinColumn(unique=false) annotated field.
         */
        static final Field FIELD_JOIN_COLUMN_NOT_UNIQUE_ANNO = InvocationUtil.getField(MyUniqueObject.class, "theJoinColumnNotUnique");

        /**
         * The transient field.
         */
        static final Field FIELD_TRANSIENT = InvocationUtil.getField(MyUniqueObject.class, "theTransient");

        /**
         * The field without annotation.
         */
        static final Field FIELD_NOTHING = InvocationUtil.getField(MyUniqueObject.class, "nothing");
    }

    /**
     * Define an object for testing nullable persisted fields.
     * @author qjafcunuas
     */
    @Getter
    @Setter
    public static class MyNullableObject {
        /**
         * An object without annotation.
         */
        private Object nothing;

        /**
         * A Column(nullable=true) object.
         */
        @Column(nullable = true)
        private Object theColumnNullable;

        /**
         * A Column(nullable=false) object.
         */
        @Column(nullable = false)
        private Object theColumnNotNullable;

        /**
         * A JoinColumn(nullable=true) object.
         */
        @JoinColumn(nullable = true)
        private Object theJoinColumnNullable;

        /**
         * A JoinColumn(nullable=false) object.
         */
        @JoinColumn(nullable = false)
        private Object theJoinColumnNotNullable;

        /**
         * A ManyToOne(optional = true) object.
         */
        @ManyToOne(optional = true)
        private Object theManyToOneOptional;

        /**
         * A ManyToOne(optional = false) object.
         */
        @ManyToOne(optional = false)
        private Object theManyToOneNotOptional;

        /**
         * The field without annotation.
         */
        static final Field FIELD_NOTHING = InvocationUtil.getField(MyNullableObject.class, "nothing");

        /**
         * The Column(nullable=true) annotated field.
         */
        static final Field FIELD_COLUMN_NULLABLE_ANNO = InvocationUtil.getField(MyNullableObject.class, "theColumnNullable");

        /**
         * The Column(nullable=false) annotated field.
         */
        static final Field FIELD_COLUMN_NOT_NULLABLE_ANNO = InvocationUtil.getField(MyNullableObject.class, "theColumnNotNullable");

        /**
         * The JoinColumn(nullable=true) annotated field.
         */
        static final Field FIELD_JOIN_COLUMN_NULLABLE_ANNO = InvocationUtil.getField(MyNullableObject.class, "theJoinColumnNullable");

        /**
         * The JoinColumn(nullable=false) annotated field.
         */
        static final Field FIELD_JOIN_COLUMN_NOT_NULLABLE_ANNO = InvocationUtil.getField(MyNullableObject.class, "theJoinColumnNotNullable");

        /**
         * The ManyToOne(optional = true) annotated field.
         */
        static final Field FIELD_MANY_TO_ONE_OPTIONAL = InvocationUtil.getField(MyNullableObject.class, "theManyToOneOptional");

        /**
         * The ManyToOne(optional = false) annotated field.
         */
        static final Field FIELD_MANY_TO_ONE_NOT_OPTIONAL = InvocationUtil.getField(MyNullableObject.class, "theManyToOneNotOptional");

    }

    /**
     * Define an object for testing nullable persisted fields.
     * @author qjafcunuas
     */
    @Getter
    @Setter
    public static class MyColumnObject {
        /**
         * An object without annotation.
         */
        private Object nothing;

        /**
         * A Column without value object.
         */
        @Column
        private Object theColumn;

        /**
         * A Column with value object.
         */
        @Column(length = IntegerUtil.INT_10, scale = IntegerUtil.INT_20, precision = IntegerUtil.INT_30)
        private Object theColumnValue;

        /**
         * The field without annotation.
         */
        static final Field FIELD_NOTHING = InvocationUtil.getField(MyColumnObject.class, "nothing");

        /**
         * The Column annotated field.
         */
        static final Field FIELD_COLUMN = InvocationUtil.getField(MyColumnObject.class, "theColumn");

        /**
         * The Column(xxx=yyy) annotated field.
         */
        static final Field FIELD_COLUMN_VALUE = InvocationUtil.getField(MyColumnObject.class, "theColumnValue");

    }

    /**
     * Define an object for testing lazy persisted fields.
     * @author qjafcunuas
     */
    @Getter
    @Setter
    public static class MyLazyObject {

        /**
         * An object without annotation.
         */
        private Object nothing;

        /**
         * A ManyToOne object.
         */
        @ManyToOne
        private Object manyToOne;

        /**
         * A ManyToOne(fetch=FetchType.EAGER) object.
         */
        @ManyToOne(fetch = FetchType.EAGER)
        private Object manyToOneEager;

        /**
         * A ManyToOne(fetch=FetchType.LAZY) object.
         */
        @ManyToOne(fetch = FetchType.LAZY)
        private Object manyToOneLazy;

        /**
         * A OneToOne object.
         */
        @OneToOne
        private Object oneToOne;

        /**
         * A OneToOne(fetch=FetchType.EAGER) object.
         */
        @OneToOne(fetch = FetchType.EAGER)
        private Object oneToOneEager;

        /**
         * A OneToOne(fetch=FetchType.LAZY) object.
         */
        @OneToOne(fetch = FetchType.LAZY)
        private Object oneToOneLazy;

        /**
         * A OneToMany object.
         */
        @OneToMany(mappedBy = "theMappedBy")
        private Object oneToMany;

        /**
         * A OneToMany(fetch=FetchType.EAGER) object.
         */
        @OneToMany(fetch = FetchType.EAGER)
        private Object oneToManyEager;

        /**
         * A OneToMany(fetch=FetchType.LAZY) object.
         */
        @OneToMany(fetch = FetchType.LAZY)
        private Object oneToManyLazy;

        /**
         * A ManyToMany object.
         */
        @ManyToMany
        private Object manyToMany;

        /**
         * A ManyToMany(fetch=FetchType.EAGER) object.
         */
        @ManyToMany(fetch = FetchType.EAGER)
        private Object manyToManyEager;

        /**
         * A ManyToMany(fetch=FetchType.LAZY) object.
         */
        @ManyToMany(fetch = FetchType.LAZY)
        private Object manyToManyLazy;

        /**
         * The field without annotation.
         */
        static final Field FIELD_NOTHING = InvocationUtil.getField(MyLazyObject.class, "nothing");

        /**
         * The ManyToOne field.
         */
        static final Field FIELD_MANY_TO_ONE = InvocationUtil.getField(MyLazyObject.class, "manyToOne");

        /**
         * The ManyToOne(fetch = FetchType.EAGER) field.
         */
        static final Field FIELD_MANY_TO_ONE_EAGER = InvocationUtil.getField(MyLazyObject.class, "manyToOneEager");

        /**
         * The ManyToOne(fetch = FetchType.LAZY) field.
         */
        static final Field FIELD_MANY_TO_ONE_LAZY = InvocationUtil.getField(MyLazyObject.class, "manyToOneLazy");

        /**
         * The OneToOne field.
         */
        static final Field FIELD_ONE_TO_ONE = InvocationUtil.getField(MyLazyObject.class, "oneToOne");

        /**
         * The OneToOne(fetch = FetchType.EAGER) field.
         */
        static final Field FIELD_ONE_TO_ONE_EAGER = InvocationUtil.getField(MyLazyObject.class, "oneToOneEager");

        /**
         * The OneToOne(fetch = FetchType.LAZY) field.
         */
        static final Field FIELD_ONE_TO_ONE_LAZY = InvocationUtil.getField(MyLazyObject.class, "oneToOneLazy");

        /**
         * The OneToMany field.
         */
        static final Field FIELD_ONE_TO_MANY = InvocationUtil.getField(MyLazyObject.class, "oneToMany");

        /**
         * The OneToMany(fetch = FetchType.EAGER) field.
         */
        static final Field FIELD_ONE_TO_MANY_EAGER = InvocationUtil.getField(MyLazyObject.class, "oneToManyEager");

        /**
         * The OneToMany(fetch = FetchType.LAZY) field.
         */
        static final Field FIELD_ONE_TO_MANY_LAZY = InvocationUtil.getField(MyLazyObject.class, "oneToManyLazy");

        /**
         * The ManyToMany field.
         */
        static final Field FIELD_MANY_TO_MANY = InvocationUtil.getField(MyLazyObject.class, "manyToMany");

        /**
         * The ManyToMany(fetch = FetchType.EAGER) field.
         */
        static final Field FIELD_MANY_TO_MANY_EAGER = InvocationUtil.getField(MyLazyObject.class, "manyToManyEager");

        /**
         * The ManyToMany(fetch = FetchType.LAZY) field.
         */
        static final Field FIELD_MANY_TO_MANY_LAZY = InvocationUtil.getField(MyLazyObject.class, "manyToManyLazy");
    }

    /**
     * Test isPersisted method.
     */
    @Test
    public void isPersisted() {
        Assert.assertFalse(EntityUtil.isPersisted(InvocationUtil.getField(MyObject.class, "theStatic")));
        Assert.assertFalse(EntityUtil.isPersisted(InvocationUtil.getField(MyObject.class, "THE_STATIC_FINAL")));
        Assert.assertTrue(EntityUtil.isPersisted(MyObject.FIELD_ID_ANNO));
        Assert.assertFalse(EntityUtil.isPersisted(MyObject.FIELD_TRANSIENT));
        Assert.assertFalse(EntityUtil.isPersisted(MyObject.FIELD_TRANSIENT_ANNO));
        Assert.assertTrue(EntityUtil.isPersisted(MyObject.FIELD_EMBEDDED_ANNO));
    }

    /**
     * Test getPersistedFields(Class) method.
     */
    @Test
    public void getPersistedFieldsClass() {
        final List<Field> fields = EntityUtil.getPersistedFields(MyObject.class);
        Assert.assertEquals(IntegerUtil.INT_4, fields.size());
        Assert.assertFalse(fields.contains(MyObject.FIELD_TRANSIENT));
        Assert.assertTrue(fields.contains(MyObject.FIELD_ID_ANNO));
        Assert.assertTrue(fields.contains(MyObject.FIELD_EMBEDDED_ANNO));
        Assert.assertTrue(fields.contains(MyObject.FIELD_EMBEDDED_ID_ANNO));
        Assert.assertTrue(fields.contains(MyObject.FIELD_MAPS_ID_ANNO));

        // Same object because it is cached.
        Assert.assertEquals(fields, EntityUtil.getPersistedFields(MyObject.class));
    }

    /**
     * Test getPersistedFields(Field) method.
     */
    @Test
    public void getPersistedFieldsField() {
        final List<Field> fields = EntityUtil.getPersistedFields(CompositeEntity.FIELD_USER);
        Assert.assertEquals(IntegerUtil.INT_1, fields.size());
    }

    /**
     * Test getPersistedFields(Object) method.
     */
    @Test
    public void getPersistedFieldsObject() {
        final List<Field> fields = EntityUtil.getPersistedFields(new CompositeEntity());
        Assert.assertEquals(IntegerUtil.INT_3, fields.size());
        Assert.assertTrue(fields.contains(CompositeEntity.FIELD_PRIMARY_KEY));
        Assert.assertTrue(fields.contains(CompositeEntity.FIELD_USER));
        Assert.assertTrue(fields.contains(CompositeEntity.FIELD_CITY));
        Assert.assertFalse(fields.contains(MyObject.FIELD_MAPS_ID_ANNO));
    }

    /**
     * Test getPersistentClass(Object) method.
     */
    @Test
    public void getPersistentClass() {
        // null object.
        Class<?> theClass = EntityUtil.getPersistentClass(null);
        Assert.assertNull(theClass);

        // not null object.
        final CompositeEntity entity = new CompositeEntity();
        try {
            // Not null JpaProvider.
            MockJpaProviderFactory.mock(MockJpaProviderFactory.PROVIDER_FALSE);
            theClass = EntityUtil.getPersistentClass(entity);
            Assert.assertNotNull(theClass);
            Assert.assertEquals(CompositeEntity.class, theClass);

            // Null JpaProvider.
            MockJpaProviderFactory.mock(null);
            theClass = EntityUtil.getPersistentClass(entity);
            Assert.assertNotNull(theClass);
            Assert.assertEquals(CompositeEntity.class, theClass);
        } finally {
            MockJpaProviderFactory.unmock();
        }
    }

    /**
     * Test getPersistedFields method.
     */
    @Test
    public void getPrimaryKeyClass() {
        Field field = EntityUtil.getPrimaryKeyField(MyObject.class);
        Assert.assertNotNull(field);
        Assert.assertTrue(EntityUtil.isId(field));

        // Same object because it is cached.
        Assert.assertEquals(field, EntityUtil.getPrimaryKeyField(MyObject.class));

        // No pk
        field = EntityUtil.getPrimaryKeyField(Object.class);
        Assert.assertNull(field);
    }

    /**
     * Test isEmbedded method.
     */
    @Test
    public void isEmbedded() {
        Assert.assertTrue(EntityUtil.isEmbedded(MyObject.FIELD_EMBEDDED_ANNO));
        Assert.assertFalse(EntityUtil.isEmbedded(MyObject.FIELD_ID_ANNO));
    }

    /**
     * Test isTransient method.
     */
    @Test
    public void isTransient() {
        Assert.assertTrue(EntityUtil.isTransient(MyObject.FIELD_TRANSIENT_ANNO));
        Assert.assertFalse(EntityUtil.isTransient(MyObject.FIELD_TRANSIENT));
        Assert.assertFalse(EntityUtil.isTransient(MyObject.FIELD_ID_ANNO));
    }

    /**
     * Test isId method.
     */
    @Test
    public void isId() {
        Assert.assertFalse(EntityUtil.isId(MyObject.FIELD_TRANSIENT_ANNO));
        Assert.assertFalse(EntityUtil.isId(MyObject.FIELD_EMBEDDED_ID_ANNO));
        Assert.assertTrue(EntityUtil.isId(MyObject.FIELD_ID_ANNO));
    }

    /**
     * Test isEmbeddedId method.
     */
    @Test
    public void isEmbeddedId() {
        Assert.assertFalse(EntityUtil.isEmbeddedId(MyObject.FIELD_TRANSIENT_ANNO));
        Assert.assertFalse(EntityUtil.isEmbeddedId(MyObject.FIELD_EMBEDDED_ANNO));
        Assert.assertTrue(EntityUtil.isEmbeddedId(MyObject.FIELD_EMBEDDED_ID_ANNO));
    }

    /**
     * Test getMapsIdFields method.
     */
    @Test
    public void getMapsIdFields() {
        final List<Field> fields = EntityUtil.getMapsIdFields(MyObject.class);
        Assert.assertTrue(fields.size() == IntegerUtil.INT_1);
        Assert.assertFalse(fields.contains(MyObject.FIELD_TRANSIENT));
        Assert.assertFalse(fields.contains(MyObject.FIELD_ID_ANNO));
        Assert.assertFalse(fields.contains(MyObject.FIELD_EMBEDDED_ANNO));
        Assert.assertFalse(fields.contains(MyObject.FIELD_EMBEDDED_ID_ANNO));
        Assert.assertTrue(fields.contains(MyObject.FIELD_MAPS_ID_ANNO));

        // Same object because it is cached.
        Assert.assertEquals(fields, EntityUtil.getMapsIdFields(MyObject.class));
    }

    /**
     * Test isMapsId method.
     */
    @Test
    public void isMapsId() {
        Assert.assertFalse(EntityUtil.isMapsId(MyObject.FIELD_TRANSIENT_ANNO));
        Assert.assertFalse(EntityUtil.isMapsId(MyObject.FIELD_EMBEDDED_ANNO));
        Assert.assertTrue(EntityUtil.isMapsId(MyObject.FIELD_MAPS_ID_ANNO));
    }

    /**
     * Test getMapsIdValue method.
     */
    @Test
    public void getMapsIdValue() {
        Assert.assertNull(EntityUtil.getMapsIdValue(MyObject.FIELD_TRANSIENT_ANNO));
        Assert.assertNull(EntityUtil.getMapsIdValue(MyObject.FIELD_EMBEDDED_ANNO));
        Assert.assertNotNull(EntityUtil.getMapsIdValue(MyObject.FIELD_MAPS_ID_ANNO));
    }

    /**
     * Test getMapsIdFieldValue method.
     */
    @Test
    public void getMapsIdFieldValue() {
        final MyObject object = new MyObject();
        object.theMapsId = new CompositeEntity();
        Assert.assertNull(EntityUtil.getMapsIdFieldValue(null, null));
        Assert.assertNull(EntityUtil.getMapsIdFieldValue(null, "tototo"));
        Assert.assertNull(EntityUtil.getMapsIdFieldValue(object, null));
        Assert.assertNull(EntityUtil.getMapsIdFieldValue(object, "tototo"));
        Assert.assertNotNull(EntityUtil.getMapsIdFieldValue(object, "theId"));
        Assert.assertEquals(object.theMapsId, EntityUtil.getMapsIdFieldValue(object, "theId"));
    }

    /**
     * Test getMapsId method.
     */
    @Test
    public void getMapsId() {
        final MyObject object = new MyObject();
        object.theMapsId = new CompositeEntity();

        Assert.assertNull(EntityUtil.getMapsId(null));
        Assert.assertNotNull(EntityUtil.getMapsId(object));
        Assert.assertFalse(EntityUtil.getMapsId(object).isEmpty());
        Assert.assertTrue(EntityUtil.getMapsId(object).containsKey("theId"));
        Assert.assertNotNull(EntityUtil.getMapsId(object).get("theId"));
        Assert.assertEquals(object.theMapsId, EntityUtil.getMapsId(object).get("theId"));
    }

    /**
     * Test isPrimaryKey method.
     */
    @Test
    public void isPrimaryKey() {
        Assert.assertFalse(EntityUtil.isPrimaryKey(MyObject.FIELD_TRANSIENT_ANNO));
        Assert.assertFalse(EntityUtil.isPrimaryKey(MyObject.FIELD_EMBEDDED_ANNO));
        Assert.assertTrue(EntityUtil.isPrimaryKey(MyObject.FIELD_EMBEDDED_ID_ANNO));
        Assert.assertTrue(EntityUtil.isPrimaryKey(MyObject.FIELD_ID_ANNO));
    }

    /**
     * Test isGeneratedValue method.
     */
    @Test
    public void isGeneratedValue() {
        Assert.assertFalse(EntityUtil.isGeneratedValue(MyObject.FIELD_TRANSIENT_ANNO));
        Assert.assertFalse(EntityUtil.isGeneratedValue(MyObject.FIELD_EMBEDDED_ANNO));
        Assert.assertFalse(EntityUtil.isGeneratedValue(MyObject.FIELD_EMBEDDED_ID_ANNO));
        Assert.assertTrue(EntityUtil.isGeneratedValue(MyObject.FIELD_ID_ANNO));
    }

    /**
     * Test isNullable method.
     */
    @Test
    public void isNullable() {
        Assert.assertTrue(EntityUtil.isNullable(MyNullableObject.FIELD_NOTHING));
        Assert.assertFalse(EntityUtil.isNullable(MyNullableObject.FIELD_COLUMN_NOT_NULLABLE_ANNO));
        Assert.assertTrue(EntityUtil.isNullable(MyNullableObject.FIELD_COLUMN_NULLABLE_ANNO));
        Assert.assertFalse(EntityUtil.isNullable(MyNullableObject.FIELD_JOIN_COLUMN_NOT_NULLABLE_ANNO));
        Assert.assertTrue(EntityUtil.isNullable(MyNullableObject.FIELD_JOIN_COLUMN_NULLABLE_ANNO));
        Assert.assertFalse(EntityUtil.isNullable(MyNullableObject.FIELD_MANY_TO_ONE_NOT_OPTIONAL));
        Assert.assertTrue(EntityUtil.isNullable(MyNullableObject.FIELD_MANY_TO_ONE_OPTIONAL));
    }

    /**
     * Test isUnique method.
     */
    @Test
    public void isUnique() {
        Assert.assertFalse(EntityUtil.isUnique(MyObject.FIELD_TRANSIENT_ANNO));
        Assert.assertFalse(EntityUtil.isUnique(MyObject.FIELD_EMBEDDED_ANNO));
        Assert.assertFalse(EntityUtil.isUnique(MyObject.FIELD_EMBEDDED_ID_ANNO));
        Assert.assertFalse(EntityUtil.isUnique(MyUniqueObject.FIELD_COLUMN_ANNO));
        Assert.assertFalse(EntityUtil.isUnique(MyUniqueObject.FIELD_COLUMN_NOT_UNIQUE_ANNO));
        Assert.assertTrue(EntityUtil.isUnique(MyUniqueObject.FIELD_COLUMN_UNIQUE_ANNO));
        Assert.assertFalse(EntityUtil.isUnique(MyUniqueObject.FIELD_JOIN_COLUMN_ANNO));
        Assert.assertFalse(EntityUtil.isUnique(MyUniqueObject.FIELD_JOIN_COLUMN_NOT_UNIQUE_ANNO));
        Assert.assertTrue(EntityUtil.isUnique(MyUniqueObject.FIELD_JOIN_COLUMN_UNIQUE_ANNO));
        Assert.assertFalse(EntityUtil.isUnique(MyUniqueObject.FIELD_NOTHING));
    }

    /**
     * Test isLazy(Field) method.
     */
    @Test
    public void isLazyField() {
        // No annotation
        Assert.assertTrue(EntityUtil.isLazy(MyLazyObject.FIELD_NOTHING));
        // ManyToMany
        Assert.assertTrue(EntityUtil.isLazy(MyLazyObject.FIELD_MANY_TO_MANY));
        Assert.assertFalse(EntityUtil.isLazy(MyLazyObject.FIELD_MANY_TO_MANY_EAGER));
        Assert.assertTrue(EntityUtil.isLazy(MyLazyObject.FIELD_MANY_TO_MANY_LAZY));
        // ManyToOne
        Assert.assertFalse(EntityUtil.isLazy(MyLazyObject.FIELD_MANY_TO_ONE));
        Assert.assertFalse(EntityUtil.isLazy(MyLazyObject.FIELD_MANY_TO_ONE_EAGER));
        Assert.assertTrue(EntityUtil.isLazy(MyLazyObject.FIELD_MANY_TO_ONE_LAZY));
        // OneToMany
        Assert.assertTrue(EntityUtil.isLazy(MyLazyObject.FIELD_ONE_TO_MANY));
        Assert.assertFalse(EntityUtil.isLazy(MyLazyObject.FIELD_ONE_TO_MANY_EAGER));
        Assert.assertTrue(EntityUtil.isLazy(MyLazyObject.FIELD_ONE_TO_MANY_LAZY));
        // OneToOne
        Assert.assertFalse(EntityUtil.isLazy(MyLazyObject.FIELD_ONE_TO_ONE));
        Assert.assertFalse(EntityUtil.isLazy(MyLazyObject.FIELD_ONE_TO_ONE_EAGER));
        Assert.assertTrue(EntityUtil.isLazy(MyLazyObject.FIELD_ONE_TO_ONE_LAZY));
    }

    /**
     * Test getColumnLength method.
     */
    @Test
    public void getColumnLength() {
        // Nothing
        Assert.assertNull(EntityUtil.getColumnLength(MyColumnObject.FIELD_NOTHING));
        // Column
        Assert.assertEquals(EntityUtil.getColumnLength(MyColumnObject.FIELD_COLUMN), Integer.valueOf(IntegerUtil.INT_255));
        // Column with attribute
        Assert.assertEquals(EntityUtil.getColumnLength(MyColumnObject.FIELD_COLUMN_VALUE), Integer.valueOf(IntegerUtil.INT_10));
    }

    /**
     * Test getColumnScale method.
     */
    @Test
    public void getColumnScale() {
        // Nothing
        Assert.assertNull(EntityUtil.getColumnScale(MyColumnObject.FIELD_NOTHING));
        // Column
        Assert.assertEquals(EntityUtil.getColumnScale(MyColumnObject.FIELD_COLUMN), Integer.valueOf(IntegerUtil.INT_0));
        // Column with attribute
        Assert.assertEquals(EntityUtil.getColumnScale(MyColumnObject.FIELD_COLUMN_VALUE), Integer.valueOf(IntegerUtil.INT_20));
    }

    /**
     * Test getColumnPrecision method.
     */
    @Test
    public void getColumnPrecision() {
        // Nothing
        Assert.assertNull(EntityUtil.getColumnPrecision(MyColumnObject.FIELD_NOTHING));
        // Column
        Assert.assertEquals(EntityUtil.getColumnPrecision(MyColumnObject.FIELD_COLUMN), Integer.valueOf(IntegerUtil.INT_0));
        // Column with attribute
        Assert.assertEquals(EntityUtil.getColumnPrecision(MyColumnObject.FIELD_COLUMN_VALUE), Integer.valueOf(IntegerUtil.INT_30));
    }

    /**
     * Test isEmbeddable method.
     */
    @Test
    public void isEmbeddable() {
        Assert.assertTrue(EntityUtil.isEmbeddable(MyUniqueObject.class));
        Assert.assertFalse(EntityUtil.isEmbeddable(MyObject.class));
    }

    /**
     * Test isManyToMany method.
     */
    @Test
    public void isManyToMany() {
        Assert.assertFalse(EntityUtil.isManyToMany(MyLazyObject.FIELD_ONE_TO_ONE));
        Assert.assertFalse(EntityUtil.isManyToMany(MyLazyObject.FIELD_ONE_TO_MANY));
        Assert.assertTrue(EntityUtil.isManyToMany(MyLazyObject.FIELD_MANY_TO_MANY));
        Assert.assertFalse(EntityUtil.isManyToMany(MyLazyObject.FIELD_MANY_TO_ONE));
    }

    /**
     * Test isOneToMany method.
     */
    @Test
    public void isOneToMany() {
        Assert.assertFalse(EntityUtil.isOneToMany(MyLazyObject.FIELD_ONE_TO_ONE));
        Assert.assertTrue(EntityUtil.isOneToMany(MyLazyObject.FIELD_ONE_TO_MANY));
        Assert.assertFalse(EntityUtil.isOneToMany(MyLazyObject.FIELD_MANY_TO_MANY));
        Assert.assertFalse(EntityUtil.isOneToMany(MyLazyObject.FIELD_MANY_TO_ONE));
    }

    /**
     * Test getOneToManyMappedBy method.
     */
    @Test
    public void getOneToManyMappedBy() {
        Assert.assertNull(EntityUtil.getOneToManyMappedBy(MyLazyObject.FIELD_ONE_TO_ONE));
        Assert.assertNotNull(EntityUtil.getOneToManyMappedBy(MyLazyObject.FIELD_ONE_TO_MANY));
        Assert.assertEquals("theMappedBy", EntityUtil.getOneToManyMappedBy(MyLazyObject.FIELD_ONE_TO_MANY));
        Assert.assertNull(EntityUtil.getOneToManyMappedBy(MyLazyObject.FIELD_MANY_TO_MANY));
        Assert.assertNull(EntityUtil.getOneToManyMappedBy(MyLazyObject.FIELD_MANY_TO_ONE));
    }

    /**
     * Test isOneToOne method.
     */
    @Test
    public void isOneToOne() {
        Assert.assertTrue(EntityUtil.isOneToOne(MyLazyObject.FIELD_ONE_TO_ONE));
        Assert.assertFalse(EntityUtil.isOneToOne(MyLazyObject.FIELD_ONE_TO_MANY));
        Assert.assertFalse(EntityUtil.isOneToOne(MyLazyObject.FIELD_MANY_TO_MANY));
        Assert.assertFalse(EntityUtil.isOneToOne(MyLazyObject.FIELD_MANY_TO_ONE));
    }

    /**
     * Test isManyToOne method.
     */
    @Test
    public void isManyToOne() {
        Assert.assertFalse(EntityUtil.isManyToOne(MyLazyObject.FIELD_ONE_TO_ONE));
        Assert.assertFalse(EntityUtil.isManyToOne(MyLazyObject.FIELD_ONE_TO_MANY));
        Assert.assertFalse(EntityUtil.isManyToOne(MyLazyObject.FIELD_MANY_TO_MANY));
        Assert.assertTrue(EntityUtil.isManyToOne(MyLazyObject.FIELD_MANY_TO_ONE));
    }

    /**
     * Test isManyToOne method.
     */
    @Test
    public void readPrimaryKey() {
        final CompositeEntity entity = new CompositeEntity();
        try {
            MockJpaProviderFactory.mock(MockJpaProviderFactory.PROVIDER_FALSE);
            Assert.assertEquals(entity.getPrimaryKey(), EntityUtil.readPrimaryKey(entity));
            MockJpaProviderFactory.mock(MockJpaProviderFactory.PROVIDER_TRUE);
            Assert.assertEquals(entity.getPrimaryKey(), EntityUtil.readPrimaryKey(entity));
            MockJpaProviderFactory.mock(null);
            Assert.assertEquals(entity.getPrimaryKey(), EntityUtil.readPrimaryKey(entity));
        } finally {
            MockJpaProviderFactory.unmock();
        }
    }

}
