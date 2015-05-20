var ParameterHandler = function() {

	var _init = function() {
		$('#parameterSection a.add').on('click', function(e) {
			if(_isValidParameter()) {
				alert('TODO: Post it aync!');
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
