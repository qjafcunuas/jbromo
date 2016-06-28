package org.jbromo.sample.server.model.test;

import org.jbromo.common.IntegerUtil;
import org.jbromo.model.jpa.AbstractEntityTest;
import org.jbromo.model.jpa.testutil.builder.AbstractEntityBuilderFactory;
import org.jbromo.sample.server.model.src.City;
import org.jbromo.sample.server.model.src.User;
import org.jbromo.sample.server.model.src.UserSurname;
import org.jbromo.sample.server.model.src.compositepk.UserSurnamePk;
import org.jbromo.sample.server.model.test.builder.EntityBuilderFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit UserSurname class.
 *
 * @author qjafcunuas
 *
 */
public class UserSurnameTest extends
        AbstractEntityTest<UserSurname, UserSurnamePk> {

    /**
     * The user test.
     */
    private final UserTest userTest = new UserTest();

    /**
     * The city test.
     */
    private final CityTest cityTest = new CityTest();

    /**
     * Create entities used by user.
     */
    @Before
    public final void initialize() {
        Assert.assertNotNull(this.userTest);
        this.userTest.initialize(IntegerUtil.INT_10);
        Assert.assertNotNull(this.cityTest);
        this.cityTest.initialize(IntegerUtil.INT_10);
    }

    @Override
    protected AbstractEntityBuilderFactory getEntityFactory() {
        return EntityBuilderFactory.getInstance();
    }

    /**
     * Test empty constructor.
     */
    @Test
    public void constructorEmpty() {
        final UserSurname surname = new UserSurname();
        Assert.assertNotNull(surname.getPrimaryKey());
        Assert.assertNull(surname.getCity());
        Assert.assertNull(surname.getUser());
    }

    /**
     * Test full constructor.
     */
    @Test
    public void constructorFull() {
        User user = null;
        City city = null;

        // null, null
        UserSurname surname = new UserSurname(user, city);
        Assert.assertNotNull(surname.getPrimaryKey());
        Assert.assertNull(surname.getUser());
        Assert.assertNull(surname.getCity());

        // not null, null
        user = new User();
        city = null;
        surname = new UserSurname(user, city);
        Assert.assertNotNull(surname.getPrimaryKey());
        Assert.assertNotNull(surname.getUser());
        Assert.assertEquals(surname.getUser(), user);
        Assert.assertTrue(user.getSurnames().contains(surname));
        Assert.assertNull(surname.getCity());

        // null, not null
        user = null;
        city = new City();
        surname = new UserSurname(user, city);
        Assert.assertNotNull(surname.getPrimaryKey());
        Assert.assertNull(surname.getUser());
        Assert.assertNotNull(surname.getCity());

        // not null, not null
        user = new User();
        city = new City();
        surname = new UserSurname(user, city);
        Assert.assertNotNull(surname.getPrimaryKey());
        Assert.assertNotNull(surname.getUser());
        Assert.assertEquals(surname.getUser(), user);
        Assert.assertTrue(user.getSurnames().contains(surname));
        Assert.assertNotNull(surname.getCity());
        Assert.assertEquals(surname.getCity(), city);
    }

}
