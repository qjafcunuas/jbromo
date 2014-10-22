package org.jbromo.webapp.jsf.sample.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.jbromo.common.i18n.IMessageKey;
import org.jbromo.webapp.jsf.sample.i18n.AppMessageKey;
import org.jbromo.webapp.jsf.security.ISecurityRole;

/**
 * Define security role.
 * 
 * @author qjafcunuas
 * 
 */
@AllArgsConstructor
public enum SecurityRole implements ISecurityRole {
    /** Admin role. */
    ADMIN(AppMessageKey.ROLE_ADMIN),
    /** Guest role. */
    GUEST(AppMessageKey.ROLE_GUEST);

    /**
     * The i18n key.
     */
    @Getter
    private final IMessageKey key;

    @Override
    public String getName() {
        return name();
    }

}
