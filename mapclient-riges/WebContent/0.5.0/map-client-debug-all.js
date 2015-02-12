/**
 * Initializes GUI from configuration
 */
Ext.define('MapClient.Gui', {
	activeAction: null,
	constructor : function(config) {
		this.config = config;
		
		this.mapPanel = Ext.create('MapClient.view.Map', {
			map: config.map,
			extent: config.map.extent
		});
				
		this.createPanels();
		this.createToolbar();
		
		var items = [];
		items.push(this.mapPanel);
		if (this.zoomTools) items.push(this.zoomTools);
		if (this.leftPanel) items.push(this.leftPanel);
		if (this.rightPanel) items.push(this.rightPanel);
		if (this.baseLayers) items.push(this.baseLayers);
		
		// create map rendered to a target element or as viewport depending on config
		if (this.config.gui.map && this.config.gui.map.renderTo) {
			var element = Ext.get(this.config.gui.map.renderTo);
			this.container = Ext.create('Ext.container.Container', Ext.apply({
				layout : 'absolute',
				border: false,
				width: element.getWidth(),
				height: element.getHeight(),
				items : items
			}, this.config.gui.map));
		} else {
			this.container = Ext.create('Ext.container.Viewport', {
				layout : 'absolute',
				items : items
			});
		}
				
		if (this.config.map.attribution) {
			var el = this.mapPanel.getEl();
			Ext.DomHelper.append(el, '<span class="unselectable attribution">'+this.config.map.attribution+'</span>');
		}
	},
	destroy: function() {
		if (this.mapPanel) this.mapPanel.destroy();
		if (this.zoomTools) this.zoomTools.destroy();
		if (this.mapLayers) this.mapLayers.destroy();
		if (this.searchFastighet) this.searchFastighet.destroy();
		if (this.searchCoordinate) this.searchCoordinate.destroy();
		if (this.toolbar) this.toolbar.destroy();
		if (this.leftPanel) this.leftPanel.destroy();
		if (this.rightPanel) this.rightPanel.destroy();
		if (this.baseLayers) this.baseLayers.destroy();
		if (this.objectConfig) this.objectConfig.destroy();
		if (this.objectConfigWindow) this.objectConfigWindow.destroy();
		if (this.container) this.container.destroy();
	},
	onToggle: function(button, pressed) {
		var action = button.baseAction;
		
		if (!this.objectConfig) return;
		
		// NOTE: want the effect of unselecting all and hiding object dialog on detoggle, but that is triggered after toggle... so this will have to do
		if (pressed) {
			this.mapPanel.unselectAll();
			this.objectConfig.hide();
			this.activeAction = action;
		}
		
		action.toggle(pressed);
	},
	createToolbar: function() {
		var basePath = this.config.basePath;
		
		var createAction = function(type) {
			var config = {
				map: this.mapPanel.map,
				mapPanel: this.mapPanel
			};
			
			if (type.constructor === Object) {
				Ext.apply(config, type);
				type = config.type;
				delete config.type;
			}

			if (type == 'ZoomSelector') {
				return Ext.create('MapClient.form.ZoomSelector', {map: this.mapPanel.map});
			}
			else {
				if (type == 'DrawObject') {
					config.objectConfigView = this.objectConfig;
				} else if (type == 'Identify') {
					config.basePath = basePath;
				}
				var action = Ext.create('MapClient.action.' + type, config);
				if (config.activate && action.control) {
					this.controlToActivate = action.control;
				}
				var button = Ext.create('Ext.button.Button', action);
				button.on('toggle', this.onToggle, this);
				return button;
			}
		};
		
		if (!this.config.tools) {
			this.config.tools = [];
		}
		
		// TODO: need to apply cls: 'oep-tools-last' to last item...
		var tbar = this.config.tools.map(function(config) {
			return [ ].concat(config.map(createAction, this));
		}, this);
		tbar = [].concat.apply([], tbar);
		
		// calc width of toolbar
		var width = 6; // padding
		tbar.forEach(function(item) {
			if (item){
				if (item.constructor == String) {
					width += 1; // separator
				} else if (item.width) {
					width += item.width;
				} else {
					width += 24; // button
				}
				// add spacing to next control
				width += 8;
			}
		});
		width += 6; // padding
				
		// create toolbar as floating left panel if no renderTo target is configured
		if (this.config.gui.toolbar && !this.config.gui.toolbar.renderTo) {
			this.leftPanel = Ext.create('Ext.toolbar.Toolbar', Ext.apply({
				cls: 'oep-tools',
				y : 20,
				x : 80,
				items: tbar
			}, this.config.gui.toolbar));
		} else if (this.config.gui.toolbar && this.config.gui.toolbar.renderTo) {
			this.toolbar = Ext.create('Ext.toolbar.Toolbar', Ext.apply({
				cls: 'oep-tools',
				width : this.config.gui.toolbar.width || width,
				items: tbar
			}, this.config.gui.toolbar));
		}
	},
	createPanels: function(items) {
		this.mapLayers = Ext.create('MapClient.view.Layers', Ext.apply({
			mapPanel : this.mapPanel
		}, this.config.gui.layers));
		
		this.searchFastighet = Ext.create('MapClient.view.SearchFastighet', Ext.apply({
			mapPanel : this.mapPanel,
			basePath: this.config.basePath
		}, this.config.gui.searchFastighet));
		
		// NOTE: only create right panel if layers panel isn't rendered
		// create right panel containing layers and search panels if no renderTo target is configured
		if (this.config.gui.layers && !this.config.gui.layers.renderTo) {
			this.rightPanel = Ext.create('Ext.panel.Panel', {
				y : 20,
				layout : {
					type: 'vbox',
				    align : 'stretch',
				    pack  : 'start'
				},
				width : 300,
				border: false,
				style : {
					'right' : '20px',
				},
				bodyStyle: {
					background: 'transparent'
				},
				items : [ this.mapLayers ]
			});
		}
		
		// TODO: only create if config has baselayers
		if (!this.config.map.allOverlays && this.config.gui.baseLayers) {
			this.baseLayers = Ext.create("MapClient.view.BaseLayers", {
				mapPanel : this.mapPanel,
				y: 20,
				style: {
					'right' : '45%'
				},
				width: 155
			});
		}
		
		if (this.config.gui.zoomTools && !this.config.gui.zoomTools.renderTo) {
			this.zoomTools = Ext.create('MapClient.view.ZoomTools', Ext.apply({
				mapPanel : this.mapPanel,
				x: 20,
				y: 20,
				width: 36
			}, this.config.gui.zoomTools));
		}
				
		// only create if renderTo
		if (this.config.gui.searchCoordinate && this.config.gui.searchCoordinate.renderTo) {
			this.searchCoordinate = Ext.create('MapClient.view.SearchCoordinate', Ext.apply({
				mapPanel : this.mapPanel
			}, this.config.gui.searchCoordinate));
		}
		
		// only create if renderTo
		if (this.config.gui.objectConfig && this.config.gui.objectConfig.renderTo) {
			this.objectConfig = Ext.create('MapClient.view.ObjectConfig', Ext.apply({
				mapPanel : this.mapPanel,
				gui: this
			}, this.config.gui.objectConfig));
		} else if (this.config.gui.objectConfig) {
			this.objectConfig = Ext.create('MapClient.view.ObjectConfig', Ext.apply({
				mapPanel : this.mapPanel,
				gui: this
			}, this.config.gui.objectConfig));
			this.objectConfigWindow = Ext.create('Ext.window.Window', Ext.apply({
				title: 'Object configuration',
				width: 480,
				height: 260,
				layout: 'fit',
				items: this.objectConfig
			}, this.config.gui.objectConfig));
			this.objectConfigWindow.show();
		}
	}
});
/**
 * Form field based on combobox to select zoom level
 */
Ext.define('MapClient.form.ZoomSelector' ,{
    extend:  Ext.form.ComboBox ,
    emptyText: "Zoom Level",
    listConfig: {
        getInnerTpl: function() {
            return "1: {scale:round(0)}";
        }
    },
    width: 120,
    editable: false,
    triggerAction: 'all',
    queryMode: 'local',
    initComponent: function() {
    	this.store = Ext.create("GeoExt.data.ScaleStore", {map: this.map});
    	this.listeners = {
			select: {
				fn:function(combo, record, index) {
	                this.map.zoomTo(record[0].get("level"));
	            },
				scope:this
			}
    	};
    	this.map.events.register('zoomend', this, function() {
            var scale = this.store.queryBy(function(record){
                return this.map.getZoom() == record.data.level;
            });

            if (scale.length > 0) {
                scale = scale.items[0];
                this.setValue("1 : " + parseInt(scale.data.scale));
            } else {
                if (!zoomSelector.rendered) return;
                this.clearValue();
            }
        });
    	this.callParent(arguments);
    }
});

        
/**
 * MapClient application class
 *  
 * Typical use case is to call the method configure(config) where config is a valid configuration object (usually parsed from JSON).
 */
