package org.jbromo.webapp.jsf.sample.view.select.common;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jbromo.common.IntegerUtil;
import org.jbromo.common.RandomUtil;

/**
 * Define SelectMenuOption utility.
 * 
 * @author qjafcunuas
 * 
 */
public final class SelectMenuOptionUtil {

    /**
     * Default constructor.
     */
    private SelectMenuOptionUtil() {
        super();
    }

    /**
     * Buil a collection of SelectMenuOption.
     * 
     * @param size
     *            the size of the collection to return.
     * @return the collection.
     */
    public static Collection<SelectMenuOption> build(final int size) {
        final Set<SelectMenuOption> col = new HashSet<SelectMenuOption>();
        SelectMenuOption option;
        Integer key;
        String label;
        while (col.size() < size) {
            key = RandomUtil.nextInt(false, IntegerUtil.INT_100 * size);
            label = "" + key;
            while (label.length() < IntegerUtil.INT_4) {
                label = "0" + label;
            }
            option = new SelectMenuOption(key, "Label " + label, "Desc "
                    + RandomUtil.nextInt(false, IntegerUtil.INT_100 * size));
            col.add(option);
        }
        return col;
    }
}
