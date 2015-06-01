<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:include href="classpath://se/unlogic/hierarchy/core/utils/xsl/Common.xsl"/>

	<xsl:variable name="globalscripts">
		/jquery/jquery.js
	</xsl:variable>

	<xsl:variable name="scripts">
		/js/calculationbasisquery.js
	</xsl:variable>

	<xsl:variable name="links">
		/css/bootstrap.min.css
		/css/fix.css
	</xsl:variable> 
	
	<xsl:template match="Document">	
		
		<xsl:apply-templates select="ShowQueryValues"/>
		<xsl:apply-templates select="ShowQueryForm"/>
		
	</xsl:template>
	
	<xsl:template match="ShowQueryValues">
		<div style="width:500px;margin-left:50px;">
		<div class="row">
			<label class="title" data-type="formulaName" title="...Inkomster...">Inkomster</label>
		</div>
		<div class="row">
			<div class="col-md-6">
				<label>Summa</label>
			</div>
			<div class="col-md-6">
				<span data-type="formulaResult" data-id="13">12000</span>
				&#160;kr
			</div>
		</div>
	
		<div class="row">
			<label class="title" data-type="formulaName" title="...description...">Förbehållsbelopp</label>
		</div>
		<div class="row">
			<div class="col-md-6">
				Hyra&#160;
				<span data-type="parameter" data-name="äldreboende" data-id="13">Alnösol</span>
			</div>
			<div class="col-md-6">
				<span data-type="formulaResult" data-id="13">4239</span>
				&#160;kr
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<span data-type="parametername" data-id="13" title="...minimibelopp...">Minimibelopp</span>
			</div>
			<div class="col-md-6">
				<span data-type="parameter" data-name="minimibelopp" data-id="13">4787</span>
				&#160;kr
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<span data-type="parametername" data-id="13"
					title="...Individuellt belopp...">Individuellt belopp</span>
			</div>
			<div class="col-md-6">
				<span data-type="parameter" data-name="Individuellt belopp"
					data-id="13">1430</span>
				&#160;kr
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<span data-type="parametername" data-id="13" title="...avdrag...">Avdrag</span>
			</div>
			<div class="col-md-6">
				<span data-type="parameter" data-id="13">-550</span>
				&#160;kr
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<label>Summa</label>
			</div>
			<div class="col-md-6">
				<span data-type="formulaResult" data-id="13">9906</span>
				&#160;kr
			</div>
		</div>
	
		<div class="row">
			<label class="title">Avgift</label>
		</div>
		<div class="row">
			<div class="col-md-6">
				<span data-type="formulaname" title="...Avgiftsutrymme...">Avgiftsutrymme</span>
			</div>
			<div class="col-md-6">
				<span data-type="formulaResult" data-id="13">2094</span>
				&#160;kr
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<label class="title">Avgift</label>
			</div>
			<div class="col-md-6">
				<span data-type="formulaResult" data-id="13">1696</span>
				&#160;kr
			</div>
		</div>
	
		<div class="row">
			<label class="title">Din månadskostnad blir</label>
		</div>
		<div class="row">
			<div class="col-md-6">
				Hyra&#160;
				<span data-type="parameter" data-name="äldreboende" data-id="13">Alnösol</span>
			</div>
			<div class="col-md-6">
				<span data-type="formulaResult" data-id="13">4239</span>
				&#160;kr
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<span data-type="parameterName">Mat</span>
			</div>
			<div class="col-md-6">
				<span data-type="formulaResult" data-id="13">2910</span>
				&#160;kr
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<span data-type="formulaname">Omvårdnadsavgift</span>
			</div>
			<div class="col-md-6">
				<span data-type="formulaResult" data-id="13">1696</span>
				&#160;kr
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<label data-type="formulaname">Summa</label>
			</div>
			<div class="col-md-6">
				<span data-type="formulaResult" data-id="13">8845</span>
				&#160;kr
			</div>
		</div>
	</div>
	old stuff
		
		<div class="query">
			
			<article>
				
				<div class="heading-wrapper">
					<h2>
						<xsl:attribute name="class">
							<xsl:if test="CalculationBasisQueryInstance/QueryInstanceDescriptor/queryState = 'VISIBLE_REQUIRED'">
								<xsl:text>required</xsl:text>
							</xsl:if>
						</xsl:attribute>
						<xsl:value-of select="CalculationBasisQueryInstance/QueryInstanceDescriptor/QueryDescriptor/name"/>
					</h2>
					
					<xsl:call-template name="createUpdateButton">
						<xsl:with-param name="queryID" select="CalculationBasisQueryInstance/CalculationBasisQuery/queryID" />
					</xsl:call-template>
					
					<xsl:if test="CalculationBasisQueryInstance/CalculationBasisQuery/description">
						<span class="italic">
							<xsl:value-of select="CalculationBasisQueryInstance/CalculationBasisQuery/description" disable-output-escaping="yes" />
						</span>		
					</xsl:if>
					
				</div>
				
				<fieldset>
					
					<div class="split">
						<strong class="block">Fisk<xsl:value-of select="$i18n.FirstnameAndLastname" /></strong>
						<xsl:value-of select="CalculationBasisQueryInstance/firstname" />
						<xsl:text>&#160;</xsl:text>
						<xsl:value-of select="CalculationBasisQueryInstance/lastname" />
					</div>
					
					<div class="split odd"></div>
					
				</fieldset>
				
				<xsl:if test="CalculationBasisQueryInstance/CalculationBasisQuery/allowLetter = 'true'">
					
					<fieldset>
					
						<div class="split">
							<strong class="block"><xsl:value-of select="$i18n.Address" /></strong>
							<xsl:call-template name="printValue">
								<xsl:with-param name="value" select="CalculationBasisQueryInstance/address" />
							</xsl:call-template>
						</div>					
						
						<div class="split odd">
							<strong class="block"><xsl:value-of select="$i18n.ZipCode" /><xsl:text>&#160;</xsl:text><xsl:value-of select="$i18n.And" /><xsl:text>&#160;</xsl:text><xsl:value-of select="$i18n.PostalAddress" /></strong>
							<xsl:call-template name="printValue">
								<xsl:with-param name="value" select="CalculationBasisQueryInstance/zipCode" />
							</xsl:call-template>
							<xsl:text>&#160;</xsl:text>
							<xsl:call-template name="printValue">
								<xsl:with-param name="value" select="CalculationBasisQueryInstance/postalAddress" />
							</xsl:call-template>
						</div>
						
					</fieldset>
					
				</xsl:if>
				
				<fieldset>
					
					<xsl:if test="CalculationBasisQueryInstance/CalculationBasisQuery/allowPhone = 'true'">
						
						<div class="split">
							<strong class="block"><xsl:value-of select="$i18n.Phone" /></strong>
							<xsl:call-template name="printValue">
								<xsl:with-param name="value" select="CalculationBasisQueryInstance/phone" />
							</xsl:call-template>
						</div>
					
					</xsl:if>
					
					<xsl:if test="CalculationBasisQueryInstance/CalculationBasisQuery/allowEmail = 'true'">
			
						<div class="split">
							<strong class="block"><xsl:value-of select="$i18n.Email" /></strong>
							<xsl:call-template name="printValue">
								<xsl:with-param name="value" select="CalculationBasisQueryInstance/email" />
							</xsl:call-template>
						</div>
					
					</xsl:if>
					
					<div class="split">
						<strong class="block"><xsl:value-of select="$i18n.MobilePhone" /></strong>
						<xsl:call-template name="printValue">
							<xsl:with-param name="value" select="CalculationBasisQueryInstance/mobilePhone" />
						</xsl:call-template>
					</div>
					
				</fieldset>
				
				<fieldset>
					
					<div class="split">
						<strong class="block"><xsl:value-of select="$i18n.ChooseContactChannels" /></strong>
						<xsl:if test="CalculationBasisQueryInstance/contactByLetter = 'true'">
							<xsl:value-of select="$i18n.ContactByLetter" /><br/>
						</xsl:if>
						
						<xsl:if test="CalculationBasisQueryInstance/contactByEmail = 'true'">
							<xsl:value-of select="$i18n.ContactByEmail" /><br/>
						</xsl:if>
						
						<xsl:if test="CalculationBasisQueryInstance/contactBySMS = 'true'">
							<xsl:value-of select="$i18n.ContactBySMS" /><br/>
						</xsl:if>
						
						<xsl:if test="CalculationBasisQueryInstance/contactByPhone = 'true'">
							<xsl:value-of select="$i18n.ContactByPhone" />
						</xsl:if>
					</div>
					
				</fieldset>
				
			</article>
			
		</div>
		
	</xsl:template>	
	
	<xsl:template name="printValue">
		
		<xsl:param name="value" />
		
		<xsl:choose>
			<xsl:when test="$value">
				<xsl:value-of select="$value"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>-</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>
	
	<xsl:template match="ShowQueryForm">
	
		<xsl:variable name="shortQueryID" select="concat('q', CalculationBasisQueryInstance/CalculationBasisQuery/queryID)" />
	
		<xsl:variable name="queryID" select="concat('query_', CalculationBasisQueryInstance/CalculationBasisQuery/queryID)" />
	
		<div class="query" id="{$queryID}">
			
			<xsl:if test="EnableAjaxPosting">
				<xsl:attribute name="class">query enableAjaxPosting</xsl:attribute>
			</xsl:if>
			
			<a name="{$queryID}" />
		
			<xsl:if test="ValidationErrors/validationError[messageKey = 'NoContactChannelChoosen']">
				<div id="{$queryID}-validationerrors" class="validationerrors">
					<div class="info-box error">
						<xsl:apply-templates select="ValidationErrors/validationError[messageKey = 'NoContactChannelChoosen']"/>
						<div class="marker"></div>
					</div>
				</div>
			</xsl:if>
	
			<article>
			
				<xsl:if test="ValidationErrors/validationError[messageKey = 'NoContactChannelChoosen']">
					<xsl:attribute name="class">error</xsl:attribute>
				</xsl:if>
			
				<div class="heading-wrapper">
					
					<h2>
						<xsl:attribute name="class">
							<xsl:if test="CalculationBasisQueryInstance/QueryInstanceDescriptor/queryState = 'VISIBLE_REQUIRED'">
								<xsl:text>required</xsl:text>
							</xsl:if>
						</xsl:attribute>
						<xsl:value-of select="CalculationBasisQueryInstance/QueryInstanceDescriptor/QueryDescriptor/name"/>
					</h2>
					
					<xsl:if test="CalculationBasisQueryInstance/CalculationBasisQuery/helpText">		
						<xsl:apply-templates select="CalculationBasisQueryInstance/CalculationBasisQuery/helpText" />
					</xsl:if>
					
					<xsl:if test="CalculationBasisQueryInstance/CalculationBasisQuery/description">
						<span class="italic">
							<xsl:if test="/Document/useCKEditorForDescription = 'true'"><xsl:attribute name="class">italic html-description</xsl:attribute></xsl:if>
							<xsl:value-of select="CalculationBasisQueryInstance/CalculationBasisQuery/description" disable-output-escaping="yes" />
						</span>		
					</xsl:if>
				
				</div>
				
				<fieldset>
					<div style="width:500px;margin-left:50px;">
						<div class="row">
							<label class="title" data-type="formulaName" title="...Inkomster...">Inkomster</label>
						</div>
						<div class="row">
							<div class="col-md-6">
								<label>Summa</label>
							</div>
							<div class="col-md-6">
								<span data-type="formulaResult" data-id="13">12000</span>
								&#160;kr
							</div>
						</div>
					
						<div class="row">
							<label class="title" data-type="formulaName" title="...description...">Förbehållsbelopp</label>
						</div>
						<div class="row">
							<div class="col-md-6">
								Hyra&#160;
								<span data-type="parameter" data-name="äldreboende" data-id="13">Alnösol</span>
							</div>
							<div class="col-md-6">
								<span data-type="formulaResult" data-id="13">4239</span>
								&#160;kr
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<span data-type="parametername" data-id="13" title="...minimibelopp...">Minimibelopp</span>
							</div>
							<div class="col-md-6">
								<span data-type="parameter" data-name="minimibelopp" data-id="13">4787</span>
								&#160;kr
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<span data-type="parametername" data-id="13"
									title="...Individuellt belopp...">Individuellt belopp</span>
							</div>
							<div class="col-md-6">
								<span data-type="parameter" data-name="Individuellt belopp"
									data-id="13">1430</span>
								&#160;kr
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<span data-type="parametername" data-id="13" title="...avdrag...">Avdrag</span>
							</div>
							<div class="col-md-6">
								<span data-type="parameter" data-id="13">-550</span>
								&#160;kr
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<label>Summa</label>
							</div>
							<div class="col-md-6">
								<span data-type="formulaResult" data-id="13">9906</span>
								&#160;kr
							</div>
						</div>
					
						<div class="row">
							<label class="title">Avgift</label>
						</div>
						<div class="row">
							<div class="col-md-6">
								<span data-type="formulaname" title="...Avgiftsutrymme...">Avgiftsutrymme</span>
							</div>
							<div class="col-md-6">
								<span data-type="formulaResult" data-id="13">2094</span>
								&#160;kr
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<label class="title">Avgift</label>
							</div>
							<div class="col-md-6">
								<span data-type="formulaResult" data-id="13">1696</span>
								&#160;kr
							</div>
						</div>
					
						<div class="row">
							<label class="title">Din månadskostnad blir</label>
						</div>
						<div class="row">
							<div class="col-md-6">
								Hyra&#160;
								<span data-type="parameter" data-name="äldreboende" data-id="13">Alnösol</span>
							</div>
							<div class="col-md-6">
								<span data-type="formulaResult" data-id="13">4239</span>
								&#160;kr
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<span data-type="parameterName">Mat</span>
							</div>
							<div class="col-md-6">
								<span data-type="formulaResult" data-id="13">2910</span>
								&#160;kr
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<span data-type="formulaname">Omvårdnadsavgift</span>
							</div>
							<div class="col-md-6">
								<span data-type="formulaResult" data-id="13">1696</span>
								&#160;kr
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<label data-type="formulaname">Summa</label>
							</div>
							<div class="col-md-6">
								<span data-type="formulaResult" data-id="13">8845</span>
								&#160;kr
							</div>
						</div>
					</div>
					
				</fieldset>

				<fieldset>
					
					<div class="split">
						
						<label class="marginbottom"><xsl:value-of select="$i18n.ChooseContactChannels" /></label>
			
						<xsl:if test="CalculationBasisQueryInstance/CalculationBasisQuery/allowLetter = 'true'">
							<xsl:call-template name="createCheckbox">
								<xsl:with-param name="id" select="concat($shortQueryID, '_contactByLetter')" />
								<xsl:with-param name="name" select="concat($shortQueryID, '_contactByLetter')" />
								<xsl:with-param name="value" select="'true'" />
								<xsl:with-param name="disabled" select="'disabled'" />
								<xsl:with-param name="checked" select="CalculationBasisQueryInstance/contactByLetter"/>
							</xsl:call-template>
							<label for="{concat($shortQueryID, '_contactByLetter')}" class="checkbox"><xsl:value-of select="$i18n.ContactByLetter" /></label><br/>
						</xsl:if>

					</div>
					
				</fieldset>
			</article>
		
		</div>
		
		<script type="text/javascript">$(document).ready(function(){initCalculationBasisQuery('<xsl:value-of select="CalculationBasisQueryInstance/CalculationBasisQuery/queryID" />');});</script>
		
	</xsl:template>
	
	<xsl:template match="validationError[messageKey = 'NoContactChannelChoosen']">
	
		<span>
			<strong data-icon-before="!">
				<xsl:value-of select="$i18n.NoContactChannelChoosen" />
			</strong>
		</span>
	
	</xsl:template>
	
	<xsl:template match="validationError[messageKey = 'EmailAlreadyTaken']">
	
		<i data-icon-after="!" title="{$i18n.EmailAlreadyTaken}"></i>
	
	</xsl:template>
	
	<xsl:template match="validationError[messageKey = 'UnableToUpdateUser']">
	
		<span>
			<strong data-icon-before="!">
				<xsl:value-of select="$i18n.UnableToUpdateUser" />
			</strong>
		</span>
	
	</xsl:template>
	
	<xsl:template match="validationError[validationErrorType = 'TooLong']">
		
		<span>
			<strong data-icon-before="!">
				<xsl:value-of select="$i18n.TooLongFieldContent.part1"/>
				<xsl:value-of select="currentLength"/>
				<xsl:value-of select="$i18n.TooLongFieldContent.part2"/>
				<xsl:value-of select="maxLength"/>
				<xsl:value-of select="$i18n.TooLongFieldContent.part3"/>			
			</strong>
		</span>
		
	</xsl:template>		
	
	<xsl:template match="validationError[messageKey = 'RequiredField']">
		
		<i data-icon-after="!" title="{$i18n.RequiredField}"></i>
		
	</xsl:template>
	
	<xsl:template match="validationError[validationErrorType = 'RequiredField']">
		
		<i data-icon-after="!" title="{$i18n.RequiredField}"></i>
		
	</xsl:template>
	
	<xsl:template match="validationError[validationErrorType = 'InvalidFormat']">
		
		<i data-icon-after="!" title="{$i18n.InvalidFormat}"></i>
		
	</xsl:template>		
	
	<xsl:template match="validationError">
		
		<span>
			<strong data-icon-before="!">
				<xsl:value-of select="$i18n.UnknownValidationError"/>
			</strong>
		</span>
		
	</xsl:template>	
		
</xsl:stylesheet>