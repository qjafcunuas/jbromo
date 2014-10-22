package org.jbromo.webapp.jsf.sample.view.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.jbromo.webapp.jsf.security.ISecurityUser;

/**
 * Define the security user.
 * 
 * @author qjafcunuas
 * 
 */
@AllArgsConstructor
@Getter
public class SecurityUser implements ISecurityUser {

    /**
     * serial version UID.?
     */
    private static final long serialVersionUID = 7272339795538515111L;

    /**
     * The login.
     */
    private final String login;

}
