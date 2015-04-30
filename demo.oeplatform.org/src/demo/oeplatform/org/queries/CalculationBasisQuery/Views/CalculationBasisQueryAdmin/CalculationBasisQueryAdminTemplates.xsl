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
		/js/calculationbasisqueryadmin.js
	</xsl:variable>
	
	<xsl:variable name="links">
		/css/bootstrap.min.css
		/css/fix.css
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
				<div class="row">
					<div id="ParameterSection" class="col-lg-12">
					<h3>Parametrar</h3>
					<div class="row">
						<div class="col-lg-5">
							<!--  Params first container -->
							<div>Namn</div>
							<div>Behållare</div>
							<div>Typ</div>
						</div>
						<div class="col-lg-5">
							<!--  Params second container -->
							<div>Standardvärde</div>
							<div>Inmatningsbar</div>
						</div>
						<div class="col-lg-2">
							<!-- Actions -->
							<a href="javascript::void(0)"><i class="glyphicon glyphicon-plus"></i>Lägg till</a>
						</div>
					</div>
					<!--  
						<table id="parametersTable" class="table">
							<tr>
								<th>Namn</th>
								<th>Behållare</th>
								<th>Typ</th>
								<th>Standardvärde</th>
								<th>Inmatningsbar</th>
								<th></th>
							</tr>
							<tr id="parameter_1">
								<td><input id="parameter_1_name" value="Skostorlek"></input></td>
								<td><input id="parameter_1_placeholder" value="Ange skostorlek"></input></td>
								<td><select id="parameter_1_type">
									<option value="int" selected="selected">Heltal</option>
									<option value="string">Text</option>
								</select></td>
								<td><input id="parameter_1_default" value="42"></input></td>
								<td><select  id="parameter_1_isinput">
									<option value="true" selected="selected">Ja</option>
									<option value="false">Nej</option>
								</select></td>
								<td><input type="button" id="parameter_1_deletebtn" value="x"></input></td>
							</tr>
							<tr id="parameter_2">
								<td><input id="parameter_2_name" value="Ort"></input></td>
								<td><input id="parameter_2_placeholder" value="Ange ort"></input></td>
								<td><select id="parameter_2_type">
									<option value="int">Heltal</option>
									<option value="string" selected="selected">Text</option>
								</select></td>
								<td><input id="parameter_2_default" value="Sundsvall"></input></td>
								<td><select  id="parameter_2_isinput">
									<option value="true" selected="selected">Ja</option>
									<option value="false">Nej</option>
								</select></td>
								<td><input type="button" id="parameter_2_deletebtn" value="x"></input></td>
							</tr>
							<tr id="parameter_3">
								<td><input id="parameter_3_name" value="Prisbasbelopp"></input></td>
								<td><input id="parameter_3_placeholder"></input></td>
								<td><select id="parameter_3_type">
									<option value="int" selected="selected">Heltal</option>
									<option value="string">Text</option>
								</select></td>
								<td><input id="parameter_3_default" value="44500"></input></td>
								<td><select  id="parameter_3_isinput">
									<option value="true">Ja</option>
									<option value="false" selected="selected">Nej</option>
								</select></td>
								<td><input type="button" id="parameter_3_deletebtn" value="x"></input></td>
							</tr>		
						</table>
						-->
					</div>
					<div id="Formulas">
						<h3>Formler</h3>
						<xsl:text>...</xsl:text>
					</div>
					<div id="Views">
						<h3>Vy1...</h3>
							<xsl:text>...</xsl:text>
					</div>
					<div id="Operands">
						<h3>Operander</h3>
							<xsl:text>...</xsl:text>
					</div>
					<div id="Layout">
						<h3>Layout</h3>
							<xsl:text>...</xsl:text>
					</div>
					<xsl:text>Här skriver vi ut adminformuläret. Försök att få in en input som sparas i db och som sedan kan visas i användarvyn av frågan</xsl:text>
				</div>
				
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