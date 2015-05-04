var FormulaHandler = function() {

	var _init = function() {
		
		$('#formulasSection a').on('click', function(e) {
			var row = '<div class="row"><i class="glyphicon glyphicon-remove pull-right"></i><div class="col-lg-12"></div></div>';
			$('#formulasSection .formulas').append(row);
		});
		
	};
	
	return {
		init: _init
	};
	
}(jQuery);
