var ParameterHandler = function() {

	var ADD_PARAM_PATH = '/demo.oeplatform.org/CalculationBasisProvider/AddParameter',
		DELETE_PARAM_PATH = '/demo.oeplatform.org/CalculationBasisProvider/DeleteParameter';
	
	var _init = function() {
		$('#parameterSection').on('click', function(e) {
			
			var $target = $(e.target);
			
			if($target.hasClass('add')) { // Add param
				
				if(_isValidParameter()) {
					$.post(ADD_PARAM_PATH, {indata: "test" }, function (data, rq, ro) {
						if(rq === 'success') {
							_addParameterRow(data); // Add the parameter to the table
							_resetElements();
						}
						else {
							ErrorHandler.showError();
						}
					}, "json");
					
				}
			}
			else if($target.hasClass('glyphicon-remove')) { // Remove param
				
				var $tr = $target.parents('tr:first'),
					paramId = $tr.attr('data-id');
				
				$.post(DELETE_PARAM_PATH, {id: paramId }, function (data, rq, ro) {
					if(rq === 'success') {
						// Remove the row from GUI
						$tr.remove();
					}
					else {
						ErrorHandler.showError();
					}
				}, "json");
			}
			
			// ...
			
		});
	};
	
	var _resetElements = function() {
		$.each($('#parameterSection input, #parameterSection select'), function(i, el) {
			var $element = $(el);
			if($element.is('input')) {
				$element.val('');
			}
			else if($element.is('select')) {
				$element.find('option:first').attr('selected', 'selected');
			}
		});
	};
	
	var _addParameterRow = function(data) {
		
		// TODO: Fill with data.. add Id to the row.. 
		
		var $table = $('#addedParametersTable');
		$table.find('thead').show();
		$tr = $('<tr>');
		$tr.append('<td>' + $('#parameter_name').val() + '</td>')
		$tr.append('<td>' + $('#parameter_placeholder').val() + '</td>');
		$tr.append('<td>' + $('#parameter_type option:selected').html() + '</td>');
		$tr.append('<td>' + $('#parameter_default').val() + '</td>');
		$tr.append('<td>' + $('#parameter_isinput option:selected').html() + '</td>');
		$tr.append('<td><i class="glyphicon glyphicon-remove pull-right"></i></td>');
		
		$table.find('tbody').append($tr);
	};
	
	var _isValidParameter = function() {
		var isValid = true;
		$.each($('#parameterSection input, #parameterSection select'), function(i, el) {
			var $element = $(el);
			if($element.is('input') && $element.val() === '') {
				$element.focus();
				isValid = false;
				return isValid;
			}
			/* Must always have a value
			else if($element.is('select')) {
				return false;
			}
			*/
		});
		
		return isValid;
	};
	
	return {
		init: _init
	};
	
}(jQuery);