Ext.define('MapClient', {
	                                     
	                                              
	                           
	                                          
    version: '0.5.0-SNAPSHOT',
    /**
     * Base path to be used for all AJAX requests against search-lm REST API
     * 
     * @property {String}
     */
    basePath: '/search/lm/',
    /**
     * OpenLayers Map instance
     * 
     * @property {OpenLayers.Map}
     */
    map: null,
    /**
     * Overlay used by drawing actions
     * 
     * @property {OpenLayers.Layer.Vector}
     */
    drawLayer: null,
    launch: function() {
    	Ext.tip.QuickTipManager.init();
    	OpenLayers.DOTS_PER_INCH = 101.6;
    },
    /**
     * Clean up rendered elements
     */
    destroy: function() {
    	if (this.gui) this.gui.destroy();
    },
    /**
     * Configure map
     * 
     * If this method is to be used multiple times, make sure to call destroy before calling it.
     * 
     * @param {String} config
     */
    configure: function(config) {
    	this.initialConfig = Ext.clone(config);
    	
    	// TODO: perhaps this is a bit ugly... but I need to pass basePath down to invididual components
    	config.basePath = this.basePath;
    	
    	var parser = Ext.create('MapClient.config.Parser');
    	parser.parse(config);
    	
    	this.gui = Ext.create('MapClient.Gui', config);
    	
    	this.map = this.gui.mapPanel.map;
    	this.drawLayer = this.gui.mapPanel.drawLayer;
    	
    	if (this.gui.controlToActivate) this.gui.controlToActivate.activate();
	},
	/**
	 * Helper method to add GeoJSON directly to the draw layer
	 * 
	 * @param geojson
	 */
	addGeoJSON: function(geojson) {
		var format = new OpenLayers.Format.GeoJSON();
		var feature = format.read(geojson, "Feature");
		this.drawLayer.addFeatures([feature]);
	}
});

/**
 * Creates predefined objects
 */
Ext.define('MapClient.ObjectFactory', {

	toPoint: function(coord) {
		return new OpenLayers.Geometry.Point(coord[0], coord[1]);
	},
	
	createR: function(config) {
		var x = config.x;
		var y = config.y;
		var l = config.l;
		var w = config.w;
		var coords = [
			[x    , y],
			[x    , y - l],
			[x + w, y - l],
			[x + w, y]
		];
		var linearRing = new OpenLayers.Geometry.LinearRing(coords.map(this.toPoint));
		var polygon = new OpenLayers.Geometry.Polygon([linearRing]);
		return polygon;
	},
	
	createL: function(config) {
		var x = config.x;
		var y = config.y;
		var l = config.l;
		var w = config.w;
		var m1 = config.m1;
		var m2 = config.m2;
		var coords = [
			[x     , y],
			[x     , y - w],
			[x + m1, y - w],
			[x + m1, y - m2],
			[x + l , y - m2],
			[x + l , y]
		];
		var linearRing = new OpenLayers.Geometry.LinearRing(coords.map(this.toPoint));
		var polygon = new OpenLayers.Geometry.Polygon([linearRing]);
		return polygon;
	},
			
	createD: function(config) {
		var x = config.x;
		var y = config.y;
		var l = config.l;
		var w = config.w;
		var m1 = config.m1;
		var m2 = config.m2;
		
		var o = (l-m1)/2;
		var coords = [
			[x        , y],
			[x        , y - w + o],
			[x + o    , y - w],
			[x + l - o, y - w],
			[x + l    , y - w + o],
			[x + l    , y]
		];
					
		var linearRing = new OpenLayers.Geometry.LinearRing(coords.map(this.toPoint));
		var polygon = new OpenLayers.Geometry.Polygon([linearRing]);
		return polygon;
	},
	
	createO: function(config) {
		var x = config.x;
		var y = config.y;
		var l = config.l;
		var w = config.w;
		var m1 = config.m1;
		var m2 = config.m2;
		var r = ((m1/2)*(Math.sqrt((4+(2*Math.SQRT2)))));
		var origin = new OpenLayers.Geometry.Point(x + r, y - r);
		var polygon = OpenLayers.Geometry.Polygon.createRegularPolygon(origin, r, 8);
		return polygon;
	},
	
	// NOTE: monkey patch rotate and resize to write resulting attributes on
	// change
	figurHooks: function(feature) {
		var oldMove = feature.geometry.move;
		feature.geometry.move = function(x, y) {
			feature.attributes.config.x += x;
			feature.attributes.config.y += y;
			oldMove.apply(feature.geometry, arguments);
		};
		
		var oldRotate = feature.geometry.rotate;
		feature.geometry.rotate = function(angle, origin) {
			feature.attributes.config.angle += angle;
			// TODO: rotate will change point of origin causing jumps if object is recreated
			// TODO: tried to fix below but isn't correct...
			//var point = feature.geometry.components[0].components[0];
			//feature.attributes.config.x = point.x;
			//feature.attributes.config.y = point.y;
			oldRotate.apply(feature.geometry, arguments);
		};
		
		// NOTE: dynamic resizing isn't allowed
		/*var oldResize = feature.geometry.resize;
		feature.geometry.resize = function(scale, origin, ratio) {
			feature.attributes.scale += scale;
			feature.attributes.origin = origin;
			feature.attributes.ratio = ratio;
			oldResize.apply(feature.geometry, arguments);
		};*/
	},
	create: function(config, attributes, style) {
		var geometry;
		if (config.type=='R') {
			geometry = this.createR(config);
		} else if (config.type=='L')	{
			geometry = this.createL(config);
		} else if (config.type=='D') {
			geometry = this.createD(config);
		} else {
			geometry = this.createO(config);
		}
		var center = geometry.getCentroid();
		var originGeometry = new OpenLayers.Geometry.Point(center.x, center.y);
		geometry.rotate(config.angle, originGeometry);
        var feature = new OpenLayers.Feature.Vector(geometry, attributes, style);
        this.figurHooks(feature);
        feature.attributes.config = config;
        return feature;
	}
});
/**
 * TODO: needs to be implemented
 */
Ext.define('MapClient.Search', {
	constructor: function(config) {
		initConfig();
	},
	doSearch: function() {
		// TODO: generic search logic..?
	}
});

/**
 * Base class for adding common functionality upon GeoExt.Action
 */
Ext.define('MapClient.action.Action', {
	extend:  GeoExt.Action ,
	constructor: function(config) {
		var mapPanel = config.mapPanel;
		var map = mapPanel.map;
				
		if (config.minScale || config.maxScale) {
			if (!config.minScale) config.minScale = 0;
			if (!config.maxScale) config.maxScale = 99999999999999;
			
			function onZoomend() {
				if (map.getScale() >= config.maxScale ||
					map.getScale() <= config.minScale) {
					this.disable();
				} else {
					this.enable();
				}
			};
			
			map.events.register('zoomend', this, onZoomend);
		}
		
    	this.callParent(arguments);
	},
	/**
	 * To be implemented by actions that need special logic on toggle
	 * @abstract
	 */
	toggle: function() {}
});

/**
 * Action to delete geometry
 * 
 * The snippet below is from configuration to MapClient.view.Map
 * 
 *  	@example
 *  	"tools" : [{
 *				"type": "DeleteGeometry",
 *			"tooltip": "Ta bort valt objekt/geometri"
 *		}]
 */
Ext.define('MapClient.action.DeleteGeometry', {
	extend:  MapClient.action.Action ,
	/**
	 * @param config
	 * @param {string} config.typeAttribute string to write to new feature attribute type
	 */
	constructor: function(config) {
		var mapPanel = config.mapPanel;
		var layer = mapPanel.drawLayer;
		
    	config.handler = function() {
    		layer.destroyFeatures(layer.selectedFeatures);
    	};
    	
    	config.iconCls = config.iconCls || 'action-deletegeometry';
    	config.tooltip = config.tooltip || 'Delete geometry';
    	
    	this.callParent(arguments);
	}
});

/**
 * Action for draw geometry.
 * 
 * The snippet below is from configuration to MapClient.view.Map
 * 
 * 		@example
 * 		"tools" : [{
 *  	 	"type": "DrawGeometry",
 *  	 	"geometry": "Path",
 *  	 	"tooltip": "Markera väg",
 *  	 	"attributes": {
 *  	 		"type": "Väg"
 *  	 		}
 *  	 }]
 */
