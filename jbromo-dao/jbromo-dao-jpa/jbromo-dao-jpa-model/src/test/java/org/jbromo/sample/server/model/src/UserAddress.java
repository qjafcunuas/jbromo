package org.jbromo.sample.server.model.src;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.jbromo.common.IntegerUtil;
import org.jbromo.model.jpa.AbstractEntityId;

/**
 * A user address.
 *
 * @author qjafcunuas
 *
 */
@Entity
@Table(name = "USER_ADDRESS")
@NoArgsConstructor
public class UserAddress extends AbstractEntityId {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 7448332070844322658L;

    /**
     * The street.
     */
    @Getter
    @Setter
    @Size(max = IntegerUtil.INT_255)
    @Column(name = "STREET", length = IntegerUtil.INT_255, nullable = false)
    @NotNull
    private String street;

    /**
     * The user.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    @Getter
    @NotNull
    private User user;

    /**
     * The city.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CITY_ID", nullable = false)
    @Getter
    @Setter
    @NotNull
    private City city;

    /**
     * Default constructor.
     * 
     * @param user
     *            the user of the address.
     */
    public UserAddress(final User user) {
        this();
        if (user != null) {
            this.user = user;
            user.getAddresses().add(this);
        }
    }

}
