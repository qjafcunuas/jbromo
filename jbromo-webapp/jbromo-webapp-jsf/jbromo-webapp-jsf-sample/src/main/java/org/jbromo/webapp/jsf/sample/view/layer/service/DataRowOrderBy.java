package org.jbromo.webapp.jsf.sample.view.layer.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.jbromo.common.dto.IOrderBy;

/**
 * Define DataRow order clause.
 *
 * @author qjafcunuas
 *
 */
@AllArgsConstructor
@Getter
public enum DataRowOrderBy implements IOrderBy<DataRow> {
    /**
     * Order by primary key.
     */
    PRIMARY_KEY_ASC("primaryKey", IOrderBy.SORT.ASCENDING),
    /**
     * Order by primary key.
     */
    PRIMARY_KEY_DESC(PRIMARY_KEY_ASC.getOrder(), IOrderBy.SORT.DESCENDING),
    /**
     * Order by name.
     */
    NAME_ASC("name", IOrderBy.SORT.ASCENDING),
    /**
     * Order by name.
     */
    NAME_DESC(NAME_ASC.getOrder(), IOrderBy.SORT.DESCENDING),
    /**
     * Order by description.
     */
    DESCRIPTION_ASC("description", IOrderBy.SORT.ASCENDING),
    /**
     * Order by description.
     */
    DESCRIPTION_DESC(DESCRIPTION_ASC.getOrder(), IOrderBy.SORT.DESCENDING);
    /**
     * The order clause.
     */
    private String order;
    /**
     * The order clause.
     */
    private IOrderBy.SORT sort;

    /**
     * Return the order by.
     *
     * @param columnRef
     *            the columnRef.
     * @param sort
     *            the sort.
     * @return the order by.
     */
    public static DataRowOrderBy getOrderBy(final String columnRef,
            final IOrderBy.SORT sort) {
        for (final DataRowOrderBy one : values()) {
            if (one.getOrder().equals(columnRef) && one.getSort().equals(sort)) {
                return one;
            }
        }
        return null;
    }
}
