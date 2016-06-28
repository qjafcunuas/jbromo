package org.jbromo.sample.server.model.test;

import org.jbromo.model.jpa.AbstractEntityTest;
import org.jbromo.model.jpa.testutil.builder.AbstractEntityBuilderFactory;
import org.jbromo.sample.server.model.src.UserGroup;
import org.jbromo.sample.server.model.test.builder.EntityBuilderFactory;

/**
 * JUnit UserGroup class.
 *
 * @author qjafcunuas
 *
 */
public class UserGroupTest extends AbstractEntityTest<UserGroup, Long> {

    @Override
    protected AbstractEntityBuilderFactory getEntityFactory() {
        return EntityBuilderFactory.getInstance();
    }

}
