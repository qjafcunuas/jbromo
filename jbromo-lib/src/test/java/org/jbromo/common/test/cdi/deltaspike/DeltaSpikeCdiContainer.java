package org.jbromo.common.test.cdi.deltaspike;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.jbromo.common.test.cdi.ICdiContainer;

/**
 * Define the DeltaSpike CDI container.
 *
 * @author qjafcunuas
 *
 */
public class DeltaSpikeCdiContainer implements ICdiContainer {

    /**
     * The DeltaSpike CDI container.
     */
	private CdiContainer container;

	@Override
	public void start() {
        this.container = CdiContainerLoader.getCdiContainer();
        this.container.boot();
	}

	@Override
	public void postStart() {
		final ContextControl contextControl = this.container.getContextControl();
		contextControl.startContext(ApplicationScoped.class);
		contextControl.startContext(SessionScoped.class);
		contextControl.startContext(RequestScoped.class);
	}

	@Override
	public void preStop() {
		final ContextControl contextControl = this.container.getContextControl();
		contextControl.stopContext(RequestScoped.class);
		contextControl.stopContext(SessionScoped.class);
		contextControl.stopContext(ApplicationScoped.class);
	}

	@Override
	public void stop() {
		if (this.container != null) {
			this.container.shutdown();
		}
	}

	@Override
    public <T> T select(final Class<T> theClass) {
		return BeanProvider.getContextualReference(theClass);
	}

}
