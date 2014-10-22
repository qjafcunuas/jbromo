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
package org.jbromo.model.common.test.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.CollectionUtil;
import org.jbromo.common.ListUtil;
import org.junit.Assert;

/**
 * Define Cache Data test. All data defined in the cache are accessible every
 * where. It should be removed at the end of a test.
 *
 * @author qjafcunuas
 *
 */
@Slf4j
public class CacheData {

    /**
     * Map data by class.
     */
    private final HashMap<Class<?>, List<?>> mapByClass = new HashMap<Class<?>, List<?>>();

    /**
     * Ordering created objects list.
     */
    private final List<Object> sorted = new ArrayList<Object>();

    /**
     * Default constructor.
     */
    public CacheData() {
        super();
    }

    /**
     * Return the class of an object.
     *
     * @param theClass
     *            the theClass to get class.
     * @return the class object.
     */
    protected Class<?> getClass(final Class<?> theClass) {
        return theClass;
    }

    /**
     * Return true if object is in the cache.
     *
     * @param <E>
     *            the object type.
     * @param object
     *            the object.
     * @return true/false.
     */
    public <E> boolean contains(final E object) {
        if (object == null) {
            return false;
        }
        return getObjects(object.getClass(), false).contains(object);
    }

    /**
     * Return objects list according to a class for the current space.
     *
     * @param <E>
     *            the object type.
     * @param theClass
     *            the class to get objects.
     * @return objects list.
     */
    public <E> List<E> getObjects(final Class<?> theClass) {
        return getObjects(theClass, true);
    }

    /**
     * Return objects ordering by creation date.
     *
     * @return objects list.
     */
    public List<Object> getSorted() {
        return ListUtil.toList(this.sorted);
    }

    /**
     * Return objects list according to a class for the current space.
     *
     * @param <E>
     *            the object type.
     * @param theClass
     *            the class to get objects.
     * @param clone
     *            if true, the returned list is not the same list than from the
     *            mapper.
     * @return objects list.
     */
    @SuppressWarnings("unchecked")
    private <E> List<E> getObjects(final Class<?> theClass, final boolean clone) {
        if (theClass == null) {
            return null;
        }
        final Class<?> clazz = getClass(theClass);
        List<E> objects = (List<E>) this.mapByClass.get(clazz);
        if (objects == null) {
            objects = new ArrayList<E>();
            this.mapByClass.put(clazz, objects);
        }
        if (clone) {
            return ListUtil.toList(objects);
        } else {
            return objects;
        }
    }

    /**
     * Add an object in the cache.
     *
     * @param <E>
     *            the object type.
     * @param object
     *            the object to add.
     */
    public <E> void add(final E object) {
        if (object == null) {
            return;
        }
        final List<E> objects = getObjects(object.getClass(), false);
        if (objects.contains(object)) {
            log.error("Object should not be existed {}", object);
            Assert.fail("Object should not be existed ");
        }
        objects.add(object);
        this.sorted.add(object);
    }

    /**
     * Updates an object in the cache. If the object is null, return true. If
     * the object exists in the cache, update it and return true. If the object
     * doesn't exist in the cache, don't add it and return false.
     *
     * @param <E>
     *            the object type.
     * @param object
     *            the object to add.
     * @return true/false.
     */
    public <E> boolean update(final E object) {
        if (object == null) {
            return true;
        }
        final List<E> objects = getObjects(object.getClass(), false);
        int index = objects.indexOf(object);
        if (index < 0) {
            return false;
        } else {
            objects.set(index, object);
            index = this.sorted.indexOf(object);
            this.sorted.set(index, object);
            return true;
        }
    }

    /**
     * Add or updates an object in the cache. If the object is null, return
     * null. If the object exists in the cache, update it and return false. If
     * the object doesn't exist in the cache, add it and return true.
     *
     * @param <E>
     *            the object type.
     * @param object
     *            the object to add.
     * @return true/false.
     */
    public <E> Boolean addOrUpdate(final E object) {
        if (object == null) {
            return null;
        }
        if (contains(object)) {
            update(object);
            return false;
        } else {
            add(object);
            return true;
        }
    }

    /**
     * Update objects in the cache.
     *
     * @param <E>
     *            the object type.
     * @param objects
     *            the objects to update.
     */
    public <E> void update(final Collection<E> objects) {
        if (CollectionUtil.isEmpty(objects)) {
            return;
        }
        for (final E object : objects) {
            update(object);
        }
    }

    /**
     * Add objects in the cache.
     *
     * @param <E>
     *            the object type.
     * @param objects
     *            the objects to add.
     */
    public <E> void add(final Collection<E> objects) {
        if (CollectionUtil.isEmpty(objects)) {
            return;
        }
        for (final E object : objects) {
            add(object);
        }
    }

    /**
     * Remove an object from the cache. It return false if object to remove is
     * null or object is not in the cache.
     *
     * @param <E>
     *            the object type.
     * @param object
     *            the object to remove.
     * @return true/false.
     */
    public <E> boolean remove(final E object) {
        if (object == null) {
            return true;
        }
        final List<E> objects = getObjects(object.getClass(), false);
        this.sorted.remove(object);
        return objects.remove(object);
    }

    /**
     * Update objects in the cache.
     *
     * @param <E>
     *            the object type.
     * @param objects
     *            the objects to update.
     */
    public <E> void remove(final Collection<E> objects) {
        if (CollectionUtil.isEmpty(objects)) {
            return;
        }
        for (final E object : objects) {
            remove(object);
        }
    }

    /**
     * Remove all objects from the cache.
     *
     * @param <E>
     *            the object type.
     * @param theClass
     *            the class'objects to remove.
     */
    public <E> void remove(final Class<E> theClass) {
        if (theClass == null) {
            return;
        }
        final List<E> objects = getObjects(theClass);
        remove(objects);
        this.mapByClass.remove(theClass);
    }

    /**
     * Remove all objects from the cache.
     *
     */
    public void removeAll() {
        for (final List<?> one : this.mapByClass.values()) {
            one.clear();
        }
        this.mapByClass.clear();
        this.sorted.clear();
    }

    /**
     * Logs if cache is not empty and clear the cache.
     *
     * @return true if the cache was empty, else return false.
     */
    public boolean clear() {
        boolean empty = true;
        for (final Map.Entry<Class<?>, List<?>> entry : this.mapByClass
                .entrySet()) {
            if (!entry.getValue().isEmpty()) {
                empty = false;
                log.warn("Cache contains {} {} elements", entry.getValue()
                        .size(), entry.getKey().getSimpleName());
                entry.getValue().clear();
            }
        }
        this.mapByClass.clear();
        this.sorted.clear();
        return empty;
    }

    /**
     * Return object from cache.
     *
     * @param <E>
     *            the object type.
     * @param object
     *            the object to get.
     * @return the object.
     */
    public <E> E get(final E object) {
        if (object == null) {
            return null;
        }
        final List<E> objects = getObjects(object.getClass(), false);
        if (objects.isEmpty()) {
            return null;
        }
        final int index = objects.indexOf(object);
        if (index >= 0) {
            return objects.get(index);
        } else {
            return null;
        }
    }

}