Ext.define('MapClient.action.DrawGeometry', {
	extend:  MapClient.action.Action ,
	/**
	 * @param config
	 * @param {string} config.typeAttribute string to write to new feature attribute type
	 * @param {boolean} config.singleObject Set to true to clear layer before adding feature effectively restricing 
	 */
	constructor: function(config) {
		var mapPanel = config.mapPanel;
		var layer = mapPanel.drawLayer;
		
		config.attributes = config.attributes || {};
		config.attributes = Ext.applyIf(config.attributes, {label: ''});
		
		config.geometry = config.geometry || 'Polygon';
		
		var Control = OpenLayers.Class(OpenLayers.Control.DrawFeature, {
			// NOTE: override drawFeature to set custom attributes
			drawFeature: function(geometry) {
		        var feature = new OpenLayers.Feature.Vector(geometry, config.attributes);
		        var proceed = this.layer.events.triggerEvent(
		            "sketchcomplete", {feature: feature}
		        );
		        if(proceed !== false) {
		            feature.state = OpenLayers.State.INSERT;
		            this.layer.addFeatures([feature]);
		            this.featureAdded(feature);
		            this.events.triggerEvent("featureadded",{feature : feature});
		        }
		    }
		});
		
    	config.control = new Control(layer, OpenLayers.Handler[config.geometry]);
    	    	
    	config.iconCls = config.iconCls || 'action-drawgeometry';
    	config.tooltip = config.tooltip || 'Draw geometry';
    	config.toggleGroup = 'extraTools';
    	
    	this.callParent(arguments);
	}
});

/**
 * 
 */
Ext.define('MapClient.view.ObjectConfig', {
	extend :  Ext.form.Panel ,
	statics: {
		config: {
			type: 'R',
			w: 100,
			l: 100,
			m1: 20,
			m2: 20,
			angle: 0
		}
	},
	fieldDefaults: {
        labelWidth: 60
    },
    autoHeight: true,
    width: 400,
    border: false,
	selectedFeature: undefined,
	layer: undefined,
	factory: undefined,
	hidden: true,
	defaults: {
		border: false
	},
	initComponent : function() {
		this.layer = this.mapPanel.drawLayer;
		this.factory = Ext.create('MapClient.ObjectFactory');
						
		var types = {
			xtype : 'radiogroup',
			vertical : true,
			fieldLabel: 'Type',
			itemId: 'type',
			hidden: true,
			items : [ {
				boxLabel : '<img src="resources/css/images/figur-R.png">',
				name : 'rb',
				inputValue : 'R',
				checked : true
			}, {
				boxLabel : '<img src="resources/css/images/figur-L.png">',
				name : 'rb',
				enabled: false,
				inputValue : 'L'
			}, {
				boxLabel : '<img src="resources/css/images/figur-D.png">',
				name : 'rb',
				enabled: false,
				inputValue : 'D'
			}, {
				boxLabel : '<img src="resources/css/images/figur-O.png">',
				name : 'rb',
				enabled: false,
				inputValue : 'O'
			} ],
			listeners: {
				change: function(field, value) {
					this.config.type = value.rb;
					this.updateHelpImage(this.config.type);
					this.setFormItemVisibility(this.config.type);
					this.createObject();
				},
				scope: this
			}
		};
		
		var formItems = [];
		
		formItems.push(types);
		
		formItems = formItems.concat([{
			xtype: 'numberfield',
			fieldLabel: 'Width',
			itemId: 'w',
			minValue: 0,
			listeners: {
				change: function(field, value) {
					this.config.w = value;
					this.createObject();
				},
				scope: this
			}
		},{
			xtype: 'numberfield',
			fieldLabel: 'Length',
			itemId: 'l',
			minValue: 0,
			listeners: {
				change: function(field, value) {
					this.config.l = value;
					this.createObject();
				},
				scope: this
			}
		},{
			xtype: 'numberfield',
			fieldLabel: 'M1',
			itemId: 'm1',
			minValue: 0,
			listeners: {
				change: function(field, value) {
					this.config.m1 = value;
					this.createObject();
				},
				scope: this
			}
		},{
			xtype: 'numberfield',
			fieldLabel: 'M2',
			itemId: 'm2',
			minValue: 0,
			listeners: {
				change: function(field, value) {
					this.config.m2 = value;
					this.createObject();
				},
				scope: this
			}
		},{
			xtype: 'numberfield',
			itemId: 'angle',
			fieldLabel: 'Angle',
			value: 0,
			listeners: {
				change: function(field, value) {
					this.config.angle = value;
					this.createObject();
				},
				scope: this
			}
		}]);
		
		this.attributeFields = Ext.create('Ext.container.Container', { title: 'Attributes' });
		formItems.push(this.attributeFields);
		
		this.items = [ {
			layout: 'column',
			defaults: {
				border: false
			},
			padding: 5,
			items: [{
				width: 160,
				layout: 'form',
				items: formItems
			}, {
				columnWidth: 1,
				padding: 5,
				items: {
					itemId: 'objectimage',
					border: false,
					height: 152
				}
			}]
		}];
		
		this.layer.events.register('featuremodified', this, this.onFeaturemodified);
		this.layer.events.register('beforefeatureselected', this, this.onBeforefeatureselected);
		this.layer.events.register('featureunselected', this, this.onFeatureunselected);

		this.callParent(arguments);
	},
	setConfig: function(config) {
		if (config === undefined) {
			this.config = Ext.clone(MapClient.view.ObjectConfig.config);
			this.down('#type').show();
		} else if (config.type) {
			this.config = Ext.clone(config);
			Ext.applyIf(this.config, MapClient.view.ObjectConfig.config);
			this.down('#type').hide();
		} else {
			this.config = Ext.clone(config);
			Ext.applyIf(this.config, MapClient.view.ObjectConfig.config);
			this.down('#type').show();
		}
		this.setFormValues();
		this.show();
		return this.config;
	},
	setFormValues: function() {
		if (this.config) {
			this.down('#type').setValue({'rb': this.config.type});
			this.updateHelpImage(this.config.type);
			this.down('#w').setRawValue(this.config.w);
			this.down('#l').setRawValue(this.config.l);
			this.down('#m1').setRawValue(this.config.m1);
			this.down('#m2').setRawValue(this.config.m2);
			this.down('#angle').setRawValue(this.config.angle);
			this.setFormItemVisibility(this.config.type);
			this.down('#angle').show();
		} else {
			this.down('#type').hide();
			this.down('#w').hide();
			this.down('#l').hide();
			this.down('#m1').hide();
			this.down('#m2').hide();
			this.down('#angle').hide();
			this.down('#objectimage').hide();
		}
		
		this.attributeFields.removeAll();
		if (this.selectedFeature) {
			Object.keys(this.selectedFeature.attributes).forEach(function(key) {
				this.createAttributeField(this.selectedFeature, key);
			}, this);
		}
		this.doLayout();
	},
	createAttributeField: function(feature, key) {
		if (key == 'config') return;
		
		var value = feature.attributes[key];
		
		this.attributeFields.add({
			xtype: 'textfield',
			fieldLabel: key,
			value: value,
			listeners: {
				change: function(field, value) {
					this.selectedFeature.attributes[key] = value;
					this.layer.drawFeature(this.selectedFeature);
				},
				scope: this
			}
		});
	},
	setFormItemVisibility: function(type) {
		if (type == 'R') {
			this.down('#w').show();
			this.down('#l').show();
			this.down('#m1').hide();
			this.down('#m2').hide();
		} else if (type == 'L') {
			this.down('#w').show();
			this.down('#l').show();
			this.down('#m1').show();
			this.down('#m2').show();
		} else if (type == 'D') {
			this.down('#w').show();
			this.down('#l').show();
			this.down('#m1').show();
			this.down('#m2').hide();
		} else if (type == 'O') {
			this.down('#w').hide();
			this.down('#l').hide();
			this.down('#m1').show();
			this.down('#m2').hide();
		}
	},
	onFeaturemodified: function(e) {
		var feature = e.feature;
		config = feature.attributes.config;
		
		if (!config) return;
		
		this.down('#angle').setRawValue(config.angle);
	},
	onBeforefeatureselected: function(e) {
		var feature = e.feature;
		this.selectedFeature = feature;
		this.config = feature.attributes.config;
		
		var action = this.gui.activeAction;
		if (action && action.control instanceof OpenLayers.Control.ModifyFeature) {
			if (this.config && (action.control.mode & OpenLayers.Control.ModifyFeature.RESHAPE)) {
				action.control.mode = action.control.mode ^ OpenLayers.Control.ModifyFeature.RESHAPE;
			} else {
				action.control.mode = action.control._mode;
			}
			action.control.resetVertices();
		}
		
		this.show();
		
		this.setFormValues();
	},
	onFeatureunselected: function(e) {
		if (this.layer.selectedFeatures.length == 0) this.hide();
		
		this.selectedFeature = undefined;
	},
	createObject: function(x, y, style) {
		if (this.selectedFeature && this.selectedFeature.attributes.config) {
			// NOTE: for some strange reason replacing geometry components works, whereas replacing geometry causes strange render bugs
			var geometry = this.factory.create(this.config, style).geometry;
			this.selectedFeature.geometry.removeComponent(this.selectedFeature.geometry.components[0]);
			this.selectedFeature.geometry.addComponent(geometry.components[0]);
			this.selectedFeature.modified = true;
			this.selectedFeature.geometry.calculateBounds();
			if (this.mapPanel.modifyFeature) this.mapPanel.modifyFeature.resetVertices();
			this.layer.drawFeature(this.selectedFeature);
		}
	},
	updateHelpImage: function(type) {
		var name = 'figur-' + type + '-help.png';
		this.down('#objectimage').show();
		this.down('#objectimage').update('<img src="css/images/' + name + '"></img>');
	}
});
/**
 * Specialised draw Action that draws predefined objects
 * 
 * Predefined objects can either be configured when constructing the action and/or defined
 * by a corresponding (optional) ObjectConfig view.
 */
