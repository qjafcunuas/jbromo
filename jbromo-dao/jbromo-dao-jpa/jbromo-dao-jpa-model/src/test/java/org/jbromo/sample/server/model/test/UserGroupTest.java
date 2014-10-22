package org.jbromo.sample.server.model.test;

import org.jbromo.model.jpa.test.AbstractEntityTest;
import org.jbromo.model.jpa.test.builder.AbstractEntityBuilderFactory;
import org.jbromo.sample.server.model.src.UserGroup;

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
