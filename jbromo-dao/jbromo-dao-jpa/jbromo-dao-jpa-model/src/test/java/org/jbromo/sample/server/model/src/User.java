package org.jbromo.sample.server.model.src;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.StringUtil;
import org.jbromo.model.jpa.AbstractEntityId;
import org.jbromo.sample.server.model.src.common.UserCalendar;

/**
 * Define the user.
 *
 * @author qjafcunuas
 *
 */
@Entity
@Table(name = "USER")
@NamedQueries(value = {
        @NamedQuery(name = User.NAMED_QUERY_FIND_ALL, query = "select o from User o"),
        @NamedQuery(name = User.NAMED_QUERY_FIND_BY_LOGIN, query = "select o from User o where o.login = ?1") })
public class User extends AbstractEntityId {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -1438104360938143438L;

    /**
     * The query named.
     */
    public static final String NAMED_QUERY_FIND_BY_LOGIN = "User.findByLogin";

    /**
     * The query named.
     */
    public static final String NAMED_QUERY_FIND_ALL = "User.findAll";

    /**
     * The login.
     */
    @Getter
    @Setter
    @Size(max = IntegerUtil.INT_32)
    @Column(name = "LOGIN", length = IntegerUtil.INT_32, nullable = false, unique = true)
    @NotNull
    private String login;

    /**
     * The encrypted password.
     */
    @Getter
    @Setter
    @Size(max = StringUtil.PASSWORD_SIZE)
    @Column(name = "PASSWORD", length = StringUtil.PASSWORD_SIZE, nullable = false)
    @NotNull
    private String encryptedPassword;

    /**
     * The first name.
     */
    @Getter
    @Setter
    @Size(max = IntegerUtil.INT_32)
    @Column(name = "FIRSTNAME", length = IntegerUtil.INT_32, nullable = false)
    @NotNull
    private String firstname;

    /**
     * The last name.
     */
    @Getter
    @Setter
    @Size(max = IntegerUtil.INT_32)
    @Column(name = "LASTNAME", length = IntegerUtil.INT_32, nullable = false)
    @NotNull
    private String lastname;

    /**
     * Define if user is active.
     */
    @Getter
    @Setter
    @Column(name = "ACTIVE", nullable = false)
    @NotNull
    private Boolean active;

    /**
     * An embeddable object.
     */
    @Embedded
    @Valid
    private UserCalendar calendar;

    /**
     * A ManyToOne user group.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MANY_TO_ONE_GROUP_ID", nullable = false)
    @Getter
    @Setter
    @NotNull
    private UserGroup manyToOneGroup;

    /**
     * A OneToOne user group.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ONE_TO_ONE_GROUP_ID", nullable = false)
    @Getter
    @Setter
    @NotNull
    private UserGroup oneToOneGroup;

    /**
     * The ManyToMany user roles.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USER_TO_USER_GROUP", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "USER_GROUP_ID"))
    private Set<UserGroup> manyToManyGroups;

    /**
     * The addresses.
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
    private Set<UserAddress> addresses;

    /**
     * The surnames.
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
    private Set<UserSurname> surnames;

    /**
     * Return true if user is active.
     *
     * @return true/false
     */
    public boolean isActive() {
        return Boolean.TRUE.equals(getActive());
    }

    /**
     * Return the groups.
     *
     * @return the groups.
     */
    public Set<UserGroup> getManyToManyGroups() {
        if (this.manyToManyGroups == null) {
            this.manyToManyGroups = new HashSet<UserGroup>();
        }
        return this.manyToManyGroups;
    }

    /**
     * Return the addresses.
     *
     * @return the addresses.
     */
    public Set<UserAddress> getAddresses() {
        if (this.addresses == null) {
            this.addresses = new HashSet<UserAddress>();
        }
        return this.addresses;
    }

    /**
     * Return the surnames.
     *
     * @return the surnames.
     */
    public Set<UserSurname> getSurnames() {
        if (this.surnames == null) {
            this.surnames = new HashSet<UserSurname>();
        }
        return this.surnames;
    }

    /**
     * Return the user calendar.
     *
     * @return the calendar.
     */
    public UserCalendar getCalendar() {
        if (this.calendar == null) {
            this.calendar = new UserCalendar();
        }
        return this.calendar;
    }
}
