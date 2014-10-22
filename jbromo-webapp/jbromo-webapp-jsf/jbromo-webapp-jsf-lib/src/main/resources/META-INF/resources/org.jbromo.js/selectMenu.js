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

if (!bromo.selectMenu) {
	bromo.selectMenu = {
		init : function(selectBox) {
			if (!selectBox.bromoInitialized) {
				// Set select box as initialized.
				selectBox.bromoInitialized = true;
				// Declare if box is to be chained. If true, when the user selects an element in a child box, 
				// the selected parent is set to the parent of the selected child.  
				selectBox.chained = false;

				$(selectBox).change(function() {
					selectBox.updateDependencies();
				});

				// Add an option element to the select box.
				selectBox.addOption = function(value, text, selected) {
					var optn = document.createElement("OPTION");
					optn.value = value;
					optn.text = text;
					this.options.add(optn);
					if (selected) {
						this.selectedIndex = this.options.length - 1;
					}
				};

				// Return the selected option or null if not selected.
				selectBox.getSelectedOption = function() {
					if (this.selectedIndex >= 0) {
						return this.options[this.selectedIndex];
					} else {
						return null;
					}
				};

				// Return true if option is option parameter is the
				// "noSelectionOption".
				selectBox.isNoSelectionOption = function(option) {
					return option != null
							&& (option.value == "" || option.value == option.text);
				};

				// Return true if select box has a selected element, distinct
				// from noSelectionOption.
				selectBox.hasSelectedOption = function() {
					var option = this.getSelectedOption();
					if (option == null) {
						return false;
					} else {
						return !this.isNoSelectionOption(option);
					}
				};

				// Remove all options.
				selectBox.removeAll = function() {
					for (var o = this.options.length - 1; o >= 0; o--) {
						this.remove(o);
					}
					this.selectedIndex = -1;
				};

				// Declare a hierarchical parent to the current select box.
				selectBox.setParent = function(parent, map) {
					this.parent = parent;
					this.initOptions = new Array();
					this.initMap = map;
					// Clone options to initOptions.
					for (var i = 0; i < this.options.length; i++) {
						this.initOptions.push(this.options[i]);
					}
					// Reference this child into the parent element.
					if (!parent.initChildren) {
						selectMenu.init(parent);
						parent.initChildren = new Array();
					}
					parent.initChildren.push(this);
					parent.updateDependencies();
				};

				// Return true if select's options already contains an
				// option.value.
				selectBox.containsOptionValue = function(option) {
					for (var indexOption = 0; indexOption < this.options.length; indexOption++) {
						if (option.value == this.options[indexOption].value) {
							return true;
						}
					}
					return false;
				};

				// Return true if select's options already contains an
				// option.text.
				selectBox.containsOptionText = function(option) {
					for (var indexOption = 0; indexOption < this.options.length; indexOption++) {
						if (option.text == this.options[indexOption].text) {
							return true;
						}
					}
					return false;
				};

				// update hierarchical children elements according to the
				// selected element.
				selectBox.updateChildren = function() {
					if (this.initChildren) {
						for (var i = 0; i < this.initChildren.length; i++) {
							this.updateChild(this.initChildren[i]);
						}
					}
				};
				
				// update hierarchical elements according to the
				// selected element.
				selectBox.updateDependencies = function() {
					if (this.chained && this.parent) {
						// Get current selected element of child.
						var optionChild = this.getSelectedOption();
						var selectMap = this.initMap;
						if (this.isNoSelectionOption(optionChild)) {
							// Nothing to do.
						} else {
							for (var indexMap = 0; indexMap < selectMap.options.length; indexMap++) {
								if (optionChild.value == selectMap.options[indexMap].text) {
									for (var indexParent = 0; indexParent < this.parent.options.length; indexParent++) {
										if (selectMap.options[indexMap].value == this.parent.options[indexParent].value) {
											this.parent.selectedIndex = indexParent;
										}
									}
								}
							}
						}
						this.parent.updateDependencies();
					} else {
						this.updateChildren();
					}
				};

				// update one hierarchical child element according to the
				// selected
				// element.
				selectBox.updateChild = function(selectChild) {
					// Get current selected element of child.
					var selectedChildOption = selectChild.getSelectedOption();
					// Remove all child options.
					selectChild.removeAll();
					var selectMap = selectChild.initMap;
					// For each possible child options
					for (var indexOption = 0; indexOption < selectChild.initOptions.length; indexOption++) {
						var optionChild = selectChild.initOptions[indexOption];
						if (this.isNoSelectionOption(optionChild)) {
							// Add noSelection option to the child.
							selectChild.addOption(optionChild.value,
									optionChild.text, true);
						} else if (!this.hasSelectedOption()) {
							// No selection on parent, or "noSelectionLabel" is
							// selected.
							selectedChildOption = null;
							// Add all possible options according to all parent
							// visible options.
							for (var indexMap = 0; indexMap < selectMap.options.length; indexMap++) {
								if (optionChild.value == selectMap.options[indexMap].text) {
									for (var indexParent = 0; indexParent < this.options.length; indexParent++) {
										if (selectMap.options[indexMap].value == this.options[indexParent].value) {
											// OptionChild is according to the
											// parent's options.
											// Verify that we haven't already
											// added it in selectChild.
											if (!selectChild
													.containsOptionValue(optionChild)) {
												selectChild
														.addOption(
																optionChild.value,
																optionChild.text,
																selectedChildOption != null
																		&& optionChild.value == selectedChildOption.value);
											}
										}
									}
								}
							}
						} else {
							// Parent has a selected option, so filtering child
							// according to this parent selected option.
							// Loop on map
							var selectedParentOption = this.getSelectedOption();
							for (var indexMap = 0; indexMap < selectMap.options.length; indexMap++) {
								if (optionChild.value == selectMap.options[indexMap].text) {
									optionMap = selectMap.options[indexMap];
									if (optionMap.value == selectedParentOption.value) {
										selectChild
												.addOption(
														optionChild.value,
														optionChild.text,
														selectedChildOption != null
																&& optionChild.value == selectedChildOption.value);
									}
								}
							}
						}
					}
					if (selectChild.updateChildren) {
						selectChild.updateChildren();
					}
				};
			}
		},
		chained : function(parent, child, map) {
			selectMenu.init(parent);
			selectMenu.init(child);
			child.chained = true;
			child.setParent(parent, map);
		}
	};
}

if (!bromo.selectCountry) {
	bromo.selectCountry = {
		init : function(selectBox, emptyImgUrl) {
			bromo.selectMenu.init(selectBox);
			bromo.selectCountry.imgCountryEmpty = emptyImgUrl;

			selectBox.updateBackgroundImage = function() {
				var img = $(this).val();
				if (img) {
					// img = fr_CA for example. Get 'ca' value.
					img = img.substring(3, 5).toLowerCase();
					img = bromo.selectCountry.imgCountryEmpty.replace("empty",
							img);
					this.style.backgroundImage = img;
				} else {
					this.style.backgroundImage = bromo.selectCountry.imgCountryEmpty;
				}
			};

			$(selectBox).change(function() {
				this.updateBackgroundImage();
			});

			selectBox.updateBackgroundImage();
		}
	};
}
