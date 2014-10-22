package org.jbromo.sample.server.model.src;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.jbromo.common.IntegerUtil;
import org.jbromo.model.jpa.AbstractEntity;
import org.jbromo.sample.server.model.src.compositepk.UserSurnamePk;

/**
 * A user surname.
 *
 * @author qjafcunuas
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "USER_SURNAME")
public class UserSurname extends AbstractEntity<UserSurnamePk> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 5097407877472139154L;
    /**
     * The primary key.
     */
    @EmbeddedId
    private UserSurnamePk primaryKey = new UserSurnamePk(this);

    /**
     * The city.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("userId")
    @Getter
    @Setter(AccessLevel.NONE)
    @NotNull
    private User user;

    /**
     * Another pk, not functionnaly sense, only for having composite pk example.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("cityId")
    @Getter
    @Setter(AccessLevel.NONE)
    @NotNull
    private City city;

    /**
     * The surname.
     */
    @Getter
    @Setter
    @Size(max = IntegerUtil.INT_255)
    @Column(name = "SURNAME", length = IntegerUtil.INT_255, nullable = false)
    @NotNull
    private String surname;

    /**
     * Default constructor.
     *
     * @param user
     *            the user.
     * @param city
     *            the city.
     */
    public UserSurname(final User user, final City city) {
        super();
        this.city = city;
        this.user = user;
        this.primaryKey = new UserSurnamePk(this);
        if (user != null) {
            user.getSurnames().add(this);
        }
    }

    @Override
    public UserSurnamePk getPrimaryKey() {
        if (this.primaryKey == null) {
            this.primaryKey = new UserSurnamePk(this);
        }
        return this.primaryKey;
    }

}
