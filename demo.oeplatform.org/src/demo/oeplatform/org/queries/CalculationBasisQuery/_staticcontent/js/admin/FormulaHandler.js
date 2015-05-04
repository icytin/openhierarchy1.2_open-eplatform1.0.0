var FormulaHandler = function() {

	var _init = function() {
		
		_setFormulaLinks();
		_setDropHandling();
		
	};
	
	var _setFormulaLinks = function() {
		
		// Add formula
		$('#formulasSection a').on('click', function(e) {
			var row = '<div class="row"><i class="glyphicon glyphicon-remove pull-right"></i><div class="col-lg-12"></div></div>';
			$('#formulasSection .formulas').append(row);
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
		
		$('#formulasSection .formulas').bind('drop', function(e) {
			
		    e.preventDefault();
		    e.stopPropagation();
		    
		    var $transferObj = $(e.originalEvent.dataTransfer.getData('Text')).clone(),
		    	$target = $(e.target);
		    
		    if($target.hasClass('row') && $transferObj.hasClass('operand')) {
		    	
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
