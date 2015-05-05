var FormulaHandler = function() {

	var _init = function() {
		
		_setFormulaLinks();
		_setDropHandling();
		
	};
	
	var _setFormulaLinks = function() {
		
		// Add formula
		$('#formulasSection a').on('click', function(e) {
			var $formulaNameInput = $('#formulasSection input');
			if($formulaNameInput.val().trim() === '') {
				$formulaNameInput.focus();
			}
			else {
				
				var $row = $('<div class="row formula"><strong>' + $formulaNameInput.val() + '</strong><i class="glyphicon glyphicon-remove pull-right"></i><div class="col-lg-12"></div></div>');
				
				$.each($('#formulasSection .editBox div'), function(i, element) {
					
					var $el = $(element).clone();
					
					// Modify param or operand element
					$el.removeAttr('draggable');
					$el.addClass('formula-operand');
					
					// Append to the formula row
					$row.find('.col-lg-12').append($el);
				});
				
				$('#formulasSection .added').append($row);
			}
		});
		
		// Remove formula
		$('#formulasSection .formulas').on('click', function(e) {
			var $target = $(e.target);
			if($target.hasClass('glyphicon-remove')) {
				$target.parents('.row:first').remove();
			}
			
		});
	};
	
	var _setDropHandling = function() {
		
		$('#formulasSection .formulas .editBox').bind('drop', function(e) {
			
		    e.preventDefault();
		    e.stopPropagation();
		    
		    var $transferObj = $(e.originalEvent.dataTransfer.getData('Text')).clone(),
		    	$target = $(e.target);
		    
		    if(($target.hasClass('editBox') || $target.parents('.editBox').length !== 0) && $transferObj.hasClass('operand')) {
		    	
		    	if(!$target.hasClass('editBox')) { // make sure the target always is the box
		    		$target = $target.parents('.editBox');
		    	}
		    	
			    if($target.find('[data-removable]')) { // Clean info if exist
			    	$target.find('[data-removable]').remove();
			    }
		    	
		    	// Manipulate the transfered object..
			    $transferObj.removeClass (function (index, css) {
			        return (css.match (/(^|\s)col-lg-\S+/g) || []).join(' ');
			    });
			    
			    $transferObj.addClass('col-lg-1');
		    	
		    	$target.append($transferObj);
		    }

		    return false;
		    
		}).bind('dragover', false);
	};
	
	return {
		init: _init
	};
	
}(jQuery);
