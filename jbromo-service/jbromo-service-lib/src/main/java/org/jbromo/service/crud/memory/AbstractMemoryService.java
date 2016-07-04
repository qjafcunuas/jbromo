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
package org.jbromo.service.crud.memory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.jbromo.common.ListUtil;
import org.jbromo.common.MapUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.dto.IOrderBy;
import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.dao.common.exception.DataFindException;
import org.jbromo.service.crud.AbstractCrudService;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * The class for implementation of Memory CRUD services. EJB must be declared with Singleton annotation.
 * @author qjafcunuas
 * @param <M> the model type giving by Service layer
 */
@Slf4j
public abstract class AbstractMemoryService<M extends Serializable> extends AbstractCrudService<M, Integer> implements IMemoryService<M> {

    /**
     * Serial version UID for class.
     */
    private static final long serialVersionUID = -4053211327121284550L;

    /**
     * The objects map.
     */
    @Getter(AccessLevel.PROTECTED)
    private final Map<Integer, M> map = MapUtil.toSynchronizedMap();

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public M save(final M model) throws MessageLabelException {
        if (!getMap().containsValue(model)) {
            return create(model);
        } else {
            return update(model);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public M create(final M model) throws MessageLabelException {
        getMap().put(model.hashCode(), model);
        return model;
    }

    @Override
    public M read(final M model) throws MessageLabelException {
        return getMap().get(model.hashCode());
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public M update(final M model) throws MessageLabelException {
        getMap().put(model.hashCode(), model);
        return model;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean delete(final M model) throws MessageLabelException {
        if (getMap().containsKey(model.hashCode())) {
            getMap().remove(model.hashCode());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<M> findAll() throws MessageLabelException {
        return ListUtil.toList(getMap().values());
    }

    @Override
    public List<M> findAll(final M criteria) throws MessageLabelException {
        if (criteria == null) {
            return findAll();
        }
        final List<M> list = new ArrayList<>();
        // Build map from criteria for field with not null value (key = field
        // name, value = field value).
        final Map<String, Object> filters = new HashMap<>();
        Object value;
        for (final Field field : InvocationUtil.getFields(criteria.getClass())) {
            if (InvocationUtil.isPersistable(field)) {
                try {
                    value = InvocationUtil.getValue(criteria, field);
                } catch (final InvocationException e) {
                    throw new DataFindException(e);
                }
                if (value != null) {
                    filters.put(field.getName(), value);
                }
            }
        }
        if (filters.isEmpty()) {
            return findAll();
        }
        M correct;
        for (final M one : getMap().values()) {
            correct = one;
            for (final Entry<String, Object> entry : filters.entrySet()) {
                try {
                    value = InvocationUtil.getValue(one, entry.getKey());
                } catch (final InvocationException e) {
                    throw new DataFindException(e);
                }
                if (value == null) {
                    correct = null;
                } else if (value instanceof String) {
                    final String str = (String) value;
                    if (str.indexOf(entry.getValue().toString()) < 0) {
                        correct = null;
                    }

                } else if (!value.equals(entry.getValue())) {
                    correct = null;
                }
            }
            if (correct != null) {
                list.add(correct);
            }
        }
        return list;
    }

    @Override
    public List<M> findAll(final M criteria, final M eagerLoading, final List<IOrderBy> orderBy) throws MessageLabelException {
        final List<M> list = findAll(criteria);
        final Comparator<M> comparator = new Comparator<M>() {
            @Override
            public int compare(final M o1, final M o2) {
                int val;
                Object value1;
                Object value2;
                for (final IOrderBy order : orderBy) {
                    try {
                        value1 = InvocationUtil.getValue(o1, order.getOrder());
                        value2 = InvocationUtil.getValue(o2, order.getOrder());
                    } catch (final InvocationException e) {
                        log.warn("Cannot invoke getter", e);
                        return 0;
                    }
                    val = ObjectUtil.compare(value1, value2);
                    if (val != 0) {
                        switch (order.getSort()) {
                            case DESCENDING:
                                return -val;
                            default:
                                return val;
                        }
                    }
                }
                return 0;
            }
        };
        Collections.sort(list, comparator);
        return list;
    }

    @Override
    public M findByPk(final Integer primaryKey) throws MessageLabelException {
        if (getMap().containsKey(primaryKey)) {
            return getMap().get(primaryKey);
        } else {
            return null;
        }
    }

    @Override
    public <C extends Collection<M>> C create(final C transientInstance) throws MessageLabelException {
        return null;
    }

    @Override
    public <C extends Collection<M>> C update(final C detachedInstance) throws MessageLabelException {
        return null;
    }

    @Override
    public void delete(final Collection<M> detachedInstance) throws MessageLabelException {
    }

    @Override
    public <C extends Collection<M>> C save(final C detachedInstance) throws MessageLabelException {
        return null;
    }

    @Override
    public Collection<M> findAllByPk(final Collection<Integer> primaryKeys) throws MessageLabelException {
        return null;
    }

    @Override
    public M findByPk(final Integer primaryKey, final M eagerLoading) throws MessageLabelException {
        return null;
    }

    @Override
    public Collection<M> findAllByPk(final Collection<Integer> primaryKeys, final M eagerLoading) throws MessageLabelException {
        return null;
    }

    @Override
    public Long count() throws MessageLabelException {
        return null;
    }

    @Override
    public <C extends Collection<M>> C read(final C detachedInstance) throws MessageLabelException {
        return null;
    }

}
