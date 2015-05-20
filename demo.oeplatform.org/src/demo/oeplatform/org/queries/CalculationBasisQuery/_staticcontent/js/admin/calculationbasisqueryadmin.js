var AdminHandler = function() {
	
	var _initTabs = function() {
		$('#page-tabs').on('click', function(e) {
			
			$.each($('#page-tabs li'), function(i, li) { // Hide all sections
				$(li).removeClass('active');
				$($(li).find('a').attr('href')).hide();
			});
			
			var $target = $(e.target);
			if($target.is('li')) {
				$target = $target.find('a');
			}
			
			$target.parents('li:first').addClass('active');
			$($target.attr('href')).show(); // Show the selected one
			
		});
	};
	
	// Init
	$(function() {
		
		_initTabs();
		
		OperandHandler.init();
		ParameterHandler.init();
		FormulaHandler.init();
		ViewComponentHandler.init();
		ViewHandler.init();
		
		//Hide aside section
		$('div[role=main] aside').hide();
		
	});
	
}(jQuery);