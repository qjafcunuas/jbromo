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

if (!bromo.popup) {
	bromo.popup = {
		init : function(popup) {
			if (!popup.bromoInitialized) {
				// Set table as bromo initialized.
				popup.bromoInitialized = true;
			}
		}
	};
}

if (!bromo.popupMessage) {
	bromo.popupMessage = {
		init : function(popup, richPopup) {
			if (!popup.bromoInitialized) {
				bromo.popup.init(popup);
			}
			popup.rf = richPopup;
			popup.titleId = null;
			popup.contentId = null;
			popup.displayOk = null;
			popup.labelOk = null;
			popup.callbackOk = null;
			popup.displayCancel = null;
			popup.labelCancel = null;
			popup.callbackCancel = null;
			popup.type = null;

			/**
			 * Use for displaying an informational popup.
			 */
			popup.info = function(contentId) {
				this.show("info", null, contentId, true, false);
			};

			/**
			 * Use for displaying a warning popup.
			 */
			popup.warn = function(contentId) {
				this.show("warn", null, contentId, true, false);
			};

			/**
			 * Use for displaying a waiting popup.
			 */
			popup.wait = function(contentId) {
				this.show("wait", null, contentId, false, false);
			};

			/**
			 * Use for displaying a confirmation popup.
			 */
			popup.confirm = function(contentId) {
				this.show("confirm", null, contentId, true, true);
			};

			/**
			 * Use for displaying a delete popup.
			 */
			popup.remove = function(contentId) {
				this.show("delete", null, contentId, true, true);
			};

			/**
			 * Use for displaying a typed popup.
			 */
			popup.show = function(type, titleId, contentId, displayOk, displayCancel) {
				this.type = type;
				this.contentId = contentId;
				if (titleId) {
					this.titleId = titleId;
				}
				this.displayOk = displayOk;
				this.displayCancel = displayCancel;
				this.display();
			};

			/**
			 * Use for setting the title of the popup.
			 */
			popup.setTitle = function(objId) {
				this.titleId = objId;
				return this;
			};

			/**
			 * Use for setting the label of the Ok button.
			 */
			popup.setLabelOk = function(label) {
				this.labelOk = label;
				return this;
			};

			/**
			 * Use for setting a javascript callback when ok button is clicked.
			 */
			popup.setCallbackOk = function(callback) {
				this.callbackOk = callback;
				return this;
			};

			/**
			 * Use for setting the label of the Cancel button.
			 */
			popup.setLabelCancel = function(label) {
				this.labelCancel = label;
				return this;
			};

			/**
			 * Use for setting a javascript callback when cancel button is
			 * clicked.
			 */
			popup.setCallbackCancel = function(callback) {
				this.callbackCancel = callback;
				return this;
			};

			/**
			 * Display the popup.
			 */
			popup.display = function() {
				this.updateTitle();
				this.updateContent();
				this.updateButtons();
				this.updateStyleClass();
				// Erase autosize for rebuilding it.
				var obj = document.getElementById(this.rf.id + "_container");
				$(obj).css("width", "");
				$(obj).css("top", "");
				$(obj).css("left", "");
				obj = document.getElementById(this.rf.id + "_content_scroller");
				$(obj).css("width", "");
				obj = document.getElementById(this.rf.id + "_shadow");
				$(obj).css("width", "");
				this.rf.show();
				// Center element.
				obj = document.getElementById(this.rf.id + "_container");
				$(obj).center();
			};

			/**
			 * Return the html element id for displaying the title. If method
			 * setTitle has been called before, it returns the title id that
			 * have been set by this method, else it returns the default
			 * title according to the type.
			 */
			popup.getTitleId = function() {
				if (this.titleId) {
					return this.titleId;
				} else {
					return this.id + ":pt" + this.type;
				}
			};

			/**
			 * Update the content of the title bar.
			 */
			popup.updateTitle = function() {
				var id = this.getTitleId();
				var from = document.getElementById(id);
				var to = document
						.getElementById(this.rf.id + "_header_content");
				to.innerHTML = from.innerHTML;
				this.titleId = null;
			};

			/**
			 * Update the content of the message to display.
			 */
			popup.updateContent = function() {
				var from = document.getElementById(this.contentId);
				var to = document.getElementById(this.id + ":msg");
				if (from) {
					to.innerHTML = from.innerHTML;
				} else if (this.contentId) {
					to.innerHTML = this.contentId;
				} else {
					to.innerHTML = "";
				}
			};

			/**
			 * Return the label of the Ok button to used. If method
			 * setLabelOk has been called before, it returns the label that
			 * have been set by this method, else it returns the default
			 * label.
			 */
			popup.getLabelOk = function() {
				if (this.labelOk) {
					return this.labelOk;
				} else if (this.type == 'delete') {
					var obj = document.getElementById(this.id + ":pbdel");
					return obj.innerHTML;
				} else {
					var obj = document.getElementById(this.id + ":pbok");
					return obj.innerHTML;
				}
			};

			/**
			 * Return the label of the Cancel button to used. If method
			 * setLabelCancel has been called before, it returns the label that
			 * have been set by this method, else it returns the default
			 * label.
			 */
			popup.getLabelCancel = function() {
				if (this.labelCancel) {
					return this.labelCancel;
				} else {
					var obj = document.getElementById(this.id + ":pbccl");
					return obj.innerHTML;
				}
			};

			/**
			 * Update the buttons to display (label, callback, display or not).
			 */
			popup.updateButtons = function() {
				// Button Ok
				var id = this.id + ":ok";
				var label = this.getLabelOk();
				document.getElementById(id).value = label;
				bromo.display(id, this.displayOk);
				this.labelOk = null;
				// Button Cancel
				id = this.id + ":ccl";
				label = this.getLabelCancel();
				document.getElementById(id).value = label;
				bromo.display(id, this.displayCancel);
				this.labelCancel = null;
			};

			/**
			 * Update the styleClass of the popup according to the type.
			 */
			popup.updateStyleClass = function() {
				$(this).addClass(this.type);
			};

			/**
			 * Call when user clicks the Ok button.
			 */
			popup.onOk = function() {
				if (this.callbackOk) {
					eval(this.callbackOk);
				}
				this.hide();
				return false;
			};

			/**
			 * Call when user clicks the Cancel button.
			 */
			popup.onCancel = function() {
				if (this.callbackCancel) {
					eval(this.callbackCancel);
				}
				this.hide();
				return false;
			};
			
			/**
			 * Hide the popup.
			 */
			popup.hide = function() {
				this.callbackOk = null;
				this.callbackCancel = null;
				this.rf.hide();
				$(this).removeClass(this.type);
			};

		}

	};
}
