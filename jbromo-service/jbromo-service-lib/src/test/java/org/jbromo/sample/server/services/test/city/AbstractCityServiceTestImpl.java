/*
 * Copyright (C) 2013-2014 The JBromo Authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jbromo.sample.server.services.test.city;

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.sample.server.model.src.City;
import org.jbromo.sample.server.services.src.city.ICityService;
import org.jbromo.sample.server.services.test.AbstractEntityServiceTest;
import org.jbromo.service.Service;

/**
 * Define the JUnit service test.
 *
 * @author qjafcunuas
 *
 */
public abstract class AbstractCityServiceTestImpl extends
        AbstractEntityServiceTest<City, Long> {

    /**
     * The service to test.
     */
    @Inject
    @Service
    @Getter(AccessLevel.PROTECTED)
    private ICityService crud;

}
