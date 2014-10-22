package org.jbromo.sample.server.model.src;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jbromo.common.IntegerUtil;
import org.jbromo.model.jpa.AbstractEntityId;

import lombok.Getter;
import lombok.Setter;

/**
 * Define the user group.
 *
 * @author qjafcunuas
 *
 */
@Entity
@Table(name = "USER_GROUP")
public class UserGroup extends AbstractEntityId {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -1438104360938143469L;

    /**
     * The name.
     */
    @Getter
    @Setter
    @Size(max = IntegerUtil.INT_32)
    @Column(name = "NAME", length = IntegerUtil.INT_32, nullable = false, unique = true)
    @NotNull
    private String name;

}
