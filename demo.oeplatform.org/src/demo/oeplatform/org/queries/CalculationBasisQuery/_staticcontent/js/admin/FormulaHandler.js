var FormulaHandler = function() {
	
	var ADD_FORMULA_PATH = '/demo.oeplatform.org/CalculationBasisProvider/AddFormula',
		DELETE_FORMULA_PATH = '/demo.oeplatform.org/CalculationBasisProvider/DeleteFormula',
		GET_FORMULA_LIST_PATH = '/demo.oeplatform.org/CalculationBasisProvider/GetFormulaList'
			
	var _init = function() {
		
		_setFormulaLinks();
		_setDropHandling();
		_handleDragOfParameters();
	};
	
	var _setFormulaLinks = function() {
		
		// Remove formula
		$('#formulasSection .formulas').on('click', function(e) {
			var $target = $(e.target);
			if($target.is('a')) {
				$target = $target.find('i');
			}
			
			if($target.hasClass('glyphicon-remove')) { // Remove formla
				
				var $tr = $target.parents('.row:first'),
				id = $tr.attr('id');
			
				$.post(DELETE_FORMULA_PATH, {id: id }, function (data, rq, ro) {
					if(rq === 'success') {
						if (data.success===1){
							// Remove the row from GUI
							$tr.remove();
						}
						else{
							alert(data.message);
						}
					}
					else {
						ErrorHandler.showError();
					}
				}, "json");
				
			}
			else if($target.hasClass('glyphicon-plus')) { // Add formula
				_addFormula();
			}
			else if($target.hasClass('glyphicon-trash')) { // Clean formula section
				$('#formulasSection input').val('');
				$('#formulasSection .editBox').html('');
			}
			
		});
	};
	
	var _addFormula = function() {
		
		var $formulaNameInput = $('#formulasSection input:first'),
			$formulaDescriptionInput = $('#formulasSection input:eq(1)'),
			$editBox = $('#formulasSection .editBox'),
			$editBoxElements = $('#formulasSection .editBox span'),
			numberOfElements = $editBoxElements.length,
			formulaName = $formulaNameInput.val().trim();
		
		if(formulaName === '' || $('#formulasSection .added div[data-name="' + formulaName + '"]').length !== 0) {
			$formulaNameInput.focus();
		}
		else if(numberOfElements === 0) {
			$editBox.css('border-color', '#d43f3a');
			setTimeout(function() {
				$editBox.css('border-color', '#000');				
			}, 800);
		}
		else {
			var formula = '',
				formulaPresentation = '';
			
			$.each($editBoxElements, function(i, element) {
				var $el = $(element);
				if($el.hasClass('operand')) {
					var operand = $el.html().trim();
					formula += "op(" + operand +")";
					formulaPresentation += ' ' + operand + ' ';
				}
				else if($el.hasClass('parameter')) {
					var paramaterName = $el.html(),
						presentationSepBefore = ( i === 0 ? '' : ' '),
						presentationSepAfter = ( i === numberOfElements - 1 ? '' : ' '),
						formulaSepBefore = ( i === 0 ? '' : ','),
						formulaSepAfter = ( i === numberOfElements - 1 ? '' : ',');
					
					var id = $el.attr('data-id').trim();
					formula += formulaSepBefore + "pm(" + id + ")" + formulaSepAfter;
					formulaPresentation += presentationSepBefore + paramaterName + presentationSepAfter;
				}
				else if($el.hasClass('formula')) {
					var formulaName = $el.html(),
						presentationSepBefore = ( i === 0 ? '' : ' '),
						presentationSepAfter = ( i === numberOfElements - 1 ? '' : ' '),
						formulaSepBefore = ( i === 0 ? '' : ','),
						formulaSepAfter = ( i === numberOfElements - 1 ? '' : ',');
					
					var id = $el.attr('data-id').trim();
					formula += formulaSepBefore + "fm(" + id + ")" + formulaSepAfter;
					formulaPresentation += presentationSepBefore + formulaName + presentationSepAfter;
				}
			});
			
			var formulaName = $formulaNameInput.val(),
				description = $formulaDescriptionInput.val(),
				$row = $('<div data-name="' + formulaName + '" data-description="' + description + '" data-formula="' + formula + '" data-formula-presentation="' + formulaPresentation + '" class="row formula" draggable="true" ><strong>' + formulaName + ':</strong>' + formulaPresentation + '<i class="glyphicon glyphicon-remove pull-right"></i><div class="col-lg-12"></div></div>');
			
			$.post(ADD_FORMULA_PATH, { queryId: $('#queryId').val(),formulaName:formulaName,formula:formula,description:description  }, function (data, rq, ro) {
				if(rq === 'success') {
					if (data.success === 1) {
						$row.attr('id', data.id);
						$('#formulasSection .added').append($row);
						_handleDragOfParameters();
					}
					else {
						alert(data.message);
					}
				}
				else {
					ErrorHandler.showError();
				}
			}, "json");
		}
	};
	
	var _handleDragOfParameters = function() {
		
		$('#formulasSection .added [draggable]')
		.bind('dragstart', function(e) {
			var $target = $(e.target),
				name = $target.find('td:first').html(),
				id = $target.attr('id');
			
		    e.originalEvent.dataTransfer.effectAllowed = 'copy';
		    e.originalEvent.dataTransfer.setData('Text', '#formulasSection .added [id="' + id + '"]'); // '#operandsSection [draggable]' + classSelector
		});
		
	};
	
	var _setDropHandling = function() {
		
		$('#formulasSection .formulas .editBox').bind('drop', function(e) {
			
		    e.preventDefault();
		    e.stopPropagation();
		    
		    var $target = $(e.target);
		    
	    	if(!$target.hasClass('editBox')) { // make sure the target always is the box
	    		$target = $target.parents('.editBox');
	    	}
		    
		    var $transferObj = $(e.originalEvent.dataTransfer.getData('Text')).clone(),
		    	isEditBox = $target.hasClass('editBox');
		    
		    if(isEditBox) {
		    	
			    if($target.find('[data-removable]')) { // Clean info if exist
			    	$target.find('[data-removable]').remove();
			    }
		    	
		    	if($transferObj.hasClass('operand')) { // Operands case
		    			    	
			    	// Manipulate the transfered object..
		    		/*
				    $transferObj.removeClass (function (index, css) {
				        return (css.match (/(^|\s)col-lg-\S+/g) || []).join(' ');
				    });
				    
				    $transferObj.addClass('col-lg-1');
				    */
			    	
			    	$target.append($transferObj);
		    	}
		    	else if($transferObj.hasClass('parameter')) {
		    		$target.append($transferObj);
		    	}
		    	else if($transferObj.hasClass('formula')) {
		    		var span = '<span data-name="' + $transferObj.attr('data-name') + '" data-id="' + $transferObj.attr('id') + '" class="formula" draggable="true">' + $transferObj.attr('data-name') + '</span>'; // '#operandsSection [draggable]' + classSelector
		    		$target.append(span);
		    	}
		    }

		    return false;
		    
		}).bind('dragover', false);
	};
	
	var _loadFormulas = function() {
		$.post(GET_FORMULA_LIST_PATH, { queryId: $('#queryId').val()}, function (data, rq, ro) {
			if(rq === 'success') {
				if (data.success===1){
					//generate all rows
					$.each(data.formulas, function(i,formula){
						_addFormulaRow(formula); // Add to the table
					});
					
					_handleDragOfParameters();
				}
				else{
					alert(data.message);
				}
			}
			else {
				ErrorHandler.showError();
			}
		}, "json");
	};
	
	var _addFormulaRow = function(formula){
		var formulaelements = formula.formula.split(",");
		var formulaPresentation = "";
		$.each(formulaelements,function(i,element){
			var id = _getParenthesesValue(element);
			
			if (element.substr(0,2)==="fm"){
				//Formula - get formula with id
				var formulaRow = $('div.formula[id="'+id+'"]')
				var name = formulaRow.attr("data-name");
				formulaPresentation+=name;
			}
			else if (element.substr(0,2)==="op"){
				//Operand
				formulaPresentation+=id;
			}
			else if (element.substr(0,2)==="pm"){
				//Parameter
				var name = $('#addedParametersTable [id="'+ id +'"] td:eq(0)').html();
				formulaPresentation+=name;
			}
		});
		
		/*var formulaPresentation = */
		$row = $('<div data-name="' + formula.name + 
				'" id="' + formula.formulaId + 
				'" data-description="' + formula.description + 
				'" data-formula="' + formula.formula + 
				'" data-formula-presentation="' + formulaPresentation + '" class="row formula" draggable="true" ><strong>' + formula.name + ':</strong>' + 
					formulaPresentation + '<i class="glyphicon glyphicon-remove pull-right"></i><div class="col-lg-12"></div></div>');
		$('#formulasSection .added').append($row);
	}
	
	var _getParenthesesValue = function(text){
		var regExp = /\(([^)]+)\)/;
		var matches = regExp.exec(text);
		return matches[1];
	}
	return {
		init: _init,
		loadFormulas: _loadFormulas
	};
	
}(jQuery);
