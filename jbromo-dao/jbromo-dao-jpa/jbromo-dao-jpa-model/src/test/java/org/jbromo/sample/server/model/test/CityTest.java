package org.jbromo.sample.server.model.test;

import org.jbromo.model.jpa.test.AbstractEntityTest;
import org.jbromo.model.jpa.test.builder.AbstractEntityBuilderFactory;
import org.jbromo.sample.server.model.src.City;

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
