package org.jbromo.common.test.cdi.weld;

import org.jbromo.common.test.cdi.ICdiContainer;

/**
 * Define the Weld container access.
 *
 * @author qjafcunuas
 *
 */
public class WeldCdiContainer implements ICdiContainer {

    @Override
    public void start() {
        // TODO Auto-generated method stub

    }

    @Override
    public void postStart() {
        // TODO Auto-generated method stub

    }

    @Override
    public void preStop() {
        // TODO Auto-generated method stub

    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }

    @Override
    public <T> T select(final Class<T> theClass) {
        // TODO Auto-generated method stub
        return null;
    }

    // /**
    // * The weld object.
    // */
    // private Weld weld;
    // /**
    // * The weld container.
    // */
    // private WeldContainer container;
    //
    // @Override
    // public void start() {
    // this.weld = new Weld();
    // this.container = this.weld.initialize();
    // this.container.getBeanManager().fireEvent(new CdiStartedEvent());
    // }
    //
    // @Override
    // public void postStart() {
    // }
    //
    // @Override
    // public void preStop() {
    // }
    //
    // @Override
    // public void stop() {
    // try {
    // this.container.getBeanManager().fireEvent(new CdiShutdownEvent());
    // this.weld.shutdown();
    // } catch (final Exception e) {
    // log.error("Cannot stop Weld Container", e);
    // }
    // }
    //
    // @Override
    // public <T> T select(final Class<T> theClass) {
    // return this.container.instance().select(theClass).get();
    // }

}
