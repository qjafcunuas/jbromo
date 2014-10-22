/*
 * Copyright (C) 2013-2014 The JBromo Authors.
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.mockito.Mockito;
 without limitation the rights
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
package org.jbromo.webapp.jsf.test.mock.servlet;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.mockito.Mockito;

/**
 * Mock the HttpServletResponse.
 *
 * @author qjafcunuas
 *
 */
@Slf4j
public final class MockHttpServletResponse {

    /**
     * Default constructor.
     */
    private MockHttpServletResponse() {
        super();
    }

    /**
     * Mock the response.
     *
     * @return the response.
     */
    public static HttpServletResponse mock() {
        final HttpServletResponse response = Mockito
                .mock(HttpServletResponse.class);

        final ServletOutputStream stream = MockServletOutputStream.mock();
        try {
            Mockito.when(response.getOutputStream()).thenReturn(stream);
        } catch (final IOException e) {
            log.error("Cannot get outputStream", e);
            Assert.fail("Cannot get outputStream");
        }

        return response;
    }

}
