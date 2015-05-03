var ViewHandler = function() {

	var _init = function() {
		
		_setTabHandling();
		_setDropHandling();
		
		
	};
	
	var _setTabHandling = function() {
		// Add view
		$('#viewsSection .link-section a').on('click', function() {
					
			var index = $('.nav-tabs li:last').index(),
				tabNumber = index + 2,
				tabName = 'Vy ' + tabNumber,
				tabIdentifier = 'view' + tabNumber;
			
			// Tab
			$('.nav-tabs').append('<li role="presentation"><a href="#' + tabIdentifier + '" aria-controls="' + tabIdentifier + '" role="tab" data-toggle="tab">' + tabName + '</a></li>');
			
			// Content
			$('.tab-content').append('<div role="tabpanel" class="tab-pane" id="' + tabIdentifier + '"></div>'); // Empty content pane..
			
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
	
	var _setDropHandling = function() {
		
		$('.tab-content').bind('drop', function(e) {
			
		    e.preventDefault();
		    e.stopPropagation();
		    
		    var $transferObj = $(e.originalEvent.dataTransfer.getData('Text')).clone(),
		    	$target = $(e.target);
		    
		    $transferObj.html('');
		    
		    if($target.hasClass('tab-content')) {
		    	
			    if($transferObj.attr('data-row')) { // If row
			    	var row = '<div class="row"></div>';
			    	$(this).find('.tab-pane.active').append(row);
			    }
		    }
		    else if($target.hasClass('row')) { // Drop on row
		    	
		    	if($transferObj.attr('data-col')) { // Dropped obj is col
		    		_handleDropOfCol($target);
		    	}
		    }
		    else if($target.attr('class').substring(0, 6) === 'col-lg') { // Drop on col
		    	
		    	$target = $target.parents('.row:first');
		    	_handleDropOfCol($target);
		    }

		    return false;
		    
		}).bind('dragover', false);
	};
	
	var _handleDropOfCol = function($target) {
		
		var colsAfterDrop = $target.find("div[class^='col-lg-']").length + 1;
		var colClassSpan = parseInt(12/colsAfterDrop);
		
		var newColClassDef = 'col-lg-' + colClassSpan;
		$target.find("div[class^='col-lg-']").removeClass().addClass(newColClassDef);
		
		$target.append('<div class="' + newColClassDef + '"></div>');
	};
	
	return {
		init: _init
	};
	
}(jQuery);
