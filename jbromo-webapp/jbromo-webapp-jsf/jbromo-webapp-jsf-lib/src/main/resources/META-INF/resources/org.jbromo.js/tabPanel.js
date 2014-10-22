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
if (!bromo) {
	var bromo = {};
}

if (!bromo.tabPanel) {
	bromo.tabPanel = {
		init : function(tabPanel) {
			if (!tabPanel.bromoInitialized) {
				// Set tabPanel as bromo initialized.
				tabPanel.bromoInitialized = true;
			}
		}
	}
}

if (!bromo.dynamicTabPanel) {
	bromo.dynamicTabPanel = {
		init : function(richPanel, tabWidth) {
			var tabPanel = document.getElementById(richPanel.id);
			if (!tabPanel.bromoInitialized) {
				// Initialize panel.
				bromo.tabPanel.init(tabPanel);
				tabPanel.tabWidth = tabWidth;

				// on bromo before item change
				tabPanel.onbromobeforeitemchange = function(event) {
					// Set active item on input text
					var id = this.id.replace("tp", "it");
					document.getElementById(id).value = event.rf.data.newItem.id;
					// ajax switch ?
					var id = this.id.replace("tp", "cba");
					var btn = document.getElementById(id);
					if (btn != null) {
						btn.click();
						return false;
					}
					// server switch ?
					id = this.id.replace("tp", "cbs");
					btn = document.getElementById(id);
					if (btn != null) {
						btn.click();
						return false;
					}
					// client switch
					if (event.rf.data.newItem.id.indexOf("firstTab") > 0) {
						this.firstTabListener(event);
						return false;
					} else if (event.rf.data.newItem.id.indexOf("lastTab") > 0) {
						this.lastTabListener(event);
						return false;
					} else {
						return true;
					}
				}

				// Return the items.
				tabPanel.getItems = function() {
					return this.rf.component.getItems();
				}

				// Return an item.
				tabPanel.getItem = function(index) {
					return this.getItems()[index];
				}

				// Return the active item index.
				tabPanel.getActiveIndex = function() {
					var active = this.rf.component.getSelectItem();
					var items = this.getItems();
					for ( var i = 0; i < items.length; i++) {
						if (active == items[i].getName()) {
							return i;
						}
					}
					return 1;
				}

				// Return the label of an item.
				tabPanel.getLabel = function(index) {
					var id = this.getItem(index).getName() + ":header:inactive";
					var obj = document.getElementById(id);
					return obj.children[0].innerHTML;
				}

				// Get first item index to be rendered.
				tabPanel.getItemFirstIndex = function(index) {
					var items = this.getItems();
					var nbLetter = 0;
					var firstPageIndex = 1;
					for ( var i = 1; i < items.length - 1; i++) {
						var label = this.getLabel(i);
						nbLetter += label.length + 4;
						if (nbLetter > this.tabWidth) {
							if (i > index) {
								return firstPageIndex;
							} else {
								nbLetter = label.length + 4;
								firstPageIndex = i;
							}
						}
					}
					return firstPageIndex;
				}

				// Get last item index to be rendered.
				tabPanel.getItemLastIndex = function(index) {
					var items = this.getItems();
					var firstPageIndex = this.getItemFirstIndex(index);
					var nbLetter = 0;
					for ( var i = firstPageIndex; i < items.length - 1; i++) {
						var label = this.getLabel(i);
						nbLetter += label.length + 4;
						if (nbLetter > this.tabWidth) {
							return i - 1;
						}
					}
					return items.length - 2;
				}

				// Set active index item.
				tabPanel.setActiveIndex = function(index) {
					this.rf.component.switchToItem(this.getItem(index)
							.getName());
				}

				// Call when user clicks on first tab.
				tabPanel.firstTabListener = function(event) {
					var active = this.getActiveIndex();
					var first = this.getItemFirstIndex(active) - 1;
					active = this.getItemFirstIndex(first);
					this.setActiveIndex(active);
					this.renderItems();
				}

				// Call when user clicks on last tab.
				tabPanel.lastTabListener = function(event) {
					var active = this.getActiveIndex();
					var last = this.getItemLastIndex(active) + 1;
					active = this.getItemFirstIndex(last);
					this.setActiveIndex(active);
					this.renderItems();
				}

				// Render items.
				tabPanel.renderItems = function() {
					var active = this.getActiveIndex();
					var first = this.getItemFirstIndex(active);
					var last = this.getItemLastIndex(first);
					var items = this.getItems();
					// Items
					for ( var i = 1; i < items.length - 1; i++) {
						// inactive tab.
						var id = items[i].getName() + ":header:inactive";
						bromo.display(id, i >= first && i <= last && i != active);
						// active tab.
						id = items[i].getName() + ":header:active";
						bromo.display(id, i >= first && i <= last && i == active);
					}
					// firstTab
					id = items[0].getName() + ":header:inactive";
					bromo.display(id, first != 1);
					// lastTab
					id = items[items.length - 1].getName() + ":header:inactive";
					bromo.display(id, last != items.length - 2);
					// tab separator.
					var obj = document.getElementById(id).parentNode;
					for (var i = 1; i < obj.children.length - 1; i += 4) {
						// inactive tab.
						var displayed = bromo.isObjectDisplayed(obj.children[i]);
						// active tab.
						displayed = displayed || bromo.isObjectDisplayed(obj.children[i+1]);
						// separator tab.
						bromo.displayObject(obj.children[i+3], displayed);
					}
				}

				// Render items for client switch mode.
				if (tabPanel.getItem(0).switchMode == "client") {
					if (tabPanel.getActiveIndex() == 0) {
						tabPanel.setActiveIndex(1);
					}
					tabPanel.renderItems();
				}
			}
		}
	}
}
