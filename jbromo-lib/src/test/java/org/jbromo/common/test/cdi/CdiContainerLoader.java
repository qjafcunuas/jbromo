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
package org.jbromo.common.test.cdi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.jbromo.common.ClassUtil;
import org.jbromo.common.ObjectUtil;
import org.jbromo.common.test.common.IContainer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Load containers on CDI context.
 * @author qjafcunuas
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
     * @return the instance.
     */
    public static CdiContainerLoader getInstance() {
        return INSTANCE;
    }

    /**
     * Return loaded containers.
     * @return containers.
     */
    public List<IContainer> getContainers() {
        if (CONTAINERS.isEmpty()) {
            log.info("Start loading containers");
            final Iterator<IContainer> iter = ServiceLoader.load(IContainer.class).iterator();
            IContainer container;
            while (iter.hasNext()) {
                container = iter.next();
                CONTAINERS.add(container);
                if (ClassUtil.isInstance(container, ICdiContainer.class)) {
                    this.cdiContainer = ObjectUtil.cast(container, ICdiContainer.class);
                }
            }
            log.info("End of loading {} containers {}", CONTAINERS.size(), CONTAINERS);
        }
        return CONTAINERS;
    }

}
