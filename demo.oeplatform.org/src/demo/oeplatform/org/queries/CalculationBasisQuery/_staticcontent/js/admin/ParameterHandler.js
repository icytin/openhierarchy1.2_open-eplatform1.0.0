var ParameterHandler = function() {

	var ADD_PARAM_PATH = '/demo.oeplatform.org/CalculationBasisProvider/AddParameter',
		DELETE_PARAM_PATH = '/demo.oeplatform.org/CalculationBasisProvider/DeleteParameter',
		GET_REF_QUERIES_PATH = '/demo.oeplatform.org/CalculationBasisProvider/GetRefQueries'
	
	var _init = function() {
		
		//Start by populating the query select list
		_populateQuerySelectList();
		
		_loadParameters();
		
		_handleDragOfParameters();
		
		_initParamaterChangeEvents();
	
		_initParameterClickEvents();
	};
	
	var _initParameterClickEvents = function() {
		$('#parameterSection').on('click', function(e) {
			
			var $target = $(e.target);
			
			if($target.hasClass('add')) { // Add param
				
				if(_isValidParameter()) {
					$.post(ADD_PARAM_PATH, { name: $('#parameter_name').val(), queryId: $('#queryId').val(), refQuery: $('#parameter_query option:selected').val(), value: $('#parameter_value').val(), description: $('#parameter_description').val() }, function (data, rq, ro) {
						if(rq === 'success') {
							if (data.success===1){
								var id = data.id;
								_addParameterRow(data); // Add the parameter to the table
								_resetElements();
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
			}
			else if($target.hasClass('glyphicon-remove')) { // Remove param
				
				var $tr = $target.parents('tr:first'),
					paramId = $tr.attr('data-id');
				
				$.post(DELETE_PARAM_PATH, {id: paramId }, function (data, rq, ro) {
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
			
			// ...
			
		});
	};
	
	var _initParamaterChangeEvents = function() {
		$('#parameterSection #parameter_query').change(function(e) {
			$target = $(e.target);
		})
	};
	
	var _handleDragOfParameters = function() {
		
		$('#addedParametersTable [draggable]')
		.bind('dragstart', function(e) {
			var $target = $(e.target),
				name = $target.find('td:first').html(),
				id = $target.attr('id');
			
		    e.originalEvent.dataTransfer.effectAllowed = 'copy';
		    e.originalEvent.dataTransfer.setData('Text', '<span data-name="' + name + '" data-id="' + id + '" class="parameter" draggable="true">' + name + '</span>'); // '#operandsSection [draggable]' + classSelector
		});
		
	};
	
	var _loadParameters = function() {
		
	};
	
	var _populateQuerySelectList = function(){
		$.post(GET_REF_QUERIES_PATH, {}, function (data, rq, ro) {
			if(rq === 'success') {
				//generate select list
				var selectList = $('#parameter_query');
				selectList.empty().append('<option value="-1">Ej vald</option>');
				$.each(data, function(name, value)
				{
					selectList.append('<option value=' + value + '>' + name + '</option>');
				});
			}
			else {
				ErrorHandler.showError();
			}
		}, "json");
	}
	
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
		var id= data.id; 
			
		var $table = $('#addedParametersTable');
		$table.find('thead').show();
		$tr = $('<tr id="' + id + '" draggable="true">');
		$tr.append('<td>' + $('#parameter_name').val() + '</td>')
		var query = $('#parameter_query option:selected').val() === '-1' ? '-' : $('#parameter_query option:selected').html(); 
		$tr.append('<td>' + query + '</td>');
		$tr.append('<td>' + $('#parameter_value').val() + '</td>');
		$tr.append('<td>' + $('#parameter_description').val() + '</td>');
		$tr.append('<td><i class="glyphicon glyphicon-remove pull-right"></i></td>');
		
		$table.find('tbody').append($tr);
		
		_handleDragOfParameters();
	};
	
	var _isValidParameter = function() {
		var isValid = true;
		
		var $name  = $('#parameterSection #parameter_name'),
			$query = $('#parameterSection #parameter_query'),
			$value = $('#parameterSection #parameter_value');
		
		if($name.val() === '') {
			$name.focus();
			return false;
		}
		else if($query.val() === '-1' && $value.val() === '') {
			$query.focus();
			return false;
		}
		else if($query.val() !== '-1' && $value.val() !== '') {
			$value.focus();
			return false;
		}
		
		/*
		$.each($('#parameterSection input, #parameterSection select'), function(i, el) {
			var $element = $(el);
			if($element.is('input') && $element.val() === '') {
				
				$element.focus();
				isValid = false;
				return isValid;
			}
			// Must always have a value
			//else if($element.is('select')) {
			//	return false;
			//}
			
		});
		*/
		
		return isValid;
	};
	
	return {
		init: _init
	};
	
}(jQuery);
