/*-
 * Copyright (C) 2013-2014 The JBromo Authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitatiish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *on the rights
 * to use, copy, modify, merge, publ The above copyright notice and this permission notice shall be included in
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
package org.jbromo.webapp.jsf.sample.component.menu;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import lombok.AccessLevel;
import lombok.Getter;

import org.jbromo.webapp.jsf.component.menu.AbstractMenuController;
import org.jbromo.webapp.jsf.component.menu.AbstractMenuModel;
import org.jbromo.webapp.jsf.sample.view.View;

/**
 * Define the menu controller.
 * @author qjafcunuas
 */
@Named
@RequestScoped
public class MenuController extends AbstractMenuController<AbstractMenuModel> {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 1305937245618610409L;

    /**
     * The faces context.
     */
    @Inject
    @Getter(AccessLevel.PRIVATE)
    private FacesContext facesContext;

    /**
     * The component model.
     */
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private MenuModel model;

    /**
     * Called when user select countries menu.
     * @return the corresponding view's id.
     */
    public String onCountries() {
        return fireEventRenderView(View.COUNTRIES).getId();
    }

    /**
     * Called when user select countries menu.
     * @return the corresponding view's id.
     */
    public String onIndex() {
        return View.INDEX.getId();
    }

    /**
     * Called when user select languages menu.
     * @return the corresponding view's id.
     */
    public String onLanguages() {
        return fireEventRenderView(View.LANGUAGES).getId();
    }

    /**
     * Called when user select messages menu.
     * @return the corresponding view's id.
     */
    public String onMessageLabels() {
        return fireEventRenderView(View.MESSAGE_LABEL).getId();
    }

    /**
     * Called when user select selectOneMenu menu.
     * @return the corresponding view's id.
     */
    public String onSelectOneMenu() {
        return fireEventRenderView(View.SELECT_ONE_MENU).getId();
    }

    /**
     * Called when user select selectYesNoMenu menu.
     * @return the corresponding view's id.
     */
    public String onSelectBooleanMenu() {
        return fireEventRenderView(View.SELECT_BOOLEAN_MENU).getId();
    }

    /**
     * Called when user select selectYesNoMenu menu.
     * @return the corresponding view's id.
     */
    public String onSelectManyCheckbox() {
        return fireEventRenderView(View.SELECT_MANY_CHECKBOX).getId();
    }

    /**
     * Called when user select popupWait menu.
     * @return the corresponding view's id.
     */
    public String onPopupWait() {
        return fireEventRenderView(View.POPUP_WAIT).getId();
    }

    /**
     * Called when user select popupMessage menu.
     * @return the corresponding view's id.
     */
    public String onPopupMessage() {
        return fireEventRenderView(View.POPUP_MESSAGE).getId();
    }

    /**
     * Called when user select inputText menu.
     * @return the corresponding view's id.
     */
    public String onInputText() {
        return fireEventRenderView(View.INPUT_TEXT).getId();
    }

    /**
     * Called when user select fieldSet menu.
     * @return the corresponding view's id.
     */
    public String onFieldSet() {
        return fireEventRenderView(View.FIELDSET).getId();
    }

    /**
     * Called when user select datatable menu.
     * @return the corresponding view's id.
     */
    public String onColumnHeaderDataTable() {
        return fireEventRenderView(View.HEADER_DATATABLE).getId();
    }

    /**
     * Called when user select datatable menu.
     * @return the corresponding view's id.
     */
    public String onCrudDataTable() {
        return fireEventRenderView(View.CRUD_DATATABLE).getId();
    }

    /**
     * Called when user select datatable menu.
     * @return the corresponding view's id.
     */
    public String onRowClickDataTable() {
        return fireEventRenderView(View.ROWCLICK_DATATABLE).getId();
    }

    /**
     * Called when user select datatable menu.
     * @return the corresponding view's id.
     */
    public String onSelectAllDataTable() {
        return fireEventRenderView(View.SELECTALL_DATATABLE).getId();
    }

    /**
     * Called when user select datatable menu.
     * @return the corresponding view's id.
     */
    public String onDataScroller() {
        return fireEventRenderView(View.DATASCROLLER).getId();
    }

    /**
     * Called when user select inputTextarea menu.
     * @return the corresponding view's id.
     */
    public String onInputTextarea() {
        return fireEventRenderView(View.INPUT_TEXTAREA).getId();
    }

    /**
     * Called when user select dynamicTabPanel menu.
     * @return the corresponding view's id.
     */
    public String onDynamicTabPanel() {
        return fireEventRenderView(View.DYNAMIC_TAB_PANEL).getId();
    }

    /**
     * Called when user select togglePanel menu.
     * @return the corresponding view's id.
     */
    public String onTogglePanel() {
        return fireEventRenderView(View.TOGGLE_PANEL).getId();
    }

    /**
     * Called when user select wizard menu.
     * @return the corresponding view's id.
     */
    public String onWizard() {
        return fireEventRenderView(View.WIZARD).getId();
    }

    /**
     * Called when user select commandButton menu.
     * @return the corresponding view's id.
     */
    public String onCommandButton() {
        return fireEventRenderView(View.COMMAND_BUTTON).getId();
    }

    /**
     * Called when user select login menu.
     * @return the corresponding view's id.
     */
    public String onLogin() {
        return fireEventRenderView(View.LOGIN).getId();
    }

    /**
     * Called when user select logout menu.
     * @return the corresponding view's id.
     * @throws ServletException exception.
     */
    public String onLogout() throws ServletException {
        final HttpServletRequest request = (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
        request.logout();
        return fireEventRenderView(View.LOGIN).getId();
    }

    /**
     * Called when user select admin access menu.
     * @return the corresponding view's id.
     */
    public String onAdminAccess() {
        return fireEventRenderView(View.ADMIN_ACCESS).getId();
    }

    /**
     * Called when user select guess access menu.
     * @return the corresponding view's id.
     */
    public String onGuestAccess() {
        return fireEventRenderView(View.GUEST_ACCESS).getId();
    }

}
