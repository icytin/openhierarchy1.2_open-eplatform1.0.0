var AdminHandler = function() {
	
	// Init
	$(function() {
		OperandHandler.init();
		ParameterHandler.init();
		FormulaHandler.init();
		ViewComponentHandler.init();
		ViewHandler.init();
		
		//Hide aside section
		$('div[role=main] aside').hide();
	});
	
}(jQuery);