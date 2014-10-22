package org.jbromo.sample.server.model.test;

import lombok.Getter;

import org.jbromo.model.jpa.test.asserts.AbstractEntityAssertFactory;

/**
 * Define entity assert factory.
 * 
 * @author qjafcunuas
 * 
 */
public final class EntityAssertFactory extends AbstractEntityAssertFactory {

    /**
     * The singleton instance.
     */
    @Getter
    private static EntityAssertFactory instance = new EntityAssertFactory();

    /**
     * Default constructor.
     */
    private EntityAssertFactory() {
        super();
    }

}
