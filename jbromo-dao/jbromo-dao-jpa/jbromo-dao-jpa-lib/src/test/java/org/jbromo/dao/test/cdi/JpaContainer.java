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
package org.jbromo.dao.test.cdi;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jbromo.common.test.cdi.CdiContainerLoader;
import org.jbromo.common.test.common.IContainer;
import org.jbromo.dao.jpa.container.common.JpaProviderFactory;
import org.junit.Assert;

import lombok.extern.slf4j.Slf4j;

/**
 * Define the delta spike external container for JPA. This class must be referenced in
 * META-INF\services\org.apache.deltaspike.testcontrol.spi.ExternalContainer file.
 * @author qjafcunuas
 */
@Slf4j
public class JpaContainer implements IContainer {

    /**
     * Define persistence unit name.
     */
    private static final String PERSISTENCE_UNIT_NAME = "JBromoTestPU";

    /**
     * The entity manager factory.
     */
    static EntityManagerFactory emf;

    @Override
    public void start() {
        log.info("Starting persistence unit {}", PERSISTENCE_UNIT_NAME);
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        log.info("Persistence unit {} started", PERSISTENCE_UNIT_NAME);
    }

    @Override
    public void postStart() {
        Assert.assertNotNull(JpaProviderFactory.getInstance().getImplementation());
        // Force CDI to load entity terminator so that at the end of the test,
        // it will be applied for deleting all entities (PreDestroy).
        CdiContainerLoader.getInstance().getCdiContainer().select(CdiEntityTerminator.class).toString();
    }

    @Override
    public void preStop() {
        // Nothing to do.
    }

    @Override
    public void stop() {
        log.info("Shutdowning Persistence unit");
        emf.close();
        emf = null;
        log.info("Persistence unit shutdowned");
    }

}
