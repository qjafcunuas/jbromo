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
// http://devbank.wordpress.com/2012/09/11/object-oriented-classes-in-javascript/
function BromoDataTable(table) {
	BromoComponent.call(this, table);
}
BromoDataTable.prototype = new BromoComponent();
BromoDataTable.prototype.constructor = BromoDataTable;

BromoDataTable.prototype.onRowClick = function(event) {
	var src = event.srcElement ? event.srcElement
			: event.target;
	// Search source's parent row/cell.
	var row = src;
	while (row != null && !(row instanceof HTMLTableRowElement)) {
		row = row.parentNode;
	}
	var cell = src;
	while (cell != null
			&& !(cell instanceof HTMLTableCellElement)) {
		cell = cell.parentNode;
	}
	if (row == null || cell == null) {
		return;
	}
	var id = cell.id.substring(0, cell.id.lastIndexOf(":"));
	// Search ajax/server link.
	var link = document.getElementById(id + ":cla");
	if (link == null) {
		link = document.getElementById(id + ":cls");
	}
	if (link && src != link) {
		var table = this.getSrc();
		$(table).find("tr").removeClass('active-row');
		$(row).addClass('active-row');
		link.click();
	}
};

BromoDataTable.prototype.initSelectAll = function(selectAll, forSelect) {
	if (!forSelect) {
		return;
	}
	var inputs = document.getElementsByTagName("input");
	var checked = true;
	var found = false;
	for (i = 0; i < inputs.length; i++) {
		if (inputs[i].type == "checkbox") {
			var checkbox = inputs[i];
			if (checkbox.id.startsWith(this.getSrc().id)
					&& checkbox.id.endsWith(forSelect)) {
				found = true;
				if (!checkbox.checked) {
					checked = false;
					break;
				}
			}
		}
	}
	if (found) {
		selectAll.checked = checked;
	} else {
		// no select box.
		selectAll.checked = false;
		selectAll.disabled = true;
	}
};

BromoDataTable.prototype.onSelectOne = function(selectAll, selectOne) {
	if (!selectOne.checked) {
		selectAll.checked = false;
	} else {
		var forSelect = selectOne.id.substring(selectOne.id.lastIndexOf(":"));
		this.initSelectAll(selectAll, forSelect);
	}
};

BromoDataTable.prototype.onSelectAll = function(event, forSelect) {
	if (!forSelect) {
		return;
	}
	var src = event.srcElement ? event.srcElement
			: event.target;
	var inputs = document.getElementsByTagName("input");
	for (i = 0; i < inputs.length; i++) {
		if (inputs[i].type == "checkbox") {
			var checkbox = inputs[i];
			if (checkbox.id.startsWith(this.getSrc().id)
					&& checkbox.id.endsWith(forSelect)) {
				checkbox.checked = src.checked;
			}
		}
	}
};


if (!bromo.columnHeader) {
	bromo.columnHeader = {
		init : function(div) {
			if (!div.bromoInitialized) {
				// Set div as bromo initialized.
				div.bromoInitialized = true;

				// Trap Return on it element.
				var it = document.getElementById(div.id + ":it");
				if (it) {
					$(it).keypress(function(event) {
						var code = event.keyCode ? event.keyCode : event.which;
						if (code == 13) {
							event.preventDefault();
							event.stopPropagation();
							this.blur();
							this.focus();
							return false;
						}
					});
				}
			}
		}
	};
}
