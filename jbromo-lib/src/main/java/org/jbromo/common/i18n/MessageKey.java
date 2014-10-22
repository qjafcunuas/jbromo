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
package org.jbromo.common.i18n;

import lombok.Getter;

/**
 * Define message key.
 *
 * @author qjafcunuas.
 */
public enum MessageKey implements IMessageKey {

    /**
     * Message used to give a creation information.
     */
    ENTITY_TO_CREATE_IS_NULL("error.entity.create.null"),

    /**
     * Message used to give a upgrade information.
     */
    ENTITY_TO_UPDATE_IS_NULL("error.entity.update.null"),

    /**
     * Message used to give a upgrade information.
     */
    ENTITY_TO_UPDATE_NOT_FOUND("error.entity.update.notfound"),

    /**
     * Message used to give a deletion information.
     */
    ENTITY_TO_DELETE_NOT_FOUND("error.entity.delete.notfound"),

    /**
     * Message used to give a creation information.
     */
    ENTITY_TO_CREATE_ALREADY_EXITS("error.entity.create.exists"),

    /**
     * Message used to give a validation error information.
     */
    ENTITY_VALIDATION_ERROR("error.entity.validation.error"),

    /**
     * Message used to give a deletion information.
     */
    ENTITY_TO_DELETE_IS_USED("error.entity.used"),

    /**
     * Message used to give a deletion information.
     */
    ENTITY_TO_DELETE_IS_NULL("error.entity.delete.null"),

    /**
     * Message used to give a big result information.
     */
    ENTITY_TOO_MUCH_RESULT("error.entity.result.tooMuch"),

    /**
     * Message used to give a read information.
     */
    ENTITY_PK_MUST_BE_NOT_NULL("error.entity.pk.null"),

    /**
     * Message used to give a read information.
     */
    ENTITY_MUST_BE_NOT_NULL("error.entity.null"),

    /**
     * Too much result message.
     */
    TOO_MUCH_RESULT_MESSAGE("error.result.tooMuch"),

    /**
     * Unauthorized access.
     */
    UNAUTHORIZED_ACCESS("error.access.unauthorized"),
    /**
     * Default message.
     */
    DEFAULT_MESSAGE("error.default.message"),
    /**
     * True message.
     */
    TRUE("default.label.true"),
    /**
     * False message.
     */
    FALSE("default.label.false"),
    /**
     * Yes message.
     */
    YES("default.label.yes"),
    /**
     * No message.
     */
    NO("default.label.no"),
    /**
     * arrow.down.
     */
    ARROW_DOWN("arrow.down"),
    /**
     * arrow.up.
     */
    ARROW_UP("arrow.up"),
    /**
     * arrow.upanddown.
     */
    ARROW_UP_AND_DOWN("arrow.upanddown"),
    /**
     * arrow.left.
     */
    ARROW_LEFT("arrow.left"),
    /**
     * arrow.leftandright.
     */
    ARROW_LEFT_AND_RIGHT("arrow.leftandright"),
    /**
     * arrow.right.
     */
    ARROW_RIGHT("arrow.right"),
    /**
     * error.download.file.
     */
    ERROR_DOWNLOAD_FILE("error.download.file");

    /**
     * The message key.
     */
    @Getter
    private final String key;

    /**
     * Default constructor.
     *
     * @param key
     *            message key
     */
    private MessageKey(final String key) {
        this.key = key;
    }

}
