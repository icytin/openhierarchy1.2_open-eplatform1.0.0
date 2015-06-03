var FormulaHandler = function() {
	
	var ADD_FORMULA_PATH = '/demo.oeplatform.org/CalculationBasisProvider/AddFormula';

	var _init = function() {
		
		_setFormulaLinks();
		_setDropHandling();
		
	};
	
	var _setFormulaLinks = function() {
		
		// Remove formula
		$('#formulasSection .formulas').on('click', function(e) {
			var $target = $(e.target);
			if($target.is('a')) {
				$target = $target.find('i');
			}
			
			if($target.hasClass('glyphicon-remove')) { // Remove formla
				$target.parents('.row:first').remove();
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
			$formulaDescriptionInput = $('#formulasSection input:eq(1)');
		
		if($formulaNameInput.val().trim() === '') {
			$formulaNameInput.focus();
		}
		else {
			var formula = '',
				formulaPresentation = '',
				$elements = $('#formulasSection .editBox span'),
				nOfEls = $elements.length;
			
			$.each($elements, function(i, element) {
				var $el = $(element);
				if($el.hasClass('operand')) {
					var operand = $el.html().trim();
					formula += operand;
					formulaPresentation += ' ' + operand + ' ';
				}
				else if($el.hasClass('parameter')) {
					var paramaterName = $el.html(),
						separatorBefore = ( i === 0 ? '' : ' '),
						separatorAfter = ( i === nOfEls - 1 ? '' : ' ');
					
					formula += 'dataId_' + $el.attr('data-id');
					formulaPresentation += separatorBefore + paramaterName + separatorAfter;
				}
			});
			
			var formulaName = $formulaNameInput.val(),
				description = $formulaDescriptionInput.val(),
				$row = $('<div data-name="' + formulaName + '" data-description="' + description + '" data-formula="' + formula + '" data-formula-presentation="' + formulaPresentation + '" class="row formula"><strong>' + formulaName + ':</strong>' + formulaPresentation + '<i class="glyphicon glyphicon-remove pull-right"></i><div class="col-lg-12"></div></div>');
			
			$.post(ADD_FORMULA_PATH, { /* Add params */  }, function (data, rq, ro) {
				if(rq === 'success') {
					if (data.success === 1) {
						$row.attr('id', data.id);
						$('#formulasSection .added').append($row);
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
		    }

		    return false;
		    
		}).bind('dragover', false);
	};
	
	return {
		init: _init
	};
	
}(jQuery);
