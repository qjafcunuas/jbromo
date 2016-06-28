/*
 * Copyright (C) 2013-2014 The JBromo Authors.import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.test.asserts.AbstractEntityAssert;
import org.jbromo.model.jpa.test.builder.AbstractEntityBuilder;
import org.jbromo.sample.server.model.test.EntityAssertFactory;
import org.jbromo.sample.server.model.test.EntityBuilderFactory;
import org.jbromo.service.test.AbstractDefaultEntityServiceTest;
conditions:
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
package org.jbromo.sample.server.services.test;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.model.jpa.IEntity;
import org.jbromo.model.jpa.testutil.asserts.AbstractEntityAssert;
import org.jbromo.model.jpa.testutil.builder.AbstractEntityBuilder;
import org.jbromo.sample.server.model.test.asserts.EntityAssertFactory;
import org.jbromo.sample.server.model.test.builder.EntityBuilderFactory;
import org.jbromo.service.test.AbstractDefaultEntityServiceTest;

/**
 * Abstract JPA Service JUnit.
 *
 * @param <E>
 *            the entity type.
 * @param <PK>
 *            the primary key type.
 * @author qjafcunuas
 *
 */
public abstract class AbstractEntityServiceTest<E extends IEntity<PK>, PK extends Serializable>
        extends AbstractDefaultEntityServiceTest<E, PK> {

    /**
     * The entity builder.
     */
    @Getter(AccessLevel.PROTECTED)
    private final AbstractEntityBuilder<E> entityBuilder = EntityBuilderFactory
            .getInstance().getBuilder(getEntityClass());

    /**
     * The entity assert.
     */
    @Getter(AccessLevel.PROTECTED)
    private final AbstractEntityAssert<E> entityAssert = EntityAssertFactory
            .getInstance().getAssert(getEntityClass());

}
