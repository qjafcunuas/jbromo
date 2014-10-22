package org.jbromo.webapp.jsf.sample.i18n;

import lombok.Getter;

import org.jbromo.common.i18n.IMessageKey;

/**
 * Define Application messages.
 * 
 * @author qjafcunuas
 * 
 */
public enum AppMessageKey implements IMessageKey {
    /** role.admin. */
    ROLE_ADMIN("role.admin"),
    /** role.guest. */
    ROLE_GUEST("role.guest"),
    /** view.messageLabel.text. */
    VIEW_MESSAGE_LABEL_TEXT("view.messageLabel.text"),
    /** view.login.error. */
    VIEW_LOGIN_ERROR("view.login.error"),
    /** error.result.tooMuch. */
    ERROR_RESULT_TOO_MUCH("error.result.tooMuch");

    /**
     * The message key.
     */
    @Getter
    private String key;

    /**
     * Default constructor.
     * 
     * @param key
     *            the i18n key.
     */
    private AppMessageKey(final String key) {
        this.key = key;
    }

}
