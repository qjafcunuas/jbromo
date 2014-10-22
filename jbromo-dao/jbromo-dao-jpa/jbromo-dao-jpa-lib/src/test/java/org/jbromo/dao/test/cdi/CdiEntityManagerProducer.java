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
package org.jbromo.dao.test.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;

import lombok.extern.slf4j.Slf4j;

/**
 * Define the entity manager producer. Note that this package is excluded for
 * Arquillian deployment.
 *
 * @author qjafcunuas
 *
 */
@ApplicationScoped
@Slf4j
public class CdiEntityManagerProducer {

    /**
     * The default entity manager.
     */
    private EntityManager entityManager;

    /**
     * Produces the entity manager.
     *
     * @return the entity manager.
     */
    @Produces
    public EntityManager getEntityManager() {
        if (this.entityManager == null) {
            log.info("Creating an entity manager.");
            this.entityManager = JpaContainer.emf.createEntityManager();
        }
        return this.entityManager;
    }

    /**
     * Close the entity manager.
     *
     * @param entityManager
     *            the manager to close.
     */
    protected void closeEntityManager(
            @Disposes final EntityManager entityManager) {
        log.info("Closing an entity manager.");
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
        if (this.entityManager == entityManager) {
            this.entityManager = null;
        }
    }
}
