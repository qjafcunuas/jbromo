package org.jbromo.sample.server.model.test;

import org.jbromo.common.IntegerUtil;
import org.jbromo.model.jpa.test.AbstractEntityTest;
import org.jbromo.model.jpa.test.builder.AbstractEntityBuilderFactory;
import org.jbromo.sample.server.model.src.User;
import org.jbromo.sample.server.model.src.UserAddress;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit UserAddress class.
 *
 * @author qjafcunuas
 *
 */
public class UserAddressTest extends AbstractEntityTest<UserAddress, Long> {

    /**
     * The city test.
     */
    private final CityTest cityTest = new CityTest();

    @Override
    protected AbstractEntityBuilderFactory getEntityFactory() {
        return EntityBuilderFactory.getInstance();
    }

    /**
     * Create entities city.
     */
    @Before
    public final void initialize() {
        Assert.assertNotNull(this.cityTest);
        this.cityTest.initialize(IntegerUtil.INT_10);
    }

    /**
     * Test city.
     */
    @Test
    public void city() {
        final UserAddress address = getEntityBuilder().newEntity(false);
        Assert.assertNotNull(address.getCity());
    }

    /**
     * Test empty constructor.
     */
    @Test
    public void constructorEmpty() {
        final UserAddress address = new UserAddress();
        Assert.assertNull(address.getUser());
    }

    /**
     * Test full constructor.
     */
    @Test
    public void constructorFull() {
        User user = null;

        // null
        user = null;
        UserAddress address = new UserAddress(user);
        Assert.assertNull(address.getUser());

        // not null
        user = new User();
        address = new UserAddress(user);
        Assert.assertNotNull(address.getUser());
        Assert.assertEquals(address.getUser(), user);
        Assert.assertTrue(user.getAddresses().contains(address));
    }

}
