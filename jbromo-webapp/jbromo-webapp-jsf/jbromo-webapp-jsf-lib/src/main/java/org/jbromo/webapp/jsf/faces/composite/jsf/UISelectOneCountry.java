/*-
 * Copyright (C) 2013-2014 The JBromo Authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jbromo.webapp.jsf.faces.composite.jsf;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;

import org.jbromo.common.locale.LocaleCountryUtil;
import org.jbromo.webapp.jsf.cdi.CDIFacesUtil;
import org.jbromo.webapp.jsf.faces.composite.util.AbstractUIOutputSelectOne;
import org.jbromo.webapp.jsf.locale.LocaleContext;

/**
 * Define UISelectOneCountry composite.
 * @author qjafcunuas
 */
@FacesComponent(value = "org.jbromo.webapp.jsf.faces.composite.jsf.UISelectOneCountry")
public class UISelectOneCountry extends AbstractUIOutputSelectOne {

    /**
     * Return the locale context.
     * @return the locale context.
     */
    protected LocaleContext getLocaleContext() {
        return CDIFacesUtil.getCDIObject(LocaleContext.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected List<Locale> getValues() {
        final LocaleContext localeContext = getLocaleContext();
        final List<Locale> values = super.getValues();
        if (values == null) {
            return localeContext.getCountries();
        }
        LocaleCountryUtil.sort(values, localeContext.getLocale());
        return values;
    }

    @Override
    protected void encodeOutputText(final FacesContext context) throws IOException {
        // Nothing to do.
    }

}
