/*-
 * Copyright (C) 2013-2014 The JBromo Authors.
 *
 * Permission is hereby grantthis software and associated documentation files (the "Software"), to deal
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
package org.jbromo.webapp.jsf.sample.view;

import java.lang.annotation.Annotation;

import org.jbromo.webapp.jsf.mvc.view.AbstractViewController;
import org.jbromo.webapp.jsf.sample.view.button.CommandButtonController;
import org.jbromo.webapp.jsf.sample.view.country.CountryController;
import org.jbromo.webapp.jsf.sample.view.input.InputTextController;
import org.jbromo.webapp.jsf.sample.view.input.InputTextareaController;
import org.jbromo.webapp.jsf.sample.view.language.LanguageController;
import org.jbromo.webapp.jsf.sample.view.layer.dynamic.DynamicTabPanelController;
import org.jbromo.webapp.jsf.sample.view.layer.scroller.DataScrollerController;
import org.jbromo.webapp.jsf.sample.view.layer.table.columnheader.ColumnHeaderDataTableController;
import org.jbromo.webapp.jsf.sample.view.layer.table.crud.CrudDataTableController;
import org.jbromo.webapp.jsf.sample.view.layer.table.rowclick.RowClickDataTableController;
import org.jbromo.webapp.jsf.sample.view.layer.table.selectall.SelectAllDataTableController;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage.TogglePanelControllerView;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage.togglepanel.TogglePanelController;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage.togglepanel.first.FirstTogglePanelItemController;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage.togglepanel.second.SecondTogglePanelItemController;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.monopage.togglepanel.third.ThirdTogglePanelItemController;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.WizardController;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.first.FirstWizardItemController;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.second.SecondWizardItemController;
import org.jbromo.webapp.jsf.sample.view.layer.wizard.multipages.item.third.ThirdWizardItemController;
import org.jbromo.webapp.jsf.sample.view.login.LoginController;
import org.jbromo.webapp.jsf.sample.view.message.MessageLabelController;
import org.jbromo.webapp.jsf.sample.view.select.booleanmenu.SelectBooleanMenuController;
import org.jbromo.webapp.jsf.sample.view.select.manycheckbox.SelectManyCheckboxController;
import org.jbromo.webapp.jsf.sample.view.select.onemenu.SelectOneMenuController;
import org.jbromo.webapp.jsf.view.IView;
import org.jbromo.webapp.jsf.view.RenderView;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Define views.
 * @author qjafcunuas
 */
@AllArgsConstructor
@Getter
public enum View implements IView {

    /** The index view. */
    INDEX("/index.xhtml", null),

    /** The login view. */
    LOGIN("/security/login.xhtml", LoginController.class),

    /** The admin access view. */
    ADMIN_ACCESS("/security/admin.xhtml", null),

    /** The guess access view. */
    GUEST_ACCESS("/security/guest.xhtml", null),

    /** The command button view. */
    COMMAND_BUTTON("/button/commandButtonView.xhtml", CommandButtonController.class),

    /** The countries view. */
    COUNTRIES("/country/country.xhtml", CountryController.class),

    /** The dynamicTabPanel view. */
    DYNAMIC_TAB_PANEL("/layer/dynamicTabPanelView.xhtml", DynamicTabPanelController.class),

    /** The togglePanel view. */
    TOGGLE_PANEL("/layer/togglePanelView.xhtml", TogglePanelControllerView.class),

    /** The togglePanel view. Notes viewId is set to view */
    TOGGLE_PANEL_WIZARD(TOGGLE_PANEL.getId(), TogglePanelController.class),

    /** The togglePanel first item view. */
    TOGGEL_PANEL_FIRST_ITEM("firstItem", FirstTogglePanelItemController.class),

    /** The togglePanel second item view. */
    TOGGEL_PANEL_SECOND_ITEM("secondItem", SecondTogglePanelItemController.class),

    /** The togglePanel third item view. */
    TOGGLE_PANEL_THIRD_ITEM("thirdItem", ThirdTogglePanelItemController.class),

    /** The wizard view. Notes null id. */
    WIZARD(null, WizardController.class),

    /** The wizard first item view. */
    WIZARD_FIRST_ITEM("/layer/wizardFirstItemView.xhtml", FirstWizardItemController.class),

    /** The wizard second item view. */
    WIZARD_SECOND_ITEM("/layer/wizardSecondItemView.xhtml", SecondWizardItemController.class),

    /** The wizard first item view. */
    WIZARD_THIRD_ITEM("/layer/wizardThirdItemView.xhtml", ThirdWizardItemController.class),

    /** The dataScroller view. */
    DATASCROLLER("/layer/dataScrollerView.xhtml", DataScrollerController.class),

    /** The datatable view. */
    ROWCLICK_DATATABLE("/layer/rowClickDataTableView.xhtml", RowClickDataTableController.class),

    /** The datatable view. */
    SELECTALL_DATATABLE("/layer/selectAllDataTableView.xhtml", SelectAllDataTableController.class),

    /** The datatable view. */
    CRUD_DATATABLE("/layer/crudDataTableView.xhtml", CrudDataTableController.class),

    /** The datatable view. */
    HEADER_DATATABLE("/layer/headerDataTableView.xhtml", ColumnHeaderDataTableController.class),

    /** The fieldset view. */
    FIELDSET("/layer/fieldSetView.xhtml", null),

    /** The input text view. */
    INPUT_TEXT("/input/inputTextView.xhtml", InputTextController.class),

    /** The input text view. */
    INPUT_TEXTAREA("/input/inputTextareaView.xhtml", InputTextareaController.class),

    /** The countries view. */
    LANGUAGES("/language/language.xhtml", LanguageController.class),

    /** The messageLabel view. */
    MESSAGE_LABEL("/message/messageLabelView.xhtml", MessageLabelController.class),

    /** The popupWait view. */
    POPUP_WAIT("/popup/popupWaitView.xhtml", null),

    /** The popupMessage view. */
    POPUP_MESSAGE("/popup/popupMessageView.xhtml", null),

    /** The selectOneMenu view. */
    SELECT_ONE_MENU("/select/selectOneMenuView.xhtml", SelectOneMenuController.class),

    /** The selectBooleanMenu view. */
    SELECT_BOOLEAN_MENU("/select/selectBooleanMenuView.xhtml", SelectBooleanMenuController.class),

    /** The selectManyCheckbox view. */
    SELECT_MANY_CHECKBOX("/select/selectManyCheckboxView.xhtml", SelectManyCheckboxController.class);

    /**
     * The view's id.
     */
    private final String id;

    /**
     * The view's controller.
     */
    private final Class<? extends AbstractViewController<?>> controller;

    /**
     * Define the render view annotation for this view.
     */
    private final RenderView renderViewAnnotation = new RenderView() {

        @Override
        public Class<? extends Annotation> annotationType() {
            return RenderView.class;
        }

        @Override
        public Class<? extends AbstractViewController<?>> controller() {
            return getOuterThis().controller;
        }
    };

    /**
     * Return this object.
     * @return this.
     */
    private View getOuterThis() {
        return this;
    }

}
