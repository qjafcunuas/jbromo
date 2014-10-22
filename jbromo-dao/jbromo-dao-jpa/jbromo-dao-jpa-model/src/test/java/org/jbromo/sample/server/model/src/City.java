package org.jbromo.sample.server.model.src;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.jbromo.common.IntegerUtil;
import org.jbromo.model.jpa.AbstractEntityId;

/**
 * Define the City entity.
 * 
 * @author qjafcunuas
 * 
 */
@Entity
@Table(name = "CITY")
@NoArgsConstructor
public class City extends AbstractEntityId {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 7448332070844322658L;

    /**
     * The name.
     */
    @Getter
    @Setter
    @Size(max = IntegerUtil.INT_255)
    @Column(name = "NAME", length = IntegerUtil.INT_255, nullable = false)
    @NotNull
    private String name;

    /**
     * The postcode.
     */
    @Getter
    @Setter
    @Size(max = IntegerUtil.INT_6)
    @Column(name = "POSTCODE", length = IntegerUtil.INT_6, nullable = false)
    @NotNull
    private String postcode;

}
