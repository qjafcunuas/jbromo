package org.jbromo.common.test.jul;

import org.jbromo.common.test.common.IContainer;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Define Java Utility logging (JUL) container so that logs from JUL are redirected to slf4j.
 * @author qjafcunuas
 *
 */
public class JulContainer implements IContainer {

    @Override
    public void start() {
        // needed only for the JUL bridge: http://stackoverflow.com/a/9117188/1915920
        java.util.logging.LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        java.util.logging.Logger.getLogger( "global" ).setLevel( java.util.logging.Level.FINEST );
    }

    @Override
    public void postStart() {
        // Nothing to do.
    }

    @Override
    public void preStop() {
        // Nothing to do.
    }

    @Override
    public void stop() {
        // Nothing to do.
    }

}
