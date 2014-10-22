package org.jbromo.common.test.common;

/**
 * Define CDI container interface.
 * 
 * @author qjafcunuas
 * 
 */
public interface IContainer {
	/**
	 * Start the container.
	 */
	void start();

	/**
	 * Initialize the container after all used container are started.
	 */
	void postStart();

	/**
	 * Execute method on the container before all used container will be stopped.
	 */
	void preStop();

	/**
	 * Stop the CDI container.
	 */
	void stop();

}
