package org.jbromo.sample.server.model.test;

import lombok.Getter;

import org.jbromo.model.jpa.test.builder.AbstractEntityBuilderFactory;
import org.jbromo.sample.server.model.src.User;

/**
 * Define entity builder factory.
 *
 * @author qjafcunuas
 *
 */
public final class EntityBuilderFactory extends AbstractEntityBuilderFactory {

    /**
     * The singleton instance.
     */
    @Getter
    private static EntityBuilderFactory instance = new EntityBuilderFactory();

    /**
     * Default constructor.
     */
    private EntityBuilderFactory() {
        super();
        setBuilderClass(User.class, UserBuilder.class);
    }

}