Ext.define('MapClient.action.DrawObject', {
	extend:  MapClient.action.Action ,
	                                          
	/**
	 * NOTE: objectConfigView is assumed to be supplied as a config object
	 * 
	 * @param {Object} config
	 * @param {Object} config.objectConfig
	 * @param {string} config.objectConfig.type available types are 'R', 'L', 'D', 'O'
	 * @param {number} config.objectConfig.w
	 * @param {number} config.objectConfig.l
	 * @param {number} config.objectConfig.m1
	 * @param {number} config.objectConfig.m2
	 * @param {number} config.objectConfig.angle rotation
	 */
	constructor: function(config) {	
		this.mapPanel = config.mapPanel;
		this.layer = this.mapPanel.drawLayer;
		this.style = config.style;
		this.attributes = config.attributes;
		this.objectConfig = config.objectConfig;
		this.objectConfigView = config.objectConfigView;
		this.factory = Ext.create('MapClient.ObjectFactory');
		
		this.attributes = this.attributes || {};
		this.attributes = Ext.applyIf(this.attributes, {label: ''});
					
    	var Click = OpenLayers.Class(OpenLayers.Control, {                
            initialize: function(options) {
                OpenLayers.Control.prototype.initialize.apply(
                    this, arguments
                ); 
                this.handler = new OpenLayers.Handler.Click(
                    this, {
                        'click': this.onClick
                    }, this.handlerOptions
                );
            },
            onClick: Ext.bind(this.onClick, this)
        });
    	
    	config.control = new Click({
            type: OpenLayers.Control.TYPE_TOGGLE
        });
    	
    	config.iconCls = config.iconCls || 'action-drawobject';
    	config.tooltip = config.tooltip || 'Draw predefined object';
    	config.toggleGroup = 'extraTools';
    	
    	this.callParent(arguments);
	},
	onClick: function(e) {
		var lonlat = this.mapPanel.map.getLonLatFromPixel(e.xy);
		var config = this.objectConfigView ? this.objectConfigView.config : MapClient.view.ObjectConfig.config;
        config = Ext.clone(config);
        config.x = lonlat.lon;
        config.y = lonlat.lat;
        var feature = this.factory.create(config, this.attributes, this.style);
        this.mapPanel.unselectAll();
        this.layer.addFeatures([feature]);
        this.mapPanel.selectControl.select(feature);
	},
	toggle: function(pressed) {
		if (pressed) this.objectConfigView.setConfig(this.objectConfig);
	}
});

/**
 * TODO: unfinished implementation
 */
Ext.define('MapClient.action.DrawText', {
	extend:  MapClient.action.Action ,
	constructor: function(config) {
		var mapPanel = config.mapPanel;
		var layer = mapPanel.drawLayer;
		
		config.attributes = config.attributes || {};
		config.attributes = Ext.applyIf(config.attributes, {label: ''});
		
		var Control = OpenLayers.Class(OpenLayers.Control.DrawFeature, {
			// NOTE: override drawFeature to set custom attributes
			drawFeature: function(geometry) {
		        var feature = new OpenLayers.Feature.Vector(geometry, config.attributes);
		        var proceed = this.layer.events.triggerEvent(
		            "sketchcomplete", {feature: feature}
		        );
		        if(proceed !== false) {
		            feature.state = OpenLayers.State.INSERT;
		            this.layer.addFeatures([feature]);
		            this.featureAdded(feature);
		            this.events.triggerEvent("featureadded",{feature : feature});
		            mapPanel.unselectAll();
		            mapPanel.selectControl.select(feature);
		        }
		    }
		});
    	
		config.control = new Control(layer, OpenLayers.Handler.Point);
		
    	config.iconCls = config.iconCls || 'action-drawtext';
    	config.tooltip = config.tooltip || 'Draw text';
    	config.toggleGroup = 'extraTools';
    	
    	this.callParent(arguments);
	}
});

/**
 * Action in toolbar that zooms the user to full extent
 * 
 * The snippet below is from configuration to MapClient.view.Map
 * 
 * {@img Fullextent.png fullextent}
 * 
 * 		@example
 * 		"tools": ["FullExtent"]
 */
Ext.define('MapClient.action.FullExtent', {
	extend:  MapClient.action.Action ,
	constructor: function(config) {		
    	config.control = new OpenLayers.Control.ZoomToMaxExtent();
    	
    	config.iconCls = config.iconCls || 'action-fullextent';
    	config.tooltip = config.tooltip || 'Zoom to full extent';
    	
    	this.callParent(arguments);
	}
});

/**
 * 
 */
Ext.define('MapClient.view.IdentifyResults', {
	extend :  Ext.panel.Panel ,
	autoScroll : true,
	layout: {
        type: 'vbox',
        pack:'start',
        align: 'stretch'
    },
	initComponent : function() {

		var store = Ext.create('Ext.data.TreeStore', {
			root : {
				expanded : true
			}
		});
		
		var root = store.getRootNode();
		
		// TODO: expect layer node to get text from result
		var layerNode = root.appendChild({
			text: 'Fastigheter',
			leaf: false,
			expanded : true
		});
		
		// TODO: do not assume a single object as result
		layerNode.appendChild({
			text: this.result[Object.keys(this.result)[0]],
			leaf: true
		});

		this.items = [{
			xtype : 'treepanel',
			flex: 1,
			rootVisible : false,
			//lines : false,
			store : store
		}, {
			xtype : 'propertygrid',
			flex: 0,
			title: 'Egenskaper',
			collapsible : true,
			collapsed: true,
			source: this.result
		} ];

		this.callParent(arguments);
	}
});
/**
 * Identify action
 * 
 * TODO: Should be generic, is now hardcoded against search-lm parcels
 */
Ext.define('MapClient.action.Identify', {
	extend:  MapClient.action.Action ,
	                                             
	
	constructor: function(config) {
		
		var mapPanel = config.mapPanel;
		var layer = mapPanel.searchLayer;
		var map = config.map;
		var basePath = config.basePath;
		
    	var Click = OpenLayers.Class(OpenLayers.Control, {                
            initialize: function(options) {
                OpenLayers.Control.prototype.initialize.apply(
                    this, arguments
                ); 
                this.handler = new OpenLayers.Handler.Click(
                    this, {
                        'click': this.onClick
                    }, this.handlerOptions
                );
            },
            onClick: function(evt) {
            	mapPanel.setLoading(true);
            	layer.destroyFeatures();
            	
            	var lonlat = map.getLonLatFromPixel(evt.xy);
                
                var x = lonlat.lon;
                var y = lonlat.lat;
                
                var point = new OpenLayers.Geometry.Point(x, y);
                var feature = new OpenLayers.Feature.Vector(point);
                layer.addFeatures([feature]);

                Ext.Ajax.request({
            		url: basePath + 'registerenheter?x=' +x+ '&y=' +y,
            		success: function(response) {
            			var json = Ext.decode(response.responseText);
            			
            			var identifyResults = Ext.create('MapClient.view.IdentifyResults', {
            				mapPanel : mapPanel,
            				result: json
            			});
            			
            			var popup = Ext.create('GeoExt.window.Popup', {
                            title: 'Sökresultat',
                            location: feature,
                            map: mapPanel,
                            maximizable : false,
                            minimizable : false,
                            resizable: true,
                            width: 300,
                            height: 200,
                            layout: 'fit',
                            items: identifyResults,
                            collapsible: false
                        });
                        popup.show();
            		},
            		failure: function() {
            			Ext.Msg.alert('Fel', 'Ingen träff.');
            		},
            		callback: function() {
            			mapPanel.setLoading(false);
            		}
            	});
            }
        });
    	
    	config.control = new Click({
            type: OpenLayers.Control.TYPE_TOGGLE
        });
    	
    	config.iconCls = config.iconCls || 'action-search';
    	config.tooltip = config.tooltip || 'Identify';
    	config.toggleGroup = 'extraTools';
    	
    	this.callParent(arguments);
	}
});

