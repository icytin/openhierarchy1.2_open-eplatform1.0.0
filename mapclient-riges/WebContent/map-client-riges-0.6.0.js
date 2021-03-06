var mapClientScriptLocation;
var mapClientScriptLocationFallback;
var mapClientLanguages = {
	"DIMENSION_AND_ANGLE_SETTINGS" : "Dimension and angle settings",
}
var mapInstances = {};
var version = "0.8.0";

(function () {
	
	if (!$("html").hasClass("map-client-riges")) {
	
		Ext = {
			buildSettings:{
				"scopeResetCSS": true
			}
		};
	
		var scriptNameRaw = "map-client-riges.js";
		var mapClientScriptName = new RegExp("(^|(.*?\\/))(" + scriptNameRaw + ")(\\?|$)");
	
		var scripts = document.getElementsByTagName('script');
	
		for (var i = 0; i < scripts.length; i++) {
	        
			var src = scripts[i].getAttribute('src');
	
			if (src) {
	            
				var match = src.match(mapClientScriptName);
	            
	            if (match) {
	
	            	mapClientScriptLocation = match[1];
	            	
	            	break;
	            }
	            
	        }
			
	    }
		
		if(mapClientScriptLocation == undefined && mapClientScriptLocationFallback != undefined) {
			mapClientScriptLocation = mapClientScriptLocationFallback.match(mapClientScriptName)[1];
		}
	
		var cssFiles = new Array(
			mapClientScriptLocation + version + "/ext/resources/css/ext-all-oep.css",
			mapClientScriptLocation + version + "/resources/ext-theme-oep/extras.css",
			mapClientScriptLocation + version + "/resources/map-client.css",
			mapClientScriptLocation + "css/map-client-riges.css",
			mapClientScriptLocation + version + "/openlayers/theme/default/style.css"
		);
	
		loadCssFiles(cssFiles);
	
		var scripts = new Array(
			mapClientScriptLocation + version + "/ext/ext-all.js",
			mapClientScriptLocation + version + "/ext/ext-theme-oep.js",
			mapClientScriptLocation + version + "/ext/locale/ext-lang-sv_SE.js",
			mapClientScriptLocation + version + "/openlayers/OpenLayers.js",
			mapClientScriptLocation + version + "/geoext2-all.js",
			mapClientScriptLocation + version + "/es5-shim.min.js",	
			mapClientScriptLocation + version + "/map-client-debug-all.js"
	    );
	    
		loadScripts(scripts);
		
		$("html").addClass("map-client-riges");
	
	}
	
})();

function loadScripts(jsFiles) {

    var scriptTags = new Array(jsFiles.length);
    
    for (var i = 0; i < jsFiles.length; i++) {
    	
		scriptTags[i] = "<script src='" + jsFiles[i] + "'></script>";
        
    }
    
    if (scriptTags.length > 0) {
    	
		var $head = $("head");

		for (var i = 0, len = scriptTags.length; i < len; i++) {
			$head.append(scriptTags[i]);
		}
    }
}

function loadCssFiles(cssFiles) {
    
	var linkTags = new Array(cssFiles.length);
	
	var head = document.getElementsByTagName('head')[0];
	
	for (var i = 0, len = cssFiles.length; i < len; i++) {

		var link = document.createElement('link');
	    link.rel = 'stylesheet';
	    link.type = 'text/css';
	    link.href = cssFiles[i];
	   
		if(!cssIsLoaded(link)) {
			head.appendChild(link);
		}
		
	}
    
}

function scriptIsLoaded(script) {
	
	if ($("script[src='" + script.src + "']").length == 0) {
		return false;
	}
	
	return true;
	
}

function cssIsLoaded(link) {
	
	if ($("link[href='" + link.href + "']").length == 0) {
		return false;
	}
	
	return true;
	
}

