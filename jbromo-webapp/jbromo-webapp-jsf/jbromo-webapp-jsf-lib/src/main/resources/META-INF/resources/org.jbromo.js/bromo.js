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
// For IE8 ...
if (typeof Object.create !== 'function') {
	Object.create = function (o) {
		function F() {}
		F.prototype = o;
		return new F();
	};
}
// ... for IE8

// Default bromo Component.
function BromoComponent(src) {
	this.src = src;
	if (src) {
		this.src.bromo = this;
	}
}

BromoComponent.prototype.getSrc = function() {
	return this.src;
};

var bromo = bromo || {};
if (!bromo.initialized) {
	bromo.initialized = true;

	bromo.onAjaxError = function(obj, event, contextPath) {
		if (event.type == "error") {
			if (event.responseText.indexOf('ViewExpiredException') >= 0) {
				window.location = contextPath + "/error/expired.xhtml";
			} else {
				var parent = document.getElementById(obj.id).parentNode;
				parent.title = event.responseText;
			}
			popupWait.hide();
		}
	};
	
	bromo.isAjaxRunning = function() {
		return document.getElementById("gStatus").children[0].style.display != 'none';
	}

	// Display or hide an object
	bromo.display = function(objId, show) {
		var obj = document.getElementById(objId);
		bromo.displayObject(obj, show);
	};

	// Display or hide an object
	bromo.displayObject = function(obj, show) {
		if (show == null) {
			show = !bromo.isObjectDisplayed(obj);
		}
		obj.style.display = show ? "" : "none";
		if (show && !bromo.isObjectDisplayed(obj)) {
			obj.style.display = "block";
		}
	};

	// Return true if an object is displayed.
	bromo.isDisplayed = function(objId) {
		var obj = document.getElementById(objId);
		return bromo.isObjectDisplayed(obj);
	};

	// Return true if an object is displayed.
	bromo.isObjectDisplayed = function(obj) {
		return !$(obj).is(":hidden");
	};
	
}