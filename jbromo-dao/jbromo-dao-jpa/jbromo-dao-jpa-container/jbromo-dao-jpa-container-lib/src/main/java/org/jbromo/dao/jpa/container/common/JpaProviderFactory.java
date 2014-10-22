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
package org.jbromo.dao.jpa.container.common;

import java.util.Iterator;
import java.util.ServiceLoader;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Define the JpaProvider Factory.
 *
 * @author qjafcunuas
 *
 */
@Slf4j
public final class JpaProviderFactory {

	/**
	 * The singleton instance.
	 */
	private static final JpaProviderFactory INSTANCE = new JpaProviderFactory();

	/**
	 * The current implementation.
	 */
	@Getter
	private IJpaProvider implementation;

	/**
	 * Return the singleton instance.
	 *
	 * @return the instance.
	 */
	public static JpaProviderFactory getInstance() {
		return INSTANCE;
	}

	/**
	 * Default constructor.
	 */
	private JpaProviderFactory() {
		super();
		log.info("Start loading implementation");
		final Iterator<IJpaProvider> iter = ServiceLoader.load(
				IJpaProvider.class).iterator();
		if (iter.hasNext()) {
			this.implementation = iter.next();
			log.info("Use {} implementation", this.implementation.getClass());
		} else {
            log.error("Cannot find implementation of interface {}",
                    IJpaProvider.class);
		}
		while (iter.hasNext()) {
			log.warn("Found more than one implementation of {} interface: {}",
					IJpaProvider.class, iter.next().getClass());
		}

	}

}
