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
package org.jbromo.webapp.jsf.util.browser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.common.exception.MessageLabelExceptionFactory;
import org.jbromo.common.i18n.MessageKey;
import org.jbromo.webapp.jsf.cdi.CDIFacesUtil;
import org.jbromo.webapp.jsf.faces.FacesContextUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for downloading a file into a browser.
 * @author qjafcunuas
 */
@Slf4j
public final class DownloadUtil {

    /**
     * Default constructor.
     */
    private DownloadUtil() {
        super();
    }

    /**
     * Download the file.
     * @param file the file to download.
     * @throws MessageLabelException exception.
     */
    public static void download(final File file) throws MessageLabelException {
        final HttpServletResponse response = CDIFacesUtil.getResponse();

        response.setHeader("Content-disposition", "attachment; filename= " + file.getName());
        response.setHeader("Content-Length", Long.toString(file.length()));
        response.setContentType(getContentType(file));

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {

            final byte[] buffer = new byte[IntegerUtil.INT_1024];
            int bytesRead = bis.read(buffer);

            while (bytesRead != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
                bytesRead = bis.read(buffer);
            }
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContextUtil.getFacesContext().responseComplete();
        } catch (final IOException e) {
            log.error("Error on downloaded file", e);
            throw MessageLabelExceptionFactory.getInstance().newInstance(MessageKey.ERROR_DOWNLOAD_FILE);
        }
    }

    /**
     * Return the content-type of a file, according to it extension.
     * @param file the file.
     * @return the content-type.
     */
    private static String getContentType(final File file) {
        final MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        final String contentType = mimeTypesMap.getContentType(file);
        if (contentType == null) {
            return "application/octet-stream";
        } else {
            return contentType;
        }
    }

}
