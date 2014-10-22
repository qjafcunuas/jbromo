package org.jbromo.sample.server.model.src.common;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * An embeddable object.
 *
 * @author qjafcunuas
 *
 */
@Embeddable
@EqualsAndHashCode
@ToString
public class UserCalendar implements Serializable {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 2062684448256712697L;
    /**
     * The creation date.
     */
    @Getter
    @Setter
    @Column(name = "CREATION_DATE")
    private Calendar creationDate;
    /**
     * The creation date.
     */
    @Getter
    @Setter
    @Column(name = "UPDATE_DATE")
    private Calendar updateDate;
}
