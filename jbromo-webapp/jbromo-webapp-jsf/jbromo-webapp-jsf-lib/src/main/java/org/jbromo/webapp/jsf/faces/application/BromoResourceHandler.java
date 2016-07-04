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
package org.jbromo.webapp.jsf.faces.application;

import java.util.Calendar;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.application.ResourceWrapper;

import org.jbromo.common.CalendarUtil;
import org.jbromo.webapp.jsf.cdi.CDIFacesUtil;
import org.jbromo.webapp.jsf.faces.BromoConfigKey;

/**
 * Define resource handler for adding framework version on resource link.
 * @author qjafcunuas
 */
public class BromoResourceHandler extends ResourceHandlerWrapper {

    /**
     * The wrapped version.
     */
    private final ResourceHandler wrapped;

    /**
     * The framework version.
     */
    private static String version;

    /**
     * The framework library.
     */
    private static String library;

    /**
     * Default constructor.
     * @param wrapped the wrapped resource.
     */
    public BromoResourceHandler(final ResourceHandler wrapped) {
        this.wrapped = wrapped;
    }

    /**
     * Return the framework version.
     * @return the version.
     */
    private String getVersion() {
        if (BromoResourceHandler.version == null) {
            String value = CDIFacesUtil.getFacesResourceBundle().getConfig(BromoConfigKey.BROMO_VERSION);
            final int pos = value.indexOf("-SNAPSHOT");
            if (pos > 0) {
                value = "&v=" + value + "-" + CalendarUtil.toString(Calendar.getInstance(), CalendarUtil.FORMAT.YYYYMMDDHHMMSS);
            }
            version = value;
        }
        return version;
    }

    /**
     * Return the framework version.
     * @return the version.
     */
    private String getLibrary() {
        if (BromoResourceHandler.library == null) {
            final String value = "?ln=" + CDIFacesUtil.getFacesResourceBundle().getConfig(BromoConfigKey.BROMO_LIBRARY);
            library = value;
        }
        return library;
    }

    @Override
    public Resource createResource(final String resourceName) {
        return createResource(resourceName, null, null);
    }

    @Override
    public Resource createResource(final String resourceName, final String libraryName) {
        return createResource(resourceName, libraryName, null);
    }

    @Override
    public Resource createResource(final String resourceName, final String libraryName, final String contentType) {
        final Resource resource = super.createResource(resourceName, libraryName, contentType);

        if (resource == null) {
            return null;
        }

        return new ResourceWrapper() {

            @Override
            public String getRequestPath() {
                final String path = super.getRequestPath();
                if (path.indexOf(getLibrary()) >= 0) {
                    return path + getVersion();
                } else {
                    return path;
                }
            }

            @Override
            // Necessary because this is missing in ResourceWrapper (will be
            // fixed in JSF 2.2).
            public String getResourceName() {
                return resource.getResourceName();
            }

            @Override
            // Necessary because this is missing in ResourceWrapper (will be
            // fixed in JSF 2.2).
            public String getLibraryName() {
                return resource.getLibraryName();
            }

            @Override
            // Necessary because this is missing in ResourceWrapper (will be
            // fixed in JSF 2.2).
            public String getContentType() {
                return resource.getContentType();
            }

            @Override
            public Resource getWrapped() {
                return resource;
            }
        };
    }

    @Override
    public ResourceHandler getWrapped() {
        return this.wrapped;
    }

}
