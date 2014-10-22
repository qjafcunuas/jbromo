/*
 * Copyright (C) 2013-2014 The JBroimport javax.faces.context.FacesContext;
import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
out restriction, including without limitation the rights
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
package org.jbromo.webapp.jsf.test;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * Default Faces test implementation.
 *
 * @author qjafcunuas
 *
 */
public class AbstractFacesTest {

    /**
     * The faces context.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private FacesContext facesContext;

    /**
     * Run before each test.
     */
    @Before
    public void beforeAbstractFacesTest() {
        Assert.assertNotNull(getFacesContext());
        Assert.assertNotNull(getFacesContext().getExternalContext());
        Assert.assertNotNull(getFacesContext().getExternalContext()
                .getRequest());
        Assert.assertNotNull(getFacesContext().getExternalContext()
                .getResponse());
        Assert.assertNotNull(getFacesContext().getELContext());
    }

    @After
    public void toto() {
        BromoWeldExtension.requestMap.clear();
    }

}
