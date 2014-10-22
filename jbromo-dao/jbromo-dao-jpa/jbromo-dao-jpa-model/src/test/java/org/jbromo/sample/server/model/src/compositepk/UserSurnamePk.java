package org.jbromo.sample.server.model.src.compositepk;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.jbromo.model.jpa.compositepk.AbstractCompositePk;
import org.jbromo.sample.server.model.src.UserSurname;

/**
 * A user surname primary key.
 *
 * @author qjafcunuas
 *
 */
@NoArgsConstructor
@ToString
@Embeddable
@Getter
@Setter(AccessLevel.NONE)
public class UserSurnamePk extends AbstractCompositePk {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -1868241073221114671L;

    /**
     * The user id.
     */
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    /**
     * Another pk, not functionnaly sense, only for having composite pk example.
     */
    @Column(name = "CITY_ID", nullable = false)
    private Long cityId;

    /**
     * Default constructor.
     *
     * @param surname
     *            the user surname of the primary key.
     */
    public UserSurnamePk(final UserSurname surname) {
        super(surname);
        if (surname != null) {
            if (surname.getUser() != null) {
                this.userId = surname.getUser().getPrimaryKey();
            }
            if (surname.getCity() != null) {
                this.cityId = surname.getCity().getPrimaryKey();
            }
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }

}
