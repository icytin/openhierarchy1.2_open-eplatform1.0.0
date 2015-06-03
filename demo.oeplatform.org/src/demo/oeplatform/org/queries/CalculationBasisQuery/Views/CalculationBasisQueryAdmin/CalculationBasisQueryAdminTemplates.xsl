<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:include href="classpath://se/unlogic/hierarchy/core/utils/xsl/Common.xsl"/>

	<xsl:variable name="globalscripts">
		/jquery/jquery.js
		/ckeditor/ckeditor.js
		/ckeditor/adapters/jquery.js
		/ckeditor/init.js
	</xsl:variable>	
	
	<xsl:variable name="scripts">
		/js/lib/Lib.js
		/js/admin/StorageHandler.js
		/js/admin/OperandHandler.js
		/js/admin/ParameterHandler.js
		/js/admin/FormulaHandler.js
		/js/admin/ViewComponentHandler.js
		/js/admin/ViewHandler.js
		/js/admin/calculationbasisqueryadmin.js
	</xsl:variable>
	
	<xsl:variable name="links">
		/css/bootstrap.min.css
		/css/fix.css
		/css/admin.css
	</xsl:variable>  <!-- Bootstrap pajjar layouten så denna version har bara gridkomponenten.. funkar ändå inte -->

	<xsl:template match="Document">	
		<div id="CalculationBasisQueryProvider" class="contentitem">
			<xsl:apply-templates select="validationError"/>
			<xsl:apply-templates select="UpdateCalculationBasisQuery"/>
		</div>
	</xsl:template>
		
	<xsl:template match="UpdateCalculationBasisQuery">
	
		<h1><xsl:text>Uppdatera fråga:&#160;</xsl:text><xsl:value-of select="CalculationBasisQuery/QueryDescriptor/name" /></h1>
		<xsl:apply-templates select="validationException/validationError" />
		
		<ul id="page-tabs" class="nav nav-tabs">
		  <li role="presentation" class="active"><a href="#basic">Grunduppgifter</a></li>
		  <li role="presentation"><a href="#queryBuilder">Beräkningsunderlag</a></li>
		</ul>
		
        <div id="basic">
        
      			<form id="updateCalculationBasisQueryForm" name="queryAdminForm" method="post" action="{/Document/requestinfo/uri}">
      			
      				<h2>Grunduppgifter</h2>
      			
				<xsl:call-template name="createCommonFieldsForm">
					<xsl:with-param name="element" select="CalculationBasisQuery" />
				</xsl:call-template>
				
				<xsl:text>Här skriver vi ut adminformuläret. Försök att få in en input som sparas i db och som sedan kan visas i användarvyn av frågan</xsl:text>
			
				<!-- Detta är bara ett exempel på en input som är kopplad hela vägen från admin via model till användarguit -->
				<div class="floatleft full marginbottom">
					<xsl:call-template name="createCheckbox">
						<xsl:with-param name="id" select="'allowLetter'" />
						<xsl:with-param name="name" select="'allowLetter'" />
						<xsl:with-param name="value" select="'true'" />
						<xsl:with-param name="element" select="ContactDetailQuery" />
					</xsl:call-template>
					<label for="allowLetter"><xsl:text>Brev</xsl:text></label>
				</div>
				
				<div class="floatright margintop clearboth">
					<input type="submit" value="Spara" />
				</div>
				
				<div class="clearboth"></div>
				
			</form>
			
        </div> <!-- End basic tab -->
        <div id="queryBuilder" style="display: none">
        
        	<h2>Beräkningsunderlag</h2>
        	
			<div id="parameterSection" class="row">
			
				<div class="col-lg-12">
				
					<h3>Parametrar</h3>
					
					<!--  Params first row -->
					<div class="row">
						<div class="col-lg-4">
							<label for="parameter_name">Namn</label>
							<input id="parameter_name" name="parameter_name" placeholder="Namn" class="form-control"></input>
						</div>
						<div class="col-lg-4">
							<label for="parameter_query">Fråga</label>
							<select id="parameter_query" name="parameter_query" placeholder="Fråga" class="form-control">
								<option value="-1" selected="selected">Välj...</option>
							</select>
						</div>
						<div class="col-lg-4">
							<label for="parameter_value">Värde</label>
							<input id="parameter_value" name="parameter_value" placeholder="Värde" class="form-control"></input>
						</div>
					</div>
					
					<!-- Params second row -->
					<div class="row" style="padding-top: 1.2em; padding-bottom: 1.2em;">
						<div class="col-lg-8">
							<label for="parameter_description">Beskrivning</label>
							<input id="parameter_description" name="parameter_description" placeholder="Beskrivning (valfri)" class="form-control"></input>
						</div>
						<div class="col-lg-4 link-section">
							<a class="add" href="javascript:void(0)">Lägg till parameter <i class="glyphicon glyphicon-plus"></i></a>
						</div>
					</div>
					
					<!-- Added parameters end up in this table -->
					<div class="row">
						<div class="col-lg-12">
							<table class="table" id="addedParametersTable">
								<thead style="display: none;">
									<tr>
										<th>Namn</th>
										<th>Fråga</th>
										<th>Värde</th>
										<th>Beskrivning</th>
									</tr>
								</thead>
								<tbody>
								  <!-- Rows are added dynamically -->
								</tbody>
							</table>
						</div>
					</div>
				</div>
				
			</div>

			<div id="formulasSection" class="row">
				<div class="col-lg-9">
				
					<h3>Formler</h3>
					
					<div class="formulas">
						
						
						<!-- Name of the formula to be added -->
						<h4>Lägg till en formel</h4>
						<div class="row">
							<div class="col-lg-5" style="margin-left: .2em">
								<input class="form-control" type="text" placeholder="Ange ett namn för din formel" />
							</div>
						</div>
						<div class="row editBox">
							<div data-removable="">Dra parametrar och operander till den här ytan för att bygga din formel!</div>
						</div>
						<div class="link-section" style="text-align: right;">
							<a href="javascript:void(0)">Lägg till formel <i class="glyphicon glyphicon-plus"></i></a>
						</div>
						<div class="added">
							<h4>Tillagda formler</h4>
						</div>
					
					</div>
					
				</div>
				
				<div id="operandsSection" class="col-lg-3">
					<h3>Operander</h3>
					<!-- Dynamically added -->
				</div>
				
			</div>
			
			<div class="row">
				<div id="viewsSection" class="col-lg-9">
					<h3>Vyer</h3>
					
					<div role="tabpanel">

					  <!-- Nav tabs -->
					  <ul class="nav nav-tabs" role="tablist">
					    <li role="presentation" class="active"><a href="#view1" aria-controls="view1" role="tab" data-toggle="tab">Vy 1</a></li>
					  </ul>
					
					  <!-- Tab panes -->
					  <div class="tab-content">
					    <div role="tabpanel" class="tab-pane active" id="view1">
					    	<div data-removable="">Designa din vy genom att dra in vy-komponenter från höger och placera sedan ut de formler som du vill ha med. Notera att du kan skapa en ny vy genom att klicka "Lägg till vy" under den här boxen.</div>
					    </div>
					  </div>
					
					</div>
					
					<div class="link-section" style="text-align: right;">
						<a href="javascript:void(0)">Lägg till vy <i class="glyphicon glyphicon-plus"></i></a>
					</div>
					
				</div>
				<div id="viewComponentsSection" class="col-lg-3">
					<h3>Vy-komponenter</h3>
					<div data-row="true" draggable="true"><div class="inner-text">Rad</div></div>
					<div data-col="true" draggable="true"><div class="inner-text">Kolumn</div></div>
				</div>
			</div>
			
        </div> <!-- End query tab -->
		        

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		
		

		
		
	</xsl:template>
	
	<xsl:template match="validationError[messageKey = 'UpdateFailedContactDetailQueryNotFound']">
		
		<p class="error">
			<xsl:text>ContactDetailQueryNotFound</xsl:text>
		</p>
		
	</xsl:template>

	<xsl:template match="validationError[messageKey = 'NoContactChannelChoosen']">
	
		<p class="error">
			<xsl:text>NoContactChannelChoosen</xsl:text>
		</p>
	
	</xsl:template>
</xsl:stylesheet>