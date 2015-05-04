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
		
		<form id="updateCalculationBasisQueryForm" name="queryAdminForm" method="post" action="{/Document/requestinfo/uri}">
		
			<fieldset>
				<legend>Grunduppgifter</legend>
				<xsl:call-template name="createCommonFieldsForm">
					<xsl:with-param name="element" select="CalculationBasisQuery" />
				</xsl:call-template>
			</fieldset>
			<br /><br />
			
			<fieldset>
				<legend>Beräkningsunderlag</legend>
						
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
									<label for="parameter_placeholder">Behållare</label>
									<input id="parameter_placeholder" name="parameter_placeholder" placeholder="Behållare" class="form-control"></input>
								</div>
								<div class="col-lg-4">
									<label for="parameter_type">Typ</label>
									<select id="parameter_type" name="parameter_type" class="form-control">
										<option value="int" selected="selected">Heltal</option>
										<option value="string">Text</option>
									</select>
								</div>
							</div>
							
							<!--  Params second row -->
							<div class="row" style="padding-top: 1.2em; padding-bottom: 1.2em;">
								<div class="col-lg-4">
									<label for="parameter_default">Standardvärde</label>
									<input id="parameter_default" name="parameter_default" placeholder="Standardvärde" class="form-control"></input>
								</div>
								<div class="col-lg-4">
									<label for="parameter_isinput">Inmatningsbar</label>
									<select  id="parameter_isinput" class="form-control">
										<option value="true" selected="selected">Ja</option>
										<option value="false">Nej</option>
									</select>
								</div>
								<div class="col-lg-4 link-section">
									<a href="javascript:void(0)">Lägg till parameter <i class="glyphicon glyphicon-plus"></i></a>
								</div>
							</div>
						</div>
						
					</div>

					<div id="formulasSection" class="row">
						<div class="col-lg-9">
						
							<h3>Formler</h3>
							
							<div class="formulas">
								
								<!-- Each row represents a formula -->
								<div class="row">
									<i class="glyphicon glyphicon-remove pull-right"></i>
									<div class="col-lg-12">
										
									</div>
								</div>
							
							</div>
							
							<div class="link-section" style="text-align: right;">
								<a href="javascript:void(0)">Lägg till formel <i class="glyphicon glyphicon-plus"></i></a>
							</div>
							
						</div>
						
						<div id="operandsSection" class="col-lg-3">
							<h3>Operander</h3>
							
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
							    <div role="tabpanel" class="tab-pane active" id="view1"></div>
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
				
			</fieldset>
			<div class="clearboth"></div>
		</form>
		
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