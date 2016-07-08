/*
 * Copyright (C) 2013-2014 The JBromo Authors. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.jbromo.webapp.jsf.util.browser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.jbromo.common.exception.MessageLabelException;
import org.jbromo.common.test.cdi.CdiRunner;
import org.jbromo.common.test.common.ConstructorUtil;
import org.jbromo.webapp.jsf.cdi.CDIFacesUtil;
import org.jbromo.webapp.jsf.test.AbstractFacesTest;
import org.jbromo.webapp.jsf.test.mock.servlet.MockServletOutputStream;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.extern.slf4j.Slf4j;

/**
 * Define JUnit DownloadUtil class.
 * @author qjafcunuas
 */
@RunWith(CdiRunner.class)
@Slf4j
public class DownloadUtilTest extends AbstractFacesTest {

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        ConstructorUtil.executePrivate(DownloadUtil.class);
    }

    /**
     * Test download method.
     */
    @Test
    public void download() {
        final String fileContent = "Bromo download JUnit";
        // create file to download.
        File inputFile = null;
        File outputFile = null;
        try {
            inputFile = File.createTempFile("download", ".jbromo");
            outputFile = ((MockServletOutputStream) CDIFacesUtil.getResponse().getOutputStream()).getFile();
        } catch (final IOException e) {
            log.error("Cannot create temporary file", e);
            Assert.fail("Cannot create temporary file");
        }

        // Transfer file to servlet api.
        try {
            Files.write(Paths.get(inputFile.toURI()), fileContent.getBytes());
            DownloadUtil.download(inputFile);
        } catch (final MessageLabelException e) {
            Assert.fail("Cannot download file");
        } catch (final IOException e) {
            log.error("Cannot write into file", e);
            Assert.fail("Cannot write into file");
        } finally {
            inputFile.delete();
        }

        // Test file transfer.
        try {
            final List<String> lines = Files.readAllLines(Paths.get(outputFile.toURI()));
            Assert.assertEquals(1, lines.size());
            Assert.assertEquals(lines.get(0), fileContent);
        } catch (final IOException e) {
            log.error("Cannot read output file", e);
            Assert.fail("Cannot read output file");
        }
    }

    /**
     * Test download method.
     */
    // @Test
    public void download2() {
        download();
    }

}
