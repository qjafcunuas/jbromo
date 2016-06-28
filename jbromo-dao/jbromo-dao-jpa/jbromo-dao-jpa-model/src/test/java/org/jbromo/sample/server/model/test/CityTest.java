package org.jbromo.sample.server.model.test;

import org.jbromo.model.jpa.AbstractEntityTest;
import org.jbromo.model.jpa.testutil.builder.AbstractEntityBuilderFactory;
import org.jbromo.sample.server.model.src.City;
import org.jbromo.sample.server.model.test.builder.EntityBuilderFactory;

/**
 * JUnit City class.
 *
 * @author qjafcunuas
 *
 */
public class CityTest extends AbstractEntityTest<City, Long> {

    @Override
    protected AbstractEntityBuilderFactory getEntityFactory() {
        return EntityBuilderFactory.getInstance();
    }

}
