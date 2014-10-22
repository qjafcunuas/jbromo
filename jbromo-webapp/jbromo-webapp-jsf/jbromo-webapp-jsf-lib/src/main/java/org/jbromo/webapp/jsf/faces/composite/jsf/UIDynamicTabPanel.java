/*
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
import java.util.ArrayList;
import java.util.List;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import lombok.Getter;
import lombok.Setter;

import org.jbromo.webapp.jsf.faces.composite.util.UINamingContainerApp;
import org.richfaces.component.AbstractTogglePanelItemInterface;
import org.richfaces.component.SwitchType;
import org.richfaces.component.UITabPanel;

/**
 * Define UIDynamicTabPanel composite.
 *
 * @author qjafcunuas
 *
 */
@FacesComponent(value = "org.jbromo.webapp.jsf.faces.composite.jsf.UIDynamicTabPanel")
public class UIDynamicTabPanel extends UINamingContainerApp {

    /**
     * Header string constant.
     */
    private static final String HEADER = "header";

    /**
     * Define space between two tabs.
     */
    private static final int SPACE_BETWEEN_TABS = 4;

    /**
     * For binding the tab panel.
     */
    @Getter
    @Setter
    private UITabPanel tabPanel;

    /**
     * For binding the input field value to get the next tab id.
     */
    @Getter
    @Setter
    private String nextTabId;

    @Override
    public void encodeBegin(final FacesContext context) throws IOException {
        renderItems();
        super.encodeBegin(context);
    }

    /**
     * Return the tabWidth attribute.
     *
     * @return the value.
     */
    protected Integer getTabWidth() {
        return getAttribute("tabWidth");
    }

    /**
     * Return all tab panel elements, even they are not rendered.
     *
     * @return all tab elements.
     */
    private List<AbstractTogglePanelItemInterface> getItems() {
        final List<AbstractTogglePanelItemInterface> res = new ArrayList<AbstractTogglePanelItemInterface>(
                getTabPanel().getChildCount());
        for (final UIComponent child : getTabPanel().getChildren()) {
            if (child instanceof AbstractTogglePanelItemInterface) {
                res.add((AbstractTogglePanelItemInterface) child);
            }
        }
        return res;
    }

    /**
     * Return a tab as item.
     *
     * @param index
     *            the tab index.
     * @return the panel.
     */
    private AbstractTogglePanelItemInterface getItem(final int index) {
        return getItems().get(index);
    }

    /**
     * Return the index of the tab name.
     *
     * @param name
     *            the tab name.
     * @return the index.
     */
    private int getIndex(final String name) {
        final List<AbstractTogglePanelItemInterface> items = getItems();
        for (int index = 0; index < items.size(); index++) {
            if (items.get(index).getName().equals(name)) {
                return index;
            }
        }
        return 0;
    }

    /**
     * Return the active item index.
     *
     * @return the active index.
     */
    private int getActiveIndex() {
        return getIndex(getTabPanel().getActiveItem());
    }

    /**
     * Set the active item index.
     *
     * @param index
     *            the active index to set.
     */
    private void setActiveIndex(final int index) {
        getTabPanel().setActiveItem(getItem(index).getName());
    }

    /**
     * Render tabs.
     *
     */
    private void renderItems() {
        initRendered();
        final SwitchType type = SwitchType.valueOf(getAttribute("switchType")
                .toString());
        if (SwitchType.client.equals(type)) {
            return;
        }
        final int active = getActiveIndex();
        final int first = getItemFirstIndex(active);
        final int last = getItemLastIndex(first);
        renderItems(first, last);
    }

    /**
     * Initialize rendered attribute on tabs, according to expression language
     * from jsf page.
     *
     * @return the first rendered panel index.
     */
    private int initRendered() {
        final ELContext elContext = getFacesContext().getELContext();
        ValueExpression ve;
        int firstRendered = 0;
        UIComponent component = null;
        final List<UIComponent> items = getTabPanel().getChildren();
        for (int i = 0; i < items.size(); i++) {
            component = items.get(i);
            ve = component.getValueExpression("rendered");
            if (ve != null) {
                component.setRendered((Boolean) ve.getValue(elContext));
            } else {
                component.setRendered(true);
            }
            if (firstRendered == 0 && component.isRendered()) {
                firstRendered = i;
            }
        }
        return firstRendered;
    }