/**
 * Action that measure area.
 *{@img Measurearea.png measurearea}
 * 
 * The example below is from configuration adding the tool to MapClient.view.Map:
 * 
 * 		@example
 * 		"tools": [
 *		[ "FullExtent", "ZoomSelector" ],
 *		[ "MeasureLine", "MeasureArea"]
 *		]
 */
Ext.define('MapClient.action.MeasureArea', {
	extend:  MapClient.action.Action ,
	
	constructor: function(config) {
    	
        var sketchSymbolizers = {
            "Point": {
                pointRadius: 4,
                graphicName: "square",
                fillColor: "white",
                fillOpacity: 1,
                strokeWidth: 1,
                strokeOpacity: 1,
                strokeColor: "#333333"
            },
            "Line": {
                strokeWidth: 3,
                strokeOpacity: 1,
                strokeColor: "#2969db",
                strokeDashstyle: "dash"
            },
            "Polygon": {
                strokeWidth: 3,
                strokeOpacity: 1,
                strokeColor: "#2969db",
                strokeDashstyle: "dash",
                fillColor: "#deecff",
                fillOpacity: 0.4
            }
        };
        var style = new OpenLayers.Style();
        style.addRules([
            new OpenLayers.Rule({symbolizer: sketchSymbolizers})
        ]);
		var styleMap = new OpenLayers.StyleMap({"default": style});
		
    	config.control = new OpenLayers.Control.Measure(OpenLayers.Handler.Polygon, {
            persist: true,
            handlerOptions: {
                layerOptions: {
                	styleMap: styleMap
                }
            }
        });
    	
    	var out = "";
    	var count = 1;
    	var reset = true;
    	function handleMeasurements(event) {
    		MapClient.action.MeasureLine.createMeasureWindow();
    		MapClient.action.MeasureLine.measureWindow.show();
    		
    		var geometry = event.geometry;
            var units = event.units;
            var order = event.order;
            var measure = event.measure;
            //var out = "";
            if (reset) { 
            	out = "";
            	count = 1;
            	reset = false;
            }
            var p1 = geometry.components[geometry.components.length-2];
            var p2 = geometry.components[geometry.components.length-3];
            if (p1 === undefined || p2 === undefined) return;
            measure = p1.distanceTo(p2);
            units = "m";
            if(order == 1) {
                out += "Delsträcka " + count + " : " + measure.toFixed(3) + " " + units + "<br>";
            } else {
                out += "Delsträcka " + count + " : " + measure.toFixed(3) + " " + units + "<sup>2</" + "sup>" + "<br>";
            }
            MapClient.action.MeasureLine.measureWindow.update(out);
            count = count+1;
    	};
    	
    	function handleMeasurement(event) {
            var units = event.units;
            var order = event.order;
            var measure = event.measure;
            //var out = "";
            if(order == 1) {
                out += "Totalt: " + measure.toFixed(3) + " " + units + "<br>";
            } else {
                out += "Totalt: " + measure.toFixed(3) + " " + units + "<sup>2</" + "sup>" + "<br>";
            }
            MapClient.action.MeasureLine.measureWindow.update(out);
            reset = true;
    	};
    	
    	config.control.events.on({
            'measure': handleMeasurement,
            'measurepartial': handleMeasurements
        });
    	
    	config.iconCls = config.iconCls || 'action-measurearea';
    	config.tooltip = config.tooltip || 'Measure area';
    	config.toggleGroup = 'extraTools';
    	
    	this.callParent(arguments);
	}
});

/**
 * Action that measure line.
 * {@img Measureline.png measureline}
 * 
 * The example below is from configuration:
 * 
* The example below is from configuration adding the tool to MapClient.view.Map:
 * 
 * 		@example
 * 		"tools": [
 *		[ "FullExtent", "ZoomSelector" ],
 *		[ "MeasureLine", "MeasureArea"]
 *		]
 */
 
Ext.define('MapClient.action.MeasureLine', {
	extend:  MapClient.action.Action ,
	statics: {
		measureWindow: null,
		createMeasureWindow: function() {
			if (this.measureWindow) {
				this.measureWindow.update('');
				return;
			}
			this.measureWindow = Ext.create('Ext.window.Window', {
	            title: 'Mätresultat',
	            maximizable : false,
	            minimizable : false,
	            resizable: true,
	            width: 300,
	            height: 200,
	            autoScroll: true,
	            layout: 'fit',
	            collapsible: false,
	            closeAction: 'hide'
	        });
		}
	},
	constructor: function(config) {
    	
        var sketchSymbolizers = {
            "Point": {
                pointRadius: 4,
                graphicName: "square",
                fillColor: "white",
                fillOpacity: 1,
                strokeWidth: 1,
                strokeOpacity: 1,
                strokeColor: "#333333"
            },
            "Line": {
                strokeWidth: 3,
                strokeOpacity: 1,
                strokeColor: "#666666",
                strokeDashstyle: "dash"
            },
            "Polygon": {
                strokeWidth: 2,
                strokeOpacity: 1,
                strokeColor: "#666666",
                fillColor: "white",
                fillOpacity: 0.3
            }
        };
        var style = new OpenLayers.Style();
        style.addRules([
            new OpenLayers.Rule({symbolizer: sketchSymbolizers})
        ]);
		var styleMap = new OpenLayers.StyleMap({"default": style});
		
    	config.control = new OpenLayers.Control.Measure(OpenLayers.Handler.Path, {
            persist: true,
            handlerOptions: {
                layerOptions: {
                	styleMap: styleMap
                }
            }
        });
    	
    	var out = "";
    	var count = 1;
    	var reset = true;
    	function handleMeasurements(event) {
    		MapClient.action.MeasureLine.createMeasureWindow();
    		MapClient.action.MeasureLine.measureWindow.show();
    		
    		var geometry = event.geometry;
            var units = event.units;
            var order = event.order;
            var measure = event.measure;
            //var out = "";
            if (reset) { 
            	out = "";
            	count = 1;
            	reset = false;
            }
            var p1 = geometry.components[geometry.components.length-2];
            var p2 = geometry.components[geometry.components.length-3];
            if (p1 === undefined || p2 === undefined) return;
            measure = p1.distanceTo(p2);
            units = "m";
            if(order == 1) {
                out += "Delsträcka " + count + " : " + measure.toFixed(3) + " " + units + "<br>";
            } else {
                out += "Delsträcka " + count + " : " + measure.toFixed(3) + " " + units + "<sup>2</" + "sup>" + "<br>";
            }
            MapClient.action.MeasureLine.measureWindow.update(out);
            count = count+1;
    	};
    	
    	function handleMeasurement(event) {
            var units = event.units;
            var order = event.order;
            var measure = event.measure;
            //var out = "";
            if(order == 1) {
                out += "Totalt: " + measure.toFixed(3) + " " + units + "<br>";
            } else {
                out += "Totalt: " + measure.toFixed(3) + " " + units + "<sup>2</" + "sup>" + "<br>";
            }
            MapClient.action.MeasureLine.measureWindow.update(out);
            reset = true;
    	};
    	
    	config.control.events.on({
            'measure': handleMeasurement,
            'measurepartial': handleMeasurements
        });
    	
    	config.iconCls = config.iconCls || 'action-measureline';
    	config.tooltip = config.tooltip || 'Measure line';
    	config.toggleGroup = 'extraTools';
    	
    	this.callParent(arguments);
	}
});

/**
 * Action that modify geometry
 *			
 *			
 */
Ext.define('MapClient.action.ModifyGeometry', {
	extend:  MapClient.action.Action ,
	constructor: function(config) {	
		var mapPanel = config.mapPanel;
		var layer = mapPanel.drawLayer;
		
		if (config.drag === undefined) config.drag = true;
		if (config.rotate === undefined) config.rotate = true;
		
		var mode = 0;
		if (config.drag) mode = mode | OpenLayers.Control.ModifyFeature.DRAG;
		if (config.rotate) mode = mode | OpenLayers.Control.ModifyFeature.ROTATE;
		if (config.resize) mode = mode | OpenLayers.Control.ModifyFeature.RESIZE;
		if (config.reshape) mode = mode | OpenLayers.Control.ModifyFeature.RESHAPE;
		
		var options = Ext.apply({mode: mode}, config.options);
		config.control = new OpenLayers.Control.ModifyFeature(layer, options);
		config.control._mode = config.control.mode;
		
    	config.iconCls = config.iconCls || 'action-modifygeometry';
    	config.tooltip = config.tooltip || 'Modify geometry';
    	config.toggleGroup = 'extraTools';
    	
    	this.callParent(arguments);
	}
});

