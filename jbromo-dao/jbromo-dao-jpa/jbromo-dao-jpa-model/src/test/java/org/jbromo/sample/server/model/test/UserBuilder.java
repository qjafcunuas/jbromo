package org.jbromo.sample.server.model.test;

import org.jbromo.common.StringUtil;
import org.jbromo.model.jpa.test.builder.AbstractEntityBuilder;
import org.jbromo.model.jpa.test.builder.AbstractEntityBuilderFactory;
import org.jbromo.sample.server.model.src.User;

/**
 * Define user entity builder.
 *
 * @author qjafcunuas
 *
 */
public class UserBuilder extends AbstractEntityBuilder<User> {

    /**
     * Default constructor.
     *
     * @param factory
     *            the factory to used.
     */
    protected UserBuilder(final AbstractEntityBuilderFactory factory) {
        super(factory);
        // Don't forget to register your builder in your factory.
    }

    /**
     * Modifies the instance of the user.
     *
     * @param user
     *            the user instance to modify
     * @param acceptNullField
     *            if true, entity fields can be null if it is authorized by the
     *            mapping.
     */
    public void modify(final User user, final boolean acceptNullField) {
        super.modify(user, acceptNullField);
        user.setEncryptedPassword(StringUtil.encrypt(user
                .getEncryptedPassword()));
    }

}
