/*-
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
package org.jbromo.common.i18n;

import java.util.Set;

import org.jbromo.common.SetUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Define JUnit IOrderBy test.
 * @author qjafcunuas
 */
public class MessageKeyTest {

    /**
     * Test enum.
     */
    @Test
    public void sort() {
        final Set<String> set = SetUtil.toSet();
        for (final MessageKey one : MessageKey.values()) {
            switch (one) {
                case ARROW_DOWN:
                case ARROW_LEFT:
                case ARROW_LEFT_AND_RIGHT:
                case ARROW_RIGHT:
                case ARROW_UP:
                case ARROW_UP_AND_DOWN:
                case DEFAULT_MESSAGE:
                case ENTITY_MUST_BE_NOT_NULL:
                case ENTITY_PK_MUST_BE_NOT_NULL:
                case ENTITY_TO_CREATE_ALREADY_EXITS:
                case ENTITY_TO_CREATE_IS_NULL:
                case ENTITY_TO_DELETE_IS_NULL:
                case ENTITY_TO_DELETE_IS_USED:
                case ENTITY_TO_DELETE_NOT_FOUND:
                case ENTITY_TO_UPDATE_IS_NULL:
                case ENTITY_TO_UPDATE_NOT_FOUND:
                case ENTITY_TOO_MUCH_RESULT:
                case ENTITY_VALIDATION_ERROR:
                case ERROR_DOWNLOAD_FILE:
                case FALSE:
                case NO:
                case TOO_MUCH_RESULT_MESSAGE:
                case TRUE:
                case UNAUTHORIZED_ACCESS:
                case YES:
                    if (set.contains(one.getKey())) {
                        Assert.fail("Message key should not contains a key more than one.");
                    }
                    set.add(one.getKey());
                    break;
                default:
                    Assert.fail("Not tested");
                    break;
            }
        }
    }

}