/**
 * Action for print using mapfish.
 */
Ext.define('MapClient.action.Print', {
	extend :  MapClient.action.Action ,
	constructor : function(config) {
		var mapPanel = config.mapPanel;
		var printExtent = mapPanel.plugins[0];
		var printProvider = printExtent.printProvider;
		var printDialog = null;
		var page = null;

		var onTransformComplete = function() {
			var scale = printDialog.down('#scale');
			scale.select(page.scale);
		};
		var onBeforedownload = function() {
			if (printDialog) printDialog.setLoading(false);
		};
		var onPrintexception = function(printProvider, response) {
			if (printDialog) printDialog.setLoading(false);
			Ext.Msg.show({
			     title:'Felmeddelande',
			     msg: 'Print failed.\n\n' + response.responseText,
			     icon: Ext.Msg.ERROR
			});
		};
		var close = function() {
			printProvider.un('beforedownload', onBeforedownload);
			printProvider.on('printexception', onPrintexception);
			printExtent.control.events.unregister('transformcomplete', null, onTransformComplete);
			printExtent.removePage(page);
			printExtent.hide();
			printDialog = null;
		};
		var onClose = function() {
			close();
			control.deactivate();
		};
		
		config.iconCls = config.iconCls || 'action-print';
		config.tooltip = config.tooltip || 'Print';
		config.toggleGroup = 'extraTools';
		
		var Custom =  OpenLayers.Class(OpenLayers.Control, {         
			initialize: function(options) {
                OpenLayers.Control.prototype.initialize.apply(
                    this, arguments
                );
            },
            type: OpenLayers.Control.TYPE_TOGGLE,
            activate: function() {
            	if (printDialog) {
    				return;
    			};
    			// NOTE: doing a hide/show at first display fixes interaction problems with preview extent for unknown reasons
    			printExtent.hide();
    			printExtent.show();
    			page = printExtent.addPage();
    			
    			printDialog = new Ext.Window({
    				autoHeight : true,
    				width : 250,
    				resizable: false,
    				layout : 'fit',
    				bodyPadding : '5 5 0',
    				title: 'Print settings',
    				listeners: {
    					close: onClose
    				},
    				items : [ {
    					xtype : 'form',
    					layout : 'anchor',
    					defaults : {
    						anchor : '100%'
    					},
    					fieldDefaults : {
    						labelWidth : 75
    					},
    					items : [ {
    						xtype : 'combo',
    						fieldLabel: 'Layout',
    						store : printProvider.layouts,
    						displayField : 'name',
    						valueField : 'name',
    						queryMode: 'local',
    						value: printProvider.layouts.first().get("name"),
    						listeners: {
    							select: function(combo, records, eOpts) {
    								var record = records[0];
    								printProvider.setLayout(record);
    							}
    						}
    					}, {
    						xtype : 'combo',
    						fieldLabel: 'Resolution',
    						store : printProvider.dpis,
    						displayField : 'name',
    						valueField : 'value',
    						queryMode: 'local',
    						value: printProvider.dpis.first().get("value"),
    						listeners: {
    							select: function(combo, records, eOpts) {
    								var record = records[0];
    								printProvider.setDpi(record);
    							}
    						}
    					}, {
    						xtype : 'combo',
    						fieldLabel: 'Scale',
    						store : printProvider.scales,
    						displayField : 'name',
    						valueField : 'value',
    						queryMode: 'local',
    						itemId: 'scale',
    						value: printProvider.scales.first().get("value"),
    						listeners: {
    							select: function(combo, records, eOpts) {
    								var record = records[0];
    								page.setScale(record, "m");
    							}
    						}
    					} ]
    				} ],
    				bbar : [ '->', {
    					text : "Skriv ut",
    					handler : function() {
    						printDialog.setLoading(true);
    						printExtent.print();
    					}
    				} ]
    			});
    			printDialog.show();
    			var scale = printDialog.down('#scale');
    			scale.select(page.scale);
    			
    			printExtent.control.events.register('transformcomplete', null, onTransformComplete);
    			printExtent.control.events.register('transformcomplete', null, onTransformComplete);
    			printProvider.on('beforedownload', onBeforedownload);
    			printProvider.on('printexception', onPrintexception);
    			
    			OpenLayers.Control.prototype.activate.apply(this, arguments);
    		},
    		deactivate: function() {
    			if (printDialog) printDialog.close();
    			OpenLayers.Control.prototype.deactivate.apply(this, arguments);
    		}
		});
		var control = new Custom({
			type: OpenLayers.Control.TYPE_TOGGLE
		});
		config.control = control;
		
		this.callParent(arguments);
	}
});

/**
 * Action that selelcts geometry.
 */
Ext.define('MapClient.action.SelectGeometry', {
	extend:  MapClient.action.Action ,
	constructor: function(config) {
		var mapPanel = config.mapPanel;
		
    	config.control = mapPanel.selectControl;
    	
    	config.iconCls = config.iconCls || 'action-selectgeometry';
    	config.tooltip = config.tooltip || 'Select geometry';
    	config.toggleGroup = 'extraTools';
    	
    	this.callParent(arguments);
	}
});

/**
 * Parser for configuration JSON
 * Set defaults, initializes OpenLayers and Ext JS stuff.
 */
Ext.define('MapClient.config.Parser', {
	                          

	/**
	 * Parse configuration JSON
	 * 
	 * Set defaults, initializes OpenLayers and Ext JS stuff.
	 */
	parse : function(config) {
		// default config
		Ext.applyIf(config.map, {
			"resolutions" : [ 256.0, 128.0, 36.0, 12.0, 4.0, 2.0, 1.0, 0.5,	0.25, 0.125 ],
			"units" : "m",
			"projection" : "EPSG:3006",
			"fallThrough" : true,
			"controls": ["Navigation", "KeyboardDefaults"]
		});
		
		var allOverlays = !config.map.layers.some(this.testForBaseLayer);
		config.map.allOverlays = allOverlays;
		
		config.map.controls = config.map.controls.map(this.parseControl);
		
		config.map.layers = config.map.layers.map(this.addLayer);
		config.map = new OpenLayers.Map(config.map);
		
		if (!config.gui) {
			config.gui = {
				"map": false,
				"toolbar": {},
				"zoomTools":  {},
				"layers": {},
				"searchFastighet": {},
				"figurConfig": {},
				"searchCoordinate": false
			};
		}
	},
	testForBaseLayer: function(layer) {
		if (layer.options && layer.options.isBaseLayer) {
			return true;
		} else {
			return false;
		}
	},
	parseControl: function(control) {
		if (control.constructor == String) {
			return new OpenLayers.Control[control]();
		} else {
			return new OpenLayers.Control[control.type](control.options);
		}
	},
	addLayer: function(layer) {
		return new OpenLayers.Layer.WMS(layer.name, layer.url, layer.params, layer.options);
	}

});

/**
 * Combobox that searches from LM with type ahead.
 */
Ext.define('MapClient.form.SearchAddress', {
	extend :  Ext.form.field.ComboBox ,
	alias: 'widget.searchaddress',
	require: ['Ext.data.*',
	          'Ext.form.*'],
	initComponent : function() {
		var registeromrade = Ext.Object.fromQueryString(location.search).registeromrade;
		var layer = this.mapPanel.searchLayer;
		
		function doSearch(fnr, x, y) {
			this.mapPanel.setLoading(true);
			this.mapPanel.searchLayer.destroyFeatures();
			Ext.Ajax.request({
        		url: this.basePath + 'registerenheter?fnr=' + fnr,
        		success: function(response) {
        			var json = Ext.decode(response.responseText);
        			if (json.success === false) {
        				Ext.Msg.alert('Meddelande', 'Ingen fastighet kunde hittas på adressen.');
        				return;
        			}
        			this.resultPanel.expand();
        			var features = new OpenLayers.Format.GeoJSON().read(response.responseText, "FeatureCollection");
        			layer.addFeatures(features);
        			var point = new OpenLayers.Geometry.Point(x, y);
                    feature = new OpenLayers.Feature.Vector(point);
                    layer.addFeatures([feature]);
                    var resolution = OpenLayers.Util.getResolutionFromScale(1000, 'm');
                    var zoom = this.mapPanel.map.getZoomForResolution(resolution);
                    this.mapPanel.map.setCenter([x,y], zoom);
        			MapClient.app.fireEvent('searchresult', feature);
        		},
        		failure: function() {
        			Ext.Msg.alert('Fel', 'Okänt.');
        		},
        		callback: function() {
        			this.mapPanel.setLoading(false);
        		},
        		scope: this
        	});
		}
		
		this.store = Ext.create('Ext.data.Store', {
	        proxy: {
	            type: 'ajax',
	            url : this.basePath + 'addresses',
	            reader: {
	                type: 'array'
	            }
	        },
	        fields: ['id', 'name', 'x', 'y', 'fnr']
	    });
		
		this.labelWidth= 60;
		this.displayField= 'name';
		this.valueField= 'id';
		this.queryParam='q';
		this.typeAhead= true;
		this.forceSelection= true;
		
		this.listeners = {
			'select':  function(combo, records) {
				doSearch.call(this, records[0].data.fnr, records[0].data.x, records[0].data.y);
			},
			'beforequery': function(queryPlan) {
				if (registeromrade && queryPlan.query.match(registeromrade) == null) {
					queryPlan.query = registeromrade + ' ' + queryPlan.query;
				}
			},
			scope: this
		};
		
		this.callParent(arguments);
	}
});
/**
 * Combobox that searches from LM with type ahead.
 */
