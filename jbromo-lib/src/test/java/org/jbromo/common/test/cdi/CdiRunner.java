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
package org.jbromo.common.test.cdi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.enterprise.inject.spi.BeanManager;

import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.test.common.IContainer;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * Implements the JUnit 4 standard test case class model with Weld container.
 */
@Slf4j
public class CdiRunner extends BlockJUnit4ClassRunner {

    /**
     * Default constructor.
     *
     * @param klass
     *            the class to test.
     * @throws InitializationError
     *             exception.
     */
    public CdiRunner(final Class<Object> klass) throws InitializationError {
        super(klass);
    }

    /**
     * Start all containers.
     */
    private void start() {
        final List<IContainer> containers = CdiContainerLoader.getInstance()
                .getContainers();
        // Start containers.
        log.info("Start {} containers", containers.size());
        for (final IContainer container : containers) {
            container.start();
        }

        // Post-start containers.
        log.info("Post-start all containers");
        for (final IContainer container : containers) {
            container.postStart();
        }

        // Fire event for all started container.
        log.info("Fire started container event.");
        CdiContainerLoader.getInstance().getCdiContainer()
                .select(BeanManager.class).fireEvent(new CdiStartedEvent());

    }

    /**
     * Stop all containers.
     */
    private void stop() {
        final List<IContainer> containers = CdiContainerLoader.getInstance()
                .getContainers();
        // Fire event for all started container.
        log.info("Fire shutdown container event.");
        CdiContainerLoader.getInstance().getCdiContainer()
                .select(BeanManager.class).fireEvent(new CdiShutdownEvent());

        // Pre-stop containers.
        log.info("Pre-stop {} containers", containers.size());
        final List<IContainer> reverse = new ArrayList<IContainer>(containers);
        Collections.reverse(reverse);
        for (final IContainer container : reverse) {
            container.preStop();
        }

        // Stop containers.
        log.info("Stop all containers");
        for (final IContainer container : reverse) {
            container.stop();
        }
    }

    @Override
    public void run(final RunNotifier arg0) {
        start();
        // Run tests.
        super.run(arg0);
        stop();

    }

    @Override
    protected Object createTest() throws Exception {
        return CdiContainerLoader.getInstance().getCdiContainer()
                .select(getTestClass().getJavaClass());
    }

}
