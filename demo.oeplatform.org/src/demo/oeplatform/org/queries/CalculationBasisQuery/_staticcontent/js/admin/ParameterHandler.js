var ParameterHandler = function() {

	var _init = function() {
		$('#parameterSection a.add').on('click', function(e) {
			if(_isValidParameter()) {
				alert('TODO: Post it aync!');
				
				// .. and to the following after result
				var $table = $('#addedParametersTable');
				$table.find('thead').show();
				$tr = $('<tr>');
				$tr.append('<td>' + $('#parameter_name').val() + '</td>')
				$tr.append('<td>' + $('#parameter_placeholder').val() + '</td>');
				$tr.append('<td>' + $('#parameter_type option:selected').html() + '</td>');
				$tr.append('<td>' + $('#parameter_default').val() + '</td>');
				$tr.append('<td>' + $('#parameter_isinput option:selected').html() + '</td>');
				
				$table.find('tbody').append($tr);
				
			}
		});
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
