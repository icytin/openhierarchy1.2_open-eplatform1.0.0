var ViewComponentHandler = function() {

	var _init = function() {
		
		$('#viewComponentsSection [draggable]')
			.bind('dragstart', function(e) {
			    e.originalEvent.dataTransfer.effectAllowed = 'copy';
			    
			    var attrIdentifier = '[data-col]';
			    if($(e.target).attr('data-row')) {
			    	attrIdentifier = '[data-row]';
			    }
			    
			    e.originalEvent.dataTransfer.setData('Text', '#viewComponentsSection [draggable]' + attrIdentifier);
			});
	};
	
	return {
		init: _init
	};
	
}(jQuery);
