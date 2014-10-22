package org.jbromo.common.test.cdi;

import org.jbromo.common.test.common.IContainer;

/**
 * Define CDI container interface.
 *
 * @author qjafcunuas
 *
 */
public interface ICdiContainer extends IContainer {
    /**
     * Obtains a child Instance for the given required type and additional
     * required qualifiers.
     *
     * @param <T>
     *            the child type.
     * @param theClass
     *            the child class to get instance.
     * @return the child instance.
     */
    <T> T select(final Class<T> theClass);

}
