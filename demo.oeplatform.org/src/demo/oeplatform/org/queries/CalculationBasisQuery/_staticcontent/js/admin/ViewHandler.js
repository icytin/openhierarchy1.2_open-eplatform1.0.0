var ViewHandler = function() {

	var SAVE_VIEW_HTML_PATH = '/demo.oeplatform.org/CalculationBasisProvider/SaveViewHtml',
		GET_VIEW_HTML_PATH = '/demo.oeplatform.org/CalculationBasisProvider/GetViewHtml',
		defaultInitBox = '<div data-removable="">Designa din vy genom att dra in vy-komponenter fr�n h�ger och placera sedan ut de formler som du vill ha med. Notera att du kan skapa en ny vy genom att klicka "L�gg till vy" under den h�r boxen.</div>',
		removeLink = '<i class="glyphicon glyphicon-remove pull-right"></i>',
		clearDiv = '<div class="clearboth"></div>';
	
	var _init = function() {
		
		_setTabHandling();
		_setDropHandling();
		_loadViewHtml();
	};
	
	var _setTabHandling = function() {
		
		// Add view
		$('#viewsSection').on('click', function(e) {
			
			var $target = $(e.target);
			
			if($target.is('.link-section a') && $target.find('.glyphicon-plus').length !== 0) { // Add a view
				
				var index = $('#viewsSection .nav-tabs li:last').index(),
					tabNumber = index + 2,
					tabName = 'Vy ' + tabNumber,
					tabIdentifier = 'view' + tabNumber;
			
				// Create a tab
				$('#viewsSection .nav-tabs').append('<li role="presentation"><a href="#' + tabIdentifier + '" aria-controls="' + tabIdentifier + '" role="tab" data-toggle="tab">' + tabName + '</a></li>');
				
				// Remove the delete link from the currently last view
				$('#viewsSection .tab-content .tab-pane .glyphicon-remove').remove();
				
				// Create content to this tab
				$('#viewsSection .tab-content').append('<div role="tabpanel" class="tab-pane" id="' + tabIdentifier + '">' + removeLink + defaultInitBox + '</div>');
			}
			else if($target.is('.link-section a') && $target.find('.glyphicon-trash').length !== 0) { // Clean current view
				$('#viewsSection .tab-pane.active').html(defaultInitBox);
				_saveViewHtml('');
			}
			else if($target.hasClass('glyphicon-remove')) { // remove a view
				
				var numberOfViews = $target.parents('#viewsSection .tab-content').find('.tab-pane').length,
					$currentTabPane = $target.parents('#viewsSection .tab-pane:first'),
					$currentTab = $currentTabPane.parents('[role=tabpanel]:first').find('li.active');
				
				if(numberOfViews > 2) { // Make the previous removable
					$currentTabPane.prev().prepend(removeLink + clearDiv);
				}
				
				$currentTab.prev().addClass('active');
				$currentTab.prev().find('a').click();
				
				// Remove this tab and pane
				$currentTab.remove();
				$currentTabPane.remove();
			}
		});
		
		// Change view
		$('#viewsSection .nav.nav-tabs').on('click', function(e) {
			$(this).find('li').removeClass('active')
			if($(e.target).is('a')) {
				$(e.target).parents('li:first').addClass('active');
				
				$('#viewsSection .tab-content div').removeClass('active');
				$('#viewsSection .tab-content div[id="' + $(e.target).attr('aria-controls') + '"]').addClass('active');
			}
			else {
				$(e.target).addClass('active');
			}
		});
	};
	
	var _saveViewHtml = function(html) {
		var viewId = $('#viewsSection .tab-pane.active').attr("data-viewId");
		$.post(SAVE_VIEW_HTML_PATH, { html: html, queryId: $('#queryId').val(),viewId:viewId===undefined?"-1":viewId }, function (data, rq, ro) { // Update html in db
			if(rq === 'success') {
				if (data.success === 1) {
					var id = data.id;
					$('#viewsSection .tab-pane.active').attr("data-viewId",id);//Save id
				}
				else {
					alert(data.message);
				}
			}
			else {
				ErrorHandler.showError();
			}
		}, "json");
	};
	
	var _loadViewHtml = function() {
		$.post(GET_VIEW_HTML_PATH, { queryId: $('#queryId').val() }, function (data, rq, ro) {
			if(rq === 'success') {
				if (data.success === 1) {
					// TODO: Set the view html
					if(data.view.html) 
					  $('#viewsSection .tab-pane.active').html(data.view.html);
					  
					$('#viewsSection .tab-pane.active').attr("data-viewId",data.view.viewId);//Save id
				}
				else{
					alert(data.message);
				}
			}
			else {
				ErrorHandler.showError();
			}
		}, "json");
	};
	
	var _setDropHandling = function() {
		
		$('#viewsSection .tab-content').bind('drop', function(e) {
			
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
		    
		    if($transferObj.hasClass('formula')) {
		    	_addFormulaToView($target, $transferObj);
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
	
	var _addFormulaToView = function($contentArea, $transferObject) {
		
		var formulaName = $transferObject.attr('data-name'),
			formulaDescription = $transferObject.attr('data-description'),
			formulaId = $transferObject.attr('id'),
			$formulaSection = $('<div id="' + formulaId + '">');
		
		// Title
		$formulaSection.append('<div class="row"><label class="title" data-type="formulaName" title="' + formulaName + '">' + formulaName + '</label></div>');
		
		// Description
		if(formulaDescription && formulaDescription !== '') {
			$formulaSection.append('<div class="row">' + formulaDescription + '</div>');
		}
		
		var rowString = '<div class="row"></row>',
			colString = '<div class="col-md-6"></div>';
		
		$.each($transferObject.attr('data-formula').split(','), function(i, part) {
		
			if(part.startsWith('fm')) {
				var formulaId = part.replace('fm(', '').replace(')', '');
			}
			else if(part.startsWith('pm')) {
				var parameterId = part.replace('pm(', '').replace(')', ''),
					$parameter = $('#addedParametersTable [id="' + parameterId + '"]'),
					$row = $(rowString),
					parameterName = $parameter.find('td:eq(0)').html(),
					parameterDescription = $parameter.find('td:eq(3)').html(),
					parameterValue = $parameter.find('td:eq(2)').html();

				// ===========================
				// Col1
				var $colString = $(colString);
				$span = $('<span data-id="' + parameterId + '" data-type="parameter" data-name="' + parameterName + '" data-description="' + parameterDescription + '" title="' + parameterDescription + '">' + parameterName + '</span>');
				$colString.append($span);
				$row.append($colString); // Add first col
				
				// ===========================
				// Col2
				$colString = $(colString);
				var val = parameterValue === '-' ? '[ X ]' : parameterValue;
				$span = $('<span data-id="' + parameterId + '" data-type="parameter value" data-name="' + parameterName + '" data-description="' + parameterDescription + '" title="' + parameterDescription + '">' + val + '</span>');
				$colString.append($span);
				$row.append($colString); // Add second col
				
				$formulaSection.append($row); // Append parameter section
				
			}
			else if(part.startsWith('op')) {
				var operand = part.replace('op(', '').replace(')', '');
			}
			
		});
		
		// Add to tab
		$contentArea.find('.tab-pane.active').append($formulaSection);
		
		_saveViewHtml($contentArea.find('.tab-pane.active').html());
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
