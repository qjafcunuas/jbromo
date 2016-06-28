package org.jbromo.sample.server.model.test.compositepk;

import org.jbromo.model.jpa.compositepk.AbstractCompositePkTest;
import org.jbromo.model.jpa.testutil.builder.AbstractEntityBuilderFactory;
import org.jbromo.sample.server.model.src.compositepk.UserSurnamePk;
import org.jbromo.sample.server.model.test.builder.EntityBuilderFactory;

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
