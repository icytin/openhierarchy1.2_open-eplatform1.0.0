var ErrorHandler = function() {
	
	var _showError = function(message) {
		alert(message ? message : 'Misslyckades!');
	};
	
	return {
		showError: _showError
	}
	
}(jQuery);
