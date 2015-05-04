var FormulaHandler = function() {

	var _init = function() {
		
		// Add formula
		$('#formulasSection a').on('click', function(e) {
			var row = '<div class="row"><i class="glyphicon glyphicon-remove pull-right"></i><div class="col-lg-12"></div></div>';
			$('#formulasSection .formulas').append(row);
		});
		
		// Remove formula
		$('#formulasSection .formulas').on('click', function(e) {
			$(e.target).parents('.row:first').remove();
		});
		
	};
	
	return {
		init: _init
	};
	
}(jQuery);