    /**
     * Return the first index of the tabs to be rendered.
     *
     * @param index
     *            the index of the displayed tab.
     * @return the first index of the tabs.
     */
    private int getItemFirstIndex(final int index) {
        final List<UIComponent> items = getTabPanel().getChildren();
        UIComponent item;
        if (index == 1) {
            // return the first rendered tab.
            for (int i = 1; i < items.size() - 1; i++) {
                item = items.get(i);
                if (item.isRendered()) {
                    return i;
                }
            }
            return 1;
        }
        int firstPageIndex = -1;
        int nbLetter = 0;
        final int tabWidth = getTabWidth();
        for (int i = 1; i < items.size() - 1; i++) {
            item = items.get(i);
            if (item.isRendered()) {
                if (firstPageIndex == -1) {
                    firstPageIndex = i;
                }
                final String string = (String) item.getAttributes().get(HEADER);
                nbLetter += string.length() + SPACE_BETWEEN_TABS;
                if (nbLetter > tabWidth) {
                    if (i > index) {
                        return firstPageIndex;
                    } else {
                        nbLetter = string.length() + SPACE_BETWEEN_TABS;
                        firstPageIndex = i;
                    }
                }
            }
        }
        return firstPageIndex;
    }

    /**
     * Return the last index of the tabs to be rendered.
     *
     * @param index
     *            the index of the displayed tab.
     * @return the last index of the tabs.
     */
    private int getItemLastIndex(final int index) {
        final List<UIComponent> items = getTabPanel().getChildren();
        final int firstPageIndex = getItemFirstIndex(index);
        int nbLetter = 0;
        final int tabWidth = getTabWidth();
        UIComponent item;
        for (int i = firstPageIndex; i < items.size() - 1; i++) {
            item = items.get(i);
            if (item.isRendered()) {
                final String string = (String) item.getAttributes().get(HEADER);
                nbLetter += string.length() + SPACE_BETWEEN_TABS;
                if (nbLetter > tabWidth) {
                    return i - 1;
                }
            }
        }
        return items.size() - 2;
    }

    /**
     * Items from first index to last index will be rendered, and render next
     * and previous tab if necessary.
     *
     * @param first
     *            the first index.
     * @param last
     *            the last index.
     */
    private void renderItems(final int first, final int last) {
        // Tab list.
        final List<UIComponent> items = getTabPanel().getChildren();
        // Render panels.
        int firstRenderableIndex = -1;
        int firstRenderedIndex = -1;
        for (int index = 1; index < items.size() - 1; index++) {
            if (items.get(index).isRendered()) {
                if (firstRenderableIndex == -1) {
                    // first index with can be rendered, without first and last
                    // conditions.
                    firstRenderableIndex = index;
                }
                if (firstRenderedIndex == -1 && index >= first && index <= last) {
                    // first index with can be rendered, witch index >= first.
                    firstRenderedIndex = index;
                }
                items.get(index).setRendered(index >= first && index <= last);
            }
        }
        // Set active panel.
        final int active = getActiveIndex();
        if (active < firstRenderedIndex || active > last) {
            setActiveIndex(firstRenderedIndex);
        }
        // Update First/last tab rendered.
        items.get(0).setRendered(first > firstRenderableIndex);
        items.get(items.size() - 1).setRendered(last < items.size() - 2);
    }

    /**
     * Call when user clicks on first item.
     *
     */
    public void firstItemListener() {
        initRendered();
        int active = getActiveIndex();
        final int first = getItemFirstIndex(active) - 1;
        active = getItemFirstIndex(first);
        setActiveIndex(active);
        renderItems();
        // run actionListener on active item.
        active = getActiveIndex();
        actionListener(active);
    }

    /**
     * Call when user clicks on last item.
     *
     */
    public void lastItemListener() {
        initRendered();
        int active = getActiveIndex();
        final int last = getItemLastIndex(active) + 1;
        active = getItemFirstIndex(last);
        setActiveIndex(active);
        renderItems();
        // run actionListener on active item.
        active = getActiveIndex();
        actionListener(active);
    }

    /**
     * Return a tab as a component.
     *
     * @param index
     *            the tab index.
     * @return the panel.
     */
    private UIComponent getItemComponent(final int index) {
        return getTabPanel().getChildren().get(index);
    }

    /**
     * Call when user clicks on last item.
     *
     */
    public void itemChangeListener() {
        int active = getIndex(getNextTabId());
        if (active == 0) {
            actionListener(active);
        } else if (active == getItems().size() - 1) {
            actionListener(active);
        } else {
            setActiveIndex(active);
            // run action on active item.
            active = getActiveIndex();
            actionListener(active);
        }
    }

    /**
     * Execute action listeners on a tab.
     *
     * @param index
     *            the index of the tab to execute action listeners.
     */
    private void actionListener(final int index) {
        final UIComponent component = getItemComponent(index);
        final ActionListener[] actions = (ActionListener[]) component
                .getAttributes().get("actionListeners");
        if (actions != null && actions.length > 0) {
            final ActionEvent event = new ActionEvent(component);
            for (final ActionListener action : actions) {
                action.processAction(event);
            }
        }
    }
}
