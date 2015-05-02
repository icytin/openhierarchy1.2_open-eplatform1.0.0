var OperandHandler = function() {

	var _init = function() {
		_buildOperandLayout();
	};
	
	var _buildOperandLayout = function() {
		var $operandsSection = $('#operandsSection');
		$operandsSection.append('<h4>Aritmetiska</h4>' +
				_getSection([{operand: '+', name: 'plus'}, {operand: '-', name: 'minus'}]) +
				_getSection([{operand: '*', name: 'multiplication' }, { operand: '/', name: 'division' }]));
		$operandsSection.append('<h4>Logiska</h4>' +
				_getSection([{operand: '>', name: 'greaterthan' }, {operand: '<', name: 'lessthan' }]) +
				_getSection([{operand: '>=', name: 'greaterthanorequal' }, {operand: '<=', name: 'lessthanorequal'}]));
	};
	
	var _getSection = function(operands /* Max two */) {
		var $row = $('<div><div class="row"></div></div>');
		$.each(operands, function(i, val) {
			$row.append('<div class="col-lg-6 ' + val.name + ' operand" draggable="true">' + val.operand + '</div>');
		});
		
		return $row.html();
	}
	
	return {
		init: _init
	};
	
}(jQuery);
