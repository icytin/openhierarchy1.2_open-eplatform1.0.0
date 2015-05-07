var ViewHandler = function() {

	var defaultInitBox = '<div data-removable="">Designa din vy genom att dra in vy-komponenter från höger och placera sedan ut de formler som du vill ha med. Notera att du kan skapa en ny vy genom att klicka "Lägg till vy" under den här boxen.</div>'
	
	var _init = function() {
		
		_setTabHandling();
		_setDropHandling();
		
	};
	
	var _setTabHandling = function() {
		
		// Add view
		$('#viewsSection').on('click', function(e) {
			
			var $target = $(e.target);
			if($target.is('.link-section a')) {
				
				var index = $('.nav-tabs li:last').index(),
					tabNumber = index + 2,
					tabName = 'Vy ' + tabNumber,
					tabIdentifier = 'view' + tabNumber;
			
				// Tab
				$('.nav-tabs').append('<li role="presentation"><a href="#' + tabIdentifier + '" aria-controls="' + tabIdentifier + '" role="tab" data-toggle="tab">' + tabName + '</a></li>');
				
				// Content
				$('.tab-content').append('<div role="tabpanel" class="tab-pane" id="' + tabIdentifier + '"><i class="glyphicon glyphicon-remove pull-right"></i>' + defaultInitBox + '</div>');
			}
			else if($target.hasClass('glyphicon-remove')) {
				var $tabPane = $target.parents('.tab-pane:first');
				var $currentTab = $tabPane.parents('[role=tabpanel]:first').find('li.active');
				$currentTab.prev().addClass('active');
				
				// Remove this tab and 
				$currentTab.remove();
				$tabPane.remove();
			}
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
		    
		    var isDropOnCol = $target.attr('class') && $target.attr('class').substring(0, 6) === 'col-lg',
		    	isDropOnRow = $target.hasClass('row'),
		    	isInvalidDrop = !$target.hasClass('tab-content') && !isDropOnRow && !isDropOnCol;
		    
		    // Make sure the target is correct and clean
		    if(isInvalidDrop) {
		    	$target = $target.parents('.tab-content:first');
		    	var $removableContent = $target.find('[data-removable]');
		    	if($removableContent.length !== 0) {
		    		$removableContent.remove();
		    	}
		    }
		    
		    $transferObj.html('');
		    
		    if($target.hasClass('tab-content')) {
		    	
			    if($transferObj.attr('data-row')) { // If row
			    	var row = '<div class="row"></div>';
			    	$(this).find('.tab-pane.active').append(row);
			    }
		    }
		    else if(isDropOnRow) { // Drop on row
		    	
		    	if($transferObj.attr('data-col')) { // Dropped obj is col
		    		_handleDropOfCol($target);
		    	}
		    }
		    else if(isDropOnCol) { // Drop on col
		    	
		    	$target = $target.parents('.row:first');
		    	_handleDropOfCol($target);
		    }

		    return false;
		    
		}).bind('dragover', false);
	};
	
	var _handleDropOfCol = function($target) {
		
		var colsAfterDrop = $target.find("div[class^='col-lg-']").length + 1,
			colClassSpan = parseInt(12/colsAfterDrop),
			newColClassDef = 'col-lg-' + colClassSpan;
		
		$target.find("div[class^='col-lg-']").removeClass().addClass(newColClassDef);
		
		$target.append('<div class="' + newColClassDef + '"></div>');
	};
	
	return {
		init: _init
	};
	
}(jQuery);
