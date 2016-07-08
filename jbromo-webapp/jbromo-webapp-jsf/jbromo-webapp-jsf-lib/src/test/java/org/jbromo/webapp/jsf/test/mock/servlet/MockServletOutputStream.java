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
package org.jbromo.webapp.jsf.test.mock.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;

import org.junit.Assert;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Mock the ServletOutputStream.
 * @author qjafcunuas
 */
@Slf4j
public class MockServletOutputStream extends ServletOutputStream {

    /**
     * The output file stream.
     */
    private FileOutputStream fileStream;

    /**
     * The output file.
     */
    @Getter
    private File file;

    /**
     * Initialize object.
     * @throws IOException exception.
     */
    private void init() throws IOException {
        this.file = File.createTempFile("servletoutputstream", ".jbromo");
        this.file.deleteOnExit();
        this.fileStream = new FileOutputStream(this.file);
    }

    /**
     * Mock the stream.
     * @return the stream.
     */
    public static MockServletOutputStream mock() {
        try {
            final MockServletOutputStream stream = new MockServletOutputStream();
            stream.init();
            return stream;
        } catch (final IOException e) {
            log.error("Cannot mock ServletOutputStream.", e);
            Assert.fail("Cannot mock ServletOutputStream.");
        }
        return null;
    }

    @Override
    public void flush() throws IOException {
        this.fileStream.flush();
    }

    @Override
    public void write(final int b) throws IOException {
        this.fileStream.write(b);
    }

    @Override
    public void write(final byte[] b) throws IOException {
        this.fileStream.write(b);
    }

    @Override
    public void write(final byte[] b, final int off, final int len) throws IOException {
        this.fileStream.write(b, off, len);
    }

    @Override
    public void close() throws IOException {
        this.fileStream.close();
    }

}
