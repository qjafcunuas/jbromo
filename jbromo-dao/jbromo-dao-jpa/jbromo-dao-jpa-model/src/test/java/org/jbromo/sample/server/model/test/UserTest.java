package org.jbromo.sample.server.model.test;

import org.jbromo.common.IntegerUtil;
import org.jbromo.model.jpa.AbstractEntityTest;
import org.jbromo.model.jpa.testutil.builder.AbstractEntityBuilderFactory;
import org.jbromo.sample.server.model.src.User;
import org.jbromo.sample.server.model.test.builder.EntityBuilderFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit User class.
 * @author qjafcunuas
 */
public class UserTest extends AbstractEntityTest<User, Long> {

    /**
     * The user group test.
     */
    private final UserGroupTest userGroupTest = new UserGroupTest();

    /**
     * The city test.
     */
    private final CityTest cityTest = new CityTest();

    /**
     * Create entities used by user.
     */
    @Before
    public final void initialize() {
        Assert.assertNotNull(this.userGroupTest);
        this.userGroupTest.initialize(IntegerUtil.INT_10);
        Assert.assertNotNull(this.cityTest);
        this.cityTest.initialize(IntegerUtil.INT_10);
    }

    @Override
    protected AbstractEntityBuilderFactory getEntityFactory() {
        return EntityBuilderFactory.getInstance();
    }

    /**
     * Test group.
     */
    @Test
    public void group() {
        final User user = getEntityBuilder().newEntity(false);
        Assert.assertNotNull(user.getOneToOneGroup());
        Assert.assertNotNull(user.getManyToOneGroup());
        Assert.assertNotNull(user.getManyToManyGroups());
        Assert.assertFalse(user.getManyToManyGroups().isEmpty());
    }

    /**
     * Test addresses.
     */
    @Test
    public void addresses() {
        final User user = getEntityBuilder().newEntity(false);
        Assert.assertNotNull(user.getAddresses());
        Assert.assertFalse(user.getAddresses().isEmpty());
    }

    /**
     * Test surnames.
     */
    @Test
    public void surnames() {
        final User user = getEntityBuilder().newEntity(false);
        Assert.assertNotNull(user.getSurnames());
        Assert.assertFalse(user.getSurnames().isEmpty());
    }

    /**
     * Test isActive.
     */
    @Test
    public void isActive() {
        final User user = new User();
        Assert.assertFalse(user.isActive());
        user.setActive(null);
        Assert.assertFalse(user.isActive());
        user.setActive(Boolean.TRUE);
        Assert.assertTrue(user.isActive());
        user.setActive(Boolean.FALSE);
        Assert.assertFalse(user.isActive());
    }

}
