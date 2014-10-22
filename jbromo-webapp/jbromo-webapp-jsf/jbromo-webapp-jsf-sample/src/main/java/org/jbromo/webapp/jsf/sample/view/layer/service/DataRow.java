package org.jbromo.webapp.jsf.sample.view.layer.service;

import java.io.Serializable;

import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.jbromo.common.IntegerUtil;

/**
 * Define a row data.
 * 
 * @author qjafcunuas
 * 
 */
@EqualsAndHashCode(of = { "primaryKey" })
public class DataRow implements Serializable {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -4555118327274556353L;

    /**
     * Define the name size max.
     */
    public static final int NAME_SIZE_MAX = IntegerUtil.INT_50;

    /**
     * Define the description size max.
     */
    public static final int DESCRIPTION_SIZE_MAX = IntegerUtil.INT_50;

    /**
     * The primary key.
     */
    @Getter
    @Setter
    private Integer primaryKey;

    /**
     * The name.
     */
    @Getter
    @Setter
    @Size(max = NAME_SIZE_MAX)
    private String name;

    /**
     * The description.
     */
    @Getter
    @Setter
    @Size(max = DESCRIPTION_SIZE_MAX)
    private String description;

}
