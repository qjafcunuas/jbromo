package org.jbromo.sample.server.model.test.common;

import org.jbromo.sample.server.model.src.common.UserCalendar;
import org.junit.Assert;
import org.junit.Test;

/**
 * Define JUnit UserCalendar class.
 *
 * @author qjafcunuas
 *
 */
public class UserCalendarTest {

    /**
     * Test toString method.
     */
    @Test
    public void toStringTest() {
        final UserCalendar ucal = new UserCalendar();
        Assert.assertNotNull(ucal.toString());
    }

}
