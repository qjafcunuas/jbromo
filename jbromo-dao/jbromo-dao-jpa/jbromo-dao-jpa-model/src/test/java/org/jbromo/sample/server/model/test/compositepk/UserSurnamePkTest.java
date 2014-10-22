package org.jbromo.sample.server.model.test.compositepk;

import org.jbromo.model.jpa.test.AbstractCompositePkTest;
import org.jbromo.model.jpa.test.builder.AbstractEntityBuilderFactory;
import org.jbromo.sample.server.model.src.compositepk.UserSurnamePk;
import org.jbromo.sample.server.model.test.EntityBuilderFactory;

/**
 * Define JUnit UserSurnamePk class.
 *
 * @author qjafcunuas
 *
 */
public class UserSurnamePkTest extends AbstractCompositePkTest<UserSurnamePk> {

    @Override
    protected AbstractEntityBuilderFactory getEntityFactory() {
        return EntityBuilderFactory.getInstance();
    }

}
