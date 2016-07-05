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

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Define Map Utility.
 * @author qjafcunuas
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MapUtil {

    /**
     * Return true if map contains same keys and same elements.
     * @param <K> the key type of the map.
     * @param <V> the value type of the map.
     * @param one the first map.
     * @param two the second map.
     * @return true/false.
     */
    public static <K, V> boolean containsAll(final Map<K, V> one, final Map<K, V> two) {
        if (one == null || two == null) {
            return false;
        }
        if (ObjectUtil.same(one, two)) {
            return true;
        }
        return CollectionUtil.containsAll(one.entrySet(), two.entrySet(), false);
    }

    /**
     * Return true if map is null or empty.
     * @param map the map to check.
     * @return true/false.
     */
    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Return true if map is not null and not empty.
     * @param map the map to check.
     * @return true/false.
     */
    public static boolean isNotEmpty(final Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * Return true if map is not null and has only one elements.
     * @param map the map to check.
     * @return true/false.
     */
    public static boolean hasOneElement(final Map<?, ?> map) {
        return map != null && map.size() == 1;
    }

    /**
     * Return map if map contains more than one elements.
     * @param map the map to check.
     * @return true/false.
     */
    public static boolean hasElements(final Map<?, ?> map) {
        return map != null && map.size() > 1;
    }

    /**
     * Is assignable to a Map.
     * @param type the type to check
     * @return true if type is Map.
     */
    public static boolean isMap(final Class<?> type) {
        return ClassUtil.isAssignableFrom(type, Map.class);
    }

    /**
     * Is assignable to a Map.
     * @param obj the obj to check
     * @return true if type is Map.
     */
    public static boolean isMap(final Object obj) {
        return ClassUtil.isInstance(obj, Map.class);
    }

    /**
     * Build a HashMap from a map.
     * @param <K> the key map type.
     * @param <V> the value map type.
     * @param values the values.
     * @return HashMap.
     */
    public static <K, V> Map<K, V> toMap(final Map<K, V> values) {
        if (values == null) {
            return toMap();
        }
        final Map<K, V> map = toMap();
        map.putAll(values);
        return map;
    }

    /**
     * Build a new HashMap.
     * @param <K> the key map type.
     * @param <V> the value map type.
     * @return HashMap.
     */
    public static <K, V> Map<K, V> toMap() {
        return new HashMap<>();
    }

    /**
     * Build a new ordered map.
     * @param <K> the key map type.
     * @param <V> the value map type.
     * @return map.
     */
    public static <K, V> Map<K, V> toOrderedMap() {
        return new LinkedHashMap<>();
    }

    /**
     * Build a synchronized map.
     * @param <K> the key map type.
     * @param <V> the value map type.
     * @return HashMap.
     */
    public static <K, V> Map<K, V> toSynchronizedMap() {
        final Map<K, V> map = toMap();
        return Collections.synchronizedMap(map);
    }

    /**
     * Build a synchronized from a map.
     * @param <K> the key map type.
     * @param <V> the value map type.
     * @param map the map.
     * @return HashMap.
     */
    public static <K, V> Map<K, V> toSynchronizedMap(final Map<K, V> map) {
        final Map<K, V> synch = toSynchronizedMap();
        if (map != null) {
            synch.putAll(map);
        }
        return synch;
    }

}
