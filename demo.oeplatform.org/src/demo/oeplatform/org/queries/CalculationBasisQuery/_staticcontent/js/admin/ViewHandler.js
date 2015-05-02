var ViewHandler = function() {

	var _init = function() {
		
		// Add view
		$('#viewsSection .link-section a').on('click', function() {
					
			var index = $('.nav-tabs li:last').index(),
				tabNumber = index + 2,
				tabName = 'Vy ' + tabNumber,
				tabIdentifier = 'view' + tabNumber;
			
			// Tab
			$('.nav-tabs').append('<li role="presentation"><a href="#' + tabIdentifier + '" aria-controls="' + tabIdentifier + '" role="tab" data-toggle="tab">' + tabName + '</a></li>');
			
			// Content
			$('.tab-content').append('<div role="tabpanel" class="tab-pane" id="' + tabIdentifier + '">' + tabName + '</div>');
			
		});
		
		// Change view
		$('.nav.nav-tabs').on('click', function(e) {
			$(this).find('li').removeClass('active')
			if($(e.target).is('a')) {
				$(e.target).parents('li:first').addClass('active');
				
				$('.tab-content div').removeClass('active');
				$('.tab-content div[id="' + $(e.target).attr('aria-controls') + '"]').addClass('active');
			}
			else {
				$(e.target).addClass('active');
			}
		});
		
		
	};
	
	return {
		init: _init
	};
	
}(jQuery);