Ext.define('MapClient.form.SearchPlacename', {
	extend :  Ext.form.field.ComboBox ,
	alias: 'widget.searchplacename',
	require: ['Ext.data.*',
	          'Ext.form.*'],
	initComponent : function() {
		var kommunkod = Ext.Object.fromQueryString(location.search).kommunkod;
				
		this.store = Ext.create('Ext.data.Store', {
	        proxy: {
	            type: 'ajax',
	            url : this.basePath + 'placenames',
	            reader: {
	                type: 'json',
	                root: 'features'
	            },
	            extraParams: {
	            	kommunkod: kommunkod
	            }
	        },
	        fields: [
 	            {name: 'id', mapping: 'properties.id'},
 	            {name: 'name', mapping: 'properties.name'}
 	        ]
	    });
		
		this.labelWidth= 60;
		this.displayField= 'name';
		this.valueField= 'id';
		this.queryParam='q';
		this.typeAhead= true;
		this.forceSelection= true;
		
		this.listeners = {
			'select':  function(combo, records) {
				var fake = records[0].raw;
				this.mapPanel.map.setCenter(fake.geometry.coordinates, 5);
			},
			scope: this
		};
		
		this.callParent(arguments);
	}
});
/**
 * Combobox that searches from LM with type ahead.
 */
Ext.define('MapClient.form.SearchRegisterenhet', {
	extend :  Ext.form.field.ComboBox ,
	alias: 'widget.searchregisterenhet',
	require: ['Ext.data.*',
	          'Ext.form.*'],
	initComponent : function() {
		var registeromrade = Ext.Object.fromQueryString(location.search).registeromrade;
		var layer = this.mapPanel.searchLayer;
		
		function doSearch(id) {
			this.mapPanel.setLoading(true);
			this.mapPanel.searchLayer.destroyFeatures();
			Ext.Ajax.request({
        		url: this.basePath + 'registerenheter/'+id+'/enhetsomraden',
        		success: function(response) {
        			this.resultPanel.expand();
        			var features = new OpenLayers.Format.GeoJSON().read(response.responseText);
        			layer.addFeatures(features);
        			var extent = layer.getDataExtent();
        			// TODO : small extents should not be allowed to zoom below certain zoom level
        			this.mapPanel.map.zoomToExtent(extent);
        		},
        		failure: function() {
        			Ext.Msg.alert('Fel', 'Ingen träff.');
        		},
        		callback: function() {
        			this.mapPanel.setLoading(false);
        		},
        		scope: this
        	});
		}
		
		this.store = Ext.create('Ext.data.Store', {
	        proxy: {
	            type: 'ajax',
	            url : this.basePath + 'registerenheter',
	            reader: {
	                type: 'json',
	                root: 'features'
	            }
	        },
	        fields: [
 	            {name: 'id', mapping: 'properties.objid'},
 	            {name: 'name', mapping: 'properties.name'}
 	        ]
	    });
		
		this.labelWidth = 60;
		this.displayField = 'name';
		this.valueField = 'id';
		this.queryParam = 'q';
		this.typeAhead = true;
		this.forceSelection = true;
		
		this.listeners = {
			'select':  function(combo, records) {
				var id = records[0].get('id');
				doSearch.call(this, id);
			},
			'beforequery': function(queryPlan) {
				if (registeromrade && queryPlan.query.match(registeromrade) == null) {
					queryPlan.query = registeromrade + ' ' + queryPlan.query;
				}
			},
			scope: this
		};
		
		this.callParent(arguments);
	}
});
/**
 * Custom floating panel to switch baselayers
 */
Ext.define('MapClient.view.BaseLayers', {
	extend :  Ext.toolbar.Toolbar ,
	border: false,
	cls: 'oep-map-tools',
	constructor : function(config){
		var mapPanel = config.mapPanel;
		
		var createButton = function(layer){
			var button = Ext.create('Ext.Button', {
				text : layer.name,
				toggleGroup : 'baseLayers',
				pressed : layer.visibility,
				listeners : {
					toggle : function(btn, pressed, opts){
						layer.setVisibility(pressed);
						pressed ? this.disable() : this.enable();
					}
				}
			});
			
			layer.visibility ? button.disable() : button.enable();

			return button;
		};
		
		var baseLayers = mapPanel.map.layers.filter(function(layer) { return layer.isBaseLayer; });
		
		this.items = baseLayers.map(createButton);
		
		this.callParent(arguments);
	}
});
/**
 * 
 */
Ext.define('MapClient.view.Layers' ,{
    extend:  GeoExt.tree.Panel ,
                                            
                                             
	autoScroll: true,
	lines: false,
	rootVisible: false,
	width: 300,
	border: false,
    initComponent: function() {
    	
    	if (!this.renderTo) {
			this.title = 'Lager';
			this.bodyPadding = 5;
			this.collapsible = true;
		}
    	
    	this.store = Ext.create('Ext.data.TreeStore', {
            model: 'GeoExt.data.LayerTreeModel',
            root: {
                expanded: true,
                plugins: [{
                    ptype: 'gx_layercontainer',
                    store: this.mapPanel.layers	
                }]
            }
        });
    	
    	this.callParent(arguments);
    }
});
/**
 * 
 */