function MapClientInstance(queryID, mapID){

	this.mapID = mapID;
	this.mapDiv = $("#" + mapID);
	this.map;
	this.featureDialogs = {}; 
	this.dialog;
	this.preview;
	this.externalGraphicsLocation = mapClientScriptLocation + "images";
	this.mapImageLocation = mapClientScriptLocation + version + "/resources/images/";
	this.config;
	this.originalTools;

	var instance = this;

	mapInstances[queryID] = instance;

	instance.setCustomConfig = function(config, customConfig, preview) {

		if(customConfig.extent) {
			config.extent = customConfig.extent;
		}

		if(customConfig.search && config.gui.searchFastighet) {
			config.gui.searchFastighet = {
				renderTo : customConfig.search.renderTo
			};
			$("#" + customConfig.search.renderTo).show();
			if(customConfig.search.basePath) {
				config.basePath = customConfig.search.basePath;	
			}
		}

		if(customConfig.objectConfig && config.gui.objectConfig) {
			config.gui.objectConfig = {
				renderTo : customConfig.objectConfig.renderTo
			};
		} else {
			config.gui.objectConfig = false;
		}

		if (customConfig.searchCoordinate && config.gui.searchCoordinate) {
			config.gui.searchCoordinate = {
				renderTo : customConfig.searchCoordinate.renderTo,
			};
			$("#" + customConfig.searchCoordinate.renderTo).show();
		}

		instance.originalTools = config.tools;

		if(preview) {
			config.gui.zoomTools = false;
			config.gui.baseLayers = false;
			config.gui.searchFastighet = false;
			config.gui.toolbar = false;
			config.gui.searchCoordinate = false;
			config.tools = false;
		}

	};

	var initLoadingDialog = null;

	instance.init = function(configURL, config, customConfig, preview) {

		instance.preview = preview;

		if(configURL) {

			$.getJSON(configURL, function (result) {
								
				try {
					
					config = eval(result);

				} catch (e) {
					
					instance.showValidationError("Felaktigt format p� konfigurationen", "Ett fel uppstod n�r kartkonfigurationen skulle h�mtas, kontakta administrat�ren!");
					return;

				}

				if (!config) {
					
					instance.showValidationError("Ingen kartkonfiguration", "Ingen kartkonfiguration hittades, kontakta administrat�ren!");
					return

				} else {
					
					if(customConfig) {
						instance.setCustomConfig(config, customConfig, preview);
					}

					waitUntilMapIsCreated(instance, config);

				}

			}).error(function(response, status, xhr) { 
				
				if(status == "parsererror") {
					instance.showValidationError("Felaktigt format p� konfigurationen", "Ett fel uppstod n�r kartkonfigurationen skulle h�mtas, kontakta administrat�ren!");
				} else {
					instance.showValidationError("Anslutningsfel", "Kan inte ansluta till servern f�r h�mtning av kartkonfiguration, kontakta administrat�ren!"); 
				}

				return; 
			});

		} else if(!config) {
			
			instance.showValidationError("Ingen kartkonfiguration", "Ingen kartkonfiguration finns angiven!");
			return;

		} else {
			
			if(customConfig) {
				instance.setCustomConfig(config, customConfig, preview);
			}

			waitUntilMapIsCreated(instance, config);

		}

	};

	instance.initMapClient = function(config) {

		instance.showLoadingDialog = function(message) {
		
			var loadingDialog = new Ext.LoadMask(Ext.get(mapID), { msg: message, centered: true });

			loadingDialog.show();

			return loadingDialog;

		}

		initLoadingDialog = instance.showLoadingDialog("Laddar karta...");

		instance.mapDiv.bind("map-client-riges.loaded", instance.mapLoadedEventCallback);

		config.gui.map = {
			renderTo : mapID
		};

		instance.map = new MapClient();
		
		if(config.basePath) {
			instance.map.basePath = config.basePath;
		}
		
		var options = {
			"theme" : null
		};

		options.gui = config.gui;

		instance.config = config;

		instance.map.configure(config, options);

		if(instance.preview) {
			$.each(instance.map.gui.mapPanel.map.getControlsByClass("OpenLayers.Control.Navigation"), function(i, control){
				control.deactivate();
			});
		}

		initLoadingDialog.hide();
		
		instance.mapDiv.trigger("map-client-riges.loaded", [instance]);

		instance.map.gui.mapPanel.map.events.register('move', this, function(e) { instance.mapMovedCallback(e, instance); });
	
		if (instance.map.gui.mapPanel && instance.map.gui.mapPanel.drawLayer) {
			
			instance.map.gui.mapPanel.drawLayer.events.register('featureadded', null, function(e) { instance.featureAddedCallback(e, instance); } ); 
		
			instance.map.gui.mapPanel.drawLayer.events.register('featureremoved', null, function(e) { instance.removeFeatureDialog(e.feature); instance.featureRemovedCallback(e, instance); } );
			
			instance.map.gui.mapPanel.drawLayer.events.register('beforefeatureadded', null, function(e) { return instance.beforeFeatureAddedCallback(e, instance); } );

			instance.map.gui.mapPanel.drawLayer.events.register('sketchcomplete', null, function(e) { return instance.sketchCompletedCallback(e, instance); } );

			instance.map.gui.mapPanel.map.events.register('changelayer', null, function(e) { 
				if(e.property == "visibility") { 
					instance.layerVisibilityChangedCallback(e, instance); 
				}
			});

			instance.map.gui.mapPanel.map.events.register('changebaselayer', null, function(e) { 
				instance.baseLayerChangedCallback(e, instance); 
			});

		}

		if(instance.config.gui.objectConfig && instance.config.gui.objectConfig.renderTo) {
			
			instance.mapDiv.on("click", function(e) {
				
				e.preventDefault();
				
				var $configWrapper = $("#" + instance.config.gui.objectConfig.renderTo);
				
				var $header = $configWrapper.find("span.header");
				
				if($configWrapper.find(".x-panel:visible").length > 0) {
					if($header.length == 0) {
						$configWrapper.prepend("<span class='header'>" + mapClientLanguages.DIMENSION_AND_ANGLE_SETTINGS + "</span>");
					}
				} else {
					$header.remove();
				}
				
			});
			
			instance.mapDiv.trigger("click");
			
		}
		
	};

	instance.zoomToScale = function(scale) {
		
		instance.map.gui.mapPanel.map.zoomToScale(scale);

	};

	instance.showValidationError = function(title, message) {

		$("#query_" + queryID).find("article").addClass("error").addClass("jserror").before(
			'<div class="info-box first error jserror">' +
				'<span>' +
					'<strong data-icon-before="!">' + title +  '. </strong>' + message +
				'</span>' +
				'<div class="marker"></div>' +
			'</div>'
		);

	};

	instance.removeValidationErrors = function() {
		
		$("#query_" + queryID).find("article.jserror").removeClass("error").removeClass("jserror").prev(".jserror").remove();

	};

	instance.showFeatureDialog = function(feature, layer, message, contentSize, showCloseButton) {
					
		var $message = null;

		if(message.jquery) {
			$message = message;
		} else {
			$message = $(message);
		}

		var popup = new OpenLayers.Popup.Anchored("map-client-messagebox_" + mapID, feature.geometry.getBounds().getCenterLonLat(), contentSize, $message.html() + "<div class='marker' />", null, showCloseButton);
        
		popup.displayClass = "map-client-messagebox";
		popup.autoSize = false;

		var featureHeight = feature.geometry.getBounds().getHeight();

		var layerStyles = layer.styleMap.styles["default"];

		if(featureHeight == 0) {
			if(layerStyles.defaultStyle.pointRadius == "${getSize}") {
				featureHeight =layerStyles.context.getSize(feature);
			} else if(layerStyles.defaultStyle.pointRadius) {
				featureHeight = layerStyles.defaultStyle.pointRadius;
			} 
		}


		var anchor = {'size': new OpenLayers.Size(0, 0), 'offset': new OpenLayers.Pixel(-188, (featureHeight / 2) * -1)};
		popup.anchor = anchor;
		popup.panMapIfOutOfView = true;
		popup.relativePosition = "tr";
		popup.calculateRelativePosition = function () { return 'tr'; };
		feature.popup = popup;
		instance.map.gui.mapPanel.map.addPopup(popup);

		instance.featureDialogs[feature.id] = popup;

	};

	instance.removeFeatureDialog = function(feature) {
		
		var popup = instance.featureDialogs[feature.id];

		if(popup != null) {
			instance.map.gui.mapPanel.map.removePopup(popup);
			popup = null;
		}

	};
	
	instance.showDialog = function(message, showCloseButton, closeButtonCallback) {
		
		var $message = null;

		if(message.jquery) {
			$message = message.clone();
			$message.show();
		} else {
			$message = $(message);
		}
		
		var $dialog = $("<div class='mapquerydialog olPopup'><div class='olPopupContent'></div></div>");
		
		var $content = $dialog.find(".olPopupContent");
		
		$content.append($message);
		
		if(showCloseButton) {
			
			var $closeBox = $("<div class='olPopupCloseBox' />");
			
			$closeBox.click(function(e) {
				$dialog.close(e);
			});
			
			$content.append($closeBox);
			
		}
		
		$dialog.close = function(e) {
			
			if(closeButtonCallback) {
				closeButtonCallback($dialog);
			} 
			
			$dialog.remove();
			
			Ext.get(instance.mapID).unmask();
			
		};
		
		Ext.get(instance.mapID).mask();
		instance.mapDiv.append($dialog);
		
		$dialog.css({ "left": (instance.mapDiv.width()/2)-($dialog.width()/2), "top": (instance.mapDiv.height()/2)-($dialog.height()/2) });
		
		instance.dialog = $dialog;
	};

	instance.getVisibleLayers = function() {

		var visibleLayers = [];
		
		var layers = instance.map.gui.mapPanel.map.layers;
		
		for(var i = 0; i < layers.length; i++) {
			
			var layer = layers[i];
			
			if(layer.visibility == true) {
				visibleLayers.push(layer);
			}
			
		}
		
		return visibleLayers;
		
	};

	instance.getVisibleBaseLayers = function() {

		var visibleBaseLayers = [];
		
		var layers = instance.map.gui.mapPanel.map.layers;
		
		for(var i = 0; i < layers.length; i++) {
			
			var layer = layers[i];
			
			if(layer.isBaseLayer && layer.visibility == true) {
				visibleBaseLayers.push(layer);
			}
			
		}
		
		return visibleBaseLayers;
		
	};

	instance.mapMovedCallback = function(e, instance) { };

	instance.featureAddedCallback = function(e, instance) { };

	instance.featureRemovedCallback = function(e, instance) { };

	instance.beforeFeatureAddedCallback = function(e, instance) { };

	instance.sketchCompletedCallback = function(e, instance) { };

	instance.mapLoadedEventCallback = function(object, instance) { };

	instance.layerVisibilityChangedCallback =  function(e, instance) { };

	instance.baseLayerChangedCallback =  function(e, instance) { };

	return instance;

}

function waitUntilMapIsCreated(mapInstance, config) {
	
	if(!(typeof MapClient === "undefined")) {

		mapInstance.initMapClient(config);

	} else {

		setTimeout(function() { waitUntilMapIsCreated(mapInstance, config); }, 5);

	}

}