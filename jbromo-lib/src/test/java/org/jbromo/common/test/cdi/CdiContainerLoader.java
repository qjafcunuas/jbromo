package org.jbromo.common.test.cdi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.ClassUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.test.common.IContainer;

/**
 * Load containers on CDI context.
 *
 * @author qjafcunuas
 *
 */
@Slf4j
public final class CdiContainerLoader {

    /**
     * The singleton instance.
     */
    private static final CdiContainerLoader INSTANCE = new CdiContainerLoader();

    /**
     * The CDI container.
     */
    @Getter
    private ICdiContainer cdiContainer;

    /**
     * All containers to start.
     */
    private static final List<IContainer> CONTAINERS = new ArrayList<IContainer>();

    /**
     * Default constructor.
     */
    private CdiContainerLoader() {
    }

    /**
     * Return the singleton instance.
     *
     * @return the instance.
     */
    public static CdiContainerLoader getInstance() {
        return INSTANCE;
    }

    /**
     * Return loaded containers.
     *
     * @return containers.
     */
    public List<IContainer> getContainers() {
        if (CONTAINERS.isEmpty()) {
            log.info("Start loading containers");
            final Iterator<IContainer> iter = ServiceLoader.load(
                    IContainer.class).iterator();
            IContainer container;
            while (iter.hasNext()) {
                container = iter.next();
                CONTAINERS.add(container);
                if (ClassUtil.isInstance(container, ICdiContainer.class)) {
                    this.cdiContainer = ObjectUtil.cast(container,
                            ICdiContainer.class);
                }
            }
            log.info("End of loading {} containers {}", CONTAINERS.size(),
                    CONTAINERS);
        }
        return CONTAINERS;
    }

}