Ext.define('MapClient.view.Map' ,{
    extend:  GeoExt.panel.Map ,
    border: false,
    anchor: '100% 100%',
    constructor: function(config) {
    	this.initDefaultLayers(config);
    	
    	var printProvider = Ext.create('GeoExt.data.MapfishPrintProvider', {
		    url: "/print/pdf",
		    autoLoad: true,
		    listeners: {
		    	/*"loadcapabilities": function(printProvider, capabilities) {
		    		// NOTE: need to override to test locally...
		    		capabilities.createURL = "/print/pdf/create.json";
		    	},*/
		    	"encodelayer": function(printProvider, layer, encodedLayer) {
		    		if (encodedLayer && encodedLayer.baseURL) {
			    		encodedLayer.baseURL = encodedLayer.baseURL.replace('gwc/service/', '');
		    		}
		    	}/*,
		    	"beforedownload": function(printProvider, url) {
		    		console.log("beforedownload");
		    	}*/
		    }
		});
		
		var printExtent = Ext.create('GeoExt.plugins.PrintExtent', {
            printProvider: printProvider
        });
		
		config.plugins = [printExtent];
		
    	this.callParent(arguments);
    	    	
    	this.layers.add(this.searchLayer);
    	this.layers.add(this.drawLayer);
    	
    	this.selectControl = new OpenLayers.Control.SelectFeature(this.drawLayer);
    	this.map.addControl(this.selectControl);
    },
    unselectAll: function() {
    	this.drawLayer.selectedFeatures.forEach(function(feature) {
    		this.selectControl.unselect(feature);
    	}, this);
    },
    parseStyle: function(style) {
    	var template = {
                "Point": {
                    pointRadius: 4,
                    graphicName: "square",
                    fillColor: "#e8ffee",
                    fillOpacity: 0.9,
                    strokeWidth: 1,
                    strokeOpacity: 1,
                    strokeColor: "#29bf4c"
                },
                "Line": {
                	strokeWidth: 3,
                	strokeColor: "#29bf4c",
    				strokeOpacity: 1
                },
                "Polygon": {
                    strokeWidth: 3,
                    strokeOpacity: 1,
                    strokeColor: "#29bf4c",
                    fillColor: "#e8ffee",
                    fillOpacity: 0.9
                }
            };
    	
    	var createSymbolizer = function(style) {
    		var clone = Ext.clone(template);
    		if (style["Point"]) {
    			Ext.apply(clone["Point"], style["Point"]);
    			Ext.apply(clone["Line"], style["Line"]);
    			Ext.apply(clone["Polygon"], style["Polygon"]);
    		} else {
    			Ext.apply(clone["Point"], style);
    			Ext.apply(clone["Line"], style);
    			Ext.apply(clone["Polygon"], style);
    		}
    		return clone;
    	};
    	
    	var defaultStyle = new OpenLayers.Style(null, {rules: [ new OpenLayers.Rule({symbolizer: template})]});
    	var selectStyle = undefined;
    	var temporaryStyle = undefined;
    	if (style) {
    		if (style["default"]) {
    			defaultStyle = createSymbolizer(style["default"]);
    			defaultStyle = new OpenLayers.Style(null, {rules: [ new OpenLayers.Rule({symbolizer: defaultStyle})]});
    		}
    		if (style["select"]) {
    			selectStyle = createSymbolizer(style["select"]);
    			selectStyle = new OpenLayers.Style(null, {rules: [ new OpenLayers.Rule({symbolizer: selectStyle})]});
    		}
    		if (style["temporary"]) {
    			temporaryStyle = createSymbolizer(style["temporary"]);
    			temporaryStyle = new OpenLayers.Style(null, {rules: [ new OpenLayers.Rule({symbolizer: temporaryStyle})]});
    		}
    		if (!style["default"]) {
    			defaultStyle = createSymbolizer(style);
    			defaultStyle = new OpenLayers.Style(null, {rules: [ new OpenLayers.Rule({symbolizer: defaultStyle})]});
    		}
    	}
    	
        var map = {
        	"default": defaultStyle
        };
        if (selectStyle) {
        	map["select"] = selectStyle;
        }
        if (temporaryStyle) {
        	map["temporary"] = temporaryStyle;
        }
		var styleMap = new OpenLayers.StyleMap(map);
		
		return styleMap;
    },
    initDefaultLayers: function(config) {
    	if (!config.map.drawStyle) {
    		config.map.drawStyle = {
    				"default": {
    					"Point": {
    	                    pointRadius: 4,
    	                    graphicName: "square",
    	                    fillColor: "#ffffff",
    	                    fillOpacity: 1,
    	                    strokeWidth: 1,
    	                    strokeOpacity: 1,
    	                    strokeColor: "#2969bf",
    	                    label: '${label}',
    	                    labelSelect: true
    	                },
    	                "Line": {
    	                	strokeWidth: 3,
    	                	strokeColor: "#2969bf",
    	    				strokeOpacity: 1
    	                },
    	                "Polygon": {
    	                    strokeWidth: 3,
    	                    strokeOpacity: 1,
    	                    strokeColor: "#2969bf",
    	                    fillOpacity: 0
    	                }
    	            },
    	            "select": {
    		            strokeWidth: 3,
    		            strokeOpacity: 1,
    		            fillColor: "#deecff",
    	                fillOpacity: 0.9,
    		            strokeColor: "#2969bf"
    	            },
    	            "temporary": {
    		            strokeWidth: 3,
    		            strokeOpacity: 0.75,
    		            fillColor: "#ff00ff",
    	                fillOpacity: 0,
    		            strokeColor: "#ff00ff"
    	            }
    		};
    	}
    	
    	this.drawLayer = new OpenLayers.Layer.Vector('Drawings', {
    		displayInLayerSwitcher: false,
    		styleMap: this.parseStyle(config.map.drawStyle)
    	});
    	
    	if (config.map.autoClearDrawLayer) {
    		this.drawLayer.events.register('beforefeatureadded', this, function() {
        		this.drawLayer.destroyFeatures();
        	});
    	}
    	
    	function onFeatureadded(e) {
			var feature = e.feature;
			this.selectControl.select(feature);
		};
    	
    	function onBeforefeaturemodified(e) {
			var feature = e.feature;
			this.selectControl.select(feature);
		};
		
		function onAfterfeaturemodified(e) {
			var feature = e.feature;
			//this.selectControl.unselect(feature);
		};
    	
		//this.drawLayer.events.register('featureadded', this, onFeatureadded);
		this.drawLayer.events.register('beforefeaturemodified', this, onBeforefeaturemodified);
		this.drawLayer.events.register('afterfeaturemodified', this, onAfterfeaturemodified);
    	
		var searchStyle = {
			strokeDashstyle: 'dot',
			strokeWidth: 3,
            strokeOpacity: 1,
            strokeColor: "#f58d1e",
            fillOpacity: 0
		};
		
    	this.searchLayer = new OpenLayers.Layer.Vector('Searchresult', {
    		displayInLayerSwitcher: false,
    		styleMap: this.parseStyle(searchStyle)
    	});	
    }
});

/**
 * 
 */
Ext.define('MapClient.view.SearchCoordinate', {
	extend :  Ext.container.Container ,
	layout: 'column',
	defaults: {
        labelWidth: 20
	},
	width: 300,
	border: false,
	initComponent : function(config) {		
		this.items = [ {
			itemId: 'e',
			fieldLabel: 'E',
			xtype : 'textfield',
			columnWidth: 0.5
		},{
			itemId: 'n',
			fieldLabel: 'N',
			xtype : 'textfield',
			columnWidth: 0.5
		}, {
			xtype: 'button',
			text: 'Sök',
			handler: function() {
				var x = this.down('#e').getValue();
				var y = this.down('#n').getValue();
				this.mapPanel.map.setCenter([x, y], 5);
			},
			scope: this
		}];
		
		this.callParent(arguments);
	}
});
/**
 * 
 */
Ext.define('MapClient.view.SearchFastighet', {
	extend :  Ext.form.Panel ,
	            
	                                                 
	                                           
	                                             
	                                             
	width: 400,
	border: false,
	initComponent : function() {

		if (!this.renderTo) {
			this.title = 'Sök fastighet';
			this.bodyPadding = 5;
		}
		
		var data = [
		            [ 'searchregisterenhet', 'Fastighet' ],
		            [ 'searchaddress', 'Adress' ],
		            [ 'searchplacename', 'Ort' ]/*,
		            [ 'searchbyggnad', 'Byggnad' ]*/
		            ];

		var columns = [ {
			text : 'Namn',
			dataIndex : 'name',
			flex : 1
		} ];

		var store = Ext.create('GeoExt.data.FeatureStore', {
			layer : this.mapPanel.searchLayer,
			featureFilter: new OpenLayers.Filter.Function({
				evaluate: function(context) {
					if (context.attributes.name) {
						return true;
					} else {
						return false;
					}
				}
			}),
			fields : [ {
				name : 'name'
			}, {
				name : 'fid'
			}, {
				name : 'objid'
			} ]
		});
		
		var grid = Ext.create('Ext.grid.Panel', {
			columns : columns,
			store : store,
			selType : 'featuremodel'
		});
		
		function defSearchCombo(type) {
			return {
				xtype : type,
				mapPanel : this.mapPanel,
				basePath: this.basePath,
				resultPanel : grid
			};
		}
		
		function onChange(combo, value) {
			var container = this.down('#search');
			this.mapPanel.searchLayer.destroyFeatures();
			container.removeAll();
			container.add(defSearchCombo.call(this,value));
		}

		this.items = [ {
			layout : 'column',
			border: false,
			items : [ {
				xtype : 'combo',
				width : 110,
				store : data,
				forceSelection : true,
				queryMode : 'local',
				value : 'searchregisterenhet',
				border: false,
				listeners : {
					change : onChange,
					scope : this
				}
			}, {
				itemId : 'search',
				columnWidth : 1,
				layout : 'fit',
				border: false,
				items : defSearchCombo.call(this,'searchregisterenhet')
			} ]	}
		];
		
		if (!this.renderTo) {
			this.items.push(grid);
		}

		this.callParent(arguments);
	}
});
/**
 * Custom panel to represent a floating zoom slider
 */
Ext.define('MapClient.view.ZoomTools', {
	extend :  Ext.panel.Panel ,
	bodyStyle : 'background : transparent',
	border: false,
	getTools : function() {
		var pile = [];
		var slider = Ext.create('GeoExt.slider.Zoom', {
			height : 200,
			vertical : true,
			aggressive : true,
			margin  : '5 0 5 0',
			map : this.mapPanel.map
		});
		pile.push({
			xtype : 'button',
			iconCls: 'zoomtools-plus',
			mapPanel : this.mapPanel,
			scale: 'large',
            cls: 'x-action-btn',
			listeners : {
				'click' : function() {
					this.mapPanel.map.zoomIn();
				},
				scope: this
			}
		});
		pile.push(slider);
		pile.push({
			xtype : 'button',
			scale: 'large',
            cls: 'x-action-btn',
            iconCls: 'zoomtools-minus',
			mapPanel : this.mapPanel,
			listeners : {
				'click' : function() {
					this.mapPanel.map.zoomOut();
				},
				scope: this
			}
		});
		return pile;
	},

	constructor : function(config) {		
		Ext.apply(this, config);

		this.items = this.getTools();

		this.callParent(arguments);

	}

});
